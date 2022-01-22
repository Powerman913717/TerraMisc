package terramisc.items;

import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.TFCItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBowlTallow extends ItemTFCM {
    private final IIcon[] icons = new IIcon[3];

    public ItemBowlTallow() {
        super(EnumSize.MEDIUM, EnumWeight.MEDIUM);
        this.setMaxDamage(2);
        this.hasSubtypes = true;
        this.metaNames = new String[]{"0", "1", "2"};
    }

    @Override
    public IIcon getIconFromDamage(int i) {
        return icons[i];
    }

    @Override
    public void registerIcons(IIconRegister registerer) {
        icons[0] = registerer.registerIcon("tfcm:" + getUnlocalizedName().replace("item.", "") + metaNames[0]);
        icons[1] = registerer.registerIcon("tfcm:" + getUnlocalizedName().replace("item.", "") + metaNames[1]);
        icons[2] = registerer.registerIcon("tfcm:" + getUnlocalizedName().replace("item.", "") + metaNames[2]);
    }

    @Override
    public boolean canStack() {
        return false;
    }

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
        return false;
    }

    public boolean hasContainerItem() {
        return true;
    }

    public ItemStack getContainerItem(ItemStack itemStack) {
        if (itemStack.getItemDamage() < 2) {
            itemStack.attemptDamageItem(1, itemRand);

        } else {
            itemStack = new ItemStack(TFCItems.potteryBowl, 1, 1);

        }
        return itemStack;

    }
}
