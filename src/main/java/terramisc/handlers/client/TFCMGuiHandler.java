package terramisc.handlers.client;

import com.bioxx.tfc.GUI.GuiInventoryTFC;
import com.bioxx.tfc.GUI.GuiQuiver;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import terramisc.gui.GuiBrickOven;
import terramisc.gui.GuiFruitPress;
import terramisc.gui.GuiSoupKettle;
import terramisc.tileentities.TEBrickOven;
import terramisc.tileentities.TEFruitPress;
import terramisc.tileentities.TESoupKettle;

public class TFCMGuiHandler extends terramisc.handlers.TFCMGuiHandler
{
	@Override
	public Object getClientGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te;
		
		try
		{
			te = world.getTileEntity(x, y, z);
		}
		catch(Exception e)
		{
			return null;
		}

		switch (Id)
		{
			case 1:
			{
				return new GuiFruitPress(player.inventory, ((TEFruitPress) te), world, x, y, z);
			}
			case 2:
			{
				return new GuiQuiver(player.inventory, world, x, y, z);
			}
			case 3:
			{
				return new GuiBrickOven(player.inventory, ((TEBrickOven) te), world, x, y, z, 0);
			}
			case 4:
			{
				return new GuiBrickOven(player.inventory, ((TEBrickOven) te), world, x, y, z, 1);
			}
			case 5:
			{
				return new GuiSoupKettle(player.inventory, ((TESoupKettle) te), world, x, y, z, 0);
			}
			case 6:
			{
				return new GuiSoupKettle(player.inventory, ((TESoupKettle) te), world, x, y, z, 1);
			}
			default:
			{
				return null;
			}
		}
	}
	
	@SubscribeEvent
	public void openGuiHandler(GuiOpenEvent event)
	{
		if(event.gui instanceof GuiInventory && !(event.gui instanceof GuiInventoryTFC))
		{
			event.gui = new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer);
		}
	}
}
