package client.module.player;

import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.BooleanSetting;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookAndOnGround;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;
import net.minecraft.util.math.Vec3d;

public class AirStuck extends Module {
   private static AirStuck INSTANCE;
   private Vec3d vec3d = Vec3d.ZERO;
   private BooleanSetting otmenitDvizhenie;
   private BooleanSetting besshumnayaZamorozka;

   public AirStuck() {
      super("AirStuck", Category.PLAYER);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Отменить движение");
      booleansetting.setDescription("Блокирует ввод движения игрока");
      this.otmenitDvizhenie = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Бесшумная заморозка");
      booleansetting.setDescription("Подменяет позицию в исходящих пакетах на замороженную");
      this.besshumnayaZamorozka = booleansetting;
      INSTANCE = this;
      this.addSetting(this.otmenitDvizhenie);
      this.addSetting(this.besshumnayaZamorozka);
   }

   @Override
   public void onTick() {
      if (!this.notInGame() && this.vec3d != Vec3d.ZERO) {
         this.player().setPosition(this.vec3d.x, this.vec3d.y, this.vec3d.z);
         this.player().setVelocity(Vec3d.ZERO);
         this.player().fallDistance = 0.0F;
         if (this.otmenitDvizhenie.isFlag3()) {
            this.clientPlayer().input.movementForward = 0.0F;
            this.clientPlayer().input.movementSideways = 0.0F;
         }
      }
   }

   @Override
   public void onDisable() {
      if (!this.notInGame() && this.vec3d != Vec3d.ZERO) {
         this.player().setPosition(this.vec3d.x, this.vec3d.y, this.vec3d.z);
         this.player().setVelocity(Vec3d.ZERO);
         this.player().fallDistance = 0.0F;
      }

      this.vec3d = Vec3d.ZERO;
   }

   public static AirStuck getInstance() {
      return INSTANCE;
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.SEND) {
         if (this.besshumnayaZamorozka.isFlag3()) {
            if (!this.notInGame() && this.vec3d != Vec3d.ZERO) {
               if (packetEvent.getPacket() instanceof PlayerMoveC2SPacket playermovec2spacket) {
                  if (playermovec2spacket.changesPosition()) {
                     packetEvent.setFlag(true);
                     boolean flag1 = playermovec2spacket.isOnGround();
                     boolean flag = playermovec2spacket.horizontalCollision();
                     Object object;
                     if (playermovec2spacket.changesLook()) {
                        float f = playermovec2spacket.getYaw(this.player().getYaw());
                        float f1 = playermovec2spacket.getPitch(this.player().getPitch());
                        object = new LookAndOnGround(f, f1, flag1, flag);
                     } else {
                        object = new OnGroundOnly(flag1, flag);
                     }

                     PacketEvent.onPacket2((Packet)object);
                  }
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
      if (!this.notInGame()) {
         this.vec3d = this.player().getPos();
      }
   }
}
