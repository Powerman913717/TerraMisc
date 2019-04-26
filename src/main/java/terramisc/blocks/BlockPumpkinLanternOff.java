package terramisc.blocks;

import java.util.Random;

import com.bioxx.tfc.Items.Tools.ItemFirestarter;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import terramisc.core.TFCMBlocks;

public class BlockPumpkinLanternOff extends BlockPumpkinTFCM
{
	public BlockPumpkinLanternOff(boolean carved, boolean lit)
	{
		super(carved, lit);
	}

	@Override
	public int quantityDropped(Random rand)
	{
		return 1;
	}
	
	/**
	 * Lights Jack-O'-Lantern when player right-clicks with a torch, firestarter, or flint&steel in hand.
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
		}
		
		return true;
	}
}
