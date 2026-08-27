package client.util;

import client.module.Feature;
import client.render.WorldRenderContext;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LimbAnimator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class FakePlayerCopy {
   private OtherClientPlayerEntity otherClientPlayerEntity;
   private Vec3d vec3d;
   private float value;
   private float value2;
   private float value3;
   private float value4;
   private boolean flag;
   private boolean flag2;
   private float value5;
   private float value6;
   private float value7;
   private float value8;
   private boolean flag3;
   private int value9;
   private Hand hand;
   private float value10;
   private EntityPose entityPose;
   private int value11;
   private UnsafeFields<Float> unsafeFields;
   private UnsafeFields<Float> unsafeFields2;
   private UnsafeFields<Float> unsafeFields3;
   private UnsafeFields<Float> unsafeFields4;
   private UnsafeFields<Float> unsafeFields5;
   private UnsafeFields<Float> unsafeFields6;

   public void update() {
      this.otherClientPlayerEntity = null;
      this.vec3d = null;
   }

   private void onAbstractClientPlayerEntity(AbstractClientPlayerEntity abstractClientPlayerEntity) {
      if (this.otherClientPlayerEntity != null) {
         for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
            this.otherClientPlayerEntity.equipStack(equipmentslot, abstractClientPlayerEntity.getEquippedStack(equipmentslot).copy());
         }
      }
   }

   public void render(WorldRenderContext worldRenderContext) {
      if (this.otherClientPlayerEntity != null && this.vec3d != null && Feature.mc.world != null) {
         MatrixStack matrixstack = worldRenderContext.getMatrixStack();
         Vec3d vec3dx = worldRenderContext.getCamera().getPos();
         float f = 0.0F;
         this.update2();
         double d0 = this.vec3d.x - vec3dx.x;
         double d1 = this.vec3d.y - vec3dx.y;
         double d2 = this.vec3d.z - vec3dx.z;
         EntityRenderDispatcher entityrenderdispatcher = Feature.mc.getEntityRenderDispatcher();
         Immediate immediate = Feature.mc.getBufferBuilders().getEntityVertexConsumers();
         int i = entityrenderdispatcher.getLight(this.otherClientPlayerEntity, f);
         entityrenderdispatcher.render(this.otherClientPlayerEntity, d0, d1, d2, f, matrixstack, immediate, i);
         immediate.draw();
      }
   }

   private void setLimbAnimator(LimbAnimator limbAnimator) {
      this.unsafeFields = new UnsafeFields<>(limbAnimator, LimbAnimator.class, 0);
      this.unsafeFields2 = new UnsafeFields<>(limbAnimator, LimbAnimator.class, 1);
      this.unsafeFields3 = new UnsafeFields<>(limbAnimator, LimbAnimator.class, 2);
      this.unsafeFields4 = new UnsafeFields<>(limbAnimator, LimbAnimator.class, 3);
   }

   public boolean check() {
      return this.otherClientPlayerEntity != null && this.vec3d != null;
   }

   public Vec3d getVec3d() {
      return this.vec3d;
   }

   public OtherClientPlayerEntity getOtherClientPlayerEntity() {
      return this.otherClientPlayerEntity;
   }

   public void setAbstractClientPlayerEntity(AbstractClientPlayerEntity abstractClientPlayerEntity) {
      if (abstractClientPlayerEntity != null && Feature.mc.world != null) {
         this.vec3d = abstractClientPlayerEntity.getPos();
         this.value = abstractClientPlayerEntity.getYaw();
         this.value2 = abstractClientPlayerEntity.getPitch();
         this.value3 = abstractClientPlayerEntity.bodyYaw;
         this.value4 = abstractClientPlayerEntity.headYaw;
         this.flag = abstractClientPlayerEntity.isSneaking();
         this.flag2 = abstractClientPlayerEntity.isSprinting();
         this.setLimbAnimator(abstractClientPlayerEntity.limbAnimator);
         this.setLivingEntity(abstractClientPlayerEntity);
         this.value6 = abstractClientPlayerEntity.limbAnimator.getSpeed();

         try {
            this.value5 = this.unsafeFields3.getFloat();
            this.value7 = this.unsafeFields4.getFloat();
         } catch (Exception exception1) {
            this.value5 = 0.0F;
            this.value7 = 1.0F;
         }

         this.value8 = abstractClientPlayerEntity.handSwingProgress;
         this.flag3 = abstractClientPlayerEntity.handSwinging;
         this.value9 = abstractClientPlayerEntity.handSwingTicks;
         this.hand = abstractClientPlayerEntity.getActiveHand();

         try {
            this.value10 = this.unsafeFields5.getFloat();
         } catch (Exception exception) {
            this.value10 = 0.0F;
         }

         this.entityPose = abstractClientPlayerEntity.getPose();
         this.value11 = abstractClientPlayerEntity.age;
         ClientWorld clientworld = Feature.mc.world;
         this.onClientWorldAbstractClientPlayerEntity(clientworld, abstractClientPlayerEntity);
      }
   }

   private void onClientWorldAbstractClientPlayerEntity(ClientWorld clientWorld, AbstractClientPlayerEntity abstractClientPlayerEntity) {
      if (this.otherClientPlayerEntity == null) {
         GameProfile gameprofile = abstractClientPlayerEntity.getGameProfile();
         this.otherClientPlayerEntity = new OtherClientPlayerEntity(clientWorld, gameprofile);
         this.update2();
         this.onAbstractClientPlayerEntity(abstractClientPlayerEntity);
      }
   }

   private void update2() {
      if (this.otherClientPlayerEntity != null) {
         this.otherClientPlayerEntity.setPosition(this.vec3d);
         this.otherClientPlayerEntity.prevX = this.vec3d.x;
         this.otherClientPlayerEntity.prevY = this.vec3d.y;
         this.otherClientPlayerEntity.prevZ = this.vec3d.z;
         this.otherClientPlayerEntity.lastRenderX = this.vec3d.x;
         this.otherClientPlayerEntity.lastRenderY = this.vec3d.y;
         this.otherClientPlayerEntity.lastRenderZ = this.vec3d.z;
         this.otherClientPlayerEntity.setYaw(this.value);
         this.otherClientPlayerEntity.setPitch(this.value2);
         this.otherClientPlayerEntity.prevYaw = this.value;
         this.otherClientPlayerEntity.prevPitch = this.value2;
         this.otherClientPlayerEntity.bodyYaw = this.value3;
         this.otherClientPlayerEntity.prevBodyYaw = this.value3;
         this.otherClientPlayerEntity.headYaw = this.value4;
         this.otherClientPlayerEntity.prevHeadYaw = this.value4;
         this.otherClientPlayerEntity.setSneaking(this.flag);
         this.otherClientPlayerEntity.setSprinting(this.flag2);
         this.otherClientPlayerEntity.setPose(this.entityPose);
         this.otherClientPlayerEntity.age = this.value11;
         this.setLimbAnimator(this.otherClientPlayerEntity.limbAnimator);
         this.setLivingEntity(this.otherClientPlayerEntity);

         try {
            float f = this.value5 + this.value6;
            this.unsafeFields3.onFloat(f);
            this.unsafeFields2.onFloat(this.value6);
            this.unsafeFields.onFloat(this.value6);
            this.unsafeFields4.onFloat(this.value7);
         } catch (Exception exception1) {
            exception1.printStackTrace();
         }

         this.otherClientPlayerEntity.handSwingProgress = this.value8;
         this.otherClientPlayerEntity.lastHandSwingProgress = this.value8;
         this.otherClientPlayerEntity.handSwinging = this.flag3;
         this.otherClientPlayerEntity.handSwingTicks = this.value9;
         this.otherClientPlayerEntity.preferredHand = this.hand;
         this.otherClientPlayerEntity.clearActiveItem();

         try {
            this.unsafeFields5.onFloat(this.value10);
            this.unsafeFields6.onFloat(this.value10);
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }
   }

   private void setLivingEntity(LivingEntity livingEntity) {
      this.unsafeFields5 = new UnsafeFields<>(livingEntity, LivingEntity.class, 103);
      this.unsafeFields6 = new UnsafeFields<>(livingEntity, LivingEntity.class, 104);
   }
}
