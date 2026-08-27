package client.gui.widget;

import client.api.Theme;
import client.api.UiMetrics;
import client.concurrent.ConfigManager;
import client.concurrent.SystemClient;
import client.data.HudConfig;
import client.gui.hud.CooldownsHud;
import client.gui.hud.CoordsHudElement;
import client.gui.hud.HotbarHud;
import client.gui.hud.InventoryHud;
import client.gui.hud.KeybindsHud;
import client.gui.hud.NotificationsHud;
import client.gui.hud.PotionsHud;
import client.gui.hud.StaffHud;
import client.gui.hud.StatsHud;
import client.gui.hud.SwapBindsHud;
import client.gui.hud.TargetHudElement;
import client.gui.hud.UseTrackerHud;
import client.gui.hud.WaypointsHud;
import client.gui.screen.ClickGuiScreen;
import client.module.Feature;
import client.module.client.ThemeModule;
import client.render.TextShader;
import client.util.HashUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix4f;

public final class UiContext implements UiMetrics {
   private static final float value235 = 1920.0F;
   private static final float value236 = 1080.0F;
   private static final UiContext INSTANCE = new UiContext();
   private static long time;
   private final List<RenderElement> list = new ArrayList<>();
   private final List<RenderElement> list2 = Collections.unmodifiableList(this.list);
   private final TooltipLayer tooltipLayer = new TooltipLayer(this);
   private final FocusManager focusManager = new FocusManager(this, this.tooltipLayer);
   private final KeyHintBar keyHintBar = new KeyHintBar(this, this.tooltipLayer);
   private final UiInputHandler uiInputHandler = new UiInputHandler(this, this.keyHintBar);
   private boolean flag;
   private float value237 = 1.0F;
   private float value238 = 1080.0F;

   private UiContext() {
      for (RenderElement renderelement : List.of(
         new KeybindsHud(),
         new PotionsHud(),
         new StaffHud(),
         new CooldownsHud(),
         new UseTrackerHud(),
         new HotbarHud(),
         new SwapBindsHud(),
         new TargetHudElement(),
         new WaypointsHud(),
         new NotificationsHud(),
         new CoordsHudElement(),
         new InventoryHud(),
         new StatsHud()
      )) {
         this.addRenderElement2(renderelement);
      }

      HudConfig.getHudConfig().addList(this.list);
   }

   public static void update() {
      time++;
   }

   public void update2() {
      ConfigManager configmanager = getConfigManager();
      if (configmanager != null) {
         HudConfig.getHudConfig().addList(this.list);
         Map map = configmanager.getMapByString("__global__");
         if (map != null) {
            HudConfig.getHudConfig().onMap(map);
         }

         for (RenderElement renderelement : this.list) {
            Map map1 = configmanager.getMapByString(renderelement.getString3());
            if (map1 != null) {
               renderelement.onMap(map1);
            }
         }
      }
   }

   public boolean isIntIntInt(int count, int count2, int count3) {
      return this.uiInputHandler.isIntIntInt(count2, count, count3);
   }

   public float getValue238() {
      return this.value238;
   }

   public void addRenderElement(RenderElement renderElement) {
      if (this.list.remove(renderElement)) {
         this.list.add(renderElement);
      }
   }

   private float getFloat() {
      if (Feature.mc != null && Feature.mc.getWindow() != null) {
         int i = Feature.mc.getWindow().getWidth();
         return i <= 0 ? 0.0F : (float)(Feature.mc.mouse.getX() * 1920.0 / i);
      } else {
         return 0.0F;
      }
   }

   private void update3() {
      ConfigManager configmanager = getConfigManager();
      if (!this.flag && configmanager != null) {
         SystemClient systemclient = SystemClient.getInstance();
         HashUtil hashutil = systemclient != null ? systemclient.getHashUtil() : null;
         boolean flagx = hashutil != null && hashutil.isFlag3();
         if (hashutil != null) {
            hashutil.setFlag3(true);
         }

         try {
            this.update5();
            this.update2();
         } finally {
            if (hashutil != null) {
               hashutil.setFlag3(flagx);
            }
         }

         this.flag = true;
      }
   }

