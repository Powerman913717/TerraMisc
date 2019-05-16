package terramisc.api.crops;

import java.util.Random;

import com.bioxx.tfc.Food.ItemFoodTFC;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import terramisc.tileentities.TECropTFCM;

public class CropIndexRipenTFCM extends CropIndexTFCM
{
	public CropIndexRipenTFCM(String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed, Boolean worldGen)
	{
		super(name, type, growth, stages, minGTemp, minATemp, seed, worldGen);
	}
	public CropIndexRipenTFCM(String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed, Boolean worldGen, float nutrientUsageMultiplier)
	{
		super(name, type, growth, stages, minGTemp, minATemp, seed, worldGen);
		nutrientUsageMult = nutrientUsageMultiplier;
	}
	public CropIndexRipenTFCM(String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed, Boolean worldGen, float nutrientUsageMultiplier, int[] nutriRestore)
	{
		super(name, type, growth, stages, minGTemp, minATemp, seed, worldGen);
		nutrientExtraRestore = nutriRestore.clone();
		nutrientUsageMult = nutrientUsageMultiplier;
	}

	@Override
	public ItemStack getOutput1(TECropTFCM crop)
	{
		if(output1 != null && crop.growth >= 5 && crop.growth < 6)
		{
			ItemStack is = new ItemStack(output1);
			Random r = new Random();
			if(r.nextInt(100) < chanceForOutput1)
			{
				ItemFoodTFC.createTag(is, getWeight(output1Avg, r));
				return is;
			}
		}
		return null;
	}
	@Override
	public ItemStack getOutput2(TECropTFCM crop)
	{
		if(output2 != null && crop.growth >= 6)
		{
			ItemStack is = new ItemStack(output2);
			Random r = new Random();
			if(r.nextInt(100) < chanceForOutput2)
			{
				ItemFoodTFC.createTag(is, getWeight(output2Avg, r));
				return is;
			}
		}
		return null;
	}
}
