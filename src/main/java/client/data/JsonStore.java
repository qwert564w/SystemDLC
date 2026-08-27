package client.data;

import client.concurrent.WaypointStore;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class JsonStore {
   private static final JsonStore INSTANCE = new JsonStore();

   private JsonStore() {
   }

   public void onStringString(String text, String text2) {
      WaypointStore.getInstance().onStringString(text, text2);
   }

   public void onStringIntIntInt(String text, int count, int count2, int count3) {
      WaypointStore.getInstance().onStringIntIntInt(text, count, count2, count3);
   }

   public void onBooleanString(boolean flag, String text) {
      WaypointStore.getInstance().onStringBoolean(text, flag);
   }

   public List getList() {
      ArrayList<Waypoint> arraylist = new ArrayList<>(WaypointStore.getInstance().getList());
      arraylist.sort(Comparator.comparing(var0 -> var0.getText4() == null ? "" : var0.getText4()));
      return arraylist;
   }

   public Waypoint getWaypointByStringIntIntStringInt(String text, int count, int count2, String text2, int count3) {
      return WaypointStore.getInstance().getWaypointByStringIntIntIntString2(text, count2, count3, count, text2);
   }

   public void onString(String text) {
      WaypointStore.getInstance().removeString(text);
   }

   public static JsonStore getInstance() {
      return INSTANCE;
   }
}
