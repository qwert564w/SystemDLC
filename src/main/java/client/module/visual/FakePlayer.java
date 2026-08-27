package client.module.visual;

import client.module.Category;
import client.module.Module;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.Entity.RemovalReason;

public class FakePlayer extends Module {
   private OtherClientPlayerEntity otherClientPlayerEntity;

   public FakePlayer() {
      super("FakePlayer", Category.VISUAL);
   }

   @Override
   public void onDisable() {
      if (this.inGame() && this.otherClientPlayerEntity != null) {
         this.clientWorld().removeEntity(this.otherClientPlayerEntity.getId(), RemovalReason.KILLED);
         this.otherClientPlayerEntity = null;
      }
   }

   @Override
   public void update3() {
      this.setEnabled(false);
   }

   @Override
   public void update4() {
      this.setEnabled(false);
   }

   @Override
   public void onEnable() {
      if (this.inGame()) {
         this.otherClientPlayerEntity = new OtherClientPlayerEntity(this.clientWorld(), new GameProfile(UUID.randomUUID(), "System"));
         this.otherClientPlayerEntity.setPosition(this.player().getPos());
         this.otherClientPlayerEntity.setYaw(this.player().getYaw());
         this.otherClientPlayerEntity.setPitch(this.player().getPitch());
         this.otherClientPlayerEntity.bodyYaw = this.player().bodyYaw;
         this.otherClientPlayerEntity.headYaw = this.player().headYaw;

         for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
            this.otherClientPlayerEntity.equipStack(equipmentslot, this.player().getEquippedStack(equipmentslot).copy());
         }

         this.clientWorld().addEntity(this.otherClientPlayerEntity);
      }
   }
}
