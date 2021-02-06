package terramisc.core;

import com.dunk.tfc.Core.Recipes;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.ItemQuiver;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Crafting.AnvilManager;
import com.dunk.tfc.api.Crafting.AnvilRecipe;
import com.dunk.tfc.api.Crafting.AnvilReq;
import com.dunk.tfc.api.Crafting.BarrelFireRecipe;
import com.dunk.tfc.api.Crafting.BarrelLiquidToLiquidRecipe;
import com.dunk.tfc.api.Crafting.BarrelManager;
import com.dunk.tfc.api.Crafting.BarrelRecipe;
import com.dunk.tfc.api.Crafting.ClothingManager;
import com.dunk.tfc.api.Crafting.CraftingManagerTFC;
import com.dunk.tfc.api.Crafting.KilnCraftingManager;
import com.dunk.tfc.api.Crafting.KilnRecipe;
import com.dunk.tfc.api.Crafting.PlanRecipe;
import com.dunk.tfc.api.Crafting.QuernManager;
import com.dunk.tfc.api.Crafting.QuernRecipe;
import com.dunk.tfc.api.Crafting.SewingPattern;
import com.dunk.tfc.api.Crafting.SewingRecipe;
import com.dunk.tfc.api.Enums.RuleEnum;
import com.dunk.tfc.api.HeatIndex;
import com.dunk.tfc.api.HeatRaw;
import com.dunk.tfc.api.HeatRegistry;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.TFCItems;
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
import terramisc.api.crafting.VatManager;
import terramisc.api.crafting.VatRecipeDoubleBoiler;
import terramisc.api.crafting.VatRecipeEvaporation;

import java.util.List;
import java.util.Map;

public class TFCMRecipes {
    public static final int WILDCARD_VALUE = OreDictionary.WILDCARD_VALUE;

    private static final AnvilManager anvilManager = AnvilManager.getInstance();
    private static final CraftingManagerTFC craftingManager = CraftingManagerTFC.getInstance();
    private static final HeatRegistry heatmanager = HeatRegistry.getInstance();

    // Plan values
    public static String PlanNameCasing = "casing";
    public static String PlanNameClockGear = "clockgear";
    public static String PlanNameCircuit = "circuit";
    public static String PlanNameHalberd = "halberd";
    public static String PlanNameWarHammer = "warhammer";
    public static String PlanNamePoniard = "poniard";
    public static String PlanNameCoil = "coil";
    public static String PlanNameLink = "link";
    public static String PlanNameBolt = "bolt";
    public static String PlanNameArrow = "arrow";

    public static void initialise() {
        System.out.println("[" + TFCMDetails.ModName + "] Registering Recipes");

        registerRecipes();
        registerBarrelRecipes();
        registerKilnRecipes();
        registerToolMolds();
        registerKnappingRecipes();
        registerQuernRecipes();
        registerHeatingRecipes();
        registerVatRecipes();
        registerClothingRecipes();

        System.out.println("[" + TFCMDetails.ModName + "] Done Registering Recipes");
    }

    public static void initialiseAnvil() {
        // check if the plans/recipes have already been initialized.
        if (TFCMRecipes.areAnvilRecipesInitialised()) return;

        System.out.println("[" + TFCMDetails.ModName + "] Registering Anvil Recipes");

        registerAnvilPlans();
        registerAnvilRecipes();

        System.out.println("[" + TFCMDetails.ModName + "] Done Registering Anvil Recipes");
    }

