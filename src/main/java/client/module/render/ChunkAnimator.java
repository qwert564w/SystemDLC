package client.module.render;

import client.data.BlockUpdateCapture;
import client.enums.Easing;
import client.module.Category;
import client.module.Module;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.ChunkAnimatorAccess;
import client.util.StringParts;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ChunkAnimator extends Module {
   private ListSetting mode;
   private SliderSetting dlitelnost;
   private ListSetting izing;
   private final Map<Long, BlockUpdateCapture> map;
   private final Set<Long> set;
   private boolean flag;
   private double value235;
   private double value236;
   private double value237;
   private final float[] floatArray;
   private World world;
   private int value238;

   public ChunkAnimator() {
      super("ChunkAnimator", Category.RENDER);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         List.of(
            StringParts.join(new String[]{"С", "н", "и", "з", "у"}),
            StringParts.join(new String[]{"С", "в", "е", "р", "х", "у"}),
            StringParts.join(new String[]{"Г", "и", "б", "р", "и", "д"}),
            StringParts.join(new String[]{"С", "б", "о", "к", "у"})
         ),
         List.of(StringParts.join(new String[]{"С", "н", "и", "з", "у"})),
         false
      );
      listsetting.setName("Режим");
      listsetting.setDescription("Откуда выезжают чанки (Гибрид — ниже горизонта снизу, выше — сверху)");
      this.mode = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 1000.0, 100.0, 5000.0, 50.0, StringParts.join(new String[]{"м", "c"}), 0);
      slidersetting.setName("Длительность");
      slidersetting.setDescription("Время анимации одной секции");
      this.dlitelnost = slidersetting;
      listsetting = new ListSetting(
         "",
         "",
         List.of(
            StringParts.join(new String[]{"Д", "у", "г", "а"}),
            StringParts.join(new String[]{"С", "и", "н", "у", "с"}),
            StringParts.join(new String[]{"Э", "к", "с", "п", "о", "н", "е", "н", "т", "а"}),
            StringParts.join(new String[]{"К", "в", "а", "р", "т"})
         ),
         List.of(StringParts.join(new String[]{"Д", "у", "г", "а"})),
         false
      );
      listsetting.setName("Изинг");
      listsetting.setDescription("Функция сглаживания анимации");
      this.izing = listsetting;
      this.map = new HashMap<>();
      this.set = new HashSet<>();
      this.flag = false;
      this.floatArray = new float[3];
      this.world = null;
      this.value238 = 0;
      this.addSettings(new Setting[]{this.mode, this.dlitelnost, this.izing});
   }

   private int getInt() {
      return this.world().getDimension().minY();
   }

   private static boolean isIntIntLongLong(int count, int count2, long time, Long time2) {
      long i = time2;
      return getLongByIntIntLong(count2, count, i) > time;
   }

   public void setFlag() {
      this.flag = false;
   }

   public int getInt2() {
      String s = this.mode.getString2();
      if (s == null) {
         return 0;
      } else {
         return switch (s) {
            case "Сверху" -> 1;
            case "Гибрид" -> 2;
            case "Сбоку" -> 3;
            default -> 0;
         };
      }
   }

   public void update11() {
      this.map.clear();
      this.set.clear();
      ChunkAnimatorAccess.update();
   }

   @Override
   public void onDisable() {
      this.update12();
   }

   private int getInt3() {
      return this.world().getDimension().minY() + this.world().getDimension().height();
   }

   public int getInt4() {
      String s = this.izing.getString2();
      if (s == null) {
         return 7;
      } else {
         return switch (s) {
            case "Синус" -> 6;
            case "Экспонента" -> 5;
            case "Кварт" -> 3;
            default -> 7;
         };
      }
   }

   private Direction getDirectionByIntInt(int count, int count2) {
      BlockPos blockpos = this.player().getBlockPos();
      int i = blockpos.getX() - (count2 + 8);
      int j = blockpos.getZ() - (count + 8);
      if (Math.abs(i) > Math.abs(j)) {
         return i > 0 ? Direction.EAST : Direction.WEST;
      } else {
         return j > 0 ? Direction.SOUTH : Direction.NORTH;
      }
   }

   public int getInt5() {
      return this.dlitelnost.getInt2();
   }

   private Easing getEasing() {
      String s = this.izing.getString2();
      if (s == null) {
         return Easing.CIRC_OUT;
      } else {
         return switch (s) {
            case "Синус" -> Easing.SINE_OUT;
            case "Экспонента" -> Easing.EXPO_OUT;
            case "Кварт" -> Easing.QUART_OUT;
            default -> Easing.CIRC_OUT;
         };
      }
   }

   private static boolean isIntIntLongLong2(int count, int count2, long time, Long time2) {
      long i = time2;
      return getLongByIntIntLong(count2, count, i) > time;
   }

   public void onDoubleDoubleDouble(double value, double value2, double value3) {
      this.value235 = value2;
      this.value236 = value3;
      this.value237 = value;
      this.flag = true;
   }

   private static long getLongByIntIntLong(int count, int count2, long time) {
      long i = count2 - (BlockPos.unpackLongX(time) + 8);
      long j = count - (BlockPos.unpackLongZ(time) + 8);
      return i * i + j * j;
   }

   private void update12() {
      this.map.clear();
      this.set.clear();
      ChunkAnimatorAccess.update();
      this.flag = false;
      this.world = null;
      this.value238 = 0;
   }

   public float[] getFloatArrayByFloatFloatFloat(float value, float value3, float value4) {
      if (this.flag && this.world() != null) {
         double d0 = value4 + this.value235;
         double d1 = value + this.value236;
         double d2 = value3 + this.value237;
         int i = (int)Math.round(d0);
         int j = (int)Math.round(d1);
         int k = (int)Math.round(d2);
         if ((i & 15) == 0 && (j & 15) == 0 && (k & 15) == 0) {
            if (!(Math.abs(d0 - i) > 0.01) && !(Math.abs(d1 - j) > 0.01) && !(Math.abs(d2 - k) > 0.01)) {
               long l = BlockPos.asLong(i, j, k);
               BlockUpdateCapture blockupdatecapture = this.map.get(l);
               if (blockupdatecapture == null) {
                  if (!this.set.add(l)) {
                     return null;
                  }

                  blockupdatecapture = new BlockUpdateCapture(i, j, k);
                  this.map.put(l, blockupdatecapture);
               }

               long i1 = System.currentTimeMillis();
               if (blockupdatecapture.time == -1L) {
                  blockupdatecapture.time = i1;
                  if ("Сбоку".equals(this.mode.getString2()) && this.player() != null) {
                     blockupdatecapture.direction = this.getDirectionByIntInt(k, i);
                  }
               }

               float f = (float)(i1 - blockupdatecapture.time);
               float f1 = this.dlitelnost.getValueAsFloat();
               if (f >= f1) {
                  this.map.remove(l);
                  return null;
               } else {
                  Easing easing = this.getEasing();
                  String s = this.mode.getString2();
                  if ("Гибрид".equals(s)) {
                     s = blockupdatecapture.value2 < 63.0 ? "Снизу" : "Сверху";
                  }

                  float f2 = 0.0F;
                  float f3 = 0.0F;
                  float f4 = 0.0F;
                  if ("Снизу".equals(s)) {
                     float f5 = Math.abs(blockupdatecapture.value2 - this.getInt());
                     float f12 = -f5;
                     float f6 = 0.0F;
                     f3 = f12 + easing.getFloatByFloatFloatFloatFloat(f, f6, f1, f5);
                  } else if ("Сверху".equals(s)) {
                     float f10 = Math.max(0, this.getInt3() - blockupdatecapture.value2);
                     float f7 = 0.0F;
                     f3 = f10 - easing.getFloatByFloatFloatFloatFloat(f, f7, f1, f10);
                  } else {
                     if (blockupdatecapture.direction == null) {
                        return null;
                     }

                     float f9 = 200.0F;
                     float f8 = 0.0F;
                     float f11 = -(200.0F - easing.getFloatByFloatFloatFloatFloat(f, f8, f1, f9));
                     f2 = blockupdatecapture.direction.getOffsetX() * f11;
                     f4 = blockupdatecapture.direction.getOffsetZ() * f11;
                  }

                  if (f2 == 0.0F && f3 == 0.0F && f4 == 0.0F) {
                     return null;
                  } else {
                     this.floatArray[0] = value4 + f2;
                     this.floatArray[1] = value + f3;
                     this.floatArray[2] = value3 + f4;
                     return this.floatArray;
                  }
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   @Override
   public void onEnable() {
      this.update12();
   }

   @Override
   public void update8() {
      World worldx = this.inGame() ? this.world() : null;
      if (worldx != this.world) {
         this.world = worldx;
         this.map.clear();
         this.set.clear();
      }

      if (worldx != null && this.player() != null) {
         if (++this.value238 >= 20) {
            this.value238 = 0;
            int i = ((Integer)this.options().getViewDistance().getValue() + 2) * 16;
            long j = (long)i * i;
            int k = this.player().getBlockPos().getX();
            int l = this.player().getBlockPos().getZ();
            this.set.removeIf(p0 -> ChunkAnimator.isIntIntLongLong(k, l, j, p0));
            this.map.keySet().removeIf(p0 -> ChunkAnimator.isIntIntLongLong2(k, l, j, p0));
         }
      }
   }
}
