package client.gui.widget;

import client.api.UiMetrics;
import client.module.Feature;
import client.util.SnapTracker;
import client.util.ViewModelController;
import java.util.List;
import org.lwjgl.glfw.GLFW;

public final class KeyHintBar implements UiMetrics {
   private static final float value235 = 8.0F;
   private final UiContext uiContext;
   private final TooltipLayer tooltipLayer;
   private RenderElement renderElement;
   private float value236;
   private float value237;

   public KeyHintBar(UiContext uiContext2, TooltipLayer tooltipLayer2) {
      this.uiContext = uiContext2;
      this.tooltipLayer = tooltipLayer2;
   }

   private void onFloatFloat(float value, float value2) {
      float f4 = value2 - this.value236;
      float f1 = value - this.value237;
      float f = f4;
      RenderElement renderelement = this.renderElement;
      float[] afloat = this.getFloatArrayByFloatFloatRenderElement(f, f1, renderelement);
      this.renderElement.onFloatFloat(afloat[0], afloat[1]);
      this.renderElement.update2();
      f4 = afloat[0];
      float f3 = afloat[1];
      float f2 = f4;
      RenderElement renderelement1 = this.renderElement;
      this.tooltipLayer.onFloatFloatRenderElement(f3, f2, renderelement1);
   }

   private float getFloatByRenderElementFloat(RenderElement renderElement, float value) {
      float f = renderElement.getFloat21();
      float f1 = renderElement.getFloat17() - renderElement.getValue260();
      float f2 = value + f1;
      float f3 = f2 + f;
      float f4 = f2 + f / 2.0F;
      float f5 = this.uiContext.getFloat3();
      SnapTracker snaptracker = new SnapTracker(value);
      snaptracker.onFloatFloat(f2, 0.0F);
      snaptracker.onFloatFloat(f3, f5);
      snaptracker.onFloatFloat(f4, f5 / 2.0F);

      for (RenderElement renderelement : this.uiContext.getList2()) {
         if (renderelement != renderElement && isRenderElement(renderelement)) {
            float f6 = renderelement.getFloat17();
            float f7 = f6 + renderelement.getFloat21();
            float f8 = f6 + renderelement.getFloat21() / 2.0F;
            snaptracker.onFloatFloat(f2, f6);
            snaptracker.onFloatFloat(f3, f7);
            snaptracker.onFloatFloat(f2, f7 + 8.0F);
            snaptracker.onFloatFloat(f3, f6 - 8.0F);
            snaptracker.onFloatFloat(f4, f8);
         }
      }

      return snaptracker.value2;
   }

   private float getFloatByFloatRenderElement(float value, RenderElement renderElement) {
      float f = renderElement.getFloat12();
      float f1 = renderElement.getFloat13() - renderElement.getValue261();
      float f2 = value + f1;
      float f3 = f2 + f;
      float f4 = f2 + f / 2.0F;
      float f5 = this.uiContext.getValue238();
      SnapTracker snaptracker = new SnapTracker(value);
      snaptracker.onFloatFloat(f2, 0.0F);
      snaptracker.onFloatFloat(f3, f5);
      snaptracker.onFloatFloat(f4, f5 / 2.0F);

      for (RenderElement renderelement : this.uiContext.getList2()) {
         if (renderelement != renderElement && isRenderElement(renderelement)) {
            float f6 = renderelement.getFloat13();
            float f7 = f6 + renderelement.getFloat12();
            float f8 = f6 + renderelement.getFloat12() / 2.0F;
            snaptracker.onFloatFloat(f2, f6);
            snaptracker.onFloatFloat(f3, f7);
            snaptracker.onFloatFloat(f2, f7 + 8.0F);
            snaptracker.onFloatFloat(f3, f6 - 8.0F);
            snaptracker.onFloatFloat(f4, f8);
         }
      }

      return snaptracker.value2;
   }

   public static boolean isRenderElement(RenderElement renderElement) {
      return renderElement.isFlag() && renderElement.check18();
   }

