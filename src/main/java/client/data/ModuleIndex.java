package client.data;

import client.module.Module;
import client.module.client.CleanHud;
import client.module.client.HudModule;
import client.module.client.MSF;
import client.module.client.PanicModule;
import client.module.client.SliskGui;
import client.module.client.SoundsModule;
import client.module.client.StreamBypass;
import client.module.client.ThemeModule;
import client.module.client.Waypoints;
import client.module.combat.AimAssist;
import client.module.combat.Aura;
import client.module.combat.AnchorTap;
import client.module.combat.AntiBot;
import client.module.combat.AutoAnchor;
import client.module.combat.AutoCrystal;
import client.module.combat.AutoStart;
import client.module.combat.AutoTotem;
import client.module.combat.CrystalTap;
import client.module.combat.Hitbox;
import client.module.combat.MaceSwap;
import client.module.combat.ShieldBreaker;
import client.module.combat.ShieldTap;
import client.module.combat.ShiftTap;
import client.module.combat.TapMouse;
import client.module.combat.TriggerBot;
import client.module.combat.WebTrap;
import client.module.movement.Blink;
import client.module.movement.BoatFly;
import client.module.movement.ElytraSwap;
import client.module.movement.FakeLag;
import client.module.movement.FreeCam;
import client.module.movement.InvMove;
import client.module.movement.JumpReset;
import client.module.movement.LagRange;
import client.module.movement.NoJumpDelay;
import client.module.movement.NoSlow;
import client.module.movement.NoWeb;
import client.module.movement.Speed;
import client.module.movement.Sprint;
import client.module.player.AirStuck;
import client.module.player.AutoAccept;
import client.module.player.AutoHealthPot;
import client.module.player.AutoInteract;
import client.module.player.AutoLeave;
import client.module.player.AutoResell;
import client.module.player.AutoRespawn;
import client.module.player.AutoSell;
import client.module.player.AutoSwap;
import client.module.player.AutoTool;
import client.module.player.ChestStealer;
import client.module.player.ContainerHelper;
import client.module.player.FTHotkeys;
import client.module.player.FastSearch;
import client.module.player.FastUse;
import client.module.player.GhostHand;
import client.module.player.ItemScroller;
import client.module.player.ItemTracker;
import client.module.player.LockSlot;
import client.module.player.NoArmorStand;
import client.module.player.NoInteract;
import client.module.player.NoPush;
import client.module.player.NoServerRotate;
import client.module.player.NoSounds;
import client.module.player.PingSpoof;
import client.module.player.PotionCooldown;
import client.module.player.Protect;
import client.module.player.RPSpoofer;
import client.module.player.SafeLeave;
import client.module.player.SwapWheel;
import client.module.render.AuctionHelper;
import client.module.render.BlockESP;
import client.module.render.ChunkAnimator;
import client.module.render.CompassCooldown;
import client.module.render.FriendChecks;
import client.module.render.ItemColor;
import client.module.render.ItemESP;
import client.module.render.ItemRadius;
import client.module.render.NameTags;
import client.module.render.PearlTracer;
import client.module.render.PlayerChams;
import client.module.render.PlayerESP;
import client.module.render.PlayerScaler;
import client.module.render.ResourceFinder;
import client.module.render.ShowInvisible;
import client.module.render.ShulkerPreview;
import client.module.render.TntTimer;
import client.module.render.Tracers;
import client.module.render.Trajectories;
import client.module.visual.AspectRatio;
import client.module.visual.AttackEffects;
import client.module.visual.Bloom;
import client.module.visual.CameraChecks;
import client.module.visual.CrystalChecks;
import client.module.visual.CustomFog;
import client.module.visual.CustomTime;
import client.module.visual.Enhancer;
import client.module.visual.FakePlayer;
import client.module.visual.FrameSync;
import client.module.visual.FreeLook;
import client.module.visual.FullBright;
import client.module.visual.HandGlow;
import client.module.visual.HitboxChecks;
import client.module.visual.JumpCircles;
import client.module.visual.MotionBlur;
import client.module.visual.NoRender;
import client.module.visual.Saturation;
import client.module.visual.SkyShader;
import client.module.visual.SwingAnimation;
import client.module.visual.TargetESP;
import client.module.visual.ViewModel;
import client.module.visual.Zoom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ModuleIndex {
   private static final Map<Integer, Supplier<Module>> map = new LinkedHashMap<>();
   private static int value;

   static {
      setValue(1);
      setSupplier(HudModule::new);
      setSupplier(SoundsModule::new);
      setSupplier(MSF::new);
      setSupplier(PanicModule::new);
      setSupplier(CleanHud::new);
      setSupplier(SliskGui::new);
      setSupplier(ThemeModule::new);
      setSupplier(Waypoints::new);
      setSupplier(RPSpoofer::new);
      setSupplier(StreamBypass::new);
      setValue(100);
      setSupplier(AimAssist::new);
      setSupplier(Aura::new);
      setSupplier(TriggerBot::new);
      setSupplier(Hitbox::new);
      setSupplier(ShieldBreaker::new);
      setSupplier(AutoTotem::new);
      setSupplier(WebTrap::new);
      setSupplier(AutoCrystal::new);
      setSupplier(CrystalTap::new);
      setSupplier(AnchorTap::new);
      setSupplier(AutoAnchor::new);
      setSupplier(AutoStart::new);
      setSupplier(MaceSwap::new);
      setSupplier(AntiBot::new);
      setSupplier(ShieldTap::new);
      setSupplier(TapMouse::new);
      setValue(200);
      setSupplier(InvMove::new);
      setSupplier(ElytraSwap::new);
      setSupplier(FreeCam::new);
      setSupplier(NoJumpDelay::new);
      setSupplier(JumpReset::new);
      setSupplier(Sprint::new);
      setSupplier(AirStuck::new);
      setSupplier(NoWeb::new);
      setSupplier(Speed::new);
      setSupplier(BoatFly::new);
      setSupplier(FakeLag::new);
      setSupplier(LagRange::new);
      setSupplier(Blink::new);
      setSupplier(NoSlow::new);
      setValue(300);
      setSupplier(NoPush::new);
      setSupplier(FastUse::new);
      setSupplier(AutoHealthPot::new);
      setSupplier(AutoRespawn::new);
      setSupplier(NoArmorStand::new);
      setSupplier(AutoTool::new);
      setSupplier(AutoSwap::new);
      setSupplier(SwapWheel::new);
      setSupplier(AutoLeave::new);
      update();
      setSupplier(NoInteract::new);
      setSupplier(ShiftTap::new);
      setSupplier(PingSpoof::new);
      setSupplier(AuctionHelper::new);
      setSupplier(FastSearch::new);
      setSupplier(ItemScroller::new);
      setSupplier(AutoAccept::new);
      setSupplier(NoServerRotate::new);
      setSupplier(ChestStealer::new);
      setSupplier(SafeLeave::new);
      setSupplier(ItemTracker::new);
      setSupplier(PotionCooldown::new);
      setSupplier(GhostHand::new);
      setSupplier(FTHotkeys::new);
      setSupplier(ContainerHelper::new);
      setSupplier(AutoInteract::new);
      setSupplier(AutoSell::new);
      setSupplier(AutoResell::new);
      setSupplier(LockSlot::new);
      setValue(400);
      setSupplier(NoRender::new);
      setSupplier(NoSounds::new);
      setSupplier(Enhancer::new);
      setSupplier(CameraChecks::new);
      setSupplier(CustomTime::new);
      setSupplier(Saturation::new);
      setSupplier(Bloom::new);
      setSupplier(MotionBlur::new);
      setSupplier(HitboxChecks::new);
      setSupplier(ViewModel::new);
      setSupplier(SwingAnimation::new);
      setSupplier(AspectRatio::new);
      setSupplier(Protect::new);
      setSupplier(FakePlayer::new);
      setSupplier(FullBright::new);
      setSupplier(SkyShader::new);
      setSupplier(CustomFog::new);
      setSupplier(Zoom::new);
      setSupplier(FreeLook::new);
      setSupplier(FrameSync::new);
      setSupplier(AttackEffects::new);
      setSupplier(JumpCircles::new);
      setSupplier(TargetESP::new);
      setValue(500);
      setSupplier(NameTags::new);
      setSupplier(ChunkAnimator::new);
      setSupplier(PlayerESP::new);
      setSupplier(PlayerChams::new);
      setSupplier(FriendChecks::new);
      setSupplier(Tracers::new);
      setSupplier(ItemESP::new);
      setSupplier(BlockESP::new);
      setSupplier(PearlTracer::new);
      setSupplier(ShowInvisible::new);
      setSupplier(PlayerScaler::new);
      setSupplier(Trajectories::new);
      setSupplier(ItemRadius::new);
      setSupplier(CrystalChecks::new);
      setSupplier(ShulkerPreview::new);
      setSupplier(TntTimer::new);
      setSupplier(ItemColor::new);
      setSupplier(ResourceFinder::new);
      setSupplier(CompassCooldown::new);
   }

   public static List getList() {
      ArrayList arraylist = new ArrayList();

      for (Supplier supplier : map.values()) {
         try {
            arraylist.add((Module)supplier.get());
         } catch (Exception exception) {
         }
      }

      return arraylist;
   }

   public static Set getSetByString(String text) {
      HashSet hashset = new HashSet();
      if (text != null && !text.isEmpty()) {
         for (String s : text.split(",")) {
            try {
               int i = Integer.parseInt(s.trim());
               if (map.containsKey(i)) {
                  hashset.add(i);
               }
            } catch (NumberFormatException numberformatexception) {
            }
         }

         return hashset;
      } else {
         return hashset;
      }
   }

   public static List getListBySet(Set<Integer> set) {
      ArrayList arraylist = new ArrayList();

      for (int i : set) {
         Module module = getModuleByInt(i);
         if (module != null) {
            arraylist.add(module);
         }
      }

      return arraylist;
   }

   public static Module getModuleByInt(int count) {
      Supplier supplier = map.get(count);
      return supplier != null ? (Module)supplier.get() : null;
   }

   private static void setValue(int count) {
      value = count;
   }

   private static void update() {
      value++;
   }

   private static void setSupplier(Supplier supplier) {
      map.put(value++, supplier);
   }
}
