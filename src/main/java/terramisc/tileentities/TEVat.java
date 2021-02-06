package terramisc.tileentities;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Items.Tools.ItemCustomBucketMilk;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.api.Enums.EnumFuelMaterial;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.TileEntities.TEFireEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import terramisc.api.crafting.VatManager;
import terramisc.api.crafting.VatRecipe;
import terramisc.api.crafting.VatRecipeAbsorption;
import terramisc.api.crafting.VatRecipeBoil;
import terramisc.api.crafting.VatRecipeDoubleBoiler;
import terramisc.api.crafting.VatRecipeEvaporation;
import terramisc.api.crafting.VatRecipePercipitation;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TEVat extends TEFireEntity implements IInventory {
    public byte rotation;

    public int mode;
    public static final int MODE_IN = 0;
    public static final int MODE_OUT = 1;

    public ItemStack[] storage = new ItemStack[6];
    public FluidStack fluid;

    //TODO Getter methods for variable privatization.
    public int tickTimer = 0; //Tracks ticking of process function
    public int cookTimer = 0; //Tracks ticking of cooking process

    public static final int FUELSLOT_INPUT = 0; //Slots 0-3 hold fuel items
    public static final int FUELSLOT_CONSUME = 3;
    public static final int CRAFTINGSLOT_INPUT = 4;
    public static final int CRAFTINGSLOT_OUTPUT = 5;
    public static final boolean CAN_COOK_FOOD = true;

    private TEFireEntity fire; //TE used as the basis for TEFirepit and TEForge.

    public VatRecipe recipe;

    //temporary field. No need to save
    public boolean shouldDropItem = true;

    /**
     * Crafting TE for combining items and fluids at specific temperatures over a period of time.
     */
    public TEVat() {
        fuelTimeLeft = 375;
        fuelBurnTemp = 613;
        fireTemp = 350;
        maxFireTempScale = 2000; //TODO Lower max temp to realistic limitations?
    }

    /**
     * Root Method for controlling TE Behavior
     */
    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            handleFallingItems();
            handleFuelStack();

            if (fireTemp < 1 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0) {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            } else if (fireTemp >= 1 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1) {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }

            //If the fire is still burning and has fuel
            if (fuelTimeLeft > 0 && this.isBurning()) {
                if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 2) {
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 3);
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                }
            } else if (fuelTimeLeft <= 0 && this.isBurning() && storage[FUELSLOT_CONSUME] != null && !TFC_Core.isExposedToRain(worldObj, xCoord, yCoord, zCoord)) {
                if (storage[FUELSLOT_CONSUME] != null) {
                    EnumFuelMaterial m = TFC_Core.getFuelMaterial(storage[FUELSLOT_CONSUME]);
                    fuelTasteProfile = m.ordinal();
                    storage[FUELSLOT_CONSUME] = null;
                    fuelTimeLeft = m.burnTimeMax;
                    fuelBurnTemp = m.burnTempMax;
                }
            }

            //Calculate the fire temp
            float desiredTemp = handleTemp();
            handleTempFlux(desiredTemp);

            //Here we handle the bellows
            handleAirReduction();

            if (!this.isBurning()) //Don't tick food if crafting could be in process.
            {
                //We only want to bother ticking food once per 5 seconds to keep overhead low.
                tickTimer++;
                if (tickTimer > 100) {
                    TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord, CAN_COOK_FOOD);

                    tickTimer = 0;
                }
            } else if (this.isBurning() && this.canProcess())
                processItems();

            //Reset our fluid if all of the liquid is gone.
            if (fluid != null && fluid.amount == 0)
                fluid = null;

            handleFluidContainers();
        }
    }

    /**
     * Handles the crafting process including item/fluid consumption and output.
     */
    public void processItems() {
        if (!worldObj.isRemote) {
            recipe = VatManager.getInstance().findMatchingRecipe(getInputStack(), getFluidStack());

            this.cookTimer++;

            //Stops processing if enough time hasn't passed.
            if (recipe != null)
                if (!(this.cookTimer >= recipe.cookTime))
                    return;

            /**Copy of stack found in the crafting input slot.*/
            ItemStack origIS = getInputStack() != null ? getInputStack().copy() : null;
            /**Copy of fluid stack found in the vat itself.*/
            FluidStack origFS = getFluidStack() != null ? getFluidStack().copy() : null;

            //Consume input itemstack TODO Handle Food Items
            if (recipe != null) {
                //Double check recipe conditions.
                recipe = VatManager.getInstance().findMatchingRecipe(getInputStack(), getFluidStack());

                if (origIS != null && origIS.getItem() instanceof IFood)
                    return; //TODO switch to food handling code.

                ItemStack recipeStack;
                int isSize = 0;
                FluidStack fluidStack;
                int fluidAmt = 0;

                float tryScale = 1;
                int scale = 1;

                if (recipe instanceof VatRecipeAbsorption) {
                    //Attempting to scale recipe.
                    if (origFS != null)
                        fluidAmt = origFS.amount;

                    if (fluidAmt > recipe.getRecipeInFluid().amount)
                        tryScale = fluidAmt / recipe.getRecipeInFluid().amount;

                    if (origIS.stackSize >= (int) (recipe.recipeIS.stackSize * tryScale))
                        scale = (int) tryScale;
                    else
                        scale = 1;

                    //Consume items
                    recipeStack = recipe.getRecipeInIS();
                    if (recipeStack != null)
                        isSize = recipeStack.stackSize;

                    if (this.storage[CRAFTINGSLOT_INPUT] != null)
                        this.storage[CRAFTINGSLOT_INPUT].stackSize -= (isSize * scale);

                    if (this.storage[CRAFTINGSLOT_INPUT].stackSize <= 0)
                        this.storage[CRAFTINGSLOT_INPUT] = null;

                    //Consume and output fluid
                    this.fluid = recipe.getResultFluid(origIS, origFS).copy();

                    fluidAmt = (int) (recipe.getRecipeOutFluid().amount * scale);
                    if (fluidAmt > 5000)
                        fluidAmt = 5000;

                    this.fluid.amount = fluidAmt;

                }

                if (recipe instanceof VatRecipeBoil) {
                    //Scale recipe
                    tryScale = recipe.getRecipeInFluid().amount / recipe.getRecipeOutFluid().amount;

                    //Consume and output fluid
                    this.fluid = recipe.getResultFluid(origIS, origFS).copy();

                    fluidAmt = (int) (recipe.getRecipeOutFluid().amount / tryScale);
                    if (fluidAmt > 5000)
                        fluidAmt = 5000;
                }

                if (recipe instanceof VatRecipeDoubleBoiler) {
                    //Consume items
                    recipeStack = recipe.recipeIS.copy();
                    this.storage[CRAFTINGSLOT_INPUT].stackSize -= recipeStack.stackSize;

                    if (this.storage[CRAFTINGSLOT_INPUT].stackSize <= 0) {
                        this.storage[CRAFTINGSLOT_INPUT] = null;
                    }

                    //Output items
                    recipeStack = recipe.recipeOutIS.copy();
                    if (this.storage[CRAFTINGSLOT_OUTPUT] == null) {
                        this.storage[CRAFTINGSLOT_OUTPUT] = recipeStack.copy();
                    } else if (this.storage[CRAFTINGSLOT_OUTPUT].getItem() == recipeStack.getItem()) {
                        this.storage[CRAFTINGSLOT_OUTPUT].stackSize += recipeStack.stackSize;
                    }
                }

                if (recipe instanceof VatRecipeEvaporation) {
                    //Scale Recipe
                    tryScale = this.fluid.amount / recipe.getRecipeInFluid().amount;
                    if (tryScale < 1)
                        scale = 1;
                    else
                        scale = (int) tryScale;

                    //Output items
                    recipeStack = recipe.recipeOutIS.copy();
                    isSize = recipeStack.stackSize;
                    recipeStack.stackSize = (isSize * scale);
                    if (this.storage[CRAFTINGSLOT_OUTPUT] == null) {
                        this.storage[CRAFTINGSLOT_OUTPUT] = recipeStack.copy();
                    } else if (this.storage[CRAFTINGSLOT_OUTPUT].getItem() == recipeStack.getItem()) {
                        this.storage[CRAFTINGSLOT_OUTPUT].stackSize += recipeStack.stackSize;
                    }

                    //Consume fluid
                    fluidStack = recipe.getRecipeInFluid().copy();
                    fluidAmt = fluidStack.amount;

                    if (this.fluid != null)
                        this.fluid.amount -= (fluidAmt * scale);

                    if (this.fluid.amount <= 0)
                        this.fluid = null;
                }

                //TODO add melt recipe

                if (recipe instanceof VatRecipePercipitation) {
                    //Scale Recipe
                    tryScale = recipe.getRecipeInFluid().amount / recipe.getRecipeOutFluid().amount;
                    if (tryScale < 1)
                        scale = 1;
                    else
                        scale = (int) tryScale;

                    //Output items
                    recipeStack = recipe.recipeOutIS.copy();
                    isSize = recipeStack.stackSize;
                    recipeStack.stackSize = (isSize * scale);
                    if (this.storage[CRAFTINGSLOT_OUTPUT] == null) {
                        this.storage[CRAFTINGSLOT_OUTPUT] = recipeStack.copy();
                    } else if (this.storage[CRAFTINGSLOT_OUTPUT].getItem() == recipeStack.getItem()) {
                        this.storage[CRAFTINGSLOT_OUTPUT].stackSize += recipeStack.stackSize;
                    }

                    //Consume and output fluid
                    fluidStack = recipe.getResultFluid(origIS, origFS).copy();
                    fluidAmt = fluidStack.amount;

                    this.fluid = fluidStack;

                    fluidAmt *= scale;
                    if (fluidAmt > 5000)
                        fluidAmt = 5000;

                    if (this.fluid != null)
                        this.fluid.amount = fluidAmt;
                }

                this.cookTimer = 0; //Reset timer
                this.tickTimer = 100; //Attempt to tick TE inventory.
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    /**
     * Handles logic to determine if conditions for a valid recipe are present.
     *
     * @return True if a recipe process should proceed.
     */
    public boolean canProcess() {
        recipe = VatManager.getInstance().findMatchingRecipe(getInputStack(), getFluidStack());
        if (recipe == null)
            return false;

        //Temperature
        int temp = (int) this.fireTemp;
        int recipeTemp = recipe.getRecipeTemperature();

        //Vat must be hot enough for the recipe.
        if (temp < recipeTemp)
            return false;

        /**Copy of stack found in the crafting input slot.*/
        ItemStack origIS = getInputStack() != null ? getInputStack().copy() : null;
        /**Copy of fluid stack found in the vat itself.*/
        FluidStack origFS = getFluidStack() != null ? getFluidStack().copy() : null;

        //TODO Handle food
        if (recipe instanceof VatRecipeAbsorption) {
            if (origIS == null || origFS == null)
                return false;

            if (storage[CRAFTINGSLOT_OUTPUT] != null)
                return false;

            if (origIS.getItem() instanceof IFood) {
                if (Food.getWeight(origIS) < Food.getWeight(recipe.getRecipeInIS()))
                    return false;
            } else {
                if (origIS.stackSize < recipe.getRecipeInIS().stackSize)
                    return false;
            }
        }

        if (recipe instanceof VatRecipeBoil) {
            if (origIS != null || origFS == null)
                return false;

            if (storage[CRAFTINGSLOT_OUTPUT] != null)
                return false;

            if (origFS.amount < recipe.getRecipeInFluid().amount)
                return false;
        }

        if (recipe instanceof VatRecipeDoubleBoiler) {
            if (origIS == null || origFS == null)
                return false;

            if (storage[CRAFTINGSLOT_OUTPUT] != null)
                if (storage[CRAFTINGSLOT_OUTPUT].getItem() != recipe.getRecipeOutIS().getItem())
                    return false;
                else if (storage[CRAFTINGSLOT_OUTPUT].stackSize + recipe.getRecipeOutIS().stackSize > storage[CRAFTINGSLOT_OUTPUT].getMaxStackSize())
                    return false;
        }

        if (recipe instanceof VatRecipeEvaporation) {
            if (origIS != null || origFS == null)
                return false;

            if (storage[CRAFTINGSLOT_OUTPUT] != null)
                if (storage[CRAFTINGSLOT_OUTPUT].getItem() != recipe.getRecipeOutIS().getItem())
                    return false;
                else if (storage[CRAFTINGSLOT_OUTPUT].stackSize + recipe.getRecipeOutIS().stackSize > storage[CRAFTINGSLOT_OUTPUT].getMaxStackSize())
                    return false;
        }

        //TODO add melt recipe

        if (recipe instanceof VatRecipePercipitation) {
            if (origIS == null || origFS == null)
                return false;

            if (storage[CRAFTINGSLOT_OUTPUT] != null)
                if (storage[CRAFTINGSLOT_OUTPUT].getItem() != recipe.getRecipeOutIS().getItem())
                    return false;
                else if (storage[CRAFTINGSLOT_OUTPUT].stackSize + recipe.getRecipeOutIS().stackSize > storage[CRAFTINGSLOT_OUTPUT].getMaxStackSize())
                    return false;
        }

        //If items, fluids, and temperature all check out, our recipe is valid.
        return true;
    }

    public boolean isBurning() {
        return this.fireTemp >= 1;
    }

    /**
     * Creates a list of items dropped apon the vat and controls behaviors associated with the dropped items.
     */
    @SuppressWarnings("rawtypes")
    private void handleFallingItems() {
        //Create a list of all the items that are falling onto the firepit
        List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1.1, zCoord + 1));

        if (list != null && !list.isEmpty() && storage[FUELSLOT_INPUT] == null) //Only go through the list if more fuel can fit.
        {
            //Iterate through the list and check for logs and peat
            for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
                EntityItem entity = (EntityItem) iterator.next();
                ItemStack is = entity.getEntityItem();
                Item item = is.getItem();

                if (item == TFCItems.logs || item == Item.getItemFromBlock(TFCBlocks.peat)) {
                    for (int c = 0; c < is.stackSize; c++) {
                        if (storage[FUELSLOT_INPUT] == null) //Secondary check for empty fuel input slot.
                        {
                            /**
                             * Place a copy of only one of the logs into the fuel slot, due to the stack limitation of the fuel slots.
                             * Do not change to storage[0] = is;
                             */
                            setInventorySlotContents(1, new ItemStack(item, 1, is.getItemDamage()));
                            is.stackSize--;
                            handleFuelStack(); // Attempt to shift the fuel down so more fuel can be added within the same for loop.
                        }
                    }

                    if (is.stackSize == 0)
                        entity.setDead();
                }
            }
        }
    }

    /**
     * Handles fluid containers based on vat fluid mode.
     */
    private void handleFluidContainers() {
        //Handle adding fluids to the vat if the vat is currently in input mode.
        if (mode == MODE_IN) {
            ItemStack container = getInputStack();
            FluidStack inLiquid = FluidContainerRegistry.getFluidForFilledItem(container);

            if (container != null && container.getItem() instanceof IFluidContainerItem) {
                FluidStack isfs = ((IFluidContainerItem) container.getItem()).getFluid(container);
                if (isfs != null && addLiquid(isfs)) {
                    ((IFluidContainerItem) container.getItem()).drain(container, ((IFluidContainerItem) container.getItem()).getCapacity(container), true);
                }
            } else if (inLiquid != null && container != null && container.stackSize == 1) {
                if (addLiquid(inLiquid)) {
                    this.setInventorySlotContents(CRAFTINGSLOT_INPUT, FluidContainerRegistry.drainFluidContainer(container));
                }
            }
        }
        //Drain liquid from the vat to a container if the vat is in output mode.
        else if (mode == MODE_OUT) {
            ItemStack container = getInputStack();

            if (container != null && fluid != null && container.getItem() instanceof IFluidContainerItem) {
                FluidStack isfs = ((IFluidContainerItem) container.getItem()).getFluid(container);
                if (isfs == null || fluid.isFluidEqual(isfs)) {
                    fluid.amount -= ((IFluidContainerItem) container.getItem()).fill(container, fluid, true);
                    if (fluid.amount == 0)
                        fluid = null;
                }
            } else if (FluidContainerRegistry.isEmptyContainer(container)) {
                ItemStack fullContainer = this.removeLiquid(getInputStack());
                if (fullContainer.getItem() == TFCItems.woodenBucketMilk) {
                    ItemCustomBucketMilk.createTag(fullContainer, 20f);
                }
                this.setInventorySlotContents(CRAFTINGSLOT_INPUT, fullContainer);
            }
        }
    }

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int scale) {
        int time = recipe.cookTime;

        if (TFCOptions.enableDebugMode) {
            TerraFirmaCraft.LOG.info("TFCM:Recipe Cook Timer: " + this.cookTimer);
            TerraFirmaCraft.LOG.info("TFCM:Scale: " + scale);
            TerraFirmaCraft.LOG.info("TFCM:Recipe Cook Time: " + time);
        }

        return this.cookTimer * scale / time;
    }

    @Override
    public int getSizeInventory() {
        return storage.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return storage[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (storage[slot] != null) {
            if (storage[slot].stackSize <= amount) {
                ItemStack itemstack = storage[slot];
                storage[slot] = null;
                return itemstack;
            }
            ItemStack itemstack1 = storage[slot].splitStack(amount);
            if (storage[slot].stackSize == 0)
                storage[slot] = null;
            return itemstack1;
        } else
            return null;
    }

    public void ejectContents() {
        float f3 = 0.05F;
        EntityItem entityitem;
        Random rand = new Random();
        float f = rand.nextFloat() * 0.8F + 0.1F;
        float f1 = rand.nextFloat() * 0.8F + 0.3F;
        float f2 = rand.nextFloat() * 0.8F + 0.1F;

        for (int i = 0; i < getSizeInventory(); i++) {
            if (storage[i] != null) {
                entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[i]);
                entityitem.motionX = (float) rand.nextGaussian() * f3;
                entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) rand.nextGaussian() * f3;
                worldObj.spawnEntityInWorld(entityitem);
                storage[i] = null;
            }
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    public int getInvCount() {
        int count = 0;
        for (ItemStack is : storage) {
            if (is != null)
                count++;
        }
        if (storage[CRAFTINGSLOT_INPUT] != null && count == 1)
            return 0;
        return count;
    }

    /**
     * Attempts to pass fuel down to other fuel slots from the fuel input slot.
     */
    public void handleFuelStack() {
        int slot = FUELSLOT_INPUT;
        int i = slot + 1;
        int j = slot + 2;
        int k = slot + 3;

        if (storage[i] == null && storage[slot] != null) {
            storage[i] = storage[slot];
            storage[slot] = null;
        }
        if (storage[j] == null && storage[i] != null) {
            storage[j] = storage[i];
            storage[i] = null;
        }
        if (storage[k] == null && storage[j] != null) {
            storage[k] = storage[j];
            storage[j] = null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack is) {
        if (!ItemStack.areItemStacksEqual(storage[i], is)) {
            storage[i] = is;
            if (i == 0) {
                processItems();
            }
        }
    }

    @Override
    public String getInventoryName() {
        return "Vat";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack is) {
        return false;
    }

    public int getFluidLevel() {
        if (fluid != null)
            return fluid.amount;
        return 0;
    }

    public ItemStack getInputStack() {
        return storage[CRAFTINGSLOT_INPUT];
    }

    public FluidStack getFluidStack() {
        return this.fluid;
    }

    public int getMaxLiquid() {
        return 5000;
    }

    public boolean addLiquid(FluidStack inFS) {
        if (inFS != null) {
            //Prevent Liquids that are hotter than the melting point of pewter from being added.
            if (inFS.getFluid() != null && inFS.getFluid().getTemperature(inFS) > 500)
                return false;

            if (fluid == null) {
                fluid = inFS.copy();
                if (fluid.amount > this.getMaxLiquid()) {
                    fluid.amount = getMaxLiquid();
                    inFS.amount = inFS.amount - this.getMaxLiquid();

                } else
                    inFS.amount = 0;
            } else {
                //check if the barrel is full or if the fluid being added does not match the barrel liquid
                if (fluid.amount == getMaxLiquid() || !fluid.isFluidEqual(inFS))
                    return false;

                int a = fluid.amount + inFS.amount - getMaxLiquid();
                fluid.amount = Math.min(fluid.amount + inFS.amount, getMaxLiquid());
                if (a > 0)
                    inFS.amount = a;
                else
                    inFS.amount = 0;
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return true;
        }

        return false;
    }

    public ItemStack addLiquid(ItemStack is) {
        if (is == null || is.stackSize > 1)
            return is;
        if (FluidContainerRegistry.isFilledContainer(is)) {
            FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(is);
            if (addLiquid(fs)) {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                return FluidContainerRegistry.drainFluidContainer(is);
            }
        } else if (is.getItem() instanceof IFluidContainerItem) {
            FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
            if (isfs != null && addLiquid(isfs)) {
                ((IFluidContainerItem) is.getItem()).drain(is, is.getMaxDamage(), true);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
        return is;
    }

    /**
     * This attempts to remove a portion of the water in this container and put it into a valid Container Item
     */
    public ItemStack removeLiquid(ItemStack is) {
        if (is == null || is.stackSize > 1)
            return is;
        if (FluidContainerRegistry.isEmptyContainer(is)) {
            ItemStack out = FluidContainerRegistry.fillFluidContainer(fluid, is);
            if (out != null) {
                FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(out);
                fluid.amount -= fs.amount;
                is = null;
                if (fluid.amount == 0) {
                    fluid = null;
                }
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                return out;
            }
        } else if (fluid != null && is.getItem() instanceof IFluidContainerItem) {
            FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
            if (isfs == null || fluid.isFluidEqual(isfs)) {
                fluid.amount -= ((IFluidContainerItem) is.getItem()).fill(is, fluid, true);
                if (fluid.amount == 0)
                    fluid = null;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
        return is;
    }

    /**
     * This removes a specified amount of liquid from the container and updates the block.
     */
    public void drainLiquid(int amount) {
        if (this.getFluidStack() != null) {
            this.getFluidStack().amount -= amount;
            if (this.getFluidStack().amount <= 0)
                this.actionEmpty();
            else
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public int getLiquidScaled(int i) {
        if (fluid != null)
            return fluid.amount * i / getMaxLiquid();
        return 0;
    }

    public void actionEmpty() {
        fluid = null;
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte("fluidID", (byte) -1);
        this.broadcastPacketInRange(this.createDataPacket(nbt));
    }

    public void actionMode() {
        mode = mode == 0 ? 1 : 0;
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte("mode", (byte) mode);
        this.broadcastPacketInRange(this.createDataPacket(nbt));
    }

    public ResourceLocation getTexture() //Sets texture for TESR
    {
        this.fire = (TEFireEntity) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);

        if (this.fire != null && this.isBurning()) {
            return new ResourceLocation("tfcm:textures/blocks/devices/Lead Pewter Vat_Lit.png");
        } else {
            return new ResourceLocation("tfcm:textures/blocks/devices/Lead Pewter Vat.png");
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        fluid = FluidStack.loadFluidStackFromNBT(nbttagcompound.getCompoundTag("fluidNBT"));
        rotation = nbttagcompound.getByte("rotation");
        tickTimer = nbttagcompound.getInteger("processTime");
        cookTimer = nbttagcompound.getInteger("cookTime");

        NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
        storage = new ItemStack[getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < storage.length)
                storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        NBTTagCompound fluidNBT = new NBTTagCompound();
        if (fluid != null)
            fluid.writeToNBT(fluidNBT);
        nbttagcompound.setTag("fluidNBT", fluidNBT);
        nbttagcompound.setByte("rotation", rotation);

        nbttagcompound.setInteger("processTime", tickTimer);
        nbttagcompound.setInteger("cookTime", cookTimer);

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                storage[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbttagcompound.setTag("Items", nbttaglist);
    }

    public void updateGui() {
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void handleInitPacket(NBTTagCompound nbt) {
        this.rotation = nbt.getByte("rotation");
        tickTimer = nbt.getInteger("processTime");
        cookTimer = nbt.getInteger("cookTime");
        if (nbt.getInteger("fluid") != -1) {
            if (fluid != null)
                fluid.amount = nbt.getInteger("fluidAmount");
            else
                fluid = new FluidStack(nbt.getInteger("fluid"), nbt.getInteger("fluidAmount"));
        } else {
            fluid = null;
        }
        this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }

    @Override
    public void createInitNBT(NBTTagCompound nbt) {
        nbt.setByte("rotation", rotation);
        nbt.setInteger("processTime", tickTimer);
        nbt.setInteger("cookTime", cookTimer);
        nbt.setInteger("fluid", fluid != null ? fluid.getFluidID() : -1);
        nbt.setInteger("fluidAmount", fluid != null ? fluid.amount : 0);
    }

    @Override
    public void handleDataPacket(NBTTagCompound nbt) {
        if (nbt.hasKey("fluidID")) {
            if (nbt.getByte("fluidID") == -1)
                fluid = null;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        if (!worldObj.isRemote) {
            if (nbt.hasKey("mode")) {
                mode = nbt.getByte("mode");
            }
        }
    }
}
