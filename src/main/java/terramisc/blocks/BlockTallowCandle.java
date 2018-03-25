package terramisc.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMOptions;
import terramisc.items.ItemTallowDye;
import terramisc.tileentities.TETallowCandle;

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Items.Tools.ItemFirestarter;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTallowCandle extends BlockTerraContainer
{
	public int CandleBurnTime = (int) TFCMOptions.TallowCandleBurnTime;
	
	public BlockTallowCandle()
	{
		super(Material.circuits);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		this.setTickRandomly(true);
		this.setBlockBounds(0.375f, 0.0f, 0.375f, 0.625f, 0.375f, 0.625f);
		setLightLevel(0.8F);
	}
	
	//Disables ablity to produce light if candle has burnt out.
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= 8)
		{
			return 0;
		}
		return getLightValue();
	}
	
	//Called when the block is right clicked.
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			ItemStack is = player.inventory.getCurrentItem();
			Item item = is != null ? is.getItem() : null;
			TETallowCandle te = (TETallowCandle)world.getTileEntity(x, y, z);
			
			//Makes additonal torches.
			if (meta < 8 && item == TFCItems.stick)
			{	
				player.inventory.consumeInventoryItem(TFCItems.stick);
				TFC_Core.giveItemToPlayer(new ItemStack(TFCBlocks.torch), player);
			}
			// Refreshing candle timer, or re-lighting burned out candles that haven't converted yet.
			else if (item == Item.getItemFromBlock(TFCBlocks.torch))
			{
				te.hourPlaced = (int)TFC_Time.getTotalHours();
				if (meta >= 8)
				{
					world.setBlockMetadataWithNotify(x, y, z, meta - 8, 3);
				}
			}
			// Relights candles but uses firestarters or flint&steels instead of torches.
			else if (item instanceof ItemFirestarter || item instanceof ItemFlintAndSteel)
			{
				ItemStack equippedItem = player.getCurrentEquippedItem();
				
				if ((TETallowCandle) world.getTileEntity(x, y, z) != null)
				{
					if (item instanceof ItemFlintAndSteel)
					{
						Random rand = new Random();
						world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
					}
					
					equippedItem.damageItem(1, player);
					world.setBlockMetadataWithNotify(x, y, z, meta - 8, 3);
					return true;
				}
			}
			//Add dye to candle inventory
			else if(te.color == 15 && item instanceof ItemTallowDye)
			{
				int d = item.getDamage(is);
				if(d != 15)
				{
					te.setCandleTexture(d);
					player.inventory.consumeInventoryItem(item);
					world.scheduleBlockUpdate(x, y, z, this, 1);
				}
			}
			// Extinguish the candle.
			else if(item == null)
			{
				int c1 = te.color;
				
				world.setBlock(x, y, z, TFCMBlocks.blockTallowCandleOff, meta, 3);
				if(world.getTileEntity(x, y, z) instanceof TETallowCandle)
				{
					TETallowCandle te1 = (TETallowCandle) world.getTileEntity(x, y, z);
					te1.setCandleTexture(c1);
				}
			}
		}
		else
		{
			if(TFCOptions.enableDebugMode)
			{
				int metadata = world.getBlockMetadata(x, y, z);
			    System.out.println("Meta = "+(new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
			}
		}
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TETallowCandle();
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	//Placing
	private boolean canSupportCandle(World world, int x, int y, int z)
	{
		if (World.doesBlockHaveSolidTopSurface(world, x, y, z))
		{
			return true;
		}
		else
		{
			Block block = world.getBlock(x, y, z);
			return block.canPlaceTorchOnTop(world, x, y, z);
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return canSupportCandle(world, x, y - 1, z);
	}
	
	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		super.updateTick(world, x, y, z, rand);
		int meta = world.getBlockMetadata(x, y, z);

		TETallowCandle te = (TETallowCandle)world.getTileEntity(x, y, z);
		int c1 = te.color;
		
		if (meta == 0)
		{
			this.onBlockAdded(world, x, y, z);
		}
		if (!world.isRemote)
		{
			if (this.CandleBurnTime != 0 && world.getTileEntity(x, y, z) instanceof TETallowCandle)
			{
				if (TFC_Time.getTotalHours() > te.hourPlaced + this.CandleBurnTime ||
					TFC_Core.isExposedToRain(world, x, y, z))
				{
					world.setBlock(x, y, z, TFCMBlocks.blockTallowCandleOff, meta, 3);
					if(world.getTileEntity(x, y, z) instanceof TETallowCandle)
					{
						TETallowCandle te1 = (TETallowCandle) world.getTileEntity(x, y, z);
						te1.setCandleTexture(c1);
					}
				}
			}
			else if (meta >= 8)
			{
				world.setBlock(x, y, z, TFCMBlocks.blockTallowCandleOff, meta - 8, 3);
				if(world.getTileEntity(x, y, z) instanceof TETallowCandle)
				{
					TETallowCandle te1 = (TETallowCandle) world.getTileEntity(x, y, z);
					te1.setCandleTexture(c1);
				}
			}
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if(!World.doesBlockHaveSolidTopSurface(world, x, y-1, z))
			TFC_Core.setBlockToAirWithDrops(world, x, y, z);
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
	
	//Particles
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= 8)
			return;


		double centerX = x + 0.5F;
		double centerY = y + 0.5F;
		double centerZ = z + 0.43F;
		double d3 = 0.22;
		double d4 = 0.27;

		if ((meta & 7) == 1)
		{
			world.spawnParticle("smoke", centerX - d4, centerY + d3, centerZ, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", centerX - d4, centerY + d3, centerZ, 0.0D, 0.0D, 0.0D);
		}
		else if ((meta & 7) == 2)
		{
			world.spawnParticle("smoke", centerX + d4, centerY + d3, centerZ, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", centerX + d4, centerY + d3, centerZ, 0.0D, 0.0D, 0.0D);
		}
		else if ((meta & 7) == 3)
		{
			world.spawnParticle("smoke", centerX, centerY + d3, centerZ - d4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", centerX, centerY + d3, centerZ - d4, 0.0D, 0.0D, 0.0D);
		}
		else if ((meta & 7) == 4)
		{
			world.spawnParticle("smoke", centerX, centerY + d3, centerZ + d4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", centerX, centerY + d3, centerZ + d4, 0.0D, 0.0D, 0.0D);
		}
		else
		{
			world.spawnParticle("smoke", centerX, centerY, centerZ, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", centerX, centerY, centerZ, 0.0D, 0.0D, 0.0D);
		}
	}
}
