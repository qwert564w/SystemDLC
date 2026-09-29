package client.gui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.text.Text;

/**
 * Custom main menu in glass-button style (inspired by FugaClient).
 * Cosmetics button opens CosmeticsScreen.
 */
public class IntelMainMenuScreen extends Screen {
    private static final int BTN_H = 28;
    private static final int BTN_GAP = 8;
    private static final int RADIUS_VISUAL = 8;

    private float hoverSingle, hoverMulti, hoverCosmetics, hoverSettings, hoverQuit;
    private long openTime = System.currentTimeMillis();

    public IntelMainMenuScreen() {
        super(Text.literal("intel client"));
    }

    @Override
    protected void init() {
        super.init();
        openTime = System.currentTimeMillis();
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // solid dark backdrop
        context.fill(0, 0, this.width, this.height, 0xFF0A0A12);
        // subtle vignette bars
        context.fill(0, 0, this.width, 40, 0x66000000);
        context.fill(0, this.height - 40, this.width, this.height, 0x66000000);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        float t = Math.min(1.0F, (System.currentTimeMillis() - openTime) / 400.0F);
        int alpha = (int) (t * 255) << 24;

        // Title
        String title = "intel client";
        int tw = this.textRenderer.getWidth(title);
        int titleX = this.width / 2 - tw / 2;
        int titleY = this.height / 2 - 90;
        context.drawTextWithShadow(this.textRenderer, title, titleX, titleY, 0xFFFFFFFF);
        // blue accent dot
        context.fill(titleX + 1, titleY - 5, titleX + 4, titleY - 2, 0xFF4DA3FF);

        // Layout: two primary buttons, then row of secondary
        int panelW = 220;
        int panelX = this.width / 2 - panelW / 2;
        int y = this.height / 2 - 30;
        int halfW = (panelW - BTN_GAP) / 2;

        // Singleplayer
        hoverSingle = approach(hoverSingle, hit(mouseX, mouseY, panelX, y, halfW, BTN_H) ? 1f : 0f, delta);
        drawGlassButton(context, panelX, y, halfW, BTN_H, "Одиночная", hoverSingle);

        // Multiplayer
        hoverMulti = approach(hoverMulti, hit(mouseX, mouseY, panelX + halfW + BTN_GAP, y, halfW, BTN_H) ? 1f : 0f, delta);
        drawGlassButton(context, panelX + halfW + BTN_GAP, y, halfW, BTN_H, "Мультиплеер", hoverMulti);

        y += BTN_H + BTN_GAP;

        // Cosmetics (full width primary accent)
        hoverCosmetics = approach(hoverCosmetics, hit(mouseX, mouseY, panelX, y, panelW, BTN_H) ? 1f : 0f, delta);
        drawGlassButton(context, panelX, y, panelW, BTN_H, "Косметика", hoverCosmetics, true);

        y += BTN_H + BTN_GAP;

        // Settings + Quit
        hoverSettings = approach(hoverSettings, hit(mouseX, mouseY, panelX, y, halfW, BTN_H) ? 1f : 0f, delta);
        drawGlassButton(context, panelX, y, halfW, BTN_H, "Настройки", hoverSettings);

        hoverQuit = approach(hoverQuit, hit(mouseX, mouseY, panelX + halfW + BTN_GAP, y, halfW, BTN_H) ? 1f : 0f, delta);
        drawGlassButton(context, panelX + halfW + BTN_GAP, y, halfW, BTN_H, "Выход", hoverQuit, false, true);

        // footer
        String ver = "1.21.11 · SystemDLC";
        context.drawTextWithShadow(this.textRenderer, ver,
            this.width / 2 - this.textRenderer.getWidth(ver) / 2, this.height - 20, 0x88FFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawGlassButton(DrawContext ctx, int x, int y, int w, int h, String label, float hover) {
        drawGlassButton(ctx, x, y, w, h, label, hover, false, false);
    }

    private void drawGlassButton(DrawContext ctx, int x, int y, int w, int h, String label, float hover, boolean accent) {
        drawGlassButton(ctx, x, y, w, h, label, hover, accent, false);
    }

    private void drawGlassButton(DrawContext ctx, int x, int y, int w, int h, String label, float hover, boolean accent, boolean danger) {
        // glass fill
        int baseA = 0x28 + (int) (hover * 0x28);
        int fill;
        if (danger) fill = (baseA << 24) | 0x3A1520;
        else if (accent) fill = (baseA << 24) | 0x1A3A5C;
        else fill = (baseA << 24) | 0x1A1A28;

        ctx.fill(x, y, x + w, y + h, fill);

        // border
        int border = accent
            ? (0xAA000000 | 0x4DA3FF)
            : danger
                ? (0x88000000 | 0xE05555)
                : (0x66000000 | 0xFFFFFF);
        int bA = (int) ((border >>> 24) * (0.5f + 0.5f * hover));
        int borderCol = (bA << 24) | (border & 0x00FFFFFF);
        // top/bottom/left/right 1px
        ctx.fill(x, y, x + w, y + 1, borderCol);
        ctx.fill(x, y + h - 1, x + w, y + h, borderCol);
        ctx.fill(x, y, x + 1, y + h, borderCol);
        ctx.fill(x + w - 1, y, x + w, y + h, borderCol);

        // hover glow strip
        if (hover > 0.01f) {
            int g = (int) (hover * 0x40) << 24 | (accent ? 0x4DA3FF : 0xFFFFFF);
            ctx.fill(x + 2, y + 2, x + w - 2, y + 3, g);
        }

        int textColor = 0xFFFFFFFF;
        if (danger) textColor = 0xFFFFAAAA;
        int lw = this.textRenderer.getWidth(label);
        ctx.drawTextWithShadow(this.textRenderer, label, x + (w - lw) / 2, y + (h - 8) / 2, textColor);
    }

    private static boolean hit(int mx, int my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w && my >= y && my < y + h;
    }

    private static float approach(float current, float target, float delta) {
        float speed = 0.25f;
        if (current < target) return Math.min(target, current + speed);
        if (current > target) return Math.max(target, current - speed);
        return current;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);

        int panelW = 220;
        int panelX = this.width / 2 - panelW / 2;
        int y = this.height / 2 - 30;
        int halfW = (panelW - BTN_GAP) / 2;
        int mx = (int) mouseX, my = (int) mouseY;

        if (hit(mx, my, panelX, y, halfW, BTN_H)) {
            this.client.setScreen(new SelectWorldScreen(this));
            return true;
        }
        if (hit(mx, my, panelX + halfW + BTN_GAP, y, halfW, BTN_H)) {
            this.client.setScreen(new MultiplayerScreen(this));
            return true;
        }
        y += BTN_H + BTN_GAP;
        if (hit(mx, my, panelX, y, panelW, BTN_H)) {
            this.client.setScreen(new CosmeticsScreen(this));
            return true;
        }
        y += BTN_H + BTN_GAP;
        if (hit(mx, my, panelX, y, halfW, BTN_H)) {
            this.client.setScreen(new OptionsScreen(this, this.client.options));
            return true;
        }
        if (hit(mx, my, panelX + halfW + BTN_GAP, y, halfW, BTN_H)) {
            this.client.scheduleStop();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
