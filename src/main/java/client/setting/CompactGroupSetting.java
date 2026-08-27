package client.setting;

import client.util.TextHash;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;

public class CompactGroupSetting extends Setting {
   private final List<Setting> list;
   private transient Function<Setting, ItemStack> function;
   private transient Function<Setting, Sprite> function2;

   public CompactGroupSetting(String text, String text2, Setting... setting2) {
      super(text, text2);
      this.list = new ArrayList<>(Arrays.asList(setting2));

      for (Setting setting : this.list) {
         setting.setOnChange(this::fireOnChange);
      }
   }

   @Override
   public String getTypeId() {
      return "compact_group";
   }

   public CompactGroupSetting getCompactGroupSettingByFunction(Function<Setting, Sprite> function3) {
      this.function2 = function3;
      return this;
   }

   public Function<Setting, Sprite> getFunction2() {
      return this.function2;
   }

   @Override
   public void reset() {
      for (Setting setting : this.list) {
         setting.reset();
      }
   }

   public List<Setting> getList() {
      return this.list;
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("children") && jsonObject.get("children").isJsonObject()) {
         JsonObject jsonobject = jsonObject.getAsJsonObject("children");

         for (Setting setting : this.list) {
            JsonElement jsonelement = jsonobject.get(setting.getNameHash());
            if (jsonelement == null) {
               String s = setting.getName();
               jsonelement = TextHash.getJsonElementByStringJsonObject(s, jsonobject);
               if (jsonelement != null) {
                  TextHash.setFlag();
               }
            }

            if (jsonelement != null && jsonelement.isJsonObject()) {
               setting.fromJson(jsonelement.getAsJsonObject());
            }
         }
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      JsonObject jsonobject1 = new JsonObject();

      for (Setting setting : this.list) {
         jsonobject1.add(setting.getNameHash(), setting.toJson());
      }

      jsonobject.add("children", jsonobject1);
      return jsonobject;
   }

   public CompactGroupSetting getCompactGroupSettingByFunction2(Function<Setting, ItemStack> function2) {
      this.function = function2;
      return this;
   }

   public Function<Setting, ItemStack> getFunction() {
      return this.function;
   }
}
