package terramisc.common;

public class ArmorStats {
    public static ArmorStats[] armorArrayStats = new ArmorStats[256];
    public static ArmorStats CopperChain = new ArmorStats(1, 480, 480, 100, "Copper_Chain");//Tier 0
    public static ArmorStats BismuthBronzeChain = new ArmorStats(2, 720, 480, 132, "BismuthBronze_Chain");//Tier 1
    public static ArmorStats BlackBronzeChain = new ArmorStats(3, 480, 720, 132, "BlackBronze_Chain");//Tier 1
    public static ArmorStats BronzeChain = new ArmorStats(4, 600, 600, 132, "Bronze_Chain");//Tier 1
    public static ArmorStats WroughtIronChain = new ArmorStats(5, 960, 960, 212, "WroughtIron_Chain");//Tier 2
    public static ArmorStats SteelChain = new ArmorStats(6, 1200, 1440, 264, "Steel_Chain");//Tier 3
    public static ArmorStats BlackSteelChain = new ArmorStats(7, 2400, 2160, 528, "BlackSteel_Chain");//Tier 4
    public static ArmorStats BlueSteelChain = new ArmorStats(8, 3000, 2400, 800, "BlueSteel_Chain");//Tier 5
    public static ArmorStats RedSteelChain = new ArmorStats(9, 2400, 3000, 800, "RedSteel_Chain");//Tier 5

    public static ArmorStats CopperChainPlate = new ArmorStats(1, 440, 440, 175, "Copper_ChainPlate");//Tier 0
    public static ArmorStats BismuthBronzeChainPlate = new ArmorStats(2, 660, 440, 231, "BismuthBronze_ChainPlate");//Tier 1
    public static ArmorStats BlackBronzeChainPlate = new ArmorStats(3, 440, 660, 231, "BlackBronze_ChainPlate");//Tier 1
    public static ArmorStats BronzeChainPlate = new ArmorStats(4, 550, 550, 231, "Bronze_ChainPlate");//Tier 1
    public static ArmorStats WroughtIronChainPlate = new ArmorStats(5, 880, 880, 370, "WroughtIron_ChainPlate");//Tier 2
    public static ArmorStats SteelChainPlate = new ArmorStats(6, 1100, 1320, 462, "Steel_ChainPlate");//Tier 3
    public static ArmorStats BlackSteelChainPlate = new ArmorStats(7, 2200, 1980, 924, "BlackSteel_ChainPlate");//Tier 4
    public static ArmorStats BlueSteelChainPlate = new ArmorStats(8, 2750, 2200, 1400, "BlueSteel_ChainPlate");//Tier 5
    public static ArmorStats RedSteelChainPlate = new ArmorStats(9, 2200, 2750, 1400, "RedSteel_ChainPlate");//Tier 5

    private int armorRatingPiercing;
    private int armorRatingSlashing;
    private int armorRatingCrushing;
    public String metaltype;
    public int[] baseDurability = new int[]{2500, 3750, 3000, 2500, 0};//Helm,Chest,Legs,Boots,back
    public int armorId;

    public ArmorStats(int id, int ARP, int ARS, int ARC, String material) {
        armorArrayStats[id] = this;
        armorId = id;
        armorRatingPiercing = ARP;
        armorRatingSlashing = ARS;
        armorRatingCrushing = ARC;
        metaltype = material;
    }

    public ArmorStats(int id, int ARP, int ARS, int ARC, int[] dura, String material) {
        armorArrayStats[id] = this;
        armorId = id;
        armorRatingPiercing = ARP;
        armorRatingSlashing = ARS;
        armorRatingCrushing = ARC;
        metaltype = material;
        baseDurability = dura;
    }

    public int getDurability(int slot) {
        return baseDurability[slot];
    }

    public int getPiercingAR() {
        return armorRatingPiercing;
    }

    public int getSlashingAR() {
        return armorRatingSlashing;
    }

    public int getCrushingAR() {
        return armorRatingCrushing;
    }
}