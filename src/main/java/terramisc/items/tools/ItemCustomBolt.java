package terramisc.items.tools;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Enums.EnumAmmo;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.IQuiverAmmo;
import com.dunk.tfc.api.Interfaces.ISize;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemCustomBolt extends Item implements ISize, IQuiverAmmo {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) {
        ItemTerra.addSizeInformation(is, arraylist);
    }

    public ItemCustomBolt() {
        super();
        this.maxStackSize = 16;
        this.hasSubtypes = false;
        this.setCreativeTab(TFCTabs.TFC_WEAPONS);
    }

    @Override
    public void registerIcons(IIconRegister registerer) {
        this.itemIcon = registerer.registerIcon("tfcm:projectileItems/" + this.getUnlocalizedName().replace("item.", ""));
    }

    @Override
    public EnumSize getSize(ItemStack is) {
        return EnumSize.LARGE;
    }

    @Override
    public EnumWeight getWeight(ItemStack is) {
        return EnumWeight.LIGHT;
    }

    @Override
    public EnumItemReach getReach(ItemStack is) {
        return EnumItemReach.SHORT;
    }

    @Override
    public boolean canStack() {
        return false;
    }

    @Override
    public EnumAmmo getAmmoType() {
        return EnumAmmo.ARROW;
    }
}
