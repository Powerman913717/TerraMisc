package terramisc.handlers;

import com.bioxx.tfc.Containers.ContainerHopper;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import terramisc.containers.ContainerBrickOven;
import terramisc.containers.ContainerCustomQuiver;
import terramisc.containers.ContainerSoupKettle;
import terramisc.tileentities.TEBrickOven;
import terramisc.tileentities.TEFruitPress;
import terramisc.tileentities.TESoupKettle;

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
			case 3:
			{
				return new ContainerBrickOven(player.inventory, ((TEBrickOven) te), world, x, y, z, 0);
			}
			case 4:
			{
				return new ContainerBrickOven(player.inventory, ((TEBrickOven) te), world, x, y, z, 1);
			}
			case 5:
			{
				return new ContainerSoupKettle(player.inventory, ((TESoupKettle) te), world, x, y, z, 0);
			}
			case 6:
			{
				return new ContainerSoupKettle(player.inventory, ((TESoupKettle) te), world, x, y, z, 1);
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
