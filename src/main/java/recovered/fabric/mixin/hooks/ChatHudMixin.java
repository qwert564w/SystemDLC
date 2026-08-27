package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.util.ChatSpamGuard;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {
    @Inject(method = "method_58744", at = @At("HEAD"), cancellable = true)
    private void systemdlcAddMessage(ChatHudLine line, CallbackInfo ci) {
        ChatSpamGuard.onChatHudChatHudLine((ChatHud)(Object)this, line);
        ci.cancel();
    }

    @Inject(method = "method_1815", at = @At("HEAD"), cancellable = true)
    private void systemdlcAddVisibleMessage(ChatHudLine line, CallbackInfo ci) {
        ChatSpamGuard.onChatHudChatHudLine2((ChatHud)(Object)this, line);
        ci.cancel();
    }

    @Inject(method = "method_45027", at = @At("HEAD"), cancellable = true)
    private void systemdlcLogChatMessage(ChatHudLine line, CallbackInfo ci) {
        if (!ChatSpamGuard.isChatHudChatHudLine((ChatHud)(Object)this, line)) {
            ci.cancel();
        }
    }

    @Inject(method = "method_1808", at = @At("HEAD"), cancellable = true)
    private void systemdlcClear(boolean clearHistory, CallbackInfo ci) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            ChatSpamGuard.onChatHudBoolean((ChatHud)(Object)this, clearHistory);
            ci.cancel();
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_1805", at = @At("HEAD"), cancellable = true)
    private void systemdlcRender(DrawContext context, int width, int height, int tickDelta, boolean unfocused, CallbackInfo ci) {
        if (!ChatSpamGuard.isChatHudDrawContextIntIntIntBoolean((ChatHud)(Object)this, context, width, height, tickDelta, unfocused)) {
            ci.cancel();
        }
    }

    @Inject(method = "method_1803", at = @At("HEAD"), cancellable = true)
    private void systemdlcAddToHistory(String message, CallbackInfo ci) {
        ChatSpamGuard.onChatHudString((ChatHud)(Object)this, message);
        ci.cancel();
    }
}
