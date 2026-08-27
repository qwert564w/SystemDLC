package client.setting;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class FilterMenuSetting extends ActionSetting {
   private final BooleanSetting booleanSetting;
   private final MultilistSetting obyazatelnyeChary;
   private final MultilistSetting isklyuchennyeChary;
   private final String text;

   public FilterMenuSetting(String text2, String text3, String text4, List list, List list2, List list3) {
      super(text2, text3);
      this.text = text4;
      this.booleanSetting = new BooleanSetting(text2, text3, false);
      MultilistSetting multilistsetting = new MultilistSetting("", "", list, (List)(list2 != null ? list2 : new ArrayList()));
      multilistsetting.setName("Обязательные чары");
      multilistsetting.setDescription("Обязательно должны быть на предмете");
      this.obyazatelnyeChary = multilistsetting;
      multilistsetting = new MultilistSetting("", "", list, (List)(list3 != null ? list3 : new ArrayList()));
      multilistsetting.setName("Исключенные чары");
      multilistsetting.setDescription("Не должны быть на предмете");
      this.isklyuchennyeChary = multilistsetting;
      this.booleanSetting.setOnChange(this::fireOnChange);
      this.obyazatelnyeChary.setOnChange(this::fireOnChange);
      this.isklyuchennyeChary.setOnChange(this::fireOnChange);
   }

   @Override
   public String getTypeId() {
      return "filter_menu";
   }

   public MultilistSetting getIsklyuchennyeChary() {
      return this.isklyuchennyeChary;
   }

   @Override
   public void reset() {
      this.booleanSetting.reset();
      this.obyazatelnyeChary.reset();
      this.isklyuchennyeChary.reset();
   }

   public String getText() {
      return this.text;
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = super.toJson();
      jsonobject.add("enabled", this.booleanSetting.toJson());
      jsonobject.add("required", this.obyazatelnyeChary.toJson());
      jsonobject.add("excluded", this.isklyuchennyeChary.toJson());
      return jsonobject;
   }

   public MultilistSetting getObyazatelnyeChary() {
      return this.obyazatelnyeChary;
   }

   public BooleanSetting getBooleanSetting() {
      return this.booleanSetting;
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      super.fromJson(jsonObject);
      if (jsonObject.has("enabled") && jsonObject.get("enabled").isJsonObject()) {
         this.booleanSetting.fromJson(jsonObject.getAsJsonObject("enabled"));
      }

      if (jsonObject.has("required") && jsonObject.get("required").isJsonObject()) {
         this.obyazatelnyeChary.fromJson(jsonObject.getAsJsonObject("required"));
      }

      if (jsonObject.has("excluded") && jsonObject.get("excluded").isJsonObject()) {
         this.isklyuchennyeChary.fromJson(jsonObject.getAsJsonObject("excluded"));
      }
   }
}
