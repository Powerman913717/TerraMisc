package terramisc.api.crafting;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;

public class FruitPressRecipe 
{
	private Item ingredient;
	private Fluid Output;
	private int OutputAmount;
	
	public FruitPressRecipe(Item ingredient, Fluid Output, int OutputAmount)
	{
		this.ingredient = ingredient;
		this.Output= Output;
		this.OutputAmount = OutputAmount;
	}
	
	public boolean matches(Item ingredient)
	{
		if(ingredient == this.ingredient)
			return true;
		else
			return false;
	}
	
	public FruitPressRecipe getRecipe()
	{
		return this;
	}
	
	public Fluid getOutput()
	{
		return this.Output;
	}
	
	public int getOutputAmount()
	{
		return OutputAmount;
	}
}
