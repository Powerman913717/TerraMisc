package terramisc.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import terramisc.core.TFCMRecipes;

public class ServerTickHandlerTFCM {
    @SubscribeEvent
    public void onServerWorldTick(TickEvent.WorldTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            if (e.world.provider.dimensionId == 0) {
                TFCMRecipes.initialiseAnvil();
            }
        } else if (e.phase != TickEvent.Phase.END) {
        }
    }
}