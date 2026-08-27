package client.data;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL20C;

public final class ValueRegistry {
   private final int value;
   private final Map<String, Integer> map = new HashMap<>();

   public ValueRegistry(int count) {
      this.value = count;
   }

   private int getIntByString(String text) {
      Integer integer = this.map.get(text);
      if (integer != null) {
         return integer;
      } else {
         int i = GL20C.glGetUniformLocation(this.value, text);
         this.map.put(text, i);
         return i;
      }
   }

   public void onStringFloat(String text, float value) {
      int i = this.getIntByString(text);
      if (i >= 0) {
         GL20C.glUniform1f(i, value);
      }
   }

   public void onStringIntArray(String text, int[] countArray) {
      int i = this.getIntByString(text + "[0]");
      if (i >= 0) {
         GL20C.glUniform1iv(i, countArray);
      }
   }

   public void onIntString(int count, String text) {
      int i = this.getIntByString(text);
      if (i >= 0) {
         GL20C.glUniform1i(i, count);
      }
   }
}
