package client.util;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.player.LockSlot;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;

@HookClass(ClientPlayerEntity.class)
public class EntityHooks {
   private static final UnsafeAccess<LockSlot> unsafeAccess = new UnsafeAccess<>(LockSlot.class);

   @Hook(
      method = "method_7290",
      desc = "(Z)Z",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static boolean isClientPlayerEntityBoolean(ClientPlayerEntity clientPlayerEntity, boolean flag) {
      LockSlot lockslot = (LockSlot)unsafeAccess.getModule2();
      return lockslot != null && lockslot.check3() ? false : HandleInvoker.isObjectArray(clientPlayerEntity, flag);
   }

   @Hook(
      target = ClientPlayerInteractionManager.class,
      method = "method_2906",
      desc = "(IIILnet/minecraft/class_1713;Lnet/minecraft/class_1657;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onClientPlayerInteractionManagerIntIntIntSlotActionTypePlayerEntity(
      ClientPlayerInteractionManager clientPlayerInteractionManager, int count, int count2, int count3, SlotActionType slotActionType, PlayerEntity playerEntity
   ) {
      LockSlot lockslot = (LockSlot)unsafeAccess.getModule2();
      if (lockslot == null || !lockslot.isSlotActionTypeInt(slotActionType, count2)) {
         HandleInvoker.onObjectArray(clientPlayerInteractionManager, count, count2, count3, slotActionType, playerEntity);
      }
   }
}
