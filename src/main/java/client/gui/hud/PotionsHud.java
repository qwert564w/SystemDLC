package client.gui.hud;

import client.api.Theme;
import client.enums.PotionType;
import client.gui.widget.KeybindEntry;
import client.gui.widget.UiContext;
import client.module.CategoryType;
import client.module.Feature;
import client.module.client.HudModule;
import client.module.visual.FullBright;
import client.render.ItemIconCache;
import client.render.RoundedTextureShader;
import client.render.ShapeShader;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.util.Interpolation;
import client.util.SphereItems;
import client.util.TextFormatUtil;
import client.util.UnsafeAccess;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class PotionsHud extends HudPanel {
   private static final float value277 = 16.0F;
   private static final float value278 = 8.0F;
   private static final int value279 = 200;
   private static final int value280 = 100;
   private static final long time2 = 400000000L;
   private static final float value281 = 20.0F;
   private static final float value282 = 4.0F;
   private static final float value283 = 6.0F;
   private static final float value284 = 11.0F;
   private static final float value285 = 1.5F;
   private static final float value286 = 5.0F;
   private static final float value287 = 0.18F;
   private static final float value288 = 0.65F;
   private static final float value289 = 7.0F;
   private static final UnsafeAccess<FullBright> unsafeAccess2 = new UnsafeAccess<>(FullBright.class);
   private final EnumSet<PotionType> enumSet = EnumSet.noneOf(PotionType.class);
   private static final Map<String, PotionType> map2 = new HashMap<>();
   private final EnumMap<PotionType, ItemStack> enumMap = new EnumMap<>(PotionType.class);
   private String text = null;
   private final EnumMap<PotionType, StatusEffectInstance> enumMap2 = new EnumMap<>(PotionType.class);
   private final BooleanSetting svorachivatBafy;
   private final Map<String, RegistryEntry<StatusEffect>> map3;
   private final Map<String, StatusEffectInstance> map4;
   private final Map<String, Integer> map5;
   private final Map<String, Float> map6;
   private final Map<String, Integer> map7;
   private final Map<String, Float> map8;
   private final Interpolation interpolation3;
   private long time3;
   private float value290;
   private final ArrayList<KeybindEntry> list5;
   private final ArrayList<StatusEffectInstance> list6;

   public PotionsHud() {
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Сворачивать бафы");
      booleansetting.setDescription("Прятать эффекты бафа из списка — остаются только те, что переживут cam баф");
      this.svorachivatBafy = booleansetting;
      this.map3 = new HashMap<>();
      this.map4 = new HashMap<>();
      this.map5 = new HashMap<>();
      this.map6 = new HashMap<>();
      this.map7 = new HashMap<>();
      this.map8 = new HashMap<>();
      this.interpolation3 = new Interpolation();
      this.time3 = -1L;
      this.list5 = new ArrayList<>();
      this.list6 = new ArrayList<>();
      this.onSettingArray(new Setting[]{this.svorachivatBafy});
   }

   static {
      for (PotionType potiontype : PotionType.values()) {
         map2.put(potiontype.text, potiontype);
      }
   }

   @Override
   public String getString() {
      return "Потионз";
   }

   @Override
   protected int getIntByKeybindEntry(KeybindEntry keybindEntry) {
      int i = Theme.mutedFg();
      Integer integer = this.map7.get(keybindEntry.getText());
      if (integer == null) {
         return i;
      } else {
         int j = integer;
         if (j > 200) {
            return i;
         } else {
            int k = Theme.danger();
            float f = 1.0F - Math.max(0, j) / 200.0F;
            int l = getIntByIntIntFloat(k, i, f);
            if (j <= 100) {
               float f1 = (float)(System.nanoTime() % 400000000L) / 4.0E8F;
               float f2 = 0.5F + 0.5F * (float)Math.sin(f1 * Math.PI * 2.0);
               l = getIntByIntIntFloat(k, l, f2);
            }

            return l;
         }
      }
   }

   @Override
   public float getFloatByKeybindEntry(KeybindEntry keybindEntry) {
      return this.getFloat30() + this.getFloatByKeybindEntry2(keybindEntry);
   }

   @Override
   protected float getFloat28() {
      return 24.0F;
   }

   private static String getStringByStatusEffectInstance(StatusEffectInstance statusEffectInstance) {
      if (statusEffectInstance.isInfinite()) {
         return "**:**";
      } else {
         int i = statusEffectInstance.getDuration();
         if (i <= 0) {
            return "0:00";
         } else {
            int j = i / 20;
            int k = j % 60;
            return j / 60 + (k < 10 ? ":0" : ":") + k;
         }
      }
   }

   @Override
   protected boolean check24() {
      return true;
   }

   @Override
   protected List getList2() {
      this.map3.put("Speed II", StatusEffects.SPEED);
      this.map3.put("Strength", StatusEffects.STRENGTH);
      this.map3.put("Resistance", StatusEffects.RESISTANCE);
      this.text = PotionType.ASSASIN.text;
      return List.of(
         new KeybindEntry("Speed II", "1:30"),
         new KeybindEntry("Strength", "0:45"),
         new KeybindEntry("Resistance", "**:**"),
         new KeybindEntry(PotionType.ASSASIN.text, "0:58")
      );
   }

   @Override
   protected float getFloatByKeybindEntry2(KeybindEntry keybindEntry) {
      return this.isKeybindEntry(keybindEntry) ? 7.0F : 0.0F;
   }

   @Override
   protected String getString2() {
      return "Potions";
   }

   @Override
   protected float getFloatByKeybindEntry3(KeybindEntry keybindEntry) {
      return 22.0F;
   }

   @Override
   public String getString3() {
      return "ps";
   }

   private boolean isStatusEffectInstance(StatusEffectInstance statusEffectInstance) {
      if (this.svorachivatBafy.isFlag3() && !this.enumMap2.isEmpty()) {
         for (Entry entry : this.enumMap2.entrySet()) {
            if (((PotionType)entry.getKey()).isStatusEffectInstance(statusEffectInstance)) {
               if (statusEffectInstance.isInfinite()) {
                  return false;
               }

               StatusEffectInstance statuseffectinstance = (StatusEffectInstance)entry.getValue();
               return statuseffectinstance.isInfinite() || statusEffectInstance.getDuration() <= statuseffectinstance.getDuration();
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   protected void onMatrix4fKeybindEntryFloatFloatFloat(Matrix4f matrix4f, KeybindEntry keybindEntry, float value, float value2, float value3) {
      PotionType potiontype = map2.get(keybindEntry.getText());
      if (potiontype != null) {
         this.onPotionTypeFloatFloatMatrix4fKeybindEntryFloat(potiontype, value3, value2, matrix4f, keybindEntry, value);
      } else {
         RegistryEntry registryentry = this.map3.get(keybindEntry.getText());
         if (registryentry != null && Feature.mc != null) {
            Sprite sprite = Feature.mc.getStatusEffectSpriteManager().getSprite(registryentry);
            if (sprite != null) {
               float f = value2 + 8.0F;
               float f1 = value3 + 1.0F;
               Identifier identifier1 = sprite.getAtlasId();
               float f12 = sprite.getMinU();
               float f13 = sprite.getMinV();
               float f14 = sprite.getMaxU();
               float f15 = sprite.getMaxV();
               byte b0 = -1;
               float f11 = 0.0F;
               float f10 = 0.0F;
               float f9 = 0.0F;
               float f8 = 0.0F;
               float f7 = f15;
               float f6 = f14;
               float f5 = f13;
               float f4 = f12;
               float f3 = 16.0F;
               float f2 = 16.0F;
               Identifier identifier = identifier1;
               RoundedTextureShader.onFloatFloatFloatFloatFloatFloatIntFloatFloatMatrix4fIdentifierFloatFloatFloatFloatFloat(
                  f, f2, f6, f3, f7, f11, b0, f1, f10, matrix4f, identifier, f8, f4, f5, value, f9
               );
            }
         }
      }
   }

   private static int getIntByIntIntFloat(int count, int count2, float value) {
      if (value <= 0.0F) {
         return count2;
      } else if (value >= 1.0F) {
         return count;
      } else {
         int i = count2 >>> 24 & 0xFF;
         int j = count2 >>> 16 & 0xFF;
         int k = count2 >>> 8 & 0xFF;
         int l = count2 & 0xFF;
         int i1 = count >>> 24 & 0xFF;
         int j1 = count >>> 16 & 0xFF;
         int k1 = count >>> 8 & 0xFF;
         int l1 = count & 0xFF;
         int i2 = (int)(i + (i1 - i) * value);
         int j2 = (int)(j + (j1 - j) * value);
         int k2 = (int)(k + (k1 - k) * value);
         int l2 = (int)(l + (l1 - l) * value);
         return i2 << 24 | j2 << 16 | k2 << 8 | l2;
      }
   }

   private static String getStringByInt(int count) {
      return switch (count) {
         case 1 -> "I";
         case 2 -> "II";
         case 3 -> "III";
         case 4 -> "IV";
         case 5 -> "V";
         case 6 -> "VI";
         case 7 -> "VII";
         case 8 -> "VIII";
         case 9 -> "IX";
         case 10 -> "X";
         default -> Integer.toString(count);
      };
   }

   private static String getStringByStatusEffectInstance2(StatusEffectInstance statusEffectInstance) {
      RegistryEntry registryentry = statusEffectInstance.getEffectType();
      Identifier identifier = Registries.STATUS_EFFECT.getId((StatusEffect)registryentry.value());
      String s = TextFormatUtil.getStringByString(Objects.requireNonNull(identifier).getPath());
      int i = statusEffectInstance.getAmplifier();
      return i <= 0 ? s : s + " " + getStringByInt(i + 1);
   }

   private void onPotionTypeFloatFloatMatrix4fKeybindEntryFloat(PotionType potionType, float value, float value2, Matrix4f matrix4f, KeybindEntry keybindEntry, float value3) {
      float f = this.getFloatByKeybindEntry2(keybindEntry);
      if (f > 0.0F) {
         float f7 = value2 + 8.0F;
         int i = Theme.border();
         float f3 = 1.0F;
         float f2 = 184.0F;
         float f1 = f7;
         ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value3, f3, f1, value, f2, matrix4f, i);
      }

      ItemStack itemstack = this.enumMap.computeIfAbsent(potionType, var0 -> SphereItems.getItemStackByTrackedItem(var0.trackedItem));
      float f8 = value2 + 8.0F;
      float f9 = value + f + 1.0F;
      float f6 = 16.0F;
      float f5 = f9;
      float f4 = f8;
      ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f6, value3, f4, itemstack, matrix4f, f5);
   }

   private boolean isKeybindEntry(KeybindEntry keybindEntry) {
      return keybindEntry != null && keybindEntry.getText().equals(this.text);
   }

   @Override
   protected float getFloat30() {
      return Math.max(16.0F, 20.0F);
   }

   @Override
   protected CategoryType getCategoryType2() {
      return CategoryType.POTION;
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getEffectsZeliy().setBoolean(flag);
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getEffectsZeliy().isFlag3();
   }

   private float getFloatByKeybindEntry4(KeybindEntry keybindEntry) {
      String s = keybindEntry.getText();
      Float f = this.map6.get(s);
      float f1 = f == null ? 0.65F : f;
      long i = UiContext.getTime();
      if (this.time3 != i) {
         this.time3 = i;
         this.value290 = this.interpolation3.getFloat2();
      }

      Float f2 = this.map8.get(s);
      float f7;
      if (f2 == null) {
         f7 = f1;
      } else {
         f7 = f2;
         float f6 = 0.18F;
         float f5 = this.value290;
         float f4 = f7;
         f7 = Interpolation.getFloatByFloatFloatFloatFloat2(f1, f4, f5, f6);
      }

      float f3 = f7;
      this.map8.put(s, f3);
      return f3;
   }

   @Override
   protected void onFloatMatrix4fFloatFloatKeybindEntryFloatFloat(float value, Matrix4f matrix4f, float value2, float value3, KeybindEntry keybindEntry, float value4, float value5) {
      float f = value2 - 6.0F;
      float f1 = value5 + 5.0F + 11.0F + 12.0F;
      float f2 = value + -4.0F;
      int k = Theme.elevated();
      float f7 = this.getFloatByFloat(value4);
      int i = k;
      float f6 = 4.0F;
      float f5 = 20.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f6, f, i, matrix4f, f5, f1, f7, f2);
      float f3 = Math.round(value2 + value5 + 5.0F);
      float f4 = Math.round(value + 0.5F);
      int j = this.getIntByKeybindEntry(keybindEntry);
      float f10 = this.getFloatByKeybindEntry4(keybindEntry);
      float f9 = 1.5F;
      float f8 = 11.0F;
      ShapeShader.onIntFloatMatrix4fFloatFloatFloatFloatFloat(j, f4, matrix4f, f10, f9, f3, value4, f8);
   }

   private void addArrayList(ArrayList arrayList) {
      this.text = null;

      for (Entry entry : this.enumMap2.entrySet()) {
         PotionType potiontype = (PotionType)entry.getKey();
         StatusEffectInstance statuseffectinstance = (StatusEffectInstance)entry.getValue();
         String s = potiontype.text;
         if (this.text == null) {
            this.text = s;
         }

         arrayList.add(new KeybindEntry(s, getStringByStatusEffectInstance(statuseffectinstance)));
         this.map4.put(s, statuseffectinstance);
         Integer integer = this.map5.get(s);
         Float f = this.map6.get(s);
         boolean flag = f != null && f <= 0.001F;
         if (integer == null || flag || statuseffectinstance.getDuration() > integer) {
            this.map5.put(s, statuseffectinstance.getDuration());
            if (flag) {
               this.map8.remove(s);
            }
         }
      }
   }

   private void addClientPlayerEntity(ClientPlayerEntity clientPlayerEntity) {
      this.enumMap2.clear();

      for (PotionType potiontype : PotionType.values()) {
         if (potiontype.isLivingEntity(clientPlayerEntity)) {
            this.enumSet.add(potiontype);
         }

         if (this.enumSet.contains(potiontype)) {
            StatusEffectInstance statuseffectinstance = potiontype.getStatusEffectInstanceByLivingEntity(clientPlayerEntity);
            if (statuseffectinstance == null) {
               this.enumSet.remove(potiontype);
            } else {
               this.enumMap2.put(potiontype, statuseffectinstance);
            }
         }
      }
   }

   @Override
   protected List getList3() {
      ArrayList arraylist = this.list5;
      arraylist.clear();
      this.map4.clear();
      ClientPlayerEntity clientplayerentity = Feature.mc.player;
      if (clientplayerentity == null) {
         this.enumSet.clear();
         this.enumMap2.clear();
         return arraylist;
      } else {
         boolean flag = unsafeAccess2.getModule2() != null;
         this.addClientPlayerEntity(clientplayerentity);
         ArrayList<StatusEffectInstance> arraylist1 = this.list6;
         arraylist1.clear();

         for (StatusEffectInstance statuseffectinstance : clientplayerentity.getStatusEffects()) {
            if ((!flag || statuseffectinstance.getEffectType() != StatusEffects.NIGHT_VISION) && !this.isStatusEffectInstance(statuseffectinstance)) {
               arraylist1.add(statuseffectinstance);
            }
         }

         arraylist1.sort(Comparator.comparingInt(var0 -> var0.isInfinite() ? Integer.MAX_VALUE : var0.getDuration()));

         for (StatusEffectInstance statuseffectinstance1 : arraylist1) {
            String s = getStringByStatusEffectInstance2(statuseffectinstance1);
            arraylist.add(new KeybindEntry(s, getStringByStatusEffectInstance(statuseffectinstance1)));
            this.map3.put(s, statuseffectinstance1.getEffectType());
            this.map4.put(s, statuseffectinstance1);
            if (!statuseffectinstance1.isInfinite()) {
               Integer integer = this.map5.get(s);
               Float f = this.map6.get(s);
               boolean flag1 = f != null && f <= 0.001F;
               if (integer == null || flag1 || statuseffectinstance1.getDuration() > integer) {
                  this.map5.put(s, statuseffectinstance1.getDuration());
                  if (flag1) {
                     this.map8.remove(s);
                  }
               }
            }
         }

         this.addArrayList(arraylist);

         for (Entry entry : this.map4.entrySet()) {
            StatusEffectInstance statuseffectinstance2 = (StatusEffectInstance)entry.getValue();
            float f1;
            if (statuseffectinstance2.isInfinite()) {
               f1 = 1.0F;
            } else {
               Integer integer1 = this.map5.get(entry.getKey());
               f1 = integer1 != null && integer1 > 0 ? Math.clamp((float)statuseffectinstance2.getDuration() / integer1.intValue(), 0.0F, 1.0F) : 1.0F;
            }

            this.map6.put((String)entry.getKey(), f1);
            if (statuseffectinstance2.isInfinite()) {
               this.map7.remove(entry.getKey());
            } else {
               this.map7.put((String)entry.getKey(), statuseffectinstance2.getDuration());
            }
         }

         for (String s1 : this.map6.keySet()) {
            if (!this.map4.containsKey(s1)) {
               this.map6.put(s1, 0.0F);
               this.map7.put(s1, 0);
            }
         }

         this.addMapArray(new Map[]{this.map3, this.map5, this.map6, this.map7, this.map8});
         return arraylist;
      }
   }
}
