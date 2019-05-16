package terramisc.blocks;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockCrop;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import terramisc.api.crops.CropIndexTFCM;
import terramisc.api.crops.CropManagerTFCM;
import terramisc.core.TFCMBlocks;
import terramisc.tileentities.TECropTFCM;

public class BlockCropTFCM extends BlockCrop
{
	private IIcon[] iconsPumpkin = new IIcon[7];
	private IIcon[] iconsCayenne = new IIcon[7];
	private IIcon[] iconsCoffee = new IIcon[7];
	private IIcon[] iconsWatermelon = new IIcon[7];
	private IIcon[] iconsSweetPotato = new IIcon[7];
	private IIcon[] iconsHops = new IIcon[8];
	
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
			
		}
		for(int i = 1; i < 8; i++)
		{
			iconsPumpkin[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Pumpkin (" + i + ")");
			iconsCayenne[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Pepper Cayenne (" + i + ")");
			iconsCoffee[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Coffee (" + i + ")");
			iconsWatermelon[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Watermelon (" + i + ")");
			iconsSweetPotato[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Sweet Potato (" + i + ")");
		}
		for(int i = 1; i < 9; i++)
		{
			iconsHops[i - 1] = register.registerIcon(Reference.MOD_ID + ":" + "plants/crops/Hops (" + i + ")");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int meta)
	{
		TECropTFCM te = (TECropTFCM) access.getTileEntity(x, y, z);
		CropIndexTFCM crop = CropManagerTFCM.getInstance().getCropFromName(te.cropId);

		int stage = (int) Math.floor(te.growth);
		if(stage > crop.numGrowthStages)
			stage = crop.numGrowthStages;
		
		switch(te.cropId)
		{
		case "pumpkin":
			return iconsPumpkin[stage];
		case "cayenne":
			return iconsCayenne[stage];
		case "coffee":
			return iconsCoffee[stage];
		case "watermelon":
			return iconsWatermelon[stage];
		case "sweet potato":
			return iconsSweetPotato[stage];
		case "hops":
			return iconsHops[stage];
		default:
			return iconsPumpkin[1];
		}
	}
	
	@Override
	public int getRenderType()
	{
		return TFCMBlocks.cropRenderId;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TECropTFCM();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		TECropTFCM te = (TECropTFCM) world.getTileEntity(x, y, z);
		CropIndexTFCM crop = CropManagerTFCM.getInstance().getCropFromName(te.cropId);

		if(TFCOptions.enableDebugMode)
		{
			TerraFirmaCraft.LOG.info("Crop ID: " + te.cropId);
			TerraFirmaCraft.LOG.info("Growth: " + te.growth);
			TerraFirmaCraft.LOG.info("Est Growth: " + te.getEstimatedGrowth(crop));
		}

		return false;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int l, EntityPlayer player)
	{
		TECropTFCM te = (TECropTFCM) world.getTileEntity(i, j, k);
		if (!world.isRemote)
		{
			ItemStack itemstack = player.inventory.getCurrentItem();
			int[] equipIDs = OreDictionary.getOreIDs(itemstack);

			for (int id : equipIDs)
			{
				String name = OreDictionary.getOreName(id);
				if (name.startsWith("itemScythe"))
				{
					for (int x = -1; x < 2; x++)
					{
						for (int z = -1; z < 2; z++)
						{
							if (world.getBlock(i + x, j, k + z) == this && player.inventory.getStackInSlot(player.inventory.currentItem) != null)
							{
								player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
								TECropTFCM teX = (TECropTFCM) world.getTileEntity(i + x, j, k + z);
								teX.onHarvest(world, player, true);

								world.setBlockToAir(i + x, j, k + z);

								itemstack.damageItem(1, player);
								if (itemstack.stackSize == 0)
									player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
							}
						}
					}

					return;
				}
			}
		}

		// Only executes if scythe wasn't found
		te.onHarvest(world, player, true);
	}
}
