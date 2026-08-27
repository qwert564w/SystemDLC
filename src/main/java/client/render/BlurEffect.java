package client.render;

import client.concurrent.ResourceManagerHooks;
import client.module.client.StreamBypass;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.util.Identifier;

public final class BlurEffect {
   private static final ShaderProgramKey shaderProgramKey = new ShaderProgramKey(Identifier.ofVanilla("core/blur"), VertexFormats.POSITION, Defines.EMPTY);
   private static final ShaderProgramKey shaderProgramKey2 = new ShaderProgramKey(
      Identifier.ofVanilla("core/blur_composite"), VertexFormats.POSITION, Defines.EMPTY
   );
   private static final int value = 2;
   private static SimpleFramebuffer simpleFramebuffer;
   private static SimpleFramebuffer simpleFramebuffer2;
   private static SimpleFramebuffer simpleFramebuffer3;
   private static ShaderProgram shaderProgram;
   private static GlUniform glUniform;
   private static GlUniform glUniform2;
   private static GlUniform glUniform3;
   private static ShaderProgram shaderProgram2;
   private static GlUniform glUniform4;
   private static GlUniform glUniform5;
   private static GlUniform glUniform6;
   private static GlUniform glUniform7;
   private static GlUniform glUniform8;

   private BlurEffect() {
   }

