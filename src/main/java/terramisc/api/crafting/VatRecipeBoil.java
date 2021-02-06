package terramisc.api.crafting;

import net.minecraftforge.fluids.FluidStack;

public class VatRecipeBoil extends VatRecipe {
    public VatRecipeBoil(FluidStack inputFluid, FluidStack outputFluid, int temp) {
        super(null, inputFluid, null, outputFluid, temp);
    }

    public VatRecipeBoil(FluidStack inputFluid, FluidStack outputFluid, int temp, int seal) {
        super(null, inputFluid, null, outputFluid, temp, seal);
    }
}
