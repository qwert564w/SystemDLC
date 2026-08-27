package client.module.movement;

import client.data.AnimatedFloat;
import client.module.Category;
import client.module.Module;
import client.render.DepthState;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.Easings;
import client.util.FreeCamEntity;
import client.util.MouseState;
import client.util.SneakState;
import client.util.StringParts;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FreeCam extends Module {
   private SliderSetting speed;
   private SliderSetting animationVozvrata;
   private BooleanSetting otklyuchatOnUrone;
   private ColorSetting colorPodsvetki;
   private boolean flag;
   private FreeCamEntity freeCamEntity;
   private ClientPlayerEntity clientPlayerEntity;
   private MouseState mouseState;
   private float value235;
   private boolean flag2;
   private long time;
   private long time2;
   private Vec3d vec3d;
   private float value236;
   private float value237;

   public FreeCam() {
      super("FreeCam", Category.MOVEMENT);
      SliderSetting slidersetting = new SliderSetting("", "", 0.5, 0.1, 5.0, 0.05);
      slidersetting.setName("Скорость");
      slidersetting.setDescription("Скорость движения камеры");
      this.speed = slidersetting;
      slidersetting = new SliderSetting("", "", 300.0, 0.0, 1500.0, 25.0, StringParts.join(new String[]{"м", "c"}), 0);
      slidersetting.setName("Анимация возврата");
      slidersetting.setDescription("Длительность плавного возврата камеры к игроку в мс (0 — мгновенно)");
      this.animationVozvrata = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Отключать при уроне");
      booleansetting.setDescription("Автоматически отключать при получении урона");
      this.otklyuchatOnUrone = booleansetting;
      ColorSetting colorsetting = new ColorSetting("", "", 1073807206, true);
      colorsetting.setName("Цвет подсветки");
      colorsetting.setDescription("Цвет бокса, показывающего реальное положение игрока");
      this.colorPodsvetki = colorsetting;
      this.flag = false;
      this.value235 = 20.0F;
      this.flag2 = false;
      this.time = -1L;
      this.time2 = 0L;
      this.vec3d = Vec3d.ZERO;
      this.value236 = 0.0F;
      this.value237 = 0.0F;
      this.addSettings(new Setting[]{this.speed, this.animationVozvrata, this.otklyuchatOnUrone, this.colorPodsvetki});
   }

   @Override
   public void onTick() {
      if (this.flag && this.freeCamEntity != null) {
         this.client().setCameraEntity(this.freeCamEntity);
         SneakState.update4();
         if (this.flag2) {
            this.update14();
            return;
         }

         this.mouseState.onFreeCamEntityFloat(this.freeCamEntity, 1.0F);
         this.freeCamEntity.setValueAsFloat((float)this.speed.getValue());
         this.freeCamEntity.update();
         if (this.otklyuchatOnUrone.isFlag3()) {
            this.update15();
         }
      }
   }

   private void update11() {
      this.flag2 = false;
      this.time = -1L;
      super.setEnabled(false, null);
   }

   private void update12() {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      if (!this.notInGame()) {
         this.clientPlayerEntity = clientplayerentity;
         this.value235 = clientplayerentity.getHealth();
         this.freeCamEntity = new FreeCamEntity(
            this.world(), clientplayerentity.getPos(), clientplayerentity.getYaw(), clientplayerentity.getPitch(), (float)this.speed.getValue()
         );
         this.clientWorld().addEntity(this.freeCamEntity);
         this.client().setCameraEntity(this.freeCamEntity);
         this.flag = true;
         SneakState.update5();
      }
   }

   @Override
   public void onDisable() {
      this.update13();
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (this.flag && this.clientPlayerEntity != null && !this.notInGame()) {
         float f = worldRenderContext.getRenderTickCounter().getTickDelta(true);
         Vec3d vec3dx = worldRenderContext.getCamera().getPos();
         double d0 = MathHelper.lerp(f, this.clientPlayerEntity.prevX, this.clientPlayerEntity.getX());
         double d1 = MathHelper.lerp(f, this.clientPlayerEntity.prevY, this.clientPlayerEntity.getY());
         double d2 = MathHelper.lerp(f, this.clientPlayerEntity.prevZ, this.clientPlayerEntity.getZ());
         Box box = this.clientPlayerEntity.getBoundingBox();
         double d3 = (box.maxX - box.minX) / 2.0;
         double d4 = box.maxY - box.minY;
         Vec3d vec3d1 = new Vec3d(d0 - d3 - vec3dx.x, d1 - vec3dx.y, d2 - d3 - vec3dx.z);
         Vec3d vec3d2 = new Vec3d(d0 + d3 - vec3dx.x, d1 + d4 - vec3dx.y, d2 + d3 - vec3dx.z);
         int i = this.colorPodsvetki.getInt3();
         float f1 = (i >>> 24 & 0xFF) / 255.0F;
         float f2 = (i >>> 16 & 0xFF) / 255.0F;
         float f3 = (i >>> 8 & 0xFF) / 255.0F;
         float f4 = (i & 0xFF) / 255.0F;
         MatrixStack matrixstack1 = worldRenderContext.getMatrixStack();
         boolean flagx = true;
         MatrixStack matrixstack = matrixstack1;
         DepthState.render(matrixstack, f4, f2, vec3d1, flagx, f3, vec3d2, f1);
      }
   }

   private void update13() {
      if (this.clientPlayerEntity != null) {
         this.client().setCameraEntity(this.clientPlayerEntity);
         if (this.freeCamEntity != null) {
            this.freeCamEntity.remove(RemovalReason.DISCARDED);
            this.freeCamEntity = null;
         }

         this.flag = false;
         if (this.mouseState != null) {
            this.mouseState.setFlag();
         }

         this.value235 = 20.0F;
         this.flag2 = false;
         this.time = -1L;
         SneakState.update2();
      }
   }

   @Override
   public void setEnabled(boolean flag3, AnimatedFloat animatedFloat) {
      if (!flag3 && !this.flag2 && this.flag && this.freeCamEntity != null && this.clientPlayerEntity != null) {
         double d0 = this.animationVozvrata.getValue();
         if (d0 > 0.0) {
            this.setDouble(d0);
            return;
         }
      }

      super.setEnabled(flag3, animatedFloat);
   }

   public Vec3d getVec3d() {
      return this.flag && this.freeCamEntity != null ? this.freeCamEntity.getPos() : null;
   }

   public boolean check3() {
      return this.flag && this.clientPlayerEntity != null;
   }

   @Override
   public void onPlayerEntity(PlayerEntity playerEntity) {
      super.onPlayerEntity(playerEntity);
      this.setEnabled(false);
   }

   private void setDouble(double value) {
      this.flag2 = true;
      this.vec3d = this.freeCamEntity.getPos();
      this.value236 = this.freeCamEntity.getYaw();
      this.value237 = this.freeCamEntity.getPitch();
      this.time = System.nanoTime();
      this.time2 = (long)(value * 1000000.0);
   }

   private void update14() {
      if (this.clientPlayerEntity != null && this.freeCamEntity != null) {
         long i = System.nanoTime() - this.time;
         if (i >= this.time2) {
            this.update11();
         } else {
            float f = (float)i / (float)this.time2;
            float f1 = Easings.getFloatByFloat3(f);
            Vec3d vec3dx = this.clientPlayerEntity.getPos();
            double d0 = MathHelper.lerp(f1, this.vec3d.x, vec3dx.x);
            double d1 = MathHelper.lerp(f1, this.vec3d.y, vec3dx.y);
            double d2 = MathHelper.lerp(f1, this.vec3d.z, vec3dx.z);
            this.freeCamEntity.setPosition(d0, d1, d2);
            float f2 = this.clientPlayerEntity.getYaw();
            float f3 = this.clientPlayerEntity.getPitch();
            float f4 = MathHelper.wrapDegrees(f2 - this.value236);
            this.freeCamEntity.setYaw(this.value236 + f4 * f1);
            this.freeCamEntity.setPitch(this.value237 + (f3 - this.value237) * f1);
         }
      } else {
         this.update11();
      }
   }

   private void update15() {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      if (clientplayerentity != null && this.clientPlayerEntity != null) {
         float f = clientplayerentity.getHealth();
         if (f < this.value235) {
            this.setEnabled(false);
            return;
         }

         this.value235 = f;
      }
   }

   @Override
   public void onEnable() {
      this.mouseState = new MouseState();
      this.flag2 = false;
      this.time = -1L;
      this.update12();
   }

   @Override
   public void update8() {
      if (this.flag && this.clientPlayerEntity != null) {
         this.client().setCameraEntity(this.clientPlayerEntity);
         SneakState.update4();
      }
   }
}
