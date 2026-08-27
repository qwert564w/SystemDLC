package client.module.render;

import client.api.Theme;
import client.module.Category;
import client.module.Module;
import client.module.client.StreamBypass;
import client.render.IconAtlas;
import client.render.ItemIconCache;
import client.render.RotationBuffer;
import client.render.ShapeShader;
import client.render.TextShader;
import client.render.WorldRenderContext;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.DistanceScale;
import client.util.ItemIcons;
import client.util.TimeFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class TntTimer extends Module {
   private SliderSetting scalePlashki;
   private static final ItemStack itemStack = new ItemStack(Items.TNT);
   private final Map<Integer, Float> map;
   private final Set<Integer> set;
   private long time;

   public TntTimer() {
      super("TntTimer", Category.RENDER);
      SliderSetting slidersetting = new SliderSetting("", "", 0.3, 0.1, 1.0, 0.1);
      slidersetting.setName("Масштаб плашки");
      slidersetting.setDescription("Масштаб плашки c таймером");
      this.scalePlashki = slidersetting;
      this.map = new HashMap<>();
      this.set = new HashSet<>();
      this.time = System.currentTimeMillis();
      this.addSettings(new Setting[]{this.scalePlashki});
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!StreamBypass.check6()) {
         if (!this.notInGame()) {
            MatrixStack matrixstack = worldRenderContext.getMatrixStack();
            Camera camera = worldRenderContext.getCamera();
            if (matrixstack != null) {
               Vec3d vec3d = camera.getPos();
               long i = System.currentTimeMillis();
               float f = Math.min((float)(i - this.time) / 1000.0F, 0.1F);
               this.time = i;
               RotationBuffer.setMinecraftClient2(this.client());
               ShapeShader.update2();
               this.set.clear();

               try {
                  for (Entity entity : this.clientWorld().getEntities()) {
                     if (entity instanceof TntEntity tntentity) {
                        int j = tntentity.getFuse();
                        double d0 = Math.max(0.0, j / 20.0);
                        String s = getStringByDouble(d0);
                        Vec3d vec3d1 = tntentity.getLerpedPos(worldRenderContext.getRenderTickCounter().getTickDelta(true)).subtract(vec3d);
                        double d1 = tntentity.getPos().distanceTo(vec3d);
                        this.set.add(tntentity.getId());
                        int k = tntentity.getId();
                        this.render(matrixstack, k, camera, d1, f, s, vec3d1);
                     }
                  }
               } finally {
                  ShapeShader.update();
                  RotationBuffer.setMinecraftClient(this.client());
               }

               this.map.keySet().retainAll(this.set);
            }
         }
      }
   }

   private static String getStringByDouble(double value) {
      return TimeFormat.getStringByDouble(value);
   }

   private void render(MatrixStack matrixStack, int count, Camera camera, double value, float value2, String text, Vec3d vec3d) {
      matrixStack.push();

      try {
         matrixStack.translate(vec3d.x, vec3d.y + 1.5, vec3d.z);
         RotationBuffer.render(matrixStack);
         float f = DistanceScale.getFloatByDoubleFloat(value, this.scalePlashki.getValueAsFloat());
         float f1 = this.map.getOrDefault(count, f);
         float f2 = f1 + (f - f1) * Math.min(1.0F, value2 * 10.0F);
         this.map.put(count, f2);
         matrixStack.scale(-f2, -f2, -f2);
         float f13 = 14.0F;
         IconAtlas iconatlas = icon;
         float f3 = TextShader.getFloatByFloatIconAtlasString(f13, iconatlas, text);
         float f4 = 22.0F + f3;
         float f5 = f4 + 20.0F;
         float f6 = Math.max(14.0F, 16.0F) + 10.0F;
         float f7 = -f5 / 2.0F;
         float f8 = -f6 / 2.0F;
         Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
         int j = Theme.background();
         float f15 = 1.0F;
         int i = j;
         float f14 = 8.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f14, f7, i, matrix4f, f6, f5, f15, f8);
         float f9 = f7 + 10.0F;
         float f10 = -8.0F;
         float f17 = 1.0F;
         float f16 = 16.0F;
         ItemStack itemstack = itemStack;
         if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(f9, matrix4f, f17, f16, f10, itemstack)) {
            float f19 = 1.0F;
            float f18 = 16.0F;
            ItemStack itemstack1 = itemStack;
            ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f18, f19, f9, itemstack1, matrix4f, f10);
         }

         float f11 = f9 + 16.0F + 6.0F;
         float f12 = -8.5F;
         byte b0 = -1;
         float f20 = 14.0F;
         IconAtlas iconatlas1 = icon;
         TextShader.onStringFloatMatrix4fIntFloatIconAtlasFloat(text, f20, matrix4f, b0, f11, iconatlas1, f12);
      } finally {
         matrixStack.pop();
      }
   }

   @Override
   public void onEnable() {
   }
}
