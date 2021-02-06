package terramisc.items;

import com.dunk.tfc.Items.Pottery.ItemPotteryMold;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ItemToolMold extends ItemPotteryMold {
    IIcon CopperIcon;
    IIcon BronzeIcon;
    IIcon BismuthBronzeIcon;
    IIcon BlackBronzeIcon;

    @Override
    public void registerIcons(IIconRegister registerer) {
        this.clayIcon = registerer.registerIcon("tfcm:pottery/" + metaNames[0]);
        this.ceramicIcon = registerer.registerIcon("tfcm:pottery/" + metaNames[1]);
        if (metaNames.length > 2) {
            CopperIcon = registerer.registerIcon("tfcm:pottery/" + metaNames[2]);
            BronzeIcon = registerer.registerIcon("tfcm:pottery/" + metaNames[3]);
            BismuthBronzeIcon = registerer.registerIcon("tfcm:pottery/" + metaNames[4]);
            BlackBronzeIcon = registerer.registerIcon("tfcm:pottery/" + metaNames[5]);
        }
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        if (damage > 5) {
            damage = ((damage - 2) % 4) + 2;
        }
        if (damage == 0) return this.clayIcon;
        else if (damage == 1) return this.ceramicIcon;
        else if (damage == 2) return this.CopperIcon;
        else if (damage == 3) return this.BronzeIcon;
        else if (damage == 4) return this.BismuthBronzeIcon;
        else if (damage == 5) return this.BlackBronzeIcon;

        return this.clayIcon;
    }
}
