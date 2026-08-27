package client.util;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.data.ChatEntry;
import client.enums.InjectPoint;
import client.module.Feature;
import client.module.visual.Enhancer;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.ChatHudLine.Visible;
import net.minecraft.client.gui.hud.MessageIndicator.Icon;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.network.message.ChatVisibility;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

@HookClass(ChatHud.class)
public class ChatSpamGuard {
   private static final long time = ReflectionCache.getLongByClassClass2(ChatHud.class, MinecraftClient.class);
   private static final long time2 = ReflectionCache.getLongByClassClassInt(ChatHud.class, List.class, 0);
   private static final long time3 = ReflectionCache.getLongByClassClassInt(ChatHud.class, List.class, 1);
   private static final long time4 = ReflectionCache.getLongByClassClass2(ChatHud.class, int.class);
   private static final long time5 = ReflectionCache.getLongByClassClass2(ChatHud.class, boolean.class);
   private static boolean flag = false;
   private static final List<ChatEntry> list = new ArrayList<>();
   private static final UnsafeAccess<Enhancer> unsafeAccess = new UnsafeAccess<>(Enhancer.class);

   @Hook(
      method = "method_58744",
      desc = "(Lnet/minecraft/class_303;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onChatHudChatHudLine(ChatHud chatHud, ChatHudLine chatHudLine) {
      List<ChatHudLine> listx = (List<ChatHudLine>)ReflectionCache.getObjectByObjectLong(chatHud, time2);
      Enhancer enhancer = (Enhancer)unsafeAccess.getModule2();
      boolean flagx = enhancer != null && enhancer.check8();
      boolean flag1 = enhancer != null && enhancer.check5();
      if (flag1) {
         String s = chatHudLine.content().getString();

         for (int i = 0; i < list.size() && i < 5; i++) {
            ChatEntry chatentry = list.get(i);
            if (chatentry.text.equals(s)) {
               listx.removeIf(var1x -> var1x.creationTick() == chatentry.value2);
               chatentry.value++;
               chatentry.value2 = chatHudLine.creationTick();
               MutableText mutabletext = Text.empty().append(chatHudLine.content()).append(Text.literal(" (x" + chatentry.value + ")").formatted(Formatting.GRAY));
               ChatHudLine chathudline = new ChatHudLine(chatHudLine.creationTick(), mutabletext, chatHudLine.signature(), chatHudLine.indicator());
               listx.addFirst(chathudline);
               onChatHud(chatHud);
               return;
            }
         }

         list.addFirst(new ChatEntry(s, chatHudLine.creationTick()));

         while (list.size() > 100) {
            list.removeLast();
         }
      }

      listx.addFirst(chatHudLine);
      if (!flagx) {
         while (listx.size() > 100) {
            listx.removeLast();
         }
      }
   }

