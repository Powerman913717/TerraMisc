package terramisc.render;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemCrossbowRender implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
                                         ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        EntityLivingBase entity = (EntityLivingBase) data[1];

        GL11.glPopMatrix();                                         // prevents Forge from pre-translating the item

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            float i = 0.0F;
            float i2 = (float) Math.sin(i / 20.0D);

            if (item.stackTagCompound != null) {
                if (item.getTagCompound().getBoolean("load") == true) {
                    GL11.glPopMatrix();

                    GL11.glTranslatef(0.0F, -(Math.abs(i2) / 2.0F), 0.0F);
                    GL11.glRotatef(80.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-62.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-39.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-0.5F, -1.4F, -0.7F);

                    ItemRenderHelper.renderItem(entity, item, 0);

                    GL11.glPushMatrix();
                } else {
                    GL11.glPopMatrix();

                    GL11.glRotatef(80.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-62.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-39.0F, 0.0F, 1.0F, 0.0F);

                    ItemRenderHelper.renderItem(entity, item, 0);

                    GL11.glPushMatrix();
                }
            } else {
                GL11.glPopMatrix();

                GL11.glRotatef(80.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-62.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-39.0F, 0.0F, 1.0F, 0.0F);

                ItemRenderHelper.renderItem(entity, item, 0);

                GL11.glPushMatrix();
            }
        } else {
            if (item.stackTagCompound != null) {
                if (item.getTagCompound().getBoolean("load") == true) {
                    GL11.glPushMatrix();

                    GL11.glTranslatef(0.7F, 0.225F, 0.6125F);
                    GL11.glRotatef(-40.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-110.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glTranslatef(-0.6F, -0.8F, 0.4F);
                    GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(1.4F, 1.4F, 1.8F);

                    ItemRenderHelper.renderItem(entity, item, 0);

                    GL11.glPopMatrix();
                } else {
                    GL11.glPushMatrix();

                    GL11.glTranslatef(0.7F, 0.225F, 0.6125F);
                    GL11.glRotatef(-40.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-110.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glTranslatef(-0.6F, -0.8F, 0.4F);
                    GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(1.4F, 1.4F, 1.8F);

                    ItemRenderHelper.renderItem(entity, item, 0);

                    GL11.glPopMatrix();
                }
            }
        }
        GL11.glPushMatrix();
    }

}
