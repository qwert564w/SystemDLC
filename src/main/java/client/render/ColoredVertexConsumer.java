package client.render;

import net.minecraft.client.render.VertexConsumer;

public final class ColoredVertexConsumer implements VertexConsumer {
   private final VertexConsumer vertexConsumer;

   ColoredVertexConsumer(VertexConsumer vertexConsumer2) {
      this.vertexConsumer = vertexConsumer2;
   }

   public VertexConsumer normal(float value, float value2, float value3) {
      this.vertexConsumer.normal(value, value2, value3);
      return this;
   }

   public VertexConsumer vertex(float value, float value2, float value3) {
      this.vertexConsumer.vertex(value, value2, value3).color(-1);
      return this;
   }

   public VertexConsumer color(int count, int count2, int count3, int count4) {
      return this;
   }

   public VertexConsumer texture(float value, float value2) {
      this.vertexConsumer.texture(value, value2);
      return this;
   }

   public VertexConsumer light(int count, int count2) {
      this.vertexConsumer.light(count, count2);
      return this;
   }

   public VertexConsumer overlay(int count, int count2) {
      this.vertexConsumer.overlay(count, count2);
      return this;
   }
}
