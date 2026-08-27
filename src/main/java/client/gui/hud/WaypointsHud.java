package client.gui.hud;

import client.api.Theme;
import client.concurrent.WaypointStore;
import client.data.OrderedSet;
import client.data.Waypoint;
import client.gui.widget.KeybindEntry;
import client.module.CategoryType;
import client.module.Feature;
import client.module.client.HudModule;
import client.module.client.Waypoints;
import client.render.SvgShader;
import client.util.TextFormatUtil;
import client.util.TimeFormat;
import client.util.UnsafeAccess;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

public class WaypointsHud extends HudPanel {
   private static final UnsafeAccess<Waypoints> unsafeAccess2 = new UnsafeAccess<>(Waypoints.class);
   private static final float value277 = 12.0F;
   private static final float value278 = 8.0F;
   private final Map<String, Float> map2 = new HashMap<>();
   private final Map<String, Float> map3 = new HashMap<>();
   private final Set<String> set2 = new HashSet<>();
   private long time2;
   private static final float value279 = 12.0F;
   private static final float value280 = 0.05F;
   private final OrderedSet<String> orderedSet = new OrderedSet<>();
   private final Map<String, Double> map4 = new HashMap<>();
   private final Map<String, String> map5 = new HashMap<>();
   private final ArrayList<KeybindEntry> list5 = new ArrayList<>();

   @Override
   public String getString() {
      return "Шайпоинтз";
   }

   @Override
   protected List getList2() {
      return List.of(new KeybindEntry("База", "120m"), new KeybindEntry("Шахта", "1.2km"), new KeybindEntry("Спавн", "350m"));
   }

   @Override
   protected float getFloat28() {
      return 20.0F;
   }

   @Override
   protected String getString2() {
      return "Waypoints";
   }

   @Override
   public String getString3() {
      return "ws";
   }

   private static String getStringByMapString(Map map, String text) {
      if (!map.containsKey(text)) {
         return text;
      } else {
         for (int i = 2; i < 1000; i++) {
            String s = text + " (" + i + ")";
            if (!map.containsKey(s)) {
               return s;
            }
         }

         return text;
      }
   }

