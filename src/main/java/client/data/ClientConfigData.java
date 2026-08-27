package client.data;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientConfigData {
   @SerializedName("modules")
   private Map<String, ModuleState> map = new HashMap<>();
   @SerializedName("hudPositions")
   private Map<String, float[]> map2 = new HashMap<>();
   @SerializedName("hudSettings")
   private Map<String, Map<String, JsonElement>> map3 = new HashMap<>();
   @SerializedName("guiState")
   private ConfigData configData;
   @SerializedName("friends")
   private List<String> list = new ArrayList<>();
   @SerializedName("createdAt")
   private String text;
   @SerializedName("friendNicknames")
   private Map<String, String> map4 = new HashMap<>();
   @SerializedName("friendAliases")
   private Map<String, String> map5 = new HashMap<>();
   @SerializedName("friendCreatedAt")
   private Map<String, String> map6 = new HashMap<>();
   @SerializedName("favouriteModules")
   private List<String> list2 = new ArrayList<>();
   @SerializedName("collapsedModules")
   private List<String> list3 = new ArrayList<>();
   @SerializedName("savedColors")
   private List<Integer> list4 = new ArrayList<>();
   @SerializedName("waypoints")
   private List<Waypoint> list5 = new ArrayList<>();

   public Map<String, String> getMap5() {
      return this.map5;
   }

   public List<Waypoint> getList5() {
      return this.list5;
   }

   public void setMap5(Map<String, String> map) {
      this.map5 = map;
   }

   public void setList5(List<Waypoint> list) {
      this.list5 = list;
   }

   public void setList2(List<String> list) {
      this.list2 = list;
   }

   public void setMap2(Map<String, float[]> map) {
      this.map2 = map;
   }

   public Map<String, float[]> getMap2() {
      return this.map2;
   }

   public List<String> getList2() {
      return this.list2;
   }

   public void setMap3(Map<String, Map<String, JsonElement>> map) {
      this.map3 = map;
   }

   public Map<String, Map<String, JsonElement>> getMap3() {
      return this.map3;
   }

   public void setList3(List<String> list) {
      this.list3 = list;
   }

   public List<String> getList3() {
      return this.list3;
   }

   public void setMap6(Map<String, String> map) {
      this.map6 = map;
   }

   public Map<String, String> getMap6() {
      return this.map6;
   }

   public void setMap4(Map<String, String> map) {
      this.map4 = map;
   }

   public Map<String, String> getMap4() {
      return this.map4;
   }

   public void setList4(List<Integer> list) {
      this.list4 = list;
   }

   public List<Integer> getList4() {
      return this.list4;
   }

   public Map<String, ModuleState> getMap() {
      return this.map;
   }

   public List<String> getList() {
      return this.list;
   }

   public void setMap(Map<String, ModuleState> map2) {
      this.map = map2;
   }

   public ConfigData getConfigData() {
      return this.configData;
   }

   public String getText() {
      return this.text;
   }

   public void setConfigData(ConfigData configData2) {
      this.configData = configData2;
   }

   public void setList(List<String> list2) {
      this.list = list2;
   }

   public void setText(String text2) {
      this.text = text2;
   }
}
