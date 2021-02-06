package terramisc.core;

import com.dunk.tfc.CommonProxy;
import com.dunk.tfc.Core.Metal.Alloy;
import com.dunk.tfc.Core.Metal.AlloyManager;
import com.dunk.tfc.Core.Metal.MetalRegistry;
import com.dunk.tfc.Food.ItemFoodMeat;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.ItemIngot;
import com.dunk.tfc.Items.ItemMeltedMetal;
import com.dunk.tfc.Items.ItemMetalSheet;
import com.dunk.tfc.Items.ItemMetalSheet2x;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Metal;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import terramisc.common.ArmorStats;
import terramisc.common.GlobalTFCM;
import terramisc.items.ItemBowlTallow;
import terramisc.items.ItemMetalTFCM;
import terramisc.items.ItemTFCM;
import terramisc.items.ItemTallowDye;
import terramisc.items.ItemToolMold;
import terramisc.items.food.CustomSeedsTFCM;
import terramisc.items.food.ItemBottleSoyMilk;
import terramisc.items.food.ItemPie;
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

public class TFCMItemsSetup extends TFCMItems {
    public static void ItemSetup() {
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


        System.out.println("[" + TFCMDetails.ModName + "] Registering Items."); //Beginning of item registering

        /* TODO Make these items proper metal
         * add lead pewter casing, retain iron casing for legacy purposes
         * replace iron with pewter in compass recipe
         */
        casingBrass = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Brass", 100).setUnlocalizedName("CasingBrass");
        casingIron = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Wrought Iron", 100).setUnlocalizedName("CasingIron");
        casingRoseGold = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Rose Gold", 100).setUnlocalizedName("CasingRoseGold");
        casingGold = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Gold", 100).setUnlocalizedName("CasingGold");
        casingSilver = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Silver", 100).setUnlocalizedName("CasingSilver");
        casingLeadPewter = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Lead Pewter", 100).setUnlocalizedName("CasingLeadPewter");

        gear = new ItemMetalTFCM(EnumSize.TINY, EnumWeight.MEDIUM, "Wrought Iron", 100).setUnlocalizedName("Gear");

        caseinGlue = new ItemTFCM(EnumSize.TINY, EnumWeight.LIGHT).setUnlocalizedName("CaseinGlue");

        pistonBase = new ItemTFCM(EnumSize.LARGE, EnumWeight.HEAVY).setUnlocalizedName("PistonBase");
        circuit = new ItemTFCM(EnumSize.SMALL, EnumWeight.MEDIUM).setUnlocalizedName("Circuit");

        sinewFiber = new ItemTFCM(EnumSize.TINY, EnumWeight.LIGHT).setUnlocalizedName("SinewFiber");
        sinewString = new ItemTFCM(EnumSize.TINY, EnumWeight.LIGHT).setUnlocalizedName("SinewString");
        sinewBowString = new ItemTFCM(EnumSize.SMALL, EnumWeight.LIGHT).setUnlocalizedName("SinewBowString");

        bowLimb = new ItemTFCM(EnumSize.LARGE, EnumWeight.MEDIUM).setUnlocalizedName("BowLimb");

        suet = new ItemTFCM(EnumSize.SMALL, EnumWeight.MEDIUM).setUnlocalizedName("Suet");
        bowlSuet = new ItemTFCM(EnumSize.LARGE, EnumWeight.MEDIUM).setUnlocalizedName("BowlSuet").setMaxStackSize(1);
        bowlTallow = new ItemBowlTallow().setUnlocalizedName("BowlTallow");

        brownDye = new ItemTFCM(EnumSize.SMALL, EnumWeight.MEDIUM).setUnlocalizedName("BrownDye");
        ironDust = new ItemTFCM(EnumSize.SMALL, EnumWeight.HEAVY).setUnlocalizedName("IronDust");
        tallowDye = new ItemTallowDye().setUnlocalizedName("TallowDye");

        //Bows and Such
        longBow = new ItemCustomLongbow().setUnlocalizedName("LongBow");
        crossBow = new ItemCustomCrossbow().setUnlocalizedName("CrossBow");

        //Halberds
        halberd_BismuthBronze = new ItemCustomHalberd(BismuthBronzeToolMaterial, BismuthBronzeToolMaterial.getDamageVsEntity() * 1.25f).setAttackSpeed(30).setUnlocalizedName("Halberd_BismuthBronze").setMaxDamage(BismuthBronzeUses);
        halberd_BlackBronze = new ItemCustomHalberd(BlackBronzeToolMaterial, BlackBronzeToolMaterial.getDamageVsEntity() * 1.25f).setAttackSpeed(30).setUnlocalizedName("Halberd_BlackBronze").setMaxDamage(BlackBronzeUses);
        halberd_BlackSteel = new ItemCustomHalberd(BlackSteelToolMaterial, BlackSteelToolMaterial.getDamageVsEntity() * 1.25f).setAttackSpeed(30).setUnlocalizedName("Halberd_BlackSteel").setMaxDamage(BlackSteelUses);
        halberd_BlueSteel = new ItemCustomHalberd(BlueSteelToolMaterial, BlueSteelToolMaterial.getDamageVsEntity() * 1.25f).setAttackSpeed(30).setUnlocalizedName("Halberd_BlueSteel").setMaxDamage(BlueSteelUses);
        halberd_Bronze = new ItemCustomHalberd(BronzeToolMaterial, BronzeToolMaterial.getDamageVsEntity() * 1.25f).setAttackSpeed(30).setUnlocalizedName("Halberd_Bronze").setMaxDamage(BronzeUses);
        halberd_Copper = new ItemCustomHalberd(CopperToolMaterial, CopperToolMaterial.getDamageVsEntity() * 1.25f).setAttackSpeed(30).setUnlocalizedName("Halberd_Copper").setMaxDamage(CopperUses);
        halberd_WroughtIron = new ItemCustomHalberd(IronToolMaterial, IronToolMaterial.getDamageVsEntity() * 1.25f).setAttackSpeed(30).setUnlocalizedName("Halberd_WroughtIron").setMaxDamage(WroughtIronUses);
        halberd_RedSteel = new ItemCustomHalberd(RedSteelToolMaterial, RedSteelToolMaterial.getDamageVsEntity() * 1.25f).setAttackSpeed(30).setUnlocalizedName("Halberd_RedSteel").setMaxDamage(RedSteelUses);
        halberd_Steel = new ItemCustomHalberd(SteelToolMaterial, SteelToolMaterial.getDamageVsEntity() * 1.25f).setAttackSpeed(30).setUnlocalizedName("Halberd_Steel").setMaxDamage(SteelUses);

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
        warHammer_BismuthBronze = new ItemCustomWarHammer(BismuthBronzeToolMaterial, BismuthBronzeToolMaterial.getDamageVsEntity() * 1.25f, EnumDamageType.CRUSHING).setAttackSpeed(30).setUnlocalizedName("WarHammer_BismuthBronze").setMaxDamage(BismuthBronzeUses);
        warHammer_BlackBronze = new ItemCustomWarHammer(BlackBronzeToolMaterial, BlackBronzeToolMaterial.getDamageVsEntity() * 1.25f, EnumDamageType.CRUSHING).setAttackSpeed(30).setUnlocalizedName("WarHammer_BlackBronze").setMaxDamage(BlackBronzeUses);
        warHammer_BlackSteel = new ItemCustomWarHammer(BlackSteelToolMaterial, BlackSteelToolMaterial.getDamageVsEntity() * 1.25f, EnumDamageType.CRUSHING).setAttackSpeed(30).setUnlocalizedName("WarHammer_BlackSteel").setMaxDamage(BlackSteelUses);
        warHammer_BlueSteel = new ItemCustomWarHammer(BlueSteelToolMaterial, BlueSteelToolMaterial.getDamageVsEntity() * 1.25f, EnumDamageType.CRUSHING).setAttackSpeed(30).setUnlocalizedName("WarHammer_BlueSteel").setMaxDamage(BlueSteelUses);
        warHammer_Bronze = new ItemCustomWarHammer(BronzeToolMaterial, BronzeToolMaterial.getDamageVsEntity() * 1.25f, EnumDamageType.CRUSHING).setAttackSpeed(30).setUnlocalizedName("WarHammer_Bronze").setMaxDamage(BronzeUses);
        warHammer_Copper = new ItemCustomWarHammer(CopperToolMaterial, CopperToolMaterial.getDamageVsEntity() * 1.25f, EnumDamageType.CRUSHING).setAttackSpeed(30).setUnlocalizedName("WarHammer_Copper").setMaxDamage(CopperUses);
        warHammer_WroughtIron = new ItemCustomWarHammer(IronToolMaterial, IronToolMaterial.getDamageVsEntity() * 1.25f, EnumDamageType.CRUSHING).setAttackSpeed(30).setUnlocalizedName("WarHammer_WroughtIron").setMaxDamage(WroughtIronUses);
        warHammer_RedSteel = new ItemCustomWarHammer(RedSteelToolMaterial, RedSteelToolMaterial.getDamageVsEntity() * 1.25f, EnumDamageType.CRUSHING).setAttackSpeed(30).setUnlocalizedName("WarHammer_RedSteel").setMaxDamage(RedSteelUses);
        warHammer_Steel = new ItemCustomWarHammer(SteelToolMaterial, SteelToolMaterial.getDamageVsEntity() * 1.25f, EnumDamageType.CRUSHING).setAttackSpeed(30).setUnlocalizedName("WarHammer_Steel").setMaxDamage(SteelUses);

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
        poniard_BismuthBronze = new ItemCustomPoniard(BismuthBronzeToolMaterial, BismuthBronzeToolMaterial.getDamageVsEntity() * 0.85f, EnumDamageType.PIERCING).setAttackSpeed(7).setUnlocalizedName("Poniard_BismuthBronze").setMaxDamage(BismuthBronzeUses);
        poniard_BlackBronze = new ItemCustomPoniard(BlackBronzeToolMaterial, BlackBronzeToolMaterial.getDamageVsEntity() * 0.85f, EnumDamageType.PIERCING).setAttackSpeed(7).setUnlocalizedName("Poniard_BlackBronze").setMaxDamage(BlackBronzeUses);
        poniard_BlackSteel = new ItemCustomPoniard(BlackSteelToolMaterial, BlackSteelToolMaterial.getDamageVsEntity() * 0.85f, EnumDamageType.PIERCING).setAttackSpeed(7).setUnlocalizedName("Poniard_BlackSteel").setMaxDamage(BlackSteelUses);
        poniard_BlueSteel = new ItemCustomPoniard(BlueSteelToolMaterial, BlueSteelToolMaterial.getDamageVsEntity() * 0.85f, EnumDamageType.PIERCING).setAttackSpeed(7).setUnlocalizedName("Poniard_BlueSteel").setMaxDamage(BlueSteelUses);
        poniard_Bronze = new ItemCustomPoniard(BronzeToolMaterial, BronzeToolMaterial.getDamageVsEntity() * 0.85f, EnumDamageType.PIERCING).setAttackSpeed(7).setUnlocalizedName("Poniard_Bronze").setMaxDamage(BronzeUses);
        poniard_Copper = new ItemCustomPoniard(CopperToolMaterial, CopperToolMaterial.getDamageVsEntity() * 0.85f, EnumDamageType.PIERCING).setAttackSpeed(7).setUnlocalizedName("Poniard_Copper").setMaxDamage(CopperUses);
        poniard_WroughtIron = new ItemCustomPoniard(IronToolMaterial, IronToolMaterial.getDamageVsEntity() * 0.85f, EnumDamageType.PIERCING).setAttackSpeed(7).setUnlocalizedName("Poniard_WroughtIron").setMaxDamage(WroughtIronUses);
        poniard_RedSteel = new ItemCustomPoniard(RedSteelToolMaterial, RedSteelToolMaterial.getDamageVsEntity() * 0.85f, EnumDamageType.PIERCING).setAttackSpeed(7).setUnlocalizedName("Poniard_RedSteel").setMaxDamage(RedSteelUses);
        poniard_Steel = new ItemCustomPoniard(SteelToolMaterial, SteelToolMaterial.getDamageVsEntity() * 0.85f, EnumDamageType.PIERCING).setAttackSpeed(7).setUnlocalizedName("Poniard_Steel").setMaxDamage(SteelUses);

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
        halberd_Mold = new ItemToolMold().setMetaNames(new String[]{"Clay_Mold_Halberd", "Ceramic_Mold_Halberd",
                "Ceramic_Mold_Halberd_Copper", "Ceramic_Mold_Halberd_Bronze", "Ceramic_Mold_Halberd_BismuthBronze", "Ceramic_Mold_Halberd_BlackBronze"}).setUnlocalizedName("halberd_Mold");
        warHammer_Mold = new ItemToolMold().setMetaNames(new String[]{"Clay_Mold_WarHammer", "Ceramic_Mold_WarHammer",
                "Ceramic_Mold_WarHammer_Copper", "Ceramic_Mold_WarHammer_Bronze", "Ceramic_Mold_WarHammer_BismuthBronze", "Ceramic_Mold_WarHammer_BlackBronze"}).setUnlocalizedName("warHammer_Mold");
        poniard_Mold = new ItemToolMold().setMetaNames(new String[]{"Clay_Mold_Poniard", "Ceramic_Mold_Poniard",
                "Ceramic_Mold_Poniard_Copper", "Ceramic_Mold_Poniard_Bronze", "Ceramic_Mold_Poniard_BismuthBronze", "Ceramic_Mold_Poniard_BlackBronze"}).setUnlocalizedName("poniard_Mold");
        arrow_Mold = new ItemToolMold().setMetaNames(new String[]{"Clay_Mold_Arrow", "Ceramic_Mold_Arrow",
                "Ceramic_Mold_Arrow_Copper", "Ceramic_Mold_Arrow_Bronze", "Ceramic_Mold_Arrow_BismuthBronze", "Ceramic_Mold_Arrow_BlackBronze"}).setUnlocalizedName("arrow_Mold");
        bolt_Mold = new ItemToolMold().setMetaNames(new String[]{"Clay_Mold_Bolt", "Ceramic_Mold_Bolt",
                "Ceramic_Mold_Bolt_Copper", "Ceramic_Mold_Bolt_Bronze", "Ceramic_Mold_Bolt_BismuthBronze", "Ceramic_Mold_Bolt_BlackBronze"}).setUnlocalizedName("bolt_Mold");

        //Armor Ingrediants
        coil_BismuthBronze = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Bismuth Bronze", 100).setUnlocalizedName("Coil_BismuthBronze");
        coil_BlackBronze = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Black Bronze", 100).setUnlocalizedName("Coil_BlackBronze");
        coil_BlackSteel = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Black Steel", 100).setUnlocalizedName("Coil_BlackSteel");
        coil_BlueSteel = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Blue Steel", 100).setUnlocalizedName("Coil_BlueSteel");
        coil_Bronze = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Bronze", 100).setUnlocalizedName("Coil_Bronze");
        coil_Copper = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Copper", 100).setUnlocalizedName("Coil_Copper");
        coil_WroughtIron = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Wrought Iron", 100).setUnlocalizedName("Coil_WroughtIron");
        coil_RedSteel = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Red Steel", 100).setUnlocalizedName("Coil_RedSteel");
        coil_Steel = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Steel", 100).setUnlocalizedName("Coil_Steel");

        link_BismuthBronze = new ItemMetalTFCM(EnumSize.TINY, EnumWeight.HEAVY, "Bismuth Bronze", (short) 6.25).setUnlocalizedName("Link_BismuthBronze");
        link_BlackBronze = new ItemMetalTFCM(EnumSize.TINY, EnumWeight.HEAVY, "Black Bronze", (short) 6.25).setUnlocalizedName("Link_BlackBronze");
        link_BlackSteel = new ItemMetalTFCM(EnumSize.TINY, EnumWeight.HEAVY, "Black Steel", (short) 6.25).setUnlocalizedName("Link_BlackSteel");
        link_BlueSteel = new ItemMetalTFCM(EnumSize.TINY, EnumWeight.HEAVY, "Blue Steel", (short) 6.25).setUnlocalizedName("Link_BlueSteel");
        link_Bronze = new ItemMetalTFCM(EnumSize.TINY, EnumWeight.HEAVY, "Bronze", (short) 6.25).setUnlocalizedName("Link_Bronze");
        link_Copper = new ItemMetalTFCM(EnumSize.TINY, EnumWeight.HEAVY, "Copper", (short) 6.25).setUnlocalizedName("Link_Copper");
        link_WroughtIron = new ItemMetalTFCM(EnumSize.TINY, EnumWeight.HEAVY, "Wrought Iron", (short) 6.25).setUnlocalizedName("Link_WroughtIron");
        link_RedSteel = new ItemMetalTFCM(EnumSize.TINY, EnumWeight.HEAVY, "Red Steel", (short) 6.25).setUnlocalizedName("Link_RedSteel");
        link_Steel = new ItemMetalTFCM(EnumSize.TINY, EnumWeight.HEAVY, "Steel", (short) 6.25).setUnlocalizedName("Link_Steel");

        chain_Square_BismuthBronze = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Bismuth Bronze", 25).setUnlocalizedName("Chain_Square_BismuthBronze");
        chain_Square_BlackBronze = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Black Bronze", 25).setUnlocalizedName("Chain_Square_BlackBronze");
        chain_Square_BlackSteel = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Black Steel", 25).setUnlocalizedName("Chain_Square_BlackSteel");
        chain_Square_BlueSteel = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Blue Steel", 25).setUnlocalizedName("Chain_Square_BlueSteel");
        chain_Square_Bronze = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Bronze", 25).setUnlocalizedName("Chain_Square_Bronze");
        chain_Square_Copper = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Copper", 25).setUnlocalizedName("Chain_Square_Copper");
        chain_Square_WroughtIron = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Wrought Iron", 25).setUnlocalizedName("Chain_Square_WroughtIron");
        chain_Square_RedSteel = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Red Steel", 25).setUnlocalizedName("Chain_Square_RedSteel");
        chain_Square_Steel = new ItemMetalTFCM(EnumSize.SMALL, EnumWeight.HEAVY, "Steel", 25).setUnlocalizedName("Chain_Square_Steel");

        chain_Sheet_BismuthBronze = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Bismuth Bronze", 100).setUnlocalizedName("Chain_Sheet_BismuthBronze");
        chain_Sheet_BlackBronze = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Black Bronze", 100).setUnlocalizedName("Chain_Sheet_BlackBronze");
        chain_Sheet_BlackSteel = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Black Steel", 100).setUnlocalizedName("Chain_Sheet_BlackSteel");
        chain_Sheet_BlueSteel = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Blue Steel", 100).setUnlocalizedName("Chain_Sheet_BlueSteel");
        chain_Sheet_Bronze = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Bronze", 100).setUnlocalizedName("Chain_Sheet_Bronze");
        chain_Sheet_Copper = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Copper", 100).setUnlocalizedName("Chain_Sheet_Copper");
        chain_Sheet_WroughtIron = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Wrought Iron", 100).setUnlocalizedName("Chain_Sheet_WroughtIron");
        chain_Sheet_RedSteel = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Red Steel", 100).setUnlocalizedName("Chain_Sheet_RedSteel");
        chain_Sheet_Steel = new ItemMetalTFCM(EnumSize.MEDIUM, EnumWeight.HEAVY, "Steel", 100).setUnlocalizedName("Chain_Sheet_Steel");

        //Inventory items
        itemQuiver = new ItemCustomQuiver().setUnlocalizedName("Quiver");

        SetupArmor();
        SetupFood();
        SetupMetal();
        registers();

        System.out.println("[" + TFCMDetails.ModName + "] Done Registering Items.");
    }

