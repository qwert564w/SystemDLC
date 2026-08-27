package client.setting;

import com.google.gson.JsonObject;

public class ActionSetting extends Setting {
   private transient Runnable runnable;

   public ActionSetting(String text, String text2) {
      super(text, text2);
   }

   @Override
   public String getTypeId() {
      return "action";
   }

   public void update() {
      if (this.runnable != null) {
         try {
            this.runnable.run();
         } catch (Exception exception) {
         }
      }

      this.fireOnChange();
   }

   @Override
   public void reset() {
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   public Runnable getRunnable() {
      return this.runnable;
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("visible", this.visible);
      return jsonobject;
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("visible")) {
         this.visible = jsonObject.get("visible").getAsBoolean();
      }
   }
}
