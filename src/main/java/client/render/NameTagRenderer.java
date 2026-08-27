package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.concurrent.SystemClient;
import client.data.SystemFriend;
import client.enums.InjectPoint;
import client.module.Feature;
import client.module.player.Protect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

@HookClass(TextRenderer.class)
public class NameTagRenderer {
   private static Protect protect;
   private static String text;
   private static long time = 0L;
   private static final Map<String, Pattern> map = new ConcurrentHashMap<>();
   private static Set<String> set;
   private static final StringBuilder stringBuilder = new StringBuilder(64);

   private static String getStringByString(String text2) {
      String s = SystemFriend.getInstance().getStringByString(text2);
      return s != null && !s.isEmpty() ? s : "SystemFriend";
   }

   private static OrderedText getOrderedTextByOrderedText(OrderedText orderedText) {
      if (Feature.mc.player != null && Feature.mc.world != null) {
         update();
         if (protect != null && protect.isEnabled()) {
            StringBuilder stringbuilder = stringBuilder;
            stringbuilder.setLength(0);
            orderedText.accept((item, item2, item3) -> {
               stringbuilder.appendCodePoint(item3);
               return true;
            });
            String s = stringbuilder.toString();
            boolean flag = protect.check3();
            boolean flag1 = protect.getHideDruzey().isFlag3() && set != null;
            boolean flag2 = protect.getHideNick().isFlag3();
            boolean flag3 = flag2 && text != null && s.contains(text);
            boolean flag4 = false;
            if (flag1) {
               for (Pattern pattern : map.values()) {
                  if (pattern.matcher(s).find()) {
                     flag4 = true;
                     break;
                  }
               }
            }

            if (!flag3 && !flag4 && !flag) {
               return orderedText;
            } else {
               ArrayList arraylist2 = new ArrayList();
               ArrayList arraylist3 = new ArrayList();
               orderedText.accept((item2, item3, item4) -> {
                  arraylist2.add(new int[]{item4});
                  arraylist3.add(item3);
                  return true;
               });
               ArrayList<int[]> arraylist = new ArrayList();
               ArrayList arraylist1 = new ArrayList();
               if (flag2 && text != null) {
                  int i = 0;

                  while ((i = s.indexOf(text, i)) != -1) {
                     String s1 = protect.getSvoyNick().getText();
                     String s2 = s1 != null && !s1.isEmpty() ? s1 : "SystemPlayer";
                     arraylist.add(new int[]{i, i + text.length()});
                     arraylist1.add(s2);
                     i += text.length();
                  }
               }

               if (protect.getHideDruzey().isFlag3() && set != null) {
                  for (Entry entry : map.entrySet()) {
                     Matcher matcher = ((Pattern)entry.getValue()).matcher(s);

                     while (matcher.find()) {
                        boolean flag5 = false;

                        for (int[] aint : arraylist) {
                           if (matcher.start() < aint[1] && matcher.end() > aint[0]) {
                              flag5 = true;
                              break;
                           }
                        }

                        if (!flag5) {
                           arraylist.add(new int[]{matcher.start(), matcher.end()});
                           arraylist1.add(getStringByString((String)entry.getKey()));
                        }
                     }
                  }
               }

               boolean flag6 = !arraylist.isEmpty();
               ArrayList<int[]> arraylist4;
               ArrayList arraylist5;
               if (flag6) {
                  arraylist4 = new ArrayList();
                  arraylist5 = new ArrayList();
                  Integer[] ainteger = new Integer[arraylist.size()];

                  for (int j1 = 0; j1 < ainteger.length; j1++) {
                     ainteger[j1] = j1;
                  }

                  Arrays.sort(ainteger, Comparator.comparingInt(item -> ((int[])arraylist.get(item))[0]));
                  int k1 = 0;
                  Integer[] ainteger1 = ainteger;
                  int j = ainteger.length;

                  for (int k = 0; k < j; k++) {
                     int l = ainteger1[k];

                     int[] aint1;
                     for (aint1 = (int[])arraylist.get(l); k1 < aint1[0]; k1++) {
                        arraylist4.add((int[])arraylist2.get(k1));
                        arraylist5.add((Style)arraylist3.get(k1));
                     }

                     Style style = (Style)arraylist3.get(aint1[0]);
                     String s3 = (String)arraylist1.get(l);

                     for (int i1 = 0; i1 < s3.length(); i1++) {
                        arraylist4.add(new int[]{s3.codePointAt(i1)});
                        arraylist5.add(style);
                     }

                     k1 = aint1[1];
                  }

                  while (k1 < arraylist2.size()) {
                     arraylist4.add((int[])arraylist2.get(k1));
                     arraylist5.add((Style)arraylist3.get(k1));
                     k1++;
                  }
               } else {
                  arraylist4 = arraylist2;
                  arraylist5 = arraylist3;
               }

               if (protect.check3()) {
                  StringBuilder stringbuilder1 = new StringBuilder();

                  for (int[] aint3 : arraylist4) {
                     stringbuilder1.appendCodePoint(aint3[0]);
                  }

                  int[] aint2 = protect.getIntArrayByString(stringbuilder1.toString());
                  if (aint2 != null && aint2[1] > aint2[0]) {
                     if (arraylist4 == arraylist2) {
                        arraylist4 = new ArrayList(arraylist2);
                        arraylist5 = new ArrayList(arraylist3);
                     }

                     byte b0 = 42;

                     for (int l1 = aint2[0]; l1 < aint2[1] && l1 < arraylist4.size(); l1++) {
                        int i2 = ((int[])arraylist4.get(l1))[0];
                        if (!Character.isWhitespace(i2)) {
                           arraylist4.set(l1, new int[]{b0});
                        }
                     }
                  } else if (arraylist4 == arraylist2) {
                     return orderedText;
                  }
               } else if (!flag6) {
                  return orderedText;
               }

               ArrayList arraylist6 = arraylist4;
               ArrayList arraylist7 = arraylist5;
               return item2 -> {
                  for (int ix = 0; ix < arraylist6.size(); ix++) {
                     if (!item2.accept(ix, (Style)arraylist7.get(ix), ((int[])arraylist6.get(ix))[0])) {
                        return false;
                     }
                  }

                  return true;
               };
            }
         } else {
            return orderedText;
         }
      } else {
         return orderedText;
      }
   }

