package client.util;

import client.setting.ListSetting;
import client.setting.Setting;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public abstract class AimMode {
   private final String text;

   protected AimMode(String text2) {
      this.text = text2;
   }

   public double getDouble() {
      return 15.0;
   }

   public abstract void update();

   public abstract void update2();

   public abstract double[] getDoubleArrayByEntity(Entity entity2);

   public abstract double[] getDoubleArrayByDoubleLongDoubleDoubleDoubleDoubleDoubleLongPlayerEntityDouble(
      double value, long time, double value2, double value3, double value4, double value5, double value6, long time2, PlayerEntity playerEntity, double value7
   );

   private static Boolean getBooleanByStringListSetting(String text, ListSetting listSetting) {
      return text.equals(listSetting.getString2());
   }

   public String getText() {
      return this.text;
   }

   public List<Setting> getList() {
      return Collections.emptyList();
   }

   public void onStringListSetting(String text, ListSetting listSetting) {
      for (Setting setting : this.getList()) {
         setting.setVisibleWhen(() -> AimMode.getBooleanByStringListSetting(text, listSetting));
      }
   }

   public abstract void onDoubleLongEntity(double value, long time, Entity entity2);

   public abstract double getDouble2();
}
