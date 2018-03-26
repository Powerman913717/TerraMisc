package terramisc.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import terramisc.core.TFCMAchievements;
import terramisc.items.weights.ItemWeightSmallHeavy;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Interfaces.ISize;

public class ItemBottleSugar extends ItemWeightSmallHeavy
{
	public ItemBottleSugar()
	{
		this.setMaxStackSize(64);
	}
	
	@Override
	public boolean canStack()
	{
		return true;
	}
	
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			--is.stackSize;
			
			if (!player.capabilities.isCreativeMode && (!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.glassBottle))))
			{
				if (is.stackSize == 0)
				{
					is = new ItemStack(TFCItems.glassBottle);
					player.dropPlayerItemWithRandomChoice(ItemFoodTFC.createTag(new ItemStack(TFCItems.sugar), 1F), false);
				}
				else
				{
					player.dropPlayerItemWithRandomChoice(new ItemStack(TFCItems.glassBottle), false);
					player.dropPlayerItemWithRandomChoice(ItemFoodTFC.createTag(new ItemStack(TFCItems.sugar), 1F), false);
				}	
			}
			else
			{
				player.dropPlayerItemWithRandomChoice(ItemFoodTFC.createTag(new ItemStack(TFCItems.sugar), 1F), false);
			}
		}
		
		player.addStat(TFCMAchievements.achSugar, 1);
		return is;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		if(((ISize)is.getItem()).getSize(is)!= null && ((ISize)is.getItem()).getWeight(is) != null && ((ISize)is.getItem()).getReach(is)!= null)
			arraylist.add("\u2696" + TFC_Core.translate("gui.Weight." + ((ISize)is.getItem()).getWeight(is).getName()) + " \u21F2" + 
					TFC_Core.translate("gui.Size." + ((ISize)is.getItem()).getSize(is).getName().replace(" ", "")));
		
		arraylist.add("Right click to remove sugar from bottle.");
	}
	
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) 
    {
        return true;
    }
    
    public boolean hasContainerItem() 
    {
    	return true;
    }
    
    public ItemStack getContainerItem(ItemStack itemStack) 
    {
    	itemStack = new ItemStack(TFCItems.glassBottle);
    	
    	return itemStack;
    	
    }
}
