// language: Java 21, Minecraft 1.21.4, Fabric/Yarn
package aethereal.module.combat;

import aethereal.core.*;
import aethereal.event.InputEvent;
import aethereal.event.WillLandEvent;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ModeSetting;
import aethereal.setting.MultiModeSetting;
import aethereal.setting.SliderSetting;
import aethereal.ui.screen.AssistantScreen;
import aethereal.ui.screen.GUIScreen;
import aethereal.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@ModuleRegister(name = "Aura", description = "Автоматически атакует цели рядом с вами", category = Category.Combat)
public class Aura extends Module {

    private static final String[] LOCAL_TEST_HOSTS = {
            "localhost",
            "127.0.0.1",
            "[::1]"
    };

    final float[] timers = {
            -1.0f, -1.0f, -1.0f, -1.0f,
            0.0f, -1.0f, 0.0f, 0.0f,
            0.0f, -1.0f, 0.0f, 0.0f
    };

    final int[] savedSlots = {-1, -1};

    private final ModeSetting rotationType = new ModeSetting(
            "Выберите тип наведения",
            "Легит",
            "ФанТайм",
            "ФанТайм ФОВ",
            "Легит"
    );

    private final MultiModeSetting targetSettings = new MultiModeSetting(
            "Цели для атаки",
            new BooleanSetting("Без брони", true),
            new BooleanSetting("Враждебные мобы", false),
            new BooleanSetting("Животные", false),
            new BooleanSetting("Друзья", false),
            new BooleanSetting("Игроки", true)
    );

    private final SliderSetting attackDistance = new SliderSetting(
            "Дистанция атаки",
            3.0f,
            0.1f,
            6.0f,
            0.1f
    );

    private final SliderSetting extraReach = new SliderSetting(
            "Дополнительная дистанция",
            0.0f,
            0.0f,
            3.0f,
            0.1f
    );

    private final BooleanSetting onlyCrits = new BooleanSetting(
            "Только критические удары",
            false
    );

    private final BooleanSetting adaptiveHits = new BooleanSetting(
            "Адаптивные удары",
            true
    ).a(() -> {
        return this.onlyCrits.c();
    });

    private final MultiModeSetting dontHitWhen = new MultiModeSetting(
            "Не бить когда",
            new BooleanSetting("Используется предмет", true),
            new BooleanSetting("Открыт контейнер", true),
            new BooleanSetting("Враг за стеной", true)
    );

    private final BooleanSetting shieldBreaking = new BooleanSetting(
            "Пробитие щита legacy",
            true
    );

    private final BooleanSetting smartSprint = new BooleanSetting(
            "Умный спринт",
            false
    );

    private final ModeSetting targetPriority = new ModeSetting(
            "Приоритет цели",
            "Прицел",
            "Прицел",
            "Дистанция",
            "ХП"
    );

    private final ModeSetting movementCorrection = new ModeSetting(
            "Коррекция движения",
            "Фокус",
            "Фокус",
            "Свободно"
    );

    private final ModeSetting targetVisualization = new ModeSetting(
            "Визуализация цели",
            "Сферы",
            "Сферы",
            "Круг",
            "Тест"
    );

    private final float[] pitchHistory = new float[30];

    private final SliderSetting maxReach = new SliderSetting(
            "Максимальная дистанция",
            3.0f,
            0.1f,
            6.0f,
            0.1f
    );

    private final SliderSetting rotationSpeed = new SliderSetting(
            "Скорость наведения",
            140.0f,
            20.0f,
            360.0f,
            1.0f
    );

    private final SliderSetting aimSmoothing = new SliderSetting(
            "Плавность наведения",
            0.25f,
            0.01f,
            1.0f,
            0.01f
    );

    private final SliderSetting maceSmoothing = new SliderSetting(
            "Плавность мейса",
            0.75f,
            0.1f,
            1.0f,
            0.01f
    );

    private final SliderSetting yawNoise = new SliderSetting(
            "Шум yaw",
            0.25f,
            0.0f,
            2.0f,
            0.01f
    );

    private final SliderSetting pitchNoise = new SliderSetting(
            "Шум pitch",
            0.18f,
            0.0f,
            2.0f,
            0.01f
    );

    private final SliderSetting noiseFrequency = new SliderSetting(
            "Частота шума",
            1.0f,
            0.1f,
            3.0f,
            0.01f
    );

    private final SliderSetting attackDelay = new SliderSetting(
            "Задержка удара, тики",
            10.0f,
            0.0f,
            40.0f,
            1.0f
    );

    private final SliderSetting attackProgress = new SliderSetting(
            "Прогресс атаки",
            0.9f,
            0.1f,
            1.0f,
            0.01f
    );

