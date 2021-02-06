package terramisc.containers;

import com.dunk.tfc.Containers.ContainerTFC;
import com.dunk.tfc.Containers.Slots.SlotBlocked;
import com.dunk.tfc.Containers.Slots.SlotFoodOnly;
import com.dunk.tfc.Containers.Slots.SlotSize;
import com.dunk.tfc.Core.Player.PlayerInventory;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TileEntities.TEFireEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import terramisc.containers.slots.SlotMealFood;
import terramisc.containers.slots.SlotMealFoodGroup;
import terramisc.tileentities.TEBrickOven;

public class ContainerBrickOven extends ContainerTFC {
    //Combining Functionality from FoodPrep, and Grill

    private TEBrickOven te;
    private EntityPlayer player;
    private int guiTab;

    private TEFireEntity fire;
    private float firetemp;

    public ContainerBrickOven(InventoryPlayer playerinv, TEBrickOven pile, World world, int x, int y, int z, int tab) {
        this.player = playerinv.player;
        this.te = pile;
        guiTab = tab;

        firetemp = -1111;

        if (world.getTileEntity(x, y - 1, z) instanceof TEFireEntity) {
            fire = (TEFireEntity) world.getTileEntity(x, y - 1, z);
        }

        pile.openInventory();
        layoutContainer(playerinv, pile, 0, 0);
        pile.lastTab = tab;
        PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90, false, true);
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        te.closeInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }

    protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {
        if (guiTab == 0) //Pie
        {
            this.addSlotToContainer(new SlotMealFood(chestInventory, 0, 44, 8).addItemInclusion(TFCItems.wheatDough, TFCItems.barleyDough, TFCItems.oatDough,
                    TFCItems.ryeDough, TFCItems.riceDough, TFCItems.cornmealDough).setSize(EnumSize.HUGE));

            this.addSlotToContainer(new SlotBlocked(chestInventory, 1, 26, 8));
            this.addSlotToContainer(new SlotMealFoodGroup(chestInventory, 2, 62, 8, EnumFoodGroup.Fruit).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
            this.addSlotToContainer(new SlotFoodOnly(chestInventory, 3, 80, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
            this.addSlotToContainer(new SlotFoodOnly(chestInventory, 4, 98, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
            this.addSlotToContainer(new SlotFoodOnly(chestInventory, 5, 116, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
        } else if (guiTab == 1) //Casserole
        {
            this.addSlotToContainer(new SlotMealFood(chestInventory, 1, 44, 8).addItemInclusion(TFCItems.wheatGround, TFCItems.barleyGround, TFCItems.oatGround,
                    TFCItems.ryeGround, TFCItems.riceGround, TFCItems.cornmealGround).setSize(EnumSize.HUGE));

            this.addSlotToContainer(new SlotBlocked(chestInventory, 0, 26, 8));
            this.addSlotToContainer(new SlotFoodOnly(chestInventory, 2, 62, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
            this.addSlotToContainer(new SlotFoodOnly(chestInventory, 3, 80, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
            this.addSlotToContainer(new SlotFoodOnly(chestInventory, 4, 98, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
            this.addSlotToContainer(new SlotFoodOnly(chestInventory, 5, 116, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
        }

        this.addSlotToContainer(new SlotFoodOnly(chestInventory, 6, 53, 30)); //Output Slot?
        this.addSlotToContainer(new SlotFoodOnly(chestInventory, 7, 53, 58));
        this.addSlotToContainer(new SlotSize(chestInventory, 8, 134, 8).setSize(EnumSize.SMALL));
        this.addSlotToContainer(new SlotSize(chestInventory, 9, 134, 26).setSize(EnumSize.SMALL));
        this.addSlotToContainer(new SlotSize(chestInventory, 10, 134, 44).setSize(EnumSize.SMALL));
        this.addSlotToContainer(new SlotSize(chestInventory, 11, 134, 62).setSize(EnumSize.SMALL));
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    @Override
    public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum) {
        ItemStack origStack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotNum);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            origStack = slotStack.copy();

            // From food prep to inventory
            if (slotNum < 10) {
                if (!this.mergeItemStack(slotStack, 10, inventorySlots.size(), true))
                    return null;
            }
            // Slot exception lists already handle what should go where
            else {
                if (!this.mergeItemStack(slotStack, 0, 10, false))
                    return null;
            }

            if (slotStack.stackSize <= 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();

            if (slotStack.stackSize == origStack.stackSize)
                return null;

            slot.onPickupFromSlot(player, slotStack);
        }

        return origStack;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1) {
            ItemStack var2 = ((Slot) this.inventorySlots.get(var1)).getStack();
            ItemStack var3 = (ItemStack) this.inventoryItemStacks.get(var1);

            if (!ItemStack.areItemStacksEqual(var3, var2)) {
                var3 = var2 == null ? null : var2.copy();
                this.inventoryItemStacks.set(var1, var3);

                for (int var4 = 0; var4 < this.crafters.size(); ++var4)
                    ((ICrafting) this.crafters.get(var4)).sendSlotContents(this, var1, var3);
            }
        }

        for (int var1 = 0; var1 < this.crafters.size(); ++var1) {
            ICrafting var2 = (ICrafting) this.crafters.get(var1);
            if (this.fire != null && this.firetemp != this.fire.fireTemp)
                var2.sendProgressBarUpdate(this, 0, (int) this.fire.fireTemp);
        }

        if (this.fire != null)
            firetemp = this.fire.fireTemp;
        else firetemp = 0;
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
        if (this.fire != null && par1 == 0)
            this.fire.fireTemp = par2;
    }
}
