package client.util;

import client.api.Theme;
import client.data.Waypoint;
import client.module.CategoryType;
import client.module.Feature;
import client.render.RotationBuffer;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.render.WorldRenderContext;
import java.util.List;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class WaypointMath {
   public void render(WorldRenderContext worldRenderContext, float value, List<Waypoint> list) {
      if (list != null && !list.isEmpty()) {
         MatrixStack matrixstack = worldRenderContext.getMatrixStack();
         Camera camera = worldRenderContext.getCamera();
         Vec3d vec3d = camera.getPos();
         int i = Theme.background();
         int j = Theme.foreground();
         int k = Theme.mutedFg();
         RotationBuffer.setMinecraftClient2(Feature.mc);

         try {
            for (Waypoint waypoint : list) {
               if (waypoint != null) {
                  double d0 = waypoint.getValue() + 0.5;
                  double d1 = waypoint.getValue2() + 1.0F;
                  double d2 = waypoint.getValue3() + 0.5;
                  double d3 = d0 - vec3d.x;
                  double d4 = d1 - vec3d.y;
                  double d5 = d2 - vec3d.z;
                  double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                  if (!(d6 < 0.001)) {
                     double d7;
                     if (Feature.mc != null && Feature.mc.player != null) {
                        double d8 = Feature.mc.player.getX() - waypoint.getValue();
                        double d9 = Feature.mc.player.getY() - waypoint.getValue2();
                        double d10 = Feature.mc.player.getZ() - waypoint.getValue3();
                        d7 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
                     } else {
                        d7 = d6;
                     }

                     double d11;
                     if (d6 > 32.0) {
                        double d12 = 32.0 / d6;
                        d3 *= d12;
                        d4 *= d12;
                        d5 *= d12;
                        d11 = 32.0;
                     } else {
                        d11 = d6;
                     }

                     String s1 = getStringByDouble(d7);
                     String s = waypoint.getText2() == null ? "" : waypoint.getText2();
                     float f25 = TextShader.getFloatByStringFloat(s, 14.0F);
                     float f = TextShader.getFloatByStringFloat(s1, 14.0F);
                     float f1 = 26.0F + f25 + 6.0F + f + 8.0F;
                     matrixstack.push();

                     try {
                        matrixstack.translate(d3, d4, d5);
                        RotationBuffer.render(matrixstack);
                        float f2 = (float)Math.clamp(d11 * 0.0025F, 0.02F, 0.1F) * value;
                        matrixstack.scale(-f2, -f2, -f2);
                        Matrix4f matrix4f = matrixstack.peek().getPositionMatrix();
                        float f3 = -f1 / 2.0F;
                        float f4 = -14.0F;
                        float f5 = f4 + 6.5F;
                        float f6 = f4 + 7.0F;
                        float f17 = 1.0F;
                        float f16 = 0.0F;
                        float f15 = 0.0F;
                        float f14 = 0.0F;
                        byte b1 = 0;
                        float f13 = 0.0F;
                        byte b0 = 0;
                        float f12 = 8.0F;
                        float f11 = 8.0F;
                        float f10 = 8.0F;
                        float f9 = 8.0F;
                        float f8 = 28.0F;
                        ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
                           f1, b1, i, f3, f4, f16, f9, f8, f15, matrix4f, f17, b0, f11, f13, f10, f12, f14
                        );
                        float f7 = f3 + 8.0F;
                        float f20 = 1.0F;
                        float f19 = 15.0F;
                        float f18 = 12.0F;
                        CategoryType categorytype = CategoryType.WAYPOINT;
                        SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(f20, j, matrix4f, f5, categorytype, f19, f7, f18);
                        f7 += 18.0F;
                        float f22 = 1.0F;
                        float f21 = 14.0F;
                        TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f6, f7, j, f21, f22, s, matrix4f);
                        f7 += f25 + 6.0F;
                        float f24 = 1.0F;
                        float f23 = 14.0F;
                        TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f6, f7, k, f23, f24, s1, matrix4f);
                     } finally {
                        matrixstack.pop();
                     }
                  }
               }
            }
         } finally {
            RotationBuffer.setMinecraftClient(Feature.mc);
         }
      }
   }

   private static String getStringByDouble(double value) {
      return value < 1000.0 ? (int)Math.round(value) + "m" : String.format("%.1fkm", value / 1000.0);
   }
}
