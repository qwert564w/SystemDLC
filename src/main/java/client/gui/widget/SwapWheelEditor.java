package client.gui.widget;

import client.api.SwapWheelView;
import client.api.Theme;
import client.data.AnimatedInt;
import client.data.Tween;
import client.gui.screen.ClickGuiScreen;
import client.module.CategoryType;
import client.module.player.SwapWheel;
import client.render.ItemIconCache;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.HotkeySetting;
import client.setting.KeybindSetting;
import client.util.EasingPresets;
import client.util.Easings;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.joml.Matrix4f;

public class SwapWheelEditor extends ButtonWidget {
   private static final float value241 = Math.max(Math.max(184.0F, 184.0F), 204.0F);
   private static final float value242 = 54.0F + value241 + 14.0F + 38.0F;
   private final SwapWheelView swapWheelView;
   private int value243 = 0;
   private int value244 = -1;
   private int value245 = -1;
   private final Tween[] tweenArray = new Tween[6];
   private final Tween[] tweenArray2 = new Tween[36];
   private final Tween tween5 = EasingPresets.getTween();
   private final Tween[] tweenArray3 = new Tween[6];
   private final Tween[] tweenArray4 = new Tween[6];
   private final Tween[] tweenArray5 = new Tween[6];
   private final Tween[] tweenArray6 = new Tween[6];
   private final Tween tween6 = EasingPresets.getTween();
   private final Tween tween7 = EasingPresets.getTween();
   private int value246 = -1;
   private int value247 = -1;

   public SwapWheelEditor(SwapWheelView swapWheelView2) {
      this.swapWheelView = swapWheelView2;

      for (int i = 0; i < this.tweenArray2.length; i++) {
         this.tweenArray2[i] = EasingPresets.getTween();
      }

      for (int j = 0; j < 6; j++) {
         this.tweenArray[j] = EasingPresets.getTween();
         this.tweenArray3[j] = EasingPresets.getTween();
         this.tweenArray4[j] = EasingPresets.getTween();
         this.tweenArray5[j] = new Tween(0.0F, 0.55F).getTweenByFunction(Easings::getFloatByFloat3);
         this.tweenArray6[j] = EasingPresets.getTweenByFloatFloat2(0.0F, 0.15F);
      }

      this.setFlag52(true);
      this.setFlag6(true);
   }

