package client.module.client;

import b.Boot;
import client.api.Icon;
import client.data.CharMap;
import client.gui.screen.ClickGuiScreen;
import client.gui.widget.UiContext;
import client.module.Category;
import client.module.CategoryType;
import client.module.Module;
import client.module.player.ItemTracker;
import client.module.render.ItemESP;
import client.module.render.NameTags;
import client.module.render.PearlTracer;
import client.module.render.TntTimer;
import client.module.render.Tracers;
import client.render.GameRendererHooks;
import client.render.OverlayFramebuffers;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.util.ModuleDispatcher;
import client.util.NotificationManager;
import client.util.UnsafeAccess;
import java.util.function.Predicate;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;

public class StreamBypass extends Module {
   private static final UnsafeAccess<StreamBypass> unsafeAccess = new UnsafeAccess<>(StreamBypass.class);
   private static final UnsafeAccess<Tracers> unsafeAccess2 = new UnsafeAccess<>(Tracers.class);
   private static final UnsafeAccess<NameTags> unsafeAccess3 = new UnsafeAccess<>(NameTags.class);
   private static final UnsafeAccess<ItemESP> unsafeAccess4 = new UnsafeAccess<>(ItemESP.class);
   private static final UnsafeAccess<ItemTracker> unsafeAccess5 = new UnsafeAccess<>(ItemTracker.class);
   private static final UnsafeAccess<PearlTracer> unsafeAccess6 = new UnsafeAccess<>(PearlTracer.class);
   private static final UnsafeAccess<TntTimer> unsafeAccess7 = new UnsafeAccess<>(TntTimer.class);
   private static final UnsafeAccess<Waypoints> unsafeAccess8 = new UnsafeAccess<>(Waypoints.class);
   private static final MatrixStack matrixStack = new MatrixStack();
   private static final WorldRenderContext worldRenderContext = new WorldRenderContext();
   private static boolean flag;
   private static boolean flag2;
   private static boolean flag3;
   private static DrawContext drawContext;
   private BooleanSetting hud;
   private BooleanSetting gui;
   private BooleanSetting overlei;
   private BooleanSetting plashkiVMire;

   public StreamBypass() {
      super("StreamBypass", Category.CLIENT);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Худ");
      booleansetting.setDescription("Прятать наши худ-элементы");
      this.hud = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Гуи");
      booleansetting.setDescription("Прятать меню клиента");
      this.gui = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Оверлеи");
      booleansetting.setDescription("Прятать оверлеи модулей");
      this.overlei = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Плашки в мире");
      booleansetting.setDescription("Прятать нейметеги, ItemESP и прочие плашки в мире");
      this.plashkiVMire = booleansetting;
      this.addSettings(new Setting[]{this.hud, this.gui, this.overlei, this.plashkiVMire});
   }

   public static boolean check3() {
      return isPredicate(StreamBypass::isStreamBypass2);
   }

   public static void update11() {
      StreamBypass streambypass = getStreamBypass();
      if (streambypass != null) {
         RenderTickCounter rendertickcounter = GameRendererHooks.getRenderTickCounter();
         if (rendertickcounter == null) {
            rendertickcounter = RenderTickCounter.ONE;
         }

         if (drawContext == null) {
            drawContext = new DrawContext(mc, mc.getBufferBuilders().getEntityVertexConsumers());
         }

         flag = true;

         try {
            streambypass.render(drawContext, rendertickcounter);
         } catch (Throwable throwable1) {
         } finally {
            flag = false;

            try {
               drawContext.draw();
            } catch (Throwable throwable) {
            }
         }
      }
   }

   public static boolean isFlag() {
      return flag;
   }

   private static boolean isStreamBypass(StreamBypass streamBypass) {
      return streamBypass.overlei.isFlag3();
   }

   public static void update12() {
      StreamBypass streambypass = getStreamBypass();
      if (streambypass != null && streambypass.plashkiVMire.isFlag3()) {
         if (mc.world != null && mc.player != null) {
            RenderTickCounter rendertickcounter = GameRendererHooks.getRenderTickCounter();
            if (rendertickcounter != null) {
               matrixStack.loadIdentity();
               WorldRenderContext worldrendercontext = worldRenderContext.getWorldRenderContextByMatrixStackCameraRenderTickCounterClientWorld(
                  matrixStack, mc.gameRenderer.getCamera(), rendertickcounter, mc.world
               );
               flag = true;

               try {
                  render8(worldrendercontext);
               } catch (Throwable throwable) {
               } finally {
                  flag = false;
               }
            }
         }
      }
   }

   public static boolean isFlag2() {
      return flag2;
   }

   private static boolean isStreamBypass2(StreamBypass streamBypass) {
      return streamBypass.gui.isFlag3();
   }

