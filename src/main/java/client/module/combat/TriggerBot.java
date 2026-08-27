package client.module.combat;

import client.data.AnimatedFloat;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.CritChecks;
import client.util.KeyBindings;
import client.util.StringParts;
import client.util.TargetSelector;
import client.util.TickCounter;
import client.util.TpsTracker;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.MaceItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class TriggerBot extends Module {
   private ListSetting mode;
   private ListSetting targets;
   private MultilistSetting bit;
   private SliderSetting range;
   private BooleanSetting throughEntiti;
   private BooleanSetting svoyCooldown;
   private SliderSetting cooldown;
   private BooleanSetting tpsSink;
   private BooleanSetting neBitSvipami;
   private static LivingEntity livingEntity = null;
   private static float value235;
   private static float value236;
   private static double value237 = 0.0;
   private boolean flag;
   private boolean flag2;
   private LivingEntity livingEntity2;
   private boolean flag3;
   private float value238;
   private float value239;
   private LivingEntity livingEntity3;
   private float value240;
   private float value241;
   private int value242;

   public TriggerBot() {
      super("TriggerBot", Category.COMBAT);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"C", "м", "а", "р", "т"}), StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "к", "p", "и", "т", "ы"})
         ),
         List.of(StringParts.join(new String[]{"C", "м", "а", "р", "т"})),
         false
      );
      listsetting.setName("Режим");
      listsetting.setDescription("Режим атаки");
      this.mode = listsetting;
      listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"В", "c", "е"}),
            StringParts.join(new String[]{"И", "г", "р", "o", "к", "и"}),
            StringParts.join(new String[]{"M", "о", "б", "ы"})
         ),
         List.of(StringParts.join(new String[]{"И", "г", "р", "o", "к", "и"})),
         false
      );
      listsetting.setName("Цели");
      listsetting.setDescription("Тип целей для атаки");
      this.targets = listsetting;
      MultilistSetting multilistsetting = new MultilistSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "c", " ", "o", "р", "у", "ж", "и", "е", "м"}),
            StringParts.join(new String[]{"Ч", "e", "р", "е", "з", " ", "т", "р", "a", "в", "у"}),
            StringParts.join(new String[]{"Г", "o", "л", "ы", "х"}),
            StringParts.join(new String[]{"Н", "e", "в", "и", "д", "и", "м", "ы", "х"}),
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "в", " ", "б", "р", "o", "н", "е"})
         ),
         Arrays.asList(
            StringParts.join(new String[]{"Ч", "e", "р", "е", "з", " ", "т", "р", "a", "в", "у"}),
            StringParts.join(new String[]{"Н", "e", "в", "и", "д", "и", "м", "ы", "х"})
         )
      );
      multilistsetting.setName("Бить");
      multilistsetting.setDescription("Условия для атаки");
      this.bit = multilistsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 2.9, 1.0, 6.0, 0.1);
      slidersetting.setName("Дистанция");
      slidersetting.setDescription("Максимальная дальность удара");
      this.range = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Через энтити");
      booleansetting.setDescription("Бить цель сквозь любые живые сущности");
      this.throughEntiti = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Свой кулдаун");
      booleansetting1.setDescription("Игнорировать ванильный кулдаун и бить со своим процентом готовности");
      this.svoyCooldown = booleansetting1;
      SliderSetting slidersetting1 = new SliderSetting("", "", 80.0, 0.0, 100.0, 1.0, "%", 0);
      slidersetting1.setName("Кулдаун");
      slidersetting1.setDescription("Минимальный процент готовности оружия для удара");
      this.cooldown = slidersetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", false);
      booleansetting2.setName("ТПС синк");
      booleansetting2.setDescription("Подгонять тайминг ударов под ТПС сервера");
      this.tpsSink = booleansetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", false);
      booleansetting3.setName("Не бить свипами");
      booleansetting3.setDescription("Пропускать последние тики падения — сервер засчитывает такой удар наземным");
      this.neBitSvipami = booleansetting3;
      this.flag = false;
      this.flag2 = false;
      this.livingEntity2 = null;
      this.flag3 = false;
      this.livingEntity3 = null;
      this.value242 = 0;
      this.addSettings(
         new Setting[]{this.mode, this.targets, this.bit, this.range, this.throughEntiti, this.svoyCooldown, this.cooldown, this.tpsSink, this.neBitSvipami}
      );
      this.cooldown.setVisibleWhen(this.svoyCooldown::isFlag3);
   }

   @Override
   public void onAnimatedFloat(AnimatedFloat animatedFloat) {
      if (!this.notInGame() && this.currentScreen() == null) {
         LivingEntity livingentity = this.getLivingEntity();
         if (!this.interactionManager().isBreakingBlock()) {
            this.update12();
            if (!this.check4()) {
               if (this.livingEntity2 != null) {
                  this.setAnimatedFloat(animatedFloat);
               } else {
                  ClientPlayerEntity clientplayerentity = this.clientPlayer();
                  List list = this.bit.getList4();
                  if (!CritChecks.isClass(CrossbowItem.class)) {
                     if (!list.contains("Tолько c oружием") || CritChecks.check6()) {
                        LivingEntity livingentity1 = this.getLivingEntityByClientPlayerEntityList(clientplayerentity, list);
                        if (livingentity1 != null) {
                           if (this.isFloat(0.0F)) {
                              this.onLivingEntityClientPlayerEntity(livingentity1, clientplayerentity);
                           }
                        } else {
                           if (this.isClientPlayerEntityListLivingEntity(clientplayerentity, list, livingentity) && this.isFloat(-1.0F)) {
                              this.onClientPlayerEntityLivingEntityAnimatedFloat(clientplayerentity, livingentity, animatedFloat);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean isLivingEntity(LivingEntity livingEntity) {
      String s = (String)this.targets.getList3().getFirst();
      if ("Игрoки".equals(s)) {
         return livingEntity instanceof PlayerEntity;
      } else {
         return "Mобы".equals(s) ? livingEntity instanceof MobEntity : true;
      }
   }

   private boolean isClientPlayerEntity(ClientPlayerEntity clientPlayerEntity) {
      double d0 = clientPlayerEntity.getVelocity().y;
      if (!clientPlayerEntity.isOnGround() && !(d0 >= 0.0)) {
         double d1 = -d0 * 1.0;
         return !this.world().isSpaceEmpty(clientPlayerEntity, clientPlayerEntity.getBoundingBox().stretch(0.0, -d1, 0.0));
      } else {
         return false;
      }
   }

   private void setLivingEntity(LivingEntity livingEntity) {
      KeyBindings.update();
      this.livingEntity2 = livingEntity;
      this.flag3 = false;
   }

   private LivingEntity getLivingEntity() {
      LivingEntity livingentity = this.livingEntity3;
      this.livingEntity3 = null;
      return livingentity;
   }

   private boolean check3() {
      return this.clientPlayer().isOnGround() && this.value242 >= 5;
   }

   private void update11() {
      this.livingEntity2 = null;
      KeyBindings.update4();
   }

   private void setLivingEntity2(LivingEntity livingEntity) {
      this.setLivingEntity(livingEntity);
      this.flag3 = true;
      this.value238 = this.value240;
      this.value239 = this.value241;
   }

   private void update12() {
      if (this.clientPlayer().isOnGround()) {
         this.value242++;
      } else {
         this.value242 = 0;
      }
   }

   @Override
   public void onDisable() {
      KeyBindings.update2();
      KeyBindings.update4();
      this.update13();
   }

   private void update13() {
      this.flag = false;
      this.flag2 = false;
      this.livingEntity2 = null;
      this.flag3 = false;
      this.livingEntity3 = null;
      livingEntity = null;
      this.value242 = 0;
   }

   private boolean isClientPlayerEntityListLivingEntity(ClientPlayerEntity clientPlayerEntity, List list, LivingEntity livingEntity) {
      if (livingEntity == null || !livingEntity.isAlive()) {
         return false;
      } else if (!this.isClientPlayerEntityLivingEntityList(clientPlayerEntity, livingEntity, list)) {
         return false;
      } else {
         float f2 = this.value240;
         float f3 = this.value241;
         double d0 = this.range.getValue();
         float f1 = f3;
         float f = f2;
         return isFloatFloatLivingEntityDouble(f, f1, livingEntity, d0);
      }
   }

   private LivingEntity getLivingEntityByClientPlayerEntityList(ClientPlayerEntity clientPlayerEntity, List list) {
      double d0 = this.range.getValue();
      Vec3d vec3d = clientPlayerEntity.getCameraPosVec(1.0F);
      Vec3d vec3d1 = clientPlayerEntity.getRotationVec(1.0F);
      Vec3d vec3d2 = vec3d.add(vec3d1.multiply(d0));
      boolean flagx = list.contains("Чeрез трaву");
      double d1 = this.getDoubleByBooleanClientPlayerEntityVec3dDoubleVec3d(flagx, clientPlayerEntity, vec3d, d0, vec3d2);
      Box box = clientPlayerEntity.getBoundingBox().stretch(vec3d1.multiply(d0)).expand(1.0);
      if (this.throughEntiti.isFlag3()) {
         return this.getLivingEntityByVec3dVec3dBoxDoubleClientPlayerEntityList(vec3d2, vec3d, box, d1, clientPlayerEntity, list);
      } else {
         EntityHitResult entityhitresult = ProjectileUtil.raycast(clientPlayerEntity, vec3d, vec3d2, box, p0 -> TriggerBot.isClientPlayerEntityEntity(clientPlayerEntity, p0), d0 * d0);
         if (entityhitresult != null && entityhitresult.getEntity() instanceof LivingEntity livingentity) {
            double d2 = Math.sqrt(vec3d.squaredDistanceTo(entityhitresult.getPos()));
            if (d2 > d1 + 0.1) {
               return null;
            } else {
               return !this.isClientPlayerEntityLivingEntityList(clientPlayerEntity, livingentity, list) ? null : livingentity;
            }
         } else {
            return null;
         }
      }
   }

   public static boolean isLivingEntity2(LivingEntity livingEntity) {
      if (mc.player == null) {
         return false;
      } else {
         double d0 = value237;
         float f1 = value236;
         float f = value235;
         if (!isFloatFloatLivingEntityDouble(f, f1, livingEntity, d0)) {
            float f4 = mc.player.getYaw();
            float f5 = mc.player.getPitch();
            double d1 = value237;
            float f3 = f5;
            float f2 = f4;
            if (!isFloatFloatLivingEntityDouble(f2, f3, livingEntity, d1)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isListLivingEntity(List list, LivingEntity livingEntity) {
      boolean flagx = TargetSelector.isLivingEntity(livingEntity);
      return list.contains("Tолько в брoне") && !flagx ? false : list.contains("Гoлых") || flagx;
   }

   private static boolean isClientPlayerEntityLivingEntity(ClientPlayerEntity clientPlayerEntity, LivingEntity livingEntity) {
      return livingEntity != clientPlayerEntity && livingEntity.isAlive() && !livingEntity.isSpectator() && livingEntity.canHit();
   }

   private boolean isClientPlayerEntityLivingEntityList(ClientPlayerEntity clientPlayerEntity, LivingEntity livingEntity, List list) {
      if (livingEntity == clientPlayerEntity || !livingEntity.isAlive() || this.isFriend(livingEntity) || !this.isLivingEntity(livingEntity)) {
         return false;
      } else {
         return livingEntity.isInvisible() && !list.contains("Нeвидимых") ? false : this.isListLivingEntity(list, livingEntity);
      }
   }

   private double getDoubleByBooleanClientPlayerEntityVec3dDoubleVec3d(boolean flag, ClientPlayerEntity clientPlayerEntity, Vec3d vec3d, double value, Vec3d vec3d2) {
      BlockHitResult blockhitresult = this.world().raycast(new RaycastContext(vec3d, vec3d2, ShapeType.OUTLINE, FluidHandling.NONE, clientPlayerEntity));
      if (blockhitresult.getType() != Type.BLOCK) {
         return value;
      } else {
         BlockState blockstate = this.world().getBlockState(blockhitresult.getBlockPos());
         boolean flagx = blockstate.getBlock() instanceof PlantBlock;
         return flag && flagx ? value : Math.sqrt(vec3d.squaredDistanceTo(blockhitresult.getPos()));
      }
   }

   private LivingEntity getLivingEntityByVec3dVec3dBoxDoubleClientPlayerEntityList(
      Vec3d vec3d, Vec3d vec3d2, Box box2, double value, ClientPlayerEntity clientPlayerEntity, List list
   ) {
      LivingEntity livingentity = null;
      double d0 = Double.MAX_VALUE;
      double d1 = (value + 0.1) * (value + 0.1);

      for (LivingEntity livingentity1 : this.world().getEntitiesByClass(LivingEntity.class, box2, p0 -> TriggerBot.isClientPlayerEntityLivingEntity(clientPlayerEntity, p0))) {
         if (this.isClientPlayerEntityLivingEntityList(clientPlayerEntity, livingentity1, list)) {
            Box box = livingentity1.getBoundingBox().expand(livingentity1.getTargetingMargin());
            double d2;
            if (box.contains(vec3d2)) {
               d2 = 0.0;
            } else {
               Optional optional = box.raycast(vec3d2, vec3d);
               if (optional.isEmpty()) {
                  continue;
               }

               d2 = vec3d2.squaredDistanceTo((Vec3d)optional.get());
            }

            if (!(d2 > d1) && d2 < d0) {
               d0 = d2;
               livingentity = livingentity1;
            }
         }
      }

      return livingentity;
   }

   private static boolean isClientPlayerEntityEntity(ClientPlayerEntity clientPlayerEntity, Entity entity2) {
      return !entity2.isSpectator() && entity2.canHit() && entity2 != clientPlayerEntity;
   }

   private void update14() {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      value235 = clientplayerentity.getYaw();
      value236 = clientplayerentity.getPitch();
      value237 = this.getDouble();
   }

   private boolean check4() {
      if (!this.flag) {
         return false;
      } else {
         this.flag = false;
         if (this.flag2) {
            this.flag2 = false;
            KeyBindings.update4();
         }

         return true;
      }
   }

   private void setLivingEntity3(LivingEntity livingEntity2) {
      this.update14();
      livingEntity = livingEntity2;
      KeyBindings.update6();
      this.flag = true;
      this.flag2 = CritChecks.check3();
      if (this.flag2) {
         KeyBindings.update();
      }
   }

   private void onClientPlayerEntityLivingEntityAnimatedFloat(ClientPlayerEntity clientPlayerEntity, LivingEntity livingEntity, AnimatedFloat animatedFloat) {
      animatedFloat.setValue2(this.value240);
      animatedFloat.setValue(this.value241);
      if (this.isClientPlayerEntity2(clientPlayerEntity)) {
         this.setLivingEntity2(livingEntity);
      } else {
         CritChecks.onEntity(livingEntity);
      }
   }

   private void onLivingEntityClientPlayerEntity(LivingEntity livingEntity, ClientPlayerEntity clientPlayerEntity) {
      if (this.isClientPlayerEntity2(clientPlayerEntity)) {
         this.setLivingEntity(livingEntity);
      } else {
         this.setLivingEntity3(livingEntity);
      }
   }

   private static boolean isFloatFloatLivingEntityDouble(float value, float value2, LivingEntity livingEntity, double value3) {
      if (mc.player == null) {
         return false;
      } else {
         Vec3d vec3d = mc.player.getCameraPosVec(1.0F);
         Box box = livingEntity.getBoundingBox().expand(livingEntity.getTargetingMargin());
         if (box.contains(vec3d)) {
            return true;
         } else {
            Vec3d vec3d1 = Vec3d.fromPolar(value2, value);
            return box.raycast(vec3d, vec3d.add(vec3d1.multiply(value3))).isPresent();
         }
      }
   }

   private float getFloatByFloat(float value) {
      if (!this.tpsSink.isFlag3()) {
         return value;
      } else {
         float f = TpsTracker.getInstance().getFloat();
         if (!(f <= 0.0F) && !(f >= 20.0F)) {
            float f1 = value * (20.0F / f);
            return Math.min(f1, 1.0F);
         } else {
            return value;
         }
      }
   }

   private boolean isFloat(float value) {
      if (TickCounter.isValueAsBoolean()) {
         return false;
      } else {
         ClientPlayerEntity clientplayerentity = this.clientPlayer();
         boolean flagx = CritChecks.check();
         if (!flagx && clientplayerentity.getVelocity().y > 0.0) {
            return false;
         } else if (CritChecks.check4()) {
            return false;
         } else {
            boolean flag1 = CritChecks.check3();
            if (flag1 && this.neBitSvipami.isFlag3() && this.isClientPlayerEntity(clientplayerentity)) {
               return false;
            } else {
               float f = clientplayerentity.getAttackCooldownProgress(value);
               if (CritChecks.isClass(MaceItem.class)) {
                  return f >= this.getFloatByFloat(0.4F) && (flag1 || flagx);
               } else {
                  String s = (String)this.mode.getList3().getFirst();
                  if (this.svoyCooldown.isFlag3()) {
                     if (f < this.getFloatByFloat(this.cooldown.getValueAsFloat() / 100.0F)) {
                        return false;
                     }

                     if ("Tолько кpиты".equals(s) && !flag1 && !flagx) {
                        return false;
                     }
                  } else {
                     float f3 = this.getFloatByFloat(CritChecks.getFloat());
                     float f2 = -0.01F;
                     float f1 = f3;
                     boolean flag2x = true;
                     if (!CritChecks.isStringFloatBooleanFloatFloat(s, f1, flag2x, f2, value)) {
                        return false;
                     }
                  }

                  return "Cмарт".equals(s) && !flag1 && !flagx ? !this.options().jumpKey.isPressed() && this.check3() : true;
               }
            }
         }
      }
   }

   public static LivingEntity getLivingEntity2() {
      LivingEntity livingentity = livingEntity;
      livingEntity = null;
      return livingentity;
   }

   private double getDouble() {
      double d0 = this.range.getValue();
      return d0 >= 3.0 ? d0 : Math.min(d0 + 0.1, 3.0);
   }

   private boolean isClientPlayerEntity2(ClientPlayerEntity clientPlayerEntity) {
      return clientPlayerEntity.isSprinting() && CritChecks.check3();
   }

   private void setAnimatedFloat(AnimatedFloat animatedFloat) {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      float f = this.flag3 ? this.value238 : clientplayerentity.getYaw();
      float f1 = this.flag3 ? this.value239 : clientplayerentity.getPitch();
      if (this.livingEntity2.isAlive()) {
         LivingEntity livingentity1 = this.livingEntity2;
         double d0 = this.getDouble();
         LivingEntity livingentity = livingentity1;
         if (isFloatFloatLivingEntityDouble(f, f1, livingentity, d0)) {
            if (this.flag3) {
               animatedFloat.setValue2(f);
               animatedFloat.setValue(f1);
            }

            if (this.isClientPlayerEntity2(clientplayerentity)) {
               KeyBindings.update();
               return;
            }

            KeyBindings.update4();
            if (!this.isFloat(0.0F)) {
               return;
            }

            CritChecks.onEntity(this.livingEntity2);
            this.livingEntity2 = null;
            return;
         }
      }

      this.update11();
   }

   @Override
   public void update7() {
      if (this.livingEntity3 == null && !this.notInGame() && this.currentScreen() == null) {
         ClientPlayerEntity clientplayerentity = this.clientPlayer();
         LivingEntity livingentity = this.getLivingEntityByClientPlayerEntityList(clientplayerentity, this.bit.getList4());
         if (livingentity != null) {
            this.livingEntity3 = livingentity;
            this.value240 = clientplayerentity.getYaw();
            this.value241 = clientplayerentity.getPitch();
         }
      }
   }

   @Override
   public void onEnable() {
      this.update13();
   }
}
