package client.render;

import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public final class MatrixUtil {
   private MatrixUtil() {
   }

   public static Matrix4f getMatrix4fByFloatFloatFloatFloatMatrixStack(float value, float value2, float value3, float value4, MatrixStack matrixStack) {
      float f = 0.0F;
      return getMatrix4fByFloatFloatFloatFloatMatrixStackFloatFloat(value4, f, value, value3, matrixStack, value2, value);
   }

   public static Matrix4f getMatrix4fByFloatFloatFloatFloatMatrixStackFloat(float value, float value2, float value3, float value4, MatrixStack matrixStack, float value5) {
      float f = 0.0F;
      return getMatrix4fByFloatFloatFloatFloatMatrixStackFloatFloat(value3, f, value5, value4, matrixStack, value, value2);
   }

   public static Matrix4f getMatrix4fByFloatFloatFloatFloatMatrixStackFloatFloat(
      float value, float value2, float value3, float value4, MatrixStack matrixStack, float value5, float value6
   ) {
      matrixStack.push();
      matrixStack.translate(value5 + value2, value + value4, 0.0F);
      matrixStack.scale(value3, value6, 1.0F);
      matrixStack.translate(-value5, -value, 0.0F);
      return matrixStack.peek().getPositionMatrix();
   }
}
