package terramisc.items;

import java.util.List;

import terramisc.core.TFCMDetails;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMetalPart extends ItemTerra implements ISmeltable
{
	private String metal;
	private short metalAmount;
	
	public ItemMetalPart()
	{
		super();
		setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.setMaxStackSize(16);
	}

	public ItemMetalPart(boolean canSmelt)
	{
		this();
		canSmelt = true;
	}

	public ItemTerra setMetal(String m, int amt)
	{
		metal = m;
		metalAmount = (short) amt;
		return this;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(TFCMDetails.ModID + ":" + this.getUnlocalizedName().replace("item.", ""));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.SMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}
	
	@Override
	public Metal getMetalType(ItemStack is) 
	{
		if(metal == null) 
		{
			return MetalRegistry.instance.getMetalFromItem(this);
		} 
		else 
		{
			return MetalRegistry.instance.getMetalFromString(metal);
		}
	}

	@Override
	public short getMetalReturnAmount(ItemStack is) 
	{
		return metalAmount;
	}

	@Override
	public boolean isSmeltable(ItemStack is) 
	{
		return true;
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is) 
	{
		return EnumTier.TierI;
	}

	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if(	is.getItem() instanceof ItemMetalPart)
		{
			if(TFC_ItemHeat.hasTemp(is))
			{
				String s = "";
				if(HeatRegistry.getInstance().isTemperatureDanger(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.danger") + " | ";
				}

				if(HeatRegistry.getInstance().isTemperatureWeldable(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.weldable") + " | ";
				}

				if(HeatRegistry.getInstance().isTemperatureWorkable(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.workable");
				}

				if (!"".equals(s))
					arraylist.add(s);
			}
		}
	}
	
	@Override
	public int getItemStackLimit(ItemStack is)
	{
		// hot or worked items cannot stack
		if (is.hasTagCompound() && (TFC_ItemHeat.hasTemp(is) ||
									is.getTagCompound().hasKey("itemCraftingValue") && is.getTagCompound().getShort("itemCraftingValue") != 0))
		{
			return 1;
		}

		return super.getItemStackLimit(is);
	}

}
