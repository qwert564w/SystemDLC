package client.util;

import client.data.ValueRegistry;
import client.module.Feature;
import client.module.render.ChunkAnimator;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import org.lwjgl.opengl.GL20C;

public final class ChunkAnimatorAccess {
   private static final long time = System.currentTimeMillis();
   private static final UnsafeAccess<ChunkAnimator> unsafeAccess = new UnsafeAccess<>(ChunkAnimator.class);
   private static final WeakHashMap<Object, Integer> weakHashMap = new WeakHashMap<>();
   private static final WeakHashMap<Object, RegionState> regionStates = new WeakHashMap<>();
   private static int version;
   private static final Map<Integer, ValueRegistry> map = new HashMap<>();
   private static final int[] intArray = new int[256];
   private static Method method;
   private static Method method2;
   private static Method method3;
   private static Method method4;
   private static Method method5;
   private static boolean flag;

   private ChunkAnimatorAccess() {
   }

   static {
      Arrays.fill(intArray, -1);
   }

   private static float getFloat() {
      return Feature.mc.world == null ? 256.0F : Feature.mc.world.getDimension().minY() + Feature.mc.world.getDimension().height();
   }

   private static boolean isObject(Object value) {
      if (method4 != null) {
         return true;
      } else if (flag) {
         return false;
      } else {
         try {
            Class oclass = value.getClass();
            method = oclass.getMethod("getOriginX");
            method2 = oclass.getMethod("getOriginY");
            method3 = oclass.getMethod("getOriginZ");
            method4 = oclass.getMethod("getSection", int.class);
            return true;
         } catch (ReflectiveOperationException reflectiveoperationexception) {
            flag = true;
            return false;
         }
      }
   }

   private static int getInt() {
      return (int)Math.min(2147483647L, System.currentTimeMillis() - time);
   }

   private static float getFloat2() {
      return Feature.mc.world == null ? 0.0F : Feature.mc.world.getDimension().minY();
   }

   public static String getStringByStringString(String text, String text2) {
      if (text == null || text2 == null || !text2.contains("blocks/block_layer_opaque.vsh")) {
         return text;
      } else if (!text.contains("u_ChunkAnimatorEnabled") && text.contains("    vec3 translation = u_RegionOffset + _get_draw_translation(_draw_id);")) {
         String s = text.replace("void main() {", getString() + "\nvoid main() {");
         return s.replace(
            "    vec3 translation = u_RegionOffset + _get_draw_translation(_draw_id);",
            "    vec3 chunkanim_relative = _get_draw_translation(_draw_id);\n    vec3 translation = u_RegionOffset + chunkanim_relative + chunkanim_offset(chunkanim_relative);"
         );
      } else {
         return text;
      }
   }

