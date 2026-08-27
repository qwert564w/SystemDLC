package client.api;

import client.render.RoundedTextureShader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public record StatusEffectIcon(RegistryEntry<StatusEffect> effect) implements Icon {
   public RegistryEntry<StatusEffect> getEffect() {
      return this.effect;
   }

   @Override
   public void draw(float value, float value2, float value3, Matrix4f matrix4f, float value4, DrawContext drawContext) {
      if (this.effect != null) {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         if (minecraftclient != null) {
            Sprite sprite = minecraftclient.getStatusEffectSpriteManager().getSprite(this.effect);
            if (sprite != null) {
               Identifier identifier1 = sprite.getAtlasId();
               float f8 = sprite.getMinU();
               float f9 = sprite.getMinV();
               float f10 = sprite.getMaxU();
               float f11 = sprite.getMaxV();
               byte b0 = -1;
               float f7 = 0.0F;
               float f6 = 0.0F;
               float f5 = 0.0F;
               float f4 = 0.0F;
               float f3 = f11;
               float f2 = f10;
               float f1 = f9;
               float f = f8;
               Identifier identifier = identifier1;
               RoundedTextureShader.onFloatFloatFloatFloatFloatFloatIntFloatFloatMatrix4fIdentifierFloatFloatFloatFloatFloat(
                  value2, value4, f2, value4, f3, f7, b0, value, f6, matrix4f, identifier, f4, f, f1, value3, f5
               );
            }
         }
      }
   }
}
