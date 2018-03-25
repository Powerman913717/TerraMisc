package terramisc.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import terramisc.containers.ContainerCustomQuiver;
import terramisc.tileentities.TEFruitPress;

import com.bioxx.tfc.Containers.ContainerHopper;

import cpw.mods.fml.common.network.IGuiHandler;

public class TFCMGuiHandler implements IGuiHandler
{
//	public static final int GuiIdCustom = ModDetails.GuiOffset + 1;
	
	@Override
	public Object getServerGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getTileEntity(x, y, z);
		
		switch (Id)
		{
			case 1:
			{
				return new ContainerHopper(player.inventory, ((TEFruitPress) te));
			}
			case 2:
			{
				return new ContainerCustomQuiver(player.inventory, world, x, y, z);
			}
			default:
			{
				return null;
			}	
		}
	}

	@Override
	public Object getClientGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
}
