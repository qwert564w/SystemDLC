package client.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.BufferAllocator;

public final class ImmediateBufferSource implements VertexConsumerProvider, AutoCloseable {
   private static final ImmediateBufferSource INSTANCE = new ImmediateBufferSource();
   private final Immediate immediate = VertexConsumerProvider.immediate(new BufferAllocator(262144));

   public void update() {
      this.immediate.draw();
   }

   public static ImmediateBufferSource getInstance() {
      return INSTANCE;
   }

   @Override
   public void close() {
      this.immediate.draw();
   }

   public VertexConsumer getBuffer(RenderLayer renderLayer) {
      return new ColoredVertexConsumer(this.immediate.getBuffer(renderLayer));
   }
}
