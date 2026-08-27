package client.util;

import client.api.Hook;
import client.api.HookClass;
import client.data.AnimatedFloat;
import client.data.NoSlowState;
import client.enums.InjectPoint;
import client.module.Feature;
import client.module.client.PanicModule;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;

@HookClass(ClientPlayerEntity.class)
public class TargetAnimation {
   private static AnimatedFloat animatedFloat;
   private static float value;
   private static float value2;
   private static float value3;
   private static float value4;
   private static boolean flag;
   private static boolean flag2;
   private static boolean flag3;

   @Hook(
      method = "method_3136",
      desc = "()V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onClientPlayerEntity(ClientPlayerEntity clientPlayerEntity) {
      if (flag) {
         clientPlayerEntity.setYaw(animatedFloat.getValue2());
         clientPlayerEntity.setPitch(animatedFloat.getValue());
      }
   }

   public static void onClientPlayerEntityTail(ClientPlayerEntity clientPlayerEntity) {
      if (flag && clientPlayerEntity == Feature.mc.player) {
         clientPlayerEntity.setYaw(value);
         clientPlayerEntity.setPitch(value2);
      }
   }

   @Hook(
      target = LivingEntity.class,
      method = "method_6007",
      desc = "()V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void setLivingEntity(LivingEntity livingEntity) {
      if (livingEntity == Feature.mc.player) {
         ClientPlayerEntity clientplayerentity = (ClientPlayerEntity)livingEntity;
         flag2 = false;
         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            moduledispatcher.update4();
         }

         if (flag) {
            clientplayerentity.setYaw(value);
            clientplayerentity.setPitch(value2);
         }
      }
   }

   @Hook(
      method = "method_5773",
      desc = "()V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onClientPlayerEntity2(ClientPlayerEntity clientPlayerEntity) {
      if (flag) {
         clientPlayerEntity.lastRenderYaw = value3;
         clientPlayerEntity.lastRenderPitch = value4;
         clientPlayerEntity.renderYaw = value3 + (value - value3) * 0.5F;
         clientPlayerEntity.renderPitch = value4 + (value2 - value4) * 0.5F;
      }
   }

   @Hook(
      target = ClientPlayerEntity.class,
      method = "method_6007",
      desc = "()V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void setClientPlayerEntity(ClientPlayerEntity clientPlayerEntity) {
      if (clientPlayerEntity == Feature.mc.player) {
         flag3 = false;
         if (!PanicModule.isFlag() && clientPlayerEntity.isUsingItem() && !clientPlayerEntity.hasVehicle()) {
            ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
            if (moduledispatcher != null) {
               NoSlowState noslowstate = new NoSlowState();
               moduledispatcher.onNoSlowState(noslowstate);
               flag3 = noslowstate.isFlag();
            }
         }

         flag2 = true;
      }
   }

   @Hook(
      target = LivingEntity.class,
      method = "method_6043",
      desc = "()V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onLivingEntity(LivingEntity livingEntity) {
      if (livingEntity == Feature.mc.player && !PanicModule.isFlag()) {
         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            moduledispatcher.update7();
         }
      }
   }

   @Hook(
      method = "method_3136",
      desc = "()V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onClientPlayerEntity3(ClientPlayerEntity clientPlayerEntity) {
      if (flag) {
         clientPlayerEntity.setYaw(value);
         clientPlayerEntity.setPitch(value2);
      }
   }

   @Hook(
      target = ClientPlayerEntity.class,
      method = "method_6007",
      desc = "()V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void setClientPlayerEntity2(ClientPlayerEntity clientPlayerEntity) {
      if (clientPlayerEntity == Feature.mc.player) {
         flag2 = false;
      }
   }

   @Hook(
      target = LivingEntity.class,
      method = "method_6007",
      desc = "()V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void setLivingEntity2(LivingEntity livingEntity) {
      if (livingEntity == Feature.mc.player) {
         ClientPlayerEntity clientplayerentity = (ClientPlayerEntity)livingEntity;
         flag3 = false;
         if (!PanicModule.isFlag() && clientplayerentity.isUsingItem() && !clientplayerentity.hasVehicle()) {
            ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
            if (moduledispatcher != null) {
               NoSlowState noslowstate = new NoSlowState();
               moduledispatcher.onNoSlowState(noslowstate);
               flag3 = noslowstate.isFlag();
            }
         }

         flag2 = true;
         SneakState.update4();
         if (flag) {
            clientplayerentity.setYaw(animatedFloat.getValue2());
            clientplayerentity.setPitch(animatedFloat.getValue());
         }
      }
   }

   @Hook(
      method = "method_5773",
      desc = "()V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void setClientPlayerEntity3(ClientPlayerEntity clientPlayerEntity) {
      animatedFloat = null;
      flag = false;
      if (!PanicModule.isFlag()) {
         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            moduledispatcher.update();
            animatedFloat = moduledispatcher.getAnimatedFloatByFloatFloat(clientPlayerEntity.getPitch(), clientPlayerEntity.getYaw());
            if (animatedFloat != null && animatedFloat.check() && !clientPlayerEntity.hasVehicle()) {
               value = clientPlayerEntity.getYaw();
               value2 = clientPlayerEntity.getPitch();
               value3 = clientPlayerEntity.renderYaw;
               value4 = clientPlayerEntity.renderPitch;
               flag = true;
            }
         }
      }
   }

   @Hook(
      target = ClientPlayerEntity.class,
      method = "method_6115",
      desc = "()Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isClientPlayerEntity(ClientPlayerEntity clientPlayerEntity) {
      return !flag2 || !flag3 || clientPlayerEntity != Feature.mc.player;
   }

   @Hook(
      target = LivingEntity.class,
      method = "method_6115",
      desc = "()Z",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isLivingEntity(LivingEntity livingEntity) {
      return !flag2 || !flag3 || livingEntity != Feature.mc.player;
   }
}
