package client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class MouseState {
   private double value = 0.0;
   private double value2 = 0.0;
   private boolean flag = true;
   public MinecraftClient mc = MinecraftClient.getInstance();

   public void onFreeCamEntityFloat(FreeCamEntity freeCamEntity, float value3) {
      if (this.mc.mouse != null && freeCamEntity != null && this.mc.currentScreen == null) {
         long i = this.mc.getWindow().getHandle();
         double[] adouble = new double[1];
         double[] adouble1 = new double[1];
         GLFW.glfwGetCursorPos(i, adouble, adouble1);
         if (this.flag) {
            this.value = adouble[0];
            this.value2 = adouble1[0];
            this.flag = false;
         }

         double d0 = adouble[0] - this.value;
         double d1 = adouble1[0] - this.value2;
         this.value = adouble[0];
         this.value2 = adouble1[0];
         double d2 = (Double)this.mc.options.getMouseSensitivity().getValue() * 0.6 + 0.2;
         double d3 = d2 * d2 * d2 * 8.0 * value3;
         d0 *= d3;
         d1 *= d3;
         float f = freeCamEntity.getYaw() + (float)d0 * 0.15F;
         float f1 = freeCamEntity.getPitch() + (float)d1 * 0.15F;
         f1 = MathHelper.clamp(f1, -90.0F, 90.0F);
         freeCamEntity.setYaw(f);
         freeCamEntity.setPitch(f1);
      } else {
         this.flag = true;
      }
   }

   public void setFlag() {
      this.flag = true;
   }
}
