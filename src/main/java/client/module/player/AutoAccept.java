package client.module.player;

import client.data.SystemFriend;
import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.BooleanSetting;
import client.setting.InputSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.StringParts;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;

public class AutoAccept extends Module {
   private BooleanSetting onlyFriends;
   private SliderSetting delay;
   private InputSetting komanda;
   private int value235;
   private boolean flag;

   public AutoAccept() {
      super("AutoAccept", Category.PLAYER);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Только друзья");
      booleansetting.setDescription("Принимать тп только от друзей");
      this.onlyFriends = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.0, 0.0, 40.0, 1.0, StringParts.join(new String[]{" ", "т", "и", "к", "o", "в"}), 0);
      slidersetting.setName("Задержка");
      slidersetting.setDescription("Задержка перед принятием тп");
      this.delay = slidersetting;
      InputSetting inputsetting = new InputSetting("", "", "tpaccept", StringParts.join(new String[]{"/", "к", "o", "м", "а", "н", "д", "а"}));
      inputsetting.setName("Команда");
      inputsetting.setDescription("Команда для принятия тп");
      this.komanda = inputsetting;
      this.value235 = 0;
      this.flag = false;
      this.addSettings(new Setting[]{this.onlyFriends, this.delay, this.komanda});
   }

   @Override
   public void onTick() {
      if (!this.notInGame() && this.flag) {
         if (this.value235 > 0) {
            this.value235--;
         } else {
            String s = this.komanda.getText().trim();
            if (!s.isEmpty() && this.networkHandler() != null) {
               this.networkHandler().sendChatCommand(s);
            }

            this.flag = false;
         }
      }
   }

   private boolean isString(String text2) {
      for (String s : SystemFriend.getInstance().getSet()) {
         if (text2.contains(s.toLowerCase())) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void onDisable() {
      this.flag = false;
      this.value235 = 0;
   }

   private boolean isString2(String text2) {
      return (text2.contains("teleport") || text2.contains("телепорт") || text2.contains("tpa") || text2.contains("tpask"))
         && (text2.contains("request") || text2.contains("просит") || text2.contains("запрос") || text2.contains("has requested") || text2.contains("wants to"));
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gamemessages2cpacket) {
            GameMessageS2CPacket gamemessages2cpacket1 = gamemessages2cpacket;

            Text text = gamemessages2cpacket1.content();
            gamemessages2cpacket1 = gamemessages2cpacket;

            boolean flagx = gamemessages2cpacket1.overlay();
            if (true) {
               if (flagx) {
                  return;
               }

               String s = text.getString().toLowerCase();
               if (!this.isString2(s)) {
                  return;
               }

               if (this.onlyFriends.isFlag3() && !this.isString(s)) {
                  return;
               }

               this.value235 = (int)this.delay.getValue();
               this.flag = true;
               return;
            }
         }
      }
   }

   @Override
   public void onEnable() {
      this.flag = false;
      this.value235 = 0;
   }
}