    private final SliderSetting fallThreshold = new SliderSetting(
            "Порог падения",
            1.5f,
            0.0f,
            5.0f,
            0.1f
    );

    private final SliderSetting shieldSwapDelay = new SliderSetting(
            "Задержка после свапа щита",
            1.0f,
            0.0f,
            10.0f,
            1.0f
    );

    private final ModeSetting shieldMode = new ModeSetting(
            "Пробитие щита",
            "Хотбар",
            "Выкл",
            "Хотбар",
            "Инвентарь"
    );

    private final ModeSetting wallMode = new ModeSetting(
            "Стены",
            "Не таргетить",
            "Не таргетить",
            "Таргетить, не бить",
            "Бить сквозь стены"
    );

    private final ModeSetting hitPolicy = new ModeSetting(
            "Политика ударов",
            "Обычный",
            "Обычный",
            "Только крит",
            "Адаптивный",
            "Всегда"
    );

    private final BooleanSetting allowSprintReset = new BooleanSetting(
            "Сбрасывать спринт",
            true
    );

    private final BooleanSetting allowMaceLogic = new BooleanSetting(
            "Логика мейса",
            true
    );

    public int attackCooldown = 0;

    boolean willLand;
    boolean randomDirection = false;

    private int pendingShieldSwap = 0;
    private int savedSelectedSlot = -1;

    private LivingEntity target;

    // ===== Polar bypass state =====
    private float noisePhaseA = 0.0f;
    private float noisePhaseB = 0.0f;
    private float noisePhaseC = 0.0f;

    private int reactionDelay = 0;
    private int lostTargetTicks = 0;
    private int randomizedDelay = -1;
    private int lastTargetEntityId = -1;

    public Aura() {
        a(
                this.attackDistance,
                this.extraReach,
                this.maxReach,

                this.rotationType,
                this.rotationSpeed,
                this.aimSmoothing,
                this.maceSmoothing,

                this.yawNoise,
                this.pitchNoise,
                this.noiseFrequency,

                this.attackDelay,
                this.attackProgress,
                this.fallThreshold,

                this.shieldMode,
                this.shieldSwapDelay,

                this.wallMode,
                this.hitPolicy,

                this.allowSprintReset,
                this.allowMaceLogic,

                this.movementCorrection,
                this.targetVisualization,
                this.targetPriority,
                this.targetSettings,
                this.dontHitWhen,

                this.onlyCrits,
                this.adaptiveHits,
                this.shieldBreaking,
                this.smartSprint
        );
    }

    public ModeSetting getVisualizationMode() {
        return this.targetVisualization;
    }

    public LivingEntity getTarget() {
        return this.target;
    }

    @Override
    public void b() {
        super.b();
        resetState();

        this.timers[8] = 2.0f;

        if (this.timers[9] == -1.0f) {
            this.timers[9] = (int) MathUtil.a(9.0f, 13.0f);
        }
    }

    @Override
    public void c() {
        super.c();
        resetState();
    }

    private void resetState() {
        forceRestoreSlot();

        Delta.getInstance().getModuleProcessor().k().reset();

        this.timers[0] = -1.0f;
        this.timers[1] = 0.0f;
        this.timers[2] = -1.0f;
        this.timers[3] = 0.0f;
        this.timers[4] = 0.0f;
        this.timers[5] = -1.0f;
        this.timers[6] = 0.0f;
        this.timers[7] = 0.0f;
        this.timers[8] = 1.0f;
        this.timers[9] = -1.0f;
        this.timers[10] = 0.0f;
        this.timers[11] = 0.0f;

        this.attackCooldown = 0;
        this.target = null;

        this.randomDirection = false;
        this.willLand = false;

        this.pendingShieldSwap = 0;
        this.savedSelectedSlot = -1;

        this.noisePhaseA = 0.0f;
        this.noisePhaseB = 0.0f;
        this.noisePhaseC = 0.0f;
        this.reactionDelay = 0;
        this.lostTargetTicks = 0;
        this.randomizedDelay = -1;
        this.lastTargetEntityId = -1;

        Arrays.fill(this.pitchHistory, mc.player != null ? mc.player.getPitch() : 0.0f);
    }

    private boolean authorizedEnvironment() {
        if (mc == null) {
            return false;
        }

        if (mc.isInSingleplayer()) {
            return true;
        }

        var server = mc.getCurrentServerEntry();
        if (server == null || server.address == null) {
            return false;
        }

        String host = server.address.toLowerCase(java.util.Locale.ROOT);

        for (String allowed : LOCAL_TEST_HOSTS) {
            if (host.equals(allowed) || host.startsWith(allowed + ":")) {
                return true;
            }
        }

        return false;
    }

    private boolean canRun() {
        return authorizedEnvironment()
                && mc.player != null
                && mc.world != null
                && mc.interactionManager != null;
    }

