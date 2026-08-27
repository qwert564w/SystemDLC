package recovered.fabric.mixin.hooks;

import client.gui.widget.TextFieldAccess;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin {
    @Inject(method = "method_48579", at = @At("HEAD"), cancellable = true)
    private void systemdlcRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!TextFieldAccess.isTextFieldWidgetDrawContextIntIntFloat((TextFieldWidget)(Object)this, context, mouseX, mouseY, delta)) {
            ci.cancel();
        }
    }
}
