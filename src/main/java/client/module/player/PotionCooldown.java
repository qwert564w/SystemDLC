package client.module.player;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.AttackEvent;
import client.util.PvpStateParser;
import client.util.StringParts;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.component.type.UseCooldownComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class PotionCooldown extends Module {
   private static final Identifier identifier = Identifier.of("minecraft", "healing_potion");
   private SliderSetting cooldown;
   private BooleanSetting onlyVPvp;
   private final Set<ItemStack> set;
   private Item item;
   private ItemStack itemStack;
   private int value235;
   private int value236;

   public PotionCooldown() {
      super("PotionCooldown", Category.PLAYER);
      SliderSetting slidersetting = new SliderSetting("", "", 18.0, 1.0, 30.0, 1.0, StringParts.join(new String[]{" ", "c", "е", "к"}), 0);
      slidersetting.setName("Кулдаун");
      slidersetting.setDescription("Задержка между бросками лечебного зелья");
      this.cooldown = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Только в ПвП");
      booleansetting.setDescription("Применять кулдаун только в режиме пвп");
      this.onlyVPvp = booleansetting;
      this.set = new HashSet<>();
      this.addSettings(new Setting[]{this.cooldown, this.onlyVPvp});
   }

   @Override
   public void onTick() {
      PlayerEntity playerentity = this.player();
      if (playerentity == null) {
         this.update11();
         this.set.clear();
      } else {
         this.onPlayerEntity2(playerentity);
         this.setPlayerEntity(playerentity);
      }
   }

   private void update11() {
      this.item = null;
      this.itemStack = null;
      this.value235 = 0;
      this.value236 = 0;
   }

   private void setPlayerEntity(PlayerEntity playerEntity) {
      if (this.item != null) {
         Item itemx = this.item;
         if (getIntByItemPlayerEntity(itemx, playerEntity) < this.value235) {
            ItemStack itemstack = this.itemStack;
            this.onItemStackPlayerEntity(itemstack, playerEntity);
            this.update11();
         } else {
            if (--this.value236 <= 0) {
               this.update11();
            }
         }
      }
   }

   private void onPlayerEntity2(PlayerEntity playerEntity) {
      if (!this.set.isEmpty()) {
         ItemCooldownManager itemcooldownmanager = playerEntity.getItemCooldownManager();
         boolean flag = this.onlyVPvp.isFlag3() && !PvpStateParser.check2();
         this.set.removeIf(p0 -> PotionCooldown.isItemCooldownManagerBooleanItemStack(itemcooldownmanager, flag, p0));
      }
   }

   @Override
   public void onDisable() {
      this.update11();
      this.update12();
   }

   private static Hand getHandByPlayerEntity(PlayerEntity playerEntity) {
      if (isItemStack(playerEntity.getStackInHand(Hand.MAIN_HAND))) {
         return Hand.MAIN_HAND;
      } else {
         return isItemStack(playerEntity.getStackInHand(Hand.OFF_HAND)) ? Hand.OFF_HAND : null;
      }
   }

   public static boolean isItemStack(ItemStack itemStack) {
      if (!itemStack.isEmpty() && itemStack.getItem() instanceof PotionItem) {
         PotionContentsComponent potioncontentscomponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
         if (potioncontentscomponent == null) {
            return false;
         } else {
            if (potioncontentscomponent.potion().isPresent()) {
               RegistryEntry registryentry = (RegistryEntry)potioncontentscomponent.potion().get();
               if (isIterable(((Potion)registryentry.value()).getEffects())) {
                  return true;
               }
            }

            return isIterable(potioncontentscomponent.customEffects());
         }
      } else {
         return false;
      }
   }

   private static boolean isIterable(Iterable<StatusEffectInstance> iterable) {
      for (StatusEffectInstance statuseffectinstance : iterable) {
         if (statuseffectinstance.getEffectType() == StatusEffects.INSTANT_HEALTH) {
            return true;
         }
      }

      return false;
   }

   private static boolean isItemCooldownManagerBooleanItemStack(ItemCooldownManager itemCooldownManager, boolean flag, ItemStack itemStack) {
      if (!itemCooldownManager.isCoolingDown(itemStack)) {
         return true;
      } else if (flag) {
         itemCooldownManager.set(itemStack, 0);
         return true;
      } else {
         return false;
      }
   }

   private static ItemStack getItemStackByItemStack(ItemStack itemStack) {
      ItemStack itemstack = itemStack.copy();
      itemstack.set(DataComponentTypes.USE_COOLDOWN, new UseCooldownComponent(1.0F, Optional.of(identifier)));
      return itemstack;
   }

   private void onItemStackPlayerEntity(ItemStack itemStack, PlayerEntity playerEntity) {
      int i = Math.max(1, (int)(this.cooldown.getValue() * 20.0));
      ItemStack itemstack = getItemStackByItemStack(itemStack);
      playerEntity.getItemCooldownManager().set(itemstack, i);
      this.set.add(itemstack);
   }

   private static int getIntByItemPlayerEntity(Item item2, PlayerEntity playerEntity) {
      int i = 0;

      for (int j = 0; j < playerEntity.getInventory().size(); j++) {
         ItemStack itemstack = playerEntity.getInventory().getStack(j);
         if (itemstack.getItem() == item2) {
            i += itemstack.getCount();
         }
      }

      ItemStack itemstack1 = playerEntity.getOffHandStack();
      if (itemstack1.getItem() == item2) {
         i += itemstack1.getCount();
      }

      return i;
   }

   private void update12() {
      if (!this.set.isEmpty()) {
         PlayerEntity playerentity = this.player();
         if (playerentity != null) {
            ItemCooldownManager itemcooldownmanager = playerentity.getItemCooldownManager();

            for (ItemStack itemstack : this.set) {
               itemcooldownmanager.set(itemstack, 0);
            }
         }

         this.set.clear();
      }
   }

   @Override
   public void onAttackEvent(AttackEvent attackEvent) {
      PlayerEntity playerentity = this.player();
      if (playerentity != null) {
         Hand hand = getHandByPlayerEntity(playerentity);
         if (hand != null) {
            if (!this.onlyVPvp.isFlag3() || PvpStateParser.check2()) {
               ItemStack itemstack = playerentity.getStackInHand(hand);
               ItemStack itemstack1 = getItemStackByItemStack(itemstack);
               if (playerentity.getItemCooldownManager().isCoolingDown(itemstack1)) {
                  attackEvent.setFlag(true);
               } else {
                  this.item = itemstack.getItem();
                  this.itemStack = itemstack1;
                  Item itemx = this.item;
                  this.value235 = getIntByItemPlayerEntity(itemx, playerentity);
                  this.value236 = 30;
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
      this.update11();
   }
}
