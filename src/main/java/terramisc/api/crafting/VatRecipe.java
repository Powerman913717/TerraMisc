package terramisc.api.crafting;

import java.util.Stack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class VatRecipe 
{
	public ItemStack recipeIS;
	public FluidStack recipeFluid;
	public ItemStack recipeOutIS;
	public FluidStack recipeOutFluid;
	public int cookTime = 1; //Time in hours
	public int temperature; //Temperture in celsius
	public boolean removesLiquid = true;
	public boolean allowAnyStack = true;

	public VatRecipe(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp)
	{
		this.recipeIS = inputItem;
		recipeFluid = inputFluid;
		this.recipeOutIS = outIS;
		recipeOutFluid = outputFluid;
		temperature = temp;
	}

	public VatRecipe(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int temp, int seal)
	{
		this(inputItem, inputFluid, outIS, outputFluid, temp);
		this.cookTime = seal;
	}

	public VatRecipe setRemovesLiquid(boolean b)
	{
		this.removesLiquid = b;
		return this;
	}

	public VatRecipe setAllowAnyStack(boolean b)
	{
		this.allowAnyStack = b;
		return this;
	}

	public Boolean matches(ItemStack item, FluidStack fluid)
	{
		boolean iStack = removesLiquid ? true : recipeIS != null && item != null && fluid != null && recipeFluid != null && item.stackSize >= (int)Math.ceil(fluid.amount/recipeFluid.amount);
		boolean fStack = !removesLiquid ? true : recipeFluid != null && item != null && fluid != null && recipeOutFluid != null && fluid.amount >= item.stackSize*recipeOutFluid.amount;

		boolean anyStack = !removesLiquid && this.recipeOutIS == null && allowAnyStack;
		boolean itemsEqual = item == null && recipeIS == null || OreDictionary.itemMatches(recipeIS, item, false);

		return (recipeIS != null && itemsEqual && (iStack || anyStack) || recipeIS == null) &&
				(recipeFluid != null && recipeFluid.isFluidEqual(fluid) && (fStack || anyStack) || recipeFluid == null);
	}

	public Boolean isInFluid(FluidStack item)
	{
		return recipeFluid.isFluidEqual(item);
	}

	public ItemStack getRecipeInIS()
	{
		return recipeIS;
	}

	public FluidStack getRecipeInFluid()
	{
		return recipeFluid;
	}

	public ItemStack getRecipeOutIS()
	{
		return recipeOutIS;
	}

	public FluidStack getRecipeOutFluid()
	{
		return recipeOutFluid;
	}

	public int getRecipeTemperature()
	{
		return temperature;
	}
	
	public boolean isRemovesLiquid()
	{
		return removesLiquid;
	}

	public boolean isAllowAnyStack()
	{
		return allowAnyStack;
	}

	public String getRecipeName()
	{
		String s = "";
		if(this.recipeOutIS != null)
		{
			if(recipeOutIS.stackSize > 1)
				s += recipeOutIS.stackSize+"x ";
			s += recipeOutIS.getDisplayName();
		}
		if(recipeOutFluid != null && !this.recipeFluid.isFluidEqual(recipeOutFluid))
			s = recipeOutFluid.getFluid().getLocalizedName(recipeOutFluid);
		return s;
	}

	protected int getnumberOfRuns(ItemStack inIS, FluidStack inFS)
	{
		int runs = 0;
		int div = 0;
		if(inIS != null && recipeIS != null)
		{
			runs = inIS.stackSize/this.recipeIS.stackSize;
			div = inFS.amount/this.getRecipeInFluid().amount;
		}
		return Math.min(runs, div);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Stack<ItemStack> getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		Stack<ItemStack> stackList = new Stack();
		ItemStack outStack = null;

		if (recipeOutIS != null)
		{
			stackList.clear();
			outStack = recipeOutIS.copy();
			int outputCount = outStack.stackSize * this.getnumberOfRuns(inIS, inFS);
			int maxStackSize = outStack.getMaxStackSize();
			Item item = outStack.getItem();
			int damage = outStack.getItemDamage();

			int remainder = outputCount % maxStackSize; // The amount remaining after making full-sized stacks.
			if (remainder > 0)
			{
				stackList.push(new ItemStack(item, remainder, damage)); // Push this on first, so it doesn't end up in the input slot.
				outputCount -= remainder;
			}

			while (outputCount >= maxStackSize) // Add as many full-sized stacks as possible to stackList.
			{
				stackList.push(new ItemStack(item, maxStackSize, damage));
				outputCount -= maxStackSize;
			}
			return stackList;

		}
		if (!removesLiquid && inIS != null && inFS != null)
		{
			stackList.clear();
			outStack = inIS.copy();
			outStack.stackSize -= inFS.amount / this.recipeOutFluid.amount;
			stackList.push(outStack);
		}
		if (outStack == null)
		{
			stackList.clear();
			stackList.push(outStack);
		}
		return stackList;
	}

	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		if(recipeOutFluid != null)
		{
			FluidStack fs = null;
			// The FluidStack .copy() method does not make a copy of the NBT tag, which may have been the cause of the quantum entanglement
			if (recipeOutFluid.tag != null)
			{
				fs = new FluidStack(recipeOutFluid.getFluid(), recipeOutFluid.amount, (NBTTagCompound) recipeOutFluid.tag.copy());
			}
			else
			{
				fs = new FluidStack(recipeOutFluid.getFluid(), recipeOutFluid.amount);
			}
			
			if (!removesLiquid && inFS != null)
			{
				fs.amount = inFS.amount;
			}
			else if (inIS != null)
			{
				fs.amount *= inIS.stackSize;
			}
			return fs;
		}
		return null;
	}
}
