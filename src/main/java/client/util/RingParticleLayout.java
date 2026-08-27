package client.util;

import client.api.ParticleLayout;
import client.enums.TargetEffect;
import client.render.ParticleBuffer;

public class RingParticleLayout implements ParticleLayout {
   @Override
   public void frame(ParticleBuffer particleBuffer, float[] valueArray, float value, int count) {
      valueArray[count] = TargetEffect.getFloatByFloat4(TargetEffect.getFloatByFloat(value + TargetEffect.getFloatByIntFloat(11, value * 0.7F) * 0.05F)) * particleBuffer.value8;
      valueArray[count + 1] = value * 3.0F + TargetEffect.getFloatByIntFloat(12, value * 0.6F) * 3.0F;
   }

   @Override
   public void place(int count, float[] valueArray, float[] valueArray2, int count2, ParticleBuffer particleBuffer) {
      float f = (float) (Math.PI * 2) * count / particleBuffer.value11;
      float f1 = particleBuffer.value7 * (1.0F + 0.045F * TargetEffect.getFloatByFloat3(f * 2.0F + valueArray[count2 + 1]));
      valueArray2[0] = TargetEffect.getFloatByFloat2(f) * f1;
      valueArray2[1] = valueArray[count2];
      valueArray2[2] = TargetEffect.getFloatByFloat3(f) * f1;
   }
}
