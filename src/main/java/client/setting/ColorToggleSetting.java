package client.setting;

import com.google.gson.JsonObject;

public class ColorToggleSetting extends Setting {
   private final BooleanSetting booleanSetting;
   private final ColorSetting colorSetting;

   public ColorToggleSetting(String text, String text2, boolean flag, int count) {
      this(text, text2, flag, count, true);
   }

   public ColorToggleSetting(String text, String text2, boolean flag, int count, boolean flag2) {
      super(text, text2);
      this.booleanSetting = new BooleanSetting(text, text2, flag);
      this.colorSetting = new ColorSetting(text, text2, count, flag2);
      this.booleanSetting.setOnChange(this::fireOnChange);
      this.colorSetting.setOnChange(this::fireOnChange);
   }

   @Override
   public String getTypeId() {
      return "color_toggle";
   }

   @Override
   public void reset() {
      this.booleanSetting.reset();
      this.colorSetting.reset();
   }

   public boolean check() {
      return this.booleanSetting.isFlag3();
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.add("enabled", this.booleanSetting.toJson());
      jsonobject.add("color", this.colorSetting.toJson());
      return jsonobject;
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("enabled") && jsonObject.get("enabled").isJsonObject()) {
         this.booleanSetting.fromJson(jsonObject.getAsJsonObject("enabled"));
      }

      if (jsonObject.has("color") && jsonObject.get("color").isJsonObject()) {
         this.colorSetting.fromJson(jsonObject.getAsJsonObject("color"));
      }
   }

   public BooleanSetting getBooleanSetting() {
      return this.booleanSetting;
   }

   public int getInt() {
      return this.colorSetting.getInt3();
   }

   public void onInt(int count) {
      this.colorSetting.setInt(count);
   }

   public void onBoolean(boolean flag) {
      this.booleanSetting.setBoolean(flag);
   }

   public ColorSetting getColorSetting() {
      return this.colorSetting;
   }
}
