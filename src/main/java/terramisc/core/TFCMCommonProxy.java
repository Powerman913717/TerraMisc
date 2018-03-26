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
import terramisc.tileentities.TEFruitPress;
import terramisc.tileentities.TETallowCandle;
import terramisc.tileentities.TEWetClay;

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
		NetworkRegistry.INSTANCE.registerGuiHandler(terramisc.TerraMisc.instance, new terramisc.handlers.TFCMGuiHandler());
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
		FMLCommonHandler.instance().bus().register(new terramisc.handlers.TFCMServerTickHandler());
	}
	
	public void registerTileEntities(boolean flag)
	{
		// non TESR registers
		GameRegistry.registerTileEntity(TEWetClay.class, "WetClay");

		if (flag)
		{
			// TESR registers
			GameRegistry.registerTileEntity(TETallowCandle.class, "TallowCandle");
			GameRegistry.registerTileEntity(TEFruitPress.class, "FruitPress");
		}
	}

	public void registerFluids()
	{
		FluidRegistry.registerFluid(TFCMFluids.FRUITJUICE);
		FluidRegistry.registerFluid(TFCMFluids.FRUITWINE);
		
		FluidRegistry.registerFluid(TFCMFluids.JUICEAPPLE);
		FluidRegistry.registerFluid(TFCMFluids.JUICEORANGE);
		
		FluidRegistry.registerFluid(TFCMFluids.JUICELEMON);
		FluidRegistry.registerFluid(TFCMFluids.LEMONADE);
		
		FluidRegistry.registerFluid(TFCMFluids.SOYMILK);
		FluidRegistry.registerFluid(TFCMFluids.WATERHOT);
		
		FluidRegistry.registerFluid(TFCMFluids.JUICEONION);
	}
	
	public void setupFluids()
	{
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.FRUITJUICE, 250), new ItemStack(TFCMItems.itemBottleFruitJuice), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.FRUITWINE, 250), new ItemStack(TFCMItems.itemBottleFruitWine), new ItemStack(TFCItems.glassBottle));
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.JUICEAPPLE, 250), new ItemStack(TFCMItems.itemBottleJuiceApple), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.JUICEORANGE, 250), new ItemStack(TFCMItems.itemBottleJuiceOrange), new ItemStack(TFCItems.glassBottle));
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.JUICELEMON, 250), new ItemStack(TFCMItems.itemBottleJuiceLemon), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.LEMONADE, 250), new ItemStack(TFCMItems.itemBottleLemonade), new ItemStack(TFCItems.glassBottle));
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.SOYMILK, 250), new ItemStack(TFCMItems.itemBottleSoyMilk), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.WATERHOT, 1000), new ItemStack(TFCMItems.itemBucketHotWater), new ItemStack(TFCItems.woodenBucketEmpty));
	
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCMFluids.JUICEONION, 250), new ItemStack(TFCMItems.itemBottleJuiceOnion), new ItemStack(TFCItems.glassBottle));
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
