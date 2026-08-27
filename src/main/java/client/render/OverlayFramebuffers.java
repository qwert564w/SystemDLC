package client.render;

import b.Boot;
import client.concurrent.ResourceManagerHooks;
import client.module.Feature;
import client.module.client.StreamBypass;
import client.util.WindowUtil;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.Window;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public final class OverlayFramebuffers extends Feature {
   private static SimpleFramebuffer simpleFramebuffer;
   private static SimpleFramebuffer simpleFramebuffer2;
   private static final SimpleFramebuffer[] simpleFramebufferArray = new SimpleFramebuffer[2];
   private static int value235;
   private static int value236;
   private static int value237;
   private static Framebuffer framebuffer;
   private static Framebuffer framebuffer2;
   private static final Matrix4f matrix4f = new Matrix4f();
   private static final Matrix4f matrix4f2 = new Matrix4f();
   private static ProjectionType projectionType;
   private static boolean flag;

   private OverlayFramebuffers() {
   }

   public static void update() {
      framebuffer = null;
      framebuffer2 = null;

      for (int i = 0; i < simpleFramebufferArray.length; i++) {
         simpleFramebufferArray[i] = getSimpleFramebufferBySimpleFramebuffer(simpleFramebufferArray[i]);
      }

      simpleFramebuffer = getSimpleFramebufferBySimpleFramebuffer(simpleFramebuffer);
      simpleFramebuffer2 = getSimpleFramebufferBySimpleFramebuffer(simpleFramebuffer2);
      value236 = 0;
      value237 = 0;
   }

   public static Framebuffer getFramebuffer2() {
      return framebuffer2;
   }

   public static void update2() {
      GL20.glBlendEquationSeparate(32774, 32776);
   }

   public static void update3() {
      GL20.glBlendEquationSeparate(32774, 32774);
   }

   private static Framebuffer getFramebuffer() {
      if (simpleFramebuffer2 != null && simpleFramebuffer2.textureWidth == value236 && simpleFramebuffer2.textureHeight == value237) {
         return simpleFramebuffer2;
      } else {
         try {
            if (simpleFramebuffer2 == null) {
               simpleFramebuffer2 = new SimpleFramebuffer(value236, value237, true);
               simpleFramebuffer2.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            } else {
               simpleFramebuffer2.resize(value236, value237);
            }
         } catch (Throwable throwable) {
            simpleFramebuffer2 = null;
         }

         return simpleFramebuffer2;
      }
   }

   public static void update4() {
      if (StreamBypass.getStreamBypass() != null) {
         Window window = mc.getWindow();
         if (window != null) {
            int i = window.getFramebufferWidth();
            int j = window.getFramebufferHeight();
            if (i > 0 && j > 0) {
               if (window.getScaledWidth() > 0 && window.getScaledHeight() > 0) {
                  if (ResourceManagerHooks.isFlag3()) {
                     if (mc.getOverlay() == null) {
                        WindowUtil.setWindow(window);
                        if (Boot.sbCreate(GLFWNativeWin32.glfwGetWin32Window(window.getHandle()))) {
                           Framebuffer framebufferx = getFramebufferByIntInt(i, j);
                           if (framebufferx != null) {
                              try {
                                 onFramebufferWindow(framebufferx, window);
                                 if (Boot.sbBeginFrame()) {
                                    onIntInt(i, j);
                                 }

                                 if (StreamBypass.isFlag2()) {
                                    onFramebufferIntInt(framebufferx, i, j);
                                 }
                              } catch (Throwable throwable1) {
                              } finally {
                                 try {
                                    mc.getFramebuffer().endWrite();
                                 } catch (Throwable throwable) {
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void onInt(int count) {
      Window window = mc.getWindow();
      float f = window.getScaledWidth();
      float f1 = window.getScaledHeight();
      RenderSystem.enableBlend();
      update3();
      RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.colorMask(true, true, true, false);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX);
      RenderSystem.setShaderTexture(0, count);
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
      bufferbuilder.vertex(0.0F, 0.0F, 0.0F).texture(0.0F, 1.0F);
      bufferbuilder.vertex(0.0F, f1, 0.0F).texture(0.0F, 0.0F);
      bufferbuilder.vertex(f, f1, 0.0F).texture(1.0F, 0.0F);
      bufferbuilder.vertex(f, 0.0F, 0.0F).texture(1.0F, 1.0F);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      RenderSystem.colorMask(true, true, true, true);
      update2();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
   }

   public static void onScreenDrawContextIntIntFloat(Screen screen2, DrawContext drawContext, int count, int count2, float value) {
      if (simpleFramebuffer != null && screen2 != null) {
         Framebuffer framebufferx = getFramebuffer();
         if (framebufferx != null) {
            Framebuffer framebuffer1 = framebuffer;

            try {
               framebufferx.clear();
               framebufferx.beginWrite(true);
               framebuffer = framebufferx;
               screen2.render(drawContext, count, count2, value);
               drawContext.draw();
            } catch (Throwable throwable1) {
            } finally {
               framebuffer = framebuffer1;
            }

            try {
               simpleFramebuffer.beginWrite(true);
               onInt(framebufferx.getColorAttachment());
            } catch (Throwable throwable) {
            }
         }
      }
   }

   private static void onMatrix4fStack(Matrix4fStack matrix4fStack) {
      if (flag && projectionType != null) {
         RenderSystem.setProjectionMatrix(matrix4f, projectionType);
         matrix4fStack.set(matrix4f2);

         try {
            StreamBypass.update12();
         } catch (Throwable throwable) {
         }
      }
   }

   private static SimpleFramebuffer getSimpleFramebufferBySimpleFramebuffer(SimpleFramebuffer simpleFramebuffer) {
      if (simpleFramebuffer != null) {
         try {
            simpleFramebuffer.delete();
         } catch (Throwable throwable) {
         }
      }

      return null;
   }

   private static void onFramebufferIntInt(Framebuffer framebuffer, int count, int count2) {
      mc.getFramebuffer().endWrite();
      RenderSystem.viewport(0, 0, count, count2);
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      update3();
      RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX);
      RenderSystem.setShaderTexture(0, framebuffer.getColorAttachment());
      RenderSystem.backupProjectionMatrix();
      Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
      matrix4fstack.pushMatrix();

      try {
         RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(0.0F, 1.0F, 0.0F, 1.0F, -1.0F, 1.0F), ProjectionType.ORTHOGRAPHIC);
         matrix4fstack.identity();
         BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
         bufferbuilder.vertex(0.0F, 0.0F, 0.0F).texture(0.0F, 0.0F);
         bufferbuilder.vertex(1.0F, 0.0F, 0.0F).texture(1.0F, 0.0F);
         bufferbuilder.vertex(1.0F, 1.0F, 0.0F).texture(1.0F, 1.0F);
         bufferbuilder.vertex(0.0F, 1.0F, 0.0F).texture(0.0F, 1.0F);
         BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      } finally {
         matrix4fstack.popMatrix();
         RenderSystem.restoreProjectionMatrix();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
      }
   }

   public static Framebuffer getFramebuffer3() {
      return framebuffer;
   }

   private static void onIntInt(int count, int count2) {
      SimpleFramebuffer simpleframebuffer = getSimpleFramebufferByIntIntInt(value235, count, count2);
      if (simpleframebuffer != null) {
         value235 = (value235 + 1) % simpleFramebufferArray.length;
         simpleframebuffer.beginWrite(true);
         mc.getFramebuffer().draw(count, count2);
         long i = 0L;

         try {
            i = GL32.glFenceSync(37143, 0);
            GL11.glFlush();
         } catch (Throwable throwable) {
         }

         Boot.sbPublish(simpleframebuffer.getColorAttachment(), i);
      }
   }

   private static SimpleFramebuffer getSimpleFramebufferByIntIntInt(int count, int count2, int count3) {
      SimpleFramebuffer simpleframebuffer = simpleFramebufferArray[count];
      if (simpleframebuffer != null && simpleframebuffer.textureWidth == count2 && simpleframebuffer.textureHeight == count3) {
         return simpleframebuffer;
      } else {
         try {
            if (simpleframebuffer == null) {
               simpleframebuffer = new SimpleFramebuffer(count2, count3, false);
               simpleframebuffer.setClearColor(0.0F, 0.0F, 0.0F, 1.0F);
            } else {
               simpleframebuffer.resize(count2, count3);
            }
         } catch (Throwable throwable) {
            simpleframebuffer = null;
         }

         simpleFramebufferArray[count] = simpleframebuffer;
         return simpleframebuffer;
      }
   }

   public static void update5() {
      if (StreamBypass.getStreamBypass() != null) {
         matrix4f.set(RenderSystem.getProjectionMatrix());
         matrix4f2.set(RenderSystem.getModelViewStack());
         projectionType = RenderSystem.getProjectionType();
         flag = true;
      }
   }

   private static Framebuffer getFramebufferByIntInt(int count, int count2) {
      if (simpleFramebuffer != null && value236 == count && value237 == count2) {
         return simpleFramebuffer;
      } else {
         try {
            if (simpleFramebuffer == null) {
               simpleFramebuffer = new SimpleFramebuffer(count, count2, true);
               simpleFramebuffer.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            } else {
               simpleFramebuffer.resize(count, count2);
            }
         } catch (Throwable throwable) {
            simpleFramebuffer = getSimpleFramebufferBySimpleFramebuffer(simpleFramebuffer);
            return null;
         }

         value236 = count;
         value237 = count2;
         return simpleFramebuffer;
      }
   }

   private static void onFramebufferWindow(Framebuffer framebuffer3, Window window) {
      StreamBypass.setFlag2();
      framebuffer3.clear();
      framebuffer3.beginWrite(true);
      RenderSystem.backupProjectionMatrix();
      Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
      matrix4fstack.pushMatrix();

      try {
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         update2();
         framebuffer2 = mc.getFramebuffer();
         framebuffer = framebuffer3;
         framebuffer2.setTexFilter(9729);

         try {
            onMatrix4fStack(matrix4fstack);
            Matrix4f matrix4fx = new Matrix4f().setOrtho(0.0F, window.getScaledWidth(), window.getScaledHeight(), 0.0F, 1000.0F, 21000.0F);
            RenderSystem.setProjectionMatrix(matrix4fx, ProjectionType.ORTHOGRAPHIC);
            matrix4fstack.translation(0.0F, 0.0F, -11000.0F);
            DiffuseLighting.enableGuiDepthLighting();
            StreamBypass.update11();
         } finally {
            framebuffer = null;

            try {
               framebuffer2.setTexFilter(9728);
            } catch (Throwable throwable) {
            }

            framebuffer2 = null;
         }
      } finally {
         update3();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
         RenderSystem.enableDepthTest();
         matrix4fstack.popMatrix();
         RenderSystem.restoreProjectionMatrix();
      }
   }
}