    private float fl(SliderSetting setting) {
        return setting == null ? 0.0f : setting.c().floatValue();
    }

    private int in(SliderSetting setting) {
        return setting == null ? 0 : setting.c().intValue();
    }

    private boolean bl(BooleanSetting setting) {
        return setting != null && setting.c().booleanValue();
    }

    private boolean bl(MultiModeSetting group, String name) {
        if (group == null) {
            return false;
        }

        BooleanSetting setting = group.a(name);
        return setting != null && setting.c().booleanValue();
    }

    private double reach() {
        double base = fl(this.attackDistance);
        double extra = fl(this.extraReach);
        double max = fl(this.maxReach);

        return MathHelper.clamp(base + extra, 0.1d, max);
    }

    private boolean maceActive() {
        return bl(this.allowMaceLogic) && MaceUtil.a();
    }

    private boolean targetThroughWalls() {
        return this.wallMode.l("Таргетить, не бить")
                || this.wallMode.l("Бить сквозь стены");
    }

    private boolean attackThroughWalls() {
        return this.wallMode.l("Бить сквозь стены");
    }

    private boolean jumpPressed() {
        return mc.options != null && mc.options.jumpKey.isPressed();
    }

    private boolean mainhandCoolingDown() {
        return mc.player.getItemCooldownManager().isCoolingDown(
                mc.player.getMainHandStack().getItem()
        );
    }

    private float wrapYaw(float yaw) {
        return MathHelper.wrapDegrees(yaw);
    }

    private float clampPitch(float pitch) {
        return (float) MathHelper.clamp(pitch, -90.0d, 90.0d);
    }

    private int currentAttackDelay() {
        if (this.randomizedDelay <= 0) {
            int base = in(this.attackDelay);
            int jitter = (int) MathUtil.a(-2.0f, 2.0f);
            this.randomizedDelay = Math.max(1, base + jitter);
        }
        return this.randomizedDelay;
    }

    private float randomWalkNoise() {
        float freq = fl(this.noiseFrequency);

        this.noisePhaseA += (float) ((Math.random() - 0.5d) * 0.35d * freq);
        this.noisePhaseB += (float) ((Math.random() - 0.5d) * 0.20d * freq);
        this.noisePhaseC += (float) ((Math.random() - 0.5d) * 0.10d * freq);

        this.noisePhaseA *= 0.92f;
        this.noisePhaseB *= 0.94f;
        this.noisePhaseC *= 0.96f;

        this.noisePhaseA = MathHelper.clamp(this.noisePhaseA, -1.5f, 1.5f);
        this.noisePhaseB = MathHelper.clamp(this.noisePhaseB, -1.0f, 1.0f);
        this.noisePhaseC = MathHelper.clamp(this.noisePhaseC, -0.6f, 0.6f);

        return this.noisePhaseA * 0.6f
                + this.noisePhaseB * 0.3f
                + this.noisePhaseC * 0.1f;
    }

    @EventTarget
    public void onInput(InputEvent e) {
        if (!canRun()) {
            return;
        }

        if (this.target != null) {
            MoveUtil.a(
                    e,
                    !this.movementCorrection.l("Фокус") ? Look.b() : this.timers[1],
                    2
            );
        }

        if (this.timers[0] > 0.0f
                && this.target != null
                && AuraUtil.a(this.target, reach())) {
            e.setForward(0.0f);
            e.setStrafe(0.0f);

            float[] fArr = this.timers;
            fArr[0] = fArr[0] - 1.0f;
        }
    }

    @EventTarget
    public void onGlobalEvent(GlobalEvent e) {
        if (!canRun()) {
            return;
        }

        if (this.pendingShieldSwap > 0) {
            this.pendingShieldSwap--;
        }

        if (!isValidTarget(this.target)) {
            LivingEntity prev = this.target;

            this.target = findTargetWithParam(targetThroughWalls()).orElse(null);

            if (this.target != prev) {
                this.timers[10] = 0.0f;
                this.timers[11] = 0.0f;
                Arrays.fill(this.pitchHistory, mc.player != null ? mc.player.getPitch() : 0.0f);

                int newId = this.target != null ? this.target.getId() : -1;
                if (newId != this.lastTargetEntityId) {
                    this.lastTargetEntityId = newId;

                    if (this.target != null) {
                        this.reactionDelay = (int) MathUtil.a(1.0f, 2.5f);
                        this.lostTargetTicks = 0;
                        this.randomizedDelay = -1;
                    }
                }
            }
        }

        restoreSelectedSlot();

        if (this.target != null) {
            rotateToTarget();
            performAttack();
            return;
        }

        this.timers[8] = 1.0f;
    }

    @EventTarget
    public void onWillLand(WillLandEvent e) {
        if (!canRun()) {
            return;
        }

        this.willLand = e.b() && !mc.player.isOnGround();
    }

