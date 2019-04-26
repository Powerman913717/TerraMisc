package terramisc.items.food;

import java.util.Random;

import terramisc.core.TFCMAchievements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Items.ItemAlcohol;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

public class ItemBottleWine extends ItemAlcohol
{
	public ItemBottleWine()
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

			Random rand = new Random();
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
			fs.restoreWater(player, 4000);
			fs.addNutrition(EnumFoodGroup.Fruit, 25.0F);
			int time = 400+rand.nextInt(1000);
			fs.consumeAlcohol();
			if(rand.nextInt(100)==0){
				player.addPotionEffect(new PotionEffect(8,time,4));
			}
			if(rand.nextInt(100)==0){
				player.addPotionEffect(new PotionEffect(5,time,4));
			}
			if(rand.nextInt(100)==0){
				player.addPotionEffect(new PotionEffect(8,time,4));
			}
			if(rand.nextInt(200)==0){
				player.addPotionEffect(new PotionEffect(10,time,4));
			}
			if(rand.nextInt(150)==0){
				player.addPotionEffect(new PotionEffect(12,time,4));
			}
			if(rand.nextInt(180)==0){
				player.addPotionEffect(new PotionEffect(13,time,4));
			}
			int levelMod = 250*player.experienceLevel;
			if(fs.soberTime >TFC_Time.getTotalTicks()+3000+levelMod){
				/*if(rand.nextInt(4)==0){
					//player.addPotionEffect(new PotionEffect(9,time,4));
				}*/
				if(fs.soberTime >TFC_Time.getTotalTicks()+5000+levelMod){
					if(rand.nextInt(4)==0){
						player.addPotionEffect(new PotionEffect(18,time,4));
					}
					if(fs.soberTime >TFC_Time.getTotalTicks()+7000+levelMod){
						if(rand.nextInt(2)==0){
							player.addPotionEffect(new PotionEffect(15,time,4));
						}
						if(fs.soberTime >TFC_Time.getTotalTicks()+8000+levelMod){
							if(rand.nextInt(10)==0){
								player.addPotionEffect(new PotionEffect(20,time,4));
							}
						}
						if(fs.soberTime > TFC_Time.getTotalTicks()+10000+levelMod && !player.capabilities.isCreativeMode){
							fs.soberTime = 0;

							player.attackEntityFrom(new DamageSource("alcohol").setDamageBypassesArmor().setDamageIsAbsolute(), player.getMaxHealth());
						}
					}

				}
			}
			
			player.addStat(TFCMAchievements.achWine, 1);
			TFC_Core.setPlayerFoodStats(player, fs);
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
