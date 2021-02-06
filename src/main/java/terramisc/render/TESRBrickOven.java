package terramisc.render;

import com.dunk.tfc.Core.TFC_Core;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import terramisc.models.BlockBrickOvenModel;
import terramisc.tileentities.TEBrickOven;

public class TESRBrickOven extends TileEntitySpecialRenderer {
    protected BlockBrickOvenModel model;

    public TESRBrickOven() {
        this.model = new BlockBrickOvenModel();
    }

    public void renderAt(TEBrickOven tileEnt, double x, double y, double z, float f) {
        TFC_Core.bindTexture(tileEnt.getOvenTexture());

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f, (float) z + 0.5f);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glPushMatrix();
        this.model.renderModel(0.0625f);
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEnt, double x, double y, double z, float f) {
        this.renderAt((TEBrickOven) tileEnt, x, y, z, f);
    }
}
