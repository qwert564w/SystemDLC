package recovered.fabric.mixin.hooks;

import client.render.TabListHooks;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
    @Inject(method = "method_1921", at = @At("HEAD"), cancellable = true)
    private void systemdlcSetVisible(boolean visible, CallbackInfo ci) {
        TabListHooks.onPlayerListHudBoolean((PlayerListHud)(Object)this, visible);
        ci.cancel();
    }

    @Inject(method = "method_1919", at = @At("HEAD"), cancellable = true)
    private void systemdlcRender(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective, CallbackInfo ci) {
        TabListHooks.onPlayerListHudDrawContextIntScoreboardScoreboardObjective(
            (PlayerListHud)(Object)this, context, scaledWindowWidth, scoreboard, objective
        );
        ci.cancel();
    }
}
