package terramisc.api.crops;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Util.Helper;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropIndexTFCM
{
	public int growthTime;
	public String cropName;
	public int cycleType;
	public int numGrowthStages;
	public float minGrowthTemp;
	public float minAliveTemp;
	public float nutrientUsageMult;
	public int[] nutrientExtraRestore;
	public boolean dormantInFrost;
	public int maxLifespan = -1;

	public int chanceForOutput1 = 100;
	public Item output1;
	public float output1Avg;

	public int chanceForOutput2 = 100;
	public Item output2;
	public float output2Avg;

	public boolean needsSunlight = true;
	public float waterUsageMult = 1;
	public Item seedItem;
	
	public boolean generate = false;

	/**
	 * 
	 * @param name Crop Name used to identify the crop. Keep these unique!
	 * @param type Nutriant type consumed by crop.
	 * @param growth Time taken to grow.
	 * @param stages Number of growth stages.
	 * @param minGTemp Minimum temperature to grow.
	 * @param minATemp Minimum temperature to prevent plant death.
	 * @param seed Item that is dropped as a seed.
	 * @param worldGen Should the crop be added to world generation?
	 */
	public CropIndexTFCM(String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed, Boolean worldGen)
	{
		growthTime = growth;
		cycleType = type;
		cropName = name.toLowerCase();
		numGrowthStages = stages;
		minGrowthTemp = minGTemp;
		minAliveTemp = minATemp;
		nutrientExtraRestore = new int[]{0,0,0};
		nutrientUsageMult = 1.0f;
		dormantInFrost = false;
		seedItem = seed;
		generate = worldGen;
	}
	
	/**
	 * 
	 * @param name Crop Name used to identify the crop. Keep these unique!
	 * @param type Nutriant type consumed by crop.
	 * @param growth Time taken to grow.
	 * @param stages Number of growth stages.
	 * @param minGTemp Minimum temperature to grow.
	 * @param minATemp Minimum temperature to prevent plant death.
	 * @param seed Item that is dropped as a seed.
	 * @param worldGen Should the crop be added to world generation?
	 * @param nutrientUsageMultiplier Influences the rates at which nutriants are consumed.
	 */
	public CropIndexTFCM(String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed, Boolean worldGen, float nutrientUsageMultiplier)
	{
		this(name,type,growth,stages,minGTemp,minATemp,seed,worldGen);
		nutrientUsageMult = nutrientUsageMultiplier;
	}
	
	/**
	 * 
	 * @param name Crop Name used to identify the crop. Keep these unique!
	 * @param type Nutriant type consumed by crop.
	 * @param growth Time taken to grow.
	 * @param stages Number of growth stages.
	 * @param minGTemp Minimum temperature to grow.
	 * @param minATemp Minimum temperature to prevent plant death.
	 * @param seed Item that is dropped as a seed.
	 * @param worldGen Should the crop be added to world generation?@param nutrientUsageMultiplier Influences the rates at which nutriants are consumed.
	 * @param Type of nutriant that will be restored by the crop. Should NOT be the same as the consumed type.
	 */
	public CropIndexTFCM(String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed, Boolean worldGen, float nutrientUsageMultiplier, int[] nutriRestore)
	{
		this(name,type,growth,stages,minGTemp,minATemp,seed,worldGen);
		nutrientExtraRestore = nutriRestore.clone();
		nutrientUsageMult = nutrientUsageMultiplier;
	}

	public CropIndexTFCM setOutput1(Item o, float oAvg)
	{
		output1 = o;
		output1Avg = oAvg;
		return this;
	}
	public CropIndexTFCM setOutput2(Item o, float oAvg)
	{
		output2 = o;
		output2Avg = oAvg;
		return this;
	}
	public CropIndexTFCM setOutput1Chance(Item o, float oAvg, int chance)
	{
		output1 = o;
		output1Avg = oAvg;
		chanceForOutput1 = chance;
		return this;
	}
	public CropIndexTFCM setOutput2Chance(Item o, float oAvg, int chance)
	{
		output2 = o;
		output2Avg = oAvg;
		chanceForOutput2 = chance;
		return this;
	}  
	public ItemStack getOutput1(TECropTFCM teCropTFCM)
	{
		if (output1 != null && teCropTFCM.growth >= numGrowthStages)
		{
			ItemStack is = new ItemStack(output1);
			Random r = new Random();
			if(r.nextInt(100) < chanceForOutput1)
			{
				ItemFoodTFC.createTag(is, getWeight(output1Avg, r));
				addFlavorProfile(teCropTFCM, is);
				return is;
			}
		}
		return null;
	}
	public ItemStack getOutput2(TECropTFCM crop)
	{
		if (output2 != null && crop.growth >= numGrowthStages)
		{
			ItemStack is = new ItemStack(output2);
			Random r = new Random();
			if(r.nextInt(100) < chanceForOutput2)
			{
				ItemFoodTFC.createTag(is, getWeight(output2Avg, r));
				addFlavorProfile(crop, is);
				return is;
			}
		}
		return null;
	}

	private Random getGrowthRand(TECropTFCM teCropTFCM)
	{
		Block farmBlock = teCropTFCM.getWorldObj().getBlock(teCropTFCM.xCoord, teCropTFCM.yCoord-1, teCropTFCM.zCoord);
		//Block underFarmBlock = te.getWorldObj().getBlock(te.xCoord, te.yCoord-2, te.zCoord);
		if(!TFC_Core.isSoil(farmBlock))
		{
			int soilType1 = (farmBlock == TFCBlocks.tilledSoil ? teCropTFCM.getWorldObj().getBlockMetadata(teCropTFCM.xCoord, teCropTFCM.yCoord-1, teCropTFCM.zCoord) : 
				teCropTFCM.getWorldObj().getBlockMetadata(teCropTFCM.xCoord, teCropTFCM.yCoord-1, teCropTFCM.zCoord)+16);
			int soilType2 = (farmBlock == TFCBlocks.dirt ? teCropTFCM.getWorldObj().getBlockMetadata(teCropTFCM.xCoord, teCropTFCM.yCoord-2, teCropTFCM.zCoord)*2 : 
				farmBlock == TFCBlocks.dirt2 ? (teCropTFCM.getWorldObj().getBlockMetadata(teCropTFCM.xCoord, teCropTFCM.yCoord-2, teCropTFCM.zCoord)+16)*2 : 0);

			int ph = TFC_Climate.getCacheManager(teCropTFCM.getWorldObj()).getPHLayerAt(teCropTFCM.xCoord, teCropTFCM.zCoord).data1*100;
			int drainage = 0;

			for(int y = 2; y < 8; y++)
			{
				if(TFC_Core.isGravel(teCropTFCM.getWorldObj().getBlock(teCropTFCM.xCoord, teCropTFCM.yCoord-y, teCropTFCM.zCoord)))
				{
					drainage++;
				}
			}
			drainage *= 1000;

			return new Random(soilType1+soilType2+ph+drainage);
		}
		return null;
	}

	private void addFlavorProfile(TECropTFCM crop, ItemStack outFood)
	{
		Random r = getGrowthRand(crop);
		if(r != null)
		{
			Food.adjustFlavor(outFood, r);
		}
	}

	public static float getWeight(float average, Random r)
	{
		float weight = average + (average * (10 * r.nextFloat() - 5) / 100);
		return Helper.roundNumber(weight, 10);
	}

	public CropIndexTFCM setNeedsSunlight(boolean b)
	{
		needsSunlight = b;
		return this;
	}

	public CropIndexTFCM setWaterUsage(float m)
	{
		waterUsageMult = m;
		return this;
	}

	public CropIndexTFCM setGoesDormant(boolean b)
	{
		dormantInFrost = b;
		return this;
	}

	public boolean canWorldGen(String s)
	{
		return generate;
	}
	
	public ItemStack getSeed()
	{
		return new ItemStack(seedItem, 1);
	}

	public int getCycleType()
	{
		return cycleType;
	}
	public void onCropGrow(float stage){}
}
