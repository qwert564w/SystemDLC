package client.gui.widget;

import client.api.Hook;
import client.api.HookClass;
import client.enums.InjectPoint;
import client.module.visual.Enhancer;
import client.util.ReflectionCache;
import client.util.UnsafeAccess;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.BiFunction;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

@HookClass(TextFieldWidget.class)
public class TextFieldAccess {
   private static final Class<TextFieldWidget> classValue = TextFieldWidget.class;
   private static final UnsafeAccess<Enhancer> unsafeAccess = new UnsafeAccess<>(Enhancer.class);
   private static final long time = ReflectionCache.getLongByClassClass2(classValue, TextRenderer.class);
   private static final long time2 = ReflectionCache.getLongByClassClassInt(classValue, String.class, 0);
   private static final long time3 = ReflectionCache.getLongByClassClassInt(classValue, String.class, 1);
   private static final long time4 = ReflectionCache.getLongByClassClass2(classValue, BiFunction.class);
   private static final long time5 = ReflectionCache.getLongByClassClass2(classValue, Text.class);
   private static final long time6 = ReflectionCache.getLongByClassClass2(classValue, long.class);
   private static final long time7 = ReflectionCache.getLongByClassClassInt(classValue, boolean.class, 2);
   private static final long time8 = ReflectionCache.getLongByClassClassInt(classValue, int.class, 1);
   private static final long time9 = ReflectionCache.getLongByClassClassInt(classValue, int.class, 2);
   private static final long time10 = ReflectionCache.getLongByClassClassInt(classValue, int.class, 3);
   private static final long time11 = ReflectionCache.getLongByClassClassInt(classValue, int.class, 4);
   private static final long time12 = ReflectionCache.getLongByClassClassInt(classValue, int.class, 5);
   private static final MethodHandle methodHandle = getMethodHandle2();
   private static final MethodHandle methodHandle2 = getMethodHandle();
   private static final ButtonTextures buttonTextures = getButtonTextures();

   private static MethodHandle getMethodHandle() {
      for (Method method : classValue.getDeclaredMethods()) {
         if (method.getReturnType() == void.class && method.getParameterCount() == 5) {
            Class[] aclass = method.getParameterTypes();
            if (aclass[0] == DrawContext.class && aclass[1] == int.class && aclass[2] == int.class && aclass[3] == int.class && aclass[4] == int.class) {
               try {
                  method.setAccessible(true);
                  return MethodHandles.lookup()
                     .unreflect(method)
                     .asType(MethodType.methodType(void.class, classValue, DrawContext.class, int.class, int.class, int.class, int.class));
               } catch (Throwable throwable) {
                  return null;
               }
            }
         }
      }

      return null;
   }

   private static int getIntByTextFieldWidget(TextFieldWidget textFieldWidget) {
      if (methodHandle == null) {
         return Integer.MAX_VALUE;
      } else {
         try {
            return (int)methodHandle.invokeExact((TextFieldWidget)textFieldWidget);
         } catch (Throwable throwable) {
            return Integer.MAX_VALUE;
         }
      }
   }

   private static ButtonTextures getButtonTextures() {
      for (Field field : classValue.getDeclaredFields()) {
         if (Modifier.isStatic(field.getModifiers()) && field.getType() == ButtonTextures.class) {
            try {
               field.setAccessible(true);
               return (ButtonTextures)field.get(null);
            } catch (Throwable throwable) {
               return null;
            }
         }
      }

      return null;
   }

   private static MethodHandle getMethodHandle2() {
      int i = 0;

      for (Method method : classValue.getDeclaredMethods()) {
         if (!Modifier.isStatic(method.getModifiers()) && method.getParameterCount() == 0 && method.getReturnType() == int.class && i++ == 1) {
            try {
               method.setAccessible(true);
               return MethodHandles.lookup().unreflect(method).asType(MethodType.methodType(int.class, classValue));
            } catch (Throwable throwable) {
               return null;
            }
         }
      }

      return null;
   }

