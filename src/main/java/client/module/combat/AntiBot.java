package client.module.combat;

import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.BooleanSetting;
import client.setting.Setting;
import com.google.common.collect.Lists;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;

public class AntiBot extends Module {
   public static final CopyOnWriteArrayList<PlayerEntity> copyOnWriteArrayList = Lists.newCopyOnWriteArrayList();
   private static final Set<UUID> set = new HashSet<>();
   public BooleanSetting udalyatIzMira;
   private final Set<UUID> set2;
   private final Set<UUID> set3;

   public AntiBot() {
      super("AntiBot", Category.COMBAT);
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Удалять из мира");
      booleansetting.setDescription("Полностью скрывает обнаруженных ботов.");
      this.udalyatIzMira = booleansetting;
      this.set2 = new HashSet<>();
      this.set3 = new HashSet<>();
      this.addSettings(new Setting[]{this.udalyatIzMira});
   }

   @Override
   public void onTick() {
      if (this.world() != null) {
         List<? extends PlayerEntity> list = this.world().getPlayers();
         Set setx = this.set2;
         setx.clear();
         PlayerEntity playerentity = this.player();

         for (PlayerEntity playerentity1 : list) {
            setx.add(playerentity1.getUuid());
         }

         copyOnWriteArrayList.removeIf(p0 -> AntiBot.isSetPlayerEntity(setx, p0));
         set.removeIf(p0 -> AntiBot.isSetUUID(setx, p0));

         for (PlayerEntity playerentity2 : list) {
            if (playerentity2 != playerentity) {
               String s = playerentity2.getName().getString();
               UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + s).getBytes());
               boolean flag = !playerentity2.getUuid().equals(uuid);
               boolean flag1 = !s.contains("NPC") && !s.startsWith("[ZNPC]");
               if (flag && flag1) {
                  set.add(playerentity2.getUuid());
               }
            }
         }

         Set set1 = this.set3;
         set1.clear();

         for (PlayerEntity playerentity3 : copyOnWriteArrayList) {
            set1.add(playerentity3.getUuid());
         }

         for (PlayerEntity playerentity4 : list) {
            UUID uuid1 = playerentity4.getUuid();
            if (set.contains(uuid1) && !set1.contains(uuid1)) {
               copyOnWriteArrayList.add(playerentity4);
            }
         }

         copyOnWriteArrayList.removeIf(AntiBot::isPlayerEntity);
         if (this.udalyatIzMira.isFlag3()) {
            for (PlayerEntity playerentity5 : copyOnWriteArrayList) {
               this.clientWorld().removeEntity(playerentity5.getId(), RemovalReason.DISCARDED);
            }
         }
      }
   }

   @Override
   public void onDisable() {
      this.update11();
   }

   private static boolean isSetPlayerEntity(Set set, PlayerEntity playerEntity) {
      return !set.contains(playerEntity.getUuid());
   }

   private static boolean isSetUUID(Set set, UUID uUID) {
      return !set.contains(uUID);
   }

   private void update11() {
      copyOnWriteArrayList.clear();
      set.clear();
   }

   private static boolean isPlayerEntity(PlayerEntity playerEntity) {
      return !set.contains(playerEntity.getUuid());
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof PlayerRemoveS2CPacket playerremoves2cpacket) {
            for (UUID uuid : playerremoves2cpacket.profileIds()) {
               set.remove(uuid);
            }
         }
      }
   }

   public static boolean isLivingEntity(LivingEntity livingEntity) {
      return !(livingEntity instanceof PlayerEntity playerentity) ? false : copyOnWriteArrayList.contains(playerentity) || set.contains(playerentity.getUuid());
   }

   @Override
   public void onEnable() {
      this.update11();
   }
}
