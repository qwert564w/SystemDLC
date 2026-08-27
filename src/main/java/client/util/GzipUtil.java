package client.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {
   private static String text = "GZ:";
   private static int value = 8192;

   public static String getStringByString(String text2) {
      if (text2 == null || text2.isEmpty()) {
         return text2;
      } else if (!text2.startsWith(text)) {
         return text2;
      } else {
         try {
            byte[] abyte = Base64.getDecoder().decode(text2.substring(text.length()));
            return getStringByByteArray(abyte);
         } catch (Exception exception) {
            return null;
         }
      }
   }

   private static String getStringByByteArray(byte[] valueArray) throws Exception {
      ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(valueArray);
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(valueArray.length * 3);

      try (GZIPInputStream gzipinputstream = new GZIPInputStream(bytearrayinputstream)) {
         byte[] abyte = new byte[value];

         int i;
         while ((i = gzipinputstream.read(abyte)) != -1) {
            bytearrayoutputstream.write(abyte, 0, i);
         }
      }

      return bytearrayoutputstream.toString(StandardCharsets.UTF_8);
   }

   public static String getStringByString2(String text2) {
      if (text2 != null && !text2.isEmpty()) {
         try {
            byte[] abyte = text2.getBytes(StandardCharsets.UTF_8);
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(abyte.length / 2);

            try (GZIPOutputStream gzipoutputstream = new GZIPOutputStream(bytearrayoutputstream)) {
               gzipoutputstream.write(abyte);
            }

            return text + Base64.getEncoder().encodeToString(bytearrayoutputstream.toByteArray());
         } catch (Exception exception) {
            return null;
         }
      } else {
         return text2;
      }
   }
}
