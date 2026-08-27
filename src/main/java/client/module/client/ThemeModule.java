package client.module.client;

import client.data.ChoiceOption;
import client.data.Palette;
import client.data.ThemeConfig;
import client.enums.ThemePalette;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.ChoiceSetting;
import client.setting.ColorSetting;
import client.setting.CompactGroupSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.MathUtil;
import client.util.UnsafeAccess;
import java.util.List;
import java.util.function.Supplier;

public class ThemeModule extends Module {
   private static final List<String> list = List.of("Матовость", "Постер", "Пиксели", "Волны", "Свечение");
   private static final List<String> list2 = List.of("Фон", "Подложки", "Текст", "Обводки");
   private static boolean flag;
   private BooleanSetting kromeGui;
   private ChoiceSetting modeOtobrazheniya;
   private SliderSetting intensivnost;
   private SliderSetting iskazhenie;
   private SliderSetting hromatika;
   private SliderSetting blyur;
   private ColorSetting colorTinta;
   private SliderSetting strengthTinta;
   private ListSetting effects;
   private SliderSetting strengthMatovosti;
   private SliderSetting strengthPosterizacii;
   private SliderSetting sizePikseley;
   private SliderSetting strengthVoln;
   private SliderSetting speedVoln;
   private SliderSetting chastotaVoln;
   private SliderSetting strengthSvecheniya;
   private SliderSetting sizeSvecheniya;
   private ColorSetting colorSvecheniya;
   private SliderSetting sizeGui;
   private BooleanSetting guiBlyur;
   private BooleanSetting svoyAkcent;
   private ColorSetting akcent;
   private ListSetting gdePrimenyatAkcent;
   private static final UnsafeAccess<ThemeModule> unsafeAccess = new UnsafeAccess<>(ThemeModule.class);

