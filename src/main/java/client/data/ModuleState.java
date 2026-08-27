package client.data;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class ModuleState {
   @SerializedName("enabled")
   private boolean flag;
   @SerializedName("keyBind")
   private int value;
   @SerializedName("settings")
   private Map<String, JsonElement> map = new HashMap<>();

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public void setValue(int count) {
      this.value = count;
   }

   public void setMap(Map<String, JsonElement> map2) {
      this.map = map2;
   }

   public Map<String, JsonElement> getMap() {
      return this.map;
   }

   public int getValue() {
      return this.value;
   }

   public boolean isFlag() {
      return this.flag;
   }
}
