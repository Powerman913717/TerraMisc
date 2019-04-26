package terramisc.core;

import com.bioxx.tfc.CommonProxy;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Food.ItemFoodMeat;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import terramisc.common.ArmorStats;
import terramisc.items.ItemBowLimb;
import terramisc.items.ItemBowlTallow;
import terramisc.items.ItemMetalPart;
import terramisc.items.ItemTallowDye;
import terramisc.items.ItemToolMold;
import terramisc.items.TFCMCustomSeeds;
import terramisc.items.food.ItemBottleJuice;
import terramisc.items.food.ItemBottleJuiceLemon;
import terramisc.items.food.ItemBottleJuiceOnion;
import terramisc.items.food.ItemBottleSoyMilk;
import terramisc.items.food.ItemBottleSugar;
import terramisc.items.food.ItemBottleWine;
import terramisc.items.food.ItemFoodReturn;
import terramisc.items.food.ItemPie;
import terramisc.items.food.ItemPlaceableFood;
import terramisc.items.tools.ItemCustomArmor;
import terramisc.items.tools.ItemCustomArrow;
import terramisc.items.tools.ItemCustomBolt;
import terramisc.items.tools.ItemCustomCrossbow;
import terramisc.items.tools.ItemCustomHalberd;
import terramisc.items.tools.ItemCustomLongbow;
import terramisc.items.tools.ItemCustomPoniard;
import terramisc.items.tools.ItemCustomProjectileHead;
import terramisc.items.tools.ItemCustomQuiver;
import terramisc.items.tools.ItemCustomToolHead;
import terramisc.items.tools.ItemCustomWarHammer;
import terramisc.items.weights.ItemWeightLargeHeavy;
import terramisc.items.weights.ItemWeightLightMedium;
import terramisc.items.weights.ItemWeightSmallHeavy;
import terramisc.items.weights.ItemWeightTinyLight;

