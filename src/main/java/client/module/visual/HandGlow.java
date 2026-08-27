package client.module.visual;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.CompactGroupSetting;
import client.setting.Setting;
import client.setting.SliderSetting;

public class HandGlow extends Module {
   public final ColorSetting color;
   public final BooleanSetting gradient;
   public final ColorSetting vtoroyColor;
   public final SliderSetting speedGradienta;
   public final SliderSetting radius;
   public final SliderSetting yarkost;
   public final BooleanSetting vklyuchitAnimaciyu;
   public final SliderSetting strengthAnimacii;
   public final SliderSetting sizeAnimacii;
   private static HandGlow animation;

   public HandGlow() {
      super("HandGlow", Category.VISUAL);
      ColorSetting colorsetting = new ColorSetting("", "", -13183233, true);
      colorsetting.setName("Цвет");
      colorsetting.setDescription("Основной цвет свечения");
      this.color = colorsetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Градиент");
      booleansetting.setDescription("Плавный переход между двумя цветами");
      this.gradient = booleansetting;
      colorsetting = new ColorSetting("", "", -38090, true);
      colorsetting.setName("Второй цвет");
      colorsetting.setDescription("Второй цвет градиента");
      this.vtoroyColor = colorsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.0, 0.0, 5.0, 0.05);
      slidersetting.setName("Скорость градиента");
      slidersetting.setDescription("Скорость движения градиента (0 — статично)");
      this.speedGradienta = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 16.0, 4.0, 64.0, 1.0);
      slidersetting1.setName("Радиус");
      slidersetting1.setDescription("Радиус свечения в пикселях");
      this.radius = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 1.5, 0.3, 4.0, 0.05);
      slidersetting2.setName("Яркость");
      slidersetting2.setDescription("Яркость ореола");
      this.yarkost = slidersetting2;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Включить анимацию");
      booleansetting1.setDescription("Волнистая огненная обводка");
      this.vklyuchitAnimaciyu = booleansetting1;
      SliderSetting slidersetting3 = new SliderSetting("", "", 1.0, 0.0, 3.0, 0.05);
      slidersetting3.setName("Сила анимации");
      slidersetting3.setDescription("Дрожание и искажение обводки");
      this.strengthAnimacii = slidersetting3;
      SliderSetting slidersetting4 = new SliderSetting("", "", 1.0, 1.0, 2.5, 0.05);
      slidersetting4.setName("Размер анимации");
      slidersetting4.setDescription("Расширение ореола вокруг руки");
      this.sizeAnimacii = slidersetting4;
      this.vtoroyColor.setVisibleWhen(this.gradient::isFlag3);
      this.speedGradienta.setVisibleWhen(this.gradient::isFlag3);
      this.strengthAnimacii.setVisibleWhen(this.vklyuchitAnimaciyu::isFlag3);
      this.sizeAnimacii.setVisibleWhen(this.vklyuchitAnimaciyu::isFlag3);
      CompactGroupSetting compactgroupsetting3 = new CompactGroupSetting("", "", this.gradient, this.color, this.vtoroyColor, this.speedGradienta);
      compactgroupsetting3.setName("Цвета");
      compactgroupsetting3.setDescription("Цвета свечения и градиента.");
      CompactGroupSetting compactgroupsetting = compactgroupsetting3;
      compactgroupsetting3 = new CompactGroupSetting("", "", this.radius, this.yarkost);
      compactgroupsetting3.setName("Свечение");
      compactgroupsetting3.setDescription("Параметры ореола вокруг руки.");
      CompactGroupSetting compactgroupsetting1 = compactgroupsetting3;
      compactgroupsetting3 = new CompactGroupSetting("", "", this.vklyuchitAnimaciyu, this.strengthAnimacii, this.sizeAnimacii);
      compactgroupsetting3.setName("Анимация");
      compactgroupsetting3.setDescription("Огненная волнистая обводка.");
      CompactGroupSetting compactgroupsetting2 = compactgroupsetting3;
      this.addSettings(new Setting[]{compactgroupsetting, compactgroupsetting1, compactgroupsetting2});
      animation = this;
   }

   @Override
   public void onDisable() {
   }

   public static HandGlow getAnimation() {
      return animation;
   }

   @Override
   public void onEnable() {
   }
}