   private static String getString() {
      return String.join(
         "\n",
         "uniform int u_ChunkAnimatorEnabled;",
         "uniform int u_ChunkAnimatorMode;",
         "uniform int u_ChunkAnimatorEasing;",
         "uniform int u_ChunkAnimatorCurrentTime;",
         "uniform int u_ChunkAnimatorDuration;",
         "uniform int u_ChunkAnimatorDisableAroundPlayer;",
         "uniform int u_ChunkAnimatorStartTimes[256];",
         "uniform float u_ChunkAnimatorMinY;",
         "uniform float u_ChunkAnimatorMaxY;",
         "uniform float u_ChunkAnimatorHorizon;",
         "uniform float u_ChunkAnimatorRegionOriginX;",
         "uniform float u_ChunkAnimatorRegionOriginY;",
         "uniform float u_ChunkAnimatorRegionOriginZ;",
         "uniform float u_ChunkAnimatorPlayerX;",
         "uniform float u_ChunkAnimatorPlayerZ;",
         "",
         "float chunkanim_ease(float elapsed) {",
         "    if (u_ChunkAnimatorDuration <= 0) {",
         "        return 1.0;",
         "    }",
         "",
         "    float duration = float(u_ChunkAnimatorDuration);",
         "    float t = clamp(elapsed, 0.0, duration);",
         "",
         "    if (t >= duration) {",
         "        return 1.0;",
         "    }",
         "",
         "    float n = t / duration;",
         "",
         "    if (u_ChunkAnimatorEasing == 0) {",
         "        return n;",
         "    }",
         "",
         "    if (u_ChunkAnimatorEasing == 1) {",
         "        return -n * (n - 2.0);",
         "    }",
         "",
         "    if (u_ChunkAnimatorEasing == 2) {",
         "        n -= 1.0;",
         "        return n * n * n + 1.0;",
         "    }",
         "",
         "    if (u_ChunkAnimatorEasing == 3) {",
         "        n -= 1.0;",
         "        return -(n * n * n * n - 1.0);",
         "    }",
         "",
         "    if (u_ChunkAnimatorEasing == 4) {",
         "        n -= 1.0;",
         "        return n * n * n * n * n + 1.0;",
         "    }",
         "",
         "    if (u_ChunkAnimatorEasing == 5) {",
         "        return (n >= 1.0) ? 1.0 : (-pow(2.0, -10.0 * n) + 1.0);",
         "    }",
         "",
         "    if (u_ChunkAnimatorEasing == 6) {",
         "        return sin(n * 1.5707963267948966);",
         "    }",
         "",
         "    if (u_ChunkAnimatorEasing == 7) {",
         "        n -= 1.0;",
         "        return sqrt(max(0.0, 1.0 - n * n));",
         "    }",
         "",
         "    if (u_ChunkAnimatorEasing == 8) {",
         "        float s = 1.70158;",
         "        n -= 1.0;",
         "        return n * n * ((s + 1.0) * n + s) + 1.0;",
         "    }",
         "",
         "    if (u_ChunkAnimatorEasing == 9) {",
         "        if (n < 1.0 / 2.75) {",
         "            return 7.5625 * n * n;",
         "        } else if (n < 2.0 / 2.75) {",
         "            n -= 1.5 / 2.75;",
         "            return 7.5625 * n * n + 0.75;",
         "        } else if (n < 2.5 / 2.75) {",
         "            n -= 2.25 / 2.75;",
         "            return 7.5625 * n * n + 0.9375;",
         "        }",
         "",
         "        n -= 2.625 / 2.75;",
         "        return 7.5625 * n * n + 0.984375;",
         "    }",
         "",
         "    if (n <= 0.0 || n >= 1.0) {",
         "        return n;",
         "    }",
         "",
         "    return pow(2.0, -10.0 * n) * sin((n - 0.075) * 20.943951023931955) + 1.0;",
         "}",
         "",
         "vec3 chunkanim_horizontal_offset(float elapsed, vec3 origin) {",
         "    float eased = chunkanim_ease(elapsed) * 200.0;",
         "    float distance = -(200.0 - eased);",
         "    float differenceX = u_ChunkAnimatorPlayerX - (origin.x + 8.0);",
         "    float differenceZ = u_ChunkAnimatorPlayerZ - (origin.z + 8.0);",
         "",
         "    if (abs(differenceX) > abs(differenceZ)) {",
         "        return vec3((differenceX > 0.0 ? 1.0 : -1.0) * distance, 0.0, 0.0);",
         "    }",
         "",
         "    return vec3(0.0, 0.0, (differenceZ > 0.0 ? 1.0 : -1.0) * distance);",
         "}",
         "",
         "vec3 chunkanim_offset(vec3 relative) {",
         "    if (u_ChunkAnimatorEnabled == 0 || u_ChunkAnimatorDuration <= 0) {",
         "        return vec3(0.0);",
         "    }",
         "",
         "    int sectionId = int(_draw_id);",
         "    int startTime = u_ChunkAnimatorStartTimes[sectionId];",
         "",
         "    if (startTime < 0) {",
         "        return vec3(0.0);",
         "    }",
         "",
         "    float elapsed = float(u_ChunkAnimatorCurrentTime - startTime);",
         "",
         "    if (elapsed < 0.0 || elapsed >= float(u_ChunkAnimatorDuration)) {",
         "        return vec3(0.0);",
         "    }",
         "",
         "    vec3 origin = vec3(u_ChunkAnimatorRegionOriginX, u_ChunkAnimatorRegionOriginY, u_ChunkAnimatorRegionOriginZ) + relative;",
         "",
         "    if (u_ChunkAnimatorDisableAroundPlayer != 0) {",
         "        float nearX = u_ChunkAnimatorPlayerX - (origin.x + 8.0);",
         "        float nearZ = u_ChunkAnimatorPlayerZ - (origin.z + 8.0);",
         "",
         "        if (nearX * nearX + nearZ * nearZ <= 4096.0) {",
         "            return vec3(0.0);",
         "        }",
         "    }",
         "",
         "    int mode = u_ChunkAnimatorMode;",
         "",
         "    if (mode == 2) {",
         "        mode = origin.y < u_ChunkAnimatorHorizon ? 0 : 1;",
         "    }",
         "",
         "    if (mode == 0) {",
         "        float distance = abs(origin.y - u_ChunkAnimatorMinY);",
         "        return vec3(0.0, -distance + chunkanim_ease(elapsed) * distance, 0.0);",
         "    }",
         "",
         "    if (mode == 1) {",
         "        float distance = max(0.0, u_ChunkAnimatorMaxY - origin.y);",
         "        return vec3(0.0, distance - chunkanim_ease(elapsed) * distance, 0.0);",
         "    }",
         "",
         "    if (mode == 3 || mode == 4) {",
         "        return chunkanim_horizontal_offset(elapsed, origin);",
         "    }",
         "",
         "    return vec3(0.0);",
         "}"
      );
   }

   private static boolean isObject2(Object value) {
      try {
         if (method5 == null) {
            method5 = value.getClass().getMethod("isBuilt");
         }

         return Boolean.TRUE.equals(method5.invoke(value));
      } catch (ReflectiveOperationException reflectiveoperationexception) {
         return false;
      }
   }

