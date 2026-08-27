package client.data;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

public record EffectLevel(RegistryEntry<StatusEffect> type, int level) {
   public boolean isStatusEffectInstance(StatusEffectInstance statusEffectInstance) {
      return statusEffectInstance.getEffectType().value() == this.type.value() && statusEffectInstance.getAmplifier() == this.level - 1;
   }

   public RegistryEntry<StatusEffect> getType() {
      return this.type;
   }

   public int getLevel() {
      return this.level;
   }
}
