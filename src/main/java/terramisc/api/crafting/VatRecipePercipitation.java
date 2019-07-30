package terramisc.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class VatRecipePercipitation extends VatRecipe
{
	public VatRecipePercipitation(FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp) 
	{
		super(null, inputFluid, outIS, outputFluid, temp);
	}
	
	public VatRecipePercipitation(FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp, int seal) 
	{
		super(null, inputFluid, outIS, outputFluid, temp, seal);
	}
}
