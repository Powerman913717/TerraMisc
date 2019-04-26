package terramisc.food;

import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropIndexPepper;
import com.bioxx.tfc.Food.CropManager;

import terramisc.core.TFCMItems;
import terramisc.core.TFCMOptions;

public class CropRegistry 
{
	/**
	 * CropIndex (ID, Name, Nutrient Type, Time, Stages, minimiumGTemp, minimiumATemp, Nutrient Usage, Seeds, Crop)
	 */
	public static void registerCrops()
	{
		CropManager cropmanager = CropManager.getInstance();

		cropmanager.addIndex(new CropIndex(TFCMOptions.pumpkinID, "pumpkin", 2, 32, 7, 14, 3, 0.9f, TFCMItems.seedsPumpkin).setOutput1(TFCMItems.pumpkin, 32));
		cropmanager.addIndex(new CropIndexPepper(TFCMOptions.cayenneID, "cayenne", 2, 18, 6, 14, 6, 1.2f, TFCMItems.seedsCayenne).setOutput1(TFCMItems.greenCayenne, 6).setOutput2(TFCMItems.redCayenne, 6));
		//Harvest Coffee Cherries, Process into Green Coffee Beans, Roast into Coffee Beans
		cropmanager.addIndex(new CropIndex(TFCMOptions.coffeeID, "coffee", 1, 90, 6, 19, 14, 0.3f, TFCMItems.seedsCoffee).setOutput1(TFCMItems.coffee, 4));
	}
}
