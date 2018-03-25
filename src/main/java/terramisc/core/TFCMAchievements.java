package terramisc.core;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.Core.TFC_Achievements;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class TFCMAchievements 
{
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
	
	public static void initAchievements()
	{
		achlist = new ArrayList<Achievement>();
		
		achHalberd = createAchievement("achievement.achHalberd", "HalberdAchievement", 0, 0, new ItemStack(TFCMItems.itemHalberd_Copper), TFC_Achievements.achCopperAge);
		achWarHammer = createAchievement("achievement.achWarHammer", "WarHammerAchievement", 0, 1, new ItemStack(TFCMItems.itemWarHammer_Copper), TFC_Achievements.achCopperAge);
		achPoniard = createAchievement("achievement.achPoniard", "PoniardAchievement", 0, 2, new ItemStack(TFCMItems.itemPoniard_Copper), TFC_Achievements.achCopperAge);
		
		achSinew = createAchievement("achievement.achSinew", "SinewAchievement", 3, 0, new ItemStack(TFCMItems.itemDeerTendon), null);
		achLongbow = createAchievement("achievement.achLongbow", "LongbowAchievement", 2, 1, new ItemStack(TFCMItems.itemLongBow), achSinew);
		achCrossbow = createAchievement("achievement.achCrossbow", "CrossbowAchievement", 4, 1, new ItemStack(TFCMItems.itemCrossBow), achSinew);
		
		achChainArmor = createAchievement("achievement.achChainArmor", "ChainArmorAchievement", 0, 3, new ItemStack(TFCMItems.CopperChainChestplate), TFC_Achievements.achCopperAge);
		
		achSuet = createAchievement("achievement.achSuet", "SuetAchievement", 6, 0, new ItemStack(TFCMItems.itemSuet), null);
		achCandle = createAchievement("achievement.achCandle", "CandleAchievement", 6, 2, new ItemStack(TFCMBlocks.blockTallowCandle), achSuet);
		
		achRedstoneTiles = createAchievement("achievement.achRedstoneTiles", "RedstoneTileAchievement", 8, 0, new ItemStack(TFCMItems.itemCircuit), null);
		achPiston = createAchievement("achievement.achPistone", "PistonAchievement", 8, 1, new ItemStack(Blocks.piston), null);
		
		achFruitPress = createAchievement("achievement.achFruitPress", "FruitPressAchievement", 0, 5, new ItemStack(TFCMBlocks.blockFruitPress), null);
		achWine = createAchievement("achievement.achWine", "WineAchievement", 0, 7, new ItemStack(TFCMItems.itemBottleFruitWine), achFruitPress);
		achSugar = createAchievement("achievement.achSugar", "SugarAchievement", 1, 7, new ItemStack(TFCMItems.itemBottleSugar), achFruitPress);
		achJuiceOrange = createAchievement("achievement.achJuiceOrange", "JuiceOrangeAchievement", 2, 7, new ItemStack(TFCMItems.itemBottleJuiceOrange), achFruitPress);
		achJuiceLemon = createAchievement("achievement.achJuiceLemon", "JuiceLemonAchievement", -1, 7, new ItemStack(TFCMItems.itemBottleJuiceLemon), achFruitPress);
		achLemonade = createAchievement("achievement.achLemonade", "LemonadeAchievement", -1, 8, new ItemStack(TFCMItems.itemBottleLemonade), achJuiceLemon);
		
		achievementsTFCM = new Achievement[achlist.size()];
		achievementsTFCM = achlist.toArray(achievementsTFCM);
		TerraMiscPage = new AchievementPage("TerraMisc", achievementsTFCM);
		AchievementPage.registerAchievementPage(TerraMiscPage);
	}
	
	private static Achievement createAchievement(String id, String name, int posX, int posY, ItemStack is, Achievement preReq)
	{
		Achievement a = new Achievement(id, name, posX, posY, is, preReq).registerStat();
		achlist.add(a);
		return a;
	}
}
