package client.setting;

import client.data.ToggleValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BlocklistSetting extends Setting {
   private final Map<Block, ToggleValue> map = new ConcurrentHashMap<>();
   private final Map<Block, ToggleValue> map2 = new HashMap<>();

   public BlocklistSetting(String text, String text2) {
      super(text, text2);
   }

   @Override
   public String getTypeId() {
      return "blocklist";
   }

   @Override
   public void reset() {
      this.map.clear();

      for (Entry entry : this.map2.entrySet()) {
         this.map.put((Block)entry.getKey(), new ToggleValue(((ToggleValue)entry.getValue()).isFlag(), ((ToggleValue)entry.getValue()).getValue()));
      }

      this.fireOnChange();
   }

   public void onIntBlock(int count, Block block2) {
      ToggleValue togglevalue = this.map.get(block2);
      if (togglevalue != null) {
         togglevalue.setValue(count);
         this.fireOnChange();
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      JsonArray jsonarray = new JsonArray();

      for (Entry entry : this.map.entrySet()) {
         JsonObject jsonobject1 = new JsonObject();
         Identifier identifier = Registries.BLOCK.getId((Block)entry.getKey());
         jsonobject1.addProperty("id", identifier.toString());
         jsonobject1.addProperty("enabled", ((ToggleValue)entry.getValue()).isFlag());
         int i = ((ToggleValue)entry.getValue()).getValue();
         jsonobject1.addProperty("r", i >> 16 & 0xFF);
         jsonobject1.addProperty("g", i >> 8 & 0xFF);
         jsonobject1.addProperty("b", i & 0xFF);
         jsonobject1.addProperty("a", i >> 24 & 0xFF);
         jsonarray.add(jsonobject1);
      }

      jsonobject.add("blocks", jsonarray);
      return jsonobject;
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("blocks")) {
         this.map.clear();
         JsonArray jsonarray = jsonObject.getAsJsonArray("blocks");

         for (int i = 0; i < jsonarray.size(); i++) {
            JsonObject jsonobject = jsonarray.get(i).getAsJsonObject();
            String s = jsonobject.get("id").getAsString();
            Identifier identifier = Identifier.of(s);
            Block block = (Block)Registries.BLOCK.get(identifier);
            if (block != null) {
               boolean flag = jsonobject.get("enabled").getAsBoolean();
               int j = jsonobject.get("r").getAsInt();
               int k = jsonobject.get("g").getAsInt();
               int l = jsonobject.get("b").getAsInt();
               int i1 = jsonobject.get("a").getAsInt();
               int j1 = i1 << 24 | j << 16 | k << 8 | l;
               this.map.put(block, new ToggleValue(flag, j1));
            }
         }

         this.fireOnChange();
      }
   }

   public Map<Block, ToggleValue> getMap() {
      return this.map;
   }

   public void onBlockBooleanInt(Block block2, boolean flag, int count) {
      ToggleValue togglevalue = new ToggleValue(flag, count);
      this.map2.put(block2, togglevalue);
      this.map.put(block2, new ToggleValue(flag, count));
   }

   public void onBooleanBlockInt(boolean flag, Block block2, int count) {
      this.map.put(block2, new ToggleValue(flag, count));
      this.fireOnChange();
   }

   public void removeBlock(Block block2) {
      this.map.remove(block2);
      this.fireOnChange();
   }

   public boolean isBlock(Block block2) {
      ToggleValue togglevalue = this.map.get(block2);
      return togglevalue != null && togglevalue.isFlag();
   }

   public int getIntByBlock(Block block2) {
      ToggleValue togglevalue = this.map.get(block2);
      return togglevalue != null ? togglevalue.getValue() : -1;
   }

   public void onBlockBoolean(Block block2, boolean flag) {
      ToggleValue togglevalue = this.map.get(block2);
      if (togglevalue != null) {
         togglevalue.setFlag(flag);
         this.fireOnChange();
      }
   }
}
