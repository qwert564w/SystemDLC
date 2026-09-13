package client.module.combat;

import client.module.Category;
import client.module.Module;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.PolarBypass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

public class AimAssist extends Module {
    private final SliderSetting fov;
    private final SliderSetting range;
    private final SliderSetting smooth;
    private final SliderSetting maxSpeed;
    private final SliderSetting jitter;
    private final SliderSetting spread;
    private final BooleanSetting onlyLmb;
    private final BooleanSetting throughWalls;

    private final Random rnd = new Random();
    private LivingEntity currentTarget;
    private long targetAcquireMs = 0;
    private long lastAimPointMs = 0;
    private Vec3d aimPoint = Vec3d.ZERO;
    private long lastFrame = 0;

    public AimAssist() {
        super("AimAssist", Category.COMBAT);
        this.fov = new SliderSetting("", "", 90, 10, 180, 1);
        this.fov.setName("FOV");
        this.range = new SliderSetting("", "", 4.2, 1, 6, 0.1);
        this.range.setName("Дистанция");
        this.smooth = new SliderSetting("", "", 0.35, 0.05, 1, 0.01);
        this.smooth.setName("Плавность");
        this.maxSpeed = new SliderSetting("", "", 14, 3, 40, 0.5);
        this.maxSpeed.setName("Макс скорость");
        this.jitter = new SliderSetting("", "", 0.5, 0, 2, 0.05);
        this.jitter.setName("Джиттер");
        this.spread = new SliderSetting("", "", 0.16, 0, 0.4, 0.01);
        this.spread.setName("Разброс точки");
        this.onlyLmb = new BooleanSetting("", "", true);
        this.onlyLmb.setName("Только с ЛКМ");
        this.throughWalls = new BooleanSetting("", "", false);
        this.throughWalls.setName("Сквозь стены");

        this.addSettings(new Setting[]{this.fov, this.range, this.smooth, this.maxSpeed, this.jitter, this.spread, this.onlyLmb, this.throughWalls});
    }

    @Override
    public void render5(WorldRenderContext worldRenderContext) {
        if (this.notInGame()) return;
        
        long now = System.currentTimeMillis();
        long dt = Math.max(8, Math.min(50, now - this.lastFrame));
        this.lastFrame = now;

        if (this.onlyLmb.isFlag3() && GLFW.glfwGetMouseButton(this.client().getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) != 1) {
            this.currentTarget = null;
            return;
        }

        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        double maxRange = this.range.getValue();
        double maxRangeSq = maxRange * maxRange;
        float maxFov = (float) this.fov.getValue() / 2.0F;

        for (Entity e : this.world().getPlayers()) {
            if (e == this.player() || !(e instanceof LivingEntity) || !e.isAlive()) continue;
            
            double dSq = e.squaredDistanceTo(this.player());
            if (dSq > maxRangeSq) continue;

            if (!this.throughWalls.isFlag3() && !PolarBypass.hasLos(this.player(), e)) continue;

            float angle = PolarBypass.angleTo(this.player(), e.getBoundingBox().getCenter());
            if (angle > maxFov) continue;

            if (dSq < bestDist) {
                bestDist = dSq;
                best = (LivingEntity) e;
            }
        }

        if (best != this.currentTarget) {
            this.currentTarget = best;
            this.targetAcquireMs = now;
            this.lastAimPointMs = 0;
        }

        if (this.currentTarget == null || now - this.targetAcquireMs < 90) return;

        if (now - this.lastAimPointMs > 220 + this.rnd.nextInt(260)) {
            this.aimPoint = PolarBypass.randomAimPoint(this.currentTarget, this.spread.getValue(), this.rnd);
            this.lastAimPointMs = now;
        }

        Vec3d target = this.aimPoint;
        double dx = target.x - this.player().getX();
        double dy = target.y - this.player().getEyeY();
        double dz = target.z - this.player().getZ();
        double len = Math.sqrt(dx * dx + dy * dy + dz * dz);

        float tgtYaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        float tgtPitch = (float) -Math.toDegrees(Math.asin(dy / len));

        float smoothVal = MathHelper.clamp((float)(this.smooth.getValue() * dt) / 50.0F, 0.02F, 1.0F);
        float maxDeg = (float)(this.maxSpeed.getValue() * dt) / 50.0F;

        float[] rot = PolarBypass.stepRotation(
            this.player().getYaw(), this.player().getPitch(),
            tgtYaw, tgtPitch,
            maxDeg, smoothVal, (float) this.jitter.getValue(), this.rnd
        );

        this.player().setYaw(rot[0]);
        this.player().setPitch(rot[1]);
    }
}