   @Override
   public void onDisable() {
      flag = false;
      Boot.sbSetActive(false);
   }

   public static boolean check4() {
      return isPredicate(StreamBypass::isStreamBypass3);
   }

   public static void update13() {
      flag = false;
      Boot.sbDestroy();
      OverlayFramebuffers.update();
      drawContext = null;
      flag3 = false;
   }

   private static boolean isStreamBypass3(StreamBypass streamBypass) {
      return streamBypass.hud.isFlag3();
   }

   private static boolean isStreamBypass4(StreamBypass streamBypass) {
      return streamBypass.plashkiVMire.isFlag3();
   }

   private void onRenderTickCounterDrawContext(RenderTickCounter renderTickCounter, DrawContext drawContext) {
      if (this.overlei.isFlag3()) {
         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            moduledispatcher.render(drawContext, renderTickCounter);
         }

         Tracers tracers = (Tracers)unsafeAccess2.getModule2();
         if (tracers != null) {
            tracers.render2(drawContext);
         }
      }

      if (this.hud.isFlag3()) {
         UiContext.getInstance().render(drawContext, 1.0F);
      }
   }

   public static boolean check5() {
      return flag3 && !flag && unsafeAccess.getModule2() != null;
   }

   public static StreamBypass getStreamBypass() {
      return flag3 ? (StreamBypass)unsafeAccess.getModule2() : null;
   }

   private static boolean isPredicate(Predicate<StreamBypass> predicate) {
      if (flag) {
         return false;
      } else {
         StreamBypass streambypass = getStreamBypass();
         return streambypass != null && predicate.test(streambypass);
      }
   }

   private void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
      boolean flagx = this.world() != null && this.player() != null && (this.options() == null || !this.options().hudHidden);
      if (flagx && (this.hud.isFlag3() || this.overlei.isFlag3())) {
         this.onRenderTickCounterDrawContext(renderTickCounter, drawContext);
         flag2 = true;
      }

      if (this.currentScreen() != null) {
         double d0 = this.client().getWindow().getScaleFactor();
         int i = (int)(this.client().mouse.getX() / d0);
         int j = (int)(this.client().mouse.getY() / d0);
         float f = renderTickCounter.getTickDelta(false);
         if (this.currentScreen() instanceof ClickGuiScreen clickguiscreen) {
            if (this.gui.isFlag3()) {
               clickguiscreen.render(drawContext, i, j, f);
               flag2 = true;
            }
         } else if (!(this.currentScreen() instanceof ChatScreen)) {
            if (flag2) {
               OverlayFramebuffers.onScreenDrawContextIntIntFloat(this.currentScreen(), drawContext, i, j, f);
            }
         }
      }
   }

   public static void setFlag2() {
      flag2 = false;
   }

   private static void render8(WorldRenderContext worldRenderContext) {
      ItemESP itemesp = (ItemESP)unsafeAccess4.getModule2();
      ItemTracker itemtracker = (ItemTracker)unsafeAccess5.getModule2();
      PearlTracer pearltracer = (PearlTracer)unsafeAccess6.getModule2();
      TntTimer tnttimer = (TntTimer)unsafeAccess7.getModule2();
      Waypoints waypoints = (Waypoints)unsafeAccess8.getModule2();
      NameTags nametags = (NameTags)unsafeAccess3.getModule2();
      if (itemesp != null || itemtracker != null || pearltracer != null || tnttimer != null || waypoints != null || nametags != null) {
         flag2 = true;
         if (itemesp != null) {
            itemesp.render5(worldRenderContext);
         }

         if (itemtracker != null) {
            itemtracker.render5(worldRenderContext);
         }

         if (pearltracer != null) {
            pearltracer.render5(worldRenderContext);
         }

         if (tnttimer != null) {
            tnttimer.render5(worldRenderContext);
         }

         if (waypoints != null) {
            waypoints.render5(worldRenderContext);
         }

         if (nametags != null) {
            nametags.render7(worldRenderContext);
         }
      }
   }

   public static boolean check6() {
      return isPredicate(StreamBypass::isStreamBypass4);
   }

   @Override
   public void onEnable() {
      flag3 = Boot.sbLoad();
      if (!flag3) {
         NotificationManager notificationmanager = NotificationManager.getInstance();
         Icon icon1 = Icon.getIconByCategoryType(CategoryType.INFO);
         String s2 = CharMap.getStringByString(this.getName());
         String s1 = "- Нативная часть недоступна";
         String s = s2;
         Icon icon = icon1;
         notificationmanager.onStringIconString(s, icon, s1);
         this.forceDisabled();
      } else {
         Boot.sbSetActive(true);
      }
   }

   public static boolean check7() {
      return isPredicate(StreamBypass::isStreamBypass);
   }
}
