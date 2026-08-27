package client.enums;

import client.api.ParticleLayout;
import client.render.ParticleBuffer;
import client.util.RingParticleLayout;
import client.util.TriangleParticleLayout;
import java.util.Arrays;
import java.util.List;

public enum TargetEffect {
   RING("Кольцо") {
   public void onParticleBuffer(ParticleBuffer var1) {
      float f = 0.9F;
      ParticleLayout particlelayout = TargetEffect.particleLayout;
      int i = var1.value11;
      TargetEffect.onIntParticleBufferParticleLayoutFloat(i, var1, particlelayout, f);
   }
   },
   TRIANGLE("Треугольник") {
   public void onParticleBuffer(ParticleBuffer var1) {
      int j = Math.max(4, var1.value11 / 3) * 3;
      float f = 0.85F;
      ParticleLayout particlelayout = TargetEffect.particleLayout2;
      int i = j;
      TargetEffect.onIntParticleBufferParticleLayoutFloat(i, var1, particlelayout, f);
   }
   },
   LIGHTNING("Молнии") {
   public void onParticleBuffer(ParticleBuffer var1) {
      float[] afloat = var1.getFloatArrayByInt2(45);
      int[] aint = var1.getIntArrayByInt2(15);
      int[] aint1 = var1.getIntArrayByInt(15);

      for (int i = 0; i < 6; i++) {
         float f = var1.value10 * 8.0F + TargetEffect.getFloatByInt(i * 7717);
         int j = (int)f;
         float f1 = f - j;
         int k = j * 7919 + i * 104729;
         float f2 = 0.1F + TargetEffect.getFloatByInt(k) * 0.8F;
         float f3 = TargetEffect.getFloatByInt(k + 3) * (float) (Math.PI * 2) + f1 * 0.5F;
         float f4 = TargetEffect.getFloatByInt(k + 5) < 0.5F ? -1.0F : 1.0F;
         float f5 = 1.6F + TargetEffect.getFloatByInt(k + 7) * 2.0F;
         float f6 = (0.35F + 0.65F * TargetEffect.getFloatByFloat3((float) Math.PI * f1)) * var1.getFloatByFloat(f2);

         for (int l = 0; l <= 14; l++) {
            float f7 = l / 14.0F;
            float f8 = TargetEffect.getFloatByInt(k + l * 53) - 0.5F + (TargetEffect.getFloatByInt(k + l * 191) - 0.5F) * 0.45F;
            float f9 = TargetEffect.getFloatByInt(k + l * 97) - 0.5F + (TargetEffect.getFloatByInt(k + l * 313) - 0.5F) * 0.45F;
            float f10 = TargetEffect.getFloatByInt(k + l * 17) - 0.5F + (TargetEffect.getFloatByInt(k + l * 421) - 0.5F) * 0.45F;
            float f11 = f3 + f4 * f5 * f7 + f8 * 0.32F;
            float f12 = var1.value7 * (0.9F + f9 * 0.42F + TargetEffect.getFloatByIntFloat(k + l, var1.value10 * 55.0F) * 0.06F);
            float f13 = (f2 + f10 * 0.2F + TargetEffect.getFloatByIntFloat(k + l * 5, var1.value10 * 45.0F) * 0.025F) * var1.value8;
            afloat[l * 3] = TargetEffect.getFloatByFloat2(f11) * f12;
            afloat[l * 3 + 1] = f13;
            afloat[l * 3 + 2] = TargetEffect.getFloatByFloat3(f11) * f12;
            float f14 = 0.25F + 0.75F * TargetEffect.getFloatByFloat3((float) Math.PI * f7);
            aint[l] = var1.getIntByFloat(f6 * 0.85F * f14);
            aint1[l] = var1.getIntByFloat(f6 * 0.2F * f14);
         }

         float f29 = var1.value9 * 1.15F;
         boolean flag = false;
         float f18 = f29;
         byte b0 = 15;
         var1.onFloatFloatArrayIntArrayBooleanInt(f18, afloat, aint1, flag, b0);
         f29 = var1.value9 * 0.34F;
         boolean flag1 = false;
         float f19 = f29;
         byte b1 = 15;
         var1.onFloatFloatArrayIntArrayBooleanInt(f19, afloat, aint, flag1, b1);
         int j1 = 3 + (int)(TargetEffect.getFloatByInt(k + 13) * 9.0F);
         float f21 = afloat[j1 * 3];
         float f22 = afloat[j1 * 3 + 1];
         float f23 = afloat[j1 * 3 + 2];
         float f24 = f21 - afloat[(j1 - 1) * 3];
         float f25 = f23 - afloat[(j1 - 1) * 3 + 2];
         float f26 = (float)Math.sqrt(f24 * f24 + f25 * f25);
         if (!(f26 < 1.0E-5F)) {
            float f27 = TargetEffect.getFloatByInt(k + 19) < 0.5F ? -0.9F : 0.9F;
            float f28 = (f24 * TargetEffect.getFloatByFloat2(f27) - f25 * TargetEffect.getFloatByFloat3(f27)) / f26;
            float f15 = (f24 * TargetEffect.getFloatByFloat3(f27) + f25 * TargetEffect.getFloatByFloat2(f27)) / f26;

            for (int i1 = 0; i1 < 4; i1++) {
               float f16 = i1 / 3.0F;
               float f17 = var1.value7 * 0.85F * f16;
               afloat[i1 * 3] = f21 + f28 * f17 + var1.value7 * (TargetEffect.getFloatByInt(k + i1 * 71) - 0.5F) * 0.2F;
               afloat[i1 * 3 + 1] = f22 - var1.value8 * f16 * 0.14F;
               afloat[i1 * 3 + 2] = f23 + f15 * f17 + var1.value7 * (TargetEffect.getFloatByInt(k + i1 * 83) - 0.5F) * 0.2F;
               aint[i1] = var1.getIntByFloat(f6 * 0.5F * (1.0F - f16));
            }

            f29 = var1.value9 * 0.28F;
            boolean flag2 = false;
            float f20 = f29;
            byte b2 = 4;
            var1.onFloatFloatArrayIntArrayBooleanInt(f20, afloat, aint, flag2, b2);
         }
      }
   }
   },
   HELIX("Спираль") {
   public void onParticleBuffer(ParticleBuffer var1) {
      int i = Math.max(28, var1.value11 / 4);
      float f = var1.value10 * (float) (Math.PI * 2) * 0.9F + TargetEffect.getFloatByIntFloat(31, var1.value10 * 0.8F) * 0.55F;
      float f1 = var1.value9 * 0.6F;
      float[] afloat = var1.getFloatArrayByInt2(i * 3);
      int[] aint = var1.getIntArrayByInt2(i);

      for (int j = 0; j < i; j++) {
         float f2 = (float)j / (i - 1);
         float f3 = var1.value7 * (1.0F - 0.12F * f2 + 0.09F * TargetEffect.getFloatByIntFloat(32, var1.value10 * 1.1F + f2 * 3.0F));
         float f4 = f + f2 * 2.2F * (float) (Math.PI * 2);
         afloat[j * 3] = TargetEffect.getFloatByFloat2(f4) * f3;
         afloat[j * 3 + 1] = f2 * var1.value8;
         afloat[j * 3 + 2] = TargetEffect.getFloatByFloat3(f4) * f3;
         aint[j] = var1.getIntByFloat(0.8F * (0.45F + 0.55F * TargetEffect.getFloatByFloat3((float) Math.PI * f2)) * var1.getFloatByFloat(f2));
      }

      boolean flag = false;
      var1.onFloatFloatArrayIntArrayBooleanInt(f1, afloat, aint, flag, i);

      for (int k = 0; k < i; k++) {
         afloat[k * 3] = -afloat[k * 3];
         afloat[k * 3 + 2] = -afloat[k * 3 + 2];
      }

      boolean flag1 = false;
      var1.onFloatFloatArrayIntArrayBooleanInt(f1, afloat, aint, flag1, i);
   }
   },
   ORBIT("Орбиты") {
   public void onParticleBuffer(ParticleBuffer var1) {
      int i = (var1.value12 + 4) * 3;
      float f = var1.value7 * 1.2F;
      float f1 = var1.value8 * 0.55F;
      float[] afloat = var1.getFloatArrayByInt2(168);

      for (int j = 0; j < 3; j++) {
         float f2 = j * (float) Math.PI / 3.0F + TargetEffect.getFloatByIntFloat(41 + j, var1.value10 * 0.5F) * 0.3F;
         float f3 = 1.0F + TargetEffect.getFloatByIntFloat(51 + j, var1.value10 * 0.45F) * 0.22F;
         float f4 = TargetEffect.getFloatByFloat2(f2) * f;
         float f5 = TargetEffect.getFloatByFloat3(f2) * f;
         float f6 = -TargetEffect.getFloatByFloat3(f2) * TargetEffect.getFloatByFloat2(f3) * f;
         float f7 = -TargetEffect.getFloatByFloat3(f3) * f;
         float f8 = TargetEffect.getFloatByFloat2(f2) * TargetEffect.getFloatByFloat2(f3) * f;

         for (int k = 0; k < 56; k++) {
            float f9 = (float) (Math.PI * 2) * k / 56.0F;
            afloat[k * 3] = f4 * TargetEffect.getFloatByFloat2(f9) + f6 * TargetEffect.getFloatByFloat3(f9);
            afloat[k * 3 + 1] = f1 + f7 * TargetEffect.getFloatByFloat3(f9);
            afloat[k * 3 + 2] = f5 * TargetEffect.getFloatByFloat2(f9) + f8 * TargetEffect.getFloatByFloat3(f9);
         }

         float f17 = var1.getFloatByFloat((j + 0.9F) / 3.0F);
         float f22 = var1.value9 * 0.28F;
         int k1 = var1.getIntByFloat(0.2F * f17);
         boolean flag = true;
         int i1 = k1;
         float f12 = f22;
         byte b0 = 56;
         var1.onBooleanIntIntFloatArrayFloat(flag, b0, i1, afloat, f12);
         float f18 = var1.value10 * (float) (Math.PI * 2) * (1.1F + j * 0.17F) + j * 2.1F + TargetEffect.getFloatByIntFloat(61 + j, var1.value10 * 0.7F) * 0.9F;

         for (int l = 0; l < i; l++) {
            float f10 = f18 - l * 0.032F;
            float f11 = 1.0F - (float)l / i;
            float f20 = f4 * TargetEffect.getFloatByFloat2(f10) + f6 * TargetEffect.getFloatByFloat3(f10);
            float f21 = f1 + f7 * TargetEffect.getFloatByFloat3(f10);
            f22 = f5 * TargetEffect.getFloatByFloat2(f10) + f8 * TargetEffect.getFloatByFloat3(f10);
            float f19 = var1.value9 * (0.5F + 0.7F * f11);
            int j1 = var1.getIntByFloat(f11 * f11 * f17 * 0.35F);
            float f16 = f19;
            float f15 = f22;
            float f14 = f21;
            float f13 = f20;
            var1.onFloatFloatFloatFloatInt(f13, f16, f15, f14, j1);
         }
      }
   }
   },
   EMBERS("Искры") {
   public void onParticleBuffer(ParticleBuffer var1) {
      for (int i = 0; i < var1.value11; i++) {
         float f = TargetEffect.getFloatByFloat(var1.value10 * 0.35F + TargetEffect.getFloatByInt(i * 7919));
         float f1 = TargetEffect.getFloatByInt(i * 104729) * (float) (Math.PI * 2)
            + f * 0.7F
            + TargetEffect.getFloatByIntFloat(i * 13, var1.value10 * 0.6F) * 0.5F;
         float f2 = var1.value7 * (0.25F + 0.8F * TargetEffect.getFloatByInt(i * 15486))
            + TargetEffect.getFloatByIntFloat(i * 29, var1.value10 * 0.9F + f * 2.0F) * 0.07F;
         float f3 = TargetEffect.getFloatByFloat3((float) Math.PI * f) * var1.getFloatByFloat(f);
         float f8 = TargetEffect.getFloatByFloat2(f1) * f2;
         float f9 = f * var1.value8 * 1.25F;
         float f10 = TargetEffect.getFloatByFloat3(f1) * f2;
         float f11 = var1.value9 * (0.45F + 0.65F * TargetEffect.getFloatByInt(i * 32452));
         int j = var1.getIntByFloat(f3 * 0.75F);
         float f7 = f11;
         float f6 = f10;
         float f5 = f9;
         float f4 = f8;
         var1.onFloatFloatFloatFloatInt(f4, f7, f6, f5, j);
      }
   }
   },
   RUNES("Руны") {
   public void onParticleBuffer(ParticleBuffer var1) {
      int i = Math.max(3, var1.value11 / 24);
      int j = Math.max(24, var1.value11 / 2);
      float f15 = var1.value7 * 1.25F;
      float f16 = var1.value10 * (float) (Math.PI * 2) * 0.3F + TargetEffect.getFloatByIntFloat(71, var1.value10 * 0.5F) * 0.35F;
      float f5 = var1.value9;
      float f4 = 0.75F;
      float f3 = 0.55F;
      float f2 = f16;
      byte b0 = 12;
      float f1 = f15;
      TargetEffect.onIntParticleBufferFloatFloatIntFloatFloatFloat(i, var1, f3, f2, b0, f5, f1, f4);
      f15 = var1.value7 * 0.8F;
      f16 = -var1.value10 * (float) (Math.PI * 2) * 0.45F + TargetEffect.getFloatByIntFloat(72, var1.value10 * 0.55F) * 0.3F;
      float f10 = var1.value9 * 0.85F;
      float f9 = 0.6F;
      float f8 = 0.45F;
      float f7 = f16;
      byte b1 = 7;
      float f6 = f15;
      TargetEffect.onIntParticleBufferFloatFloatIntFloatFloatFloat(i, var1, f8, f7, b1, f10, f6, f9);

      for (int k = 0; k < j; k++) {
         float f = (float)k / j;
         f15 = TargetEffect.getFloatByFloat2((float) (Math.PI * 2) * f) * var1.value7;
         f16 = TargetEffect.getFloatByFloat3((float) (Math.PI * 2) * f) * var1.value7;
         float f17 = var1.value9 * 0.55F;
         int l = var1.getIntByFloat(0.18F * var1.getFloatByFloat(f));
         float f14 = f17;
         float f13 = f16;
         float f12 = 0.03F;
         float f11 = f15;
         var1.onFloatFloatFloatFloatInt(f11, f14, f13, f12, l);
      }
   }
   },
   PULSE("Пульс") {
   public void onParticleBuffer(ParticleBuffer var1) {
      int i = Math.max(24, var1.value11);

      for (int j = 0; j < 3; j++) {
         float f = TargetEffect.getFloatByFloat(var1.value10 * 0.85F + j / 3.0F + TargetEffect.getFloatByIntFloat(91 + j, var1.value10 * 0.5F) * 0.05F);
         float f1 = var1.value7 * (0.3F + f * 1.6F);
         float f2 = 0.03F + f * var1.value8 * 0.15F;
         float f3 = (1.0F - f) * (1.0F - f) * Math.min(1.0F, f * 5.0F) * var1.getFloatByFloat(f);
         float f4 = var1.value9 * (1.0F - 0.35F * f);
         int k = var1.getIntByFloat(f3 * 0.8F);

         for (int l = 0; l < i; l++) {
            float f5 = (float) (Math.PI * 2) * l / i;
            float f7 = TargetEffect.getFloatByFloat3(f5) * f1;
            float f6 = TargetEffect.getFloatByFloat2(f5) * f1;
            var1.onFloatFloatFloatFloatInt(f6, f4, f7, f2, k);
         }
      }
   }
   };

