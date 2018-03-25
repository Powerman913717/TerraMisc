package terramisc.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class TESRTallowCandleItem implements IItemRenderer 
{

	TileEntitySpecialRenderer render;
	private TileEntity entity;
	
	public TESRTallowCandleItem(TileEntitySpecialRenderer render, TileEntity entity)
	{
		this.render = render;
		this.entity = entity;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) 
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) 
	{
		if(type == IItemRenderer.ItemRenderType.ENTITY)
			GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
		this.render.renderTileEntityAt(this.entity, 0.0d, 0.0d, 0.0d, 0.0f);
	}

}
