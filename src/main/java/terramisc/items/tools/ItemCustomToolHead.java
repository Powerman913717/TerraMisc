package terramisc.items.tools;

import com.dunk.tfc.Items.Tools.ItemMiscToolHead;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemCustomToolHead extends ItemMiscToolHead {
    @Override
    public void registerIcons(IIconRegister registerer) {
        String name = this.getUnlocalizedName().replace("item.", "");
        this.itemIcon = registerer.registerIcon("tfcm:toolheads/" + name);
    }
}
