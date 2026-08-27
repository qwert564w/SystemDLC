package client.gui.widget;

import client.api.Theme;
import client.concurrent.ConfigManager;
import client.data.ClientAccess;
import client.data.Tween;
import client.module.Module;
import client.module.client.ThemeModule;
import client.render.ShapeShader;
import client.util.EasingPresets;
import client.util.RangeMath;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.joml.Matrix4f;

public class ContentArea extends Widget {
   private static final float value239 = 2.0F;
   private static final float value240 = 64.0F;
   private static final float value241 = 16.0F;
   private static final float value242 = 3.0F;
   private static final float value243 = 4.0F;
   private static final float value244 = 48.0F;
   private static final float value245 = 4.0F;
   private static final float value246 = 4.0F;
   private static float value247;
   private static float value248;
   private final List<ModuleRow> list = new ArrayList<>();
   private int[] intArray = new int[0];
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat(0.0F, 0.28F);
   private float value249;
   private float value250;
   private float value251 = 301.0F;
   private boolean flag4;
   private float value252;
   private final float[] floatArray = new float[3];
   private final float[] floatArray2 = new float[3];

   public ContentArea() {
      this.value237 = 963.0F;
   }

   private float getFloat() {
      float f5 = this.getFloat3();
      float f6 = this.getFloat5();
      float f7 = this.tween4.getValue3();
      float f4 = this.getFloat4();
      float f3 = f7;
      float f2 = 48.0F;
      float f1 = f6;
      float f = f5;
      return RangeMath.getFloatByFloatFloatFloatFloatFloat2(f3, f1, f2, f4, f);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      ModuleRow modulerow = null;

      for (ModuleRow modulerow1 : this.list) {
         if (modulerow1.check3()) {
            modulerow = modulerow1;
            break;
         }
      }

      if (modulerow != null) {
         if (modulerow.isIntDoubleDouble(count, value, value2)) {
            return true;
         } else {
            modulerow.update8();
            return true;
         }
      } else {
         if (count == 0 && this.getFloat4() > 0.001F && this.getFloat5() > 48.0F) {
            float f4 = this.getFloat2() - 4.0F;
            float f5 = this.getFloat();
            float f3 = 48.0F;
            float f2 = 12.0F;
            float f1 = f5;
            float f = f4;
            if (isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value)) {
               this.flag4 = true;
               this.value252 = (float)value2 - this.getFloat();
               return true;
            }
         }

         if (!(value < this.value235) && !(value > this.value235 + this.value237) && !(value2 < value247) && !(value2 > value248)) {
            for (ModuleRow modulerow2 : this.list) {
               if (modulerow2.isIntDoubleDouble(count, value, value2)) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }
   }

   private void update3() {
      this.value249 = Math.clamp(this.value249, 0.0F, this.getFloat4());
      this.tween4.setFloat2(this.value249);
   }

   private float getFloat2() {
      return this.value235 + this.value237 - 4.0F - 4.0F;
   }

   @Override
   public boolean isIntIntInt(int count, int count2, int count3) {
      for (ModuleRow modulerow : this.list) {
         if (modulerow.isIntIntInt(count, count2, count3)) {
            return true;
         }
      }

      return false;
   }

   public void setFloat(float value) {
      this.value249 = Math.max(0.0F, value);
      this.tween4.setFloat2(this.value249);
   }

   private float getFloat3() {
      return this.value236 + 16.0F;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      for (ModuleRow modulerow : this.list) {
         modulerow.update5();
      }

      this.update4();
      float f12 = this.tween4.getFloat();
      float f13 = this.value235 + 16.0F;
      float f = this.value236 + 16.0F - f12;
      float f1 = this.value251 + 16.0F;
      float[] afloat = this.floatArray2;
      afloat[0] = 0.0F;
      afloat[1] = 0.0F;
      afloat[2] = 0.0F;
      float f2 = Math.max(0.0F, this.value238 - 2.0F);
      value247 = this.value236;
      value248 = this.value236 + f2;
      float f8 = this.value237;
      float f7 = this.value236;
      float f6 = this.value235;
      ScissorStack.onFloatFloatFloatFloat(f8, f2, f7, f6);

      for (int i = 0; i < this.list.size(); i++) {
         ModuleRow modulerow1 = this.list.get(i);
         int j = i < this.intArray.length ? this.intArray[i] : 0;
         float f3 = modulerow1.getFloat5();
         float f4 = f13 + j * f1;
         float f5 = f + afloat[j];
         modulerow1.onFloatFloat2(f5, f4);
         modulerow1.update4();
         boolean flag = f5 + f3 > this.value236 && f5 < this.value236 + f2;
         if (flag) {
            modulerow1.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
         }

         afloat[j] += f3 + 16.0F;
      }

      ScissorStack.update();
      float f11 = this.value237;
      float f10 = this.value236;
      float f9 = this.value235;
      ScissorStack.onFloatFloatFloatFloat(f11, f2, f10, f9);

      for (ModuleRow modulerow2 : this.list) {
         modulerow2.onFloatFloatFloatMatrix4f2(value2, value3, value, matrix4f);
      }

      ScissorStack.update();
      this.value250 = 0.0F;

      for (float f14 : afloat) {
         if (f14 > this.value250) {
            this.value250 = f14;
         }
      }

      this.update3();
      this.onMatrix4fFloatFloat(matrix4f, value, f12);
   }

   private float getFloat4() {
      return Math.max(0.0F, this.value250 - this.value238 + 16.0F);
   }

   private float getFloat5() {
      return Math.max(0.0F, this.value238 - 2.0F - 32.0F);
   }

   private static int getIntByFloatInt(float value, int count) {
      int i = Math.clamp((long)((int)(255.0F * value)), 0, 255);
      return i << 24 | count & 16777215;
   }

   public void setModule(Module module2) {
      if (module2 != null) {
         int i = -1;

         for (int j = 0; j < this.list.size(); j++) {
            if (this.list.get(j).getModule() == module2) {
               i = j;
               break;
            }
         }

         if (i >= 0) {
            ModuleRow modulerow = this.list.get(i);
            modulerow.setBoolean2(false);
            int k = i < this.intArray.length ? this.intArray[i] : 0;
            float f = 0.0F;

            for (int l = 0; l < i; l++) {
               int i1 = l < this.intArray.length ? this.intArray[l] : 0;
               if (i1 == k) {
                  f += this.list.get(l).getFloat2() + 16.0F;
               }
            }

            this.value249 = f;
            this.tween4.setFloat2(this.value249);
            modulerow.update6();
         }
      }
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      for (ModuleRow modulerow : this.list) {
         if (modulerow.isIntChar(count, symbol)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      for (ModuleRow modulerow : this.list) {
         if (modulerow.isIntIntInt2(count, count2, count3)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      if (value2 >= this.value235 && value2 <= this.value235 + this.value237 && value3 >= this.value236 && value3 <= this.value236 + this.value238) {
         for (ModuleRow modulerow : this.list) {
            if (modulerow.isDoubleDoubleDouble(value, value2, value3)) {
               return true;
            }
         }

         this.value249 = Math.clamp(this.value249 - (float)(value * 48.0), 0.0F, this.getFloat4());
         this.tween4.setFloat2(this.value249);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (this.flag4 && count == 0) {
         float f5 = (float)value - this.value252;
         float f6 = this.getFloat3();
         float f7 = this.getFloat5();
         float f4 = this.getFloat4();
         float f3 = 48.0F;
         float f2 = f7;
         float f1 = f6;
         float f = f5;
         this.value249 = RangeMath.getFloatByFloatFloatFloatFloatFloat(f3, f2, f4, f, f1);
         this.tween4.setFloat2(this.value249);
         return true;
      } else {
         for (ModuleRow modulerow : this.list) {
            if (modulerow.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (this.flag4 && count == 0) {
         this.flag4 = false;
         return true;
      } else {
         for (ModuleRow modulerow : this.list) {
            if (modulerow.isDoubleDoubleInt(value, value2, count)) {
               return true;
            }
         }

         return false;
      }
   }

   public List<ModuleRow> getList() {
      return this.list;
   }

   public void setFloat2(float value) {
      this.value249 = Math.max(0.0F, value);
      this.tween4.setFloat(this.value249);
   }

   public float getValue249() {
      return this.value249;
   }

   private Set getSet() {
      ConfigManager configmanager = ClientAccess.getConfigManager();
      return (Set)(configmanager == null ? Set.of() : new HashSet(configmanager.getList2()));
   }

   public void addList(List<Module> list2) {
      this.list.clear();
      Set set = this.getSet();

      for (Module module : list2) {
         ModuleRow modulerow = new ModuleRow(module);
         modulerow.setFloat(this.value251);
         modulerow.setBoolean(set.contains(module.getName()));
         this.list.add(modulerow);
      }

      this.update4();
      this.value249 = 0.0F;
      this.tween4.setFloat(0.0F);
   }

   public void onFloatFloat4(float value, float value2) {
      this.value237 = value2;
      float f2 = 358.0F;
      float f1 = 301.0F;
      float f = EasingPresets.getFloatByFloatFloatFloat(f2, value, f1);
      if (!(Math.abs(f - this.value251) <= 0.001F)) {
         this.value251 = f;

         for (ModuleRow modulerow : this.list) {
            modulerow.setFloat(this.value251);
         }

         this.update4();
         this.update3();
      }
   }

   public void onMatrix4fFloat(Matrix4f matrix4f, float value) {
      ThemeModule thememodule = ThemeModule.getThemeModule();
      if (thememodule == null || !thememodule.check4()) {
         float f = this.tween4.getValue3();
         float f1 = this.getFloat4();
         float f2 = value * Math.clamp(f / 16.0F, 0.0F, 1.0F);
         float f3 = value * Math.clamp((f1 - f) / 16.0F, 0.0F, 1.0F);
         int i = Theme.background() & 16777215;
         float f4 = this.value237 - 3.0F;
         if (f2 > 0.001F) {
            float f15 = this.value235;
            float f16 = this.value236;
            int j = getIntByFloatInt(f2, i);
            float f7 = 64.0F;
            float f6 = f16;
            float f5 = f15;
            ShapeShader.onIntMatrix4fFloatIntFloatFloatFloat(j, matrix4f, f4, i, f7, f5, f6);
         }

         if (f3 > 0.001F) {
            float f13 = this.value235;
            float f14 = this.value236 + this.value238 - 64.0F;
            int l = getIntByFloatInt(f3, i);
            float f12 = 1.0F;
            int k = l;
            float f11 = 12.0F;
            float f10 = 64.0F;
            float f9 = f14;
            float f8 = f13;
            ShapeShader.onFloatIntFloatFloatFloatFloatFloatIntMatrix4f(f11, k, f12, f9, f10, f4, f8, i, matrix4f);
         }
      }
   }

   private void onMatrix4fFloatFloat(Matrix4f matrix4f, float value, float value2) {
      if (!(this.getFloat4() <= 0.001F) && !(this.getFloat5() <= 48.0F)) {
         float f9 = this.getFloat3();
         float f10 = this.getFloat5();
         float f4 = this.getFloat4();
         float f3 = 48.0F;
         float f2 = f10;
         float f1 = f9;
         float f = RangeMath.getFloatByFloatFloatFloatFloatFloat2(value2, f2, f3, f4, f1);
         f10 = this.getFloat2();
         int i = Theme.surface();
         float f8 = 2.0F;
         float f7 = 48.0F;
         float f6 = 4.0F;
         float f5 = f10;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f8, f5, i, matrix4f, f7, f6, value, f);
      }
   }

   public void onBoolean(boolean flag) {
      for (ModuleRow modulerow : this.list) {
         modulerow.setBoolean2(flag);
      }
   }

   private void update4() {
      int i = this.list.size();
      if (this.intArray.length != i) {
         this.intArray = new int[i];
      }

      float[] afloat = this.floatArray;
      afloat[0] = 0.0F;
      afloat[1] = 0.0F;
      afloat[2] = 0.0F;

      for (int j = 0; j < i; j++) {
         int k = 0;

         for (int l = 1; l < 3; l++) {
            if (afloat[l] < afloat[k]) {
               k = l;
            }
         }

         this.intArray[j] = k;
         afloat[k] += this.list.get(j).getFloat2() + 16.0F;
      }
   }

   public void onSet(Set set) {
      if (set != null) {
         for (ModuleRow modulerow : this.list) {
            String s = modulerow.getModule().getName();
            modulerow.setBoolean(set.contains(s));
         }

         this.update4();
      }
   }
}
