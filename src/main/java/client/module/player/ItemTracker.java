package client.module.player;

import client.api.Icon;
import client.api.Theme;
import client.concurrent.CooldownTracker;
import client.data.AnimatedFloat;
import client.data.ItemUseRecord;
import client.data.ServerFlagSwitchMap;
import client.enums.CooldownItem;
import client.enums.PacketDirection;
import client.enums.TrackedItem;
import client.module.Category;
import client.module.Module;
import client.module.client.StreamBypass;
import client.module.render.ItemRadius;
import client.network.PacketEvent;
import client.render.IconAtlas;
import client.render.ItemIconCache;
import client.render.RotationBuffer;
import client.render.ShapeShader;
import client.render.TextShader;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.DistanceScale;
import client.util.DroppedItemEntry;
import client.util.ItemCount;
import client.util.ItemIcons;
import client.util.ItemTrackEntry;
import client.util.NotificationManager;
import client.util.SphereItems;
import client.util.TimeFormat;
import client.util.UnsafeAccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.RemoveEntityStatusEffectS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;

public class ItemTracker extends Module {
   private static final UnsafeAccess<ItemRadius> unsafeAccess = new UnsafeAccess<>(ItemRadius.class);
   private static final UnsafeAccess<Protect> unsafeAccess2 = new UnsafeAccess<>(Protect.class);
   private static final UnsafeAccess<AutoSwap> unsafeAccess3 = new UnsafeAccess<>(AutoSwap.class);
   private static final ItemStack itemStack = new ItemStack(Items.CROSSBOW);
   private static final ItemStack itemStack2 = getItemStack();
   private static final ItemStack itemStack3 = new ItemStack(TrackedItem.NETHERITE_SCRAP.item);
   private static final ItemStack itemStack4 = itemStack3;
   private static final ItemStack itemStack5 = itemStack3;
   private static final ItemStack itemStack6 = new ItemStack(TrackedItem.DRIED_KELP.item);
   private final SliderSetting radius;
   private final BooleanSetting metanieZeliy;
   private final BooleanSetting brosokPerlov;
   private final BooleanSetting ispolzovanie;
   private final BooleanSetting poteryaTotemov;
   private final BooleanSetting kuldaunyProtivnikov;
   private final BooleanSetting taymeryTrapkiPlasta;
   private final SliderSetting scalePlashki;
   private final BooleanSetting sebyaTozhe;
   private final BooleanSetting ignorirovatDruzey;
   private final Map<Integer, DroppedItemEntry> map;
   private final Map<UUID, ItemCount> map2;
   private final Map<UUID, ItemStack> map3;
   private final Map<UUID, ItemStack> map4;
   private final Set<UUID> set;
   private final Set<UUID> set2;
   private final Set<Integer> set3;
   private final Set<Integer> set4;
   private final Map<UUID, ItemUseRecord> map5;
   private final List<ItemTrackEntry> list;
   private final Map<ItemTrackEntry, Float> map6;
   private long time;
   private final Set<StatusEffect> set5;
   private Vec3d vec3d;
   private long time2;
   private Vec3d vec3d2;
   private long time3;

   public ItemTracker() {
      super("ItemTracker", Category.PLAYER);
      SliderSetting slidersetting = new SliderSetting("", "", 50.0, 10.0, 100.0, 1.0);
      slidersetting.setName("Радиус");
      slidersetting.setDescription("Дистанция отслеживания");
      this.radius = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Метание зелий");
      booleansetting.setDescription("Сообщать кому прилетело брошенное зелье");
      this.metanieZeliy = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Бросок перлов");
      booleansetting1.setDescription("Сообщать когда кто-то бросил эндер-перл");
      this.brosokPerlov = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", true);
      booleansetting2.setName("Использование");
      booleansetting2.setDescription("Сообщать когда игрок начал использовать предмет");
      this.ispolzovanie = booleansetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", true);
      booleansetting3.setName("Потеря тотемов");
      booleansetting3.setDescription("Сообщать когда y кого-то сработал тотем");
      this.poteryaTotemov = booleansetting3;
      BooleanSetting booleansetting4 = new BooleanSetting("", "", true);
      booleansetting4.setName("Кулдауны противников");
      booleansetting4.setDescription("Отслеживать откаты гэплов, чарок и трапок y последней цели");
      this.kuldaunyProtivnikov = booleansetting4;
      BooleanSetting booleansetting5 = new BooleanSetting("", "", true);
      booleansetting5.setName("Таймеры трапки/пласта");
      booleansetting5.setDescription("Показывать в мире таймер срабатывания трапки и пласта");
      this.taymeryTrapkiPlasta = booleansetting5;
      slidersetting = new SliderSetting("", "", 0.3, 0.1, 1.0, 0.1);
      slidersetting.setName("Масштаб плашки");
      slidersetting.setDescription("Масштаб плашки таймера трапки/пласта");
      this.scalePlashki = slidersetting;
      BooleanSetting booleansetting6 = new BooleanSetting("", "", false);
      booleansetting6.setName("Себя тоже");
      booleansetting6.setDescription("Логгировать собственные действия");
      this.sebyaTozhe = booleansetting6;
      BooleanSetting booleansetting7 = new BooleanSetting("", "", true);
      booleansetting7.setName("Игнорировать друзей");
      booleansetting7.setDescription("Не логгировать действия друзей");
      this.ignorirovatDruzey = booleansetting7;
      this.map = new HashMap<>();
      this.map2 = new HashMap<>();
      this.map3 = new HashMap<>();
      this.map4 = new HashMap<>();
      this.set = new HashSet<>();
      this.set2 = new HashSet<>();
      this.set3 = new HashSet<>();
      this.set4 = new HashSet<>();
      this.map5 = new HashMap<>();
      this.list = new ArrayList<>();
      this.map6 = new HashMap<>();
      this.time = System.currentTimeMillis();
      this.set5 = ConcurrentHashMap.newKeySet();
      this.vec3d = null;
      this.time2 = 0L;
      this.vec3d2 = null;
      this.time3 = 0L;
      this.scalePlashki.setVisibleWhen(this.taymeryTrapkiPlasta::isFlag3);
      this.addSettings(
         new Setting[]{
            this.radius,
            this.metanieZeliy,
            this.brosokPerlov,
            this.ispolzovanie,
            this.poteryaTotemov,
            this.kuldaunyProtivnikov,
            this.taymeryTrapkiPlasta,
            this.scalePlashki,
            this.sebyaTozhe,
            this.ignorirovatDruzey
         }
      );
   }

