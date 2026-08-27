package client.render;

import client.data.GlyphInfo;
import client.data.RectF;
import client.module.client.PanicModule;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix4f;

public class AtlasSprite {
   private final float value;
   private final float value2;
   private final float value3;
   private final float value4;
   private final float value5;
   private final float value6;
   private final float value7;
   private final float value8;

   public AtlasSprite(GlyphInfo glyphInfo, float value9, float value10) {
      this.value5 = glyphInfo.getValue2();
      RectF rectf = glyphInfo.getRectF2();
      if (rectf != null) {
         this.value = rectf.getValue() / value9;
         this.value2 = rectf.getValue3() / value9;
         this.value3 = 1.0F - rectf.getValue2() / value10;
         this.value4 = 1.0F - rectf.getValue4() / value10;
      } else {
         this.value = this.value2 = this.value3 = this.value4 = 0.0F;
      }

      RectF rectf1 = glyphInfo.getRectF();
      if (rectf1 != null) {
         this.value7 = rectf1.getValue3() - rectf1.getValue();
         this.value8 = rectf1.getValue2() - rectf1.getValue4();
         this.value6 = rectf1.getValue2();
      } else {
         this.value7 = this.value8 = this.value6 = 0.0F;
      }
   }

   public float getFloatByVertexConsumerFloatMatrix4fFloatFloatFloat(VertexConsumer vertexConsumer, float value9, Matrix4f matrix4f, float value10, float value11, float value12) {
      if (PanicModule.isFlag()) {
         return 1.0F;
      } else {
         value12 -= this.value6 * value11;
         float f = this.value7 * value11;
         float f1 = this.value8 * value11;
         vertexConsumer.vertex(matrix4f, value9, value12, value10).texture(this.value, this.value3);
         vertexConsumer.vertex(matrix4f, value9, value12 + f1, value10).texture(this.value, this.value4);
         vertexConsumer.vertex(matrix4f, value9 + f, value12 + f1, value10).texture(this.value2, this.value4);
         vertexConsumer.vertex(matrix4f, value9 + f, value12, value10).texture(this.value2, this.value3);
         return this.value5 * value11;
      }
   }

   public float getValue5(float value) {
      return this.value5 * value;
   }
}
