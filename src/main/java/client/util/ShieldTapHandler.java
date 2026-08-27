package client.util;

import client.module.Feature;
import client.module.combat.ShieldTap;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket.Handler;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class ShieldTapHandler implements Handler {
   public final ShieldTap shieldTap;

   public ShieldTapHandler(ShieldTap shieldTap2) {
      this.shieldTap = shieldTap2;
   }

   public void interact(Hand hand) {
   }

   public void interactAt(Hand hand, Vec3d vec3d) {
   }

   public void attack() {
      if (this.shieldTap.bVozduhe.isFlag3() || this.shieldTap.player().isOnGround()) {
         if (GLFW.glfwGetKey(Feature.mc.getWindow().getHandle(), 32) != 1 || this.shieldTap.bVozduhe.isFlag3()) {
            if (this.shieldTap.value235 <= 0) {
               if (ShieldTap.getGameOptionsByShieldTap(this.shieldTap).forwardKey.isPressed() && this.shieldTap.player().isSprinting()) {
                  this.shieldTap.flag = true;
               }
            }
         }
      }
   }
}
