package terramisc.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class VatRecipeDoubleBoiler extends VatRecipe
{
	public VatRecipeDoubleBoiler(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, int temp) 
	{
		super(inputItem, inputFluid, outIS, null, temp);
	}
	
	public VatRecipeDoubleBoiler(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, int temp, int seal) 
	{
		super(inputItem, inputFluid, outIS, null, temp, seal);
	}
}
