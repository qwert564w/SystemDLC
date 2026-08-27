package client.gui.widget;

import client.api.Theme;
import client.concurrent.Translations;
import client.data.ThemeConfig;
import client.data.Tween;
import client.enums.Language;
import client.enums.ThemePalette;
import client.module.Category;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.TextShader;
import client.util.EasingPresets;
import client.util.Easings;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class TopBar extends Widget {
   private static final float value239 = 0.1F;
   private static final float value240 = 0.18F;
   private static final float value241 = 6.0F;
   private boolean flag4 = false;
   private boolean flag5;
   private String text = "All";
   private String text2;
   private final Tween tween4 = new Tween(1.0F, 0.18F).getTweenByFunction(Easings::getFloatByFloat6);
   private boolean flag6;
   private int value242 = 1;
   private int value243 = 1;
   private Category category;
   private Consumer<Boolean> consumer;
   private Consumer<Boolean> consumer2;
   private Runnable runnable;
   private final IconButton iconButton;
   private final IconButton iconButton2;
   private final IconButton iconButton3;
   private final IconButton iconButton4;
   private final IconButton iconButton5;

   public TopBar() {
      this.value237 = 963.0F;
      this.value238 = 49.0F;
      this.iconButton = new IconButton(CategoryType.PANEL, 14.0F);
      this.iconButton.setText("Боковая панель");
      this.iconButton.setRunnable(() -> {
         this.flag5 = !this.flag5;
         if (this.consumer2 != null) {
            this.consumer2.accept(this.flag5);
         }
      });
      this.iconButton2 = new IconButton(CategoryType.COLLAPSE_ALL, 14.0F);
      this.iconButton2.setText("Свернуть всё");
      this.iconButton2.setRunnable(() -> {
         this.flag4 = !this.flag4;
         if (this.consumer != null) {
            this.consumer.accept(this.flag4);
         }
      });
      this.iconButton3 = new IconButton(CategoryType.MOON, 14.0F);
      this.iconButton3.setText("Сменить тему");
      this.iconButton3.setRunnable(ThemeConfig::update);
      this.iconButton4 = new IconButton(CategoryType.LANGUAGE, 14.0F);
      this.update7();
      Translations.getInstance().addRunnable(this::update7);
      this.iconButton4.setRunnable(() -> {
         Translations translations = Translations.getInstance();
         if (translations.getLanguage() == Language.RU) {
            translations.update3();
         }

         translations.update();
      });
      this.iconButton5 = new IconButton(CategoryType.STAR, 14.0F);
      this.iconButton5.setCategoryType2(CategoryType.STAR_FILLED);
      this.iconButton5.setText("Избранное");
      this.iconButton5.setRunnable(() -> {
         if (this.runnable != null) {
            this.runnable.run();
         }
      });
   }

   public void setString(String text3) {
      if (text3 != null) {
         String s = this.flag6 ? this.text2 : this.text;
         if (!text3.equals(s)) {
            this.text2 = text3;
            this.flag6 = true;
            this.value242 = this.value243;
            this.tween4.setValue5(0.1F);
            this.tween4.getTweenByFunction(Easings::getFloatByFloat4);
            this.tween4.setFloat2(0.0F);
         }
      }
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.iconButton.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.iconButton2.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.iconButton3.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else {
         return this.iconButton4.isIntDoubleDouble(count, value, value2) ? true : this.iconButton5.isIntDoubleDouble(count, value, value2);
      }
   }

   public void setConsumer2(Consumer<Boolean> consumer) {
      this.consumer2 = consumer;
   }

   public void setFlag5(boolean flag) {
      this.flag5 = flag;
   }

   public void setCategory(Category category2) {
      this.flag4 = false;
      this.category = category2;
      this.value243 = 1;
      this.setString2(category2 != null ? category2.getString() : "All");
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f28 = this.value235;
      float f29 = this.value236;
      float f30 = this.value237;
      int l = Theme.background();
      float f21 = 0.0F;
      float f20 = 0.0F;
      float f19 = 0.0F;
      byte b1 = 0;
      float f18 = 0.0F;
      byte b0 = 0;
      int i = l;
      float f17 = 0.0F;
      float f16 = 0.0F;
      float f15 = 12.0F;
      float f14 = 0.0F;
      float f13 = 48.0F;
      float f12 = f30;
      float f11 = f29;
      float f10 = f28;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f12, b1, i, f10, f11, f21, f14, f13, f20, matrix4f, value, b0, f16, f18, f15, f17, f19
      );
      float f = this.value235 + 18.0F - 1.0F;
      float f1 = this.value236 + 17.0F;
      this.iconButton.onFloatFloat2(f1, f);
      this.iconButton.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
      float f2 = f + 14.0F + 14.0F;
      float f3 = this.value236 + 15.5F;
      float f4 = f2 + 7.5F;
      int j = Theme.border();
      float f23 = 17.0F;
      float f22 = 1.0F;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f23, f4, f3, f22, matrix4f, j);
      float f5 = f2 + 16.0F + 8.0F;
      float f6 = this.value236 + 17.0F;
      float f7 = this.tween4.getFloat();
      if (this.flag6 && f7 <= 0.001F) {
         if (this.text2 != null) {
            this.text = this.text2;
         }

         this.text2 = null;
         this.flag6 = false;
         this.tween4.setValue5(0.18F);
         this.tween4.getTweenByFunction(Easings::getFloatByFloat6);
         this.tween4.setFloat2(1.0F);
         f7 = this.tween4.getFloat();
      }

      float f8 = (1.0F - f7) * 6.0F * (this.flag6 ? -this.value242 : this.value242);
      float f9 = EasingPresets.getFloatByFloat3(f7);
      TextShader.onMatrix4fStringFloatFloatFloatIntFloat(matrix4f, this.text, f5, f6 + f8, 14.0F, Theme.foreground(), value * f9);
      f28 = this.value235;
      f29 = this.value236 + 48.0F;
      f30 = this.value237;
      int k = Theme.border();
      float f27 = 1.0F;
      float f26 = f30;
      float f25 = f29;
      float f24 = f28;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f27, f24, f25, f26, matrix4f, k);
      this.onFloatMatrix4fFloatFloat(value2, matrix4f, value, value3);
   }

   private void update7() {
      Language language = Translations.getInstance().getLanguage();
      this.iconButton4.setText(language == Language.RU ? "Switch to EN" : "На русском");
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   private void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      this.iconButton2.setCategoryType(this.flag4 ? CategoryType.EXPAND_ALL : CategoryType.COLLAPSE_ALL);
      this.iconButton2.setText(this.flag4 ? "Развернуть всё" : "Свернуть всё");
      this.iconButton3.setCategoryType(ThemeConfig.getThemePalette() == ThemePalette.INSTANCE2 ? CategoryType.SUN : CategoryType.MOON);
      float f = this.value236 + 17.0F;
      float f1 = this.value236 + 15.5F;
      float f2 = this.value235 + this.value237 - 25.0F;
      IconButton[] aiconbutton = new IconButton[]{this.iconButton2, this.iconButton3, this.iconButton4, this.iconButton5};

      for (int i = 0; i < aiconbutton.length; i++) {
         float f3 = f2 - 14.0F;
         aiconbutton[i].onFloatFloat2(f, f3);
         aiconbutton[i].onFloatFloatFloatMatrix4f(value2, value, value3, matrix4f);
         f2 = f3 - 7.0F;
         if (i < aiconbutton.length - 1) {
            float f4 = f2 - 1.0F;
            int j = Theme.border();
            float f6 = 17.0F;
            float f5 = 1.0F;
            ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value2, f6, f4, f1, f5, matrix4f, j);
            f2 = f4 - 7.0F;
         }
      }
   }

   public void onBoolean(boolean flag) {
      this.iconButton5.setFlag4(flag);
   }

   public void setString2(String text3) {
      if (text3 != null) {
         this.text = text3;
         this.text2 = null;
         this.flag6 = false;
         this.tween4.setFloat(1.0F);
      }
   }

   public void setConsumer(Consumer<Boolean> consumer2) {
      this.consumer = consumer2;
   }

   public void setCategory2(Category category2) {
      this.flag4 = false;
      String s = category2 != null ? category2.getString() : "All";
      if (this.category != null && category2 != null && category2 != this.category) {
         this.value243 = category2.ordinal() > this.category.ordinal() ? 1 : -1;
      } else {
         this.value243 = 1;
      }

      this.category = category2;
      this.setString(s);
   }
}
