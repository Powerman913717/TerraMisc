package terramisc.items;

import com.dunk.tfc.Core.Metal.MetalRegistry;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.HeatRegistry;
import com.dunk.tfc.api.Interfaces.ISmeltable;
import com.dunk.tfc.api.Metal;
import com.dunk.tfc.api.TFC_ItemHeat;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import terramisc.core.TFCMDetails;

import java.util.List;

public class ItemMetalTFCM extends ItemTFCM implements ISmeltable {
    Metal metal;
    int metalAmt;

    public ItemMetalTFCM(EnumSize size, EnumWeight weight, String metalString, int amt) {
        super(size, weight);
        metal = MetalRegistry.instance.getMetalFromString(metalString);
        metalAmt = amt;
    }

    @Override
    public void registerIcons(IIconRegister registerer) {
        this.itemIcon = registerer.registerIcon(TFCMDetails.ModID + ":" + this.getUnlocalizedName().replace("item.", ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public Metal getMetalType(ItemStack is) {
        return metal;
    }

    @Override
    public short getMetalReturnAmount(ItemStack is) {
        return (short) metalAmt;
    }

    @Override
    public boolean isSmeltable(ItemStack is) {
        return true;
    }

    @Override
    public EnumTier getSmeltTier(ItemStack is) {
        return EnumTier.TierI;
    }

    public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist) {
        if (is.getItem() instanceof ItemMetalTFCM) {
            if (TFC_ItemHeat.hasTemp(is)) {
                String s = "";
                if (HeatRegistry.getInstance().isTemperatureDanger(is)) {
                    s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.danger") + " | ";
                }

                if (HeatRegistry.getInstance().isTemperatureWeldable(is)) {
                    s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.weldable") + " | ";
                }

                if (HeatRegistry.getInstance().isTemperatureWorkable(is)) {
                    s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.workable");
                }

                if (!"".equals(s))
                    arraylist.add(s);
            }
        }
    }

    @Override
    public int getItemStackLimit(ItemStack is) {
        //hot or worked items cannot stack
        if (is.hasTagCompound() && (TFC_ItemHeat.hasTemp(is) ||
                is.getTagCompound().hasKey("itemCraftingValue") && is.getTagCompound().getShort("itemCraftingValue") != 0)) {
            return 1;
        }

        return super.getItemStackLimit(is);
    }
}