   private void addDouble(double value) {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      this.set2.clear();

      for (PlayerEntity playerentity : this.world().getPlayers()) {
         if (!(clientplayerentity.squaredDistanceTo(playerentity) > value)) {
            UUID uuid = playerentity.getUuid();
            this.set2.add(uuid);
            boolean flag = isPlayerEntity(playerentity);
            boolean flag1 = this.set.contains(uuid);
            if (flag && !flag1) {
               this.set.add(uuid);
            } else if (!flag && flag1) {
               this.set.remove(uuid);
               if (isPlayerEntity2(playerentity) && this.isLivingEntity(playerentity)) {
                  NotificationManager notificationmanager = NotificationManager.getInstance();
                  Icon icon1 = Icon.getIconByItemStack(itemStack);
                  String s1 = getStringByLivingEntity(playerentity) + " выстрелил из арбалета";
                  String s = "";
                  Icon icon = icon1;
                  notificationmanager.onStringIconString(s, icon, s1);
               }
            }
         }
      }

      this.set.retainAll(this.set2);
   }

   private boolean isLivingEntity(LivingEntity livingEntity) {
      return !this.sebyaTozhe.isFlag3() && livingEntity == this.clientPlayer() ? false : !this.ignorirovatDruzey.isFlag3() || !this.isFriend(livingEntity);
   }

   private static void onItemStack(ItemStack itemStack) {
      Item item = itemStack.getItem();
      if (item == Items.ENCHANTED_GOLDEN_APPLE) {
         CooldownTracker.onCooldownItem(CooldownItem.ENCHANTED_GOLDEN_APPLE);
      } else if (item == Items.GOLDEN_APPLE) {
         CooldownTracker.onCooldownItem(CooldownItem.GOLDEN_APPLE);
      } else if (item == Items.CHORUS_FRUIT) {
         CooldownTracker.onCooldownItem(CooldownItem.CHORUS_FRUIT);
      } else if (item == Items.POTION) {
         PotionContentsComponent potioncontentscomponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
         if (potioncontentscomponent != null) {
            for (StatusEffectInstance statuseffectinstance : potioncontentscomponent.getEffects()) {
               StatusEffect statuseffect = (StatusEffect)statuseffectinstance.getEffectType().value();
               if (statuseffect == StatusEffects.INSTANT_HEALTH.value()) {
                  CooldownTracker.onCooldownItem(CooldownItem.HEALING_POTION);
               } else if (statuseffect == StatusEffects.NAUSEA.value()) {
                  CooldownTracker.onCooldownItem(CooldownItem.NAUSEA);
               }
            }
         }
      }
   }

   private static ItemStack getItemStackByLivingEntity(LivingEntity livingEntity) {
      return getItemStackByLivingEntityPredicate(livingEntity, ItemTracker::isItemStack4);
   }

   private static boolean isTrackedItem(TrackedItem trackedItem) {
      return trackedItem.text2 != null || trackedItem.text3 != null || trackedItem.value != -1 || trackedItem.value2 != -1;
   }

   private static boolean isPlayerEntity(PlayerEntity playerEntity) {
      return !getItemStackByLivingEntityPredicate(playerEntity, ItemTracker::isItemStack3).isEmpty();
   }

