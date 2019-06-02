package terramisc.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class VatRecipeiFoSF extends VatRecipe
{
	public VatRecipeiFoSF(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp) 
	{
		super(null, inputFluid, outIS, outputFluid, temp);
	}
	
	public VatRecipeiFoSF(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp, int seal) 
	{
		super(null, inputFluid, outIS, outputFluid, temp, seal);
	}
}
