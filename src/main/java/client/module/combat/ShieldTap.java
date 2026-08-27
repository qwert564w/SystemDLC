package client.module.combat;

import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.BooleanSetting;
import client.setting.RangeSliderSetting;
import client.setting.Setting;
import client.util.ShieldTapHandler;
import net.minecraft.client.option.GameOptions;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.lwjgl.glfw.GLFW;

public class ShieldTap extends Module {
   private RangeSliderSetting cooldown;
   public BooleanSetting bVozduhe;
   public boolean flag;
   private boolean flag2;
   public int value235;
   private int value236;

   public ShieldTap() {
      super("ShieldTap", Category.COMBAT);
      RangeSliderSetting rangeslidersetting = new RangeSliderSetting("", "", 4.0, 6.0, 1.0, 20.0, 1.0);
      rangeslidersetting.setName("Кулдаун");
      rangeslidersetting.setDescription("Минимум тиков между тапами");
      this.cooldown = rangeslidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("B воздухе");
      booleansetting.setDescription("W-тап в воздухе");
      this.bVozduhe = booleansetting;
      this.addSettings(new Setting[]{this.cooldown, this.bVozduhe});
   }

   @Override
   public void onDisable() {
      if (!this.notInGame() && this.client().getWindow() != null) {
         this.options().forwardKey.setPressed(GLFW.glfwGetKey(mc.getWindow().getHandle(), 87) == 1);
         this.flag = false;
         this.flag2 = false;
      }
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.SEND) {
         if (packetEvent.getPacket() instanceof PlayerInteractEntityC2SPacket playerinteractentityc2spacket) {
            if (!this.notInGame() && this.client().getWindow() != null) {
               playerinteractentityc2spacket.handle(new ShieldTapHandler(this));
            }
         }
      }
   }

   public static GameOptions getGameOptionsByShieldTap(ShieldTap shieldTap) {
      return shieldTap.options();
   }

   @Override
   public void onEnable() {
      this.flag = false;
      this.flag2 = false;
      this.value235 = 0;
      this.value236 = (int)this.cooldown.getDouble();
   }

   @Override
   public void update8() {
      if (!this.notInGame() && this.client().getWindow() != null) {
         if (this.value235 > 0) {
            this.value235--;
         }

         boolean flagx = GLFW.glfwGetKey(mc.getWindow().getHandle(), 87) == 1;
         if (!flagx) {
            this.flag = false;
            this.flag2 = false;
         } else if (this.flag2) {
            this.options().forwardKey.setPressed(true);
            this.flag2 = false;
            this.value235 = this.value236;
            this.value236 = (int)this.cooldown.getDouble();
         } else {
            if (this.flag) {
               this.options().forwardKey.setPressed(false);
               this.flag = false;
               this.flag2 = true;
            }
         }
      }
   }
}
