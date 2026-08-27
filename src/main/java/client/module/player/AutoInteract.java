package client.module.player;

import client.enums.AutoInteractState;
import client.module.Category;
import client.module.Module;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.SphereItems;
import client.util.StringParts;
import java.util.Arrays;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;

public class AutoInteract extends Module {
   private MultilistSetting funkcii;
   private SliderSetting kdInviza;
   private SliderSetting minEdy;
   private AutoInteractState autoInteractState;
   private int value235;
   private int value236;
   private int value237;
   private boolean flag;
   private int value238;
   private int value239;

   public AutoInteract() {
      super("AutoInteract", Category.PLAYER);
      MultilistSetting multilistsetting = new MultilistSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"A", "в", "т", "о", " ", "и", "н", "в", "и", "з"}),
            StringParts.join(new String[]{"A", "в", "т", "о", " ", "e", "д", "а"})
         ),
         Arrays.asList(
            StringParts.join(new String[]{"A", "в", "т", "о", " ", "и", "н", "в", "и", "з"}),
            StringParts.join(new String[]{"A", "в", "т", "о", " ", "e", "д", "а"})
         )
      );
      multilistsetting.setName("Функции");
      multilistsetting.setDescription("Что автоматизировать");
      this.funkcii = multilistsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 10.0, 1.0, 60.0, 1.0, StringParts.join(new String[]{" ", "c", "е", "к"}), 0);
      slidersetting.setName("Кд инвиза");
      slidersetting.setDescription("За сколько секунд до конца эффекта пить новое зелье");
      this.kdInviza = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 16.0, 1.0, 19.0, 1.0);
      slidersetting1.setName("Мин. еды");
      slidersetting1.setDescription("Минимальный уровень еды для автоеды");
      this.minEdy = slidersetting1;
      this.autoInteractState = AutoInteractState.IDLE;
      this.addSettings(new Setting[]{this.funkcii, this.kdInviza, this.minEdy});
      this.kdInviza.setVisibleWhen(this::getBoolean);
      this.minEdy.setVisibleWhen(this::getBoolean2);
   }

   private Boolean getBoolean() {
      return this.funkcii.isString("Авто инвиз");
   }

   private boolean check3() {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      if (clientplayerentity.getHungerManager().getFoodLevel() > this.minEdy.getInt2()) {
         return false;
      } else {
         int i = this.getInt();
         if (i == -1) {
            return false;
         } else {
            this.setInt(i);
            return true;
         }
      }
   }

   private void update11() {
      if (!this.funkcii.isString("Авто инвиз") || !this.check4()) {
         if (this.funkcii.isString("Авто еда")) {
            this.check3();
         }
      }
   }

   private int getInt() {
      for (int i = 0; i < 36; i++) {
         ItemStack itemstack = this.inventory().getStack(i);
         if (isItemStack(itemstack)) {
            return getIntByInt(i);
         }
      }

      return -1;
   }

   private static boolean isItemStack(ItemStack itemStack) {
      return itemStack.isEmpty() ? false : itemStack.get(DataComponentTypes.FOOD) != null;
   }

   private void update12() {
      this.autoInteractState = AutoInteractState.SELECT;
   }

   @Override
   public void onDisable() {
      if (this.autoInteractState != AutoInteractState.IDLE) {
         this.options().useKey.setPressed(false);
         if (this.value236 >= 0) {
            this.inventory().selectedSlot = this.value236;
         }
      }

      this.update14();
   }

   private void update13() {
      int i = this.flag ? 8 : this.value237 - 36;
      this.inventory().selectedSlot = i;
      this.options().useKey.setPressed(true);
      this.autoInteractState = AutoInteractState.HOLD;
      this.value238 = 0;
   }

   private int getInt2() {
      for (int i = 0; i < 36; i++) {
         ItemStack itemstack = this.inventory().getStack(i);
         if (isItemStack2(itemstack)) {
            return getIntByInt(i);
         }
      }

      return -1;
   }

   private static int getIntByInt(int count) {
      return SphereItems.getIntByInt(count);
   }

   private Boolean getBoolean2() {
      return this.funkcii.isString("Авто еда");
   }

   private static boolean isItemStack2(ItemStack itemStack) {
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
         if (statuseffectinstance.getEffectType() == StatusEffects.INVISIBILITY) {
            return true;
         }
      }

      return false;
   }

   private void update14() {
      this.autoInteractState = AutoInteractState.IDLE;
      this.value235 = 0;
      this.value236 = -1;
      this.value237 = -1;
      this.flag = false;
      this.value238 = 0;
      this.value239 = 0;
   }

   private boolean check4() {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      StatusEffectInstance statuseffectinstance = clientplayerentity.getStatusEffect(StatusEffects.INVISIBILITY);
      int i = this.kdInviza.getInt2() * 20;
      if (statuseffectinstance != null && statuseffectinstance.getDuration() > i) {
         return false;
      } else {
         int j = this.getInt2();
         if (j == -1) {
            return false;
         } else {
            this.setInt(j);
            return true;
         }
      }
   }

   private void setInt(int count) {
      this.value236 = this.inventory().selectedSlot;
      this.value237 = count;
      this.flag = !SphereItems.isInt2(count);
      if (this.flag) {
         SphereItems.onIntInt4(count, 8);
         this.autoInteractState = AutoInteractState.SWAP_IN;
         this.value235 = 1;
      } else {
         this.autoInteractState = AutoInteractState.SELECT;
         this.value235 = 0;
      }
   }

   private void update15() {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      this.value238++;
      boolean flagx = clientplayerentity.isUsingItem();
      int i = clientplayerentity.getItemUseTime();
      ItemStack itemstack = clientplayerentity.getActiveItem();
      int j = itemstack.isEmpty() ? 32 : itemstack.getMaxUseTime(clientplayerentity);
      if (flagx && i >= j) {
         this.autoInteractState = AutoInteractState.RELEASE;
      } else if (!flagx && this.value238 > 4) {
         this.autoInteractState = AutoInteractState.RELEASE;
      } else {
         if (this.value238 >= 60) {
            this.autoInteractState = AutoInteractState.RELEASE;
         }
      }
   }

   @Override
   public void onEnable() {
      this.update14();
   }

   private void update16() {
      if (--this.value239 <= 0) {
         this.update14();
      }
   }

   @Override
   public void update8() {
      if (!this.notInGame()) {
         if (this.currentScreen() == null) {
            if (this.value235 > 0) {
               this.value235--;
            } else {
               switch (this.autoInteractState) {
                  case IDLE:
                     this.update11();
                     break;
                  case SWAP_IN:
                     this.update12();
                     break;
                  case SELECT:
                     this.update13();
                     break;
                  case HOLD:
                     this.update15();
                     break;
                  case RELEASE:
                     this.update17();
                     break;
                  case POST_COOLDOWN:
                     this.update16();
               }
            }
         }
      }
   }

   private void update17() {
      this.options().useKey.setPressed(false);
      if (this.flag) {
         SphereItems.onIntInt4(this.value237, 8);
      }

      if (this.value236 >= 0) {
         this.inventory().selectedSlot = this.value236;
      }

      this.autoInteractState = AutoInteractState.POST_COOLDOWN;
      this.value239 = 20;
   }
}
