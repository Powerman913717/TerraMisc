package terramisc.items.tools;

import com.dunk.tfc.Core.Player.InventoryPlayerTFC;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.ISize;
import com.dunk.tfc.api.TFCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import terramisc.core.TFCMItems;
import terramisc.core.TFCMOptions;
import terramisc.entities.EntityMetalArrow;

import java.util.List;

public class ItemCustomLongbow extends ItemBow implements ISize {
    private IIcon[] iconArray;

    public ItemCustomLongbow() {
        super();
        this.maxStackSize = 1;
        this.setMaxDamage(800);
        this.setCreativeTab(TFCTabs.TFC_WEAPONS);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
        if (is.stackTagCompound == null) {
            is.stackTagCompound = new NBTTagCompound();
            is.stackTagCompound.setInteger("ammo", 0);
        }

        //Check to see if player is in creative mode or has enchantments.
        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, is) > 0;
        //First we check the if there is ammo in the player's quiver
        boolean hasQuiverAmmo = flag || this.hasAmmoQuiver(is, player);
        boolean hasAmmo = false;
        //If there was no quiver or ammo, we check the inventory.
        if (!hasQuiverAmmo) {
            hasAmmo = this.hasAmmo(is, player);
        }

        if (hasQuiverAmmo || hasAmmo) {
            ArrowNockEvent event = new ArrowNockEvent(player, is);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled())
                return event.result;

            if (player.capabilities.isCreativeMode || (hasQuiverAmmo || hasAmmo)) ;
            player.setItemInUse(is, this.getMaxItemUseDuration(is));

