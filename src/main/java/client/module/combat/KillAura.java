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
import net.minecraft.util.math.MathHelper;

/**
 * Basic 1.21.4 attack aura.
 *
 * This module intentionally uses ordinary client interaction and does not
 * attempt to conceal or bypass server-side anti-cheat checks.
 */
public final class KillAura extends Module {
   private final SliderSetting range;
   private final SliderSetting fov;
   private final SliderSetting rotationSpeed;
   private final SliderSetting cooldown;
   private final BooleanSetting players;
   private final BooleanSetting mobs;
   private final BooleanSetting throughWalls;

   private LivingEntity target;
   private long lastAttackMs;

   public KillAura() {
      super("Aura", Category.COMBAT);

      this.range = new SliderSetting("", "", 3.2, 2.0, 5.0, 0.1);
      this.range.setName("Дистанция");
      this.range.setDescription("Максимальная дистанция выбора цели");

      this.fov = new SliderSetting("", "", 120.0, 20.0, 360.0, 5.0);
      this.fov.setName("FOV");
      this.fov.setDescription("Максимальный угол поиска цели");

      this.rotationSpeed = new SliderSetting("", "", 0.45, 0.10, 1.0, 0.05);
      this.rotationSpeed.setName("Скорость ротации");
      this.rotationSpeed.setDescription("Скорость доведения взгляда к цели");

      this.cooldown = new SliderSetting("", "", 0.90, 0.50, 1.0, 0.05);
      this.cooldown.setName("Кулдаун");
      this.cooldown.setDescription("Минимальная готовность обычной атаки");

      this.players = new BooleanSetting("", "", true);
      this.players.setName("Игроки");
      this.players.setDescription("Выбирать игроков");

      this.mobs = new BooleanSetting("", "", false);
      this.mobs.setName("Мобы");
      this.mobs.setDescription("Выбирать обычных живых мобов");

      this.throughWalls = new BooleanSetting("", "", false);
      this.throughWalls.setName("Через стены");
      this.throughWalls.setDescription("Разрешить выбор цели без прямой видимости");

      this.addSettings(new Setting[] {
         this.range, this.fov, this.rotationSpeed, this.cooldown,
         this.players, this.mobs, this.throughWalls
      });
   }

   @Override
   public void onEnable() {
      this.target = null;
      this.lastAttackMs = 0L;
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

      float progress = this.player().getAttackCooldownProgress(0.0F);
      if (progress < this.cooldown.getValue()) {
         return;
      }

      long now = System.currentTimeMillis();
      if (now - this.lastAttackMs < 40L) {
         return;
      }

      this.interactionManager().attackEntity(this.player(), this.target);
      this.player().swingHand(this.mainHand());
      this.lastAttackMs = now;
   }

   private LivingEntity findTarget() {
      double maxRange = this.range.getValue();
      double maxFov = this.fov.getValue() * 0.5;
      List<LivingEntity> candidates = new ArrayList<>();

      for (LivingEntity living : this.world().getEntitiesByClass(
         LivingEntity.class, this.player().getBoundingBox().expand(maxRange), entity -> true
      )) {
         if (!living.isAlive() || living == this.player()) {
            continue;
         }

         if (!this.isAllowedType(living)) {
            continue;
         }

         if (!this.throughWalls.isFlag3() && !this.player().canSee(living)) {
            continue;
         }

         if (this.angleTo(living) > maxFov) {
            continue;
         }

         candidates.add(living);
      }

      candidates.sort(Comparator
         .comparingDouble((LivingEntity e) -> this.angleTo(e))
         .thenComparingDouble(e -> this.player().distanceTo(e)));

      return candidates.isEmpty() ? null : candidates.getFirst();
   }

   private boolean isAllowedType(LivingEntity living) {
      if (living instanceof PlayerEntity) {
         return this.players.isFlag3() && !this.isFriend((PlayerEntity) living);
      }
      return this.mobs.isFlag3();
   }

   private double angleTo(LivingEntity entity) {
      double dx = entity.getX() - this.player().getX();
      double dz = entity.getZ() - this.player().getZ();
      float desiredYaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
      return Math.abs(MathHelper.wrapDegrees(desiredYaw - this.player().getYaw()));
   }

   private void rotateTo(LivingEntity entity) {
      double dx = entity.getX() - this.player().getX();
      double dz = entity.getZ() - this.player().getZ();
      double dy = entity.getY() + entity.getHeight() * 0.55 - this.player().getEyeY();
      double horizontal = Math.sqrt(dx * dx + dz * dz);

      float desiredYaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
      float desiredPitch = (float)(-Math.toDegrees(Math.atan2(dy, horizontal)));

      float yawDelta = MathHelper.wrapDegrees(desiredYaw - this.player().getYaw());
      float pitchDelta = desiredPitch - this.player().getPitch();
      float factor = this.rotationSpeed.getValueAsFloat();

      this.player().setYaw(this.player().getYaw() + yawDelta * factor);
      this.player().setPitch(MathHelper.clamp(
         this.player().getPitch() + pitchDelta * factor, -90.0F, 90.0F
      ));
   }
}