   private void addDouble2(double value) {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      HashSet hashset = new HashSet();

      for (Entity entity : this.clientWorld().getEntities()) {
         if (entity instanceof EnderPearlEntity enderpearlentity) {
            int i = enderpearlentity.getId();
            hashset.add(i);
            if (!this.set4.contains(i)) {
               this.set4.add(i);
               if (!(clientplayerentity.squaredDistanceTo(enderpearlentity) > value)
                  && enderpearlentity.getOwner() instanceof LivingEntity livingentity
                  && this.isLivingEntity(livingentity)) {
                  NotificationManager notificationmanager = NotificationManager.getInstance();
                  Icon icon1 = Icon.getIconByItemStack(new ItemStack(Items.ENDER_PEARL));
                  String s1 = getStringByLivingEntity(livingentity) + " бросил перл";
                  String s = "";
                  Icon icon = icon1;
                  notificationmanager.onStringIconString(s, icon, s1);
               }
            }
         }
      }

      this.set4.retainAll(hashset);
   }

   private static String getStringByItemStack(ItemStack itemStack) {
      TrackedItem trackeditem = SphereItems.getTrackedItemByItemStack(itemStack);
      return trackeditem != null ? trackeditem.text : "тотем";
   }

   private static boolean check3() {
      ItemRadius itemradius = (ItemRadius)unsafeAccess.getModule();
      return itemradius != null && itemradius.check3();
   }

   private static boolean isItemStack(ItemStack itemStack) {
      return itemStack.getItem() == Items.TOTEM_OF_UNDYING;
   }

   @Override
   public void onAnimatedFloat(AnimatedFloat animatedFloat) {
      if (!this.notInGame()) {
         double d0 = this.radius.getValue() * this.radius.getValue();
         if (this.metanieZeliy.isFlag3()) {
            this.onDouble(d0);
            this.addDouble4(d0);
         }

         if (this.brosokPerlov.isFlag3()) {
            this.addDouble2(d0);
         }

         if (this.ispolzovanie.isFlag3() || this.kuldaunyProtivnikov.isFlag3()) {
            this.addDouble3(d0);
         }

         if (this.ispolzovanie.isFlag3()) {
            this.addDouble(d0);
         }

         if (this.poteryaTotemov.isFlag3()) {
            this.onDouble2(d0);
         }
      }
   }

   private static boolean isItemStack2(ItemStack itemStack) {
      return itemStack.getItem() instanceof CrossbowItem;
   }

   private void onDouble(double value) {
      Map map1 = this.map4;
      Function<LivingEntity, ItemStack> function = ItemTracker::getItemStackByLivingEntity;
      Map mapx = map1;
      this.onFunctionDoubleMap(function, value, mapx);
   }

   private static boolean isPlayerEntity2(PlayerEntity playerEntity) {
      return !getItemStackByLivingEntityPredicate(playerEntity, ItemTracker::isItemStack2).isEmpty();
   }

   private static boolean isLivingEntity2(LivingEntity livingEntity) {
      return livingEntity instanceof PlayerEntity;
   }

   @Override
   public void onDisable() {
      this.update11();
   }

