package client.gui.widget;

import client.api.MatrixDrawCall;
import net.minecraft.client.util.math.MatrixStack;

public final class DrawLayer {
   private final MatrixDrawCall matrixDrawCall;
   private final MatrixDrawCall matrixDrawCall2;
   private final boolean flag;

   public DrawLayer(MatrixDrawCall matrixDrawCall3, MatrixDrawCall matrixDrawCall4, boolean flag2) {
      this.matrixDrawCall = matrixDrawCall3;
      this.matrixDrawCall2 = matrixDrawCall4;
      this.flag = flag2;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public float getFloatByFloatLongLong(float value, long time, long time2) {
      if (this.flag) {
         return 1.0F;
      } else {
         float f = Math.min((float)(time2 - time) / value, 1.0F);
         return 0.5F + 0.5F * LayerStack.getFloatByFloat(f);
      }
   }

   public float getFloatByFloat(float value) {
      return LayerStack.getFloatByFloat(value);
   }

   public void onIntMatrixStackIntFloatInt(int count, MatrixStack matrixStack, int count2, float value, int count3) {
      this.matrixDrawCall2.apply(matrixStack, count3, count2, count, value);
   }

   public void onIntFloatIntMatrixStackInt(int count, float value, int count2, MatrixStack matrixStack, int count3) {
      this.matrixDrawCall.apply(matrixStack, count, count3, count2, value);
   }
}