    private int findAxeSlot(int from, int to) {
        for (int i = from; i < to; i++) {
            if (mc.player.getInventory().getStack(i).getItem() instanceof AxeItem) {
                return i;
            }
        }

        return -1;
    }

    private void forceRestoreSlot() {
        if (mc.player == null) {
            this.savedSlots[0] = -1;
            this.savedSlots[1] = -1;
            this.savedSelectedSlot = -1;
            this.pendingShieldSwap = 0;
            return;
        }

        if (this.savedSlots[0] != -1) {
            mc.player.getInventory().selectedSlot = this.savedSlots[0];
            this.savedSlots[0] = -1;
        }

        if (this.savedSlots[1] != -1) {
            var handler = Delta.getInstance()
                    .getModuleProcessor()
                    .v()
                    .getInventoryHandler();

            if (handler != null
                    && handler.a().isEmpty()
                    && mc.player.getInventory().selectedSlot == this.savedSelectedSlot) {
                handler.moveItem(
                        this.savedSelectedSlot,
                        this.savedSlots[1],
                        1
                );
            }

            this.savedSlots[1] = -1;
            this.savedSelectedSlot = -1;
        }

        this.pendingShieldSwap = 0;
    }

    private void restoreSelectedSlot() {
        if (!canRun()) {
            forceRestoreSlot();
            return;
        }

        if (this.pendingShieldSwap > 0) {
            return;
        }

        if (!this.shieldMode.l("Выкл")
                && this.target != null
                && this.target.isBlocking()) {
            return;
        }

        if (this.savedSlots[0] != -1) {
            mc.player.getInventory().selectedSlot = this.savedSlots[0];
            this.savedSlots[0] = -1;
        }

        if (this.savedSlots[1] != -1) {
            var handler = Delta.getInstance()
                    .getModuleProcessor()
                    .v()
                    .getInventoryHandler();

            if (handler != null
                    && handler.a().isEmpty()
                    && mc.player.getInventory().selectedSlot == this.savedSelectedSlot) {
                handler.moveItem(
                        this.savedSelectedSlot,
                        this.savedSlots[1],
                        1
                );
            }

            this.savedSlots[1] = -1;
            this.savedSelectedSlot = -1;
        }
    }

    private void performAttack() {
        if (!canRun() || this.target == null) {
            return;
        }

        double r = reach();
        boolean walls = attackThroughWalls();

        if (!AuraUtil.a(mc.player.getYaw(), mc.player.getPitch(), r, this.target, walls)) {
            return;
        }

        if (this.pendingShieldSwap > 0) {
            return;
        }

        if (tryShieldBreak(r, walls)) {
            return;
        }

        if (!canAttack()) {
            return;
        }

        if (shouldStopSprint()) {
            stopSprint();
            return;
        }

        if (maceBlocked()) {
            return;
        }

        doAttack();
    }

    private boolean tryShieldBreak(double r, boolean walls) {
        if (this.shieldMode.l("Выкл")
                || this.target == null
                || !this.target.isBlocking()) {
            return false;
        }

        if (!canAttackSoft()) {
            return true;
        }

        if (mc.player.getMainHandStack().getItem() instanceof AxeItem) {
            if (!AuraUtil.a(mc.player.getYaw(), mc.player.getPitch(), r, this.target, walls)) {
                return true;
            }

            if (!canAttack()) {
                return true;
            }

            if (shouldStopSprint()) {
                stopSprint();
                return true;
            }

            if (maceBlocked()) {
                return true;
            }

            doAttack();
            return true;
        }

        if (this.shieldMode.l("Хотбар")) {
            int hotbar = findAxeSlot(0, 9);

            if (hotbar == -1) {
                return false;
            }

            if (mc.player.getInventory().selectedSlot != hotbar) {
                if (this.savedSlots[0] == -1) {
                    this.savedSlots[0] = mc.player.getInventory().selectedSlot;
                }

                mc.player.getInventory().selectedSlot = hotbar;
                this.pendingShieldSwap = Math.max(1, in(this.shieldSwapDelay));
            }

            return true;
        }

        if (this.shieldMode.l("Инвентарь")) {
            int hotbar = findAxeSlot(0, 9);

            if (hotbar != -1) {
                if (mc.player.getInventory().selectedSlot != hotbar) {
                    if (this.savedSlots[0] == -1) {
                        this.savedSlots[0] = mc.player.getInventory().selectedSlot;
                    }

                    mc.player.getInventory().selectedSlot = hotbar;
                    this.pendingShieldSwap = Math.max(1, in(this.shieldSwapDelay));
                }

                return true;
            }

            int inventory = findAxeSlot(9, 36);

            if (inventory == -1) {
                return false;
            }

            var handler = Delta.getInstance()
                    .getModuleProcessor()
                    .v()
                    .getInventoryHandler();

            if (this.savedSlots[1] == -1
                    && handler != null
                    && handler.a().isEmpty()) {
                this.savedSlots[1] = inventory;
                this.savedSelectedSlot = mc.player.getInventory().selectedSlot;

                handler.moveItem(
                        inventory,
                        this.savedSelectedSlot,
                        1
                );

                mc.player.getInventory().selectedSlot = this.savedSelectedSlot;
                this.pendingShieldSwap = Math.max(1, in(this.shieldSwapDelay));
            }

            return true;
        }

        return true;
    }

