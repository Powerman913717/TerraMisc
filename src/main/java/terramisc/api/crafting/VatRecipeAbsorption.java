package terramisc.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class VatRecipeAbsorption extends VatRecipe {
    public VatRecipeAbsorption(ItemStack inputItem, FluidStack inputFluid, FluidStack outputFluid, int temp) {
        super(inputItem, inputFluid, null, outputFluid, temp);
    }

    public VatRecipeAbsorption(ItemStack inputItem, FluidStack inputFluid, FluidStack outputFluid, int temp, int seal) {
        super(inputItem, inputFluid, null, outputFluid, temp, seal);
    }
}
