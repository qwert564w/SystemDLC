package client.util;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public final class ServerInfo {
   private static boolean flag;
   private static boolean flag2;
   public static String text;
   public static int value;
   public static int value2;
   public static int value3;
   public static String text2;
   public static String text3;
   public static String text4;
   public static String text5;
   public static int value4;
   public static int value5;
   public static int value6;
   private static final Map<String, String> map = new HashMap<>();
   private static final Map<String, String> map2 = new HashMap<>();
   private static final Map<String, String> map3 = new HashMap<>();
   private static final Map<String, String> map4 = new HashMap<>();
   private static final Map<String, String> map5 = new HashMap<>();
   private static final Map<String, String> map6 = new HashMap<>();
   private static final String namespace = "intermediary";

   private ServerInfo() {
   }

   private static String getStringByString(String text) {
      return null;
   }

   private static MappingResolver getMappingResolver() {
      try {
         return FabricLoader.getInstance().getMappingResolver();
      } catch (Throwable throwable) {
         return null;
      }
   }

   /**
    * Intermediary name of a runtime class, given its internal name. Hooks are declared against
    * intermediary, so the owner has to be translated back before a member can be looked up.
    */
   public static String getStringByString2(String text) {
      if (text == null) {
         return null;
      }

      MappingResolver mappingresolver = getMappingResolver();
      if (mappingresolver == null) {
         return null;
      }

      return map.computeIfAbsent(text, var1 -> {
         try {
            return mappingresolver.unmapClassName(namespace, var1.replace('/', '.'));
         } catch (Throwable throwable) {
            return var1.replace('/', '.');
         }
      });
   }

   /** Translates an intermediary method descriptor into the namespace the game is running in. */
   public static String getStringByString3(String text) {
      if (text == null) {
         return null;
      }

      MappingResolver mappingresolver = getMappingResolver();
      if (mappingresolver == null) {
         return null;
      }

      return map2.computeIfAbsent(text, var1 -> {
         StringBuilder stringbuilder = new StringBuilder(var1.length());
         int i = 0;

         while (i < var1.length()) {
            char c0 = var1.charAt(i);
            if (c0 != 'L') {
               stringbuilder.append(c0);
               i++;
            } else {
               int j = var1.indexOf(59, i);
               if (j < 0) {
                  stringbuilder.append(var1.substring(i));
                  break;
               }

               String s = var1.substring(i + 1, j).replace('/', '.');

               String s1;
               try {
                  s1 = mappingresolver.mapClassName(namespace, s);
               } catch (Throwable throwable) {
                  s1 = s;
               }

               stringbuilder.append('L').append(s1.replace('.', '/')).append(';');
               i = j + 1;
            }
         }

         return stringbuilder.toString();
      });
   }

   private static String getStringByString4(String text) {
      return null;
   }

   private static void update() throws Exception {
   }

   public static String getStringByString5(String text) {
      return null;
   }

   public static boolean check() {
      return getMappingResolver() != null;
   }

   public static String getStringByStringStringString(String text, String text2, String text3) {
      return null;
   }

   /**
    * Runtime name of an intermediary method. Returns the input unchanged when no mapping exists,
    * which is how the caller detects a failed lookup.
    */
   public static String methodNamed(String text, String text2, String text3) {
      if (text == null || text2 == null) {
         return text2;
      }

      MappingResolver mappingresolver = getMappingResolver();
      if (mappingresolver == null) {
         return text2;
      }

      String s = text + "." + text2 + text3;
      return map3.computeIfAbsent(s, var4 -> {
         try {
            return mappingresolver.mapMethodName(namespace, text, text2, text3);
         } catch (Throwable throwable) {
            return text2;
         }
      });
   }
}
