package client.module.player;

import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.ConnectionHooks;
import client.network.PacketEvent;
import java.util.UUID;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket.Status;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;

public class RPSpoofer extends Module {
   public RPSpoofer() {
      super("RPSpoofer", Category.PLAYER);
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof ResourcePackSendS2CPacket resourcepacksends2cpacket) {
            packetEvent.setFlag(true);
            ClientConnection clientconnection = ConnectionHooks.getClientConnection();
            if (clientconnection != null && clientconnection.isOpen()) {
               UUID uuid = resourcepacksends2cpacket.id();
               clientconnection.send(new ResourcePackStatusC2SPacket(uuid, Status.ACCEPTED));
               clientconnection.send(new ResourcePackStatusC2SPacket(uuid, Status.SUCCESSFULLY_LOADED));
            }
         }
      }
   }

   @Override
   public void onEnable() {
   }
}
