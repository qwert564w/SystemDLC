package client.gui.widget;

import client.api.UiMetrics;
import client.concurrent.ConfigManager;
import client.concurrent.SystemClient;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public final class TooltipLayer implements UiMetrics {
   private static final float value235 = 0.01F;
   private final UiContext uiContext;
   private final Map<RenderElement, float[]> map = new IdentityHashMap<>();

   public TooltipLayer(UiContext uiContext2) {
      this.uiContext = uiContext2;
   }

   public void onFloatFloatRenderElement(float value, float value2, RenderElement renderElement) {
      float[] afloat = this.map.get(renderElement);
      if (afloat == null) {
         this.map.put(renderElement, new float[]{value2, value});
      } else {
         afloat[0] = value2;
         afloat[1] = value;
      }
   }

   public void onRenderElement(RenderElement renderElement) {
      ConfigManager configmanager = getConfigManager();
      if (configmanager != null) {
         float[] afloat = configmanager.getFloatArrayByString(renderElement.getString3());
         if (afloat != null && afloat.length >= 2) {
            float f = afloat[0];
            float f1 = afloat[1];
            this.onFloatFloatRenderElement(f1, f, renderElement);
            renderElement.onFloatFloat(this.getFloatByFloatRenderElement(f, renderElement), this.getFloatByRenderElementFloat(renderElement, f1));
            return;
         }
      }

      this.onRenderElement2(renderElement);
   }

   private void onRenderElement2(RenderElement renderElement) {
      float[] afloat = renderElement.getFloatArray();
      if (afloat != null && afloat.length >= 2) {
         float f8 = Math.max(0.0F, afloat[0]);
         float f9 = Math.max(0.0F, afloat[1]);
         renderElement.onFloatFloat(f8, f9);
         this.onFloatFloatRenderElement(f9, f8, renderElement);
      } else {
         List<RenderElement> list = this.uiContext.getList2();
         int i = list.size();
         int j = Math.max(0, list.indexOf(renderElement));
         float f = this.getFloat();
         float f1 = 200.0F;
         float f2 = i * f1 + (i - 1) * 8.0F;
         float f3 = (this.uiContext.getFloat3() - f2) / 2.0F;
         float f4 = Math.max(0.0F, f3 + j * (f1 + 8.0F));
         float f5 = (f - 200.0F) / 2.0F;
         float f6 = f - Math.max(renderElement.getFloat10(), 32.0F);
         if (f5 > f6) {
            f5 = Math.max(0.0F, f6);
         }

         renderElement.onFloatFloat(f4, Math.max(0.0F, f5));
         float f7 = Math.max(0.0F, f5);
         this.onFloatFloatRenderElement(f7, f4, renderElement);
      }
   }

   public float getFloatByRenderElementFloat(RenderElement renderElement, float value) {
      float f = renderElement.getFloat12();
      float f1 = renderElement.getFloat13() - renderElement.getValue261();
      float f2 = -f1;
      float f3 = Math.max(f2, this.uiContext.getValue238() - f - f1);
      return Math.clamp(value, f2, f3);
   }

   public float getFloatByFloatRenderElement(float value, RenderElement renderElement) {
      float f = renderElement.getFloat21();
      float f1 = renderElement.getFloat17() - renderElement.getValue260();
      float f2 = -f1;
      float f3 = Math.max(f2, this.uiContext.getFloat3() - f - f1);
      return Math.clamp(value, f2, f3);
   }

   private static ConfigManager getConfigManager() {
      SystemClient systemclient = SystemClient.getInstance();
      return systemclient != null ? systemclient.getConfigManager() : null;
   }

   private float getFloat() {
      float f = this.uiContext.getValue238();
      return f > 0.0F ? f : 1080.0F;
   }

   public float[] getFloatArrayByRenderElement(RenderElement renderElement) {
      return this.map.get(renderElement);
   }

   public void onFloatFloatRenderElement2(float value, float value2, RenderElement renderElement) {
      if (renderElement != null) {
         float[] afloat = this.map.get(renderElement);
         if (afloat == null || !(Math.abs(afloat[0] - value2) < 0.01F) || !(Math.abs(afloat[1] - value) < 0.01F)) {
            this.onFloatFloatRenderElement(value, value2, renderElement);
            renderElement.onFloatFloat(value2, value);
            this.onRenderElement3(renderElement);
         }
      }
   }

   public void onRenderElement3(RenderElement renderElement) {
      ConfigManager configmanager = getConfigManager();
      if (configmanager != null) {
         float[] afloat = this.map.get(renderElement);
         float f = afloat != null ? afloat[0] : renderElement.getValue260();
         float f1 = afloat != null ? afloat[1] : renderElement.getValue261();
         String s = renderElement.getString3();
         configmanager.onFloatFloatString(f1, f, s);
      }
   }

   public void onList(List<RenderElement> list) {
      for (RenderElement renderelement : list) {
         this.onRenderElement(renderelement);
      }
   }
}
