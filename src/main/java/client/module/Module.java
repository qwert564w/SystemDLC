package client.module;

import client.audio.SoundEngine;
import client.concurrent.ModuleRegistry;
import client.concurrent.SystemClient;
import client.concurrent.Translations;
import client.data.AnimatedFloat;
import client.data.NoSlowState;
import client.data.Rotation;
import client.data.SlotSelection;
import client.enums.SoundEvent;
import client.network.PacketEvent;
import client.render.HudRenderContext;
import client.render.IconAtlas;
import client.render.WorldRenderContext;
import client.setting.KeybindSetting;
import client.setting.Setting;
import client.util.AttackEvent;
import client.util.InteractEvent;
import client.util.NotificationManager;
import client.util.TextHash;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public abstract class Module extends Feature {
   private final String name;
   private final Category category;
   private volatile boolean enabled = false;
   private boolean toggling = false;
   private final List<Setting> settings = new ArrayList<>();
   private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
   protected KeybindSetting keybind;
   protected static final IconAtlas icon = IconAtlas.getIconAtlasLoader().getIconAtlasLoaderByString2("a").getIconAtlasLoaderByString("a").getIconAtlas();

   public Module(String text, Category category2) {
      this.name = text;
      this.category = category2;
      if (this.keybind == null) {
         KeybindSetting keybindsetting = new KeybindSetting("", "", -1, this::toggle);
         keybindsetting.setName("Клавиша включения");
         this.keybind = keybindsetting;
         this.addSetting(this.keybind);
      }
   }

   private static boolean check() {
      try {
         SystemClient systemclient = SystemClient.getInstance();
         return systemclient != null && systemclient.getHashUtil() != null && systemclient.getHashUtil().isFlag3();
      } catch (Exception exception) {
         return false;
      }
   }

   public void render(WorldRenderContext worldRenderContext) {
   }

   public synchronized void toggle() {
      this.setEnabled(!this.enabled);
   }

   public void onTick() {
   }

   public void onAnimatedFloat(AnimatedFloat animatedFloat) {
   }

   private void bindSettingListener(Setting setting2) {
      setting2.setOnChange(() -> {
         this.onSettingChanged(setting2);

         try {
            SystemClient systemclient = SystemClient.getInstance();
            if (systemclient != null && systemclient.getHashUtil() != null) {
               systemclient.getHashUtil().update5();
            }
         } catch (Exception exception) {
         }
      });
   }

   public void onString(String text) {
   }

   public void render2(WorldRenderContext worldRenderContext) {
   }

   private synchronized void applyEnabled(boolean flag, AnimatedFloat animatedFloat) {
      if (this.enabled != flag) {
         if (!this.toggling) {
            this.toggling = true;
            this.enabled = flag;

            try {
               if (flag) {
                  this.onEnable();
                  SoundEngine.getInstance().onSoundEvent(SoundEvent.MODULE_ENABLE);
               } else {
                  this.onDisable();
                  SoundEngine.getInstance().onSoundEvent(SoundEvent.MODULE_DISABLE);
               }

               if (this.check2() && !check()) {
                  NotificationManager.getInstance().onBooleanModule(flag, this);
               }
            } catch (Exception exception2) {
            } finally {
               this.toggling = false;

               try {
                  ModuleRegistry moduleregistry = SystemClient.getInstance().getModuleRegistry();
                  if (moduleregistry != null) {
                     moduleregistry.update2();
                  }
               } catch (Exception exception1) {
               }

               try {
                  SystemClient systemclient = SystemClient.getInstance();
                  if (systemclient != null && systemclient.getHashUtil() != null) {
                     systemclient.getHashUtil().update5();
                  }
               } catch (Exception exception) {
               }
            }
         }
      }
   }

   public void setEnabledSilent(boolean flag) {
      if (this.enabled != flag) {
         this.enabled = flag;

         try {
            ModuleRegistry moduleregistry = SystemClient.getInstance().getModuleRegistry();
            if (moduleregistry != null) {
               moduleregistry.update2();
            }
         } catch (Exception exception) {
         }
      }
   }

   public String getName() {
      return this.name;
   }

   public Setting getSettingByName(String text) {
      if (text != null && !text.trim().isEmpty()) {
         this.lock.readLock().lock();

         Setting setting2;
         try {
            for (Setting setting : this.settings) {
               if (setting.getName().equals(text)) {
                  return setting;
               }
            }

            Iterator iterator = this.settings.iterator();

            String s;
            Setting setting1;
            do {
               if (!iterator.hasNext()) {
                  return null;
               }

               setting1 = (Setting)iterator.next();
               s = setting1.getName();
            } while (!TextHash.isStringString(text, s));

            setting2 = setting1;
         } finally {
            this.lock.readLock().unlock();
         }

         return setting2;
      } else {
         return null;
      }
   }

   public List getVisibleSettings() {
      this.lock.readLock().lock();

      ArrayList arraylist1;
      try {
         ArrayList arraylist = new ArrayList();

         for (Setting setting : this.settings) {
            if (setting.isVisible()) {
               arraylist.add(setting);
            }
         }

         arraylist1 = arraylist;
      } finally {
         this.lock.readLock().unlock();
      }

      return arraylist1;
   }

   public void update() {
   }

   public int getSettingCount() {
      this.lock.readLock().lock();

      int i;
      try {
         i = this.settings.size();
      } finally {
         this.lock.readLock().unlock();
      }

      return i;
   }

   public void render3(WorldRenderContext worldRenderContext) {
   }

   protected void onSettingChanged(Setting setting2) {
   }

   public List<Setting> getSettings() {
      return this.settings;
   }

   public boolean hasSetting(String text) {
      return this.getSettingByName(text) != null;
   }

   public void update2() {
   }

   public void render4(WorldRenderContext worldRenderContext) {
   }

   public abstract void onDisable();

   public boolean isEnabled() {
      return this.enabled;
   }

   public void render5(WorldRenderContext worldRenderContext) {
   }

   public void forceDisabled() {
      this.enabled = false;
   }

   public void update3() {
   }

   protected boolean check2() {
      return true;
   }

   public void render6(WorldRenderContext worldRenderContext) {
   }

   public Setting getSettingByHash(String text) {
      if (text != null && !text.isEmpty()) {
         this.lock.readLock().lock();

         Setting setting1;
         try {
            Iterator iterator = this.settings.iterator();

            Setting setting;
            do {
               if (!iterator.hasNext()) {
                  return null;
               }

               setting = (Setting)iterator.next();
            } while (!setting.getNameHash().equals(text));

            setting1 = setting;
         } finally {
            this.lock.readLock().unlock();
         }

         return setting1;
      } else {
         return null;
      }
   }

   public void onRotation(Rotation rotation) {
   }

   public List getSettingsCopy() {
      this.lock.readLock().lock();

      ArrayList arraylist;
      try {
         arraylist = new ArrayList<>(this.settings);
      } finally {
         this.lock.readLock().unlock();
      }

      return arraylist;
   }

   public void onPlayerEntity(PlayerEntity playerEntity) {
   }

   public void addSettings(Setting... setting2) {
      if (setting2 != null && setting2.length != 0) {
         this.lock.writeLock().lock();

         try {
            for (Setting setting : setting2) {
               if (setting != null && this.settings.stream().noneMatch(var1x -> var1x.getName().equals(setting.getName()))) {
                  this.settings.add(setting);
                  this.bindSettingListener(setting);
               }
            }
         } finally {
            this.lock.writeLock().unlock();
         }
      }
   }

   public void onPacketEvent(PacketEvent packetEvent) {
   }

   public Category getCategory() {
      return this.category;
   }

   public void setEnabled(boolean flag) {
      this.setEnabled(flag, null);
   }

   public void setEnabled(boolean flag, AnimatedFloat animatedFloat) {
      if (this.client() != null && !this.client().isOnThread()) {
         this.client().execute(() -> this.applyEnabled(flag, animatedFloat));
      } else {
         this.applyEnabled(flag, animatedFloat);
      }
   }

   public void onSlotSelection(SlotSelection slotSelection) {
   }

   // By COLLAPSELOADER
      // by COLLAPSELOADER
           // By COLLAPSELOADER

   public void onInteractEvent(InteractEvent interactEvent) {
   }

   public String getDisplayName() {
      return Translations.getInstance().getStringByString2(this.name);
   }

   public ReentrantReadWriteLock getLock() {
      return this.lock;
   }

   public void onAttackEvent(AttackEvent attackEvent) {
   }

   public void onHudRenderContext(HudRenderContext hudRenderContext) {
   }

   public KeybindSetting getKeybindSetting() {
      return this.keybind;
   }

   public void addSetting(Setting setting2) {
      if (setting2 != null) {
         this.lock.writeLock().lock();

         try {
            if (this.settings.stream().noneMatch(var1x -> var1x.getName().equals(setting2.getName()))) {
               this.settings.add(setting2);
               this.bindSettingListener(setting2);
            }
         } finally {
            this.lock.writeLock().unlock();
         }
      }
   }

   public ActionResult getActionResultByPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      return ActionResult.PASS;
   }

   public ActionResult getActionResultByPlayerEntityWorldHandBlockHitResult(PlayerEntity playerEntity, World world2, Hand hand, BlockHitResult blockHitResult) {
      return ActionResult.PASS;
   }

   public void onPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
   }

   public void onNoSlowState(NoSlowState noSlowState) {
   }

   public void update4() {
   }

   public void update5() {
   }

   public void update6() {
   }

   public void update7() {
   }

   public boolean isToggling() {
      return this.toggling;
   }

   public abstract void onEnable();

   public void render7(WorldRenderContext worldRenderContext) {
   }

   public void update8() {
   }

   public void update9() {
   }

   public void update10() {
   }
}
