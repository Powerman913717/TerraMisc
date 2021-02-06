package terramisc.plants;

import terramisc.api.crops.CropIndexRipenTFCM;
import terramisc.api.crops.CropIndexTFCM;
import terramisc.api.crops.CropManagerTFCM;
import terramisc.core.TFCMItems;

public class CropRegistry {
    /**
     * CropIndex(ID, Name, Nutrient Type, Time, Stages, minimiumGTemp, minimiumATemp, seed item, worldgen boolean, Nutrient Usage)
     */
    public static void registerCrops() {
        CropManagerTFCM manager = CropManagerTFCM.getInstance();

        manager.addIndex(new CropIndexRipenTFCM("cayenne", 2, 18, 6, 12, 6, TFCMItems.seedsCayenne, true, 1.2f).setOutput1(TFCMItems.greenCayenne, 6.0f).setOutput2(TFCMItems.redCayenne, 6.0f));
        manager.addIndex(new CropIndexTFCM("coffee", 1, 90, 6, 19, 14, TFCMItems.seedsCoffee, true, 0.3f).setOutput1(TFCMItems.coffeeCherries, 4.0f));
        manager.addIndex(new CropIndexTFCM("sweet potato", 2, 48, 6, 4, 0, TFCMItems.seedsSweetPotato, true, 0.7f).setOutput1(TFCMItems.sweetPotato, 50.0f));
        manager.addIndex(new CropIndexTFCM("hops", 1, 28, 7, 10, 0, TFCMItems.seedsHops, true, 1.0f).setOutput1(TFCMItems.hops, 8f).setGoesDormant(true));
    }
}
