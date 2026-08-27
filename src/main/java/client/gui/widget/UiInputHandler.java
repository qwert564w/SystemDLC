package client.gui.widget;

import client.api.Theme;
import client.api.UiMetrics;
import client.module.Feature;
import client.render.TextShader;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

public final class UiInputHandler implements UiMetrics {
   private final UiContext uiContext;
   private final KeyHintBar keyHintBar;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private HudEditorOverlay hudEditorOverlay;
   private boolean flag4;
   private ContextMenu contextMenu;
   private ProfileBar profileBar;
   private boolean flag5;

   public UiInputHandler(UiContext uiContext2, KeyHintBar keyHintBar2) {
      this.uiContext = uiContext2;
      this.keyHintBar = keyHintBar2;
   }

   private void onFloatFloat(float value, float value2) {
      if (!this.contextMenu.isFlag4()) {
         boolean flagx = this.profileBar != null && this.profileBar.check();
         boolean flag1 = isInt(1);
         if (flag1 && !this.flag3) {
            if (flagx) {
               this.profileBar.update6();
            } else {
               this.update();
            }

            this.flag3 = flag1;
         } else {
            boolean flag2x;
            boolean flag3x;
            boolean flag4x;
            boolean flag6;
            label74: {
               this.flag3 = flag1;
               flag2x = isInt(0);
               flag3x = this.contextMenu.isFloatFloat(value, value2);
               flag4x = this.profileBar != null && this.profileBar.isFloatFloat(value2, value);
               if (flagx) {
                  double d23 = value2;
                  double d1 = value;
                  double d0 = d23;
                  if (this.profileBar.isDoubleDouble2(d1, d0)) {
                     flag6 = true;
                     break label74;
                  }
               }

               flag6 = false;
            }

            label97: {
               boolean flag5x = flag6;
               if (flag2x && !this.flag5) {
                  if (flag5x) {
                     double d15 = value2;
                     double d19 = value;
                     byte b0 = 0;
                     double d3 = d19;
                     double d2 = d15;
                     this.profileBar.isIntDoubleDouble(b0, d2, d3);
                  } else if (flag3x) {
                     double d16 = value2;
                     double d20 = value;
                     byte b1 = 0;
                     double d5 = d20;
                     double d4 = d16;
                     this.contextMenu.isIntDoubleDouble(b1, d4, d5);
                  } else if (flag4x) {
                     double d17 = value2;
                     double d21 = value;
                     byte b2 = 0;
                     double d7 = d21;
                     double d6 = d17;
                     this.profileBar.isIntDoubleDouble(b2, d6, d7);
                  } else if (flagx) {
                     this.profileBar.update6();
                  } else {
                     if (this.profileBar == null) {
                        break label97;
                     }

                     double d18 = value2;
                     double d22 = value;
                     byte b3 = 0;
                     double d9 = d22;
                     double d8 = d18;
                     if (!this.profileBar.isIntDoubleDouble(b3, d8, d9)) {
                        break label97;
                     }
                  }
               } else if (flag2x && this.profileBar != null) {
                  double d14 = value2;
                  double d24 = value;
                  double d13 = 0.0;
                  double d12 = 0.0;
                  byte b4 = 0;
                  double d11 = d24;
                  double d10 = d14;
                  this.profileBar.isDoubleDoubleIntDoubleDouble(d11, d10, b4, d12, d13);
               } else if (this.flag5 && this.profileBar != null) {
                  this.profileBar.isDoubleDoubleInt(value2, value, 0);
               }

               this.flag5 = flag2x;
               return;
            }

            this.update();
            this.flag5 = true;
         }
      }
   }

   private void update() {
      if (this.contextMenu != null) {
         this.contextMenu.update3();
      }

      if (this.profileBar != null) {
         this.profileBar.update7();
      }

      this.update2();
   }

   private void onFloatFloatDrawContextMatrix4fFloat(float value, float value2, DrawContext drawContext, Matrix4f matrix4f, float value3) {
      if (this.contextMenu != null) {
         this.contextMenu.onFloatFloatMatrix4fDrawContextFloat(value3, value2, matrix4f, drawContext, value);
         if (this.profileBar != null) {
            this.profileBar.onMatrix4fDrawContextFloatFloatFloat(matrix4f, drawContext, value2, value3, value);
            this.profileBar.onFloatFloatFloatMatrix4f2(value, value3, value2, matrix4f);
         }

         if (this.contextMenu.check()) {
            this.contextMenu.update2();
            this.contextMenu = null;
            if (this.profileBar != null) {
               this.profileBar.update2();
               this.profileBar = null;
            }
         }
      }
   }

   private void onFloatFloat2(float value, float value2) {
      if (this.hudEditorOverlay != null) {
         this.onFloatFloat5(value, value2);
      } else if (this.contextMenu != null) {
         this.onFloatFloat(value, value2);
      } else {
         boolean flagx = isInt(0);
         boolean flag1 = this.flag2;
         this.keyHintBar.onFloatBooleanFloatBoolean(value2, flag1, value, flagx);
         this.flag2 = flagx;
         this.onFloatFloat3(value2, value);
      }
   }

