package client.module.combat;

import client.module.Category;
import client.module.Module;
import client.module.visual.FreeLook;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.AimMode;
import client.util.AimSample;
import client.util.CritChecks;
import client.util.FreeAim;
import client.util.LegitAim;
import client.util.RandomUtil;
import client.util.RaycastUtil;
import client.util.RotationUtil;
import client.util.StringParts;
import client.util.TargetSelector;
import client.util.UnsafeAccess;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AimAssist extends Module {
   private static String text = "Дистанция";
   private static String text2 = "Здоровье";
   private static String text3 = "Угол";
   private static final List<AimMode> list = List.of(new FreeAim(), new LegitAim());
   private static final List<String> list2 = list.stream().map(AimMode::getText).toList();
   private static final UnsafeAccess<FreeLook> unsafeAccess = new UnsafeAccess<>(FreeLook.class);
   private ListSetting rotation;
   private ListSetting prioritet;
   private SliderSetting range;
   private SliderSetting fov;
   private SliderSetting uderzhanieAfterUdara;
   private BooleanSetting onlyLasthit;
   private BooleanSetting navodkaCArbaletom;
   private SliderSetting rangeArbaleta;
   private MultilistSetting targets;
   private MultilistSetting usloviya;
   private Entity entity;
   private long time;
   private final RotationUtil rotationUtil;
   private long time2;
   private double value235;
   private double value236;
   private RegistryKey<World> registryKey;
   private Vec3d vec3d;
   private Vec3d vec3d2;
   private Entity entity2;
   private double value237;
   private final Map<UUID, AimSample> map;

   public AimAssist() {
      super("AimAssist", Category.COMBAT);
      ListSetting listsetting = new ListSetting("", "", list2, List.of(list2.getFirst()), false);
      listsetting.setName("Ротация");
      listsetting.setDescription("Режим ротации прицеливания");
      this.rotation = listsetting;
      listsetting = new ListSetting("", "", Arrays.asList(text, text2, text3), List.of(text3), false);
      listsetting.setName("Приоритет");
      listsetting.setDescription("По какому параметру выбирать цель");
      this.prioritet = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 4.5, 1.0, 6.0, 0.1);
      slidersetting.setName("Дистанция");
      slidersetting.setDescription("Максимальная дистанция до цели");
      this.range = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 360.0, 20.0, 360.0, 1.0);
      slidersetting1.setName("Фов");
      slidersetting1.setDescription("Угол обзора для захвата цели");
      this.fov = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 5.0, 0.0, 30.0, 1.0, "c", 0);
      slidersetting2.setName("Удержание после удара");
      slidersetting2.setDescription("После удара по цели аим держится только на ней. Таймер обнуляется при каждом ударе");
      this.uderzhanieAfterUdara = slidersetting2;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Только ластхит");
      booleansetting.setDescription("Целить только в того, по кому последний раз ударили");
      this.onlyLasthit = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Наводка c арбалетом");
      booleansetting1.setDescription("Когда в руке арбалет — отдельная наводка");
      this.navodkaCArbaletom = booleansetting1;
      SliderSetting slidersetting3 = new SliderSetting("", "", 20.0, 1.0, 100.0, 0.5);
      slidersetting3.setName("Дистанция арбалета");
      slidersetting3.setDescription("Максимальная дистанция до цели когда работает наводка c арбалетом");
      this.rangeArbaleta = slidersetting3;
      MultilistSetting multilistsetting = new MultilistSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "и", "г", "р", "o", "к", "и"}),
            StringParts.join(new String[]{"Н", "e", "в", "и", "д", "и", "м", "ы", "е"}),
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "в", " ", "б", "р", "o", "н", "е"}),
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "в", " ", "н", "e", "з", "е", "р", "и", "т", "о", "в", "о", "й"}),
            StringParts.join(new String[]{"Г", "o", "л", "ы", "е"})
         ),
         List.of(StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "и", "г", "р", "o", "к", "и"}))
      );
      multilistsetting.setName("Цели");
      multilistsetting.setDescription("Кого целить");
      this.targets = multilistsetting;
      MultilistSetting multilistsetting1 = new MultilistSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "c", " ", "o", "р", "у", "ж", "и", "е", "м"}),
            StringParts.join(new String[]{"Н", "e", " ", "ц", "e", "л", "и", "т", "ь", " ", "в", " ", "и", "н", "в", "e", "н", "т", "а", "р", "е"}),
            StringParts.join(new String[]{"Н", "e", " ", "ц", "e", "л", "и", "т", "ь", " ", "к", "o", "г", "д", "а", " ", "e", "м"}),
            StringParts.join(new String[]{"П", "р", "o", "в", "е", "р", "к", "а", " ", "c", "т", "е", "н"}),
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "п", "o", " ", "X"}),
            StringParts.join(new String[]{"B", "ы", "к", "л", ".", " ", "п", "p", "и", " ", "c", "м", "е", "н", "е", " ", "м", "и", "р", "a"})
         ),
         Arrays.asList(
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "c", " ", "o", "р", "у", "ж", "и", "е", "м"}),
            StringParts.join(new String[]{"Н", "e", " ", "ц", "e", "л", "и", "т", "ь", " ", "в", " ", "и", "н", "в", "e", "н", "т", "а", "р", "е"}),
            StringParts.join(new String[]{"Н", "e", " ", "ц", "e", "л", "и", "т", "ь", " ", "к", "o", "г", "д", "а", " ", "e", "м"}),
            StringParts.join(new String[]{"П", "р", "o", "в", "е", "р", "к", "а", " ", "c", "т", "е", "н"}),
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "п", "o", " ", "X"})
         )
      );
      multilistsetting1.setName("Условия");
      multilistsetting1.setDescription("Условия работы");
      this.usloviya = multilistsetting1;
      this.rotationUtil = new RotationUtil();
      this.map = new HashMap<>();
      this.addSettings(new Setting[]{this.rotation});

      for (AimMode aimmode : list) {
         List<Setting> listx = aimmode.getList();
         if (!listx.isEmpty()) {
            this.addSettings(listx.toArray(new Setting[0]));
            aimmode.onStringListSetting(aimmode.getText(), this.rotation);
         }
      }

      this.addSettings(
         new Setting[]{
            this.prioritet,
            this.range,
            this.fov,
            this.uderzhanieAfterUdara,
            this.onlyLasthit,
            this.navodkaCArbaletom,
            this.rangeArbaleta,
            this.targets,
            this.usloviya
         }
      );
      this.rangeArbaleta.setVisibleWhen(this.navodkaCArbaletom::isFlag3);
   }

   private boolean isString(String text) {
      return this.usloviya.isString(text);
   }

   private static boolean isLivingEntity(LivingEntity livingEntity) {
      return true;
   }

   private static AimSample getAimSampleByUUID(UUID uUID) {
      return new AimSample();
   }

   private boolean check3() {
      return this.player() != null && CritChecks.isClass(CrossbowItem.class) ? CrossbowItem.isCharged(this.player().getMainHandStack()) : false;
   }

   private boolean check4() {
      if (this.player() != null && this.player().isUsingItem()) {
         UseAction useaction = this.player().getActiveItem().getUseAction();
         return useaction == UseAction.EAT || useaction == UseAction.DRINK;
      } else {
         return false;
      }
   }

   @Override
   public void onDisable() {
      this.update11();
   }

   private static double getDoubleByDouble(double value) {
      return RandomUtil.getDoubleByDouble(value);
   }

   @Override
   public void onPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity3, EntityHitResult entityHitResult) {
      boolean flag5 = this.isString2("Только игроки");
      boolean flag6 = this.isString2("Невидимые");
      boolean flag7 = this.isString2("Только в броне");
      boolean flag8 = this.isString2("Только в незеритовой");
      boolean flag4 = this.isString2("Голые");
      boolean flag3 = flag8;
      boolean flag2 = flag7;
      boolean flag1 = flag6;
      boolean flag = flag5;
      if (this.isBooleanBooleanBooleanEntityBooleanBoolean(flag4, flag, flag1, entity3, flag2, flag3)) {
         if (this.uderzhanieAfterUdara.getValueAsLong() > 0L || this.onlyLasthit.isFlag3()) {
            this.entity2 = entity3;
            this.time2 = System.currentTimeMillis();
         }
      }
   }

   private boolean isString2(String text) {
      return this.targets.isString(text);
   }

   private boolean isBooleanBooleanBooleanEntityBooleanBoolean(boolean flag2, boolean flag3, boolean flag4, Entity entity2, boolean flag5, boolean flag6) {
      if (entity2 instanceof LivingEntity livingentity) {
         if (entity2 instanceof ArmorStandEntity) {
            return false;
         } else if (entity2 instanceof PlayerEntity playerentity && this.isFriend(playerentity)) {
            return false;
         } else if (flag3 && !(entity2 instanceof PlayerEntity)) {
            return false;
         } else if (!flag4 && livingentity.isInvisible()) {
            return false;
         } else {
            boolean flag = TargetSelector.isLivingEntity(livingentity);
            if (flag6 && !this.isLivingEntity2(livingentity)) {
               return false;
            } else {
               return flag5 && !flag ? false : flag2 || flag;
            }
         }
      } else {
         return false;
      }
   }

   private static boolean isLongEntry(long time2, Entry entry) {
      return time2 - ((AimSample)entry.getValue()).time > 2000L;
   }

   private void update11() {
      this.entity = null;
      this.time = 0L;
      this.rotationUtil.setTime();
      this.value235 = 0.0;
      this.value236 = 0.0;
      this.registryKey = null;
      this.vec3d = null;
      this.vec3d2 = null;
      this.entity2 = null;
      this.time2 = 0L;
      this.value237 = 0.0;
      this.map.clear();
      this.getAimMode().update2();
   }

   private static AimSample getAimSampleByUUID2(UUID uUID) {
      return new AimSample();
   }

   private static Vec3d getVec3dByFloatFloat(float value, float value2) {
      float f = (float)Math.toRadians(value);
      float f1 = (float)Math.toRadians(value2);
      double d0 = -MathHelper.sin(f) * MathHelper.cos(f1);
      double d1 = -MathHelper.sin(f1);
      double d2 = MathHelper.cos(f) * MathHelper.cos(f1);
      return new Vec3d(d0, d1, d2).normalize();
   }

   private double[] getDoubleArrayByEntityAimMode(Entity entity2, AimMode aimMode) {
      Vec3d vec3dx = this.player().getEyePos();
      double d0 = 0.78;
      double d1 = d0 + aimMode.getDouble2();
      double[] adouble = aimMode.getDoubleArrayByEntity(entity2);
      double d2 = adouble[0];
      double d3 = adouble[1];
      double d4 = adouble[2];
      double d5 = MathHelper.clamp(d1 + d3, 0.05, 0.95);
      Vec3d vec3d1 = entity2.getPos().add(d2, entity2.getHeight() * d5, d4);
      double d6 = vec3d1.x - vec3dx.x;
      double d7 = vec3d1.y - vec3dx.y;
      double d8 = vec3d1.z - vec3dx.z;
      double d9 = Math.sqrt(d6 * d6 + d8 * d8);
      double d10;
      if (d9 < 0.001) {
         d10 = this.player().getYaw();
      } else {
         d10 = Math.toDegrees(Math.atan2(-d6, d8));
      }

      double d11 = Math.sqrt(d6 * d6 + d7 * d7 + d8 * d8);
      double d12;
      if (d11 < 0.001) {
         d12 = this.player().getPitch();
      } else {
         d12 = -Math.toDegrees(Math.asin(MathHelper.clamp(d7 / d11, -1.0, 1.0)));
      }

      return new double[]{d10, d12};
   }

   private Vec3d getVec3dByEntity(Entity entity2) {
      Vec3d vec3dx = this.player().getEyePos();
      Vec3d vec3d1 = entity2.getPos();
      double d0 = vec3d1.x - vec3dx.x;
      double d1 = vec3d1.z - vec3dx.z;
      double d2 = Math.sqrt(d0 * d0 + d1 * d1);
      double d3;
      if (d2 <= 4.0) {
         d3 = 0.55;
      } else if (d2 <= 12.0) {
         double d4 = (d2 - 4.0) / 8.0;
         d3 = 0.55 + d4 * 0.29999999999999993;
      } else {
         double d6 = MathHelper.clamp((d2 - 12.0) / 13.0, 0.0, 1.0);
         d3 = 0.85 + d6 * 0.20000000000000007;
      }

      double d7 = vec3d1.y + entity2.getHeight() * 0.5 - vec3dx.y;
      if (d2 > 0.5) {
         double d5 = d7 / d2;
         d3 -= MathHelper.clamp(d5 * 0.15, -0.15, 0.15);
      }

      d3 = MathHelper.clamp(d3, 0.2, 1.15);
      return vec3d1.add(0.0, entity2.getHeight() * d3, 0.0);
   }

   private void onDoubleEntity(double value, Entity entity2) {
      Vec3d vec3dx = this.player().getEyePos();
      Vec3d vec3d1 = this.getVec3dByEntity(entity2);
      double d0 = vec3d1.x - vec3dx.x;
      double d1 = vec3d1.y - vec3dx.y;
      double d2 = vec3d1.z - vec3dx.z;
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      double d4 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      if (!(d4 < 0.001)) {
         double d5 = Math.toDegrees(Math.atan2(-d0, d2));
         double d6 = -Math.toDegrees(Math.asin(MathHelper.clamp(d1 / d4, -1.0, 1.0)));
         if (d3 < 0.001) {
            d5 = this.player().getYaw();
         }

         float f = this.player().getYaw();
         float f1 = this.player().getPitch();
         double d7 = getDoubleByDouble(d5 - f);
         double d8 = d6 - f1;
         double d9 = 1.0 - Math.pow(0.72, Math.max(value, 0.01));
         double d10 = d7 * d9;
         double d11 = d8 * d9;
         if (Math.abs(d7) < 0.02) {
            d10 = d7;
         }

         if (Math.abs(d8) < 0.02) {
            d11 = d8;
         }

         float f2 = f + (float)d10;
         float f3 = MathHelper.clamp(f1 + (float)d11, -90.0F, 90.0F);
         this.player().setYaw(f2);
         this.player().setPitch(f3);
      }
   }

   private Vec3d getVec3dByAimModeEntity(AimMode aimMode, Entity entity2) {
      double d0 = 0.78;
      double d1 = d0 + aimMode.getDouble2();
      double[] adouble = aimMode.getDoubleArrayByEntity(entity2);
      double d2 = MathHelper.clamp(d1 + adouble[1], 0.05, 0.95);
      return entity2.getPos().add(adouble[0], entity2.getHeight() * d2, adouble[2]);
   }

   private double getDoubleByVec3dVec3dEntity(Vec3d vec3d, Vec3d vec3d2, Entity entity2) {
      Vec3d vec3dx = entity2.getPos();
      double d0 = vec3dx.x - vec3d2.x;
      double d1 = vec3dx.y + entity2.getHeight() * 0.78 - vec3d2.y;
      double d2 = vec3dx.z - vec3d2.z;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      if (d3 < 1.0E-6) {
         return 1.0;
      } else {
         double d4 = 1.0 / Math.sqrt(d3);
         return vec3d.x * d0 * d4 + vec3d.y * d1 * d4 + vec3d.z * d2 * d4;
      }
   }

   private boolean isLivingEntity2(LivingEntity livingEntity) {
      for (ItemStack itemstack : livingEntity.getArmorItems()) {
         if (!itemstack.isEmpty()) {
            Item item = itemstack.getItem();
            if (item == Items.NETHERITE_HELMET || item == Items.NETHERITE_CHESTPLATE || item == Items.NETHERITE_LEGGINGS || item == Items.NETHERITE_BOOTS) {
               return true;
            }
         }
      }

      return false;
   }

   private Entity getEntity() {
      if (this.notInGame()) {
         return null;
      } else {
         boolean flag = this.navodkaCArbaletom.isFlag3() && this.check3();
         double d0 = flag ? this.rangeArbaleta.getValue() : this.range.getValue();
         double d1 = flag ? 45.0 : this.fov.getValue() / 2.0;
         double d2 = Math.cos(Math.toRadians(d1));
         String s = flag ? text3 : this.prioritet.getString2();
         LivingEntity livingentity = null;
         double d3 = Double.MAX_VALUE;
         boolean flag1 = this.isString2("Только игроки");
         boolean flag2 = this.isString2("Невидимые");
         boolean flag3 = this.isString2("Только в броне");
         boolean flag4 = this.isString2("Только в незеритовой");
         boolean flag5 = this.isString2("Голые");
         boolean flag6 = this.isString("Проверка стен");
         Vec3d vec3dx = this.player().getEyePos();
         float f = this.player().getYaw();
         float f1 = this.player().getPitch();
         Vec3d vec3d1 = getVec3dByFloatFloat(f, f1);
         AimMode aimmode = this.getAimMode();
         long i = System.currentTimeMillis();
         this.map.entrySet().removeIf(p0 -> AimAssist.isLongEntry(i, p0));
         if (this.onlyLasthit.isFlag3()) {
            Entity entity1 = this.entity2;
            if (!this.isBooleanBooleanBooleanEntityBooleanBoolean(flag5, flag1, flag2, entity1, flag3, flag4)) {
               this.entity2 = null;
               return null;
            } else if (this.entity2 instanceof LivingEntity livingentity3 && livingentity3.isAlive() && livingentity3.getHealth() > 0.0F) {
               double d8 = this.player().distanceTo(this.entity2);
               if (d8 <= d0 && d8 >= 0.5) {
                  Entity entity2x = this.entity2;
                  double d9 = this.getDoubleByVec3dVec3dEntity(vec3d1, vec3dx, entity2x);
                  if (d9 >= d2) {
                     if (!flag6) {
                        return this.entity2;
                     }

                     Entity entity3 = this.entity2;
                     Vec3d vec3d3 = this.getVec3dByAimModeEntity(aimmode, entity3);
                     if (!RaycastUtil.isVec3dVec3d(vec3d3, vec3dx)) {
                        return this.entity2;
                     }
                  }
               }

               return null;
            } else {
               this.entity2 = null;
               return null;
            }
         } else {
            long j = this.uderzhanieAfterUdara.getValueAsLong() * 1000L;
            Entity entityx = null;
            if (j > 0L) {
               if (this.entity2 != null && this.time2 > 0L && i - this.time2 < j) {
                  entityx = this.entity2;
               } else if (this.entity != null && this.time > 0L && i - this.time < j) {
                  entityx = this.entity;
               }
            }

            if (entityx != null) {
               if (!this.isBooleanBooleanBooleanEntityBooleanBoolean(flag5, flag1, flag2, entityx, flag3, flag4)) {
                  if (entityx == this.entity2) {
                     this.entity2 = null;
                     this.time2 = 0L;
                  }
               } else {
                  if (entityx instanceof LivingEntity livingentity1 && livingentity1.isAlive() && livingentity1.getHealth() > 0.0F) {
                     double d10 = this.player().distanceTo(entityx);
                     if (d10 <= d0 && d10 >= 0.5) {
                        AimSample aimsample2 = this.map.get(entityx.getUuid());
                        boolean flag11 = aimsample2 != null && i < aimsample2.time2;
                        if (!flag11) {
                           double d12 = this.getDoubleByVec3dVec3dEntity(vec3d1, vec3dx, entityx);
                           if (d12 >= d2) {
                              boolean flag14;
                              label207: {
                                 if (flag6) {
                                    Vec3d vec3d4 = this.getVec3dByAimModeEntity(aimmode, entityx);
                                    if (RaycastUtil.isVec3dVec3d(vec3d4, vec3dx)) {
                                       flag14 = false;
                                       break label207;
                                    }
                                 }

                                 flag14 = true;
                              }

                              boolean flag12 = flag14;
                              if (flag12) {
                                 return entityx;
                              }
                           }
                        }
                     }

                     return null;
                  }

                  if (entityx == this.entity2) {
                     this.entity2 = null;
                     this.time2 = 0L;
                  }
               }
            }

            Box box = this.player().getBoundingBox().expand(d0);

            for (LivingEntity livingentity2 : this.clientWorld().getEntitiesByClass(LivingEntity.class, box, AimAssist::isLivingEntity)) {
               if (livingentity2 != this.player()
                  && !livingentity2.isDead()
                  && !(livingentity2.getHealth() <= 0.0F)
                  && !(livingentity2 instanceof ArmorStandEntity)
                  && !livingentity2.isSpectator()
                  && livingentity2.canHit()
                  && (!flag1 || livingentity2 instanceof PlayerEntity)
                  && !(livingentity2 instanceof PlayerEntity playerentity && this.isFriend(playerentity))
                  && (flag2 || !livingentity2.isInvisible())) {
                  boolean flag10 = TargetSelector.isLivingEntity(livingentity2);
                  if ((!flag4 || this.isLivingEntity2(livingentity2)) && (!flag3 || flag10) && (flag5 || flag10)) {
                     double d4 = this.player().distanceTo(livingentity2);
                     if (!(d4 > d0) && !(d4 < 0.5)) {
                        Vec3d vec3d2x = livingentity2.getPos();
                        AimSample aimsample = this.map.computeIfAbsent(livingentity2.getUuid(), AimAssist::getAimSampleByUUID2);
                        if (aimsample.vec3d != null) {
                           double d5 = vec3d2x.distanceTo(aimsample.vec3d);
                           double d6 = Math.max(1L, i - aimsample.time);
                           double d7 = d5 / (d6 / 1000.0);
                           if (d5 > 12.0 || d7 > 60.0) {
                              aimsample.time2 = i + 200L;
                           }
                        }

                        aimsample.vec3d = vec3d2x;
                        aimsample.time = i;
                        if (i >= aimsample.time2) {
                           if (flag6) {
                              Vec3d vec3d6 = this.getVec3dByAimModeEntity(aimmode, livingentity2);
                              if (RaycastUtil.isVec3dVec3d(vec3d6, vec3dx)) {
                                 continue;
                              }
                           }

                           double d14 = this.getDoubleByVec3dVec3dEntity(vec3d1, vec3dx, livingentity2);
                           if (!(d14 < d2)) {
                              double d15;
                              if (text2.equals(s)) {
                                 d15 = livingentity2.getHealth();
                              } else if (text3.equals(s)) {
                                 d15 = Math.toDegrees(Math.acos(MathHelper.clamp(d14, -1.0, 1.0)));
                              } else {
                                 d15 = d4;
                              }

                              if (d15 < d3) {
                                 d3 = d15;
                                 livingentity = livingentity2;
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (this.entity != null
               && livingentity != null
               && livingentity != this.entity
               && this.entity instanceof LivingEntity livingentity4
               && livingentity4.isAlive()
               && livingentity4.getHealth() > 0.0F) {
               Entity entity4 = this.entity;
               if (this.isBooleanBooleanBooleanEntityBooleanBoolean(flag5, flag1, flag2, entity4, flag3, flag4)) {
                  boolean flag7;
                  boolean flag8;
                  double d11;
                  double d13;
                  boolean flag13;
                  boolean flag15;
                  label229: {
                     AimSample aimsample1 = this.map.get(this.entity.getUuid());
                     flag7 = aimsample1 != null && i < aimsample1.time2;
                     d11 = this.player().distanceTo(this.entity);
                     flag8 = d11 <= d0 && d11 >= 0.5;
                     Entity entity5 = this.entity;
                     d13 = this.getDoubleByVec3dVec3dEntity(vec3d1, vec3dx, entity5);
                     flag13 = d13 >= d2;
                     if (flag6) {
                        Entity entity6 = this.entity;
                        Vec3d vec3d5 = this.getVec3dByAimModeEntity(aimmode, entity6);
                        if (RaycastUtil.isVec3dVec3d(vec3d5, vec3dx)) {
                           flag15 = false;
                           break label229;
                        }
                     }

                     flag15 = true;
                  }

                  boolean flag9 = flag15;
                  if (!flag7 && flag8 && flag13 && flag9) {
                     double d16;
                     if (text2.equals(s)) {
                        d16 = livingentity4.getHealth();
                     } else if (text3.equals(s)) {
                        d16 = Math.toDegrees(Math.acos(MathHelper.clamp(d13, -1.0, 1.0)));
                     } else {
                        d16 = d11;
                     }

                     double d17 = text3.equals(s) ? 8.0 : (text2.equals(s) ? 4.0 : 0.75);
                     if (d3 > d16 - d17) {
                        return this.entity;
                     }
                  }
               }
            }

            return livingentity;
         }
      }
   }

   private boolean check5() {
      return this.player() == null ? false : CritChecks.isItemStack(this.player().getMainHandStack());
   }

   private AimMode getAimMode() {
      String s = this.rotation.getString2();

      for (AimMode aimmode : list) {
         if (aimmode.getText().equals(s)) {
            return aimmode;
         }
      }

      return list.getFirst();
   }

   @Override
   public void update7() {
      if (!this.notInGame() && this.player() != null) {
         FreeLook freelook = (FreeLook)unsafeAccess.getModule2();
         if (freelook != null && freelook.check3()) {
            if (this.entity != null) {
               this.update11();
            }
         } else {
            RegistryKey registrykey = this.world().getRegistryKey();
            if (this.registryKey == null) {
               this.registryKey = registrykey;
            } else if (!this.registryKey.equals(registrykey)) {
               if (this.isString("Выкл. при смене мира")) {
                  this.setEnabled(false);
                  return;
               }

               this.registryKey = registrykey;
               this.update11();
               this.registryKey = registrykey;
               return;
            }

            Vec3d vec3dx = this.player().getPos();
            if (this.vec3d2 != null && vec3dx.squaredDistanceTo(this.vec3d2) > 64.0) {
               this.update11();
               this.vec3d2 = vec3dx;
            } else {
               this.vec3d2 = vec3dx;
               boolean flag = this.navodkaCArbaletom.isFlag3() && this.check3();
               if (!this.isString("Только c оружием") || this.check5() || flag) {
                  if (!this.isString("Не целить в инвентаре") || !(this.currentScreen() instanceof HandledScreen)) {
                     if (!this.isString("Не целить когда ем") || !this.check4()) {
                        double d0 = this.rotationUtil.getDouble();
                        Entity entityx = this.getEntity();
                        if (entityx == null) {
                           long k = this.uderzhanieAfterUdara.getValueAsLong() * 1000L;
                           long l = System.currentTimeMillis();
                           boolean flag2 = k > 0L
                              && (this.entity2 != null && this.time2 > 0L && l - this.time2 < k || this.entity != null && this.time > 0L && l - this.time < k);
                           if (!flag2 && this.entity != null) {
                              this.update11();
                           }
                        } else {
                           Vec3d vec3d1 = entityx.getPos();
                           if (entityx == this.entity && this.vec3d != null) {
                              double d1 = vec3d1.distanceTo(this.vec3d);
                              double d2 = d1 / Math.max(d0, 0.1);
                              if (d2 > 3.0) {
                                 this.value237 += d2 - 3.0;
                              } else {
                                 this.value237 = Math.max(0.0, this.value237 - 3.0 * d0);
                              }

                              if (d1 > 12.0 || this.value237 > 20.0) {
                                 AimSample aimsample = this.map.computeIfAbsent(entityx.getUuid(), AimAssist::getAimSampleByUUID);
                                 aimsample.time2 = System.currentTimeMillis() + 250L;
                                 this.value237 = 0.0;
                                 this.vec3d = vec3d1;
                                 return;
                              }
                           } else {
                              this.value237 = 0.0;
                           }

                           this.vec3d = vec3d1;
                           AimMode aimmode = this.getAimMode();
                           if (entityx != this.entity) {
                              this.entity = entityx;
                              this.time = System.currentTimeMillis();
                              this.vec3d = vec3d1;
                              this.value235 = 0.0;
                              this.value236 = 0.0;
                              aimmode.update();
                           }

                           long i = System.currentTimeMillis();
                           aimmode.onDoubleLongEntity(d0, i, entityx);
                           if (flag) {
                              this.onDoubleEntity(d0, entityx);
                           } else {
                              double[] adouble = this.getDoubleArrayByEntityAimMode(entityx, aimmode);
                              float f = this.player().getYaw();
                              float f1 = this.player().getPitch();
                              boolean flag1 = this.isString("Только по X") && !flag;
                              double d3 = getDoubleByDouble(adouble[0] - f);
                              double d4 = adouble[1] - f1;
                              double d5;
                              if (flag1) {
                                 ThreadLocalRandom threadlocalrandom = ThreadLocalRandom.current();
                                 double d6 = 8.0 + threadlocalrandom.nextDouble(4.0);
                                 double d8 = Math.abs(d4);
                                 if (d8 > d6) {
                                    double d9 = 0.06 + threadlocalrandom.nextDouble(0.06);
                                    d5 = (d4 - Math.signum(d4) * d6) * d9;
                                 } else {
                                    d5 = 0.0;
                                 }
                              } else {
                                 d5 = d4;
                              }

                              double d15 = flag1 ? Math.abs(d3) : Math.sqrt(d3 * d3 + d5 * d5);
                              if (!(d15 < 0.05)) {
                                 double d7 = aimmode.getDouble();
                                 long i1 = this.time;
                                 PlayerEntity playerentity1 = this.player();
                                 double d14 = this.value236;
                                 double d13 = this.value235;
                                 PlayerEntity playerentity = playerentity1;
                                 long j = i1;
                                 double[] adouble1 = aimmode.getDoubleArrayByDoubleLongDoubleDoubleDoubleDoubleDoubleLongPlayerEntityDouble(
                                    d3, j, d13, d5, d15, d14, d7, i, playerentity, d0
                                 );
                                 double d16 = adouble1[0];
                                 double d10 = adouble1[1];
                                 this.value235 = adouble1[2];
                                 this.value236 = adouble1[3];
                                 double d11 = this.player().distanceTo(entityx);
                                 if (d11 < 1.5) {
                                    double d12 = MathHelper.clamp(d11 - 0.5, 0.05, 1.0);
                                    d16 *= d12;
                                    d10 *= d12;
                                    this.value235 = adouble1[2] * d12;
                                    this.value236 = adouble1[3] * d12;
                                 }

                                 if (!(Math.abs(d16) < 0.003) || !(Math.abs(d10) < 0.003)) {
                                    float f3 = f + (float)d16;
                                    float f2 = MathHelper.clamp(f1 + (float)d10, -90.0F, 90.0F);
                                    this.player().setYaw(f3);
                                    this.player().setPitch(f2);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
      this.update11();
   }
}
