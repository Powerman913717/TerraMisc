package terramisc.api.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class VatManager
{
	private static final VatManager INSTANCE = new VatManager();
	public static final VatManager getInstance()
	{
		return INSTANCE;
	}
	
	private static List<VatRecipe> recipes;
	
	private VatManager()
	{
		recipes = new ArrayList<VatRecipe>();
	}
	
	public static void addRecipe(VatRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public void clearRecipes()
	{
		recipes.clear();
	}
	
	public List<VatRecipe> getRecipeList()
	{
		return recipes;
	}
	
	public VatRecipe findMatchingRecipe(ItemStack item, FluidStack fluid)
	{
		for(Object recipe : recipes)
		{
			VatRecipe br = (VatRecipe) recipe;
			if(br.matches(item, fluid))
				return br;
		}
		return null;
	}
}
