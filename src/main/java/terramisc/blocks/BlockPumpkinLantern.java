package terramisc.blocks;

import java.util.Random;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.BlockTerraContainer;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Items.Tools.ItemFirestarter;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMOptions;
import terramisc.tileentities.TEPumpkinLantern;

public class BlockPumpkinLantern extends BlockTerraContainer
{
	public int LanternBurnTime = (int) TFCMOptions.TallowCandleBurnTime;

	private boolean isCarved;
	private boolean isLit;
	
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon faceIcon;
	
	public BlockPumpkinLantern(boolean carved, boolean lit)
	{
		super(Material.gourd);
		
		this.isCarved = carved;
		this.isLit = lit;
		
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		this.setTickRandomly(true);
		setLightLevel(0.8F);
	}

	/**
	 * Returns the items to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return new ItemStack(TFCMBlocks.blockPumpkinLanternOff).getItem();
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 1;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEPumpkinLantern();
	}
	
	/**
	 * Lights Jack-O'-Lantern when player right-clicks with a torch, firestarter, or flint&steel in hand.
	 * Snuffs Jack-O'-Lantern if right=clicked by a player with an empty hand.
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			ItemStack is = player.inventory.getCurrentItem();
			Item item = is != null ? is.getItem() : null;
			
			if(item == Item.getItemFromBlock(TFCBlocks.torch))
			{
				world.setBlock(x, y, z, TFCMBlocks.blockPumpkinLantern, meta, 3);
				
				return true;
			}
			else if(item instanceof ItemFirestarter || item instanceof ItemFlintAndSteel)
			{
				ItemStack equippedItem = player.getCurrentEquippedItem();
				
				if(item instanceof ItemFlintAndSteel)
				{
					Random rand = new Random();
					world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
				}
				
				equippedItem.damageItem(1, player);
				
				world.setBlock(x, y, z, TFCMBlocks.blockPumpkinLantern, meta, 3);
				
				return true;
			}
			else if(item == null)
			{
				if(world.getTileEntity(x, y, z) != null)
					world.getTileEntity(x, y, z).invalidate();
				
				world.setBlock(x, y, z, TFCMBlocks.blockPumpkinLanternOff, meta, 3);
			}
		}
		else
		{
		    if(TFCOptions.enableDebugMode)
		    {
		        int metadata = world.getBlockMetadata(x, y, z);
				TerraFirmaCraft.LOG.info("Meta = " + (new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		    }
		}
		
		return true;
	}
	
	/**
	 * Ticks the block if it's been scheduled
	 * If Jack-O'-Lantern has reached max burn time it will be replaced by the off-state lantern.
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		super.updateTick(world, x, y, z, rand);
		int meta = world.getBlockMetadata(x, y, z);

		TEPumpkinLantern te = (TEPumpkinLantern)world.getTileEntity(x, y, z);
		
		if(!world.isRemote)
		{
			if(this.LanternBurnTime != 0 && world.getTileEntity(x, y, z) instanceof TEPumpkinLantern)
			{
				if(TFC_Time.getTotalHours() > (te.hourPlaced + this.LanternBurnTime))
				{
					if(world.getTileEntity(x, y, z) != null)
						world.getTileEntity(x, y, z).invalidate();
					
					world.setBlock(x, y, z, TFCMBlocks.blockPumpkinLanternOff, meta, 3);
				}
			}
		}
	}
	
	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		((TEPumpkinLantern) world.getTileEntity(x, y, z)).create();
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
	
	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 1 ? this.topIcon : side == 0 ? this.topIcon : // Top or Bottom Side
        	meta == 2 && side == 2 ? this.faceIcon : meta == 3 && side == 5 ? this.faceIcon : // Face Side
        		meta == 0 && side == 3 ? this.faceIcon : meta == 1 && side == 4 ? this.faceIcon : // Face Side
        			this.blockIcon; // Blank Side
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.topIcon = iconRegister.registerIcon("pumpkin_top");
		this.blockIcon = iconRegister.registerIcon("pumpkin_side");
		this.faceIcon = this.isCarved ? (this.isLit ? iconRegister.registerIcon("pumpkin_face_on") : iconRegister.registerIcon("pumpkin_face_off")) : this.blockIcon; // Only have a face when lit.
	}
}