   public RenderElement getRenderElement() {
      return this.renderElement;
   }

   public static boolean check() {
      if (Feature.mc != null && Feature.mc.getWindow() != null) {
         long i = Feature.mc.getWindow().getHandle();
         return GLFW.glfwGetKey(i, 341) == 1 || GLFW.glfwGetKey(i, 345) == 1;
      } else {
         return false;
      }
   }

   public void onFloatBooleanFloatBoolean(float value, boolean flag, float value2, boolean flag2) {
      if (flag2 && !flag) {
         this.onFloatFloat2(value2, value);
      } else if (flag2 && this.renderElement != null) {
         this.onFloatFloat(value2, value);
      } else if (!flag2 && flag) {
         this.update();
      }
   }

   private void onFloatFloat2(float value, float value2) {
      if (!ViewModelController.isFlag()) {
         List list = this.uiContext.getList2();

         for (int i = list.size() - 1; i >= 0; i--) {
            RenderElement renderelement = (RenderElement)list.get(i);
            if (isRenderElement(renderelement) && renderelement.check22() && renderelement.isFloatFloat(value2, value)) {
               this.renderElement = renderelement;
               this.value236 = value2 - renderelement.getValue260();
               this.value237 = value - renderelement.getValue261();
               this.uiContext.addRenderElement(renderelement);
               break;
            }
         }
      }
   }

   public void update() {
      if (this.renderElement != null) {
         TooltipLayer tooltiplayer = this.tooltipLayer;
         RenderElement renderelement1 = this.renderElement;
         float f1 = this.renderElement.getValue261();
         float f = this.renderElement.getValue260();
         RenderElement renderelement = renderelement1;
         tooltiplayer.onFloatFloatRenderElement(f1, f, renderelement);
         this.tooltipLayer.onRenderElement3(this.renderElement);
         this.renderElement = null;
      }
   }

   private float[] getFloatArrayByFloatFloatRenderElement(float value, float value2, RenderElement renderElement) {
      float f = renderElement.getValue260();
      float f1 = renderElement.getValue261();
      float f2 = this.tooltipLayer.getFloatByFloatRenderElement(value, renderElement);
      float f3 = this.tooltipLayer.getFloatByRenderElementFloat(renderElement, value2);
      if (!check()) {
         f2 = this.getFloatByRenderElementFloat(renderElement, f2);
         f3 = this.getFloatByFloatRenderElement(f3, renderElement);
      }

      f2 = this.tooltipLayer.getFloatByFloatRenderElement(f2, renderElement);
      f3 = this.tooltipLayer.getFloatByRenderElementFloat(renderElement, f3);
      if (this.isRenderElementFloatFloat(renderElement, f3, f2)) {
         if (!this.isRenderElementFloatFloat(renderElement, f3, f)) {
            f2 = f;
         } else if (!this.isRenderElementFloatFloat(renderElement, f1, f2)) {
            f3 = f1;
         } else {
            f2 = f;
            f3 = f1;
         }
      }

      return new float[]{f2, f3};
   }

   private boolean isRenderElementFloatFloat(RenderElement renderElement, float value, float value2) {
      float f = renderElement.getFloat21();
      float f1 = renderElement.getFloat12();
      float f2 = renderElement.getFloat17() - renderElement.getValue260();
      float f3 = renderElement.getFloat13() - renderElement.getValue261();
      float f4 = value2 + f2;
      float f5 = f4 + f;
      float f6 = value + f3;
      float f7 = f6 + f1;

      for (RenderElement renderelement : this.uiContext.getList2()) {
         if (renderelement != renderElement && isRenderElement(renderelement)) {
            float f8 = renderelement.getFloat17();
            float f9 = f8 + renderelement.getFloat21();
            float f10 = renderelement.getFloat13();
            float f11 = f10 + renderelement.getFloat12();
            if (f5 > f8 && f4 < f9 && f7 > f10 && f6 < f11) {
               return true;
            }
         }
      }

      return false;
   }
}