   public ThemeModule() {
      super("ThemeModule", Category.CLIENT);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Кроме GUI");
      booleansetting.setDescription("Не применять кастомный стиль рендера к ГУИ клиента (только к ХУД и его настройкам)");
      this.kromeGui = booleansetting;
      ChoiceSetting choicesetting = new ChoiceSetting("", "", new ChoiceOption("Обычный"), new ChoiceOption("Прозрачный"), true);
      choicesetting.setName("Режим отображения");
      choicesetting.setDescription("Установите желаемый стиль рендера элементов GUI и HUD.");
      this.modeOtobrazheniya = choicesetting;
      SliderSetting slidersetting = new SliderSetting("", "", 1.0, 0.0, 1.0, 0.05);
      slidersetting.setName("Интенсивность");
      slidersetting.setDescription("Насколько ярко проявляется эффект стекла на элементах интерфейса.");
      this.intensivnost = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 1.0, 0.0, 3.0, 0.05);
      slidersetting1.setName("Искажение");
      slidersetting1.setDescription("Сила, c которой картинка за стеклом изгибается и плывёт.");
      this.iskazhenie = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 1.0, 0.0, 3.0, 0.05);
      slidersetting2.setName("Хроматика");
      slidersetting2.setDescription("Лёгкая радужная окантовка на краях, как y настоящей оптики.");
      this.hromatika = slidersetting2;
      SliderSetting slidersetting3 = new SliderSetting("", "", 0.4, 0.0, 0.8, 0.1);
      slidersetting3.setName("Блюр");
      slidersetting3.setDescription("Насколько фон за элементами интерфейса становится мутным.");
      this.blyur = slidersetting3;
      ColorSetting colorsetting = new ColorSetting("", "", -3151361);
      colorsetting.setName("Цвет тинта");
      colorsetting.setDescription("Цвет, в который слегка подкрашивается стекло.");
      this.colorTinta = colorsetting;
      SliderSetting slidersetting4 = new SliderSetting("", "", 0.15, 0.0, 1.0, 0.05);
      slidersetting4.setName("Сила тинта");
      slidersetting4.setDescription("Насколько сильно выбранный цвет окрашивает стекло.");
      this.strengthTinta = slidersetting4;
      ListSetting listsetting = new ListSetting("", "", list, List.of(), true);
      listsetting.setName("Эффекты");
      listsetting.setDescription("Дополнительные визуальные эффекты для стекла. Включи нужные.");
      this.effects = listsetting;
      SliderSetting slidersetting5 = new SliderSetting("", "", 0.5, 0.05, 1.0, 0.05);
      slidersetting5.setName("Сила матовости");
      slidersetting5.setDescription("Делает стекло более мутным, как в ванной комнате.");
      this.strengthMatovosti = slidersetting5;
      SliderSetting slidersetting6 = new SliderSetting("", "", 0.5, 0.05, 1.0, 0.05);
      slidersetting6.setName("Сила постеризации");
      slidersetting6.setDescription("Уменьшает количество оттенков, картинка становится плакатной.");
      this.strengthPosterizacii = slidersetting6;
      SliderSetting slidersetting7 = new SliderSetting("", "", 0.3, 0.05, 1.0, 0.05);
      slidersetting7.setName("Размер пикселей");
      slidersetting7.setDescription("Чем выше — тем крупнее видны квадратики.");
      this.sizePikseley = slidersetting7;
      SliderSetting slidersetting8 = new SliderSetting("", "", 0.5, 0.05, 1.0, 0.05);
      slidersetting8.setName("Сила волн");
      slidersetting8.setDescription("Насколько сильно картинка колышется, будто под водой.");
      this.strengthVoln = slidersetting8;
      SliderSetting slidersetting9 = new SliderSetting("", "", 1.0, 0.0, 3.0, 0.1);
      slidersetting9.setName("Скорость волн");
      slidersetting9.setDescription("Как быстро бежит волнообразное искажение.");
      this.speedVoln = slidersetting9;
      SliderSetting slidersetting10 = new SliderSetting("", "", 0.5, 0.05, 1.0, 0.05);
      slidersetting10.setName("Частота волн");
      slidersetting10.setDescription("Сколько волн умещается на стекле одновременно.");
      this.chastotaVoln = slidersetting10;
      SliderSetting slidersetting11 = new SliderSetting("", "", 0.3, 0.05, 1.0, 0.05);
      slidersetting11.setName("Сила свечения");
      slidersetting11.setDescription("Насколько ярко стекло светится изнутри по краям.");
      this.strengthSvecheniya = slidersetting11;
      SliderSetting slidersetting12 = new SliderSetting("", "", 0.5, 0.05, 1.0, 0.05);
      slidersetting12.setName("Размер свечения");
      slidersetting12.setDescription("Насколько далеко свечение расходится от края внутрь.");
      this.sizeSvecheniya = slidersetting12;
      ColorSetting colorsetting1 = new ColorSetting("", "", -8401921);
      colorsetting1.setName("Цвет свечения");
      colorsetting1.setDescription("Цвет внутреннего свечения y краёв стекла.");
      this.colorSvecheniya = colorsetting1;
      SliderSetting slidersetting13 = new SliderSetting("", "", 1.0, 0.5, 1.5, 0.05, "x", 2);
      slidersetting13.setName("Размер GUI");
      slidersetting13.setDescription("Общий масштаб окна клиента.");
      this.sizeGui = slidersetting13;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Гуи блюр");
      booleansetting.setDescription("Размывать фон за открытым интерфейсом клиента.");
      this.guiBlyur = booleansetting;
      booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Свой акцент");
      booleansetting.setDescription("Использовать пользовательский акцентный оттенок. Остальные цвета подстраиваются автоматически.");
      this.svoyAkcent = booleansetting;
      ColorSetting colorsetting2 = new ColorSetting("", "", -2343358);
      colorsetting2.setName("Акцент");
      colorsetting2.setDescription("Один цвет — все оттенки GUI выводятся из него автоматически.");
      this.akcent = colorsetting2;
      ListSetting listsetting1 = new ListSetting("", "", list2, list2, true);
      listsetting1.setName("Где применять акцент");
      listsetting1.setDescription("На что распространяется свой оттенок. Снятая галочка оставляет группе цвета базовой темы.");
      this.gdePrimenyatAkcent = listsetting1;
      Supplier<Boolean> supplier = this.modeOtobrazheniya::isFlag3;
      this.intensivnost.setVisibleWhen(supplier);
      this.iskazhenie.setVisibleWhen(supplier);
      this.hromatika.setVisibleWhen(supplier);
      this.blyur.setVisibleWhen(supplier);
      this.colorTinta.setVisibleWhen(supplier);
      this.strengthTinta.setVisibleWhen(supplier);
      this.effects.setVisibleWhen(supplier);
      Supplier<Boolean> supplier1 = () -> this.getBooleanBySupplier7(supplier);
      Supplier<Boolean> supplier2 = () -> this.getBooleanBySupplier4(supplier);
      Supplier<Boolean> supplier3 = () -> this.getBooleanBySupplier(supplier);
      Supplier<Boolean> supplier4 = () -> this.getBooleanBySupplier5(supplier);
      Supplier<Boolean> supplier5 = () -> this.getBooleanBySupplier3(supplier);
      this.strengthMatovosti.setVisibleWhen(supplier1);
      this.strengthPosterizacii.setVisibleWhen(supplier2);
      this.sizePikseley.setVisibleWhen(supplier3);
      this.strengthVoln.setVisibleWhen(supplier4);
      this.speedVoln.setVisibleWhen(supplier4);
      this.chastotaVoln.setVisibleWhen(supplier4);
      this.strengthSvecheniya.setVisibleWhen(supplier5);
      this.sizeSvecheniya.setVisibleWhen(supplier5);
      this.colorSvecheniya.setVisibleWhen(supplier5);
      CompactGroupSetting compactgroupsetting1 = new CompactGroupSetting(
         "",
         "",
         this.intensivnost,
         this.iskazhenie,
         this.hromatika,
         this.blyur,
         this.colorTinta,
         this.strengthTinta,
         this.strengthMatovosti,
         this.strengthPosterizacii,
         this.sizePikseley,
         this.strengthVoln,
         this.speedVoln,
         this.chastotaVoln,
         this.strengthSvecheniya,
         this.sizeSvecheniya,
         this.colorSvecheniya
      );
      compactgroupsetting1.setName("Стекло");
      CompactGroupSetting compactgroupsetting = compactgroupsetting1;
      compactgroupsetting.setVisibleWhen(supplier);
      Supplier<Boolean> supplier6 = () -> ThemeModule.getBooleanBySupplier2(supplier);
      this.svoyAkcent.setVisibleWhen(supplier6);
      Supplier<Boolean> supplier7 = () -> this.getBooleanBySupplier6(supplier);
      this.akcent.setVisibleWhen(supplier7);
      this.gdePrimenyatAkcent.setVisibleWhen(supplier7);
      this.addSettings(
         new Setting[]{
            this.kromeGui,
            this.modeOtobrazheniya,
            this.sizeGui,
            this.guiBlyur,
            this.effects,
            compactgroupsetting,
            this.svoyAkcent,
            this.akcent,
            this.gdePrimenyatAkcent
         }
      );
   }

   private Boolean getBooleanBySupplier(Supplier<Boolean> supplier) {
      return (Boolean)supplier.get() && this.effects.isString("Пиксели");
   }

   public float getFloat() {
      return this.blyur.getValueAsFloat();
   }

   public float getFloat2() {
      return this.effects.isString("Пиксели") ? this.sizePikseley.getValueAsFloat() : 0.0F;
   }

   public boolean check3() {
      return this.isEnabled() && !this.modeOtobrazheniya.isFlag3() && this.svoyAkcent.isFlag3();
   }

   private static Boolean getBooleanBySupplier2(Supplier<Boolean> supplier) {
      return !(Boolean)supplier.get();
   }

   public int getInt() {
      return this.colorSvecheniya.getInt3();
   }

   public float getFloat3() {
      return this.intensivnost.getValueAsFloat();
   }

   public static void setFlag() {
      flag = false;
   }

   public boolean check4() {
      return this.isEnabled() && this.modeOtobrazheniya.isFlag3() ? !this.kromeGui.isFlag3() || !flag : false;
   }

   public static void update11() {
   }

   public float getFloat4() {
      return this.iskazhenie.getValueAsFloat();
   }

   public void onBoolean(boolean flag) {
      this.guiBlyur.setBoolean(flag);
   }

   private Boolean getBooleanBySupplier3(Supplier<Boolean> supplier) {
      return (Boolean)supplier.get() && this.effects.isString("Свечение");
   }

   public float getFloat5() {
      return this.sizeSvecheniya.getValueAsFloat();
   }

   public float getFloat6() {
      return this.effects.isString("Постер") ? this.strengthPosterizacii.getValueAsFloat() : 0.0F;
   }

   @Override
   public void onDisable() {
   }

   public float getFloat7() {
      return this.strengthTinta.getValueAsFloat();
   }

   private Boolean getBooleanBySupplier4(Supplier<Boolean> supplier) {
      return (Boolean)supplier.get() && this.effects.isString("Постер");
   }

   public float getFloat8() {
      return this.chastotaVoln.getValueAsFloat();
   }

   private Boolean getBooleanBySupplier5(Supplier<Boolean> supplier) {
      return (Boolean)supplier.get() && this.effects.isString("Волны");
   }

   public float getFloat9() {
      return this.hromatika.getValueAsFloat();
   }

   public static void update12() {
   }

   public Palette getPaletteByPalette(Palette palette) {
      if (!this.check3()) {
         return palette;
      } else {
         float[] afloat = MathUtil.getFloatArrayByInt(this.akcent.getInt3());
         float f = afloat[0];
         float f1 = afloat[1];
         boolean flagx = ThemeConfig.getThemePalette() == ThemePalette.INSTANCE2;
         boolean flag1 = this.gdePrimenyatAkcent.isString("Текст");
         boolean flag2 = this.gdePrimenyatAkcent.isString("Подложки");
         boolean flag3 = this.gdePrimenyatAkcent.isString("Обводки");
         boolean flag4 = this.gdePrimenyatAkcent.isString("Фон");
         int l1;
         if (flag1) {
            float f26;
            if (flagx) {
               f26 = 0.9F;
            } else {
               f26 = afloat[2];
               float f4 = 0.35F;
               float f3 = 0.15F;
               float f2 = f26;
               f26 = getFloatByFloatFloatFloat(f4, f2, f3);
            }

            float f5 = f26;
            l1 = MathUtil.getIntByFloatFloatFloat(f5, f, f1);
         } else {
            l1 = palette.getValue7();
         }

         int i = l1;
         if (flag1) {
            float f24;
            if (flagx) {
               f24 = 0.8F;
            } else {
               f24 = afloat[2];
               float f8 = 0.4F;
               float f7 = 0.2F;
               float f6 = f24;
               f24 = getFloatByFloatFloatFloat(f8, f6, f7);
            }

            float f9 = f24;
            l1 = MathUtil.getIntByFloatFloatFloat(f9, f, f1);
         } else {
            l1 = palette.getValue6();
         }

         int j = l1;
         if (flag1) {
            float f25 = f1 * 0.55F;
            float f11 = flagx ? 0.62F : 0.5F;
            float f10 = f25;
            l1 = MathUtil.getIntByFloatFloatFloat(f11, f, f10);
         } else {
            l1 = palette.getValue5();
         }

         int k = l1;
         if (flag3) {
            float f20 = f1 * 0.4F;
            float f13 = flagx ? 0.28F : 0.88F;
            float f12 = f20;
            l1 = MathUtil.getIntByFloatFloatFloat(f13, f, f12);
         } else {
            l1 = palette.getValue4();
         }

         int l = l1;
         if (flag2) {
            float f21 = f1 * 0.35F;
            float f15 = flagx ? 0.22F : 0.94F;
            float f14 = f21;
            l1 = MathUtil.getIntByFloatFloatFloat(f15, f, f14);
         } else {
            l1 = palette.getValue3();
         }

         int i1 = l1;
         if (flag2) {
            float f22 = f1 * 0.3F;
            float f17 = flagx ? 0.16F : 0.97F;
            float f16 = f22;
            l1 = MathUtil.getIntByFloatFloatFloat(f17, f, f16);
         } else {
            l1 = palette.getValue2();
         }

         int j1 = l1;
         if (flag4) {
            float f23 = f1 * 0.25F;
            float f19 = flagx ? 0.1F : 0.99F;
            float f18 = f23;
            l1 = MathUtil.getIntByFloatFloatFloat(f19, f, f18);
         } else {
            l1 = palette.getValue();
         }

         int k1 = l1;
         return new Palette(k1, j1, i1, l, k, j, i, palette.getValue8(), palette.getValue9(), palette.getValue10(), palette.getValue11());
      }
   }

   public static void setFlag2() {
      flag = true;
   }

   private Boolean getBooleanBySupplier6(Supplier<Boolean> supplier) {
      return (Boolean)supplier.get() && this.svoyAkcent.isFlag3();
   }

   public boolean check5() {
      return this.guiBlyur.isFlag3();
   }

   public static float getFloat10() {
      ThemeModule thememodule = getThemeModule();
      return thememodule != null ? thememodule.sizeGui.getValueAsFloat() : 1.0F;
   }

   public static ThemeModule getThemeModule() {
      return (ThemeModule)unsafeAccess.getModule();
   }

   public int getInt2() {
      return this.colorTinta.getInt3();
   }

   private static float getFloatByFloatFloatFloat(float value, float value2, float value3) {
      return Math.max(value3, Math.min(value, value2));
   }

   public float getFloat11() {
      return this.effects.isString("Свечение") ? this.strengthSvecheniya.getValueAsFloat() : 0.0F;
   }

   public float getFloat12() {
      return this.speedVoln.getValueAsFloat();
   }

   private Boolean getBooleanBySupplier7(Supplier<Boolean> supplier) {
      return (Boolean)supplier.get() && this.effects.isString("Матовость");
   }

   public float getFloat13() {
      return this.effects.isString("Матовость") ? this.strengthMatovosti.getValueAsFloat() : 0.0F;
   }

   @Override
   public void onEnable() {
   }

   public float getFloat14() {
      return this.effects.isString("Волны") ? this.strengthVoln.getValueAsFloat() : 0.0F;
   }
}