   private void onDouble2(double value) {
      Map map1 = this.map3;
      Function<LivingEntity, ItemStack> function = ItemTracker::getItemStackByLivingEntity2;
      Map mapx = map1;
      this.onFunctionDoubleMap(function, value, mapx);
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!StreamBypass.check6()) {
         if (this.taymeryTrapkiPlasta.isFlag3() && !this.list.isEmpty()) {
            MatrixStack matrixstack1 = worldRenderContext.getMatrixStack();
            Vec3d vec3dx = worldRenderContext.getCamera().getPos();
            Camera camera = worldRenderContext.getCamera();
            MatrixStack matrixstack = matrixstack1;
            this.render(matrixstack, vec3dx, camera);
         }
      }
   }

   private static boolean isItemStack3(ItemStack itemStack) {
      return itemStack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemStack);
   }

   private void addDouble3(double value3) {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      this.set2.clear();
      boolean flag = this.ispolzovanie.isFlag3();
      UUID uuid = this.kuldaunyProtivnikov.isFlag3() ? CooldownTracker.getUUID() : null;

      for (PlayerEntity playerentity : this.world().getPlayers()) {
         UUID uuid1 = playerentity.getUuid();
         boolean flag1 = uuid1.equals(uuid);
         if (flag1 || !(clientplayerentity.squaredDistanceTo(playerentity) > value3)) {
            this.set2.add(uuid1);
            boolean flag2 = playerentity.isUsingItem();
            ItemStack itemstack = flag2 ? playerentity.getActiveItem() : ItemStack.EMPTY;
            ItemCount itemcount = this.map2.get(uuid1);
            if (flag2 && !itemstack.isEmpty()) {
               int i = playerentity.getItemUseTime();
               if (itemcount != null && itemcount.item == itemstack.getItem()) {
                  itemcount.value2 = i;
               } else {
                  this.map2.put(uuid1, new ItemCount(itemstack.copy(), itemstack.getMaxUseTime(playerentity), i));
                  if (flag1) {
                     onItemStack2(itemstack);
                  }
               }
            } else if (itemcount != null) {
               if (itemcount.value2 + 2 >= itemcount.value) {
                  if (flag && this.isLivingEntity(playerentity)) {
                     ItemStack itemstack1 = itemcount.itemStack;
                     this.onItemStackPlayerEntity(itemstack1, playerentity);
                  }

                  if (flag1) {
                     onItemStack(itemcount.itemStack);
                  }
               }

               this.map2.remove(uuid1);
            }
         }
      }

      this.map2.keySet().retainAll(this.set2);
   }

   private static boolean isLongItemTrackEntry(long time3, ItemTrackEntry itemTrackEntry) {
      return time3 - itemTrackEntry.time > itemTrackEntry.time2;
   }

   private void onItemStackPlayerEntity(ItemStack itemStack, PlayerEntity playerEntity) {
      if (!itemStack.isEmpty()) {
         String s = itemStack.getName().getString();
         TrackedItem trackeditem = getTrackedItemByItemStack(itemStack);
         String s1 = getStringByItemStackTrackedItem(itemStack, trackeditem);
         if (s1 != null) {
            String s2 = trackeditem != null ? trackeditem.text : getStringByString(s);
            ItemStack itemstack = itemStack.getItem() instanceof CrossbowItem ? itemStack2 : itemStack;
            NotificationManager notificationmanager = NotificationManager.getInstance();
            Icon icon1 = Icon.getIconByItemStack(itemstack);
            String s4 = getStringByLivingEntity(playerEntity) + " " + s1 + " " + s2;
            String s3 = "";
            Icon icon = icon1;
            notificationmanager.onStringIconString(s3, icon, s4);
         }
      }
   }

   private static String getStringByItemStackTrackedItem(ItemStack itemStack, TrackedItem trackedItem) {
      Item item = itemStack.getItem();
      if (item instanceof CrossbowItem) {
         return "натянул";
      } else if (item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION) {
         return "выпил";
      } else if (item == Items.MILK_BUCKET) {
         return "выпил";
      } else if (item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE) {
         return "съел";
      } else if (item == Items.CHORUS_FRUIT) {
         return "съел";
      } else if (item == Items.GOLDEN_CARROT) {
         return "съел";
      } else {
         return trackedItem != null && trackedItem.item != Items.CROSSBOW ? "использовал" : null;
      }
   }

   private static String getStringByLivingEntity(LivingEntity livingEntity) {
      String s = livingEntity.getName().getString();
      Protect protect = (Protect)unsafeAccess2.getModule2();
      return protect != null ? protect.getStringByString2(s) : s;
   }

   private void onFunctionDoubleMap(Function function2, double value, Map map) {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      this.set2.clear();

      for (Entity entity : this.clientWorld().getEntities()) {
         if (entity instanceof LivingEntity livingentity && !(clientplayerentity.squaredDistanceTo(entity) > value)) {
            UUID uuid = livingentity.getUuid();
            this.set2.add(uuid);
            ItemStack itemstack = (ItemStack)function2.apply(livingentity);
            if (!itemstack.isEmpty()) {
               map.put(uuid, itemstack.copy());
            }
         }
      }

      map.keySet().retainAll(this.set2);
   }

   private static ItemStack getItemStackByLivingEntityPredicate(LivingEntity livingEntity, Predicate<ItemStack> predicate) {
      ItemStack itemstack = livingEntity.getMainHandStack();
      if (predicate.test(itemstack)) {
         return itemstack;
      } else {
         ItemStack itemstack1 = livingEntity.getOffHandStack();
         return predicate.test(itemstack1) ? itemstack1 : ItemStack.EMPTY;
      }
   }

   private static boolean isItemStack4(ItemStack itemStack) {
      Item item = itemStack.getItem();
      return item == Items.SPLASH_POTION || item == Items.LINGERING_POTION;
   }

   public static TrackedItem getTrackedItemByItemStack(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         TrackedItem trackeditem = null;

         for (TrackedItem trackeditem1 : TrackedItem.values()) {
            if (isTrackedItem(trackeditem1)) {
               if (AutoSwap.isTrackedItemItemStack4(trackeditem1, itemStack)) {
                  return trackeditem1;
               }
            } else if (trackeditem == null && trackeditem1.item == itemStack.getItem()) {
               trackeditem = trackeditem1;
            }
         }

         return trackeditem;
      } else {
         return null;
      }
   }

   public static String getStringByString(String text) {
      return text == null ? "" : text.replaceAll("(?i)§[0-9a-fk-or]", "").replaceAll("[\\[\\]]", "").replaceAll("\\s+", " ").trim();
   }

   private static ItemStack getItemStackByLivingEntity2(LivingEntity livingEntity) {
      return getItemStackByLivingEntityPredicate(livingEntity, ItemTracker::isItemStack);
   }

   private void removeLivingEntity(LivingEntity livingEntity) {
      ItemStack itemstack = this.map3.remove(livingEntity.getUuid());
      if (itemstack == null) {
         itemstack = new ItemStack(Items.TOTEM_OF_UNDYING);
      }

      NotificationManager notificationmanager = NotificationManager.getInstance();
      Icon icon1 = Icon.getIconByItemStack(itemstack);
      String s1 = getStringByLivingEntity(livingEntity) + " потерял " + getStringByItemStack(itemstack);
      String s = "";
      Icon icon = icon1;
      notificationmanager.onStringIconString(s, icon, s1);
   }

   private static ItemStack getItemStack() {
      ItemStack itemstack = new ItemStack(Items.CROSSBOW);
      itemstack.set(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.of(new ItemStack(Items.ARROW)));
      return itemstack;
   }

   private static boolean check4() {
      AutoSwap autoswap = (AutoSwap)unsafeAccess3.getModule();
      return autoswap != null && autoswap.check6();
   }

   private boolean isLivingEntity3(LivingEntity livingEntity) {
      UUID uuid = CooldownTracker.getUUID();
      return uuid != null && uuid.equals(livingEntity.getUuid());
   }

   private boolean isTrackedItemUUID(TrackedItem trackedItem, UUID uUID) {
      long i = System.currentTimeMillis();
      ItemUseRecord itemuserecord = this.map5.get(uUID);
      if (itemuserecord != null && itemuserecord.entry() == trackedItem && i - itemuserecord.time() < 1000L) {
         return false;
      } else {
         this.map5.put(uUID, new ItemUseRecord(trackedItem, i));
         return true;
      }
   }

   private void addEntityStatusEffectS2CPacket(EntityStatusEffectS2CPacket entityStatusEffectS2CPacket) {
      if (!this.notInGame() && CooldownTracker.getUUID() != null) {
         if (entityStatusEffectS2CPacket.getEntityId() == this.clientPlayer().getId()) {
            StatusEffect statuseffect = (StatusEffect)entityStatusEffectS2CPacket.getEffectId().value();
            int i = entityStatusEffectS2CPacket.getAmplifier();
            boolean flag = statuseffect == StatusEffects.WITHER.value() && i == 1
               || statuseffect == StatusEffects.SLOWNESS.value() && i == 4
               || statuseffect == StatusEffects.MINING_FATIGUE.value() && i == 4;
            if (flag) {
               this.set5.add(statuseffect);
               if (this.set5.size() >= 3) {
                  CooldownTracker.onCooldownItem(CooldownItem.NAUSEA);
                  this.set5.clear();
               }
            }
         }
      }
   }

   private void addPlaySoundS2CPacket(PlaySoundS2CPacket playSoundS2CPacket) {
      String s = ((SoundEvent)playSoundS2CPacket.getSound().value()).id().getPath();
      float f = playSoundS2CPacket.getPitch();
      float f1 = playSoundS2CPacket.getVolume();
      Vec3d vec3dx = new Vec3d(playSoundS2CPacket.getX(), playSoundS2CPacket.getY(), playSoundS2CPacket.getZ());
      long i = System.currentTimeMillis();
      if (s.equals("block.piston.extend")) {
         float f3 = 0.7F;
         float f2 = 0.5F;
         if (isFloatFloatFloatFloat(f3, f, f1, f2)) {
            long k = check3() ? 30000L : 15000L;
            String s4 = TrackedItem.NETHERITE_SCRAP.text;
            if (!this.isVec3dLongString(vec3dx, i, s4)) {
               this.list.add(new ItemTrackEntry(vec3dx, i, k, itemStack3, s4));
            }

            return;
         }
      }

      if (s.equals("block.anvil.place")) {
         float f5 = 0.7F;
         float f4 = 1.1F;
         if (isFloatFloatFloatFloat(f5, f, f1, f4)) {
            long j = this.isVec3d(vec3dx) ? 20000L : 60000L;
            String s1 = TrackedItem.DRIED_KELP.text;
            if (!this.isVec3dLongString(vec3dx, i, s1)) {
               this.list.add(new ItemTrackEntry(vec3dx, i, j, itemStack6, s1));
            }

            return;
         }
      }

      if (s.equals("entity.evoker_fangs.attack")) {
         this.vec3d = vec3dx;
         this.time2 = i;
      } else if (s.equals("ui.toast.challenge_complete") && this.vec3d != null && i - this.time2 <= 500L && this.vec3d.squaredDistanceTo(vec3dx) < 0.5) {
         Vec3d vec3d1 = this.vec3d;
         String s2 = "Драконка";
         if (!this.isVec3dLongString(vec3d1, i, s2)) {
            this.list.add(new ItemTrackEntry(this.vec3d, i, 30000L, itemStack4, "Драконка"));
         }

         this.vec3d = null;
         this.time2 = 0L;
      } else if (s.equals("entity.wither.break_block")) {
         this.vec3d2 = vec3dx;
         this.time3 = i;
      } else {
         if (s.equals("entity.ender_dragon.hurt") && this.vec3d2 != null && i - this.time3 <= 500L && this.vec3d2.squaredDistanceTo(vec3dx) < 0.5) {
            Vec3d vec3d2x = this.vec3d2;
            String s3 = "Неизбежка";
            if (!this.isVec3dLongString(vec3d2x, i, s3)) {
               this.list.add(new ItemTrackEntry(this.vec3d2, i, 15000L, itemStack5, "Неизбежка"));
            }

            this.vec3d2 = null;
            this.time3 = 0L;
         }
      }
   }

   private boolean isVec3dLongString(Vec3d vec3d2, long time3, String text2) {
      for (ItemTrackEntry itemtrackentry : this.list) {
         if (itemtrackentry.text.equals(text2)) {
            long i = itemtrackentry.time2 - (time3 - itemtrackentry.time);
            if (i <= 3000L) {
               double d0 = itemtrackentry.vec3d.squaredDistanceTo(vec3d2);
               if (!(d0 < 0.0625) && d0 <= 25.0) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private void update11() {
      this.map.clear();
      this.map2.clear();
      this.map3.clear();
      this.map4.clear();
      this.set.clear();
      this.map5.clear();
      this.list.clear();
      this.map6.clear();
      this.set4.clear();
      this.set5.clear();
      CooldownTracker.update();
      this.vec3d = null;
      this.time2 = 0L;
      this.vec3d2 = null;
      this.time3 = 0L;
   }

   @Override
   public void onPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      if (this.kuldaunyProtivnikov.isFlag3()) {
         if (entity2 instanceof PlayerEntity playerentity && playerentity != playerEntity && playerentity.isAlive()) {
            CooldownTracker.removePlayerEntity(playerentity);
         }
      }
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof EntityStatusS2CPacket entitystatuss2cpacket && entitystatuss2cpacket.getStatus() == 35) {
            this.onEntityStatusS2CPacket(entitystatuss2cpacket);
         } else if (packetEvent.getPacket() instanceof PlaySoundS2CPacket playsounds2cpacket && this.taymeryTrapkiPlasta.isFlag3()) {
            this.addPlaySoundS2CPacket(playsounds2cpacket);
         } else if (packetEvent.getPacket() instanceof GameJoinS2CPacket) {
            this.set5.clear();
            CooldownTracker.update();
         } else if (packetEvent.getPacket() instanceof EntityAnimationS2CPacket entityanimations2cpacket) {
            this.onEntityAnimationS2CPacket(entityanimations2cpacket);
         } else if (this.kuldaunyProtivnikov.isFlag3()) {
            if (packetEvent.getPacket() instanceof EntityStatusEffectS2CPacket entitystatuseffects2cpacket) {
               this.addEntityStatusEffectS2CPacket(entitystatuseffects2cpacket);
            } else {
               if (packetEvent.getPacket() instanceof RemoveEntityStatusEffectS2CPacket removeentitystatuseffects2cpacket) {
                  this.set5.remove(removeentitystatuseffects2cpacket.effect().value());
               }
            }
         }
      }
   }

   private void onEntityStatusS2CPacket(EntityStatusS2CPacket entityStatusS2CPacket) {
      if (!this.notInGame()) {
         if (entityStatusS2CPacket.getEntity(this.world()) instanceof LivingEntity livingentity) {
            if (this.kuldaunyProtivnikov.isFlag3() && this.isLivingEntity3(livingentity)) {
               CooldownTracker.onCooldownItem(CooldownItem.TOTEM);
            }

            if (this.poteryaTotemov.isFlag3() && this.isLivingEntity(livingentity)) {
               this.removeLivingEntity(livingentity);
            }
         }
      }
   }

   private void onEntityAnimationS2CPacket(EntityAnimationS2CPacket entityAnimationS2CPacket) {
      int i = entityAnimationS2CPacket.getAnimationId();
      if (i == 0 || i == 3) {
         if (!this.notInGame()) {
            if (this.world().getEntityById(entityAnimationS2CPacket.getEntityId()) instanceof PlayerEntity playerentity) {
               ItemStack itemstack = i == 0 ? playerentity.getMainHandStack() : playerentity.getOffHandStack();
               TrackedItem trackeditem = getTrackedItemByItemStack(itemstack);
               if (trackeditem != null && isTrackedItem2(trackeditem)) {
                  if (this.kuldaunyProtivnikov.isFlag3() && this.isLivingEntity3(playerentity)) {
                     onItemStack2(itemstack);
                  }

                  if (this.ispolzovanie.isFlag3() && this.isLivingEntity(playerentity)) {
                     double d0 = this.radius.getValue() * this.radius.getValue();
                     if (!(this.clientPlayer().squaredDistanceTo(playerentity) > d0)) {
                        UUID uuid = playerentity.getUuid();
                        if (this.isTrackedItemUUID(trackeditem, uuid)) {
                           NotificationManager notificationmanager = NotificationManager.getInstance();
                           Icon icon1 = Icon.getIconByItemStack(itemstack);
                           String s1 = getStringByLivingEntity(playerentity) + " использовал " + trackeditem.text;
                           String s = "";
                           Icon icon = icon1;
                           notificationmanager.onStringIconString(s, icon, s1);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isTrackedItem2(TrackedItem trackedItem) {
      return switch (ServerFlagSwitchMap.intArray[trackedItem.serverFlag.ordinal()]) {
         case 1 -> false;
         case 2 -> true;
         case 3 -> !check4();
         case 4 -> check4();
         default -> throw new MatchException(null, null);
      };
   }

   private static String getStringByDouble(double value) {
      return TimeFormat.getStringByDouble(value);
   }

   private void addDouble4(double value) {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      this.set3.clear();

      for (Entity entity : this.clientWorld().getEntities()) {
         if (entity instanceof PotionEntity potionentity) {
            int i = entity.getId();
            DroppedItemEntry droppeditementry = this.map.get(i);
            if (droppeditementry == null) {
               if (clientplayerentity.squaredDistanceTo(entity) > value) {
                  continue;
               }

               this.map.put(i, new DroppedItemEntry(this.getItemStackByPotionEntity(potionentity).copy(), entity.getPos()));
            } else {
               droppeditementry.vec3d = entity.getPos();
            }

            this.set3.add(i);
         }
      }

      Iterator iterator = this.map.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry entry = (Entry)iterator.next();
         if (!this.set3.contains(entry.getKey())) {
            iterator.remove();
            this.onDroppedItemEntry((DroppedItemEntry)entry.getValue());
         }
      }
   }

   private ItemStack getItemStackByPotionEntity(PotionEntity potionEntity) {
      Entity entity = potionEntity.getOwner();
      if (entity != null) {
         ItemStack itemstack = this.map4.get(entity.getUuid());
         if (itemstack != null && !itemstack.isEmpty()) {
            return itemstack;
         }
      }

      return potionEntity.getStack();
   }

   private void onDroppedItemEntry(DroppedItemEntry droppedItemEntry) {
      Vec3d vec3dx = droppedItemEntry.vec3d;
      Box box = new Box(vec3dx.x - 4.0, vec3dx.y - 2.0, vec3dx.z - 4.0, vec3dx.x + 4.0, vec3dx.y + 2.0, vec3dx.z + 4.0);
      String s = getStringByItemStack2(droppedItemEntry.itemStack);

      for (LivingEntity livingentity : this.world().getEntitiesByClass(LivingEntity.class, box, ItemTracker::isLivingEntity2)) {
         double d0 = livingentity.getX() - vec3dx.x;
         double d1 = livingentity.getZ() - vec3dx.z;
         if (!(Math.sqrt(d0 * d0 + d1 * d1) > 4.0) && this.isLivingEntity(livingentity)) {
            NotificationManager notificationmanager = NotificationManager.getInstance();
            Icon icon1 = Icon.getIconByItemStack(droppedItemEntry.itemStack);
            String s2 = getStringByLivingEntity(livingentity) + " получил " + s;
            String s1 = "";
            Icon icon = icon1;
            notificationmanager.onStringIconString(s1, icon, s2);
         }
      }
   }

   private static String getStringByItemStack2(ItemStack itemStack) {
      PotionContentsComponent potioncontentscomponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
      if (potioncontentscomponent != null && potioncontentscomponent.customColor().isPresent()) {
         TrackedItem trackeditem = TrackedItem.getTrackedItemByInt2((Integer)potioncontentscomponent.customColor().get());
         if (trackeditem != null) {
            return trackeditem.text;
         }
      }

      String s = getStringByString(itemStack.getName().getString());
      TrackedItem trackeditem1 = getTrackedItemByItemStack(itemStack);
      if (trackeditem1 != null) {
         return trackeditem1.text;
      } else {
         return s.isEmpty() ? "зелье" : s;
      }
   }

   private static void onItemStack2(ItemStack itemStack) {
      Item item = itemStack.getItem();
      if (item == TrackedItem.ENDER_EYE.item) {
         CooldownTracker.onCooldownItem(CooldownItem.NAUSEA);
      } else if (item == TrackedItem.DRIED_KELP.item) {
         CooldownTracker.onCooldownItem(CooldownItem.DRIED_KELP);
      } else if (item == TrackedItem.NETHERITE_SCRAP.item) {
         CooldownTracker.onCooldownItem(CooldownItem.NETHERITE_SCRAP);
      }
   }

   private boolean isVec3d(Vec3d vec3d) {
      if (this.notInGame()) {
         return false;
      } else {
         PlayerEntity playerentity = null;
         double d0 = 64.0;

         for (PlayerEntity playerentity1 : this.world().getPlayers()) {
            if (isPlayerEntity3(playerentity1)) {
               double d1 = playerentity1.getPos().squaredDistanceTo(vec3d);
               if (d1 < d0) {
                  d0 = d1;
                  playerentity = playerentity1;
               }
            }
         }

         return playerentity == null ? false : Math.abs(playerentity.getPitch()) < 45.0F;
      }
   }

   private static boolean isPlayerEntity3(PlayerEntity playerEntity) {
      return playerEntity.getMainHandStack().getItem() == TrackedItem.DRIED_KELP.item || playerEntity.getOffHandStack().getItem() == TrackedItem.DRIED_KELP.item;
   }

   private static boolean isFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      return Math.abs(value2 - value4) < 0.01F && Math.abs(value3 - value) < 0.01F;
   }

   private void render(MatrixStack matrixStack, Vec3d vec3d2, Camera camera) {
      long i = System.currentTimeMillis();
      this.list.removeIf(p0 -> ItemTracker.isLongItemTrackEntry(i, p0));
      this.map6.keySet().retainAll(this.list);
      if (!this.list.isEmpty()) {
         float f = Math.min((float)(i - this.time) / 1000.0F, 0.1F);
         this.time = i;
         RotationBuffer.setMinecraftClient2(this.client());
         ShapeShader.update2();

         try {
            for (ItemTrackEntry itemtrackentry : this.list) {
               long j = itemtrackentry.time2 - (i - itemtrackentry.time);
               if (j > 0L) {
                  String s = itemtrackentry.text + " " + getStringByDouble(j / 1000.0);
                  Vec3d vec3dx = itemtrackentry.vec3d.subtract(vec3d2);
                  double d0 = itemtrackentry.vec3d.distanceTo(vec3d2);
                  this.onCameraFloatItemTrackEntryMatrixStackVec3dDoubleString(camera, f, itemtrackentry, matrixStack, vec3dx, d0, s);
               }
            }
         } finally {
            ShapeShader.update();
            RotationBuffer.setMinecraftClient(this.client());
         }
      }
   }

   private void onCameraFloatItemTrackEntryMatrixStackVec3dDoubleString(
      Camera camera, float value, ItemTrackEntry itemTrackEntry, MatrixStack matrixStack, Vec3d vec3d, double value2, String text
   ) {
      matrixStack.push();

      try {
         matrixStack.translate(vec3d.x, vec3d.y + 1.0, vec3d.z);
         RotationBuffer.render(matrixStack);
         float f = DistanceScale.getFloatByDoubleFloat(value2, this.scalePlashki.getValueAsFloat());
         float f1 = this.map6.getOrDefault(itemTrackEntry, f);
         float f2 = f1 + (f - f1) * Math.min(1.0F, value * 10.0F);
         this.map6.put(itemTrackEntry, f2);
         matrixStack.scale(-f2, -f2, -f2);
         float f13 = 14.0F;
         IconAtlas iconatlas = icon;
         float f3 = TextShader.getFloatByFloatIconAtlasString(f13, iconatlas, text);
         float f4 = 22.0F + f3;
         float f5 = f4 + 20.0F;
         float f6 = Math.max(14.0F, 16.0F) + 10.0F;
         float f7 = -f5 / 2.0F;
         float f8 = -f6 / 2.0F;
         Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
         int j = Theme.background();
         float f15 = 1.0F;
         int i = j;
         float f14 = 8.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f14, f7, i, matrix4f, f6, f5, f15, f8);
         float f9 = f7 + 10.0F;
         float f10 = -8.0F;
         float f17 = 1.0F;
         float f16 = 16.0F;
         ItemStack itemstack = itemTrackEntry.itemStack;
         if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(f9, matrix4f, f17, f16, f10, itemstack)) {
            float f19 = 1.0F;
            float f18 = 16.0F;
            ItemStack itemstack1 = itemTrackEntry.itemStack;
            ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f18, f19, f9, itemstack1, matrix4f, f10);
         }

         float f11 = f9 + 16.0F + 6.0F;
         float f12 = -8.5F;
         byte b0 = -1;
         float f20 = 14.0F;
         IconAtlas iconatlas1 = icon;
         TextShader.onStringFloatMatrix4fIntFloatIconAtlasFloat(text, f20, matrix4f, b0, f11, iconatlas1, f12);
      } finally {
         matrixStack.pop();
      }
   }

   @Override
   public void onEnable() {
      this.update11();
   }
}
