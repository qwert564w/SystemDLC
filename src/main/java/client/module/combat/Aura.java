package client.module.combat;

import client.module.Category;
import client.module.Feature;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.SliderSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Aura extends Module {

    public enum FovMode { SNAP, HVH, POLAR, MATRIX, GRIM }
    public enum AimTarget { HEAD, CHEST, LEGS, RANDOM, OFFSET }

    private final ListSetting currentMode;
    private final SliderSetting range;
    private final SliderSetting fov;
    private final SliderSetting turnSpeed;
    private final SliderSetting rotationRandomness;
    private final SliderSetting minCps;
    private final SliderSetting maxCps;
    
    private final BooleanSetting failSwing;
    private final BooleanSetting throughWalls;
    private final BooleanSetting autoSwitch;
    private final SliderSetting targetSwitchDelay;
    
    private final BooleanSetting noAttackWhileEating;
    private final BooleanSetting noAttackInGUI;
    private final BooleanSetting smartSprint;
    private final BooleanSetting smartCrit;
    private final BooleanSetting noAttackInLiquid;
    
    private final BooleanSetting quantizationEnabled;
    private final SliderSetting quantizationStep;
    
    private final BooleanSetting smoothRotation;
    private final SliderSetting rotationAcceleration;
    private final BooleanSetting lagCompensation;
    private final SliderSetting reactionDelay;
    
    private final BooleanSetting adaptiveCps;
    private final BooleanSetting microAdjustments;
    private final BooleanSetting attackPauses;
    private final BooleanSetting missChanceDynamic;
    private final SliderSetting maxHitsBeforePause;
    private final SliderSetting pauseDurationMin;
    private final SliderSetting pauseDurationMax;
    
    private final ListSetting aimTarget;
    private final SliderSetting aimOffsetX;
    private final SliderSetting aimOffsetY;
    private final BooleanSetting randomizeAimPoint;
    private final SliderSetting horizontalSpread;
    private final BooleanSetting checkVisibility;

    private Entity target = null;
    private long lastAttackTime = 0;
    private long lastTargetSwitchTime = 0;
    private long targetSeenTime = 0;
    private float currentYawSpeed = 0;
    private float currentPitchSpeed = 0;
    private float microOffsetX = 0, microOffsetY = 0;
    private long lastMicroAdjustTime = 0;
    private long lastPauseEnd = 0;
    private int hitsInRow = 0;
    private double missChance = 0.05;

    public Aura() {
        super("Aura", Category.COMBAT);
        
        currentMode = new ListSetting("Режим", "Пресеты работы ауры", List.of("SNAP", "HVH", "POLAR", "MATRIX", "GRIM"), List.of("POLAR"));
        addSetting(currentMode);
        
        range = new SliderSetting("Дистанция", "Максимальная дистанция атаки", 3.1, 1.0, 6.0, 0.1);
        addSetting(range);
        fov = new SliderSetting("FOV", "Угол обзора для поиска целей", 180.0, 30.0, 360.0, 1.0);
        addSetting(fov);
        turnSpeed = new SliderSetting("Скорость поворота", "Скорость наведения на цель", 28.0, 1.0, 90.0, 0.5);
        addSetting(turnSpeed);
        rotationRandomness = new SliderSetting("Рандом поворота", "Случайное отклонение при наведении", 2.5, 0.0, 10.0, 0.1);
        addSetting(rotationRandomness);
        minCps = new SliderSetting("Мин. CPS", "Минимальное количество кликов в секунду", 8.5, 1.0, 20.0, 0.5);
        addSetting(minCps);
        maxCps = new SliderSetting("Макс. CPS", "Максимальное количество кликов в секунду", 10.5, 1.0, 20.0, 0.5);
        addSetting(maxCps);
        
        failSwing = new BooleanSetting("Промахиваться", "Иногда промахиваться по воздуху", true);
        addSetting(failSwing);
        throughWalls = new BooleanSetting("Через стены", "Атаковать через блоки", false);
        addSetting(throughWalls);
        autoSwitch = new BooleanSetting("Авто-смена цели", "Автоматически менять цель", true);
        addSetting(autoSwitch);
        targetSwitchDelay = new SliderSetting("Задержка смены цели", "Задержка в мс перед сменой цели", 100.0, 0.0, 1000.0, 10.0);
        addSetting(targetSwitchDelay);
        
        noAttackWhileEating = new BooleanSetting("Не бить при еде", "Останавливать атаку при поедании", true);
        addSetting(noAttackWhileEating);
        noAttackInGUI = new BooleanSetting("Не бить в меню", "Останавливать атаку в инвентаре", true);
        addSetting(noAttackInGUI);
        smartSprint = new BooleanSetting("Умный спринт", "Автоматический спринт при атаке", false);
        addSetting(smartSprint);
        smartCrit = new BooleanSetting("Умные криты", "Прыгать для нанесения крита", false);
        addSetting(smartCrit);
        noAttackInLiquid = new BooleanSetting("Не бить в воде", "Останавливать атаку в воде/лаве", true);
        addSetting(noAttackInLiquid);
        
        quantizationEnabled = new BooleanSetting("Квантование", "Ступенчатое наведение (легит)", true);
        addSetting(quantizationEnabled);
        quantizationStep = new SliderSetting("Шаг квантования", "Шаг ступеней при наведении", 0.5, 0.1, 5.0, 0.1);
        addSetting(quantizationStep);
        
        smoothRotation = new BooleanSetting("Плавный поворот", "Использовать ускорение при повороте", true);
        addSetting(smoothRotation);
        rotationAcceleration = new SliderSetting("Ускорение поворота", "Сила ускорения при плавном повороте", 0.8, 0.1, 1.0, 0.05);
        addSetting(rotationAcceleration);
        lagCompensation = new BooleanSetting("Лаг-компенсация", "Учитывать задержку реакции", true);
        addSetting(lagCompensation);
        reactionDelay = new SliderSetting("Задержка реакции", "Имитация задержки реакции человека (мс)", 150.0, 50.0, 500.0, 10.0);
        addSetting(reactionDelay);
        
        adaptiveCps = new BooleanSetting("Адаптивный CPS", "Увеличивать CPS при низком ХП цели", true);
        addSetting(adaptiveCps);
        microAdjustments = new BooleanSetting("Микро-доводки", "Микро-случайные движения прицела", true);
        addSetting(microAdjustments);
        attackPauses = new BooleanSetting("Паузы в атаке", "Иногда делать паузы между ударами", true);
        addSetting(attackPauses);
        missChanceDynamic = new BooleanSetting("Динамический шанс промаха", "Промахиваться чаще при движении цели", true);
        addSetting(missChanceDynamic);
        maxHitsBeforePause = new SliderSetting("Ударов до паузы", "Сколько ударов нанести перед паузой", 3.0, 1.0, 20.0, 1.0);
        addSetting(maxHitsBeforePause);
        pauseDurationMin = new SliderSetting("Мин. пауза", "Минимальная длительность паузы (мс)", 200.0, 50.0, 2000.0, 50.0);
        addSetting(pauseDurationMin);
        pauseDurationMax = new SliderSetting("Макс. пауза", "Максимальная длительность паузы (мс)", 500.0, 50.0, 2000.0, 50.0);
        addSetting(pauseDurationMax);
        
        aimTarget = new ListSetting("Точка прицела", "Куда целаться", List.of("HEAD", "CHEST", "LEGS", "RANDOM", "OFFSET"), List.of("CHEST"));
        addSetting(aimTarget);
        aimOffsetX = new SliderSetting("Оффсет X", "Смещение точки прицела по X", 0.0, -5.0, 5.0, 0.1);
        addSetting(aimOffsetX);
        aimOffsetY = new SliderSetting("Оффсет Y", "Смещение точки прицела по Y", 0.0, -5.0, 5.0, 0.1);
        addSetting(aimOffsetY);
        randomizeAimPoint = new BooleanSetting("Рандом точки", "Случайное смещение точки прицела", true);
        addSetting(randomizeAimPoint);
        horizontalSpread = new SliderSetting("Горизонтальный разброс", "Радиус случайного смещения", 0.1, 0.0, 2.0, 0.05);
        addSetting(horizontalSpread);
        checkVisibility = new BooleanSetting("Проверка видимсти", "Атаковать только видимые цели", true);
        addSetting(checkVisibility);
        
        applyMode(FovMode.POLAR);
    }

    public void applyMode(FovMode mode) {
        switch (mode) {
            case SNAP:
                fov.setValue(360.0); turnSpeed.setValue(90.0); rotationRandomness.setValue(0.5); minCps.setValue(12.0); maxCps.setValue(15.0);
                smoothRotation.setFlag(false); lagCompensation.setFlag(false); attackPauses.setFlag(false);
                missChanceDynamic.setFlag(false); adaptiveCps.setFlag(false); microAdjustments.setFlag(false);
                break;
            case HVH:
                fov.setValue(180.0); turnSpeed.setValue(45.0); rotationRandomness.setValue(1.5); minCps.setValue(11.0); maxCps.setValue(14.0);
                smoothRotation.setFlag(true); lagCompensation.setFlag(false); attackPauses.setFlag(false);
                missChanceDynamic.setFlag(false); adaptiveCps.setFlag(false); microAdjustments.setFlag(true);
                break;
            case POLAR:
                fov.setValue(180.0); turnSpeed.setValue(22.0); rotationRandomness.setValue(2.8); minCps.setValue(8.0); maxCps.setValue(10.0);
                smoothRotation.setFlag(true); lagCompensation.setFlag(true); attackPauses.setFlag(true);
                missChanceDynamic.setFlag(true); adaptiveCps.setFlag(true); microAdjustments.setFlag(true);
                break;
            case MATRIX:
                fov.setValue(180.0); turnSpeed.setValue(30.0); rotationRandomness.setValue(2.0); minCps.setValue(9.0); maxCps.setValue(11.5);
                smoothRotation.setFlag(true); lagCompensation.setFlag(true); attackPauses.setFlag(true);
                missChanceDynamic.setFlag(true); adaptiveCps.setFlag(true); microAdjustments.setFlag(true);
                break;
            case GRIM:
                fov.setValue(150.0); turnSpeed.setValue(18.0); rotationRandomness.setValue(3.5); minCps.setValue(7.5); maxCps.setValue(9.5);
                smoothRotation.setFlag(true); lagCompensation.setFlag(true); attackPauses.setFlag(true);
                missChanceDynamic.setFlag(true); adaptiveCps.setFlag(true); microAdjustments.setFlag(true);
                break;
        }
        resetState();
    }

    private void resetState() {
        target = null;
        lastAttackTime = 0;
        hitsInRow = 0;
        lastPauseEnd = 0;
        currentYawSpeed = 0;
        currentPitchSpeed = 0;
        microOffsetX = 0; microOffsetY = 0;
    }

    @Override
    public void onTick() {
        if (!isEnabled()) return;
        if (Feature.mc.player == null || Feature.mc.world == null) return;

        if (shouldBlockAttack()) return;

        updateTarget();

        if (target != null && target.isAlive() && Feature.mc.player.distanceTo(target) <= range.getValue()) {
            if (smartSprint.isFlag() && !Feature.mc.player.isSprinting()) Feature.mc.player.setSprinting(true);
            rotateToEntity(target);
            if (shouldAttack(target)) {
                if (smartCrit.isFlag() && Feature.mc.player.isOnGround() && !Feature.mc.player.isTouchingWater())
                    Feature.mc.player.jump();
                attackEntity(target);
            }
        } else {
            if (failSwing.isFlag() && ThreadLocalRandom.current().nextFloat() < 0.1f)
                Feature.mc.player.swingHand(Feature.mc.player.getActiveHand());
            if (!autoSwitch.isFlag() && target != null) target = null;
            if (smartSprint.isFlag() && Feature.mc.player.input.movementForward == 0 && Feature.mc.player.input.movementSideways == 0)
                Feature.mc.player.setSprinting(false);
        }
    }

    private boolean shouldBlockAttack() {
        if (noAttackWhileEating.isFlag() && Feature.mc.player.isUsingItem()) {
            ItemStack item = Feature.mc.player.getActiveItem();
            if (!item.isEmpty() && item.getItem() instanceof FoodItem) return true;
        }
        if (noAttackInGUI.isFlag() && Feature.mc.currentScreen != null) return true;
        if (noAttackInLiquid.isFlag() && (Feature.mc.player.isTouchingWater() || Feature.mc.player.isInLava())) return true;
        return false;
    }

    private void updateTarget() {
        if (target != null && target.isAlive() && Feature.mc.player.distanceTo(target) <= range.getValue()) return;
        long now = System.currentTimeMillis();
        if (now - lastTargetSwitchTime < targetSwitchDelay.getValue()) return;
        Entity newTarget = findBestTarget();
        if (newTarget != null) {
            target = newTarget;
            lastTargetSwitchTime = now;
            targetSeenTime = now;
            currentYawSpeed = 0;
            currentPitchSpeed = 0;
        } else target = null;
    }

    private Entity findBestTarget() {
        if (Feature.mc.world == null || Feature.mc.player == null) return null;
        return Feature.mc.world.getPlayers()
                .stream()
                .filter(e -> e != Feature.mc.player)
                .filter(Entity::isAlive)
                .filter(e -> !isBot(e))
                .filter(e -> Feature.mc.player.distanceTo(e) <= range.getValue())
                .filter(e -> isInFov(e, fov.getValue()))
                .min((a, b) -> Double.compare(Feature.mc.player.distanceTo(a), Feature.mc.player.distanceTo(b)))
                .orElse(null);
    }

    private boolean isBot(Entity entity) {
        String name = entity.getName().getString();
        return name.startsWith("Bot") || name.contains("NPC") || name.contains("§r") || name.startsWith("§");
    }

    private boolean isInFov(Entity entity, double fovVal) {
        if (Feature.mc.player == null) return false;
        Vec3d playerPos = Feature.mc.player.getPos();
        Vec3d targetPos = entity.getPos();
        double deltaX = targetPos.x - playerPos.x;
        double deltaZ = targetPos.z - playerPos.z;
        double yawToEntity = Math.atan2(deltaZ, deltaX) * 180.0 / Math.PI - 90.0;
        double playerYaw = Feature.mc.player.getYaw();
        float angleDiff = MathHelper.wrapDegrees((float)(playerYaw - yawToEntity));
        return Math.abs(angleDiff) <= fovVal / 2;
    }

    private Vec3d getAimPoint(Entity target) {
        Vec3d base = target.getPos();
        double height = target.getBoundingBox().getLengthY();
        double yOffset = 0.0;
        String atStr = aimTarget.getString();
        switch (atStr) {
            case "HEAD": yOffset = height * 0.85; break;
            case "CHEST": yOffset = height * 0.5; break;
            case "LEGS": yOffset = height * 0.15; break;
            case "RANDOM":
                double r = ThreadLocalRandom.current().nextDouble();
                if (r < 0.33) yOffset = height * 0.85;
                else if (r < 0.66) yOffset = height * 0.5;
                else yOffset = height * 0.15;
                break;
            case "OFFSET":
                double dist = Feature.mc.player.distanceTo(target);
                double angleRadY = Math.toRadians(aimOffsetY.getValue());
                yOffset = height * 0.5 + Math.tan(angleRadY) * dist;
                break;
        }
        double xOffset = 0, zOffset = 0;
        if (randomizeAimPoint.isFlag()) {
            xOffset = (ThreadLocalRandom.current().nextFloat() - 0.5f) * horizontalSpread.getValue();
            zOffset = (ThreadLocalRandom.current().nextFloat() - 0.5f) * horizontalSpread.getValue();
        }
        if (atStr.equals("OFFSET")) {
            double dist = Feature.mc.player.distanceTo(target);
            double angleHorizRad = Math.toRadians(aimOffsetX.getValue());
            xOffset += Math.sin(angleHorizRad) * dist * 0.1;
            zOffset += Math.cos(angleHorizRad) * dist * 0.1;
        }
        return base.add(xOffset, yOffset, zOffset);
    }

    private void rotateToEntity(Entity target) {
        if (Feature.mc.player == null) return;
        Vec3d aimPoint = getAimPoint(target);
        Vec3d playerPos = Feature.mc.player.getPos();
        double deltaX = aimPoint.x - playerPos.x;
        double deltaY = aimPoint.y - (playerPos.y + Feature.mc.player.getEyeHeight(Feature.mc.player.getPitch()));
        double deltaZ = aimPoint.z - playerPos.z;
        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float targetYaw = (float)(Math.atan2(deltaZ, deltaX) * 180.0 / Math.PI) - 90.0f;
        float targetPitch = (float)(-Math.atan2(deltaY, distance) * 180.0 / Math.PI);

        targetYaw += (ThreadLocalRandom.current().nextFloat() - 0.5f) * rotationRandomness.getValue();
        targetPitch += (ThreadLocalRandom.current().nextFloat() - 0.5f) * rotationRandomness.getValue();

        if (microAdjustments.isFlag()) {
            long now = System.currentTimeMillis();
            if (now - lastMicroAdjustTime > 200) {
                microOffsetX = (ThreadLocalRandom.current().nextFloat() - 0.5f) * 0.3f;
                microOffsetY = (ThreadLocalRandom.current().nextFloat() - 0.5f) * 0.3f;
                lastMicroAdjustTime = now;
            }
            targetYaw += microOffsetX;
            targetPitch += microOffsetY;
        }

        if (quantizationEnabled.isFlag()) {
            float step = (float) quantizationStep.getValue();
            targetYaw = Math.round(targetYaw / step) * step;
            targetPitch = Math.round(targetPitch / step) * step;
        }

        if (lagCompensation.isFlag()) {
            long now = System.currentTimeMillis();
            long elapsed = now - targetSeenTime;
            long reactDelay = (long) reactionDelay.getValue();
            if (elapsed < reactDelay) {
                float factor = 1.0f - (float)elapsed / reactDelay;
                float limitedSpeed = (float) turnSpeed.getValue() * (0.5f + 0.5f * factor);
                applyRotation(targetYaw, targetPitch, limitedSpeed);
                return;
            }
        }
        applyRotation(targetYaw, targetPitch, (float) turnSpeed.getValue());
    }

    private void applyRotation(float targetYaw, float targetPitch, float speed) {
        float currentYaw = Feature.mc.player.getYaw();
        float currentPitch = Feature.mc.player.getPitch();
        float yawDiff = MathHelper.wrapDegrees(targetYaw - currentYaw);
        float pitchDiff = MathHelper.wrapDegrees(targetPitch - currentPitch);

        if (smoothRotation.isFlag()) {
            float maxChangeYaw = speed;
            float maxChangePitch = speed * 0.8f;
            float accel = (float) rotationAcceleration.getValue();
            currentYawSpeed += (Math.abs(yawDiff) > 0.1f ? Math.signum(yawDiff) * accel : -accel * 0.1f);
            currentYawSpeed = MathHelper.clamp(currentYawSpeed, -maxChangeYaw, maxChangeYaw);
            currentPitchSpeed += (Math.abs(pitchDiff) > 0.1f ? Math.signum(pitchDiff) * accel : -accel * 0.1f);
            currentPitchSpeed = MathHelper.clamp(currentPitchSpeed, -maxChangePitch, maxChangePitch);
            if (Math.abs(yawDiff) > 0.1f) Feature.mc.player.setYaw(currentYaw + currentYawSpeed);
            if (Math.abs(pitchDiff) > 0.1f) Feature.mc.player.setPitch(currentPitch + currentPitchSpeed);
            if (Math.abs(yawDiff) < 0.5f) currentYawSpeed *= 0.9f;
            if (Math.abs(pitchDiff) < 0.5f) currentPitchSpeed *= 0.9f;
        } else {
            if (Math.abs(yawDiff) > speed) yawDiff = Math.signum(yawDiff) * speed;
            if (Math.abs(pitchDiff) > speed * 0.8f) pitchDiff = Math.signum(pitchDiff) * speed * 0.8f;
            Feature.mc.player.setYaw(currentYaw + yawDiff);
            Feature.mc.player.setPitch(currentPitch + pitchDiff);
        }
    }

    private boolean shouldAttack(Entity target) {
        long now = System.currentTimeMillis();
        if (attackPauses.isFlag() && now < lastPauseEnd) return false;
        long elapsed = now - lastAttackTime;
        double cps = getCurrentCps(target);
        long interval = (long)(1000.0 / cps);
        if (missChanceDynamic.isFlag()) {
            double distance = Feature.mc.player.distanceTo(target);
            double speed = target.getVelocity().length();
            missChance = Math.min(0.3, 0.05 + (distance / range.getValue()) * 0.5 + speed * 0.2);
        }
        if (ThreadLocalRandom.current().nextFloat() < missChance && elapsed > interval / 2) return false;
        return elapsed >= interval;
    }

    private double getCurrentCps(Entity target) {
        double baseMin = minCps.getValue();
        double baseMax = maxCps.getValue();
        if (adaptiveCps.isFlag() && target instanceof PlayerEntity) {
            float health = ((PlayerEntity) target).getHealth();
            float healthFactor = 1.0f - Math.min(1, health / 20f);
            double boost = 1.0 + healthFactor * 0.2;
            baseMin *= boost;
            baseMax *= boost;
        }
        return baseMin + (baseMax - baseMin) * ThreadLocalRandom.current().nextDouble();
    }

    private void attackEntity(Entity target) {
        if (Feature.mc.player == null || Feature.mc.interactionManager == null) return;
        if (Feature.mc.player.distanceTo(target) > range.getValue()) return;
        if (checkVisibility.isFlag() && !throughWalls.isFlag() && !Feature.mc.player.canSee(target)) return;

        Feature.mc.interactionManager.attackEntity(Feature.mc.player, target);
        Feature.mc.player.swingHand(Feature.mc.player.getActiveHand());
        lastAttackTime = System.currentTimeMillis();
        hitsInRow++;

        if (attackPauses.isFlag()) {
            if (hitsInRow >= maxHitsBeforePause.getValue() && ThreadLocalRandom.current().nextFloat() < 0.3f) {
                long pause = (long) pauseDurationMin.getValue() + (long)(ThreadLocalRandom.current().nextFloat() * (pauseDurationMax.getValue() - pauseDurationMin.getValue()));
                lastPauseEnd = System.currentTimeMillis() + pause;
                hitsInRow = 0;
            }
        }
    }
}