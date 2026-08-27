package client.gui.widget;

import client.api.Theme;
import client.data.Tween;
import client.module.Category;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.TextShader;
import client.util.EasingPresets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class SideBar extends Widget {
   private static final float value239 = 0.999F;
   private static final long time = 100000000L;
   private float value240;
   private float value241;
   private long time2 = -1L;
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.22F);
   private final IconSlider iconSlider;
   private final AvatarSlider avatarSlider;
   private final CategoryTab categoryTab;
   private final List<CategoryTab> list = new ArrayList<>();
   private final AvatarSlider avatarSlider2;
   private final List<IconTab> list2 = new ArrayList<>();
   private final List<IconTab> list3 = new ArrayList<>();
   private final IconTab iconTab;
   private final IconTab iconTab2;
   private final IconTab iconTab3;
   private final IconTab iconTab4;
   private final ServerLabel serverLabel;
   private final QuickMenu quickMenu = new QuickMenu();
   private double value242;
   private Consumer<Category> consumer;
   private Consumer<Category> consumer2;
   private Runnable runnable;
   private Runnable runnable2;
   private Runnable runnable3;
   private Runnable runnable4;

   public SideBar() {
      this.value237 = 224.0F;
      this.value238 = 700.0F;
      this.iconSlider = new IconSlider(CategoryType.LOGO, "System");
      this.avatarSlider = new AvatarSlider("Client categories");
      this.categoryTab = new CategoryTab(null);
      this.categoryTab.setFlag4(true);

      for (Category category : Category.values()) {
         if (!category.check()) {
            this.list.add(new CategoryTab(category));
         }
      }

      this.avatarSlider2 = new AvatarSlider("Settings");
      this.iconTab = new IconTab(CategoryType.CLOUDS, "Сонфигз", this::update6);
      this.iconTab2 = new IconTab(CategoryType.FRIENDS, "Фриендз", this::update9);
      this.iconTab3 = new IconTab(CategoryType.WAYPOINT, "Шайпоинтз", this::update3);
      this.list2.add(this.iconTab);
      this.list2.add(this.iconTab2);
      this.list2.add(this.iconTab3);
      this.iconTab4 = new IconTab(CategoryType.SETTINGS, "Зеттингз", this::update11);
      this.list3.add(this.iconTab4);
      this.list3.add(new IconTab(CategoryType.SEARCH, "Зеарсх", this::update8));
      this.serverLabel = new ServerLabel();
   }

   public void setRunnable4(Runnable runnable) {
      this.runnable4 = runnable;
   }

   private void update3() {
      if (!this.iconTab3.isFlag4()) {
         this.update10();
         this.iconTab3.setFlag4(true);
         if (this.runnable3 != null) {
            this.runnable3.run();
         }
      }
   }

   public void update4() {
      this.update10();
      this.iconTab2.setFlag4(true);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      this.value242 = value;
      if (this.categoryTab.isIntDoubleDouble(count, value, value2)) {
         this.update5();
         return true;
      } else {
         for (CategoryTab categorytab : this.list) {
            if (categorytab.isIntDoubleDouble(count, value, value2)) {
               this.onCategoryTab(categorytab);
               return true;
            }
         }

         for (IconTab icontab : this.list2) {
            if (icontab.isIntDoubleDouble(count, value, value2)) {
               return true;
            }
         }

         for (IconTab icontab1 : this.list3) {
            if (icontab1.isIntDoubleDouble(count, value, value2)) {
               return true;
            }
         }

         return false;
      }
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   private void update5() {
      if (this.categoryTab.isFlag4()) {
         if (this.consumer2 != null) {
            this.consumer2.accept(null);
         }
      } else {
         this.update10();
         this.categoryTab.setFlag4(true);
         if (this.consumer != null) {
            this.consumer.accept(null);
         }
      }
   }

   public void setConsumer2(Consumer<Category> consumer) {
      this.consumer2 = consumer;
   }

   public void setFloat(float value) {
      float f = Math.clamp(value, 0.0F, 1.0F);
      this.value240 = f;
      this.value241 = f;
      float f2 = 48.0F;
      float f1 = 224.0F;
      this.value237 = EasingPresets.getFloatByFloatFloatFloat(f2, f, f1);
      boolean flag = f >= 0.999F;
      this.time2 = -1L;
      this.tween4.setFloat(flag ? 1.0F : 0.0F);
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f44 = this.value235;
      float f45 = this.value236;
      float f46 = this.value237;
      float f47 = this.value238;
      int i = Theme.surface();
      float f24 = 12.0F;
      float f23 = f47;
      float f22 = f46;
      float f21 = f45;
      float f20 = f44;
      ShapeShader.onFloatFloatMatrix4fFloatFloatIntFloatFloat(f23, f20, matrix4f, f22, f24, i, f21, value);
      f44 = this.value235;
      f45 = this.value236 + 48.0F;
      f46 = this.value237 + 1.0F;
      int j = Theme.border();
      float f28 = 1.0F;
      float f27 = f46;
      float f26 = f45;
      float f25 = f44;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f28, f25, f26, f27, matrix4f, j);
      f44 = this.value235 + this.value237;
      f45 = this.value236;
      f47 = this.value238;
      int k = Theme.border();
      float f32 = f47;
      float f31 = 1.0F;
      float f30 = f45;
      float f29 = f44;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f32, f29, f30, f31, matrix4f, k);
      this.onSliderWidgetFloat(this.iconSlider, this.value237);
      float f34 = this.value236;
      float f33 = this.value235;
      this.iconSlider.onFloatFloat2(f34, f33);
      this.iconSlider.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
      float f = this.value235 + 8.0F;
      float f37 = this.value240;
      float f36 = 32.0F;
      float f35 = 208.0F;
      float f1 = EasingPresets.getFloatByFloatFloatFloat(f36, f37, f35);
      float f2 = 9.0F;
      float f3 = this.tween4.getFloat();
      float f4 = this.value236 + 49.0F + 18.0F - 1.0F - f2;
      float f5 = f4 + 32.0F;
      float f6 = this.value236 + 49.0F + 16.0F - 1.0F;
      float f7 = EasingPresets.getFloatByFloatFloatFloat(f6, f3, f5);
      float f38 = this.value240;
      AvatarSlider avatarslider = this.avatarSlider;
      this.onFloatSliderWidgetFloat(f1, avatarslider, f38);
      this.avatarSlider.onFloatFloat2(f4, f);
      this.avatarSlider.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
      this.onSliderWidgetFloat(this.categoryTab, f1);
      this.categoryTab.onFloatFloat2(f7, f);
      this.categoryTab.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
      float f8 = f5 + 32.0F + 4.0F;
      float f9 = f6 + 32.0F + 4.0F;
      List listx = this.list;
      this.getFloatByFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fList(f9, f, f3, value2, value3, value, f8, f1, matrix4f, listx);
      int j1 = this.list.size();
      float f39 = 4.0F;
      int l = j1;
      float f10 = this.getFloatByFloatFloatInt(f8, f39, l);
      int k1 = this.list.size();
      float f40 = 4.0F;
      int i1 = k1;
      float f11 = this.getFloatByFloatFloatInt(f9, f40, i1);
      float f12 = 6.0F + f2 * 2.0F;
      float f13 = f10 + 38.0F - 32.0F + f2 * 2.0F;
      float f14 = f11 + f12 - 32.0F;
      float f41 = this.value240;
      AvatarSlider avatarslider1 = this.avatarSlider2;
      this.onFloatSliderWidgetFloat(f1, avatarslider1, f41);
      AvatarSlider avatarslider2 = this.avatarSlider2;
      float f42 = EasingPresets.getFloatByFloatFloatFloat(f14, f3, f13);
      avatarslider2.onFloatFloat2(f42, f);
      this.avatarSlider2.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
      float f15 = f13 + 32.0F;
      float f16 = f11 + f12;
      List list1 = this.list2;
      this.getFloatByFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fList(f16, f, f3, value2, value3, value, f15, f1, matrix4f, list1);
      float f17 = this.value236 + this.value238 - 18.0F - 32.0F;
      float f18 = this.list3.size() * 32.0F + (this.list3.size() - 1) * 4.0F;
      float f19 = f17 - f18 - 15.0F;
      float f43 = 0.0F;
      List list2x = this.list3;
      this.getFloatByFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fList(f19, f, f43, value2, value3, value, f19, f1, matrix4f, list2x);
      this.onSliderWidgetFloat(this.serverLabel, f1);
      this.serverLabel.onFloatFloat2(f17, f);
      this.serverLabel.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
   }

   private void update6() {
      if (!this.iconTab.isFlag4()) {
         this.update10();
         this.iconTab.setFlag4(true);
         if (this.runnable != null) {
            this.runnable.run();
         }
      }
   }

   public void setRunnable2(Runnable runnable) {
      this.runnable2 = runnable;
   }

   public void update7() {
      this.update10();
      this.iconTab.setFlag4(true);
   }

   private void update8() {
      if (this.runnable4 != null) {
         this.runnable4.run();
      }
   }

   private void update9() {
      if (!this.iconTab2.isFlag4()) {
         this.update10();
         this.iconTab2.setFlag4(true);
         if (this.runnable2 != null) {
            this.runnable2.run();
         }
      }
   }

   public void setRunnable3(Runnable runnable) {
      this.runnable3 = runnable;
   }

   public void onCategory(Category category) {
      if (category == null) {
         if (!this.categoryTab.isFlag4()) {
            this.update10();
            this.categoryTab.setFlag4(true);
         }
      } else {
         for (CategoryTab categorytab : this.list) {
            if (categorytab.getCategory() == category) {
               if (categorytab.isFlag4()) {
                  return;
               }

               this.update10();
               categorytab.setFlag4(true);
               return;
            }
         }
      }
   }

   public void setConsumer(Consumer<Category> consumer2) {
      this.consumer = consumer2;
   }

   public Category getCategory() {
      if (this.categoryTab.isFlag4()) {
         return null;
      } else {
         for (CategoryTab categorytab : this.list) {
            if (categorytab.isFlag4()) {
               return categorytab.getCategory();
            }
         }

         return null;
      }
   }

   private void update10() {
      this.categoryTab.setFlag4(false);

      for (CategoryTab categorytab : this.list) {
         categorytab.setFlag4(false);
      }

      this.iconTab.setFlag4(false);
      this.iconTab2.setFlag4(false);
      this.iconTab3.setFlag4(false);
   }

   public void setFloat2(float value) {
      this.value240 = Math.clamp(value, 0.0F, 1.0F);
      float f2 = this.value240;
      float f1 = 48.0F;
      float f = 224.0F;
      this.value237 = EasingPresets.getFloatByFloatFloatFloat(f1, f2, f);
      boolean flag = this.value240 + 1.0E-4F < this.value241;
      boolean flag1 = this.value240 >= 0.999F;
      if (!flag && flag1) {
         if (this.time2 < 0L) {
            this.time2 = System.nanoTime();
         }

         if (System.nanoTime() - this.time2 >= 100000000L) {
            this.tween4.setFloat2(1.0F);
         }
      } else {
         this.time2 = -1L;
         this.tween4.setFloat2(0.0F);
      }

      this.value241 = this.value240;
   }

   private void onSliderWidgetFloat(SliderWidget sliderWidget, float value) {
      sliderWidget.setValue239(this.value240);
      sliderWidget.setValue237(value);
   }

   private float getFloatByFloatFloatInt(float value, float value2, int count) {
      return count <= 0 ? value : value + count * 32.0F + Math.max(0, count - 1) * value2;
   }

   private float getFloatByFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fList(
      float value, float value2, float value3, float value4, float value5, float value6, float value7, float value8, Matrix4f matrix4f, List list
   ) {
      float f = value7;
      float f1 = value;

      for (int i = 0; i < list.size(); i++) {
         SliderWidget sliderwidget = (SliderWidget)list.get(i);
         this.onSliderWidgetFloat(sliderwidget, value8);
         float f3 = EasingPresets.getFloatByFloatFloatFloat(f1, value3, f);
         sliderwidget.onFloatFloat2(f3, value2);
         sliderwidget.onFloatFloatFloatMatrix4f(value6, value4, value5, matrix4f);
         float f2 = 32.0F + (i < list.size() - 1 ? 4.0F : 0.0F);
         f += f2;
         f1 += f2;
      }

      return f;
   }

   private void onCategoryTab(CategoryTab categoryTab) {
      if (categoryTab.isFlag4()) {
         if (this.consumer2 != null) {
            this.consumer2.accept(categoryTab.getCategory());
         }
      } else {
         this.update10();
         categoryTab.setFlag4(true);
         if (this.consumer != null) {
            this.consumer.accept(categoryTab.getCategory());
         }
      }
   }

   public void onRunnable(Runnable runnable) {
      this.quickMenu.setRunnable(runnable);
   }

   private void onFloatSliderWidgetFloat(float value, SliderWidget sliderWidget, float value2) {
      sliderWidget.setValue239(value2);
      sliderWidget.setValue237(value);
   }

   private void update11() {
      double d0 = this.value242;
      this.quickMenu.setColorSupplier2(() -> {
         float f = this.iconTab4.getValue235() + 16.0F - 8.0F + 16.0F;
         float f1 = f + 8.0F;
         String s1 = this.iconTab4.getText();
         float f4 = 14.0F;
         String s = s1;
         float f2 = f1 + TextShader.getFloatByFloatString(f4, s);
         float f5 = this.value240;
         float f3 = EasingPresets.getFloatByFloatFloatFloat(f, f5, f2);
         return new float[]{Math.max(f3, (float)d0) + 10.0F, this.iconTab4.getValue236() + this.iconTab4.getValue238() / 2.0F};
      });
   }

   public void update12() {
      this.update10();
      this.iconTab3.setFlag4(true);
   }
}
