package client.render;

import net.minecraft.client.render.VertexConsumer;

public final class NoopVertexConsumer implements VertexConsumer {
   public NoopVertexConsumer() {
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

   public VertexConsumer texture(float value, float value2) {
      return this;
   }

   public VertexConsumer light(int count, int count2) {
      return this;
   }

   public VertexConsumer overlay(int count, int count2) {
      return this;
   }
}
