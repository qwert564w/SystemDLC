package client.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.BufferAllocator;

public final class LayeredBufferSource implements VertexConsumerProvider, AutoCloseable {
   private static final VertexConsumer vertexConsumer = new NoopVertexConsumer();
   private static final VertexFormat vertexFormat = VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL;
   private static final LayeredBufferSource INSTANCE = new LayeredBufferSource();
   private final Immediate immediate = VertexConsumerProvider.immediate(new BufferAllocator(786432));

   public void update() {
      this.immediate.draw();
   }

   public static LayeredBufferSource getInstance() {
      return INSTANCE;
   }

   @Override
   public void close() {
      this.immediate.draw();
   }

   public VertexConsumer getBuffer(RenderLayer renderLayer) {
      return (VertexConsumer)(renderLayer.getVertexFormat() != vertexFormat ? vertexConsumer : new VertexConsumerWrapper(this.immediate.getBuffer(renderLayer)));
   }
}
