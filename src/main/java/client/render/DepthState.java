package client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Set;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class DepthState {
   private static final Set<Item> set = Set.of(
      Items.DIAMOND,
      Items.EMERALD,
      Items.GOLD_INGOT,
      Items.IRON_INGOT,
      Items.NETHERITE_INGOT,
      Items.DIAMOND_SWORD,
      Items.DIAMOND_PICKAXE,
      Items.DIAMOND_AXE,
      Items.DIAMOND_SHOVEL,
      Items.DIAMOND_HOE,
      Items.DIAMOND_HELMET,
      Items.DIAMOND_CHESTPLATE,
      Items.DIAMOND_LEGGINGS,
      Items.DIAMOND_BOOTS,
      Items.NETHERITE_SWORD,
      Items.NETHERITE_PICKAXE,
      Items.NETHERITE_AXE,
      Items.NETHERITE_SHOVEL,
      Items.NETHERITE_HOE,
      Items.NETHERITE_HELMET,
      Items.NETHERITE_CHESTPLATE,
      Items.NETHERITE_LEGGINGS,
      Items.NETHERITE_BOOTS,
      Items.ENCHANTED_GOLDEN_APPLE,
      Items.GOLDEN_APPLE,
      Items.TOTEM_OF_UNDYING,
      Items.ELYTRA,
      Items.SHULKER_BOX,
      Items.WHITE_SHULKER_BOX,
      Items.ORANGE_SHULKER_BOX,
      Items.MAGENTA_SHULKER_BOX,
      Items.LIGHT_BLUE_SHULKER_BOX,
      Items.YELLOW_SHULKER_BOX,
      Items.LIME_SHULKER_BOX,
      Items.PINK_SHULKER_BOX,
      Items.GRAY_SHULKER_BOX,
      Items.LIGHT_GRAY_SHULKER_BOX,
      Items.CYAN_SHULKER_BOX,
      Items.PURPLE_SHULKER_BOX,
      Items.BLUE_SHULKER_BOX,
      Items.BROWN_SHULKER_BOX,
      Items.GREEN_SHULKER_BOX,
      Items.RED_SHULKER_BOX,
      Items.BLACK_SHULKER_BOX
   );

   public static void update() {
      RenderSystem.depthMask(true);
      GL11.glDepthFunc(515);
      RenderSystem.disableBlend();
      RenderSystem.enableCull();
   }

   public static void onFloatFloatFloatFloatFloatMatrix4fFloatBufferBuilderFloatFloatFloatFloat(
      float value,
      float value2,
      float value3,
      float value4,
      float value5,
      Matrix4f matrix4f,
      float value6,
      BufferBuilder bufferBuilder,
      float value7,
      float value8,
      float value9,
      float value10
   ) {
      onFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloatFloatMatrix4fFloat(value3, value, value3, value9, value6, value4, value8, bufferBuilder, value2, value7, matrix4f, value5);
      onFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloatFloatMatrix4fFloat(value10, value, value10, value9, value6, value4, value8, bufferBuilder, value2, value7, matrix4f, value5);
      onFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloatFloatMatrix4fFloat(value3, value, value10, value9, value6, value4, value2, bufferBuilder, value2, value7, matrix4f, value5);
      onFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloatFloatMatrix4fFloat(value3, value, value10, value9, value6, value4, value8, bufferBuilder, value8, value7, matrix4f, value5);
      onFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloatFloatMatrix4fFloat(value3, value, value10, value9, value6, value7, value8, bufferBuilder, value2, value7, matrix4f, value5);
      onFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloatFloatMatrix4fFloat(value3, value, value10, value9, value6, value4, value8, bufferBuilder, value2, value4, matrix4f, value5);
   }

   public static void onFloatFloatFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloatFloatFloat(
      float value,
      float value2,
      float value3,
      float value4,
      Matrix4f matrix4f,
      float value5,
      float value6,
      float value7,
      BufferBuilder bufferBuilder,
      float value8,
      float value9,
      float value10,
      float value11
   ) {
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value, value6, value4, value2, value9, value6, value3, value8, bufferBuilder, matrix4f, value5, value11, value4
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value, value10, value4, value2, value9, value6, value3, value8, bufferBuilder, matrix4f, value, value11, value4
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value5, value10, value4, value2, value9, value10, value3, value8, bufferBuilder, matrix4f, value, value11, value4
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value5, value6, value4, value2, value9, value10, value3, value8, bufferBuilder, matrix4f, value5, value11, value4
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value, value6, value7, value2, value9, value6, value3, value8, bufferBuilder, matrix4f, value5, value11, value7
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value, value10, value7, value2, value9, value6, value3, value8, bufferBuilder, matrix4f, value, value11, value7
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value5, value10, value7, value2, value9, value10, value3, value8, bufferBuilder, matrix4f, value, value11, value7
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value5, value6, value7, value2, value9, value10, value3, value8, bufferBuilder, matrix4f, value5, value11, value7
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value5, value6, value7, value2, value9, value6, value3, value8, bufferBuilder, matrix4f, value5, value11, value4
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value, value6, value7, value2, value9, value6, value3, value8, bufferBuilder, matrix4f, value, value11, value4
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value, value10, value7, value2, value9, value10, value3, value8, bufferBuilder, matrix4f, value, value11, value4
      );
      onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
         value5, value10, value7, value2, value9, value10, value3, value8, bufferBuilder, matrix4f, value5, value11, value4
      );
   }

   public static void render(MatrixStack matrixStack, float value, float value2, Vec3d vec3d, boolean flag, float value3, Vec3d vec3d2, float value4) {
      if (flag) {
         GL11.glDepthFunc(519);
         RenderSystem.depthMask(false);
      }

      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      float f6 = (float)vec3d.x;
      float f7 = (float)vec3d.y;
      float f8 = (float)vec3d.z;
      float f9 = (float)vec3d2.x;
      float f10 = (float)vec3d2.y;
      float f5 = (float)vec3d2.z;
      float f4 = f10;
      float f3 = f9;
      float f2 = f8;
      float f1 = f7;
      float f = f6;
      onFloatFloatFloatFloatFloatMatrix4fFloatBufferBuilderFloatFloatFloatFloat(value3, f2, f1, f3, value, matrix4f, value4, bufferbuilder, f, f5, value2, f4);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      if (flag) {
         RenderSystem.depthMask(true);
         GL11.glDepthFunc(515);
      }

      RenderSystem.disableBlend();
      RenderSystem.enableCull();
   }

   public static void onFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloatFloatMatrix4fFloat(
      float value,
      float value2,
      float value3,
      float value4,
      float value5,
      float value6,
      float value7,
      BufferBuilder bufferBuilder,
      float value8,
      float value9,
      Matrix4f matrix4f,
      float value10
   ) {
      boolean flag = Math.abs(value6 - value9) > 1.0E-4F;
      boolean flag1 = Math.abs(value3 - value) > 1.0E-4F;
      boolean flag2 = Math.abs(value7 - value8) > 1.0E-4F;
      if (flag && flag1) {
         bufferBuilder.vertex(matrix4f, value9, value, value8).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value6, value, value8).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value6, value3, value8).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value9, value, value8).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value6, value3, value8).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value9, value3, value8).color(value4, value2, value10, value5);
      } else if (flag && flag2) {
         bufferBuilder.vertex(matrix4f, value9, value, value8).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value6, value, value8).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value6, value, value7).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value9, value, value8).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value6, value, value7).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value9, value, value7).color(value4, value2, value10, value5);
      } else if (flag1 && flag2) {
         bufferBuilder.vertex(matrix4f, value9, value, value8).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value9, value, value7).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value9, value3, value7).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value9, value, value8).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value9, value3, value7).color(value4, value2, value10, value5);
         bufferBuilder.vertex(matrix4f, value9, value3, value8).color(value4, value2, value10, value5);
      }
   }

   public static void onFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fFloatFloatFloat(
      float value,
      float value2,
      float value3,
      float value4,
      float value5,
      float value6,
      float value7,
      float value8,
      BufferBuilder bufferBuilder,
      Matrix4f matrix4f,
      float value9,
      float value10,
      float value11
   ) {
      float f = value - value9;
      float f1 = value3 - value11;
      float f2 = value2 - value6;
      float f3 = f * f + f1 * f1 + f2 * f2;
      if (!(f3 < 1.0E-6F)) {
         float f4 = (float)Math.sqrt(f3);
         float f5 = f / f4;
         float f6 = f1 / f4;
         float f7 = f2 / f4;
         float f8;
         float f9;
         float f10;
         if (Math.abs(f5) <= Math.abs(f6) && Math.abs(f5) <= Math.abs(f7)) {
            f8 = 1.0F;
            f9 = 0.0F;
            f10 = 0.0F;
         } else if (Math.abs(f6) <= Math.abs(f7)) {
            f8 = 0.0F;
            f9 = 1.0F;
            f10 = 0.0F;
         } else {
            f8 = 0.0F;
            f9 = 0.0F;
            f10 = 1.0F;
         }

         float f11 = f6 * f10 - f7 * f9;
         float f12 = f7 * f8 - f5 * f10;
         float f13 = f5 * f9 - f6 * f8;
         float f14 = (float)Math.sqrt(f11 * f11 + f12 * f12 + f13 * f13);
         f11 *= value5 / f14;
         f12 *= value5 / f14;
         f13 *= value5 / f14;
         float f15 = f6 * f13 - f7 * f12;
         float f16 = f7 * f11 - f5 * f13;
         float f17 = f5 * f12 - f6 * f11;
         float f18 = (float)Math.sqrt(f15 * f15 + f16 * f16 + f17 * f17);
         f15 *= value5 / f18;
         f16 *= value5 / f18;
         f17 *= value5 / f18;
         bufferBuilder.vertex(matrix4f, value9 + f11, value11 + f12, value6 + f13).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value + f11, value3 + f12, value2 + f13).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value - f11, value3 - f12, value2 - f13).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value9 + f11, value11 + f12, value6 + f13).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value - f11, value3 - f12, value2 - f13).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value9 - f11, value11 - f12, value6 - f13).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value9 + f15, value11 + f16, value6 + f17).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value + f15, value3 + f16, value2 + f17).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value - f15, value3 - f16, value2 - f17).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value9 + f15, value11 + f16, value6 + f17).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value - f15, value3 - f16, value2 - f17).color(value10, value7, value8, value4);
         bufferBuilder.vertex(matrix4f, value9 - f15, value11 - f16, value6 - f17).color(value10, value7, value8, value4);
      }
   }

   public static void onFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloatFloatFloat(
      float value, float value2, Matrix4f matrix4f, float value3, float value4, float value5, BufferBuilder bufferBuilder, float value6, float value7, float value8, float value9
   ) {
      bufferBuilder.vertex(matrix4f, value4 + value7, value3, value).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4 + value7, value8, value).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4 - value7, value8, value).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4 + value7, value3, value).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4 - value7, value8, value).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4 - value7, value3, value).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4, value3, value + value7).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4, value8, value + value7).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4, value8, value - value7).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4, value3, value + value7).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4, value8, value - value7).color(value9, value2, value6, value5);
      bufferBuilder.vertex(matrix4f, value4, value3, value - value7).color(value9, value2, value6, value5);
   }

   public static float[] getFloatArrayByInt(int count) {
      return new float[]{(count >> 16 & 0xFF) / 255.0F, (count >> 8 & 0xFF) / 255.0F, (count & 0xFF) / 255.0F, (count >> 24 & 0xFF) / 255.0F};
   }

   public static boolean isVec3dDoubleVec3d(Vec3d vec3d, double value, Vec3d vec3d2) {
      double d0 = value * value;
      return vec3d.squaredDistanceTo(vec3d2) <= d0;
   }

   public static boolean isItem(Item item2) {
      return set.contains(item2);
   }

   public static Vec3d getVec3dByFloatEntityVec3d(float value, Entity entity2, Vec3d vec3d) {
      double d0 = MathHelper.lerp(value, entity2.prevX, entity2.getX()) - vec3d.x;
      double d1 = MathHelper.lerp(value, entity2.prevY, entity2.getY()) - vec3d.y;
      double d2 = MathHelper.lerp(value, entity2.prevZ, entity2.getZ()) - vec3d.z;
      return new Vec3d(d0, d1, d2);
   }

   public static void onBufferBuilder(BufferBuilder bufferBuilder) {
      BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
      RenderSystem.depthMask(true);
      GL11.glDepthFunc(515);
      RenderSystem.disableBlend();
      RenderSystem.lineWidth(1.0F);
   }

   public static void onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
      BufferBuilder bufferBuilder,
      float value,
      float value2,
      float value3,
      float value4,
      float value5,
      float value6,
      float value7,
      float value8,
      float value9,
      Matrix4f matrix4f,
      float value10
   ) {
      bufferBuilder.vertex(matrix4f, value5, value8, value9).color(value6, value4, value, value3);
      bufferBuilder.vertex(matrix4f, value10, value2, value7).color(value6, value4, value, value3);
   }

   public static BufferBuilder getBufferBuilderByFloat(float value) {
      GL11.glDepthFunc(519);
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      RenderSystem.lineWidth(value);
      return Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
   }

   public static void onEntryMatrixStack(Entry entry, MatrixStack matrixStack) {
      if (matrixStack != null) {
         while (!matrixStack.isEmpty() && matrixStack.peek() != entry) {
            matrixStack.pop();
         }
      }
   }

   public static void render(MatrixStack matrixStack) {
      Object object = null;
      onEntryMatrixStack((Entry)object, matrixStack);
   }

   public static void onMatrix4fFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloat(
      Matrix4f matrix4f, float value, float value2, float value3, float value4, float value5, float value6, float value7, float value8, BufferBuilder bufferBuilder, float value9
   ) {
      bufferBuilder.vertex(matrix4f, value7 + value5, value2, value4).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7 + value5, value2, value9).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7 - value5, value2, value9).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7 + value5, value2, value4).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7 - value5, value2, value9).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7 - value5, value2, value4).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7, value2 + value5, value4).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7, value2 + value5, value9).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7, value2 - value5, value9).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7, value2 + value5, value4).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7, value2 - value5, value9).color(value, value8, value3, value6);
      bufferBuilder.vertex(matrix4f, value7, value2 - value5, value4).color(value, value8, value3, value6);
   }

   public static void update2() {
      GL11.glDepthFunc(519);
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
   }

   public static void onFloatFloatFloatMatrix4fFloatFloatFloatFloatFloatBufferBuilderFloat(
      float value, float value2, float value3, Matrix4f matrix4f, float value4, float value5, float value6, float value7, float value8, BufferBuilder bufferBuilder, float value9
   ) {
      bufferBuilder.vertex(matrix4f, value3, value4, value6 + value9).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value2, value4, value6 + value9).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value2, value4, value6 - value9).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value3, value4, value6 + value9).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value2, value4, value6 - value9).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value3, value4, value6 - value9).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value3, value4 + value9, value6).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value2, value4 + value9, value6).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value2, value4 - value9, value6).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value3, value4 + value9, value6).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value2, value4 - value9, value6).color(value, value8, value5, value7);
      bufferBuilder.vertex(matrix4f, value3, value4 - value9, value6).color(value, value8, value5, value7);
   }

   public static void onFloatFloatFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloat(
      float value, float value2, float value3, float value4, Matrix4f matrix4f, float value5, float value6, float value7, BufferBuilder bufferBuilder, float value8, float value9
   ) {
      float f16 = value6 - value7;
      float f17 = value5 - value7;
      float f18 = value8 + value7;
      float f3 = value5 + value7;
      float f2 = f18;
      float f1 = f17;
      float f = f16;
      onFloatMatrix4fFloatFloatFloatFloatFloatFloatBufferBuilderFloat(f, matrix4f, value4, f3, f2, value, value2, f1, bufferBuilder, value9);
      f16 = value6 - value7;
      f17 = value3 - value7;
      f18 = value8 + value7;
      float f7 = value3 + value7;
      float f6 = f18;
      float f5 = f17;
      float f4 = f16;
      onFloatMatrix4fFloatFloatFloatFloatFloatFloatBufferBuilderFloat(f4, matrix4f, value4, f7, f6, value, value2, f5, bufferBuilder, value9);
      f16 = value6 - value7;
      f17 = value5 + value7;
      f18 = value6 + value7;
      float f11 = value3 - value7;
      float f10 = f18;
      float f9 = f17;
      float f8 = f16;
      onFloatMatrix4fFloatFloatFloatFloatFloatFloatBufferBuilderFloat(f8, matrix4f, value4, f11, f10, value, value2, f9, bufferBuilder, value9);
      f16 = value8 - value7;
      f17 = value5 + value7;
      f18 = value8 + value7;
      float f15 = value3 - value7;
      float f14 = f18;
      float f13 = f17;
      float f12 = f16;
      onFloatMatrix4fFloatFloatFloatFloatFloatFloatBufferBuilderFloat(f12, matrix4f, value4, f15, f14, value, value2, f13, bufferBuilder, value9);
   }

   public static void onFloatMatrix4fFloatFloatFloatFloatFloatFloatBufferBuilderFloat(
      float value, Matrix4f matrix4f, float value2, float value3, float value4, float value5, float value6, float value7, BufferBuilder bufferBuilder, float value8
   ) {
      bufferBuilder.vertex(matrix4f, value, value7, 0.0F).color(value8, value5, value6, value2);
      bufferBuilder.vertex(matrix4f, value4, value7, 0.0F).color(value8, value5, value6, value2);
      bufferBuilder.vertex(matrix4f, value4, value3, 0.0F).color(value8, value5, value6, value2);
      bufferBuilder.vertex(matrix4f, value, value7, 0.0F).color(value8, value5, value6, value2);
      bufferBuilder.vertex(matrix4f, value4, value3, 0.0F).color(value8, value5, value6, value2);
      bufferBuilder.vertex(matrix4f, value, value3, 0.0F).color(value8, value5, value6, value2);
   }
}
