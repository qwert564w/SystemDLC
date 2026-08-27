package client.module.render;

import client.data.ChoiceOption;
import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.BooleanSetting;
import client.setting.ChoiceSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.UnsafeFields;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public class FriendChecks extends Module {
   private static FriendChecks INSTANCE;
   private BooleanSetting opacityDruzey;
   private ChoiceSetting modeProzrachnosti;
   private SliderSetting opacity;
   private SliderSetting rangeProzrachnosti;
   private BooleanSetting neBitDruzey;

   public FriendChecks() {
      super("FriendChecks", Category.RENDER);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Прозрачность друзей");
      booleansetting.setDescription("Делать друзей прозрачными");
      this.opacityDruzey = booleansetting;
      ChoiceSetting choicesetting = new ChoiceSetting("", "", new ChoiceOption("Статик"), new ChoiceOption("Динамик"), true);
      choicesetting.setName("Режим прозрачности");
      choicesetting.setDescription("Тип прозрачности друзей");
      this.modeProzrachnosti = choicesetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.4, 0.01, 1.0, 0.05);
      slidersetting.setName("Прозрачность");
      slidersetting.setDescription("Уровень прозрачности (в динамике — минимальная альфа вплотную)");
      this.opacity = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 8.0, 1.0, 64.0, 1.0);
      slidersetting1.setName("Дистанция прозрачности");
      slidersetting1.setDescription("На этой дистанции друг снова полностью виден");
      this.rangeProzrachnosti = slidersetting1;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Не бить друзей");
      booleansetting.setDescription("Отменяет атаку по игрокам из списка друзей");
      this.neBitDruzey = booleansetting;
      this.modeProzrachnosti.setVisibleWhen(this.opacityDruzey::isFlag3);
      this.opacity.setVisibleWhen(this.opacityDruzey::isFlag3);
      this.rangeProzrachnosti.setVisibleWhen(this::getBoolean);
      this.addSettings(new Setting[]{this.opacityDruzey, this.modeProzrachnosti, this.opacity, this.rangeProzrachnosti, this.neBitDruzey});
      INSTANCE = this;
   }

   @Override
   public void onDisable() {
   }

   private Boolean getBoolean() {
      return this.opacityDruzey.isFlag3() && this.modeProzrachnosti.isFlag3();
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (this.neBitDruzey.isFlag3()) {
         if (packetEvent.getPacketDirection() == PacketDirection.SEND) {
            if (packetEvent.getPacket() instanceof PlayerInteractEntityC2SPacket playerinteractentityc2spacket) {
               if (!this.notInGame()) {
                  int i = new UnsafeFields(playerinteractentityc2spacket, PlayerInteractEntityC2SPacket.class, int.class).getInt();
                  if (this.world().getEntityById(i) instanceof PlayerEntity playerentity && this.isFriend(playerentity)) {
                     packetEvent.setFlag(true);
                  }
               }
            }
         }
      }
   }

   public static FriendChecks getInstance() {
      return INSTANCE;
   }

   public float getFloatByPlayerEntity(PlayerEntity playerEntity) {
      float f = this.opacity.getValueAsFloat();
      if (!this.modeProzrachnosti.isFlag3()) {
         return f;
      } else if (playerEntity != null && this.player() != null) {
         double d0 = this.rangeProzrachnosti.getValue();
         if (d0 <= 0.0) {
            return 1.0F;
         } else {
            double d1 = Math.sqrt(this.player().squaredDistanceTo(playerEntity));
            float f1 = (float)Math.min(1.0, d1 / d0);
            return f + (1.0F - f) * f1;
         }
      } else {
         return f;
      }
   }

   public double getDouble() {
      return this.rangeProzrachnosti.getValue();
   }

   public boolean isPlayerEntity(PlayerEntity playerEntity) {
      if (!this.isEnabled() || !this.opacityDruzey.isFlag3() || playerEntity == null || this.player() == null) {
         return false;
      } else if (playerEntity == this.player()) {
         return false;
      } else {
         return !this.isFriend(playerEntity) ? false : this.getFloatByPlayerEntity(playerEntity) <= 0.7F;
      }
   }

   @Override
   public void onEnable() {
   }
}
