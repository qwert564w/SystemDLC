package client.network;

import java.util.HashMap;
import java.util.Map;

public class AuthResult {
   private final boolean flag;
   private final String text;
   private final String text2;
   private final Map<String, Object> map;

   public AuthResult(boolean flag, String text, String text2) {
      this(flag, text, text2, null);
   }

   public AuthResult(boolean flag2, String text3, String text4, Map map2) {
      this.flag = flag2;
      this.text = text3;
      this.text2 = text4;
      this.map = (Map<String, Object>)(map2 != null ? map2 : new HashMap<>());
   }

   public String getText2() {
      return this.text2;
   }

   public boolean check() {
      return "AUTHORIZED".equals(this.text2);
   }

   public Object getObjectByString(String text) {
      return this.map.get(text);
   }

   public boolean isFlag() {
      return this.flag;
   }

   public Map<String, Object> getMap() {
      return this.map;
   }

   public String getText() {
      return this.text;
   }
}
