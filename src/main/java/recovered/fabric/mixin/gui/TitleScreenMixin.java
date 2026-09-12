package recovered.fabric.mixin.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin() {
        super(null);
    }

    // Инжектимся в рендер фона Главного Меню
    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    private void renderCustomBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // Путь к твоей сгенерированной текстуре с пиксельными золотыми монетами
        Identifier bgTexture = Identifier.of("systemdlc", "textures/gui/menu_background.png");
        int width = this.width;
        int height = this.height;
        
        // Растягиваем текстуру на весь экран (чтобы не было швов и стандартного dirt/panorama)
        context.drawTexture(bgTexture, 0, 0, 0, 0, width, height, width, height);
        
        // Отменяем стандартный фон Minecraft (Panorama / Dirt)
        ci.cancel();
    }
}
