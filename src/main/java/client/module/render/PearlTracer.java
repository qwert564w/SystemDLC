package client.module.render;

import client.api.Theme;
import client.module.Category;
import client.module.Module;
import client.module.client.StreamBypass;
import client.render.DepthState;
import client.render.IconAtlas;
import client.render.ItemIconCache;
import client.render.RotationBuffer;
import client.render.ShapeShader;
import client.render.TextShader;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.DistanceScale;
import client.util.ItemIcons;
import client.util.PearlPath;
import client.util.TimeFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;

public class PearlTracer extends Module {
   private BooleanSetting showTraektoriyu;
   private BooleanSetting showTochkuPrizemleniya;
   private BooleanSetting showHitboks;
   private BooleanSetting plashkaPrizemleniya;
   private BooleanSetting ignorirovatOwnZhemchuga;
   private BooleanSetting trekatDruzey;
   private SliderSetting scalePlashki;
   private ColorSetting colorTraektorii;
   private ColorSetting colorPrizemleniya;
   private ColorSetting colorTraektoriiDruga;
   private ColorSetting colorPrizemleniyaDruga;
   private final Map<Integer, PearlPath> map;
   private static final ItemStack itemStack = new ItemStack(Items.ENDER_PEARL);
   private final Map<Integer, Float> map2;
   private long time;

