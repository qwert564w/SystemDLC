package client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import pulse.cosmetic.LocalCosmetics;

/**
 * Cosmetics picker on main menu — same glass button style.
 */
public class CosmeticsScreen extends Screen {
    private final Screen parent;
    private int scroll;
    private final List<Integer> indices = new ArrayList<>();
    private static final int ROW_H = 26;
    private static final int PANEL_W = 280;

    public CosmeticsScreen(Screen parent) {
        super(Text.literal("Косметика"));
        this.parent = parent;
        for (int i = 0; i < LocalCosmetics.size(); i++) {
            if (!"cape".equals(LocalCosmetics.type(i))) {
                indices.add(i);
            }
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xFF0A0A12);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        String title = "Косметика";
        context.drawTextWithShadow(this.textRenderer, title,
            this.width / 2 - this.textRenderer.getWidth(title) / 2, 24, 0xFFFFFFFF);

        String sub = "Выбрано: " + LocalCosmetics.selectedIndices().size() + "  ·  клик = вкл/выкл";
        context.drawTextWithShadow(this.textRenderer, sub,
            this.width / 2 - this.textRenderer.getWidth(sub) / 2, 40, 0x88FFFFFF);

        int panelX = this.width / 2 - PANEL_W / 2;
        int panelY = 60;
        int panelH = this.height - 120;
        context.fill(panelX, panelY, panelX + PANEL_W, panelY + panelH, 0x221A1A28);
        // border
        context.fill(panelX, panelY, panelX + PANEL_W, panelY + 1, 0x554DA3FF);
        context.fill(panelX, panelY + panelH - 1, panelX + PANEL_W, panelY + panelH, 0x554DA3FF);

        int maxVisible = Math.max(1, panelH / ROW_H);
        int maxScroll = Math.max(0, indices.size() - maxVisible);
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        for (int row = 0; row < maxVisible; row++) {
            int idxPos = row + scroll;
            if (idxPos >= indices.size()) break;
            int cosIdx = indices.get(idxPos);
            int ry = panelY + row * ROW_H;
            boolean sel = LocalCosmetics.isSelected(cosIdx);
            boolean hover = mouseX >= panelX && mouseX < panelX + PANEL_W && mouseY >= ry && mouseY < ry + ROW_H;

            int fill = sel ? 0x442A5A8C : (hover ? 0x332A2A3C : 0x181A1A28);
            context.fill(panelX + 2, ry + 1, panelX + PANEL_W - 2, ry + ROW_H - 1, fill);
            if (sel) {
                context.fill(panelX + 2, ry + 1, panelX + 5, ry + ROW_H - 1, 0xFF4DA3FF);
            }

            String name = LocalCosmetics.name(cosIdx);
            String type = LocalCosmetics.type(cosIdx);
            String line = name + "  §8" + type;
            context.drawTextWithShadow(this.textRenderer, line, panelX + 12, ry + 8,
                sel ? 0xFFFFFFFF : 0xFFCCCCCC);
        }

        // Back button
        int bx = this.width / 2 - 60;
        int by = this.height - 40;
        boolean backHover = mouseX >= bx && mouseX < bx + 120 && mouseY >= by && mouseY < by + 26;
        context.fill(bx, by, bx + 120, by + 26, backHover ? 0x443A3A50 : 0x281A1A28);
        context.fill(bx, by, bx + 120, by + 1, 0x66FFFFFF);
        context.fill(bx, by + 25, bx + 120, by + 26, 0x66FFFFFF);
        String back = "Назад";
        context.drawTextWithShadow(this.textRenderer, back,
            bx + 60 - this.textRenderer.getWidth(back) / 2, by + 9, 0xFFFFFFFF);

        // Clear
        int cx = this.width / 2 - 160;
        boolean clearHover = mouseX >= cx && mouseX < cx + 90 && mouseY >= by && mouseY < by + 26;
        context.fill(cx, by, cx + 90, by + 26, clearHover ? 0x443A1520 : 0x281A1A28);
        String clr = "Сбросить";
        context.drawTextWithShadow(this.textRenderer, clr,
            cx + 45 - this.textRenderer.getWidth(clr) / 2, by + 9, 0xFFFFAAAA);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);
        int mx = (int) mouseX, my = (int) mouseY;

        int bx = this.width / 2 - 60;
        int by = this.height - 40;
        if (mx >= bx && mx < bx + 120 && my >= by && my < by + 26) {
            this.client.setScreen(parent);
            return true;
        }
        int cx = this.width / 2 - 160;
        if (mx >= cx && mx < cx + 90 && my >= by && my < by + 26) {
            LocalCosmetics.clearSelection();
            return true;
        }

        int panelX = this.width / 2 - PANEL_W / 2;
        int panelY = 60;
        int panelH = this.height - 120;
        int maxVisible = Math.max(1, panelH / ROW_H);
        if (mx >= panelX && mx < panelX + PANEL_W && my >= panelY && my < panelY + panelH) {
            int row = (my - panelY) / ROW_H;
            int idxPos = row + scroll;
            if (idxPos >= 0 && idxPos < indices.size()) {
                LocalCosmetics.toggle(indices.get(idxPos));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontal, double vertical) {
        scroll -= (int) Math.signum(vertical);
        if (scroll < 0) scroll = 0;
        return true;
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
