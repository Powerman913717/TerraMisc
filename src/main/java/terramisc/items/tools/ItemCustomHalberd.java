package terramisc.items.tools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Items.Tools.ItemWeapon;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

public class ItemCustomHalberd extends ItemWeapon
{
	public ItemCustomHalberd(ToolMaterial par2EnumToolMaterial, float damage, EnumDamageType dt)
	{
		super(par2EnumToolMaterial, damage);
		this.damageType = dt;
	}

	public ItemCustomHalberd(ToolMaterial par2EnumToolMaterial, float damage)
	{
		super(par2EnumToolMaterial, damage);
		this.damageType = EnumDamageType.SLASHING;
		this.setCreativeTab(TFCTabs.TFC_WEAPONS);
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon("tfcm:tools/"+this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		return is;
	}
	
	 @Override
	 public void onUpdate (ItemStack stack, World world, Entity entity, int par4, boolean par5)
	 {
		 super.onUpdate(stack, world, entity, par4, par5);
	     if (entity instanceof EntityPlayer)
	     {
	    	 EntityPlayer player = (EntityPlayer) entity;
	         ItemStack equipped = player.getCurrentEquippedItem();
	         if (equipped == stack)
	         {
	        	 player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, -1, 2, true));
	         }
	     }
	 }
	
	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.FAR;
	}
	
	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}
}
