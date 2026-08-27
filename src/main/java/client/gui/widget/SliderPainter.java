package client.gui.widget;

import client.render.ShapeShader;
import java.util.List;
import org.joml.Matrix4f;

public final class SliderPainter {
   private static final float value = 1.0F;
   private static final float value2 = 0.5F;
   private static final float value3 = 8.0F;

   private static void onMatrix4fFloatIntFloatFloatFloat(Matrix4f matrix4f, float value, int count, float value2, float value3, float value4) {
      if (Math.abs(value3 - value) <= 0.5F) {
         float f = value - 0.5F;
         onFloatFloatIntFloatMatrix4f(f, value4, count, value2, matrix4f);
      }
   }

   private static void onFloatFloatFloatMatrix4fInt(float value, float value2, float value3, Matrix4f matrix4f, int count) {
      float f2 = 0.0F;
      float f1 = 1.0F;
      float f = 0.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f2, value2, count, matrix4f, value3, f1, value, f);
   }

   private static void onFloatFloatIntFloatMatrix4f(float value, float value2, int count, float value3, Matrix4f matrix4f) {
      float f2 = 0.0F;
      float f1 = 1.0F;
      float f = 0.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f2, f, count, matrix4f, f1, value2, value3, value);
   }

   private static void onFloatIntFloatFloatMatrix4fFloat(float value, int count, float value2, float value3, Matrix4f matrix4f, float value4) {
      if (Math.abs(value2 - value) <= 0.5F) {
         float f = value - 0.5F;
         onFloatFloatFloatMatrix4fInt(value3, f, value4, matrix4f, count);
      }
   }

   private static boolean isRenderElement(RenderElement renderElement) {
      return renderElement.isFlag() && renderElement.check18();
   }

   public static void onFloatListMatrix4fIntFloatFloatRenderElement(float value, List<RenderElement> list, Matrix4f matrix4f, int count, float value2, float value3, RenderElement renderElement) {
      if (renderElement != null) {
         float f = renderElement.getFloat21();
         float f1 = renderElement.getFloat12();
         float f2 = renderElement.getFloat17();
         float f3 = f2 + f;
         float f4 = f2 + f / 2.0F;
         float f5 = renderElement.getFloat13();
         float f6 = f5 + f1;
         float f7 = f5 + f1 / 2.0F;
         float f14 = 0.0F;
         onFloatIntFloatFloatMatrix4fFloat(f14, count, f2, value, matrix4f, value3);
         onFloatIntFloatFloatMatrix4fFloat(value2, count, f3, value, matrix4f, value3);
         float f15 = value2 / 2.0F;
         onFloatIntFloatFloatMatrix4fFloat(f15, count, f4, value, matrix4f, value3);
         float f16 = 0.0F;
         onMatrix4fFloatIntFloatFloatFloat(matrix4f, f16, count, value, f5, value2);
         onMatrix4fFloatIntFloatFloatFloat(matrix4f, value3, count, value, f6, value2);
         float f17 = value3 / 2.0F;
         onMatrix4fFloatIntFloatFloatFloat(matrix4f, f17, count, value, f7, value2);

         for (RenderElement renderelement : list) {
            if (renderelement != renderElement && isRenderElement(renderelement)) {
               float f8 = renderelement.getFloat17();
               float f9 = f8 + renderelement.getFloat21();
               float f10 = f8 + renderelement.getFloat21() / 2.0F;
               float f11 = renderelement.getFloat13();
               float f12 = f11 + renderelement.getFloat12();
               float f13 = f11 + renderelement.getFloat12() / 2.0F;
               onFloatIntFloatFloatMatrix4fFloat(f8, count, f2, value, matrix4f, value3);
               onFloatIntFloatFloatMatrix4fFloat(f9, count, f3, value, matrix4f, value3);
               onFloatIntFloatFloatMatrix4fFloat(f10, count, f4, value, matrix4f, value3);
               float f18 = f9 + 8.0F;
               onFloatIntFloatFloatMatrix4fFloat(f18, count, f2, value, matrix4f, value3);
               float f19 = f8 - 8.0F;
               onFloatIntFloatFloatMatrix4fFloat(f19, count, f3, value, matrix4f, value3);
               onMatrix4fFloatIntFloatFloatFloat(matrix4f, f11, count, value, f5, value2);
               onMatrix4fFloatIntFloatFloatFloat(matrix4f, f12, count, value, f6, value2);
               onMatrix4fFloatIntFloatFloatFloat(matrix4f, f13, count, value, f7, value2);
               float f20 = f12 + 8.0F;
               onMatrix4fFloatIntFloatFloatFloat(matrix4f, f20, count, value, f5, value2);
               float f21 = f11 - 8.0F;
               onMatrix4fFloatIntFloatFloatFloat(matrix4f, f21, count, value, f6, value2);
            }
         }
      }
   }
}
