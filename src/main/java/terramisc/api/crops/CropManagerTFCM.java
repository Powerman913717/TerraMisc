package terramisc.api.crops;

import java.util.ArrayList;
import java.util.List;

public class CropManagerTFCM //See CropManager.class
{
    public List<CropIndexTFCM> crops;

    protected static final CropManagerTFCM INSTANCE = new CropManagerTFCM();

    public static final CropManagerTFCM getInstance() {
        return INSTANCE;
    }

    public CropManagerTFCM() {
        crops = new ArrayList<CropIndexTFCM>();
    }

    public void addIndex(CropIndexTFCM index) {
        crops.add((CropIndexTFCM) index);
    }

    public int getTotalCrops() {
        return crops.size();
    }

    /**
     * @param n String Name/ID of Crop
     * @return CropIndex associated with the Name/ID String
     */
    public CropIndexTFCM getCropFromName(String n) {
        for (CropIndexTFCM pi : crops)
            if (pi.cropName.equalsIgnoreCase(n))
                return pi;
        return null;
    }
}