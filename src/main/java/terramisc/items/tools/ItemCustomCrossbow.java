package terramisc.items.tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import terramisc.core.TFCMItems;
import terramisc.entities.EntityMetalBolt;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.InventoryPlayerTFC;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.ISize;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomCrossbow extends ItemBow implements ISize
{
	private IIcon[] iconArray;
	
	public ItemCustomCrossbow()
	{
		super();
		this.maxStackSize = 1;
		this.setMaxDamage(800);
		this.setCreativeTab(TFCTabs.TFC_WEAPONS);
	}
	
	//Creates NBT tags when item is crafted and stores them to that item stack.
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) 
	{
		 itemStack.stackTagCompound = new NBTTagCompound();
		 itemStack.stackTagCompound.setBoolean("load", false);
		 itemStack.stackTagCompound.setInteger("ammo", 0);
		 itemStack.stackTagCompound.setFloat("forceMult", 0);
		 
	}
	
	//Activates when player right clicks with crossbow.
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{	
		//Sets up data for crossbow on use, should prevent crashing.
		if(is.stackTagCompound == null)
		{
			is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setBoolean("load", false);
			is.stackTagCompound.setInteger("ammo", 0);
			is.stackTagCompound.setFloat("forceMult", 0);
		}
		
		//To try to prevent crashing, from cheated in crossbows.
		if(is.stackTagCompound != null)
		{
			//Check to see if player is in creative mode or has enchantments.
			boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, is) > 0;
			//First we check the if there is ammo in the player's quiver
			boolean hasQuiverAmmo = flag || this.hasAmmoQuiver(is, player);
			boolean hasAmmo = false;
			//If there was no quiver or ammo, we check the inventory.
			if(!hasQuiverAmmo)
			{
				hasAmmo = this.hasAmmo(is, player);
			}
			
			//Load the crossbow if its unloaded, and has ammo.
			if((hasQuiverAmmo || hasAmmo) && is.getTagCompound().getBoolean("load") == false)
			{
				ArrowNockEvent event = new ArrowNockEvent(player, is);
				MinecraftForge.EVENT_BUS.post(event);
				if (event.isCanceled())
				{	
					return event.result;
				}
				
				player.setItemInUse(is, this.getMaxItemUseDuration(is));
			}
			
			//If Crossbow is already loaded
			if(is.getTagCompound().getBoolean("load") == true)
			{					
				this.fireCrossbow(is, world, player, is.getTagCompound().getFloat("forceMult"));
			}
		}
		
		return is;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int inUseCount)
	{
		boolean load = is.getTagCompound().getBoolean("load");
		int ammo = is.getTagCompound().getInteger("ammo");
		
		int j = this.getMaxItemUseDuration(is) - inUseCount;
		float forceMult = j / getUseSpeed(player);
		
		//Check to see if player is in creative mode or has enchantments.
		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, is) > 0;
		//First we check the if there is ammo in the player's quiver
		boolean hasQuiverAmmo = flag || this.hasAmmoQuiver(is, player);
		boolean hasAmmo = false;
		//If there was no quiver or ammo, we check the inventory.
		if(!hasQuiverAmmo)
		{
			hasAmmo = this.hasAmmo(is, player);
		}
		
		if(load == false)
		{
			if (forceMult < 1.80F)
			{
				return;
			}
			if (forceMult > 1.80F)
			{
				forceMult = 1.80F;
				
				if(hasAmmo)
				{
					switch(ammo)
					{
					case 1: player.inventory.consumeInventoryItem(TFCMItems.bolt_Copper);
						break;
					case 2: player.inventory.consumeInventoryItem(TFCMItems.bolt_BismuthBronze);
						break;
					case 3: player.inventory.consumeInventoryItem(TFCMItems.bolt_Bronze);
						break;
					case 4: player.inventory.consumeInventoryItem(TFCMItems.bolt_BlackBronze);
						break;
					case 5: player.inventory.consumeInventoryItem(TFCMItems.bolt_WroughtIron);
						break;
					case 6: player.inventory.consumeInventoryItem(TFCMItems.bolt_Steel);
						break;
					case 7: player.inventory.consumeInventoryItem(TFCMItems.bolt_BlackSteel);
						break;
					case 8: player.inventory.consumeInventoryItem(TFCMItems.bolt_BlueSteel);
						break;
					case 9: player.inventory.consumeInventoryItem(TFCMItems.bolt_RedSteel);
						break;
					default:
						break;
					}
				}
				else if(hasQuiverAmmo)
				{
					this.consumeBoltInQuiver(player, ammo);
				}
				
				is.stackTagCompound.setFloat("forceMult", forceMult);
				is.stackTagCompound.setBoolean("load", true);
			}
		}
	}
	
	//Checks to see if player has ammo, and what type of ammo he/she has.
		//Selects the lowest tier of ammo first.
	public boolean hasAmmo(ItemStack is, EntityPlayer player) 
	{	
		if(player.inventory.hasItem(TFCMItems.bolt_Copper))
		{
			is.stackTagCompound.setInteger("ammo", 1);
			return true;
		}
		if(player.inventory.hasItem(TFCMItems.bolt_BismuthBronze))
		{
			is.stackTagCompound.setInteger("ammo", 2);
			return true;
		}
		if(player.inventory.hasItem(TFCMItems.bolt_Bronze))
		{
			is.stackTagCompound.setInteger("ammo", 3);
			return true;
		}
		if(player.inventory.hasItem(TFCMItems.bolt_BlackBronze))
		{
			is.stackTagCompound.setInteger("ammo", 4);
			return true;
		}
		if(player.inventory.hasItem(TFCMItems.bolt_WroughtIron))
		{
			is.stackTagCompound.setInteger("ammo", 5);
			return true;
		}
		if(player.inventory.hasItem(TFCMItems.bolt_Steel))
		{
			is.stackTagCompound.setInteger("ammo", 6);
			return true;
		}
		if(player.inventory.hasItem(TFCMItems.bolt_BlackSteel))
		{
			is.stackTagCompound.setInteger("ammo", 7);
			return true;
		}
		if(player.inventory.hasItem(TFCMItems.bolt_BlueSteel))
		{
			is.stackTagCompound.setInteger("ammo", 8);
			return true;
		}
		if(player.inventory.hasItem(TFCMItems.bolt_RedSteel))
		{
			is.stackTagCompound.setInteger("ammo", 9);
			return true;
		}

		return false;
	}
	
	//Checks the custom quiver for crossbow ammo.
	public boolean hasAmmoQuiver(ItemStack is, EntityPlayer player)
	{
		ItemStack quiver = ((InventoryPlayerTFC) player.inventory).extraEquipInventory[0];
		
		if(quiver != null && quiver.getItem() instanceof ItemCustomQuiver)
		{
			if(((ItemCustomQuiver)quiver.getItem()).hasBoltAmmo(quiver) != null)
			{
				int t = ((ItemCustomQuiver)quiver.getItem()).getQuiverMetalBoltTier(quiver);
				is.stackTagCompound.setInteger("ammo", t);
				
				return true;
			}
		}
		return false;
	}
	
	//Removes ammo from quiver.
	public void consumeBoltInQuiver(EntityPlayer player, int t)
	{
		ItemStack quiver = ((InventoryPlayerTFC) player.inventory).extraEquipInventory[0];
		
		Item ammo;
		
		switch(t)
		{
			case 1:
			{
				ammo = TFCMItems.bolt_Copper;
				break;
			}
			case 2:
			{
				ammo = TFCMItems.bolt_BismuthBronze;
				break;
			}
			case 3:
			{
				ammo = TFCMItems.bolt_Bronze;
				break;
			}
			case 4:
			{
				ammo = TFCMItems.bolt_BlackBronze;
				break;
			}
			case 5:
			{
				ammo = TFCMItems.bolt_WroughtIron;
				break;
			}
			case 6:
			{
				ammo = TFCMItems.bolt_Steel;
				break;
			}
			case 7:
			{
				ammo = TFCMItems.bolt_BlackSteel;
				break;
			}
			case 8:
			{
				ammo = TFCMItems.bolt_BlueSteel;
				break;
			}
			case 9:
			{
				ammo = TFCMItems.bolt_RedSteel;
				break;
			}
			default:
			{
				ammo = null;
				break;
			}
		}
		
		if(quiver != null && quiver.getItem() instanceof ItemCustomQuiver)
		{
			((ItemCustomQuiver)quiver.getItem()).consumeMetalAmmo(quiver, ammo, true);
		}
	}
	
	public void fireCrossbow(ItemStack is, World world, EntityPlayer player, float forceMult)
	{
		int ammo = is.getTagCompound().getInteger("ammo");
		
		float ammoMult = 0;
		
		EntityMetalBolt entityarrow = new EntityMetalBolt(world, player, 2);;
		
		switch(ammo)
		{
			case 1: 
				ammoMult = 0.6F; //Copper
				entityarrow.setPickupItem(TFCMItems.bolt_Copper);
				break;
			case 2: 
				ammoMult = 0.65F; //Bismuth Bronze
				entityarrow.setPickupItem(TFCMItems.bolt_BismuthBronze);
				break;
			case 3: 
				ammoMult = 0.7F; //Bronze
				entityarrow.setPickupItem(TFCMItems.bolt_Bronze);
				break;
			case 4: 
				ammoMult = 0.75F; //Black Bronze
				entityarrow.setPickupItem(TFCMItems.bolt_BlackBronze);
				break;
			case 5: 
				ammoMult = 0.8F; //Wrought Iron
				entityarrow.setPickupItem(TFCMItems.bolt_WroughtIron);
				break;
			case 6: 
				ammoMult = 0.85F; //Steel
				entityarrow.setPickupItem(TFCMItems.bolt_Steel);
				break;
			case 7: 
				ammoMult = 0.9F; //Black Steel
				entityarrow.setPickupItem(TFCMItems.bolt_BlackSteel);
				break; 
			case 8: 
				ammoMult = 1.0F; //Blue Steel
				entityarrow.setPickupItem(TFCMItems.bolt_BlueSteel);
				break;
			case 9: 
				ammoMult = 1.0F; //Red Steel
				entityarrow.setPickupItem(TFCMItems.bolt_RedSteel);
				break;
			default:
				ammoMult = 0.8F; //Wrought Iron
				entityarrow.setPickupItem(TFCMItems.bolt_WroughtIron);
				break;
		}
		
		entityarrow.setDamage((400.0) * ammoMult);
		
		int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, is);

		if (k > 0)
			entityarrow.setDamage(entityarrow.getDamage() + k * 50D + 25D);

		int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, is);

		if (l > 0)
			entityarrow.setKnockbackStrength(l);

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, is) > 0)
			entityarrow.setFire(100);

		//Check to see if player is in creative mode or has enchantments.
		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, is) > 0;
		
		if (flag)
			entityarrow.canBePickedUp = 2;
		
		is.damageItem(1, player);
		world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + forceMult * 0.5F);
		if (!world.isRemote)
		{
			world.spawnEntityInWorld(entityarrow);
		}
		
		is.getTagCompound().setInteger("ammo", 0);
		is.stackTagCompound.setFloat("forceMult", 0);
		is.stackTagCompound.setBoolean("load", false);
	}
	
	public float getUseSpeed(EntityPlayer player)
	{
		float speed = 60.0f;
		ItemStack[] armor = player.inventory.armorInventory;
		if(armor[0] != null && armor[0].getItem() instanceof ISize)
			speed += 20.0f / ((ISize)armor[0].getItem()).getWeight(armor[0]).multiplier;
		if(armor[1] != null && armor[1].getItem() instanceof ISize)
			speed += 20.0f / ((ISize)armor[1].getItem()).getWeight(armor[1]).multiplier;
		if(armor[2] != null && armor[2].getItem() instanceof ISize)
			speed += 20.0f / ((ISize)armor[2].getItem()).getWeight(armor[2]).multiplier;
		if(armor[3] != null && armor[3].getItem() instanceof ISize)
			speed += 20.0f / ((ISize)armor[3].getItem()).getWeight(armor[3]).multiplier;

		return speed;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);
		
		if(is.stackTagCompound != null)
		{
			if(is.getTagCompound().getBoolean("load") == true)
			{
				arraylist.add(TFC_Core.translate("gui.crossbow.loaded"));
			}
			else
			{
				arraylist.add(TFC_Core.translate("gui.crossbow.unloaded"));
			}
		}
	}
	
	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.HEAVY;
	}

	@Override
	public boolean canStack() {
		return false;
	}
	
	@Override
	public EnumItemReach getReach(ItemStack is) {
		return EnumItemReach.SHORT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("tfcm:CrossBow");
		iconArray = new IIcon[5];

		iconArray[0] = par1IconRegister.registerIcon("tfcm:CrossBow_loading_0");
		iconArray[1] = par1IconRegister.registerIcon("tfcm:CrossBow_loading_1");
		iconArray[2] = par1IconRegister.registerIcon("tfcm:CrossBow_loading_2");
		iconArray[3] = par1IconRegister.registerIcon("tfcm:CrossBow_loaded");
		iconArray[4] = par1IconRegister.registerIcon("tfcm:CrossBow");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int par1)
	{
		return iconArray[par1];
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		if(stack.stackTagCompound != null)
        {
        	if(stack.getTagCompound().getBoolean("load") == true)
        	{
        		return getItemIconForUseDuration(3); //While loaded
        	}
        	else if (usingItem != null && usingItem.getItem() == TFCMItems.crossBow)
            {
            	int j = usingItem.getMaxItemUseDuration() - useRemaining;
                float force = j / getUseSpeed(player);
            	
    			if (force >= 1.80) // Fully loaded
                {
    				return getItemIconForUseDuration(3);
    			}
    			else if (force > 0.90)
                {
    				return getItemIconForUseDuration(2);
                }
    			else if (force > 0.30)
                {
                    return getItemIconForUseDuration(1);
                }
    			else if (force > 0) // Beginning to load
                {
                    return getItemIconForUseDuration(0);
                }
            }
        }
		else
		{
			return getItemIconForUseDuration(4);
		}
		
        return getIcon(stack, renderPass);
	}
}
