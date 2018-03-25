package terramisc.containerslots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTallowCandle extends Slot
{
	public SlotTallowCandle(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() != null;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}