   public static void onObject(Object value) {
      if (value != null && isObject(value)) {
         ChunkAnimator chunkanimator = (ChunkAnimator)unsafeAccess.getModule2();
         int i = GL20C.glGetInteger(35725);
         if (i != 0) {
            ValueRegistry valueregistry = map.computeIfAbsent(i, ValueRegistry::new);
            if (chunkanimator == null) {
               byte b0 = 0;
               String s = "u_ChunkAnimatorEnabled";
               valueregistry.onIntString(b0, s);
            } else {
               int i1 = getInt();
               int j1 = chunkanimator.getInt5();
               RegionState regionstate = getRegionState(value, i1);
               if (regionstate == null || i1 - regionstate.maxTime >= j1) {
                  byte b3 = 0;
                  String s7 = "u_ChunkAnimatorEnabled";
                  valueregistry.onIntString(b3, s7);
               } else {
                  byte b1 = 1;
                  String s1 = "u_ChunkAnimatorEnabled";
                  valueregistry.onIntString(b1, s1);
                  int k1 = chunkanimator.getInt2();
                  String s2 = "u_ChunkAnimatorMode";
                  valueregistry.onIntString(k1, s2);
                  int l1 = chunkanimator.getInt4();
                  String s3 = "u_ChunkAnimatorEasing";
                  valueregistry.onIntString(l1, s3);
                  String s4 = "u_ChunkAnimatorCurrentTime";
                  valueregistry.onIntString(i1, s4);
                  String s5 = "u_ChunkAnimatorDuration";
                  valueregistry.onIntString(j1, s5);
                  byte b2 = 0;
                  String s6 = "u_ChunkAnimatorDisableAroundPlayer";
                  valueregistry.onIntString(b2, s6);
                  valueregistry.onStringIntArray("u_ChunkAnimatorStartTimes", regionstate.times);
                  valueregistry.onStringFloat("u_ChunkAnimatorMinY", getFloat2());
                  valueregistry.onStringFloat("u_ChunkAnimatorMaxY", getFloat());
                  valueregistry.onStringFloat("u_ChunkAnimatorHorizon", 63.0F);
                  valueregistry.onStringFloat("u_ChunkAnimatorRegionOriginX", regionstate.originX);
                  valueregistry.onStringFloat("u_ChunkAnimatorRegionOriginY", regionstate.originY);
                  valueregistry.onStringFloat("u_ChunkAnimatorRegionOriginZ", regionstate.originZ);
                  valueregistry.onStringFloat("u_ChunkAnimatorPlayerX", Feature.mc.player != null ? Feature.mc.player.getBlockPos().getX() : 0.0F);
                  valueregistry.onStringFloat("u_ChunkAnimatorPlayerZ", Feature.mc.player != null ? Feature.mc.player.getBlockPos().getZ() : 0.0F);
               }
            }
         }
      }
   }

   private static synchronized RegionState getRegionState(Object value, int count) {
      RegionState regionstate = regionStates.get(value);
      if (regionstate == null) {
         regionstate = new RegionState();

         try {
            regionstate.originX = (Integer)method.invoke(value);
            regionstate.originY = (Integer)method2.invoke(value);
            regionstate.originZ = (Integer)method3.invoke(value);
         } catch (ReflectiveOperationException reflectiveoperationexception) {
            return null;
         }

         Arrays.fill(regionstate.times, -1);
         regionStates.put(value, regionstate);
      }

      if (regionstate.scanVersion != version && count - regionstate.scanTime >= 16) {
         int i = Integer.MIN_VALUE;

         for (int j = 0; j < 256; j++) {
            Object object = getObjectByObjectInt(value, j);
            Integer integer = getIntegerByIntObject(count, object);
            int k = integer == null ? -1 : integer;
            regionstate.times[j] = k;
            if (k > i) {
               i = k;
            }
         }

         regionstate.maxTime = i;
         regionstate.scanVersion = version;
         regionstate.scanTime = count;
      }

      return regionstate;
   }

   private static Integer getIntegerByIntObject(int count, Object value) {
      if (value == null) {
         return null;
      } else {
         Integer integer = weakHashMap.get(value);
         if (integer == null && isObject2(value)) {
            weakHashMap.put(value, count);
            return count;
         } else {
            return integer;
         }
      }
   }

   private static Object getObjectByObjectInt(Object value, int count) {
      try {
         return method4.invoke(value, count);
      } catch (ReflectiveOperationException reflectiveoperationexception) {
         return null;
      }
   }

   public static synchronized void onBooleanObject(boolean flag, Object value) {
      if (value != null) {
         if (flag) {
            weakHashMap.putIfAbsent(value, getInt());
         } else {
            weakHashMap.remove(value);
         }

         version++;
      }
   }

   public static synchronized void update() {
      weakHashMap.clear();
      regionStates.clear();
      version++;
   }

   private static final class RegionState {
      private final int[] times = new int[256];
      private float originX;
      private float originY;
      private float originZ;
      private int scanVersion = -1;
      private int scanTime = Integer.MIN_VALUE / 2;
      private int maxTime = Integer.MIN_VALUE / 2;
   }
}
