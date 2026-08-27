package client.setting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StafflistSetting extends Setting {
   private final Set<String> set = Collections.synchronizedSet(new LinkedHashSet<>());

   public StafflistSetting(String text, String text2) {
      super(text, text2);
   }

   @Override
   public String getTypeId() {
      return "stafflist";
   }

   public boolean isString(String text) {
      String s = getStringByString(text);
      if (s == null) {
         return false;
      } else {
         boolean flag;
         synchronized (this.set) {
            flag = this.set.remove(s);
         }

         if (flag) {
            this.fireOnChange();
         }

         return flag;
      }
   }

   public boolean isString2(String text) {
      String s = getStringByString(text);
      if (s == null) {
         return false;
      } else {
         synchronized (this.set) {
            return this.set.contains(s);
         }
      }
   }

   @Override
   public void reset() {
      boolean flag;
      synchronized (this.set) {
         flag = !this.set.isEmpty();
         this.set.clear();
      }

      if (flag) {
         this.fireOnChange();
      }
   }

   public List<String> getList() {
      synchronized (this.set) {
         return List.copyOf(this.set);
      }
   }

   public boolean isString3(String text) {
      String s = getStringByString(text);
      if (s == null) {
         return false;
      } else {
         boolean flag;
         synchronized (this.set) {
            flag = this.set.add(s);
         }

         if (flag) {
            this.fireOnChange();
         }

         return flag;
      }
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("nicks")) {
         JsonArray jsonarray = jsonObject.getAsJsonArray("nicks");
         synchronized (this.set) {
            this.set.clear();

            for (int i = 0; i < jsonarray.size(); i++) {
               String s = jsonarray.get(i).getAsString();
               String s1 = getStringByString(s);
               if (s1 != null) {
                  this.set.add(s1);
               }
            }
         }

         this.fireOnChange();
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      JsonArray jsonarray = new JsonArray();
      synchronized (this.set) {
         for (String s : this.set) {
            jsonarray.add(s);
         }
      }

      jsonobject.add("nicks", jsonarray);
      return jsonobject;
   }

   private static String getStringByString(String text) {
      if (text == null) {
         return null;
      } else {
         String s = text.trim().toLowerCase();
         if (s.isEmpty()) {
            return null;
         } else {
            return !s.matches("^\\w{3,16}$") ? null : s;
         }
      }
   }

   public int getIntByIterable(Iterable<String> iterable) {
      int i = 0;
      synchronized (this.set) {
         for (String s : iterable) {
            String s1 = getStringByString(s);
            if (s1 != null && this.set.add(s1)) {
               i++;
            }
         }
      }

      if (i > 0) {
         this.fireOnChange();
      }

      return i;
   }
}
