package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.concurrent.ResourceManagerHooks;
import client.enums.InjectPoint;
import client.gui.screen.ScreenHelper;
import client.module.client.PanicModule;
import client.module.combat.TriggerBot;
import client.module.player.GhostHand;
import client.module.player.SafeLeave;
import client.network.NetworkHandlerHooks;
import client.util.AttackEvent;
import client.util.InteractEvent;
import client.util.KeyboardState;
import client.util.ModuleDispatcher;
import client.util.ReflectionCache;
import client.util.UnsafeAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

@HookClass(MinecraftClient.class)
public class GameMenuHooks {
   @Hook(
      method = "method_1574",
      desc = "()V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onMinecraftClient(MinecraftClient minecraftClient) {
      if (minecraftClient.player != null) {
         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            try {
               moduledispatcher.update2();
            } catch (Throwable throwable) {
            }
         }
      }
   }

   @Hook(
      method = "method_55505",
      desc = "()V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onMinecraftClient2(MinecraftClient minecraftClient) {
      if (NetworkHandlerHooks.flag) {
         NetworkHandlerHooks.flag = false;
         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            try {
               moduledispatcher.update3();
            } catch (Throwable throwable1) {
            }
         }

         try {
            KeyboardState.getKeyboardState().update4();
         } catch (Throwable throwable) {
         }
      }
   }

   @Hook(
      method = "method_1583",
      desc = "()V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onMinecraftClient3(MinecraftClient minecraftClient) {
      if (!PanicModule.isFlag() && minecraftClient.player != null) {
         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            AttackEvent attackevent = moduledispatcher.getAttackEventByPlayerEntity(minecraftClient.player);
            if (attackevent.isFlag()) {
               return;
            }

            if (attackevent.isFlag2()) {
               minecraftClient.gameRenderer.updateCrosshairTarget(1.0F);
            }
         }
      }

      HandleInvoker.onObjectArray(minecraftClient);
   }

   @Hook(
      method = "method_1522",
      desc = "()Lnet/minecraft/class_276;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static Framebuffer getFramebufferByMinecraftClient(MinecraftClient minecraftClient) {
      Framebuffer framebuffer = OverlayFramebuffers.getFramebuffer3();
      return framebuffer != null ? framebuffer : (Framebuffer)HandleInvoker.getObjectByObjectArray2(minecraftClient);
   }

   @Hook(
      target = GameMenuScreen.class,
      method = "method_47632",
      desc = "()V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onGameMenuScreen(GameMenuScreen gameMenuScreen) {
      if (!SafeLeave.isFlag()) {
         HandleInvoker.onObjectArray(gameMenuScreen);
      } else if (SafeLeave.isFlag2()) {
         SafeLeave.setFlag2(false);
         HandleInvoker.onObjectArray(gameMenuScreen);
      } else {
         SafeLeave.setFlag2(true);
         if (ScreenHelper.time != -1L) {
            ButtonWidget buttonwidget = (ButtonWidget)ReflectionCache.getObjectByObjectLong(gameMenuScreen, ScreenHelper.time);
            if (buttonwidget != null) {
               buttonwidget.active = true;
            }
         }
      }
   }

   @Hook(
      method = "method_1536",
      desc = "()Z",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static boolean isMinecraftClient(MinecraftClient minecraftClient) {
      LivingEntity livingentity = TriggerBot.getLivingEntity2();
      if (livingentity == null || livingentity.isAlive() && TriggerBot.isLivingEntity2(livingentity)) {
         if (livingentity == null) {
            livingentity = GhostHand.getLivingEntity2();
         }

         HitResult hitresult = null;
         if (livingentity != null && livingentity.isAlive()) {
            hitresult = minecraftClient.crosshairTarget;
            minecraftClient.crosshairTarget = new EntityHitResult(livingentity, livingentity.getBoundingBox().getCenter());
         }

         try {
            if (!PanicModule.isFlag() && minecraftClient.crosshairTarget instanceof EntityHitResult entityhitresult) {
               ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
               if (moduledispatcher != null && minecraftClient.player != null) {
                  InteractEvent interactevent = moduledispatcher.getInteractEventByPlayerEntityHandEntityEntityHitResult(
                     minecraftClient.player, Hand.MAIN_HAND, entityhitresult.getEntity(), entityhitresult
                  );
                  if (interactevent.isFlag()) {
                     return false;
                  }
               }
            }

            return HandleInvoker.isObjectArray(minecraftClient);
         } finally {
            if (hitresult != null) {
               minecraftClient.crosshairTarget = hitresult;
            }
         }
      } else {
         return false;
      }
   }

   @Hook(
      method = "method_1574",
      desc = "()V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onMinecraftClient4(MinecraftClient minecraftClient) {
      try {
         ResourceManagerHooks.update();
      } catch (Throwable throwable1) {
      }

      if (minecraftClient.player != null) {
         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            try {
               moduledispatcher.update6();
            } catch (Throwable throwable) {
            }
         }
      }
   }
}
