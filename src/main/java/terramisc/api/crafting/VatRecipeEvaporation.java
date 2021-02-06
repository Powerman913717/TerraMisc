package terramisc.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class VatRecipeEvaporation extends VatRecipe {
    public VatRecipeEvaporation(FluidStack inputFluid, ItemStack outIS, int temp) {
        super(null, inputFluid, outIS, null, temp);
    }

    public VatRecipeEvaporation(FluidStack inputFluid, ItemStack outIS, int temp, int seal) {
        super(null, inputFluid, outIS, null, temp, seal);
    }
}
