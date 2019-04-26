package terramisc.render;

import java.lang.reflect.Method;

import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Render.Blocks.RenderCrop;
import com.bioxx.tfc.TileEntities.TECrop;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import terramisc.core.TFCMOptions;

public class CropRender extends RenderCrop implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks)
	{
		IBlockAccess blockaccess = renderblocks.blockAccess;
		TECrop cropTE = (TECrop)blockaccess.getTileEntity(x, y, z);

		if(cropTE != null)
			CropManager.getInstance().getCropFromId(cropTE.cropId);
		else
			return false;

		Tessellator var9 = Tessellator.instance;
		var9.setBrightness(block.getMixedBrightnessForBlock(blockaccess, x, y, z));

		if(cropTE.cropId == TFCMOptions.pumpkinID)
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
		}
		else if(cropTE.cropId == TFCMOptions.cayenneID)
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
		}
		else if(cropTE.cropId == TFCMOptions.coffeeID)
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
		}
		else
		{
			renderblocks.renderBlockCrops(block, x, y, z);
		}
		
		return true;
	}
	
	public static void drawCrossedSquares(Block block, double a, double b, double c, RenderBlocks rb, double d, double e)
	{
		try
		{
			//invoke the drawCrossedSquares method from RenderCrop with reflection
			Method draw = RenderCrop.class.getDeclaredMethod("drawCrossedSquares", Block.class,double.class,double.class,double.class,RenderBlocks.class, double.class, double.class);
			draw.setAccessible(true);
			draw.invoke(RenderCrop.class, block,a,b,c,rb,d,e);
		}
		catch(Exception ex)
		{
		}	
	}
	
	public static void renderBlockCropsImpl(Block block, double i, double j, double k, RenderBlocks rb, double d, double e)
	{
		try
		{
			//invoke the renderBLockCropsImpl method from RenderCrop with reflection
			Method draw = RenderCrop.class.getDeclaredMethod("renderBlockCropsImpl", Block.class,double.class,double.class,double.class,RenderBlocks.class, double.class, double.class);
			draw.setAccessible(true);
			draw.invoke(RenderCrop.class, block,i,j,k,rb,d,e);
		}
		catch(Exception ex)
		{
		}
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) 
	{
		return false;
	}

	@Override
	public int getRenderId() 
	{
		return 0;
	}
}