            return is;
        }

        return is;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int inUseCount) {
        int ammo = is.getTagCompound().getInteger("ammo");

        int j = this.getMaxItemUseDuration(is) - inUseCount;
        double drawSpeed = TFCMOptions.LongbowDrawSpeedMult;

        ArrowLooseEvent event = new ArrowLooseEvent(player, is, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return;
        j = event.charge;

        //Check to see if player is in creative mode or has enchantments.
        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, is) > 0;
        //First we check the if there is ammo in the player's quiver
        boolean hasQuiverAmmo = flag || this.hasAmmoQuiver(is, player);
        boolean hasAmmo = false;
        //If there was no quiver or ammo, we check the inventory.
        if (!hasQuiverAmmo) {
            hasAmmo = this.hasAmmo(is, player);
        }

        if (hasQuiverAmmo || hasAmmo) {
            float forceMult = j / getUseSpeed(player);
            float ammoMult = 0;

            if (forceMult < (0.25D * drawSpeed))
                return;

            if (forceMult > (1.00F * drawSpeed))
                forceMult = (float) (1.00F * drawSpeed);

            EntityMetalArrow entityarrow = new EntityMetalArrow(world, player, (float) ((forceMult / drawSpeed) * 3.0F));

            switch (ammo) {
                case 1:
                    ammoMult = 0.55F; //Stone
                    entityarrow.setPickupItem(TFCItems.arrow);
                    break;
                case 2:
                    ammoMult = 0.6F; //Copper
                    entityarrow.setPickupItem(TFCMItems.arrow_Copper);
                    break;
                case 3:
                    ammoMult = 0.65F; //Bismuth Bronze
                    entityarrow.setPickupItem(TFCMItems.arrow_BismuthBronze);
                    break;
                case 4:
                    ammoMult = 0.7F; //Bronze
                    entityarrow.setPickupItem(TFCMItems.arrow_Bronze);
                    break;
                case 5:
                    ammoMult = 0.75F; //Black Bronze
                    entityarrow.setPickupItem(TFCMItems.arrow_BlackBronze);
                    break;
                case 6:
                    ammoMult = 0.8F; //Wrought Iron
                    entityarrow.setPickupItem(TFCMItems.arrow_WroughtIron);
                    break;
                case 7:
                    ammoMult = 0.85F; //Steel
                    entityarrow.setPickupItem(TFCMItems.arrow_Steel);
                    break;
                case 8:
                    ammoMult = 0.9F; //Black Steel
                    entityarrow.setPickupItem(TFCMItems.arrow_BlackSteel);
                    break;
                case 9:
                    ammoMult = 1.0F; //Blue Steel
                    entityarrow.setPickupItem(TFCMItems.arrow_BlueSteel);
                    break;
                case 10:
                    ammoMult = 1.0F; //Red Steel
                    entityarrow.setPickupItem(TFCMItems.arrow_RedSteel);
                    break;
                default:
                    ammoMult = 0.8F; //Wrought Iron
                    entityarrow.setPickupItem(TFCMItems.arrow_WroughtIron);
                    break;
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, is);

            if (l > 0)
                entityarrow.setKnockbackStrength(l);

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, is) > 0)
                entityarrow.setFire(100);

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, is);

            if (k > 0)
                entityarrow.setDamage(entityarrow.getDamage() + k * 50D + 25D);

            if (flag)
                entityarrow.canBePickedUp = 2;

            if (hasAmmo) {
                switch (ammo) {
                    case 1:
                        player.inventory.consumeInventoryItem(TFCItems.arrow);
                        break;
                    case 2:
                        player.inventory.consumeInventoryItem(TFCMItems.arrow_Copper);
                        break;
                    case 3:
                        player.inventory.consumeInventoryItem(TFCMItems.arrow_BismuthBronze);
                        break;
                    case 4:
                        player.inventory.consumeInventoryItem(TFCMItems.arrow_Bronze);
                        break;
                    case 5:
                        player.inventory.consumeInventoryItem(TFCMItems.arrow_BlackBronze);
                        break;
                    case 6:
                        player.inventory.consumeInventoryItem(TFCMItems.arrow_WroughtIron);
                        break;
                    case 7:
                        player.inventory.consumeInventoryItem(TFCMItems.arrow_Steel);
                        break;
                    case 8:
                        player.inventory.consumeInventoryItem(TFCMItems.arrow_BlackSteel);
                        break;
                    case 9:
                        player.inventory.consumeInventoryItem(TFCMItems.arrow_BlueSteel);
                        break;
                    case 10:
                        player.inventory.consumeInventoryItem(TFCMItems.arrow_RedSteel);
                        break;
                    default:
                        break;
                }
            } else if (hasQuiverAmmo) {
                this.consumeArrowInQuiver(player, ammo);
            }

            is.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + forceMult * 0.5F);

            entityarrow.setDamage((forceMult * 300.0 * ammoMult));
            if (forceMult == (1.00F * drawSpeed))
                entityarrow.setIsCritical(true);

            if (!world.isRemote) {
                world.spawnEntityInWorld(entityarrow);
            }

            is.getTagCompound().setInteger("ammo", 0);
        }
    }

    //Checks to see if player has ammo, and what type of ammo he/she has.
    //Selects the lowest tier of ammo first.
    public boolean hasAmmo(ItemStack is, EntityPlayer player) {
        if (player.inventory.hasItem(TFCItems.arrow)) {
            is.stackTagCompound.setInteger("ammo", 1);
            return true;
        } else if (player.inventory.hasItem(TFCMItems.arrow_Copper)) {
            is.stackTagCompound.setInteger("ammo", 2);
            return true;
        } else if (player.inventory.hasItem(TFCMItems.arrow_BismuthBronze)) {
            is.stackTagCompound.setInteger("ammo", 3);
            return true;
        } else if (player.inventory.hasItem(TFCMItems.arrow_Bronze)) {
            is.stackTagCompound.setInteger("ammo", 4);
            return true;
        } else if (player.inventory.hasItem(TFCMItems.arrow_BlackBronze)) {
            is.stackTagCompound.setInteger("ammo", 5);
            return true;
        } else if (player.inventory.hasItem(TFCMItems.arrow_WroughtIron)) {
            is.stackTagCompound.setInteger("ammo", 6);
            return true;
        } else if (player.inventory.hasItem(TFCMItems.arrow_Steel)) {
            is.stackTagCompound.setInteger("ammo", 7);
            return true;
        } else if (player.inventory.hasItem(TFCMItems.arrow_BlackSteel)) {
            is.stackTagCompound.setInteger("ammo", 8);
            return true;
        } else if (player.inventory.hasItem(TFCMItems.arrow_BlueSteel)) {
            is.stackTagCompound.setInteger("ammo", 9);
            return true;
        } else if (player.inventory.hasItem(TFCMItems.arrow_RedSteel)) {
            is.stackTagCompound.setInteger("ammo", 10);
            return true;
        }

        return false;
    }

    //Checks the custom quiver for bow ammo.
    public boolean hasAmmoQuiver(ItemStack is, EntityPlayer player) {
        ItemStack quiver = ((InventoryPlayerTFC) player.inventory).extraEquipInventory[0];

        if (quiver != null && quiver.getItem() instanceof ItemCustomQuiver) {
            if (((ItemCustomQuiver) quiver.getItem()).hasArrowAmmo(quiver) != null) {
                int t = ((ItemCustomQuiver) quiver.getItem()).getQuiverMetalArrowTier(quiver);
                is.stackTagCompound.setInteger("ammo", t);

                return true;
            }
        }
        return false;
    }

    //Removes ammo from quiver.
    public void consumeArrowInQuiver(EntityPlayer player, int t) {
        ItemStack quiver = ((InventoryPlayerTFC) player.inventory).extraEquipInventory[0];

        Item ammo;

        switch (t) {
            case 1: {
                ammo = TFCItems.arrow;
                break;
            }
            case 2: {
                ammo = TFCMItems.arrow_Copper;
                break;
            }
            case 3: {
                ammo = TFCMItems.arrow_BismuthBronze;
                break;
            }
            case 4: {
                ammo = TFCMItems.arrow_Bronze;
                break;
            }
            case 5: {
                ammo = TFCMItems.arrow_BlackBronze;
                break;
            }
            case 6: {
                ammo = TFCMItems.arrow_WroughtIron;
                break;
            }
            case 7: {
                ammo = TFCMItems.arrow_Steel;
                break;
            }
            case 8: {
                ammo = TFCMItems.arrow_BlackSteel;
                break;
            }
            case 9: {
                ammo = TFCMItems.arrow_BlueSteel;
                break;
            }
            case 10: {
                ammo = TFCMItems.arrow_RedSteel;
                break;
            }
            default: {
                ammo = null;
                break;
            }
        }

        if (quiver != null && quiver.getItem() instanceof ItemCustomQuiver) {
            ((ItemCustomQuiver) quiver.getItem()).consumeMetalAmmo(quiver, ammo, true);
        }
    }

    public float getUseSpeed(EntityPlayer player) {
        float speed = 60.0f;
        ItemStack[] armor = player.inventory.armorInventory;
        if (armor[0] != null && armor[0].getItem() instanceof ISize)
            speed += 20.0f / ((ISize) armor[0].getItem()).getWeight(armor[0]).multiplier;
        if (armor[1] != null && armor[1].getItem() instanceof ISize)
            speed += 20.0f / ((ISize) armor[1].getItem()).getWeight(armor[1]).multiplier;
        if (armor[2] != null && armor[2].getItem() instanceof ISize)
            speed += 20.0f / ((ISize) armor[2].getItem()).getWeight(armor[2]).multiplier;
        if (armor[3] != null && armor[3].getItem() instanceof ISize)
            speed += 20.0f / ((ISize) armor[3].getItem()).getWeight(armor[3]).multiplier;

        return speed;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) {
        ItemTerra.addSizeInformation(is, arraylist);
    }

    @Override
    public EnumSize getSize(ItemStack is) {
        return EnumSize.HUGE;
    }

    @Override
    public EnumWeight getWeight(ItemStack is) {
        return EnumWeight.LIGHT;
    }

    @Override
    public boolean canStack() {
        return false;
    }

    @Override
    public EnumItemReach getReach(ItemStack is) {
        return EnumItemReach.SHORT;
    }

    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("tfcm:LongBow");
        iconArray = new IIcon[4];

        iconArray[0] = par1IconRegister.registerIcon("tfcm:LongBow_pulling_0");
        iconArray[1] = par1IconRegister.registerIcon("tfcm:LongBow_pulling_1");
        iconArray[2] = par1IconRegister.registerIcon("tfcm:LongBow_pulling_2");
        iconArray[3] = par1IconRegister.registerIcon("tfcm:LongBow_pulling_3");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getItemIconForUseDuration(int par1) {
        return iconArray[par1];
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (usingItem != null && usingItem.getItem() == this) {
            int j = usingItem.getMaxItemUseDuration() - useRemaining;
            double drawSpeed = TFCMOptions.LongbowDrawSpeedMult;
            float force = j / getUseSpeed(player);

            if (force >= (1.00 * drawSpeed)) // Fully drawn
            {
                return getItemIconForUseDuration(3);
            } else if (force > 0.50 * drawSpeed) {
                return getItemIconForUseDuration(2);
            } else if (force > 0.25 * drawSpeed) // Minimum required force to fire
            {
                return getItemIconForUseDuration(1);
            } else if (force > 0) {
                return getItemIconForUseDuration(0);
            }
        }
        return getIcon(stack, renderPass);
    }
}