    private boolean canAttackSoft() {
        if (!canRun() || this.target == null) {
            return false;
        }

        if (bl(this.dontHitWhen, "Используется предмет")
                && mc.player.isUsingItem()
                && mc.player.getItemUseTimeLeft() > 0) {
            return false;
        }

        if (bl(this.dontHitWhen, "Открыт контейнер")
                && mc.currentScreen != null
                && !(mc.currentScreen instanceof GUIScreen)
                && !(mc.currentScreen instanceof AssistantScreen)) {
            return false;
        }

        return true;
    }

    private boolean shouldStopSprint() {
        if (!bl(this.allowSprintReset)) {
            return false;
        }

        boolean wasSprinting = ((platform.inject.accessors.ClientPlayerEntityAccessor) mc.player)
                .getWasSprinting();

        return wasSprinting
                && !mc.player.isTouchingWater()
                && !mc.player.isInLava()
                && !mc.player.isSwimming()
                && !mc.player.isOnGround();
    }

    private void stopSprint() {
        ((platform.inject.accessors.ClientPlayerEntityAccessor) mc.player).setWasSprinting(false);
        mc.player.setSprinting(false);

        mc.player.networkHandler.sendPacket(
                new ClientCommandC2SPacket(
                        mc.player,
                        ClientCommandC2SPacket.Mode.STOP_SPRINTING
                )
        );

        this.timers[0] = 1.0f;
    }

    private boolean maceBlocked() {
        if (!maceActive()) {
            return false;
        }

        if (mc.player.isGliding()) {
            return true;
        }

        if (mc.player.fallDistance <= fl(this.fallThreshold)) {
            return false;
        }

        return !this.willLand && !MaceUtil.b();
    }

    private void doAttack() {
        if (mc.interactionManager == null || this.target == null) {
            return;
        }

        this.timers[3] = 0.0f;

        mc.interactionManager.attackEntity(mc.player, this.target);
        mc.player.swingHand(Hand.MAIN_HAND);

        markAttacked();
    }

    private void markAttacked() {
        this.attackCooldown = 0;
        this.timers[5] = MathUtil.a(8.0f, 10.0f);
        this.timers[9] = (int) MathUtil.a(9.0f, 13.0f);

        if (this.timers[2] == -1.0f) {
            this.timers[4] = (int) MathUtil.a(30.0f, 35.0f);
        }

        this.timers[2] += 1.0f;

        this.randomizedDelay = -1;
    }

    public boolean canAttack() {
        if (!canAttackSoft()) {
            return false;
        }

        if (!AuraUtil.a(this.target, reach())) {
            return false;
        }

        if (this.attackCooldown < currentAttackDelay()) {
            return false;
        }

        if (mc.player.getAttackCooldownProgress(0.5f) < fl(this.attackProgress)) {
            return false;
        }

        if (maceActive() || mc.player.fallDistance > fl(this.fallThreshold)) {
            if (mainhandCoolingDown()) {
                return false;
            }
        }

        return passesHitPolicy();
    }

    private boolean passesHitPolicy() {
        String policy = this.hitPolicy.c();

        if ("Всегда".equals(policy)) {
            return true;
        }

        if ("Только крит".equals(policy) || bl(this.onlyCrits)) {
            return AuraUtil.c();
        }

        if ("Адаптивный".equals(policy)) {
            return AuraUtil.c()
                    || (bl(this.adaptiveHits)
                    && mc.player.isOnGround()
                    && !jumpPressed());
        }

        return AuraUtil.c() || !AuraUtil.b();
    }

    private boolean isEntityReachable(LivingEntity entity) {
        if (!canRun() || entity == null) {
            return false;
        }

        double r = reach();

        if (Delta.getInstance().getModuleProcessor().t().G().m() && mc.player.isGliding()) {
            r = Math.max(r, fl(this.maxReach));
        }

        return AuraUtil.a((Entity) entity, r);
    }

    private Optional<LivingEntity> findTarget() {
        return findTargetWithParam(true);
    }

