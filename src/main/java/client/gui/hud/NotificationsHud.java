package client.gui.hud;

import client.api.Icon;
import client.enums.Edge;
import client.gui.widget.NotificationToast;
import client.gui.widget.RenderElement;
import client.gui.widget.UiContext;
import client.module.CategoryType;
import client.module.client.HudModule;
import client.setting.BooleanSetting;
import client.setting.CompactGroupSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.Interpolation;
import client.util.NotificationManager;
import client.util.StringParts;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class NotificationsHud extends RenderElement {
   private static final float value271 = 220.0F;
   private static final float value272 = 0.12F;
   private static final float value273 = 24.0F;
   private static final float value274 = 250.0F;
   private final Interpolation interpolation2 = new Interpolation();
   private float value275 = -1.0F;
   private float value276 = -1.0F;
   private final SliderSetting timeViseniya;
   private final SliderSetting maxUvedomleniy;
   private final BooleanSetting statichnayaAnimation;
   private static final String text = "Все";
   private static final String text2 = "Только положительные";
   private static final String text3 = "Только отрицательные";
   private static final String text4 = "Свои";
   private final ListSetting uvedomleniyaObEffektah;
   private final CompactGroupSetting spisokEffektov;
   private final Map<Identifier, BooleanSetting> map;
   private final IdentityHashMap<Setting, RegistryEntry<StatusEffect>> identityHashMap;
   private NotificationToast notificationToast;
   private static final int[] intArray = new int[]{20, 10, 5};
   private int value277;
   private int value278;
   private static final int value279 = 100;
   private final Set<Identifier> set;
   private final Set<Identifier> set2;

   public NotificationsHud() {
      SliderSetting slidersetting = new SliderSetting("", "", 3000.0, 500.0, 10000.0, 100.0, StringParts.join(new String[]{"м", "c"}), 0);
      slidersetting.setName("Время висения");
      slidersetting.setDescription("Сколько миллисекунд уведомление остаётся на экране.");
      this.timeViseniya = slidersetting;
      slidersetting = new SliderSetting("", "", 5.0, 1.0, 10.0, 1.0);
      slidersetting.setName("Макс. уведомлений");
      slidersetting.setDescription("Максимальное количество уведомлений на экране одновременно.");
      this.maxUvedomleniy = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Статичная анимация");
      booleansetting.setDescription("Если включено — уведомления появляются на месте (без выезда от ближайшего края экрана).");
      this.statichnayaAnimation = booleansetting;
      ListSetting listsetting = new ListSetting(
         "",
         "",
         List.of(
            StringParts.join(new String[]{"В", "c", "е"}),
            StringParts.join(new String[]{"Т", "о", "л", "ь", "к", "о", " ", "п", "о", "л", "о", "ж", "и", "т", "е", "л", "ь", "н", "ы", "е"}),
            StringParts.join(new String[]{"Т", "о", "л", "ь", "к", "о", " ", "о", "т", "р", "и", "ц", "а", "т", "е", "л", "ь", "н", "ы", "е"}),
            StringParts.join(new String[]{"С", "в", "о", "и"})
         ),
         List.of(StringParts.join(new String[]{"В", "c", "е"})),
         false
      );
      listsetting.setName("Уведомления об эффектах");
      listsetting.setDescription("O каких эффектах уведомлять при истечении.");
      this.uvedomleniyaObEffektah = listsetting;
      this.map = new HashMap<>();
      this.identityHashMap = new IdentityHashMap<>();
      this.value278 = -1;
      this.set = new HashSet<>();
      this.set2 = new HashSet<>();
      this.timeViseniya.setOnChange(this::update4);
      this.maxUvedomleniy.setOnChange(this::update4);
      ArrayList<Setting> arraylist = new ArrayList<>();
      Registries.STATUS_EFFECT
         .streamEntries()
         .sorted(Comparator.comparing(var0 -> getStringByStatusEffect((StatusEffect)var0.value())))
         .forEach(
            var2x -> {
               Identifier identifier = Registries.STATUS_EFFECT.getId((StatusEffect)var2x.value());
               if (identifier != null) {
                  BooleanSetting booleansetting1 = new BooleanSetting(
                     getStringByStatusEffect((StatusEffect)var2x.value()), "Уведомлять o скором истечении этого эффекта", true
                  );
                  this.map.put(identifier, booleansetting1);
                  this.identityHashMap.put(booleansetting1, var2x);
                  arraylist.add(booleansetting1);
               }
            }
         );
      CompactGroupSetting compactgroupsetting = new CompactGroupSetting("", "", arraylist.toArray(new Setting[0]));
      compactgroupsetting.setName("Список эффектов");
      compactgroupsetting.setDescription("Включи или выключи уведомления по конкретным эффектам.");
      this.spisokEffektov = compactgroupsetting;
      this.spisokEffektov.getCompactGroupSettingByFunction(var1x -> {
         RegistryEntry registryentry = this.identityHashMap.get(var1x);
         if (registryentry == null) {
            return null;
         } else {
            MinecraftClient minecraftclient = MinecraftClient.getInstance();
            return minecraftclient == null ? null : minecraftclient.getStatusEffectSpriteManager().getSprite(registryentry);
         }
      });
      this.spisokEffektov.setVisibleWhen(() -> "Свои".equals(this.uvedomleniyaObEffektah.getString2()));
      this.onSettingArray(new Setting[]{this.timeViseniya, this.maxUvedomleniy, this.statichnayaAnimation, this.uvedomleniyaObEffektah, this.spisokEffektov});
      this.update4();
   }

   @Override
   protected boolean check() {
      return false;
   }

   @Override
   public String getString() {
      return "Нотифисатионз";
   }

   @Override
   public CategoryType getCategoryType() {
      return CategoryType.INFO;
   }

   private float getFloat28() {
      float f = NotificationManager.getInstance().getFloat();
      if (f > 0.0F) {
         return f;
      } else {
         return this.notificationToast != null ? this.notificationToast.getFloat() : 220.0F;
      }
   }

   private void addNotificationManager(NotificationManager notificationManager) {
      ClientPlayerEntity clientplayerentity = MinecraftClient.getInstance().player;
      if (clientplayerentity == null) {
         this.set.clear();
      } else {
         String s = this.uvedomleniyaObEffektah.getString2();
         boolean flag = "Только положительные".equals(s);
         boolean flag1 = "Только отрицательные".equals(s);
         boolean flag2 = "Свои".equals(s);
         this.set2.clear();

         for (StatusEffectInstance statuseffectinstance : clientplayerentity.getStatusEffects()) {
            RegistryEntry registryentry = statuseffectinstance.getEffectType();
            Identifier identifier = Registries.STATUS_EFFECT.getId((StatusEffect)registryentry.value());
            if (identifier != null) {
               this.set2.add(identifier);
               StatusEffect statuseffect = (StatusEffect)registryentry.value();
               if (!this.isBooleanBooleanBooleanStatusEffectIdentifier(flag2, flag1, flag, statuseffect, identifier)) {
                  this.set.remove(identifier);
               } else if (statuseffectinstance.isInfinite()) {
                  this.set.remove(identifier);
               } else {
                  int i = statuseffectinstance.getDuration();
                  if (i > 100) {
                     this.set.remove(identifier);
                  } else if (i > 0 && this.set.add(identifier)) {
                     Icon icon1 = Icon.getIconByRegistryEntry(registryentry);
                     String s3 = getStringByStatusEffectInstanceIdentifier(statuseffectinstance, identifier);
                     String s2 = "5с";
                     String s1 = s3;
                     Icon icon = icon1;
                     notificationManager.onStringIconString(s1, icon, s2);
                  }
               }
            }
         }

         this.set.removeIf(var1x -> !this.set2.contains(var1x));
      }
   }

   public SliderSetting getMaxUvedomleniy() {
      return this.maxUvedomleniy;
   }

   @Override
   public float getFloat8() {
      return 1.0F;
   }

   private void setNotificationManager(NotificationManager notificationManager) {
      if (this.check4()) {
         this.setNotificationManager2(notificationManager);
      } else if (this.notificationToast != null) {
         notificationManager.onNotificationToast2(this.notificationToast);
         this.notificationToast = null;
      }
   }

   private float getFloat29() {
      float f = NotificationManager.getInstance().getFloat2();
      return Math.max(32.0F, f);
   }

   @Override
   public float getFloat9() {
      return 32.0F * this.getFloat30();
   }

   private void setNotificationManager2(NotificationManager notificationManager) {
      if (this.notificationToast == null) {
         Icon icon1 = Icon.getIconByCategoryType(CategoryType.INFO);
         String s1 = "Уведомления";
         String s = "Пример";
         Icon icon = icon1;
         this.notificationToast = notificationManager.getNotificationToastByStringIconString(s1, icon, s);
      }
   }

   @Override
   public String getString3() {
      return "ns";
   }

   private void setNotificationManager3(NotificationManager notificationManager) {
      ClientPlayerEntity clientplayerentity = MinecraftClient.getInstance().player;
      if (clientplayerentity == null) {
         this.value277 = 0;
         this.value278 = -1;
      } else {
         ItemStack itemstack = clientplayerentity.getEquippedStack(EquipmentSlot.HEAD);
         if (!itemstack.isEmpty() && itemstack.isDamageable() && itemstack.getMaxDamage() > 0) {
            int i = System.identityHashCode(itemstack.getItem()) ^ itemstack.getMaxDamage();
            if (i != this.value277) {
               this.value277 = i;
               this.value278 = -1;
            }

            int j = itemstack.getMaxDamage();
            int k = j - itemstack.getDamage();
            int l = Math.round(k * 100.0F / j);
            int i1 = -1;

            for (int j1 = 0; j1 < intArray.length; j1++) {
               if (l <= intArray[j1]) {
                  i1 = j1;
               }
            }

            if (i1 > this.value278) {
               Icon icon1 = Icon.getIconByItemStack(itemstack);
               String s1 = intArray[i1] + "%";
               String s = "Шлем";
               Icon icon = icon1;
               notificationManager.onStringIconString(s, icon, s1);
               this.value278 = i1;
            }
         } else {
            this.value277 = 0;
            this.value278 = -1;
         }
      }
   }

   private boolean isBooleanBooleanBooleanStatusEffectIdentifier(boolean flag, boolean flag2, boolean flag3, StatusEffect statusEffect, Identifier identifier) {
      if (!flag) {
         return flag3 && statusEffect.getCategory() != StatusEffectCategory.BENEFICIAL ? false : !flag2 || statusEffect.getCategory() == StatusEffectCategory.HARMFUL;
      } else {
         BooleanSetting booleansetting = this.map.get(identifier);
         return booleansetting != null && booleansetting.isFlag3();
      }
   }

   private static String getStringByStatusEffectInstanceIdentifier(StatusEffectInstance statusEffectInstance, Identifier identifier) {
      String s = Objects.requireNonNull(identifier).getPath();
      StringBuilder stringbuilder = new StringBuilder(s.length());
      boolean flag = true;

      for (int i = 0; i < s.length(); i++) {
         char c0 = s.charAt(i);
         if (c0 == '_') {
            stringbuilder.append(' ');
            flag = true;
         } else {
            stringbuilder.append(flag ? Character.toUpperCase(c0) : c0);
            flag = false;
         }
      }

      int j = statusEffectInstance.getAmplifier();
      if (j > 0) {
         stringbuilder.append(' ').append(j + 1);
      }

      return stringbuilder.toString();
   }

   private Edge getEdge() {
      if (this.statichnayaAnimation.isFlag3()) {
         return Edge.NONE;
      } else {
         UiContext uicontext = UiContext.getInstance();
         if (uicontext == null) {
            return Edge.NONE;
         } else {
            float f5 = this.getValue260();
            float f6 = this.getValue261();
            float f7 = uicontext.getFloat3();
            float f8 = uicontext.getValue238();
            float f4 = 250.0F;
            float f3 = f8;
            float f2 = f7;
            float f1 = f6;
            float f = f5;
            return Edge.getEdgeByFloatFloatFloatFloatFloat(f3, f2, f1, f4, f);
         }
      }
   }

   public BooleanSetting getStatichnayaAnimation() {
      return this.statichnayaAnimation;
   }

   public SliderSetting getTimeViseniya() {
      return this.timeViseniya;
   }

   private static String getStringByStatusEffect(StatusEffect statusEffect) {
      Identifier identifier = Registries.STATUS_EFFECT.getId(statusEffect);
      if (identifier == null) {
         return "?";
      } else {
         String s = identifier.getPath();
         StringBuilder stringbuilder = new StringBuilder(s.length());
         boolean flag = true;

         for (int i = 0; i < s.length(); i++) {
            char c0 = s.charAt(i);
            if (c0 == '_') {
               stringbuilder.append(' ');
               flag = true;
            } else {
               stringbuilder.append(flag ? Character.toUpperCase(c0) : c0);
               flag = false;
            }
         }

         return stringbuilder.toString();
      }
   }

   private float getFloat30() {
      return Math.max(0.1F, this.getSizeModulya().getValueAsFloat() / 100.0F);
   }

   private void update4() {
      NotificationManager.getInstance().onIntLong(this.maxUvedomleniy.getInt2(), this.timeViseniya.getValueAsLong());
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getUvedomleniya().isFlag3();
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getUvedomleniya().setBoolean(flag);
   }

   @Override
   public float getFloat10() {
      return this.getFloat29() * this.getFloat30();
   }

   @Override
   protected boolean check11() {
      return false;
   }

   @Override
   public float getFloat11() {
      return this.getFloat28() * this.getFloat30();
   }

   @Override
   public float getFloat14() {
      return this.getFloat24();
   }

   @Override
   protected boolean check19() {
      return this.check5();
   }

   @Override
   protected boolean check20() {
      return false;
   }

   @Override
   protected void onFloatDrawContextMatrix4fFloat(float value, DrawContext drawContext, Matrix4f matrix4f, float value2) {
      NotificationManager notificationmanager = NotificationManager.getInstance();
      this.setNotificationManager(notificationmanager);
      float f = this.getFloat30();
      float f1 = this.interpolation2.getFloat2();
      float f12 = this.value275;
      float f13 = this.getFloat28() * f;
      float f4 = 0.12F;
      float f3 = f13;
      float f2 = f12;
      this.value275 = Interpolation.getFloatByFloatFloatFloatFloat(f3, f1, f2, f4);
      f12 = this.value276;
      f13 = this.getFloat29() * f;
      float f7 = 0.12F;
      float f6 = f13;
      float f5 = f12;
      this.value276 = Interpolation.getFloatByFloatFloatFloatFloat(f6, f1, f5, f7);
      this.setNotificationManager3(notificationmanager);
      this.addNotificationManager(notificationmanager);
      Edge edge = this.getEdge();
      notificationmanager.removeFloat(this.getValue261());
      f13 = this.getValue260();
      float f14 = this.getValue261();
      float f11 = this.getFloat18();
      float f10 = 24.0F;
      float f9 = f14;
      float f8 = f13;
      notificationmanager.onFloatFloatFloatEdgeFloatFloatDrawContextFloat(f9, f, f8, edge, f11, f10, drawContext, value);
   }

   @Override
   public float[] getFloatArray() {
      return new float[]{960.0F, 80.0F};
   }

   @Override
   public float getFloat22() {
      return this.getValue260() - this.getFloat24() / 2.0F;
   }

   @Override
   public float getFloat23() {
      if (this.value276 < 0.0F) {
         this.value276 = this.getFloat29() * this.getFloat30();
      }

      return this.value276;
   }

   @Override
   public float getFloat24() {
      if (this.value275 < 0.0F) {
         this.value275 = this.getFloat28() * this.getFloat30();
      }

      return this.value275;
   }
}
