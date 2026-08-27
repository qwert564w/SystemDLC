package client.module.player;

import client.module.Category;
import client.module.Module;
import client.render.WorldRenderContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.Box;

public class NoArmorStand extends Module {
   public NoArmorStand() {
      super("NoArmorStand", Category.PLAYER);
   }

   @Override
   public void render2(WorldRenderContext worldRenderContext) {
      for (Entity entity : worldRenderContext.getClientWorld().getEntities()) {
         if (entity instanceof ArmorStandEntity) {
            entity.setBoundingBox(
               new Box(
                  entity.getX() - 0.0,
                  entity.getBoundingBox().minY,
                  entity.getZ() - 0.0,
                  entity.getX() + 0.0,
                  entity.getBoundingBox().maxY,
                  entity.getZ() + 0.0
               )
            );
         }
      }
   }

   @Override
   public void render3(WorldRenderContext worldRenderContext) {
      for (Entity entity : worldRenderContext.getClientWorld().getEntities()) {
         if (entity instanceof ArmorStandEntity) {
            entity.setBoundingBox(
               new Box(
                  entity.getX() - 0.25,
                  entity.getBoundingBox().minY,
                  entity.getZ() - 0.25,
                  entity.getX() + 0.25,
                  entity.getBoundingBox().maxY,
                  entity.getZ() + 0.25
               )
            );
         }
      }
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onEnable() {
   }
}
