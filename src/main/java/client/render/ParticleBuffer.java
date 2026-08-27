package client.render;

import net.minecraft.client.render.BufferBuilder;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class ParticleBuffer {
   private BufferBuilder bufferBuilder;
   private Matrix4f matrix4f;
   private final Vector3f vector3f = new Vector3f();
   private final Vector3f vector3f2 = new Vector3f();
   private float value;
   private float value2;
   private float value3;
   private int value4;
   private float value5;
   private boolean flag;
   private float value6;
   public float value7;
   public float value8;
   public float value9;
   public float value10;
   public int value11;
   public int value12;
   private float[] floatArray = new float[128];
   private float[] floatArray2 = new float[128];
   private float[] floatArray3 = new float[128];
   private float[] floatArray4 = new float[128];
   public final float[] floatArray5 = new float[3];
   private int[] intArray = new int[64];
   private int[] intArray2 = new int[64];

   public int[] getIntArrayByInt(int count) {
      if (this.intArray2.length < count) {
         this.intArray2 = new int[count];
      }

      return this.intArray2;
   }

   public float[] getFloatArrayByInt(int count) {
      if (this.floatArray4.length < count) {
         this.floatArray4 = new float[count];
      }

      return this.floatArray4;
   }

   public void onFloatFloatArrayIntArrayBooleanInt(float value, float[] valueArray, int[] countArray, boolean flag, int count) {
      byte b0 = 0;
      this.onIntArrayIntFloatFloatArrayIntBoolean(countArray, count, value, valueArray, b0, flag);
   }

   private void onIntArrayIntFloatFloatArrayIntBoolean(int[] countArray, int count, float value, float[] valueArray, int count2, boolean flag) {
      if (count >= 2) {
         if (this.floatArray.length < count * 2) {
            this.floatArray = new float[count * 2];
         }

         if (this.floatArray2.length < count * 2) {
            this.floatArray2 = new float[count * 2];
         }

         float f = value * 0.5F;
         int i = flag ? count : count - 1;

         for (int j = 0; j < i; j++) {
            int j3 = (j + 1) % count;
            int k1 = j * 2;
            int j1 = j3;
            this.onIntIntFloatArrayInt(j, k1, valueArray, j1);
         }

         float f11 = 0.0F;
         float f1 = 0.0F;

         for (int k = 0; k < i; k++) {
            if (this.floatArray2[k * 2] == 0.0F && this.floatArray2[k * 2 + 1] == 0.0F) {
               this.floatArray2[k * 2] = f11;
               this.floatArray2[k * 2 + 1] = f1;
            } else {
               f11 = this.floatArray2[k * 2];
               f1 = this.floatArray2[k * 2 + 1];
            }
         }

         for (int l1 = i - 1; l1 >= 0; l1--) {
            if (this.floatArray2[l1 * 2] == 0.0F && this.floatArray2[l1 * 2 + 1] == 0.0F) {
               this.floatArray2[l1 * 2] = f11;
               this.floatArray2[l1 * 2 + 1] = f1;
            } else {
               f11 = this.floatArray2[l1 * 2];
               f1 = this.floatArray2[l1 * 2 + 1];
            }
         }

         for (int i2 = 0; i2 < count; i2++) {
            int l = flag ? (i2 + count - 1) % count : Math.max(0, i2 - 1);
            int i1 = flag ? i2 : Math.min(i2, i - 1);
            float f2 = this.floatArray2[l * 2];
            float f3 = this.floatArray2[l * 2 + 1];
            float f4 = this.floatArray2[i1 * 2];
            float f5 = this.floatArray2[i1 * 2 + 1];
            float f6 = -f3 - f5;
            float f7 = f2 + f4;
            float f8 = (float)Math.sqrt(f6 * f6 + f7 * f7);
            if (f8 < 1.0E-5F) {
               f6 = -f5;
               f7 = f4;
               f8 = (float)Math.sqrt(f6 * f6 + f4 * f4);
            }

            if (f8 < 1.0E-5F) {
               f6 = 1.0F;
               f7 = 0.0F;
               f8 = 1.0F;
            }

            f6 /= f8;
            f7 /= f8;
            float f9 = Math.abs(f6 * -f5 + f7 * f4);
            float f10;
            if (f9 < 0.35F) {
               f6 = -f5;
               f7 = f4;
               f10 = f;
            } else {
               f10 = f / f9;
            }

            this.floatArray[i2 * 2] = f6 * f10;
            this.floatArray[i2 * 2 + 1] = f7 * f10;
         }

         for (int j2 = 0; j2 < i; j2++) {
            int k2 = (j2 + 1) % count;
            int l2 = countArray == null ? count2 : countArray[j2];
            int i3 = countArray == null ? count2 : countArray[k2];
            if (l2 >>> 24 != 0 || i3 >>> 24 != 0) {
               float[] afloat = this.floatArray;
               this.onIntIntFloatArrayIntFloatArrayInt(l2, k2, valueArray, j2, afloat, i3);
            }
         }
      }
   }

   public void onBooleanIntIntFloatArrayFloat(boolean flag, int count, int count2, float[] valueArray, float value) {
      if (count2 >>> 24 != 0) {
         Object object = null;
         this.onIntArrayIntFloatFloatArrayIntBoolean((int[])object, count, value, valueArray, count2, flag);
      }
   }

   private void onIntIntFloatArrayIntFloatArrayInt(int count, int count2, float[] valueArray, int count3, float[] valueArray2, int count4) {
      float f = this.value + valueArray[count3 * 3];
      float f1 = this.value2 + valueArray[count3 * 3 + 1];
      float f2 = this.value3 + valueArray[count3 * 3 + 2];
      float f3 = this.value + valueArray[count2 * 3];
      float f4 = this.value2 + valueArray[count2 * 3 + 1];
      float f5 = this.value3 + valueArray[count2 * 3 + 2];
      float f6 = valueArray2[count3 * 2];
      float f7 = valueArray2[count3 * 2 + 1];
      float f8 = valueArray2[count2 * 2];
      float f9 = valueArray2[count2 * 2 + 1];
      float f10 = this.vector3f.x * f6 + this.vector3f2.x * f7;
      float f11 = this.vector3f.y * f6 + this.vector3f2.y * f7;
      float f12 = this.vector3f.z * f6 + this.vector3f2.z * f7;
      float f13 = this.vector3f.x * f8 + this.vector3f2.x * f9;
      float f14 = this.vector3f.y * f8 + this.vector3f2.y * f9;
      float f15 = this.vector3f.z * f8 + this.vector3f2.z * f9;
      this.bufferBuilder.vertex(this.matrix4f, f - f10, f1 - f11, f2 - f12).texture(0.0F, 0.5F).color(count);
      this.bufferBuilder.vertex(this.matrix4f, f3 - f13, f4 - f14, f5 - f15).texture(0.0F, 0.5F).color(count4);
      this.bufferBuilder.vertex(this.matrix4f, f3 + f13, f4 + f14, f5 + f15).texture(1.0F, 0.5F).color(count4);
      this.bufferBuilder.vertex(this.matrix4f, f + f10, f1 + f11, f2 + f12).texture(1.0F, 0.5F).color(count);
   }

   private void onIntIntFloatArrayInt(int count, int count2, float[] valueArray, int count3) {
      float f = valueArray[count3 * 3] - valueArray[count * 3];
      float f1 = valueArray[count3 * 3 + 1] - valueArray[count * 3 + 1];
      float f2 = valueArray[count3 * 3 + 2] - valueArray[count * 3 + 2];
      float f3 = f * this.vector3f.x + f1 * this.vector3f.y + f2 * this.vector3f.z;
      float f4 = f * this.vector3f2.x + f1 * this.vector3f2.y + f2 * this.vector3f2.z;
      float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
      if (f5 < 1.0E-5F) {
         this.floatArray2[count2] = 0.0F;
         this.floatArray2[count2 + 1] = 0.0F;
      } else {
         this.floatArray2[count2] = f3 / f5;
         this.floatArray2[count2 + 1] = f4 / f5;
      }
   }

   public int getIntByFloat(float value) {
      int i = (int)((this.value4 >>> 24) * this.value5 * value * this.value6);
      return Math.min(255, Math.max(0, i)) << 24 | this.value4 & 16777215;
   }

   public void onIntBufferBuilderFloatFloatMatrix4fFloatVector3fVector3fIntFloatIntFloatFloatFloatFloatBooleanFloat(
      int count,
      BufferBuilder bufferBuilder2,
      float value13,
      float value14,
      Matrix4f matrix4f2,
      float value15,
      Vector3f vector3f3,
      Vector3f vector3f4,
      int count2,
      float value16,
      int count3,
      float value17,
      float value18,
      float value19,
      float value20,
      boolean flag2,
      float value21
   ) {
      this.bufferBuilder = bufferBuilder2;
      this.matrix4f = matrix4f2;
      this.vector3f.set(vector3f4);
      this.vector3f2.set(vector3f3);
      this.value = value13;
      this.value2 = value14;
      this.value3 = value18;
      this.value7 = value20;
      this.value8 = value15;
      this.value9 = value16;
      this.value10 = value21;
      this.value11 = count2;
      this.value12 = count3;
      this.value4 = count;
      this.value5 = value19;
      this.flag = flag2;
      this.value6 = value17;
   }

   public float[] getFloatArrayByInt2(int count) {
      if (this.floatArray3.length < count) {
         this.floatArray3 = new float[count];
      }

      return this.floatArray3;
   }

   public int[] getIntArrayByInt2(int count) {
      if (this.intArray.length < count) {
         this.intArray = new int[count];
      }

      return this.intArray;
   }

   public float getFloatByFloat(float value) {
      float f = this.flag ? this.value5 * 1.1800001F - value : value - ((1.0F - this.value5) * 1.1800001F - 0.18F);
      return f <= 0.0F ? 0.0F : Math.min(1.0F, f / 0.18F);
   }

   public void onFloatFloatFloatFloatInt(float value4, float value5, float value6, float value7, int count) {
      if (count >>> 24 != 0) {
         float f = value5 * 0.5F;
         float f1 = this.value + value4;
         float f2 = this.value2 + value7;
         float f3 = this.value3 + value6;
         float f4 = this.vector3f.x * f;
         float f5 = this.vector3f.y * f;
         float f6 = this.vector3f.z * f;
         float f7 = this.vector3f2.x * f;
         float f8 = this.vector3f2.y * f;
         float f9 = this.vector3f2.z * f;
         this.bufferBuilder.vertex(this.matrix4f, f1 - f4 + f7, f2 - f5 + f8, f3 - f6 + f9).texture(0.0F, 1.0F).color(count);
         this.bufferBuilder.vertex(this.matrix4f, f1 + f4 + f7, f2 + f5 + f8, f3 + f6 + f9).texture(1.0F, 1.0F).color(count);
         this.bufferBuilder.vertex(this.matrix4f, f1 + f4 - f7, f2 + f5 - f8, f3 + f6 - f9).texture(1.0F, 0.0F).color(count);
         this.bufferBuilder.vertex(this.matrix4f, f1 - f4 - f7, f2 - f5 - f8, f3 - f6 - f9).texture(0.0F, 0.0F).color(count);
      }
   }
}
