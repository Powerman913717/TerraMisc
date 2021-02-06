package terramisc.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import terramisc.core.TFCMAchievements;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMItems;
import terramisc.items.tools.ItemCustomArmor;
import terramisc.items.tools.ItemCustomHalberd;
import terramisc.items.tools.ItemCustomPoniard;
import terramisc.items.tools.ItemCustomWarHammer;

public class EventListener {
    @SubscribeEvent
    public void crafting(ItemCraftedEvent event) {
        if (event.crafting.getItem() instanceof ItemCustomHalberd) {
            event.player.addStat(TFCMAchievements.achHalberd, 1);
        }

        if (event.crafting.getItem() instanceof ItemCustomWarHammer) {
            event.player.addStat(TFCMAchievements.achWarHammer, 1);
        }

        if (event.crafting.getItem() instanceof ItemCustomPoniard) {
            event.player.addStat(TFCMAchievements.achPoniard, 1);
        }

        if (event.crafting.getItem() == TFCMItems.longBow) {
            event.player.addStat(TFCMAchievements.achLongbow, 1);
        }

        if (event.crafting.getItem() == TFCMItems.crossBow) {
            event.player.addStat(TFCMAchievements.achCrossbow, 1);
        }

        if (event.crafting.getItem() instanceof ItemCustomArmor) {
            event.player.addStat(TFCMAchievements.achChainArmor, 1);
        }

        if (event.crafting.getItem() == Item.getItemFromBlock(TFCMBlocks.blockTallowCandle)) {
            event.player.addStat(TFCMAchievements.achCandle, 1);
        }

        if (event.crafting.getItem() == Items.comparator) {
            event.player.addStat(TFCMAchievements.achRedstoneTiles, 1);
        }

        if (event.crafting.getItem() == Items.repeater) {
            event.player.addStat(TFCMAchievements.achRedstoneTiles, 1);
        }

        if (event.crafting.getItem() == Item.getItemFromBlock(Blocks.piston)) {
            event.player.addStat(TFCMAchievements.achPiston, 1);
        }

        if (event.crafting.getItem() == Item.getItemFromBlock(TFCMBlocks.blockFruitPress)) {
            event.player.addStat(TFCMAchievements.achFruitPress, 1);
        }
    }

    @SubscribeEvent
    public void pickup(ItemPickupEvent event) {
        if (event.pickedUp.getEntityItem().getItem() == TFCMItems.suet) {
            event.player.addStat(TFCMAchievements.achSuet, 1);
        }
    }
}
