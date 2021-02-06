package terramisc.blocks;

import com.dunk.tfc.Blocks.Terrain.BlockSedSmooth;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockRoadSed extends BlockSedSmooth {
    public BlockRoadSed() {
        super();
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegisterer) {
        for (int i = 0; i < names.length; i++)
            icons[i] = iconRegisterer.registerIcon("tfcm:" + "roads/" + names[i] + "Road");
    }
}
