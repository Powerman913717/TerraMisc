package terramisc.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.bioxx.tfc.Blocks.BlockTerraContainer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import terramisc.core.TFCMBlocks;
import terramisc.tileentities.TEFoodBlock;

public class BlockFoodPumpkin extends BlockTerraContainer
{
	private boolean isCarved;
	private boolean isLit;
	
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon faceIcon;

	public BlockFoodPumpkin(boolean carved, boolean lit) 
	{
		super(Material.gourd);
		
		this.isCarved = carved;
		this.isLit = lit;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		ItemStack is = player.getHeldItem();
		
		ArrayList<ItemStack> knives = OreDictionary.getOres("itemKnife");
		int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
		
		for(int j = 0; j < knives.size(); j++)
		{
			if(is.getItem() == knives.get(j).getItem())
			{
				if(world.getTileEntity(x, y, z) != null)
					world.getTileEntity(x, y, z).invalidate();
				
				world.setBlock(x, y, z, TFCMBlocks.blockPumpkinCarved, l, 3);
				
				is.setItemDamage(is.getItemDamage()-1);
				
				return true;
			}
		}
		
		
		return false;
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}
	
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEFoodBlock();
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion ex)
	{
		eject(par1World,par2,par3,par4);
	}

	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
	{
		eject(par1World,par2,par3,par4);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
	{
		eject(par1World,par2,par3,par4);
	}
	
	public void eject(World par1World, int par2, int par3, int par4)
	{
		if (par1World.getTileEntity(par2, par3, par4) instanceof TEFoodBlock)
		{
			TEFoodBlock te = (TEFoodBlock)par1World.getTileEntity(par2, par3, par4);
			te.ejectContents();
			par1World.removeTileEntity(par2, par3, par4);
		}
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
