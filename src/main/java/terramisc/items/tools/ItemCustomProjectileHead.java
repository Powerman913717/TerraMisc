package terramisc.items.tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.ISize;

public class ItemCustomProjectileHead extends Item implements ISize
{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
	}
	
	public ItemCustomProjectileHead()
	{
		super();
		this.maxStackSize = 16;
		this.hasSubtypes = false;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		String name = this.getUnlocalizedName().replace("item.", "");
		this.itemIcon = registerer.registerIcon("tfcm:toolheads/" + name);
	}
	
	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return EnumSize.SMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		return EnumWeight.HEAVY;
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
