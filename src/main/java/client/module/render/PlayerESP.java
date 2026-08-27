package client.module.render;

import client.module.Category;
import client.module.Module;
import client.render.BoxEspRenderer;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.CompactGroupSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.ItemPickupMath;
import client.util.MathUtil;
import client.util.StringParts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class PlayerESP extends Module {
   private ListSetting targets;
   private ListSetting mode;
   private ListSetting filtrPoBrone;
   private SliderSetting thicknessLines;
   private BooleanSetting scaleOtDistancii;
   private ColorSetting colorLiniy;
   private BooleanSetting outline;
   private ColorSetting colorObvodki;
   private BooleanSetting fill;
   private ColorSetting colorZalivki;
   private BooleanSetting showDruzey;
   private BooleanSetting svoyColorDlyaDruzey;
   private ColorSetting colorLiniyDruzey;
   private ColorSetting colorZalivkiDruzey;
   private BooleanSetting svechenieBoksa;
   private SliderSetting radiusSvecheniyaBoksa;
   private SliderSetting yarkostSvecheniyaBoksa;
   private ColorSetting colorSvecheniya;
   private BooleanSetting gradient;
   private ColorSetting vtoroyColor;
   private SliderSetting speedGradienta;
   private SliderSetting radiusSvecheniya;
   private SliderSetting yarkostSvecheniya;
   private ColorSetting colorZalivkiSilueta;
   private BooleanSetting svechenieThroughWalls;
   private BooleanSetting vklyuchitAnimaciyu;
   private SliderSetting strengthAnimacii;
   private SliderSetting sizeAnimacii;
   private ColorSetting colorSvecheniyaDruzey;
   private ColorSetting vtoroyColorDruzey;
   private final BoxEspRenderer boxEspRenderer;
   private float value235;
   private boolean flag;
   private long time;
   private final ArrayList<LivingEntity> list;
   private final ArrayList<LivingEntity> list2;
   private final ArrayList<LivingEntity> list3;

   public PlayerESP() {
      super("PlayerESP", Category.RENDER);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"И", "г", "р", "o", "к", "и"}),
            StringParts.join(new String[]{"M", "о", "б", "ы"}),
            StringParts.join(new String[]{"В", "c", "е"})
         ),
         List.of(StringParts.join(new String[]{"И", "г", "р", "o", "к", "и"})),
         false
      );
      listsetting.setName("Цели");
      listsetting.setDescription("На кого работает ЕСП");
      this.targets = listsetting;
      listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(StringParts.join(new String[]{"Б", "о", "к", "с"})),
         List.of(StringParts.join(new String[]{"Б", "о", "к", "с"})),
         false
      );
      listsetting.setName("Режим");
      listsetting.setDescription("Режим отображения");
      this.mode = listsetting;
      listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"В", "c", "е"}),
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "в", " ", "б", "р", "o", "н", "е"}),
            StringParts.join(new String[]{"Т", "о", "л", "ь", "к", "о", " ", "в", " ", "н", "е", "з", "е", "р", "и", "т", "е"})
         ),
         List.of(StringParts.join(new String[]{"В", "c", "е"})),
         false
      );
      listsetting.setName("Фильтр по броне");
      listsetting.setDescription("Показывать игроков в зависимости от брони");
      this.filtrPoBrone = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 2.0, 0.1, 5.0, 0.1);
      slidersetting.setName("Толщина линии");
      slidersetting.setDescription("Толщина линий");
      this.thicknessLines = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Масштаб от дистанции");
      booleansetting.setDescription("Утолщать линии c расстоянием");
      this.scaleOtDistancii = booleansetting;
      ColorSetting colorsetting = new ColorSetting("", "", -1, true);
      colorsetting.setName("Цвет линий");
      colorsetting.setDescription("Цвет линий");
      this.colorLiniy = colorsetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Обводка");
      booleansetting1.setDescription("Тёмная обводка вокруг линий для контраста");
      this.outline = booleansetting1;
      ColorSetting colorsetting1 = new ColorSetting("", "", -16777216, true);
      colorsetting1.setName("Цвет обводки");
      colorsetting1.setDescription("Цвет обводки линий");
      this.colorObvodki = colorsetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", false);
      booleansetting2.setName("Заливка");
      booleansetting2.setDescription("Заливка внутри бокса");
      this.fill = booleansetting2;
      ColorSetting colorsetting2 = new ColorSetting("", "", 1291845631, true);
      colorsetting2.setName("Цвет заливки");
      colorsetting2.setDescription("Цвет заливки");
      this.colorZalivki = colorsetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", true);
      booleansetting3.setName("Показывать друзей");
      booleansetting3.setDescription("Отображать друзей");
      this.showDruzey = booleansetting3;
      BooleanSetting booleansetting4 = new BooleanSetting("", "", false);
      booleansetting4.setName("Свой цвет для друзей");
      booleansetting4.setDescription("Использовать отдельный цвет линий/заливки для друзей");
      this.svoyColorDlyaDruzey = booleansetting4;
      ColorSetting colorsetting3 = new ColorSetting("", "", -16711681, true);
      colorsetting3.setName("Цвет линий друзей");
      colorsetting3.setDescription("Цвет линий для друзей");
      this.colorLiniyDruzey = colorsetting3;
      ColorSetting colorsetting4 = new ColorSetting("", "", 1275133951, true);
      colorsetting4.setName("Цвет заливки друзей");
      colorsetting4.setDescription("Цвет заливки для друзей");
      this.colorZalivkiDruzey = colorsetting4;
      BooleanSetting booleansetting5 = new BooleanSetting("", "", true);
      booleansetting5.setName("Свечение бокса");
      booleansetting5.setDescription("Мягкое размытое свечение вокруг линий (post-effect)");
      this.svechenieBoksa = booleansetting5;
      SliderSetting slidersetting1 = new SliderSetting("", "", 20.0, 4.0, 96.0, 1.0, "px", 0);
      slidersetting1.setName("Радиус свечения бокса");
      slidersetting1.setDescription("Радиус размытия свечения в пикселях");
      this.radiusSvecheniyaBoksa = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 1.0, 0.1, 4.0, 0.05, "", 2);
      slidersetting2.setName("Яркость свечения бокса");
      slidersetting2.setDescription("Яркость свечения");
      this.yarkostSvecheniyaBoksa = slidersetting2;
      ColorSetting colorsetting5 = new ColorSetting("", "", -13183233, true);
      colorsetting5.setName("Цвет свечения");
      colorsetting5.setDescription("Основной цвет ореола вокруг игроков");
      this.colorSvecheniya = colorsetting5;
      BooleanSetting booleansetting6 = new BooleanSetting("", "", false);
      booleansetting6.setName("Градиент");
      booleansetting6.setDescription("Плавный переход между двумя цветами");
      this.gradient = booleansetting6;
      ColorSetting colorsetting6 = new ColorSetting("", "", -38090, true);
      colorsetting6.setName("Второй цвет");
      colorsetting6.setDescription("Второй цвет градиента");
      this.vtoroyColor = colorsetting6;
      SliderSetting slidersetting3 = new SliderSetting("", "", 0.0, 0.0, 5.0, 0.05);
      slidersetting3.setName("Скорость градиента");
      slidersetting3.setDescription("Скорость движения градиента (0 — статично)");
      this.speedGradienta = slidersetting3;
      SliderSetting slidersetting4 = new SliderSetting("", "", 24.0, 4.0, 96.0, 1.0);
      slidersetting4.setName("Радиус свечения");
      slidersetting4.setDescription("Радиус ореола в пикселях");
      this.radiusSvecheniya = slidersetting4;
      SliderSetting slidersetting5 = new SliderSetting("", "", 1.5, 0.3, 4.0, 0.05);
      slidersetting5.setName("Яркость свечения");
      slidersetting5.setDescription("Яркость ореола");
      this.yarkostSvecheniya = slidersetting5;
      ColorSetting colorsetting7 = new ColorSetting("", "", 16777215, true);
      colorsetting7.setName("Цвет заливки силуэта");
      colorsetting7.setDescription("Цвет заливки внутри силуэта");
      this.colorZalivkiSilueta = colorsetting7;
      BooleanSetting booleansetting7 = new BooleanSetting("", "", true);
      booleansetting7.setName("Свечение сквозь стены");
      booleansetting7.setDescription("Показывать свечение через блоки");
      this.svechenieThroughWalls = booleansetting7;
      BooleanSetting booleansetting8 = new BooleanSetting("", "", false);
      booleansetting8.setName("Включить анимацию");
      booleansetting8.setDescription("Волнистая огненная обводка");
      this.vklyuchitAnimaciyu = booleansetting8;
      SliderSetting slidersetting6 = new SliderSetting("", "", 1.0, 0.0, 3.0, 0.05);
      slidersetting6.setName("Сила анимации");
      slidersetting6.setDescription("Дрожание и искажение обводки");
      this.strengthAnimacii = slidersetting6;
      SliderSetting slidersetting7 = new SliderSetting("", "", 1.0, 1.0, 2.5, 0.05);
      slidersetting7.setName("Размер анимации");
      slidersetting7.setDescription("Расширение ореола вокруг силуэта");
      this.sizeAnimacii = slidersetting7;
      ColorSetting colorsetting8 = new ColorSetting("", "", -16711681, true);
      colorsetting8.setName("Цвет свечения друзей");
      colorsetting8.setDescription("Цвет ореола для друзей");
      this.colorSvecheniyaDruzey = colorsetting8;
      ColorSetting colorsetting9 = new ColorSetting("", "", -256, true);
      colorsetting9.setName("Второй цвет друзей");
      colorsetting9.setDescription("Второй цвет градиента для друзей");
      this.vtoroyColorDruzey = colorsetting9;
      this.boxEspRenderer = new BoxEspRenderer();
      this.time = System.currentTimeMillis();
      this.list = new ArrayList<>();
      this.list2 = new ArrayList<>();
      this.list3 = new ArrayList<>();
      Supplier<Boolean> supplier = this::getBoolean2;
      Supplier<Boolean> supplier1 = () -> PlayerESP.getBooleanBySupplier2(supplier);
      Supplier<Boolean> supplier2 = this::getBoolean;
      Supplier<Boolean> supplier3 = () -> this.getBooleanBySupplier(supplier2);
      this.showDruzey.setVisibleWhen(supplier2::get);
      this.filtrPoBrone.setVisibleWhen(supplier2::get);
      this.colorObvodki.setVisibleWhen(this.outline::isFlag3);
      this.colorZalivki.setVisibleWhen(this.fill::isFlag3);
      this.radiusSvecheniyaBoksa.setVisibleWhen(this.svechenieBoksa::isFlag3);
      this.yarkostSvecheniyaBoksa.setVisibleWhen(this.svechenieBoksa::isFlag3);
      this.colorLiniyDruzey.setVisibleWhen(this.svoyColorDlyaDruzey::isFlag3);
      this.colorZalivkiDruzey.setVisibleWhen(this::getBoolean3);
      CompactGroupSetting compactgroupsetting7 = new CompactGroupSetting("", "", this.thicknessLines, this.scaleOtDistancii);
      compactgroupsetting7.setName("Форма");
      compactgroupsetting7.setDescription("Параметры геометрии бокса.");
      CompactGroupSetting compactgroupsetting = compactgroupsetting7;
      compactgroupsetting.setVisibleWhen(supplier1::get);
      compactgroupsetting7 = new CompactGroupSetting("", "", this.colorLiniy, this.outline, this.colorObvodki, this.fill, this.colorZalivki);
      compactgroupsetting7.setName("Цвета");
      compactgroupsetting7.setDescription("Цвета линий, обводки и заливки.");
      CompactGroupSetting compactgroupsetting1 = compactgroupsetting7;
      compactgroupsetting1.setVisibleWhen(supplier1::get);
      compactgroupsetting7 = new CompactGroupSetting("", "", this.svechenieBoksa, this.radiusSvecheniyaBoksa, this.yarkostSvecheniyaBoksa);
      compactgroupsetting7.setName("Свечение линий");
      compactgroupsetting7.setDescription("Мягкое post-effect свечение вокруг линий бокса.");
      CompactGroupSetting compactgroupsetting2 = compactgroupsetting7;
      compactgroupsetting2.setVisibleWhen(supplier1::get);
      compactgroupsetting7 = new CompactGroupSetting("", "", this.svoyColorDlyaDruzey, this.colorLiniyDruzey, this.colorZalivkiDruzey);
      compactgroupsetting7.setName("Друзья");
      compactgroupsetting7.setDescription("Отдельные цвета для друзей.");
      CompactGroupSetting compactgroupsetting3 = compactgroupsetting7;
      compactgroupsetting3.setVisibleWhen(() -> PlayerESP.getBooleanBySupplierSupplier(supplier1, supplier2));
      this.svechenieThroughWalls.setVisibleWhen(supplier::get);
      this.vtoroyColor.setVisibleWhen(this.gradient::isFlag3);
      this.speedGradienta.setVisibleWhen(this.gradient::isFlag3);
      this.strengthAnimacii.setVisibleWhen(this.vklyuchitAnimaciyu::isFlag3);
      this.sizeAnimacii.setVisibleWhen(this.vklyuchitAnimaciyu::isFlag3);
      this.colorSvecheniyaDruzey.setVisibleWhen(supplier3::get);
      this.vtoroyColorDruzey.setVisibleWhen(() -> this.getBooleanBySupplier3(supplier3));
      compactgroupsetting7 = new CompactGroupSetting(
         "",
         "",
         this.gradient,
         this.colorSvecheniya,
         this.vtoroyColor,
         this.speedGradienta,
         this.colorZalivkiSilueta,
         this.colorSvecheniyaDruzey,
         this.vtoroyColorDruzey
      );
      compactgroupsetting7.setName("Цвета свечения");
      compactgroupsetting7.setDescription("Цвета свечения и заливки силуэта.");
      CompactGroupSetting compactgroupsetting4 = compactgroupsetting7;
      compactgroupsetting4.setVisibleWhen(supplier::get);
      compactgroupsetting7 = new CompactGroupSetting("", "", this.radiusSvecheniya, this.yarkostSvecheniya);
      compactgroupsetting7.setName("Ореол");
      compactgroupsetting7.setDescription("Параметры ореола вокруг силуэта.");
      CompactGroupSetting compactgroupsetting5 = compactgroupsetting7;
      compactgroupsetting5.setVisibleWhen(supplier::get);
      compactgroupsetting7 = new CompactGroupSetting("", "", this.vklyuchitAnimaciyu, this.strengthAnimacii, this.sizeAnimacii);
      compactgroupsetting7.setName("Анимация");
      compactgroupsetting7.setDescription("Огненная волнистая обводка.");
      CompactGroupSetting compactgroupsetting6 = compactgroupsetting7;
      compactgroupsetting6.setVisibleWhen(supplier::get);
      this.addSettings(
         new Setting[]{
            this.targets,
            this.mode,
            this.showDruzey,
            this.filtrPoBrone,
            compactgroupsetting,
            compactgroupsetting1,
            compactgroupsetting2,
            compactgroupsetting3,
            compactgroupsetting4,
            compactgroupsetting5,
            compactgroupsetting6,
            this.svechenieThroughWalls
         }
      );
   }

   public ColorSetting getVtoroyColorDruzey() {
      return this.vtoroyColorDruzey;
   }

   public boolean check3() {
      return this.gradient.isFlag3();
   }

   private Boolean getBoolean() {
      return this.targets.isString("Игроки") || this.targets.isString("Все");
   }

   private boolean isPlayerEntity(PlayerEntity playerEntity) {
      if (this.filtrPoBrone.isString("Только в незерите")) {
         return isPlayerEntity3(playerEntity);
      } else {
         return this.filtrPoBrone.isString("Только в броне") ? isPlayerEntity2(playerEntity) : true;
      }
   }

   public float getFloat() {
      return this.sizeAnimacii.getValueAsFloat();
   }

   public boolean check4() {
      return this.svechenieThroughWalls.isFlag3();
   }

   public double getDouble() {
      return this.yarkostSvecheniya.getValue();
   }

   private Boolean getBooleanBySupplier(Supplier<Boolean> supplier) {
      return this.showDruzey.isFlag3() && (Boolean)supplier.get();
   }

   public ColorSetting getColorZalivkiSilueta() {
      return this.colorZalivkiSilueta;
   }

   public float getFloat2() {
      return this.speedGradienta.getValueAsFloat();
   }

   private static Boolean getBooleanBySupplier2(Supplier<Boolean> supplier) {
      return !(Boolean)supplier.get();
   }

   private static boolean isPlayerEntity2(PlayerEntity playerEntity) {
      for (ItemStack itemstack : playerEntity.getArmorItems()) {
         if (!itemstack.isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private Boolean getBoolean2() {
      return this.mode.isString("Свечение");
   }

   public boolean check5() {
      return this.showDruzey.isFlag3();
   }

   public ColorSetting getColorSvecheniyaDruzey() {
      return this.colorSvecheniyaDruzey;
   }

   public double getDouble2() {
      return this.colorZalivkiSilueta.getValue4() / 255.0;
   }

   public boolean check6() {
      return this.vklyuchitAnimaciyu.isFlag3();
   }

   @Override
   public void onDisable() {
      this.flag = true;
      this.time = System.currentTimeMillis();
   }

   private static boolean isPlayerEntity3(PlayerEntity playerEntity) {
      ItemStack itemstack = playerEntity.getEquippedStack(EquipmentSlot.HEAD);
      ItemStack itemstack1 = playerEntity.getEquippedStack(EquipmentSlot.CHEST);
      ItemStack itemstack2 = playerEntity.getEquippedStack(EquipmentSlot.LEGS);
      ItemStack itemstack3 = playerEntity.getEquippedStack(EquipmentSlot.FEET);
      if (!itemstack.isOf(Items.NETHERITE_HELMET)) {
         return false;
      } else if (!itemstack2.isOf(Items.NETHERITE_LEGGINGS)) {
         return false;
      } else {
         return !itemstack3.isOf(Items.NETHERITE_BOOTS) ? false : itemstack1.isOf(Items.NETHERITE_CHESTPLATE) || itemstack1.isOf(Items.ELYTRA);
      }
   }

   public ColorSetting getVtoroyColor() {
      return this.vtoroyColor;
   }

   public double getDouble3() {
      return this.radiusSvecheniya.getValue();
   }

   private Boolean getBoolean3() {
      return this.svoyColorDlyaDruzey.isFlag3() && this.fill.isFlag3();
   }

   private Boolean getBooleanBySupplier3(Supplier<Boolean> supplier) {
      return (Boolean)supplier.get() && this.gradient.isFlag3();
   }

   private static Boolean getBooleanBySupplierSupplier(Supplier<Boolean> supplier, Supplier supplier2) {
      return (Boolean)supplier.get() && (Boolean)supplier2.get();
   }

   public ColorSetting getColorSvecheniya() {
      return this.colorSvecheniya;
   }

   public boolean check7() {
      return this.mode.isString("Свечение");
   }

   public boolean isPlayerEntity4(PlayerEntity playerEntity) {
      return this.isPlayerEntity(playerEntity);
   }

   public float getFloat3() {
      return this.strengthAnimacii.getValueAsFloat();
   }

   private void onBooleanListFloatWorldRenderContextIntBooleanInt(
      boolean flag, List list, float value, WorldRenderContext worldRenderContext, int count, boolean flag3, int count2
   ) {
      float f = MathUtil.getFloatByInt4(count2);
      float f1 = MathUtil.getFloatByInt(count2);
      float f2 = MathUtil.getFloatByInt2(count2);
      float f3 = MathUtil.getFloatByInt3(count2) * this.value235;
      float f4 = MathUtil.getFloatByInt4(count);
      float f5 = MathUtil.getFloatByInt(count);
      float f6 = MathUtil.getFloatByInt2(count);
      float f7 = MathUtil.getFloatByInt3(count) * this.value235;
      int i = this.colorObvodki.getInt3();
      boolean flagx = this.outline.isFlag3();
      this.boxEspRenderer.setFlag(flag);
      BoxEspRenderer boxesprenderer = this.boxEspRenderer;
      boolean flag2 = this.svechenieBoksa.isFlag3();
      float f10 = this.radiusSvecheniyaBoksa.getValueAsFloat();
      float f9 = this.yarkostSvecheniyaBoksa.getValueAsFloat();
      float f8 = f10;
      boolean flag1 = flag2;
      boxesprenderer.onBooleanFloatFloat(flag1, f9, f8);
      this.boxEspRenderer.onBooleanInt(flagx, i);
      this.boxEspRenderer.onFloatFloatListFloatFloatFloatFloatBooleanFloatFloatWorldRenderContextFloat(f4, f2, list, f3, f1, f5, f7, flag3, f6, f, worldRenderContext, value);
   }

   private List getListByWorldRenderContext(WorldRenderContext worldRenderContext) {
      boolean flagx = this.targets.isString("Игроки") || this.targets.isString("Все");
      boolean flag1 = this.targets.isString("Мобы") || this.targets.isString("Все");
      if (!flagx && !flag1) {
         return Collections.emptyList();
      } else {
         PlayerEntity playerentity = this.player();
         Vec3d vec3d = playerentity.getPos();
         boolean flag2 = this.showDruzey.isFlag3();
         ArrayList arraylist = this.list;
         arraylist.clear();
         if (flagx) {
            for (PlayerEntity playerentity1 : this.world().getPlayers()) {
               boolean flag3 = false;
               double d0 = 128.0;
               if (ItemPickupMath.isDoublePlayerEntityVec3dModuleBooleanBooleanPlayerEntity(d0, playerentity, vec3d, this, flag2, flag3, playerentity1)
                  && this.isPlayerEntity(playerentity1)) {
                  arraylist.add(playerentity1);
               }
            }
         }

         if (flag1) {
            for (Entity entity : this.clientWorld().getEntities()) {
               if (entity instanceof MobEntity mobentity && mobentity.isAlive() && !(mobentity.getPos().distanceTo(vec3d) > 128.0)) {
                  arraylist.add(mobentity);
               }
            }
         }

         return arraylist;
      }
   }

   private static float getFloatByFloat(float value) {
      return Math.clamp(value, 0.0F, 1.0F);
   }

   private void update11() {
      long i = System.currentTimeMillis();
      float f = (float)(i - this.time) / 1000.0F;
      this.time = i;
      float f1 = f * 5.0F;
      this.value235 = getFloatByFloat(this.flag ? this.value235 - f1 : this.value235 + f1);
   }

   @Override
   public void render7(WorldRenderContext worldRenderContext) {
      if (!this.notInGame() && (this.isEnabled() || this.flag)) {
         this.update11();
         if (this.flag && this.value235 <= 0.0F) {
            this.flag = false;
         } else if (!this.check7()) {
            List<LivingEntity> listx = this.getListByWorldRenderContext(worldRenderContext);
            if (!listx.isEmpty()) {
               float f = this.thicknessLines.getValueAsFloat();
               boolean flagx = this.fill.isFlag3();
               boolean flag1 = this.scaleOtDistancii.isFlag3();
               boolean flag2 = this.svoyColorDlyaDruzey.isFlag3() && this.showDruzey.isFlag3();
               if (!flag2) {
                  int l1 = this.colorLiniy.getInt3();
                  int j = this.colorZalivki.getInt3();
                  int i = l1;
                  this.onBooleanListFloatWorldRenderContextIntBooleanInt(flag1, listx, f, worldRenderContext, j, flagx, i);
               } else {
                  this.list2.clear();
                  this.list3.clear();

                  for (LivingEntity livingentity : listx) {
                     if (this.isFriend(livingentity)) {
                        this.list2.add(livingentity);
                     } else {
                        this.list3.add(livingentity);
                     }
                  }

                  if (!this.list3.isEmpty()) {
                     ArrayList arraylist3 = this.list3;
                     int i2 = this.colorLiniy.getInt3();
                     int l = this.colorZalivki.getInt3();
                     int k = i2;
                     ArrayList arraylist = arraylist3;
                     this.onBooleanListFloatWorldRenderContextIntBooleanInt(flag1, arraylist, f, worldRenderContext, l, flagx, k);
                  }

                  if (!this.list2.isEmpty()) {
                     ArrayList arraylist2 = this.list2;
                     int k1 = this.colorLiniyDruzey.getInt3();
                     int j1 = this.colorZalivkiDruzey.getInt3();
                     int i1 = k1;
                     ArrayList arraylist1 = arraylist2;
                     this.onBooleanListFloatWorldRenderContextIntBooleanInt(flag1, arraylist1, f, worldRenderContext, j1, flagx, i1);
                  }
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
      this.time = System.currentTimeMillis();
      this.value235 = 0.0F;
      this.flag = false;
   }
}
