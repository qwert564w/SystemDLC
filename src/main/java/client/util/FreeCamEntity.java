package client.util;

import client.module.Feature;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker.Builder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FreeCamEntity extends Entity {
   private double value;

   public FreeCamEntity(World world2, Vec3d vec3d, float value2, float value3, float value4) {
      super(EntityType.MARKER, world2);
      this.setPosition(vec3d);
      this.setYaw(value2);
      this.setPitch(value3);
      this.value = value4;
      this.noClip = true;
      this.setInvisible(true);
   }

   public void update() {
      if (Feature.mc.options != null && Feature.mc.getWindow() != null) {
         long i = Feature.mc.getWindow().getHandle();
         Vec3d vec3d = Vec3d.ZERO;
         double d0 = this.value;
         float f = this.getYaw();
         float f1 = this.getPitch();
         double d1 = Math.toRadians(f);
         double d2 = Math.toRadians(f1);
         Vec3d vec3d1 = new Vec3d(-Math.sin(d1) * Math.cos(d2), -Math.sin(d2), Math.cos(d1) * Math.cos(d2));
         Vec3d vec3d2 = new Vec3d(Math.cos(d1), 0.0, Math.sin(d1));
         Vec3d vec3d3 = new Vec3d(0.0, 1.0, 0.0);
         KeyBinding keybinding = Feature.mc.options.forwardKey;
         if (isKeyBindingLong(keybinding, i)) {
            vec3d = vec3d.add(vec3d1.multiply(d0));
         }

         KeyBinding keybinding1 = Feature.mc.options.backKey;
         if (isKeyBindingLong(keybinding1, i)) {
            vec3d = vec3d.add(vec3d1.multiply(-d0));
         }

         KeyBinding keybinding2 = Feature.mc.options.leftKey;
         if (isKeyBindingLong(keybinding2, i)) {
            vec3d = vec3d.add(vec3d2.multiply(d0));
         }

         KeyBinding keybinding3 = Feature.mc.options.rightKey;
         if (isKeyBindingLong(keybinding3, i)) {
            vec3d = vec3d.add(vec3d2.multiply(-d0));
         }

         KeyBinding keybinding4 = Feature.mc.options.jumpKey;
         if (isKeyBindingLong(keybinding4, i)) {
            vec3d = vec3d.add(vec3d3.multiply(d0));
         }

         KeyBinding keybinding5 = Feature.mc.options.sneakKey;
         if (isKeyBindingLong(keybinding5, i)) {
            vec3d = vec3d.add(vec3d3.multiply(-d0));
         }

         if (!vec3d.equals(Vec3d.ZERO)) {
            Vec3d vec3d4 = this.getPos().add(vec3d);
            this.setPosition(vec3d4);
         }
      }
   }

   private static boolean isKeyBindingLong(KeyBinding keyBinding, long time) {
      Key key;
      try {
         key = InputUtil.fromTranslationKey(keyBinding.getBoundKeyTranslationKey());
      } catch (Throwable throwable) {
         key = keyBinding.getDefaultKey();
      }

      if (key.getCategory() != Type.KEYSYM) {
         return false;
      } else {
         return key.getCode() == InputUtil.UNKNOWN_KEY.getCode() ? false : InputUtil.isKeyPressed(time, key.getCode());
      }
   }

   public void setValueAsFloat(float value2) {
      this.value = value2;
   }

   protected void initDataTracker(Builder builder) {
   }

   public void tick() {
      super.tick();
      if (Feature.mc.player != null) {
         this.update();
      }
   }

   protected void readCustomDataFromNbt(NbtCompound nbtCompound) {
   }

   public boolean shouldRender(double value) {
      return false;
   }

   protected void writeCustomDataToNbt(NbtCompound nbtCompound) {
   }

   public boolean isInvisible() {
      return true;
   }

   public boolean canMoveVoluntarily() {
      return false;
   }

   public boolean damage(ServerWorld serverWorld, DamageSource damageSource, float value) {
      return false;
   }
}
