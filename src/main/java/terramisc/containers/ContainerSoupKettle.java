package terramisc.containers;

import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Containers.Slots.SlotBlocked;
import com.bioxx.tfc.Containers.Slots.SlotFoodOnly;
import com.bioxx.tfc.Containers.Slots.SlotSize;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import terramisc.containers.slots.SlotMealFood;
import terramisc.containers.slots.SlotMealFoodGroup;
import terramisc.tileentities.TESoupKettle;

public class ContainerSoupKettle extends ContainerTFC
{
	//private World world;
	//private int posX;
	//private int posY;
	//private int posZ;
	private TESoupKettle te;
	private EntityPlayer player;
	private int guiTab;

	public ContainerSoupKettle(InventoryPlayer playerinv, TESoupKettle pile, World world, int x, int y, int z, int tab)
	{
		this.player = playerinv.player;
		this.te = pile;
		//this.world = world;
		//this.posX = x;
		//this.posY = y;
		//this.posZ = z;
		guiTab = tab;
		pile.openInventory();
		layoutContainer(playerinv, pile, 0, 0);
		pile.lastTab = tab;
		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90, false, true);
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		te.closeInventory();
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize)
	{
		if(guiTab == 0)
		{
			this.addSlotToContainer(new SlotBlocked(chestInventory, 1, 26, 8));
			this.addSlotToContainer(new SlotMealFoodGroup(chestInventory, 0, 44, 8, EnumFoodGroup.Vegetable, EnumFoodGroup.Protein).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 2, 62, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 3, 80, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 4, 98, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 5, 116, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
		}
		else if(guiTab == 1)
		{
			this.addSlotToContainer(new SlotBlocked(chestInventory, 0, 26, 8));
			this.addSlotToContainer(new SlotMealFood(chestInventory, 1, 44, 8).addItemInclusion(TFCItems.wheatGround, TFCItems.barleyGround, TFCItems.oatGround,
					TFCItems.ryeGround, TFCItems.riceGround, TFCItems.cornmealGround).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 2, 62, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 3, 80, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 4, 98, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 5, 116, 8).addItemException(TFCItems.woodenBucketMilk).setSize(EnumSize.HUGE));
		}
		this.addSlotToContainer(new SlotBlocked(chestInventory, 6, 53, 30));
		this.addSlotToContainer(new SlotBlocked(chestInventory, 7, 53, 58));
		this.addSlotToContainer(new SlotSize(chestInventory, 8, 134, 8).setSize(EnumSize.SMALL));
		this.addSlotToContainer(new SlotSize(chestInventory, 9, 134, 26).setSize(EnumSize.SMALL));
		this.addSlotToContainer(new SlotSize(chestInventory, 10, 134, 44).setSize(EnumSize.SMALL));
		this.addSlotToContainer(new SlotSize(chestInventory, 11, 134, 62).setSize(EnumSize.SMALL));
	}

	public EntityPlayer getPlayer()
	{
		return player;
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// From food prep to inventory
			if (slotNum < 10)
			{
				if (!this.mergeItemStack(slotStack, 10, inventorySlots.size(), true))
					return null;
			}
			// Slot exception lists already handle what should go where
			else
			{
				if (!this.mergeItemStack(slotStack, 0, 10, false))
					return null;
			}

			if (slotStack.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
		}

		return origStack;
	}
}
