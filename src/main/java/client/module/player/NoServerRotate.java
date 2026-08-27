package client.module.player;

import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import java.util.Set;
import net.minecraft.entity.player.PlayerPosition;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class NoServerRotate extends Module {
   public NoServerRotate() {
      super("NoServerRotate", Category.PLAYER);
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (!this.notInGame()) {
            if (packetEvent.getPacket() instanceof PlayerPositionLookS2CPacket playerpositionlooks2cpacket) {
               PlayerPositionLookS2CPacket playerpositionlooks2cpacket1 = playerpositionlooks2cpacket;

               int i = playerpositionlooks2cpacket1.teleportId();
               if (true) {
                  playerpositionlooks2cpacket1 = playerpositionlooks2cpacket;

                  PlayerPosition playerposition1 = playerpositionlooks2cpacket1.change();
                  playerpositionlooks2cpacket1 = playerpositionlooks2cpacket;

                  Set set = playerpositionlooks2cpacket1.relatives();
                  packetEvent.setFlag(true);
                  PlayerPosition playerposition = new PlayerPosition(
                     playerposition1.position(), playerposition1.deltaMovement(), this.client().player.getYaw(), this.client().player.getPitch()
                  );
                  PacketEvent.onPacket(new PlayerPositionLookS2CPacket(i, playerposition, set));
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
   }
}
