package client.setting;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class FilePickerSetting extends Setting {
   @Expose
   private String text;
   @Expose
   private final String text2;

   public FilePickerSetting(String text, String text2) {
      this(text, text2, "");
   }

   public FilePickerSetting(String text3, String text4, String text5) {
      super(text3, text4);
      this.text2 = text5 != null ? text5 : "";
      this.text = this.text2;
   }

   @Override
   public String getTypeId() {
      return "file_picker";
   }

   public String getText() {
      return this.text;
   }

   @Override
   public void reset() {
      this.text = this.text2;
   }

   public String getText2() {
      return this.text2;
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("value", this.text);
      jsonobject.addProperty("visible", this.visible);
      return jsonobject;
   }

   public void setString(String text2) {
      this.text = text2 != null ? text2 : "";
      this.fireOnChange();
   }

   public boolean check() {
      return this.text != null && !this.text.isEmpty();
   }

   public String getString() {
      if (!this.check()) {
         return "";
      } else {
         int i = Math.max(this.text.lastIndexOf(47), this.text.lastIndexOf(92));
         return i >= 0 ? this.text.substring(i + 1) : this.text;
      }
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("value")) {
         this.text = jsonObject.get("value").getAsString();
      }

      if (jsonObject.has("visible")) {
         this.visible = jsonObject.get("visible").getAsBoolean();
      }
   }
}