    private Optional<LivingEntity> findTargetWithParam(boolean allowBehindWalls) {
        if (mc.world == null || mc.player == null) {
            return Optional.empty();
        }

        Vec3d eye = mc.player.getEyePos();
        double r = reach();

        Comparator<LivingEntity> priorityComparator;

        switch (this.targetPriority.c()) {
            case "Дистанция":
                priorityComparator = Comparator.comparingDouble(e -> AuraUtil.a(e));
                break;

            case "ХП":
                priorityComparator = Comparator.comparingDouble(LivingEntity::getHealth);
                break;

            case "Прицел":
            default:
                priorityComparator = Comparator.comparingDouble(e -> {
                    Vec3d to = e.getBoundingBox().getCenter().subtract(eye);

                    if (to.lengthSquared() < 1.0E-9d) {
                        return 0.0d;
                    }

                    Vec3d look = Vec3d.fromPolar(mc.player.getPitch(), mc.player.getYaw());
                    double dot = look.dotProduct(to.normalize());

                    if (Double.isNaN(dot)) {
                        return Double.POSITIVE_INFINITY;
                    }

                    dot = MathHelper.clamp(dot, -1.0d, 1.0d);
                    return Math.acos(dot);
                });
                break;
        }

        Comparator<LivingEntity> order;

        if (maceActive()) {
            Comparator<LivingEntity> visibility = Comparator.comparing(
                    (LivingEntity e) -> Boolean.valueOf(!AuraUtil.a(eye, e, r))
            );

            Comparator<LivingEntity> armor = Comparator.comparingDouble(
                    (LivingEntity e) -> hasArmor(e) ? 1.0d : 0.0d
            );

            order = visibility.thenComparing(armor).thenComparing(priorityComparator);
        } else {
            order = priorityComparator;
        }

        Stream<LivingEntity> stream = StreamSupport.stream(
                        mc.world.getEntities().spliterator(),
                        false
                )
                .filter(LivingEntity.class::isInstance)
                .map(LivingEntity.class::cast)
                .filter(e -> e != mc.player && e.isAlive())
                .filter(this::isEntityReachable)
                .filter(this::isValidTarget);

        if (!allowBehindWalls) {
            stream = stream.filter(e -> AuraUtil.a(eye, e, r));
        }

        return stream.min(order);
    }

    private boolean isValidTarget(LivingEntity entity) {
        if (entity == null || !entity.isAlive()) {
            return false;
        }

        if (entity instanceof PlayerEntity) {
            boolean isFriend = Delta.getInstance()
                    .getModuleProcessor()
                    .e()
                    .d(entity.getName().getString());

            boolean naked = Stream.of(
                            EquipmentSlot.HEAD,
                            EquipmentSlot.CHEST,
                            EquipmentSlot.LEGS,
                            EquipmentSlot.FEET
                    )
                    .noneMatch(slot -> entity.getEquippedStack(slot).getItem() instanceof ArmorItem);

            if (!isSettingEnabled("Игроки")) {
                return false;
            }

            if (isFriend) {
                return isSettingEnabled("Друзья");
            }

            return !naked || isSettingEnabled("Без брони");
        }

        if (entity instanceof HostileEntity
                || entity instanceof SlimeEntity
                || entity instanceof FlyingEntity
                || entity instanceof EnderDragonEntity) {
            return isSettingEnabled("Враждебные мобы");
        }

        if (entity instanceof PassiveEntity
                || entity instanceof GolemEntity
                || entity instanceof AllayEntity
                || entity instanceof AmbientEntity) {
            return isSettingEnabled("Животные");
        }

        return false;
    }

    private boolean isSettingEnabled(String name) {
        BooleanSetting setting = this.targetSettings.a(name);
        return setting != null && setting.c().booleanValue();
    }

    private boolean hasArmor(LivingEntity entity) {
        return Stream.of(
                        EquipmentSlot.HEAD,
                        EquipmentSlot.CHEST,
                        EquipmentSlot.LEGS,
                        EquipmentSlot.FEET
                )
                .anyMatch(slot -> entity.getEquippedStack(slot).getItem() instanceof ArmorItem);
    }

