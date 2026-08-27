package client.setting;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class BooleanSetting extends Setting {
   @Expose
   private boolean flag;
   @Expose
   private boolean flag2;

   public BooleanSetting(String text, String text2, boolean flag3) {
      super(text, text2);
      this.flag2 = flag3;
      this.flag = flag3;
   }

   @Override
   public String getTypeId() {
      return "boolean";
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setFlag2(boolean flag) {
      this.flag2 = flag;
   }

   public boolean isFlag2() {
      return this.flag2;
   }

   @Override
   public void reset() {
      this.setBoolean(this.flag2);
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("value", this.flag);
      jsonobject.addProperty("defaultValue", this.flag2);
      jsonobject.addProperty("visible", this.visible);
      return jsonobject;
   }

   public boolean isFlag3() {
      return this.flag;
   }

   public void setBoolean(boolean flag2) {
      if (this.flag != flag2) {
         this.flag = flag2;
         this.fireOnChange();
      }
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("value")) {
         this.flag = jsonObject.get("value").getAsBoolean();
      }

      if (jsonObject.has("visible")) {
         this.visible = jsonObject.get("visible").getAsBoolean();
      }
   }

   public void update() {
      this.flag = !this.flag;
      this.fireOnChange();
   }
}
