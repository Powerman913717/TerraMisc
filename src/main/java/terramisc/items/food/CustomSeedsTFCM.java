package terramisc.items.food;

import java.util.List;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.SkillStats.SkillRank;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import terramisc.api.crops.CropIndexTFCM;
import terramisc.api.crops.CropManagerTFCM;
import terramisc.core.TFCMBlocks;
import terramisc.tileentities.TECropTFCM;

public class CustomSeedsTFCM extends ItemTerra
{
	/**
	 * The type of block this seed turns into (wheat or pumpkin stems for instance)
	 */
	private String cropId;

	public CustomSeedsTFCM(String cropId)
	{
		super();
		this.cropId = cropId;
		this.setCreativeTab(TFCTabs.TFC_FOODS);
		this.setFolder("food/");
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.TINY);
	}

	/**
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
				CropIndexTFCM crop = CropManagerTFCM.getInstance().getCropFromName(cropId);
				if (crop.needsSunlight && !TECropTFCM.hasSunlight(world, x, y + 1, z))
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedSun"));
					return false;
				}

				if (TFC_Climate.getHeightAdjustedTemp(world, x, y, z) <= crop.minAliveTemp && !crop.dormantInFrost)
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedTemp"));
					return false;
				}

				world.setBlock(x, y + 1, z, TFCMBlocks.blockCrops);

				TECropTFCM te = (TECropTFCM) world.getTileEntity(x, y + 1, z);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);

		SkillRank rank = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_AGRICULTURE);
		int nutrient = CropManagerTFCM.getInstance().getCropFromName(cropId).getCycleType();

		if (rank == SkillRank.Expert || rank == SkillRank.Master)
		{
			switch (nutrient)
			{
			case 0:
				arraylist.add(EnumChatFormatting.RED + TFC_Core.translate("gui.Nutrient.A"));
				break;
			case 1:
				arraylist.add(EnumChatFormatting.GOLD + TFC_Core.translate("gui.Nutrient.B"));
				break;
			case 2:
				arraylist.add(EnumChatFormatting.YELLOW + TFC_Core.translate("gui.Nutrient.C"));
				break;
			default:
				break;
			}

		}
	}
	
}
