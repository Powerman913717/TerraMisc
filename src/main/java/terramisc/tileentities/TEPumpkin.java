package terramisc.tileentities;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TEPumpkin extends TileEntity 
{
	/**
	 * Food can contain multiple NBT Tags including
	 * temperature:
	 * foodWeight:
	 * foodDecay
	 * decayTimer
	 */

	private EnumFoodGroup foodgroup;

	public int foodID;
	public float decayRate = 1.0f;
	public boolean edible = true;
	public boolean canBeUsedRaw = true;
	protected int tasteSweet;
	protected int tasteSour;
	protected int tasteSalty;
	protected int tasteBitter;
	protected int tasteUmami;
	protected boolean canBeSmoked;
	protected float smokeAbsorb;
	
	public float getDecayRate(ItemStack is)
	{
		float mult = 1.0f; //Default food decay rate
		return decayRate * (TFC_Time.getYearRatio(96)) * mult;
	}
}
