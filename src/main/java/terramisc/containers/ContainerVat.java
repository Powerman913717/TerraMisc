package terramisc.containers;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Containers.Slots.SlotChest;
import com.bioxx.tfc.Containers.Slots.SlotFirepit;
import com.bioxx.tfc.Containers.Slots.SlotFirepitFuel;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumSize;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import terramisc.tileentities.TEVat;

public class ContainerVat extends ContainerTFC
{
	private TEVat vat;
	@SuppressWarnings("unused")
	private float liquidLevel;
	@SuppressWarnings("unused")
	private int liquidID;
	private float fireTemp;
	private int cookTimer;
	
	public ContainerVat(InventoryPlayer inventoryplayer, TEVat teVat, World world, int x, int y, int z)
	{
		vat = teVat;
		fireTemp = -1111;
		liquidLevel = 0;
		liquidID = -1;

		//Crafting slot ... functions as both item input and output for crafting. 
		//TODO Make slot only accept valid recipe ingrediants.
		addSlotToContainer(new SlotChest(vat, 4, 80, 12).setSize(EnumSize.LARGE).addItemException(getExceptions()));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, teVat, 5, 80, 37));
		
		//Fuel slots
		addSlotToContainer(new SlotFirepitFuel(inventoryplayer.player, teVat, 0, 8, 8));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, teVat, 1, 8, 26));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, teVat, 2, 8, 44));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, teVat, 3, 8, 62));

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);
	}

	public static List<Item> getExceptions()
	{
		List<Item> exceptions = new ArrayList<Item>();
		exceptions.add(Item.getItemFromBlock(TFCBlocks.barrel));
		exceptions.add(Item.getItemFromBlock(TFCBlocks.vessel));
		return exceptions;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot) inventorySlots.get(slotNum);
		Slot slotinput = (Slot) this.inventorySlots.get(4);
		Slot slotfuel = (Slot) this.inventorySlots.get(0);

		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			//From firepit to inventory
			if(slotNum < 6)
			{
				if (!this.mergeItemStack(slotStack, 6, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				HeatRegistry manager = HeatRegistry.getInstance();

				//Fuel to the fuel input slot
				if(slotStack.getItem() == TFCItems.logs || slotStack.getItem() == Item.getItemFromBlock(TFCBlocks.peat))
				{
					if(slotfuel.getHasStack())
						return null;
					
					ItemStack stack = slotStack.copy();
					stack.stackSize = 1;
					slotfuel.putStack(stack);
					slotStack.stackSize--;
				}
				//No ores, but anything else with a heat index to the input slot
				else if (!(slotStack.getItem() instanceof ItemOre) && manager.findMatchingIndex(slotStack) != null)
				{
					if(slotinput.getHasStack())
						return null;
					ItemStack stack = slotStack.copy();
					stack.stackSize = 1;
					slotinput.putStack(stack);
					slotStack.stackSize--;
				}
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

	@SuppressWarnings("unchecked")
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int var1 = 0; var1 < this.inventorySlots.size(); ++var1)
		{
			ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
			ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);

			if(!ItemStack.areItemStacksEqual(var3, var2))
			{
				var3 = var2 == null ? null : var2.copy();
				this.inventoryItemStacks.set(var1, var3);

				for(int var4 = 0; var4 < this.crafters.size(); ++var4)
					((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
			}
		}

		for(int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			if (this.fireTemp != this.vat.fireTemp)
				var2.sendProgressBarUpdate(this, 0, (int)this.vat.fireTemp);
			
			if (this.cookTimer != this.vat.cookTimer)
				var2.sendProgressBarUpdate(this, 1, (int)this.vat.cookTimer);
		}

		fireTemp = this.vat.fireTemp;
		cookTimer = this.vat.cookTimer;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if(par1 == 0)
			this.vat.fireTemp = par2;
		
		if(par1 == 1)
			this.vat.cookTimer = par2;
	}
}
