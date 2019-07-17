package terramisc;

import java.io.File;

import com.dunk.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import terramisc.core.TFCMAchievements;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMCommonProxy;
import terramisc.core.TFCMDetails;
import terramisc.core.TFCMItemsSetup;
import terramisc.core.TFCMOreDictionary;
import terramisc.core.TFCMRecipes;
import terramisc.core.player.TFCMPlayerTracker;
import terramisc.events.EventListener;
import terramisc.handlers.BlockDropHandlerTFCM;
import terramisc.handlers.ChunkEventHandlerTFCM;
import terramisc.handlers.CraftingHandlerTFCM;
import terramisc.handlers.CraftingToolUsageHandlerTFCM;
import terramisc.handlers.EntityLivingHandlerTFCM;
import terramisc.handlers.MobDropHandlerTFCM;
import terramisc.handlers.PlayerInteractHandlerTFCM;
import terramisc.handlers.network.TFCMCreateMealPacket;
import terramisc.handlers.network.TFCMInitClientWorldPacket;
import terramisc.plants.CropRegistry;

@Mod(modid = TFCMDetails.ModID, name = TFCMDetails.ModName, version = "0.14.0", dependencies = TFCMDetails.ModDependencies)
public class TerraMisc
{
	@Instance(TFCMDetails.ModID)
	public static TerraMisc instance;

	@SidedProxy(clientSide = TFCMDetails.CLIENT_PROXY_CLASS, serverSide = TFCMDetails.SERVER_PROXY_CLASS)
	public static TFCMCommonProxy proxy;
	
	public File getMinecraftDirectory()
	{
		return proxy.getMinecraftDirectory();
	}
	
	@EventHandler
	public void preInitialize(FMLPreInitializationEvent e)
	{
		instance = this;		
		
		//Registering Fluids
		proxy.registerFluids();
		
		// Load our configs
		proxy.loadOptions();
		//Initialise Mod Blocks
		TFCMBlocks.initialise();
		//Register Tick Handler
		proxy.registerTickHandler();
		
		TFCMBlocks.setupFire();
	    
		// Register Key Bindings(Client only)
		proxy.registerKeys();
		// Register KeyBinding Handler (Client only)
		proxy.registerKeyBindingHandler();
		// Register Handler (Client only)
		proxy.registerHandlers();
		// Register Tile Entities
		proxy.registerTileEntities(true);
		// Register Sound Handler (Client only)
		proxy.registerSoundHandler();
		
		TFCMItemsSetup.ItemSetup();
		
		// Register Gui Handler
		proxy.registerGuiHandler();
		
		//Register World Generators
		proxy.registerWorldGen();
	}

	@EventHandler
	public void initialize(FMLInitializationEvent e)
	{
		// Register packets in the TFC PacketPipeline
		TerraFirmaCraft.PACKET_PIPELINE.registerPacket(TFCMInitClientWorldPacket.class);
		TerraFirmaCraft.PACKET_PIPELINE.registerPacket(TFCMCreateMealPacket.class);
		
		// Register the player tracker
		FMLCommonHandler.instance().bus().register(new TFCMPlayerTracker());
		
		// Register the tool classes
		proxy.registerToolClasses();

		// Register Crafting Handlers
		FMLCommonHandler.instance().bus().register(new CraftingHandlerTFCM());
		//FMLCommonHandler.instance().bus().register(new TFCMFoodCraftingHandler());
		FMLCommonHandler.instance().bus().register(new CraftingToolUsageHandlerTFCM());

		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandlerTFCM());
		
		// Register all the render stuff for the client
		proxy.registerRenderInformation();

		// Register recipes
		TFCMOreDictionary.registerOreDict();
		TFCMRecipes.initialise();
		
		// Register Mob Drop Handlers
		MinecraftForge.EVENT_BUS.register(new MobDropHandlerTFCM());
		
		// Register Block Drop Handlers
		MinecraftForge.EVENT_BUS.register(new BlockDropHandlerTFCM());
		
		// Register Player Right Click Handlers
		MinecraftForge.EVENT_BUS.register(new PlayerInteractHandlerTFCM());
		
		// Register Liquids
		proxy.setupFluids();
		
		//Achievements
		TFCMAchievements.initAchievements();
		FMLCommonHandler.instance().bus().register(new EventListener());
		
		// Register the Entity Living Update Handler
		MinecraftForge.EVENT_BUS.register(new EntityLivingHandlerTFCM());
		
		// Register WAILA classes
		proxy.registerWailaClasses();
		proxy.hideNEIItems();
		
		//Register Crops
		CropRegistry.registerCrops();
	}

	@EventHandler
	public void postInitialize(FMLPostInitializationEvent e)
	{
	}
}
