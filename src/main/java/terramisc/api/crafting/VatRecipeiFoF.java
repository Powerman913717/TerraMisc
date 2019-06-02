package terramisc.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class VatRecipeiFoF extends VatRecipe
{
	public VatRecipeiFoF(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp) 
	{
		super(null, inputFluid, null, outputFluid, temp);
	}
	
	public VatRecipeiFoF(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp, int seal) 
	{
		super(null, inputFluid, null, outputFluid, temp, seal);
	}
}