   public static Text getTextByChatHudDoubleDouble(ChatHud chatHud, double value, double value2) {
      List listx = (List)ReflectionCache.getObjectByObjectLong(chatHud, time3);
      List<ChatHudLine> list1 = (List)ReflectionCache.getObjectByObjectLong(chatHud, time2);
      int i = getIntByChatHudDoubleDouble(chatHud, value, value2);
      if (i >= 0 && i < listx.size()) {
         Visible visible = (Visible)listx.get(i);
         int j = visible.addedTime();

         for (ChatHudLine chathudline : list1) {
            if (chathudline.creationTick() == j) {
               return chathudline.content();
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static void onChatHud(ChatHud chatHud) {
      MinecraftClient minecraftclient = (MinecraftClient)ReflectionCache.getObjectByObjectLong(chatHud, time);
      List listx = (List)ReflectionCache.getObjectByObjectLong(chatHud, time3);
      List list1 = (List)ReflectionCache.getObjectByObjectLong(chatHud, time2);
      listx.clear();
      if (minecraftclient.textRenderer != null) {
         for (ChatHudLine chathudline : (Iterable<ChatHudLine>)(Lists.reverse(list1))) {
            int i = MathHelper.floor(chatHud.getWidth() / chatHud.getChatScale());
            Icon icon = chathudline.getIcon();
            if (icon != null) {
               i -= icon.width + 4 + 2;
            }

            List list2 = ChatMessages.breakRenderedChatMessageLines(chathudline.content(), i, minecraftclient.textRenderer);
            boolean flagx = chatHud.isChatFocused();

            for (int j = 0; j < list2.size(); j++) {
               OrderedText orderedtext = (OrderedText)list2.get(j);
               if (flagx && UnsafeAccess.unsafe.getInt(chatHud, time4) > 0) {
                  UnsafeAccess.unsafe.putBoolean(chatHud, time5, true);
                  chatHud.scroll(1);
               }

               boolean flag1 = j == list2.size() - 1;
               listx.addFirst(new Visible(chathudline.creationTick(), orderedtext, chathudline.indicator(), flag1));
            }
         }
      }
   }

   private static boolean check() {
      Enhancer enhancer = (Enhancer)unsafeAccess.getModule2();
      return enhancer != null && enhancer.check8();
   }

   private static int getIntByChatHudDoubleDouble(ChatHud chatHud, double value, double value2) {
      MinecraftClient minecraftclient = (MinecraftClient)ReflectionCache.getObjectByObjectLong(chatHud, time);
      List listx = (List)ReflectionCache.getObjectByObjectLong(chatHud, time3);
      double d0 = value / chatHud.getChatScale() - 4.0;
      int i = (int)(9.0 * ((Double)minecraftclient.options.getChatLineSpacing().getValue() + 1.0));
      double d1 = (minecraftclient.getWindow().getScaledHeight() - value2 - 40.0) / (chatHud.getChatScale() * i);
      return getIntByChatHudListDoubleDoubleMinecraftClientInt(chatHud, listx, d0, d1, minecraftclient, i);
   }

   private static int getIntByChatHudListDoubleDoubleMinecraftClientInt(ChatHud chatHud, List list, double value, double value2, MinecraftClient minecraftClient, int count) {
      if (!chatHud.isChatFocused() || minecraftClient.options.getChatVisibility().getValue() == ChatVisibility.HIDDEN) {
         return -1;
      } else if (!(value < -4.0) && !(value > MathHelper.floor(chatHud.getWidth() / chatHud.getChatScale()))) {
         int i = Math.min(chatHud.getVisibleLineCount(), list.size());
         if (value2 >= 0.0 && value2 < i) {
            int j = MathHelper.floor(value2 + UnsafeAccess.unsafe.getInt(chatHud, time4));
            if (j >= 0 && j < list.size()) {
               return j;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   @Hook(
      method = "method_1815",
      desc = "(Lnet/minecraft/class_303;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onChatHudChatHudLine2(ChatHud chatHud, ChatHudLine chatHudLine) {
      if (Feature.mc.textRenderer != null) {
         List listx = (List)ReflectionCache.getObjectByObjectLong(chatHud, time3);
         int i = MathHelper.floor(chatHud.getWidth() / chatHud.getChatScale());
         Icon icon = chatHudLine.getIcon();
         if (icon != null) {
            i -= icon.width + 4 + 2;
         }

         List list1 = ChatMessages.breakRenderedChatMessageLines(chatHudLine.content(), i, Feature.mc.textRenderer);
         boolean flagx = chatHud.isChatFocused();

         for (int j = 0; j < list1.size(); j++) {
            OrderedText orderedtext = (OrderedText)list1.get(j);
            if (flagx && UnsafeAccess.unsafe.getInt(chatHud, time4) > 0) {
               UnsafeAccess.unsafe.putBoolean(chatHud, time5, true);
               chatHud.scroll(1);
            }

            boolean flag1 = j == list1.size() - 1;
            listx.addFirst(new Visible(chatHudLine.creationTick(), orderedtext, chatHudLine.indicator(), flag1));
         }

         if (!check()) {
            while (listx.size() > 100) {
               listx.removeLast();
            }
         }
      }
   }

   @Hook(
      method = "method_45027",
      desc = "(Lnet/minecraft/class_303;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isChatHudChatHudLine(ChatHud chatHud, ChatHudLine chatHudLine) {
      if (flag) {
         flag = false;
         return false;
      } else {
         return true;
      }
   }

   @Hook(
      method = "method_1808",
      desc = "(Z)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onChatHudBoolean(ChatHud chatHud, boolean flag) {
      Enhancer enhancer = (Enhancer)unsafeAccess.getModule2();
      if (enhancer != null && enhancer.check4()) {
         Feature.mc.getMessageHandler().processAll();
      } else {
         HandleInvoker.onObjectArray(chatHud, flag);
         list.clear();
      }
   }

   @Hook(
      method = "method_1805",
      desc = "(Lnet/minecraft/class_332;IIIZ)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isChatHudDrawContextIntIntIntBoolean(ChatHud chatHud, DrawContext drawContext, int count, int count2, int count3, boolean flag) {
      return Feature.mc.textRenderer != null;
   }

   @Hook(
      method = "method_1803",
      desc = "(Ljava/lang/String;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onChatHudString(ChatHud chatHud, String text2) {
      if (!text2.equals(chatHud.getMessageHistory().peekLast())) {
         if (!check() && chatHud.getMessageHistory().size() >= 100) {
            chatHud.getMessageHistory().removeFirst();
         }

         chatHud.getMessageHistory().addLast(text2);
      }

      if (text2.startsWith("/")) {
         Feature.mc.getCommandHistoryManager().add(text2);
      }
   }

   public static void setFlag() {
      flag = true;
   }

   public static String getStringByChatHudDoubleDouble(ChatHud chatHud, double value, double value2) {
      List listx = (List)ReflectionCache.getObjectByObjectLong(chatHud, time3);
      int i = getIntByChatHudDoubleDouble(chatHud, value, value2);
      if (i >= 0 && i < listx.size()) {
         StringBuilder stringbuilder = new StringBuilder();
         ((Visible)listx.get(i)).content().accept((item, item2, item3) -> {
            stringbuilder.appendCodePoint(item3);
            return true;
         });
         return stringbuilder.toString();
      } else {
         return null;
      }
   }
}
