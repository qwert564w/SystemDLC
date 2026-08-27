package client.gui.widget;

import client.concurrent.WaypointStore;
import client.data.JsonStore;
import client.data.Waypoint;
import client.module.Feature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class WaypointsPage extends PageWidget<WaypointRow, WaypointCreateForm> {
   private final WaypointCreateForm waypointCreateForm = new WaypointCreateForm();

   public WaypointsPage() {
      this.waypointCreateForm.setSubmitCallback(this::onStringIntIntInt);
      this.update3();
   }

   private void update3() {
      List<Waypoint> list = JsonStore.getInstance().getList();
      HashMap hashmap = new HashMap();

      for (WaypointRow waypointrow : this.list) {
         Waypoint waypoint = waypointrow.getWaypoint();
         if (!waypointrow.isFlag4() && waypoint != null && waypoint.getText() != null) {
            hashmap.put(waypoint.getText(), waypointrow);
         }
      }

      HashSet hashset = new HashSet();
      ArrayList arraylist = new ArrayList(list.size());

      for (Waypoint waypoint1 : list) {
         if (waypoint1 != null && waypoint1.getText() != null) {
            hashset.add(waypoint1.getText());
            WaypointRow waypointrow1 = (WaypointRow)hashmap.get(waypoint1.getText());
            if (waypointrow1 != null) {
               waypointrow1.setWaypoint(waypoint1);
            } else {
               waypointrow1 = this.getWaypointRowByWaypoint(waypoint1);
            }

            arraylist.add(waypointrow1);
         }
      }

      for (WaypointRow waypointrow2 : this.list) {
         if (!waypointrow2.isFlag4()) {
            Waypoint waypoint2 = waypointrow2.getWaypoint();
            if (waypoint2 == null || waypoint2.getText() == null || !hashset.contains(waypoint2.getText())) {
               waypointrow2.update3();
               arraylist.add(waypointrow2);
            }
         }
      }

      this.list.clear();
      this.list.addAll(arraylist);
   }

   private void onWaypoint(Waypoint waypoint) {
      if (waypoint != null) {
         if (Feature.mc != null && Feature.mc.keyboard != null) {
            String s = waypoint.getValue() + " " + waypoint.getValue2() + " " + waypoint.getValue3();
            Feature.mc.keyboard.setClipboard(s);
         }
      }
   }

   private void onWaypoint2(Waypoint waypoint) {
      if (waypoint != null && waypoint.getText() != null) {
         JsonStore jsonstore = JsonStore.getInstance();
         boolean flag = waypoint.isFlag();
         String s = waypoint.getText();
         jsonstore.onBooleanString(flag, s);
      }
   }

   private void onWaypoint3(Waypoint waypoint) {
      if (waypoint != null && waypoint.getText() != null) {
         JsonStore.getInstance().onString(waypoint.getText());
         this.update3();
      }
   }

   private void onWaypointString(Waypoint waypoint, String text) {
      if (waypoint != null && waypoint.getText() != null) {
         JsonStore.getInstance().onStringString(waypoint.getText(), text);
         this.update3();
      }
   }

   @Override
   protected Widget getWidget() {
      return this.getWaypointCreateForm();
   }

   @Override
   protected void onWidgetBooleanFloatFloat(Widget widget2, boolean flag, float value, float value2) {
      WaypointCreateForm waypointcreateform = (WaypointCreateForm)widget2;
      this.onWaypointCreateFormFloatBooleanFloat(waypointcreateform, value2, flag, value);
   }

   private void onStringIntIntInt(String text, int count, int count2, int count3) {
      if (text != null && !text.isBlank()) {
         if (WaypointStore.check()) {
            String s = WaypointStore.getString2();
            if (s != null) {
               JsonStore jsonstore = JsonStore.getInstance();
               String s1 = text.trim();
               jsonstore.getWaypointByStringIntIntStringInt(s1, count3, count, s, count2);
               this.update3();
            }
         }
      }
   }

   private void onWaypointIntIntInt(Waypoint waypoint, int count, int count2, int count3) {
      if (waypoint != null && waypoint.getText() != null) {
         JsonStore.getInstance().onStringIntIntInt(waypoint.getText(), count, count2, count3);
         this.update3();
      }
   }

   @Override
   protected boolean check() {
      return false;
   }

   @Override
   protected void onFloat(float value) {
      this.waypointCreateForm.setFloat(value);
   }

   protected WaypointCreateForm getWaypointCreateForm() {
      return this.waypointCreateForm;
   }

   @Override
   protected float getFloat3() {
      return 300.0F;
   }

   protected void onWaypointCreateFormFloatBooleanFloat(WaypointCreateForm waypointCreateForm, float value, boolean flag, float value2) {
      if (flag) {
         waypointCreateForm.onFloatFloat4(value, value2);
      } else {
         waypointCreateForm.onFloatFloat2(value, value2);
      }
   }

   public void update4() {
      this.update3();
   }

   private WaypointRow getWaypointRowByWaypoint(Waypoint waypoint) {
      WaypointRow waypointrow = new WaypointRow(waypoint);
      waypointrow.setFloat(this.value244);
      waypointrow.setConsumer(this::onWaypoint3);
      waypointrow.setConsumer2(this::onWaypoint);
      waypointrow.setConsumer3(this::onWaypoint2);
      waypointrow.setBiConsumer(this::onWaypointString);
      waypointrow.setConfigChangeCallback(this::onWaypointIntIntInt);
      return waypointrow;
   }
}