    private void rotateToTarget() {
        if (!canRun() || this.target == null) {
            return;
        }

        Vec3d eye = mc.player.getEyePos();
        double r = reach();

        boolean throughWalls = targetThroughWalls();

        Vec3d delta = AuraUtil.a(eye, this.target, r, throughWalls);

        float yawToTarget = delta == Vec3d.ZERO
                ? Look.b()
                : (float) MathHelper.wrapDegrees(
                Math.toDegrees(Math.atan2(delta.z, delta.x)) - 90.0d
        );

        float pitchToTarget = delta == Vec3d.ZERO
                ? Look.c()
                : (float) (-Math.toDegrees(
                Math.atan2(delta.y, Math.hypot(delta.x, delta.z))
        ));

        System.arraycopy(this.pitchHistory, 0, this.pitchHistory, 1, 29);
        this.pitchHistory[0] = pitchToTarget;

        int skipDelay = Math.max(1, currentAttackDelay() - 2);

        boolean skip =
                (bl(this.dontHitWhen, "Используется предмет")
                        && mc.player.isUsingItem()
                        && mc.player.getItemUseTimeLeft() > 0
                        && this.attackCooldown >= skipDelay)
                        ||
                        (bl(this.dontHitWhen, "Открыт контейнер")
                                && mc.currentScreen != null
                                && !(mc.currentScreen instanceof GUIScreen)
                                && !(mc.currentScreen instanceof AssistantScreen));

        if ((this.timers[3] <= 0.0f && canAttack())
                || AuraUtil.a(this.attackCooldown, this.target, skip)) {
            this.timers[3] = 1.0f;

            if (!mc.player.isTouchingWater()
                    && bl(this.smartSprint)
                    && !mc.player.isOnGround()) {
                this.timers[0] = 1.0f;
            }
        }

        if (this.reactionDelay > 0) {
            this.reactionDelay--;

            float weakLerp = fl(this.aimSmoothing) * 0.15f;
            float weakYaw = AuraUtil.a(mc.player.getYaw(), yawToTarget, weakLerp);
            float weakPitch = AuraUtil.a(mc.player.getPitch(), pitchToTarget, weakLerp);

            Delta.getInstance()
                    .getModuleProcessor()
                    .k()
                    .startAiming(
                            new Rotation(wrapYaw(weakYaw), clampPitch(weakPitch)),
                            fl(this.rotationSpeed) * 0.45f,
                            1,
                            1
                    );

            this.timers[3] -= 1.0f;
            this.timers[5] -= 1.0f;
            this.timers[8] -= 1.0f;
            this.timers[1] = yawToTarget;
            return;
        }

        if (this.lostTargetTicks > 0) {
            this.lostTargetTicks--;

            this.timers[3] -= 1.0f;
            this.timers[5] -= 1.0f;
            this.timers[8] -= 1.0f;
            this.timers[1] = yawToTarget;
            return;
        }

        if (this.attackCooldown < 3
                && this.attackCooldown > 0
                && Math.random() < 0.008d) {
            this.lostTargetTicks = (int) MathUtil.a(1.0f, 2.0f);
            return;
        }

        Rotation rotation;
        float speed = fl(this.rotationSpeed);
        int priority = 1;
        int mode = 1;

        boolean glidingAim = Delta.getInstance()
                .getModuleProcessor()
                .t()
                .F()
                .m()
                && canAttack()
                && AuraUtil.a(this.target, r)
                && mc.player.isGliding();

        boolean maceAim = maceActive()
                && mc.player.fallDistance > fl(this.fallThreshold)
                && !mc.player.isGliding();

        if (glidingAim) {
            rotation = new Rotation(yawToTarget, pitchToTarget);
            priority = 0;
            mode = 3;
        } else if (maceAim) {
            rotation = createMaceRotation(yawToTarget, pitchToTarget);
            mode = 2;
        } else {
            switch (this.rotationType.c()) {
                case "ФанТайм":
                case "ФанТайм ФОВ":
                    rotation = createFantimeRotation(yawToTarget, pitchToTarget);
                    break;

                case "Легит":
                default:
                    rotation = createLegitRotation(yawToTarget, pitchToTarget);
                    break;
            }
        }

        Delta.getInstance()
                .getModuleProcessor()
                .k()
                .startAiming(rotation, speed, priority, mode);

        this.timers[3] -= 1.0f;
        this.timers[5] -= 1.0f;
        this.timers[8] -= 1.0f;
        this.timers[1] = yawToTarget;
    }

