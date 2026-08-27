package client.setting;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class InputSetting extends Setting {
   @Expose
   private String text;
   @Expose
   private final String text2;
   @Expose
   private final String text3;
   @Expose
   private final boolean flag;
   @Expose
   private final double value;
   @Expose
   private final double value2;

   public InputSetting(String text4, String text5, String text6, String text7, boolean flag2, double value3, double value4) {
      super(text4, text5);
      this.text2 = text6 != null ? text6 : "";
      this.text = this.text2;
      this.text3 = text7 != null ? text7 : "";
      this.flag = flag2;
      this.value = value3;
      this.value2 = value4;
   }

   public InputSetting(String text, String text2, String text3, String text4, boolean flag) {
      this(text, text2, text3, text4, flag, 0.0, 100.0);
   }

   public InputSetting(String text, String text2, String text3, String text4) {
      this(text, text2, text3, text4, false, 0.0, 0.0);
   }

   public boolean check() {
      return this.flag && this.value != this.value2;
   }

   @Override
   public String getTypeId() {
      return "input";
   }

   public boolean check2() {
      if (!this.flag) {
         return false;
      } else {
         try {
            Double.parseDouble(this.text);
            return true;
         } catch (NumberFormatException numberformatexception) {
            return false;
         }
      }
   }

   public void update() {
      if (this.flag && this.value != this.value2) {
         double d0 = this.getDouble2();
         double d1 = this.getDouble();
         this.setDouble(d0 - d1);
      }
   }

   private double getDouble() {
      double d0 = this.value2 - this.value;
      if (d0 >= 100.0) {
         return 1.0;
      } else if (d0 >= 10.0) {
         return 0.1;
      } else {
         return d0 >= 1.0 ? 0.01 : 0.001;
      }
   }

   public String getText() {
      return this.text;
   }

   public boolean check3() {
      return this.text.equals(this.text2);
   }

   public double getValue() {
      return this.value;
   }

   public String getText2() {
      return this.text2;
   }

   @Override
   public void reset() {
      this.text = this.text2;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public double getValue2() {
      return this.value2;
   }

   public String getText3() {
      return this.text3;
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("value")) {
         this.setString(jsonObject.get("value").getAsString());
      }

      if (jsonObject.has("visible")) {
         this.visible = jsonObject.get("visible").getAsBoolean();
      }
   }

   public void setDouble(double value3) {
      if (this.flag) {
         if (this.value != this.value2) {
            value3 = Math.max(this.value, Math.min(this.value2, value3));
         }

         this.text = String.valueOf(value3);
      }
   }

   public void setString(String text2) {
      if (text2 == null) {
         this.text = "";
      } else {
         if (this.flag) {
            try {
               double d0 = Double.parseDouble(text2);
               if (this.value != this.value2) {
                  d0 = Math.max(this.value, Math.min(this.value2, d0));
               }

               this.text = String.valueOf(d0);
            } catch (NumberFormatException numberformatexception) {
            }
         } else {
            this.text = text2;
         }
      }
   }

   public boolean check4() {
      return this.text == null || this.text.trim().isEmpty();
   }

   public int getInt() {
      return (int)this.getDouble2();
   }

   public float getFloat() {
      return (float)this.getDouble2();
   }

   public void update2() {
      if (this.flag && this.value != this.value2) {
         double d0 = this.getDouble2();
         double d1 = this.getDouble();
         this.setDouble(d0 + d1);
      }
   }

   public long getLong() {
      return (long)this.getDouble2();
   }

   public String getString() {
      return this.check4() ? this.getName() + ": " + this.text3 : this.getName() + ": " + this.text;
   }

   public double getDouble2() {
      if (this.flag) {
         try {
            return Double.parseDouble(this.text);
         } catch (NumberFormatException numberformatexception) {
            return this.value;
         }
      } else {
         return 0.0;
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("value", this.text);
      jsonobject.addProperty("defaultValue", this.text2);
      jsonobject.addProperty("placeholder", this.text3);
      jsonobject.addProperty("isNumber", this.flag);
      jsonobject.addProperty("minValue", this.value);
      jsonobject.addProperty("maxValue", this.value2);
      jsonobject.addProperty("isEmpty", this.check4());
      jsonobject.addProperty("isValidNumber", this.check2());
      jsonobject.addProperty("isAtDefault", this.check3());
      jsonobject.addProperty("visible", this.visible);
      return jsonobject;
   }
}
