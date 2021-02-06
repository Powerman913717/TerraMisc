package terramisc.containers.slots;

import com.dunk.tfc.Containers.Slots.SlotSize;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.Interfaces.ISize;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SlotMealFood extends SlotSize {
    private List<EnumFoodGroup> excpetionsFG = new ArrayList<EnumFoodGroup>();
    private List<EnumFoodGroup> inclusionsFG = new ArrayList<EnumFoodGroup>();

    public SlotMealFood(IInventory iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        Item item = itemstack.getItem();
        if (item instanceof IFood) //Modified to allow Dough and Flour items
        {
            EnumFoodGroup efg = ((IFood) item).getFoodGroup();

            boolean except = excpetionsFG.contains(efg);
            boolean include = inclusionsFG.contains(efg) || inclusionsFG.isEmpty();
            if (except || !include)
                return false;
            if (item instanceof ISize && ((ISize) item).getSize(itemstack).stackSize >= size.stackSize)
                return super.isItemValid(itemstack);
        }
        return false;
    }

    public SlotMealFood addFGException(EnumFoodGroup... ex) {
        for (int i = 0; i < ex.length; i++)
            excpetionsFG.add(ex[i]);
        return this;
    }

    public SlotMealFood addFGInclusion(EnumFoodGroup... ex) {
        for (int i = 0; i < ex.length; i++)
            inclusionsFG.add(ex[i]);
        return this;
    }
}
