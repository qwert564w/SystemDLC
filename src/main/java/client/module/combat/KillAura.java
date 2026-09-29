package client.module.combat;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Intel client Aura for Fabric 1.21.11.
 *
 * Uses the normal client interaction manager and vanilla attack cooldown.
 * No packet spoofing, hitbox spoofing, or anti-cheat bypass behavior.
 */
public final class KillAura extends Module {
   private final SliderSetting range = slider("Дистанция", "Максимальная дистанция атаки", 3.2, 2.0, 5.0, 0.1);
   private final SliderSetting fov = slider("FOV", "Угол поиска цели", 160.0, 20.0, 360.0, 5.0);
   private final SliderSetting rotationSpeed = slider("Скорость ротации", "Плавность доведения взгляда", 0.34, 0.08, 1.0, 0.02);
   private final SliderSetting targetHeight = slider("Точка цели", "Вертикальная точка внутри хитбокса", 0.58, 0.15, 0.90, 0.01);
   private final SliderSetting attackThreshold = slider("Готовность атаки", "Минимальный ванильный cooldown", 0.92, 0.50, 1.0, 0.01);
   private final BooleanSetting players = bool("Игроки", "Выбирать игроков", true);
   private final BooleanSetting mobs = bool("Мобы", "Выбирать живых мобов", false);
   private final BooleanSetting requireLineOfSight = bool("Проверка видимости", "Атаковать только при прямой видимости", true);
   private final BooleanSetting nearestPoint = bool("Ближайшая точка", "Наводиться на ближайшую точку хитбокса", true);
   private final BooleanSetting attackOnlyWhenReady = bool("Только готовая атака", "Ждать полного ванильного cooldown", true);

   private LivingEntity target;
   private float smoothYaw;
   private float smoothPitch;

   public KillAura() {
      super("Aura", Category.COMBAT);
      this.addSettings(new Setting[] {
         this.range, this.fov, this.rotationSpeed, this.targetHeight,
         this.attackThreshold, this.players, this.mobs,
         this.requireLineOfSight, this.nearestPoint, this.attackOnlyWhenReady
      });
   }

   private static SliderSetting slider(String name, String description, double value, double min, double max, double step) {
      SliderSetting s = new SliderSetting("", "", value, min, max, step);
      s.setName(name);
      s.setDescription(description);
      return s;
   }

   private static BooleanSetting bool(String name, String description, boolean value) {
      BooleanSetting s = new BooleanSetting("", "", value);
      s.setName(name);
      s.setDescription(description);
      return s;
   }

   @Override
   public void onEnable() {
      this.target = null;
      if (this.player() != null) {
         this.smoothYaw = this.player().getYaw();
         this.smoothPitch = this.player().getPitch();
      }
   }

   @Override
   public void onDisable() {
      this.target = null;
   }

   @Override
   public void onTick() {
      if (this.notInGame() || this.currentScreen() != null || this.interactionManager() == null) {
         this.target = null;
         return;
      }

      this.target = this.findTarget();
      if (this.target == null) {
         return;
      }

      this.rotateTo(this.target);

      float cooldown = this.player().getAttackCooldownProgress(0.0F);
      if (this.attackOnlyWhenReady.isFlag3() && cooldown < this.attackThreshold.getValue()) {
         return;
      }

      this.interactionManager().attackEntity(this.player(), this.target);
      this.player().swingHand(this.mainHand());
   }

   private LivingEntity findTarget() {
      double maxRange = this.range.getValue();
      double maxFov = this.fov.getValue() * 0.5;
      Box searchBox = this.player().getBoundingBox().expand(maxRange);
      List<LivingEntity> candidates = new ArrayList<>();

      for (LivingEntity living : this.world().getEntitiesByClass(
         LivingEntity.class,
         searchBox,
         entity -> entity != this.player() && entity.isAlive() && this.isAllowedType(entity)
      )) {
         if (this.player().distanceTo(living) > maxRange) continue;
         if (this.angleTo(living) > maxFov) continue;
         if (this.requireLineOfSight.isFlag3() && !this.player().canSee(living)) continue;
         candidates.add(living);
      }

      candidates.sort(Comparator
         .comparingDouble((LivingEntity e) -> this.angleTo(e))
         .thenComparingDouble(e -> this.player().distanceTo(e))
         .thenComparingDouble(LivingEntity::getHealth));

      return candidates.isEmpty() ? null : candidates.getFirst();
   }

   private boolean isAllowedType(LivingEntity living) {
      if (living instanceof PlayerEntity player) {
         return this.players.isFlag3() && !this.isFriend(player);
      }
      return this.mobs.isFlag3();
   }

   private double angleTo(LivingEntity entity) {
      double dx = entity.getX() - this.player().getX();
      double dz = entity.getZ() - this.player().getZ();
      float desiredYaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
      return Math.abs(MathHelper.wrapDegrees(desiredYaw - this.player().getYaw()));
   }

   private Vec3Target aimPoint(LivingEntity entity) {
      Box box = entity.getBoundingBox();
      double y = box.minY + (box.maxY - box.minY) * this.targetHeight.getValue();

      if (!this.nearestPoint.isFlag3()) {
         return new Vec3Target(
            (box.minX + box.maxX) * 0.5,
            y,
            (box.minZ + box.maxZ) * 0.5
         );
      }

      return new Vec3Target(
         MathHelper.clamp(this.player().getX(), box.minX, box.maxX),
         y,
         MathHelper.clamp(this.player().getZ(), box.minZ, box.maxZ)
      );
   }

   private void rotateTo(LivingEntity entity) {
      Vec3Target point = this.aimPoint(entity);
      Vec3d eye = this.player().getEyePos();
      double dx = point.x - eye.x;
      double dy = point.y - eye.y;
      double dz = point.z - eye.z;
      double horizontal = Math.sqrt(dx * dx + dz * dz);

      float desiredYaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
      float desiredPitch = (float)(-Math.toDegrees(Math.atan2(dy, horizontal)));

      float factor = MathHelper.clamp(this.rotationSpeed.getValueAsFloat(), 0.05F, 1.0F);
      float yawDelta = MathHelper.wrapDegrees(desiredYaw - this.smoothYaw);
      float pitchDelta = desiredPitch - this.smoothPitch;

      float maxYawStep = 4.0F + 14.0F * factor;
      float maxPitchStep = 3.0F + 10.0F * factor;

      yawDelta = MathHelper.clamp(yawDelta * factor, -maxYawStep, maxYawStep);
      pitchDelta = MathHelper.clamp(pitchDelta * factor, -maxPitchStep, maxPitchStep);

      this.smoothYaw += yawDelta;
      this.smoothPitch = MathHelper.clamp(this.smoothPitch + pitchDelta, -90.0F, 90.0F);

      this.player().setYaw(this.smoothYaw);
      this.player().setPitch(this.smoothPitch);
   }

   private record Vec3Target(double x, double y, double z) {}
}
