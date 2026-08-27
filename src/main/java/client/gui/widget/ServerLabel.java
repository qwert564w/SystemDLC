package client.gui.widget;

import client.api.Theme;
import client.data.TextTrimmer;
import client.module.Feature;
import client.module.player.Protect;
import client.render.ShapeShader;
import client.render.TextShader;
import client.util.EasingPresets;
import client.util.UnsafeAccess;
import net.minecraft.client.network.ServerInfo;
import org.joml.Matrix4f;

public class ServerLabel extends SliderWidget {
   private static final UnsafeAccess<Protect> unsafeAccess = new UnsafeAccess<>(Protect.class);

   public ServerLabel() {
      super("", 208.0F, 32.0F);
   }

   private static String getStringByString(String text) {
      int i = text.lastIndexOf(58);
      int j = text.lastIndexOf(93);
      return i > j && i > 0 ? text.substring(0, i) : text;
   }

   private String getString() {
      if (Feature.mc == null) {
         return null;
      } else {
         ServerInfo serverinfo = Feature.mc.getCurrentServerEntry();
         if (serverinfo != null && serverinfo.address != null && !serverinfo.address.isEmpty()) {
            return getStringByString(serverinfo.address);
         } else {
            return Feature.mc.isInSingleplayer() ? "Singleplayer" : null;
         }
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      String s = this.getString2();
      String s1 = this.getString();
      float f = this.value235 + 16.0F - 8.0F;
      float f1 = this.value235;
      float f8 = this.value239;
      float f2 = EasingPresets.getFloatByFloatFloatFloat(f1, f8, f);
      float f23 = this.value236;
      int i1 = Theme.surface();
      int j1 = Theme.border();
      float f13 = 1.0F;
      int j = j1;
      int i = i1;
      float f12 = 8.0F;
      float f11 = 32.0F;
      float f10 = 32.0F;
      float f9 = f23;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f13, f10, value, matrix4f, f9, i, f12, f2, j, f11);
      String s2 = getStringByString2(s);
      if (!s2.isEmpty()) {
         float f3 = TextShader.getFloatByStringFloat(s2, 14.0F);
         float f4 = f2 + (32.0F - f3) / 2.0F;
         float f5 = this.value236 + 9.0F;
         int k = Theme.foreground();
         float f14 = 14.0F;
         TextShader.onIntFloatFloatMatrix4fFloatFloatString(k, value, f5, matrix4f, f4, f14, s2);
      }

      float f20 = f + 32.0F + 8.0F;
      float f21 = Math.max(0.0F, this.value235 + this.value237 - 8.0F - f20);
      float f22 = value * (1.0F - this.value239);
      if (!(f21 <= 0.0F) && !(f22 <= 0.001F)) {
         float f6 = this.value236 + 3.0F;
         float f7 = this.value236 + 17.0F;
         float f15 = 14.0F;
         String s3 = TextTrimmer.getStringByFloatStringFloat2(f21, s, f15);
         float f16 = 12.0F;
         String s4 = TextTrimmer.getStringByFloatStringFloat2(f21, s1, f16);
         float f18 = 36.0F;
         float f17 = this.value236;
         ScissorStack.onFloatFloatFloatFloat(f21, f18, f17, f20);
         int l = Theme.foreground();
         float f19 = 14.0F;
         TextShader.onIntFloatFloatMatrix4fFloatFloatString(l, f22, f6, matrix4f, f20, f19, s3);
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s4, f20, f7, 12.0F, Theme.foreground(), f22);
         ScissorStack.update();
      }
   }

   private String getString2() {
      if (Feature.mc != null && Feature.mc.getGameProfile() != null) {
         String s = Feature.mc.getGameProfile().getName();
         if (s != null && !s.isEmpty()) {
            Protect protect = (Protect)unsafeAccess.getModule2();
            return protect != null ? protect.getStringByString2(s) : s;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static String getStringByString2(String text) {
      if (text == null) {
         return "";
      } else {
         for (int i = 0; i < text.length(); i++) {
            char c0 = text.charAt(i);
            if (Character.isLetterOrDigit(c0)) {
               return String.valueOf(Character.toUpperCase(c0));
            }
         }

         return "";
      }
   }
}
