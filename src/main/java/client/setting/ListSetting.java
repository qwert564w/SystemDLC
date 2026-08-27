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

public class ListSetting extends Setting {
   private String[][] stringArrayArray;
   private final Set<Integer> set = new LinkedHashSet<>();
   private final Set<Integer> set2 = new LinkedHashSet<>();
   private final boolean flag;
   private int value;
   private final int value2;

   public ListSetting(String text, String text2, List<String> list, List<String> list2, boolean flag2, int count, int count2) {
      super(text, text2);
      if (list != null && !list.isEmpty()) {
         this.setList(list);
         this.flag = flag2;
         this.value = Math.max(1, count);
         this.value2 = Math.max(0, count2);
         if (list2 != null) {
            for (String s : list2) {
               int i = this.getIntByString(s);
               if (i >= 0) {
                  this.set2.add(i);
               }
            }
         }

         this.set.addAll(this.set2);
         this.update();
         this.set2.clear();
         this.set2.addAll(this.set);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public ListSetting(String text, String text2, List<String> list, List list2, boolean flag) {
      this(text, text2, list, list2, flag, flag ? list.size() : 1, 0);
   }

   public ListSetting(String text, String text2, List<String> list, List list2) {
      this(text, text2, list, list2, true, list.size(), 0);
   }

   public boolean check() {
      return this.set.equals(this.set2);
   }

   public void setValue(int count) {
      this.value = count;
   }

   public int getInt() {
      return this.set.size();
   }

   private void update() {
      this.set.removeIf(this::isInteger);

      for (int i = 0; i < this.stringArrayArray.length && this.set.size() < this.value2; i++) {
         this.set.add(i);
      }

      while (this.set.size() > this.value) {
         int k = -1;

         for (int j : this.set) {
            k = j;
         }

         this.set.remove(k);
      }

      if (!this.flag && this.set.size() > 1) {
         int l = this.getInt2();
         this.set.clear();
         if (l >= 0) {
            this.set.add(l);
         }
      }
   }

   @Override
   public String getTypeId() {
      return "list";
   }

   public List<String> getList() {
      ArrayList<String> arraylist = new ArrayList<>();

      for (int i : this.set2) {
         arraylist.add(this.getStringByInt(i));
      }

      return arraylist;
   }

   public boolean check2() {
      return this.set.size() >= this.value;
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

      this.update();
      if (!linkedhashset.equals(this.set)) {
         this.fireOnChange();
      }
   }

   public boolean isString(String text) {
      return this.isString2(text);
   }

   public void onString(String text) {
      int i = this.getIntByString(text);
      if (i >= 0) {
         this.removeInt(i);
      }
   }

   public void update2() {
      if (this.flag) {
         LinkedHashSet linkedhashset = new LinkedHashSet<>(this.set);
         this.set.clear();
         int i = Math.min(this.stringArrayArray.length, this.value);

         for (int j = 0; j < i; j++) {
            this.set.add(j);
         }

         if (!linkedhashset.equals(this.set)) {
            this.fireOnChange();
         }
      }
   }

   public String getString() {
      if (this.set.isEmpty()) {
         return "None";
      } else {
         return this.set.size() == 1 ? this.getStringByInt(this.getInt2()) : String.join(", ", this.getList3());
      }
   }

   public int getInt2() {
      for (int i = 0; i < this.stringArrayArray.length; i++) {
         if (this.set.contains(i)) {
            return i;
         }
      }

      return -1;
   }

   public void onInt(int count) {
      if (count >= 0 && count < this.stringArrayArray.length) {
         if (this.set.contains(count)) {
            this.removeInt(count);
         } else {
            this.addInt(count);
         }
      }
   }

   public void onString2(String text) {
      int i = this.getIntByString(text);
      if (i >= 0) {
         this.onInt(i);
      }
   }

   public void addList2(List list2) {
      if (list2 != null && !list2.isEmpty()) {
         List<String> list = this.getList3();
         this.setList(list2);
         this.value = this.flag ? list2.size() : 1;
         this.set.clear();

         for (String s : list) {
            int i = this.getIntByString(s);
            if (i >= 0) {
               this.set.add(i);
            }
         }

         if (this.set.isEmpty() && this.stringArrayArray.length > 0) {
            this.set.add(0);
         }

         this.update();
         this.fireOnChange();
      }
   }

   public List getList2() {
      ArrayList arraylist = new ArrayList();

      for (int i = 0; i < this.stringArrayArray.length; i++) {
         arraylist.add(this.getStringByInt(i));
      }

      return arraylist;
   }

   public int getValue() {
      return this.value;
   }

   public boolean check3() {
      return this.set.size() <= this.value2;
   }

   private void addInt(int count) {
      boolean flagx = false;
      if (!this.flag) {
         if (this.set.size() != 1 || !this.set.contains(count)) {
            this.set.clear();
            this.set.add(count);
            flagx = true;
         }
      } else if (!this.set.contains(count) && this.set.size() < this.value) {
         this.set.add(count);
         flagx = true;
      }

      if (flagx) {
         this.fireOnChange();
      }
   }

   @Override
   public void reset() {
      this.addList(this.getList());
   }

   public void addList3(List list2) {
      if (list2 != null && !list2.isEmpty()) {
         List<String> list = this.getList3();
         this.setList(list2);
         this.set.clear();

         for (String s : list) {
            int i = this.getIntByString(s);
            if (i >= 0) {
               this.set.add(i);
            }
         }

         this.update();
      }
   }

   public int getValue2() {
      return this.value2;
   }

   private void removeInt(int count) {
      if (this.set.size() > this.value2 && this.set.remove(count)) {
         this.fireOnChange();
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("allowMultiple", this.flag);
      jsonobject.addProperty("maxSelections", this.value);
      jsonobject.addProperty("minSelections", this.value2);
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

   private boolean isInteger(Integer value) {
      return value < 0 || value >= this.stringArrayArray.length;
   }

   public boolean isFlag() {
      return this.flag;
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

         this.update();
      } else if (jsonObject.has("selectedValues") && jsonObject.get("selectedValues").isJsonArray()) {
         TextHash.setFlag();
         this.set.clear();

         for (JsonElement jsonelement : jsonObject.getAsJsonArray("selectedValues")) {
            int i = this.getIntByString(jsonelement.getAsString());
            if (i >= 0) {
               this.set.add(i);
            }
         }

         this.update();
      }

      if (jsonObject.has("visible")) {
         this.visible = jsonObject.get("visible").getAsBoolean();
      }

      if (!linkedhashset.equals(this.set)) {
         this.fireOnChange();
      }
   }

   public String[] getStringArrayByInt(int count) {
      return count >= 0 && count < this.stringArrayArray.length ? this.stringArrayArray[count] : new String[0];
   }

   public boolean isInt(int count) {
      return this.set.contains(count);
   }

   public void onInt2(int count) {
      if (count >= 0 && count < this.stringArrayArray.length) {
         this.addInt(count);
      }
   }

   private String getStringByInt(int count) {
      return StringParts.join(this.stringArrayArray[count]);
   }

   public int getStringArrayArrayAsInt() {
      return this.stringArrayArray.length;
   }

   public void onStringArrayArrayIntArray(String[][] textArray, int[] countArray) {
      this.stringArrayArray = textArray;
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
      this.update();
   }

   private void setList(List list) {
      this.stringArrayArray = new String[list.size()][];

      for (int i = 0; i < list.size(); i++) {
         this.stringArrayArray[i] = StringParts.split((String)list.get(i));
      }
   }

   public void onString3(String text) {
      int i = this.getIntByString(text);
      if (i >= 0) {
         this.addInt(i);
      }
   }

   public Set getSet() {
      return new LinkedHashSet(this.getList3());
   }

   public void update3() {
      if (this.value2 == 0 && !this.set.isEmpty()) {
         this.set.clear();
         this.fireOnChange();
      }
   }

   public List getList3() {
      ArrayList arraylist = new ArrayList();

      for (int i = 0; i < this.stringArrayArray.length; i++) {
         if (this.set.contains(i)) {
            arraylist.add(this.getStringByInt(i));
         }
      }

      return arraylist;
   }

   public boolean isString2(String text) {
      int i = this.getIntByString(text);
      return i >= 0 && this.set.contains(i);
   }

   public String getString2() {
      int i = this.getInt2();
      return i < 0 ? null : this.getStringByInt(i);
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
}
