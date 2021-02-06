package terramisc.handlers.network;

import com.dunk.tfc.Handlers.Network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import terramisc.core.TFCMRecipes;

public class TFCMInitClientWorldPacket extends AbstractPacket {
    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        TFCMRecipes.initialiseAnvil();
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    }
}
