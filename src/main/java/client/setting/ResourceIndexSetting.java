package client.setting;

import client.data.ResourceEntry;
import client.data.ResourceItem;
import client.util.NamedBlockPos;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import net.minecraft.util.math.BlockPos;

public class ResourceIndexSetting extends ActionSetting {
   private final Map<String, ResourceItem> map = new ConcurrentHashMap<>();
   private final Set<String> set = ConcurrentHashMap.newKeySet();

   public ResourceIndexSetting(String text, String text2) {
      super(text, text2);
   }

   @Override
   public String getTypeId() {
      return "resource_index";
   }

   private static boolean isEntry(Entry entry) {
      return ((ResourceItem)entry.getValue()).map.isEmpty();
   }

   @Override
   public void reset() {
      this.update2();
   }

   public void onPredicateString(Predicate<BlockPos> predicate, String text2) {
      boolean flag = false;
      Iterator iterator = this.map.entrySet().iterator();

      while (iterator.hasNext()) {
         ResourceItem resourceitem = (ResourceItem)((Entry)iterator.next()).getValue();
         Iterator iterator1 = resourceitem.map.entrySet().iterator();

         while (iterator1.hasNext()) {
            NamedBlockPos namedblockpos = (NamedBlockPos)((Entry)iterator1.next()).getKey();
            if (namedblockpos.text.equals(text2) && !predicate.test(namedblockpos.blockPos)) {
               iterator1.remove();
               flag = true;
            }
         }

         if (resourceitem.map.isEmpty()) {
            iterator.remove();
            flag = true;
         }
      }

      if (flag) {
         this.fireOnChange();
      }
   }

   private static ResourceItem getResourceItemByResourceEntryString(ResourceEntry resourceEntry, String text6) {
      return new ResourceItem(resourceEntry.text, resourceEntry.text2, resourceEntry.text3, resourceEntry.text4, resourceEntry.text5);
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = super.toJson();
      JsonArray jsonarray = new JsonArray();

      for (ResourceItem resourceitem : this.map.values()) {
         JsonObject jsonobject1 = new JsonObject();
         jsonobject1.addProperty("id", resourceitem.text);
         jsonobject1.addProperty("plainId", resourceitem.text2);
         jsonobject1.addProperty("name", resourceitem.text3);
         jsonobject1.addProperty("search", resourceitem.text4);
         if (resourceitem.text5 != null) {
            jsonobject1.addProperty("snbt", resourceitem.text5);
         }

         jsonobject1.addProperty("highlighted", this.set.contains(resourceitem.text));
         JsonArray jsonarray1 = new JsonArray();

         for (Entry entry : resourceitem.map.entrySet()) {
            NamedBlockPos namedblockpos = (NamedBlockPos)entry.getKey();
            JsonObject jsonobject2 = new JsonObject();
            jsonobject2.addProperty("dim", namedblockpos.text);
            jsonobject2.addProperty("x", namedblockpos.blockPos.getX());
            jsonobject2.addProperty("y", namedblockpos.blockPos.getY());
            jsonobject2.addProperty("z", namedblockpos.blockPos.getZ());
            jsonobject2.addProperty("n", (Number)entry.getValue());
            jsonarray1.add(jsonobject2);
         }

         jsonobject1.add("locs", jsonarray1);
         jsonarray.add(jsonobject1);
      }

      jsonobject.add("items", jsonarray);
      return jsonobject;
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      super.fromJson(jsonObject);
      this.map.clear();
      this.set.clear();
      if (jsonObject.has("items")) {
         JsonArray jsonarray = jsonObject.getAsJsonArray("items");

         for (int i = 0; i < jsonarray.size(); i++) {
            JsonObject jsonobject = jsonarray.get(i).getAsJsonObject();
            String s = jsonobject.get("id").getAsString();
            String s1 = jsonobject.has("plainId") ? jsonobject.get("plainId").getAsString() : (s.contains("\u0000") ? s.substring(0, s.indexOf(0)) : s);
            String s2 = jsonobject.has("name") ? jsonobject.get("name").getAsString() : s;
            String s3 = jsonobject.has("search") ? jsonobject.get("search").getAsString() : s2.toLowerCase();
            String s4 = jsonobject.has("snbt") ? jsonobject.get("snbt").getAsString() : null;
            ResourceItem resourceitem = new ResourceItem(s, s1, s2, s3, s4);
            if (jsonobject.has("locs")) {
               JsonArray jsonarray1 = jsonobject.getAsJsonArray("locs");

               for (int j = 0; j < jsonarray1.size(); j++) {
                  JsonObject jsonobject1 = jsonarray1.get(j).getAsJsonObject();
                  NamedBlockPos namedblockpos = new NamedBlockPos(
                     jsonobject1.get("dim").getAsString(),
                     new BlockPos(jsonobject1.get("x").getAsInt(), jsonobject1.get("y").getAsInt(), jsonobject1.get("z").getAsInt())
                  );
                  int k = jsonobject1.has("n") ? jsonobject1.get("n").getAsInt() : 1;
                  resourceitem.map.put(namedblockpos, k);
               }
            }

            this.map.put(s, resourceitem);
            if (jsonobject.has("highlighted") && jsonobject.get("highlighted").getAsBoolean()) {
               this.set.add(s);
            }
         }
      }
   }

