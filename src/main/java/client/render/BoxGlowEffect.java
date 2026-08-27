package client.render;

import client.concurrent.ResourceManagerHooks;
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

public final class BoxGlowEffect {
   private static final ShaderProgramKey shaderProgramKey = new ShaderProgramKey(
      Identifier.ofVanilla("core/box_glow_blur"), VertexFormats.POSITION, Defines.EMPTY
   );
   private static final ShaderProgramKey shaderProgramKey2 = new ShaderProgramKey(
      Identifier.ofVanilla("core/box_glow_blit"), VertexFormats.POSITION, Defines.EMPTY
   );
   private static SimpleFramebuffer simpleFramebuffer;
   private static SimpleFramebuffer simpleFramebuffer2;
   private static ShaderProgram shaderProgram;
   private static GlUniform glUniform;
   private static GlUniform glUniform2;
   private static GlUniform glUniform3;
   private static ShaderProgram shaderProgram2;
   private static GlUniform glUniform4;
   private static Framebuffer framebuffer;
   private static int value;
   private static int value2;

   private BoxGlowEffect() {
   }

   private static void update() {
      BufferBuilder bufferbuilder = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION);
      bufferbuilder.vertex(0.0F, 0.0F, 0.0F);
      bufferbuilder.vertex(1.0F, 0.0F, 0.0F);
      bufferbuilder.vertex(1.0F, 1.0F, 0.0F);
      bufferbuilder.vertex(0.0F, 1.0F, 0.0F);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
   }

   private static void onSimpleFramebuffer(SimpleFramebuffer simpleFramebuffer) {
      PixelReader.onSimpleFramebuffer(simpleFramebuffer);
   }

   private static void onIntInt(int count, int count2) {
      if (simpleFramebuffer == null) {
         simpleFramebuffer = new SimpleFramebuffer(count, count2, false);
         onSimpleFramebuffer(simpleFramebuffer);
      } else if (simpleFramebuffer.textureWidth != count || simpleFramebuffer.textureHeight != count2) {
         simpleFramebuffer.resize(count, count2);
         onSimpleFramebuffer(simpleFramebuffer);
      }

      if (simpleFramebuffer2 == null) {
         simpleFramebuffer2 = new SimpleFramebuffer(count, count2, false);
         onSimpleFramebuffer(simpleFramebuffer2);
      } else if (simpleFramebuffer2.textureWidth != count || simpleFramebuffer2.textureHeight != count2) {
         simpleFramebuffer2.resize(count, count2);
         onSimpleFramebuffer(simpleFramebuffer2);
      }
   }

   public static boolean check() {
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      Framebuffer framebufferx = minecraftclient.getFramebuffer();
      if (framebufferx == null) {
         return false;
      } else {
         int i = Math.max(1, framebufferx.textureWidth / 2);
         int j = Math.max(1, framebufferx.textureHeight / 2);
         onIntInt(i, j);
         if (simpleFramebuffer == null) {
            return false;
         } else {
            framebuffer = framebufferx;
            value = framebufferx.textureWidth;
            value2 = framebufferx.textureHeight;
            simpleFramebuffer.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            simpleFramebuffer.clear();
            simpleFramebuffer.beginWrite(true);
            RenderSystem.viewport(0, 0, simpleFramebuffer.textureWidth, simpleFramebuffer.textureHeight);
            return true;
         }
      }
   }

   public static void onFloatFloat(float value3, float value4) {
      if (simpleFramebuffer != null && simpleFramebuffer2 != null && framebuffer != null) {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         if (ResourceManagerHooks.isFlag4()) {
            restore();
         } else {
            ShaderProgram shaderprogram;
            ShaderProgram shaderprogram1;
            try {
               shaderprogram = minecraftclient.getShaderLoader().getOrCreateProgram(shaderProgramKey);
               shaderprogram1 = minecraftclient.getShaderLoader().getOrCreateProgram(shaderProgramKey2);
            } catch (Throwable throwable) {
               restore();
               return;
            }

            if (shaderprogram != null && shaderprogram1 != null) {
               if (shaderprogram != shaderProgram) {
                  shaderProgram = shaderprogram;
                  glUniform = shaderprogram.getUniform("InSize");
                  glUniform2 = shaderprogram.getUniform("Direction");
                  glUniform3 = shaderprogram.getUniform("Radius");
               }

               if (shaderprogram1 != shaderProgram2) {
                  shaderProgram2 = shaderprogram1;
                  glUniform4 = shaderprogram1.getUniform("Intensity");
               }

               int i = simpleFramebuffer.textureWidth;
               int j = simpleFramebuffer.textureHeight;
               float f = value3 / 2.0F;
               RenderSystem.disableBlend();
               RenderSystem.viewport(0, 0, i, j);
               RenderSystem.setShader(shaderprogram);
               if (glUniform != null) {
                  glUniform.set(i, j);
               }

               if (glUniform3 != null) {
                  glUniform3.set(f);
               }

               simpleFramebuffer2.beginWrite(false);
               shaderprogram.addSamplerTexture("InSampler", simpleFramebuffer.getColorAttachment());
               if (glUniform2 != null) {
                  glUniform2.set(1.0F, 0.0F);
               }

               update();
               simpleFramebuffer.beginWrite(false);
               shaderprogram.addSamplerTexture("InSampler", simpleFramebuffer2.getColorAttachment());
               if (glUniform2 != null) {
                  glUniform2.set(0.0F, 1.0F);
               }

               update();
               framebuffer.beginWrite(false);
               RenderSystem.viewport(0, 0, value, value2);
               RenderSystem.disableDepthTest();
               RenderSystem.depthMask(false);
               RenderSystem.enableBlend();
               RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
               RenderSystem.setShader(shaderprogram1);
               if (glUniform4 != null) {
                  glUniform4.set(value4);
               }

               shaderprogram1.addSamplerTexture("InSampler", simpleFramebuffer.getColorAttachment());
               update();
               RenderSystem.depthMask(true);
               RenderSystem.enableDepthTest();
               RenderSystem.defaultBlendFunc();
               framebuffer = null;
            } else {
               restore();
            }
         }
      }
   }

   public static void restore() {
      if (framebuffer != null) {
         framebuffer.beginWrite(false);
         RenderSystem.viewport(0, 0, value, value2);
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         RenderSystem.defaultBlendFunc();
         framebuffer = null;
      }
   }
}
