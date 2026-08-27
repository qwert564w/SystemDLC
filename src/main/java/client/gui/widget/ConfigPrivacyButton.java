package client.gui.widget;

import client.api.Theme;
import client.concurrent.Translations;
import client.data.ConfigEntry;
import client.data.TextTrimmer;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.TextShader;
import client.util.EasingPresets;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class ConfigPrivacyButton extends ButtonWidget {
   private static final float value241 = 0.82F;
   private static final String text = "Приватный конфиг";
   private static final String text2 = "Сделает ваш конфиг приватным. Пользователи, использующий данный конфиг не смогут им поделиться.";
   private static final String text3 = "Генерация ключа";
   private static final String text4 = "Открыть меню";
   private static final String text5 = "Скрывать использованные ключи";
   private static final String text6 = "Скрывает ключи, которые уже были использованы.";
   private ConfigEntry configEntry;
   private final SmallButton smallButton;
   private final SmallButton smallButton2;
   private final SmallButton smallButton3;
   private final ConfigKeysPanel configKeysPanel;
   private final Tween tween5 = EasingPresets.getTweenByFloatFloat2(1.0F, 0.22F);
   private final Tween tween6 = EasingPresets.getTweenByFloatFloat2(1.0F, 0.22F);
   private final Tween tween7 = EasingPresets.getTween();
   private Consumer<ConfigEntry> consumer;
   private Consumer<ConfigEntry> consumer2;
   private Consumer<ConfigEntry> consumer3;
   private Consumer<ConfigEntry> consumer4;
   private float value242;
   private float value243;
   private float value244;
   private float value245;

   public ConfigPrivacyButton(ConfigEntry configEntry2) {
      this.configEntry = configEntry2;
      this.value237 = 300.0F;
      boolean flag = configEntry2 != null && configEntry2.isFlag();
      this.smallButton = new SmallButton(flag);
      this.smallButton.setRunnable(() -> {
         if (this.smallButton.isFlag4()) {
            if (this.consumer != null) {
               this.consumer.accept(this.configEntry);
            }
         } else if (this.consumer2 != null) {
            this.consumer2.accept(this.configEntry);
         }
      });
      boolean flag1 = configEntry2 != null && configEntry2.isFlag2();
      this.smallButton2 = new SmallButton(flag1);
      this.smallButton2.setRunnable(() -> {
         if (this.configEntry != null && this.smallButton2.isFlag4() != this.configEntry.isFlag2() && this.consumer4 != null) {
            this.consumer4.accept(this.configEntry);
         }
      });
      this.configKeysPanel = new ConfigKeysPanel(configEntry2 != null ? configEntry2.getText() : "");
      this.smallButton3 = new SmallButton(false);
      this.smallButton3.setRunnable(() -> this.configKeysPanel.setFlag4(this.smallButton3.isFlag4()));
      this.tween5.setFloat(flag ? 1.0F : 0.5F);
      this.tween6.setFloat(flag1 ? 1.0F : 0.5F);
   }

   @Override
   protected void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f2) {
      this.tween5.setFloat2(this.configEntry != null && this.configEntry.isFlag() ? 1.0F : 0.5F);
      float f = value2 * this.tween5.getFloat();
      boolean flag = this.configEntry != null && this.configEntry.isFlag2();
      this.tween6.setFloat2(flag ? 1.0F : 0.5F);
      float f1 = f * this.tween6.getFloat();
      if (this.configEntry != null) {
         this.smallButton2.setBoolean(flag);
      }

      float f2 = this.getFloat6();
      this.value238 = f2;
      float f3 = this.value236 + EasingPresets.getFloatByFloat(value2);
      Matrix4f matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, value2, this.value235 + this.value237 / 2.0F, f3 + f2 / 2.0F);
      float f14 = this.value235;
      float f15 = this.value237;
      int l = Theme.surface();
      int i1 = Theme.border();
      float f12 = 1.0F;
      int j = i1;
      int i = l;
      float f11 = 14.0F;
      float f10 = f15;
      float f9 = f14;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f12, f10, f, matrix4f, f3, i, f11, f9, j, f2);
      float f4 = this.getFloatByFloatFloatMatrix4fFloatFloatFloat(value, f3, matrix4f, value2, value3, f);
      float f5 = f3 + 16.0F + f4 + 16.0F;
      float f6 = this.value235 + 16.0F;
      float f7 = this.value237 - 32.0F;
      int k = Theme.border();
      float f13 = 1.0F;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(f, f13, f6, f5, f7, matrix4f, k);
      float f8 = f5 + 1.0F + 16.0F;
      String s7 = getStringByString("Приватный конфиг");
      String s10 = getStringByString("Сделает ваш конфиг приватным. Пользователи, использующий данный конфиг не смогут им поделиться.");
      SmallButton smallbutton = this.smallButton2;
      String s1 = s10;
      String s = s7;
      f8 = this.getFloatByFloatFloatFloatFloatFloatMatrix4fStringStringSmallButtonFloat(f8, f, value3, value, f7, matrix4f, s1, s, smallbutton, f6);
      f8 += 16.0F;
      String s8 = getStringByString("Генерация ключа");
      s10 = getStringByString(this.getString());
      String s4 = getStringByString("Открыть меню");
      String s3 = s10;
      String s2 = s8;
      f8 = this.getFloatByMatrix4fFloatFloatStringStringFloatStringFloatFloatFloat(matrix4f, value3, value, s3, s4, f6, s2, f7, f8, f1);
      f8 += 16.0F;
      String s9 = getStringByString("Скрывать использованные ключи");
      s10 = getStringByString("Скрывает ключи, которые уже были использованы.");
      SmallButton smallbutton1 = this.smallButton3;
      String s6 = s10;
      String s5 = s9;
      this.getFloatByFloatFloatFloatFloatFloatMatrix4fStringStringSmallButtonFloat(f8, f1, value3, value, f7, matrix4f, s6, s5, smallbutton1, f6);
      this.configKeysPanel.onFloatFloatFloatMatrix4f(value2, value, value3, matrix4f2);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.configKeysPanel.isFlag5()) {
         float f4 = this.configKeysPanel.getValue235();
         float f5 = this.configKeysPanel.getValue236();
         float f3 = this.configKeysPanel.getValue238();
         float f2 = this.configKeysPanel.getValue237();
         float f1 = f5;
         float f = f4;
         if (isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value)) {
            return this.isIntDoubleDouble2(count, value, value2);
         }
      }

      return super.isIntDoubleDouble(count, value, value2);
   }

   public void setConsumer2(Consumer<ConfigEntry> consumer) {
      this.consumer2 = consumer;
   }

   @Override
   protected boolean isIntIntInt3(int count, int count2, int count3) {
      return this.configKeysPanel.isFlag5() ? this.configKeysPanel.isIntIntInt2(count2, count, count3) : false;
   }

   public void setConsumer3(Consumer<ConfigEntry> consumer) {
      this.consumer3 = consumer;
   }

   public void setConsumer4(Consumer<ConfigEntry> consumer) {
      this.consumer4 = consumer;
   }

   public ConfigEntry getConfigEntry() {
      return this.configEntry;
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      return this.configKeysPanel.isFlag5() && this.configKeysPanel.isDoubleDoubleInt(value, value2, count) ? true : super.isDoubleDoubleInt(value, value2, count);
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      return this.configKeysPanel.isFlag5() && this.configKeysPanel.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4)
         ? true
         : super.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4);
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      return this.configKeysPanel.isFlag5() && this.configKeysPanel.isDoubleDoubleDouble(value, value2, value3)
         ? true
         : super.isDoubleDoubleDouble(value, value2, value3);
   }

   public void update14() {
      this.update4();
   }

   public void setConfigEntry(ConfigEntry configEntry2) {
      this.configEntry = configEntry2;
      if (configEntry2 != null) {
         this.smallButton.setBoolean(configEntry2.isFlag());
         this.smallButton2.setBoolean(configEntry2.isFlag2());
      }
   }

   public void setConsumer(Consumer<ConfigEntry> consumer2) {
      this.consumer = consumer2;
   }

   private float getFloatByFloat(float value) {
      String s2 = getStringByString("Генерация ключа");
      String s1 = getStringByString(this.getString());
      String s = s2;
      return EmptyElement.getFloatByFloatStringString(value, s1, s) + 8.0F + 32.0F;
   }

   public float getFloat6() {
      float f = 56.0F;
      f += 17.0F;
      float f1 = this.value237 - 32.0F;
      float f2 = f1 - 16.0F - 8.0F;
      String s4 = getStringByString("Приватный конфиг");
      String s1 = getStringByString("Сделает ваш конфиг приватным. Пользователи, использующий данный конфиг не смогут им поделиться.");
      String s = s4;
      f += Math.max(EmptyElement.getFloatByFloatStringString(f2, s1, s), 16.0F);
      f += 16.0F;
      f += this.getFloatByFloat(f1);
      f += 16.0F;
      s4 = getStringByString("Скрывать использованные ключи");
      String s3 = getStringByString("Скрывает ключи, которые уже были использованы.");
      String s2 = s4;
      f += Math.max(EmptyElement.getFloatByFloatStringString(f2, s3, s2), 16.0F);
      return f + 16.0F;
   }

   private float getFloatByMatrix4fFloatFloatStringStringFloatStringFloatFloatFloat(
      Matrix4f matrix4f, float value, float value2, String text, String text2, float value3, String text3, float value4, float value5, float value6
   ) {
      float f1;
      boolean flag;
      boolean flag3;
      label43: {
         float f = EmptyElement.getFloatByFloatMatrix4fFloatFloatStringFloatString(value4, matrix4f, value3, value5, text, value6, text3);
         f1 = value5 + f + 8.0F;
         this.value242 = value3;
         this.value243 = f1;
         this.value244 = value4;
         this.value245 = 32.0F;
         flag = !this.check2() && this.configEntry != null && this.configEntry.isFlag2();
         if (flag) {
            double d2 = value;
            double d3 = value2;
            float f10 = this.value245;
            float f9 = this.value244;
            float f8 = this.value243;
            float f7 = this.value242;
            double d1 = d3;
            double d0 = d2;
            if (isFloatFloatDoubleFloatFloatDouble(f7, f8, d1, f10, f9, d0)) {
               flag3 = true;
               break label43;
            }
         }

         flag3 = false;
      }

      boolean flag1 = flag3;
      boolean flag2 = flag && this.configKeysPanel.isFlag5();
      this.tween7.setFloat2(flag2 ? 1.0F : (flag1 ? 0.82F : 0.0F));
      float f2 = this.tween7.getFloat();
      float f3 = value6 * (flag ? 1.0F : 0.5F);
      float f30 = this.value242;
      float f31 = this.value243;
      float f32 = this.value244;
      float f33 = this.value245;
      int k1 = Theme.background();
      int l1 = Theme.border();
      float f22 = 1.0F;
      float f21 = 1.0F;
      float f20 = 0.0F;
      int k = 436207616;
      float f19 = 1.0F;
      int j = l1;
      int i = k1;
      float f18 = 8.0F;
      float f17 = 8.0F;
      float f16 = 8.0F;
      float f15 = 8.0F;
      float f14 = f33;
      float f13 = f32;
      float f12 = f31;
      float f11 = f30;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f13, k, i, f11, f12, f22, f15, f14, f21, matrix4f, f3, j, f17, f19, f16, f18, f20
      );
      if (f2 > 0.001F) {
         f30 = this.value242;
         f31 = this.value243;
         f32 = this.value244;
         f33 = this.value245;
         int j1 = Theme.elevated();
         float f28 = f3 * f2;
         int l = j1;
         float f27 = 8.0F;
         float f26 = f33;
         float f25 = f32;
         float f24 = f31;
         float f23 = f30;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f27, f23, l, matrix4f, f26, f25, f28, f24);
      }

      float f4 = TextShader.getFloatByStringFloat(text2, 14.0F);
      float f5 = this.value242 + (this.value244 - f4) / 2.0F;
      float f6 = this.value243 + (this.value245 - 14.0F) / 2.0F;
      int i1 = Theme.foreground();
      float f29 = 14.0F;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f6, f5, i1, f29, f3, text2, matrix4f);
      return f1 + this.value245;
   }

   private float getFloatByFloatFloatFloatFloatFloatMatrix4fStringStringSmallButtonFloat(
      float value, float value2, float value3, float value4, float value5, Matrix4f matrix4f, String text, String text2, SmallButton smallButton, float value6
   ) {
      float f = value5 - 16.0F - 8.0F;
      float f1 = EmptyElement.getFloatByFloatMatrix4fFloatFloatStringFloatString(f, matrix4f, value6, value, text, value2, text2);
      float f2 = Math.max(f1, 16.0F);
      float f3 = value + -1.0F;
      float f4 = value6 + value5 - 16.0F;
      smallButton.onFloatFloat2(f3, f4);
      smallButton.onFloatFloatFloatMatrix4f(value2, value4, value3, matrix4f);
      return value + f2;
   }

   private float getFloatByFloatFloatMatrix4fFloatFloatFloat(float value, float value2, Matrix4f matrix4f, float value3, float value4, float value5) {
      float f = this.value235 + 16.0F;
      float f1 = value2 + 16.0F + 2.0F;
      float f9 = 13.0F;
      float f8 = 14.0F;
      float f7 = 20.0F;
      CategoryType categorytype = CategoryType.CLOUDS;
      Spacer.onCategoryTypeMatrix4fFloatFloatFloatFloatFloatFloat(categorytype, matrix4f, f, f9, f7, f1, value5, f8);
      float f2 = this.value235 + this.value237 - 16.0F - this.smallButton.getValue237();
      float f3 = f1 + 2.0F;
      float f4 = f + 20.0F + 8.0F;
      float f5 = f1 + 2.0F;
      float f6 = f2 - 8.0F - f4;
      String s = this.configEntry != null && this.configEntry.getText() != null ? this.configEntry.getText() : "";
      float f10 = 16.0F;
      String s2 = TextTrimmer.getStringByFloatStringFloat2(f6, s, f10);
      int i = Theme.foreground();
      float f11 = 16.0F;
      String s1 = s2;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f5, f4, i, f11, value5, s1, matrix4f);
      if (this.configEntry != null) {
         this.smallButton.setBoolean(this.configEntry.isFlag());
      }

      this.smallButton.onFloatFloat2(f3, f2);
      this.smallButton.onFloatFloatFloatMatrix4f(value3, value, value4, matrix4f);
      return 24.0F;
   }

   @Override
   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      if (this.configKeysPanel.isFlag5()) {
         if (!this.configKeysPanel.isIntDoubleDouble(count, value, value2)) {
            this.configKeysPanel.setFlag5();
         }

         return true;
      } else if (this.smallButton.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.smallButton2.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.smallButton3.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else {
         if (count == 0 && !this.check2() && this.configEntry != null && this.configEntry.isFlag2()) {
            float f3 = this.value245;
            float f2 = this.value244;
            float f1 = this.value243;
            float f = this.value242;
            if (isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value)) {
               this.configKeysPanel.setColorSupplier(() -> new float[]{this.value235 + this.value237 + 8.0F, this.value236});
               return true;
            }
         }

         return true;
      }
   }

   public boolean check2() {
      return this.configEntry != null && this.configEntry.isFlag3();
   }

   private static String getStringByString(String text) {
      return Translations.getInstance().getStringByString2(text);
   }

   private String getString() {
      return this.check2() ? "Импортированный конфиг" : "Используйте генерацию для создания ключей на активацию вашего приватного конфига.";
   }

   @Override
   protected void update7() {
      this.configKeysPanel.setFlag5();
      if (this.consumer3 != null) {
         this.consumer3.accept(this.configEntry);
      }
   }

   @Override
   public void update2() {
      this.configKeysPanel.setFlag5();
      super.update2();
   }
}
