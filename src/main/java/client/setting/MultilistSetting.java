package client.setting;

import client.util.StringParts;
import client.util.TextHash;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MultilistSetting extends Setting {
   private String[][] stringArrayArray;
   private String[][] stringArrayArray2;
   private final Set<Integer> set = new LinkedHashSet<>();
   private final Set<Integer> set2 = new LinkedHashSet<>();
   private boolean flag;

   public MultilistSetting(String text, String text2, List<String> list, List<String> list2) {
      this(text, text2, list, list2, null, false);
   }

   public MultilistSetting(String text, String text2, List<String> list, List<String> list2, List list3, boolean flag2) {
      super(text, text2);
      if (list != null && !list.isEmpty()) {
         this.onListList(list, list3);
         if (list2 != null) {
            for (String s : list2) {
               int i = this.getIntByString(s);
               if (i >= 0) {
                  this.set2.add(i);
               }
            }
         }

         this.set.addAll(this.set2);
         this.flag = flag2;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public String getTypeId() {
      return "multilist";
   }

   public List<String> getList() {
      ArrayList<String> arraylist = new ArrayList<>();

      for (int i : this.set2) {
         arraylist.add(this.getStringByInt(i));
      }

      return arraylist;
   }

   public Set getSet() {
      return new LinkedHashSet(this.getList4());
   }

   public boolean isString(String text) {
      return this.isString2(text);
   }

   public void onList(List list) {
      if (list != null && !list.isEmpty()) {
         this.onListList(list, null);
         this.set.removeIf(this::isInteger);
      }
   }

   public int getInt() {
      return this.set.size();
   }

   public boolean isFlag() {
      return this.flag;
   }

   @Override
   public void reset() {
      LinkedHashSet linkedhashset = new LinkedHashSet<>(this.set);
      this.set.clear();
      this.set.addAll(this.set2);
      if (!linkedhashset.equals(this.set)) {
         this.fireOnChange();
      }
   }

   public void onList2(List list) {
      if (list != null) {
         for (int i = 0; i < this.stringArrayArray2.length && i < list.size(); i++) {
            this.stringArrayArray2[i] = StringParts.split((String)list.get(i));
         }
      }
   }

   public List getList2() {
      ArrayList arraylist = new ArrayList();

      for (int i = 0; i < this.stringArrayArray.length; i++) {
         arraylist.add(this.getStringByInt(i));
      }

      return arraylist;
   }

   public List getList3() {
      ArrayList arraylist = new ArrayList();

      for (String[] astring : this.stringArrayArray2) {
         arraylist.add(StringParts.join(astring));
      }

      return arraylist;
   }

   public String getString() {
      if (this.set.isEmpty()) {
         return "None";
      } else {
         List list = this.getList4();
         return list.size() == 1 ? (String)list.getFirst() : (String)list.getFirst() + "...";
      }
   }

   public int getStringArrayArrayAsInt() {
      return this.stringArrayArray.length;
   }

   public boolean check() {
      return this.set.equals(this.set2);
   }

   public String[] getStringArrayByInt(int count) {
      return count >= 0 && count < this.stringArrayArray2.length ? this.stringArrayArray2[count] : new String[0];
   }

   private boolean isInteger(Integer value) {
      return value >= this.stringArrayArray.length;
   }

   private void onListList(List list, List list2) {
      int i = list.size();
      this.stringArrayArray = new String[i][];
      this.stringArrayArray2 = new String[i][];

      for (int j = 0; j < i; j++) {
         this.stringArrayArray[j] = StringParts.split((String)list.get(j));
         String s = list2 != null && j < list2.size() ? (String)list2.get(j) : (String)list.get(j);
         this.stringArrayArray2[j] = StringParts.split(s);
      }
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      LinkedHashSet linkedhashset = new LinkedHashSet<>(this.set);
      if (jsonObject.has("selectedIndices") && jsonObject.get("selectedIndices").isJsonArray()) {
         this.set.clear();

         for (JsonElement jsonelement1 : jsonObject.getAsJsonArray("selectedIndices")) {
            int j = jsonelement1.getAsInt();
            if (j >= 0 && j < this.stringArrayArray.length) {
               this.set.add(j);
            }
         }
      } else if (jsonObject.has("selectedValues") && jsonObject.get("selectedValues").isJsonArray()) {
         TextHash.setFlag();
         this.set.clear();

         for (JsonElement jsonelement : jsonObject.getAsJsonArray("selectedValues")) {
            int i = this.getIntByString(jsonelement.getAsString());
            if (i >= 0) {
               this.set.add(i);
            }
         }
      }

      if (jsonObject.has("visible")) {
         this.visible = jsonObject.get("visible").getAsBoolean();
      }

      if (!linkedhashset.equals(this.set)) {
         this.fireOnChange();
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("selectedCount", this.getInt());
      jsonobject.addProperty("isAtDefault", this.check());
      jsonobject.addProperty("visible", this.visible);
      JsonArray jsonarray = new JsonArray();

      for (int i : this.set) {
         jsonarray.add(i);
      }

      jsonobject.add("selectedIndices", jsonarray);
      return jsonobject;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public void onIntArrayStringArrayArrayStringArrayArray(int[] countArray, String[][] textArray, String[][] textArray2) {
      this.stringArrayArray = textArray;
      this.stringArrayArray2 = textArray2 != null && textArray2.length == textArray.length ? textArray2 : textArray;
      this.set2.clear();
      this.set.clear();
      if (countArray != null) {
         for (int i : countArray) {
            if (i >= 0 && i < textArray.length) {
               this.set2.add(i);
            }
         }
      }

      this.set.addAll(this.set2);
   }

   public boolean isString2(String text) {
      int i = this.getIntByString(text);
      return i >= 0 && this.set.contains(i);
   }

   public void onString(String text) {
      int i = this.getIntByString(text);
      if (i >= 0) {
         this.addInt(i);
      }
   }

   public void addList(List<String> list) {
      LinkedHashSet linkedhashset = new LinkedHashSet<>(this.set);
      this.set.clear();
      if (list != null) {
         for (String s : list) {
            int i = this.getIntByString(s);
            if (i >= 0) {
               this.set.add(i);
            }
         }
      }

      if (!linkedhashset.equals(this.set)) {
         this.fireOnChange();
      }
   }

   public String getStringByString(String text) {
      int i = this.getIntByString(text);
      return i < 0 ? text : StringParts.join(this.stringArrayArray2[i]);
   }

   private int getIntByString(String text) {
      if (text == null) {
         return -1;
      } else {
         for (int i = 0; i < this.stringArrayArray.length; i++) {
            String s = this.getStringByInt(i);
            if (TextHash.isStringString(text, s)) {
               return i;
            }
         }

         return -1;
      }
   }

   private String getStringByInt(int count) {
      return StringParts.join(this.stringArrayArray[count]);
   }

   public Set<Integer> getSet2() {
      return new LinkedHashSet<>(this.set);
   }

   public boolean isInt(int count) {
      return this.set.contains(count);
   }

   public List getList4() {
      ArrayList arraylist = new ArrayList();

      for (int i = 0; i < this.stringArrayArray.length; i++) {
         if (this.set.contains(i)) {
            arraylist.add(this.getStringByInt(i));
         }
      }

      return arraylist;
   }

   public void addInt(int count) {
      if (count >= 0 && count < this.stringArrayArray.length) {
         if (!this.set.remove(count)) {
            this.set.add(count);
         }

         this.fireOnChange();
      }
   }

   public void onBooleanInt(boolean flag, int count) {
      if (count >= 0 && count < this.stringArrayArray.length) {
         boolean flagx = flag ? this.set.add(count) : this.set.remove(count);
         if (flagx) {
            this.fireOnChange();
         }
      }
   }
}
