package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.CrosshairTextures;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderStatusBars(DrawContext ctx, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            CrosshairTextures.onInGameHudDrawContext((InGameHud)(Object)this, ctx);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "renderChat", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderChat(DrawContext ctx, RenderTickCounter rtc, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            CrosshairTextures.onInGameHudDrawContextRenderTickCounter((InGameHud)(Object)this, ctx, rtc);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderCrosshair(DrawContext ctx, RenderTickCounter rtc, CallbackInfo ci) {
        CrosshairTextures.onInGameHudDrawContextRenderTickCounter2((InGameHud)(Object)this, ctx, rtc);
        ci.cancel();
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderStatusEffectOverlay(DrawContext ctx, RenderTickCounter rtc, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            CrosshairTextures.onInGameHudDrawContextRenderTickCounter3((InGameHud)(Object)this, ctx, rtc);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V", at = @At("TAIL"))
    private void systemdlcRender(DrawContext ctx, RenderTickCounter rtc, CallbackInfo ci) {
        CrosshairTextures.renderModuleHud(ctx, rtc);
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderHeldItemTooltip(DrawContext ctx, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            CrosshairTextures.onInGameHudDrawContext2((InGameHud)(Object)this, ctx);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(
        method = "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void systemdlcRenderScoreboardSidebar(DrawContext ctx, net.minecraft.scoreboard.ScoreboardObjective obj, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            CrosshairTextures.onInGameHudDrawContextScoreboardObjective((InGameHud)(Object)this, ctx, obj);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "updateVignetteDarkness", at = @At("HEAD"), cancellable = true)
    private void systemdlcUpdateVignetteDarkness(net.minecraft.entity.Entity entity, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            CrosshairTextures.onInGameHudEntity((InGameHud)(Object)this, entity);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "renderNauseaOverlay", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderNauseaOverlay(DrawContext ctx, float delta, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            CrosshairTextures.onInGameHudDrawContextFloat((InGameHud)(Object)this, ctx, delta);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderFood(DrawContext ctx, PlayerEntity player, int x, int y, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            CrosshairTextures.onInGameHudDrawContextPlayerEntityIntInt((InGameHud)(Object)this, ctx, player, x, y);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "renderAirBubbles", at = @At("HEAD"), cancellable = true)
    private void systemdlcRenderAirBubbles(DrawContext ctx, PlayerEntity player, int x, int y, int ticks, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            CrosshairTextures.onInGameHudDrawContextPlayerEntityIntIntInt((InGameHud)(Object)this, ctx, player, x, y, ticks);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "getCameraPlayer", at = @At("HEAD"), cancellable = true)
    private void systemdlcGetCameraPlayer(CallbackInfoReturnable<PlayerEntity> cir) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            PlayerEntity result = CrosshairTextures.getPlayerEntityByInGameHud((InGameHud)(Object)this);
            if (result != null) {
                cir.setReturnValue(result);
            }
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }
}
