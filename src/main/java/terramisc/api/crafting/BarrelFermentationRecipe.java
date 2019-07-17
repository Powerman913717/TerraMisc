package terramisc.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import com.dunk.tfc.api.Crafting.BarrelRecipe;

public class BarrelFermentationRecipe extends BarrelRecipe
{

	public FluidStack inputfluid;
	
	/**
	This recipe is intended to convert one fluid to another after a certain period of time.
	*/
	public BarrelFermentationRecipe(FluidStack inputFluid, FluidStack outputFluid, int seal) 
	{
		super(null, inputFluid, null, outputFluid, seal);
		this.inputfluid = inputFluid;
	}
	
	@Override
	public Boolean matches(ItemStack itemstack, FluidStack inFluid)
	{
		if(recipeFluid != null && recipeFluid.isFluidEqual(inFluid))
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		if(recipeOutFluid != null)
		{
			return recipeOutFluid;
		}
		
		return null;
	}

	public FluidStack getInputfluid()
	{
		return inputfluid;
	}
}
