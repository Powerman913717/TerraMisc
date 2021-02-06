package terramisc.entities;

import com.dunk.tfc.Entities.EntityProjectileTFC;
import com.dunk.tfc.api.Enums.EnumDamageType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import terramisc.core.TFCMItems;

public class EntityMetalBolt extends EntityProjectileTFC {
    public Item pickupItem = TFCMItems.bolt_Copper;

    public EntityMetalBolt(World par1World) {
        super(par1World);
    }

    public EntityMetalBolt(World par1World, EntityLivingBase shooter, float force) {
        super(par1World, shooter, force);
    }

    @Override
    public EnumDamageType getDamageType(EntityLivingBase is) {
        return EnumDamageType.CRUSHING;
    }
}
