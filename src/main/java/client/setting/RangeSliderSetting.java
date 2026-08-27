package client.setting;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import java.util.concurrent.ThreadLocalRandom;

public class RangeSliderSetting extends Setting {
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
   private double value6;
   @Expose
   private double value7;
   @Expose
   private String text;
   @Expose
   private int value8;

   public RangeSliderSetting(String text, String text2, double value, double value2, double value3, double value4, double value5) {
      this(text, text2, value, value2, value3, value4, value5, "", 1);
   }

   public RangeSliderSetting(String text2, String text3, double value9, double value10, double value11, double value12, double value13, String text4, int count) {
      super(text2, text3);
      this.value3 = value9;
      this.value4 = value10;
      this.value5 = value11;
      this.value6 = value12;
      this.value7 = value13;
      this.text = text4 != null ? text4 : "";
      this.value8 = Math.max(0, count);
      this.value = this.getDoubleByDouble2(value9);
      this.value2 = this.getDoubleByDouble2(value10);
   }

   public double getValue4() {
      return this.value4;
   }

   public void setValue5(double value) {
      this.value5 = value;
   }

   @Override
   public String getTypeId() {
      return "range_slider";
   }

   public void setDouble(double value3) {
      double d0 = this.getDoubleByDouble(this.getDoubleByDouble2(Math.max(value3, this.value)));
      if (this.value2 != d0) {
         this.value2 = d0;
         this.fireOnChange();
      }
   }

   public double getValue() {
      return this.value;
   }

   private double getDoubleByDouble(double value) {
      return Math.round(value * Math.pow(10.0, this.value8)) / Math.pow(10.0, this.value8);
   }

   public String getString() {
      return this.getStringByDouble(this.value2);
   }

   public double getValue2() {
      return this.value2;
   }

   public String getString2() {
      return this.getString3() + " - " + this.getString();
   }

   @Override
   public void reset() {
      this.onDoubleDouble(this.value3, this.value4);
   }

   public void setValue3(double value) {
      this.value3 = value;
   }

   public double getValue7() {
      return this.value7;
   }

   public void setValue6(double value) {
      this.value6 = value;
   }

   public double getValue5() {
      return this.value5;
   }

   public double getValue3() {
      return this.value3;
   }

   public String getText() {
      return this.text;
   }

   public void setValue4(double value) {
      this.value4 = value;
   }

   public void setValue8(int count) {
      this.value8 = count;
   }

   public void setText(String text2) {
      this.text = text2;
   }

   public void onDoubleDouble(double value3, double value4) {
      double d0 = this.getDoubleByDouble(this.getDoubleByDouble2(Math.min(value3, value4)));
      double d1 = this.getDoubleByDouble(this.getDoubleByDouble2(Math.max(value3, value4)));
      if (this.value != d0 || this.value2 != d1) {
         this.value = d0;
         this.value2 = d1;
         this.fireOnChange();
      }
   }

   public int getValue8() {
      return this.value8;
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("valueLow", this.value);
      jsonobject.addProperty("valueHigh", this.value2);
      jsonobject.addProperty("defaultLow", this.value3);
      jsonobject.addProperty("defaultHigh", this.value4);
      jsonobject.addProperty("min", this.value5);
      jsonobject.addProperty("max", this.value6);
      jsonobject.addProperty("step", this.value7);
      jsonobject.addProperty("unit", this.text);
      jsonobject.addProperty("decimalPlaces", this.value8);
      jsonobject.addProperty("visible", this.visible);
      return jsonobject;
   }

   public long getLong() {
      return Math.round(this.getDouble());
   }

   public String getString3() {
      return this.getStringByDouble(this.value);
   }

   private double getDoubleByDouble2(double value) {
      return Math.max(this.value5, Math.min(this.value6, value));
   }

   private String getStringByDouble(double value) {
      return this.value8 == 0 ? String.format("%.0f%s", value, this.text) : String.format("%." + this.value8 + "f%s", value, this.text);
   }

   public void setDouble2(double value3) {
      double d0 = this.getDoubleByDouble(this.getDoubleByDouble2(Math.min(value3, this.value2)));
      if (this.value != d0) {
         this.value = d0;
         this.fireOnChange();
      }
   }

   public double getDouble() {
      return Math.abs(this.value - this.value2) < this.value7 / 2.0 ? this.value : ThreadLocalRandom.current().nextDouble(this.value, this.value2);
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      double d0 = jsonObject.has("valueLow") ? jsonObject.get("valueLow").getAsDouble() : this.value;
      double d1 = jsonObject.has("valueHigh") ? jsonObject.get("valueHigh").getAsDouble() : this.value2;
      this.onDoubleDouble(d0, d1);
      if (jsonObject.has("visible")) {
         this.visible = jsonObject.get("visible").getAsBoolean();
      }
   }

   public double getValue6() {
      return this.value6;
   }

   public void setValue7(double value) {
      this.value7 = value;
   }
}
