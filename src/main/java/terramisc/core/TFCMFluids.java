package terramisc.core;

import net.minecraftforge.fluids.Fluid;

import com.dunk.tfc.Core.FluidBaseTFC;

public class TFCMFluids 
{
	public static final Fluid JUICELEMON = new FluidBaseTFC("juice lemon").setBaseColor(0xF9F589);
	
	public static final Fluid SOYMILK = new FluidBaseTFC("soy milk").setBaseColor(0xF7EBB3);
	
	public static final Fluid BOILINGWATER = new FluidBaseTFC("boiling water").setBaseColor(0x1f5099).setTemperature(375/*Kelvin*/);
	
		//TODO Set base colors for all fluids
	//Beer
	public static final Fluid GRAINWORT = new FluidBaseTFC("grain wort").setTemperature(350/*Kelvin*/);
	public static final Fluid HOPPEDGRAINWORT = new FluidBaseTFC("hopped grain wort").setTemperature(375/*Kelvin*/);
	
	//Whiskey (Scotch, Bourbon, Moonshine)
	public static final Fluid CORNWORT = new FluidBaseTFC("corn wort").setTemperature(350/*Kelvin*/);
	public static final Fluid FERMENTEDGRAINWORT = new FluidBaseTFC("fermented grain wort");
	public static final Fluid FERMENTEDCORNWORT = new FluidBaseTFC("fermented corn wort");
	public static final Fluid DISTILLEDGRAINWORT = new FluidBaseTFC("distilled grain wort");
	public static final Fluid SCOTCH = new FluidBaseTFC("scotch");
	public static final Fluid BOURBON = new FluidBaseTFC("bourbon");
	
	//Wine (Cognac, Brandy, Sherry)
	public static final Fluid REDFRUITJUICE = new FluidBaseTFC("red fruit juice").setBaseColor(0xBB648F);
	public static final Fluid WHITEFRUITJUICE = new FluidBaseTFC("white fruit juice");
	public static final Fluid REDWINE = new FluidBaseTFC("red wine");
	public static final Fluid WHITEWINE = new FluidBaseTFC("white wine");
	public static final Fluid DISTILLEDREDWINE = new FluidBaseTFC("distilled red wine");
	public static final Fluid DISTILLEDWHITEWINE = new FluidBaseTFC("distilled white wine");
	public static final Fluid BRANDY = new FluidBaseTFC("brandy");
	public static final Fluid COGNAC = new FluidBaseTFC("cognac");
	public static final Fluid SHERRY = new FluidBaseTFC("sherry");
	
	//Cider
	public static final Fluid JUICEAPPLE = new FluidBaseTFC("juice apple").setBaseColor(0xF8B422);
	
	//Potato Vodka
	public static final Fluid BOILEDPOTATOWORT = new FluidBaseTFC("boiled potato wort").setTemperature(350/*Kelvin*/);
	public static final Fluid FERMENTEDPOTATOWORT = new FluidBaseTFC("fermented potato wort");
	
	//Rum
	public static final Fluid MOLASSES = new FluidBaseTFC("molasses");
	public static final Fluid TREESYRUP = new FluidBaseTFC("tree syrup");
	public static final Fluid FERMENTEDSYRUP = new FluidBaseTFC("fermented syrup");
	public static final Fluid DISTILLEDSYRUP = new FluidBaseTFC("distilled syrup");
	
	//Saki
	
	//Vinegar
	
	/* TFC Heat Tile Entities operate on degrees Celsius fluids are kept in degrees Kelvin.
	 * 0�C + 273.15 = 273.15K
	 * 
	 * Boiling Water = 375 Degree Kelvin / 101.85 Celsius
	 * 
	 * Fluids for complex brewing mechanics
	 * 	Beer
	 * Grain Wort = 350 Degree Kelvin // Same as Hot Spring Water (76.85 Celsius)
	 * Boiled Grain Wort (Hops)
	 * Result: Beer : Fermintation
	 * 
	 * 	Whiskey (Scotch, Bourbon, Moonshine)
	 * Grain Wort
	 * Fermented Grain Wort : Fermintation
	 * Distilled Grain Wort : Distillation -> Moonshine (Corn) Whiskey : NO Maturation (95 Celsius/368.15 Kelvin)
	 * Result: Whiskey, Scotch, or Bourbon : Maturation
	 * 
	 * 	Wine (Cognac, Brandy, Sherry)
	 * Red Fruit Juice (Skin) or White Fruit Juice (Skinless)
	 * Result: Red Wine or White Wine : Fermintation
	 * Distilled Red White or Distilled White Wine : Distillation
	 * Result: Brandy (Red) or Cognac (White) : Maturation
	 * Result: Sherry (1:1 Distilled Red Wine and Red Wine) : Mixing
	 * 
	 *	Cider
	 * Apple Juice : Pressing Pomace
	 * Result: Cider : Fermintation
	 * 
	 * 	Potato Vodka
	 * Boiled Potato Wort
	 * Fermented Potato Wort : Fermintation
	 * Result: Potato Vodka : Distillation
	 * 
	 * 	Rum
	 * Molasses (From Processing Sugarcane) or Tree Syrup (From Tree Sap Processing)
	 * Fermented Syrup : Fermintation
	 * Distilled Syrup : Distillation
	 * Result: Rum : Maturation
	 * 
	 * 	Saki (Rice Wine)
	 * Rice Mash
	 * Koji Rice Mash : Mixture
	 * Moromi : Fermintation (Stages 0-4, Requires additional Koji Rice to progress stage)
	 * Fermented Moromi : Fermintation
	 * Result: Saki : Pressing
	 * 
	 * 	Vinegar (Balsamic Vinegar, Apple Cider Vinegar, Sherry Vinegar, White Wine Vinegar)
	 * Result: Vinegar : Fermintation (Some alcohol/fruit combinations will result in specific vinegars)
	 */
}
