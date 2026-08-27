package client.module.player;

import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.PearlMath;
import client.util.StringParts;
import client.util.UnsafeAccess;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerPosition;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class AutoLeave extends Module {
   private SliderSetting range;
   private BooleanSetting crystals;
   private SliderSetting distKristallov;
   private BooleanSetting otklyuchatOnProverkeNaChity;
   private BooleanSetting tnt;
   private SliderSetting tntRange;
   private SliderSetting tntFyuz;
   private final ListSetting modeVyhoda;
   private static final UnsafeAccess<Protect> unsafeAccess = new UnsafeAccess<>(Protect.class);
   private boolean flag;
   private String text;

   public AutoLeave() {
      super("AutoLeave", Category.PLAYER);
      SliderSetting slidersetting = new SliderSetting("", "", 10.0, 1.0, 150.0, 1.0, StringParts.join(new String[]{" ", "б", "л", "o", "к", "о", "в"}), 0);
      slidersetting.setName("Дистанция");
      slidersetting.setDescription("Минимальная дистанция до игрока для срабатывания");
      this.range = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Кристаллы");
      booleansetting.setDescription("Выходить при обнаружении кристалла рядом");
      this.crystals = booleansetting;
      slidersetting = new SliderSetting("", "", 6.0, 1.0, 12.0, 0.5, StringParts.join(new String[]{" ", "б", "л", "o", "к", "о", "в"}), 1);
      slidersetting.setName("Дист. кристаллов");
      slidersetting.setDescription("Максимальная дистанция до кристалла");
      this.distKristallov = slidersetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Отключать при проверке на читы");
      booleansetting1.setDescription("Выключать модуль при вызове на проверку читов");
      this.otklyuchatOnProverkeNaChity = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", true);
      booleansetting2.setName("ТНТ");
      booleansetting2.setDescription("Выходить при обнаружении активированного ТНТ рядом");
      this.tnt = booleansetting2;
      slidersetting = new SliderSetting("", "", 10.0, 1.0, 50.0, 1.0, StringParts.join(new String[]{" ", "б", "л", "o", "к", "о", "в"}), 0);
      slidersetting.setName("ТНТ дистанция");
      slidersetting.setDescription("Максимальная дистанция до ТНТ");
      this.tntRange = slidersetting;
      slidersetting = new SliderSetting("", "", 40.0, 1.0, 80.0, 1.0, StringParts.join(new String[]{" ", "т", "и", "к", "o", "в"}), 0);
      slidersetting.setName("ТНТ фьюз");
      slidersetting.setDescription("Максимальный оставшийся фьюз ТНТ для срабатывания");
      this.tntFyuz = slidersetting;
      ListSetting listsetting = new ListSetting(
         "",
         "",
         List.of(StringParts.join(new String[]{"В", "ы", "х", "o", "д"}), "/hub"),
         List.of(StringParts.join(new String[]{"В", "ы", "х", "o", "д"})),
         false
      );
      listsetting.setName("Режим выхода");
      listsetting.setDescription("Способ выхода c сервера");
      this.modeVyhoda = listsetting;
      this.flag = false;
      this.text = "";
      this.distKristallov.setVisibleWhen(this.crystals::isFlag3);
      this.tntRange.setVisibleWhen(this.tnt::isFlag3);
      this.tntFyuz.setVisibleWhen(this.tnt::isFlag3);
      this.addSettings(
         new Setting[]{
            this.range, this.crystals, this.distKristallov, this.tnt, this.tntRange, this.tntFyuz, this.otklyuchatOnProverkeNaChity, this.modeVyhoda
         }
      );
   }

   @Override
   public void onTick() {
      if (!this.notInGame() && !this.flag) {
         PlayerEntity playerentity = this.getPlayerEntity();
         if (playerentity != null) {
            double d0 = this.player().distanceTo(playerentity);
            if (d0 <= this.range.getValue()) {
               int i = (int)Math.round(d0);
               String s = playerentity.getName().getString();
               Protect protect = (Protect)unsafeAccess.getModule2();
               if (protect != null) {
                  s = protect.getStringByString2(s);
               }

               this.text = s + " " + i + "м";
               this.update11();
               this.flag = true;
               return;
            }
         }

         if (this.crystals.isFlag3() && PearlMath.isWorldPlayerEntityDouble2(this.world(), this.player(), this.distKristallov.getValue())) {
            this.text = "Кристалл";
            this.update11();
            this.flag = true;
         } else {
            if (this.tnt.isFlag3()) {
               World world1 = this.world();
               PlayerEntity playerentity2 = this.player();
               double d2 = this.tntRange.getValue();
               int j = (int)this.tntFyuz.getValue();
               double d1 = d2;
               PlayerEntity playerentity1 = playerentity2;
               World world = world1;
               if (PearlMath.isIntWorldDoublePlayerEntity(j, world, d1, playerentity1)) {
                  this.text = "ТНТ";
                  this.update11();
                  this.flag = true;
               }
            }
         }
      }
   }

   @Override
   public void onDisable() {
      this.flag = false;
   }

   @Override
   public void update3() {
      this.flag = false;
      this.text = "";
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (this.otklyuchatOnProverkeNaChity.isFlag3() && !this.flag) {
         if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
            if (!this.notInGame()) {
               if (packetEvent.getPacket() instanceof PlayerPositionLookS2CPacket playerpositionlooks2cpacket) {
                  PlayerPositionLookS2CPacket playerpositionlooks2cpacket1 = playerpositionlooks2cpacket;

                  try {
                     playerpositionlooks2cpacket1.teleportId();
                  } catch (Throwable throwable2) {
                     throw new MatchException(throwable2.toString(), throwable2);
                  }

                  if (true) {
                     playerpositionlooks2cpacket1 = playerpositionlooks2cpacket;

                     PlayerPosition playerposition = playerpositionlooks2cpacket1.change();
                     playerpositionlooks2cpacket1 = playerpositionlooks2cpacket;

                     Set set = playerpositionlooks2cpacket1.relatives();
                     double d8 = this.player().getX();
                     double d9 = this.player().getY();
                     double d0 = this.player().getZ();
                     double d1 = set.contains(PositionFlag.X) ? d8 + playerposition.position().x : playerposition.position().x;
                     double d2 = set.contains(PositionFlag.Y) ? d9 + playerposition.position().y : playerposition.position().y;
                     double d3 = set.contains(PositionFlag.Z) ? d0 + playerposition.position().z : playerposition.position().z;
                     double d4 = d1 - d8;
                     double d5 = d2 - d9;
                     double d6 = d3 - d0;
                     double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
                     if (d7 >= 3.0) {
                        this.setEnabled(false);
                     }

                     return;
                  }
               }
            }
         }
      }
   }

   private PlayerEntity getPlayerEntity() {
      if (this.notInGame()) {
         return null;
      } else {
         PlayerEntity playerentity = null;
         double d0 = Double.MAX_VALUE;

         for (PlayerEntity playerentity1 : this.world().getPlayers()) {
            if (playerentity1 != this.player() && !this.isFriend(playerentity1)) {
               double d1 = this.player().distanceTo(playerentity1);
               if (d1 < d0) {
                  d0 = d1;
                  playerentity = playerentity1;
               }
            }
         }

         return playerentity;
      }
   }

   @Override
   public String getDisplayName() {
      return this.text.isEmpty() ? super.getDisplayName() : super.getDisplayName() + " §7[" + this.text + "]";
   }

   private void update11() {
      String s = this.modeVyhoda.getString2();
      if ("Выхoд".equals(s)) {
         if (this.networkHandler() != null) {
            this.networkHandler().getConnection().disconnect(Text.of("Авто-лив"));
         }
      } else if ("/hub".equals(s) && this.player() != null) {
         this.networkHandler().sendChatCommand("hub");
      }

      this.setEnabled(false);
   }

   @Override
   public void update4() {
      this.flag = false;
   }

   @Override
   public void onEnable() {
      this.flag = false;
      this.text = "";
   }
}
