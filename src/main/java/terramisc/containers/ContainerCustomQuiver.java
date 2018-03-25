package terramisc.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;
import terramisc.containerslots.SlotCustomQuiver;

import com.bioxx.tfc.Containers.ContainerQuiver;
import com.bioxx.tfc.Containers.Slots.SlotForShowOnly;

public class ContainerCustomQuiver extends ContainerQuiver
{

	public ContainerCustomQuiver(InventoryPlayer playerinv, World world, int x, int y, int z) 
	{
		super(playerinv, world, x, y, z);
	}
	
	@Override
	protected void layoutContainer(IInventory playerInventory, int xSize, int ySize)
	{
		this.addSlotToContainer(new SlotCustomQuiver(containerInv, 0, 53, 8));
		this.addSlotToContainer(new SlotCustomQuiver(containerInv, 1, 71, 8));
		this.addSlotToContainer(new SlotCustomQuiver(containerInv, 2, 89, 8));
		this.addSlotToContainer(new SlotCustomQuiver(containerInv, 3, 107, 8));
		this.addSlotToContainer(new SlotCustomQuiver(containerInv, 4, 53, 26));
		this.addSlotToContainer(new SlotCustomQuiver(containerInv, 5, 71, 26));
		this.addSlotToContainer(new SlotCustomQuiver(containerInv, 6, 89, 26));
		this.addSlotToContainer(new SlotCustomQuiver(containerInv, 7, 107, 26));

		int row;
		int col;

		for (row = 0; row < 9; ++row)
		{
			if (row == bagsSlotNum)
				this.addSlotToContainer(new SlotForShowOnly(playerInventory, row, 8 + row * 18, 112));
			else
				this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, 112));
		}

		for (row = 0; row < 3; ++row)
		{
			for (col = 0; col < 9; ++col)
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 54 + row * 18));
		}
	}
}
