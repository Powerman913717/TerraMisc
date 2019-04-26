package terramisc.waila;

import java.util.List;

import com.bioxx.tfc.Blocks.Vanilla.BlockCustomPumpkin;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Util.Helper;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import terramisc.blocks.BlockClayTFCM;
import terramisc.blocks.BlockFoodPumpkin;
import terramisc.blocks.BlockPumpkinLantern;
import terramisc.blocks.BlockPumpkinTFCM;
import terramisc.blocks.BlockTallowCandle;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMOptions;

public class TFCMWailaData implements IWailaDataProvider
{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
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
		//TileEntity te = accessor.getTileEntity();
		
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
		
		if(block == TFCBlocks.pumpkin)
		{
			currenttip = pumpkinBody(itemStack, currenttip, accessor, config);
		}
		
		if(block == TFCMBlocks.blockPumpkin)
		{
			currenttip = pumpkinFoodBody(itemStack, currenttip, accessor, config);
		}
		
		if(block == TFCMBlocks.blockPumpkinCarved)
		{
			currenttip = pumpkinCarvedBody(itemStack, currenttip, accessor, config);
		}
		
		if(block == TFCMBlocks.blockPumpkinLanternOff)
		{
			currenttip = lightBody(itemStack, currenttip, accessor, config);
		}
		
		if(block == TFCMBlocks.blockPumpkinLantern)
		{
			currenttip = pumpkinLanternBody(itemStack, currenttip, accessor, config);
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
		
		reg.registerBodyProvider(new TFCMWailaData(), BlockCustomPumpkin.class);
		reg.registerNBTProvider(new TFCMWailaData(), BlockCustomPumpkin.class);
		
		reg.registerBodyProvider(new TFCMWailaData(), BlockFoodPumpkin.class);
		reg.registerNBTProvider(new TFCMWailaData(), BlockFoodPumpkin.class);
		
		reg.registerBodyProvider(new TFCMWailaData(), BlockPumpkinTFCM.class);
		
		reg.registerBodyProvider(new TFCMWailaData(), BlockPumpkinLantern.class);
		reg.registerNBTProvider(new TFCMWailaData(), BlockPumpkinLantern.class);
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
	
	public List<String> pumpkinBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.add(TFC_Core.translate("gui.pumpkinHarvesting"));
		
		return currenttip;
	}
	
	public List<String> pumpkinFoodBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		NBTTagCompound tag = accessor.getNBTData();
		
		NBTTagList nbttaglist = tag.getTagList("Items", 10);
		ItemStack[] storage = new ItemStack[1];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		
		float weight = Food.getWeight(storage[0]);
		float decay = Food.getDecay(storage[0]);
		
		if(decay < 0)
			decay = 0;
		
		currenttip.add(TFC_Core.translate("gui.pumpkinWeight") + " " + Helper.roundNumber(weight, 10) + " " + TFC_Core.translate("gui.pumpkinDecay") + " " + Helper.roundNumber(decay, 10) + " (" + Helper.roundNumber((decay / weight) * 100, 10) + "%)");
		currenttip.add(TFC_Core.translate("gui.pumpkinCarving"));
		
		return currenttip;
	}
	
	public List<String> pumpkinCarvedBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.add(TFC_Core.translate("gui.pumpkinCandle"));
		
		return currenttip;
	}
	
	public List<String> lightBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.add(TFC_Core.translate("gui.lightCandle"));
		
		return currenttip;
	}
	
	public List<String> pumpkinLanternBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
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
}
