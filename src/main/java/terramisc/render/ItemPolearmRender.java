package terramisc.render;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemPolearmRender implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
                                         ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        EntityLivingBase entity = (EntityLivingBase) data[1];

        GL11.glPopMatrix();                                         // prevents Forge from pre-translating the item

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            ItemRenderHelper.renderItem(entity, item, 0);
        } else {
            GL11.glPushMatrix();

            GL11.glScalef(1F, 1F, 1.7f);
            GL11.glTranslatef(0F, -0.1875F, 0F);

            ItemRenderHelper.renderItem(entity, item, 0);

            GL11.glPopMatrix();
        }
        GL11.glPushMatrix();
    }
}
