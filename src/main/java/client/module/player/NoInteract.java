package client.module.player;

import client.module.Category;
import client.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class NoInteract extends Module {
   public NoInteract() {
      super("NoInteract", Category.PLAYER);
   }

   @Override
   public void onDisable() {
   }

   public static ActionResult getActionResultByBlockHitResultHandWorld(BlockHitResult blockHitResult, Hand hand, World world2) {
      if (mc.player != null && mc.interactionManager != null) {
         mc.interactionManager.interactItem(mc.player, hand);
         return ActionResult.FAIL;
      } else {
         return ActionResult.FAIL;
      }
   }

   public static boolean isItemStack(ItemStack itemStack) {
      return itemStack != null && !itemStack.isEmpty() ? itemStack.getUseAction() != UseAction.NONE : false;
   }

   public static ActionResult getActionResultByEntityPlayerEntityHand(Entity entity2, PlayerEntity playerEntity, Hand hand) {
      if (mc.player != null && mc.interactionManager != null) {
         mc.interactionManager.interactItem(mc.player, hand);
         return ActionResult.FAIL;
      } else {
         return ActionResult.FAIL;
      }
   }

   @Override
   public ActionResult getActionResultByPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      return (ActionResult)(this.notInGame() ? ActionResult.PASS : getActionResultByEntityPlayerEntityHand(entity2, playerEntity, hand));
   }

   @Override
   public ActionResult getActionResultByPlayerEntityWorldHandBlockHitResult(PlayerEntity playerEntity, World world2, Hand hand, BlockHitResult blockHitResult) {
      return (ActionResult)(this.notInGame() ? ActionResult.PASS : getActionResultByBlockHitResultHandWorld(blockHitResult, hand, world2));
   }

   @Override
   public void onEnable() {
   }
}
