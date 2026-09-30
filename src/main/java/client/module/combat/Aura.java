package client.module.combat;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.Comparator;

public final class Aura extends Module {
    private final SliderSetting range = new SliderSetting("", "", 3.0, 2.0, 6.0, 0.1);
    private final SliderSetting fov = new SliderSetting("", "", 180.0, 10.0, 360.0, 1.0);
    private final SliderSetting rotationSpeed = new SliderSetting("", "", 25.0, 1.0, 90.0, 1.0);
    private final SliderSetting attackProgress = new SliderSetting("", "", 0.9, 0.1, 1.0, 0.01);
    private final BooleanSetting players = new BooleanSetting("", "", true);
    private final BooleanSetting mobs = new BooleanSetting("", "", false);
    private final BooleanSetting onlyCrits = new BooleanSetting("", "", false);
    private final BooleanSetting throughWalls = new BooleanSetting("", "", false);

    private LivingEntity target;

    public Aura() {
        super("Aura", Category.COMBAT);

        range.setName("Дистанция");
        range.setDescription("Максимальная дистанция до цели");

        fov.setName("ФОВ");
        fov.setDescription("Угол захвата цели");

        rotationSpeed.setName("Скорость ротации");
        rotationSpeed.setDescription("Максимальное изменение угла за тик");

        attackProgress.setName("Готовность атаки");
        attackProgress.setDescription("Минимальная готовность ванильного кулдауна");

        players.setName("Игроки");
        players.setDescription("Атаковать игроков");

        mobs.setName("Мобы");
        mobs.setDescription("Атаковать враждебных мобов");

        onlyCrits.setName("Только криты");
        onlyCrits.setDescription("Атаковать только во время критического падения");

        throughWalls.setName("Через стены");
        throughWalls.setDescription("Разрешить наведение через стены; атака всё равно требует отдельного включения");

        addSettings(new Setting[] {
                range, fov, rotationSpeed, attackProgress,
                players, mobs, onlyCrits, throughWalls
        });
    }

    public LivingEntity getTarget() {
        return target;
    }

    @Override
    public void onEnable() {
        target = null;
    }

    @Override
    public void onDisable() {
        target = null;
    }

    @Override
    public void update7() {
        if (notInGame() || player() == null || world() == null || interactionManager() == null) {
            target = null;
            return;
        }

        target = findTarget();
        if (target == null) {
            return;
        }

        rotate(target);

        if (canAttack(target)) {
            interactionManager().attackEntity(player(), target);
            player().swingHand(Hand.MAIN_HAND);
        }
    }

    private LivingEntity findTarget() {
        final Vec3d eye = player().getEyePos();
        final double maxRange = range.getValue();

        return world().getEntitiesByClass(
                LivingEntity.class,
                player().getBoundingBox().expand(maxRange),
                this::validTarget
        ).stream()
                .filter(entity -> entity != player())
                .filter(entity -> eye.squaredDistanceTo(entity.getBoundingBox().getCenter()) <= maxRange * maxRange)
                .filter(this::insideFov)
                .filter(entity -> throughWalls.isFlag3() || canSee(entity))
                .min(Comparator.comparingDouble(entity -> eye.squaredDistanceTo(entity.getBoundingBox().getCenter())))
                .orElse(null);
    }

    private boolean validTarget(LivingEntity entity) {
        if (entity == null || entity == player() || !entity.isAlive() || entity.isSpectator()) {
            return false;
        }

        if (entity instanceof PlayerEntity) {
            return players.isFlag3();
        }

        return mobs.isFlag3() && entity instanceof MobEntity;
    }

    private boolean insideFov(LivingEntity entity) {
        Vec3d eye = player().getEyePos();
        Vec3d delta = entity.getBoundingBox().getCenter().subtract(eye);
        if (delta.lengthSquared() < 1.0E-8) {
            return true;
        }

        Vec3d look = player().getRotationVec(1.0F);
        double dot = MathHelper.clamp(look.normalize().dotProduct(delta.normalize()), -1.0, 1.0);
        double angle = Math.toDegrees(Math.acos(dot));
        return angle <= fov.getValue() * 0.5;
    }

    private boolean canSee(LivingEntity entity) {
        Vec3d start = player().getEyePos();
        Vec3d end = entity.getBoundingBox().getCenter();

        HitResult result = world().raycast(new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                player()
        ));

        return result.getType() == HitResult.Type.MISS;
    }

    private void rotate(LivingEntity entity) {
        Vec3d eye = player().getEyePos();
        Vec3d point = entity.getBoundingBox().getCenter();
        Vec3d delta = point.subtract(eye);

        float targetYaw = (float) Math.toDegrees(Math.atan2(delta.z, delta.x)) - 90.0F;
        float targetPitch = (float) -Math.toDegrees(Math.atan2(delta.y, Math.hypot(delta.x, delta.z)));

        float yaw = stepAngle(player().getYaw(), targetYaw, rotationSpeed.getValueAsFloat());
        float pitch = stepAngle(player().getPitch(), targetPitch, rotationSpeed.getValueAsFloat());

        player().setYaw(MathHelper.wrapDegrees(yaw));
        player().setPitch(MathHelper.clamp(pitch, -90.0F, 90.0F));
    }

    private float stepAngle(float current, float target, float maxStep) {
        float delta = MathHelper.wrapDegrees(target - current);
        if (Math.abs(delta) <= maxStep) {
            return target;
        }
        return current + Math.copySign(maxStep, delta);
    }

    private boolean canAttack(LivingEntity entity) {
        if (!entity.isAlive()) {
            return false;
        }

        if (player().getAttackCooldownProgress(0.5F) < attackProgress.getValueAsFloat()) {
            return false;
        }

        if (onlyCrits.isFlag3() && !isCriticalWindow()) {
            return false;
        }

        if (!throughWalls.isFlag3() && !canSee(entity)) {
            return false;
        }

        return player().squaredDistanceTo(entity) <= range.getValue() * range.getValue();
    }

    private boolean isCriticalWindow() {
        if (player().isOnGround() || player().isSprinting()) {
            return false;
        }

        if (player().getVelocity().y >= 0.0) {
            return false;
        }

        return player().fallDistance > 0.0F;
    }
}
