package client.render;

import client.concurrent.ResourceManagerHooks;
import client.module.render.PlayerESP;
import client.setting.ColorSetting;
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

public final class PlayerOutlineMaskEffect {
   private static final ShaderProgramKey shaderProgramKey = new ShaderProgramKey(
      Identifier.ofVanilla("player_outline_blur"), VertexFormats.POSITION, Defines.EMPTY
   );
   private static final ShaderProgramKey shaderProgramKey2 = new ShaderProgramKey(
      Identifier.ofVanilla("player_outline_blit"), VertexFormats.POSITION, Defines.EMPTY
   );
   private static SimpleFramebuffer simpleFramebuffer;
   private static SimpleFramebuffer simpleFramebuffer2;
   private static SimpleFramebuffer simpleFramebuffer3;
   private static SimpleFramebuffer simpleFramebuffer4;
   private static boolean flag;
   private static boolean flag2;
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
   private static GlUniform glUniform11;
   private static final long time = System.nanoTime();

   private PlayerOutlineMaskEffect() {
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

      if (simpleFramebuffer4 != null) {
         simpleFramebuffer4.delete();
         simpleFramebuffer4 = null;
      }

      shaderProgram = null;
      glUniform = null;
      glUniform2 = null;
      glUniform3 = null;
      shaderProgram2 = null;
      glUniform4 = null;
      glUniform5 = null;
      glUniform6 = null;
      glUniform11 = null;
      glUniform7 = null;
      glUniform8 = null;
      glUniform9 = null;
      glUniform10 = null;
      flag = false;
      flag2 = false;
   }

   public static void setFlag() {
      flag = true;
   }

   public static Framebuffer getSimpleFramebuffer2AsFramebuffer() {
      return simpleFramebuffer2;
   }

   public static void setFlag2() {
      flag2 = true;
   }

