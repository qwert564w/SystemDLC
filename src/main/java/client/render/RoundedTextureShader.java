package client.render;

import client.util.MathUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class RoundedTextureShader {
   private static final ShaderProgramKey shaderProgramKey = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "texture", VertexFormats.POSITION_TEXTURE_COLOR, Defines.EMPTY
   );
   private static final float value = 1.0F;

   public static void onFloatIdentifierFloatFloatFloatMatrix4fFloat(float value, Identifier identifier, float value2, float value3, float value4, Matrix4f matrix4f, float value5) {
      if (identifier != null && !(value <= 0.0F) && !(value5 <= 0.0F)) {
         float f = 0.125F;
         float f1 = 0.125F;
         float f2 = 0.25F;
         float f3 = 0.25F;
         byte b0 = -1;
         onFloatFloatFloatFloatFloatFloatIntFloatFloatMatrix4fIdentifierFloatFloatFloatFloatFloat(
            value4, value, f2, value, f3, value3, b0, value2, value3, matrix4f, identifier, value3, f, f1, value5, value3
         );
         float f4 = 0.625F;
         float f5 = 0.125F;
         float f6 = 0.75F;
         float f7 = 0.25F;
         byte b1 = -1;
         onFloatFloatFloatFloatFloatFloatIntFloatFloatMatrix4fIdentifierFloatFloatFloatFloatFloat(
            value4, value, f6, value, f7, value3, b1, value2, value3, matrix4f, identifier, value3, f4, f5, value5, value3
         );
      }
   }

   private static int getIntByFloatInt(float value, int count) {
      return MathUtil.getIntByFloatInt2(value, count);
   }

   public static void onFloatFloatMatrix4fFloatIdentifierFloatFloatFloatInt(
      float value, float value2, Matrix4f matrix4f, float value3, Identifier identifier, float value4, float value5, float value6, int count
   ) {
      float f3 = 1.0F;
      float f2 = 1.0F;
      float f1 = 0.0F;
      float f = 0.0F;
      onFloatFloatFloatFloatFloatFloatIntFloatFloatMatrix4fIdentifierFloatFloatFloatFloatFloat(
         value2, value, f2, value3, f3, value6, count, value5, value6, matrix4f, identifier, value6, f, f1, value4, value6
      );
   }

   public static void onFloatIntIntFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
      float value,
      int count,
      int count2,
      float value2,
      float value3,
      float value4,
      float value5,
      float value6,
      float value7,
      float value8,
      float value9,
      float value10,
      float value11,
      float value12,
      Matrix4f matrix4f,
      float value13
   ) {
      if (count != 0) {
         Runnable runnable = () -> RenderSystem.setShaderTexture(0, count);
         onFloatFloatFloatIntFloatFloatMatrix4fFloatFloatRunnableFloatFloatFloatFloatFloatFloat(
            value7, value12, value9, count2, value13, value5, matrix4f, value6, value4, runnable, value, value10, value3, value8, value2, value11
         );
      }
   }

   private static void onFloatFloatFloatIntFloatFloatMatrix4fFloatFloatRunnableFloatFloatFloatFloatFloatFloat(
      float value,
      float value2,
      float value3,
      int count,
      float value4,
      float value5,
      Matrix4f matrix4f,
      float value6,
      float value7,
      Runnable runnable,
      float value8,
      float value9,
      float value10,
      float value11,
      float value12,
      float value13
   ) {
      if (!(value10 <= 0.0F) && !(value6 <= 0.0F) && !(value <= 0.0F)) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();

         try {
            runnable.run();
            ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey);
            if (shaderprogram != null) {
               String s = "Size";
               ShaderUniforms.onStringFloatFloatShaderProgram(s, value6, value10, shaderprogram);
               String s1 = "Radius";
               ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(value9, value3, shaderprogram, s1, value11, value2);
               float f = 1.0F;
               String s2 = "Smoothness";
               ShaderUniforms.onFloatShaderProgramString(f, shaderprogram, s2);
               int i = getIntByFloatInt(value, count);
               BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
               bufferbuilder.vertex(matrix4f, value12, value8, 0.0F).texture(value4, value13).color(i);
               bufferbuilder.vertex(matrix4f, value12, value8 + value6, 0.0F).texture(value4, value7).color(i);
               bufferbuilder.vertex(matrix4f, value12 + value10, value8 + value6, 0.0F).texture(value5, value7).color(i);
               bufferbuilder.vertex(matrix4f, value12 + value10, value8, 0.0F).texture(value5, value13).color(i);
               BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
               return;
            }
         } finally {
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   public static void onIntIdentifierFloatArrayFloatFloat(int count, Identifier identifier, float[] valueArray, float value, float value2) {
      if (identifier != null && count > 0) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();

         try {
            RenderSystem.setShaderTexture(0, identifier);
            ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey);
            if (shaderprogram != null) {
               String s = "Size";
               ShaderUniforms.onStringFloatFloatShaderProgram(s, value, value2, shaderprogram);
               float f3 = 0.0F;
               float f2 = 0.0F;
               float f1 = 0.0F;
               float f = 0.0F;
               String s1 = "Radius";
               ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f3, f1, shaderprogram, s1, f2, f);
               float f4 = 1.0F;
               String s2 = "Smoothness";
               ShaderUniforms.onFloatShaderProgramString(f4, shaderprogram, s2);
               BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

               for (int b0 = 0; b0 < count; b0 += 6) {
                  bufferbuilder.vertex(valueArray[b0], valueArray[b0 + 1], valueArray[b0 + 2]).texture(valueArray[b0 + 3], valueArray[b0 + 4]).color(Float.floatToRawIntBits(valueArray[b0 + 5]));
               }

               BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
               return;
            }
         } finally {
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   public static void onFloatMatrix4fFloatFloatFloatFloatFloatIntFloatIdentifierFloatFloatFloat(
      float value,
      Matrix4f matrix4f,
      float value2,
      float value3,
      float value4,
      float value5,
      float value6,
      int count,
      float value7,
      Identifier identifier,
      float value8,
      float value9,
      float value10
   ) {
      onFloatFloatFloatFloatFloatFloatIntFloatFloatMatrix4fIdentifierFloatFloatFloatFloatFloat(
         value9, value5, value7, value6, value10, value, count, value8, value, matrix4f, identifier, value, value4, value3, value2, value
      );
   }

   public static void onFloatFloatFloatFloatFloatFloatIntFloatFloatMatrix4fIdentifierFloatFloatFloatFloatFloat(
      float value,
      float value2,
      float value3,
      float value4,
      float value5,
      float value6,
      int count,
      float value7,
      float value8,
      Matrix4f matrix4f,
      Identifier identifier,
      float value9,
      float value10,
      float value11,
      float value12,
      float value13
   ) {
      if (identifier != null) {
         Runnable runnable = () -> RenderSystem.setShaderTexture(0, identifier);
         onFloatFloatFloatIntFloatFloatMatrix4fFloatFloatRunnableFloatFloatFloatFloatFloatFloat(
            value12, value9, value13, count, value10, value3, matrix4f, value4, value5, runnable, value7, value6, value2, value8, value, value11
         );
      }
   }
}
