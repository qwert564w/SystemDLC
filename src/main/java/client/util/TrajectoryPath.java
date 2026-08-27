package client.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.Vec3d;

public final class TrajectoryPath {
   public final List<Vec3d> list = new ArrayList<>();
   public Vec3d vec3d = null;
   public Vec3d vec3d2 = new Vec3d(0.0, 1.0, 0.0);
   public float value = 0.0F;
   public boolean flag = false;
   public boolean flag2 = false;

   public TrajectoryPath() {
   }

   public TrajectoryPath getTrajectoryPath() {
      TrajectoryPath trajectorypath = new TrajectoryPath();
      trajectorypath.list.addAll(this.list);
      trajectorypath.vec3d = this.vec3d;
      trajectorypath.vec3d2 = this.vec3d2;
      trajectorypath.value = this.value;
      trajectorypath.flag = this.flag;
      trajectorypath.flag2 = this.flag2;
      return trajectorypath;
   }
}
