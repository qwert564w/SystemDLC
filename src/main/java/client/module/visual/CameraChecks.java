package client.module.visual;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.Easings;
import client.util.StringParts;

public class CameraChecks extends Module {
   private SliderSetting rangeKamery;
   private BooleanSetting throughWalls;
   private SliderSetting animationF5;
   private float value235;
   private float value236;
   private long time;
   private long time2;

   public CameraChecks() {
      super("CameraChecks", Category.VISUAL);
      SliderSetting slidersetting = new SliderSetting("", "", 4.0, -1.0, 10.0, 0.5, "", 1);
      slidersetting.setName("Дистанция камеры");
      slidersetting.setDescription("Максимальное отдаление камеры от персонажа");
      this.rangeKamery = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Сквозь стены");
      booleansetting.setDescription("Позволяет камере проходить сквозь стены");
      this.throughWalls = booleansetting;
      slidersetting = new SliderSetting("", "", 250.0, 0.0, 1000.0, 25.0, StringParts.join(new String[]{"м", "c"}), 0);
      slidersetting.setName("Анимация F5");
      slidersetting.setDescription("Длительность плавного перехода камеры в мс (0 — мгновенно)");
      this.animationF5 = slidersetting;
      this.value235 = 0.0F;
      this.value236 = 0.0F;
      this.time = -1L;
      this.time2 = 0L;
      this.addSettings(new Setting[]{this.rangeKamery, this.throughWalls, this.animationF5});
   }

   @Override
   public void onTick() {
      if (this.options() != null) {
         boolean flag = !this.options().getPerspective().isFirstPerson();
         float f = flag ? this.rangeKamery.getValueAsFloat() : 0.0F;
         if (f != this.value236) {
            this.value235 = this.getFloat();
            this.value236 = f;
            this.time = System.nanoTime();
            this.time2 = (long)(this.animationF5.getValue() * 1000000.0);
         }
      }
   }

   @Override
   public void onDisable() {
      this.value235 = this.value236 = 0.0F;
      this.time = -1L;
   }

   public BooleanSetting getThroughWalls() {
      return this.throughWalls;
   }

   public SliderSetting getRangeKamery() {
      return this.rangeKamery;
   }

   public float getFloat() {
      if (this.time >= 0L && this.time2 > 0L) {
         long i = System.nanoTime() - this.time;
         if (i >= this.time2) {
            this.time = -1L;
            this.value235 = this.value236;
            return this.value236;
         } else {
            float f = (float)i / (float)this.time2;
            return this.value235 + (this.value236 - this.value235) * Easings.getFloatByFloat3(f);
         }
      } else {
         return this.value236;
      }
   }

   @Override
   public void onEnable() {
      this.value235 = this.value236 = 0.0F;
      this.time = -1L;
   }
}
