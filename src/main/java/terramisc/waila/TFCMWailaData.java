package terramisc.waila;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import terramisc.blocks.BlockClayTFCM;
import terramisc.blocks.BlockTallowCandle;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMOptions;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.Util.Helper;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

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
		//TileEntity tileEntity = accessor.getTileEntity();
		
		if (block == TFCMBlocks.blockTallowCandle)
		{
			currenttip = candleBody(itemStack, currenttip, accessor, config);
		}
		
		if (block == TFCMBlocks.blockClay)
		{
			currenttip = clayBody(itemStack, currenttip, accessor, config);
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
	}
	
	public List<String> candleBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getMetadata() < 8 && (int) TFCMOptions.TallowCandleBurnTime != 0)
		{
			NBTTagCompound tag = accessor.getNBTData();
			int candleBurnTime = (int) TFCMOptions.TallowCandleBurnTime;
			long hours = candleBurnTime - (TFC_Time.getTotalHours() - tag.getInteger("hourPlaced"));
			int color = tag.getInteger("color");

			if (hours > 0)
			{
				currenttip.add(hours + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber((100f * hours) / candleBurnTime, 10) + "%)");
			}
			
			if(color != 15)
			{
				currenttip.add("Color:" + color);
			}
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
			currenttip.add("Baking");
		}
		
		if(bakingTime < 6F)
		{
			currenttip.add((6 - bakingTime) + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber((100f * bakingTime) / 6, 10) + "%)");
		}
		
		return currenttip;
	}
}