   @Hook(
      method = "method_22942",
      desc = "(Lnet/minecraft/class_5481;FFIZLorg/joml/Matrix4f;Lnet/minecraft/class_4597;Lnet/minecraft/class_327$class_6415;II)I",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static int getIntByTextRendererOrderedTextFloatFloatIntBooleanMatrix4fVertexConsumerProviderTextLayerTypeIntInt(
      TextRenderer textRenderer,
      OrderedText orderedText,
      float value,
      float value2,
      int count,
      boolean flag,
      Matrix4f matrix4f,
      VertexConsumerProvider vertexConsumerProvider,
      TextLayerType textLayerType,
      int count2,
      int count3
   ) {
      orderedText = getOrderedTextByOrderedText(orderedText);
      HandleInvoker.update();
      return textRenderer.draw(orderedText, value, value2, count, flag, matrix4f, vertexConsumerProvider, textLayerType, count2, count3);
   }

   @Hook(
      method = "method_30882",
      desc = "(Lnet/minecraft/class_2561;FFIZLorg/joml/Matrix4f;Lnet/minecraft/class_4597;Lnet/minecraft/class_327$class_6415;IIZ)I",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static int getIntByTextRendererTextFloatFloatIntBooleanMatrix4fVertexConsumerProviderTextLayerTypeIntIntBoolean(
      TextRenderer textRenderer,
      Text text2,
      float value,
      float value2,
      int count,
      boolean flag,
      Matrix4f matrix4f,
      VertexConsumerProvider vertexConsumerProvider,
      TextLayerType textLayerType,
      int count2,
      int count3,
      boolean flag2
   ) {
      OrderedText orderedtext = getOrderedTextByOrderedText(text2.asOrderedText());
      HandleInvoker.update();
      return textRenderer.draw(orderedtext, value, value2, count, flag, matrix4f, vertexConsumerProvider, textLayerType, count2, count3);
   }

   @Hook(
      method = "method_37296",
      desc = "(Lnet/minecraft/class_5481;FFIILorg/joml/Matrix4f;Lnet/minecraft/class_4597;I)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onTextRendererOrderedTextFloatFloatIntIntMatrix4fVertexConsumerProviderInt(
      TextRenderer textRenderer, OrderedText orderedText, float value, float value2, int count, int count2, Matrix4f matrix4f, VertexConsumerProvider vertexConsumerProvider, int count3
   ) {
      orderedText = getOrderedTextByOrderedText(orderedText);
      HandleInvoker.update();
      textRenderer.drawWithOutline(orderedText, value, value2, count, count2, matrix4f, vertexConsumerProvider, count3);
   }

   private static void update() {
      long i = System.currentTimeMillis();
      if (i - time >= 2000L) {
         time = i;
         SystemClient systemclient = SystemClient.getInstance();
         if (systemclient != null && systemclient.getModuleRegistry() != null) {
            protect = (Protect)systemclient.getModuleRegistry().getModuleByClass(Protect.class);
            text = Feature.mc.player != null ? Feature.mc.player.getGameProfile().getName() : null;
            Set setx = SystemFriend.getInstance().getSet();
            if (set == null || !set.equals(setx)) {
               set = new HashSet<>(setx);
               map.clear();

               for (String s : set) {
                  map.put(s, Pattern.compile("(?i)" + Pattern.quote(s)));
               }
            }
         } else {
            protect = null;
         }
      }
   }

   private static String getStringByString2(String text2) {
      if (Feature.mc.player != null && Feature.mc.world != null) {
         update();
         if (protect != null && protect.isEnabled()) {
            if (protect.getHideNick().isFlag3() && text != null && text2.contains(text)) {
               String s = protect.getSvoyNick().getText();
               String s1 = s != null && !s.isEmpty() ? s : "SystemPlayer";
               text2 = text2.replace(text, s1);
            }

            if (protect.getHideDruzey().isFlag3() && set != null) {
               for (Entry entry : map.entrySet()) {
                  text2 = ((Pattern)entry.getValue()).matcher(text2).replaceAll(Matcher.quoteReplacement(getStringByString((String)entry.getKey())));
               }
            }

            if (protect.check3()) {
               text2 = protect.getStringByString(text2);
            }

            return text2;
         } else {
            return text2;
         }
      } else {
         return text2;
      }
   }

   @Hook(
      method = "method_27521",
      desc = "(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/class_4597;Lnet/minecraft/class_327$class_6415;II)I",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static int getIntByTextRendererStringFloatFloatIntBooleanMatrix4fVertexConsumerProviderTextLayerTypeIntInt(
      TextRenderer textRenderer,
      String text2,
      float value,
      float value2,
      int count,
      boolean flag,
      Matrix4f matrix4f,
      VertexConsumerProvider vertexConsumerProvider,
      TextLayerType textLayerType,
      int count2,
      int count3
   ) {
      text2 = getStringByString2(text2);
      HandleInvoker.update();
      return textRenderer.draw(text2, value, value2, count, flag, matrix4f, vertexConsumerProvider, textLayerType, count2, count3);
   }
}
