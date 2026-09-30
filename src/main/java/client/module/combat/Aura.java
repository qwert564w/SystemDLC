package client.module.combat;

import client.data.Rotation;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.RotationUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class Aura extends Module {
   private final SliderSetting range = new SliderSetting("", "", 3.0, 1.0, 6.0, 0.1);
   private final SliderSetting fov = new SliderSetting("", "", 180.0, 20.0, 360.0, 1.0);
   private final SliderSetting rotationSpeed = new SliderSetting("", "", 8.0, 1.0, 20.0, 0.5);
   private final SliderSetting cooldown = new SliderSetting("", "", 0.90, 0.50, 1.0, 0.01);
   private final ListSetting priority = new ListSetting("", "", List.of("Прицел", "Дистанция", "Здоровье"), List.of("Прицел"), false);
   private final MultilistSetting targets = new MultilistSetting(
      "", "",
      List.of("Игроки", "Мобы", "Друзья"),
      List.of("Игроки")
   );
   private final BooleanSetting onlyWeapon = new BooleanSetting("", "", false);
   private final BooleanSetting onlyCrits = new BooleanSetting("", "", false);
   private final BooleanSetting throughWalls = new BooleanSetting("", "", false);

   private final RotationUtil rotationUtil = new RotationUtil();
   private LivingEntity target;

   public Aura() {
      super("Aura", Category.COMBAT);

      this.range.setName("Дистанция");
      this.range.setDescription("Максимальная дистанция поиска и атаки");

      this.fov.setName("Фов");
      this.fov.setDescription("Максимальный угол захвата цели");

      this.rotationSpeed.setName("Скорость ротации");
      this.rotationSpeed.setDescription("Плавность поворота к выбранной цели");

      this.cooldown.setName("Кулдаун");
      this.cooldown.setDescription("Минимальная готовность обычной атаки");

      this.priority.setName("Приоритет");
      this.priority.setDescription("Порядок выбора цели");

      this.targets.setName("Цели");
      this.targets.setDescription("Типы целей");

      this.onlyWeapon.setName("Только оружие");
      this.onlyWeapon.setDescription("Работать только с мечом или топором");

      this.onlyCrits.setName("Только криты");
      this.onlyCrits.setDescription("Атаковать только во время падения для критического удара");

      this.throughWalls.setName("Через стены");
      this.throughWalls.setDescription("Разрешать выбор цели без проверки прямой видимости");

      this.addSettings(
         new Setting[]{
            this.range, this.fov, this.rotationSpeed, this.cooldown,
            this.priority, this.targets, this.onlyWeapon,
            this.onlyCrits, this.throughWalls
         }
      );
   }

   @Override
   public void onEnable() {
      this.target = null;
      this.rotationUtil.setTime();
   }

   @Override
   public void onDisable() {
      this.target = null;
      this.rotationUtil.setTime();
   }

   @Override
   public void onTick() {
      if (!this.inGame() || mc.interactionManager == null) {
         this.target = null;
         return;
      }

      this.target = findTarget();
      if (this.target == null) {
         return;
      }

      Vec3d point = getAimPoint(this.target);
      float[] rotations = RotationUtil.getFloatArrayByVec3d(point);
      this.rotationUtil.onDoubleFloatFloat(this.rotationSpeed.getValue(), rotations[1], rotations[0]);

      if (canAttack(this.target)) {
         mc.interactionManager.attackEntity(mc.player, this.target);
         mc.player.swingHand(Hand.MAIN_HAND);
      }
   }

   public LivingEntity getTarget() {
      return this.target;
   }

   private boolean canAttack(LivingEntity entity) {
      if (entity == null || !entity.isAlive()) {
         return false;
      }

      if (mc.player.getAttackCooldownProgress(0.5f) < this.cooldown.getValueAsFloat()) {
         return false;
      }

      if (this.onlyWeapon.isFlag3()
            && !(mc.player.getMainHandStack().getItem() instanceof SwordItem)
            && !(mc.player.getMainHandStack().getItem() instanceof AxeItem)) {
         return false;
      }

      if (this.onlyCrits.isFlag3()) {
         if (mc.player.isOnGround() || mc.player.getVelocity().y >= 0.0
               || mc.player.isSprinting() || mc.options.jumpKey.isPressed()) {
            return false;
         }
      }

      if (!this.throughWalls.isFlag3() && !hasLineOfSight(entity)) {
         return false;
      }

      return mc.player.squaredDistanceTo(entity) <= this.range.getValue() * this.range.getValue();
   }

   private LivingEntity findTarget() {
      Box search = mc.player.getBoundingBox().expand(this.range.getValue());
      List<LivingEntity> candidates = mc.world.getEntitiesByClass(
         LivingEntity.class,
         search,
         this::isValidTarget
      );

      return candidates.stream()
         .filter(e -> e != mc.player)
         .filter(e -> withinFov(e))
         .filter(e -> this.throughWalls.isFlag3() || hasLineOfSight(e))
         .min(this.comparator())
         .orElse(null);
   }

   private boolean isValidTarget(LivingEntity entity) {
      if (entity == mc.player || !entity.isAlive() || entity.isSpectator()) {
         return false;
      }

      if (entity instanceof PlayerEntity player) {
         if (this.isFriend(player)) {
            return this.targets.isString2("Друзья");
         }
         return this.targets.isString2("Игроки");
      }

      return entity instanceof MobEntity && this.targets.isString2("Мобы");
   }

   private Comparator<LivingEntity> comparator() {
      return switch (this.priority.getString2()) {
         case "Дистанция" -> Comparator.comparingDouble(mc.player::distanceTo);
         case "Здоровье" -> Comparator.comparingDouble(LivingEntity::getHealth);
         default -> Comparator.comparingDouble(this::angleTo);
      };
   }

   private double angleTo(LivingEntity entity) {
      Vec3d eye = mc.player.getEyePos();
      Vec3d point = getAimPoint(entity).subtract(eye).normalize();
      Vec3d look = mc.player.getRotationVec(1.0f);
      return Math.acos(MathHelper.clamp(look.dotProduct(point), -1.0, 1.0));
   }

   private boolean withinFov(LivingEntity entity) {
      return Math.toDegrees(angleTo(entity)) <= this.fov.getValue() * 0.5;
   }

   private boolean hasLineOfSight(Entity entity) {
      return mc.player.canSee(entity);
   }

   private Vec3d getAimPoint(LivingEntity entity) {
      Box box = entity.getBoundingBox();
      double x = (box.minX + box.maxX) * 0.5;
      double z = (box.minZ + box.maxZ) * 0.5;
      double y = MathHelper.clamp(
         mc.player.getEyePos().y,
         box.minY + entity.getHeight() * 0.15,
         box.maxY - entity.getHeight() * 0.05
      );
      return new Vec3d(x, y, z);
   }
}
