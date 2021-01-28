package terramisc.waila;

import java.util.List;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.api.Util.Helper;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import terramisc.api.crops.CropIndexTFCM;
import terramisc.api.crops.CropManagerTFCM;
import terramisc.blocks.BlockClayTFCM;
import terramisc.blocks.BlockTallowCandle;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMItems;
import terramisc.core.TFCMOptions;
import terramisc.tileentities.TECropTFCM;

public class TFCMWailaData implements IWailaDataProvider
{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileEntity te = accessor.getTileEntity();
		
		if(te instanceof TECropTFCM)
		{
			return cropStack(accessor, config);
		}
		
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		Block block = accessor.getBlock();
		TileEntity te = accessor.getTileEntity();
		
		if(block == TFCMBlocks.blockTallowCandle)
		{
			currenttip = candleBody(itemStack, currenttip, accessor, config);
		}
		
		if(block == TFCMBlocks.blockTallowCandleOff)
		{
			currenttip = lightBody(itemStack, currenttip, accessor, config);
		}
		
		if(block == TFCMBlocks.blockClay)
		{
			currenttip = clayBody(itemStack, currenttip, accessor, config);
		}
		
		
		if(te instanceof TECropTFCM)
		{
			currenttip = cropBody(itemStack, currenttip, accessor, config);
		}
			
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) 
	{
		if (te != null)
		{
			te.writeToNBT(tag);
		}
		return tag;
	}
	
	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerBodyProvider(new TFCMWailaData(), BlockTallowCandle.class);
		reg.registerNBTProvider(new TFCMWailaData(), BlockTallowCandle.class);
		
		reg.registerBodyProvider(new TFCMWailaData(), BlockClayTFCM.class);
		reg.registerNBTProvider(new TFCMWailaData(), BlockClayTFCM.class);
		
		reg.registerStackProvider(new TFCMWailaData(), TECropTFCM.class);
		reg.registerBodyProvider(new TFCMWailaData(), TECropTFCM.class);
		reg.registerNBTProvider(new TFCMWailaData(), TECropTFCM.class);
	}
	
	public List<String> candleBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getMetadata() < 8 && TFCMOptions.TallowCandleBurnTime != 0)
		{
			NBTTagCompound tag = accessor.getNBTData();
			long hours = (long) (TFCMOptions.TallowCandleBurnTime - (TFC_Time.getTotalHours() - tag.getInteger("hourPlaced")));

			if (hours > 0)
				currenttip.add(hours + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber((100f * hours) / TFCMOptions.TallowCandleBurnTime, 10) + "%)");
		}
		return currenttip;
	}
	
	public List<String> clayBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		NBTTagCompound tag = accessor.getNBTData();
		//long startTime = tag.getLong("StartTime");
		int bakingTime = tag.getInteger("BakingTime");
		boolean canBake = tag.getBoolean("CanBake");
		
		if(canBake == true)
		{
			currenttip.add(TFC_Core.translate("gui.clay.isBaking"));
		}
		
		if(bakingTime < 6F)
		{
			currenttip.add((6 - bakingTime) + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber((100f * bakingTime) / 6, 10) + "%)");
		}
		
		return currenttip;
	}
	
	public List<String> lightBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.add(TFC_Core.translate("gui.lightCandle"));
		
		return currenttip;
	}
	
	public List<String> cropBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		float growth = tag.getFloat("growth");
		String cropId = tag.getString("cropId");

		CropIndexTFCM crop = CropManagerTFCM.getInstance().getCropFromName(cropId);
		int percentGrowth = (int) Math.min((growth / crop.numGrowthStages) * 100, 100);

		if (percentGrowth < 100)
			currenttip.add(TFC_Core.translate("gui.growth") + " : " + percentGrowth + "%");
		else
			currenttip.add(TFC_Core.translate("gui.growth") + " : " + TFC_Core.translate("gui.mature"));

		return currenttip;
	}
	
	public ItemStack cropStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		String cropId = tag.getString("cropId");
		CropIndexTFCM crop = CropManagerTFCM.getInstance().getCropFromName(cropId);
		
		if(crop != null)
		{
			if(crop.output2 != null)
				return ItemFoodTFC.createTag(new ItemStack(crop.output2));
			else
				return ItemFoodTFC.createTag(new ItemStack(crop.output1));
		}
		else
			return new ItemStack(TFCMItems.arrow_Copper);
	}
}
