package terramisc.handlers;

import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Util.Helper;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PlayerInteractHandlerTFCM 
{
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.entityPlayer.worldObj.isRemote)
			return;

		ItemStack itemInHand = event.entityPlayer.getCurrentEquippedItem();

		boolean validAction = event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR;

		if(validAction && event.getResult() != Result.DENY && itemInHand != null)
		{	
			if(createPile(event.entityPlayer, itemInHand))
			{
				event.setCanceled(true);
				
				itemInHand.stackSize = itemInHand.stackSize-15;
			}
		}
	}
	
	private boolean createPile(EntityPlayer player, ItemStack itemstack)
	{
		World world = player.worldObj;
		MovingObjectPosition mop = Helper.getMovingObjectPositionFromPlayer(world, player, true);
		TELogPile te = null;
		
		if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
		{
			if(world.getBlock(mop.blockX, mop.blockY, mop.blockZ) == TFCBlocks.logPile)
			{
				Item item = itemstack.getItem();
				te = (TELogPile)world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
				
				if(te != null)
				{
					if(te.storage[0] != null && te.contentsMatch(0, itemstack) && te.storage[0].stackSize == 1)
					{
						if(te.storage[1] == null && te.storage[2] == null && te.storage[3] == null)
						{
							if(itemstack.stackSize == 15)
							{
								if(player.isSneaking())
									return false;
								
								te.storage[0] = new ItemStack(item, 4, itemstack.getItemDamage());
								te.storage[1] = new ItemStack(item, 4, itemstack.getItemDamage());
								te.storage[2] = new ItemStack(item, 4, itemstack.getItemDamage());
								te.storage[3] = new ItemStack(item, 4, itemstack.getItemDamage());
								
								playSound(world, mop.blockX, mop.blockY, mop.blockZ);
								
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}

	private void playSound(World world, int x, int y, int z)
	{
		world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, TFCBlocks.logNatural.stepSound.func_150496_b(), (TFCBlocks.logNatural.stepSound.getVolume() + 1.0F) / 2.0F, TFCBlocks.logNatural.stepSound.getPitch() * 0.8F);
	}
}