   @Override
   protected void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f2) {
      int i = this.swapWheelView.slotCapacity();
      if (this.value243 >= i) {
         this.value243 = 0;
      }

      this.update12();
      float f = this.value236 + EasingPresets.getFloatByFloat(value2);
      Matrix4f matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, value2, this.value235 + 406.0F, f + value242 / 2.0F);
      this.onFloatFloatMatrix4f(value2, f, matrix4f);
      this.onFloatFloatMatrix4fFloatFloat2(value2, value3, matrix4f, value, f);
      float f1 = f + 40.0F + 14.0F;
      float f2 = this.value235 + 14.0F;
      float f3 = f1 + (value241 - 184.0F) / 2.0F;
      this.onMatrix4fFloatFloatFloatFloatFloatInt(matrix4f, value3, value2, f2, f3, value, i);
      float f4 = f2 + 200.0F + 14.0F;
      float f5 = f1 + (value241 - 184.0F) / 2.0F;
      this.onFloatFloatFloatMatrix4fFloatFloat(value, value3, f4, matrix4f, f5, value2);
      float f6 = f4 + 302.0F + 14.0F;
      float f7 = f1 + (value241 - 204.0F) / 2.0F;
      this.onFloatFloatIntFloatMatrix4fFloatFloat(f7, value3, i, value, matrix4f, value2, f6);
      this.onFloatFloatMatrix4fFloatFloat(value2, value, matrix4f, f, value3);
   }

   @Override
   public boolean isIntIntInt(int count, int count2, int count3) {
      if (this.value246 < 0 || this.value247 == -1) {
         return false;
      } else if (count != this.value247) {
         return false;
      } else {
         this.onIntInt(this.value247, 0);
         this.update11();
         return true;
      }
   }

   private void onFloatFloatMatrix4fFloatFloat(float value, float value2, Matrix4f matrix4f, float value3, float value4) {
      float f = value3 + value242 - 38.0F;
      float f20 = this.value235 + 14.0F;
      int j = Theme.border();
      float f5 = 1.0F;
      float f4 = 784.0F;
      float f3 = f20;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f5, f3, f, f4, matrix4f, j);
      float f1 = this.value235 + 296.0F;
      float f2 = f + 7.0F;
      double d3 = value4;
      double d2 = value2;
      float f7 = 24.0F;
      float f6 = 220.0F;
      double d1 = d2;
      double d0 = d3;
      boolean flag = isFloatFloatDoubleFloatFloatDouble(f1, f2, d1, f7, f6, d0);
      this.tween7.setFloat2(flag ? 1.0F : 0.0F);
      int l1 = Theme.elevated();
      int i2 = Theme.border();
      float f8 = this.tween7.getFloat();
      int l = i2;
      int k = l1;
      int i = AnimatedInt.getIntByIntFloatInt(l, f8, k);
      int j2 = Theme.border();
      float f12 = 1.0F;
      int i1 = j2;
      float f11 = 6.0F;
      float f10 = 24.0F;
      float f9 = 220.0F;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f12, f9, value, matrix4f, f2, i, f11, f1, i1, f10);
      CategoryType categorytype1 = CategoryType.TRASH;
      float f21 = f1 + 10.0F;
      float f22 = f2 + 6.0F;
      int j1 = Theme.foreground();
      float f16 = 12.0F;
      float f15 = 12.0F;
      float f14 = f22;
      float f13 = f21;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, j1, matrix4f, f14, categorytype, f16, f13, f15);
      f21 = f1 + 28.0F;
      f22 = f2 + 6.0F;
      int k1 = Theme.foreground();
      float f19 = 12.0F;
      float f18 = f22;
      float f17 = f21;
      String s = "Очистить выбранную ячейку";
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f18, f17, k1, f19, value, s, matrix4f);
   }

   private void update11() {
      this.value246 = -1;
      this.value247 = -1;
   }

   @Override
   protected boolean isIntIntInt3(int count, int count2, int count3) {
      if (this.value246 >= 0) {
         if (count3 == 256) {
            this.onIntInt(-1, 0);
            this.update11();
            return true;
         } else if (KeybindSetting.isInt(count3)) {
            this.value247 = count3;
            return true;
         } else {
            this.onIntInt(count3, count);
            this.update11();
            return true;
         }
      } else {
         int i = this.swapWheelView.slotCapacity();
         if (count3 >= 49 && count3 <= 57) {
            int j = count3 - 49;
            if (j < i) {
               this.value243 = j;
               return true;
            }
         }

         if (count3 != 261 && count3 != 259) {
            return false;
         } else {
            this.swapWheelView.clearSlot(this.value243);
            return true;
         }
      }
   }

   @Override
   public void update5() {
      this.value237 = 812.0F;
      this.value238 = value242;
      if (this.value243 >= this.swapWheelView.slotCapacity()) {
         this.value243 = 0;
      }

      super.update5();
   }

   @Override
   protected boolean isDoubleDouble(double value, double value2) {
      float f3 = value242;
      float f2 = 812.0F;
      float f1 = this.value236;
      float f = this.value235;
      return isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value);
   }

   @Override
   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      int i = this.swapWheelView.slotCapacity();
      if (this.value246 >= 0) {
         HotkeySetting hotkeysetting = this.swapWheelView.getCellBind(this.value246);
         if (hotkeysetting != null) {
            byte b0 = 0;
            hotkeysetting.onIntInt(b0, count);
         }

         this.update11();
         return true;
      } else {
         int[] aint = this.getIntArrayByDoubleDouble(value2, value);
         int j = aint[0];
         int k = aint[1];
         if (j >= 0 && j < i && count == 0) {
            if (k == 1) {
               this.value246 = j;
               this.value247 = -1;
               return true;
            }

            if (k == 2) {
               HotkeySetting hotkeysetting1 = this.swapWheelView.getCellBind(j);
               if (hotkeysetting1 != null) {
                  byte b2 = 0;
                  byte b1 = -1;
                  hotkeysetting1.onIntInt(b2, b1);
               }

               this.tweenArray5[j].setFloat(0.0F);
               this.tweenArray5[j].setFloat2(1.0F);
               return true;
            }
         }

         float f = this.value235 + 812.0F - 14.0F - 20.0F;
         float f1 = this.value236 + 10.0F;
         if (count == 0) {
            float f4 = 20.0F;
            float f3 = 20.0F;
            if (isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f4, f3, value)) {
               this.update4();
               return true;
            }
         }

         if (this.value245 >= 0 && this.value245 < i) {
            if (count == 0) {
               this.value243 = this.value245;
               return true;
            }

            if (count == 1) {
               this.swapWheelView.clearSlot(this.value245);
               return true;
            }
         }

         if (count == 0 && this.value244 >= 0) {
            MinecraftClient minecraftclient = MinecraftClient.getInstance();
            if (minecraftclient.player != null) {
               ItemStack itemstack;
               if (this.value244 == 40) {
                  itemstack = minecraftclient.player.getOffHandStack();
               } else if (this.value244 < 36) {
                  itemstack = minecraftclient.player.getInventory().getStack(this.value244);
               } else {
                  itemstack = ItemStack.EMPTY;
               }

               if (itemstack != null && !itemstack.isEmpty()) {
                  int l = this.value243;
                  this.swapWheelView.setSlot(itemstack, l);
                  this.value243 = (this.value243 + 1) % i;
               }

               return true;
            }
         }

         float f7 = this.value235 + 296.0F;
         float f2 = this.value236 + value242 - 38.0F + 7.0F;
         if (count == 0) {
            float f6 = 24.0F;
            float f5 = 220.0F;
            if (isFloatFloatDoubleFloatFloatDouble(f7, f2, value2, f6, f5, value)) {
               this.swapWheelView.clearSlot(this.value243);
               return true;
            }
         }

         return this.isDoubleDouble(value, value2);
      }
   }

   private int[] getIntArrayByDoubleDouble(double value, double value2) {
      float f = this.value236 + 40.0F + 14.0F + (value241 - 204.0F) / 2.0F;
      float f1 = this.value235 + 14.0F + 200.0F + 14.0F + 302.0F + 14.0F + 14.0F;
      float f2 = f + 14.0F;

      for (int i = 0; i < 6; i++) {
         float f3 = f2 + i * 30.0F;
         float f4 = f1 + 226.0F - 8.0F - 20.0F;
         float f5 = f4 - 4.0F - 70.0F;
         float f6 = f3 + 4.0F;
         float f8 = 18.0F;
         float f7 = 70.0F;
         if (isFloatFloatDoubleFloatFloatDouble(f5, f6, value, f8, f7, value2)) {
            return new int[]{i, 1};
         }

         float f10 = 18.0F;
         float f9 = 20.0F;
         if (isFloatFloatDoubleFloatFloatDouble(f4, f6, value, f10, f9, value2)) {
            return new int[]{i, 2};
         }
      }

      return new int[]{-1, -1};
   }

   private static boolean isIntFloatDoubleFloatDoubleFloatIntFloat(int count, float value, double value2, float value3, double value4, float value5, int count2, float value6) {
      float f = (float)value2 - value3;
      float f1 = (float)value4 - value;
      float f2 = (float)Math.sqrt(f * f + f1 * f1);
      if (!(f2 < value6) && !(f2 > value5)) {
         double d0 = Math.atan2(f1, f) + (Math.PI / 2);
         if (d0 < 0.0) {
            d0 += Math.PI * 2;
         }

         int i = (int)Math.floor(d0 / (Math.PI * 2) * count2);
         return i == count;
      } else {
         return false;
      }
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      return this.value246 >= 0 ? this.isIntIntInt3(count2, count, count3) : super.isIntIntInt2(count, count2, count3);
   }

   private void onIntInt(int count, int count2) {
      if (this.value246 >= 0) {
         HotkeySetting hotkeysetting = this.swapWheelView.getCellBind(this.value246);
         if (hotkeysetting != null) {
            hotkeysetting.onIntInt(count2, count);
         }
      }
   }

   private void onMatrix4fFloatFloatFloatFloatFloatInt(Matrix4f matrix4f, float value, float value2, float value3, float value4, float value5, int count) {
      int j3 = Theme.elevated();
      int k3 = Theme.border();
      float f9 = 1.0F;
      int i2 = k3;
      int l1 = j3;
      float f8 = 8.0F;
      float f7 = 184.0F;
      float f6 = 200.0F;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f9, f6, value2, matrix4f, value4, l1, f8, value3, i2, f7);
      float f = value3 + 100.0F;
      float f1 = this.value236 + 40.0F + 14.0F + (value241 - 184.0F) / 2.0F + 92.0F;
      float f2 = f1 + EasingPresets.getFloatByFloat(value2);
      this.value245 = -1;
      int[] aint = new int[count];

      for (int i = 0; i < count; i++) {
         double d2 = value;
         double d3 = value5;
         float f11 = 78.0F;
         float f10 = 40.0F;
         double d1 = d3;
         double d0 = d2;
         boolean flag = isIntFloatDoubleFloatDoubleFloatIntFloat(i, f1, d0, f, d1, f11, count, f10);
         if (flag) {
            this.value245 = i;
         }

         boolean flag1 = i == this.value243;
         this.tweenArray[i].setFloat2(!flag && !flag1 ? 0.0F : 1.0F);
         float f3 = this.tweenArray[i].getFloat();
         int j = flag1 ? Theme.primary() : Theme.surface();
         int i3 = Theme.primary();
         float f12 = f3 * (flag1 ? 0.35F : 0.7F);
         int j2 = i3;
         aint[i] = AnimatedInt.getIntByIntFloatInt(j2, f12, j);
      }

      float f16 = 0.02F;
      RenderSystem.enableBlend();
      RenderSystem.disableCull();
      RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);

      for (int k2 = 0; k2 < count; k2++) {
         float f17 = (float)((-Math.PI / 2) + (Math.PI * 2) * ((double)k2 / count)) + f16;
         float f19 = (float)((-Math.PI / 2) + (Math.PI * 2) * ((k2 + 1.0) / count)) - f16;
         int k = aint[k2];
         int l = k >> 16 & 0xFF;
         int i1 = k >> 8 & 0xFF;
         int j1 = k & 0xFF;
         int k1 = Math.round((k >>> 24 & 0xFF) * value2);
         float f14 = 78.0F;
         float f13 = 40.0F;
         SwapWheel.onIntFloatIntIntFloatIntFloatFloatBufferBuilderFloatFloatMatrix4f(j1, f13, k1, l, f14, i1, f19, f17, bufferbuilder, f, f2, matrix4f);
      }

      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      RenderSystem.enableCull();
      RenderSystem.defaultBlendFunc();

      for (int l2 = 0; l2 < count; l2++) {
         float f18 = (float)((-Math.PI / 2) + (Math.PI * 2) * ((double)l2 / count));
         float f20 = (float)((-Math.PI / 2) + (Math.PI * 2) * ((l2 + 1.0) / count));
         float f21 = (f18 + f20) / 2.0F;
         float f22 = 59.0F;
         ItemStack itemstack = this.swapWheelView.getWheelSlots()[l2];
         if (itemstack != null && !itemstack.isEmpty()) {
            float f23 = f + (float)Math.cos(f21) * f22 - 8.0F;
            float f25 = f2 + (float)Math.sin(f21) * f22 - 8.0F;
            float f15 = 16.0F;
            ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f15, value2, f23, itemstack, matrix4f, f25);
         } else {
            String s = String.valueOf(l2 + 1);
            float f24 = TextShader.getFloatByStringFloat(s, 14.0F);
            float f4 = f + (float)Math.cos(f21) * f22 - f24 / 2.0F;
            float f5 = f2 + (float)Math.sin(f21) * f22 - 7.0F;
            TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s, f4, f5, 14.0F, Theme.mutedFg(), value2);
         }
      }
   }

   private void onFloatFloatMatrix4fFloatFloat2(float value, float value2, Matrix4f matrix4f, float value3, float value4) {
      float f = this.value235 + 14.0F;
      float f1 = value4 + 12.0F;
      float f2 = f1 + 1.0F;
      CategoryType categorytype2 = CategoryType.SWAP_HUD;
      int j = Theme.foreground();
      float f6 = 14.0F;
      float f5 = 14.0F;
      CategoryType categorytype = categorytype2;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, j, matrix4f, f2, categorytype, f6, f, f5);
      float f24 = f + 14.0F + 8.0F;
      int k = Theme.foreground();
      float f8 = 16.0F;
      float f7 = f24;
      String s = "Настройка колеса свапа";
      TextShader.onIntFloatFloatMatrix4fFloatFloatString(k, value, f1, matrix4f, f7, f8, s);
      float f3 = this.value235 + 812.0F - 14.0F - 20.0F;
      float f4 = value4 + 10.0F;
      double d3 = value2;
      double d2 = value3;
      float f10 = 20.0F;
      float f9 = 20.0F;
      double d1 = d2;
      double d0 = d3;
      boolean flag = isFloatFloatDoubleFloatFloatDouble(f3, f4, d1, f10, f9, d0);
      this.tween6.setFloat2(flag ? 1.0F : 0.0F);
      int l1 = Theme.elevated();
      int i2 = Theme.border();
      float f11 = this.tween6.getFloat();
      int i1 = i2;
      int l = l1;
      int i = AnimatedInt.getIntByIntFloatInt(i1, f11, l);
      float f14 = 6.0F;
      float f13 = 20.0F;
      float f12 = 20.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f14, f3, i, matrix4f, f13, f12, value, f4);
      categorytype2 = CategoryType.CLOSE;
      f24 = f3 + 5.0F;
      float f25 = f4 + 5.0F;
      int j1 = Theme.foreground();
      float f18 = 10.0F;
      float f17 = 10.0F;
      float f16 = f25;
      float f15 = f24;
      CategoryType categorytype1 = categorytype2;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, j1, matrix4f, f16, categorytype1, f18, f15, f17);
      float f23 = this.value235 + 14.0F;
      f24 = value4 + 40.0F;
      int k1 = Theme.border();
      float f22 = 1.0F;
      float f21 = 784.0F;
      float f20 = f24;
      float f19 = f23;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f22, f19, f20, f21, matrix4f, k1);
   }

   private void onFloatFloatMatrix4f(float value, float value2, Matrix4f matrix4f) {
      float f11 = this.value235;
      float f12 = value242;
      int l = Theme.background();
      int i1 = Theme.border();
      float f10 = 6.0F;
      float f9 = 2.0F;
      float f8 = 0.0F;
      int k = 436207616;
      float f7 = 1.0F;
      int j = i1;
      int i = l;
      float f6 = 12.0F;
      float f5 = 12.0F;
      float f4 = 12.0F;
      float f3 = 12.0F;
      float f2 = f12;
      float f1 = 812.0F;
      float f = f11;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f1, k, i, f, value2, f10, f3, f2, f9, matrix4f, value, j, f5, f7, f4, f6, f8
      );
   }

   private void update12() {
      float f = ClickGuiScreen.getValue235();
      float f1 = MinecraftClient.getInstance().getWindow().getScaledWidth() / f;
      float f2 = MinecraftClient.getInstance().getWindow().getScaledHeight() / f;
      this.value235 = Math.round((f1 - 812.0F) / 2.0F);
      this.value236 = Math.round((f2 - value242) / 2.0F);
   }

   private void onIntFloatFloatBooleanMatrix4fFloatFloatFloat(int count, float value, float value2, boolean flag5, Matrix4f matrix4f2, float value3, float value4, float value5) {
      int i5 = Theme.surface();
      float f24 = 3.0F;
      float f23 = 1.0F;
      float f22 = 0.0F;
      int l1 = 436207616;
      float f21 = 0.0F;
      byte b0 = 0;
      int k1 = i5;
      float f20 = 8.0F;
      float f19 = 8.0F;
      float f18 = 8.0F;
      float f17 = 8.0F;
      float f16 = 26.0F;
      float f15 = 226.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f15, l1, k1, value3, value4, f24, f17, f16, f23, matrix4f2, value, b0, f19, f21, f18, f20, f22
      );
      float f = value3 + 8.0F;
      float f1 = value4 + 5.0F;
      ItemStack itemstack = this.swapWheelView.getWheelSlots()[count];
      boolean flag = flag5 && itemstack != null && !itemstack.isEmpty();
      if (flag) {
         float f25 = 16.0F;
         ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f25, value, f, itemstack, matrix4f2, f1);
      } else {
         CategoryType categorytype3 = CategoryType.INFO;
         int i2 = Theme.mutedFg();
         float f27 = 16.0F;
         float f26 = 16.0F;
         CategoryType categorytype = categorytype3;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, i2, matrix4f2, f1, categorytype, f27, f, f26);
         if (flag5) {
            double d11 = value5;
            double d8 = value2;
            float f29 = 16.0F;
            float f28 = 16.0F;
            double d1 = d8;
            double d0 = d11;
            if (isFloatFloatDoubleFloatFloatDouble(f, f1, d1, f29, f28, d0)) {
               float f30 = 16.0F;
               String s1 = "Ячейка пустая — добавьте предмет из инвентаря";
               HeaderPainter.onFloatStringFloatFloat(f30, s1, f, f1);
            }
         }
      }

      float f4;
      float f5;
      float f6;
      boolean flag1;
      float f7;
      boolean flag4;
      int colorSlot;
      label64: {
         float f2 = f + 16.0F + 6.0F;
         float f3 = value4 + 7.0F;
         int i = flag5 ? Theme.foreground() : Theme.mutedFg();
         String s3 = "Ячейка " + (count + 1);
         float f31 = 12.0F;
         String s2 = s3;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f3, f2, i, f31, value, s2, matrix4f2);
         f4 = value3 + 226.0F - 8.0F - 20.0F;
         f5 = f4 - 4.0F - 70.0F;
         f6 = value4 + 4.0F;
         flag1 = this.value246 == count;
         this.tweenArray6[count].setFloat2(flag1 ? 1.0F : 0.0F);
         f7 = this.tweenArray6[count].getFloat();
         if (!flag1) {
            double d6 = value5;
            double d9 = value2;
            float f33 = 18.0F;
            float f32 = 70.0F;
            double d3 = d9;
            double d2 = d6;
            if (isFloatFloatDoubleFloatFloatDouble(f5, f6, d3, f33, f32, d2)) {
               flag4 = true;
               break label64;
            }
         }

         flag4 = false;
      }

      boolean flag2 = flag4;
      this.tweenArray3[count].setFloat2(flag2 ? 1.0F : 0.0F);
      colorSlot = Theme.background();
      int l4 = Theme.elevated();
      float f34 = 0.85F;
      int k2 = l4;
      int j2 = colorSlot;
      int j = AnimatedInt.getIntByIntFloatInt(k2, f34, j2);
      colorSlot = Theme.background();
      float f35 = this.tweenArray3[count].getFloat();
      int l2 = colorSlot;
      int k = AnimatedInt.getIntByIntFloatInt(j, f35, l2);
      int i3 = Theme.primary();
      int l = AnimatedInt.getIntByIntFloatInt(i3, f7, k);
      colorSlot = Theme.foreground();
      int k3 = Theme.background();
      int j3 = colorSlot;
      int i1 = AnimatedInt.getIntByIntFloatInt(k3, f7, j3);
      float f45 = 3.0F;
      float f44 = 1.0F;
      float f43 = 0.0F;
      int l3 = 436207616;
      float f42 = 0.0F;
      byte b1 = 0;
      float f41 = 6.0F;
      float f40 = 6.0F;
      float f39 = 6.0F;
      float f38 = 6.0F;
      float f37 = 18.0F;
      float f36 = 70.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f36, l3, l, f5, f6, f45, f38, f37, f44, matrix4f2, value, b1, f40, f42, f39, f41, f43
      );
      HotkeySetting hotkeysetting = this.swapWheelView.getCellBind(count);
      String s = flag1 ? "..." : (hotkeysetting != null ? hotkeysetting.getText2() : "None");
      float f8 = TextShader.getFloatByStringFloat(s, 12.0F);
      float f64 = f5 + (70.0F - f8) / 2.0F;
      float f65 = f6 + 3.0F;
      float f48 = 12.0F;
      float f47 = f65;
      float f46 = f64;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f47, f46, i1, f48, value, s, matrix4f2);
      double d7 = value5;
      double d10 = value2;
      float f50 = 18.0F;
      float f49 = 20.0F;
      double d5 = d10;
      double d4 = d7;
      boolean flag3 = isFloatFloatDoubleFloatFloatDouble(f4, f6, d5, f50, f49, d4);
      this.tweenArray4[count].setFloat2(flag3 ? 1.0F : 0.0F);
      colorSlot = Theme.background();
      float f51 = this.tweenArray4[count].getFloat();
      int i4 = colorSlot;
      int j1 = AnimatedInt.getIntByIntFloatInt(j, f51, i4);
      float f61 = 3.0F;
      float f60 = 1.0F;
      float f59 = 0.0F;
      int j4 = 436207616;
      float f58 = 0.0F;
      byte b2 = 0;
      float f57 = 6.0F;
      float f56 = 6.0F;
      float f55 = 6.0F;
      float f54 = 6.0F;
      float f53 = 18.0F;
      float f52 = 20.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f52, j4, j1, f4, f6, f61, f54, f53, f60, matrix4f2, value, b2, f56, f58, f55, f57, f59
      );
      float f9 = f4 + 5.0F;
      float f10 = f6 + 4.0F;
      float f11 = this.tweenArray5[count].getFloat();
      Matrix4f matrix4f = matrix4f2;
      if (f11 > 1.0E-4F && f11 < 0.9999F) {
         float f12 = f11 * (float) (Math.PI * 2);
         float f13 = f9 + 5.0F;
         float f14 = f10 + 5.0F;
         matrix4f = new Matrix4f(matrix4f2).translate(f13, f14, 0.0F).rotateZ(f12).translate(-f13, -f14, 0.0F);
      }

      CategoryType categorytype2 = CategoryType.RESET;
      int k4 = Theme.foreground();
      float f63 = 10.0F;
      float f62 = 10.0F;
      CategoryType categorytype1 = categorytype2;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, k4, matrix4f, f10, categorytype1, f63, f9, f62);
   }

   private void onFloatFloatFloatMatrix4fPlayerInventoryFloatFloatInt(
      float value, float value2, float value3, Matrix4f matrix4f, PlayerInventory playerInventory, float value4, float value5, int count
   ) {
      double d2 = value4;
      double d3 = value;
      float f3 = 26.0F;
      float f2 = 26.0F;
      double d1 = d3;
      double d0 = d2;
      boolean flag = isFloatFloatDoubleFloatFloatDouble(value5, value3, d1, f3, f2, d0);
      if (flag) {
         this.value244 = count;
      }

      this.tweenArray2[count].setFloat2(flag ? 1.0F : 0.0F);
      float f = this.tweenArray2[count].getFloat();
      int j1 = Theme.surface();
      int k1 = Theme.border();
      float f4 = f * 0.7F;
      int k = k1;
      int j = j1;
      int i = AnimatedInt.getIntByIntFloatInt(k, f4, j);
      int l1 = Theme.border();
      float f8 = 1.0F;
      int l = l1;
      float f7 = 6.0F;
      float f6 = 26.0F;
      float f5 = 26.0F;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f8, f5, value2, matrix4f, value3, i, f7, value5, l, f6);
      if (playerInventory != null) {
         ItemStack itemstack = playerInventory.getStack(count);
         if (itemstack != null && !itemstack.isEmpty()) {
            float f15 = value5 + 5.0F;
            float f16 = value3 + 5.0F;
            float f11 = 16.0F;
            float f10 = f16;
            float f9 = f15;
            ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f11, value2, f9, itemstack, matrix4f, f10);
            if (itemstack.getCount() > 1) {
               String s = String.valueOf(itemstack.getCount());
               float f1 = TextShader.getFloatByStringFloat(s, 12.0F);
               f15 = value5 + 26.0F - f1 - 3.0F;
               f16 = value3 + 26.0F - 12.0F - 2.0F;
               int i1 = Theme.foreground();
               float f14 = 12.0F;
               float f13 = f16;
               float f12 = f15;
               TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f13, f12, i1, f14, value2, s, matrix4f);
            }
         }
      }
   }

   private void onFloatFloatIntFloatMatrix4fFloatFloat(float value, float value2, int count, float value3, Matrix4f matrix4f, float value4, float value5) {
      int l = Theme.elevated();
      int i1 = Theme.border();
      float f6 = 1.0F;
      int k = i1;
      int j = l;
      float f5 = 8.0F;
      float f4 = 204.0F;
      float f3 = 254.0F;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f6, f3, value4, matrix4f, value, j, f5, value5, k, f4);
      float f = value5 + 14.0F;
      float f1 = value + 14.0F;

      for (int i = 0; i < 6; i++) {
         float f2 = f1 + i * 30.0F;
         boolean flag = i < count;
         this.onIntFloatFloatBooleanMatrix4fFloatFloatFloat(i, value4, value3, flag, matrix4f, f, f2, value2);
      }
   }

   private void onFloatFloatFloatMatrix4fFloatFloat(float value, float value2, float value3, Matrix4f matrix4f, float value4, float value5) {
      int k3 = Theme.elevated();
      int l3 = Theme.border();
      float f8 = 1.0F;
      int i1 = l3;
      int l = k3;
      float f7 = 8.0F;
      float f6 = 184.0F;
      float f5 = 302.0F;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f8, f5, value5, matrix4f, value4, l, f7, value3, i1, f6);
      float f = value3 + 14.0F;
      float f1 = value4 + 14.0F;
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      PlayerInventory playerinventory = minecraftclient.player != null ? minecraftclient.player.getInventory() : null;
      this.value244 = -1;

      for (int i = 9; i < 36; i++) {
         int j = (i - 9) % 9;
         int k = (i - 9) / 9;
         float f2 = f + j * 31.0F;
         float f3 = f1 + k * 31.0F;
         this.onFloatFloatFloatMatrix4fPlayerInventoryFloatFloatInt(value, value5, f3, matrix4f, playerinventory, value2, f2, i);
      }

      float f25 = f1 + 93.0F + 8.0F;

      for (int k2 = 0; k2 < 9; k2++) {
         float f27 = f + k2 * 31.0F;
         this.onFloatFloatFloatMatrix4fPlayerInventoryFloatFloatInt(value, value5, f25, matrix4f, playerinventory, value2, f27, k2);
      }

      float f26 = f25 + 26.0F + 8.0F;
      ItemStack itemstack = minecraftclient.player != null ? minecraftclient.player.getOffHandStack() : ItemStack.EMPTY;
      double d2 = value2;
      double d3 = value;
      float f10 = 26.0F;
      float f9 = 26.0F;
      double d1 = d3;
      double d0 = d2;
      boolean flag = isFloatFloatDoubleFloatFloatDouble(f, f26, d1, f10, f9, d0);
      if (flag) {
         this.value244 = 40;
      }

      this.tween5.setFloat2(flag ? 1.0F : 0.0F);
      int i3 = Theme.surface();
      int j3 = Theme.border();
      float f11 = this.tween5.getFloat() * 0.7F;
      int k1 = j3;
      int j1 = i3;
      int l2 = AnimatedInt.getIntByIntFloatInt(k1, f11, j1);
      l3 = Theme.border();
      float f15 = 1.0F;
      int l1 = l3;
      float f14 = 6.0F;
      float f13 = 26.0F;
      float f12 = 26.0F;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f15, f12, value5, matrix4f, f26, l2, f14, f, l1, f13);
      if (itemstack != null && !itemstack.isEmpty()) {
         float f30 = f + 5.0F;
         float f31 = f26 + 5.0F;
         float f18 = 16.0F;
         float f17 = f31;
         float f16 = f30;
         ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f18, value5, f16, itemstack, matrix4f, f17);
         if (itemstack.getCount() > 1) {
            String s = String.valueOf(itemstack.getCount());
            float f4 = TextShader.getFloatByStringFloat(s, 12.0F);
            f30 = f + 26.0F - f4 - 3.0F;
            f31 = f26 + 26.0F - 12.0F - 2.0F;
            int i2 = Theme.foreground();
            float f21 = 12.0F;
            float f20 = f31;
            float f19 = f30;
            TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f20, f19, i2, f21, value5, s, matrix4f);
         }
      }

      float f28 = f + 26.0F + 10.0F;
      float f29 = f26 + 6.0F;
      int j2 = Theme.mutedFg();
      float f24 = 14.0F;
      float f23 = f29;
      float f22 = f28;
      String s1 = "В левой руке";
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f23, f22, j2, f24, value5, s1, matrix4f);
   }

   @Override
   public void update2() {
      super.update2();
      this.update11();
   }
}
