package terramisc.items;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Items.ItemCustomSeeds;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import terramisc.core.TFCMBlocks;

public class TFCMCustomSeeds extends ItemCustomSeeds
{
	/**
	 * The type of block this seed turns into (wheat or pumpkin stems for instance)
	 */
	private int cropId;
	
	public TFCMCustomSeeds(int cropId)
	{
		super(cropId);
		this.cropId = cropId;
	}

	/**
	 * Copied from ItemCustomSeeds
	 * 
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (side != 1 || world.isRemote)
			return false;
		else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
		{
			Block var8 = world.getBlock(x, y, z);
			if ((var8 == TFCBlocks.tilledSoil || var8 == TFCBlocks.tilledSoil2) && world.isAirBlock(x, y + 1, z))
			{
				CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
				if (crop.needsSunlight && !TECrop.hasSunlight(world, x, y + 1, z))
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedSun"));
					return false;
				}

				if (TFC_Climate.getHeightAdjustedTemp(world, x, y, z) <= crop.minAliveTemp && !crop.dormantInFrost)
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedTemp"));
					return false;
				}

				//Only Change made to make sure seeds are placing the TFCM version of Crops
				world.setBlock(x, y + 1, z, TFCMBlocks.blockCrops);

				TECrop te = (TECrop) world.getTileEntity(x, y + 1, z);
				te.cropId = cropId;
				world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
				world.markBlockForUpdate(x, y, z);
				--stack.stackSize;
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
}
