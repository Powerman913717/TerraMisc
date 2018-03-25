package terramisc.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import terramisc.core.TFCMDetails;

import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

public class ItemBowLimb extends Item implements ISize{
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
	}
	
	public ItemBowLimb()
	{
		super();
		this.maxStackSize = 4;
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
		// TODO Auto-generated method stub
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}

	@Override
	public EnumItemReach getReach(ItemStack is) 
	{
		// TODO Auto-generated method stub
		return EnumItemReach.SHORT;
	}

	@Override
	public boolean canStack() 
	{
		// TODO Auto-generated method stub
		return false;
	}
}
