package terramisc.handlers;

import com.dunk.tfc.Core.Recipes;
import terramisc.core.TFCMItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingToolUsageHandlerTFCM 
{
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent e)
	{
		Item item = e.crafting.getItem();
		IInventory craftMatrix = e.craftMatrix;

		if(craftMatrix != null)
		{
			if(item == TFCMItems.bowLimb)
			{
				if (e.player.capabilities.isCreativeMode)
					e.crafting.setItemDamage(0);
				
				for(int i = 0; i < craftMatrix.getSizeInventory(); i++)
				{
					if(craftMatrix.getStackInSlot(i) == null)
						continue;

					for(int j = 0; j < Recipes.knives.length; j++)
					{
						if(craftMatrix.getStackInSlot(i).getItem() == Recipes.knives[j])
						{
							ItemStack tfcKnife = craftMatrix.getStackInSlot(i).copy();
							if(tfcKnife != null)
							{
								tfcKnife.damageItem(1, e.player);
								if(tfcKnife.getItemDamage() != 0 || e.player.capabilities.isCreativeMode)
								{
									craftMatrix.setInventorySlotContents(i, tfcKnife);
									craftMatrix.getStackInSlot(i).stackSize = 2;
								}
							}
						}
					}
				}
			}
		}
		
		if(craftMatrix != null)
		{
			if(item == TFCMItems.sinewFiber)
			{
				if (e.player.capabilities.isCreativeMode)
					e.crafting.setItemDamage(0);
				
				for(int i = 0; i < craftMatrix.getSizeInventory(); i++)
				{
					if(craftMatrix.getStackInSlot(i) == null)
						continue;

					for(int j = 0; j < Recipes.hammers.length; j++)
					{
						if(craftMatrix.getStackInSlot(i).getItem() == Recipes.hammers[j])
						{
							ItemStack tfcHammer = craftMatrix.getStackInSlot(i).copy();
							if(tfcHammer != null)
							{
								tfcHammer.damageItem(1, e.player);
								if(tfcHammer.getItemDamage() != 0 || e.player.capabilities.isCreativeMode)
								{
									craftMatrix.setInventorySlotContents(i, tfcHammer);
									craftMatrix.getStackInSlot(i).stackSize = 2;
								}
							}
						}
					}
				}
			}
		}
	}
}
