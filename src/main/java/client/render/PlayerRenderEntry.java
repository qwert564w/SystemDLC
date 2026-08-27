package client.render;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;

public final class PlayerRenderEntry {
   public final LivingEntityRenderer<?, ?, ?> livingEntityRenderer;
   public final PlayerEntity playerEntity;
   public final int value;
   public final float value2;

   PlayerRenderEntry(LivingEntityRenderer livingEntityRenderer2, PlayerEntity playerEntity2, int count, float value3) {
      this.livingEntityRenderer = livingEntityRenderer2;
      this.playerEntity = playerEntity2;
      this.value = count;
      this.value2 = value3;
   }
}
