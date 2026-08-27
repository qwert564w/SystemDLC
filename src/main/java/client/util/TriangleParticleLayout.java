package client.util;

import client.api.ParticleLayout;
import client.enums.TargetEffect;
import client.render.ParticleBuffer;

public class TriangleParticleLayout implements ParticleLayout {
   @Override
   public void frame(ParticleBuffer particleBuffer, float[] valueArray, float value, int count) {
      valueArray[count] = TargetEffect.getFloatByFloat4(TargetEffect.getFloatByFloat(value + TargetEffect.getFloatByIntFloat(21, value * 0.7F) * 0.05F)) * particleBuffer.value8;
      valueArray[count + 1] = value * (float) (Math.PI * 2) * 2.2F + TargetEffect.getFloatByIntFloat(22, value * 0.9F) * 0.65F;
   }

   @Override
   public void place(int count, float[] valueArray, float[] valueArray2, int count2, ParticleBuffer particleBuffer) {
      int i = Math.max(4, particleBuffer.value11 / 3);
      int j = count / i;
      float f = (float)(count % i) / i;
      float f1 = valueArray[count2 + 1] + j * (float) (Math.PI * 2) / 3.0F;
      float f2 = f1 + (float) (Math.PI * 2.0 / 3.0);
      float f3 = TargetEffect.getFloatByFloat2(f1) * particleBuffer.value7;
      float f4 = TargetEffect.getFloatByFloat3(f1) * particleBuffer.value7;
      valueArray2[0] = f3 + (TargetEffect.getFloatByFloat2(f2) * particleBuffer.value7 - f3) * f;
      valueArray2[1] = valueArray[count2];
      valueArray2[2] = f4 + (TargetEffect.getFloatByFloat3(f2) * particleBuffer.value7 - f4) * f;
   }
}
