package terramisc.blocks;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockCrop;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.TileEntities.TECrop;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMOptions;

public class BlockCropTFCM extends BlockCrop
{
	private IIcon[] iconsPumpkin = new IIcon[7];
	private IIcon[] iconsCayenne = new IIcon[6];
	private IIcon[] iconsCoffee = new IIcon[6];
	
	public BlockCropTFCM()
	{
		super();
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		for(int i = 1; i < 6; i++)
		{

		}
		for(int i = 1; i < 7; i++)
		{
			iconsCayenne[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Cayenne Pepper (" + i + ")");
			iconsCoffee[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Coffee (" + i + ")");
		}
		for(int i = 1; i < 8; i++)
		{
			iconsPumpkin[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Pumpkin (" + i + ")");
		}
		for(int i = 1; i < 9; i++)
		{

		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int meta)
	{
		TECrop te = (TECrop) access.getTileEntity(x, y, z);
		CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);

		int stage = (int) Math.floor(te.growth);
		if(stage > crop.numGrowthStages)
			stage = crop.numGrowthStages;
		
		if(te.cropId == TFCMOptions.pumpkinID)
			return iconsPumpkin[stage];
		
		if(te.cropId == TFCMOptions.cayenneID)
			return iconsCayenne[stage];
		
		if(te.cropId == TFCMOptions.coffeeID)
			return iconsCoffee[stage];
		
		else
			return null;
	}
	
	@Override
	public int getRenderType()
	{
		return TFCMBlocks.cropRenderId;
	}
}
