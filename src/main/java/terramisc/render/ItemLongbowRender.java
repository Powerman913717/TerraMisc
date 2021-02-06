package terramisc.render;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemLongbowRender implements IItemRenderer {

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

            float scale = 3F - (1F / 3F);                           // contra-translate the item from it's standard translation
            // also apply some more scale or else the bow is tiny
            GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);

            scale = 0.700F;                                        // render the item as 'real' bow

            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(scale, -scale, scale);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);

            ItemRenderHelper.renderItem(entity, item, 0);

            GL11.glPopMatrix();
        }
        GL11.glPushMatrix();
    }

}
