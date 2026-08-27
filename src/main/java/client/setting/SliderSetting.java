package client.setting;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class SliderSetting extends Setting {
   @Expose
   private double value;
   @Expose
   private double value2;
   @Expose
   private double value3;
   @Expose
   private double value4;
   @Expose
   private double value5;
   @Expose
   private String text;
   @Expose
   private int value6;
   private transient String text2;
   private transient double value7 = Double.NaN;

   public SliderSetting(String text2, String text3, double value7, double value8, double value9, double value10, String text4, int count) {
      super(text2, text3);
      this.value2 = value7;
      this.value = value7;
      this.value3 = value8;
      this.value4 = value9;
      this.value5 = value10;
      this.text = text4 != null ? text4 : "";
      this.value6 = Math.max(0, count);
   }

   public SliderSetting(String text, String text2, double value, double value2, double value3, double value4) {
      this(text, text2, value, value2, value3, value4, "", getIntByDouble(value4));
   }

   public double getValue2() {
      return this.value2;
   }

   public void setValue3(double value) {
      this.value3 = value;
   }

   @Override
   public String getTypeId() {
      return "slider";
   }

   public void update() {
      this.setDouble2(this.value - this.value5);
   }

   public double getDouble() {
      return (this.value - this.value3) / (this.value4 - this.value3) * 100.0;
   }

   public boolean check() {
      return Math.abs(this.value - this.value4) < this.value5 / 2.0;
   }

   public String getString() {
      return this.getName() + ": " + this.getString2();
   }

   public int getInt() {
      return (int)Math.round(this.getDouble2() / this.value5);
   }

   public void setDouble(double value2) {
      this.value = Math.max(this.value3, Math.min(this.value4, value2));
   }

   public int getValue6() {
      return this.value6;
   }

   public boolean check2() {
      return Math.abs(this.value - this.value2) < this.value5 / 2.0;
   }

   public String getText() {
      return this.text;
   }

   public double getDouble2() {
      return this.value4 - this.value3;
   }

   @Override
   public void reset() {
      this.setDouble2(this.value2);
   }

   public void onDouble(double value) {
      value = Math.max(0.0, Math.min(100.0, value));
      this.setDouble2(this.value3 + (this.value4 - this.value3) * (value / 100.0));
   }

   public double getValue5() {
      return this.value5;
   }

   public void setValue4(double value) {
      this.value4 = value;
   }

   public double getValue3() {
      return this.value3;
   }

   public double getValue() {
      return this.value;
   }

   public void setValue2(double value) {
      this.value2 = value;
   }

   public void setDouble2(double value2) {
      double d0 = Math.round(value2 * Math.pow(10.0, this.value6)) / Math.pow(10.0, this.value6);
      double d1 = Math.max(this.value3, Math.min(this.value4, d0));
      if (this.value != d1) {
         this.value = d1;
         this.fireOnChange();
      }
   }

   public void setValue6(int count) {
      this.value6 = count;
   }

   public void setText(String text2) {
      this.text = text2;
   }

   private static int getIntByDouble(double value) {
      if (value >= 1.0) {
         return 0;
      } else {
         return value <= 0.0 ? 1 : Math.min(6, Math.max(0, (int)Math.ceil(-Math.log10(value))));
      }
   }

   public float getValueAsFloat() {
      return (float)this.value;
   }

   public String getString2() {
      if (this.text2 != null && Double.doubleToRawLongBits(this.value) == Double.doubleToRawLongBits(this.value7)) {
         return this.text2;
      } else {
         String s = this.value6 == 0 ? "%.0f%s" : "%." + this.value6 + "f%s";
         this.text2 = String.format(s, this.value, this.text);
         this.value7 = this.value;
         return this.text2;
      }
   }

   public double getValue4() {
      return this.value;
   }

   public boolean check3() {
      return Math.abs(this.value - this.value3) < this.value5 / 2.0;
   }

   public long getValueAsLong() {
      return (long)this.value;
   }

   public void update2() {
      this.setDouble2(this.value + this.value5);
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("value")) {
         this.setDouble2(jsonObject.get("value").getAsDouble());
      }

      if (jsonObject.has("percentage")) {
         this.onDouble(jsonObject.get("percentage").getAsDouble());
      }

      if (jsonObject.has("visible")) {
         this.visible = jsonObject.get("visible").getAsBoolean();
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("value", this.value);
      jsonobject.addProperty("defaultValue", this.value2);
      jsonobject.addProperty("min", this.value3);
      jsonobject.addProperty("max", this.value4);
      jsonobject.addProperty("step", this.value5);
      jsonobject.addProperty("unit", this.text);
      jsonobject.addProperty("decimalPlaces", this.value6);
      jsonobject.addProperty("formattedValue", this.getString2());
      jsonobject.addProperty("percentage", this.getDouble());
      jsonobject.addProperty("isAtMin", this.check3());
      jsonobject.addProperty("isAtMax", this.check());
      jsonobject.addProperty("isAtDefault", this.check2());
      jsonobject.addProperty("range", this.getDouble2());
      jsonobject.addProperty("stepCount", this.getInt());
      jsonobject.addProperty("visible", this.visible);
      return jsonobject;
   }

   public int getInt2() {
      return (int)Math.round(this.value);
   }

   public void setValue5(double value) {
      this.value5 = value;
   }

   public double getValue42() {
      return this.value4;
   }
}
