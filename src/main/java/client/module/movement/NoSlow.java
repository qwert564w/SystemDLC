package client.module.movement;

import client.data.NoSlowState;
import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.util.CritChecks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;

public class NoSlow extends Module {
   private final BooleanSetting onlyNaZemle;
   private final BooleanSetting onlyArbalet;
   private int value235;

   public NoSlow() {
      super("NoSlow", Category.MOVEMENT);
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Только на земле");
      booleansetting.setDescription("Убирает замедление только если игрок на земле.");
      this.onlyNaZemle = booleansetting;
      booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Только арбалет");
      booleansetting.setDescription("Работает только при использовании арбалета.");
      this.onlyArbalet = booleansetting;
      this.value235 = 0;
      this.addSettings(new Setting[]{this.onlyNaZemle, this.onlyArbalet});
   }

   @Override
   public void onDisable() {
      this.value235 = 0;
   }

   @Override
   public void onNoSlowState(NoSlowState noSlowState) {
      if (!this.notInGame() && this.clientPlayer() != null) {
         if (this.clientPlayer().isUsingItem()) {
            if (!this.onlyNaZemle.isFlag3() || this.clientPlayer().isOnGround()) {
               if (!this.onlyArbalet.isFlag3() || CritChecks.isClass(CrossbowItem.class)) {
                  noSlowState.setFlag(true);
               }
            }
         }
      }
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (!this.notInGame()) {
         if (packetEvent.getPacketDirection() == PacketDirection.SEND) {
            if (packetEvent.getPacket() instanceof PlayerInteractItemC2SPacket) {
               ClientPlayerEntity clientplayerentity = this.clientPlayer();
               if (clientplayerentity != null) {
                  if (!this.onlyNaZemle.isFlag3() || clientplayerentity.isOnGround()) {
                     if (!this.onlyArbalet.isFlag3() || CritChecks.isClass(CrossbowItem.class)) {
                        if (this.value235 > 1 && clientplayerentity.getItemUseTime() > 1) {
                           this.value235 = 0;
                           packetEvent.setFlag(true);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void update8() {
      if (!this.notInGame() && this.clientPlayer() != null) {
         if (this.clientPlayer().isUsingItem()) {
            this.value235++;
         } else {
            this.value235 = 0;
         }
      }
   }
}
