package client.module.movement;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Speed extends Module {
   private SliderSetting range;
   private SliderSetting speed;
   private SliderSetting rasshirenie;
   private BooleanSetting onlyPlayers;
   private BooleanSetting trebuetsyaDvizhenie;

   public Speed() {
      super("Speed", Category.MOVEMENT);
      SliderSetting slidersetting = new SliderSetting("", "", 2.5, 1.0, 4.0, 0.1, "", 1);
      slidersetting.setName("Дистанция");
      slidersetting.setDescription("Дистанция до цели");
      this.range = slidersetting;
      slidersetting = new SliderSetting("", "", 3.0, 1.0, 10.0, 0.1, "", 1);
      slidersetting.setName("Скорость");
      slidersetting.setDescription("Скорость перемещения");
      this.speed = slidersetting;
      slidersetting = new SliderSetting("", "", 0.5, 0.1, 2.0, 0.1, "", 1);
      slidersetting.setName("Расширение");
      slidersetting.setDescription("Расширение хитбокса");
      this.rasshirenie = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Только игроки");
      booleansetting.setDescription("Атаковать только игроков, не мобов");
      this.onlyPlayers = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Требуется движение");
      booleansetting1.setDescription("Работать только когда игрок двигается");
      this.trebuetsyaDvizhenie = booleansetting1;
      this.addSettings(new Setting[]{this.range, this.speed, this.rasshirenie, this.onlyPlayers, this.trebuetsyaDvizhenie});
   }

   @Override
   public void onTick() {
      if (!this.notInGame()) {
         this.update11();
      }
   }

   @Override
   public void onDisable() {
   }

   private boolean check3() {
      return this.clientPlayer().input.movementForward != 0.0 || this.clientPlayer().input.movementSideways != 0.0;
   }

   private double[] getDoubleArrayByVec3dDoubleVec3d(Vec3d vec3d, double value, Vec3d vec3d2) {
      double d0 = vec3d.x - vec3d2.x;
      double d1 = vec3d.z - vec3d2.z;
      double d2 = Math.sqrt(d0 * d0 + d1 * d1);
      return d2 == 0.0 ? new double[]{0.0, 0.0} : new double[]{d0 / d2 * value, d1 / d2 * value};
   }

   private void update11() {
      if (!this.player().isOnGround()) {
         if (!this.trebuetsyaDvizhenie.isFlag3() || this.check3()) {
            int i = 0;
            Box box = this.player().getBoundingBox().expand(this.rasshirenie.getValue4());

            for (Entity entity : this.clientWorld().getEntities()) {
               if (entity != this.player()
                  && (!this.onlyPlayers.isFlag3() || entity instanceof PlayerEntity)
                  && (entity instanceof LivingEntity || entity instanceof BoatEntity)
                  && box.intersects(entity.getBoundingBox())) {
                  i++;
               }
            }

            if (i > 0) {
               double d3 = this.speed.getValue4() * 0.01 * i;
               Entity entity1 = null;
               double d0 = Double.MAX_VALUE;
               double d1 = this.range.getValue4() * this.range.getValue4();

               for (Entity entity2 : this.clientWorld().getEntities()) {
                  if (entity2 != this.player()
                     && (!this.onlyPlayers.isFlag3() || entity2 instanceof PlayerEntity)
                     && (entity2 instanceof LivingEntity || entity2 instanceof BoatEntity)) {
                     double d2 = this.player().squaredDistanceTo(entity2);
                     if (d2 <= d1 && d2 < d0) {
                        d0 = d2;
                        entity1 = entity2;
                     }
                  }
               }

               if (entity1 != null) {
                  Vec3d vec3d2 = this.player().getPos();
                  Vec3d vec3d1 = entity1.getPos();
                  Vec3d vec3d = vec3d2;
                  double[] adouble = this.getDoubleArrayByVec3dDoubleVec3d(vec3d1, d3, vec3d);
                  this.player().addVelocity(adouble[0], 0.0, adouble[1]);
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
   }
}
