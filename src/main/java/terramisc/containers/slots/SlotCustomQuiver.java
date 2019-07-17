package terramisc.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import terramisc.items.tools.ItemCustomArrow;
import terramisc.items.tools.ItemCustomBolt;

import com.dunk.tfc.Items.ItemArrow;
import com.dunk.tfc.Items.Tools.ItemJavelin;

public class SlotCustomQuiver extends Slot
{
	public SlotCustomQuiver(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() instanceof ItemJavelin || itemstack.getItem() instanceof ItemArrow
				|| itemstack.getItem() instanceof ItemCustomArrow || itemstack.getItem() instanceof ItemCustomBolt;
	}
}
