package client.util;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.Feature;
import client.module.client.PanicModule;
import client.module.player.NoPush;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

@HookClass(Entity.class)
public class EntityDamageHelper {
   private static final UnsafeAccess<NoPush> unsafeAccess = new UnsafeAccess<>(NoPush.class);
   private static boolean flag;

   @Hook(
      method = "method_5810",
      desc = "()Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isEntity(Entity entity2) {
      return PanicModule.isFlag() || entity2 != Feature.mc.player || unsafeAccess.getModule2() == null;
   }

   @Hook(
      method = "method_5784",
      desc = "(Lnet/minecraft/class_1313;Lnet/minecraft/class_243;)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onEntityMovementTypeVec3d(Entity entity2, MovementType movementType, Vec3d vec3d) {
      if (entity2 == Feature.mc.player) {
         flag = false;
      }
   }

   @Hook(
      method = "method_5697",
      desc = "(Lnet/minecraft/class_1297;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onEntityEntity(Entity entity2, Entity entity3) {
      if (PanicModule.isFlag() || entity2 != Feature.mc.player || unsafeAccess.getModule2() == null) {
         HandleInvoker.onObjectArray(entity2, entity3);
      }
   }

   @Hook(
      target = LivingEntity.class,
      method = "method_6078",
      desc = "(Lnet/minecraft/class_1282;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onLivingEntityDamageSource(LivingEntity livingEntity, DamageSource damageSource) {
      if (livingEntity instanceof PlayerEntity playerentity) {
         try {
            ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
            if (moduledispatcher != null) {
               moduledispatcher.onPlayerEntity(playerentity);
            }
         } catch (Throwable throwable) {
         }

         KeyboardState.getKeyboardState().update4();
      }
   }

   @Hook(
      method = "method_65038",
      desc = "()Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isEntity2(Entity entity2) {
      return !flag || PanicModule.isFlag() || entity2 != Feature.mc.player;
   }

   @Hook(
      method = "method_5784",
      desc = "(Lnet/minecraft/class_1313;Lnet/minecraft/class_243;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onEntityMovementTypeVec3d2(Entity entity2, MovementType movementType, Vec3d vec3d) {
      if (entity2 == Feature.mc.player) {
         flag = true;
      }
   }
}
