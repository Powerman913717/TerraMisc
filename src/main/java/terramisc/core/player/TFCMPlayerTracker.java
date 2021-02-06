package terramisc.core.player;

import com.dunk.tfc.Handlers.Network.AbstractPacket;
import com.dunk.tfc.TerraFirmaCraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerDisconnectionFromClientEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import terramisc.handlers.network.TFCMInitClientWorldPacket;

public class TFCMPlayerTracker {
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent e) {
        AbstractPacket packet = new TFCMInitClientWorldPacket();
        TerraFirmaCraft.PACKET_PIPELINE.sendTo(packet, (EntityPlayerMP) e.player);
    }

    @SubscribeEvent
    public void onClientConnect(ClientConnectedToServerEvent e) {
    }

    @SubscribeEvent
    public void onClientDisconnect(ServerDisconnectionFromClientEvent e) {
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerRespawnEvent e) {
    }

    @SubscribeEvent
    public void notifyPickup(ItemPickupEvent e) {
    }

    @SubscribeEvent
    public void onPlayerTossEvent(ItemTossEvent e) {
    }
}
