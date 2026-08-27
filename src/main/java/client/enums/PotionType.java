package client.enums;

import client.data.EffectLevel;
import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;

public enum PotionType {
   SNOTVORNOE(
      TrackedItem.SNOTVORNOE,
      "Снотворка",
      getEffectLevelByRegistryEntryInt(StatusEffects.WEAKNESS, 2),
      getEffectLevelByRegistryEntryInt(StatusEffects.MINING_FATIGUE, 2),
      getEffectLevelByRegistryEntryInt(StatusEffects.WITHER, 3),
      getEffectLevelByRegistryEntryInt(StatusEffects.BLINDNESS, 1)
   ),
   PALADIN(
      TrackedItem.POTION_PALADINA,
      "Палладинка",
      getEffectLevelByRegistryEntryInt(StatusEffects.HEALTH_BOOST, 3),
      getEffectLevelByRegistryEntryInt(StatusEffects.RESISTANCE, 1),
      getEffectLevelByRegistryEntryInt(StatusEffects.FIRE_RESISTANCE, 1),
      getEffectLevelByRegistryEntryInt(StatusEffects.INVISIBILITY, 1)
   ),
   SVYATAYA_VODA(
      TrackedItem.SVYATAYA_VODA,
      "Святка",
      getEffectLevelByRegistryEntryInt(StatusEffects.REGENERATION, 2),
      getEffectLevelByRegistryEntryInt(StatusEffects.INVISIBILITY, 2)
   ),
   ASSASIN(
      TrackedItem.POTION_ASSASINA,
      "Ассасинка",
      getEffectLevelByRegistryEntryInt(StatusEffects.STRENGTH, 4),
      getEffectLevelByRegistryEntryInt(StatusEffects.SPEED, 3),
      getEffectLevelByRegistryEntryInt(StatusEffects.HASTE, 1)
   ),
   RADIACIYA(
      TrackedItem.POTION_RADIACII,
      "Радиация",
      getEffectLevelByRegistryEntryInt(StatusEffects.POISON, 2),
      getEffectLevelByRegistryEntryInt(StatusEffects.WITHER, 2),
      getEffectLevelByRegistryEntryInt(StatusEffects.SLOWNESS, 3),
      getEffectLevelByRegistryEntryInt(StatusEffects.HUNGER, 5),
      getEffectLevelByRegistryEntryInt(StatusEffects.GLOWING, 1)
   );

   public final TrackedItem trackedItem;
   public final String text;
   private final List<EffectLevel> list;
   private static final PotionType[] potionTypeArray = getPotionTypeArray();

   private PotionType(TrackedItem trackedItem2, String text2, EffectLevel... effectLevel) {
      this.trackedItem = trackedItem2;
      this.text = text2;
      this.list = List.of(effectLevel);
   }

   public boolean isStatusEffectInstance(StatusEffectInstance statusEffectInstance) {
      if (statusEffectInstance == null) {
         return false;
      } else {
         for (EffectLevel effectlevel : this.list) {
            if (effectlevel.isStatusEffectInstance(statusEffectInstance)) {
               return true;
            }
         }

         return false;
      }
   }

   private static StatusEffectInstance getStatusEffectInstanceByLivingEntityEffectLevel(LivingEntity livingEntity, EffectLevel effectLevel) {
      StatusEffectInstance statuseffectinstance = livingEntity.getStatusEffect(effectLevel.getType());
      return statuseffectinstance != null && effectLevel.isStatusEffectInstance(statuseffectinstance) ? statuseffectinstance : null;
   }

   private static PotionType[] getPotionTypeArray() {
      return new PotionType[]{SNOTVORNOE, PALADIN, SVYATAYA_VODA, ASSASIN, RADIACIYA};
   }

   public StatusEffectInstance getStatusEffectInstanceByLivingEntity(LivingEntity livingEntity) {
      return livingEntity == null ? null : getStatusEffectInstanceByLivingEntityEffectLevel(livingEntity, this.list.getFirst());
   }

   public static PotionType getPotionTypeByString(String text) {
      return Enum.valueOf(PotionType.class, text);
   }

   public boolean isLivingEntity(LivingEntity livingEntity) {
      if (livingEntity == null) {
         return false;
      } else {
         for (EffectLevel effectlevel : this.list) {
            if (getStatusEffectInstanceByLivingEntityEffectLevel(livingEntity, effectlevel) == null) {
               return false;
            }
         }

         return true;
      }
   }

   private static EffectLevel getEffectLevelByRegistryEntryInt(RegistryEntry registryEntry, int count) {
      return new EffectLevel(registryEntry, count);
   }
}