   public static final List<String> list = Arrays.stream(values()).map(TargetEffect::getText).toList();
   private final String text;
   public static final ParticleLayout particleLayout = new RingParticleLayout();
   public static final ParticleLayout particleLayout2 = new TriangleParticleLayout();
   private static final TargetEffect[] targetEffectArray = getTargetEffectArray();

   private TargetEffect(String text2) {
      this.text = text2;
   }

   public static float getFloatByFloat(float value) {
      return value - (float)Math.floor(value);
   }

   public static TargetEffect getTargetEffectByString(String text2) {
      for (TargetEffect targeteffect : values()) {
         if (targeteffect.text.equals(text2)) {
            return targeteffect;
         }
      }

      return RING;
   }

   public static float getFloatByFloat2(float value) {
      return (float)Math.cos(value);
   }

   public static float getFloatByFloat3(float value) {
      return (float)Math.sin(value);
   }

   public static float getFloatByInt(int count) {
      count = count ^ 61 ^ count >>> 16;
      count *= 9;
      count ^= count >>> 4;
      count *= 668265261;
      count ^= count >>> 15;
      return (count & 16777215) / 1.6777215E7F;
   }

   public static float getFloatByIntFloat(int count, float value) {
      int i = (int)Math.floor(value);
      float f = value - i;
      f = f * f * (3.0F - 2.0F * f);
      float f1 = getFloatByInt(count + i * 8191) * 2.0F - 1.0F;
      float f2 = getFloatByInt(count + (i + 1) * 8191) * 2.0F - 1.0F;
      return f1 + (f2 - f1) * f;
   }

