package client.module.movement;

import client.module.Category;
import client.module.Module;
import client.setting.SliderSetting;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class NoWeb extends Module {
   private SliderSetting speed;

   public NoWeb() {
      super("NoWeb", Category.MOVEMENT);
      SliderSetting slidersetting = new SliderSetting("", "", 0.6, 0.0, 1.0, 0.05, "", 1);
      slidersetting.setName("Скорость");
      slidersetting.setDescription("Скорость перемещения");
      this.speed = slidersetting;
      this.addSetting(this.speed);
   }

   @Override
   public void onTick() {
      if (this.inGame() && this.check3()) {
         double[] adouble = this.getDoubleArrayByDouble(this.speed.getValue4());
         this.player().setVelocity(adouble[0], 0.0, adouble[1]);
         if (this.options().jumpKey.isPressed()) {
            this.player().setVelocity(this.player().getVelocity().add(0.0, this.speed.getValue4(), 0.0));
         }

         if (this.options().sneakKey.isPressed()) {
            this.player().setVelocity(this.player().getVelocity().add(0.0, -this.speed.getValue4(), 0.0));
         }
      }
   }

   @Override
   public void onDisable() {
   }

   public boolean check3() {
      Box box = this.player().getBoundingBox();
      BlockPos blockpos = BlockPos.ofFloored(this.player().getPos());

      for (int i = blockpos.getX() - 2; i <= blockpos.getX() + 2; i++) {
         for (int j = blockpos.getY() - 1; j <= blockpos.getY() + 4; j++) {
            for (int k = blockpos.getZ() - 2; k <= blockpos.getZ() + 2; k++) {
               BlockPos blockpos1 = new BlockPos(i, j, k);
               if (box.intersects(new Box(blockpos1)) && this.world().getBlockState(blockpos1).getBlock() == Blocks.COBWEB) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public double[] getDoubleArrayByDouble(double value) {
      float f = this.clientPlayer().input.movementForward;
      float f1 = this.clientPlayer().input.movementSideways;
      float f2 = this.clientPlayer().getYaw();
      if (f != 0.0F) {
         if (f1 > 0.0F) {
            f2 += f > 0.0F ? -45.0F : 45.0F;
         } else if (f1 < 0.0F) {
            f2 += f > 0.0F ? 45.0F : -45.0F;
         }

         f1 = 0.0F;
         if (f > 0.0F) {
            f = 1.0F;
         } else if (f < 0.0F) {
            f = -1.0F;
         }
      }

      double d0 = Math.sin(Math.toRadians(f2 + 90.0F));
      double d1 = Math.cos(Math.toRadians(f2 + 90.0F));
      double d2 = f * value * d1 + f1 * value * d0;
      double d3 = f * value * d0 - f1 * value * d1;
      return new double[]{d2, d3};
   }

   @Override
   public void onEnable() {
   }
}
