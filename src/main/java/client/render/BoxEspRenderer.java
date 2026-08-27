package client.render;

import client.api.EspRenderer;
import java.util.List;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class BoxEspRenderer implements EspRenderer {
   private boolean flag = true;
   private boolean flag2 = false;
   private float value = 20.0F;
   private float value2 = 1.0F;
   private boolean flag3 = true;
   private float value3 = 0.0F;
   private float value4 = 0.0F;
   private float value5 = 0.0F;
   private float value6 = 1.0F;

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   private void onFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fIntIntFloatFloatIntFloat(
      float value,
      float value2,
      float value3,
      float value4,
      float value5,
      float value6,
      float value7,
      BufferBuilder bufferBuilder,
      Matrix4f matrix4f,
      int count,
      int count2,
      float value8,
      float value9,
      int count3,
      float value10
   ) {
      float f = value7 + value9 * count;
      DepthState.onFloatFloatFloatMatrix4fFloatFloatFloatFloatFloatBufferBuilderFloat(value10, f, value7, matrix4f, value8, value, value2, value3, value6, bufferBuilder, value4);
      float f1 = value8 + value5 * count2;
      DepthState.onFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloatFloatFloat(value2, value6, matrix4f, value8, value7, value3, bufferBuilder, value, value4, f1, value10);
      float f2 = value2 + value9 * count3;
      DepthState.onMatrix4fFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloat(matrix4f, value10, value8, value, value2, value4, value3, value7, value6, bufferBuilder, f2);
   }

   private void onFloatMatrix4fFloatFloatFloatFloatBufferBuilderFloatVec3dFloat(
      float value, Matrix4f matrix4f, float value2, float value3, float value4, float value5, BufferBuilder bufferBuilder, float value6, Vec3d vec3d, float value7
   ) {
      float f = value / 2.0F;
      float f1 = (float)(vec3d.x - f);
      float f2 = (float)(vec3d.x + f);
      float f3 = (float)vec3d.y;
      float f4 = (float)(vec3d.y + value7);
      float f5 = (float)(vec3d.z - f);
      float f6 = (float)(vec3d.z + f);
      byte b2 = 1;
      byte b1 = 1;
      byte b0 = 1;
      this.onFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fIntIntFloatFloatIntFloat(
         value6, f5, value5, value2, value7, value4, f1, bufferBuilder, matrix4f, b0, b1, f3, value, b2, value3
      );
      byte b5 = 1;
      byte b4 = 1;
      byte b3 = -1;
      this.onFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fIntIntFloatFloatIntFloat(
         value6, f5, value5, value2, value7, value4, f2, bufferBuilder, matrix4f, b3, b4, f3, value, b5, value3
      );
      byte b8 = -1;
      byte b7 = 1;
      byte b6 = 1;
      this.onFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fIntIntFloatFloatIntFloat(
         value6, f6, value5, value2, value7, value4, f1, bufferBuilder, matrix4f, b6, b7, f3, value, b8, value3
      );
      byte b11 = -1;
      byte b10 = 1;
      byte b9 = -1;
      this.onFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fIntIntFloatFloatIntFloat(
         value6, f6, value5, value2, value7, value4, f2, bufferBuilder, matrix4f, b9, b10, f3, value, b11, value3
      );
      byte b14 = 1;
      byte b13 = -1;
      byte b12 = 1;
      this.onFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fIntIntFloatFloatIntFloat(
         value6, f5, value5, value2, value7, value4, f1, bufferBuilder, matrix4f, b12, b13, f4, value, b14, value3
      );
      byte b17 = 1;
      byte b16 = -1;
      byte b15 = -1;
      this.onFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fIntIntFloatFloatIntFloat(
         value6, f5, value5, value2, value7, value4, f2, bufferBuilder, matrix4f, b15, b16, f4, value, b17, value3
      );
      byte b20 = -1;
      byte b19 = -1;
      byte b18 = 1;
      this.onFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fIntIntFloatFloatIntFloat(
         value6, f6, value5, value2, value7, value4, f1, bufferBuilder, matrix4f, b18, b19, f4, value, b20, value3
      );
      byte b23 = -1;
      byte b22 = -1;
      byte b21 = -1;
      this.onFloatFloatFloatFloatFloatFloatFloatBufferBuilderMatrix4fIntIntFloatFloatIntFloat(
         value6, f6, value5, value2, value7, value4, f2, bufferBuilder, matrix4f, b21, b22, f4, value, b23, value3
      );
   }

   public void onBooleanFloatFloat(boolean flag, float value3, float value4) {
      this.flag2 = flag;
      this.value = value4;
      this.value2 = value3;
   }

   public void onBooleanInt(boolean flag, int count) {
      this.flag3 = flag;
      this.value3 = (count >> 16 & 0xFF) / 255.0F;
      this.value4 = (count >> 8 & 0xFF) / 255.0F;
      this.value5 = (count & 0xFF) / 255.0F;
      this.value6 = (count >> 24 & 0xFF) / 255.0F;
   }

   public void onFloatFloatListFloatFloatFloatFloatBooleanFloatFloatWorldRenderContextFloat(
      float value7,
      float value8,
      List<Entity> list,
      float value9,
      float value10,
      float value11,
      float value12,
      boolean flag,
      float value13,
      float value14,
      WorldRenderContext worldRenderContext,
      float value15
   ) {
      if (!list.isEmpty()) {
         MatrixStack matrixstack = worldRenderContext.getMatrixStack();
         Vec3d vec3d = worldRenderContext.getCamera().getPos();
         float f = worldRenderContext.getRenderTickCounter().getTickDelta(true);
         Matrix4f matrix4f = matrixstack.peek().getPositionMatrix();
         float f1 = value15 * 0.005F;
         float f2 = f1 * 2.2F;
         if (this.flag2 && this.value2 > 0.0F && value9 > 0.0F) {
            try {
               if (BoxGlowEffect.check()) {
                  DepthState.update2();
                  this.onFloatMatrix4fFloatFloatFloatVec3dListFloatFloat(value9, matrix4f, value10, f, value8, vec3d, list, value14, f1);
                  DepthState.update();
                  BoxGlowEffect.onFloatFloat(this.value, this.value2);
               }
            } catch (Throwable throwable) {
               BoxGlowEffect.restore();
            }
         }

         DepthState.update2();
         if (flag && value12 > 0.0F) {
            this.onMatrix4fListFloatVec3dFloatFloatFloatFloat(matrix4f, list, value7, vec3d, value12, value11, value13, f);
         }

         if (this.flag3 && this.value6 > 0.0F) {
            float f6 = this.value6 * value9;
            float f5 = this.value5;
            float f4 = this.value4;
            float f3 = this.value3;
            this.onFloatMatrix4fFloatFloatFloatVec3dListFloatFloat(f6, matrix4f, f4, f, f5, vec3d, list, f3, f2);
         }

         this.onFloatMatrix4fFloatFloatFloatVec3dListFloatFloat(value9, matrix4f, value10, f, value8, vec3d, list, value14, f1);
         DepthState.update();
      }
   }

   private void onFloatMatrix4fFloatFloatFloatVec3dListFloatFloat(
      float value, Matrix4f matrix4f, float value2, float value3, float value4, Vec3d vec3d2, List<Entity> list, float value5, float value6
   ) {
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);

      for (Entity entity : list) {
         Vec3d vec3d = DepthState.getVec3dByFloatEntityVec3d(value3, entity, vec3d2);
         float f = entity.getWidth();
         float f1 = entity.getHeight();
         float f2 = this.flag ? value6 * Math.max(1.0F, (float)vec3d.length() * 0.12F) : value6;
         this.onFloatMatrix4fFloatFloatFloatFloatBufferBuilderFloatVec3dFloat(f, matrix4f, f2, value5, value2, value, bufferbuilder, value4, vec3d, f1);
      }

      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
   }

   private void onMatrix4fListFloatVec3dFloatFloatFloatFloat(Matrix4f matrix4f, List<Entity> list, float value, Vec3d vec3d2, float value2, float value3, float value4, float value5) {
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);

      for (Entity entity : list) {
         Vec3d vec3d = DepthState.getVec3dByFloatEntityVec3d(value5, entity, vec3d2);
         float f = entity.getWidth() / 2.0F;
         float f1 = entity.getHeight();
         float f2 = (float)vec3d.x;
         float f3 = (float)vec3d.y;
         float f4 = (float)vec3d.z;
         float f10 = f2 - f;
         float f11 = f4 - f;
         float f12 = f2 + f;
         float f13 = f3 + f1;
         float f9 = f4 + f;
         float f8 = f13;
         float f7 = f12;
         float f6 = f11;
         float f5 = f10;
         DepthState.onFloatFloatFloatFloatFloatMatrix4fFloatBufferBuilderFloatFloatFloatFloat(
            value3, f6, f3, f7, value4, matrix4f, value2, bufferbuilder, f5, f9, value, f8
         );
      }

      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
   }

   @Override
   public void render(List list, WorldRenderContext worldRenderContext, float value, float value2, float value3, float value4) {
      float f4 = 0.0F;
      float f3 = 0.0F;
      float f2 = 0.0F;
      float f1 = 0.0F;
      boolean flagx = false;
      float f = 2.0F;
      this.onFloatFloatListFloatFloatFloatFloatBooleanFloatFloatWorldRenderContextFloat(f1, value2, list, value, value4, f2, f4, flagx, f3, value3, worldRenderContext, f);
   }
}