public class TFCMItemsSetup extends TFCMItems
{
	public static void ItemSetup()
	{
		//Materials
		ToolMaterial CopperToolMaterial;
		ToolMaterial BronzeToolMaterial;
		ToolMaterial BismuthBronzeToolMaterial;
		ToolMaterial BlackBronzeToolMaterial;
		ToolMaterial IronToolMaterial;
		ToolMaterial SteelToolMaterial;
		ToolMaterial BlackSteelToolMaterial;
		ToolMaterial BlueSteelToolMaterial;
		ToolMaterial RedSteelToolMaterial;
				
		int CopperUses = 600;
		int BronzeUses = 1300;
		int BismuthBronzeUses = 1200;
		int BlackBronzeUses = 1460;
		int WroughtIronUses = 2200;
		int SteelUses = 3300;
		int BlackSteelUses = 4200;
		int BlueSteelUses = 6500;
		int RedSteelUses = 6500;

		float CopperEff = 8;
		float BronzeEff = 11;
		float BismuthBronzeEff = 10;
		float BlackBronzeEff = 9;
		float WroughtIronEff = 12;
		float SteelEff = 14;
		float BlackSteelEff = 16;
		float BlueSteelEff = 18;
		float RedSteelEff = 18;
		
		CopperToolMaterial = EnumHelper.addToolMaterial("Copper", 2, CopperUses, CopperEff, 65, 8);
		BronzeToolMaterial = EnumHelper.addToolMaterial("Bronze", 2, BronzeUses, BronzeEff, 100, 13);
		BismuthBronzeToolMaterial = EnumHelper.addToolMaterial("BismuthBronze", 2, BismuthBronzeUses, BismuthBronzeEff, 90, 10);
		BlackBronzeToolMaterial = EnumHelper.addToolMaterial("BlackBronze", 2, BlackBronzeUses, BlackBronzeEff, 95, 10);
		IronToolMaterial = EnumHelper.addToolMaterial("Iron", 2, WroughtIronUses, WroughtIronEff, 135, 10);
		SteelToolMaterial = EnumHelper.addToolMaterial("Steel", 2, SteelUses, SteelEff, 170, 10);
		BlackSteelToolMaterial = EnumHelper.addToolMaterial("BlackSteel", 2, BlackSteelUses, BlackSteelEff, 205, 12);
		BlueSteelToolMaterial = EnumHelper.addToolMaterial("BlueSteel", 3, BlueSteelUses, BlueSteelEff, 240, 22);
		RedSteelToolMaterial = EnumHelper.addToolMaterial("RedSteel", 3, RedSteelUses, RedSteelEff, 240, 22);

		
		System.out.println("[" + TFCMDetails.ModName + "] Registering s"); //Beginning of item registering
		
		//Basic s
		casingBrass = new ItemWeightSmallHeavy().setUnlocalizedName("CasingBrass");
		casingIron = new ItemWeightSmallHeavy().setUnlocalizedName("CasingIron");
		casingRoseGold = new ItemWeightSmallHeavy().setUnlocalizedName("CasingRoseGold");
		gear = new ItemWeightTinyLight().setUnlocalizedName("Gear");
		caseinGlue = new ItemWeightTinyLight().setUnlocalizedName("CaseinGlue");
		pistonBase = new ItemWeightLargeHeavy().setUnlocalizedName("PistonBase");
		circuit = new ItemWeightSmallHeavy().setUnlocalizedName("Circuit");
		deerTendon = new ItemWeightTinyLight().setUnlocalizedName("DeerTendon");
		sinewFiber = new ItemWeightTinyLight().setUnlocalizedName("SinewFiber");
		sinewString = new ItemWeightTinyLight().setUnlocalizedName("SinewString");
		sinewBowString = new ItemWeightTinyLight().setUnlocalizedName("SinewBowString");
		bowLimb = new ItemBowLimb().setUnlocalizedName("BowLimb");
		suet = new ItemWeightSmallHeavy().setUnlocalizedName("Suet");
		bowlSuet = new ItemWeightSmallHeavy().setUnlocalizedName("BowlSuet").setMaxStackSize(1);
		bowlTallow = new ItemBowlTallow().setUnlocalizedName("BowlTallow");
		brownDye = new ItemWeightTinyLight().setUnlocalizedName("BrownDye");
		ironDust = new ItemWeightSmallHeavy().setUnlocalizedName("IronDust");
		tallowDye = new ItemTallowDye().setUnlocalizedName("TallowDye");
		
		//Bows and Such
		longBow = new ItemCustomLongbow().setUnlocalizedName("LongBow");
		crossBow = new ItemCustomCrossbow().setUnlocalizedName("CrossBow");
		
		//Halberds
		halberd_BismuthBronze = new ItemCustomHalberd(BismuthBronzeToolMaterial, 315).setUnlocalizedName("Halberd_BismuthBronze").setMaxDamage(BismuthBronzeUses);
		halberd_BlackBronze = new ItemCustomHalberd(BlackBronzeToolMaterial, 	 345).setUnlocalizedName("Halberd_BlackBronze").setMaxDamage(BlackBronzeUses);
		halberd_BlackSteel = new ItemCustomHalberd(BlackSteelToolMaterial, 	     405).setUnlocalizedName("Halberd_BlackSteel").setMaxDamage(BlackSteelUses);
		halberd_BlueSteel = new ItemCustomHalberd(BlueSteelToolMaterial,		 472).setUnlocalizedName("Halberd_BlueSteel").setMaxDamage(BlueSteelUses);
		halberd_Bronze = new ItemCustomHalberd(BronzeToolMaterial,			     330).setUnlocalizedName("Halberd_Bronze").setMaxDamage(BronzeUses);
		halberd_Copper = new ItemCustomHalberd(CopperToolMaterial, 			     248).setUnlocalizedName("Halberd_Copper").setMaxDamage(CopperUses);
		halberd_WroughtIron = new ItemCustomHalberd(IronToolMaterial,			 360).setUnlocalizedName("Halberd_WroughtIron").setMaxDamage(WroughtIronUses);
		halberd_RedSteel = new ItemCustomHalberd(RedSteelToolMaterial,		     472).setUnlocalizedName("Halberd_RedSteel").setMaxDamage(RedSteelUses);
		halberd_Steel = new ItemCustomHalberd(SteelToolMaterial,				 398).setUnlocalizedName("Halberd_Steel").setMaxDamage(SteelUses);
		
		//Halberd Tool Heads
		halberd_BismuthBronze_Head = new ItemCustomToolHead().setUnlocalizedName("Halberd_BismuthBronze_Head");
		halberd_BlackBronze_Head = new ItemCustomToolHead().setUnlocalizedName("Halberd_BlackBronze_Head");
		halberd_BlackSteel_Head = new ItemCustomToolHead().setUnlocalizedName("Halberd_BlackSteel_Head");
		halberd_BlueSteel_Head = new ItemCustomToolHead().setUnlocalizedName("Halberd_BlueSteel_Head");
		halberd_Bronze_Head = new ItemCustomToolHead().setUnlocalizedName("Halberd_Bronze_Head");
		halberd_Copper_Head = new ItemCustomToolHead().setUnlocalizedName("Halberd_Copper_Head");
		halberd_WroughtIron_Head = new ItemCustomToolHead().setUnlocalizedName("Halberd_WroughtIron_Head");
		halberd_RedSteel_Head = new ItemCustomToolHead().setUnlocalizedName("Halberd_RedSteel_Head");
		halberd_Steel_Head = new ItemCustomToolHead().setUnlocalizedName("Halberd_Steel_Head");
		
		//War Hammers
		warHammer_BismuthBronze = new ItemCustomWarHammer(BismuthBronzeToolMaterial, 315).setUnlocalizedName("WarHammer_BismuthBronze").setMaxDamage(BismuthBronzeUses);
		warHammer_BlackBronze = new ItemCustomWarHammer(BlackBronzeToolMaterial, 	 345).setUnlocalizedName("WarHammer_BlackBronze").setMaxDamage(BlackBronzeUses);
		warHammer_BlackSteel = new ItemCustomWarHammer(BlackSteelToolMaterial, 	     405).setUnlocalizedName("WarHammer_BlackSteel").setMaxDamage(BlackSteelUses);
		warHammer_BlueSteel = new ItemCustomWarHammer(BlueSteelToolMaterial,		 472).setUnlocalizedName("WarHammer_BlueSteel").setMaxDamage(BlueSteelUses);
		warHammer_Bronze = new ItemCustomWarHammer(BronzeToolMaterial,			     330).setUnlocalizedName("WarHammer_Bronze").setMaxDamage(BronzeUses);
		warHammer_Copper = new ItemCustomWarHammer(CopperToolMaterial, 			     248).setUnlocalizedName("WarHammer_Copper").setMaxDamage(CopperUses);
		warHammer_WroughtIron = new ItemCustomWarHammer(IronToolMaterial,			 360).setUnlocalizedName("WarHammer_WroughtIron").setMaxDamage(WroughtIronUses);
		warHammer_RedSteel = new ItemCustomWarHammer(RedSteelToolMaterial,		     472).setUnlocalizedName("WarHammer_RedSteel").setMaxDamage(RedSteelUses);
		warHammer_Steel = new ItemCustomWarHammer(SteelToolMaterial,				 398).setUnlocalizedName("WarHammer_Steel").setMaxDamage(SteelUses);
		
		//War Hammer Heads
		warHammer_BismuthBronze_Head = new ItemCustomToolHead().setUnlocalizedName("WarHammer_BismuthBronze_Head");
		warHammer_BlackBronze_Head = new ItemCustomToolHead().setUnlocalizedName("WarHammer_BlackBronze_Head");
		warHammer_BlackSteel_Head = new ItemCustomToolHead().setUnlocalizedName("WarHammer_BlackSteel_Head");
		warHammer_BlueSteel_Head = new ItemCustomToolHead().setUnlocalizedName("WarHammer_BlueSteel_Head");
		warHammer_Bronze_Head = new ItemCustomToolHead().setUnlocalizedName("WarHammer_Bronze_Head");
		warHammer_Copper_Head = new ItemCustomToolHead().setUnlocalizedName("WarHammer_Copper_Head");
		warHammer_WroughtIron_Head = new ItemCustomToolHead().setUnlocalizedName("WarHammer_WroughtIron_Head");
		warHammer_RedSteel_Head = new ItemCustomToolHead().setUnlocalizedName("WarHammer_RedSteel_Head");
		warHammer_Steel_Head = new ItemCustomToolHead().setUnlocalizedName("WarHammer_Steel_Head");
		
		//Poniards
		poniard_BismuthBronze = new ItemCustomPoniard(BismuthBronzeToolMaterial, 175).setUnlocalizedName("Poniard_BismuthBronze").setMaxDamage(BismuthBronzeUses);
		poniard_BlackBronze = new ItemCustomPoniard(BlackBronzeToolMaterial, 	 190).setUnlocalizedName("Poniard_BlackBronze").setMaxDamage(BlackBronzeUses);
		poniard_BlackSteel = new ItemCustomPoniard(BlackSteelToolMaterial, 	     225).setUnlocalizedName("Poniard_BlackSteel").setMaxDamage(BlackSteelUses);
		poniard_BlueSteel = new ItemCustomPoniard(BlueSteelToolMaterial,		 263).setUnlocalizedName("Poniard_BlueSteel").setMaxDamage(BlueSteelUses);
		poniard_Bronze = new ItemCustomPoniard(BronzeToolMaterial,			     183).setUnlocalizedName("Poniard_Bronze").setMaxDamage(BronzeUses);
		poniard_Copper = new ItemCustomPoniard(CopperToolMaterial, 			     138).setUnlocalizedName("Poniard_Copper").setMaxDamage(CopperUses);
		poniard_WroughtIron = new ItemCustomPoniard(IronToolMaterial,			 200).setUnlocalizedName("Poniard_WroughtIron").setMaxDamage(WroughtIronUses);
		poniard_RedSteel = new ItemCustomPoniard(RedSteelToolMaterial,		     263).setUnlocalizedName("Poniard_RedSteel").setMaxDamage(RedSteelUses);
		poniard_Steel = new ItemCustomPoniard(SteelToolMaterial,				 221).setUnlocalizedName("Poniard_Steel").setMaxDamage(SteelUses);
		
		//Poniard Blades
		poniard_BismuthBronze_Head = new ItemCustomToolHead().setUnlocalizedName("Poniard_BismuthBronze_Head");
		poniard_BlackBronze_Head = new ItemCustomToolHead().setUnlocalizedName("Poniard_BlackBronze_Head");
		poniard_BlackSteel_Head = new ItemCustomToolHead().setUnlocalizedName("Poniard_BlackSteel_Head");
		poniard_BlueSteel_Head = new ItemCustomToolHead().setUnlocalizedName("Poniard_BlueSteel_Head");
		poniard_Bronze_Head = new ItemCustomToolHead().setUnlocalizedName("Poniard_Bronze_Head");
		poniard_Copper_Head = new ItemCustomToolHead().setUnlocalizedName("Poniard_Copper_Head");
		poniard_WroughtIron_Head = new ItemCustomToolHead().setUnlocalizedName("Poniard_WroughtIron_Head");
		poniard_RedSteel_Head = new ItemCustomToolHead().setUnlocalizedName("Poniard_RedSteel_Head");
		poniard_Steel_Head = new ItemCustomToolHead().setUnlocalizedName("Poniard_Steel_Head");
		
		//Arrows
		arrow_BismuthBronze = new ItemCustomArrow().setUnlocalizedName("Arrow_BismuthBronze");
		arrow_BlackBronze = new ItemCustomArrow().setUnlocalizedName("Arrow_BlackBronze");
		arrow_BlackSteel = new ItemCustomArrow().setUnlocalizedName("Arrow_BlackSteel");
		arrow_BlueSteel = new ItemCustomArrow().setUnlocalizedName("Arrow_BlueSteel");
		arrow_Bronze = new ItemCustomArrow().setUnlocalizedName("Arrow_Bronze");
		arrow_Copper = new ItemCustomArrow().setUnlocalizedName("Arrow_Copper");
		arrow_RedSteel = new ItemCustomArrow().setUnlocalizedName("Arrow_RedSteel");
		arrow_Steel = new ItemCustomArrow().setUnlocalizedName("Arrow_Steel");
		arrow_WroughtIron = new ItemCustomArrow().setUnlocalizedName("Arrow_WroughtIron");
		
		//Arrow Heads
		arrow_BismuthBronze_Head = new ItemCustomProjectileHead().setUnlocalizedName("Arrow_BismuthBronze_Head");
		arrow_BlackBronze_Head = new ItemCustomProjectileHead().setUnlocalizedName("Arrow_BlackBronze_Head");
		arrow_BlackSteel_Head = new ItemCustomProjectileHead().setUnlocalizedName("Arrow_BlackSteel_Head");
		arrow_BlueSteel_Head = new ItemCustomProjectileHead().setUnlocalizedName("Arrow_BlueSteel_Head");
		arrow_Bronze_Head = new ItemCustomProjectileHead().setUnlocalizedName("Arrow_Bronze_Head");
		arrow_Copper_Head = new ItemCustomProjectileHead().setUnlocalizedName("Arrow_Copper_Head");
		arrow_WroughtIron_Head = new ItemCustomProjectileHead().setUnlocalizedName("Arrow_WroughtIron_Head");
		arrow_RedSteel_Head = new ItemCustomProjectileHead().setUnlocalizedName("Arrow_RedSteel_Head");
		arrow_Steel_Head = new ItemCustomProjectileHead().setUnlocalizedName("Arrow_Steel_Head");
		
		//Bolt
		bolt_BismuthBronze = new ItemCustomBolt().setUnlocalizedName("Bolt_BismuthBronze");
		bolt_BlackBronze = new ItemCustomBolt().setUnlocalizedName("Bolt_BlackBronze");
		bolt_BlackSteel = new ItemCustomBolt().setUnlocalizedName("Bolt_BlackSteel");
		bolt_BlueSteel = new ItemCustomBolt().setUnlocalizedName("Bolt_BlueSteel");
		bolt_Bronze = new ItemCustomBolt().setUnlocalizedName("Bolt_Bronze");
		bolt_Copper = new ItemCustomBolt().setUnlocalizedName("Bolt_Copper");
		bolt_RedSteel = new ItemCustomBolt().setUnlocalizedName("Bolt_RedSteel");
		bolt_Steel = new ItemCustomBolt().setUnlocalizedName("Bolt_Steel");
		bolt_WroughtIron = new ItemCustomBolt().setUnlocalizedName("Bolt_WroughtIron");
		
		//Bolt Heads
		bolt_BismuthBronze_Head = new ItemCustomProjectileHead().setUnlocalizedName("Bolt_BismuthBronze_Head");
		bolt_BlackBronze_Head = new ItemCustomProjectileHead().setUnlocalizedName("Bolt_BlackBronze_Head");
		bolt_BlackSteel_Head = new ItemCustomProjectileHead().setUnlocalizedName("Bolt_BlackSteel_Head");
		bolt_BlueSteel_Head = new ItemCustomProjectileHead().setUnlocalizedName("Bolt_BlueSteel_Head");
		bolt_Bronze_Head = new ItemCustomProjectileHead().setUnlocalizedName("Bolt_Bronze_Head");
		bolt_Copper_Head = new ItemCustomProjectileHead().setUnlocalizedName("Bolt_Copper_Head");
		bolt_WroughtIron_Head = new ItemCustomProjectileHead().setUnlocalizedName("Bolt_WroughtIron_Head");
		bolt_RedSteel_Head = new ItemCustomProjectileHead().setUnlocalizedName("Bolt_RedSteel_Head");
		bolt_Steel_Head = new ItemCustomProjectileHead().setUnlocalizedName("Bolt_Steel_Head");
		
		//Pottery
		halberd_Mold = new ItemToolMold().setMetaNames(new String[]{"Clay_Mold_Halberd","Ceramic_Mold_Halberd",
				"Ceramic_Mold_Halberd_Copper","Ceramic_Mold_Halberd_Bronze","Ceramic_Mold_Halberd_BismuthBronze","Ceramic_Mold_Halberd_BlackBronze"}).setUnlocalizedName("halberd_Mold");
		warHammer_Mold = new ItemToolMold().setMetaNames(new String[]{"Clay_Mold_WarHammer","Ceramic_Mold_WarHammer",
				"Ceramic_Mold_WarHammer_Copper","Ceramic_Mold_WarHammer_Bronze","Ceramic_Mold_WarHammer_BismuthBronze","Ceramic_Mold_WarHammer_BlackBronze"}).setUnlocalizedName("warHammer_Mold");
		poniard_Mold = new ItemToolMold().setMetaNames(new String[]{"Clay_Mold_Poniard","Ceramic_Mold_Poniard",
				"Ceramic_Mold_Poniard_Copper","Ceramic_Mold_Poniard_Bronze","Ceramic_Mold_Poniard_BismuthBronze","Ceramic_Mold_Poniard_BlackBronze"}).setUnlocalizedName("poniard_Mold");
		arrow_Mold = new ItemToolMold().setMetaNames(new String[]{"Clay_Mold_Arrow","Ceramic_Mold_Arrow",
				"Ceramic_Mold_Arrow_Copper","Ceramic_Mold_Arrow_Bronze","Ceramic_Mold_Arrow_BismuthBronze","Ceramic_Mold_Arrow_BlackBronze"}).setUnlocalizedName("arrow_Mold");
		bolt_Mold = new ItemToolMold().setMetaNames(new String[]{"Clay_Mold_Bolt","Ceramic_Mold_Bolt",
				"Ceramic_Mold_Bolt_Copper","Ceramic_Mold_Bolt_Bronze","Ceramic_Mold_Bolt_BismuthBronze","Ceramic_Mold_Bolt_BlackBronze"}).setUnlocalizedName("bolt_Mold");
		
		//Armor Ingrediants
		coil_BismuthBronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Coil_BismuthBronze")).setMetal("Bismuth Bronze", 100);
		coil_BlackBronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Coil_BlackBronze")).setMetal("Black Bronze", 100);
		coil_BlackSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Coil_BlackSteel")).setMetal("Black Steel", 100);
		coil_BlueSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Coil_BlueSteel")).setMetal("Blue Steel", 100);
		coil_Bronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Coil_Bronze")).setMetal("Bronze", 100);
		coil_Copper = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Coil_Copper")).setMetal("Copper", 100);
		coil_WroughtIron = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Coil_WroughtIron")).setMetal("Wrought Iron", 100);
		coil_RedSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Coil_RedSteel")).setMetal("Red Steel", 100);
		coil_Steel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Coil_Steel")).setMetal("Steel", 100);
		
		link_BismuthBronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Link_BismuthBronze")).setMetal("Bismuth Bronze", (short) 6.25);
		link_BlackBronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Link_BlackBronze")).setMetal("Black Bronze", (short) 6.25);
		link_BlackSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Link_BlackSteel")).setMetal("Black Steel", (short) 6.25);
		link_BlueSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Link_BlueSteel")).setMetal("Blue Steel", (short) 6.25);
		link_Bronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Link_Bronze")).setMetal("Bronze", (short) 6.25);
		link_Copper = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Link_Copper")).setMetal("Copper", (short) 6.25);
		link_WroughtIron = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Link_WroughtIron")).setMetal("Wrought Iron", (short) 6.25);
		link_RedSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Link_RedSteel")).setMetal("Red Steel", (short) 6.25);
		link_Steel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Link_Steel")).setMetal("Steel", (short) 6.25);
		
		chain_Square_BismuthBronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Square_BismuthBronze")).setMetal("Bismuth Bronze", 25);
		chain_Square_BlackBronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Square_BlackBronze")).setMetal("Black Bronze", 25);
		chain_Square_BlackSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Square_BlackSteel")).setMetal("Black Steel", 25);
		chain_Square_BlueSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Square_BlueSteel")).setMetal("Blue Steel", 25);
		chain_Square_Bronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Square_Bronze")).setMetal("Bronze", 25);
		chain_Square_Copper = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Square_Copper")).setMetal("Copper", 25);
		chain_Square_WroughtIron = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Square_WroughtIron")).setMetal("Wrought Iron", 25);
		chain_Square_RedSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Square_RedSteel")).setMetal("Red Steel", 25);
		chain_Square_Steel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Square_Steel")).setMetal("Steel", 25);
		
		chain_Sheet_BismuthBronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Sheet_BismuthBronze")).setMetal("Bismuth Bronze", 100);
		chain_Sheet_BlackBronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Sheet_BlackBronze")).setMetal("Black Bronze", 100);
		chain_Sheet_BlackSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Sheet_BlackSteel")).setMetal("Black Steel", 100);
		chain_Sheet_BlueSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Sheet_BlueSteel")).setMetal("Blue Steel", 100);
		chain_Sheet_Bronze = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Sheet_Bronze")).setMetal("Bronze", 100);
		chain_Sheet_Copper = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Sheet_Copper")).setMetal("Copper", 100);
		chain_Sheet_WroughtIron = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Sheet_WroughtIron")).setMetal("Wrought Iron", 100);
		chain_Sheet_RedSteel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Sheet_RedSteel")).setMetal("Red Steel", 100);
		chain_Sheet_Steel = ((ItemMetalPart) new ItemMetalPart().setUnlocalizedName("Chain_Sheet_Steel")).setMetal("Steel", 100);
		
		//Inventory s
		itemQuiver = new ItemCustomQuiver().setUnlocalizedName("Quiver");
		
		SetupArmor();
		SetupFood();
		registers();
		System.out.println("[" + TFCMDetails.ModName + "] Done Registering s");
	}
	
