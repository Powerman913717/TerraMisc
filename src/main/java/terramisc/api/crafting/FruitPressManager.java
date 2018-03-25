package terramisc.api.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

public class FruitPressManager
{
	private static final FruitPressManager INSTANCE = new FruitPressManager();
	public static final FruitPressManager getInstance()
	{
		return INSTANCE;
	}
	
	private static List<FruitPressRecipe> recipes;
	
	private FruitPressManager()
	{
		recipes = new ArrayList<FruitPressRecipe>();
	}
	
	public static void addRecipe(FruitPressRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public void clearRecipes()
	{
		recipes.clear();
	}
	
	public List<FruitPressRecipe> getRecipeList()
	{
		return recipes;
	}
	
	public FruitPressRecipe getMatchingRecipe(Item inputItem)
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			FruitPressRecipe irecipe = recipes.get(i);
			if(irecipe != null && irecipe.matches(inputItem))
				return irecipe.getRecipe();
		}
		return null;
	}
}
