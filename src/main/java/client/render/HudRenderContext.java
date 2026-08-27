package client.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HudRenderContext {
   private DrawContext drawContext;
   private RenderTickCounter renderTickCounter;

   public RenderTickCounter getRenderTickCounter() {
      return this.renderTickCounter;
   }

   public DrawContext getDrawContext() {
      return this.drawContext;
   }

   public HudRenderContext getHudRenderContextByDrawContextRenderTickCounter(DrawContext drawContext2, RenderTickCounter renderTickCounter2) {
      this.drawContext = drawContext2;
      this.renderTickCounter = renderTickCounter2;
      return this;
   }
}
