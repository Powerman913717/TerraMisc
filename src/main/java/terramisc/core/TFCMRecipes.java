package terramisc.core;

import java.util.List;
import java.util.Map;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRaw;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;
import com.bioxx.tfc.api.Crafting.BarrelAlcoholRecipe;
import com.bioxx.tfc.api.Crafting.BarrelLiquidToLiquidRecipe;
import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelRecipe;
import com.bioxx.tfc.api.Crafting.CraftingManagerTFC;
import com.bioxx.tfc.api.Crafting.KilnCraftingManager;
import com.bioxx.tfc.api.Crafting.KilnRecipe;
import com.bioxx.tfc.api.Crafting.PlanRecipe;
import com.bioxx.tfc.api.Crafting.QuernManager;
import com.bioxx.tfc.api.Crafting.QuernRecipe;
import com.bioxx.tfc.api.Enums.RuleEnum;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import terramisc.api.crafting.FruitPressManager;
import terramisc.api.crafting.FruitPressRecipe;

public class TFCMRecipes
{	
	public static final int WILDCARD_VALUE = OreDictionary.WILDCARD_VALUE;
	
	private static AnvilManager anvilManager = AnvilManager.getInstance();
	//private static BarrelManager barrelManager = BarrelManager.getInstance();
	private static CraftingManagerTFC craftingManager = CraftingManagerTFC.getInstance();
	//private static KilnCraftingManager kilnCraftingManager = KilnCraftingManager.getInstance();
	//private static QuernManager quernManager = QuernManager.getInstance();
	private static HeatRegistry heatmanager = HeatRegistry.getInstance();

	// Plan values
	public static String PlanNameCasing = "casing";
	public static String PlanNameClockGear= "clockgear";
	public static String PlanNameCircuit= "circuit";
	public static String PlanNameHalberd= "halberd";
	public static String PlanNameWarHammer= "warhammer";
	public static String PlanNamePoniard= "poniard";
	public static String PlanNameCoil= "coil";
	public static String PlanNameLink= "link";
	public static String PlanNameBolt= "bolt";
	public static String PlanNameArrow= "arrow";

	public static void initialise()
	{
		System.out.println("[" + TFCMDetails.ModName + "] Registering Recipes");
		
		registerRecipes();
		registerBarrelRecipes();
		registerFruitPressRecipes();
		registerKilnRecipes();
		registerToolMolds();
		registerKnappingRecipes();
		registerQuernRecipes();
		registerHeatingRecipes();
		
		System.out.println("[" + TFCMDetails.ModName + "] Done Registering Recipes");
	}

	public static void initialiseAnvil()
	{
		// check if the plans/recipes have already been initialized.
		if (TFCMRecipes.areAnvilRecipesInitialised()) return;
		
		System.out.println("[" + TFCMDetails.ModName + "] Registering Anvil Recipes");

		registerAnvilPlans();
		registerAnvilRecipes();
		
		System.out.println("[" + TFCMDetails.ModName + "] Done Registering Anvil Recipes");
	}
	 
	public static boolean areAnvilRecipesInitialised() 
	{ 
	        Map<String, PlanRecipe> map = anvilManager.getPlans(); 
	        
	        return map != null && ( map.containsKey(PlanNameCasing ) || 
							        map.containsKey(PlanNameClockGear) ||
							        map.containsKey(PlanNameCircuit) ||
							        map.containsKey(PlanNameHalberd) ||
							        map.containsKey(PlanNameWarHammer) ||
							        map.containsKey(PlanNamePoniard) ||
							        map.containsKey(PlanNameCoil) ||
							        map.containsKey(PlanNameLink) ||
							        map.containsKey(PlanNameBolt) ||
							        map.containsKey(PlanNameArrow)
	        						); 
	} 
	 
	private static void registerAnvilPlans()
	{
		//Plans
		anvilManager.addPlan(PlanNameCasing , new PlanRecipe(new RuleEnum[] {RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST}));
		anvilManager.addPlan(PlanNameClockGear, new PlanRecipe(new RuleEnum[] {RuleEnum.PUNCHLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.UPSETTHIRDFROMLAST}));
		anvilManager.addPlan(PlanNameCircuit, new PlanRecipe(new RuleEnum[] {RuleEnum.PUNCHLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.PUNCHTHIRDFROMLAST}));
		anvilManager.addPlan(PlanNameHalberd, new PlanRecipe(new RuleEnum[] {RuleEnum.BENDLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST}));
		anvilManager.addPlan(PlanNameWarHammer, new PlanRecipe(new RuleEnum[] {RuleEnum.BENDLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST}));
		anvilManager.addPlan(PlanNamePoniard, new PlanRecipe(new RuleEnum[] {RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.ANY}));
		anvilManager.addPlan(PlanNameCoil, new PlanRecipe(new RuleEnum[] {RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.DRAWTHIRDFROMLAST}));
		anvilManager.addPlan(PlanNameLink, new PlanRecipe(new RuleEnum[] {RuleEnum.PUNCHLAST, RuleEnum.ANY, RuleEnum.ANY}));
		anvilManager.addPlan(PlanNameBolt, new PlanRecipe(new RuleEnum[] {RuleEnum.HITLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.ANY}));
		anvilManager.addPlan(PlanNameArrow, new PlanRecipe(new RuleEnum[] {RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.PUNCHTHIRDFROMLAST}));
				
	}
	
