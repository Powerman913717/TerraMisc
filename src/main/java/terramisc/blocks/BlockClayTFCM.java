package terramisc.blocks;

import com.dunk.tfc.Blocks.BlockTerraContainer;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.TileEntities.TEPottery;
import com.dunk.tfc.api.TFCItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class BlockClayTFCM extends BlockTerraContainer {

    public BlockClayTFCM() {
        super();
        this.setBlockTextureName("minecraft:clay");
        this.setCreativeTab(TFCTabs.TFC_BUILDING);
        this.setHardness(0.3F);
        this.setResistance(5.0f);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TEPottery();
    }

    /**
     * Returns the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int metadata, Random rand, int fortune) {
        return TFCItems.clayBall;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random rand) {
        return 4;
    }

    @Override
    public int damageDropped(int dmg) {
        return dmg;
    }

    /**
     * The reason for overriding getDrops is because we only want to drop the clay item with meta 0,
     * but also need damageDropped to return the correct meta for localization purposes.
     */
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        int count = this.quantityDropped(world.rand);
        Item item = getItemDropped(metadata, world.rand, fortune);
        for (int i = 0; i < count; i++)
            ret.add(new ItemStack(item, 1, 0));
        return ret;
    }

}
