package client.module.client;

import client.concurrent.SystemClient;
import client.module.Category;
import client.module.Module;
import client.setting.ActionSetting;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.util.AttackRecord;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class HudModule extends Module {
   private BooleanSetting vatermarka;
   private BooleanSetting keybindy;
   private BooleanSetting spisokStafa;
   private BooleanSetting effectsZeliy;
   private BooleanSetting armor;
   private BooleanSetting targethud;
   private BooleanSetting kuldauny;
   private BooleanSetting kuldaunyTargets;
   private BooleanSetting svapBindy;
   private BooleanSetting uvedomleniya;
   private BooleanSetting koordinaty;
   private final BooleanSetting nasyschenie;
   private BooleanSetting inventar;
   private ActionSetting sbrositPoziciiHud;
   private ListSetting elementy;

   public HudModule() {
      super("HudModule", Category.CLIENT);
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Ватермарка");
      booleansetting1.setDescription("Рендерить ватермарку?");
      this.vatermarka = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Кейбинды");
      booleansetting1.setDescription("Рендерить кейбинды?");
      this.keybindy = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Список стафа");
      booleansetting1.setDescription("Рендерить список включенных модулей?");
      this.spisokStafa = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Эффекты зелий");
      booleansetting1.setDescription("Рендерить список активных зелий?");
      this.effectsZeliy = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Броня");
      booleansetting1.setDescription("Рендерить список брони?");
      this.armor = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("ТаргетХуд");
      booleansetting1.setDescription("Рендерить таргетхуд?");
      this.targethud = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Кулдауны");
      booleansetting1.setDescription("Рендерить кулдауны предметов?");
      this.kuldauny = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Кулдауны цели");
      booleansetting1.setDescription("Рендерить откаты предметов y последней цели?");
      this.kuldaunyTargets = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Свап бинды");
      booleansetting1.setDescription("Рендерить бинды свапа?");
      this.svapBindy = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Уведомления");
      booleansetting1.setDescription("Рендерить уведомления o модулях и событиях?");
      this.uvedomleniya = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Координаты");
      booleansetting1.setDescription("Рендерить текущие координаты игрока?");
      this.koordinaty = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Насыщение");
      booleansetting1.setDescription("Показывать насыщенность голода");
      this.nasyschenie = booleansetting1;
      booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Инвентарь");
      booleansetting1.setDescription("Рендерить содержимое инвентаря на экране?");
      this.inventar = booleansetting1;
      LinkedHashMap linkedhashmap = new LinkedHashMap();
      linkedhashmap.put(this.vatermarka.getName(), this.vatermarka);
      linkedhashmap.put(this.keybindy.getName(), this.keybindy);
      linkedhashmap.put(this.spisokStafa.getName(), this.spisokStafa);
      linkedhashmap.put(this.effectsZeliy.getName(), this.effectsZeliy);
      linkedhashmap.put(this.armor.getName(), this.armor);
      linkedhashmap.put(this.targethud.getName(), this.targethud);
      linkedhashmap.put(this.kuldauny.getName(), this.kuldauny);
      linkedhashmap.put(this.kuldaunyTargets.getName(), this.kuldaunyTargets);
      linkedhashmap.put(this.svapBindy.getName(), this.svapBindy);
      linkedhashmap.put(this.uvedomleniya.getName(), this.uvedomleniya);
      linkedhashmap.put(this.koordinaty.getName(), this.koordinaty);
      linkedhashmap.put(this.nasyschenie.getName(), this.nasyschenie);
      linkedhashmap.put(this.inventar.getName(), this.inventar);
      ArrayList arraylist = new ArrayList(linkedhashmap.keySet());
      ArrayList arraylist1 = new ArrayList();

      for (Entry entry : (Iterable<Entry>)(linkedhashmap.entrySet())) {
         if (((BooleanSetting)entry.getValue()).isFlag3()) {
            arraylist1.add((String)entry.getKey());
         }
      }

      ListSetting listsetting = new ListSetting("", "", arraylist, arraylist1, true);
      listsetting.setName("Элементы");
      listsetting.setDescription("Что показывать в HUD.");
      this.elementy = listsetting;
      Supplier<Boolean> supplier = HudModule::getBoolean;

      for (BooleanSetting booleansetting : (Iterable<BooleanSetting>)(linkedhashmap.values())) {
         booleansetting.setVisibleWhen(supplier);
      }

      this.elementy.setOnChange(() -> this.onMap(linkedhashmap));
      ActionSetting actionsetting = new ActionSetting("", "");
      actionsetting.setName("Сбросить позиции HUD");
      actionsetting.setDescription("Сбросить все позиции HUD элементов на дефолтные");
      this.sbrositPoziciiHud = actionsetting;
      this.sbrositPoziciiHud.setRunnable(this::update11);
      this.addSettings(
         new Setting[]{
            this.elementy,
            this.vatermarka,
            this.keybindy,
            this.spisokStafa,
            this.effectsZeliy,
            this.armor,
            this.targethud,
            this.kuldauny,
            this.kuldaunyTargets,
            this.svapBindy,
            this.uvedomleniya,
            this.koordinaty,
            this.nasyschenie,
            this.inventar,
            this.sbrositPoziciiHud
         }
      );
   }

   public BooleanSetting getArmor() {
      return this.armor;
   }

   public BooleanSetting getSvapBindy() {
      return this.svapBindy;
   }

   public BooleanSetting getKeybindy() {
      return this.keybindy;
   }

   public BooleanSetting getSpisokStafa() {
      return this.spisokStafa;
   }

   public BooleanSetting getKuldaunyTargets() {
      return this.kuldaunyTargets;
   }

   @Override
   public void onDisable() {
   }

   public BooleanSetting getTargethud() {
      return this.targethud;
   }

   public BooleanSetting getNasyschenie() {
      return this.nasyschenie;
   }

   @Override
   protected boolean check2() {
      return false;
   }

   public BooleanSetting getEffectsZeliy() {
      return this.effectsZeliy;
   }

   private void onMap(Map map) {
      for (Entry entry : (Iterable<Entry>)(map.entrySet())) {
         boolean flag = this.elementy.isString((String)entry.getKey());
         if (((BooleanSetting)entry.getValue()).isFlag3() != flag) {
            ((BooleanSetting)entry.getValue()).setBoolean(flag);
         }
      }
   }

   public BooleanSetting getVatermarka() {
      return this.vatermarka;
   }

   @Override
   public void onPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      if (entity2 instanceof PlayerEntity playerentity && playerentity != playerEntity && playerentity.isAlive()) {
         AttackRecord.setPlayerEntity(playerentity);
      }
   }

   private static Boolean getBoolean() {
      return false;
   }

   private void update11() {
      try {
         SystemClient systemclient = SystemClient.getInstance();
         if (systemclient != null && systemclient.getHashUtil() != null) {
            systemclient.getHashUtil().update5();
         }
      } catch (Exception exception) {
      }
   }

   public BooleanSetting getInventar() {
      return this.inventar;
   }

   public BooleanSetting getKoordinaty() {
      return this.koordinaty;
   }

   @Override
   public void onEnable() {
   }

   public BooleanSetting getKuldauny() {
      return this.kuldauny;
   }

   public BooleanSetting getUvedomleniya() {
      return this.uvedomleniya;
   }
}
