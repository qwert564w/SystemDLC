package client.gui.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class FocusManager {
   private static final float value = 10.0F;
   private static final float value2 = 0.001F;
   private final UiContext uiContext;
   private final TooltipLayer tooltipLayer;
   private final List<RenderElement> list = new ArrayList<>();
   private final Comparator<RenderElement> comparator;
   private boolean[] booleanArray = new boolean[16];
   private float[] floatArray = new float[16];
   private float[] floatArray2 = new float[16];
   private float[] floatArray3 = new float[16];
   private float[] floatArray4 = new float[16];
   private float value3;
   private float value4;

   public FocusManager(UiContext uiContext2, TooltipLayer tooltipLayer2) {
      this.uiContext = uiContext2;
      this.tooltipLayer = tooltipLayer2;
      this.comparator = (item, item2) -> Float.compare(tooltipLayer2.getFloatArrayByRenderElement(item)[1], tooltipLayer2.getFloatArrayByRenderElement(item2)[1]);
   }

   private void onFloatIntRenderElementRenderElementFloat(float value, int count, RenderElement renderElement, RenderElement renderElement2, float value2) {
      this.value3 = value2;
      this.value4 = value;
      if (this.booleanArray[count]) {
         float f = renderElement2.getFloat17() - renderElement2.getValue260();
         float f1 = renderElement2.getFloat21();
         float f2 = renderElement2.getFloat12();

         for (int i = 0; i < count; i++) {
            if (this.booleanArray[i]) {
               RenderElement renderelement = this.list.get(i);
               if (renderelement != renderElement) {
                  float f3 = this.floatArray3[i] - 10.0F;
                  float f4 = this.floatArray4[i] + 10.0F;
                  float f5 = this.floatArray[i] - 10.0F;
                  float f6 = this.floatArray2[i] + 10.0F;
                  float f7 = this.value3 + f;
                  float f8 = f7 + f1;
                  float f9 = this.value4;
                  float f10 = f9 + f2;
                  float f11 = Math.min(f8, f4) - Math.max(f7, f3);
                  float f12 = Math.min(f10, f6) - Math.max(f9, f5);
                  if (!(f11 <= 0.0F) && !(f12 <= 0.0F)) {
                     if (f11 < f12) {
                        if (f7 + f8 >= f3 + f4) {
                           this.value3 += f4 - f7;
                        } else {
                           this.value3 -= f8 - f3;
                        }
                     } else {
                        this.value4 += f6 - f9;
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isRenderElement(RenderElement renderElement) {
      return renderElement.isFlag() && renderElement.getFloat16() > 0.001F && renderElement.check2();
   }

   private void setInt(int count) {
      if (this.booleanArray.length < count) {
         int i = this.booleanArray.length;

         while (i < count) {
            i *= 2;
         }

         this.booleanArray = Arrays.copyOf(this.booleanArray, i);
         this.floatArray = Arrays.copyOf(this.floatArray, i);
         this.floatArray2 = Arrays.copyOf(this.floatArray2, i);
         this.floatArray3 = Arrays.copyOf(this.floatArray3, i);
         this.floatArray4 = Arrays.copyOf(this.floatArray4, i);
      }
   }

   public void addRenderElement(RenderElement renderElement) {
      this.list.clear();

      for (RenderElement renderelement : this.uiContext.getList2()) {
         if (this.tooltipLayer.getFloatArrayByRenderElement(renderelement) != null) {
            this.list.add(renderelement);
         }
      }

      this.list.sort(this.comparator);
      int i = this.list.size();
      this.setInt(i);

      for (int j = 0; j < i; j++) {
         RenderElement renderelement1 = this.list.get(j);
         boolean flag = isRenderElement(renderelement1);
         this.booleanArray[j] = flag;
         if (flag) {
            float f = renderelement1.getFloat17();
            this.floatArray3[j] = f;
            this.floatArray4[j] = f + renderelement1.getFloat21();
            this.floatArray[j] = renderelement1.getFloat13();
            this.floatArray2[j] = renderelement1.getFloat13() + renderelement1.getFloat12() * renderelement1.getFloat16();
         }
      }

      for (int k = 0; k < i; k++) {
         RenderElement renderelement2 = this.list.get(k);
         if (renderelement2 != renderElement) {
            float[] afloat = this.tooltipLayer.getFloatArrayByRenderElement(renderelement2);
            float f2 = afloat[0];
            float f4 = this.tooltipLayer.getFloatByFloatRenderElement(f2, renderelement2);
            float f1 = this.tooltipLayer.getFloatByRenderElementFloat(renderelement2, afloat[1]);
            this.onFloatIntRenderElementRenderElementFloat(f1, k, renderElement, renderelement2, f4);
            float f3 = this.value3;
            renderelement2.onFloatFloat(
               this.tooltipLayer.getFloatByFloatRenderElement(f3, renderelement2), this.tooltipLayer.getFloatByRenderElementFloat(renderelement2, this.value4)
            );
         }
      }
   }
}
