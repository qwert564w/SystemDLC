package client.module.combat;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.PolarBypass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class TriggerBot extends Module {
    private static boolean active = false;
    private static LivingEntity candidate = null;
    private static long acquireMs = 0;
    private static long reactionMs = 0;
    private static boolean slip = false;
    private static final Random rnd = new Random();
    private static float currentAngle = 6.0F;

    private final SliderSetting reaction;
    private final SliderSetting reactionVar;
    private final SliderSetting angle;
    private final SliderSetting missChance;
    private final BooleanSetting playersOnly;
    private final BooleanSetting losCheck;

    public TriggerBot() {
        super("TriggerBot", Category.COMBAT);
        this.reaction = new SliderSetting("", "", 150, 70, 350, 5);
        this.reaction.setName("Реакция");
        this.reactionVar = new SliderSetting("", "", 45, 0, 120, 5);
        this.reactionVar.setName("Разброс реакции");
        this.angle = new SliderSetting("", "", 6, 1, 20, 0.5);
        this.angle.setName("Угол прицела");
        this.missChance = new SliderSetting("", "", 6, 0, 25, 1);
        this.missChance.setName("Шанс промаха");
        this.playersOnly = new BooleanSetting("", "", true);
        this.playersOnly.setName("Только игроки");
        this.losCheck = new BooleanSetting("", "", true);
        this.losCheck.setName("Проверка стен");

        this.addSettings(new Setting[]{this.reaction, this.reactionVar, this.angle, this.missChance, this.playersOnly, this.losCheck});
    }

    @Override
    public void onEnable() { active = true; candidate = null; }
    @Override
    public void onDisable() { active = false; candidate = null; }

    @Override
    public void render5(client.render.WorldRenderContext worldRenderContext) {
        if (this.notInGame() || !active) return;
        currentAngle = this.angle.getValueAsFloat();
        long now = System.currentTimeMillis();
        
        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        double maxAngle = currentAngle * 1.5;

        for (Entity e : this.world().getPlayers()) {
            if (!(e instanceof LivingEntity le) || !e.isAlive()) continue;
            if (this.playersOnly.isFlag3() && !(e instanceof PlayerEntity)) continue;
            
            double dSq = e.squaredDistanceTo(this.player());
            if (dSq > 16) continue;

            Vec3d center = e.getBoundingBox().getCenter();
            float ang = PolarBypass.angleTo(this.player(), center);
            if (ang > maxAngle) continue;

            if (this.losCheck.isFlag3() && !PolarBypass.hasLos(this.player(), e)) continue;

            if (dSq < bestDist) {
                bestDist = dSq;
                best = le;
            }
        }

        if (best != candidate) {
            candidate = best;
            acquireMs = now;
            reactionMs = PolarBypass.humanReactionMs((long)this.reaction.getValue(), (long)this.reactionVar.getValue(), 70, 340, rnd);
            slip = rnd.nextFloat() * 100 < this.missChance.getValue();
        }
    }

    public static LivingEntity getLivingEntity2() {
        if (!active || candidate == null || slip) return null;
        long now = System.currentTimeMillis();
        if (now - acquireMs < reactionMs) return null;
        if (candidate.squaredDistanceTo(MinecraftClient.getInstance().player) > 9.6) return null;
        return candidate;
    }

    public static boolean isLivingEntity2(LivingEntity entity) {
        if (!active || entity == null || !entity.isAlive()) return false;
        Vec3d center = entity.getBoundingBox().getCenter();
        float ang = PolarBypass.angleTo(MinecraftClient.getInstance().player, center);
        return ang <= currentAngle;
    }
}
