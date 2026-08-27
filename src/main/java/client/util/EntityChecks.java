package client.util;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.player.AutoSwap;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

@HookClass(ClientPlayerInteractionManager.class)
public class EntityChecks {
   @Hook(
      method = "method_2905",
      desc = "(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lnet/minecraft/class_1268;)Lnet/minecraft/class_1269;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static ActionResult getActionResultByClientPlayerInteractionManagerPlayerEntityEntityHand(
      ClientPlayerInteractionManager clientPlayerInteractionManager, PlayerEntity playerEntity, Entity entity2, Hand hand
   ) {
      ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
      if (moduledispatcher != null && playerEntity != null && entity2 != null) {
         try {
            ActionResult actionresult = moduledispatcher.getActionResultByPlayerEntityWorldHandEntityEntityHitResult(playerEntity, playerEntity.getWorld(), hand, entity2, null);
            if (actionresult != ActionResult.PASS) {
               return actionresult;
            }
         } catch (Throwable throwable) {
         }
      }

      return (ActionResult)HandleInvoker.getObjectByObjectArray2(clientPlayerInteractionManager, playerEntity, entity2, hand);
   }

   @Hook(
      method = "method_2917",
      desc = "(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lnet/minecraft/class_3966;Lnet/minecraft/class_1268;)Lnet/minecraft/class_1269;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static ActionResult getActionResultByClientPlayerInteractionManagerPlayerEntityEntityEntityHitResultHand(
      ClientPlayerInteractionManager clientPlayerInteractionManager, PlayerEntity playerEntity, Entity entity2, EntityHitResult entityHitResult, Hand hand
   ) {
      ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
      if (moduledispatcher != null && playerEntity != null && entity2 != null) {
         try {
            ActionResult actionresult = moduledispatcher.getActionResultByPlayerEntityWorldHandEntityEntityHitResult(playerEntity, playerEntity.getWorld(), hand, entity2, entityHitResult);
            if (actionresult != ActionResult.PASS) {
               return actionresult;
            }
         } catch (Throwable throwable) {
         }
      }

      return (ActionResult)HandleInvoker.getObjectByObjectArray2(clientPlayerInteractionManager, playerEntity, entity2, entityHitResult, hand);
   }

   @Hook(
      method = "method_2919",
      desc = "(Lnet/minecraft/class_1657;Lnet/minecraft/class_1268;)Lnet/minecraft/class_1269;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static ActionResult getActionResultByClientPlayerInteractionManagerPlayerEntityHand(ClientPlayerInteractionManager clientPlayerInteractionManager, PlayerEntity playerEntity, Hand hand) {
      Float f = AutoSwap.getFloat();
      if (f != null && playerEntity != null) {
         float f1 = playerEntity.getPitch();
         float f2 = playerEntity.prevPitch;

         ActionResult actionresult;
         try {
            playerEntity.setPitch(f);
            playerEntity.prevPitch = f;
            actionresult = (ActionResult)HandleInvoker.getObjectByObjectArray2(clientPlayerInteractionManager, playerEntity, hand);
         } finally {
            playerEntity.setPitch(f1);
            playerEntity.prevPitch = f2;
         }

         return actionresult;
      } else {
         return (ActionResult)HandleInvoker.getObjectByObjectArray2(clientPlayerInteractionManager, playerEntity, hand);
      }
   }

   @Hook(
      method = "method_2918",
      desc = "(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onClientPlayerInteractionManagerPlayerEntityEntity(ClientPlayerInteractionManager clientPlayerInteractionManager, PlayerEntity playerEntity, Entity entity2) {
      ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
      if (moduledispatcher != null && playerEntity != null && entity2 != null) {
         try {
            moduledispatcher.getActionResultByPlayerEntityWorldHandEntityEntityHitResult2(playerEntity, playerEntity.getWorld(), Hand.MAIN_HAND, entity2, null);
         } catch (Throwable throwable) {
         }
      }
   }

   @Hook(
      method = "method_2896",
      desc = "(Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static ActionResult getActionResultByClientPlayerInteractionManagerClientPlayerEntityHandBlockHitResult(
      ClientPlayerInteractionManager clientPlayerInteractionManager, ClientPlayerEntity clientPlayerEntity, Hand hand, BlockHitResult blockHitResult
   ) {
      ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
      if (moduledispatcher != null && clientPlayerEntity != null) {
         try {
            ActionResult actionresult = moduledispatcher.getActionResultByPlayerEntityWorldHandBlockHitResult(clientPlayerEntity, clientPlayerEntity.getWorld(), hand, blockHitResult);
            if (actionresult != ActionResult.PASS) {
               return actionresult;
            }
         } catch (Throwable throwable) {
         }
      }

      return (ActionResult)HandleInvoker.getObjectByObjectArray2(clientPlayerInteractionManager, clientPlayerEntity, hand, blockHitResult);
   }
}
