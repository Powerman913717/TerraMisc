package terramisc.render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import terramisc.models.BlockSoupKettleModel;
import terramisc.tileentities.TESoupKettle;

public class TESRSoupKettle extends TileEntitySpecialRenderer
{
	protected BlockSoupKettleModel model;
	
	public TESRSoupKettle()
	{
		this.model = new BlockSoupKettleModel();
	}
	
	public void renderAt(TESoupKettle tileEnt, double x, double y, double z, float f) 
	{
		TFC_Core.bindTexture(new ResourceLocation("tfcm:textures/models/SoupKettle_Empty.png"));
		
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f, (float) z + 0.5f);
			GL11.glRotatef(180, 0, 0, 1);	
			GL11.glPushMatrix();
				this.model.renderModel(0.0625f);
			GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEnt, double x, double y, double z, float f) 
	{
		this.renderAt((TESoupKettle)tileEnt, x, y, z, f);
	}
}
