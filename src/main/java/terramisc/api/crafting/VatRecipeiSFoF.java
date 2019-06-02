package terramisc.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class VatRecipeiSFoF extends VatRecipe
{
	public VatRecipeiSFoF(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp) 
	{
		super(inputItem, inputFluid, null, outputFluid, temp);
	}
	
	public VatRecipeiSFoF(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp, int seal) 
	{
		super(inputItem, inputFluid, null, outputFluid, temp, seal);
	}
}
