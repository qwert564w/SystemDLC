package client.module.render;

import client.data.IdName;
import client.data.TextSanitizer;
import client.enums.ServerMode;
import client.enums.VoiceIcon;
import client.module.Category;
import client.module.Module;
import client.module.client.StreamBypass;
import client.module.player.Protect;
import client.render.DepthState;
import client.render.ItemIconCache;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.HealthTracker;
import client.util.ItemPickupMath;
import client.util.NameTagCache;
import client.util.NameTagEntry;
import client.util.StringParts;
import client.util.TargetSelector;
import client.util.UnsafeAccess;
import client.util.VoicechatBridge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class NameTags extends Module {
   private SliderSetting size;
   private BooleanSetting backgroundPlashki;
   private SliderSetting opacityPlashki;
   private SliderSetting range;
   private MultilistSetting targets;
   private MultilistSetting chtoRenderit;
   private ListSetting modeHp;
   private ListSetting obhodHp;
   private BooleanSetting vanilnyeNeymtegi;
   private BooleanSetting hidePustyeSloty;
   private BooleanSetting kolichestvoPredmetov;
   private BooleanSetting ikonkaVoicechat;
   private BooleanSetting uproschatVdali;
   private SliderSetting rangeUproscheniya;
   private final NameTagCache nameTagCache;
   private final Map<UUID, IdName> map;
   private final ArrayList<NameTagEntry> list;
   private final ArrayList<NameTagEntry> list2;
   private static final Comparator<NameTagEntry> comparator = NameTags::getIntByNameTagEntryNameTagEntry;
   private static final UnsafeAccess<Protect> unsafeAccess = new UnsafeAccess<>(Protect.class);

   public NameTags() {
      super("NameTags", Category.RENDER);
      SliderSetting slidersetting = new SliderSetting("", "", 0.35, 0.01, 1.0, 0.05);
      slidersetting.setName("Размер");
      slidersetting.setDescription("Масштаб имени игрока");
      this.size = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Фон плашки");
      booleansetting.setDescription("Рисовать подложку за неймтегом. Выключено — только текст и иконки.");
      this.backgroundPlashki = booleansetting;
      slidersetting = new SliderSetting("", "", 100.0, 0.0, 100.0, 5.0, "%", 0);
      slidersetting.setName("Прозрачность плашки");
      slidersetting.setDescription("Непрозрачность фона за ником");
      this.opacityPlashki = slidersetting;
      slidersetting = new SliderSetting("", "", 64.0, 8.0, 256.0, 4.0, StringParts.join(new String[]{" ", "б", "л", "o", "к", "о", "в"}), 0);
      slidersetting.setName("Дистанция");
      slidersetting.setDescription("Максимальная дистанция рендера");
      this.range = slidersetting;
      MultilistSetting multilistsetting = new MultilistSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"Д", "p", "у", "з", "ь", "я"}),
            StringParts.join(new String[]{"В", "р", "a", "г", "и"}),
            StringParts.join(new String[]{"В", "р", "a", "г", "и", " ", "т", "o", "л", "ь", "к", "о", " ", "в", " ", "б", "р", "o", "н", "е"})
         ),
         Arrays.asList(StringParts.join(new String[]{"Д", "p", "у", "з", "ь", "я"}), StringParts.join(new String[]{"В", "р", "a", "г", "и"}))
      );
      multilistsetting.setName("Цели");
      multilistsetting.setDescription("Кого отображать");
      this.targets = multilistsetting;
      MultilistSetting multilistsetting1 = new MultilistSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"П", "р", "е", "ф", "и", "к", "с"}),
            StringParts.join(new String[]{"Б", "р", "o", "н", "я"}),
            StringParts.join(new String[]{"Л", "е", "в", "а", "я", " ", "р", "у", "к", "а"}),
            StringParts.join(new String[]{"П", "р", "а", "в", "а", "я", " ", "р", "у", "к", "а"}),
            StringParts.join(new String[]{"Ц", "в", "е", "т", "н", "о", "е", " ", "H", "P"})
         ),
         Arrays.asList(
            StringParts.join(new String[]{"П", "р", "е", "ф", "и", "к", "с"}),
            StringParts.join(new String[]{"Б", "р", "o", "н", "я"}),
            StringParts.join(new String[]{"Л", "е", "в", "а", "я", " ", "р", "у", "к", "а"}),
            StringParts.join(new String[]{"Ц", "в", "е", "т", "н", "о", "е", " ", "H", "P"})
         )
      );
      multilistsetting1.setName("Что рендерить");
      multilistsetting1.setDescription("Какие элементы отображать на неймтеге");
      this.chtoRenderit = multilistsetting1;
      ListSetting listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"О", "ч", "к", "и", " ", "з", "д", "о", "р", "о", "в", "ь", "я"}),
            StringParts.join(new String[]{"С", "е", "р", "д", "ц", "а"})
         ),
         Collections.singletonList(StringParts.join(new String[]{"О", "ч", "к", "и", " ", "з", "д", "о", "р", "о", "в", "ь", "я"})),
         false
      );
      listsetting.setName("Режим HP");
      listsetting.setDescription("Способ отображения здоровья");
      this.modeHp = listsetting;
      ListSetting listsetting1 = new ListSetting("", "", Arrays.asList(StringParts.join(new String[]{"В", "ы", "к", "л"}), "MB", "FT"), List.of("FT"), false);
      listsetting1.setName("Обход HP");
      listsetting1.setDescription("Режим получения реального HP");
      this.obhodHp = listsetting1;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Ванильные неймтеги");
      booleansetting1.setDescription("Не скрывать оригинальные неймтеги поверх кастомных");
      this.vanilnyeNeymtegi = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", false);
      booleansetting2.setName("Скрывать пустые слоты");
      booleansetting2.setDescription("Не рендерить крестики в пустых слотах экипировки");
      this.hidePustyeSloty = booleansetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", true);
      booleansetting3.setName("Количество предметов");
      booleansetting3.setDescription("Показывать число рядом c предметом как в инвентаре");
      this.kolichestvoPredmetov = booleansetting3;
      BooleanSetting booleansetting4 = new BooleanSetting("", "", true);
      booleansetting4.setName("Иконка Voicechat");
      booleansetting4.setDescription("Показывать иконку голосового чата (Simple Voice Chat) левее ника");
      this.ikonkaVoicechat = booleansetting4;
      BooleanSetting booleansetting5 = new BooleanSetting("", "", false);
      booleansetting5.setName("Упрощать вдали");
      booleansetting5.setDescription("На дальней дистанции сворачивать неймтег до ника и хп — экономит FPS в толпе");
      this.uproschatVdali = booleansetting5;
      slidersetting = new SliderSetting("", "", 30.0, 8.0, 128.0, 2.0, StringParts.join(new String[]{" ", "б", "л", "o", "к", "о", "в"}), 0);
      slidersetting.setName("Дистанция упрощения");
      slidersetting.setDescription("Дальше этой дистанции предметы и броня на неймтеге плавно скрываются");
      this.rangeUproscheniya = slidersetting;
      this.nameTagCache = new NameTagCache();
      this.map = new HashMap<>();
      this.list = new ArrayList<>();
      this.list2 = new ArrayList<>();
      this.rangeUproscheniya.setVisibleWhen(this.uproschatVdali::isFlag3);
      this.opacityPlashki.setVisibleWhen(this.backgroundPlashki::isFlag3);
      this.addSettings(
         new Setting[]{
            this.obhodHp,
            this.modeHp,
            this.size,
            this.backgroundPlashki,
            this.opacityPlashki,
            this.range,
            this.targets,
            this.chtoRenderit,
            this.vanilnyeNeymtegi,
            this.hidePustyeSloty,
            this.kolichestvoPredmetov,
            this.ikonkaVoicechat,
            this.uproschatVdali,
            this.rangeUproscheniya
         }
      );
   }

   @Override
   public void onDisable() {
      this.nameTagCache.update();
      HealthTracker.update();
      this.map.clear();

      for (NameTagEntry nametagentry : this.list) {
         nametagentry.update();
      }

      this.list2.clear();
      ItemIconCache.update();
   }

   private NameTagEntry getNameTagEntryByInt(int count) {
      if (count < this.list.size()) {
         return this.list.get(count);
      } else {
         NameTagEntry nametagentry = new NameTagEntry();
         this.list.add(nametagentry);
         return nametagentry;
      }
   }

   private static int getIntByNameTagEntryNameTagEntry(NameTagEntry nameTagEntry, NameTagEntry nameTagEntry2) {
      return Double.compare(nameTagEntry2.value3, nameTagEntry.value3);
   }

   private void render8(WorldRenderContext worldRenderContext) {
      Camera camera = worldRenderContext.getCamera();
      Vec3d vec3d = camera.getPos();
      float f = worldRenderContext.getRenderTickCounter().getTickDelta(true);
      PlayerEntity playerentity = this.player();
      Vec3d vec3d1 = playerentity.getPos();
      float f1 = (float)((Integer)this.client().options.getFov().getValue()).intValue();
      List listx = this.chtoRenderit.getList4();
      boolean flag = listx.contains("Префикс");
      boolean flag1 = listx.contains("Брoня");
      boolean flag2 = listx.contains("Левая рука");
      boolean flag3 = listx.contains("Правая рука");
      boolean flag4 = listx.contains("Цветное HP");
      Protect protect = (Protect)unsafeAccess.getModule2();
      boolean flag5 = protect != null;
      ServerMode servermode = HealthTracker.getServerModeByString(this.obhodHp.getString2());
      boolean flag6 = "Сердца".equals(this.modeHp.getString2());
      boolean flag7 = this.ikonkaVoicechat.isFlag3() && VoicechatBridge.isFlag();
      List list1 = this.targets.getList4();
      boolean flag8 = list1.contains("Дpузья");
      boolean flag9 = list1.contains("Врaги");
      boolean flag10 = list1.contains("Врaги тoлько в брoне");
      double d0 = this.range.getValue4();
      this.list2.clear();
      int i = 0;

      for (PlayerEntity playerentity1 : this.world().getPlayers()) {
         boolean flag14 = true;
         boolean flag13 = false;
         if (ItemPickupMath.isDoubleFloatVec3dBooleanCameraBooleanModulePlayerEntityPlayerEntity(
            d0, f1, vec3d1, flag14, camera, flag13, this, playerentity1, playerentity
         )) {
            boolean flag11 = this.isFriend(playerentity1);
            if (flag11 ? flag8 : flag9 && (!flag10 || TargetSelector.isLivingEntity(playerentity1))) {
               double d1 = playerentity.distanceTo(playerentity1);
               Vec3d vec3d2 = DepthState.getVec3dByFloatEntityVec3d(f, playerentity1, vec3d);
               UUID uuid = playerentity1.getUuid();
               String s = playerentity1.getName().getString();
               String s1 = flag5 ? protect.getStringByString2(s) : s;
               String s2 = "";
               if (flag) {
                  String s3 = playerentity1.getDisplayName().getString();
                  IdName idname = this.map.get(uuid);
                  int j = s3.hashCode();
                  if (idname == null) {
                     idname = new IdName();
                     idname.value = j;
                     idname.text = TextSanitizer.getStringByStringString(s3, s);
                     this.map.put(uuid, idname);
                  } else if (idname.value != j) {
                     idname.value = j;
                     idname.text = TextSanitizer.getStringByStringString(s3, s);
                  }

                  s2 = idname.text;
               }

               float f5 = HealthTracker.getFloatByServerModePlayerEntity(servermode, playerentity1);
               float f6 = HealthTracker.getFloatByPlayerEntityServerMode(playerentity1, servermode);
               String s4 = flag6 ? HealthTracker.getStringByFloatFloat(f5, f6) : HealthTracker.getStringByFloat2(f5);
               NameTagEntry nametagentry = this.getNameTagEntryByInt(i++);
               boolean flag12 = false;
               if (flag1) {
                  for (int k1 = 0; k1 < 4; k1++) {
                     ItemStack itemstack = playerentity1.getInventory().getStack(36 + k1);
                     nametagentry.itemStackArray[k1] = itemstack;
                     if (!itemstack.isEmpty()) {
                        flag12 = true;
                     }
                  }
               } else {
                  for (int k = 0; k < 4; k++) {
                     nametagentry.itemStackArray[k] = ItemStack.EMPTY;
                  }
               }

               VoiceIcon voiceicon = flag7 ? VoicechatBridge.getVoiceIconByUUID(uuid) : VoiceIcon.NONE;
               ItemStack itemstack2 = playerentity1.getMainHandStack();
               ItemStack itemstack1 = playerentity1.getOffHandStack();
               nametagentry.onVec3dFloatBooleanItemStackPlayerEntityItemStackStringDoubleFloatStringVoiceIconStringBoolean(
                  vec3d2, f6, flag11, itemstack2, playerentity1, itemstack1, s2, d1, f5, s1, voiceicon, s4, flag12
               );
               this.list2.add(nametagentry);
            }
         }
      }

      if (this.list2.isEmpty()) {
         if (!this.map.isEmpty()) {
            this.map.clear();
         }
      } else {
         int l = this.list2.size();
         if (this.map.size() > l * 2) {
            HashSet hashset = new HashSet(l);

            for (int j1 = 0; j1 < l; j1++) {
               hashset.add(this.list2.get(j1).playerEntity.getUuid());
            }

            this.map.keySet().retainAll(hashset);
         }

         this.list2.sort(comparator);
         NameTagCache nametagcache = this.nameTagCache;
         ArrayList arraylist1 = this.list2;
         float f7 = this.size.getValueAsFloat();
         float f8 = this.backgroundPlashki.isFlag3() ? this.opacityPlashki.getValueAsFloat() / 100.0F : 0.0F;
         boolean flag19 = this.backgroundPlashki.isFlag3();
         boolean flag20 = this.hidePustyeSloty.isFlag3();
         boolean flag21 = this.kolichestvoPredmetov.isFlag3();
         boolean flag22 = this.uproschatVdali.isFlag3();
         float f4 = this.rangeUproscheniya.getValueAsFloat();
         boolean flag18 = flag22;
         boolean flag17 = flag21;
         boolean flag16 = flag20;
         boolean flag15 = flag19;
         float f3 = f8;
         float f2 = f7;
         ArrayList arraylist = arraylist1;
         nametagcache.onBooleanBooleanFloatBooleanListWorldRenderContextBooleanBooleanFloatBooleanBooleanFloatBoolean(
            flag1, flag3, f4, flag2, arraylist, worldRenderContext, flag15, flag18, f3, flag16, flag17, f2, flag4
         );

         for (int i1 = 0; i1 < l; i1++) {
            this.list2.get(i1).update();
         }
      }
   }

   public boolean check3() {
      return this.vanilnyeNeymtegi.isFlag3();
   }

   public boolean isPlayerEntity(PlayerEntity playerEntity) {
      if (this.notInGame()) {
         return false;
      } else if (playerEntity != null && playerEntity.isAlive()) {
         PlayerEntity playerentity = this.player();
         if (playerentity != null && playerEntity != playerentity) {
            if (playerEntity.distanceTo(playerentity) > this.range.getValue4()) {
               return false;
            } else {
               boolean flag = this.isFriend(playerEntity);
               List listx = this.targets.getList4();
               if (flag) {
                  return listx.contains("Дpузья");
               } else {
                  return !listx.contains("Врaги") ? false : !listx.contains("Врaги тoлько в брoне") || TargetSelector.isLivingEntity(playerEntity);
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public void render7(WorldRenderContext worldRenderContext) {
      if (!StreamBypass.check6()) {
         if (!this.notInGame()) {
            this.render8(worldRenderContext);
         }
      }
   }

   @Override
   public void onEnable() {
   }
}
