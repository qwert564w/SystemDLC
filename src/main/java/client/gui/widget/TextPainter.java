package client.gui.widget;

import client.data.CharTiming;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;

public final class TextPainter {
   private static final Map<TextFieldWidget, TextPainter> map = new WeakHashMap<>();
   private final Map<Integer, Long> map2 = new HashMap<>();
   private final Map<Integer, CharTiming> map3 = new HashMap<>();
   private String text = "";

   private TextPainter() {
   }

   private static TextPainter getTextPainterByTextFieldWidget(TextFieldWidget textFieldWidget) {
      return new TextPainter();
   }

   public void setText(String text2) {
      this.text = text2;
   }

   public static TextPainter getTextPainterByTextFieldWidget2(TextFieldWidget textFieldWidget) {
      synchronized (map) {
         return map.computeIfAbsent(textFieldWidget, TextPainter::getTextPainterByTextFieldWidget);
      }
   }

   public void onTextRendererBooleanDrawLayerDrawContextIntMatrixStackFloatBiFunctionInt(
      TextRenderer textRenderer, boolean flag, DrawLayer drawLayer, DrawContext drawContext, int count, MatrixStack matrixStack, float value, BiFunction biFunction, int count2
   ) {
      long i = System.currentTimeMillis();
      byte b0 = 9;
      Iterator iterator = this.map3.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry entry = (Entry)iterator.next();
         CharTiming chartiming = (CharTiming)entry.getValue();
         float f = (float)(i - chartiming.getTimestamp()) / value;
         if (f >= 1.0F) {
            iterator.remove();
         } else {
            float f1 = Math.max(1.0F - drawLayer.getFloatByFloat(f), 0.003921569F);
            if (f1 <= 0.01F) {
               iterator.remove();
            } else {
               OrderedText orderedtext = (OrderedText)biFunction.apply(String.valueOf(chartiming.getCh()), (Integer)entry.getKey());
               matrixStack.push();
               if (drawLayer.isFlag()) {
                  int l = chartiming.getX();
                  float f2 = 1.0F;
                  int j = l;
                  drawLayer.onIntMatrixStackIntFloatInt(b0, matrixStack, count, f2, j);
                  drawContext.drawText(textRenderer, orderedtext, chartiming.getX(), count, LayerStack.getIntByFloatInt(f1, count2), flag);
               } else {
                  int k = chartiming.getX();
                  drawLayer.onIntMatrixStackIntFloatInt(b0, matrixStack, count, f1, k);
                  drawContext.drawText(textRenderer, orderedtext, chartiming.getX(), count, count2, flag);
               }

               matrixStack.pop();
            }
         }
      }
   }

   public int getIntByIntBooleanIntBiFunctionMatrixStackIntTextRendererIntStringFloatIntDrawLayerDrawContext(
      int count,
      boolean flag,
      int count2,
      BiFunction biFunction,
      MatrixStack matrixStack,
      int count3,
      TextRenderer textRenderer,
      int count4,
      String text2,
      float value,
      int count5,
      DrawLayer drawLayer,
      DrawContext drawContext
   ) {
      int i = count3;
      int j = count3;
      long k = System.currentTimeMillis();
      byte b0 = 9;

      for (int l = 0; l < text2.length(); l++) {
         OrderedText orderedtext = (OrderedText)biFunction.apply(String.valueOf(text2.charAt(l)), count + l);
         long i1 = this.map2.getOrDefault(l, k);
         float f = Math.min((float)(k - i1) / value, 1.0F);
         if (!(f < 1.0E-4F)) {
            int j1 = count4;
            float f1 = 1.0F;
            if (drawLayer.isFlag()) {
               float f2 = drawLayer.getFloatByFloat(f);
               j1 = LayerStack.getIntByFloatInt(f2, count4);
            } else {
               f1 = drawLayer.getFloatByFloatLongLong(value, i1, k);
            }

            matrixStack.push();
            drawLayer.onIntFloatIntMatrixStackInt(i, f1, b0, matrixStack, count5);
            drawContext.drawText(textRenderer, orderedtext, i, count5, j1, flag);
            matrixStack.pop();
            int k1 = textRenderer.getWidth(orderedtext);
            if (l < count2) {
               j += k1;
            }

            i += k1;
         }
      }

      return j;
   }

   public void onTextRendererStringInt(TextRenderer textRenderer, String text2, int count) {
      long i = System.currentTimeMillis();
      int j = Math.max(text2.length(), this.text.length());

      for (int k = 0; k < j; k++) {
         boolean flag = k < text2.length();
         boolean flag1 = k < this.text.length();
         char c0 = flag ? text2.charAt(k) : 0;
         char c1 = flag1 ? this.text.charAt(k) : 0;
         if (flag && (!flag1 || c0 != c1)) {
            this.map2.put(k, i);
         }

         if (flag1 && (!flag || c0 != c1)) {
            int l = textRenderer.getWidth(this.text.substring(0, k));
            this.map3.put(k, new CharTiming(c1, i, count + l));
         }
      }
   }
}
