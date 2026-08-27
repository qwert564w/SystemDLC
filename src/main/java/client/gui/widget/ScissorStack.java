package client.gui.widget;

import client.data.RectI;
import client.gui.screen.ClickGuiScreen;
import client.module.Feature;
import client.render.TextShader;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayDeque;
import java.util.Deque;

public final class ScissorStack {
   private static final Deque<RectI> deque = new ArrayDeque<>();
   private static float value;
   private static float value2;
   private static float value3 = 1.0F;
   private static float value4;
   private static float value5;
   private static boolean flag;

   private ScissorStack() {
   }

   public static void onFloatFloatFloatFloat(float value6, float value7, float value8, float value9) {
      if (flag) {
         float f = value3;
         float f1 = (value9 - value) * f + value + value4;
         float f2 = (value8 - value2) * f + value2 + value5;
         value9 = f1;
         value8 = f2;
         value6 *= f;
         value7 *= f;
      }

      double d0 = Feature.mc.getWindow().getScaleFactor() * ClickGuiScreen.getValue235();
      int l1 = (int)Math.floor(value9 * d0);
      int i = (int)Math.floor(Feature.mc.getWindow().getFramebufferHeight() - (value8 + value7) * d0);
      int j = Math.max(0, (int)Math.ceil(value6 * d0));
      int k = Math.max(0, (int)Math.ceil(value7 * d0) - 1);
      RectI recti = new RectI(l1, i, j, k);
      if (!deque.isEmpty()) {
         RectI recti1 = deque.peek();
         int l = Math.max(recti1.getValue(), recti.getValue());
         int i1 = Math.max(recti1.getValue2(), recti.getValue2());
         int j1 = Math.min(recti1.getValue() + recti1.getValue3(), recti.getValue() + recti.getValue3());
         int k1 = Math.min(recti1.getValue2() + recti1.getValue4(), recti.getValue2() + recti.getValue4());
         recti = new RectI(l, i1, Math.max(0, j1 - l), Math.max(0, k1 - i1));
      }

      TextShader.update2();
      deque.push(recti);
      RenderSystem.enableScissor(recti.getValue(), recti.getValue2(), recti.getValue3(), recti.getValue4());
   }

   public static void update() {
      if (!deque.isEmpty()) {
         TextShader.update2();
         deque.pop();
         if (deque.isEmpty()) {
            RenderSystem.disableScissor();
         } else {
            RectI recti = deque.peek();
            RenderSystem.enableScissor(recti.getValue(), recti.getValue2(), recti.getValue3(), recti.getValue4());
         }
      }
   }

   public static void update2() {
      flag = false;
      value3 = 1.0F;
      value4 = 0.0F;
      value5 = 0.0F;
   }

   public static void onFloatFloatFloatFloatFloat(float value6, float value7, float value8, float value9, float value10) {
      value = value10;
      value2 = value8;
      value3 = value9;
      value4 = value7;
      value5 = value6;
      flag = true;
   }

   public static void onFloatFloatFloatFloat2(float value, float value2, float value3, float value4) {
      float f = 0.0F;
      onFloatFloatFloatFloatFloat(value2, f, value4, value3, value);
   }
}