   @Hook(
      method = "method_48579",
      desc = "(Lnet/minecraft/class_332;IIF)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isTextFieldWidgetDrawContextIntIntFloat(TextFieldWidget textFieldWidget, DrawContext drawContext, int count, int count2, float value) {
      Enhancer enhancer = (Enhancer)unsafeAccess.getModule2();
      if (enhancer == null || !enhancer.check7()) {
         return true;
      } else if (!textFieldWidget.visible) {
         return true;
      } else {
         TextRenderer textrenderer = (TextRenderer)ReflectionCache.getObjectByObjectLong(textFieldWidget, time);
         String s = (String)ReflectionCache.getObjectByObjectLong(textFieldWidget, time2);
         BiFunction bifunction = (BiFunction)ReflectionCache.getObjectByObjectLong(textFieldWidget, time4);
         if (textrenderer != null && s != null && bifunction != null) {
            Text text = (Text)ReflectionCache.getObjectByObjectLong(textFieldWidget, time5);
            String s1 = (String)ReflectionCache.getObjectByObjectLong(textFieldWidget, time3);
            boolean flag = textFieldWidget.drawsBackground();
            boolean flag1 = textFieldWidget.isFocused();
            int i = ReflectionCache.getIntByObjectLong(textFieldWidget, time8);
            int j = ReflectionCache.getIntByObjectLong(textFieldWidget, time9);
            int k = ReflectionCache.getIntByObjectLong(textFieldWidget, time10);
            int l = ReflectionCache.isObjectLong(textFieldWidget, time7)
               ? ReflectionCache.getIntByObjectLong(textFieldWidget, time11)
               : ReflectionCache.getIntByObjectLong(textFieldWidget, time12);
            long i1 = ReflectionCache.getLongByObjectLong(textFieldWidget, time6);
            if (flag && buttonTextures != null) {
               Identifier identifier = buttonTextures.get(textFieldWidget.isNarratable(), flag1);
               if (identifier != null) {
                  drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifier, textFieldWidget.getX(), textFieldWidget.getY(), textFieldWidget.getWidth(), textFieldWidget.getHeight());
               }
            }

            int i3 = j - i;
            String s2 = textrenderer.trimToWidth(s.substring(i), textFieldWidget.getInnerWidth());
            boolean flag2 = i3 >= 0 && i3 <= s2.length();
            boolean flag3 = flag1 && (Util.getMeasuringTimeMs() - i1) / 300L % 2L == 0L && flag2;
            int j1 = flag ? textFieldWidget.getX() + 4 : textFieldWidget.getX();
            int k1 = flag ? textFieldWidget.getY() + (textFieldWidget.getHeight() - 8) / 2 : textFieldWidget.getY();
            int l1 = MathHelper.clamp(k - i, 0, s2.length());
            TextPainter textpainter = TextPainter.getTextPainterByTextFieldWidget2(textFieldWidget);
            DrawLayer drawlayer = LayerStack.getDrawLayerByString(enhancer.getString());
            float f = Math.max(1.0F, enhancer.getFloat());
            MatrixStack matrixstack = drawContext.getMatrices();
            textpainter.onTextRendererStringInt(textrenderer, s2, j1);
            boolean flag5 = true;
            int i2 = textpainter.getIntByIntBooleanIntBiFunctionMatrixStackIntTextRendererIntStringFloatIntDrawLayerDrawContext(
               i, flag5, i3, bifunction, matrixstack, j1, textrenderer, l, s2, f, k1, drawlayer, drawContext
            );
            boolean flag6 = true;
            textpainter.onTextRendererBooleanDrawLayerDrawContextIntMatrixStackFloatBiFunctionInt(
               textrenderer, flag6, drawlayer, drawContext, k1, matrixstack, f, bifunction, l
            );
            textpainter.setText(s2);
            int j2 = getIntByTextFieldWidget(textFieldWidget);
            boolean flag4 = j < s.length() || s.length() >= j2;
            int k2 = flag2 ? i2 : (i3 > 0 ? j1 + textFieldWidget.getWidth() : k1);
            if (text != null && s2.isEmpty() && !flag1) {
               drawContext.drawTextWithShadow(textrenderer, text, k2, k1, l);
            }

            if (!flag4 && s1 != null) {
               drawContext.drawTextWithShadow(textrenderer, s1, k2 - 1, k1, -8355712);
            }

            if (l1 != i3) {
               int l2 = j1 + textrenderer.getWidth(s2.substring(0, l1));
               onTextFieldWidgetDrawContextIntIntIntInt(textFieldWidget, drawContext, k2, k1 - 1, l2 - 1, k1 + 10);
            }

            if (flag3) {
               if (flag4) {
                  drawContext.fill(RenderLayer.getGuiOverlay(), k2, k1 - 1, k2 + 1, k1 + 10, -3092272);
               } else {
                  drawContext.drawTextWithShadow(textrenderer, "_", k2, k1, l);
               }
            }

            return false;
         } else {
            return true;
         }
      }
   }

   private static void onTextFieldWidgetDrawContextIntIntIntInt(TextFieldWidget textFieldWidget, DrawContext drawContext, int count, int count2, int count3, int count4) {
      if (methodHandle2 != null) {
         try {
            methodHandle2.invokeExact((TextFieldWidget)textFieldWidget, (DrawContext)drawContext, (int)count, (int)count2, (int)count3, (int)count4);
         } catch (Throwable throwable) {
         }
      }
   }
}
