package client.gui.widget;

import client.module.Feature;
import client.render.TextShader;
import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;

public final class AccountsPage extends Widget {
    private final List<String> profiles = new ArrayList<>();
    private int selected = -1;

    public AccountsPage() {
        String current = getCurrentName();
        if (current != null && !current.isBlank()) {
            profiles.add(current);
            selected = 0;
        }
    }

    private String getCurrentName() {
        try {
            if (Feature.mc != null && Feature.mc.getSession() != null) {
                return Feature.mc.getSession().getUsername();
            }
        } catch (Throwable ignored) {
        }
        return "Unknown";
    }

    private void saveCurrent() {
        String current = getCurrentName();
        if (current == null || current.isBlank()) {
            return;
        }
        if (!profiles.contains(current)) {
            profiles.add(current);
        }
        selected = profiles.indexOf(current);
    }

    @Override
    public boolean isIntDoubleDouble(int count, double x, double y) {
        if (count != 0) {
            return false;
        }
        if (x < this.value235 || x > this.value235 + this.value237 ||
            y < this.value236 || y > this.value236 + this.value238) {
            return false;
        }

        // Local profile manager: save the current authenticated Minecraft account.
        if (y >= this.value236 + 52.0F && y <= this.value236 + 84.0F) {
            saveCurrent();
            return true;
        }

        int row = (int)((y - this.value236 - 104.0F) / 32.0F);
        if (row >= 0 && row < profiles.size()) {
            selected = row;
            return true;
        }
        return true;
    }

    @Override
    public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
        float x = this.value235 + 24.0F;
        float y = this.value236 + 24.0F;
        int foreground = this.foreground();
        int muted = this.mutedFg();

        TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value, "Account Manager", 20.0F, x, foreground, y, matrix4f);
        TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value, "Текущий: " + getCurrentName(), 14.0F, x, y + 28.0F, foreground, y + 28.0F, matrix4f);
        TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value, "Сохранить текущий аккаунт", 14.0F, x, y + 60.0F, foreground, y + 60.0F, matrix4f);

        float rowY = y + 112.0F;
        for (int i = 0; i < profiles.size(); i++) {
            int color = i == selected ? foreground : muted;
            TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value, (i + 1) + ". " + profiles.get(i), 14.0F, x, rowY, color, rowY, matrix4f);
            rowY += 32.0F;
        }
    }
}
