package client.util;

import client.enums.VoiceIcon;
import java.lang.reflect.Method;
import java.util.UUID;

public final class VoicechatBridge {
   private static final boolean flag;
   private static final Method method;
   private static final Method method2;
   private static final Method method3;
   private static final Method method4;
   private static final Method method5;
   private static final Method method6;
   private static final Method method7;
   private static final Method method8;
   private static final Method method9;

   private VoicechatBridge() {
   }

   static {
      boolean flagx = false;
      Method methodx = null;
      Method method1 = null;
      Method method2x = null;
      Method method3x = null;
      Method method4x = null;
      Method method5x = null;
      Method method6x = null;
      Method method7x = null;
      Method method8x = null;

      try {
         Class oclass = Class.forName("de.maxhenkel.voicechat.voice.client.ClientManager");
         Class oclass1 = Class.forName("de.maxhenkel.voicechat.voice.client.ClientVoicechat");
         Class oclass2 = Class.forName("de.maxhenkel.voicechat.voice.client.TalkCache");
         Class oclass3 = Class.forName("de.maxhenkel.voicechat.voice.client.ClientPlayerStateManager");
         methodx = oclass.getMethod("getClient");
         method1 = oclass.getMethod("getPlayerStateManager");
         method2x = oclass1.getMethod("getTalkCache");
         method3x = oclass2.getMethod("isTalking", UUID.class);
         method4x = oclass2.getMethod("isWhispering", UUID.class);
         method5x = oclass3.getMethod("isPlayerDisconnected", UUID.class);
         method6x = oclass3.getMethod("isPlayerDisabled", UUID.class);
         method7x = oclass3.getMethod("getGroup", UUID.class);
         method8x = oclass3.getMethod("getGroupID");
         flagx = true;
      } catch (Throwable throwable) {
      }

      flag = flagx;
      method = methodx;
      method2 = method1;
      method3 = method2x;
      method4 = method3x;
      method5 = method4x;
      method6 = method5x;
      method7 = method6x;
      method8 = method7x;
      method9 = method8x;
   }

   public static boolean isFlag() {
      return flag;
   }

   public static VoiceIcon getVoiceIconByUUID(UUID uUID) {
      if (flag && uUID != null) {
         try {
            Object object = method2.invoke(null);
            Object object1 = method.invoke(null);
            Object object2 = object1 != null ? method3.invoke(object1) : null;
            if (object2 != null && (Boolean)method5.invoke(object2, uUID)) {
               return VoiceIcon.WHISPER;
            }

            if (object2 != null && (Boolean)method4.invoke(object2, uUID)) {
               return VoiceIcon.SPEAKER;
            }

            if (object != null && (Boolean)method6.invoke(object, uUID)) {
               return VoiceIcon.DISCONNECT;
            }

            if (object != null) {
               Object object3 = method8.invoke(object, uUID);
               Object object4 = method9.invoke(object);
               if (object3 != null && !object3.equals(object4)) {
                  return VoiceIcon.GROUP;
               }

               if ((Boolean)method7.invoke(object, uUID)) {
                  return VoiceIcon.DISABLED;
               }
            }
         } catch (Throwable throwable) {
         }

         return VoiceIcon.NONE;
      } else {
         return VoiceIcon.NONE;
      }
   }
}
