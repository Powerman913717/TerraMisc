package terramisc.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Items.ItemAlcohol;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

public class ItemBottleSoyMilk extends ItemAlcohol
{
	public ItemBottleSoyMilk()
	{
		super();
		this.setFolder("food/");
		this.setContainerItem(TFCItems.glassBottle);
		this.setMaxStackSize(64);
	}
	
	@Override
	public boolean canStack()
	{
		return true;
	}
	
	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			--is.stackSize;
		}

		if (!world.isRemote)
		{

			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
			fs.restoreWater(player, 8000);
			fs.addNutrition(EnumFoodGroup.Dairy, 20.0F);
			
			player.clearActivePotions();
		}
		
		// First try to add the empty bottle to an existing stack of bottles in the inventory
				if (!player.capabilities.isCreativeMode && !player.inventory.addItemStackToInventory(new ItemStack(TFCItems.glassBottle)))
				{
					// If we couldn't fit the empty bottle in the inventory, and we drank the last alcohol bottle, put the empty bottle in the empty held slot
					if (is.stackSize == 0)
						return new ItemStack(TFCItems.glassBottle);
					// If we couldn't fit the empty bottle in the inventory, and there is more alcohol left in the stack, drop the bottle on the ground
					else
						player.dropPlayerItemWithRandomChoice(new ItemStack(TFCItems.glassBottle), false);
				}
		
		return is;
	}
}
