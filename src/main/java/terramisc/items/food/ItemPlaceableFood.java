package terramisc.items.food;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.FoodRegistry;
import com.dunk.tfc.api.TFCItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import terramisc.tileentities.TEFoodBlock;

public class ItemPlaceableFood extends ItemFoodTFC {
    @SuppressWarnings("unused")
    private EnumFoodGroup foodgroup;

    private boolean mustBeRaw;
    private float blockWeight;
    private Block placeableBlock;

    public ItemPlaceableFood(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float weight, Block block, Boolean raw) {
        super(fg, um, um, um, um, um);
        this.setCreativeTab(TFCTabs.TFC_FOODS);
        this.setFolder("food/");
        foodgroup = fg;
        TFCItems.foodList.add(this);
        this.setMaxDamage(100);
        this.hasSubtypes = false;
        smokeAbsorb = 0.5f;
        tasteSweet = sw;
        tasteSour = so;
        tasteSalty = sa;
        tasteBitter = bi;
        tasteUmami = um;
        foodID = FoodRegistry.getInstance().registerFood(fg, this);

        mustBeRaw = raw;
        blockWeight = weight;
        placeableBlock = block;
    }

    /**
     * @param fg     FoodGroup
     * @param sw     Sweet
     * @param so     Sour
     * @param sa     Salty
     * @param bi     Bitter
     * @param um     Umami
     * @param edible
     * @param weight Weight required for food to be placable as a block.
     * @param block  The block that will be set by the food item.
     * @param raw    Does the food have to be raw to be placed?
     */
    public ItemPlaceableFood(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, boolean edible, float weight, Block block, Boolean raw) {
        this(fg, sw, so, sa, bi, um, weight, block, raw);
        this.edible = edible;
    }

    public ItemPlaceableFood(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, boolean edible, boolean usable, float weight, Block block, Boolean raw) {
        this(fg, sw, so, sa, bi, um, edible, weight, block, raw);
        canBeUsedRaw = usable;
    }

    /**
     * Checks to see if a food item has been cooked, dried, infused, pickled, salted or smoked.
     */
    public boolean isFoodRaw(ItemStack is) {
        if (Food.isCooked(is))
            return false;

        if (Food.isDried(is))
            return false;

        if (Food.isInfused(is))
            return false;

        if (Food.isPickled(is))
            return false;

        if (Food.isSalted(is))
            return false;

        if (Food.isSmoked(is))
            return false;

        return true;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);

        if (player.isSneaking() && (Food.getWeight(is) >= blockWeight)) {
            if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
                side = 1;
            } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z)) {
                if (side == 0) {
                    --y;
                }

                if (side == 1) {
                    ++y;
                }

                if (side == 2) {
                    --z;
                }

                if (side == 3) {
                    ++z;
                }

                if (side == 4) {
                    --x;
                }

                if (side == 5) {
                    ++x;
                }
            }

            if (mustBeRaw) //prevents modified pumpkins from being placed. (Cooked, Dried, etc)
            {
                if (isFoodRaw(is) == false)
                    return false;
            }
            if (is.stackSize == 0) {
                return false;
            } else if (!player.canPlayerEdit(x, y, z, side, is)) {
                return false;
            } else if (y == 255 && this.placeableBlock.getMaterial().isSolid()) {
                return false;
            } else if (world.canPlaceEntityOnSide(this.placeableBlock, x, y, z, false, side, player, is)) {
                int i1 = this.getMetadata(is.getItemDamage());
                int metadata = this.placeableBlock.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, i1);

                TEFoodBlock te = null;

                if (placeBlockAt(is, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
                    te = (TEFoodBlock) world.getTileEntity(x, y, z);

                    if (te != null) {
                        ItemStack stack = transferFoodNBT(is);

                        te.storage[0] = stack;
                    }

                    world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), this.placeableBlock.stepSound.func_150496_b(), (this.placeableBlock.stepSound.getVolume() + 1.0F) / 2.0F, this.placeableBlock.stepSound.getPitch() * 0.8F);
                    is.stackSize = is.stackSize - 1;
                }

                return true;
            } else
                return false;
        } else
            return false;
    }

    /**
     * Called to actually place the block, after the location is determined
     * and all permission checks have been made.
     *
     * @param stack  The item stack that was used to place the block. This can be changed inside the method.
     * @param player The player who is placing the block. Can be null if the block is not being placed by a player.
     * @param side   The side the player (or machine) right-clicked on.
     */
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {

        if (!world.setBlock(x, y, z, placeableBlock, metadata, 3)) {
            return false;
        }

        if (world.getBlock(x, y, z) == placeableBlock) {
            placeableBlock.onBlockPlacedBy(world, x, y, z, player, stack);
            placeableBlock.onPostBlockPlaced(world, x, y, z, metadata);
        }

        return true;
    }

    /**
     * Creates new ItemStack and transfers food NBT data.
     *
     * @param is The ItemStack from which NBT data will be copied.
     */
    public ItemStack transferFoodNBT(ItemStack is) {
        ItemStack stack = new ItemStack(is.getItem());
        stack = ItemFoodTFC.createTag(stack, Food.getWeight(is), Food.getDecay(is));

        Food.setSweetMod(stack, Food.getSweetMod(is));
        Food.setSourMod(stack, Food.getSourMod(is));
        Food.setSaltyMod(stack, Food.getSaltyMod(is));
        Food.setBitterMod(stack, Food.getBitterMod(is));
        Food.setSavoryMod(stack, Food.getSavoryMod(is));

        if (Food.isCooked(is))
            Food.setCooked(stack, Food.getCooked(is));

        if (Food.isBrined(is))
            Food.setBrined(stack, true);

        if (Food.isSalted(is))
            Food.setSalted(stack, true);

        if (Food.isSmoked(is))
            Food.setSmokeCounter(stack, Food.getSmokeCounter(is));

        if (Food.isDried(is))
            Food.setDried(stack, Food.getDried(is));

        if (Food.isInfused(is))
            Food.setInfusion(stack, Food.getInfusion(is));

        return stack;
    }
}
