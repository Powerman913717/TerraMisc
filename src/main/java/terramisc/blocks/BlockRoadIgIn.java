package terramisc.blocks;

import com.bioxx.tfc.Blocks.Terrain.BlockIgInSmooth;

import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockRoadIgIn extends BlockIgInSmooth
{
	public BlockRoadIgIn()
	{
		super();
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < names.length; i++)
			icons[i] = iconRegisterer.registerIcon("tfcm:" + "roads/"+names[i]+"Road");
	}
}
