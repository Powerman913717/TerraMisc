package terramisc.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import terramisc.core.TFCMBlocks;
import terramisc.tileentities.TETallowCandle;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Items.Tools.ItemFirestarter;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTallowCandleOff extends BlockTallowCandle
{
	public BlockTallowCandleOff()
	{
		super();
		this.setCreativeTab(null);
		this.setTickRandomly(false);
		this.setBlockBounds(0.375f, 0.0f, 0.375f, 0.625f, 0.375f, 0.625f);
		setLightLevel(0.0F);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TETallowCandle tile = (TETallowCandle) world.getTileEntity(x, y, z);
			int color = tile.color;
			
			if (player.inventory.getCurrentItem() != null &&
				player.inventory.getCurrentItem().getItem() == Item.getItemFromBlock(TFCBlocks.torch))
			{
				int meta = world.getBlockMetadata(x, y, z);
				world.setBlock(x, y, z, TFCMBlocks.blockTallowCandle, meta, 3);
				if (world.getTileEntity(x, y, z) instanceof TETallowCandle)
				{
					TETallowCandle te = (TETallowCandle) world.getTileEntity(x, y, z);
					te.hourPlaced = (int) TFC_Time.getTotalHours();
					te.setCandleTexture(color);
				}
			}
			// Relights candles but uses firestarters or flint&steels instead of torches.
			else if (player.inventory.getCurrentItem() != null && (player.inventory.getCurrentItem().getItem() instanceof ItemFirestarter || player.inventory.getCurrentItem().getItem() instanceof ItemFlintAndSteel))
			{
				ItemStack equippedItem = player.getCurrentEquippedItem();
							
				if ((TETallowCandle) world.getTileEntity(x, y, z) != null)
				{
					if (player.inventory.getCurrentItem().getItem() instanceof ItemFlintAndSteel)
					{
						Random rand = new Random();
						world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
					}
								
					equippedItem.damageItem(1, player);
					int meta = world.getBlockMetadata(x, y, z);
					world.setBlock(x, y, z, TFCMBlocks.blockTallowCandle, meta, 3);
					if (world.getTileEntity(x, y, z) instanceof TETallowCandle)
					{
						TETallowCandle te = (TETallowCandle) world.getTileEntity(x, y, z);
						te.hourPlaced = (int) TFC_Time.getTotalHours();
						te.setCandleTexture(color);
					}
				}
			}
		}
		
		return true;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		new ArrayList<ItemStack>().add(new ItemStack(TFCMBlocks.blockTallowCandle));
		
		return new ArrayList<ItemStack>();
	}

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		// Burned out torches have no particles
	}
}
