package terramisc.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import terramisc.containers.ContainerBrickOven;
import terramisc.containers.ContainerCustomQuiver;
import terramisc.containers.ContainerVat;
import terramisc.tileentities.TEBrickOven;
import terramisc.tileentities.TEVat;

public class GuiHandlerTFCM implements IGuiHandler {
//	public static final int GuiIdCustom = ModDetails.GuiOffset + 1;

    @Override
    public Object getServerGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);

        switch (Id) {
            case 2: {
                return new ContainerCustomQuiver(player.inventory, world, x, y, z);
            }
            case 3: {
                return new ContainerBrickOven(player.inventory, ((TEBrickOven) te), world, x, y, z, 0);
            }
            case 4: {
                return new ContainerBrickOven(player.inventory, ((TEBrickOven) te), world, x, y, z, 1);
            }
            case 5: {
                return new ContainerVat(player.inventory, ((TEVat) te), world, x, y, z);
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public Object getClientGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