   private static void update() {
      BufferBuilder bufferbuilder = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION);
      bufferbuilder.vertex(0.0F, 0.0F, 0.0F);
      bufferbuilder.vertex(1.0F, 0.0F, 0.0F);
      bufferbuilder.vertex(1.0F, 1.0F, 0.0F);
      bufferbuilder.vertex(0.0F, 1.0F, 0.0F);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
   }

   private static SimpleFramebuffer getSimpleFramebufferBySimpleFramebufferIntInt(SimpleFramebuffer simpleFramebuffer, int count, int count2) {
      if (simpleFramebuffer == null) {
         simpleFramebuffer = new SimpleFramebuffer(count2, count, false);
      } else {
         if (simpleFramebuffer.textureWidth == count2 && simpleFramebuffer.textureHeight == count) {
            return simpleFramebuffer;
         }

         simpleFramebuffer.resize(count2, count);
      }

      onSimpleFramebuffer(simpleFramebuffer);
      return simpleFramebuffer;
   }

   private static void onIntInt(int count, int count2) {
      int i = Math.max(1, count / 2);
      int j = Math.max(1, count2 / 2);
      SimpleFramebuffer simpleframebuffer = simpleFramebuffer;
      simpleFramebuffer = getSimpleFramebufferBySimpleFramebufferIntInt(simpleframebuffer, count2, count);
      SimpleFramebuffer simpleframebuffer1 = simpleFramebuffer2;
      simpleFramebuffer2 = getSimpleFramebufferBySimpleFramebufferIntInt(simpleframebuffer1, j, i);
      SimpleFramebuffer simpleframebuffer2 = simpleFramebuffer3;
      simpleFramebuffer3 = getSimpleFramebufferBySimpleFramebufferIntInt(simpleframebuffer2, j, i);
   }

   public static void update2() {
      if (simpleFramebuffer != null) {
         simpleFramebuffer.delete();
         simpleFramebuffer = null;
      }

      if (simpleFramebuffer2 != null) {
         simpleFramebuffer2.delete();
         simpleFramebuffer2 = null;
      }

      if (simpleFramebuffer3 != null) {
         simpleFramebuffer3.delete();
         simpleFramebuffer3 = null;
      }

      shaderProgram = null;
      glUniform = null;
      glUniform2 = null;
      glUniform3 = null;
      shaderProgram2 = null;
      glUniform4 = null;
      glUniform5 = null;
      glUniform6 = null;
      glUniform7 = null;
      glUniform8 = null;
   }

   private static void onSimpleFramebuffer(SimpleFramebuffer simpleFramebuffer) {
      PixelReader.onSimpleFramebuffer(simpleFramebuffer);
   }

   private static void onFloatIntIntFloatFloatFloatFloat(float value, int count, int count2, float value2, float value3, float value4, float value5) {
      if (glUniform6 != null) {
         if (!(value5 <= 0.0F) && !(value2 <= 0.0F)) {
            float f = 1.0F / count2;
            float f1 = 1.0F / count;
            glUniform6.set(value * f, 1.0F - (value4 + value2) * f1, (value + value5) * f, 1.0F - value4 * f1);
            if (glUniform7 != null) {
               glUniform7.set(value3 * f, value3 * f1);
            }
         } else {
            glUniform6.set(0.0F, 0.0F, 0.0F, 0.0F);
            if (glUniform7 != null) {
               glUniform7.set(0.0F, 0.0F);
            }
         }
      }
   }

   public static void onFloatFloatFloatFloatFloatFloatFloatFloatFloat(
      float value, float value2, float value3, float value4, float value5, float value6, float value7, float value8, float value9
   ) {
      float f5 = value8 * value5;
      float f6 = value2 * value5;
      float f7 = value * value5;
      float f8 = value7 * value5;
      float f4 = value3 * value5;
      float f3 = f8;
      float f2 = f7;
      float f1 = f6;
      float f = f5;
      onFloatFloatFloatFloatFloatFloatFloatFloat(value9, value4, f1, f4, f, f2, value6, f3);
   }

   private static void onFloatFloatFloatFloatFloatFloatFloatFloat(
      float value, float value2, float value3, float value4, float value5, float value6, float value7, float value8
   ) {
      if (!(value2 <= 0.001F) && !(value7 <= 0.01F)) {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         Framebuffer framebuffer = minecraftclient.getFramebuffer();
         if (framebuffer != null) {
            boolean flag = value6 <= 0.0F || value8 <= 0.0F;
            Framebuffer framebuffer1 = framebuffer;
            if (flag) {
               Framebuffer framebuffer2 = OverlayFramebuffers.getFramebuffer2();
               if (framebuffer2 != null) {
                  framebuffer1 = framebuffer2;
               }
            }

            int i1 = framebuffer.textureWidth;
            int i = framebuffer.textureHeight;
            if (i1 > 0 && i > 0) {
               if (!ResourceManagerHooks.isFlag4()) {
                  ShaderProgram shaderprogram;
                  ShaderProgram shaderprogram1;
                  try {
                     shaderprogram = minecraftclient.getShaderLoader().getOrCreateProgram(shaderProgramKey);
                     shaderprogram1 = minecraftclient.getShaderLoader().getOrCreateProgram(shaderProgramKey2);
                  } catch (Throwable throwable) {
                     return;
                  }

                  if (shaderprogram != null && shaderprogram1 != null) {
                     onIntInt(i1, i);
                     if (simpleFramebuffer != null && simpleFramebuffer2 != null && simpleFramebuffer3 != null) {
                        onShaderProgramShaderProgram(shaderprogram1, shaderprogram);
                        simpleFramebuffer.beginWrite(true);
                        framebuffer1.draw(i1, i);
                        Framebuffer framebuffer3 = flag ? OverlayFramebuffers.getFramebuffer3() : null;
                        if (framebuffer3 != null) {
                           RenderSystem.enableBlend();
                           RenderSystem.defaultBlendFunc();
                           OverlayFramebuffers.update3();
                           SimpleFramebuffer simpleframebuffer4 = simpleFramebuffer;
                           int k1 = framebuffer3.getColorAttachment();
                           float f2 = 0.0F;
                           float f1 = 0.0F;
                           float f = 0.0F;
                           int j = k1;
                           SimpleFramebuffer simpleframebuffer = simpleframebuffer4;
                           onFloatFloatShaderProgramSimpleFramebufferFloatInt(f, f1, shaderprogram, simpleframebuffer, f2, j);
                        }

                        RenderSystem.disableBlend();
                        RenderSystem.disableDepthTest();
                        RenderSystem.depthMask(false);
                        SimpleFramebuffer simpleframebuffer3 = simpleFramebuffer2;
                        int j1 = simpleFramebuffer.getColorAttachment();
                        float f4 = 0.0F;
                        float f3 = 1.0F;
                        int k = j1;
                        SimpleFramebuffer simpleframebuffer1 = simpleframebuffer3;
                        onFloatFloatShaderProgramSimpleFramebufferFloatInt(f3, f4, shaderprogram, simpleframebuffer1, value7, k);
                        simpleframebuffer3 = simpleFramebuffer3;
                        j1 = simpleFramebuffer2.getColorAttachment();
                        float f6 = 1.0F;
                        float f5 = 0.0F;
                        int l = j1;
                        SimpleFramebuffer simpleframebuffer2 = simpleframebuffer3;
                        onFloatFloatShaderProgramSimpleFramebufferFloatInt(f5, f6, shaderprogram, simpleframebuffer2, value7, l);
                        onIntFloatFloatFloatFloatFloatFloatShaderProgramIntFramebufferFloat(
                           i, value, value2, value4, value8, value6, value5, shaderprogram1, i1, framebuffer, value3
                        );
                        if (StreamBypass.isFlag()) {
                           OverlayFramebuffers.update2();
                        }

                        RenderSystem.defaultBlendFunc();
                        RenderSystem.depthMask(true);
                        RenderSystem.enableDepthTest();
                     }
                  }
               }
            }
         }
      }
   }

   private static void onShaderProgramShaderProgram(ShaderProgram shaderProgram3, ShaderProgram shaderProgram4) {
      if (shaderProgram4 != shaderProgram) {
         shaderProgram = shaderProgram4;
         glUniform = shaderProgram4.getUniform("InSize");
         glUniform2 = shaderProgram4.getUniform("Direction");
         glUniform3 = shaderProgram4.getUniform("Radius");
      }

      if (shaderProgram3 != shaderProgram2) {
         shaderProgram2 = shaderProgram3;
         glUniform4 = shaderProgram3.getUniform("MixAmount");
         glUniform5 = shaderProgram3.getUniform("DimAmount");
         glUniform6 = shaderProgram3.getUniform("BoxRect");
         glUniform7 = shaderProgram3.getUniform("BoxRadius");
         glUniform8 = shaderProgram3.getUniform("OverlayPass");
      }
   }

   private static void onFloatFloatShaderProgramSimpleFramebufferFloatInt(
      float value, float value2, ShaderProgram shaderProgram, SimpleFramebuffer simpleFramebuffer, float value3, int count
   ) {
      simpleFramebuffer.beginWrite(false);
      RenderSystem.viewport(0, 0, simpleFramebuffer.textureWidth, simpleFramebuffer.textureHeight);
      RenderSystem.setShader(shaderProgram);
      shaderProgram.addSamplerTexture("InSampler", count);
      if (glUniform != null) {
         glUniform.set((float)simpleFramebuffer.textureWidth, (float)simpleFramebuffer.textureHeight);
      }

      if (glUniform3 != null) {
         glUniform3.set(value3 / 2.0F);
      }

      if (glUniform2 != null) {
         glUniform2.set(value, value2);
      }

      update();
   }

   private static void onIntFloatFloatFloatFloatFloatFloatShaderProgramIntFramebufferFloat(
      int count, float value, float value2, float value3, float value4, float value5, float value6, ShaderProgram shaderProgram, int count2, Framebuffer framebuffer, float value7
   ) {
      framebuffer.beginWrite(false);
      RenderSystem.viewport(0, 0, count2, count);
      RenderSystem.setShader(shaderProgram);
      shaderProgram.addSamplerTexture("BlurSampler", simpleFramebuffer3.getColorAttachment());
      shaderProgram.addSamplerTexture("MainSampler", simpleFramebuffer.getColorAttachment());
      if (glUniform4 != null) {
         glUniform4.set(Math.clamp(value2, 0.0F, 1.0F));
      }

      if (glUniform5 != null) {
         glUniform5.set(Math.clamp(value, 0.0F, 1.0F));
      }

      onFloatIntIntFloatFloatFloatFloat(value6, count, count2, value4, value3, value7, value5);
      boolean flag = StreamBypass.isFlag();
      boolean flag1 = value5 <= 0.0F || value4 <= 0.0F;
      if (glUniform8 != null) {
         glUniform8.set(flag ? 1.0F : 0.0F);
      }

      if (flag) {
         if (flag1) {
            RenderSystem.enableBlend();
            OverlayFramebuffers.update3();
            RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
         } else {
            RenderSystem.disableBlend();
            RenderSystem.colorMask(true, true, true, false);
         }
      }

      update();
      if (flag) {
         if (flag1) {
            RenderSystem.disableBlend();
         } else {
            RenderSystem.colorMask(true, true, true, true);
         }
      }
   }

   public static void onFloatFloatFloat(float value, float value2, float value3) {
      float f4 = 0.0F;
      float f3 = 0.0F;
      float f2 = 0.0F;
      float f1 = 0.0F;
      float f = 0.0F;
      onFloatFloatFloatFloatFloatFloatFloatFloat(value, value2, f1, f4, f, f2, value3, f3);
   }
}