   private static void update2() {
      BufferBuilder bufferbuilder = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION);
      bufferbuilder.vertex(0.0F, 0.0F, 0.0F);
      bufferbuilder.vertex(1.0F, 0.0F, 0.0F);
      bufferbuilder.vertex(1.0F, 1.0F, 0.0F);
      bufferbuilder.vertex(0.0F, 1.0F, 0.0F);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
   }

   private static void onMinecraftClientShaderProgramFloatFloatShaderProgramColorSettingFloatFloatFloatColorSettingIntIntColorSettingSimpleFramebuffer(
      MinecraftClient minecraftClient,
      ShaderProgram shaderProgram,
      float value,
      float value2,
      ShaderProgram shaderProgram2,
      ColorSetting colorSetting,
      float value3,
      float value4,
      float value5,
      ColorSetting colorSetting2,
      int count,
      int count2,
      ColorSetting colorSetting3,
      SimpleFramebuffer simpleFramebuffer
   ) {
      float f = colorSetting2.getValue() * 0.003921569F;
      float f1 = colorSetting2.getValue2() * 0.003921569F;
      float f2 = colorSetting2.getValue3() * 0.003921569F;
      float f3 = colorSetting2.getValue4() * 0.003921569F;
      RenderSystem.disableBlend();
      RenderSystem.viewport(0, 0, count, count2);
      RenderSystem.setShader(shaderProgram);
      if (glUniform != null) {
         glUniform.set(count, count2);
      }

      if (glUniform3 != null) {
         glUniform3.set(value2);
      }

      simpleFramebuffer3.beginWrite(false);
      shaderProgram.addSamplerTexture("MaskSampler", simpleFramebuffer.getColorAttachment());
      if (glUniform2 != null) {
         glUniform2.set(1.0F, 0.0F);
      }

      update2();
      simpleFramebuffer4.beginWrite(false);
      shaderProgram.addSamplerTexture("MaskSampler", simpleFramebuffer3.getColorAttachment());
      if (glUniform2 != null) {
         glUniform2.set(0.0F, 1.0F);
      }

      update2();
      Framebuffer framebuffer = minecraftClient.getFramebuffer();
      int i = framebuffer.textureWidth;
      int j = framebuffer.textureHeight;
      framebuffer.beginWrite(false);
      RenderSystem.viewport(0, 0, i, j);
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
      shaderProgram2.addSamplerTexture("BlurSampler", simpleFramebuffer4.getColorAttachment());
      shaderProgram2.addSamplerTexture("MaskSampler", simpleFramebuffer.getColorAttachment());
      if (glUniform4 != null) {
         glUniform4.set(f, f1, f2, f3);
      }

      if (glUniform5 != null) {
         if (colorSetting != null) {
            glUniform5.set(colorSetting.getValue() * 0.003921569F, colorSetting.getValue2() * 0.003921569F, colorSetting.getValue3() * 0.003921569F, colorSetting.getValue4() * 0.003921569F);
         } else {
            glUniform5.set(0.0F, 0.0F, 0.0F, 0.0F);
         }
      }

      if (glUniform6 != null) {
         glUniform6.set(colorSetting3.getValue() * 0.003921569F, colorSetting3.getValue2() * 0.003921569F, colorSetting3.getValue3() * 0.003921569F, 1.0F);
      }

      if (glUniform7 != null) {
         glUniform7.set(value4);
      }

      if (glUniform8 != null) {
         glUniform8.set(value);
      }

      if (glUniform9 != null) {
         glUniform9.set((float)(System.nanoTime() - time) / 1.0E9F);
      }

      if (glUniform10 != null) {
         glUniform10.set(value5);
      }

      if (glUniform11 != null) {
         glUniform11.set(value3);
      }

      RenderSystem.setShader(shaderProgram2);
      update2();
   }

   public static boolean isFramebufferBooleanBoolean(Framebuffer framebuffer2, boolean flag, boolean flag2) {
      int i = framebuffer2.textureWidth;
      int j = framebuffer2.textureHeight;
      onIntInt(j, i);
      Framebuffer framebuffer = flag ? framebuffer2 : null;
      boolean flagx = true;
      SimpleFramebuffer simpleframebuffer = simpleFramebuffer;
      simpleFramebuffer = getSimpleFramebufferByIntSimpleFramebufferFramebufferBooleanInt(j, simpleframebuffer, framebuffer, flagx, i);
      SimpleFramebuffer simpleframebuffer1 = simpleFramebuffer2;
      simpleFramebuffer2 = getSimpleFramebufferByIntSimpleFramebufferFramebufferBooleanInt(j, simpleframebuffer1, framebuffer, flag2, i);
      if (simpleFramebuffer == null) {
         return false;
      } else {
         simpleFramebuffer.beginWrite(true);
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(true);
         return true;
      }
   }

   private static void onIntInt(int count, int count2) {
      int i = Math.max(1, count2 / 2);
      int j = Math.max(1, count / 2);
      if (simpleFramebuffer3 == null) {
         simpleFramebuffer3 = new SimpleFramebuffer(i, j, false);
         onSimpleFramebuffer(simpleFramebuffer3);
      } else if (simpleFramebuffer3.textureWidth != i || simpleFramebuffer3.textureHeight != j) {
         simpleFramebuffer3.resize(i, j);
         onSimpleFramebuffer(simpleFramebuffer3);
      }

      if (simpleFramebuffer4 == null) {
         simpleFramebuffer4 = new SimpleFramebuffer(i, j, false);
         onSimpleFramebuffer(simpleFramebuffer4);
      } else if (simpleFramebuffer4.textureWidth != i || simpleFramebuffer4.textureHeight != j) {
         simpleFramebuffer4.resize(i, j);
         onSimpleFramebuffer(simpleFramebuffer4);
      }
   }

   private static void onSimpleFramebuffer(SimpleFramebuffer simpleFramebuffer) {
      PixelReader.onSimpleFramebuffer(simpleFramebuffer);
   }

   private static SimpleFramebuffer getSimpleFramebufferByIntSimpleFramebufferFramebufferBooleanInt(
      int count, SimpleFramebuffer simpleFramebuffer, Framebuffer framebuffer, boolean flag, int count2
   ) {
      if (!flag) {
         return simpleFramebuffer;
      } else {
         if (simpleFramebuffer == null) {
            simpleFramebuffer = new SimpleFramebuffer(count2, count, true);
            onSimpleFramebuffer(simpleFramebuffer);
         } else if (simpleFramebuffer.textureWidth != count2 || simpleFramebuffer.textureHeight != count) {
            simpleFramebuffer.resize(count2, count);
            onSimpleFramebuffer(simpleFramebuffer);
         }

         simpleFramebuffer.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         simpleFramebuffer.clear();
         if (framebuffer != null) {
            simpleFramebuffer.copyDepthFrom(framebuffer);
         }

         return simpleFramebuffer;
      }
   }

   public static void onFramebuffer(Framebuffer framebuffer) {
      framebuffer.beginWrite(false);
   }

   public static Framebuffer getSimpleFramebufferAsFramebuffer() {
      return simpleFramebuffer;
   }

   public static void update3() {
      flag = false;
      flag2 = false;
   }

   public static void onPlayerESPMinecraftClient(PlayerESP playerESP, MinecraftClient minecraftClient) {
      boolean flagx = flag;
      boolean flag1 = flag2;
      flag = false;
      flag2 = false;
      if (simpleFramebuffer3 != null && simpleFramebuffer4 != null) {
         if (playerESP != null && (flagx || flag1)) {
            if (!ResourceManagerHooks.isFlag4()) {
               ShaderProgram shaderprogram;
               ShaderProgram shaderprogram1;
               try {
                  shaderprogram = minecraftClient.getShaderLoader().getOrCreateProgram(shaderProgramKey);
                  shaderprogram1 = minecraftClient.getShaderLoader().getOrCreateProgram(shaderProgramKey2);
               } catch (Throwable throwable) {
                  return;
               }

               if (shaderprogram != null && shaderprogram1 != null) {
                  if (shaderprogram != shaderProgram) {
                     shaderProgram = shaderprogram;
                     glUniform = shaderprogram.getUniform("MaskSize");
                     glUniform2 = shaderprogram.getUniform("Direction");
                     glUniform3 = shaderprogram.getUniform("Radius");
                  }

                  if (shaderprogram1 != shaderProgram2) {
                     shaderProgram2 = shaderprogram1;
                     glUniform4 = shaderprogram1.getUniform("OutlineColor");
                     glUniform5 = shaderprogram1.getUniform("OutlineColorTop");
                     glUniform6 = shaderprogram1.getUniform("FillColor");
                     glUniform7 = shaderprogram1.getUniform("Intensity");
                     glUniform11 = shaderprogram1.getUniform("GradientSpeed");
                     glUniform8 = shaderprogram1.getUniform("FillAlpha");
                     glUniform9 = shaderprogram1.getUniform("Time");
                     glUniform10 = shaderprogram1.getUniform("FlameStrength");
                  }

                  int i = simpleFramebuffer3.textureWidth;
                  int j = simpleFramebuffer3.textureHeight;
                  RenderSystem.disableDepthTest();
                  RenderSystem.depthMask(false);
                  boolean flag2x = playerESP.check6();
                  float f = flag2x ? playerESP.getFloat3() : 0.0F;
                  float f1 = (float)playerESP.getDouble3();
                  if (flag2x) {
                     f1 *= playerESP.getFloat();
                  }

                  float f2 = (float)playerESP.getDouble();
                  float f3 = (float)playerESP.getDouble2();
                  float f4 = f1 / 2.0F;
                  boolean flag3 = playerESP.check3();
                  float f5 = flag3 ? playerESP.getFloat2() : 0.0F;
                  ColorSetting colorsetting = playerESP.getColorZalivkiSilueta();
                  if (flagx && simpleFramebuffer != null) {
                     ColorSetting colorsetting1 = playerESP.getColorSvecheniya();
                     ColorSetting colorsetting2 = flag3 ? playerESP.getVtoroyColor() : null;
                     SimpleFramebuffer simpleframebuffer = simpleFramebuffer;
                     onMinecraftClientShaderProgramFloatFloatShaderProgramColorSettingFloatFloatFloatColorSettingIntIntColorSettingSimpleFramebuffer(
                        minecraftClient, shaderprogram, f3, f4, shaderprogram1, colorsetting2, f5, f2, f, colorsetting1, i, j, colorsetting, simpleframebuffer
                     );
                  }

                  if (flag1 && simpleFramebuffer2 != null) {
                     ColorSetting colorsetting3 = playerESP.getColorSvecheniyaDruzey();
                     ColorSetting colorsetting4 = flag3 ? playerESP.getVtoroyColorDruzey() : null;
                     SimpleFramebuffer simpleframebuffer1 = simpleFramebuffer2;
                     onMinecraftClientShaderProgramFloatFloatShaderProgramColorSettingFloatFloatFloatColorSettingIntIntColorSettingSimpleFramebuffer(
                        minecraftClient, shaderprogram, f3, f4, shaderprogram1, colorsetting4, f5, f2, f, colorsetting3, i, j, colorsetting, simpleframebuffer1
                     );
                  }

                  RenderSystem.depthMask(true);
                  RenderSystem.enableDepthTest();
                  RenderSystem.disableBlend();
                  RenderSystem.defaultBlendFunc();
               }
            }
         }
      }
   }
}
