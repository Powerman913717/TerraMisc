package terramisc.core;

import com.dunk.tfc.Items.ItemBlocks.ItemTerraBlock;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import terramisc.blocks.BlockClayTFCM;
import terramisc.blocks.BlockCropTFCM;
import terramisc.blocks.BlockFoodPumpkin;
import terramisc.blocks.BlockPumpkinLantern;
import terramisc.blocks.BlockPumpkinLanternOff;
import terramisc.blocks.BlockPumpkinTFCM;
import terramisc.blocks.BlockRoadIgEx;
import terramisc.blocks.BlockRoadIgIn;
import terramisc.blocks.BlockRoadMM;
import terramisc.blocks.BlockRoadSed;
import terramisc.blocks.BlockStainedClayTFCM;
import terramisc.blocks.BlockTallowCandle;
import terramisc.blocks.BlockTallowCandleOff;
import terramisc.blocks.devices.BlockBrickOven;
import terramisc.blocks.devices.BlockFruitPress;
import terramisc.blocks.devices.BlockVat;
import terramisc.items.itemblocks.ItemBlockStainedClayTFCM;
import terramisc.items.itemblocks.ItemBlockWetClay;

public class TFCMBlocks 
{
	public static int cropRenderId;
	
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
	public static Block blockBrickOven;
	public static Block vat;
	
	//Food blockCrops
	public static Block blockPumpkin;
	public static Block blockPumpkinCarved;
	public static Block blockPumpkinLantern;
	public static Block blockPumpkinLanternOff;
	
	public static Block blockCrops;
	
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
	
		//Devices
		blockFruitPress = new BlockFruitPress().setHardness(2F).setBlockName("blockFruitPress");
		GameRegistry.registerBlock(blockFruitPress, "blockFruitPress");
		blockBrickOven = new BlockBrickOven().setHardness(14F).setBlockName("blockBrickOven");
		GameRegistry.registerBlock(blockBrickOven, ItemTerraBlock.class, "blockBrickOven");
		vat = new BlockVat().setBlockName("vat");
		GameRegistry.registerBlock(vat, ItemTerraBlock.class, "vat");
		
		//Food blockCrops
		blockPumpkin = new BlockFoodPumpkin(false, false).setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("blockPumpkin");
		GameRegistry.registerBlock(blockPumpkin, ItemTerraBlock.class, "blockPumpkin");
		blockPumpkinCarved = new BlockPumpkinTFCM(true, false).setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("blockPumpkinCarved");
		GameRegistry.registerBlock(blockPumpkinCarved, ItemTerraBlock.class, "blockPumpkinCarved");
		blockPumpkinLantern = new BlockPumpkinLantern(true, true).setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("blockPumpkinLantern");
		GameRegistry.registerBlock(blockPumpkinLantern, ItemTerraBlock.class, "blockPumpkinLantern");
		blockPumpkinLanternOff = new BlockPumpkinLanternOff(true, false).setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("blockPumpkinLanternOff");
		GameRegistry.registerBlock(blockPumpkinLanternOff, ItemTerraBlock.class, "blockPumpkinLanternOff");
		
		blockCrops = new BlockCropTFCM().setBlockName("blockCrops").setHardness(0.3F).setStepSound(Block.soundTypeGrass);
		GameRegistry.registerBlock(blockCrops, "blockCrops");
		
		//Decoration
		blockClay = new BlockClayTFCM().setBlockName("blockClay");
		GameRegistry.registerBlock(blockClay, ItemBlockWetClay.class, "blockClay");
		blockStainedClay = new BlockStainedClayTFCM(Material.rock).setBlockName("blockStainedClay");
		GameRegistry.registerBlock(blockStainedClay, ItemBlockStainedClayTFCM.class,"blockStainedClay");
	}

	
	public static void setupFire()
	{
		Blocks.fire.setFireInfo(blockCrops, 20, 20);
	}
}
