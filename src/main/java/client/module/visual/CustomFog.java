package client.module.visual;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.Setting;
import client.setting.SliderSetting;

public class CustomFog extends Module {
   private BooleanSetting svoyaRange;
   private SliderSetting konecTumana;
   private ColorSetting colorTumana;
   private BooleanSetting neTrogatPodVodoy;
   private static CustomFog INSTANCE;

   public CustomFog() {
      super("CustomFog", Category.VISUAL);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Своя дистанция");
      booleansetting.setDescription("Задавать дистанцию тумана вручную");
      this.svoyaRange = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 128.0, 1.0, 1024.0, 1.0);
      slidersetting.setName("Конец тумана");
      slidersetting.setDescription("Дистанция где туман полностью непрозрачный");
      this.konecTumana = slidersetting;
      ColorSetting colorsetting = new ColorSetting("", "", -7358209, true);
      colorsetting.setName("Цвет тумана");
      colorsetting.setDescription("Цвет тумана. Альфа = насыщенность");
      this.colorTumana = colorsetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Не трогать под водой");
      booleansetting.setDescription("Оставлять ванильный туман под водой/лавой");
      this.neTrogatPodVodoy = booleansetting;
      this.konecTumana.setVisibleWhen(this.svoyaRange::isFlag3);
      this.addSettings(new Setting[]{this.svoyaRange, this.konecTumana, this.colorTumana, this.neTrogatPodVodoy});
      INSTANCE = this;
   }

   public float getFloat() {
      return this.colorTumana.getValue3() / 255.0F;
   }

   public float getFloat2() {
      return this.konecTumana.getValueAsFloat();
   }

   public boolean check3() {
      return this.colorTumana.getValue4() > 0;
   }

   public boolean check4() {
      return this.neTrogatPodVodoy.isFlag3();
   }

   public float getFloat3() {
      return this.colorTumana.getValue() / 255.0F;
   }

   @Override
   public void onDisable() {
   }

   public float getFloat4() {
      return this.colorTumana.getValue4() / 255.0F;
   }

   public float getFloat5() {
      return this.colorTumana.getValue2() / 255.0F;
   }

   public float getFloat6() {
      return 0.0F;
   }

   public static CustomFog getInstance() {
      return INSTANCE;
   }

   public boolean check5() {
      return this.svoyaRange.isFlag3();
   }

   @Override
   public void onEnable() {
   }
}
