package client.util;

import client.api.Hook;
import client.api.HookClass;
import client.enums.InjectPoint;
import client.gui.widget.UiContext;
import client.module.client.HudModule;
import client.module.client.StreamBypass;
import client.module.player.Protect;
import java.util.function.BiFunction;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.OrderedText;

@HookClass(ChatScreen.class)
public class TextFieldHooks {
   private static final UnsafeAccess<Protect> unsafeAccess = new UnsafeAccess<>(Protect.class);
   private static final UnsafeAccess<HudModule> unsafeAccess2 = new UnsafeAccess<>(HudModule.class);
   private static final long time = ReflectionCache.getLongByClassClass2(ChatScreen.class, TextFieldWidget.class);
   private static final long time2 = ReflectionCache.getLongByClassClass2(TextFieldWidget.class, BiFunction.class);
   private static final ThreadLocal<BiFunction<String, Integer, OrderedText>> threadLocal = new ThreadLocal<>();

   @Hook(
      method = "method_25394",
      desc = "(Lnet/minecraft/class_332;IIF)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onChatScreenDrawContextIntIntFloat(ChatScreen chatScreen, DrawContext drawContext, int count, int count2, float value) {
      if (unsafeAccess2.getModule2() != null && !StreamBypass.check4()) {
         UiContext.getInstance().render(drawContext, 1.0F);
      }

      CoordsParser.onIntDrawContextInt(count, drawContext, count2);
      BiFunction bifunction = threadLocal.get();
      if (bifunction != null) {
         threadLocal.remove();
         TextFieldWidget textfieldwidget = (TextFieldWidget)ReflectionCache.getObjectByObjectLong(chatScreen, time);
         if (textfieldwidget != null) {
            textfieldwidget.setRenderTextProvider(bifunction);
         }
      }
   }

   @Hook(
      method = "method_25394",
      desc = "(Lnet/minecraft/class_332;IIF)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onChatScreenDrawContextIntIntFloat2(ChatScreen chatScreen, DrawContext drawContext, int count, int count2, float value) {
      Protect protect = (Protect)unsafeAccess.getModule2();
      if (protect != null && protect.check3()) {
         TextFieldWidget textfieldwidget = (TextFieldWidget)ReflectionCache.getObjectByObjectLong(chatScreen, time);
         if (textfieldwidget != null) {
            String s = textfieldwidget.getText();
            if (s != null && !s.isEmpty()) {
               int[] aint = protect.getIntArrayByString(s);
               if (aint != null && aint[1] > aint[0]) {
                  BiFunction<String, Integer, OrderedText> bifunction = (BiFunction<String, Integer, OrderedText>)ReflectionCache.getObjectByObjectLong(textfieldwidget, time2);
                  if (bifunction != null) {
                     int i = aint[0];
                     int j = aint[1];
                     BiFunction<String, Integer, OrderedText> bifunction1 = (item, item2) -> {
                        if (item != null && !item.isEmpty()) {
                           int ix = item2 == null ? 0 : item2;
                           int jx = ix + item.length();
                           if (jx > i && ix < j) {
                              StringBuilder stringbuilder = new StringBuilder(item.length());

                              for (int k = 0; k < item.length(); k++) {
                                 int l = ix + k;
                                 char c0 = item.charAt(k);
                                 if (l >= i && l < j && !Character.isWhitespace(c0)) {
                                    stringbuilder.append('*');
                                 } else {
                                    stringbuilder.append(c0);
                                 }
                              }

                              return (OrderedText)bifunction.apply(stringbuilder.toString(), item2);
                           } else {
                              return (OrderedText)bifunction.apply(item, item2);
                           }
                        } else {
                           return (OrderedText)bifunction.apply(item, item2);
                        }
                     };
                     threadLocal.set(bifunction);
                     textfieldwidget.setRenderTextProvider(bifunction1);
                  }
               }
            }
         }
      }
   }
}