    private Rotation createFantimeRotation(float yawToTarget, float pitchToTarget) {
        float yawAmp = fl(this.yawNoise);
        float pitchAmp = fl(this.pitchNoise);
        float lerp = fl(this.aimSmoothing);

        float base = randomWalkNoise();

        float smoothW = base * yawAmp * 1.2f;
        float smoothH = base * pitchAmp * 0.85f;

        int idx = MathHelper.clamp(currentAttackDelay() - this.attackCooldown, 0, 29);

        float pitchGoal = this.pitchHistory[idx] + smoothH;
        float yawGoal = yawToTarget + smoothW;

        float finalYaw = AuraUtil.a(mc.player.getYaw(), yawGoal, lerp);
        float finalPitch = AuraUtil.a(mc.player.getPitch(), pitchGoal, lerp);

        double r = reach();

        if (this.timers[3] >= 0.0f) {
            if (!AuraUtil.a(mc.player.getYaw(), mc.player.getPitch(), r, this.target, true)
                    && this.timers[8] <= 0.0f) {
                finalYaw = yawToTarget;
            }

            if (!AuraUtil.a(yawToTarget, finalPitch, r, this.target, true)
                    && this.timers[8] <= 0.0f) {
                finalPitch = pitchToTarget;
            }

            if (!AuraUtil.a(
                    mc.player.getYaw() + smoothW,
                    mc.player.getPitch() + smoothH,
                    r,
                    this.target,
                    true)
                    && AuraUtil.a(
                    mc.player.getYaw(),
                    mc.player.getPitch(),
                    r,
                    this.target,
                    true)) {

                smoothW = MathHelper.clamp(smoothW, -0.08f, 0.08f);
                smoothH = MathHelper.clamp(smoothH, -0.08f, 0.08f);
            }
        }

        float attackProximity = MathHelper.clamp(
                (float) this.attackCooldown / (float) Math.max(1, currentAttackDelay()),
                0.0f,
                1.0f
        );
        float jitterScale = 1.0f - attackProximity * 0.60f;
        smoothW *= jitterScale;
        smoothH *= jitterScale;

        int halfDelay = Math.max(1, currentAttackDelay() / 2);

        if (this.attackCooldown <= halfDelay && this.timers[2] % 2.0f == 0.0f) {
            finalYaw = mc.player.getYaw();
        }

        float outYaw = finalYaw + smoothW * 0.18f;
        float outPitch = (this.rotationType.c().equals("ФанТайм") ? finalPitch : Look.c())
                + smoothH * 0.18f;

        return new Rotation(
                wrapYaw(outYaw),
                clampPitch(outPitch)
        );
    }

    private Rotation createLegitRotation(float yawToTarget, float pitchToTarget) {
        float yawAmp = fl(this.yawNoise);
        float pitchAmp = fl(this.pitchNoise);
        float lerp = fl(this.aimSmoothing);

        float base = randomWalkNoise();

        float jitter = base * yawAmp;
        float pitchJitter = base * pitchAmp * 0.5f;

        float finalYaw = AuraUtil.a(mc.player.getYaw(), yawToTarget, lerp);
        float finalPitch = AuraUtil.a(mc.player.getPitch(), pitchToTarget, lerp);

        double r = reach();

        float strongLerp = MathHelper.clamp(lerp + 0.10f, 0.01f, 1.0f);
        float snapLerp = MathHelper.clamp(lerp * 0.35f, 0.01f, 1.0f);

        if (this.timers[3] >= 0.0f) {
            finalPitch = AuraUtil.a(mc.player.getPitch(), pitchToTarget, strongLerp);

            jitter *= 0.35f;
            pitchJitter *= 0.35f;

            if (!AuraUtil.a(mc.player.getYaw(), mc.player.getPitch(), r, this.target, true)) {
                finalYaw = AuraUtil.a(mc.player.getYaw(), yawToTarget, MathUtil.a(0.65f, 0.90f));
            }
        }

        float attackProximity = MathHelper.clamp(
                (float) this.attackCooldown / (float) Math.max(1, currentAttackDelay()),
                0.0f,
                1.0f
        );
        float jitterScale = 1.0f - attackProximity * 0.75f;
        jitter *= jitterScale;
        pitchJitter *= jitterScale;

        if (!AuraUtil.a(finalYaw + jitter, finalPitch + pitchJitter, r, this.target, true)
                && AuraUtil.a(yawToTarget, pitchToTarget, r, this.target, true)) {
            jitter = MathHelper.clamp(jitter, -0.12f, 0.12f);
            pitchJitter = MathHelper.clamp(pitchJitter, -0.12f, 0.12f);
        }

        if (this.timers[5] >= 0.0f) {
            jitter *= 1.8f;
            pitchJitter *= 1.8f;

            if (this.attackCooldown >= 1 && this.timers[2] % 5.0f == 0.0f) {
                finalPitch = AuraUtil.a(mc.player.getPitch(), pitchToTarget, snapLerp);
            }
        }

        return new Rotation(
                wrapYaw(finalYaw + jitter),
                clampPitch(finalPitch + pitchJitter)
        );
    }

    private Rotation createMaceRotation(float yawToTarget, float pitchToTarget) {
        float yawAmp = fl(this.yawNoise);
        float pitchAmp = fl(this.pitchNoise);
        float lerp = fl(this.maceSmoothing);

        float smooth = randomWalkNoise();

        float finalYaw = AuraUtil.a(mc.player.getYaw(), yawToTarget, lerp);
        float finalPitch = AuraUtil.a(mc.player.getPitch(), pitchToTarget, lerp);

        return new Rotation(
                wrapYaw(finalYaw + smooth * yawAmp),
                clampPitch(finalPitch + smooth * pitchAmp * 0.5f)
        );
    }
}
