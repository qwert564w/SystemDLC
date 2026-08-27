package client.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigData {
   @SerializedName("lastCategory")
   private String text;
   @SerializedName("guiOffsetX")
   private float value;
   @SerializedName("guiOffsetY")
   private float value2;
   @SerializedName("openedGroups")
   private Map<String, ModuleLayoutData> map = new HashMap<>();
   @SerializedName("categoryScrollPositions")
   private Map<String, Float> map2 = new HashMap<>();
   @SerializedName("moduleCardScrollPositions")
   private Map<String, Float> map3 = new HashMap<>();
   @SerializedName("themePreset")
   private String text2;
   @SerializedName("view")
   private String text3;
   @SerializedName("colorPickerMode")
   private String text4;
   @SerializedName("sidebarCollapsed")
   private boolean flag;
   @SerializedName("blurEnabled")
   private boolean flag2 = true;
   @SerializedName("searchHistory")
   private List<String> list = new ArrayList<>();

   public boolean isFlag2() {
      return this.flag2;
   }

   public void setMap2(Map<String, Float> map) {
      this.map2 = map;
   }

   public String getText2() {
      return this.text2;
   }

   public void setText2(String text) {
      this.text2 = text;
   }

   public void setValue2(float value) {
      this.value2 = value;
   }

   public void setFlag2(boolean flag) {
      this.flag2 = flag;
   }

   public Map<String, Float> getMap2() {
      return this.map2;
   }

   public float getValue2() {
      return this.value2;
   }

   public void setText3(String text) {
      this.text3 = text;
   }

   public void setMap3(Map<String, Float> map) {
      this.map3 = map;
   }

   public String getText3() {
      return this.text3;
   }

   public Map<String, Float> getMap3() {
      return this.map3;
   }

   public String getText4() {
      return this.text4;
   }

   public void setText4(String text) {
      this.text4 = text;
   }

   public void setList(List<String> list2) {
      this.list = list2;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public Map<String, ModuleLayoutData> getMap() {
      return this.map;
   }

   public float getValue() {
      return this.value;
   }

   public String getText() {
      return this.text;
   }

   public void setMap(Map<String, ModuleLayoutData> map2) {
      this.map = map2;
   }

   public void setValue(float value2) {
      this.value = value2;
   }

   public void setText(String text2) {
      this.text = text2;
   }

   public List<String> getList() {
      return this.list;
   }
}
