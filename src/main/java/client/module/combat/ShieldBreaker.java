package client.module.combat;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.CritChecks;
import client.util.ShieldState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ShieldBreaker extends Module {
   private BooleanSetting onlyPlayers;
   private SliderSetting timeUderzhaniya;
   private SliderSetting dalnostObnaruzheniya;
   private final ShieldState shieldState;
   private boolean flag;

   public ShieldBreaker() {
      super("ShieldBreaker", Category.COMBAT);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Только игроки");
      booleansetting.setDescription("Реагировать только на игроков, a не на мобов");
      this.onlyPlayers = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 200.0, 200.0, 1000.0, 10.0);
      slidersetting.setName("Время удержания");
      slidersetting.setDescription("Время удержания топора после атаки (в мс)");
      this.timeUderzhaniya = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 3.4, 2.0, 6.0, 0.1);
      slidersetting1.setName("Дальность обнаружения");
      slidersetting1.setDescription("Максимальное расстояние для обнаружения щита");
      this.dalnostObnaruzheniya = slidersetting1;
      this.shieldState = new ShieldState();
      this.flag = false;
      this.addSettings(new Setting[]{this.onlyPlayers, this.timeUderzhaniya, this.dalnostObnaruzheniya});
   }

   @Override
   public void onDisable() {
      this.shieldState.update2();
      this.flag = false;
   }

   private LivingEntity getLivingEntity() {
      ClientPlayerEntity clientplayerentity = this.clientPlayer();
      return clientplayerentity.getWorld()
         .getEntitiesByClass(LivingEntity.class, clientplayerentity.getBoundingBox().expand(this.dalnostObnaruzheniya.getValue()), this::isLivingEntity)
         .stream()
         .filter(p0 -> this.isClientPlayerEntityLivingEntity(clientplayerentity, p0))
         .filter(CritChecks::isLivingEntity)
         .min((a, b) -> ShieldBreaker.getIntByClientPlayerEntityLivingEntityLivingEntity(clientplayerentity, a, b))
         .orElse(null);
   }

   private boolean isLivingEntity(LivingEntity livingEntity) {
      return livingEntity != this.clientPlayer() && livingEntity.isAlive() && !livingEntity.isRemoved() && livingEntity.canHit()
         ? !this.onlyPlayers.isFlag3() || livingEntity instanceof PlayerEntity
         : false;
   }

   private static int getIntByClientPlayerEntityLivingEntityLivingEntity(ClientPlayerEntity clientPlayerEntity, LivingEntity livingEntity, LivingEntity livingEntity2) {
      return Float.compare(clientPlayerEntity.distanceTo(livingEntity), clientPlayerEntity.distanceTo(livingEntity2));
   }

   private boolean isClientPlayerEntityLivingEntity(ClientPlayerEntity clientPlayerEntity, LivingEntity livingEntity) {
      return clientPlayerEntity.distanceTo(livingEntity) <= this.dalnostObnaruzheniya.getValue();
   }

   private void update11() {
      boolean flagx = this.options().attackKey.isPressed();
      if (flagx && !this.flag && !this.shieldState.isFlag()) {
         LivingEntity livingentity = this.getLivingEntity();
         if (livingentity != null) {
            this.shieldState.setLivingEntity(livingentity);
         }
      }

      this.flag = flagx;
   }

   @Override
   public void onEnable() {
      this.shieldState.update();
      this.flag = false;
   }

   @Override
   public void update8() {
      if (!this.inGame()) {
         if (this.shieldState.isFlag()) {
            this.shieldState.update2();
         }
      } else {
         this.update11();
         this.shieldState.onDouble(this.timeUderzhaniya.getValue());
      }
   }
}
