package client.render;

import client.concurrent.AssetLoader;
import client.module.Feature;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

public class ArrowTextureLoader {
   private static final Map<String, byte[]> map = new HashMap<>();
   public static final Identifier identifier = Identifier.of("minecraft", "textures/arrow.png");
   public static final Identifier identifier2 = Identifier.of("minecraft", "textures/arrow_3d.png");

   static {
      update();
   }

   public static byte[] getByteArray() {
      return map.get("arrow_3d");
   }

   private static void update() {
      String[] astring = AssetLoader.getStringArray();

      for (String s : astring) {
         onStringString(s, s);
      }
   }

   public static boolean isString(String text) {
      return map.containsKey(text);
   }

   public static byte[] getByteArray2() {
      return map.get("arrow");
   }

   private static void onByteArrayStringIdentifier(byte[] valueArray, String text, Identifier identifier) {
      if (isString(text) && valueArray != null && valueArray.length != 0) {
         try {
            try (ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(valueArray)) {
               BufferedImage bufferedimage = ImageIO.read(bytearrayinputstream);
               if (bufferedimage != null) {
                  int i = bufferedimage.getWidth();
                  int j = bufferedimage.getHeight();
                  NativeImage nativeimage = new NativeImage(i, j, false);

                  for (int k = 0; k < i; k++) {
                     for (int l = 0; l < j; l++) {
                        nativeimage.setColorArgb(k, l, bufferedimage.getRGB(k, l));
                     }
                  }

                  NativeImageBackedTexture nativeimagebackedtexture = new NativeImageBackedTexture(nativeimage);
                  Feature.mc.getTextureManager().registerTexture(identifier, nativeimagebackedtexture);
                  return;
               }
            }
         } catch (Exception exception) {
            System.out.println("Failed to register - " + identifier);
         }
      }
   }

   public static void update3() {
      if (Feature.mc != null) {
         Feature.mc.execute(() -> {
            Identifier identifier2x = identifier;
            byte[] abyte = getByteArray2();
            Identifier identifierx = identifier2x;
            String s = "arrow";
            onByteArrayStringIdentifier(abyte, s, identifierx);
            identifier2x = identifier2;
            byte[] abyte1 = getByteArray();
            Identifier identifier1 = identifier2x;
            String s1 = "arrow_3d";
            onByteArrayStringIdentifier(abyte1, s1, identifier1);
         });
      }
   }

   private static void onStringString(String text, String text2) {
      try {
         byte[] abyte = AssetLoader.getByteArrayByString(text);
         map.put(text2, abyte);
      } catch (Exception exception) {
      }
   }
}