	public static void SetupFood()
	{	
		//Drinks
		bottleFruitJuice = new ItemBottleJuice().setUnlocalizedName("BottleFruitJuice");
		bottleFruitWine = new ItemBottleWine().setUnlocalizedName("BottleFruitWine");
		bottleSugar= new ItemBottleSugar().setUnlocalizedName("BottleSugar");
		
		bottleJuiceApple = new ItemBottleJuice().setUnlocalizedName("BottleJuiceApple");
		bottleJuiceOrange = new ItemBottleJuice().setUnlocalizedName("BottleJuiceOrange");
		
		bottleJuiceLemon = new ItemBottleJuiceLemon().setUnlocalizedName("BottleJuiceLemon");
		bottleLemonade = new ItemBottleJuice().setUnlocalizedName("BottleLemonade");
		
		bottleJuiceOnion = new ItemBottleJuiceOnion().setUnlocalizedName("BottleJuiceOnion");
		
		bottleSoyMilk = new ItemBottleSoyMilk().setUnlocalizedName("BottleSoyMilk");
		bucketHotWater= new ItemWeightLightMedium().setUnlocalizedName("BucketHotWater");
		
		//Soy
		soyPaste = new ItemFoodTFC(EnumFoodGroup.Protein, 10, 0, 0, 0, 40, false).setUnlocalizedName("SoyPaste");
		
		//Crops, See BlockCropTFCM.class CropRegistry.class and CropRender.class
		seedsPumpkin = new TFCMCustomSeeds(TFCMOptions.pumpkinID).setUnlocalizedName("Seeds Pumpkin");
		pumpkin = new ItemPlaceableFood(EnumFoodGroup.Vegetable, 5, 0, 0, 10, 5, true, 80, TFCMBlocks.blockPumpkin, true).setFolder("food/unused/").setUnlocalizedName("Pumpkin").setTextureName("img73");
		seedsCayenne = new TFCMCustomSeeds(TFCMOptions.cayenneID).setUnlocalizedName("Seeds Cayenne");
		greenCayenne = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 20, true).setUnlocalizedName("greenCayenne");
		redCayenne = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 45, true).setFolder("food/unused/").setUnlocalizedName("redCayenne").setTextureName("img105");
		seedsCoffee = new TFCMCustomSeeds(TFCMOptions.coffeeID).setUnlocalizedName("Seeds Coffee");
		coffeeCherries = new ItemFoodReturn(EnumFoodGroup.Fruit, 10, 5, 0, 0, 5, true).setReturnFood(TFCMItems.greenCoffee, 1F, 0.2F).setUnlocalizedName("coffeeCherries");
		greenCoffee = new ItemFoodTFC(EnumFoodGroup.None, 0, 0, 0, 15, 0, false, false).setUnlocalizedName("greenCoffee");
		coffee = new ItemFoodTFC(EnumFoodGroup.None, 0, 0, 0, 25, 0, false, false).setFolder("food/unused/").setUnlocalizedName("coffee").setTextureName("img78");
		groundCoffee = new ItemFoodTFC(EnumFoodGroup.None, 0, 0, 0, 25, 0, false, false).setUnlocalizedName("groundCoffee");
		
		//Meat
		bearRaw = new ItemFoodMeat(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, false, false).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Bear");
		
		//Meals
		pie = new ItemPie().setUnlocalizedName("Pie");
		// TODO add items for Casserole, Soup, and Stew
	}
	
	public static void SetupArmor()
	{
		String[] Names = {"BismuthBronze", "BlackBronze", "BlackSteel", "BlueSteel", "Bronze", "Copper", "WroughtIron", "RedSteel", "Steel"};
		CommonProxy proxy = TerraFirmaCraft.proxy;
		int i = 0;

		i = 0;
		BismuthBronzeChainGreaves = (new ItemCustomArmor(ArmorStats.BismuthBronzeChain, proxy.getArmorRenderID("bismuthbronze"), 2, 50,1).setUnlocalizedName(Names[i]+"_Chain_Greaves")); i++;
		BlackBronzeChainGreaves = 	(new ItemCustomArmor(ArmorStats.BlackBronzeChain, proxy.getArmorRenderID("blackbronze"), 2, 50,1).setUnlocalizedName(Names[i]+"_Chain_Greaves")); i++;
		BlackSteelChainGreaves = 	(new ItemCustomArmor(ArmorStats.BlackSteelChain, proxy.getArmorRenderID("blacksteel"), 2, 50,1).setUnlocalizedName(Names[i]+"_Chain_Greaves")); i++;
		BlueSteelChainGreaves = 	(new ItemCustomArmor(ArmorStats.BlueSteelChain, proxy.getArmorRenderID("bluesteel"), 2, 50,1).setUnlocalizedName(Names[i]+"_Chain_Greaves")); i++;
		BronzeChainGreaves = 		(new ItemCustomArmor(ArmorStats.BronzeChain, proxy.getArmorRenderID("bronze"), 2, 50,1).setUnlocalizedName(Names[i]+"_Chain_Greaves")); i++;
		CopperChainGreaves = 		(new ItemCustomArmor(ArmorStats.CopperChain, proxy.getArmorRenderID("copper"), 2, 50,1).setUnlocalizedName(Names[i]+"_Chain_Greaves")); i++;
		WroughtIronChainGreaves =	(new ItemCustomArmor(ArmorStats.WroughtIronChain, proxy.getArmorRenderID("wroughtiron"), 2, 50,1).setUnlocalizedName(Names[i]+"_Chain_Greaves")); i++;
		RedSteelChainGreaves = 		(new ItemCustomArmor(ArmorStats.RedSteelChain, proxy.getArmorRenderID("redsteel"), 2, 50,1).setUnlocalizedName(Names[i]+"_Chain_Greaves")); i++;
		SteelChainGreaves = 		(new ItemCustomArmor(ArmorStats.SteelChain, proxy.getArmorRenderID("steel"), 2, 50,1).setUnlocalizedName(Names[i]+"_Chain_Greaves"));

		i = 0;
		BismuthBronzeChainChestplate =	(new ItemCustomArmor(ArmorStats.BismuthBronzeChain, proxy.getArmorRenderID("bismuthbronze"), 1, 50,2).setUnlocalizedName(Names[i]+"_Chain_Chestplate")); i++;
		BlackBronzeChainChestplate = 	(new ItemCustomArmor(ArmorStats.BlackBronzeChain, proxy.getArmorRenderID("blackbronze"), 1, 50,2).setUnlocalizedName(Names[i]+"_Chain_Chestplate")); i++;
		BlackSteelChainChestplate = 	(new ItemCustomArmor(ArmorStats.BlackSteelChain, proxy.getArmorRenderID("blacksteel"), 1, 50,2).setUnlocalizedName(Names[i]+"_Chain_Chestplate")); i++;
		BlueSteelChainChestplate = 		(new ItemCustomArmor(ArmorStats.BlueSteelChain, proxy.getArmorRenderID("bluesteel"), 1, 50,2).setUnlocalizedName(Names[i]+"_Chain_Chestplate")); i++;
		BronzeChainChestplate = 		(new ItemCustomArmor(ArmorStats.BronzeChain, proxy.getArmorRenderID("bronze"), 1, 50,2).setUnlocalizedName(Names[i]+"_Chain_Chestplate")); i++;
		CopperChainChestplate = 		(new ItemCustomArmor(ArmorStats.CopperChain, proxy.getArmorRenderID("copper"), 1, 50,2).setUnlocalizedName(Names[i]+"_Chain_Chestplate")); i++;
		WroughtIronChainChestplate = 	(new ItemCustomArmor(ArmorStats.WroughtIronChain, proxy.getArmorRenderID("wroughtiron"), 1, 50,2).setUnlocalizedName(Names[i]+"_Chain_Chestplate")); i++;
		RedSteelChainChestplate = 		(new ItemCustomArmor(ArmorStats.RedSteelChain, proxy.getArmorRenderID("redsteel"), 1, 50,2).setUnlocalizedName(Names[i]+"_Chain_Chestplate")); i++;
		SteelChainChestplate = 			(new ItemCustomArmor(ArmorStats.SteelChain, proxy.getArmorRenderID("steel"), 1, 50,2).setUnlocalizedName(Names[i]+"_Chain_Chestplate"));

		i = 0;
		BismuthBronzeChainHelmet = 	(new ItemCustomArmor(ArmorStats.BismuthBronzeChain, proxy.getArmorRenderID("bismuthbronze"), 0, 50,3).setUnlocalizedName(Names[i]+"_Chain_Helmet")); i++;
		BlackBronzeChainHelmet = 	(new ItemCustomArmor(ArmorStats.BlackBronzeChain, proxy.getArmorRenderID("blackbronze"), 0, 50,3).setUnlocalizedName(Names[i]+"_Chain_Helmet")); i++;
		BlackSteelChainHelmet = 	(new ItemCustomArmor(ArmorStats.BlackSteelChain, proxy.getArmorRenderID("blacksteel"), 0, 50,3).setUnlocalizedName(Names[i]+"_Chain_Helmet")); i++;
		BlueSteelChainHelmet = 		(new ItemCustomArmor(ArmorStats.BlueSteelChain, proxy.getArmorRenderID("bluesteel"), 0, 50,3).setUnlocalizedName(Names[i]+"_Chain_Helmet")); i++;
		BronzeChainHelmet = 		(new ItemCustomArmor(ArmorStats.BronzeChain, proxy.getArmorRenderID("bronze"), 0, 50,3).setUnlocalizedName(Names[i]+"_Chain_Helmet")); i++;
		CopperChainHelmet = 		(new ItemCustomArmor(ArmorStats.CopperChain, proxy.getArmorRenderID("copper"), 0, 50,3).setUnlocalizedName(Names[i]+"_Chain_Helmet")); i++;
		WroughtIronChainHelmet = 	(new ItemCustomArmor(ArmorStats.WroughtIronChain, proxy.getArmorRenderID("wroughtiron"), 0, 50,3).setUnlocalizedName(Names[i]+"_Chain_Helmet")); i++;
		RedSteelChainHelmet = 		(new ItemCustomArmor(ArmorStats.RedSteelChain, proxy.getArmorRenderID("redsteel"), 0, 50,3).setUnlocalizedName(Names[i]+"_Chain_Helmet")); i++;
		SteelChainHelmet = 			(new ItemCustomArmor(ArmorStats.SteelChain, proxy.getArmorRenderID("steel"), 0, 50,3).setUnlocalizedName(Names[i]+"_Chain_Helmet"));
	}
}
