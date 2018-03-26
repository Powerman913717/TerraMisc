package terramisc.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import terramisc.blocks.BlockClayTFCM;
import terramisc.blocks.BlockFruitPress;
import terramisc.blocks.BlockRoadIgEx;
import terramisc.blocks.BlockRoadIgIn;
import terramisc.blocks.BlockRoadMM;
import terramisc.blocks.BlockRoadSed;
import terramisc.blocks.BlockStainedClayTFCM;
import terramisc.blocks.BlockTallowCandle;
import terramisc.blocks.BlockTallowCandleOff;

public class TFCMBlocks 
{
	//Candles
	public static Block blockTallowCandle;
	public static Block blockTallowCandleOff;
	
	//Roads
	public static Block blockRoadIgEx;
	public static Block blockRoadIgIn;
	public static Block blockRoadMM;
	public static Block blockRoadSed;
	
	//Devices
	public static Block blockFruitPress;
	
	//Decoration
	public static Block blockClay;
	public static Block blockStainedClay;
	
	public static void initialise()
	{
		System.out.println("[" + TFCMDetails.ModName + "] Registering Blocks");
		
		registerBlocks();
		
		System.out.println("[" + TFCMDetails.ModName + "] Done Registering Blocks");
	}

	private static void registerBlocks()
	{
		//Candles
		blockTallowCandle = new BlockTallowCandle().setBlockName("blockTallowCandle").setBlockTextureName("tfcm:TallowCandle").setHardness(0.2F);
		GameRegistry.registerBlock(blockTallowCandle, "blockTallowCandle");
		blockTallowCandleOff = new BlockTallowCandleOff().setBlockName("blockTallowCandleOff").setBlockTextureName("tfcm:TallowCandle").setHardness(0.2F);
		GameRegistry.registerBlock(blockTallowCandleOff, "blockTallowCandleOff");
		
		//Roads
		blockRoadIgEx = new BlockRoadIgEx().setHardness(16F).setBlockName("blockRoadIgEx");
		GameRegistry.registerBlock(blockRoadIgEx, terramisc.items.itemblocks.ItemBlockRoadBlock.class, "blockRoadIgEx");
		blockRoadIgIn = new BlockRoadIgIn().setHardness(16F).setBlockName("blockRoadIgIn");
		GameRegistry.registerBlock(blockRoadIgIn, terramisc.items.itemblocks.ItemBlockRoadBlock.class, "blockRoadIgIn");
		blockRoadMM = new BlockRoadMM().setHardness(15F).setBlockName("blockRoadMM");
		GameRegistry.registerBlock(blockRoadMM, terramisc.items.itemblocks.ItemBlockRoadBlock.class, "blockRoadMM");
		blockRoadSed = new BlockRoadSed().setHardness(14F).setBlockName("blockRoadSed");
		GameRegistry.registerBlock(blockRoadSed, terramisc.items.itemblocks.ItemBlockRoadBlock.class, "blockRoadSed");
	
		blockFruitPress = new BlockFruitPress().setHardness(2F).setBlockName("blockFruitPress");
		GameRegistry.registerBlock(blockFruitPress, "blockFruitPress");
		
		blockClay = new BlockClayTFCM().setBlockName("blockClay");
		GameRegistry.registerBlock(blockClay, terramisc.items.itemblocks.ItemBlockWetClay.class, "blockClay");
		blockStainedClay = new BlockStainedClayTFCM(Material.rock).setBlockName("blockStainedClay");
		GameRegistry.registerBlock(blockStainedClay, terramisc.items.itemblocks.ItemBlockStainedClayTFCM.class,"blockStainedClay");
	}
}
