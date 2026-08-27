package client.module.visual;

import client.data.Rotation;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.Setting;
import client.setting.SliderSetting;

public class Zoom extends Module {
   private static float value235 = 0.0F;
   private static float value236 = 120.0F;
   private HotkeySetting keyZuma;
   private SliderSetting zumFov;
   private SliderSetting speed;
   private BooleanSetting skrollUpravlenie;
   private SliderSetting shagSkrolla;
   private long time;
   private float value237;
   private float value238;
   private float value239;
   private static Zoom INSTANCE;

   public Zoom() {
      super("Zoom", Category.VISUAL);
      HotkeySetting hotkeysetting = new HotkeySetting("", "", 67);
      hotkeysetting.setName("Клавиша зума");
      hotkeysetting.setDescription("Зажмите для приближения");
      this.keyZuma = hotkeysetting;
      SliderSetting slidersetting = new SliderSetting("", "", 30.0, value235, value236, 1.0);
      slidersetting.setName("Зум FOV");
      slidersetting.setDescription("Уровень приближения (меньше = ближе)");
      this.zumFov = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 4.0, 1.0, 15.0, 0.5);
      slidersetting1.setName("Скорость");
      slidersetting1.setDescription("Скорость анимации");
      this.speed = slidersetting1;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Скролл управление");
      booleansetting.setDescription("Колёсико мыши меняет уровень зума");
      this.skrollUpravlenie = booleansetting;
      SliderSetting slidersetting2 = new SliderSetting("", "", 5.0, 1.0, 20.0, 1.0);
      slidersetting2.setName("Шаг скролла");
      slidersetting2.setDescription("Насколько меняется FOV за скролл");
      this.shagSkrolla = slidersetting2;
      this.time = 0L;
      this.value237 = 0.0F;
      this.addSettings(new Setting[]{this.keyZuma, this.zumFov, this.speed, this.skrollUpravlenie, this.shagSkrolla});
      INSTANCE = this;
   }

   public float getValue237() {
      return this.value237;
   }

   public SliderSetting getSpeed() {
      return this.speed;
   }

   @Override
   protected void onSettingChanged(Setting setting2) {
      if (setting2 == this.zumFov && !this.skrollUpravlenie.isFlag3()) {
         float f = Math.max(value235, this.zumFov.getValueAsFloat());
         this.value238 = f;
         this.value239 = f;
      }
   }

   public SliderSetting getShagSkrolla() {
      return this.shagSkrolla;
   }

   public float getValue238() {
      return this.value238;
   }

   @Override
   public void onDisable() {
      this.value237 = 0.0F;
   }

   public float getValue239() {
      return this.value239;
   }

   @Override
   public void onRotation(Rotation rotation) {
      if (this.skrollUpravlenie.isFlag3() && this.check3()) {
         float f = this.shagSkrolla.getValueAsFloat();
         float f1 = this.value238 - (float)rotation.getValue2() * f;
         this.value238 = Math.clamp(f1, value235, value236);
         rotation.setFlag(true);
      }
   }

   public long getTime() {
      return this.time;
   }

   private boolean check3() {
      return this.client() != null && this.client().currentScreen == null && this.keyZuma.check();
   }

   public static Zoom getInstance() {
      return INSTANCE;
   }

   public SliderSetting getZumFov() {
      return this.zumFov;
   }

   public HotkeySetting getKeyZuma() {
      return this.keyZuma;
   }

   private float getFloatByFloat(float value) {
      return value < 0.5F ? 4.0F * value * value * value : 1.0F - (float)Math.pow(-2.0F * value + 2.0F, 3.0) / 2.0F;
   }

   private void update11() {
      long i = System.nanoTime();
      if (this.time != 0L && i - this.time <= NANOS_PER_SECOND) {
         float f = (float)(i - this.time) / (float)NANOS_PER_SECOND;
         this.time = i;
         float f1 = this.speed.getValueAsFloat();
         boolean flag = this.check3();
         if (flag) {
            this.value237 = Math.min(1.0F, this.value237 + f * f1);
         } else {
            this.value237 = Math.max(0.0F, this.value237 - f * f1);
         }

         float f2 = this.skrollUpravlenie.isFlag3() ? this.value238 : this.zumFov.getValueAsFloat();
         float f3 = f2 - this.value239;
         if (Math.abs(f3) > 0.01F) {
            this.value239 += f3 * f * f1 * 2.0F;
         } else {
            this.value239 = f2;
         }

         this.value239 = Math.clamp(this.value239, value235, value236);
      } else {
         this.time = i;
      }
   }

   public BooleanSetting getSkrollUpravlenie() {
      return this.skrollUpravlenie;
   }

   public float getFloat() {
      if (!this.isEnabled()) {
         return 1.0F;
      } else {
         this.update11();
         if (this.value237 <= 0.0F) {
            return 1.0F;
         } else {
            float f = Math.max(value235, this.value239);
            float f1 = f / 70.0F;
            if (this.value237 >= 1.0F) {
               return f1;
            } else {
               float f2 = this.getFloatByFloat(this.value237);
               return 1.0F + (f1 - 1.0F) * f2;
            }
         }
      }
   }

   @Override
   public void onEnable() {
      this.time = System.nanoTime();
      this.value237 = 0.0F;
      float f = Math.max(value235, this.zumFov.getValueAsFloat());
      this.value238 = f;
      this.value239 = f;
   }
}
