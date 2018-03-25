package terramisc.items.weights;

import terramisc.core.TFCMDetails;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

public class ItemWeightTinyLight extends ItemTerra implements ISize
{
	public ItemWeightTinyLight()
	{
		super();
		this.maxStackSize = 64;
		this.hasSubtypes = false;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		if (this.iconString != null)
			this.itemIcon = registerer.registerIcon(TFCMDetails.ModID + ":" + this.getIconString());
		else
			this.itemIcon = registerer.registerIcon(TFCMDetails.ModID + ":" + this.getUnlocalizedName().replace("item.", ""));
	}
	
	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return EnumSize.TINY;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		return EnumWeight.LIGHT;
	}

	@Override
	public EnumItemReach getReach(ItemStack is) 
	{
		return EnumItemReach.SHORT;
	}

	@Override
	public boolean canStack() 
	{
		return true;
	}
}