	private static void registerAnvilRecipes()
	{	
		//Recipes
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.brassIngot), null, PlanNameCasing , AnvilReq.COPPER, new ItemStack(TFCMItems.casingBrass, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, PlanNameCasing , AnvilReq.WROUGHTIRON, new ItemStack(TFCMItems.casingIron, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.roseGoldIngot), null, PlanNameCasing , AnvilReq.COPPER, new ItemStack(TFCMItems.casingRoseGold, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, PlanNameClockGear, AnvilReq.WROUGHTIRON, new ItemStack(TFCMItems.gear, 2)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		
		if(TFCMOptions.enableCraftingLogicTiles = true)
		{
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.goldSheet), new ItemStack(Items.redstone), PlanNameCircuit, AnvilReq.COPPER, new ItemStack(TFCMItems.circuit, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		}
		if(TFCMOptions.enableCraftingCrossbow = true)
		{
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, PlanNameBolt, AnvilReq.BRONZE, new ItemStack(TFCMItems.bolt_BismuthBronze_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, PlanNameBolt, AnvilReq.BRONZE, new ItemStack(TFCMItems.bolt_BlackBronze_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, PlanNameBolt, AnvilReq.BLACKSTEEL, new ItemStack(TFCMItems.bolt_BlackSteel_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, PlanNameBolt, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.bolt_BlueSteel_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, PlanNameBolt, AnvilReq.BRONZE, new ItemStack(TFCMItems.bolt_Bronze_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, PlanNameBolt, AnvilReq.COPPER, new ItemStack(TFCMItems.bolt_Copper_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, PlanNameBolt, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.bolt_RedSteel_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, PlanNameBolt, AnvilReq.STEEL, new ItemStack(TFCMItems.bolt_Steel_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, PlanNameBolt, AnvilReq.WROUGHTIRON, new ItemStack(TFCMItems.bolt_WroughtIron_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		}
		if(TFCMOptions.enableCraftingLongbow = true)
		{
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, PlanNameArrow, AnvilReq.BRONZE, new ItemStack(TFCMItems.arrow_BismuthBronze_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, PlanNameArrow, AnvilReq.BRONZE, new ItemStack(TFCMItems.arrow_BlackBronze_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, PlanNameArrow, AnvilReq.BLACKSTEEL, new ItemStack(TFCMItems.arrow_BlackSteel_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, PlanNameArrow, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.arrow_BlueSteel_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, PlanNameArrow, AnvilReq.BRONZE, new ItemStack(TFCMItems.arrow_Bronze_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, PlanNameArrow, AnvilReq.COPPER, new ItemStack(TFCMItems.arrow_Copper_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, PlanNameArrow, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.arrow_RedSteel_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, PlanNameArrow, AnvilReq.STEEL, new ItemStack(TFCMItems.arrow_Steel_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, PlanNameArrow, AnvilReq.WROUGHTIRON, new ItemStack(TFCMItems.arrow_WroughtIron_Head, 4)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		}
			//Halberds
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot2x), null, PlanNameHalberd, AnvilReq.BRONZE, new ItemStack(TFCMItems.halberd_BismuthBronze_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot2x), null, PlanNameHalberd, AnvilReq.BRONZE, new ItemStack(TFCMItems.halberd_BlackBronze_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot2x), null, PlanNameHalberd, AnvilReq.BLACKSTEEL, new ItemStack(TFCMItems.halberd_BlackSteel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot2x), null, PlanNameHalberd, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.halberd_BlueSteel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot2x), null, PlanNameHalberd, AnvilReq.BRONZE, new ItemStack(TFCMItems.halberd_Bronze_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot2x), null, PlanNameHalberd, AnvilReq.COPPER, new ItemStack(TFCMItems.halberd_Copper_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot2x), null, PlanNameHalberd, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.halberd_RedSteel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot2x), null, PlanNameHalberd, AnvilReq.STEEL, new ItemStack(TFCMItems.halberd_Steel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot2x), null, PlanNameHalberd, AnvilReq.WROUGHTIRON, new ItemStack(TFCMItems.halberd_WroughtIron_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			//War Hammers
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot2x), null, PlanNameWarHammer, AnvilReq.BRONZE, new ItemStack(TFCMItems.warHammer_BismuthBronze_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot2x), null, PlanNameWarHammer, AnvilReq.BRONZE, new ItemStack(TFCMItems.warHammer_BlackBronze_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot2x), null, PlanNameWarHammer, AnvilReq.BLACKSTEEL, new ItemStack(TFCMItems.warHammer_BlackSteel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot2x), null, PlanNameWarHammer, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.warHammer_BlueSteel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot2x), null, PlanNameWarHammer, AnvilReq.BRONZE, new ItemStack(TFCMItems.warHammer_Bronze_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot2x), null, PlanNameWarHammer, AnvilReq.COPPER, new ItemStack(TFCMItems.warHammer_Copper_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot2x), null, PlanNameWarHammer, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.warHammer_RedSteel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot2x), null, PlanNameWarHammer, AnvilReq.STEEL, new ItemStack(TFCMItems.warHammer_Steel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot2x), null, PlanNameWarHammer, AnvilReq.WROUGHTIRON, new ItemStack(TFCMItems.warHammer_WroughtIron_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			//Poniards
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, PlanNamePoniard, AnvilReq.BRONZE, new ItemStack(TFCMItems.poniard_BismuthBronze_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, PlanNamePoniard, AnvilReq.BRONZE, new ItemStack(TFCMItems.poniard_BlackBronze_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, PlanNamePoniard, AnvilReq.BLACKSTEEL, new ItemStack(TFCMItems.poniard_BlackSteel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, PlanNamePoniard, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.poniard_BlueSteel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, PlanNamePoniard, AnvilReq.BRONZE, new ItemStack(TFCMItems.poniard_Bronze_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, PlanNamePoniard, AnvilReq.COPPER, new ItemStack(TFCMItems.poniard_Copper_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, PlanNamePoniard, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.poniard_RedSteel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, PlanNamePoniard, AnvilReq.STEEL, new ItemStack(TFCMItems.poniard_Steel_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, PlanNamePoniard, AnvilReq.WROUGHTIRON, new ItemStack(TFCMItems.poniard_WroughtIron_Head, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
			//Coils
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, PlanNameCoil, AnvilReq.BRONZE, new ItemStack(TFCMItems.coil_BismuthBronze, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, PlanNameCoil, AnvilReq.BRONZE, new ItemStack(TFCMItems.coil_BlackBronze, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, PlanNameCoil, AnvilReq.BLACKSTEEL, new ItemStack(TFCMItems.coil_BlackSteel, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, PlanNameCoil, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.coil_BlueSteel, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, PlanNameCoil, AnvilReq.BRONZE, new ItemStack(TFCMItems.coil_Bronze, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, PlanNameCoil, AnvilReq.COPPER, new ItemStack(TFCMItems.coil_Copper, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, PlanNameCoil, AnvilReq.WROUGHTIRON, new ItemStack(TFCMItems.coil_WroughtIron, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, PlanNameCoil, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.coil_RedSteel, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, PlanNameCoil, AnvilReq.STEEL, new ItemStack(TFCMItems.coil_Steel, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
			//Links
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCMItems.coil_BismuthBronze), null, PlanNameLink, AnvilReq.BRONZE, new ItemStack(TFCMItems.link_BismuthBronze, 16)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCMItems.coil_BlackBronze), null, PlanNameLink, AnvilReq.BRONZE, new ItemStack(TFCMItems.link_BlackBronze, 16)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCMItems.coil_BlackSteel), null, PlanNameLink, AnvilReq.BLACKSTEEL, new ItemStack(TFCMItems.link_BlackSteel, 16)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCMItems.coil_BlueSteel), null, PlanNameLink, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.link_BlueSteel, 16)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCMItems.coil_Bronze), null, PlanNameLink, AnvilReq.BRONZE, new ItemStack(TFCMItems.link_Bronze, 16)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCMItems.coil_Copper), null, PlanNameLink, AnvilReq.COPPER, new ItemStack(TFCMItems.link_Copper, 16)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCMItems.coil_WroughtIron), null, PlanNameLink, AnvilReq.WROUGHTIRON, new ItemStack(TFCMItems.link_WroughtIron, 16)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCMItems.coil_RedSteel), null, PlanNameLink, AnvilReq.BLUESTEEL, new ItemStack(TFCMItems.link_RedSteel, 16)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		anvilManager.addRecipe(new AnvilRecipe(new ItemStack(TFCMItems.coil_Steel), null, PlanNameLink, AnvilReq.STEEL, new ItemStack(TFCMItems.link_Steel, 16)).addRecipeSkill(Global.SKILL_ARMORSMITH));
	}
	 
		
	private static void registerRecipes()
	{
		//Item ItemStacks
		ItemStack smallMagnetite = new ItemStack(TFCItems.smallOreChunk, 1, 10);   // 10 unit ore
		ItemStack Gravel1 = new ItemStack(TFCBlocks.gravel, 1, WILDCARD_VALUE); //Used Since Gravel Ore Dictionary Support Doesn't Exist.
		ItemStack Gravel2 = new ItemStack(TFCBlocks.gravel2, 1, WILDCARD_VALUE);
		ItemStack Spring = new ItemStack(TFCMItems.coil_WroughtIron, 1);
		ItemStack CeramicBowl = new ItemStack(TFCItems.potteryBowl, 1, 1);
		ItemStack TallowBowl = new ItemStack(TFCMItems.bowlTallow, 1, WILDCARD_VALUE);
		
		if(TFCMOptions.enableCraftingPiston = true)
		{
			removeRecipe(new ItemStack(Blocks.piston));
			
			GameRegistry.addRecipe(new ItemStack(Blocks.piston), new Object[]{"H","S","B", 'H',TFCItems.wroughtIronSheet, 'S', TFCItems.tuyereWroughtIron, 'B', TFCMItems.pistonBase});
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sticky_piston), new ItemStack(Blocks.piston), new ItemStack(TFCMItems.caseinGlue));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMItems.pistonBase), new Object[]{"PRP","PPP", Character.valueOf('P'), "woodLumber", Character.valueOf('R'), Items.redstone}));
		}
		
		if(TFCMOptions.enableCraftingLogicTiles = true)
		{
			removeRecipe(new ItemStack(Items.repeater));
			removeRecipe(new ItemStack(Items.comparator));
			
			GameRegistry.addRecipe(new ItemStack(Items.repeater), new Object[]{"TCT", 'C', TFCMItems.circuit, 'T', Blocks.redstone_torch});
			GameRegistry.addRecipe(new ItemStack(Items.comparator), new Object[]{" T ","TCT", 'C', TFCMItems.circuit, 'T', Blocks.redstone_torch});
		}
		
		if(TFCMOptions.enableCraftingLongbow = true)
		{
			//Longbow
			GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.longBow), new ItemStack(TFCMItems.bowLimb), new ItemStack(TFCMItems.sinewBowString));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.longBow), new ItemStack(TFCMItems.caseinGlue), new ItemStack(TFCMItems.longBow, 1, WILDCARD_VALUE),"woodLumber"));
			//Arrows
			GameRegistry.addRecipe(new ItemStack(TFCMItems.arrow_BismuthBronze), new Object[]{"H","S","F",'H', TFCMItems.arrow_BismuthBronze_Head, 'S', TFCItems.stick, 'F', Items.feather});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.arrow_BlackBronze), new Object[]{"H","S","F", 'H', TFCMItems.arrow_BlackBronze_Head, 'S', TFCItems.stick, 'F', Items.feather});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.arrow_BlackSteel), new Object[]{"H","S","F", 'H', TFCMItems.arrow_BlackSteel_Head, 'S', TFCItems.stick, 'F', Items.feather});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.arrow_BlueSteel), new Object[]{"H","S","F", 'H', TFCMItems.arrow_BlueSteel_Head, 'S', TFCItems.stick, 'F', Items.feather});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.arrow_Bronze), new Object[]{"H","S","F", 'H', TFCMItems.arrow_Bronze_Head, 'S', TFCItems.stick, 'F', Items.feather});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.arrow_Copper), new Object[]{"H","S","F", 'H', TFCMItems.arrow_Copper_Head, 'S', TFCItems.stick, 'F', Items.feather});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.arrow_RedSteel), new Object[]{"H","S","F", 'H', TFCMItems.arrow_RedSteel_Head, 'S', TFCItems.stick, 'F', Items.feather});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.arrow_Steel), new Object[]{"H","S","F", 'H', TFCMItems.arrow_Steel_Head, 'S', TFCItems.stick, 'F', Items.feather});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.arrow_WroughtIron), new Object[]{"H","S","F", 'H', TFCMItems.arrow_WroughtIron_Head, 'S', TFCItems.stick, 'F', Items.feather});
			//Molds
			GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.arrow_Copper_Head, 4), new ItemStack(TFCMItems.arrow_Mold, 1, 2));
			GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.arrow_Bronze_Head, 4), new ItemStack(TFCMItems.arrow_Mold, 1, 3));
			GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.arrow_BismuthBronze_Head, 4), new ItemStack(TFCMItems.arrow_Mold, 1, 4));
			GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.arrow_BlackBronze_Head, 4), new ItemStack(TFCMItems.arrow_Mold, 1, 5));
		}
		if(TFCMOptions.enableCraftingCrossbow = true)
		{
			//Crossbow
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMItems.crossBow), new Object[]{"LBL","SGS","TLT", 'L', "woodLumber", 'B', TFCMItems.bowLimb, 'T', TFCItems.leather, 'S', TFCMItems.sinewString, 'G', TFCMItems.gear}));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.crossBow), new ItemStack(TFCMItems.caseinGlue), new ItemStack(TFCMItems.crossBow, 1, WILDCARD_VALUE),"woodLumber"));
			//Bolts
			GameRegistry.addRecipe(new ItemStack(TFCMItems.bolt_BismuthBronze), new Object[]{"H","S", 'H', TFCMItems.bolt_BismuthBronze_Head, 'S', TFCItems.stick});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.bolt_BlackBronze), new Object[]{"H","S", 'H', TFCMItems.bolt_BlackBronze_Head, 'S', TFCItems.stick});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.bolt_BlackSteel), new Object[]{"H","S", 'H', TFCMItems.bolt_BlackSteel_Head, 'S', TFCItems.stick});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.bolt_BlueSteel), new Object[]{"H","S", 'H', TFCMItems.bolt_BlueSteel_Head, 'S', TFCItems.stick});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.bolt_Bronze), new Object[]{"H","S", 'H', TFCMItems.bolt_Bronze_Head, 'S', TFCItems.stick});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.bolt_Copper), new Object[]{"H","S", 'H', TFCMItems.bolt_Copper_Head, 'S', TFCItems.stick});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.bolt_RedSteel), new Object[]{"H","S", 'H', TFCMItems.bolt_RedSteel_Head, 'S', TFCItems.stick});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.bolt_Steel), new Object[]{"H","S", 'H', TFCMItems.bolt_Steel_Head, 'S', TFCItems.stick});
			GameRegistry.addRecipe(new ItemStack(TFCMItems.bolt_WroughtIron), new Object[]{"H","S", 'H', TFCMItems.bolt_WroughtIron_Head, 'S', TFCItems.stick});
			//Molds
			GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.bolt_Copper_Head, 4), new ItemStack(TFCMItems.bolt_Mold, 1, 2));
			GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.bolt_Bronze_Head, 4), new ItemStack(TFCMItems.bolt_Mold, 1, 3));
			GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.bolt_BismuthBronze_Head, 4), new ItemStack(TFCMItems.bolt_Mold, 1, 4));
			GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.bolt_BlackBronze_Head, 4), new ItemStack(TFCMItems.bolt_Mold, 1, 5));
		}
		if(TFCMOptions.enableCraftingLongbow || TFCMOptions.enableCraftingCrossbow)
		{
			for(int j = 0; j < Recipes.knives.length; j++)
			{
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.bowLimb), new ItemStack(Recipes.knives[j], 1, 32767), "logWood"));
			}
		}
		
		if(TFCMOptions.enableCrucibleEmptying = true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(TFCBlocks.crucible), new ItemStack(TFCBlocks.crucible));
		}
		
		if(TFCMOptions.enableCraftingCompassClock = true)
		{
			removeRecipe(new ItemStack(Items.clock));
			removeRecipe(new ItemStack(Items.compass));
			
			GameRegistry.addRecipe(new ItemStack(Items.clock), new Object[]{"GPG","HCH","GSG", 'G', TFCMItems.gear, 'P', Blocks.glass_pane, 'H', TFCItems.stick, 'C', TFCMItems.casingBrass, 'S', Spring});
			GameRegistry.addRecipe(new ItemStack(Items.clock), new Object[]{"GPG","HCH","GSG", 'G', TFCMItems.gear, 'P', Blocks.glass_pane, 'H', TFCItems.stick, 'C', TFCMItems.casingRoseGold, 'S', Spring});
			GameRegistry.addRecipe(new ItemStack(Items.compass), new Object[]{"P","O","C", 'P', Blocks.glass_pane, 'O', smallMagnetite, 'C', TFCMItems.casingIron});	
		}
		
		//Shaped
		GameRegistry.addRecipe(new ItemStack(TFCMItems.sinewString), new Object[]{"F","F","F", 'F', TFCMItems.sinewFiber});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.sinewBowString), new Object[]{"SSS", 'S', TFCMItems.sinewString});
		GameRegistry.addRecipe(new ItemStack(TFCMBlocks.blockTallowCandleOff), new Object[]{"W","T", 'W', TFCItems.woolYarn, 'T', TallowBowl});
			//Clay
		GameRegistry.addRecipe(new ItemStack(TFCMBlocks.blockClay), new Object[]{"CC", "CC", 'C', TFCItems.clayBall});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 1), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeBlack"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 2), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeRed"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 3), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeGreen"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 4), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeBrown"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 5), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeBlue"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 6), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyePurple"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 7), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeCyan"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 8), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeSilver"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 9), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeGray"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 10), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyePink"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 11), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeLime"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 12), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeYellow"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 13), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeLightBlue"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 14), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeMagenta"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCMBlocks.blockStainedClay, 8, 15), new Object[]{"CCC", "CDC", "CCC", 'C', TFCMBlocks.blockStainedClay, 'D', "dyeOrange"}));
		
			//Shapeless candle dyes
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 0), "dyeBlack", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 1), "dyeRed", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 2), "dyeGreen", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 3), "dyeBrown", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 4), "dyeBlue", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 5), "dyePurple", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 6), "dyeCyan", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 7), "dyeSilver", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 8), "dyeGray", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 9), "dyePink", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 10), "dyeLime", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 11), "dyeYellow", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 12), "dyeLightBlue", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 13), "dyeMagenta", TallowBowl));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCMItems.tallowDye, 1, 14), "dyeOrange", TallowBowl));
			//Halberds
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_BismuthBronze), new Object[]{"H  "," S ","  S", 'H', TFCMItems.halberd_BismuthBronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_BismuthBronze), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.halberd_BismuthBronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_BlackBronze), new Object[]{"H  "," S ","  S", 'H', TFCMItems.halberd_BlackBronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_BlackBronze), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.halberd_BlackBronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_BlackSteel), new Object[]{"H  "," S ","  S", 'H', TFCMItems.halberd_BlackSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_BlackSteel), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.halberd_BlackSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_BlueSteel), new Object[]{"H  "," S ","  S", 'H', TFCMItems.halberd_BlueSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_BlueSteel), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.halberd_BlueSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_Bronze), new Object[]{"H  "," S ","  S", 'H', TFCMItems.halberd_Bronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_Bronze), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.halberd_Bronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_Copper), new Object[]{"H  "," S ","  S", 'H', TFCMItems.halberd_Copper_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_Copper), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.halberd_Copper_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_RedSteel), new Object[]{"H  "," S ","  S", 'H', TFCMItems.halberd_RedSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_RedSteel), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.halberd_RedSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_Steel), new Object[]{"H  "," S ","  S", 'H', TFCMItems.halberd_Steel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_Steel), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.halberd_Steel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_WroughtIron), new Object[]{"H  "," S ","  S", 'H', TFCMItems.halberd_WroughtIron_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.halberd_WroughtIron), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.halberd_WroughtIron_Head, 'S', TFCItems.stick});
			//War Hammers
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_BismuthBronze), new Object[]{"H  "," S ","  S", 'H', TFCMItems.warHammer_BismuthBronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_BismuthBronze), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.warHammer_BismuthBronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_BlackBronze), new Object[]{"H  "," S ","  S", 'H', TFCMItems.warHammer_BlackBronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_BlackBronze), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.warHammer_BlackBronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_BlackSteel), new Object[]{"H  "," S ","  S", 'H', TFCMItems.warHammer_BlackSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_BlackSteel), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.warHammer_BlackSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_BlueSteel), new Object[]{"H  "," S ","  S", 'H', TFCMItems.warHammer_BlueSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_BlueSteel), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.warHammer_BlueSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_Bronze), new Object[]{"H  "," S ","  S", 'H', TFCMItems.warHammer_Bronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_Bronze), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.warHammer_Bronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_Copper), new Object[]{"H  "," S ","  S", 'H', TFCMItems.warHammer_Copper_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_Copper), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.warHammer_Copper_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_RedSteel), new Object[]{"H  "," S ","  S", 'H', TFCMItems.warHammer_RedSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_RedSteel), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.warHammer_RedSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_Steel), new Object[]{"H  "," S ","  S", 'H', TFCMItems.warHammer_Steel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_Steel), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.warHammer_Steel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_WroughtIron), new Object[]{"H  "," S ","  S", 'H', TFCMItems.warHammer_WroughtIron_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.warHammer_WroughtIron), new Object[]{"  H"," S ","S  ", 'H', TFCMItems.warHammer_WroughtIron_Head, 'S', TFCItems.stick});
			//Poniards
		GameRegistry.addRecipe(new ItemStack(TFCMItems.poniard_BismuthBronze), new Object[]{"H","S", 'H', TFCMItems.poniard_BismuthBronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.poniard_BlackBronze), new Object[]{"H","S", 'H', TFCMItems.poniard_BlackBronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.poniard_BlackSteel), new Object[]{"H","S", 'H', TFCMItems.poniard_BlackSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.poniard_BlueSteel), new Object[]{"H","S", 'H', TFCMItems.poniard_BlueSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.poniard_Bronze), new Object[]{"H","S", 'H', TFCMItems.poniard_Bronze_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.poniard_Copper), new Object[]{"H","S", 'H', TFCMItems.poniard_Copper_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.poniard_RedSteel), new Object[]{"H","S", 'H', TFCMItems.poniard_RedSteel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.poniard_Steel), new Object[]{"H","S", 'H', TFCMItems.poniard_Steel_Head, 'S', TFCItems.stick});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.poniard_WroughtIron), new Object[]{"H","S", 'H', TFCMItems.poniard_WroughtIron_Head, 'S', TFCItems.stick});
		
		//Armor
			//Chain Helmets
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BismuthBronzeChainHelmet), new Object[]{"XOX","X X","   ", 'X', new ItemStack(TFCMItems.chain_Sheet_BismuthBronze), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BlackBronzeChainHelmet), new Object[]{"XOX","X X","   ", 'X', new ItemStack(TFCMItems.chain_Sheet_BlackBronze), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BlackSteelChainHelmet), new Object[]{"XOX","X X","   ", 'X', new ItemStack(TFCMItems.chain_Sheet_BlackSteel), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BlueSteelChainHelmet), new Object[]{"XOX","X X","   ", 'X', new ItemStack(TFCMItems.chain_Sheet_BlueSteel), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BronzeChainHelmet), new Object[]{"XOX","X X","   ", 'X', new ItemStack(TFCMItems.chain_Sheet_Bronze), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.CopperChainHelmet), new Object[]{"XOX","X X","   ", 'X', new ItemStack(TFCMItems.chain_Sheet_Copper), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.WroughtIronChainHelmet), new Object[]{"XOX","X X","   ", 'X', new ItemStack(TFCMItems.chain_Sheet_WroughtIron), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.RedSteelChainHelmet), new Object[]{"XOX","X X","   ", 'X', new ItemStack(TFCMItems.chain_Sheet_RedSteel), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.SteelChainHelmet), new Object[]{"XOX","X X","   ", 'X', new ItemStack(TFCMItems.chain_Sheet_Steel), 'O', TFCItems.leather});
			//Chain Chestplate
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BismuthBronzeChainChestplate), new Object[]{"X X","XOX","XXX", 'X', new ItemStack(TFCMItems.chain_Sheet_BismuthBronze), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BlackBronzeChainChestplate), new Object[]{"X X","XOX","XXX", 'X', new ItemStack(TFCMItems.chain_Sheet_BlackBronze), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BlackSteelChainChestplate), new Object[]{"X X","XOX","XXX", 'X', new ItemStack(TFCMItems.chain_Sheet_BlackSteel), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BlueSteelChainChestplate), new Object[]{"X X","XOX","XXX", 'X', new ItemStack(TFCMItems.chain_Sheet_BlueSteel), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BronzeChainChestplate), new Object[]{"X X","XOX","XXX", 'X', new ItemStack(TFCMItems.chain_Sheet_Bronze), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.CopperChainChestplate), new Object[]{"X X","XOX","XXX", 'X', new ItemStack(TFCMItems.chain_Sheet_Copper), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.WroughtIronChainChestplate), new Object[]{"X X","XOX","XXX", 'X', new ItemStack(TFCMItems.chain_Sheet_WroughtIron), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.RedSteelChainChestplate), new Object[]{"X X","XOX","XXX", 'X', new ItemStack(TFCMItems.chain_Sheet_RedSteel), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.SteelChainChestplate), new Object[]{"X X","XOX","XXX", 'X', new ItemStack(TFCMItems.chain_Sheet_Steel), 'O', TFCItems.leather});
			//Chain Leggings
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BismuthBronzeChainGreaves), new Object[]{"XXX","XOX","X X", 'X', new ItemStack(TFCMItems.chain_Sheet_BismuthBronze), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BlackBronzeChainGreaves), new Object[]{"XXX","XOX","X X", 'X', new ItemStack(TFCMItems.chain_Sheet_BlackBronze), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BlackSteelChainGreaves), new Object[]{"XXX","XOX","X X", 'X', new ItemStack(TFCMItems.chain_Sheet_BlackSteel), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BlueSteelChainGreaves), new Object[]{"XXX","XOX","X X", 'X', new ItemStack(TFCMItems.chain_Sheet_BlueSteel), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.BronzeChainGreaves), new Object[]{"XXX","XOX","X X", 'X', new ItemStack(TFCMItems.chain_Sheet_Bronze), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.CopperChainGreaves), new Object[]{"XXX","XOX","X X", 'X', new ItemStack(TFCMItems.chain_Sheet_Copper), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.WroughtIronChainGreaves), new Object[]{"XXX","XOX","X X", 'X', new ItemStack(TFCMItems.chain_Sheet_WroughtIron), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.RedSteelChainGreaves), new Object[]{"XXX","XOX","X X", 'X', new ItemStack(TFCMItems.chain_Sheet_RedSteel), 'O', TFCItems.leather});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.SteelChainGreaves), new Object[]{"XXX","XOX","X X", 'X', new ItemStack(TFCMItems.chain_Sheet_Steel), 'O', TFCItems.leather});
			//Chain Item Crafting
				//Squares
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Square_BismuthBronze), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.link_BismuthBronze)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Square_BlackBronze), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.link_BlackBronze)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Square_BlackSteel), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.link_BlackSteel)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Square_BlueSteel), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.link_BlueSteel)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Square_Bronze), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.link_Bronze)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Square_Copper), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.link_Copper)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Square_WroughtIron), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.link_WroughtIron)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Square_RedSteel), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.link_RedSteel)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Square_Steel), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.link_Steel)});
				//Sheets
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Sheet_BismuthBronze), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.chain_Square_BismuthBronze)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Sheet_BlackBronze), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.chain_Square_BlackBronze)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Sheet_BlackSteel), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.chain_Square_BlackSteel)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Sheet_BlueSteel), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.chain_Square_BlueSteel)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Sheet_Bronze), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.chain_Square_Bronze)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Sheet_Copper), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.chain_Square_Copper)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Sheet_WroughtIron), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.chain_Square_WroughtIron)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Sheet_RedSteel), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.chain_Square_RedSteel)});
		GameRegistry.addRecipe(new ItemStack(TFCMItems.chain_Sheet_Steel), new Object[]{"XX","XX", 'X', new ItemStack(TFCMItems.chain_Square_Steel)});
		
		//Ore Dictionary
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.ladder, 16), new Object[]{"P P","PSP","P P", Character.valueOf('P'), "woodLumber", Character.valueOf('R'), TFCItems.stick}));
		
		//Shapeless
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.bowlSuet), CeramicBowl, new ItemStack(TFCMItems.suet));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMBlocks.blockFruitPress), new ItemStack(TFCBlocks.hopper));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCBlocks.hopper), new ItemStack(TFCMBlocks.blockFruitPress));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.itemQuiver), new ItemStack(TFCItems.quiver));
		//Dyes
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.dye, 1, 6), new ItemStack(TFCItems.dye, 1, 2), new ItemStack(TFCItems.powder, 1, 6)); //Cyan
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.dye, 1, 7), new ItemStack(TFCItems.dye, 1, 8), new ItemStack(TFCItems.powder, 1, 0), ("blockSand"))); //Light Gray
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.dye, 1, 8), new ItemStack(TFCItems.dye, 1, 0), new ItemStack(TFCItems.powder, 1, 0), ("blockSand"))); //Gray
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.dye, 1, 9), new ItemStack(TFCItems.dye, 1, 1), new ItemStack(TFCItems.powder, 1, 0), ("blockSand"))); //Pink
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.dye, 1, 10), new ItemStack(TFCItems.dye, 1, 2), new ItemStack(TFCItems.dye, 1, 11)); //Lime
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.dye, 1, 13), new ItemStack(TFCItems.dye, 1, 5), new ItemStack(TFCItems.dye, 1, 9)); //Magenta
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.dye, 1, 14), new ItemStack(TFCItems.dye, 1, 1), new ItemStack(TFCItems.dye, 1, 11)); //Orange
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.brownDye), new ItemStack(TFCMItems.ironDust), new ItemStack(TFCItems.coal, 1, WILDCARD_VALUE)); //Brown
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.brownDye), new ItemStack(TFCItems.powder, 1, 5), new ItemStack(TFCItems.coal, 1, WILDCARD_VALUE)); //Brown
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.brownDye), new ItemStack(TFCItems.powder, 1, 7), new ItemStack(TFCItems.coal, 1, WILDCARD_VALUE)); //Brown
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.dye, 1, 0), new ItemStack(TFCMItems.brownDye), new ItemStack(TFCItems.coal, 1, WILDCARD_VALUE)); //Black
		
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.halberd_Copper_Head), new ItemStack(TFCMItems.halberd_Mold, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.halberd_Bronze_Head), new ItemStack(TFCMItems.halberd_Mold, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.halberd_BismuthBronze_Head), new ItemStack(TFCMItems.halberd_Mold, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.halberd_BlackBronze_Head), new ItemStack(TFCMItems.halberd_Mold, 1, 5));
		
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.warHammer_Copper_Head), new ItemStack(TFCMItems.warHammer_Mold, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.warHammer_Bronze_Head), new ItemStack(TFCMItems.warHammer_Mold, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.warHammer_BismuthBronze_Head), new ItemStack(TFCMItems.warHammer_Mold, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.warHammer_BlackBronze_Head), new ItemStack(TFCMItems.warHammer_Mold, 1, 5));
		
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.poniard_Copper_Head), new ItemStack(TFCMItems.poniard_Mold, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.poniard_Bronze_Head), new ItemStack(TFCMItems.poniard_Mold, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.poniard_BismuthBronze_Head), new ItemStack(TFCMItems.poniard_Mold, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.poniard_BlackBronze_Head), new ItemStack(TFCMItems.poniard_Mold, 1, 5));
		
		//Food

		//Road Block Crafting
		for (int j = 0; j < Global.STONE_IGEX.length; j++)
		{
			GameRegistry.addRecipe(new ItemStack(TFCMBlocks.blockRoadIgEx, 8, j), new Object[] { "GSG", "SMS", "GSG", Character.valueOf('S'), new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGEX_START), Character.valueOf('M'), new ItemStack(TFCItems.mortar, 1), Character.valueOf('G'), Gravel1 });
			GameRegistry.addRecipe(new ItemStack(TFCMBlocks.blockRoadIgEx, 8, j), new Object[] { "GSG", "SMS", "GSG", Character.valueOf('S'), new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGEX_START), Character.valueOf('M'), new ItemStack(TFCItems.mortar, 1), Character.valueOf('G'), Gravel2 });
		}
		
		for (int j = 0; j < Global.STONE_IGIN.length; j++)
		{
			GameRegistry.addRecipe(new ItemStack(TFCMBlocks.blockRoadIgIn, 8, j), new Object[] { "GSG", "SMS", "GSG", Character.valueOf('S'), new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGIN_START), Character.valueOf('M'), new ItemStack(TFCItems.mortar, 1), Character.valueOf('G'), Gravel1 });
			GameRegistry.addRecipe(new ItemStack(TFCMBlocks.blockRoadIgIn, 8, j), new Object[] { "GSG", "SMS", "GSG", Character.valueOf('S'), new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGIN_START), Character.valueOf('M'), new ItemStack(TFCItems.mortar, 1), Character.valueOf('G'), Gravel2 });
		}
		
		for (int j = 0; j < Global.STONE_MM.length; j++)
		{
			GameRegistry.addRecipe(new ItemStack(TFCMBlocks.blockRoadMM, 8, j), new Object[] { "GSG", "SMS", "GSG", Character.valueOf('S'), new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_MM_START), Character.valueOf('M'), new ItemStack(TFCItems.mortar, 1), Character.valueOf('G'), Gravel1 });
			GameRegistry.addRecipe(new ItemStack(TFCMBlocks.blockRoadMM, 8, j), new Object[] { "GSG", "SMS", "GSG", Character.valueOf('S'), new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_MM_START), Character.valueOf('M'), new ItemStack(TFCItems.mortar, 1), Character.valueOf('G'), Gravel2 });
		}
		
		for (int j = 0; j < Global.STONE_SED.length; j++)
		{
			GameRegistry.addRecipe(new ItemStack(TFCMBlocks.blockRoadSed, 8, j), new Object[] { "GSG", "SMS", "GSG", Character.valueOf('S'), new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_SED_START), Character.valueOf('M'), new ItemStack(TFCItems.mortar, 1), Character.valueOf('G'), Gravel1 });
			GameRegistry.addRecipe(new ItemStack(TFCMBlocks.blockRoadSed, 8, j), new Object[] { "GSG", "SMS", "GSG", Character.valueOf('S'), new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_SED_START), Character.valueOf('M'), new ItemStack(TFCItems.mortar, 1), Character.valueOf('G'), Gravel2 });
		}
		
		//Knife Recipes
		for(int j = 0; j < Recipes.knives.length; j++)
		{
			//moved bow limb recipe to the bow crafting section
		}
		
		//Hammer Recipes
		for(int j = 0; j < Recipes.hammers.length; j++)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(TFCMItems.sinewFiber, 3), new ItemStack(Recipes.hammers[j], 1, 32767), new ItemStack(TFCMItems.deerTendon));	
		}
	}
	
	//From TFCraft/Recipes.java
	@SuppressWarnings("unchecked")
	public static void removeRecipe(ItemStack resultItem)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			if (recipes.get(i) != null)
			{
				ItemStack recipeResult = recipes.get(i).getRecipeOutput();
			
				if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
					recipes.remove(i--);
			}
		}
	}
	
	//Barrel
	private static void registerBarrelRecipes()
	{
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.powder, 1, 0), new FluidStack(TFCFluids.MILKCURDLED, 100), new ItemStack(TFCMItems.caseinGlue, 2), new FluidStack(TFCFluids.MILKCURDLED, 100), 1).setMinTechLevel(0));
		
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCMItems.soyPaste), 160), new FluidStack(TFCFluids.HOTWATER, 10000), null, new FluidStack(TFCMFluids.SOYMILK, 10000)));	
		BarrelManager.getInstance().addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(TFCMFluids.SOYMILK, 9000), new FluidStack(TFCFluids.VINEGAR, 1000), new FluidStack(TFCFluids.MILKVINEGAR, 10000)).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCMItems.bowlSuet), new FluidStack(TFCFluids.HOTWATER, 1000), new ItemStack(TFCMItems.bowlTallow), new FluidStack(TFCFluids.FRESHWATER, 1000), 2));
	}
	
	//Fruit Press
	private static void registerFruitPressRecipes()
	{
		//Fruit Juice
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.cherry, TFCMFluids.REDFRUITJUICE, 4));
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.plum, TFCMFluids.REDFRUITJUICE, 4));
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.blueberry, TFCMFluids.REDFRUITJUICE, 8));
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.raspberry, TFCMFluids.REDFRUITJUICE, 8));
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.strawberry, TFCMFluids.REDFRUITJUICE, 8));
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.blackberry, TFCMFluids.REDFRUITJUICE, 8));
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.cranberry, TFCMFluids.REDFRUITJUICE, 8));
		
		//Apple Juice
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.redApple, TFCMFluids.JUICEAPPLE, 4));
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.greenApple, TFCMFluids.JUICEAPPLE, 4));

		//Lemon Juice
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.lemon, TFCMFluids.JUICELEMON, 4));
		
		//Other
		FruitPressManager.addRecipe(new FruitPressRecipe(TFCItems.olive, TFCFluids.OLIVEOIL, 1));
	}
	
	//Kiln
	private static void registerKilnRecipes()
	{
		KilnCraftingManager.getInstance().addRecipe(
		new KilnRecipe(
		new ItemStack(TFCMItems.halberd_Mold,1,0),
		0, 
		new ItemStack(TFCMItems.halberd_Mold,1,1)));
		
		KilnCraftingManager.getInstance().addRecipe(
		new KilnRecipe(
		new ItemStack(TFCMItems.warHammer_Mold,1,0),
		0, 
		new ItemStack(TFCMItems.warHammer_Mold,1,1)));	
		
		KilnCraftingManager.getInstance().addRecipe(
		new KilnRecipe(
		new ItemStack(TFCMItems.poniard_Mold,1,0),
		0, 
		new ItemStack(TFCMItems.poniard_Mold,1,1)));	
		
		KilnCraftingManager.getInstance().addRecipe(
		new KilnRecipe(
		new ItemStack(TFCMItems.arrow_Mold,1,0),
		0, 
		new ItemStack(TFCMItems.arrow_Mold,1,1)));	
		
		KilnCraftingManager.getInstance().addRecipe(
		new KilnRecipe(
		new ItemStack(TFCMItems.bolt_Mold,1,0),
		0, 
		new ItemStack(TFCMItems.bolt_Mold,1,1)));
	}
	
	//Mold Pouring
	public static ItemStack getItemStackTemp(ItemStack is)
	{
		NBTTagCompound Temp = new NBTTagCompound();
		Temp.setBoolean("temp", true);
		is.setTagCompound(Temp);
		return is;
	}
			
			
	private static void registerToolMolds()
	{
		craftingManager.addRecipe(new ItemStack(TFCMItems.halberd_Mold, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.halberd_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.halberd_Mold, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.halberd_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.halberd_Mold, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.halberd_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.halberd_Mold, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.halberd_Mold, 1, 1)});
		
		craftingManager.addRecipe(new ItemStack(TFCMItems.warHammer_Mold, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.warHammer_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.warHammer_Mold, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.warHammer_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.warHammer_Mold, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.warHammer_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.warHammer_Mold, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.warHammer_Mold, 1, 1)});
		
		craftingManager.addRecipe(new ItemStack(TFCMItems.poniard_Mold, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.poniard_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.poniard_Mold, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.poniard_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.poniard_Mold, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.poniard_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.poniard_Mold, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.poniard_Mold, 1, 1)});
		
		craftingManager.addRecipe(new ItemStack(TFCMItems.arrow_Mold, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.arrow_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.arrow_Mold, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.arrow_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.arrow_Mold, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.arrow_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.arrow_Mold, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.arrow_Mold, 1, 1)});
		
		craftingManager.addRecipe(new ItemStack(TFCMItems.bolt_Mold, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.bolt_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.bolt_Mold, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.bolt_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.bolt_Mold, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.bolt_Mold, 1, 1)});
		craftingManager.addRecipe(new ItemStack(TFCMItems.bolt_Mold, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getItemStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCMItems.bolt_Mold, 1, 1)});
	}
	
	private static void registerKnappingRecipes()
	{
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCMItems.halberd_Mold, 1, 0), new Object[] { 
			" ##  ",
			"#### ",
			"#####",
			"#### ",
			" ##  ", Character.valueOf('#'), new ItemStack(TFCItems.flatClay, 1, 1)});
		
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCMItems.warHammer_Mold, 1, 0), new Object[] { 
			"     ",
			"#####",
			"###  ",
			"  #  ",
			"     ", Character.valueOf('#'), new ItemStack(TFCItems.flatClay, 1, 1)});
		
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCMItems.poniard_Mold, 1, 0), new Object[] { 
			"     ",
			"  ## ",
			" ### ",
			" ##  ",
			"#    ", Character.valueOf('#'), new ItemStack(TFCItems.flatClay, 1, 1)});
		
		if(TFCMOptions.enableCraftingCrossbow == true)
		{
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCMItems.bolt_Mold, 1, 0), new Object[] { 
				"     ",
				"     ",
				"     ",
				"  #  ",
				"  #  ", Character.valueOf('#'), new ItemStack(TFCItems.flatClay, 1, 1)});
		}
		
		if(TFCMOptions.enableCraftingLongbow == true)
		{
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCMItems.arrow_Mold, 1, 0), new Object[] { 
				"     ",
				" ### ",
				"  ## ",
				"   # ",
				"     ", Character.valueOf('#'), new ItemStack(TFCItems.flatClay, 1, 1)});
		}
	}
	
	private static void registerQuernRecipes()
	{
		QuernManager.getInstance().addRecipe(new QuernRecipe(new ItemStack(TFCItems.wroughtIronIngot), new ItemStack(TFCMItems.ironDust, 8)));
		QuernManager.getInstance().addRecipe(new QuernRecipe(new ItemStack(TFCItems.smallOreChunk, 1, 10), new ItemStack(TFCMItems.ironDust, 1)));
		QuernManager.getInstance().addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 59), new ItemStack(TFCMItems.ironDust, 1)));
		QuernManager.getInstance().addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 10), new ItemStack(TFCMItems.ironDust, 2)));
		QuernManager.getInstance().addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 45), new ItemStack(TFCMItems.ironDust, 3)));
		
		QuernManager.getInstance().addRecipe(new QuernRecipe(new ItemStack(TFCItems.soybean, 1), new ItemStack(TFCMItems.soyPaste, 1)));
	}
	
	@SuppressWarnings("unused")
	public static void registerHeatingRecipes()
	{
		HeatRaw bismuthRaw = new HeatRaw(0.14, 270);
		HeatRaw bismuthBronzeRaw = new HeatRaw(0.35, 985);
		HeatRaw blackBronzeRaw = new HeatRaw(0.35, 1070);
		HeatRaw blackSteelRaw = new HeatRaw(0.35, 1485);
		HeatRaw blueSteelRaw = new HeatRaw(0.35, 1540);
		HeatRaw brassRaw = new HeatRaw(0.35, 930);
		HeatRaw bronzeRaw = new HeatRaw(0.35, 950);
		HeatRaw copperRaw = new HeatRaw(0.35, 1080);
		HeatRaw goldRaw = new HeatRaw(0.6, 1060);
		HeatRaw ironRaw = new HeatRaw(0.35, 1535);
		HeatRaw leadRaw = new HeatRaw(0.22, 328);
		HeatRaw nickelRaw = new HeatRaw(0.48, 1453);
		HeatRaw pigIronRaw = new HeatRaw(0.35, 1500);
		HeatRaw platinumRaw = new HeatRaw(0.35, 1730);
		HeatRaw redSteelRaw = new HeatRaw(0.35, 1540);
		HeatRaw roseGoldRaw = new HeatRaw(0.35, 960);
		HeatRaw silverRaw = new HeatRaw(0.48, 961);
		HeatRaw steelRaw = new HeatRaw(0.35, 1540);//sh = 0.63F; boil = 3500; melt = 1540;
		HeatRaw sterlingSilverRaw = new HeatRaw(0.35, 900);//sh = 0.72F; boil = 2212; melt = 893;
		HeatRaw tinRaw = new HeatRaw(0.14, 230);
		HeatRaw zincRaw = new HeatRaw(0.21, 420);//sh = 0.66F; boil = 907; melt = 420;
		
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.bottleFruitJuice), 0.98D, 150.0D, (new ItemStack(TFCMItems.bottleSugar))));
		
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.coil_BismuthBronze, 1), bismuthBronzeRaw, new ItemStack(TFCItems.bismuthBronzeUnshaped, 1)));
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.coil_BlackBronze, 1), blackBronzeRaw, new ItemStack(TFCItems.blackBronzeUnshaped, 1)));
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.coil_BlackSteel, 1), blackSteelRaw, new ItemStack(TFCItems.blackSteelUnshaped, 1)));
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.coil_BlueSteel, 1), blueSteelRaw, new ItemStack(TFCItems.blueSteelUnshaped, 1)));
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.coil_Bronze, 1), bronzeRaw, new ItemStack(TFCItems.bronzeUnshaped, 1)));
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.coil_Copper, 1), copperRaw, new ItemStack(TFCItems.copperUnshaped, 1)));
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.coil_WroughtIron, 1), ironRaw, new ItemStack(TFCItems.wroughtIronUnshaped, 1)));
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.coil_RedSteel, 1), redSteelRaw, new ItemStack(TFCItems.redSteelUnshaped, 1)));
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.coil_Steel, 1), steelRaw, new ItemStack(TFCItems.steelUnshaped, 1)));
	
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.pumpkin,1),1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(TFCMItems.bearRaw,1),1, 1200, null));
	}	
}