    public static boolean areAnvilRecipesInitialised() {
        Map<String, PlanRecipe> map = anvilManager.getPlans();

        return map != null && (map.containsKey(PlanNameCasing) ||
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

    private static void registerAnvilPlans() {
        //Plans
        anvilManager.addPlan(PlanNameCasing,
                new PlanRecipe(
                        new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST})
        );
        anvilManager.addPlan(PlanNameClockGear,
                new PlanRecipe(
                        new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.UPSETTHIRDFROMLAST})
        );
        anvilManager.addPlan(PlanNameCircuit,
                new PlanRecipe(
                        new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.PUNCHTHIRDFROMLAST})
        );
        anvilManager.addPlan(PlanNameHalberd,
                new PlanRecipe(
                        new RuleEnum[]{RuleEnum.BENDLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST})
        );
        anvilManager.addPlan(PlanNameWarHammer,
                new PlanRecipe(
                        new RuleEnum[]{RuleEnum.BENDLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST})
        );
        anvilManager.addPlan(PlanNamePoniard,
                new PlanRecipe(
                        new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.ANY})
        );
        anvilManager.addPlan(PlanNameCoil,
                new PlanRecipe(
                        new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.DRAWTHIRDFROMLAST})
        );
        anvilManager.addPlan(PlanNameLink,
                new PlanRecipe(
                        new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.ANY, RuleEnum.ANY})
        );
        anvilManager.addPlan(PlanNameBolt,
                new PlanRecipe(
                        new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.ANY})
        );
        anvilManager.addPlan(PlanNameArrow,
                new PlanRecipe(
                        new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.PUNCHTHIRDFROMLAST})
        );

    }

    private static void registerAnvilRecipes() {
        //Recipes
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.brassIngot),
                        null, PlanNameCasing,
                        AnvilReq.COPPER,
                        new ItemStack(TFCMItems.casingBrass, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.wroughtIronIngot),
                        null,
                        PlanNameCasing,
                        AnvilReq.WROUGHTIRON,
                        new ItemStack(TFCMItems.casingIron, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.roseGoldIngot),
                        null, PlanNameCasing,
                        AnvilReq.COPPER,
                        new ItemStack(TFCMItems.casingRoseGold, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.goldIngot),
                        null, PlanNameCasing,
                        AnvilReq.COPPER,
                        new ItemStack(TFCMItems.casingGold, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.silverIngot),
                        null, PlanNameCasing,
                        AnvilReq.COPPER,
                        new ItemStack(TFCMItems.casingSilver, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCMItems.leadPewterIngot),
                        null, PlanNameCasing,
                        AnvilReq.COPPER,
                        new ItemStack(TFCMItems.casingLeadPewter, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );

        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.wroughtIronIngot),
                        null, PlanNameClockGear,
                        AnvilReq.WROUGHTIRON,
                        new ItemStack(TFCMItems.gear, 2)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );

        if (TFCMOptions.enableCraftingLogicTiles = true) {
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.goldSheet),
                            new ItemStack(Items.redstone),
                            PlanNameCircuit,
                            AnvilReq.COPPER,
                            new ItemStack(TFCMItems.circuit, 1)
                    ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
            );
        }
        if (TFCMOptions.enableCraftingCrossbow = true) {
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.bismuthBronzeIngot),
                            null,
                            PlanNameBolt,
                            AnvilReq.BRONZE,
                            new ItemStack(TFCMItems.bolt_BismuthBronze_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.blackBronzeIngot),
                            null,
                            PlanNameBolt,
                            AnvilReq.BRONZE,
                            new ItemStack(TFCMItems.bolt_BlackBronze_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.blackSteelIngot),
                            null,
                            PlanNameBolt,
                            AnvilReq.BLACKSTEEL,
                            new ItemStack(TFCMItems.bolt_BlackSteel_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.blueSteelIngot),
                            null, PlanNameBolt,
                            AnvilReq.BLUESTEEL,
                            new ItemStack(TFCMItems.bolt_BlueSteel_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.bronzeIngot),
                            null, PlanNameBolt,
                            AnvilReq.BRONZE,
                            new ItemStack(TFCMItems.bolt_Bronze_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.copperIngot),
                            null, PlanNameBolt,
                            AnvilReq.COPPER,
                            new ItemStack(TFCMItems.bolt_Copper_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(
                                    TFCItems.redSteelIngot),
                            null, PlanNameBolt,
                            AnvilReq.BLUESTEEL,
                            new ItemStack(TFCMItems.bolt_RedSteel_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.steelIngot),
                            null, PlanNameBolt,
                            AnvilReq.STEEL, new ItemStack(TFCMItems.bolt_Steel_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.wroughtIronIngot),
                            null, PlanNameBolt,
                            AnvilReq.WROUGHTIRON,
                            new ItemStack(TFCMItems.bolt_WroughtIron_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
        }
        if (TFCMOptions.enableCraftingLongbow = true) {
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.bismuthBronzeIngot),
                            null, PlanNameArrow,
                            AnvilReq.BRONZE,
                            new ItemStack(TFCMItems.arrow_BismuthBronze_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.blackBronzeIngot),
                            null, PlanNameArrow,
                            AnvilReq.BRONZE,
                            new ItemStack(TFCMItems.arrow_BlackBronze_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.blackSteelIngot),
                            null, PlanNameArrow,
                            AnvilReq.BLACKSTEEL,
                            new ItemStack(TFCMItems.arrow_BlackSteel_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.blueSteelIngot),
                            null,
                            PlanNameArrow,
                            AnvilReq.BLUESTEEL,
                            new ItemStack(TFCMItems.arrow_BlueSteel_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.bronzeIngot),
                            null,
                            PlanNameArrow,
                            AnvilReq.BRONZE,
                            new ItemStack(TFCMItems.arrow_Bronze_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.copperIngot),
                            null,
                            PlanNameArrow,
                            AnvilReq.COPPER,
                            new ItemStack(TFCMItems.arrow_Copper_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.redSteelIngot),
                            null,
                            PlanNameArrow,
                            AnvilReq.BLUESTEEL,
                            new ItemStack(TFCMItems.arrow_RedSteel_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.steelIngot),
                            null,
                            PlanNameArrow,
                            AnvilReq.STEEL,
                            new ItemStack(TFCMItems.arrow_Steel_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
            anvilManager.addRecipe(
                    new AnvilRecipe(
                            new ItemStack(TFCItems.wroughtIronIngot),
                            null,
                            PlanNameArrow,
                            AnvilReq.WROUGHTIRON,
                            new ItemStack(TFCMItems.arrow_WroughtIron_Head, 4)
                    ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
            );
        }
        //Halberds
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.bismuthBronzeIngot2x),
                        null,
                        PlanNameHalberd,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.halberd_BismuthBronze_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blackBronzeIngot2x),
                        null,
                        PlanNameHalberd,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.halberd_BlackBronze_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blackSteelIngot2x),
                        null,
                        PlanNameHalberd,
                        AnvilReq.BLACKSTEEL,
                        new ItemStack(TFCMItems.halberd_BlackSteel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blueSteelIngot2x),
                        null,
                        PlanNameHalberd,
                        AnvilReq.BLUESTEEL,
                        new ItemStack(TFCMItems.halberd_BlueSteel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.bronzeIngot2x),
                        null,
                        PlanNameHalberd,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.halberd_Bronze_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.copperIngot2x),
                        null,
                        PlanNameHalberd,
                        AnvilReq.COPPER,
                        new ItemStack(TFCMItems.halberd_Copper_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.redSteelIngot2x),
                        null,
                        PlanNameHalberd,
                        AnvilReq.BLUESTEEL,
                        new ItemStack(TFCMItems.halberd_RedSteel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.steelIngot2x),
                        null,
                        PlanNameHalberd,
                        AnvilReq.STEEL,
                        new ItemStack(TFCMItems.halberd_Steel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.wroughtIronIngot2x),
                        null,
                        PlanNameHalberd,
                        AnvilReq.WROUGHTIRON,
                        new ItemStack(TFCMItems.halberd_WroughtIron_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        //War Hammers
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.bismuthBronzeIngot2x),
                        null,
                        PlanNameWarHammer,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.warHammer_BismuthBronze_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blackBronzeIngot2x),
                        null,
                        PlanNameWarHammer,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.warHammer_BlackBronze_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blackSteelIngot2x),
                        null,
                        PlanNameWarHammer,
                        AnvilReq.BLACKSTEEL,
                        new ItemStack(TFCMItems.warHammer_BlackSteel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blueSteelIngot2x),
                        null,
                        PlanNameWarHammer,
                        AnvilReq.BLUESTEEL,
                        new ItemStack(TFCMItems.warHammer_BlueSteel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.bronzeIngot2x),
                        null,
                        PlanNameWarHammer,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.warHammer_Bronze_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.copperIngot2x),
                        null,
                        PlanNameWarHammer,
                        AnvilReq.COPPER,
                        new ItemStack(TFCMItems.warHammer_Copper_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.redSteelIngot2x),
                        null,
                        PlanNameWarHammer,
                        AnvilReq.BLUESTEEL,
                        new ItemStack(TFCMItems.warHammer_RedSteel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.steelIngot2x),
                        null,
                        PlanNameWarHammer,
                        AnvilReq.STEEL,
                        new ItemStack(TFCMItems.warHammer_Steel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.wroughtIronIngot2x),
                        null,
                        PlanNameWarHammer,
                        AnvilReq.WROUGHTIRON,
                        new ItemStack(TFCMItems.warHammer_WroughtIron_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        //Poniards
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.bismuthBronzeIngot),
                        null,
                        PlanNamePoniard,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.poniard_BismuthBronze_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blackBronzeIngot),
                        null,
                        PlanNamePoniard,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.poniard_BlackBronze_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blackSteelIngot),
                        null,
                        PlanNamePoniard,
                        AnvilReq.BLACKSTEEL,
                        new ItemStack(TFCMItems.poniard_BlackSteel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blueSteelIngot),
                        null,
                        PlanNamePoniard,
                        AnvilReq.BLUESTEEL,
                        new ItemStack(TFCMItems.poniard_BlueSteel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.bronzeIngot),
                        null,
                        PlanNamePoniard,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.poniard_Bronze_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.copperIngot),
                        null,
                        PlanNamePoniard,
                        AnvilReq.COPPER,
                        new ItemStack(TFCMItems.poniard_Copper_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.redSteelIngot),
                        null,
                        PlanNamePoniard,
                        AnvilReq.BLUESTEEL,
                        new ItemStack(TFCMItems.poniard_RedSteel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.steelIngot),
                        null,
                        PlanNamePoniard,
                        AnvilReq.STEEL,
                        new ItemStack(TFCMItems.poniard_Steel_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.wroughtIronIngot),
                        null,
                        PlanNamePoniard,
                        AnvilReq.WROUGHTIRON,
                        new ItemStack(TFCMItems.poniard_WroughtIron_Head, 1)
                ).addRecipeSkill(Global.SKILL_WEAPONSMITH)
        );
        //Coils
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.bismuthBronzeIngot),
                        null,
                        PlanNameCoil,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.coil_BismuthBronze, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blackBronzeIngot),
                        null,
                        PlanNameCoil,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.coil_BlackBronze, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blackSteelIngot),
                        null,
                        PlanNameCoil,
                        AnvilReq.BLACKSTEEL,
                        new ItemStack(TFCMItems.coil_BlackSteel, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.blueSteelIngot),
                        null,
                        PlanNameCoil,
                        AnvilReq.BLUESTEEL,
                        new ItemStack(TFCMItems.coil_BlueSteel, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.bronzeIngot),
                        null,
                        PlanNameCoil,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.coil_Bronze, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.copperIngot),
                        null,
                        PlanNameCoil,
                        AnvilReq.COPPER,
                        new ItemStack(TFCMItems.coil_Copper, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.wroughtIronIngot),
                        null,
                        PlanNameCoil,
                        AnvilReq.WROUGHTIRON,
                        new ItemStack(TFCMItems.coil_WroughtIron, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.redSteelIngot),
                        null,
                        PlanNameCoil,
                        AnvilReq.BLUESTEEL,
                        new ItemStack(TFCMItems.coil_RedSteel, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCItems.steelIngot),
                        null,
                        PlanNameCoil,
                        AnvilReq.STEEL,
                        new ItemStack(TFCMItems.coil_Steel, 1)
                ).addRecipeSkill(Global.SKILL_GENERAL_SMITHING)
        );
        //Links
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCMItems.coil_BismuthBronze),
                        null,
                        PlanNameLink,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.link_BismuthBronze, 16)
                ).addRecipeSkill(Global.SKILL_ARMORSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCMItems.coil_BlackBronze),
                        null,
                        PlanNameLink,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.link_BlackBronze, 16)
                ).addRecipeSkill(Global.SKILL_ARMORSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCMItems.coil_BlackSteel),
                        null,
                        PlanNameLink,
                        AnvilReq.BLACKSTEEL,
                        new ItemStack(TFCMItems.link_BlackSteel, 16)
                ).addRecipeSkill(Global.SKILL_ARMORSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCMItems.coil_BlueSteel),
                        null,
                        PlanNameLink,
                        AnvilReq.BLUESTEEL,
                        new ItemStack(TFCMItems.link_BlueSteel, 16)
                ).addRecipeSkill(Global.SKILL_ARMORSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCMItems.coil_Bronze),
                        null,
                        PlanNameLink,
                        AnvilReq.BRONZE,
                        new ItemStack(TFCMItems.link_Bronze, 16)
                ).addRecipeSkill(Global.SKILL_ARMORSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCMItems.coil_Copper),
                        null,
                        PlanNameLink,
                        AnvilReq.COPPER,
                        new ItemStack(TFCMItems.link_Copper, 16)
                ).addRecipeSkill(Global.SKILL_ARMORSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCMItems.coil_WroughtIron),
                        null,
                        PlanNameLink,
                        AnvilReq.WROUGHTIRON,
                        new ItemStack(TFCMItems.link_WroughtIron, 16)
                ).addRecipeSkill(Global.SKILL_ARMORSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCMItems.coil_RedSteel),
                        null,
                        PlanNameLink,
                        AnvilReq.BLUESTEEL,
                        new ItemStack(TFCMItems.link_RedSteel, 16)
                ).addRecipeSkill(Global.SKILL_ARMORSMITH)
        );
        anvilManager.addRecipe(
                new AnvilRecipe(
                        new ItemStack(TFCMItems.coil_Steel),
                        null,
                        PlanNameLink,
                        AnvilReq.STEEL,
                        new ItemStack(TFCMItems.link_Steel, 16)
                ).addRecipeSkill(Global.SKILL_ARMORSMITH)
        );
    }


    private static void registerRecipes() {
        //Item ItemStacks
        ItemStack smallMagnetite = new ItemStack(TFCItems.smallOreChunk, 1, 10);   // 10 unit ore
        ItemStack Gravel1 = new ItemStack(TFCBlocks.gravel, 1, WILDCARD_VALUE); //Used Since Gravel Ore Dictionary Support Doesn't Exist.
        ItemStack Gravel2 = new ItemStack(TFCBlocks.gravel2, 1, WILDCARD_VALUE);
        ItemStack Spring = new ItemStack(TFCMItems.coil_WroughtIron, 1);
        ItemStack CeramicBowl = new ItemStack(TFCItems.potteryBowl, 1, 1);
        ItemStack TallowBowl = new ItemStack(TFCMItems.bowlTallow, 1, WILDCARD_VALUE);

        if (TFCMOptions.enableCraftingPiston = true) {
            removeRecipe(new ItemStack(Blocks.piston));

            GameRegistry.addRecipe(
                    new ItemStack(Blocks.piston),
                    "H", "S", "B",
                    'H', TFCItems.wroughtIronSheet,
                    'S', TFCItems.tuyereWroughtIron,
                    'B', TFCMItems.pistonBase
            );
            GameRegistry.addShapelessRecipe(
                    new ItemStack(Blocks.sticky_piston),
                    new ItemStack(Blocks.piston),
                    new ItemStack(TFCMItems.caseinGlue)
            );
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(TFCMItems.pistonBase),
                            "PRP", "PPP",
                            'P', "woodLumber",
                            'R', Items.redstone)
            );
        }

        if (TFCMOptions.enableCraftingLogicTiles = true) {
            removeRecipe(new ItemStack(Items.repeater));
            removeRecipe(new ItemStack(Items.comparator));

            GameRegistry.addRecipe(
                    new ItemStack(Items.repeater),
                    "TCT",
                    'C', TFCMItems.circuit,
                    'T', Blocks.redstone_torch
            );
            GameRegistry.addRecipe(
                    new ItemStack(Items.comparator),
                    " T ", "TCT",
                    'C', TFCMItems.circuit,
                    'T', Blocks.redstone_torch
            );
        }

        if (TFCMOptions.enableCraftingLongbow = true) {
            //Longbow
            GameRegistry.addShapelessRecipe(
                    new ItemStack(TFCMItems.longBow),
                    new ItemStack(TFCMItems.bowLimb),
                    new ItemStack(TFCMItems.sinewBowString)
            );
            GameRegistry.addRecipe(
                    new ShapelessOreRecipe(
                            new ItemStack(TFCMItems.longBow),
                            new ItemStack(TFCMItems.caseinGlue),
                            new ItemStack(TFCMItems.longBow, 1, WILDCARD_VALUE),
                            "woodLumber")
            );
            //Arrows
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.arrow_BismuthBronze),
                    "H", "S", "F",
                    'H', TFCMItems.arrow_BismuthBronze_Head,
                    'S', TFCItems.stick,
                    'F', Items.feather
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.arrow_BlackBronze),
                    "H", "S", "F",
                    'H', TFCMItems.arrow_BlackBronze_Head,
                    'S', TFCItems.stick,
                    'F', Items.feather
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.arrow_BlackSteel),
                    "H", "S", "F",
                    'H', TFCMItems.arrow_BlackSteel_Head,
                    'S', TFCItems.stick,
                    'F', Items.feather
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.arrow_BlueSteel),
                    "H", "S", "F",
                    'H', TFCMItems.arrow_BlueSteel_Head,
                    'S', TFCItems.stick,
                    'F', Items.feather
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.arrow_Bronze),
                    "H", "S", "F",
                    'H', TFCMItems.arrow_Bronze_Head,
                    'S', TFCItems.stick,
                    'F', Items.feather
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.arrow_Copper),
                    "H", "S", "F",
                    'H', TFCMItems.arrow_Copper_Head,
                    'S', TFCItems.stick,
                    'F', Items.feather
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.arrow_RedSteel),
                    "H", "S", "F",
                    'H', TFCMItems.arrow_RedSteel_Head,
                    'S', TFCItems.stick,
                    'F', Items.feather
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.arrow_Steel),
                    "H", "S", "F",
                    'H', TFCMItems.arrow_Steel_Head,
                    'S', TFCItems.stick,
                    'F', Items.feather
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.arrow_WroughtIron),
                    "H", "S", "F",
                    'H', TFCMItems.arrow_WroughtIron_Head,
                    'S', TFCItems.stick,
                    'F', Items.feather
            );
            //Molds
            GameRegistry.addShapelessRecipe(
                    new ItemStack(TFCMItems.arrow_Copper_Head, 4),
                    new ItemStack(TFCMItems.arrow_Mold, 1, 2)
            );
            GameRegistry.addShapelessRecipe(
                    new ItemStack(TFCMItems.arrow_Bronze_Head, 4),
                    new ItemStack(TFCMItems.arrow_Mold, 1, 3)
            );
            GameRegistry.addShapelessRecipe(
                    new ItemStack(TFCMItems.arrow_BismuthBronze_Head, 4),
                    new ItemStack(TFCMItems.arrow_Mold, 1, 4)
            );
            GameRegistry.addShapelessRecipe(
                    new ItemStack(TFCMItems.arrow_BlackBronze_Head, 4),
                    new ItemStack(TFCMItems.arrow_Mold, 1, 5)
            );
        }
        if (TFCMOptions.enableCraftingCrossbow = true) {
            //Crossbow
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(TFCMItems.crossBow),
                            "LBL", "SGS", "TLT",
                            'L', "woodLumber",
                            'B', TFCMItems.bowLimb,
                            'T', TFCItems.leather,
                            'S', TFCMItems.sinewString,
                            'G', TFCMItems.gear
                    )
            );
            GameRegistry.addRecipe(
                    new ShapelessOreRecipe(
                            new ItemStack(TFCMItems.crossBow),
                            new ItemStack(TFCMItems.caseinGlue),
                            new ItemStack(TFCMItems.crossBow, 1, WILDCARD_VALUE),
                            "woodLumber"
                    )
            );
            //Bolts
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.bolt_BismuthBronze),
                    "H", "S",
                    'H', TFCMItems.bolt_BismuthBronze_Head,
                    'S', TFCItems.stick
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.bolt_BlackBronze),
                    "H", "S",
                    'H', TFCMItems.bolt_BlackBronze_Head,
                    'S', TFCItems.stick
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.bolt_BlackSteel),
                    "H", "S",
                    'H', TFCMItems.bolt_BlackSteel_Head,
                    'S', TFCItems.stick
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.bolt_BlueSteel),
                    "H", "S",
                    'H', TFCMItems.bolt_BlueSteel_Head,
                    'S', TFCItems.stick
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.bolt_Bronze),
                    "H", "S",
                    'H', TFCMItems.bolt_Bronze_Head,
                    'S', TFCItems.stick
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.bolt_Copper),
                    "H", "S",
                    'H', TFCMItems.bolt_Copper_Head,
                    'S', TFCItems.stick
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.bolt_RedSteel),
                    "H", "S",
                    'H', TFCMItems.bolt_RedSteel_Head,
                    'S', TFCItems.stick
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.bolt_Steel),
                    "H", "S",
                    'H', TFCMItems.bolt_Steel_Head,
                    'S', TFCItems.stick
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMItems.bolt_WroughtIron),
                    "H", "S",
                    'H', TFCMItems.bolt_WroughtIron_Head,
                    'S', TFCItems.stick
            );
            //Molds
            GameRegistry.addShapelessRecipe(
                    new ItemStack(TFCMItems.bolt_Copper_Head, 4),
                    new ItemStack(TFCMItems.bolt_Mold, 1, 2)
            );
            GameRegistry.addShapelessRecipe(
                    new ItemStack(TFCMItems.bolt_Bronze_Head, 4),
                    new ItemStack(TFCMItems.bolt_Mold, 1, 3)
            );
            GameRegistry.addShapelessRecipe(
                    new ItemStack(TFCMItems.bolt_BismuthBronze_Head, 4),
                    new ItemStack(TFCMItems.bolt_Mold, 1, 4)
            );
            GameRegistry.addShapelessRecipe(
                    new ItemStack(TFCMItems.bolt_BlackBronze_Head, 4),
                    new ItemStack(TFCMItems.bolt_Mold, 1, 5)
            );
        }
        if (TFCMOptions.enableCraftingLongbow || TFCMOptions.enableCraftingCrossbow) {
            for (int j = 0; j < Recipes.knives.length; j++) {
                GameRegistry.addRecipe(
                        new ShapelessOreRecipe(
                                new ItemStack(TFCMItems.bowLimb),
                                new ItemStack(Recipes.knives[j], 1, 32767),
                                "logWood",
                                "logWood"
                        )
                );
            }
        }

        if (TFCMOptions.enableCrucibleEmptying = true) {
            GameRegistry.addShapelessRecipe(
                    new ItemStack(TFCBlocks.crucible),
                    new ItemStack(TFCBlocks.crucible)
            );
        }

        if (TFCMOptions.enableCraftingCompassClock = true) {
            removeRecipe(new ItemStack(Items.clock));
            removeRecipe(new ItemStack(Items.compass));

            GameRegistry.addRecipe(
                    new ItemStack(Items.clock),
                    "GPG", "HCH", "GSG",
                    'G', TFCMItems.gear,
                    'P', Blocks.glass_pane,
                    'H', TFCItems.stick,
                    'C', TFCMItems.casingBrass,
                    'S', Spring
            );
            GameRegistry.addRecipe(
                    new ItemStack(Items.clock),
                    "GPG", "HCH", "GSG",
                    'G', TFCMItems.gear,
                    'P', Blocks.glass_pane,
                    'H', TFCItems.stick,
                    'C', TFCMItems.casingRoseGold,
                    'S', Spring
            );
            GameRegistry.addRecipe(
                    new ItemStack(Items.clock),
                    "GPG", "HCH", "GSG",
                    'G', TFCMItems.gear,
                    'P', Blocks.glass_pane,
                    'H', TFCItems.stick,
                    'C', TFCMItems.casingGold,
                    'S', Spring
            );
            GameRegistry.addRecipe(
                    new ItemStack(Items.compass),
                    "P", "O", "C",
                    'P', Blocks.glass_pane,
                    'O', smallMagnetite,
                    'C', TFCMItems.casingLeadPewter
            );
            GameRegistry.addRecipe(
                    new ItemStack(Items.compass),
                    "P", "O", "C",
                    'P', Blocks.glass_pane,
                    'O', smallMagnetite,
                    'C', TFCMItems.casingSilver
            );
        }

        //Shaped
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.sinewString),
                "F", "F", "F",
                'F', TFCItems.sinew
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.sinewBowString),
                "SSS",
                'S', TFCMItems.sinewString
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMBlocks.blockTallowCandleOff),
                "W", "T",
                'W', TFCItems.woolYarn,
                'T', TallowBowl
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMBlocks.blockTallowCandleOff),
                "W", "T",
                'W', TFCItems.cottonYarn,
                'T', TallowBowl
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMBlocks.blockTallowCandleOff),
                "W", "T",
                'W', TFCItems.linenString,
                'T', TallowBowl
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMBlocks.blockTallowCandleOff),
                "W", "T",
                'W', TFCItems.silkString,
                'T', TallowBowl
        );

        if (TFCMOptions.enableGrassCordageCandles = true) {
            GameRegistry.addRecipe(
                    new ItemStack(TFCMBlocks.blockTallowCandleOff),
                    "W", "T",
                    'W', TFCItems.grassCordage,
                    'T', TallowBowl
            );
        }

        //Clay
        GameRegistry.addRecipe(
                new ItemStack(TFCMBlocks.blockClay),
                "CC", "CC",
                'C', TFCItems.clayBall
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 1),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeBlack"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 2),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeRed"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 3),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeGreen"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 4),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeBrown"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 5),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeBlue"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 6),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyePurple"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 7),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeCyan"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 8),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeSilver"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 9),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeGray"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 10),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyePink"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 11),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeLime"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 12),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeYellow"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 13),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeLightBlue"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 14),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeMagenta"
                )
        );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(TFCMBlocks.blockStainedClay, 8, 15),
                        "CCC", "CDC", "CCC",
                        'C', TFCMBlocks.blockStainedClay,
                        'D', "dyeOrange"
                )
        );

        //Shapeless candle dyes
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 0),
                        "dyeBlack", TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 1),
                        "dyeRed", TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 2),
                        "dyeGreen", TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 3),
                        "dyeBrown",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 4),
                        "dyeBlue",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 5),
                        "dyePurple",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 6),
                        "dyeCyan",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 7),
                        "dyeSilver",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 8),
                        "dyeGray",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 9),
                        "dyePink",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 10),
                        "dyeLime",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 11),
                        "dyeYellow",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 12),
                        "dyeLightBlue",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 13),
                        "dyeMagenta",
                        TallowBowl
                )
        );
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCMItems.tallowDye, 1, 14),
                        "dyeOrange",
                        TallowBowl
                )
        );

        //Halberds
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_BismuthBronze),
                "H  ", " S ", "  S",
                'H', TFCMItems.halberd_BismuthBronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_BismuthBronze),
                "  H", " S ", "S  ",
                'H', TFCMItems.halberd_BismuthBronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_BlackBronze),
                "H  ", " S ", "  S",
                'H', TFCMItems.halberd_BlackBronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_BlackBronze),
                "  H", " S ", "S  ",
                'H', TFCMItems.halberd_BlackBronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_BlackSteel),
                "H  ", " S ", "  S",
                'H', TFCMItems.halberd_BlackSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_BlackSteel),
                "  H", " S ", "S  ",
                'H', TFCMItems.halberd_BlackSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_BlueSteel),
                "H  ", " S ", "  S",
                'H', TFCMItems.halberd_BlueSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_BlueSteel),
                "  H", " S ", "S  ",
                'H', TFCMItems.halberd_BlueSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_Bronze),
                "H  ", " S ", "  S",
                'H', TFCMItems.halberd_Bronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_Bronze),
                "  H", " S ", "S  ",
                'H', TFCMItems.halberd_Bronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_Copper),
                "H  ", " S ", "  S",
                'H', TFCMItems.halberd_Copper_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_Copper),
                "  H", " S ", "S  ",
                'H', TFCMItems.halberd_Copper_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_RedSteel),
                "H  ", " S ", "  S",
                'H', TFCMItems.halberd_RedSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_RedSteel),
                "  H", " S ", "S  ",
                'H', TFCMItems.halberd_RedSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_Steel),
                "H  ", " S ", "  S",
                'H', TFCMItems.halberd_Steel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_Steel),
                "  H", " S ", "S  ",
                'H', TFCMItems.halberd_Steel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_WroughtIron),
                "H  ", " S ", "  S",
                'H', TFCMItems.halberd_WroughtIron_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.halberd_WroughtIron),
                "  H", " S ", "S  ",
                'H', TFCMItems.halberd_WroughtIron_Head,
                'S', TFCItems.stick
        );

        //War Hammers
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_BismuthBronze),
                "H  ", " S ", "  S",
                'H', TFCMItems.warHammer_BismuthBronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_BismuthBronze), "  H", " S ", "S  ",
                'H', TFCMItems.warHammer_BismuthBronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_BlackBronze),
                "H  ", " S ", "  S",
                'H', TFCMItems.warHammer_BlackBronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_BlackBronze),
                "  H", " S ", "S  ",
                'H', TFCMItems.warHammer_BlackBronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_BlackSteel),
                "H  ", " S ", "  S",
                'H', TFCMItems.warHammer_BlackSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_BlackSteel),
                "  H", " S ", "S  ",
                'H', TFCMItems.warHammer_BlackSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_BlueSteel), "H  ", " S ", "  S",
                'H', TFCMItems.warHammer_BlueSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_BlueSteel),
                "  H", " S ", "S  ",
                'H', TFCMItems.warHammer_BlueSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_Bronze),
                "H  ", " S ", "  S",
                'H', TFCMItems.warHammer_Bronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_Bronze),
                "  H", " S ", "S  ",
                'H', TFCMItems.warHammer_Bronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_Copper),
                "H  ", " S ", "  S",
                'H', TFCMItems.warHammer_Copper_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_Copper),
                "  H", " S ", "S  ",
                'H', TFCMItems.warHammer_Copper_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_RedSteel),
                "H  ", " S ", "  S",
                'H', TFCMItems.warHammer_RedSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_RedSteel),
                "  H", " S ", "S  ",
                'H', TFCMItems.warHammer_RedSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_Steel),
                "H  ", " S ", "  S",
                'H', TFCMItems.warHammer_Steel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_Steel),
                "  H", " S ", "S  ",
                'H', TFCMItems.warHammer_Steel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_WroughtIron),
                "H  ", " S ", "  S",
                'H', TFCMItems.warHammer_WroughtIron_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.warHammer_WroughtIron),
                "  H", " S ", "S  ",
                'H', TFCMItems.warHammer_WroughtIron_Head,
                'S', TFCItems.stick
        );

        //Poniards
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.poniard_BismuthBronze),
                "H", "S",
                'H', TFCMItems.poniard_BismuthBronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.poniard_BlackBronze),
                "H", "S",
                'H', TFCMItems.poniard_BlackBronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.poniard_BlackSteel),
                "H", "S",
                'H', TFCMItems.poniard_BlackSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.poniard_BlueSteel),
                "H", "S",
                'H', TFCMItems.poniard_BlueSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.poniard_Bronze),
                "H", "S",
                'H', TFCMItems.poniard_Bronze_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.poniard_Copper),
                "H", "S",
                'H', TFCMItems.poniard_Copper_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.poniard_RedSteel),
                "H", "S",
                'H', TFCMItems.poniard_RedSteel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.poniard_Steel),
                "H", "S",
                'H', TFCMItems.poniard_Steel_Head,
                'S', TFCItems.stick
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.poniard_WroughtIron),
                "H", "S",
                'H', TFCMItems.poniard_WroughtIron_Head,
                'S', TFCItems.stick
        );

        //Armor
        //Chain Helmets
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BismuthBronzeChainHelmet),
                "XOX", "X X", "   ",
                'X', new ItemStack(TFCMItems.chain_Sheet_BismuthBronze),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BlackBronzeChainHelmet),
                "XOX", "X X", "   ",
                'X', new ItemStack(TFCMItems.chain_Sheet_BlackBronze),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BlackSteelChainHelmet),
                "XOX", "X X", "   ",
                'X', new ItemStack(TFCMItems.chain_Sheet_BlackSteel),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BlueSteelChainHelmet),
                "XOX", "X X", "   ",
                'X', new ItemStack(TFCMItems.chain_Sheet_BlueSteel),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BronzeChainHelmet),
                "XOX", "X X", "   ",
                'X', new ItemStack(TFCMItems.chain_Sheet_Bronze),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.CopperChainHelmet),
                "XOX", "X X", "   ",
                'X', new ItemStack(TFCMItems.chain_Sheet_Copper),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.WroughtIronChainHelmet),
                "XOX", "X X", "   ",
                'X', new ItemStack(TFCMItems.chain_Sheet_WroughtIron),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.RedSteelChainHelmet),
                "XOX", "X X", "   ",
                'X', new ItemStack(TFCMItems.chain_Sheet_RedSteel),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.SteelChainHelmet),
                "XOX", "X X", "   ",
                'X', new ItemStack(TFCMItems.chain_Sheet_Steel),
                'O', TFCItems.leather
        );

        //Chain Chestplate
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BismuthBronzeChainChestplate),
                "X X", "XOX", "XXX",
                'X', new ItemStack(TFCMItems.chain_Sheet_BismuthBronze),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BlackBronzeChainChestplate),
                "X X", "XOX", "XXX",
                'X', new ItemStack(TFCMItems.chain_Sheet_BlackBronze),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BlackSteelChainChestplate),
                "X X", "XOX", "XXX",
                'X', new ItemStack(TFCMItems.chain_Sheet_BlackSteel),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BlueSteelChainChestplate),
                "X X", "XOX", "XXX",
                'X', new ItemStack(TFCMItems.chain_Sheet_BlueSteel),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BronzeChainChestplate),
                "X X", "XOX", "XXX",
                'X', new ItemStack(TFCMItems.chain_Sheet_Bronze),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.CopperChainChestplate),
                "X X", "XOX", "XXX",
                'X', new ItemStack(TFCMItems.chain_Sheet_Copper),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.WroughtIronChainChestplate),
                "X X", "XOX", "XXX",
                'X', new ItemStack(TFCMItems.chain_Sheet_WroughtIron),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.RedSteelChainChestplate),
                "X X", "XOX", "XXX",
                'X', new ItemStack(TFCMItems.chain_Sheet_RedSteel),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.SteelChainChestplate),
                "X X", "XOX", "XXX",
                'X', new ItemStack(TFCMItems.chain_Sheet_Steel),
                'O', TFCItems.leather
        );

        //Chain Leggings
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BismuthBronzeChainGreaves),
                "XXX", "XOX", "X X",
                'X', new ItemStack(TFCMItems.chain_Sheet_BismuthBronze),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BlackBronzeChainGreaves),
                "XXX", "XOX", "X X",
                'X', new ItemStack(TFCMItems.chain_Sheet_BlackBronze),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BlackSteelChainGreaves),
                "XXX", "XOX", "X X",
                'X', new ItemStack(TFCMItems.chain_Sheet_BlackSteel),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BlueSteelChainGreaves),
                "XXX", "XOX", "X X",
                'X', new ItemStack(TFCMItems.chain_Sheet_BlueSteel),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.BronzeChainGreaves),
                "XXX", "XOX", "X X",
                'X', new ItemStack(TFCMItems.chain_Sheet_Bronze),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.CopperChainGreaves),
                "XXX", "XOX", "X X",
                'X', new ItemStack(TFCMItems.chain_Sheet_Copper),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.WroughtIronChainGreaves),
                "XXX", "XOX", "X X",
                'X', new ItemStack(TFCMItems.chain_Sheet_WroughtIron),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.RedSteelChainGreaves),
                "XXX", "XOX", "X X",
                'X', new ItemStack(TFCMItems.chain_Sheet_RedSteel),
                'O', TFCItems.leather
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.SteelChainGreaves),
                "XXX", "XOX", "X X",
                'X', new ItemStack(TFCMItems.chain_Sheet_Steel),
                'O', TFCItems.leather
        );

        //Chain Item Crafting
        //Squares
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Square_BismuthBronze),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.link_BismuthBronze)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Square_BlackBronze),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.link_BlackBronze)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Square_BlackSteel),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.link_BlackSteel)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Square_BlueSteel),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.link_BlueSteel)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Square_Bronze),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.link_Bronze)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Square_Copper),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.link_Copper)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Square_WroughtIron),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.link_WroughtIron)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Square_RedSteel),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.link_RedSteel)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Square_Steel),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.link_Steel)
        );
        //Sheets
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Sheet_BismuthBronze),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.chain_Square_BismuthBronze)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Sheet_BlackBronze),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.chain_Square_BlackBronze)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Sheet_BlackSteel),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.chain_Square_BlackSteel)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Sheet_BlueSteel),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.chain_Square_BlueSteel)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Sheet_Bronze),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.chain_Square_Bronze)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Sheet_Copper),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.chain_Square_Copper)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Sheet_WroughtIron),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.chain_Square_WroughtIron)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Sheet_RedSteel),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.chain_Square_RedSteel)
        );
        GameRegistry.addRecipe(
                new ItemStack(TFCMItems.chain_Sheet_Steel),
                "XX", "XX",
                'X', new ItemStack(TFCMItems.chain_Square_Steel)
        );

        //Ore Dictionary
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(Blocks.ladder, 16),
                        "P P", "PSP", "P P",
                        'P', "woodLumber",
                        'R', TFCItems.stick
                )
        );

        //Shapeless
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.bowlSuet),
                CeramicBowl,
                new ItemStack(TFCMItems.suet)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMBlocks.blockFruitPress),
                new ItemStack(TFCBlocks.hopper)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCBlocks.hopper),
                new ItemStack(TFCMBlocks.blockFruitPress)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.itemQuiver),
                new ItemStack(TFCItems.quiver)
        );

        //Dyes
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCItems.dye, 1, 6),
                new ItemStack(TFCItems.dye, 1, 2),
                new ItemStack(TFCItems.powder, 1, 6)
        ); //Cyan
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCItems.dye, 1, 7),
                        new ItemStack(TFCItems.dye, 1, 8),
                        new ItemStack(TFCItems.powder, 1, 0),
                        ("blockSand")
                )
        ); //Light Gray
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCItems.dye, 1, 8),
                        new ItemStack(TFCItems.dye, 1, 0),
                        new ItemStack(TFCItems.powder, 1, 0),
                        ("blockSand")
                )
        ); //Gray
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(TFCItems.dye, 1, 9),
                        new ItemStack(TFCItems.dye, 1, 1),
                        new ItemStack(TFCItems.powder, 1, 0),
                        ("blockSand")
                )
        ); //Pink
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCItems.dye, 1, 10),
                new ItemStack(TFCItems.dye, 1, 2),
                new ItemStack(TFCItems.dye, 1, 11)
        ); //Lime
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCItems.dye, 1, 13),
                new ItemStack(TFCItems.dye, 1, 5),
                new ItemStack(TFCItems.dye, 1, 9)
        ); //Magenta
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCItems.dye, 1, 14),
                new ItemStack(TFCItems.dye, 1, 1),
                new ItemStack(TFCItems.dye, 1, 11)
        ); //Orange
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.brownDye),
                new ItemStack(TFCMItems.ironDust),
                new ItemStack(TFCItems.coal, 1, WILDCARD_VALUE)
        ); //Brown
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.brownDye),
                new ItemStack(TFCItems.powder, 1, 5),
                new ItemStack(TFCItems.coal, 1, WILDCARD_VALUE)
        ); //Brown
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.brownDye),
                new ItemStack(TFCItems.powder, 1, 7),
                new ItemStack(TFCItems.coal, 1, WILDCARD_VALUE)
        ); //Brown
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCItems.dye, 1, 0),
                new ItemStack(TFCMItems.brownDye),
                new ItemStack(TFCItems.coal, 1, WILDCARD_VALUE)
        ); //Black

        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.halberd_Copper_Head),
                new ItemStack(TFCMItems.halberd_Mold, 1, 2)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.halberd_Bronze_Head),
                new ItemStack(TFCMItems.halberd_Mold, 1, 3)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.halberd_BismuthBronze_Head),
                new ItemStack(TFCMItems.halberd_Mold, 1, 4)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.halberd_BlackBronze_Head),
                new ItemStack(TFCMItems.halberd_Mold, 1, 5)
        );

        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.warHammer_Copper_Head),
                new ItemStack(TFCMItems.warHammer_Mold, 1, 2)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.warHammer_Bronze_Head),
                new ItemStack(TFCMItems.warHammer_Mold, 1, 3)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.warHammer_BismuthBronze_Head),
                new ItemStack(TFCMItems.warHammer_Mold, 1, 4)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.warHammer_BlackBronze_Head),
                new ItemStack(TFCMItems.warHammer_Mold, 1, 5)
        );

        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.poniard_Copper_Head),
                new ItemStack(TFCMItems.poniard_Mold, 1, 2)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.poniard_Bronze_Head),
                new ItemStack(TFCMItems.poniard_Mold, 1, 3)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.poniard_BismuthBronze_Head),
                new ItemStack(TFCMItems.poniard_Mold, 1, 4)
        );
        GameRegistry.addShapelessRecipe(
                new ItemStack(TFCMItems.poniard_BlackBronze_Head),
                new ItemStack(TFCMItems.poniard_Mold, 1, 5)
        );

        //Food
        //Road Block Crafting
        for (int j = 0; j < Global.STONE_IGEX.length; j++) {
            GameRegistry.addRecipe(
                    new ItemStack(TFCMBlocks.blockRoadIgEx, 8, j),
                    "GSG", "SMS", "GSG",
                    'S', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGEX_START),
                    'M', new ItemStack(TFCItems.mortar, 1),
                    'G', Gravel1
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMBlocks.blockRoadIgEx, 8, j),
                    "GSG", "SMS", "GSG",
                    'S', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGEX_START),
                    'M', new ItemStack(TFCItems.mortar, 1),
                    'G', Gravel2
            );
        }

        for (int j = 0; j < Global.STONE_IGIN.length; j++) {
            GameRegistry.addRecipe(
                    new ItemStack(
                            TFCMBlocks.blockRoadIgIn, 8, j),
                    "GSG", "SMS", "GSG",
                    'S', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGIN_START),
                    'M', new ItemStack(TFCItems.mortar, 1),
                    'G', Gravel1
            );
            GameRegistry.addRecipe(
                    new ItemStack(
                            TFCMBlocks.blockRoadIgIn, 8, j),
                    "GSG", "SMS", "GSG",
                    'S', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGIN_START),
                    'M', new ItemStack(TFCItems.mortar, 1),
                    'G', Gravel2
            );
        }

        for (int j = 0; j < Global.STONE_MM.length; j++) {
            GameRegistry.addRecipe(
                    new ItemStack(TFCMBlocks.blockRoadMM, 8, j),
                    "GSG", "SMS", "GSG",
                    'S', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_MM_START),
                    'M', new ItemStack(TFCItems.mortar, 1),
                    'G', Gravel1
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMBlocks.blockRoadMM, 8, j),
                    "GSG", "SMS", "GSG",
                    'S', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_MM_START),
                    'M', new ItemStack(TFCItems.mortar, 1),
                    'G', Gravel2
            );
        }

        for (int j = 0; j < Global.STONE_SED.length; j++) {
            GameRegistry.addRecipe(
                    new ItemStack(TFCMBlocks.blockRoadSed, 8, j),
                    "GSG", "SMS", "GSG",
                    'S', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_SED_START),
                    'M', new ItemStack(TFCItems.mortar, 1),
                    'G', Gravel1
            );
            GameRegistry.addRecipe(
                    new ItemStack(TFCMBlocks.blockRoadSed, 8, j),
                    "GSG", "SMS", "GSG",
                    'S', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_SED_START),
                    'M', new ItemStack(TFCItems.mortar, 1),
                    'G', Gravel2
            );
        }

    }

    //From TFCraft/Recipes.java
    @SuppressWarnings("unchecked")
    public static void removeRecipe(ItemStack resultItem) {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i) != null) {
                ItemStack recipeResult = recipes.get(i).getRecipeOutput();

                if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
                    recipes.remove(i--);
            }
        }
    }

    //Barrel
    private static void registerBarrelRecipes() {
        BarrelManager.getInstance().addRecipe(
                new BarrelRecipe(
                        new ItemStack(TFCItems.powder, 1, 0),
                        new FluidStack(TFCFluids.MILKCURDLED, 100),
                        new ItemStack(TFCMItems.caseinGlue, 2),
                        new FluidStack(TFCFluids.MILKCURDLED, 100),
                        1
                ).setMinTechLevel(0)
        );

        BarrelManager.getInstance().addRecipe(
                new BarrelLiquidToLiquidRecipe(
                        new FluidStack(TFCMFluids.SOYMILK, 9000),
                        new FluidStack(TFCFluids.VINEGAR, 1000),
                        new FluidStack(TFCFluids.MILKVINEGAR, 10000)
                ).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false)
        );

        BarrelManager.getInstance().addRecipe(
                new BarrelFireRecipe(
                        ItemFoodTFC.createTag(
                                new ItemStack(TFCMItems.soyPaste),
                                160),
                        new FluidStack(TFCFluids.FRESHWATER, 5000),
                        null,
                        new FluidStack(TFCMFluids.SOYMILK, 5000),
                        2,
                        750
                ).setFireTicksScale(true).setSealedRecipe(true).setRemovesLiquid(true)
        );

        BarrelManager.getInstance().addRecipe(
                new BarrelFireRecipe(
                        new ItemStack(TFCMItems.bowlSuet),
                        new FluidStack(TFCFluids.FRESHWATER, 10000),
                        new ItemStack(TFCMItems.bowlTallow),
                        null,
                        2,
                        750
                ).setFireTicksScale(true).setSealedRecipe(true).setRemovesLiquid(true)
        );
    }

    //Kiln
    private static void registerKilnRecipes() {
        KilnCraftingManager.getInstance().addRecipe(
                new KilnRecipe(
                        new ItemStack(TFCMItems.halberd_Mold, 1, 0),
                        0,
                        new ItemStack(TFCMItems.halberd_Mold, 1, 1)
                )
        );

        KilnCraftingManager.getInstance().addRecipe(
                new KilnRecipe(
                        new ItemStack(TFCMItems.warHammer_Mold, 1, 0),
                        0,
                        new ItemStack(TFCMItems.warHammer_Mold, 1, 1)
                )
        );

        KilnCraftingManager.getInstance().addRecipe(
                new KilnRecipe(
                        new ItemStack(TFCMItems.poniard_Mold, 1, 0),
                        0,
                        new ItemStack(TFCMItems.poniard_Mold, 1, 1)
                )
        );

        KilnCraftingManager.getInstance().addRecipe(
                new KilnRecipe(
                        new ItemStack(TFCMItems.arrow_Mold, 1, 0),
                        0,
                        new ItemStack(TFCMItems.arrow_Mold, 1, 1)
                )
        );

        KilnCraftingManager.getInstance().addRecipe(
                new KilnRecipe(
                        new ItemStack(TFCMItems.bolt_Mold, 1, 0),
                        0,
                        new ItemStack(TFCMItems.bolt_Mold, 1, 1)
                )
        );

        KilnCraftingManager.getInstance().addRecipe(
                new KilnRecipe(
                        new ItemStack(TFCItems.clayBucketWater, 1, 0),
                        0,
                        new ItemStack(TFCItems.clayBucketWater, 1, 1)
                )
        );
    }

    //Mold Pouring
    public static ItemStack getItemStackTemp(ItemStack is) {
        NBTTagCompound Temp = new NBTTagCompound();
        Temp.setBoolean("temp", true);
        is.setTagCompound(Temp);
        return is;
    }


    private static void registerToolMolds() {
        craftingManager.addRecipe(new ItemStack(TFCMItems.halberd_Mold, 1, 2),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.halberd_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.halberd_Mold, 1, 3),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.halberd_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.halberd_Mold, 1, 4),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.halberd_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.halberd_Mold, 1, 5),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.halberd_Mold, 1, 1)});

        craftingManager.addRecipe(new ItemStack(TFCMItems.warHammer_Mold, 1, 2),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.warHammer_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.warHammer_Mold, 1, 3),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.warHammer_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.warHammer_Mold, 1, 4),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.warHammer_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.warHammer_Mold, 1, 5),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.warHammer_Mold, 1, 1)});

        craftingManager.addRecipe(new ItemStack(TFCMItems.poniard_Mold, 1, 2),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.poniard_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.poniard_Mold, 1, 3),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.poniard_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.poniard_Mold, 1, 4),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.poniard_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.poniard_Mold, 1, 5),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.poniard_Mold, 1, 1)});

        craftingManager.addRecipe(new ItemStack(TFCMItems.arrow_Mold, 1, 2),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.arrow_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.arrow_Mold, 1, 3),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.arrow_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.arrow_Mold, 1, 4),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.arrow_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.arrow_Mold, 1, 5),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.arrow_Mold, 1, 1)});

        craftingManager.addRecipe(new ItemStack(TFCMItems.bolt_Mold, 1, 2),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.bolt_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.bolt_Mold, 1, 3),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.bolt_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.bolt_Mold, 1, 4),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.bolt_Mold, 1, 1)});
        craftingManager.addRecipe(new ItemStack(TFCMItems.bolt_Mold, 1, 5),
                new Object[]{"12", '1', getItemStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCMItems.bolt_Mold, 1, 1)});
    }

    private static void registerKnappingRecipes() {
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCMItems.halberd_Mold, 1, 0), new Object[]{
                " ##  ",
                "#### ",
                "#####",
                "#### ",
                " ##  ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});

        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCMItems.warHammer_Mold, 1, 0), new Object[]{
                "     ",
                "#####",
                "###  ",
                "  #  ",
                "     ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});

        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCMItems.poniard_Mold, 1, 0), new Object[]{
                "     ",
                "  ## ",
                " ### ",
                " ##  ",
                "#    ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});

        if (TFCMOptions.enableCraftingCrossbow) {
            CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCMItems.bolt_Mold, 1, 0), new Object[]{
                    "     ",
                    "     ",
                    "     ",
                    "  #  ",
                    "  #  ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
        }

        if (TFCMOptions.enableCraftingLongbow) {
            CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCMItems.arrow_Mold, 1, 0), new Object[]{
                    "     ",
                    " ### ",
                    "  ## ",
                    "   # ",
                    "     ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
        }
    }

    private static void registerQuernRecipes() {
        QuernManager.getInstance().addRecipe(
                new QuernRecipe(
                        new ItemStack(TFCItems.wroughtIronIngot),
                        new ItemStack(TFCMItems.ironDust, 8)
                )
        );
        QuernManager.getInstance().addRecipe(
                new QuernRecipe(
                        new ItemStack(TFCItems.smallOreChunk, 1, 10),
                        new ItemStack(TFCMItems.ironDust, 1)
                )
        );
        QuernManager.getInstance().addRecipe(
                new QuernRecipe(
                        new ItemStack(TFCItems.oreChunk, 1, 59),
                        new ItemStack(TFCMItems.ironDust, 1)
                )
        );
        QuernManager.getInstance().addRecipe(
                new QuernRecipe(
                        new ItemStack(TFCItems.oreChunk, 1, 10),
                        new ItemStack(TFCMItems.ironDust, 2)
                )
        );
        QuernManager.getInstance().addRecipe(
                new QuernRecipe(
                        new ItemStack(TFCItems.oreChunk, 1, 45),
                        new ItemStack(TFCMItems.ironDust, 3)
                )
        );

        QuernManager.getInstance().addRecipe(
                new QuernRecipe(
                        new ItemStack(TFCItems.soybean, 1),
                        new ItemStack(TFCMItems.soyPaste, 1)
                )
        );
    }

    @SuppressWarnings("unused")
    public static void registerHeatingRecipes() {
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

        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCMItems.coil_BismuthBronze, 1),
                        bismuthBronzeRaw,
                        new ItemStack(TFCItems.bismuthBronzeUnshaped, 1)
                )
        );
        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCMItems.coil_BlackBronze, 1),
                        blackBronzeRaw,
                        new ItemStack(TFCItems.blackBronzeUnshaped, 1)
                )
        );
        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCMItems.coil_BlackSteel, 1),
                        blackSteelRaw,
                        new ItemStack(TFCItems.blackSteelUnshaped, 1)
                )
        );
        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCMItems.coil_BlueSteel, 1),
                        blueSteelRaw,
                        new ItemStack(TFCItems.blueSteelUnshaped, 1)
                )
        );
        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCMItems.coil_Bronze, 1),
                        bronzeRaw,
                        new ItemStack(TFCItems.bronzeUnshaped, 1)
                )
        );
        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCMItems.coil_Copper, 1),
                        copperRaw,
                        new ItemStack(TFCItems.copperUnshaped, 1)
                )
        );
        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCMItems.coil_WroughtIron, 1),
                        ironRaw,
                        new ItemStack(TFCItems.wroughtIronUnshaped, 1)
                )
        );
        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCMItems.coil_RedSteel, 1),
                        redSteelRaw,
                        new ItemStack(TFCItems.redSteelUnshaped, 1)
                )
        );
        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCMItems.coil_Steel, 1),
                        steelRaw,
                        new ItemStack(TFCItems.steelUnshaped, 1)
                )
        );

        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCItems.pumpkinGuts, 1),
                        1,
                        1200,
                        null
                )
        );
        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCMItems.bearRaw, 1),
                        1,
                        1200,
                        null
                )
        );
        heatmanager.addIndex(
                new HeatIndex(
                        new ItemStack(TFCItems.clayBucketWater, 1),
                        1,
                        1200,
                        null
                )
        );
    }

    public static void registerVatRecipes() {
        //1200 ticks equals one minute

        VatManager.addRecipe(
                new VatRecipeDoubleBoiler(
                        new ItemStack(TFCMItems.bowlSuet, 1),
                        new FluidStack(TFCFluids.FRESHWATER, 1000),
                        new ItemStack(TFCMItems.bowlTallow, 1),
                        102,
                        1200
                )
        );
        VatManager.addRecipe(
                new VatRecipeEvaporation(
                        new FluidStack(TFCFluids.SALTWATER, 1000),
                        new ItemStack(TFCItems.powder, 1, 9),
                        102,
                        1800
                )
        );
    }

    public static void registerClothingRecipes() {
        ClothingManager manager = ClothingManager.getInstance();

        int quiverRecipeIndex = -1;

        for (int i = 0; i < manager.getRecipes().size(); i++) {
            if (manager.getRecipes().get(i).getSewingPattern().getResultingItem() instanceof ItemQuiver) {
                 quiverRecipeIndex = i;
            }
        }

        if (quiverRecipeIndex > -1) {
            manager.getRecipes().remove(quiverRecipeIndex);
        }

        manager.addRecipe(
            new SewingRecipe(
                new SewingPattern(
                    new ItemStack(TFCMItems.itemQuiver, 1),
                    new int[][][] {
                            new int[][] {
                                    new int[] { 47, 16 },
                                    new int[] { 47, 54 },
                                    new int[] { 53, 57 },
                                    new int[] { 60, 53 },
                                    new int[] { 60, 16 }
                            }
                    },
                true),
                new ItemStack[] {
                    new ItemStack(TFCItems.quiverPiece, 1, 0),
                    new ItemStack(TFCItems.quiverPiece, 1, 0),
                    new ItemStack(TFCItems.leatherStrap, 1)
                }
            )
        );
    }
}