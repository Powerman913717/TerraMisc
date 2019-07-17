package terramisc.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.dunk.tfc.Blocks.Terrain.BlockSedSmooth;

public class BlockRoadSed extends BlockSedSmooth
{
	public BlockRoadSed()
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