   private static boolean isEntry2(Entry entry) {
      return ((ResourceItem)entry.getValue()).map.isEmpty();
   }

   public Map<String, ResourceItem> getMap() {
      return this.map;
   }

   public void update2() {
      this.map.clear();
      this.set.clear();
      this.fireOnChange();
   }

   public void onStringBlockPos(String text, BlockPos blockPos) {
      NamedBlockPos namedblockpos = new NamedBlockPos(text, blockPos.toImmutable());
      boolean flag = false;

      for (ResourceItem resourceitem : this.map.values()) {
         if (resourceitem.map.remove(namedblockpos) != null) {
            flag = true;
         }
      }

      if (flag) {
         this.map.entrySet().removeIf(ResourceIndexSetting::isEntry2);
         this.fireOnChange();
      }
   }

   public void onStringBlockPosList(String text2, BlockPos blockPos, List<ResourceEntry> list) {
      NamedBlockPos namedblockpos = new NamedBlockPos(text2, blockPos.toImmutable());

      for (ResourceItem resourceitem : this.map.values()) {
         resourceitem.map.remove(namedblockpos);
      }

      for (ResourceEntry resourceentry : list) {
         ResourceItem resourceitem1 = this.map.computeIfAbsent(resourceentry.text, p0 -> getResourceItemByResourceEntryString(resourceentry, p0));
         resourceitem1.text3 = resourceentry.text3;
         resourceitem1.text4 = resourceentry.text4;
         if (resourceentry.text5 != null && !resourceentry.text5.isEmpty()) {
            resourceitem1.text5 = resourceentry.text5;
         }

         resourceitem1.map.merge(namedblockpos, resourceentry.value, Integer::sum);
      }

      this.map.entrySet().removeIf(ResourceIndexSetting::isEntry);
      this.fireOnChange();
   }

   public List getListByString(String text2) {
      HashSet hashset = new HashSet();

      for (String s : this.set) {
         ResourceItem resourceitem = this.map.get(s);
         if (resourceitem != null) {
            for (NamedBlockPos namedblockpos : resourceitem.map.keySet()) {
               if (namedblockpos.text.equals(text2)) {
                  hashset.add(namedblockpos.blockPos);
               }
            }
         }
      }

      return new ArrayList(hashset);
   }

   public Set<String> getSet() {
      return this.set;
   }

   public void onStringBoolean(String text, boolean flag) {
      if (flag) {
         this.set.add(text);
      } else {
         this.set.remove(text);
      }

      this.fireOnChange();
   }

   public boolean isString(String text) {
      return this.set.contains(text);
   }
}
