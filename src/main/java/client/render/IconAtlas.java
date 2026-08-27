package client.render;

import client.data.FontMetrics;
import client.data.GlyphMetrics;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.AbstractTexture;
import org.joml.Matrix4f;

public class IconAtlas {
   private final AbstractTexture abstractTexture;
   private final GlyphMetrics glyphMetrics;
   private final FontMetrics fontMetrics;
   private final Map<Integer, AtlasSprite> map;
   private final Map<Integer, Map<Integer, Float>> map2;

   public IconAtlas(AbstractTexture abstractTexture2, GlyphMetrics glyphMetrics2, FontMetrics fontMetrics2, Map map3, Map map4) {
      this.abstractTexture = abstractTexture2;
      this.glyphMetrics = glyphMetrics2;
      this.fontMetrics = fontMetrics2;
      this.map = (Map<Integer, AtlasSprite>)(map3 != null ? map3 : new HashMap<>());
      this.map2 = (Map<Integer, Map<Integer, Float>>)(map4 != null ? map4 : new HashMap<>());
   }

   public void onVertexConsumerStringArrayFloatFloatMatrix4fIntFloatFloatIntFloat(
      VertexConsumer vertexConsumer, String[] textArray, float value, float value2, Matrix4f matrix4f, int count, float value3, float value4, int count2, float value5
   ) {
      if (textArray != null) {
         if (matrix4f != null && vertexConsumer != null) {
            int i = -1;

            for (int j = count2; j < count; j++) {
               String s = textArray[j];
               if (s != null && !s.isEmpty()) {
                  for (int k = 0; k < s.length(); k++) {
                     char c0 = s.charAt(k);
                     AtlasSprite atlassprite = this.map.get(Integer.valueOf(c0));
                     if (atlassprite == null) {
                        i = c0;
                     } else {
                        Map<Integer, Float> mapx = this.map2.get(i);
                        if (mapx != null) {
                           value4 += mapx.getOrDefault(Integer.valueOf(c0), 0.0F) * value;
                        }

                        value4 += atlassprite.getFloatByVertexConsumerFloatMatrix4fFloatFloatFloat(vertexConsumer, value4, matrix4f, value3, value, value2) + value5;
                        i = c0;
                     }
                  }
               }
            }
         }
      }
   }

   public static IconAtlasLoader getIconAtlasLoader() {
      return new IconAtlasLoader();
   }

   public GlyphMetrics getGlyphMetrics() {
      return this.glyphMetrics;
   }

   public FontMetrics getFontMetrics() {
      return this.fontMetrics;
   }

   public int getInt() {
      return this.abstractTexture == null ? 0 : this.abstractTexture.getGlId();
   }

   public float getFloatByStringFloat(String text, float value) {
      if (text != null && !text.isEmpty()) {
         int i = -1;
         float f = 0.0F;

         for (int j = 0; j < text.length(); j++) {
            char c0 = text.charAt(j);
            AtlasSprite atlassprite = this.map.get(Integer.valueOf(c0));
            if (atlassprite != null) {
               Map<Integer, Float> mapx = this.map2.get(i);
               if (mapx != null) {
                  f += mapx.getOrDefault(Integer.valueOf(c0), 0.0F) * value;
               }

               try {
                  f += atlassprite.getValue5(value);
               } catch (Exception exception) {
               }

               i = c0;
            }
         }

         return f;
      } else {
         return 0.0F;
      }
   }

   public void onFloatVertexConsumerFloatMatrix4fFloatFloatStringFloat(
      float value, VertexConsumer vertexConsumer, float value2, Matrix4f matrix4f, float value3, float value4, String text, float value5
   ) {
      if (text != null && !text.isEmpty()) {
         if (matrix4f != null && vertexConsumer != null) {
            int i = -1;

            for (int j = 0; j < text.length(); j++) {
               char c0 = text.charAt(j);
               AtlasSprite atlassprite = this.map.get(Integer.valueOf(c0));
               if (atlassprite != null) {
                  Map<Integer, Float> mapx = this.map2.get(i);
                  if (mapx != null) {
                     value += mapx.getOrDefault(Integer.valueOf(c0), 0.0F) * value2;
                  }

                  value += atlassprite.getFloatByVertexConsumerFloatMatrix4fFloatFloatFloat(vertexConsumer, value, matrix4f, value3, value2, value5) + value4;
                  i = c0;
               }
            }
         }
      }
   }

   public float getFloatByStringArrayIntFloatInt(String[] textArray, int count, float value, int count2) {
      if (textArray == null) {
         return 0.0F;
      } else {
         int i = -1;
         float f = 0.0F;

         for (int j = count2; j < count; j++) {
            String s = textArray[j];
            if (s != null && !s.isEmpty()) {
               for (int k = 0; k < s.length(); k++) {
                  char c0 = s.charAt(k);
                  AtlasSprite atlassprite = this.map.get(Integer.valueOf(c0));
                  if (atlassprite == null) {
                     i = c0;
                  } else {
                     Map<Integer, Float> mapx = this.map2.get(i);
                     if (mapx != null) {
                        f += mapx.getOrDefault(Integer.valueOf(c0), 0.0F) * value;
                     }

                     try {
                        f += atlassprite.getValue5(value);
                     } catch (Exception exception) {
                     }

                     i = c0;
                  }
               }
            }
         }

         return f;
      }
   }
}
