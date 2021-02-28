package terramisc.items.itemblocks;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.dunk.tfc.TileEntities.TEPottery;
import com.dunk.tfc.TileEntities.TEVessel;
import com.dunk.tfc.api.Interfaces.ISize;
import com.dunk.tfc.api.TFCBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class ItemBlockWetClay extends ItemTerraBlock {

    public ItemBlockWetClay(Block b) {
        super(b);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) {
        if (((ISize) is.getItem()).getSize(is) != null && ((ISize) is.getItem()).getWeight(is) != null && ((ISize) is.getItem()).getReach(is) != null)
            arraylist.add("\u2696" + TFC_Core.translate("gui.Weight." + ((ISize) is.getItem()).getWeight(is).getName()) + " \u21F2" +
                    TFC_Core.translate("gui.Size." + ((ISize) is.getItem()).getSize(is).getName().replace(" ", "")));

        arraylist.add(TFC_Core.translate("gui.clay.baking"));
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        if (side == 1 && player.isSneaking()) {
            Block base = world.getBlock(x, y - 1, z);
            if (base != TFCBlocks.pottery && world.isAirBlock(x, y, z)) {
                //We only want the pottery to be placeable if the block is solid on top.
                if (!world.isSideSolid(x, y - 1, z, ForgeDirection.UP))
                    return false;
                world.setBlock(x, y, z, TFCBlocks.pottery);
            } else {
                return false;
            }

            if (world.getTileEntity(x, y, z) instanceof TEPottery) {
                TEPottery te = (TEPottery) world.getTileEntity(x, y, z);
                if (te.canAddItem(0)) {
                    te.inventory[0] = stack.copy();
                    te.inventory[0].stackSize = 1;
                    world.markBlockForUpdate(x, y, z);
                    return true;
                }
            }
        }

        return false;
    }

}
