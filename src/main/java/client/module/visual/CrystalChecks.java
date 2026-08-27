package client.module.visual;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class CrystalChecks extends Module {
   private BooleanSetting menyatSize;
   private SliderSetting size;
   private SliderSetting sdvigX;
   private SliderSetting sdvigY;
   private BooleanSetting onlySvoy;
   private final Set<Integer> set;
   private final Set<Integer> set2;
   private int value235;
   private long time;

   public CrystalChecks() {
      super("CrystalChecks", Category.VISUAL);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Менять размер");
      booleansetting.setDescription("Изменять размер и положение кристалла");
      this.menyatSize = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 2.0, 0.1, 5.0, 0.05);
      slidersetting.setName("Размер");
      slidersetting.setDescription("Размер кристалла");
      this.size = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 0.0, -3.0, 3.0, 0.05);
      slidersetting1.setName("Сдвиг X");
      slidersetting1.setDescription("Сдвиг кристалла по оси X");
      this.sdvigX = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 0.0, -3.0, 3.0, 0.05);
      slidersetting2.setName("Сдвиг Y");
      slidersetting2.setDescription("Сдвиг кристалла по оси Y");
      this.sdvigY = slidersetting2;
      booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Только свой");
      booleansetting.setDescription("Применять только на кристаллы, поставленные вами");
      this.onlySvoy = booleansetting;
      this.set = ConcurrentHashMap.newKeySet();
      this.set2 = ConcurrentHashMap.newKeySet();
      this.value235 = -1;
      this.time = -1L;
      this.addSettings(new Setting[]{this.menyatSize, this.size, this.sdvigX, this.sdvigY, this.onlySvoy});
      this.size.setVisibleWhen(this.menyatSize::isFlag3);
      this.sdvigX.setVisibleWhen(this.menyatSize::isFlag3);
      this.sdvigY.setVisibleWhen(this.menyatSize::isFlag3);
   }

   public SliderSetting getSdvigX() {
      return this.sdvigX;
   }

   private boolean isInteger(Integer value) {
      return this.world().getEntityById(value) == null;
   }

   @Override
   public void update() {
      if (this.onlySvoy.isFlag3() && !this.notInGame()) {
         int i = 0;

         for (Hand hand : Hand.values()) {
            if (this.clientPlayer().getStackInHand(hand).isOf(Items.END_CRYSTAL)) {
               i += this.clientPlayer().getStackInHand(hand).getCount();
            }
         }

         if (this.value235 > 0 && i < this.value235) {
            this.time = this.world().getTime();
         }

         this.value235 = i;
         this.set.removeIf(this::isInteger);
         this.set2.removeIf(this::isInteger2);
      }
   }

   public SliderSetting getSdvigY() {
      return this.sdvigY;
   }

   @Override
   public void onDisable() {
      this.set.clear();
      this.set2.clear();
      this.value235 = -1;
      this.time = -1L;
   }

   public BooleanSetting getMenyatSize() {
      return this.menyatSize;
   }

   private boolean isInteger2(Integer value) {
      return this.world().getEntityById(value) == null;
   }

   public SliderSetting getSize() {
      return this.size;
   }

   public boolean isEntity(Entity entity2) {
      if (!this.onlySvoy.isFlag3()) {
         return true;
      } else {
         int i = entity2.getId();
         if (this.set2.add(i) && this.world() != null && this.world().getTime() - this.time <= 1L) {
            this.set.add(i);
         }

         return this.set.contains(i);
      }
   }

   @Override
   public void onEnable() {
   }
}
