package terramisc.core;

import net.minecraftforge.fluids.Fluid;

import com.dunk.tfc.Core.FluidBaseTFC;

public class TFCMFluids 
{
	public static final Fluid SOYMILK = new FluidBaseTFC("soy milk").setBaseColor(0xF7EBB3);
	
	public static final Fluid BOILINGWATER = new FluidBaseTFC("boiling water").setBaseColor(0x1f5099).setTemperature(375/*Kelvin*/);
	
	//Saki
	
	//Vinegar
	
	/* TFC Heat Tile Entities operate on degrees Celsius fluids are kept in degrees Kelvin.
	 * 0°C + 273.15 = 273.15K
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
