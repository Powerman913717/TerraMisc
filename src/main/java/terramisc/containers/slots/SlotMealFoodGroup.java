package terramisc.containers.slots;

import java.util.ArrayList;
import java.util.List;

import com.dunk.tfc.Containers.Slots.SlotSize;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.Interfaces.ISize;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotMealFoodGroup extends SlotSize
{
	private List<EnumFoodGroup> excpetionsFG = new ArrayList<EnumFoodGroup>();
	private List<EnumFoodGroup> inclusionsFG = new ArrayList<EnumFoodGroup>();
	private EnumFoodGroup[] group = new EnumFoodGroup[2];
	public SlotMealFoodGroup(IInventory iinventory, int i, int j, int k, EnumFoodGroup g)
	{
		super(iinventory, i, j, k);
		group[0] = g;
		group[1] = g;
	}
	
	public SlotMealFoodGroup(IInventory iinventory, int i, int j, int k, EnumFoodGroup g, EnumFoodGroup g1)
	{
		super(iinventory, i, j, k);
		group[0] = g;
		group[1] = g1;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		Item item = itemstack.getItem();
		if (item instanceof IFood && ((IFood) item).isUsable(itemstack))
		{
			EnumFoodGroup efg = ((IFood) item).getFoodGroup();
			if(efg == null)
				return false;
			if(group[0] == null || group[1] == null)
				return false;
			if(efg != group[0])
			{
				if(efg != group[1])
					return false;
			}
			
			
			
			boolean except = excpetionsFG.contains(efg);
			boolean include = inclusionsFG.contains(efg) || inclusionsFG.isEmpty();
			if (except || !include)
				return false;
			if (item instanceof ISize && ((ISize) item).getSize(itemstack).stackSize >= size.stackSize)
				return super.isItemValid(itemstack);
		}
		return false;
	}
	
	public SlotMealFoodGroup addFGException(EnumFoodGroup... ex)
	{
		for(int i = 0; i < ex.length; i++)
			excpetionsFG.add(ex[i]);
		return this;
	}

	public SlotMealFoodGroup addFGInclusion(EnumFoodGroup... ex)
	{
		for(int i = 0; i < ex.length; i++)
			inclusionsFG.add(ex[i]);
		return this;
	}
}
