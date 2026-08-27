package client.render;

import client.data.VertexBatch;
import java.util.Arrays;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix4f;

public final class TextVertexConsumer implements VertexConsumer {
   public VertexBatch vertexBatch;
   public int value;

   public TextVertexConsumer() {
   }

   public VertexConsumer normal(float value, float value2, float value3) {
      return this;
   }

   public VertexConsumer vertex(float value, float value2, float value3) {
      return this;
   }

   public VertexConsumer color(int count, int count2, int count3, int count4) {
      return this;
   }

   public VertexConsumer texture(float value2, float value3) {
      VertexBatch vertexbatch = this.vertexBatch;
      if (vertexbatch == null) {
         return this;
      } else {
         int i = vertexbatch.value;
         vertexbatch.floatArray[i] = value2;
         vertexbatch.floatArray[i + 1] = value3;
         vertexbatch.floatArray[i + 2] = Float.intBitsToFloat(this.value);
         vertexbatch.value = i + 3;
         return this;
      }
   }

   public VertexConsumer light(int count, int count2) {
      return this;
   }

   public VertexConsumer overlay(int count, int count2) {
      return this;
   }

   public VertexConsumer vertex(Matrix4f matrix4f, float value2, float value3, float value4) {
      VertexBatch vertexbatch = this.vertexBatch;
      if (vertexbatch == null) {
         return this;
      } else {
         if (vertexbatch.value + 6 > vertexbatch.floatArray.length) {
            vertexbatch.floatArray = Arrays.copyOf(vertexbatch.floatArray, vertexbatch.floatArray.length * 2);
         }

         int i = vertexbatch.value;
         vertexbatch.floatArray[i] = matrix4f.m00() * value2 + matrix4f.m10() * value3 + matrix4f.m30();
         vertexbatch.floatArray[i + 1] = matrix4f.m01() * value2 + matrix4f.m11() * value3 + matrix4f.m31();
         vertexbatch.floatArray[i + 2] = matrix4f.m02() * value2 + matrix4f.m12() * value3 + matrix4f.m32();
         vertexbatch.value = i + 3;
         return this;
      }
   }
}
