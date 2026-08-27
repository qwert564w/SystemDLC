package client.gui.widget;

import client.api.MatrixDrawCall;
import client.module.visual.Enhancer;
import net.minecraft.client.util.math.MatrixStack;

public final class LayerStack {
   private static final MatrixDrawCall matrixDrawCall = LayerStack::render;
   public static final DrawLayer drawLayer = getDrawLayerByMatrixDrawCall(LayerStack::render4);
   public static final DrawLayer drawLayer2 = getDrawLayerByMatrixDrawCall(LayerStack::render3);
   public static final DrawLayer drawLayer3 = getDrawLayerByMatrixDrawCall(LayerStack::render2);
   public static final DrawLayer drawLayer4 = getDrawLayerByMatrixDrawCall(LayerStack::render5);
   public static final DrawLayer drawLayer5 = new DrawLayer(matrixDrawCall, matrixDrawCall, true);

   private static void render(MatrixStack matrixStack, int count, int count2, int count3, float value) {
   }

   private static void render2(MatrixStack matrixStack, int count, int count2, int count3, float value) {
      matrixStack.translate(count, count2, 0.0F);
      matrixStack.scale(1.0F, value, 1.0F);
      matrixStack.translate(-count, -count2, 0.0F);
   }

   private static void render3(MatrixStack matrixStack, int count, int count2, int count3, float value) {
      float f = count2 + count3;
      matrixStack.translate(count, f, 0.0F);
      matrixStack.scale(1.0F, value, 1.0F);
      matrixStack.translate(-count, -f, 0.0F);
   }

   private static void render4(MatrixStack matrixStack, int count, int count2, int count3, float value) {
      float f = count2 + count3 / 2.0F;
      matrixStack.translate(count, f, 0.0F);
      matrixStack.scale(value, value, 1.0F);
      matrixStack.translate(-count, -f, 0.0F);
   }

   private static void render5(MatrixStack matrixStack, int count, int count2, int count3, float value) {
      matrixStack.translate(count, count2, 0.0F);
      matrixStack.scale(value, 1.0F, 1.0F);
      matrixStack.translate(-count, -count2, 0.0F);
   }

   public static int getIntByFloatInt(float value, int count) {
      value = Math.max(value, 0.03F);
      int i = count >> 24 & 0xFF;
      if (i == 0) {
         i = 255;
      }

      return count & 16777215 | Math.round(i * value) << 24;
   }

   public static float getFloatByFloat(float value) {
      float f = 1.0F - value;
      return 1.0F - f * f * f;
   }

   private static DrawLayer getDrawLayerByMatrixDrawCall(MatrixDrawCall matrixDrawCall) {
      return new DrawLayer(matrixDrawCall, matrixDrawCall, false);
   }

   public static DrawLayer getDrawLayerByString(String text) {
      if (text == null) {
         return drawLayer;
      } else if (text.equals(Enhancer.text8)) {
         return drawLayer2;
      } else if (text.equals(Enhancer.text9)) {
         return drawLayer3;
      } else if (text.equals(Enhancer.text10)) {
         return drawLayer4;
      } else {
         return text.equals(Enhancer.text11) ? drawLayer5 : drawLayer;
      }
   }
}
