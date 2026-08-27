package client.module.player;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.entity.player.PlayerEntity;

public class AutoRespawn extends Module {
   private BooleanSetting showKoordinatySmerti;
   private long time;
   private boolean flag;

   public AutoRespawn() {
      super("AutoRespawn", Category.PLAYER);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать координаты смерти");
      booleansetting.setDescription("Отображать координаты смерти");
      this.showKoordinatySmerti = booleansetting;
      this.time = -1L;
      this.flag = false;
      this.addSettings(new Setting[]{this.showKoordinatySmerti});
   }

   @Override
   public void onTick() {
      if (!this.notInGame()) {
         this.update14();
         this.update11();
         this.update12();
      }
   }

   private void update11() {
      if (this.time != -1L) {
         long i = System.currentTimeMillis();
         if (i >= this.time) {
            this.update15();
            this.setTime();
         }
      }
   }

   private void update12() {
      PlayerEntity playerentity = this.player();
      if (playerentity != null) {
         if (playerentity.isAlive()) {
            this.flag = false;
         }
      }
   }

   @Override
   public void onDisable() {
      this.setTime();
   }

   @Override
   public void update3() {
      this.update16();
   }

   private void update13() {
      if (!this.flag) {
         this.flag = true;
         PlayerEntity playerentity = this.player();
         if (playerentity != null && this.showKoordinatySmerti.isFlag3()) {
            this.sendMessage("Вы умерли на координатах: X=" + (int)playerentity.getX() + " Y=" + (int)playerentity.getY() + " Z=" + (int)playerentity.getZ());
         }

         this.time = System.currentTimeMillis() + 500L;
      }
   }

   @Override
   public void onPlayerEntity(PlayerEntity playerEntity) {
      if (playerEntity == this.player() && !this.flag) {
         this.update13();
      }
   }

   private void update14() {
      if (this.currentScreen() instanceof DeathScreen && !this.flag) {
         this.update13();
      }
   }

   @Override
   public void update4() {
      this.update16();
   }

   private void update15() {
      try {
         if (this.client().getNetworkHandler() == null) {
            return;
         }

         if (this.currentScreen() instanceof DeathScreen) {
            this.client().setScreen(null);
         }

         PlayerEntity playerentity = this.player();
         if (playerentity != null) {
            playerentity.requestRespawn();
         }
      } catch (Exception exception) {
      }
   }

   @Override
   public void onEnable() {
      this.update16();
   }

   private void update16() {
      this.flag = false;
      this.setTime();
   }

   private void setTime() {
      this.time = -1L;
   }
}
