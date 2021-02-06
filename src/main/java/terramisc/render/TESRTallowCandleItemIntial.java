package terramisc.render;

import com.dunk.tfc.Core.TFC_Core;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import terramisc.tileentities.TETallowCandle;

public class TESRTallowCandleItemIntial extends TESRTallowCandle {
    public ResourceLocation candleBase = new ResourceLocation("tfcm:textures/models/TallowCandle_Tallow_Off.png");

    @Override
    public void renderCandleAt(TETallowCandle tileEnt, double x, double y, double z, float f) {
        TFC_Core.bindTexture(candleBase);

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f, (float) z + 0.5f);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glPushMatrix();
        this.model.renderModel(0.0625f);
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }
}
