package client.util;

import client.concurrent.WaypointStore;
import client.module.Feature;
import client.module.client.PanicModule;
import client.module.client.StreamBypass;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public final class CoordsParser {
   private static final Pattern pattern = Pattern.compile("(-?\\d{1,7})[^\\d-]{1,6}(-?\\d{1,4})[^\\d-]{1,6}(-?\\d{1,7})");
   private static final Pattern pattern2 = Pattern.compile("§.");
   private static final int value = -128;
   private static final int value2 = 512;
   private static final int value3 = 30000000;

   private CoordsParser() {
   }

   private static BlockPos getBlockPosByDoubleDouble(double value, double value2) {
      if (PanicModule.isFlag() || StreamBypass.check7()) {
         return null;
      } else if (Feature.mc.inGameHud == null) {
         return null;
      } else {
         ChatHud chathud = Feature.mc.inGameHud.getChatHud();
         Style style = chathud.getTextStyleAt(value, value2);
         if (style != null && style.getClickEvent() != null) {
            return null;
         } else {
            String s = ChatSpamGuard.getStringByChatHudDoubleDouble(chathud, value, value2);
            BlockPos blockpos = getBlockPosByString(s);
            return blockpos != null ? blockpos : getBlockPosByString(getStringByDoubleChatHudStringDouble(value2, chathud, s, value));
         }
      }
   }

   private static BlockPos getBlockPosByString(String text2) {
      if (text2 != null && !text2.isEmpty()) {
         Matcher matcher = pattern.matcher(pattern2.matcher(text2).replaceAll(""));

         while (matcher.find()) {
            try {
               int i = Integer.parseInt(matcher.group(1));
               int j = Integer.parseInt(matcher.group(2));
               int k = Integer.parseInt(matcher.group(3));
               if (j >= -128 && j <= 512 && Math.abs(i) <= 30000000 && Math.abs(k) <= 30000000) {
                  return new BlockPos(i, j, k);
               }
            } catch (NumberFormatException numberformatexception) {
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static String getStringByDoubleChatHudStringDouble(double value, ChatHud chatHud, String text2, double value2) {
      if (text2 != null && !text2.isBlank()) {
         Text text = ChatSpamGuard.getTextByChatHudDoubleDouble(chatHud, value2, value);
         if (text == null) {
            return null;
         } else {
            String s = text.getString();
            return s.contains(text2.trim()) ? s : null;
         }
      } else {
         return null;
      }
   }

   public static void onIntDrawContextInt(int count, DrawContext drawContext, int count2) {
      BlockPos blockpos = getBlockPosByDoubleDouble(count, count2);
      if (blockpos != null && Feature.mc.textRenderer != null) {
         drawContext.drawTooltip(Feature.mc.textRenderer, Text.literal("§e+ Метка §f" + blockpos.getX() + " " + blockpos.getY() + " " + blockpos.getZ()), count, count2);
      }
   }

   public static boolean isDoubleDouble(double value, double value2) {
      BlockPos blockpos = getBlockPosByDoubleDouble(value, value2);
      if (blockpos == null) {
         return false;
      } else {
         String s = WaypointStore.getString2();
         if (s == null) {
            return false;
         } else {
            WaypointStore.getInstance()
               .getWaypointByStringIntIntIntString2("Метка " + blockpos.getX() + " " + blockpos.getZ(), blockpos.getX(), blockpos.getY(), blockpos.getZ(), s);
            return true;
         }
      }
   }
}
