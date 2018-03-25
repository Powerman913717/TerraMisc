package terramisc.items.tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumAmmo;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IQuiverAmmo;
import com.bioxx.tfc.api.Interfaces.ISize;

public class ItemCustomArrow extends Item implements ISize, IQuiverAmmo
{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
	}
	
	public ItemCustomArrow()
	{
		super();
		this.maxStackSize = 16;
		this.hasSubtypes = false;
		this.setCreativeTab(TFCTabs.TFC_WEAPONS);
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon("tfcm:projectileItems/"+this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return EnumSize.LARGE;
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
		return false;
	}

	@Override
	public EnumAmmo getAmmoType() 
	{
		return EnumAmmo.ARROW;
	}
}