    public static void SetupFood() {
        //Drinks
        bottleSoyMilk = new ItemBottleSoyMilk().setUnlocalizedName("BottleSoyMilk");

        //Soy
        soyPaste = new ItemFoodTFC(EnumFoodGroup.Protein, 10, 0, 0, 0, 40, false).setUnlocalizedName("SoybeanPaste");

        //Crops, See BlockCropTFCM.class CropRegistry.class and CropRender.class

        seedsCayenne = new CustomSeedsTFCM("cayenne").setFolder("food/unused/").setUnlocalizedName("SeedsCayenne").setTextureName("img139");
        greenCayenne = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 20, true).setUnlocalizedName("GreenCayenne");
        redCayenne = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 45, true).setFolder("food/unused/").setUnlocalizedName("RedCayenne").setTextureName("img105");

        seedsCoffee = new CustomSeedsTFCM("coffee").setFolder("food/unused/").setUnlocalizedName("SeedsCoffee").setTextureName("img141");
        coffeeCherries = new ItemFoodTFC(EnumFoodGroup.Fruit, 10, 5, 0, 0, 5, true).setUnlocalizedName("CoffeeCherries");
        greenCoffee = new ItemFoodTFC(EnumFoodGroup.None, 0, 0, 0, 15, 0, false, false).setUnlocalizedName("GreenCoffee");
        coffee = new ItemFoodTFC(EnumFoodGroup.None, 0, 0, 0, 25, 0, false, false).setFolder("food/unused/").setUnlocalizedName("Coffee").setTextureName("img78");
        groundCoffee = new ItemFoodTFC(EnumFoodGroup.None, 0, 0, 0, 25, 0, false, false).setUnlocalizedName("GroundCoffee");


        seedsSweetPotato = new CustomSeedsTFCM("sweet potato").setFolder("food/unused/").setUnlocalizedName("SeedsSweetPotato").setTextureName("img129");
        sweetPotato = new ItemFoodTFC(EnumFoodGroup.Vegetable, 12, 0, 0, 5, 0, true).setUnlocalizedName("SweetPotato");

        seedsHops = new CustomSeedsTFCM("hops").setFolder("food/unused/").setUnlocalizedName("SeedsHops").setTextureName("img137");
        hops = new ItemFoodTFC(EnumFoodGroup.Fruit, 0, 0, 0, 20, 0, false, false).setUnlocalizedName("Hops");

        //Meat
        bearRaw = new ItemFoodMeat(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, false, false).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setUnlocalizedName("Bear");

        //Meals
        pie = new ItemPie().setUnlocalizedName("Pie");
        // TODO add items for Casserole, Soup, and Stew
    }

    public static void SetupArmor() {
        String[] Names = {"BismuthBronze", "BlackBronze", "BlackSteel", "BlueSteel", "Bronze", "Copper", "WroughtIron", "RedSteel", "Steel"};
        CommonProxy proxy = TerraFirmaCraft.proxy;
        int i = 0;

        i = 0;
        BismuthBronzeChainGreaves = (new ItemCustomArmor(ArmorStats.BismuthBronzeChain, proxy.getArmorRenderID("bismuthbronze"), 2, 50, 1).setUnlocalizedName(Names[i] + "_Chain_Greaves"));
        i++;
        BlackBronzeChainGreaves = (new ItemCustomArmor(ArmorStats.BlackBronzeChain, proxy.getArmorRenderID("blackbronze"), 2, 50, 1).setUnlocalizedName(Names[i] + "_Chain_Greaves"));
        i++;
        BlackSteelChainGreaves = (new ItemCustomArmor(ArmorStats.BlackSteelChain, proxy.getArmorRenderID("blacksteel"), 2, 50, 1).setUnlocalizedName(Names[i] + "_Chain_Greaves"));
        i++;
        BlueSteelChainGreaves = (new ItemCustomArmor(ArmorStats.BlueSteelChain, proxy.getArmorRenderID("bluesteel"), 2, 50, 1).setUnlocalizedName(Names[i] + "_Chain_Greaves"));
        i++;
        BronzeChainGreaves = (new ItemCustomArmor(ArmorStats.BronzeChain, proxy.getArmorRenderID("bronze"), 2, 50, 1).setUnlocalizedName(Names[i] + "_Chain_Greaves"));
        i++;
        CopperChainGreaves = (new ItemCustomArmor(ArmorStats.CopperChain, proxy.getArmorRenderID("copper"), 2, 50, 1).setUnlocalizedName(Names[i] + "_Chain_Greaves"));
        i++;
        WroughtIronChainGreaves = (new ItemCustomArmor(ArmorStats.WroughtIronChain, proxy.getArmorRenderID("wroughtiron"), 2, 50, 1).setUnlocalizedName(Names[i] + "_Chain_Greaves"));
        i++;
        RedSteelChainGreaves = (new ItemCustomArmor(ArmorStats.RedSteelChain, proxy.getArmorRenderID("redsteel"), 2, 50, 1).setUnlocalizedName(Names[i] + "_Chain_Greaves"));
        i++;
        SteelChainGreaves = (new ItemCustomArmor(ArmorStats.SteelChain, proxy.getArmorRenderID("steel"), 2, 50, 1).setUnlocalizedName(Names[i] + "_Chain_Greaves"));

        i = 0;
        BismuthBronzeChainChestplate = (new ItemCustomArmor(ArmorStats.BismuthBronzeChain, proxy.getArmorRenderID("bismuthbronze"), 1, 50, 2).setUnlocalizedName(Names[i] + "_Chain_Chestplate"));
        i++;
        BlackBronzeChainChestplate = (new ItemCustomArmor(ArmorStats.BlackBronzeChain, proxy.getArmorRenderID("blackbronze"), 1, 50, 2).setUnlocalizedName(Names[i] + "_Chain_Chestplate"));
        i++;
        BlackSteelChainChestplate = (new ItemCustomArmor(ArmorStats.BlackSteelChain, proxy.getArmorRenderID("blacksteel"), 1, 50, 2).setUnlocalizedName(Names[i] + "_Chain_Chestplate"));
        i++;
        BlueSteelChainChestplate = (new ItemCustomArmor(ArmorStats.BlueSteelChain, proxy.getArmorRenderID("bluesteel"), 1, 50, 2).setUnlocalizedName(Names[i] + "_Chain_Chestplate"));
        i++;
        BronzeChainChestplate = (new ItemCustomArmor(ArmorStats.BronzeChain, proxy.getArmorRenderID("bronze"), 1, 50, 2).setUnlocalizedName(Names[i] + "_Chain_Chestplate"));
        i++;
        CopperChainChestplate = (new ItemCustomArmor(ArmorStats.CopperChain, proxy.getArmorRenderID("copper"), 1, 50, 2).setUnlocalizedName(Names[i] + "_Chain_Chestplate"));
        i++;
        WroughtIronChainChestplate = (new ItemCustomArmor(ArmorStats.WroughtIronChain, proxy.getArmorRenderID("wroughtiron"), 1, 50, 2).setUnlocalizedName(Names[i] + "_Chain_Chestplate"));
        i++;
        RedSteelChainChestplate = (new ItemCustomArmor(ArmorStats.RedSteelChain, proxy.getArmorRenderID("redsteel"), 1, 50, 2).setUnlocalizedName(Names[i] + "_Chain_Chestplate"));
        i++;
        SteelChainChestplate = (new ItemCustomArmor(ArmorStats.SteelChain, proxy.getArmorRenderID("steel"), 1, 50, 2).setUnlocalizedName(Names[i] + "_Chain_Chestplate"));

        i = 0;
        BismuthBronzeChainHelmet = (new ItemCustomArmor(ArmorStats.BismuthBronzeChain, proxy.getArmorRenderID("bismuthbronze"), 0, 50, 3).setUnlocalizedName(Names[i] + "_Chain_Helmet"));
        i++;
        BlackBronzeChainHelmet = (new ItemCustomArmor(ArmorStats.BlackBronzeChain, proxy.getArmorRenderID("blackbronze"), 0, 50, 3).setUnlocalizedName(Names[i] + "_Chain_Helmet"));
        i++;
        BlackSteelChainHelmet = (new ItemCustomArmor(ArmorStats.BlackSteelChain, proxy.getArmorRenderID("blacksteel"), 0, 50, 3).setUnlocalizedName(Names[i] + "_Chain_Helmet"));
        i++;
        BlueSteelChainHelmet = (new ItemCustomArmor(ArmorStats.BlueSteelChain, proxy.getArmorRenderID("bluesteel"), 0, 50, 3).setUnlocalizedName(Names[i] + "_Chain_Helmet"));
        i++;
        BronzeChainHelmet = (new ItemCustomArmor(ArmorStats.BronzeChain, proxy.getArmorRenderID("bronze"), 0, 50, 3).setUnlocalizedName(Names[i] + "_Chain_Helmet"));
        i++;
        CopperChainHelmet = (new ItemCustomArmor(ArmorStats.CopperChain, proxy.getArmorRenderID("copper"), 0, 50, 3).setUnlocalizedName(Names[i] + "_Chain_Helmet"));
        i++;
        WroughtIronChainHelmet = (new ItemCustomArmor(ArmorStats.WroughtIronChain, proxy.getArmorRenderID("wroughtiron"), 0, 50, 3).setUnlocalizedName(Names[i] + "_Chain_Helmet"));
        i++;
        RedSteelChainHelmet = (new ItemCustomArmor(ArmorStats.RedSteelChain, proxy.getArmorRenderID("redsteel"), 0, 50, 3).setUnlocalizedName(Names[i] + "_Chain_Helmet"));
        i++;
        SteelChainHelmet = (new ItemCustomArmor(ArmorStats.SteelChain, proxy.getArmorRenderID("steel"), 0, 50, 3).setUnlocalizedName(Names[i] + "_Chain_Helmet"));
    }

    public static void SetupMetal() {
        /* TODO Create custom TFCM variants of sheet item/te/block for proper texture usage
         * Sheet metalID only used for rendering currently Lead Pewter renders as Lead (ID 10)
         */
        leadPewterIngot = new ItemIngot().setUnlocalizedName("LeadPewterIngot");
        leadPewterIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("LeadPewterDoubleIngot")).setSize(EnumSize.LARGE).setMetal("Lead Pewter", 200);
        leadPewterUnshaped = new ItemMeltedMetal().setUnlocalizedName("LeadPewterUnshaped");
        leadPewterSheet = ((ItemMetalSheet) new ItemMetalSheet(10).setUnlocalizedName("LeadPewterSheet")).setMetal("Lead Pewter", 200);
        leadPewterSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(10).setUnlocalizedName("LeadPewterDoubleSheet")).setMetal("Lead Pewter", 400);

        GlobalTFCM.LEADPEWTER = new Metal("Lead Pewter", TFCMItems.leadPewterIngot);

        MetalRegistry.instance.addMetal(GlobalTFCM.LEADPEWTER, Alloy.EnumTier.TierI);

        /** +-0.01 tolerance to hopefully negate rounding errors */
        Alloy leadpewter = new Alloy(GlobalTFCM.LEADPEWTER, Alloy.EnumTier.TierI);
        leadpewter.addIngred(Global.TIN, 79.99f, 90.01f);
        leadpewter.addIngred(Global.COPPER, 4.99f, 10.01f);
        leadpewter.addIngred(Global.LEAD, 4.99f, 10.01f);
        AlloyManager.INSTANCE.addAlloy(leadpewter);

        //Alloys lead pewter ingots to be used to alloy new lead pewter without explotation.
        Alloy leadpewterMix = new Alloy(GlobalTFCM.LEADPEWTER, Alloy.EnumTier.TierI);
        leadpewterMix.addIngred(Global.TIN, 79.99f, 90.01f);
        leadpewterMix.addIngred(Global.COPPER, 4.99f, 10.01f);
        leadpewterMix.addIngred(Global.LEAD, 4.99f, 10.01f);
        leadpewterMix.addIngred(GlobalTFCM.LEADPEWTER, 1.01f, 99.01f);
        AlloyManager.INSTANCE.addAlloy(leadpewterMix);
    }
}
