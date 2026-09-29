package pulse.render;
import com.mojang.blaze3d.systems.RenderSystem;
public final class RenderSystemHelper {
    private RenderSystemHelper() {}
    public static void disableCull() { try { RenderSystem.disableCull(); } catch (Throwable ignored) {} }
    public static void enableCull() { try { RenderSystem.enableCull(); } catch (Throwable ignored) {} }
}
