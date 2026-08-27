package client.setting;

import client.enums.TextAlign;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class AlignmentSetting extends Setting {
   @Expose
   private TextAlign textAlign;
   @Expose
   private final TextAlign textAlign2;

   public AlignmentSetting(String text, String text2, TextAlign textAlign3) {
      super(text, text2);
      this.textAlign2 = textAlign3;
      this.textAlign = textAlign3;
   }

   @Override
   public String getTypeId() {
      return "alignment";
   }

   public TextAlign getTextAlign2() {
      return this.textAlign2;
   }

   @Override
   public void reset() {
      this.setTextAlign(this.textAlign2);
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("value")) {
         try {
            this.setTextAlign(TextAlign.getTextAlignByString(jsonObject.get("value").getAsString()));
         } catch (IllegalArgumentException illegalargumentexception) {
         }
      }

      if (jsonObject.has("visible")) {
         this.visible = jsonObject.get("visible").getAsBoolean();
      }
   }

   public TextAlign getTextAlign() {
      return this.textAlign;
   }

   public void setTextAlign(TextAlign textAlign2) {
      if (textAlign2 != null && this.textAlign != textAlign2) {
         this.textAlign = textAlign2;
         this.fireOnChange();
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("value", this.textAlign.name());
      jsonobject.addProperty("defaultValue", this.textAlign2.name());
      jsonobject.addProperty("visible", this.visible);
      return jsonobject;
   }
}
