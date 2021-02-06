package terramisc.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class VatRecipe {
    public ItemStack recipeIS;
    public FluidStack recipeFluid;
    public ItemStack recipeOutIS;
    public FluidStack recipeOutFluid;
    public int cookTime = 1; //Time in hours
    public int temperature; //Temperture in celsius
    public boolean allowAnyStack = true;

    public VatRecipe(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp) {
        this.recipeIS = inputItem;
        recipeFluid = inputFluid;
        this.recipeOutIS = outIS;
        recipeOutFluid = outputFluid;
        temperature = temp;
    }

    public VatRecipe(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp, int seal) {
        this(inputItem, inputFluid, outIS, outputFluid, temp);
        this.cookTime = seal;
    }

    public VatRecipe setAllowAnyStack(boolean b) {
        this.allowAnyStack = b;
        return this;
    }

    public Boolean matches(ItemStack item, FluidStack fluid) {
        Boolean checkItems = false;
        Boolean checkFluid = false;

        //Check itemstack and fluidstack
        if (this instanceof VatRecipeAbsorption || this instanceof VatRecipeDoubleBoiler) {
            if (item == null || fluid == null)
                return false;

            if (item.getItem() == recipeIS.getItem())
                checkItems = true;
            else if (OreDictionary.itemMatches(item, recipeIS, false))
                checkItems = true;

            if (fluid.getFluid() == recipeFluid.getFluid())
                checkFluid = true;
        }

        //TODO add melt recipe conditions

        //Check fluidstack, itemstack must be null.
        if (this instanceof VatRecipeBoil || this instanceof VatRecipeEvaporation || this instanceof VatRecipePercipitation) {
            if (item != null || fluid == null)
                return false;

            if (item == null)
                checkItems = true;

            if (fluid.getFluid() == recipeFluid.getFluid())
                checkFluid = true;
        }

        if (checkItems && checkFluid)
            return true;

        return false;
    }

    public Boolean isInFluid(FluidStack item) {
        return recipeFluid.isFluidEqual(item);
    }

    public ItemStack getRecipeInIS() {
        return recipeIS;
    }

    public FluidStack getRecipeInFluid() {
        return recipeFluid;
    }

    public ItemStack getRecipeOutIS() {
        return recipeOutIS;
    }

    public FluidStack getRecipeOutFluid() {
        return recipeOutFluid;
    }

    public int getRecipeTemperature() {
        return temperature;
    }

    public boolean isAllowAnyStack() {
        return allowAnyStack;
    }

    public String getRecipeName() {
        String s = "";
        if (this.recipeOutIS != null) {
            if (recipeOutIS.stackSize > 1)
                s += recipeOutIS.stackSize + "x ";
            s += recipeOutIS.getDisplayName();
        }
        if (recipeOutFluid != null && !this.recipeFluid.isFluidEqual(recipeOutFluid))
            s = recipeOutFluid.getFluid().getLocalizedName(recipeOutFluid);
        return s;
    }

    public ItemStack getResult(ItemStack inIS, FluidStack inFS) {
        ItemStack outStack = null;

        if (recipeOutIS != null) {
            return outStack = recipeOutIS.copy();
        }

        return outStack;
    }

    public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS) {
        if (recipeOutFluid != null) {
            FluidStack fs = null;
            // The FluidStack .copy() method does not make a copy of the NBT tag, which may have been the cause of the quantum entanglement
            if (recipeOutFluid.tag != null) {
                fs = new FluidStack(recipeOutFluid.getFluid(), recipeOutFluid.amount, (NBTTagCompound) recipeOutFluid.tag.copy());
            } else {
                fs = new FluidStack(recipeOutFluid.getFluid(), recipeOutFluid.amount);
            }

            return fs;
        }
        return null;
    }
}
