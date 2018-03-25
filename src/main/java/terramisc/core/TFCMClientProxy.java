package terramisc.core;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import terramisc.render.ItemCrossbowRender;
import terramisc.render.ItemLongbowRender;
import terramisc.render.ItemPolearmRender;
import terramisc.render.TESRFruitPress;
import terramisc.render.TESRTallowCandle;
import terramisc.render.TESRTallowCandleItem;
import terramisc.render.TESRTallowCandleItemIntial;
import terramisc.tileentities.TEFruitPress;
import terramisc.tileentities.TETallowCandle;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCMClientProxy extends TFCMCommonProxy
{
	@Override
	public String getCurrentLanguage()
	{
		return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
	}

	@Override
	public World getCurrentWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}

	@Override
	public boolean getGraphicsLevel()
	{
		Minecraft.getMinecraft();
		return Minecraft.isFancyGraphicsEnabled();
	}

	@Override
	public File getMinecraftDirectory()
	{
		return Minecraft.getMinecraft().mcDataDir;
	}
	
	@Override
	public void hideNEIItems()
	{
		if (Loader.isModLoaded(TFCMDetails.MODID_NEI))
		{
			/*Arrows
			API.hideItem(new ItemStack(ModItems.itemArrow_BismuthBronze_Head));
			API.hideItem(new ItemStack(ModItems.itemArrow_BlackBronze_Head));
			API.hideItem(new ItemStack(ModItems.itemArrow_BlackSteel_Head));
			API.hideItem(new ItemStack(ModItems.itemArrow_BlueSteel_Head));
			API.hideItem(new ItemStack(ModItems.itemArrow_Bronze_Head));
			API.hideItem(new ItemStack(ModItems.itemArrow_Copper_Head));
			API.hideItem(new ItemStack(ModItems.itemArrow_RedSteel_Head));
			API.hideItem(new ItemStack(ModItems.itemArrow_Steel_Head));
			API.hideItem(new ItemStack(ModItems.itemArrow_WroughtIron_Head));
			API.hideItem(new ItemStack(ModItems.itemArrow_Mold));
			API.hideItem(new ItemStack(ModItems.itemArrow_BismuthBronze));
			API.hideItem(new ItemStack(ModItems.itemArrow_BlackBronze));
			API.hideItem(new ItemStack(ModItems.itemArrow_BlackSteel));
			API.hideItem(new ItemStack(ModItems.itemArrow_BlueSteel));
			API.hideItem(new ItemStack(ModItems.itemArrow_Bronze));
			API.hideItem(new ItemStack(ModItems.itemArrow_Copper));
			API.hideItem(new ItemStack(ModItems.itemArrow_RedSteel));
			API.hideItem(new ItemStack(ModItems.itemArrow_Steel));
			API.hideItem(new ItemStack(ModItems.itemArrow_WroughtIron));
			*/
		}
	}

	@Override
	public boolean isRemote()
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void loadOptions()
	{
		//Load our settings from the server
		TFCMOptions.loadSettings();
	}

	@Override
	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(terramisc.TerraMisc.instance, new terramisc.handlers.client.TFCMGuiHandler());
	}

	@Override
	public void registerHandlers()
	{
	}

	@Override
	public void registerKeys()
	{
		uploadKeyBindingsToGame();
	}

	@Override
	public void registerKeyBindingHandler()
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderInformation()
	{
		//Other Items
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemLongBow, new ItemLongbowRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemCrossBow, new ItemCrossbowRender());
		//Halberds
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemHalberd_BismuthBronze, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemHalberd_BlackBronze, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemHalberd_BlackSteel, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemHalberd_BlueSteel, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemHalberd_Bronze, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemHalberd_Copper, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemHalberd_RedSteel, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemHalberd_Steel, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemHalberd_WroughtIron, new ItemPolearmRender());
		//War Hammers
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemWarHammer_BismuthBronze, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemWarHammer_BlackBronze, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemWarHammer_BlackSteel, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemWarHammer_BlueSteel, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemWarHammer_Bronze, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemWarHammer_Copper, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemWarHammer_RedSteel, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemWarHammer_Steel, new ItemPolearmRender());
		MinecraftForgeClient.registerItemRenderer(TFCMItems.itemWarHammer_WroughtIron, new ItemPolearmRender());
		//Tallow Candle
			//Render Registering
		TileEntitySpecialRenderer render = new TESRTallowCandle();
		TileEntitySpecialRenderer render2 = new TESRTallowCandleItemIntial();
			//Render to Tile Assigning
		ClientRegistry.bindTileEntitySpecialRenderer(TETallowCandle.class, render);
			//Held Item Rendering
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFCMBlocks.blockTallowCandle), new TESRTallowCandleItem(render2, new TETallowCandle()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFCMBlocks.blockTallowCandleOff), new TESRTallowCandleItem(render2, new TETallowCandle()));
			//Screen Overlay for custom quiver
		//MinecraftForge.EVENT_BUS.register(new RenderQuiverOverlayHandler());
	}
	
	@Override
	public void registerTileEntities(boolean flag)
	{
		super.registerTileEntities(false);
		
		// TESR registers
		ClientRegistry.registerTileEntity(TETallowCandle.class, "TallowCandleTESR", new TESRTallowCandle());
		ClientRegistry.registerTileEntity(TEFruitPress.class, "FruitPressTESR", new TESRFruitPress());
	}

	@Override
	public void uploadKeyBindingsToGame()
	{
	}
}