   private static int getIntByFloatInt(float value, int count) {
      int i = (int)((count >>> 24) * value);
      return Math.min(255, Math.max(0, i)) << 24 | count & 16777215;
   }

   private static TargetEffect[] getTargetEffectArray() {
      return new TargetEffect[]{RING, TRIANGLE, LIGHTNING, HELIX, ORBIT, EMBERS, RUNES, PULSE};
   }

   public String getText() {
      return this.text;
   }

   public static void onIntParticleBufferParticleLayoutFloat(int count, ParticleBuffer particleBuffer, ParticleLayout particleLayout, float value) {
      int i = particleBuffer.value12 > 0 ? Math.min(10, 2 + particleBuffer.value12) : 1;
      int j = Math.max(1, i - 1);
      float f = particleBuffer.value12 * 0.009F;
      float[] afloat = particleBuffer.getFloatArrayByInt2(i * 2);
      float[] afloat1 = particleBuffer.getFloatArrayByInt(i * 3);
      int[] aint = particleBuffer.getIntArrayByInt2(i);
      int[] aint1 = particleBuffer.getIntArrayByInt(i);
      float[] afloat2 = particleBuffer.floatArray5;

      for (int k = 0; k < i; k++) {
         float f16 = particleBuffer.value10 - f * k / j;
         int i1 = k * 2;
         float f5 = f16;
         particleLayout.frame(particleBuffer, afloat, f5, i1);
      }

      float f11 = particleBuffer.value9 * 0.3F;

      for (int l = 0; l < i; l++) {
         int j1 = l * 2;
         byte b0 = 0;
         particleLayout.place(b0, afloat, afloat2, j1, particleBuffer);
         afloat1[l * 3] = afloat2[0];
         afloat1[l * 3 + 1] = afloat2[1];
         afloat1[l * 3 + 2] = afloat2[2];
         float f1 = 1.0F;
         if (l > 0) {
            float f2 = afloat1[l * 3] - afloat1[(l - 1) * 3];
            float f3 = afloat1[l * 3 + 1] - afloat1[(l - 1) * 3 + 1];
            float f4 = afloat1[l * 3 + 2] - afloat1[(l - 1) * 3 + 2];
            f1 = Math.min(1.0F, (float)Math.sqrt(f2 * f2 + f3 * f3 + f4 * f4) / f11);
         }

         float f13 = 1.0F - (float)l / i;
         aint[l] = particleBuffer.getIntByFloat(value * f13 * f13 * f1);
      }

      for (int j2 = 0; j2 < count; j2++) {
         float f12 = particleBuffer.getFloatByFloat((float)j2 / count);
         if (!(f12 <= 0.0F)) {
            for (int k2 = 0; k2 < i; k2++) {
               int k1 = k2 * 2;
               particleLayout.place(j2, afloat, afloat2, k1, particleBuffer);
               afloat1[k2 * 3] = afloat2[0];
               afloat1[k2 * 3 + 1] = afloat2[1];
               afloat1[k2 * 3 + 2] = afloat2[2];
            }

            int[] aint2 = aint;
            if (f12 < 1.0F) {
               for (int l2 = 0; l2 < i; l2++) {
                  int l1 = aint[l2];
                  aint1[l2] = getIntByFloatInt(f12, l1);
               }

               aint2 = aint1;
            }

            float f15 = afloat1[0];
            float f14 = afloat1[1];
            float f17 = afloat1[2];
            int i2 = aint2[0];
            float f9 = particleBuffer.value9;
            float f8 = f17;
            float f7 = f14;
            float f6 = f15;
            particleBuffer.onFloatFloatFloatFloatInt(f6, f9, f8, f7, i2);
            if (i > 1) {
               f17 = particleBuffer.value9 * 0.8F;
               boolean flag = false;
               float f10 = f17;
               particleBuffer.onFloatFloatArrayIntArrayBooleanInt(f10, afloat1, aint2, flag, i);
            }
         }
      }
   }

   public static void onIntParticleBufferFloatFloatIntFloatFloatFloat(
      int count, ParticleBuffer particleBuffer, float value, float value2, int count2, float value3, float value4, float value5
   ) {
      float f = (float) (Math.PI * 2) / count2 * value;

      for (int i = 0; i < count2; i++) {
         float f1 = value2 + (float) (Math.PI * 2) * i / count2;
         int j = particleBuffer.getIntByFloat(value5 * particleBuffer.getFloatByFloat((float)i / count2));

         for (int k = 0; k <= count; k++) {
            float f2 = f1 + f * k / count;
            float f6 = getFloatByFloat2(f2) * value4;
            float f5 = getFloatByFloat3(f2) * value4;
            float f4 = 0.03F;
            float f3 = f6;
            particleBuffer.onFloatFloatFloatFloatInt(f3, value3, f5, f4, j);
         }
      }
   }

   public static TargetEffect getTargetEffectByString2(String text) {
      return Enum.valueOf(TargetEffect.class, text);
   }

   public static float getFloatByFloat4(float value) {
      return 0.5F * (1.0F - getFloatByFloat2(value * (float) (Math.PI * 2)));
   }

   public abstract void onParticleBuffer(ParticleBuffer particleBuffer);
}
