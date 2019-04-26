package terramisc.items.food;

import java.util.Random;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemFoodReturn extends ItemFoodTFC
{
	private Item returnFood;
	private float returnWeight;
	private float returnChance;
	
	public ItemFoodReturn(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um) 
	{
		super(fg, sw, so, sa, bi, um);
	}

	public ItemFoodReturn(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, boolean edible) 
	{
		super(fg, sw, so, sa, bi, um, edible);
	}
	
	public ItemFoodReturn(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, boolean edible, boolean usable) 
	{
		super(fg, sw, so, sa, bi, um, edible, usable);
	}
	
	public Item setReturnFood(Item item, Float w, Float c)
	{
		returnFood = item;
		returnWeight = w;
		returnChance = c;
		
		return this;
	}
	
	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote && this.isEdible(is))
		{
			if(is.hasTagCompound())
			{
				//NBTTagCompound nbt = is.getTagCompound();
				float weight = Food.getWeight(is);
				float decay = Math.max(Food.getDecay(is), 0);

				float eatAmount = Math.min(weight - decay, 5f);
				float stomachDiff = foodstats.stomachLevel+eatAmount-foodstats.getMaxStomach(foodstats.player);
				if(stomachDiff > 0)
					eatAmount-=stomachDiff;

				float tasteFactor = foodstats.getTasteFactor(is);
				foodstats.addNutrition(((IFood)(is.getItem())).getFoodGroup(), eatAmount*tasteFactor);
				foodstats.stomachLevel += eatAmount*tasteFactor;
				if(FoodStatsTFC.reduceFood(is, eatAmount))
					is.stackSize = 0;
			}
			else
			{
				foodstats.addNutrition(((IFood)(is.getItem())).getFoodGroup(), 1f);

				String error = TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " " +
								TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact");
				TerraFirmaCraft.LOG.error(error);
				TFC_Core.sendInfoMessage(player, new ChatComponentText(error));
			}
		}

		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		TFC_Core.setPlayerFoodStats(player, foodstats);
		
		//Start of new code
		if(Food.isCooked(is) == false)
		{
			if(world.rand.nextFloat() < returnChance)
			{
				Random r = new Random();
				ItemStack food = ItemFoodTFC.createTag(new ItemStack(returnFood), CropIndex.getWeight(returnWeight, r));
				
                player.dropPlayerItemWithRandomChoice(food, false);
			}
		}
		
		return is;
	}
}