   @Override
   protected void onKeybindEntryFloatFloatFloatMatrix4fFloat(KeybindEntry keybindEntry, float value, float value2, float value3, Matrix4f matrix4f2, float value4) {
      Float f = this.map3.get(keybindEntry.getText());
      if (f == null) {
         f = this.map2.get(keybindEntry.getText());
      }

      float f1 = f == null ? 0.0F : f;
      float f2 = value4 + 8.0F;
      float f3 = this.getFloat30();
      float f4 = value + (f3 - 12.0F) / 2.0F;
      float f5 = f2 + 6.0F;
      float f6 = f4 + 6.0F;
      if (this.set2.contains(keybindEntry.getText())) {
         CategoryType categorytype4 = CategoryType.WAYPOINT;
         int i = Theme.foreground();
         float f8 = 12.0F;
         float f7 = 12.0F;
         CategoryType categorytype = categorytype4;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, i, matrix4f2, f4, categorytype, f8, f2, f7);
      } else {
         MatrixStack matrixstack = this.drawContext != null ? this.drawContext.getMatrices() : null;
         if (matrixstack == null) {
            CategoryType categorytype3 = CategoryType.WAYPOINT;
            int j = Theme.foreground();
            float f10 = 12.0F;
            float f9 = 12.0F;
            CategoryType categorytype1 = categorytype3;
            SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, j, matrix4f2, f4, categorytype1, f10, f2, f9);
         } else {
            matrixstack.push();
            matrixstack.translate(f5, f6, 0.0F);
            matrixstack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f1));
            matrixstack.translate(-f5, -f6, 0.0F);
            Matrix4f matrix4f = matrixstack.peek().getPositionMatrix();
            CategoryType categorytype5 = CategoryType.WAYPOINT_ARROW;
            int k = Theme.foreground();
            float f12 = 12.0F;
            float f11 = 12.0F;
            CategoryType categorytype2 = categorytype5;
            SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, k, matrix4f, f4, categorytype2, f12, f2, f11);
            matrixstack.pop();
         }
      }
   }

   private static String getStringByDouble(double value) {
      return value < 1000.0 ? (int)Math.round(value) + "m" : TextFormatUtil.getStringByDouble(value / 1000.0) + "km";
   }

   @Override
   protected float getFloat30() {
      return 16.0F;
   }

   @Override
   protected CategoryType getCategoryType2() {
      return CategoryType.WAYPOINT;
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      Waypoints waypoints = (Waypoints)unsafeAccess2.getModule2();
      if (waypoints != null) {
         waypoints.getRenderVHude().setBoolean(flag);
      }
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      Waypoints waypoints = (Waypoints)unsafeAccess2.getModule2();
      return waypoints != null && waypoints.check4();
   }

   @Override
   protected List getList3() {
      ArrayList arraylist = this.list5;
      arraylist.clear();
      this.map2.clear();
      this.set2.clear();
      ClientPlayerEntity clientplayerentity = Feature.mc.player;
      if (clientplayerentity == null) {
         this.orderedSet.update();
         return arraylist;
      } else {
         Waypoints waypoints = (Waypoints)unsafeAccess2.getModule2();
         if (waypoints != null && waypoints.check4()) {
            if (!WaypointStore.check()) {
               this.orderedSet.update();
               return arraylist;
            } else {
               String s = WaypointStore.getString2();
               if (s == null) {
                  this.orderedSet.update();
                  return arraylist;
               } else {
                  double d0 = waypoints.getDouble();
                  boolean flag = waypoints.check3();
                  Map map = this.map4;
                  Map map1 = this.map5;
                  map.clear();
                  map1.clear();
                  long i = System.currentTimeMillis();

                  for (Waypoint waypoint : (Iterable<Waypoint>)(WaypointStore.getInstance().getListByString(s))) {
                     if (waypoint != null && waypoint.getText() != null && (!flag || waypoint.isFlag())) {
                        long j = waypoint.getTime() > 0L ? waypoint.getTime() - i : 0L;
                        if (!waypoint.isFlag3() || j > 0L) {
                           double d1 = waypoint.getValue() - clientplayerentity.getX();
                           double d2 = waypoint.getValue3() - clientplayerentity.getZ();
                           double d3 = waypoint.getValue2() - clientplayerentity.getY();
                           double d4 = Math.sqrt(d1 * d1 + d3 * d3 + d2 * d2);
                           if (!(d4 > d0) || waypoint.isFlag2()) {
                              String s1 = waypoint.getText2() == null ? "" : waypoint.getText2();
                              String s2 = getStringByMapString(map, s1);
                              map.put(s2, waypoint.isFlag3() ? 0.0 : d4);
                              map1.put(s2, j > 0L ? TimeFormat.getStringByLong(j) : getStringByDouble(d4));
                              if (waypoint.isFlag3()) {
                                 this.set2.add(s2);
                              } else {
                                 float f2 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(-d1, d2)) - clientplayerentity.getYaw() - 48.0);
                                 this.map2.put(s2, f2);
                              }
                           }
                        }
                     }
                  }

                  this.orderedSet.onSetComparator(map.keySet(), Comparator.comparingDouble(var0 -> (Float)map.get(var0)));
                  this.map3.keySet().retainAll(this.map2.keySet());
                  float f3 = this.time2 == 0L ? 0.0F : Math.min(0.2F, (float)(i - this.time2) / 1000.0F);
                  this.time2 = i;
                  float f4 = Math.min(1.0F, f3 * 12.0F);

                  for (Entry entry : this.map2.entrySet()) {
                     float f5 = (Float)entry.getValue();
                     Float f = this.map3.get(entry.getKey());
                     if (f == null) {
                        this.map3.put((String)entry.getKey(), f5);
                     } else {
                        float f6 = f;
                        float f1 = MathHelper.wrapDegrees(f5 - f6);
                        if (Math.abs(f1) < 0.05F) {
                           this.map3.put((String)entry.getKey(), f5);
                        } else {
                           this.map3.put((String)entry.getKey(), MathHelper.wrapDegrees(f6 + f1 * f4));
                        }
                     }
                  }

                  for (String s3 : (Iterable<String>)(this.orderedSet.getLinkedHashSetAsIterable())) {
                     String s4 = (String)map1.get(s3);
                     if (s4 == null) {
                        s4 = "";
                     }

                     arraylist.add(new KeybindEntry(s3, s4));
                  }

                  return arraylist;
               }
            }
         } else {
            this.orderedSet.update();
            return arraylist;
         }
      }
   }
}
