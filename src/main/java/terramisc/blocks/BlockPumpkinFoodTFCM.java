package terramisc.blocks;

import com.dunk.tfc.Blocks.Vanilla.BlockCustomPumpkin;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import terramisc.tileentities.TEPumpkin;

public class BlockPumpkinFoodTFCM extends BlockCustomPumpkin
{

	public BlockPumpkinFoodTFCM(boolean lit) 
	{
		super(lit);
	}
	
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEPumpkin();
	}
}
