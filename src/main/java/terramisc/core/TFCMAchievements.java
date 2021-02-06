package terramisc.core;

import com.dunk.tfc.Core.TFC_Achievements;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import java.util.ArrayList;
import java.util.List;

public class TFCMAchievements {
    public static AchievementPage TerraMiscPage;
    private static List<Achievement> achlist;
    public static Achievement[] achievementsTFCM;

    public static Achievement achHalberd;
    public static Achievement achWarHammer;
    public static Achievement achPoniard;

    public static Achievement achSinew;
    public static Achievement achLongbow;
    public static Achievement achCrossbow;

    public static Achievement achChainArmor;

    public static Achievement achSuet;
    public static Achievement achCandle;

    public static Achievement achRedstoneTiles;
    public static Achievement achPiston;

    public static Achievement achFruitPress;
    public static Achievement achWine;
    public static Achievement achSugar;
    public static Achievement achJuiceOrange;
    public static Achievement achJuiceLemon;
    public static Achievement achLemonade;

    public static void initAchievements() {
        achlist = new ArrayList<Achievement>();

        achHalberd = createAchievement("achievement.achHalberd", "HalberdAchievement", 0, 0, new ItemStack(TFCMItems.halberd_Copper), TFC_Achievements.achCopperAge);
        achWarHammer = createAchievement("achievement.achWarHammer", "WarHammerAchievement", 0, 1, new ItemStack(TFCMItems.warHammer_Copper), TFC_Achievements.achCopperAge);
        achPoniard = createAchievement("achievement.achPoniard", "PoniardAchievement", 0, 2, new ItemStack(TFCMItems.poniard_Copper), TFC_Achievements.achCopperAge);

        achLongbow = createAchievement("achievement.achLongbow", "LongbowAchievement", 2, 1, new ItemStack(TFCMItems.longBow), achSinew);
        achCrossbow = createAchievement("achievement.achCrossbow", "CrossbowAchievement", 4, 1, new ItemStack(TFCMItems.crossBow), achSinew);

        achChainArmor = createAchievement("achievement.achChainArmor", "ChainArmorAchievement", 0, 3, new ItemStack(TFCMItems.CopperChainChestplate), TFC_Achievements.achCopperAge);

        achSuet = createAchievement("achievement.achSuet", "SuetAchievement", 6, 0, new ItemStack(TFCMItems.suet), null);
        achCandle = createAchievement("achievement.achCandle", "CandleAchievement", 6, 2, new ItemStack(TFCMBlocks.blockTallowCandle), achSuet);

        achRedstoneTiles = createAchievement("achievement.achRedstoneTiles", "RedstoneTileAchievement", 8, 0, new ItemStack(TFCMItems.circuit), null);
        achPiston = createAchievement("achievement.achPistone", "PistonAchievement", 8, 1, new ItemStack(Blocks.piston), null);

        achievementsTFCM = new Achievement[achlist.size()];
        achievementsTFCM = achlist.toArray(achievementsTFCM);
        TerraMiscPage = new AchievementPage("TerraMisc", achievementsTFCM);
        AchievementPage.registerAchievementPage(TerraMiscPage);
    }

    private static Achievement createAchievement(String id, String name, int posX, int posY, ItemStack is, Achievement preReq) {
        Achievement a = new Achievement(id, name, posX, posY, is, preReq);
        //a.registerStat();
        achlist.add(a);
        return a;
    }
}
