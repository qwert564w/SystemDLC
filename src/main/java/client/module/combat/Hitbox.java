package client.module.combat;

import client.concurrent.SystemClient;
import client.data.AnimatedFloat;
import client.module.Category;
import client.module.Module;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.AttackEvent;
import client.util.CritChecks;
import client.util.InteractEvent;
import client.util.RandomUtil;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Hitbox extends Module {
   private static String text = "Обычная";
   private static String text2 = "Новая";
   private static String text3 = "Кастомная";
   private static String text4 = "Игроки";
   private static String text5 = "Мобы";
   private static String text6 = "Все";
   private ListSetting targets;
   private BooleanSetting onlyCOruzhiem;
   private BooleanSetting onlyVBrone;
   private BooleanSetting onlyNaProtivnike;
   private SliderSetting timeSbrosa;
   private BooleanSetting nevidimyyHitboks;
   private BooleanSetting skrytIndikator;
   private BooleanSetting fiksirovannyySize;
   private SliderSetting fiksSize;
   private SliderSetting minSize;
   private SliderSetting maxSize;
   private SliderSetting shag;
   private SliderSetting size;
   private ListSetting modeRotacii;
   private BooleanSetting obhod;
   private BooleanSetting antiReach;
   private SliderSetting rangeAntiReach;
   private BooleanSetting feykSving;
   private SliderSetting range;
   private SliderSetting speedNavodki;
   private SliderSetting speedSlezheniya;
   private SliderSetting paddingX;
   private SliderSetting randomOtstupaX;
   private SliderSetting randomNaTargets;
   private BooleanSetting uchityvatChuvstv;
   private BooleanSetting stopOnNavedenii;
   private BooleanSetting zamedlenieVblizi;
   private SliderSetting porogZamedleniya;
   private SliderSetting faktorZamedleniya;
   private BooleanSetting krivayaTraektoriya;
   private SliderSetting amplitudaKrivoy;
   private SliderSetting chastotaKrivoy;
   private SliderSetting randomShum;
   private SliderSetting chastotaRandoma;
   private BooleanSetting fleks;
   private HotkeySetting sbrositSize;
   private HotkeySetting uvelichitSize;
   private HotkeySetting umenshitSize;
   private double value235;
   private LivingEntity livingEntity;
   private Vec3d vec3d;
   private LivingEntity livingEntity2;
   private long time;
   private boolean flag;
   private boolean flag2;
   private long time2;
   private float value236;
   private float value237;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private boolean flag6;
   private static double value238 = 0.5;
   private long time3;
   private long time4;
   private long time5;
   private LivingEntity livingEntity3;
   private boolean flag7;
   private double value239;
   private double value240;
   private double value241;
   private double value242;
   private double value243;
   private double value244;
   private double value245;
   private boolean flag8;
   private Vec3d vec3d2;
   private long time6;
   private double value246;
   private double value247;
   private long time7;
   private float value248;
   private int value249;
   private boolean flag9;
   private float value250;

   public Hitbox() {
      super("Hitbox", Category.COMBAT);
      ListSetting listsetting = new ListSetting("", "", Arrays.asList(text5, text4, text6), List.of(text4), false);
      listsetting.setName("Цели");
      listsetting.setDescription("Какие сущности увеличивать");
      this.targets = listsetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Только c оружием");
      booleansetting.setDescription("Работает только при оружии в руке");
      this.onlyCOruzhiem = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Только в броне");
      booleansetting1.setDescription("Работает только на игроках в броне");
      this.onlyVBrone = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", false);
      booleansetting2.setName("Только на противнике");
      booleansetting2.setDescription("Увеличивать хитбокс только для последней атакованной цели");
      this.onlyNaProtivnike = booleansetting2;
      SliderSetting slidersetting = new SliderSetting("", "", 3.0, 1.0, 7.0, 0.5);
      slidersetting.setName("Время сброса");
      slidersetting.setDescription("Время в секундах до сброса цели");
      this.timeSbrosa = slidersetting;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", false);
      booleansetting3.setName("Невидимый хитбокс");
      booleansetting3.setDescription("Не показывать увеличенный хитбокс");
      this.nevidimyyHitboks = booleansetting3;
      BooleanSetting booleansetting4 = new BooleanSetting("", "", true);
      booleansetting4.setName("Скрыть индикатор");
      booleansetting4.setDescription("Скрыть индикатор атаки на увеличенном хитбоксе");
      this.skrytIndikator = booleansetting4;
      BooleanSetting booleansetting5 = new BooleanSetting("", "", true);
      booleansetting5.setName("Фиксированный размер");
      booleansetting5.setDescription("Использовать фиксированный размер хитбокса");
      this.fiksirovannyySize = booleansetting5;
      SliderSetting slidersetting1 = new SliderSetting("", "", 0.7, 0.3, 1.2, 0.05);
      slidersetting1.setName("Фикс. размер");
      slidersetting1.setDescription("Фиксированный размер хитбокса");
      this.fiksSize = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 0.3, 0.2, 1.0, 0.05);
      slidersetting2.setName("Мин. размер");
      slidersetting2.setDescription("Минимальный размер хитбокса");
      this.minSize = slidersetting2;
      SliderSetting slidersetting3 = new SliderSetting("", "", 2.0, 0.5, 5.0, 0.1);
      slidersetting3.setName("Макс. размер");
      slidersetting3.setDescription("Максимальный размер хитбокса");
      this.maxSize = slidersetting3;
      SliderSetting slidersetting4 = new SliderSetting("", "", 0.1, 0.05, 0.5, 0.05);
      slidersetting4.setName("Шаг");
      slidersetting4.setDescription("Шаг изменения размера");
      this.shag = slidersetting4;
      SliderSetting slidersetting5 = new SliderSetting("", "", 0.3F, this.minSize.getValue(), this.maxSize.getValue(), this.shag.getValue());
      slidersetting5.setName("Размер");
      slidersetting5.setDescription("Размер хитбокса");
      this.size = slidersetting5;
      listsetting = new ListSetting("", "", Arrays.asList(text, text2, text3), List.of(text), false);
      listsetting.setName("Режим ротации");
      listsetting.setDescription("Тип ротации обхода");
      this.modeRotacii = listsetting;
      BooleanSetting booleansetting6 = new BooleanSetting("", "", true);
      booleansetting6.setName("Обход");
      booleansetting6.setDescription("Включить автоприцел на цели");
      this.obhod = booleansetting6;
      BooleanSetting booleansetting7 = new BooleanSetting("", "", true);
      booleansetting7.setName("Анти-Reach");
      booleansetting7.setDescription("Отменяет атаки дальше 3 блоков при увеличенном хитбоксе");
      this.antiReach = booleansetting7;
      SliderSetting slidersetting6 = new SliderSetting("", "", 2.85, 1.0, 3.0, 0.01);
      slidersetting6.setName("Дистанция Анти-Reach");
      slidersetting6.setDescription("Максимальная дистанция для атаки при увеличенном хитбоксе");
      this.rangeAntiReach = slidersetting6;
      BooleanSetting booleansetting8 = new BooleanSetting("", "", true);
      booleansetting8.setName("Фейк свинг");
      booleansetting8.setDescription("Визуальный свинг при отмене удара");
      this.feykSving = booleansetting8;
      SliderSetting slidersetting7 = new SliderSetting("", "", 5.0, 3.0, 10.0, 0.5);
      slidersetting7.setName("Дистанция");
      slidersetting7.setDescription("Дистанция поиска цели");
      this.range = slidersetting7;
      SliderSetting slidersetting8 = new SliderSetting("", "", 0.85, 0.1, 2.0, 0.05);
      slidersetting8.setName("Скорость наводки");
      slidersetting8.setDescription("Скорость наведения на оригинальный хитбокс и возврата");
      this.speedNavodki = slidersetting8;
      SliderSetting slidersetting9 = new SliderSetting("", "", 0.85, 0.1, 2.0, 0.05);
      slidersetting9.setName("Скорость слежения");
      slidersetting9.setDescription("Скорость поворота камеры при движении цели");
      this.speedSlezheniya = slidersetting9;
      SliderSetting slidersetting10 = new SliderSetting("", "", 0.0, -0.3, 0.3, 0.01);
      slidersetting10.setName("Отступ X");
      slidersetting10.setDescription("Горизонтальный отступ от центра хитбокса");
      this.paddingX = slidersetting10;
      SliderSetting slidersetting11 = new SliderSetting("", "", 0.05, 0.0, 0.3, 0.01);
      slidersetting11.setName("Рандом отступа X");
      slidersetting11.setDescription("Рандомизация горизонтального отступа");
      this.randomOtstupaX = slidersetting11;
      SliderSetting slidersetting12 = new SliderSetting("", "", 0.05, 0.0, 0.3, 0.01);
      slidersetting12.setName("Рандом на цели");
      slidersetting12.setDescription("Рандомизация при наведении на ориг. хитбокс");
      this.randomNaTargets = slidersetting12;
      BooleanSetting booleansetting9 = new BooleanSetting("", "", true);
      booleansetting9.setName("Учитывать чувств.");
      booleansetting9.setDescription("Масштабировать скорость по чувствительности мыши");
      this.uchityvatChuvstv = booleansetting9;
      BooleanSetting booleansetting10 = new BooleanSetting("", "", false);
      booleansetting10.setName("Стоп при наведении");
      booleansetting10.setDescription("Не двигать камеру если прицел уже на цели");
      this.stopOnNavedenii = booleansetting10;
      BooleanSetting booleansetting11 = new BooleanSetting("", "", true);
      booleansetting11.setName("Замедление вблизи");
      booleansetting11.setDescription("Замедлять наводку когда прицел близко к цели");
      this.zamedlenieVblizi = booleansetting11;
      SliderSetting slidersetting13 = new SliderSetting("", "", 10.0, 1.0, 30.0, 1.0);
      slidersetting13.setName("Порог замедления");
      slidersetting13.setDescription("Угловое расстояние для замедления");
      this.porogZamedleniya = slidersetting13;
      SliderSetting slidersetting14 = new SliderSetting("", "", 0.3, 0.1, 1.0, 0.1);
      slidersetting14.setName("Фактор замедления");
      slidersetting14.setDescription("Минимальный множитель скорости при замедлении");
      this.faktorZamedleniya = slidersetting14;
      BooleanSetting booleansetting12 = new BooleanSetting("", "", false);
      booleansetting12.setName("Кривая траектория");
      booleansetting12.setDescription("Наводиться по кривой a не по прямой");
      this.krivayaTraektoriya = booleansetting12;
      SliderSetting slidersetting15 = new SliderSetting("", "", 1.5, 0.5, 5.0, 0.1);
      slidersetting15.setName("Амплитуда кривой");
      slidersetting15.setDescription("Сила отклонения кривой");
      this.amplitudaKrivoy = slidersetting15;
      SliderSetting slidersetting16 = new SliderSetting("", "", 0.3, 0.1, 1.5, 0.05);
      slidersetting16.setName("Частота кривой");
      slidersetting16.setDescription("Скорость прогресса кривой");
      this.chastotaKrivoy = slidersetting16;
      SliderSetting slidersetting17 = new SliderSetting("", "", 1.0, 0.0, 5.0, 0.1);
      slidersetting17.setName("Рандом шум");
      slidersetting17.setDescription("Амплитуда рандомного шума");
      this.randomShum = slidersetting17;
      SliderSetting slidersetting18 = new SliderSetting("", "", 0.3, 0.05, 2.0, 0.05);
      slidersetting18.setName("Частота рандома");
      slidersetting18.setDescription("Частота рандомного шума");
      this.chastotaRandoma = slidersetting18;
      BooleanSetting booleansetting13 = new BooleanSetting("", "", false);
      booleansetting13.setName("Флекс");
      booleansetting13.setDescription("Периодически делать 360 разворот");
      this.fleks = booleansetting13;
      HotkeySetting hotkeysetting = new HotkeySetting("", "", 61, this::update14);
      hotkeysetting.setName("Сбросить размер");
      hotkeysetting.setDescription("Сбросить размер хитбокса");
      this.sbrositSize = hotkeysetting;
      HotkeySetting hotkeysetting1 = new HotkeySetting("", "", 265, this::update23);
      hotkeysetting1.setName("Увеличить размер");
      hotkeysetting1.setDescription("Увеличить хитбокс");
      this.uvelichitSize = hotkeysetting1;
      HotkeySetting hotkeysetting2 = new HotkeySetting("", "", 264, this::update20);
      hotkeysetting2.setName("Уменьшить размер");
      hotkeysetting2.setDescription("Уменьшить хитбокс");
      this.umenshitSize = hotkeysetting2;
      this.value235 = 0.3;
      this.livingEntity = null;
      this.vec3d = null;
      this.livingEntity2 = null;
      this.time = 0L;
      this.flag = false;
      this.flag2 = false;
      this.time2 = 0L;
      this.value236 = 0.0F;
      this.value237 = 0.0F;
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.value239 = 0.0;
      this.value240 = 0.0;
      this.value241 = 0.0;
      this.value242 = 0.0;
      this.value243 = 0.1;
      this.value244 = 0.08;
      this.value245 = 1.0;
      this.flag8 = false;
      this.vec3d2 = null;
      this.time6 = 0L;
      this.value246 = 0.0;
      this.value247 = 0.0;
      this.time7 = 0L;
      this.value248 = 0.0F;
      this.value249 = 0;
      this.flag9 = false;
      this.value250 = 0.0F;
      this.addSettings(
         new Setting[]{
            this.targets,
            this.onlyCOruzhiem,
            this.onlyVBrone,
            this.fiksirovannyySize,
            this.fiksSize,
            this.onlyNaProtivnike,
            this.timeSbrosa,
            this.nevidimyyHitboks,
            this.skrytIndikator,
            this.modeRotacii,
            this.obhod,
            this.antiReach,
            this.rangeAntiReach,
            this.feykSving,
            this.range,
            this.speedNavodki,
            this.speedSlezheniya,
            this.paddingX,
            this.randomOtstupaX,
            this.randomNaTargets,
            this.uchityvatChuvstv,
            this.stopOnNavedenii,
            this.zamedlenieVblizi,
            this.porogZamedleniya,
            this.faktorZamedleniya,
            this.krivayaTraektoriya,
            this.amplitudaKrivoy,
            this.chastotaKrivoy,
            this.randomShum,
            this.chastotaRandoma,
            this.fleks,
            this.minSize,
            this.maxSize,
            this.shag,
            this.size,
            this.sbrositSize,
            this.uvelichitSize,
            this.umenshitSize
         }
      );
      this.fiksSize.setVisibleWhen(this.fiksirovannyySize::isFlag3);
      this.timeSbrosa.setVisibleWhen(this.onlyNaProtivnike::isFlag3);
      this.minSize.setVisibleWhen(this::getBoolean14);
      this.maxSize.setVisibleWhen(this::getBoolean7);
      this.shag.setVisibleWhen(this::getBoolean13);
      this.size.setVisibleWhen(this::getBoolean10);
      this.rangeAntiReach.setVisibleWhen(this.antiReach::isFlag3);
      this.range.setVisibleWhen(this.obhod::isFlag3);
      this.speedNavodki.setVisibleWhen(this.obhod::isFlag3);
      this.speedSlezheniya.setVisibleWhen(this.obhod::isFlag3);
      this.modeRotacii.setVisibleWhen(this.obhod::isFlag3);
      this.paddingX.setVisibleWhen(this::getBoolean15);
      this.randomOtstupaX.setVisibleWhen(this::getBoolean17);
      this.randomNaTargets.setVisibleWhen(this::getBoolean4);
      this.uchityvatChuvstv.setVisibleWhen(this::getBoolean8);
      this.stopOnNavedenii.setVisibleWhen(this::getBoolean16);
      this.zamedlenieVblizi.setVisibleWhen(this::getBoolean9);
      this.porogZamedleniya.setVisibleWhen(this::getBoolean3);
      this.faktorZamedleniya.setVisibleWhen(this::getBoolean11);
      this.krivayaTraektoriya.setVisibleWhen(this::getBoolean6);
      this.amplitudaKrivoy.setVisibleWhen(this::getBoolean5);
      this.chastotaKrivoy.setVisibleWhen(this::getBoolean12);
      this.randomShum.setVisibleWhen(this::getBoolean2);
      this.chastotaRandoma.setVisibleWhen(this::getBoolean);
      this.fleks.setVisibleWhen(this::getBoolean18);
      this.update17();
   }

   private void update11() {
      if (this.isEnabled() && this.fiksirovannyySize.isFlag3()) {
         this.size.setDouble2(this.fiksSize.getValue());
      }
   }

   private Boolean getBoolean() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2());
   }

   private void update12() {
      if (this.isEnabled()) {
         if (this.fiksirovannyySize.isFlag3()) {
            this.value235 = this.size.getValue();
            this.size.setDouble2(this.fiksSize.getValue());
         } else {
            this.size.setDouble2(this.value235);
         }
      }
   }

   private Boolean getBoolean2() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2());
   }

   private Boolean getBoolean3() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2()) && this.zamedlenieVblizi.isFlag3();
   }

   private boolean isLivingEntity(LivingEntity livingEntity) {
      if (!this.isEntity(livingEntity)) {
         return false;
      } else if (!livingEntity.isAlive()) {
         return false;
      } else {
         if (livingEntity instanceof PlayerEntity playerentity) {
            if (playerentity.isSpectator()) {
               return false;
            }

            if (this.isFriend(playerentity)) {
               return false;
            }

            if (this.onlyVBrone.isFlag3() && !this.isPlayerEntity(playerentity)) {
               return false;
            }
         }

         return true;
      }
   }

   private Boolean getBoolean4() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2());
   }

   private Boolean getBoolean5() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2()) && this.krivayaTraektoriya.isFlag3();
   }

   @Override
   public void render2(WorldRenderContext worldRenderContext) {
      boolean flagx = !this.onlyCOruzhiem.isFlag3() || this.check4();

      for (Entity entity : worldRenderContext.getClientWorld().getEntities()) {
         if (entity instanceof LivingEntity livingentity && livingentity != this.player() && this.isEntity(livingentity)) {
            if (livingentity instanceof PlayerEntity playerentity && this.isFriend(playerentity)) {
               this.onLivingEntityFloat(playerentity, 0.0F);
            } else {
               float f = this.getFloatByBooleanLivingEntity(flagx, livingentity);
               this.onLivingEntityFloat(livingentity, f);
            }
         }
      }
   }

   public boolean check3() {
      if (this.player() != null && this.world() != null) {
         for (Entity entity : this.clientWorld().getEntities()) {
            if (entity instanceof LivingEntity livingentity
               && livingentity != this.player()
               && this.isLivingEntity(livingentity)
               && (!this.onlyNaProtivnike.isFlag3() || livingentity == this.livingEntity2)) {
               double d0 = this.player().distanceTo(livingentity);
               if (!(d0 > this.range.getValue())) {
                  boolean flagx = true;
                  if (this.isBooleanEntity(flagx, livingentity)) {
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static double getDoubleByDouble(double value) {
      return RandomUtil.getDoubleByDouble(value);
   }

   private void update13() {
      if (this.world() != null) {
         for (Entity entity : this.clientWorld().getEntities()) {
            if (entity instanceof LivingEntity livingentity && livingentity != this.player()) {
               entity.setBoundingBox(this.getBoxByEntity2(entity));
            }
         }
      }
   }

   public boolean isLivingEntity2(LivingEntity livingEntity) {
      if (this.isEnabled() && livingEntity != null) {
         if (this.obhod.isFlag3() && !text.equals(this.modeRotacii.getString2()) && this.size.getValueAsFloat() > 0.3F) {
            if (!this.isEntity(livingEntity)) {
               return false;
            }

            boolean flagx = !this.onlyCOruzhiem.isFlag3() || this.check4();
            if (this.getFloatByBooleanLivingEntity(flagx, livingEntity) > this.getFloatByEntity(livingEntity) && !this.isLivingEntity4(livingEntity)) {
               boolean flag1 = false;
               if (!this.isBooleanEntity(flag1, livingEntity)) {
                  return true;
               }
            }
         }

         if (!this.antiReach.isFlag3() || !(this.size.getValueAsFloat() > 0.3F)) {
            return false;
         } else if (!this.isEntity(livingEntity)) {
            return false;
         } else if (livingEntity instanceof PlayerEntity playerentity && this.onlyVBrone.isFlag3() && !this.isPlayerEntity(playerentity)) {
            return false;
         } else {
            Box box = this.getBoxByEntity2(livingEntity);
            Vec3d vec3dx = this.player().getEyePos();
            Vec3d vec3d1 = this.getVec3dByBoxVec3d(box, vec3dx);
            double d0 = vec3dx.distanceTo(vec3d1);
            return d0 > this.rangeAntiReach.getValue();
         }
      } else {
         return false;
      }
   }

   private Vec3d getVec3dByLivingEntity(LivingEntity livingEntity) {
      Box box = this.getBoxByEntity2(livingEntity);
      double d0 = (box.minX + box.maxX) / 2.0;
      double d1 = (box.minY + box.maxY) / 2.0;
      double d2 = (box.minZ + box.maxZ) / 2.0;
      double d3 = (box.maxX - box.minX) / 2.0;
      double d4 = (box.maxY - box.minY) / 2.0;
      ThreadLocalRandom threadlocalrandom = ThreadLocalRandom.current();
      double d5 = this.randomNaTargets.getValue();
      double d6 = (threadlocalrandom.nextDouble() * 2.0 - 1.0) * Math.min(d5, d3 - 0.05);
      double d7 = (threadlocalrandom.nextDouble() * 2.0 - 1.0) * Math.min(d5, d4 - 0.05);
      double d8 = (threadlocalrandom.nextDouble() * 2.0 - 1.0) * Math.min(d5, d3 - 0.05);
      return new Vec3d(
         MathHelper.clamp(d0 + d6, box.minX + 0.05, box.maxX - 0.05),
         MathHelper.clamp(d1 + d7, box.minY + 0.05, box.maxY - 0.05),
         MathHelper.clamp(d2 + d8, box.minZ + 0.05, box.maxZ - 0.05)
      );
   }

   @Override
   public void onAnimatedFloat(AnimatedFloat animatedFloat) {
      if (this.player() != null && this.player().isUsingItem() && !this.flag2) {
         this.flag2 = true;
         this.time2 = System.currentTimeMillis();
         if (!this.fiksirovannyySize.isFlag3()) {
            this.value235 = this.size.getValue();
         }

         this.size.setDouble2(0.3F);
      } else if (this.player() != null && !this.player().isUsingItem() && this.flag2 && this.time2 > 0L) {
         long i = System.currentTimeMillis() - this.time2;
         if (i > 50L) {
            this.flag2 = false;
            this.time2 = 0L;
            double d0 = this.fiksirovannyySize.isFlag3() ? this.fiksSize.getValue() : this.value235;
            this.size.setDouble2(d0);
         }
      }

      float f9 = this.size.getValueAsFloat();
      if (f9 <= 0.3F && !this.flag) {
         if (this.flag4) {
            this.update18();
         }
      } else {
         this.update15();
         if (!this.obhod.isFlag3() || this.player() == null || this.world() == null) {
            this.setAnimatedFloat3(animatedFloat);
         } else if (this.onlyCOruzhiem.isFlag3() && !this.check4()) {
            this.setAnimatedFloat3(animatedFloat);
         } else {
            LivingEntity livingentity = this.getLivingEntity();
            if (livingentity != null && text3.equals(this.modeRotacii.getString2())) {
               this.onAnimatedFloatLivingEntity(animatedFloat, livingentity);
            } else {
               if (livingentity != null) {
                  boolean flag2x = true;
                  boolean flag4x = this.isBooleanEntity(flag2x, livingentity);
                  if (!flag4x && text2.equals(this.modeRotacii.getString2())) {
                     flag4x = this.isEntityDouble(livingentity, value238);
                  }

                  boolean flag3x = false;
                  boolean flagx = this.isBooleanEntity(flag3x, livingentity);
                  if (flagx || !flag4x) {
                     this.setAnimatedFloat3(animatedFloat);
                     return;
                  }

                  boolean flag1 = this.isLivingEntity3(livingentity);
                  this.livingEntity = livingentity;
                  this.flag4 = true;
                  if (!this.flag3) {
                     this.value236 = this.player().getYaw();
                     this.value237 = this.player().getPitch();
                     this.flag3 = true;
                     this.flag5 = true;
                  }

                  if (flag1) {
                     Vec3d vec3dx = this.getVec3dByLivingEntity3(this.livingEntity);
                     if (vec3dx != null) {
                        this.vec3d = vec3dx;
                        this.flag6 = true;
                     } else {
                        this.vec3d = this.getVec3dByLivingEntity2(this.livingEntity);
                        this.flag6 = false;
                     }
                  } else {
                     this.vec3d = this.getVec3dByLivingEntity2(this.livingEntity);
                     this.flag6 = false;
                  }

                  if (text2.equals(this.modeRotacii.getString2())) {
                     this.setAnimatedFloat(animatedFloat);
                  } else {
                     float f10 = this.getFloatByVec3d2(this.vec3d);
                     float f = Math.abs(this.getFloatByFloat(f10 - this.value236));
                     if (f > 2.0F) {
                        this.flag5 = true;
                        float f17 = this.value236;
                        double d1 = this.speedNavodki.getValue();
                        float f4 = f17;
                        this.value236 = this.getFloatByDoubleFloatFloat(d1, f4, f10);
                     } else {
                        this.flag5 = false;
                        float f13 = this.value236;
                        double d2 = this.speedSlezheniya.getValue();
                        float f5 = f13;
                        this.value236 = this.getFloatByDoubleFloatFloat(d2, f5, f10);
                     }

                     float f1;
                     if (this.flag6) {
                        float f2 = this.getFloatByVec3d(this.vec3d);
                        float f3 = Math.abs(f2 - this.value237);
                        if (f3 > 2.0F) {
                           float f14 = this.value237;
                           double d3 = this.speedNavodki.getValue();
                           float f6 = f14;
                           this.value237 = this.getFloatByDoubleFloatFloat(d3, f6, f2);
                        } else {
                           float f15 = this.value237;
                           double d4 = this.speedSlezheniya.getValue();
                           float f7 = f15;
                           this.value237 = this.getFloatByDoubleFloatFloat(d4, f7, f2);
                        }

                        f1 = this.value237;
                     } else {
                        float f11 = this.player().getPitch();
                        float f12 = Math.abs(f11 - this.value237);
                        if (f12 > 1.0F) {
                           float f16 = this.value237;
                           double d5 = this.speedNavodki.getValue();
                           float f8 = f16;
                           this.value237 = this.getFloatByDoubleFloatFloat(d5, f8, f11);
                           f1 = this.value237;
                        } else {
                           this.value237 = f11;
                           f1 = f11;
                        }
                     }

                     animatedFloat.setValue(f1);
                     animatedFloat.setValue2(this.value236);
                     animatedFloat.setFlag(true);
                  }
               } else {
                  this.setAnimatedFloat3(animatedFloat);
               }
            }
         }
      }
   }

   private Box getBoxByEntity(Entity entity2) {
      float f = this.getFloatByEntity(entity2) + (this.size.getValueAsFloat() - 0.3F);
      return new Box(entity2.getX() - f, entity2.getBoundingBox().minY, entity2.getZ() - f, entity2.getX() + f, entity2.getBoundingBox().maxY, entity2.getZ() + f);
   }

   private float getFloatByVec3d(Vec3d vec3d) {
      Vec3d vec3dx = this.player().getEyePos();
      double d0 = vec3d.x - vec3dx.x;
      double d1 = vec3d.y - vec3dx.y;
      double d2 = vec3d.z - vec3dx.z;
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      return (float)(-(Math.atan2(d1, d3) * 180.0 / Math.PI));
   }

   private void update14() {
      if (!this.fiksirovannyySize.isFlag3()) {
         this.size.setDouble2(0.3);
         this.value235 = 0.3;
      }
   }

   @Override
   public void render3(WorldRenderContext worldRenderContext) {
      boolean flagx = !this.onlyCOruzhiem.isFlag3() || this.check4();

      for (Entity entity : worldRenderContext.getClientWorld().getEntities()) {
         if (entity instanceof LivingEntity livingentity && livingentity != this.player() && this.isEntity(livingentity)) {
            if (livingentity instanceof PlayerEntity playerentity && this.isFriend(playerentity)) {
               this.onLivingEntityFloat(playerentity, 0.3F);
            } else {
               float f = this.getFloatByLivingEntityBoolean(livingentity, flagx);
               this.onLivingEntityFloat(livingentity, f);
            }
         }
      }
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   private Vec3d getVec3dByLivingEntity2(LivingEntity livingEntity) {
      Vec3d vec3dx = this.player().getEyePos();
      Vec3d vec3d1 = this.player().getRotationVec(1.0F);
      Box box = this.getBoxByEntity2(livingEntity);
      double d0 = 0.2F;
      Box box1 = new Box(box.minX + d0, box.minY, box.minZ + d0, box.maxX - d0, box.maxY, box.maxZ - d0);
      Box box2 = this.getBoxByEntity(livingEntity);
      double d1 = this.range.getValue();
      Vec3d vec3d2x = vec3dx.add(vec3d1.multiply(d1));
      Vec3d vec3d3 = box2.raycast(vec3dx, vec3d2x).orElse(box2.getCenter());
      return this.getVec3dByBoxVec3d(box1, vec3d3);
   }

   private boolean isLivingEntity3(LivingEntity livingEntity) {
      if (livingEntity != null && this.player() != null) {
         Vec3d vec3dx = this.player().getEyePos();
         Box box = this.getBoxByEntity2(livingEntity);
         float f = this.player().getPitch();
         double d0 = this.getDoubleByLivingEntityFloatVec3d(livingEntity, f, vec3dx);
         return d0 < box.minY || d0 > box.maxY;
      } else {
         return false;
      }
   }

   private void update15() {
      if (this.onlyNaProtivnike.isFlag3() && this.livingEntity2 != null) {
         long i = System.currentTimeMillis();
         long j = i - this.time;
         long k = (long)(this.timeSbrosa.getValue() * 1000.0);
         if (j >= k) {
            this.update16();
         }
      }
   }

   private Boolean getBoolean6() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2());
   }

   private void setAnimatedFloat(AnimatedFloat animatedFloat) {
      long i = System.nanoTime();
      double d0 = this.time3 > 0L ? (i - this.time3) / 1.66666667E7 : 1.0;
      d0 = MathHelper.clamp(d0, 0.05, 3.0);
      this.time3 = i;
      ThreadLocalRandom threadlocalrandom = ThreadLocalRandom.current();
      if (this.livingEntity != this.livingEntity3) {
         this.livingEntity3 = this.livingEntity;
         this.time4 = System.currentTimeMillis();
         this.flag7 = false;
         this.time5 = 0L;
         this.value241 = threadlocalrandom.nextDouble(Math.PI * 2);
         this.value242 = threadlocalrandom.nextDouble(Math.PI * 2);
         this.value243 = threadlocalrandom.nextDouble(0.06, 0.18);
         this.value244 = threadlocalrandom.nextDouble(0.04, 0.14);
         this.value245 = threadlocalrandom.nextDouble(0.85, 1.15);
         this.value239 = 0.0;
         this.value240 = 0.0;
      }

      this.value241 = this.value241 + this.value243 * d0;
      this.value242 = this.value242 + this.value244 * d0;
      if (threadlocalrandom.nextDouble() < 0.02) {
         this.value245 = threadlocalrandom.nextDouble(0.8, 1.2);
      }

      float f = this.getFloatByVec3d2(this.vec3d);
      float f1 = this.flag6 ? this.getFloatByVec3d(this.vec3d) : this.player().getPitch();
      double d1 = getDoubleByDouble(f - this.value236);
      double d2 = (double)f1 - this.value237;
      double d3 = Math.sqrt(d1 * d1 + d2 * d2);
      if (d3 < 0.05) {
         animatedFloat.setValue(this.value237);
         animatedFloat.setValue2(this.value236);
         animatedFloat.setFlag(true);
      } else {
         double d4 = this.getDouble();
         this.flag5 = d3 > 2.0;
         double d5 = this.flag5 ? this.speedNavodki.getValue() : this.speedSlezheniya.getValue();
         double d6 = MathHelper.clamp((System.currentTimeMillis() - this.time4) / 350.0, 0.0, 1.0);
         double d7 = d6 * d6 * (3.0 - 2.0 * d6);
         double d8 = this.getDoubleByDouble2(d3);
         double d9 = d5 / 15.0;
         double d10 = threadlocalrandom.nextDouble(-0.002, 0.002);
         double d11 = MathHelper.clamp(d5 * d7 * d8 * this.value245 * d0 + d10, 0.003, 0.85);
         double d12 = d1 * d11;
         double d13 = d2 * d11 * (0.85 + threadlocalrandom.nextDouble(0.3));
         double d14 = 1.0 + Math.min(d3 / 30.0, 4.0) * d9;
         double d15 = (1.8 + threadlocalrandom.nextDouble(1.2)) * d0 * d14;
         double d16 = (1.3 + threadlocalrandom.nextDouble(0.8)) * d0 * d14;
         double d17 = d12 - this.value239;
         double d18 = d13 - this.value240;
         if (Math.abs(d17) > d15) {
            d12 = this.value239 + Math.signum(d17) * d15;
         }

         if (Math.abs(d18) > d16) {
            d13 = this.value240 + Math.signum(d18) * d16;
         }

         d12 = this.getDoubleByDoubleDouble(d4, d12);
         d13 = this.getDoubleByDoubleDouble(d4, d13);
         if (d12 == 0.0 && Math.abs(d1) > d4) {
            d12 = Math.signum(d1) * d4;
         }

         if (d13 == 0.0 && Math.abs(d2) > d4) {
            d13 = Math.signum(d2) * d4;
         }

         if (Math.abs(d12) > Math.abs(d1)) {
            d12 = this.getDoubleByDoubleDouble(d4, d1);
         }

         if (Math.abs(d13) > Math.abs(d2)) {
            d13 = this.getDoubleByDoubleDouble(d4, d2);
         }

         this.value239 = d12;
         this.value240 = d13;
         this.value236 += (float)d12;
         this.value237 = MathHelper.clamp(this.value237 + (float)d13, -90.0F, 90.0F);
         animatedFloat.setValue(this.value237);
         animatedFloat.setValue2(this.value236);
         animatedFloat.setFlag(true);
      }
   }

   private Boolean getBoolean7() {
      return !this.fiksirovannyySize.isFlag3();
   }

   @Override
   public void onDisable() {
      this.size.setDouble2(0.3F);
      this.update18();
      this.update16();
      this.update13();
   }

   private Boolean getBoolean8() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2());
   }

   private Boolean getBoolean9() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2());
   }

   private Boolean getBoolean10() {
      return !this.fiksirovannyySize.isFlag3();
   }

   private boolean isLivingEntity4(LivingEntity livingEntity) {
      Vec3d vec3dx = this.player().getEyePos();
      float f = this.value237 * (float) (Math.PI / 180.0);
      float f1 = -this.value236 * (float) (Math.PI / 180.0);
      float f2 = MathHelper.cos(f);
      float f3 = MathHelper.sin(f);
      float f4 = MathHelper.cos(f1);
      float f5 = MathHelper.sin(f1);
      Vec3d vec3d1 = new Vec3d(f5 * f2, -f3, f4 * f2);
      double d0 = this.range.getValue();
      Vec3d vec3d2x = vec3dx.add(vec3d1.multiply(d0));
      Box box = this.getBoxByEntity2(livingEntity);
      Box box1 = !text.equals(this.modeRotacii.getString2()) ? box.expand(0.0, value238, 0.0) : box;
      return box1.contains(vec3dx) ? true : box1.raycast(vec3dx, vec3d2x).isPresent();
   }

   private void update16() {
      this.livingEntity2 = null;
      this.time = 0L;
   }

   private void setAnimatedFloat2(AnimatedFloat animatedFloat) {
      long i = System.nanoTime();
      double d0 = this.time3 > 0L ? (i - this.time3) / 1.66666667E7 : 1.0;
      d0 = MathHelper.clamp(d0, 0.05, 3.0);
      this.time3 = i;
      ThreadLocalRandom threadlocalrandom = ThreadLocalRandom.current();
      if (this.fleks.isFlag3()) {
         if (this.flag9) {
            float f = 6.0F * (float)d0;
            this.value250 += f;
            if (!(this.value250 >= 360.0F)) {
               double d14 = this.getDouble();
               double d13 = f;
               double d15 = this.getDoubleByDoubleDouble(d14, d13);
               this.value236 += (float)d15;
               this.value237 = this.player().getPitch();
               animatedFloat.setValue(this.value237);
               animatedFloat.setValue2(this.value236);
               animatedFloat.setFlag(true);
               return;
            }

            this.flag9 = false;
            this.value250 = 0.0F;
            this.value249 = 0;
         }

         this.value249++;
         if (this.value249 >= 15) {
            this.flag9 = true;
            this.value250 = 0.0F;
         }
      }

      float f1 = this.getFloatByVec3d2(this.vec3d);
      double d1 = getDoubleByDouble(f1 - this.value236);
      double d2 = Math.abs(d1);
      if (d2 < 0.05) {
         this.value237 = this.player().getPitch();
         animatedFloat.setValue(this.value237);
         animatedFloat.setValue2(this.value236);
         animatedFloat.setFlag(true);
      } else if (this.stopOnNavedenii.isFlag3() && d2 < 1.5) {
         this.value237 = this.player().getPitch();
         animatedFloat.setValue(this.value237);
         animatedFloat.setValue2(this.value236);
         animatedFloat.setFlag(true);
      } else {
         double d3 = this.getDouble();
         this.flag5 = d2 > 2.0;
         double d4 = this.flag5 ? this.speedNavodki.getValue() : this.speedSlezheniya.getValue();
         if (this.uchityvatChuvstv.isFlag3()) {
            double d5 = (Double)this.options().getMouseSensitivity().getValue();
            d4 *= d5 * 2.0;
         }

         double d16 = MathHelper.clamp((System.currentTimeMillis() - this.time4) / 200.0, 0.0, 1.0);
         double d6 = d16 * d16 * (3.0 - 2.0 * d16);
         double d7 = MathHelper.clamp(d2 / 15.0, 0.15, 1.0);
         if (this.zamedlenieVblizi.isFlag3()) {
            double d8 = this.porogZamedleniya.getValue();
            if (d2 < d8) {
               double d9 = Math.max(this.faktorZamedleniya.getValue(), d2 / d8);
               d7 *= d9;
            }
         }

         double d17 = d4 * d6 * d7 * d0 * (0.9 + threadlocalrandom.nextDouble(0.2));
         double d18 = d1;
         if (this.krivayaTraektoriya.isFlag3() && this.value248 > 0.0F) {
            double d10 = this.amplitudaKrivoy.getValue() * Math.min(1.0, d2 / 10.0);
            d18 = d1 + Math.sin(this.value248 * Math.PI) * d10;
         }

         if (this.randomShum.getValue() > 0.0) {
            double d19 = this.randomShum.getValue();
            double d11 = this.chastotaRandoma.getValue();
            double d12 = System.currentTimeMillis() % 100000L / 1000.0;
            d18 += Math.sin(d12 * d11 * 2.0 * Math.PI) * d19;
         }

         double d20 = d18 * d17;
         d20 = this.getDoubleByDoubleDouble(d3, d20);
         if (d20 == 0.0 && Math.abs(d1) > d3) {
            d20 = Math.signum(d1) * d3;
         }

         if (Math.abs(d20) > Math.abs(d1)) {
            d20 = this.getDoubleByDoubleDouble(d3, d1);
         }

         this.value236 += (float)d20;
         this.value237 = this.player().getPitch();
         animatedFloat.setValue(this.value237);
         animatedFloat.setValue2(this.value236);
         animatedFloat.setFlag(true);
      }
   }

   private Vec3d getVec3dByLivingEntity3(LivingEntity livingEntity) {
      if (livingEntity != null && this.player() != null) {
         Vec3d vec3dx = this.player().getEyePos();
         Box box = this.getBoxByEntity2(livingEntity);
         float f = this.player().getPitch();
         double d0 = this.getDoubleByLivingEntityFloatVec3d(livingEntity, f, vec3dx);
         double d1;
         if (d0 < box.minY) {
            d1 = box.minY + 0.1;
         } else {
            if (!(d0 > box.maxY)) {
               return null;
            }

            d1 = box.maxY - 0.1;
         }

         double d2 = livingEntity.getX();
         double d3 = livingEntity.getZ();
         return new Vec3d(d2, d1, d3);
      } else {
         return null;
      }
   }

   private Boolean getBoolean11() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2()) && this.zamedlenieVblizi.isFlag3();
   }

   private boolean isBooleanEntity(boolean flag, Entity entity2) {
      Vec3d vec3dx = this.player().getEyePos();
      Vec3d vec3d1 = this.player().getRotationVec(1.0F);
      double d0 = this.range.getValue();
      Vec3d vec3d2x = vec3dx.add(vec3d1.multiply(d0));
      Box box = flag ? this.getBoxByEntity(entity2) : this.getBoxByEntity2(entity2);
      if (box.contains(vec3dx)) {
         double d1 = entity2.getX() - vec3dx.x;
         double d2 = entity2.getZ() - vec3dx.z;
         double d3 = vec3d1.x;
         double d4 = vec3d1.z;
         double d5 = Math.sqrt(d1 * d1 + d2 * d2);
         double d6 = Math.sqrt(d3 * d3 + d4 * d4);
         if (!(d5 < 0.01) && !(d6 < 0.01)) {
            double d7 = d1 / d5 * (d3 / d6) + d2 / d5 * (d4 / d6);
            return d7 > -0.5;
         } else {
            return true;
         }
      } else {
         return box.raycast(vec3dx, vec3d2x).isPresent();
      }
   }

   @Override
   public synchronized void setEnabled(boolean flag) {
      if (!flag && this.isEnabled()) {
         if (!this.fiksirovannyySize.isFlag3()) {
            this.value235 = this.size.getValue();
         }

         this.size.setDouble2(0.3F);
         this.update18();
         this.update16();
      }

      super.setEnabled(flag);
   }

   private LivingEntity getLivingEntity() {
      LivingEntity livingentity = null;
      double d0 = this.range.getValue();

      for (Entity entity : this.clientWorld().getEntities()) {
         if (entity instanceof LivingEntity livingentity1
            && livingentity1 != this.player()
            && this.isLivingEntity(livingentity1)
            && this.isLivingEntity5(livingentity1)) {
            double d1 = this.player().distanceTo(livingentity1);
            if (d1 < d0) {
               d0 = d1;
               livingentity = livingentity1;
            }
         }
      }

      return livingentity;
   }

   private boolean isEntityDouble(Entity entity2, double value) {
      Vec3d vec3dx = this.player().getEyePos();
      Vec3d vec3d1 = this.player().getRotationVec(1.0F);
      double d0 = this.range.getValue();
      Vec3d vec3d2x = vec3dx.add(vec3d1.multiply(d0));
      Box box = this.getBoxByEntity(entity2).expand(0.0, value, 0.0);
      if (box.contains(vec3dx)) {
         double d1 = entity2.getX() - vec3dx.x;
         double d2 = entity2.getZ() - vec3dx.z;
         double d3 = vec3d1.x;
         double d4 = vec3d1.z;
         double d5 = Math.sqrt(d1 * d1 + d2 * d2);
         double d6 = Math.sqrt(d3 * d3 + d4 * d4);
         if (!(d5 < 0.01) && !(d6 < 0.01)) {
            double d7 = d1 / d5 * (d3 / d6) + d2 / d5 * (d4 / d6);
            return d7 > -0.5;
         } else {
            return true;
         }
      } else {
         return box.raycast(vec3dx, vec3d2x).isPresent();
      }
   }

   private boolean isPlayerEntity(PlayerEntity playerEntity) {
      return !((ItemStack)playerEntity.getInventory().armor.get(0)).isEmpty()
         || !((ItemStack)playerEntity.getInventory().armor.get(1)).isEmpty()
         || !((ItemStack)playerEntity.getInventory().armor.get(2)).isEmpty()
         || !((ItemStack)playerEntity.getInventory().armor.get(3)).isEmpty();
   }

   public BooleanSetting getSkrytIndikator() {
      return this.skrytIndikator;
   }

   public void setValue236(float value) {
      this.value236 = value;
   }

   private void setDouble(double value) {
      if (this.isEnabled()) {
         this.size.setDouble2(value);
         this.setFlag(false);
         this.flag2 = false;
         this.time2 = 0L;
      }
   }

   private float getFloatByFloat(float value) {
      return ((value + 180.0F) % 360.0F + 360.0F) % 360.0F - 180.0F;
   }

   private Boolean getBoolean12() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2()) && this.krivayaTraektoriya.isFlag3();
   }

   private boolean isEntity(Entity entity2) {
      String s = this.targets.getString2();
      if (text6.equals(s)) {
         return entity2 instanceof LivingEntity;
      } else if (text4.equals(s)) {
         return entity2 instanceof PlayerEntity;
      } else {
         return text5.equals(s) ? entity2 instanceof MobEntity : false;
      }
   }

   @Override
   public void onPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      if (this.onlyNaProtivnike.isFlag3()) {
         if (entity2 instanceof LivingEntity livingentity && this.isEntity(livingentity)) {
            this.livingEntity2 = livingentity;
            this.time = System.currentTimeMillis();
         }
      }
   }

   private void onAnimatedFloatLivingEntity(AnimatedFloat animatedFloat, LivingEntity livingEntity2) {
      boolean flag2x = true;
      boolean flagx = this.isBooleanEntity(flag2x, livingEntity2);
      if (!flagx) {
         flagx = this.isEntityDouble(livingEntity2, value238);
      }

      if (!flagx) {
         this.setAnimatedFloat3(animatedFloat);
      } else {
         boolean flag3x = false;
         boolean flag1 = this.isBooleanEntity(flag3x, livingEntity2);
         this.livingEntity = livingEntity2;
         this.flag4 = true;
         if (!this.flag3) {
            this.value236 = this.player().getYaw();
            this.value237 = this.player().getPitch();
            this.flag3 = true;
            this.flag5 = true;
            this.time4 = System.currentTimeMillis();
            this.update19();
         }

         if (this.livingEntity != this.livingEntity3) {
            this.livingEntity3 = this.livingEntity;
            this.time4 = System.currentTimeMillis();
            this.flag7 = false;
            this.time5 = 0L;
            this.update19();
         }

         this.update21();
         if (this.krivayaTraektoriya.isFlag3()) {
            this.value248 = this.value248 + (float)(this.chastotaKrivoy.getValue() * 0.05);
            if (this.value248 > 1.0F) {
               this.value248 = 1.0F;
            }
         }

         if (flag1 && this.randomNaTargets.getValue() > 0.0) {
            long i = System.currentTimeMillis();
            if (this.vec3d2 == null || i - this.time6 > 500L) {
               this.time6 = i;
               this.vec3d2 = this.getVec3dByLivingEntity(livingEntity2);
            }

            this.vec3d = this.vec3d2;
         } else {
            if (flag1) {
               float f = this.player().getYaw();
               float f1 = Math.abs((float)getDoubleByDouble(this.value236 - f));
               if (f1 > 0.5F) {
                  float f3 = this.value236;
                  double d0 = (float)this.speedSlezheniya.getValue();
                  float f2 = f3;
                  this.value236 = this.getFloatByDoubleFloatFloat(d0, f2, f);
               } else {
                  this.value236 = f;
               }

               this.value237 = this.player().getPitch();
               animatedFloat.setValue(this.value237);
               animatedFloat.setValue2(this.value236);
               return;
            }

            this.vec3d2 = null;
            this.vec3d = this.getVec3dByLivingEntity4(livingEntity2);
         }

         this.setAnimatedFloat2(animatedFloat);
      }
   }

   private double getDoubleByDouble2(double value) {
      if (value < 1.5) {
         return 0.12 + value * 0.12;
      } else if (value < 8.0) {
         double d3 = (value - 1.5) / 6.5;
         return 0.3 + d3 * 0.4;
      } else if (value < 40.0) {
         double d2 = (value - 8.0) / 32.0;
         double d1 = d2 * d2 * (3.0 - 2.0 * d2);
         return 0.7 + d1 * 1.8;
      } else {
         double d0 = Math.min((value - 40.0) / 140.0, 1.0);
         return 2.5 + d0 * 2.5;
      }
   }

   private double getDoubleByDoubleDouble(double value, double value2) {
      return RandomUtil.getDoubleByDoubleDouble(value, value2);
   }

   private Vec3d getVec3dByLivingEntity4(LivingEntity livingEntity) {
      Box box = this.getBoxByEntity2(livingEntity);
      double d0 = (box.minX + box.maxX) / 2.0;
      double d1 = (box.minY + box.maxY) / 2.0;
      double d2 = (box.minZ + box.maxZ) / 2.0;
      Vec3d vec3dx = this.player().getEyePos();
      double d3 = d0 - vec3dx.x;
      double d4 = d2 - vec3dx.z;
      double d5 = Math.sqrt(d3 * d3 + d4 * d4);
      double d6 = 0.0;
      double d7 = 0.0;
      if (d5 > 0.01) {
         d6 = -d4 / d5;
         d7 = d3 / d5;
      }

      double d8 = this.paddingX.getValue() + this.value246;
      double d9 = d0 + d6 * d8;
      double d11 = d2 + d7 * d8;
      double d12 = 0.05;
      d9 = MathHelper.clamp(d9, box.minX + d12, box.maxX - d12);
      double d10 = MathHelper.clamp(d1, box.minY + d12, box.maxY - d12);
      d11 = MathHelper.clamp(d11, box.minZ + d12, box.maxZ - d12);
      return new Vec3d(d9, d10, d11);
   }

   private Vec3d getVec3dByBoxVec3d(Box box, Vec3d vec3d) {
      double d0 = Math.clamp(box.maxX, box.minX, vec3d.x);
      double d1 = Math.clamp(box.maxY, box.minY, vec3d.y);
      double d2 = Math.clamp(box.maxZ, box.minZ, vec3d.z);
      return new Vec3d(d0, d1, d2);
   }

   private void onLivingEntityFloat(LivingEntity livingEntity, float value) {
      livingEntity.setBoundingBox(
         new Box(livingEntity.getX() - value, livingEntity.getBoundingBox().minY, livingEntity.getZ() - value, livingEntity.getX() + value, livingEntity.getBoundingBox().maxY, livingEntity.getZ() + value)
      );
   }

   private void setAnimatedFloat3(AnimatedFloat animatedFloat) {
      if (this.flag4) {
         recovered.fabric.diagnostic.SystemDlcLog.once("hb_rot", "mode=" + this.modeRotacii.getString2() + " flag4=T tgt=" + this.value236 + "/" + this.value237 + " pyaw=" + this.player().getYaw() + " ppitch=" + this.player().getPitch());
         float f = this.player().getYaw();
         float f1 = this.player().getPitch();
         float f2 = Math.abs(this.getFloatByFloat(this.value236 - f));
         float f3 = Math.abs(this.value237 - f1);
         if (text3.equals(this.modeRotacii.getString2())) {
            if (!this.flag7) {
               this.flag7 = true;
               this.time5 = System.currentTimeMillis();
            }

            this.onAnimatedFloatFloat(animatedFloat, f);
            if (f2 < 5.0F) {
               this.update18();
            }
         } else if (text2.equals(this.modeRotacii.getString2())) {
            if (!this.flag7) {
               this.flag7 = true;
               this.time5 = System.currentTimeMillis();
            }

            this.onFloatAnimatedFloatFloat(f1, animatedFloat, f);
            if (f2 < 5.0F && f3 < 5.0F) {
               this.update18();
            }
         } else {
            float f6 = this.value236;
            double d0 = this.speedNavodki.getValue();
            float f4 = f6;
            this.value236 = this.getFloatByDoubleFloatFloat(d0, f4, f);
            f6 = this.value237;
            double d1 = this.speedNavodki.getValue();
            float f5 = f6;
            this.value237 = this.getFloatByDoubleFloatFloat(d1, f5, f1);
            animatedFloat.setValue(this.value237);
            animatedFloat.setValue2(this.value236);
            animatedFloat.setFlag(true);
            if (f2 < 5.0F && f3 < 5.0F) {
               this.update18();
            }
         }
      }
   }

   private boolean isLivingEntity5(LivingEntity livingEntity) {
      return !this.onlyNaProtivnike.isFlag3() || livingEntity == this.livingEntity2;
   }

   @Override
   public void onInteractEvent(InteractEvent interactEvent) {
      HitResult hitresult = interactEvent.getHitResult();
      if (hitresult instanceof EntityHitResult entityhitresult
         && entityhitresult.getEntity() instanceof LivingEntity livingentity
         && this.onlyNaProtivnike.isFlag3()
         && this.isEntity(livingentity)) {
         this.livingEntity2 = livingentity;
         this.time = System.currentTimeMillis();
      }

      if (this.obhod.isFlag3()
         && !text.equals(this.modeRotacii.getString2())
         && this.size.getValueAsFloat() > 0.3F
         && hitresult instanceof EntityHitResult entityhitresult1
         && entityhitresult1.getEntity() instanceof LivingEntity livingentity1) {
         if (!this.isEntity(livingentity1)) {
            return;
         }

         boolean flagx = !this.onlyCOruzhiem.isFlag3() || this.check4();
         if (this.getFloatByBooleanLivingEntity(flagx, livingentity1) > this.getFloatByEntity(livingentity1) && !this.isLivingEntity4(livingentity1)) {
            boolean flag1 = false;
            if (!this.isBooleanEntity(flag1, livingentity1)) {
               interactEvent.setFlag(true);
               if (this.feykSving.isFlag3()) {
                  this.clientPlayer().swingHand(this.mainHand());
                  this.clientPlayer().resetLastAttackedTicks();
               }

               return;
            }
         }
      }

      if (this.antiReach.isFlag3()) {
         float f = this.size.getValueAsFloat();
         if (!(f <= 0.3F) && hitresult instanceof EntityHitResult entityhitresult2) {
            if (entityhitresult2.getEntity() instanceof LivingEntity livingentity2) {
               if (this.isEntity(livingentity2)) {
                  if (!(livingentity2 instanceof PlayerEntity playerentity && this.onlyVBrone.isFlag3() && !this.isPlayerEntity(playerentity))) {
                     Box box = this.getBoxByEntity2(livingentity2);
                     Vec3d vec3dx = this.player().getEyePos();
                     Vec3d vec3d1 = this.getVec3dByBoxVec3d(box, vec3dx);
                     double d0 = vec3dx.distanceTo(vec3d1);
                     if (d0 > this.rangeAntiReach.getValue()) {
                        interactEvent.setFlag(true);
                        if (this.feykSving.isFlag3()) {
                           this.clientPlayer().swingHand(this.mainHand());
                           this.clientPlayer().resetLastAttackedTicks();
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void onFloatAnimatedFloatFloat(float value, AnimatedFloat animatedFloat, float value2) {
      long i = System.nanoTime();
      double d0 = this.time3 > 0L ? (i - this.time3) / 1.66666667E7 : 1.0;
      d0 = MathHelper.clamp(d0, 0.05, 3.0);
      this.time3 = i;
      ThreadLocalRandom threadlocalrandom = ThreadLocalRandom.current();
      double d1 = getDoubleByDouble(value2 - this.value236);
      double d2 = (double)value - this.value237;
      double d3 = Math.sqrt(d1 * d1 + d2 * d2);
      if (d3 < 0.05) {
         animatedFloat.setValue(this.value237);
         animatedFloat.setValue2(this.value236);
         animatedFloat.setFlag(true);
      } else {
         double d4 = this.getDouble();
         this.flag5 = d3 > 2.0;
         double d5 = this.flag5 ? this.speedNavodki.getValue() : this.speedSlezheniya.getValue();
         double d6 = MathHelper.clamp((System.currentTimeMillis() - this.time5) / 200.0, 0.0, 1.0);
         double d7 = d6 * d6 * (3.0 - 2.0 * d6);
         double d8 = MathHelper.clamp(d3 / 15.0, 0.15, 1.0);
         double d9 = d5 * d7 * d8 * d0 * (0.9 + threadlocalrandom.nextDouble(0.2));
         double d10 = d1 * d9;
         double d11 = d2 * d9;
         d10 = this.getDoubleByDoubleDouble(d4, d10);
         d11 = this.getDoubleByDoubleDouble(d4, d11);
         if (d10 == 0.0 && Math.abs(d1) > d4) {
            d10 = Math.signum(d1) * d4;
         }

         if (d11 == 0.0 && Math.abs(d2) > d4) {
            d11 = Math.signum(d2) * d4;
         }

         if (Math.abs(d10) > Math.abs(d1)) {
            d10 = this.getDoubleByDoubleDouble(d4, d1);
         }

         if (Math.abs(d11) > Math.abs(d2)) {
            d11 = this.getDoubleByDoubleDouble(d4, d2);
         }

         this.value236 += (float)d10;
         this.value237 = MathHelper.clamp(this.value237 + (float)d11, -90.0F, 90.0F);
         animatedFloat.setValue(this.value237);
         animatedFloat.setValue2(this.value236);
         animatedFloat.setFlag(true);
      }
   }

   private void onAnimatedFloatFloat(AnimatedFloat animatedFloat, float value) {
      long i = System.nanoTime();
      double d0 = this.time3 > 0L ? (i - this.time3) / 1.66666667E7 : 1.0;
      d0 = MathHelper.clamp(d0, 0.05, 3.0);
      this.time3 = i;
      ThreadLocalRandom threadlocalrandom = ThreadLocalRandom.current();
      double d1 = getDoubleByDouble(value - this.value236);
      double d2 = Math.abs(d1);
      this.value237 = this.player().getPitch();
      if (d2 < 0.05) {
         animatedFloat.setValue(this.value237);
         animatedFloat.setValue2(this.value236);
         animatedFloat.setFlag(true);
      } else {
         double d3 = this.getDouble();
         double d4 = d2 > 2.0 ? this.speedNavodki.getValue() : this.speedSlezheniya.getValue();
         double d5 = MathHelper.clamp((System.currentTimeMillis() - this.time5) / 200.0, 0.0, 1.0);
         double d6 = d5 * d5 * (3.0 - 2.0 * d5);
         double d7 = MathHelper.clamp(d2 / 15.0, 0.15, 1.0);
         double d8 = d4 * d6 * d7 * d0 * (0.9 + threadlocalrandom.nextDouble(0.2));
         double d9 = d1 * d8;
         d9 = this.getDoubleByDoubleDouble(d3, d9);
         if (d9 == 0.0 && Math.abs(d1) > d3) {
            d9 = Math.signum(d1) * d3;
         }

         if (Math.abs(d9) > Math.abs(d1)) {
            d9 = this.getDoubleByDoubleDouble(d3, d1);
         }

         this.value236 += (float)d9;
         animatedFloat.setValue(this.value237);
         animatedFloat.setValue2(this.value236);
         animatedFloat.setFlag(true);
      }
   }

   private double getDouble() {
      return RandomUtil.getDouble();
   }

   private double getDoubleByLivingEntityFloatVec3d(LivingEntity livingEntity, float value, Vec3d vec3d) {
      double d0 = livingEntity.getX() - vec3d.x;
      double d1 = livingEntity.getZ() - vec3d.z;
      double d2 = Math.sqrt(d0 * d0 + d1 * d1);
      double d3 = Math.toRadians(value);
      return d2 < 0.5 ? vec3d.y - Math.tan(d3) * 0.5 : vec3d.y - Math.tan(d3) * d2;
   }

   private void update17() {
      this.fiksirovannyySize.setOnChange(this::update12);
      this.fiksSize.setOnChange(this::update11);
      this.size.setOnChange(this::update24);
   }

   private float getFloatByVec3d2(Vec3d vec3d) {
      Vec3d vec3dx = this.player().getEyePos();
      if (this.livingEntity != null) {
         Vec3d vec3d1 = this.livingEntity.getVelocity();
         vec3d = vec3d.add(vec3d1.multiply(0.1));
      }

      double d1 = vec3d.x - vec3dx.x;
      double d0 = vec3d.z - vec3dx.z;
      return (float)(Math.atan2(d0, d1) * 180.0 / Math.PI) - 90.0F;
   }

   private float getFloatByLivingEntityBoolean(LivingEntity livingEntity, boolean flag) {
      float f = this.getFloatByEntity(livingEntity);
      if (!flag) {
         return f;
      } else if (livingEntity instanceof PlayerEntity playerentity && this.onlyVBrone.isFlag3() && !this.isPlayerEntity(playerentity)) {
         return f;
      } else if (!this.isLivingEntity5(livingEntity)) {
         return f;
      } else {
         return this.nevidimyyHitboks.isFlag3() ? f : f + (this.size.getValueAsFloat() - 0.3F);
      }
   }

   private float getFloatByDoubleFloatFloat(double value, float value2, float value3) {
      return value2 + this.getFloatByFloat(value3 - value2) * (float)value;
   }

   private boolean check4() {
      return this.isItemStack(this.player().getMainHandStack()) || this.isItemStack(this.player().getOffHandStack());
   }

   private boolean isItemStack(ItemStack itemStack) {
      return CritChecks.isItemStack(itemStack);
   }

   private Box getBoxByEntity2(Entity entity2) {
      return entity2.getType().getDimensions().getBoxAt(entity2.getPos());
   }

   private float getFloatByEntity(Entity entity2) {
      Box box = this.getBoxByEntity2(entity2);
      return (float)(box.getLengthX() / 2.0);
   }

   private float getFloatByBooleanLivingEntity(boolean flag, LivingEntity livingEntity) {
      float f = this.getFloatByEntity(livingEntity);
      if (!flag) {
         return f;
      } else if (livingEntity instanceof PlayerEntity playerentity && this.onlyVBrone.isFlag3() && !this.isPlayerEntity(playerentity)) {
         return f;
      } else {
         return !this.isLivingEntity5(livingEntity) ? f : f + (this.size.getValueAsFloat() - 0.3F);
      }
   }

   @Override
   public void onAttackEvent(AttackEvent attackEvent) {
      if (this.obhod.isFlag3()) {
         if (this.check3()) {
            double d0 = this.fiksirovannyySize.isFlag3() ? this.fiksSize.getValue() : this.value235;
            this.flag2 = true;
            this.time2 = System.currentTimeMillis();
            this.setFlag(true);
            this.update18();
            this.setValue236(0.0F);
            this.size.setDouble2(0.3F);
            this.update13();
            attackEvent.setFlag2(true);
            SystemClient.getInstance().getScheduledExecutorService().schedule(() -> this.setDouble(d0), 500L, TimeUnit.MILLISECONDS);
         }
      }
   }

   private Boolean getBoolean13() {
      return !this.fiksirovannyySize.isFlag3();
   }

   private Boolean getBoolean14() {
      return !this.fiksirovannyySize.isFlag3();
   }

   private Boolean getBoolean15() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2());
   }

   private void update18() {
      this.livingEntity = null;
      this.vec3d = null;
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.update22();
   }

   private Boolean getBoolean16() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2());
   }

   @Override
   public void onEnable() {
      if (this.fiksirovannyySize.isFlag3()) {
         this.size.setDouble2(this.fiksSize.getValue());
      } else {
         this.size.setDouble2(this.value235);
      }

      this.update18();
      this.update16();
   }

   private void update19() {
      this.flag8 = false;
      this.vec3d2 = null;
      this.time6 = 0L;
      this.value246 = 0.0;
      this.value247 = 0.0;
      this.time7 = 0L;
      this.value248 = 0.0F;
      this.value249 = 0;
      this.flag9 = false;
      this.value250 = 0.0F;
   }

   private Boolean getBoolean17() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2());
   }

   private void update20() {
      if (!this.fiksirovannyySize.isFlag3()) {
         double d0 = Math.max(this.size.getValue() - this.shag.getValue(), this.minSize.getValue());
         if (d0 != this.size.getValue()) {
            this.size.setDouble2(d0);
            this.value235 = d0;
         }
      }
   }

   private void update21() {
      long i = System.currentTimeMillis();
      if (i - this.time7 > 500L) {
         this.time7 = i;
         ThreadLocalRandom threadlocalrandom = ThreadLocalRandom.current();
         this.value247 = (threadlocalrandom.nextDouble() * 2.0 - 1.0) * this.randomOtstupaX.getValue();
      }

      this.value246 = this.value246 + (this.value247 - this.value246) * 0.1;
   }

   private void update22() {
      this.time3 = 0L;
      this.time4 = 0L;
      this.time5 = 0L;
      this.livingEntity3 = null;
      this.flag7 = false;
      this.value239 = 0.0;
      this.value240 = 0.0;
      this.value241 = 0.0;
      this.value242 = 0.0;
      this.value243 = 0.1;
      this.value244 = 0.08;
      this.value245 = 1.0;
      this.update19();
   }

   private void update23() {
      if (!this.fiksirovannyySize.isFlag3()) {
         double d0 = Math.min(this.size.getValue() + this.shag.getValue(), this.maxSize.getValue());
         if (d0 != this.size.getValue()) {
            this.size.setDouble2(d0);
            this.value235 = d0;
         }
      }
   }

   private Boolean getBoolean18() {
      return this.obhod.isFlag3() && text3.equals(this.modeRotacii.getString2());
   }

   private void update24() {
      if (this.isEnabled() && !this.fiksirovannyySize.isFlag3() && !this.flag2) {
         this.value235 = this.size.getValue();
      }
   }
}