   public boolean isIntIntInt(int count, int count2, int count3) {
      if (!this.flag) {
         return false;
      } else {
         return this.hudEditorOverlay != null
            ? this.hudEditorOverlay.isIntIntInt(count2, count, count3)
            : this.profileBar != null && this.profileBar.isIntIntInt(count2, count, count3);
      }
   }

   private static boolean check() {
      return Feature.mc != null && Feature.mc.currentScreen instanceof ChatScreen;
   }

   public boolean isFlag() {
      return this.flag;
   }

   private void update2() {
      if (Feature.mc != null && Feature.mc.getWindow() != null) {
         boolean flagx = isInt(0);
         boolean flag1 = isInt(1);
         this.flag2 = flagx;
         this.flag4 = flagx;
         this.flag5 = flagx;
         this.flag3 = flag1;
      }
   }

   private void onFloatFloat3(float value, float value2) {
      boolean flagx = isInt(1);
      if (flagx && !this.flag3) {
         RenderElement renderelement = this.getRenderElementByFloatFloat(value, value2);
         if (renderelement != null) {
            this.setRenderElement(renderelement);
         } else {
            this.onFloatFloat4(value2, value);
         }
      }

      this.flag3 = flagx;
   }

   private void onFloatFloat4(float value, float value2) {
      if (this.contextMenu != null) {
         this.update();
      }

      this.contextMenu = new ContextMenu(new ArrayList(this.uiContext.getList2()));
      this.profileBar = new ProfileBar();
      this.onFloatFloat7(value2, value);
      this.update2();
   }

   private void onFloatFloat5(float value, float value2) {
      if (!this.hudEditorOverlay.isFlag5()) {
         boolean flagx = isInt(1);
         if (flagx && !this.flag3) {
            if (this.hudEditorOverlay.check()) {
               this.hudEditorOverlay.update3();
            } else {
               this.update3();
            }

            this.flag3 = flagx;
         } else {
            this.flag3 = flagx;
            boolean flag1 = isInt(0);
            boolean flag2x = this.hudEditorOverlay.check();
            if (flag1 && !this.flag4) {
               boolean flag3x = this.hudEditorOverlay.isFloatFloat(value2, value);
               boolean flag4x = flag2x && this.hudEditorOverlay.isDoubleDouble3(value2, value);
               if (!flag3x && !flag4x) {
                  if (!flag2x) {
                     this.update3();
                     return;
                  }

                  this.hudEditorOverlay.update3();
               } else {
                  double d6 = value2;
                  double d7 = value;
                  byte b0 = 0;
                  double d1 = d7;
                  double d0 = d6;
                  this.hudEditorOverlay.isIntDoubleDouble(b0, d0, d1);
               }
            } else if (flag1) {
               double d8 = value2;
               double d9 = value;
               double d5 = 0.0;
               double d4 = 0.0;
               byte b1 = 0;
               double d3 = d9;
               double d2 = d8;
               this.hudEditorOverlay.isDoubleDoubleIntDoubleDouble(d3, d2, b1, d4, d5);
            } else if (this.flag4) {
               this.hudEditorOverlay.isDoubleDoubleInt(value2, value, 0);
            }

            this.flag4 = flag1;
         }
      }
   }

   public boolean check2() {
      return this.hudEditorOverlay != null || this.contextMenu != null;
   }

   public void onFloatFloatDrawContextMatrix4fFloat2(float value, float value2, DrawContext drawContext, Matrix4f matrix4f, float value3) {
      if (this.flag) {
         this.onFloatFloatMatrix4fFloat(value, value2, matrix4f, value3);
      }

      this.onFloatFloatMatrix4fFloatDrawContext(value2, value, matrix4f, value3, drawContext);
      this.onFloatFloatDrawContextMatrix4fFloat(value3, value, drawContext, matrix4f, value2);
   }

   private static boolean isInt(int count) {
      return Feature.mc != null && Feature.mc.getWindow() != null ? GLFW.glfwGetMouseButton(Feature.mc.getWindow().getHandle(), count) == 1 : false;
   }

   public boolean isFloatDoubleFloat(float value, double value2, float value3) {
      if (!this.flag) {
         return false;
      } else if (this.hudEditorOverlay != null) {
         double d4 = value;
         double d1 = value3;
         double d0 = d4;
         return this.hudEditorOverlay.isDoubleDoubleDouble(value2, d0, d1);
      } else if (this.profileBar != null) {
         double d5 = value;
         double d3 = value3;
         double d2 = d5;
         return this.profileBar.isDoubleDoubleDouble(value2, d2, d3);
      } else {
         return false;
      }
   }

   public boolean isCharInt(char symbol, int count) {
      if (!this.flag) {
         return false;
      } else {
         return this.hudEditorOverlay != null ? this.hudEditorOverlay.isIntChar(count, symbol) : this.profileBar != null && this.profileBar.isIntChar(count, symbol);
      }
   }

