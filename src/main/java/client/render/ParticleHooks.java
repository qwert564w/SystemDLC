package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.visual.NoRender;
import client.util.UnsafeAccess;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

@HookClass(ParticleManager.class)
public class ParticleHooks {
   private static final UnsafeAccess<NoRender> unsafeAccess = new UnsafeAccess<>(NoRender.class);

   @Hook(
      method = "method_3056",
      desc = "(Lnet/minecraft/class_2394;DDDDDD)Lnet/minecraft/class_703;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static Particle getParticleByParticleManagerParticleEffectDoubleDoubleDoubleDoubleDoubleDouble(
      ParticleManager particleManager, ParticleEffect particleEffect, double value, double value2, double value3, double value4, double value5, double value6
   ) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      if (norender != null) {
         ParticleType particletype = particleEffect.getType();
         if (norender.check16() && (particletype == ParticleTypes.EXPLOSION || particletype == ParticleTypes.EXPLOSION_EMITTER)) {
            return null;
         }

         if (norender.check21()
            && (particletype == ParticleTypes.BLOCK || particletype == ParticleTypes.BLOCK_MARKER || particletype == ParticleTypes.BLOCK_CRUMBLE)) {
            return null;
         }

         if (norender.check8()
            && (
               particletype == ParticleTypes.BUBBLE_COLUMN_UP
                  || particletype == ParticleTypes.BUBBLE
                  || particletype == ParticleTypes.BUBBLE_POP
                  || particletype == ParticleTypes.CURRENT_DOWN
                  || particletype == ParticleTypes.UNDERWATER
                  || particletype == ParticleTypes.SPLASH
                  || particletype == ParticleTypes.FISHING
            )) {
            return null;
         }
      }

      return (Particle)HandleInvoker.getObjectByObjectArray2(particleManager, particleEffect, value, value2, value3, value4, value5, value6);
   }

   @Hook(
      method = "method_3054",
      desc = "(Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isParticleManagerBlockPosDirection(ParticleManager particleManager, BlockPos blockPos, Direction direction) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender == null || !norender.check21();
   }

   @Hook(
      method = "method_3046",
      desc = "(Lnet/minecraft/class_2338;Lnet/minecraft/class_2680;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isParticleManagerBlockPosBlockState(ParticleManager particleManager, BlockPos blockPos, BlockState blockState) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender == null || !norender.check21();
   }
}
