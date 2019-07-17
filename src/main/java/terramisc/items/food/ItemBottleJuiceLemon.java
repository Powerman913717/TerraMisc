package terramisc.items.food;

import terramisc.core.TFCMAchievements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.Items.ItemAlcohol;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Enums.EnumFoodGroup;

public class ItemBottleJuiceLemon extends ItemAlcohol
{
	public ItemBottleJuiceLemon()
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
			fs.restoreWater(player, -4000);
			fs.addNutrition(EnumFoodGroup.Fruit, 20.0F);
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
		
		player.addStat(TFCMAchievements.achJuiceLemon, 1);
		return is;
	}
}
