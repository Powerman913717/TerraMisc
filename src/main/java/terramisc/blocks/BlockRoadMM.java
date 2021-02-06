package terramisc.blocks;

import com.dunk.tfc.Blocks.Terrain.BlockMMSmooth;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockRoadMM extends BlockMMSmooth {
    public BlockRoadMM() {
        super();
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegisterer) {
        for (int i = 0; i < names.length; i++)
            icons[i] = iconRegisterer.registerIcon("tfcm:" + "roads/" + names[i] + "Road");
    }
}