   private void update4() {
      int i = Feature.mc.getWindow().getWidth();
      int j = Feature.mc.getWindow().getHeight();
      double d0 = Feature.mc.getWindow().getScaleFactor();
      if (d0 <= 0.0) {
         d0 = 1.0;
      }

      this.value237 = (float)(i / 1920.0F / d0);
      this.value238 = i > 0 ? j * 1920.0F / i : 1080.0F;
   }

   private float getFloat2() {
      if (Feature.mc != null && Feature.mc.getWindow() != null) {
         int i = Feature.mc.getWindow().getWidth();
         return i <= 0 ? 0.0F : (float)(Feature.mc.mouse.getY() * 1920.0 / i);
      } else {
         return 0.0F;
      }
   }

   private static ConfigManager getConfigManager() {
      SystemClient systemclient = SystemClient.getInstance();
      return systemclient != null ? systemclient.getConfigManager() : null;
   }

   public boolean isIntIntInt2(int count, int count2, int count3) {
      return this.uiInputHandler.isIntIntInt2(count, count2, count3);
   }

   public float getFloat3() {
      return 1920.0F;
   }

   public static long getTime() {
      return time;
   }

   public static UiContext getInstance() {
      return INSTANCE;
   }

   public boolean onMouseScrolled(float value, double scroll, float value3) {
      return this.uiInputHandler.isFloatDoubleFloat(value, scroll, value3);
   }

   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      UiInputHandler uiinputhandler = this.uiInputHandler;
      float f1 = this.getFloat2();
      float f = this.getFloat();
      return uiinputhandler.isFloatDoubleFloat(f, value2, f1);
   }

   public boolean isCharInt(char symbol, int count) {
      return this.uiInputHandler.isCharInt(symbol, count);
   }

   public void render(DrawContext drawContext, float value) {
      time++;
      this.update3();
      if (Feature.mc != null && Feature.mc.getWindow() != null) {
         this.update4();
         this.focusManager.addRenderElement(this.keyHintBar.getRenderElement());
         float f = this.getFloat();
         float f1 = this.getFloat2();
         this.uiInputHandler.onFloatFloat6(f1, f);
         drawContext.draw();
         this.render(drawContext, value, f1, f);
      }
   }

   public void update5() {
      this.tooltipLayer.onList(this.list);
   }

   public boolean check() {
      return this.uiInputHandler.isFlag();
   }

   public List<RenderElement> getList2() {
      return this.list2;
   }

   public RenderElement getRenderElementByString(String text) {
      if (text == null) {
         return null;
      } else {
         for (RenderElement renderelement : this.list) {
            if (text.equals(renderelement.getString3())) {
               return renderelement;
            }
         }

         return null;
      }
   }

   private void addRenderElement2(RenderElement renderElement) {
      this.list.add(renderElement);
      this.tooltipLayer.onRenderElement(renderElement);
   }

   private void onMatrix4fFloat(Matrix4f matrix4f, float value) {
      RenderElement renderelement = this.keyHintBar.getRenderElement();
      if (renderelement != null && !KeyHintBar.check()) {
         List list1 = this.list;
         float f2 = this.getFloat3();
         float f3 = this.getValue238();
         int i = Theme.primary();
         float f1 = f3;
         float f = f2;
         List listx = list1;
         SliderPainter.onFloatListMatrix4fIntFloatFloatRenderElement(value, listx, matrix4f, i, f, f1, renderelement);
      }
   }

   private void render(DrawContext drawContext, float value, float value2, float value3) {
      drawContext.getMatrices().push();
      drawContext.getMatrices().scale(this.value237, this.value237, 1.0F);
      Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();
      float f = ClickGuiScreen.getValue235();
      ClickGuiScreen.setValue235(this.value237);
      ThemeModule.update11();

      try {
         boolean flagx = this.uiInputHandler.isFlag();
         if (flagx) {
            this.onMatrix4fFloat(matrix4f, value);
         }

         for (RenderElement renderelement : this.list) {
            TextShader.update3();

            try {
               renderelement.onFloatMatrix4fDrawContext(value, matrix4f, drawContext);
            } finally {
               TextShader.update();
            }
         }

         if (flagx || this.uiInputHandler.check2()) {
            this.uiInputHandler.onFloatFloatDrawContextMatrix4fFloat2(value, value3, drawContext, matrix4f, value2);
         }
      } finally {
         ThemeModule.update12();
         ClickGuiScreen.setValue235(f);
         drawContext.getMatrices().pop();
      }
   }
}
