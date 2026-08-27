package client.util;

import client.concurrent.ModuleRegistry;
import client.concurrent.SystemClient;
import client.data.AnimatedFloat;
import client.data.NoSlowState;
import client.data.Rotation;
import client.data.SlotSelection;
import client.module.Module;
import client.module.client.PanicModule;
import client.render.HudRenderContext;
import client.render.ItemIconCache;
import client.render.WorldRenderContext;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class ModuleDispatcher {
   private static final ModuleRegistry moduleRegistry = new ModuleRegistry();
   private ModuleRegistry moduleRegistry2;
   private final HudRenderContext hudRenderContext = new HudRenderContext();
   private final AnimatedFloat animatedFloat = new AnimatedFloat(0.0F, 0.0F, true);
   private final SlotSelection slotSelection = new SlotSelection(0, 0, 0);
   private final Rotation rotation = new Rotation(0.0, 0.0);

   public void update() {
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).update9();
         } catch (Exception exception) {
         }
      }
   }

   public void render(WorldRenderContext worldRenderContext) {
      if (worldRenderContext != null) {
         List list = this.getModuleRegistry().getList22();
         int i = 0;

         for (int j = list.size(); i < j; i++) {
            try {
               ((Module)list.get(i)).render5(worldRenderContext);
            } catch (Exception exception) {
            }
         }
      }
   }

   public void onString(String text) {
      if (text != null && !text.trim().isEmpty()) {
         List list = this.getModuleRegistry().getList22();
         int i = 0;

         for (int j = list.size(); i < j; i++) {
            try {
               ((Module)list.get(i)).onString(text);
            } catch (Exception exception) {
            }
         }
      }
   }

   public ActionResult getActionResultByPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      return this.getActionResultByFunction(var5x -> var5x.getActionResultByPlayerEntityWorldHandEntityEntityHitResult(playerEntity, world2, hand, entity2, entityHitResult));
   }

   public void update2() {
      if (!PanicModule.isFlag()) {
         try {
            SphereItems.update3();
         } catch (Exception exception4) {
         }

         try {
            InventoryActions.update3();
         } catch (Exception exception3) {
         }

         try {
            TickCounter.update();
         } catch (Exception exception2) {
         }

         try {
            SystemClient systemclient = SystemClient.getInstance();
            if (systemclient != null) {
               InputCallbacks inputcallbacks = systemclient.getInputCallbacks();
               if (inputcallbacks != null) {
                  inputcallbacks.setModuleDispatcher(this);
               }

               KeyboardState keyboardstate = systemclient.getKeyboardState();
               if (keyboardstate != null) {
                  keyboardstate.update5();
               }
            }
         } catch (Exception exception1) {
         }

         List list = this.getModuleRegistry().getList22();
         int i = 0;

         for (int j = list.size(); i < j; i++) {
            try {
               ((Module)list.get(i)).onTick();
            } catch (Exception exception) {
            }
         }
      }
   }

   public void render2(WorldRenderContext worldRenderContext) {
      if (worldRenderContext != null) {
         List list = this.getModuleRegistry().getList22();
         int i = 0;

         for (int j = list.size(); i < j; i++) {
            try {
               ((Module)list.get(i)).render2(worldRenderContext);
            } catch (Exception exception) {
            }
         }
      }
   }

   public void render3(WorldRenderContext worldRenderContext) {
      if (worldRenderContext != null) {
         List list = this.getModuleRegistry().getList22();
         int i = 0;

         for (int j = list.size(); i < j; i++) {
            try {
               ((Module)list.get(i)).render6(worldRenderContext);
            } catch (Exception exception) {
            }
         }
      }
   }

   public void update3() {
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).update4();
         } catch (Exception exception) {
         }
      }
   }

   public void update4() {
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).update5();
         } catch (Exception exception) {
         }
      }
   }

   public void render4(WorldRenderContext worldRenderContext) {
      if (worldRenderContext != null) {
         List list = this.getModuleRegistry().getList22();
         int i = 0;

         for (int j = list.size(); i < j; i++) {
            try {
               ((Module)list.get(i)).render7(worldRenderContext);
            } catch (Exception exception) {
            }
         }
      }
   }

   public void render5(WorldRenderContext worldRenderContext) {
      if (worldRenderContext != null) {
         List list = this.getModuleRegistry().getList22();
         int i = 0;

         for (int j = list.size(); i < j; i++) {
            try {
               ((Module)list.get(i)).render(worldRenderContext);
            } catch (Exception exception) {
            }
         }
      }
   }

   public void update5() {
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).update7();
         } catch (Exception exception) {
         }
      }
   }

   public SlotSelection getSlotSelectionByIntIntInt(int count, int count2, int count3) {
      SlotSelection slotselection = this.slotSelection.getSlotSelectionByIntIntInt(count, count2, count3);
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).onSlotSelection(slotselection);
         } catch (Exception exception) {
         }

         if (slotselection.isFlag()) {
            break;
         }
      }

      return slotselection;
   }

   private ActionResult getActionResultByFunction(Function<Module, ActionResult> function2) {
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ActionResult actionresult = (ActionResult)function2.apply((Module)list.get(i));
            if (actionresult != ActionResult.PASS) {
               return actionresult;
            }
         } catch (Exception exception) {
         }
      }

      return ActionResult.PASS;
   }

   public void update6() {
      if (!PanicModule.isFlag()) {
         try {
            SneakState.update4();
         } catch (Exception exception4) {
         }

         try {
            SphereItems.update2();
         } catch (Exception exception3) {
         }

         try {
            SphereItems.update5();
         } catch (Exception exception2) {
         }
      }

      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).update8();
         } catch (Exception exception1) {
         }
      }

      try {
         SneakState.update();
      } catch (Exception exception) {
      }
   }

   public AnimatedFloat getAnimatedFloatByFloatFloat(float value, float value2) {
      AnimatedFloat animatedfloat = this.animatedFloat.getAnimatedFloatByFloatFloatBoolean(value, value2, true);
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).onAnimatedFloat(animatedfloat);
         } catch (Exception exception) {
         }
      }

      return animatedfloat;
   }

   public void onPlayerEntity(PlayerEntity playerEntity) {
      if (playerEntity != null) {
         SystemClient systemclient = SystemClient.getInstance();
         HashUtil hashutil = systemclient != null ? systemclient.getHashUtil() : null;
         if (hashutil != null) {
            hashutil.update();
         }

         try {
            List list = this.getModuleRegistry().getList22();
            int i = 0;

            for (int j = list.size(); i < j; i++) {
               try {
                  ((Module)list.get(i)).onPlayerEntity(playerEntity);
               } catch (Exception exception) {
               }
            }
         } finally {
            if (hashutil != null) {
               hashutil.setFlag2();
            }
         }
      }
   }

   private void onListConsumer(List<Module> list, Consumer<Module> consumer) {
      if (!list.isEmpty()) {
         int i = 0;

         for (int j = list.size(); i < j; i++) {
            try {
               consumer.accept(list.get(i));
            } catch (Exception exception) {
            }
         }
      }
   }

   public void setModuleRegistry2(ModuleRegistry moduleRegistry) {
      this.moduleRegistry2 = moduleRegistry;
   }

   public Rotation getRotationByDoubleDouble(double value, double value2) {
      Rotation rotationx = this.rotation.getRotationByDoubleDouble(value, value2);
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).onRotation(rotationx);
         } catch (Exception exception) {
         }

         if (rotationx.isFlag()) {
            break;
         }
      }

      return rotationx;
   }

   public void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
      try {
         ItemIconCache.render(drawContext);
      } catch (Exception exception1) {
      }

      HudRenderContext hudrendercontext = this.hudRenderContext.getHudRenderContextByDrawContextRenderTickCounter(drawContext, renderTickCounter);
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).onHudRenderContext(hudrendercontext);
         } catch (Exception exception) {
         }
      }
   }

   private ModuleRegistry getModuleRegistry() {
      if (PanicModule.isFlag()) {
         return moduleRegistry;
      } else {
         ModuleRegistry moduleregistry = this.moduleRegistry2;
         if (moduleregistry != null) {
            return moduleregistry;
         } else {
            SystemClient systemclient = SystemClient.getInstance();
            if (systemclient == null) {
               return moduleRegistry;
            } else {
               moduleregistry = systemclient.getModuleRegistry();
               if (moduleregistry == null) {
                  return moduleRegistry;
               } else {
                  this.moduleRegistry2 = moduleregistry;
                  return moduleregistry;
               }
            }
         }
      }
   }

   public InteractEvent getInteractEventByPlayerEntityHandEntityEntityHitResult(PlayerEntity playerEntity, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      InteractEvent interactevent = new InteractEvent(playerEntity, hand, entityHitResult);
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).onInteractEvent(interactevent);
         } catch (Exception exception) {
         }
      }

      return interactevent;
   }

   public ActionResult getActionResultByPlayerEntityWorldHandEntityEntityHitResult2(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).onPlayerEntityWorldHandEntityEntityHitResult(playerEntity, world2, hand, entity2, entityHitResult);
         } catch (Exception exception) {
         }
      }

      return ActionResult.PASS;
   }

   public void render6(WorldRenderContext worldRenderContext) {
      if (worldRenderContext != null) {
         List list = this.getModuleRegistry().getList22();
         int i = 0;

         for (int j = list.size(); i < j; i++) {
            try {
               ((Module)list.get(i)).render3(worldRenderContext);
            } catch (Exception exception) {
            }
         }
      }
   }

   public ActionResult getActionResultByPlayerEntityWorldHandBlockHitResult(PlayerEntity playerEntity, World world2, Hand hand, BlockHitResult blockHitResult) {
      return this.getActionResultByFunction(var4x -> var4x.getActionResultByPlayerEntityWorldHandBlockHitResult(playerEntity, world2, hand, blockHitResult));
   }

   public AttackEvent getAttackEventByPlayerEntity(PlayerEntity playerEntity) {
      AttackEvent attackevent = new AttackEvent(playerEntity);
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).onAttackEvent(attackevent);
         } catch (Exception exception) {
         }
      }

      return attackevent;
   }

   public void onNoSlowState(NoSlowState noSlowState) {
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).onNoSlowState(noSlowState);
         } catch (Exception exception) {
         }
      }
   }

   public void onString2(String text) {
      this.onListConsumer(this.getModuleRegistry().getList32(), Module::update3);
   }

   public void update7() {
      List list = this.getModuleRegistry().getList22();
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         try {
            ((Module)list.get(i)).update6();
         } catch (Exception exception) {
         }
      }
   }
}
