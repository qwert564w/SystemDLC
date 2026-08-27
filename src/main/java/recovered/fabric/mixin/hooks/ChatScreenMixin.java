package recovered.fabric.mixin.hooks;

import client.util.InputHooks;
import client.util.TextFieldHooks;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {
    @Inject(method = "method_25394", at = @At("HEAD"))
    private void systemdlcRenderHead(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        TextFieldHooks.onChatScreenDrawContextIntIntFloat2((ChatScreen)(Object)this, context, mouseX, mouseY, delta);
    }

    @Inject(method = "method_25394", at = @At("TAIL"))
    private void systemdlcRenderTail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        TextFieldHooks.onChatScreenDrawContextIntIntFloat((ChatScreen)(Object)this, context, mouseX, mouseY, delta);
    }

    @Inject(method = "method_25402", at = @At("HEAD"), cancellable = true)
    private void systemdlcMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (!InputHooks.isChatScreenDoubleDoubleInt((ChatScreen)(Object)this, mouseX, mouseY, button)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "method_25401", at = @At("HEAD"), cancellable = true)
    private void systemdlcEditorScroll(double mx, double my, double hx, double vy, org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Boolean> cir) {
        boolean r = client.gui.widget.UiContext.getInstance().onMouseScrolled((float)mx, vy, (float)my);
        System.err.println("[SDLC-SCROLL] editor forward -> "+r);
        if (r) {
            cir.setReturnValue(true);
        }
    }

    private void systemdlcMouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY, CallbackInfoReturnable<Boolean> cir) {
        if (!InputHooks.isChatScreenDoubleDoubleDoubleDouble((ChatScreen)(Object)this, mouseX, mouseY, dragX, dragY)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "method_25404", at = @At("HEAD"), cancellable = true)
    private void systemdlcKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (!InputHooks.isChatScreenIntIntInt((ChatScreen)(Object)this, keyCode, scanCode, modifiers)) {
            cir.setReturnValue(false);
        }
    }
}
