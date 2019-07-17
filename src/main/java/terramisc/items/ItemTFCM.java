package terramisc.items;

import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.ISize;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import terramisc.core.TFCMDetails;

public class ItemTFCM extends ItemTerra implements ISize
{
	protected EnumSize itemSize;
	protected EnumWeight itemWeight;
	
	public ItemTFCM(EnumSize size, EnumWeight weight)
	{
		super();
		this.itemSize = size;
		this.itemWeight = weight;
		this.hasSubtypes = false;
		
		switch(size) //Set stack size from item size
		{
		case TINY:
			this.maxStackSize = 64;
		case VERYSMALL:
			this.maxStackSize = 32;
		case SMALL:
			this.maxStackSize = 16;
		case MEDIUM:
			this.maxStackSize = 8;
		case LARGE:
			this.maxStackSize = 4;
		case VERYLARGE:
			this.maxStackSize = 2;
		case HUGE:
			this.maxStackSize = 1;
		default:
			break;
		}
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
		return itemSize;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		return itemWeight;
	}

	@Override
	public EnumItemReach getReach(ItemStack is) 
	{
		return EnumItemReach.SHORT;
	}

	@Override
	public boolean canStack()
	{
		if(size != null && size != EnumSize.HUGE)
			return true;
		else
			return false;
	}
}
