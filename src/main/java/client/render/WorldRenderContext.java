package client.render;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;

public class WorldRenderContext {
   private MatrixStack matrixStack;
   private Camera camera;
   private RenderTickCounter renderTickCounter;
   private ClientWorld clientWorld;

   public WorldRenderContext() {
   }

   public WorldRenderContext(MatrixStack matrixStack, Camera camera, RenderTickCounter renderTickCounter, ClientWorld clientWorld) {
      this.getWorldRenderContextByMatrixStackCameraRenderTickCounterClientWorld(matrixStack, camera, renderTickCounter, clientWorld);
   }

   public RenderTickCounter getRenderTickCounter() {
      return this.renderTickCounter;
   }

   public Camera getCamera() {
      return this.camera;
   }

   public ClientWorld getClientWorld() {
      return this.clientWorld;
   }

   public MatrixStack getMatrixStack() {
      return this.matrixStack;
   }

   public WorldRenderContext getWorldRenderContextByMatrixStackCameraRenderTickCounterClientWorld(
      MatrixStack matrixStack2, Camera camera2, RenderTickCounter renderTickCounter2, ClientWorld clientWorld2
   ) {
      this.matrixStack = matrixStack2;
      this.camera = camera2;
      this.renderTickCounter = renderTickCounter2;
      this.clientWorld = clientWorld2;
      return this;
   }
}
