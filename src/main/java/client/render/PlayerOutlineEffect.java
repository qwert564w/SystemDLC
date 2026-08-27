package client.render;

import client.concurrent.ResourceManagerHooks;
import client.module.visual.HandGlow;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.platform.GlStateManager;
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
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public final class PlayerOutlineEffect {
   private static final ShaderProgramKey shaderProgramKey = new ShaderProgramKey(
      Identifier.ofVanilla("player_outline_blur"), VertexFormats.POSITION, Defines.EMPTY
   );
   private static final ShaderProgramKey shaderProgramKey2 = new ShaderProgramKey(
      Identifier.ofVanilla("player_outline_blit"), VertexFormats.POSITION, Defines.EMPTY
   );
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
   private static GlUniform glUniform9;
   private static GlUniform glUniform10;
   private static final long time = System.nanoTime();

   private PlayerOutlineEffect() {
   }

   public static void update() {
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
      shaderProgram2 = null;
      glUniform3 = null;
      glUniform2 = null;
      glUniform = null;
      glUniform10 = null;
      glUniform9 = null;
      glUniform8 = null;
      glUniform7 = null;
      glUniform6 = null;
      glUniform5 = null;
      glUniform4 = null;
   }

   private static void setShaderProgram(ShaderProgram shaderProgram) {
      if (shaderProgram != shaderProgram2) {
         shaderProgram2 = shaderProgram;
         glUniform4 = shaderProgram.getUniform("OutlineColor");
         glUniform5 = shaderProgram.getUniform("OutlineColorTop");
         glUniform6 = shaderProgram.getUniform("Intensity");
         glUniform7 = shaderProgram.getUniform("FillAlpha");
         glUniform8 = shaderProgram.getUniform("Time");
         glUniform9 = shaderProgram.getUniform("FlameStrength");
         glUniform10 = shaderProgram.getUniform("GradientSpeed");
      }
   }

   private static void update2() {
      BufferBuilder bufferbuilder = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION);
      bufferbuilder.vertex(0.0F, 0.0F, 0.0F);
      bufferbuilder.vertex(1.0F, 0.0F, 0.0F);
      bufferbuilder.vertex(1.0F, 1.0F, 0.0F);
      bufferbuilder.vertex(0.0F, 1.0F, 0.0F);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
   }

   private static void onIntInt(int count, int count2) {
      int i = Math.max(1, count2 / 2);
      int j = Math.max(1, count / 2);
      boolean flag = true;
      SimpleFramebuffer simpleframebuffer = simpleFramebuffer;
      simpleFramebuffer = getSimpleFramebufferBySimpleFramebufferBooleanIntInt(simpleframebuffer, flag, count2, count);
      boolean flag1 = false;
      SimpleFramebuffer simpleframebuffer1 = simpleFramebuffer2;
      simpleFramebuffer2 = getSimpleFramebufferBySimpleFramebufferBooleanIntInt(simpleframebuffer1, flag1, i, j);
      boolean flag2 = false;
      SimpleFramebuffer simpleframebuffer2 = simpleFramebuffer3;
      simpleFramebuffer3 = getSimpleFramebufferBySimpleFramebufferBooleanIntInt(simpleframebuffer2, flag2, i, j);
   }

   private static SimpleFramebuffer getSimpleFramebufferBySimpleFramebufferBooleanIntInt(SimpleFramebuffer simpleFramebuffer, boolean flag, int count, int count2) {
      if (simpleFramebuffer == null) {
         SimpleFramebuffer simpleframebuffer = new SimpleFramebuffer(count, count2, flag);
         onSimpleFramebuffer(simpleframebuffer);
         return simpleframebuffer;
      } else {
         if (simpleFramebuffer.textureWidth != count || simpleFramebuffer.textureHeight != count2) {
            simpleFramebuffer.resize(count, count2);
            onSimpleFramebuffer(simpleFramebuffer);
         }

         return simpleFramebuffer;
      }
   }

   private static void onSimpleFramebuffer(SimpleFramebuffer simpleFramebuffer) {
      PixelReader.onSimpleFramebuffer(simpleFramebuffer);
   }

   private static Framebuffer framebuffer4;
   private static int value235 = -1;
   private static int value236 = 0;

   public static void onVertexConsumerProvider(VertexConsumerProvider vertexConsumerProvider) {
      if (vertexConsumerProvider instanceof Immediate immediate) {
         try {
            immediate.draw();
         } catch (Throwable throwable) {
         }
      }

      value235 = GL11.glGetInteger(36006);
      SimpleFramebuffer simpleframebuffer = simpleFramebuffer;
      if (simpleframebuffer != null) {
         simpleframebuffer.beginWrite(false);
      }
   }

   public static void update4() {
      int i = value235;
      value235 = -1;
      if (i >= 0) {
         GlStateManager._glBindFramebuffer(36160, i);
      } else {
         Framebuffer framebuffer = framebuffer4 != null ? framebuffer4 : MinecraftClient.getInstance().getFramebuffer();
         if (framebuffer != null) {
            framebuffer.beginWrite(false);
         }
      }
   }

   public static boolean isFramebuffer(Framebuffer framebuffer) {
      framebuffer4 = framebuffer;
      int j = framebuffer.textureHeight;
      int i = framebuffer.textureWidth;
      onIntInt(j, i);
      if (simpleFramebuffer == null) {
         return false;
      } else {
         int k = GL11.glGetInteger(36006);
         simpleFramebuffer.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         simpleFramebuffer.beginWrite(false);
         simpleFramebuffer.clear();
         GlStateManager._glBindFramebuffer(36160, k);
         return true;
      }
   }

   public static void onHandGlowMinecraftClient(HandGlow handGlow, MinecraftClient minecraftClient) {
      if (handGlow != null) {
         boolean flag = handGlow.vklyuchitAnimaciyu.isFlag3();
         boolean flag1 = handGlow.gradient.isFlag3();
         float f = (float)handGlow.radius.getValue();
         if (flag) {
            f *= handGlow.sizeAnimacii.getValueAsFloat();
         }

         float f12 = handGlow.color.getValue() * 0.003921569F;
         float f13 = handGlow.color.getValue2() * 0.003921569F;
         float f14 = handGlow.color.getValue3() * 0.003921569F;
         float f15 = handGlow.color.getValue4() * 0.003921569F;
         float f16 = flag1 ? handGlow.vtoroyColor.getValue() * 0.003921569F : 0.0F;
         float f17 = flag1 ? handGlow.vtoroyColor.getValue2() * 0.003921569F : 0.0F;
         float f18 = flag1 ? handGlow.vtoroyColor.getValue3() * 0.003921569F : 0.0F;
         float f19 = flag1 ? handGlow.vtoroyColor.getValue4() * 0.003921569F : 0.0F;
         float f20 = (float)handGlow.yarkost.getValue();
         float f21 = flag ? handGlow.strengthAnimacii.getValueAsFloat() : 0.0F;
         float f11 = flag1 ? handGlow.speedGradienta.getValueAsFloat() : 0.0F;
         float f10 = f21;
         float f9 = f20;
         float f8 = f19;
         float f7 = f18;
         float f6 = f17;
         float f5 = f16;
         float f4 = f15;
         float f3 = f14;
         float f2 = f13;
         float f1 = f12;
         onMinecraftClientFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloat(minecraftClient, f11, f7, f5, f10, f1, f4, f6, f2, f, f9, f8, f3);
      }
   }

   public static void onMinecraftClientFloat(MinecraftClient minecraftClient, float value) {
      float f10 = 0.0F;
      float f9 = 0.0F;
      float f8 = 1.8F;
      float f7 = 14.0F;
      float f6 = 0.0F;
      float f5 = 0.0F;
      float f4 = 0.0F;
      float f3 = 0.0F;
      float f2 = 1.0F;
      float f1 = 1.0F;
      float f = 1.0F;
      onMinecraftClientFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloat(minecraftClient, f10, f5, f3, f9, f, value, f4, f1, f7, f8, f6, f2);
   }

   private static void onMinecraftClientFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloat(
      MinecraftClient minecraftClient,
      float value,
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
      float value12
   ) {
      if (simpleFramebuffer != null && simpleFramebuffer2 != null && simpleFramebuffer3 != null) {
         if (!ResourceManagerHooks.isFlag4()) {
            value236 = GL11.glGetInteger(36006);
            ShaderProgram shaderprogram;
            ShaderProgram shaderprogram1;
            try {
               shaderprogram = minecraftClient.getShaderLoader().getOrCreateProgram(shaderProgramKey);
               shaderprogram1 = minecraftClient.getShaderLoader().getOrCreateProgram(shaderProgramKey2);
            } catch (Throwable throwable) {
               return;
            }

            if (shaderprogram != null && shaderprogram1 != null) {
               setShaderProgram2(shaderprogram);
               setShaderProgram(shaderprogram1);
               float f = 0.0F;
               float f1 = value9 / 2.0F;
               int i = simpleFramebuffer2.textureWidth;
               int j = simpleFramebuffer2.textureHeight;
               RenderSystem.disableDepthTest();
               RenderSystem.depthMask(false);
               RenderSystem.disableBlend();
               RenderSystem.viewport(0, 0, i, j);
               RenderSystem.setShader(shaderprogram);
               if (glUniform != null) {
                  glUniform.set(i, j);
               }

               if (glUniform3 != null) {
                  glUniform3.set(f1);
               }

               simpleFramebuffer2.beginWrite(false);
               shaderprogram.addSamplerTexture("MaskSampler", simpleFramebuffer.getColorAttachment());
               if (glUniform2 != null) {
                  glUniform2.set(1.0F, 0.0F);
               }

               update2();
               simpleFramebuffer3.beginWrite(false);
               shaderprogram.addSamplerTexture("MaskSampler", simpleFramebuffer2.getColorAttachment());
               if (glUniform2 != null) {
                  glUniform2.set(0.0F, 1.0F);
               }

               update2();
               MinecraftClient.getInstance().getFramebuffer().beginWrite(true);
               RenderSystem.enableBlend();
               RenderSystem.blendFuncSeparate(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
               shaderprogram1.addSamplerTexture("BlurSampler", simpleFramebuffer3.getColorAttachment());
               shaderprogram1.addSamplerTexture("MaskSampler", simpleFramebuffer.getColorAttachment());
               if (glUniform4 != null) {
                  glUniform4.set(value5, value8, value12, value6);
               }

               if (glUniform5 != null) {
                  glUniform5.set(value3, value7, value2, value11);
               }

               if (glUniform6 != null) {
                  glUniform6.set(value10);
               }

               if (glUniform7 != null) {
                  glUniform7.set(f);
               }

               if (glUniform8 != null) {
                  glUniform8.set((float)(System.nanoTime() - time) / 1.0E9F);
               }

               if (glUniform9 != null) {
                  glUniform9.set(value4);
               }

               if (glUniform10 != null) {
                  glUniform10.set(value);
               }

               RenderSystem.setShader(shaderprogram1);
               update2();
               RenderSystem.depthMask(true);
               RenderSystem.enableDepthTest();
               RenderSystem.disableBlend();
               RenderSystem.defaultBlendFunc();
            }
         }
      }
   }

   private static void setShaderProgram2(ShaderProgram shaderProgram2) {
      if (shaderProgram2 != shaderProgram) {
         shaderProgram = shaderProgram2;
         glUniform = shaderProgram2.getUniform("MaskSize");
         glUniform2 = shaderProgram2.getUniform("Direction");
         glUniform3 = shaderProgram2.getUniform("Radius");
      }
   }

   public static Framebuffer getSimpleFramebufferAsFramebuffer() {
      return simpleFramebuffer;
   }
}
