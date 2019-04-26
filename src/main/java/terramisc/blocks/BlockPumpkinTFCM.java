package terramisc.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import terramisc.core.TFCMBlocks;

public class BlockPumpkinTFCM extends BlockPumpkin
{
	private boolean isCarved;
	private boolean isLit;
	
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon faceIcon;

	public BlockPumpkinTFCM(boolean carved, boolean lit) 
	{
		super(lit);
		
		this.isCarved = carved;
		this.isLit = lit;
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			ItemStack is = player.inventory.getCurrentItem();
			Item item = is != null ? is.getItem() : null;
			
			if(item == new ItemStack(TFCMBlocks.blockTallowCandleOff).getItem())
			{
				world.setBlock(x, y, z, TFCMBlocks.blockPumpkinLanternOff, meta, 3);
				is.stackSize = is.stackSize-1;
				
				return true;
			}
		}
		
		return true;
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
