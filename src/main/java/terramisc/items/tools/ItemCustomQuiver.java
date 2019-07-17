package terramisc.items.tools;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import terramisc.TerraMisc;
import terramisc.core.TFCMItems;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Items.ItemArrow;
import com.dunk.tfc.Items.ItemQuiver;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.TFCItems;

public class ItemCustomQuiver extends ItemQuiver
{
	private int arrowTier;
	private int boltTier;

	//Opens gui and stuff.
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		entityplayer.openGui(TerraMisc.instance, 2, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		return itemstack;
	}
	
	//Gathers information on the amount of arrows inside the quiver.
	public int getQuiverMetalArrows(ItemStack item)
	{
		int n = 0;
		ItemStack[] inventory = loadInventory(item);
		for(ItemStack i : inventory)
		{
			if(i!=null && (i.getItem() instanceof ItemArrow || i.getItem() instanceof ItemCustomArrow))
			{
				n += i.stackSize;
			}
		}
		
		return n;
	}
	
	//Returns boolean for to determine if ammo is present.
	public ItemStack hasArrowAmmo(ItemStack quiver)
	{
		ItemStack[] inventory = loadInventory(quiver);
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null && (inventory[i].getItem() instanceof ItemArrow || inventory[i].getItem() instanceof ItemCustomArrow))
			{
				ItemStack out = inventory[i].copy();
				out.stackSize = 1;
				
				if(inventory[i].stackSize <= 0)
				{
					inventory[i] = null;
				}
				
				saveInventory(quiver, inventory);
				
				return out;
			}
		}
		return null;
	}
	
	//Gets ammo tier for longbow to determine damage, and other triggers.
	public int getQuiverMetalArrowTier(ItemStack item)
	{	
		ItemStack[] inventory = loadInventory(item);
		for(ItemStack i : inventory)
		{
			
			if(i!=null && i.getItem() == TFCItems.arrow)
			{
				return arrowTier = 1;
			}
			else if(i!=null && i.getItem() == TFCMItems.arrow_Copper)
			{
				return arrowTier = 2;
			}
			else if(i!=null && i.getItem() == TFCMItems.arrow_BismuthBronze)
			{
				return arrowTier = 3;
			}
			else if(i!=null && i.getItem() == TFCMItems.arrow_Bronze)
			{
				return arrowTier = 4;
			}
			else if(i!=null && i.getItem() == TFCMItems.arrow_BlackBronze)
			{
				return arrowTier = 5;
			}
			else if(i!=null && i.getItem() == TFCMItems.arrow_WroughtIron)
			{
				return arrowTier = 6;
			}
			else if(i!=null && i.getItem() == TFCMItems.arrow_Steel)
			{
				return arrowTier = 7;
			}
			else if(i!=null && i.getItem() == TFCMItems.arrow_BlackSteel)
			{
				return arrowTier = 8;
			}
			else if(i!=null && i.getItem() == TFCMItems.arrow_BlueSteel)
			{
				return arrowTier = 9;
			}
			else if(i!=null && i.getItem() == TFCMItems.arrow_RedSteel)
			{
				return arrowTier = 10;
			}	
			
		}
			
		return arrowTier;
	}
	
	@SuppressWarnings("rawtypes")
	// Storing both Strings and Integers in the same ArrayList
	public List[] getQuiverArrowTypes(ItemStack item)
	{
		ArrayList[] pair = new ArrayList[2];
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Integer> listNum = new ArrayList<Integer>();
		ItemStack[] inventory = loadInventory(item);
		for(ItemStack i : inventory)
		{
			if(i!=null && (i.getItem() instanceof ItemArrow || i.getItem() instanceof ItemCustomArrow))
			{
				String s = i.getItem().getItemStackDisplayName(i);
				if(!list.contains(s))
				{
					list.add(s);
				}
				int n = list.indexOf(s);
				if(listNum.size() == n)
				{
					listNum.add(0);
				}
				listNum.set(n, listNum.get(n)+i.stackSize);
			}
		}
		pair[0] = list;
		pair[1] = listNum;
		return pair;
	}
	
	//Gathers information on the amount of bolts inside the quiver.
	public int getQuiverMetalBolts(ItemStack item)
	{
		int n = 0;
		ItemStack[] inventory = loadInventory(item);
		for(ItemStack i : inventory)
		{
			if(i!=null && (i.getItem() instanceof ItemCustomBolt))
			{
				n += i.stackSize;
			}
		}
		
		return n;
	}
	
	//Returns boolean for to determine if ammo is present.
	public ItemStack hasBoltAmmo(ItemStack quiver)
	{
		ItemStack[] inventory = loadInventory(quiver);
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null && (inventory[i].getItem() instanceof ItemCustomBolt))
			{
				ItemStack out = inventory[i].copy();
				out.stackSize = 1;
				
				if(inventory[i].stackSize <= 0)
				{
					inventory[i] = null;
				}
				
				saveInventory(quiver, inventory);
				
				return out;
			}
		}
		return null;
	}
	
	//Gets ammo tier for crossbow to determine damage, and other triggers.
	public int getQuiverMetalBoltTier(ItemStack item)
	{	
		ItemStack[] inventory = loadInventory(item);
		for(ItemStack i : inventory)
		{
			
			if(i!=null && i.getItem() == TFCMItems.bolt_Copper)
			{
				return boltTier = 1;
			}
			else if(i!=null && i.getItem() == TFCMItems.bolt_BismuthBronze)
			{
				return boltTier = 2;
			}
			else if(i!=null && i.getItem() == TFCMItems.bolt_Bronze)
			{
				return boltTier = 3;
			}
			else if(i!=null && i.getItem() == TFCMItems.bolt_BlackBronze)
			{
				return boltTier = 4;
			}
			else if(i!=null && i.getItem() == TFCMItems.bolt_WroughtIron)
			{
				return boltTier = 5;
			}
			else if(i!=null && i.getItem() == TFCMItems.bolt_Steel)
			{
				return boltTier = 6;
			}
			else if(i!=null && i.getItem() == TFCMItems.bolt_BlackSteel)
			{
				return boltTier = 7;
			}
			else if(i!=null && i.getItem() == TFCMItems.bolt_BlueSteel)
			{
				return boltTier = 8;
			}
			else if(i!=null && i.getItem() == TFCMItems.bolt_RedSteel)
			{
				return boltTier = 9;
			}	
			
		}
			
		return boltTier;
	}
	
	@SuppressWarnings("rawtypes")
	// Storing both Strings and Integers in the same ArrayList
	public List[] getQuiverBoltTypes(ItemStack item)
	{
		ArrayList[] pair = new ArrayList[2];
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Integer> listNum = new ArrayList<Integer>();
		ItemStack[] inventory = loadInventory(item);
		for(ItemStack i : inventory)
		{
			if(i!=null && i.getItem() instanceof ItemCustomBolt)
			{
				String s = i.getItem().getItemStackDisplayName(i);
				if(!list.contains(s))
				{
					list.add(s);
				}
				int n = list.indexOf(s);
				if(listNum.size() == n)
				{
					listNum.add(0);
				}
				listNum.set(n, listNum.get(n)+i.stackSize);
			}
		}
		pair[0] = list;
		pair[1] = listNum;
		return pair;
	}
	
	//Removes ammo from quiver.
	public ItemStack consumeMetalAmmo(ItemStack quiver, Item ammo, boolean shouldConsume)
	{
		ItemStack[] inventory = loadInventory(quiver);
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null && inventory[i].getItem() == ammo)
			{
				ItemStack out = inventory[i].copy();
				out.stackSize = 1;
				if(shouldConsume)
					inventory[i].stackSize--;
				if(inventory[i].stackSize <= 0)
					inventory[i] = null;
				saveInventory(quiver, inventory);
				return out;
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);

		if (TFC_Core.showShiftInformation())
		{
			arraylist.add(EnumChatFormatting.WHITE + TFC_Core.translate("gui.Advanced") + ":");
			
			arraylist.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Bow.Arrows") + ": " + EnumChatFormatting.YELLOW + getQuiverMetalArrows(is));
			List[] javData1 = getQuiverArrowTypes(is);
			for(int i = 0; i < javData1[0].size();i++)
			{
				String s = (String)(javData1[0].get(i));
				int n = (Integer)(javData1[1].get(i));
				arraylist.add(EnumChatFormatting.ITALIC + "  -" + s + ": "+EnumChatFormatting.YELLOW+n);
			}
		
			arraylist.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.crossbow.bolts") + ": " + EnumChatFormatting.YELLOW + getQuiverMetalBolts(is));
			List[] javData2 = getQuiverBoltTypes(is);
			for(int i = 0; i < javData2[0].size();i++)
			{
				String s1 = (String)(javData2[0].get(i));
				int n1 = (Integer)(javData2[1].get(i));
				arraylist.add(EnumChatFormatting.ITALIC + "  -" + s1 + ": "+EnumChatFormatting.YELLOW+n1);
			}
			
			arraylist.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Bow.Javelins") + ": " + EnumChatFormatting.YELLOW + getQuiverJavelins(is));
			List[] javData3 = getQuiverJavelinTypes(is);
			for(int i = 0; i < javData3[0].size();i++)
			{
				String s2 = (String)(javData3[0].get(i));
				int n2 = (Integer)(javData3[1].get(i));
				arraylist.add(EnumChatFormatting.ITALIC + "  -" + s2 + ": "+EnumChatFormatting.YELLOW+n2);
			}
			
			if (is.hasTagCompound())
			{
				NBTTagCompound stackTagCompound = is.getTagCompound();
				if(stackTagCompound.hasKey("creator"))
					arraylist.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Armor.ForgedBy") + " " + stackTagCompound.getString("creator"));
			}
		}
		else
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.Advanced") + ": (" + TFC_Core.translate("gui.Hold") + " " + EnumChatFormatting.GRAY + TFC_Core.translate("gui.Shift") +
					EnumChatFormatting.DARK_GRAY + ")");

	}
}
