package terramisc.core;

import java.io.File;

import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import terramisc.tileentities.TEBrickOven;
import terramisc.tileentities.TECropTFCM;
import terramisc.tileentities.TEFoodBlock;
import terramisc.tileentities.TEFruitPress;
import terramisc.tileentities.TEPumpkinLantern;
import terramisc.tileentities.TETallowCandle;
import terramisc.tileentities.TEVat;
import terramisc.tileentities.TEWetClay;
import terramisc.worldGen.Generators.WorldGenGrowCropsTFCM;

public class TFCMCommonProxy
{
	public String getCurrentLanguage()
	{
		return null;
	}

	public World getCurrentWorld()
	{
		return MinecraftServer.getServer().getEntityWorld();
	}

	public boolean getGraphicsLevel()
	{
		return false;
	}
	
	public File getMinecraftDirectory()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().getFile("");
	}

	public void hideNEIItems()
	{
	}
	
	public boolean isRemote()
	{
		return false;
	}

	public void loadOptions()
	{
		//Load our settings from the Options file
		TFCMOptions.loadSettings();
	}
	
	public void onClientLogin()
	{
	}

	public void registerFluidIcons()
	{
	}

	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(terramisc.TerraMisc.instance, new terramisc.handlers.GuiHandlerTFCM());
	}

	public void registerHandlers()
	{
	}

	public void registerKeys()
	{
	}

	public void registerKeyBindingHandler()
	{
	}

	public void registerRenderInformation()
	{
		// NOOP on server
	}

	public void registerSoundHandler()
	{
	}

	public void registerTickHandler()
	{
		FMLCommonHandler.instance().bus().register(new terramisc.handlers.ServerTickHandlerTFCM());
	}
	
	public void registerWorldGen()
	{
		GameRegistry.registerWorldGenerator(new WorldGenGrowCropsTFCM(), 9);
	}
	
	public void registerTileEntities(boolean flag)
	{
		// non TESR registers
		GameRegistry.registerTileEntity(TEWetClay.class, "WetClay");
		GameRegistry.registerTileEntity(TEFoodBlock.class, "FoodBlock");
		GameRegistry.registerTileEntity(TEPumpkinLantern.class, "PumpkinLantern");
		GameRegistry.registerTileEntity(TECropTFCM.class, "CropTFCM");
		GameRegistry.registerTileEntity(TEVat.class, "VatTFCM");

		if (flag)
		{
			// TESR registers
			GameRegistry.registerTileEntity(TETallowCandle.class, "TallowCandle");
			GameRegistry.registerTileEntity(TEFruitPress.class, "FruitPress");
			GameRegistry.registerTileEntity(TEBrickOven.class, "BrickOven");
			GameRegistry.registerTileEntity(TEVat.class, "VatTFCM");
		}
	}

	public void registerFluids()
	{
		FluidRegistry.registerFluid(TFCMFluids.REDFRUITJUICE);
		
		FluidRegistry.registerFluid(TFCMFluids.JUICEAPPLE);
		
		FluidRegistry.registerFluid(TFCMFluids.JUICELEMON);
		
		FluidRegistry.registerFluid(TFCMFluids.SOYMILK);
	}
	
	public void setupFluids()
	{
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.REDFRUITJUICE, 250), new ItemStack(TFCMItems.bottleFruitJuice), new ItemStack(TFCItems.glassBottle));
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.JUICEAPPLE, 250), new ItemStack(TFCMItems.bottleJuiceApple), new ItemStack(TFCItems.glassBottle));
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.JUICELEMON, 250), new ItemStack(TFCMItems.bottleJuiceLemon), new ItemStack(TFCItems.glassBottle));
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.SOYMILK, 250), new ItemStack(TFCMItems.bottleSoyMilk), new ItemStack(TFCItems.glassBottle));
	}
	
	public void registerToolClasses()
	{
	}

	public void registerWailaClasses()
	{
		System.out.println("[TerraMisc] Registering WAILA Classes (TFCM)");
		
		FMLInterModComms.sendMessage("Waila", "register", "terramisc.waila.TFCMWailaData.callbackRegister");
		
		System.out.println("[TerraMisc] Done Registering WAILA Classes (TFCM)");
	}

	public void uploadKeyBindingsToGame()
	{
	}
}
