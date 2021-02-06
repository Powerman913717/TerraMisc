package terramisc.handlers;

import com.dunk.tfc.Core.Player.InventoryPlayerTFC;
import com.dunk.tfc.api.Interfaces.IEquipable;
import com.dunk.tfc.api.TFCItems;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import terramisc.core.TFCMItems;
import terramisc.items.tools.ItemCustomArrow;
import terramisc.items.tools.ItemCustomBolt;
import terramisc.items.tools.ItemCustomQuiver;

public class EntityLivingHandlerTFCM {

    @SubscribeEvent
    public void handleItemPickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack item = event.item.getEntityItem();
        if (player.inventory instanceof InventoryPlayerTFC) {
            ItemStack backItem = ((InventoryPlayerTFC) player.inventory).extraEquipInventory[0];

            if (backItem == null && item.getItem() instanceof IEquipable)
            {
                IEquipable equipment = (IEquipable) item.getItem();
                if (equipment.getEquipType(item) == IEquipable.EquipType.BACK && (equipment == TFCMItems.itemQuiver || equipment.getTooHeavyToCarry(item)))
                {
                    player.inventory.setInventorySlotContents(36, item.copy());
                    item.stackSize = 0;
                    event.item.setEntityItemStack(item);
                }
            }

            // Back slot contains a quiver, handle picking up arrows and javelins.
            if (backItem != null && backItem.getItem() instanceof ItemCustomQuiver) {
                ItemCustomQuiver quiver = (ItemCustomQuiver) backItem.getItem();

                // Attempt to add arrows that are picked up to the quiver instead of standard inventory.
                if (item.getItem() instanceof ItemCustomArrow) {
                    ItemStack is = quiver.addItem(backItem, item);
                    if (is != null)
                        event.item.setEntityItemStack(is);
                    else {
                        is = item;
                        is.stackSize = 0;
                        event.item.setEntityItemStack(is);
                        event.setResult(Result.DENY);
                    }
                }
                // Attempt to add bolts that are picked up to the quiver instead of standard inventory.
                else if (item.getItem() instanceof ItemCustomBolt) {
                    ItemStack is = quiver.addItem(backItem, item);
                    if (is != null)
                        event.item.setEntityItemStack(is);
                    else {
                        is = item;
                        is.stackSize = 0;
                        event.item.setEntityItemStack(is);
                        event.setResult(Result.DENY);
                    }
                }
            }
        }
    }
}
