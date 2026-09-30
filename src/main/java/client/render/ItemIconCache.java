package client.render;

import client.data.LruCache;
import client.module.Feature;
import client.util.IconRenderFlag;
import client.util.ItemKey;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class ItemIconCache {
   private static final float value = 16.0F;
   private static final int value2 = 15728880;
   private static final Quaternionf quaternionf = new Quaternionf();
   private static final MatrixStack matrixStack = new MatrixStack();
   private static DrawContext drawContext;
   private static final int value3 = 256;
   private static final long time = 5000000000L;
   private static final Map<ItemKey, ItemRenderEntry> map = new LruCache(64, 0.75F, true);
   private static final ItemRenderState itemRenderState = new ItemRenderState();
   private static final int value4 = 64;
   private static final int value5 = 64;
   private static final Map<ItemKey, SimpleFramebuffer> map2 = new FramebufferCache(16, 0.75F, true);
   private static final Map<ItemKey, ItemStack> map3 = new LinkedHashMap<>();
   private static SimpleFramebuffer simpleFramebuffer;
   private static ByteBuffer byteBuffer;
   private static int value6;
   private static boolean flag;
   private static int value7 = 0;
   private static DrawContext drawContext2 = null;
   private static float value8 = 1.0F;
   private static int value9 = 0;
   private static float value10 = 1.0F;

   private ItemIconCache() {
   }

   public static void update() {
      map.clear();

      for (SimpleFramebuffer simpleframebuffer : map2.values()) {
         try {
            simpleframebuffer.delete();
         } catch (Throwable throwable) {
         }
      }

      map2.clear();
      map3.clear();
   }

   private static void update2() {
      GL11.glDepthRange(0.0, 1.0);
      RenderSystem.depthMask(flag);
      GL11.glDepthFunc(value6);
   }

   public static void render(DrawContext drawContext) {
      if (!map3.isEmpty() && drawContext != null && Feature.mc.getFramebuffer() != null) {
         Iterator iterator = map3.entrySet().iterator();
         int i = 4;

         try {
            while (iterator.hasNext() && i-- > 0) {
               Entry entry = (Entry)iterator.next();
               iterator.remove();

               try {
                  SimpleFramebuffer simpleframebuffer = getSimpleFramebufferByDrawContextItemStack(drawContext, (ItemStack)entry.getValue());
                  if (simpleframebuffer != null) {
                     map2.put((ItemKey)entry.getKey(), simpleframebuffer);
                  }
               } catch (Throwable throwable) {
               }
            }
         } finally {
            Feature.mc.getFramebuffer().beginWrite(true);
         }
      }
   }

   public static void update3() {
      if (value7 != 0) {
         if (--value7 == 0) {
            if (drawContext2 != null) {
               drawContext2.draw();
            }

            if (value8 < 1.0F) {
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }

            drawContext2 = null;
            value8 = 1.0F;
         }
      }
   }

   public static void update4() {
      if (value9 != 0) {
         if (--value9 == 0) {
            Immediate immediate = Feature.mc.getBufferBuilders().getEntityVertexConsumers();
            update5();

            try {
               immediate.draw();
            } finally {
               update2();
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            value10 = 1.0F;
         }
      }
   }

   public static void onFloatFloatFloatItemStackMatrix4fFloat(float value, float value2, float value3, ItemStack itemStack, Matrix4f matrix4f, float value4) {
      if (itemStack != null && !itemStack.isEmpty() && !(value2 <= 0.001F) && !(value <= 0.0F)) {
         if (drawContext != null) {
            DrawContext drawcontext = drawContext;
            render(drawcontext, matrix4f, value, itemStack, value4, value3, value2);
         } else {
            int i = getIntByItemStack(itemStack);
            if (i != 0) {
               byte b0 = -1;
               float f7 = 0.0F;
               float f6 = 0.0F;
               float f5 = 0.0F;
               float f4 = 0.0F;
               float f3 = 0.0F;
               float f2 = 1.0F;
               float f1 = 1.0F;
               float f = 0.0F;
               RoundedTextureShader.onFloatIntIntFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
                  value4, i, b0, value3, value, f3, f2, value, value2, f6, f5, f7, f1, f4, matrix4f, f
               );
            } else {
               Immediate immediate = Feature.mc.getBufferBuilders().getEntityVertexConsumers();
               ItemRenderState itemrenderstate = getItemRenderStateByItemStack(itemStack);
               MatrixStack matrixstack = matrixStack;
               matrixstack.loadIdentity();
               matrixstack.peek().getPositionMatrix().set(matrix4f);
               matrixstack.translate(value3 + value / 2.0F, value4 + value / 2.0F, 0.0F);
               boolean flagx = itemStack.getItem() instanceof BlockItem blockitem && blockitem.getBlock() instanceof AbstractSkullBlock;
               float f8 = value / 16.0F;
               matrixstack.scale(16.0F * f8, -16.0F * f8, 16.0F * f8);
               if (flagx && !RotationBuffer.isFlag()) {
                  matrixstack.multiply(quaternionf.rotationY((float) Math.PI));
               }

               boolean flag1 = value2 < 1.0F;
               if (flag1) {
                  immediate.draw();
                  RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, value2);
                  IconRenderFlag.setFlag2();
               }

               update5();

               try {
                  itemrenderstate.render(matrixstack, immediate, 15728880, OverlayTexture.DEFAULT_UV);
                  immediate.draw();
               } finally {
                  update2();
                  if (flag1) {
                     IconRenderFlag.setFlag();
                     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                  }
               }
            }
         }
      }
   }

   public static void setDrawContext(DrawContext drawContext2) {
      drawContext = drawContext2;
   }

   public static void onFloatDrawContext(float value, DrawContext drawContext) {
      if (value7++ == 0) {
         drawContext2 = drawContext;
         value8 = value;
         drawContext.draw();
         RenderSystem.enableBlend();
         if (value < 1.0F) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, value);
         }
      }
   }

   public static void onFloatDrawContextFloatItemStackFloat(float value, DrawContext drawContext, float value2, ItemStack itemStack, float value3) {
      float f = 16.0F;
      onFloatItemStackFloatFloatFloatDrawContext(f, itemStack, value, value3, value2, drawContext);
   }

   private static void render(DrawContext drawContext, Matrix4f matrix4f, float value, ItemStack itemStack, float value2, float value3, float value4) {
      MatrixStack matrixstack = drawContext.getMatrices();
      matrixstack.push();

      try {
         matrixstack.peek().getPositionMatrix().set(matrix4f);
         matrixstack.translate(value3, value2, 0.0F);
         if (value != 16.0F) {
            float f = value / 16.0F;
            matrixstack.scale(f, f, 1.0F);
         }

         boolean flagx = value4 < 1.0F;
         if (flagx) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, value4);
            IconRenderFlag.setFlag2();
         }

         try {
            drawContext.drawItem(itemStack, 0, 0);
            drawContext.draw();
         } finally {
            if (flagx) {
               IconRenderFlag.setFlag();
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
         }
      } finally {
         matrixstack.pop();
      }
   }

   public static void setFloat(float value) {
      if (value9++ == 0) {
         value10 = value;
         if (value < 1.0F) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, value);
         }
      }
   }

   public static void onFloatFloatItemStackMatrix4fFloat(float value, float value2, ItemStack itemStack, Matrix4f matrix4f, float value3) {
      if (itemStack != null && !itemStack.isEmpty() && !(value2 <= 0.0F)) {
         if (value9 == 0) {
            float f = 1.0F;
            onFloatFloatFloatItemStackMatrix4fFloat(value2, f, value, itemStack, matrix4f, value3);
         } else {
            int i = getIntByItemStack(itemStack);
            if (i != 0) {
               float f9 = value10;
               byte b0 = -1;
               float f8 = 0.0F;
               float f7 = 0.0F;
               float f6 = 0.0F;
               float f5 = 0.0F;
               float f4 = 0.0F;
               float f3 = 1.0F;
               float f2 = 1.0F;
               float f1 = 0.0F;
               RoundedTextureShader.onFloatIntIntFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
                  value3, i, b0, value, value2, f4, f3, value2, f9, f7, f6, f8, f2, f5, matrix4f, f1
               );
            } else {
               Immediate immediate = Feature.mc.getBufferBuilders().getEntityVertexConsumers();
               ItemRenderState itemrenderstate = getItemRenderStateByItemStack(itemStack);
               MatrixStack matrixstack = matrixStack;
               matrixstack.loadIdentity();
               matrixstack.peek().getPositionMatrix().set(matrix4f);
               matrixstack.translate(value + value2 / 2.0F, value3 + value2 / 2.0F, 0.0F);
               boolean flagx = itemStack.getItem() instanceof BlockItem blockitem && blockitem.getBlock() instanceof AbstractSkullBlock;
               float f10 = value2 / 16.0F;
               matrixstack.scale(16.0F * f10, -16.0F * f10, 16.0F * f10);
               if (flagx && !RotationBuffer.isFlag()) {
                  matrixstack.multiply(quaternionf.rotationY((float) Math.PI));
               }

               itemrenderstate.render(matrixstack, immediate, 15728880, OverlayTexture.DEFAULT_UV);
            }
         }
      }
   }

   private static void onIntIntIntIntIntIntIntIntIntInt(int count, int count2, int count3, int count4, int count5, int count6, int count7, int count8, int count9, int count10) {
      PixelReader.onIntIntIntIntIntIntIntIntIntInt(count10, count, count3, count9, count4, count7, count2, count6, count5, count8);
   }

   private static boolean isSimpleFramebuffer(SimpleFramebuffer simpleFramebuffer) {
      try {
         if (byteBuffer == null) {
            byteBuffer = BufferUtils.createByteBuffer(16384);
         }

         byteBuffer.clear();
         GlStateManager._glBindFramebuffer(36008, simpleFramebuffer.fbo);
         GL11.glReadPixels(0, 0, 64, 64, 6408, 5121, byteBuffer);

         for (int b0 = 3; b0 < 16384; b0 += 4) {
            if (byteBuffer.get(b0) != 0) {
               return true;
            }
         }

         return false;
      } catch (Throwable throwable) {
         return true;
      }
   }

   private static SimpleFramebuffer getSimpleFramebufferByDrawContextItemStack(DrawContext drawContext, ItemStack itemStack) {
      Framebuffer framebuffer = Feature.mc.getFramebuffer();
      int i = framebuffer.textureHeight;
      if (framebuffer.textureWidth >= 64 && i >= 64) {
         float f = (float)(64.0 / Feature.mc.getWindow().getScaleFactor());
         drawContext.draw();
         if (simpleFramebuffer == null) {
            simpleFramebuffer = new SimpleFramebuffer(64, 64, false);
         }

         int k2 = i - 64;
         byte b5 = 64;
         byte b4 = 64;
         byte b3 = 0;
         byte b2 = 0;
         int l = simpleFramebuffer.fbo;
         byte b1 = 64;
         int k = k2;
         byte b0 = 0;
         int j = framebuffer.fbo;
         onIntIntIntIntIntIntIntIntIntInt(b2, j, b5, b4, b1, b0, i, b3, k, l);
         framebuffer.beginWrite(true);
         SimpleFramebuffer simpleframebuffer = null;

         try {
            GL11.glEnable(3089);
            GL11.glScissor(0, i - 64, 64, 64);
            GlStateManager._clearColor(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glClear(16640);
            GL11.glDisable(3089);
            MatrixStack matrixstack = drawContext.getMatrices();
            matrixstack.push();

            try {
               float f1 = f / 16.0F;
               matrixstack.scale(f1, f1, 1.0F);
               drawContext.drawItem(itemStack, 0, 0);
               drawContext.draw();
            } finally {
               matrixstack.pop();
            }

            simpleframebuffer = new SimpleFramebuffer(64, 64, false);
            k2 = i - 64;
            byte b11 = 64;
            byte b10 = 64;
            byte b9 = 0;
            byte b8 = 0;
            int k1 = simpleframebuffer.fbo;
            byte b7 = 64;
            int j1 = k2;
            byte b6 = 0;
            int i1 = framebuffer.fbo;
            onIntIntIntIntIntIntIntIntIntInt(b8, i1, b11, b10, b7, b6, i, b9, j1, k1);
         } finally {
            int l2 = i - 64;
            byte b17 = 64;
            int j2 = l2;
            byte b16 = 0;
            int i2 = framebuffer.fbo;
            byte b15 = 64;
            byte b14 = 64;
            byte b13 = 0;
            byte b12 = 0;
            int l1 = simpleFramebuffer.fbo;
            onIntIntIntIntIntIntIntIntIntInt(b16, l1, i, b17, b14, b12, b15, j2, b13, i2);
            framebuffer.beginWrite(true);
         }

         boolean flagx = isSimpleFramebuffer(simpleframebuffer);
         framebuffer.beginWrite(true);
         if (!flagx) {
            try {
               simpleframebuffer.delete();
            } catch (Throwable throwable) {
            }

            framebuffer.beginWrite(true);
            return null;
         } else {
            return simpleframebuffer;
         }
      } else {
         return null;
      }
   }

   private static ItemKey getItemKeyByItemStack(ItemStack itemStack) {
      int i = 0;

      for (Component component : itemStack.getComponents()) {
         if (component.type() != DataComponentTypes.DAMAGE) {
            i += component.hashCode();
         }
      }

      return new ItemKey(itemStack.getItem(), i, 0, 0);
   }

   private static int getIntByItemStack(ItemStack itemStack) {
      if (!isItemStack(itemStack)) {
         return 0;
      } else {
         ItemKey itemkey = getItemKeyByItemStack(itemStack);
         SimpleFramebuffer simpleframebuffer = map2.get(itemkey);
         if (simpleframebuffer != null) {
            return simpleframebuffer.getColorAttachment();
         } else {
            if (map3.size() < 16 && !map3.containsKey(itemkey)) {
               map3.put(itemkey, itemStack.copy());
            }

            return 0;
         }
      }
   }

   public static void onFloatItemStackFloatFloatFloatDrawContext(float value, ItemStack itemStack, float value2, float value3, float value4, DrawContext drawContext) {
      if (itemStack != null && !itemStack.isEmpty() && !(value2 <= 0.001F) && !(value <= 0.0F)) {
         if (value7 > 0) {
            MatrixStack matrixstack1 = drawContext.getMatrices();
            matrixstack1.push();
            matrixstack1.translate(value4, value3, 0.0F);
            if (value != 16.0F) {
               float f1 = value / 16.0F;
               matrixstack1.scale(f1, f1, 1.0F);
            }

            drawContext.drawItem(itemStack, 0, 0);
            matrixstack1.pop();
         } else {
            drawContext.draw();
            MatrixStack matrixstack = drawContext.getMatrices();
            matrixstack.push();
            matrixstack.translate(value4, value3, 0.0F);
            if (value != 16.0F) {
               float f = value / 16.0F;
               matrixstack.scale(f, f, 1.0F);
            }

            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, value2);

            try {
               drawContext.drawItem(itemStack, 0, 0);
               drawContext.draw();
            } finally {
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               matrixstack.pop();
            }
         }
      }
   }

   private static ItemRenderState getItemRenderStateByItemStack(ItemStack itemStack) {
      if (!isItemStack(itemStack)) {
         Feature.mc.getItemModelManager().update(itemRenderState, itemStack, ModelTransformationMode.GUI, false, Feature.mc.world, null, 0);
         return itemRenderState;
      } else {
         ComponentMap componentmap = itemStack.getComponents();
         ItemKey itemkey = new ItemKey(itemStack.getItem(), componentmap.hashCode(), itemStack.getCount(), itemStack.getDamage());
         long i = System.nanoTime();
         ItemRenderEntry itemrenderentry = map.get(itemkey);
         if (itemrenderentry != null && i - itemrenderentry.time < 5000000000L) {
            return itemrenderentry.itemRenderState;
         } else {
            ItemRenderState itemrenderstate = itemrenderentry != null ? itemrenderentry.itemRenderState : new ItemRenderState();
            Feature.mc.getItemModelManager().update(itemrenderstate, itemStack, ModelTransformationMode.GUI, false, Feature.mc.world, null, 0);
            if (itemrenderentry != null) {
               itemrenderentry.time = i;
            } else {
               map.put(itemkey, new ItemRenderEntry(itemrenderstate, i));
            }

            return itemrenderstate;
         }
      }
   }

   private static boolean isItemStack(ItemStack itemStack) {
      Item item = itemStack.getItem();
      if (item == Items.PLAYER_HEAD) {
         return false;
      } else {
         ComponentMap componentmap = itemStack.getComponents();
         return componentmap.contains(DataComponentTypes.PROFILE) ? false : !componentmap.contains(DataComponentTypes.CUSTOM_DATA);
      }
   }

   private static void update5() {
      value6 = GL11.glGetInteger(2932);
      flag = GL11.glGetBoolean(2930);
      GL11.glDepthFunc(515);
      RenderSystem.depthMask(true);
      GL11.glDepthRange(0.0, 0.05);
   }
}