   public void onFloatFloat6(float value, float value2) {
      boolean flagx = check();
      this.flag = flagx;
      if (!flagx) {
         if (this.hudEditorOverlay != null) {
            this.update3();
         }

         if (this.contextMenu != null) {
            this.update();
         }
      }

      if (flagx) {
         this.onFloatFloat2(value, value2);
      } else if (this.keyHintBar.getRenderElement() != null || this.flag2) {
         this.keyHintBar.update();
         this.flag2 = false;
         this.flag3 = false;
      }
   }

   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (!this.flag) {
         return false;
      } else {
         return this.hudEditorOverlay != null
            ? this.hudEditorOverlay.isIntIntInt2(count, count3, count2)
            : this.profileBar != null && this.profileBar.isIntIntInt2(count, count3, count2);
      }
   }

   private void update3() {
      if (this.hudEditorOverlay != null) {
         this.hudEditorOverlay.update7();
      }

      this.update2();
   }

   private void setRenderElement(RenderElement renderElement) {
      if (this.hudEditorOverlay != null) {
         this.update3();
      }

      RenderElement renderelement = renderElement.getRenderElement();
      this.hudEditorOverlay = new HudEditorOverlay(renderelement);
      HudEditorOverlay hudeditoroverlay = this.hudEditorOverlay;
      this.onRenderElementHudEditorOverlay(renderElement, hudeditoroverlay);
      this.update2();
   }

   private void onRenderElementHudEditorOverlay(RenderElement renderElement, HudEditorOverlay hudEditorOverlay) {
      float f = hudEditorOverlay.getValue237();
      float f1 = hudEditorOverlay.getValue238();
      float f2 = this.uiContext.getFloat3();
      float f3 = this.uiContext.getValue238();
      float f4 = renderElement.getFloat17();
      float f5 = f4 + renderElement.getFloat21();
      float f6 = f5 + 8.0F;
      if (f6 + f > f2) {
         f6 = f4 - f - 8.0F;
      }

      if (f6 < 8.0F) {
         f6 = (f2 - f) / 2.0F;
      }

      float f7 = renderElement.getFloat13();
      if (f7 + f1 > f3 - 8.0F) {
         f7 = Math.max(8.0F, f3 - f1 - 8.0F);
      }

      hudEditorOverlay.onFloatFloat2(f7, f6);
   }

   private RenderElement getRenderElementByFloatFloat(float value, float value2) {
      List list = this.uiContext.getList2();

      for (int i = list.size() - 1; i >= 0; i--) {
         RenderElement renderelement = (RenderElement)list.get(i);
         if (KeyHintBar.isRenderElement(renderelement) && renderelement.isFloatFloat(value, value2)) {
            return renderelement;
         }
      }

      return null;
   }

   private void onFloatFloatMatrix4fFloat(float value, float value2, Matrix4f matrix4f, float value3) {
      if (this.hudEditorOverlay == null) {
         RenderElement renderelement = this.getRenderElementByFloatFloat(value2, value3);
         if (renderelement != null) {
            String s = "RMB - Settings";
            float f = 12.0F;
            float f1 = TextShader.getFloatByStringFloat(s, f);
            float f2 = renderelement.getFloat22();
            float f3 = f2 + (renderelement.getFloat24() - f1) / 2.0F;
            float f4 = renderelement.getValue261() - 6.0F - f;
            int i = Theme.foreground();
            TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f4, f3, i, f, value, s, matrix4f);
         }
      }
   }

   private void onFloatFloatMatrix4fFloatDrawContext(float value, float value2, Matrix4f matrix4f, float value3, DrawContext drawContext) {
      if (this.hudEditorOverlay != null) {
         this.hudEditorOverlay.render(drawContext, value, value3, value2, matrix4f);
         this.hudEditorOverlay.onFloatFloatFloatMatrix4f2(value3, value, value2, matrix4f);
         if (this.hudEditorOverlay.check2()) {
            this.hudEditorOverlay.update2();
            this.hudEditorOverlay = null;
         }
      }
   }

   private void onFloatFloat7(float value, float value2) {
      float f = this.uiContext.getFloat3();
      float f1 = this.uiContext.getValue238();
      float f2 = this.contextMenu.getValue237();
      float f3 = this.contextMenu.getValue238();
      float f4 = this.profileBar.getValue237();
      float f5 = this.profileBar.getFloat2();
      float f6 = Math.max(f3, f5);
      float f7;
      float f8;
      if (value + f2 + 8.0F + f4 + 8.0F <= f) {
         f7 = Math.max(8.0F, value);
         f8 = f7 + f2 + 8.0F;
      } else {
         float f10 = Math.min(f - f2 - 8.0F, value - f2);
         f7 = Math.max(8.0F + f4 + 8.0F, f10);
         f8 = f7 - 8.0F - f4;
      }

      float f9 = Math.clamp(value2, 8.0F, f1 - f6 - 8.0F);
      this.contextMenu.onFloatFloat2(f9, f7);
      this.profileBar.onFloatFloat2(f9, f8);
   }
}
