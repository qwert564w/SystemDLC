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

/**
 * Aura for Fabric 1.21.4 using ordinary Minecraft interaction APIs.
 * No packet spoofing, hitbox spoofing, or anti-cheat bypass logic.
 */
public final class KillAura extends Module {
   private final SliderSetting range = slider("Дистанция", "Максимальная дистанция выбора и атаки", 3.2, 2.0, 5.5, 0.1);
   private final SliderSetting fov = slider("FOV", "Максимальный угол поиска", 140.0, 20.0, 360.0, 5.0);
   private final SliderSetting rotationSpeed = slider("Скорость ротации", "Плавность доведения взгляда", 0.42, 0.10, 1.0, 0.02);
   private final SliderSetting cooldown = slider("Кулдаун", "Минимальная готовность обычной атаки", 0.92, 0.50, 1.0, 0.01);
   private final SliderSetting targetHeight = slider("Точка цели", "Высота точки внутри хитбокса", 0.58, 0.15, 0.90, 0.01);
   private final BooleanSetting players = bool("Игроки", "Выбирать игроков", true);
   private final BooleanSetting mobs = bool("Мобы", "Выбирать живых мобов", false);
   private final BooleanSetting throughWalls = bool("Через стены", "Разрешить выбор без прямой видимости", false);
   private final BooleanSetting nearestPoint = bool("Ближайшая точка", "Выбирать ближайшую точку хитбокса", true);
   private final BooleanSetting attackOnlyWhenReady = bool("Только готовая атака", "Не атаковать до ванильного кулдауна", true);

   private LivingEntity target;
   private long lastAttackMs;
   private float lastYaw;
   private float lastPitch;

   public KillAura() {
      super("Aura", Category.COMBAT);
      this.addSettings(new Setting[] {
         this.range, this.fov, this.rotationSpeed, this.cooldown, this.targetHeight,
         this.players, this.mobs, this.throughWalls, this.nearestPoint, this.attackOnlyWhenReady
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
      this.lastAttackMs = 0L;
      if (this.player() != null) {
         this.lastYaw = this.player().getYaw();
         this.lastPitch = this.player().getPitch();
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
      if (this.target == null) return;

      this.rotateTo(this.target);

      float progress = this.player().getAttackCooldownProgress(0.0F);
      if (this.attackOnlyWhenReady.isFlag3() && progress < this.cooldown.getValue()) return;

      long now = System.currentTimeMillis();
      if (now - this.lastAttackMs < 45L) return;

      this.interactionManager().attackEntity(this.player(), this.target);
      this.player().swingHand(this.mainHand());
      this.lastAttackMs = now;
   }

   private LivingEntity findTarget() {
      double maxRange = this.range.getValue();
      double maxFov = this.fov.getValue() * 0.5;
      Box searchBox = this.player().getBoundingBox().expand(maxRange);
      List<LivingEntity> candidates = new ArrayList<>();

      for (LivingEntity living : this.world().getEntitiesByClass(
         LivingEntity.class, searchBox, entity -> entity != this.player() && entity.isAlive()
      )) {
         if (!this.isAllowedType(living)) continue;
         if (!this.throughWalls.isFlag3() && !this.player().canSee(living)) continue;
         if (this.angleTo(living) > maxFov) continue;
         if (this.player().distanceTo(living) > maxRange) continue;
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

   private double[] aimPoint(LivingEntity entity) {
      Box box = entity.getBoundingBox();
      double y = box.minY + (box.maxY - box.minY) * this.targetHeight.getValue();
      if (!this.nearestPoint.isFlag3()) {
         return new double[]{(box.minX + box.maxX) * 0.5, y, (box.minZ + box.maxZ) * 0.5};
      }
      double x = MathHelper.clamp(this.player().getX(), box.minX, box.maxX);
      double z = MathHelper.clamp(this.player().getZ(), box.minZ, box.maxZ);
      return new double[]{x, y, z};
   }

   private void rotateTo(LivingEntity entity) {
      double[] point = this.aimPoint(entity);
      double dx = point[0] - this.player().getEyeX();
      double dy = point[1] - this.player().getEyeY();
      double dz = point[2] - this.player().getEyeZ();
      double horizontal = Math.sqrt(dx * dx + dz * dz);

      float desiredYaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
      float desiredPitch = (float)(-Math.toDegrees(Math.atan2(dy, horizontal)));

      float factor = this.rotationSpeed.getValueAsFloat();
      float yawDelta = MathHelper.wrapDegrees(desiredYaw - this.lastYaw) * factor;
      float pitchDelta = (desiredPitch - this.lastPitch) * factor;

      yawDelta = MathHelper.clamp(yawDelta, -12.0F, 12.0F);
      pitchDelta = MathHelper.clamp(pitchDelta, -9.0F, 9.0F);

      this.lastYaw += yawDelta;
      this.lastPitch = MathHelper.clamp(this.lastPitch + pitchDelta, -90.0F, 90.0F);
      this.player().setYaw(this.lastYaw);
      this.player().setPitch(this.lastPitch);
   }
}
