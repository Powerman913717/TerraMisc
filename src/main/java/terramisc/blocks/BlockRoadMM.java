package terramisc.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.Blocks.Terrain.BlockMMSmooth;

public class BlockRoadMM extends BlockMMSmooth
{
	public BlockRoadMM()
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
