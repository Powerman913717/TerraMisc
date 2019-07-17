package terramisc.items.food;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBottleJuiceOnion extends ItemTerra
{
	public ItemBottleJuiceOnion()
	{
		super();
		this.setFolder("food/");
		this.setContainerItem(TFCItems.glassBottle);
		this.setMaxStackSize(64);
	}
	
	@Override
	public boolean canStack()
	{
		return true;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		EntityPotion entitypotion = new EntityPotion(par2World, par3EntityPlayer, new ItemStack(Items.potionitem, 1, 4));
		
		--par1ItemStack.stackSize;
		
		if (!par2World.isRemote)
		{
			par2World.spawnEntityInWorld(entitypotion);
		}
		
		return par1ItemStack;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		arraylist.add("Right click to throw onion juice.");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":Glass Bottle Overlay");		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return pass == 1 ? this.itemIcon : this.getContainerItem().getIcon(stack, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int pass)
	{
		return pass == 1 ? FluidContainerRegistry.getFluidForFilledItem(is).getFluid().getColor() : super.getColorFromItemStack(is, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
}