   public PearlTracer() {
      super("PearlTracer", Category.RENDER);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать траекторию");
      booleansetting.setDescription("Отображать путь полёта эндер-жемчуга");
      this.showTraektoriyu = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать точку приземления");
      booleansetting.setDescription("Отображать точку приземления жемчуга");
      this.showTochkuPrizemleniya = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать хитбокс");
      booleansetting.setDescription("Отображать хитбокс в точке приземления");
      this.showHitboks = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Плашка приземления");
      booleansetting.setDescription("Показывать плашку c временем до приземления");
      this.plashkaPrizemleniya = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Игнорировать свои жемчуга");
      booleansetting.setDescription("Не показывать траекторию для своих жемчугов");
      this.ignorirovatOwnZhemchuga = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Трекать друзей");
      booleansetting.setDescription("Показывать траекторию для жемчугов друзей отдельным цветом");
      this.trekatDruzey = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.3, 0.1, 1.0, 0.1);
      slidersetting.setName("Масштаб плашки");
      slidersetting.setDescription("Масштаб плашки приземления");
      this.scalePlashki = slidersetting;
      ColorSetting colorsetting = new ColorSetting("", "", -1, true);
      colorsetting.setName("Цвет траектории");
      colorsetting.setDescription("Цвет линии траектории жемчуга");
      this.colorTraektorii = colorsetting;
      ColorSetting colorsetting1 = new ColorSetting("", "", -1, true);
      colorsetting1.setName("Цвет приземления");
      colorsetting1.setDescription("Цвет точки приземления");
      this.colorPrizemleniya = colorsetting1;
      ColorSetting colorsetting2 = new ColorSetting("", "", -10046721, true);
      colorsetting2.setName("Цвет траектории друга");
      colorsetting2.setDescription("Цвет линии траектории для жемчугов друзей");
      this.colorTraektoriiDruga = colorsetting2;
      ColorSetting colorsetting3 = new ColorSetting("", "", -10046721, true);
      colorsetting3.setName("Цвет приземления друга");
      colorsetting3.setDescription("Цвет точки и хитбокса приземления для жемчугов друзей");
      this.colorPrizemleniyaDruga = colorsetting3;
      this.map = new ConcurrentHashMap<>();
      this.map2 = new HashMap<>();
      this.time = System.currentTimeMillis();
      this.scalePlashki.setVisibleWhen(this.plashkaPrizemleniya::isFlag3);
      this.colorTraektorii.setVisibleWhen(this.showTraektoriyu::isFlag3);
      this.colorPrizemleniya.setVisibleWhen(this.showTochkuPrizemleniya::isFlag3);
      this.colorTraektoriiDruga.setVisibleWhen(this::getBoolean);
      this.colorPrizemleniyaDruga.setVisibleWhen(this::getBoolean2);
      this.addSettings(
         new Setting[]{
            this.showTraektoriyu,
            this.showTochkuPrizemleniya,
            this.showHitboks,
            this.plashkaPrizemleniya,
            this.ignorirovatOwnZhemchuga,
            this.trekatDruzey,
            this.scalePlashki,
            this.colorTraektorii,
            this.colorPrizemleniya,
            this.colorTraektoriiDruga,
            this.colorPrizemleniyaDruga
         }
      );
   }

   @Override
   public void onTick() {
      if (!this.notInGame()) {
         this.update12();
         this.update11();
      }
   }

   private boolean isEnderPearlEntity(EnderPearlEntity enderPearlEntity) {
      return enderPearlEntity.getOwner() instanceof PlayerEntity playerentity && !playerentity.equals(this.player()) && this.isFriend(playerentity);
   }

   private void update11() {
      long i = System.currentTimeMillis();
      this.map.entrySet().removeIf(p0 -> PearlTracer.isLongEntry(i, p0));
      this.map2.keySet().retainAll(this.map.keySet());
   }

   private Boolean getBoolean() {
      return this.trekatDruzey.isFlag3() && this.showTraektoriyu.isFlag3();
   }

   private void render(MatrixStack matrixStack, Vec3d vec3d2) {
      BufferBuilder bufferbuilder = DepthState.getBufferBuilderByFloat(0.6F);
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      boolean flag = this.trekatDruzey.isFlag3();
      float f = 0.3F;
      float f1 = 1.8F;

      for (PearlPath pearlpath : this.map.values()) {
         if (pearlpath.vec3d != null) {
            int i = (flag && pearlpath.flag2 ? this.colorPrizemleniyaDruga : this.colorPrizemleniya).getInt3();
            float f2 = (i >> 16 & 0xFF) / 255.0F;
            float f3 = (i >> 8 & 0xFF) / 255.0F;
            float f4 = (i & 0xFF) / 255.0F;
            float f5 = (i >> 24 & 0xFF) / 255.0F * 0.5F;
            Vec3d vec3d = pearlpath.vec3d.subtract(vec3d2);
            float f6 = (float)vec3d.x;
            float f7 = (float)vec3d.y;
            float f8 = (float)vec3d.z;
            float f9 = f6 - f;
            float f10 = f6 + f;
            float f11 = f7 + f1;
            float f12 = f8 - f;
            float f13 = f8 + f;
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f7, f5, f3, f9, f2, f12, f7, f12, matrix4f, f10
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f7, f5, f3, f10, f2, f13, f7, f12, matrix4f, f10
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f7, f5, f3, f10, f2, f13, f7, f13, matrix4f, f9
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f7, f5, f3, f9, f2, f12, f7, f13, matrix4f, f9
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f11, f5, f3, f9, f2, f12, f11, f12, matrix4f, f10
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f11, f5, f3, f10, f2, f13, f11, f12, matrix4f, f10
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f11, f5, f3, f10, f2, f13, f11, f13, matrix4f, f9
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f11, f5, f3, f9, f2, f12, f11, f13, matrix4f, f9
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f11, f5, f3, f9, f2, f12, f7, f12, matrix4f, f9
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f11, f5, f3, f10, f2, f12, f7, f12, matrix4f, f10
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f11, f5, f3, f10, f2, f13, f7, f13, matrix4f, f10
            );
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f4, f11, f5, f3, f9, f2, f13, f7, f13, matrix4f, f9
            );
         }
      }

      DepthState.onBufferBuilder(bufferbuilder);
   }

   @Override
   public void onDisable() {
      this.map.clear();
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!StreamBypass.check6()) {
         if (!this.notInGame()) {
            try {
               this.render8(worldRenderContext);
            } catch (Exception exception) {
            }
         }
      }
   }

   private static boolean isLongEntry(long time2, Entry<Integer, PearlPath> entry) {
      return time2 - entry.getValue().time > 5000L;
   }

   private void update12() {
      Iterable<Entity> iterable = this.clientWorld().getEntities();
      HashSet hashset = new HashSet();

      for (Entity entity : iterable) {
         if (entity instanceof EnderPearlEntity enderpearlentity && (!this.ignorirovatOwnZhemchuga.isFlag3() || !this.isEnderPearlEntity2(enderpearlentity))) {
            boolean flag = this.isEnderPearlEntity(enderpearlentity);
            int i = enderpearlentity.getId();
            hashset.add(i);
            PearlPath pearlpath = this.map.computeIfAbsent(i, PearlTracer::getPearlPathByInteger);
            pearlpath.flag2 = flag;
            Vec3d vec3d = enderpearlentity.getPos();
            pearlpath.vec3d2 = vec3d;
            pearlpath.time = System.currentTimeMillis();
            if (!pearlpath.flag) {
               this.onPearlPathEnderPearlEntity(pearlpath, enderpearlentity);
               pearlpath.flag = true;
            }

            if (!pearlpath.list.isEmpty()) {
               pearlpath.value3 = pearlpath.value2;
               float f = pearlpath.value2;
               List list = pearlpath.list;
               pearlpath.value2 = this.getFloatByFloatVec3dList(f, vec3d, list);
               pearlpath.value = (int)pearlpath.value2;
            }
         }
      }

      this.map.keySet().retainAll(hashset);
   }

   private static String getStringByDouble(double value) {
      return TimeFormat.getStringByDouble(value);
   }

   private void onIntCameraFloatMatrixStackVec3dStringDouble(int count, Camera camera, float value, MatrixStack matrixStack, Vec3d vec3d, String text, double value2) {
      matrixStack.push();

      try {
         matrixStack.translate(vec3d.x, vec3d.y + 1.0, vec3d.z);
         RotationBuffer.render(matrixStack);
         float f = DistanceScale.getFloatByDoubleFloat(value2, this.scalePlashki.getValueAsFloat());
         float f1 = this.map2.getOrDefault(count, f);
         float f2 = f1 + (f - f1) * Math.min(1.0F, value * 10.0F);
         this.map2.put(count, f2);
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

   private static PearlPath getPearlPathByInteger(Integer value) {
      return new PearlPath();
   }

   private Boolean getBoolean2() {
      return this.trekatDruzey.isFlag3() && this.showTochkuPrizemleniya.isFlag3();
   }

   private void onVec3dMatrixStack(Vec3d vec3d2, MatrixStack matrixStack) {
      BufferBuilder bufferbuilder = DepthState.getBufferBuilderByFloat(0.8F);
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      boolean flag = this.trekatDruzey.isFlag3();
      float f = 0.5F;

      for (PearlPath pearlpath : this.map.values()) {
         if (pearlpath.vec3d != null) {
            int i = (flag && pearlpath.flag2 ? this.colorPrizemleniyaDruga : this.colorPrizemleniya).getInt3();
            float f1 = (i >> 16 & 0xFF) / 255.0F;
            float f2 = (i >> 8 & 0xFF) / 255.0F;
            float f3 = (i & 0xFF) / 255.0F;
            float f4 = (i >> 24 & 0xFF) / 255.0F;
            Vec3d vec3d = pearlpath.vec3d.subtract(vec3d2);
            float f5 = (float)vec3d.x;
            float f6 = (float)vec3d.y;
            float f7 = (float)vec3d.z;
            float f9 = f5 + f;
            float f8 = f5 - f;
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f3, f6, f4, f2, f8, f1, f7, f6, f7, matrix4f, f9
            );
            float f11 = f6 + f;
            float f10 = f6 - f;
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f3, f11, f4, f2, f5, f1, f7, f10, f7, matrix4f, f5
            );
            float f13 = f7 + f;
            float f12 = f7 - f;
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
               bufferbuilder, f3, f6, f4, f2, f5, f1, f13, f6, f12, matrix4f, f5
            );
         }
      }

      DepthState.onBufferBuilder(bufferbuilder);
   }

   private void render2(MatrixStack matrixStack, Vec3d vec3d7) {
      BufferBuilder bufferbuilder = DepthState.getBufferBuilderByFloat(1.0F);
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      boolean flag = this.trekatDruzey.isFlag3();

      for (PearlPath pearlpath : this.map.values()) {
         if (pearlpath.list.size() >= 2 && pearlpath.vec3d2 != null) {
            int i = (flag && pearlpath.flag2 ? this.colorTraektoriiDruga : this.colorTraektorii).getInt3();
            float f = (i >> 16 & 0xFF) / 255.0F;
            float f1 = (i >> 8 & 0xFF) / 255.0F;
            float f2 = (i & 0xFF) / 255.0F;
            float f3 = (i >> 24 & 0xFF) / 255.0F;
            float f4 = pearlpath.value2;
            int j = (int)f4;
            float f5 = f4 - j;
            if (j < pearlpath.list.size() - 1) {
               Vec3d vec3d = pearlpath.list.get(j);
               Vec3d vec3d1 = pearlpath.list.get(j + 1);
               Vec3d vec3d2 = vec3d.add(vec3d1.subtract(vec3d).multiply(f5));
               Vec3d vec3d3 = vec3d2.subtract(vec3d7);
               Vec3d vec3d4 = vec3d1.subtract(vec3d7);
               float f23 = (float)vec3d3.x;
               float f24 = (float)vec3d3.y;
               float f25 = (float)vec3d3.z;
               float f26 = (float)vec3d4.x;
               float f27 = (float)vec3d4.y;
               float f11 = (float)vec3d4.z;
               float f10 = f27;
               float f9 = f26;
               float f8 = f25;
               float f7 = f24;
               float f6 = f23;
               DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
                  bufferbuilder, f2, f10, f3, f1, f6, f, f11, f7, f8, matrix4f, f9
               );
            }

            for (int k = j + 1; k < pearlpath.list.size() - 1; k++) {
               Vec3d vec3d5 = pearlpath.list.get(k).subtract(vec3d7);
               Vec3d vec3d6 = pearlpath.list.get(k + 1).subtract(vec3d7);
               float f18 = (float)vec3d5.x;
               float f19 = (float)vec3d5.y;
               float f20 = (float)vec3d5.z;
               float f21 = (float)vec3d6.x;
               float f22 = (float)vec3d6.y;
               float f17 = (float)vec3d6.z;
               float f16 = f22;
               float f15 = f21;
               float f14 = f20;
               float f13 = f19;
               float f12 = f18;
               DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
                  bufferbuilder, f2, f16, f3, f1, f12, f, f17, f13, f14, matrix4f, f15
               );
            }
         }
      }

      DepthState.onBufferBuilder(bufferbuilder);
   }

   private void render8(WorldRenderContext worldRenderContext) {
      if (!this.map.isEmpty()) {
         MatrixStack matrixstack = worldRenderContext.getMatrixStack();
         Vec3d vec3d = worldRenderContext.getCamera().getPos();
         if (this.showTraektoriyu.isFlag3()) {
            this.render2(matrixstack, vec3d);
         }

         if (this.showTochkuPrizemleniya.isFlag3()) {
            this.onVec3dMatrixStack(vec3d, matrixstack);
         }

         if (this.showHitboks.isFlag3()) {
            this.render(matrixstack, vec3d);
         }

         if (this.plashkaPrizemleniya.isFlag3()) {
            this.onVec3dWorldRenderContext(vec3d, worldRenderContext);
         }
      }
   }

   private boolean isVec3dVec3d(Vec3d vec3d2, Vec3d vec3d3) {
      Vec3d vec3d = vec3d3.add(vec3d2);
      RaycastContext raycastcontext = new RaycastContext(vec3d3, vec3d, ShapeType.COLLIDER, FluidHandling.NONE, ShapeContext.absent());
      BlockHitResult blockhitresult = this.world().raycast(raycastcontext);
      return blockhitresult.getType() == Type.BLOCK;
   }

   private void onPearlPathEnderPearlEntity(PearlPath pearlPath, EnderPearlEntity enderPearlEntity) {
      pearlPath.list.clear();
      pearlPath.vec3d = null;
      pearlPath.value4 = 0;
      Vec3d vec3d = enderPearlEntity.getPos();
      Vec3d vec3d1 = enderPearlEntity.getVelocity();
      double d0 = 0.99;
      double d1 = 0.03;
      short short1 = 200;
      byte b0 = 100;
      int i = short1 / b0;

      for (int j = 0; j < short1; j++) {
         if (j % i == 0) {
            pearlPath.list.add(vec3d);
         }

         if (this.isVec3dVec3d(vec3d1, vec3d)) {
            pearlPath.vec3d = vec3d;
            pearlPath.value4 = j;
            break;
         }

         vec3d = vec3d.add(vec3d1);
         Vec3d vec3d2 = vec3d1.multiply(d0);
         vec3d1 = vec3d2.add(0.0, -d1, 0.0);
         if (vec3d.y < -64.0 || vec3d.squaredDistanceTo(enderPearlEntity.getPos()) > 250000.0) {
            pearlPath.value4 = j;
            break;
         }
      }

      if (pearlPath.vec3d == null && !pearlPath.list.isEmpty()) {
         pearlPath.vec3d = pearlPath.list.getLast();
         pearlPath.value4 = short1;
      }
   }

   private void onVec3dWorldRenderContext(Vec3d vec3d2, WorldRenderContext worldRenderContext) {
      MatrixStack matrixstack = worldRenderContext.getMatrixStack();
      Camera camera = worldRenderContext.getCamera();
      if (matrixstack != null) {
         long i = System.currentTimeMillis();
         float f = Math.min((float)(i - this.time) / 1000.0F, 0.1F);
         this.time = i;
         RotationBuffer.setMinecraftClient2(this.client());
         ShapeShader.update2();

         try {
            for (Entry entry : this.map.entrySet()) {
               PearlPath pearlpath = (PearlPath)entry.getValue();
               if (pearlpath.vec3d != null) {
                  float f1 = pearlpath.value4 - pearlpath.value2 * 2.0F;
                  double d0 = Math.max(0.0, f1 / 20.0);
                  String s = getStringByDouble(d0);
                  Vec3d vec3d = pearlpath.vec3d.subtract(vec3d2);
                  double d1 = pearlpath.vec3d.distanceTo(vec3d2);
                  int j = (Integer)entry.getKey();
                  this.onIntCameraFloatMatrixStackVec3dStringDouble(j, camera, f, matrixstack, vec3d, s, d1);
               }
            }
         } finally {
            ShapeShader.update();
            RotationBuffer.setMinecraftClient(this.client());
         }
      }
   }

   private boolean isEnderPearlEntity2(EnderPearlEntity enderPearlEntity) {
      Entity entity = enderPearlEntity.getOwner();
      return entity != null && entity.equals(this.player());
   }

   private float getFloatByFloatVec3dList(float value, Vec3d vec3d5, List list) {
      if (list.size() < 2) {
         return 0.0F;
      } else {
         int i = Math.max(0, (int)value);
         double d0 = Double.MAX_VALUE;
         float f = value;
         int j = Math.max(0, i - 1);
         int k = Math.min(list.size() - 1, i + 10);

         for (int l = j; l < k; l++) {
            Vec3d vec3d = (Vec3d)list.get(l);
            Vec3d vec3d1 = (Vec3d)list.get(l + 1);
            Vec3d vec3d2 = vec3d1.subtract(vec3d);
            double d1 = vec3d2.lengthSquared();
            if (!(d1 < 1.0E-4)) {
               Vec3d vec3d3 = vec3d5.subtract(vec3d);
               double d2 = vec3d3.dotProduct(vec3d2) / d1;
               d2 = Math.clamp(d2, 0.0, 1.0);
               Vec3d vec3d4 = vec3d.add(vec3d2.multiply(d2));
               double d3 = vec3d5.squaredDistanceTo(vec3d4);
               if (d3 < d0) {
                  d0 = d3;
                  f = l + (float)d2;
               }
            }
         }

         return Math.max(value, f);
      }
   }

   @Override
   public void onEnable() {
   }
}
