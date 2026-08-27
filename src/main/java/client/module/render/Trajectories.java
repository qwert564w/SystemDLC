package client.module.render;

import client.data.PointD;
import client.module.Category;
import client.module.Module;
import client.render.DepthState;
import client.render.ShaderCache;
import client.render.ShaderUniforms;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.CompactGroupSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.util.TrajectoryPath;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;

public class Trajectories extends Module {
   private static String text = "Эндер-перл";
   private static String text2 = "Снежок";
   private static String text3 = "Яйцо";
   private static String text4 = "Зелье";
   private static String text5 = "Лук";
   private static String text6 = "Арбалет";
   private static String text7 = "Трезубец";
   private static String text8 = "Заряд ветра";
   private static final List<String> list = List.of(text, text2, text3, text4, text5, text6, text7, text8);
   private MultilistSetting snaryady;
   private BooleanSetting lineTraektorii;
   private ColorSetting colorLines;
   private BooleanSetting showTochkuPrizemleniya;
   private ColorSetting kupolZelya;
   private ColorSetting popadanieZelem;
   private ColorSetting krugSnaryada;
   private ColorSetting popadanieSnaryadom;
   private static final ShaderProgramKey shaderProgramKey = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "circle", VertexFormats.POSITION_TEXTURE, Defines.EMPTY
   );
   private static final float[] floatArray = new float[]{0.0F};
   private static final float[] floatArray2 = new float[]{0.0F, -10.0F, 10.0F};
   private static final float[] floatArray3 = new float[49];
   private static final float[] floatArray4 = new float[49];
   private final List<TrajectoryPath> list2;
   private final List<TrajectoryPath> list3;
   private final List<TrajectoryPath> list4;
   private int value235;
   private float value236;
   private long time;

   public Trajectories() {
      super("Trajectories", Category.RENDER);
      MultilistSetting multilistsetting = new MultilistSetting("", "", list, list);
      multilistsetting.setName("Снаряды");
      multilistsetting.setDescription("Для каких предметов рисовать траекторию");
      this.snaryady = multilistsetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Линия траектории");
      booleansetting.setDescription("Отображать линию траектории");
      this.lineTraektorii = booleansetting;
      ColorSetting colorsetting = new ColorSetting("", "", -1, true);
      colorsetting.setName("Цвет линии");
      colorsetting.setDescription("Цвет линии траектории");
      this.colorLines = colorsetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Показывать точку приземления");
      booleansetting1.setDescription("Отображать сферу/круг в точке приземления");
      this.showTochkuPrizemleniya = booleansetting1;
      ColorSetting colorsetting1 = new ColorSetting("", "", 452984831, true);
      colorsetting1.setName("Купол зелья");
      colorsetting1.setDescription("Цвет купола радиуса кидательных зелей");
      this.kupolZelya = colorsetting1;
      ColorSetting colorsetting2 = new ColorSetting("", "", 439549796, true);
      colorsetting2.setName("Попадание зельем");
      colorsetting2.setDescription("Цвет купола, если в радиусе splash есть живая цель");
      this.popadanieZelem = colorsetting2;
      ColorSetting colorsetting3 = new ColorSetting("", "", 452984831, true);
      colorsetting3.setName("Круг снаряда");
      colorsetting3.setDescription("Цвет круга в точке падения стрелы/жемчуга/прочего");
      this.krugSnaryada = colorsetting3;
      ColorSetting colorsetting4 = new ColorSetting("", "", 439549796, true);
      colorsetting4.setName("Попадание снарядом");
      colorsetting4.setDescription("Цвет круга, если снаряд попадает в сущность");
      this.popadanieSnaryadom = colorsetting4;
      this.list2 = new ArrayList<>();
      this.list3 = new ArrayList<>();
      this.list4 = new ArrayList<>();
      this.value235 = Integer.MIN_VALUE;
      this.value236 = 0.0F;
      this.time = 0L;
      IdentityHashMap identityhashmap = new IdentityHashMap();
      identityhashmap.put(this.kupolZelya, Items.SPLASH_POTION);
      identityhashmap.put(this.popadanieZelem, Items.LINGERING_POTION);
      identityhashmap.put(this.krugSnaryada, Items.ARROW);
      identityhashmap.put(this.popadanieSnaryadom, Items.SPECTRAL_ARROW);
      CompactGroupSetting compactgroupsetting1 = new CompactGroupSetting(
         "", "", this.kupolZelya, this.popadanieZelem, this.krugSnaryada, this.popadanieSnaryadom
      );
      compactgroupsetting1.setName("Точки приземления");
      compactgroupsetting1.setDescription("Цвета кругов/куполов в точках падения.");
      CompactGroupSetting compactgroupsetting = compactgroupsetting1;
      compactgroupsetting.getCompactGroupSettingByFunction2(p0 -> Trajectories.getItemStackByIdentityHashMapSetting(identityhashmap, p0));
      compactgroupsetting.setVisibleWhen(this.showTochkuPrizemleniya::isFlag3);
      this.colorLines.setVisibleWhen(this.lineTraektorii::isFlag3);
      this.addSettings(new Setting[]{this.snaryady, this.lineTraektorii, this.colorLines, this.showTochkuPrizemleniya, compactgroupsetting});
   }

   static {
      for (int i = 0; i <= 48; i++) {
         double d0 = (Math.PI * 2) * i / 48.0;
         floatArray3[i] = (float)Math.cos(d0);
         floatArray4[i] = (float)Math.sin(d0);
      }
   }

   private boolean isItemStack(ItemStack itemStack) {
      try {
         if (this.world() == null) {
            return false;
         } else {
            Registry registry = this.world().getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
            RegistryEntry registryentry = (RegistryEntry)registry.getEntry(Enchantments.MULTISHOT.getValue()).orElse(null);
            return registryentry != null && EnchantmentHelper.getLevel(registryentry, itemStack) >= 1;
         }
      } catch (Exception exception) {
         return false;
      }
   }

   @Override
   public void onDisable() {
      this.list2.clear();
      this.list3.clear();
      this.list4.clear();
      this.value236 = 0.0F;
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!this.notInGame()) {
         PlayerEntity playerentity = this.player();
         if (playerentity != null) {
            float f = worldRenderContext.getRenderTickCounter().getTickDelta(true);
            if (playerentity.age != this.value235) {
               this.value235 = playerentity.age;
               this.list2.clear();

               for (TrajectoryPath trajectorypath : this.list3) {
                  this.list2.add(trajectorypath.getTrajectoryPath());
               }

               this.list3.clear();
               List<TrajectoryPath> listx = this.list3;
               this.onListPlayerEntity(listx, playerentity);
               if (this.list2.isEmpty()) {
                  for (TrajectoryPath trajectorypath1 : this.list3) {
                     this.list2.add(trajectorypath1.getTrajectoryPath());
                  }
               }
            }

            this.update11();
            if (!this.list3.isEmpty()) {
               boolean flag = this.lineTraektorii.isFlag3();
               boolean flag1 = this.showTochkuPrizemleniya.isFlag3() && this.value236 > 0.001F;
               if (flag || flag1) {
                  MatrixStack matrixstack = worldRenderContext.getMatrixStack();
                  Vec3d vec3d = worldRenderContext.getCamera().getPos();
                  Matrix4f matrix4f = matrixstack.peek().getPositionMatrix();
                  List list3x = this.list4;
                  List list2x = this.list3;
                  List list1 = this.list2;
                  onFloatListListList(f, list2x, list1, list3x);
                  if (flag) {
                     List list4x = this.list4;
                     this.onVec3dListMatrix4f(vec3d, list4x, matrix4f);
                  }

                  if (flag1) {
                     this.onMatrix4fVec3dList(matrix4f, vec3d, this.list4);
                  }
               }
            }
         }
      }
   }

   private PointD getPointDByItemStackPlayerEntity(ItemStack itemStack, PlayerEntity playerEntity) {
      Item item = itemStack.getItem();
      Objects.requireNonNull(item);

      return switch (item) {
         case Item item1 when item1 == Items.ENDER_PEARL || item1 == Items.SNOWBALL || item1 == Items.EGG -> new PointD(1.5, 0.03, 0.99);
         case Item item2 when item2 == Items.SPLASH_POTION || item2 == Items.LINGERING_POTION -> new PointD(0.5, 0.05, 0.99);
         case Item item3 when item3 == Items.BOW -> {
            if (!playerEntity.isUsingItem()) {
               yield null;
            } else {
               double d0 = BowItem.getPullProgress(playerEntity.getItemUseTime()) * 3.0;
               yield d0 < 0.1 ? null : new PointD(d0, 0.05, 0.99);
            }
         }
         case Item item4 when item4 == Items.CROSSBOW -> CrossbowItem.isCharged(itemStack) ? new PointD(3.15, 0.05, 0.99) : null;
         case Item item5 when item5 == Items.TRIDENT -> {
            if (playerEntity.isUsingItem() && playerEntity.getItemUseTime() >= 10) {
               yield new PointD(2.5, 0.05, 0.99);
            }

            yield null;
         }
         case Item item6 when item6 == Items.WIND_CHARGE -> new PointD(1.5, 0.0, 1.0);
         default -> null;
      };
   }

   private static String getStringByItemStack(ItemStack itemStack) {
      if (itemStack.isOf(Items.ENDER_PEARL)) {
         return text;
      } else if (itemStack.isOf(Items.SNOWBALL)) {
         return text2;
      } else if (itemStack.isOf(Items.EGG)) {
         return text3;
      } else if (itemStack.isOf(Items.SPLASH_POTION) || itemStack.isOf(Items.LINGERING_POTION)) {
         return text4;
      } else if (itemStack.isOf(Items.BOW)) {
         return text5;
      } else if (itemStack.isOf(Items.CROSSBOW)) {
         return text6;
      } else if (itemStack.isOf(Items.TRIDENT)) {
         return text7;
      } else {
         return itemStack.isOf(Items.WIND_CHARGE) ? text8 : null;
      }
   }

   private boolean isItemStack2(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         String s = getStringByItemStack(itemStack);
         return s != null && this.snaryady.isString2(s);
      } else {
         return false;
      }
   }

   private ItemStack getItemStackByItemStackItemStack(ItemStack itemStack, ItemStack itemStack2) {
      if (this.isItemStack2(itemStack)) {
         return itemStack;
      } else {
         return this.isItemStack2(itemStack2) ? itemStack2 : null;
      }
   }

   private static void onFloatMatrix4fVec3dFloatFloatFloatVec3dFloatVec3d(
      float value, Matrix4f matrix4f, Vec3d vec3d, float value2, float value3, float value4, Vec3d vec3d2, float value5, Vec3d vec3d3
   ) {
      ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey);
      if (shaderprogram != null) {
         String s = "FillColor";
         ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(value2, value4, shaderprogram, s, value3, value5);
         float f25 = Math.min(1.0F, value2 * 2.0F);
         String s1 = "EdgeColor";
         ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f25, value4, shaderprogram, s1, value3, value5);
         float f31 = (float)vec3d.x;
         float f32 = (float)vec3d.y;
         float f28 = (float)vec3d.z;
         float f27 = f32;
         float f26 = f31;
         String s2 = "ViewDir";
         ShaderUniforms.onShaderProgramFloatStringFloatFloat(shaderprogram, f27, s2, f28, f26);
         float f29 = 0.0F;
         String s3 = "IsDome";
         ShaderUniforms.onFloatShaderProgramString(f29, shaderprogram, s3);
         float f30 = 1.0F;
         String s4 = "GlobalAlpha";
         ShaderUniforms.onFloatShaderProgramString(f30, shaderprogram, s4);
         float f = (float)vec3d.x;
         float f1 = (float)vec3d.y;
         float f2 = (float)vec3d.z;
         float f3 = (float)Math.sqrt(f * f + f1 * f1 + f2 * f2);
         if (f3 < 1.0E-6F) {
            f = 0.0F;
            f1 = 1.0F;
            f2 = 0.0F;
         } else {
            f /= f3;
            f1 /= f3;
            f2 /= f3;
         }

         float f4;
         float f5;
         float f6;
         if (Math.abs(f1) < 0.99F) {
            f4 = -f2;
            f5 = 0.0F;
            f6 = f;
         } else {
            f4 = 0.0F;
            f5 = -f2;
            f6 = f1;
         }

         float f7 = (float)Math.sqrt(f4 * f4 + f5 * f5 + f6 * f6);
         f4 /= f7;
         f5 /= f7;
         f6 /= f7;
         float f8 = f1 * f6 - f2 * f5;
         float f9 = f2 * f4 - f * f6;
         float f10 = f * f5 - f1 * f4;
         float f11 = 0.01F;
         float f12 = (float)(vec3d3.x - vec3d2.x) + f * f11;
         float f13 = (float)(vec3d3.y - vec3d2.y) + f1 * f11;
         float f14 = (float)(vec3d3.z - vec3d2.z) + f2 * f11;
         BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_TEXTURE);

         for (int i = 0; i < 48; i++) {
            float f15 = floatArray3[i];
            float f16 = floatArray4[i];
            float f17 = floatArray3[i + 1];
            float f18 = floatArray4[i + 1];
            float f19 = f12 + (f4 * f15 + f8 * f16) * value;
            float f20 = f13 + (f5 * f15 + f9 * f16) * value;
            float f21 = f14 + (f6 * f15 + f10 * f16) * value;
            float f22 = f12 + (f4 * f17 + f8 * f18) * value;
            float f23 = f13 + (f5 * f17 + f9 * f18) * value;
            float f24 = f14 + (f6 * f17 + f10 * f18) * value;
            bufferbuilder.vertex(matrix4f, f12, f13, f14).texture(0.0F, 0.0F);
            bufferbuilder.vertex(matrix4f, f19, f20, f21).texture(f15, f16);
            bufferbuilder.vertex(matrix4f, f22, f23, f24).texture(f17, f18);
         }

         BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      }
   }

   private static void onMatrix4fVec3dFloatVec3dVec3dFloatFloatFloat(
      Matrix4f matrix4f, Vec3d vec3d, float value, Vec3d vec3d2, Vec3d vec3d3, float value2, float value3, float value4
   ) {
      float f = 0.18F;
      float f1 = (float)vec3d3.x;
      float f2 = (float)vec3d3.y;
      float f3 = (float)vec3d3.z;
      float f4 = (float)Math.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
      if (f4 > 1.0E-6F) {
         f1 /= f4;
         f2 /= f4;
         f3 /= f4;
      } else {
         f1 = 0.0F;
         f2 = 1.0F;
         f3 = 0.0F;
      }

      float f5 = f + 0.01F;
      float f6 = (float)(vec3d.x - vec3d2.x) + f1 * f5;
      float f7 = (float)(vec3d.y - vec3d2.y) + f2 * f5;
      float f8 = (float)(vec3d.z - vec3d2.z) + f3 * f5;
      float f9 = f6 - f;
      float f10 = f7 - f;
      float f11 = f8 - f;
      float f12 = f6 + f;
      float f13 = f7 + f;
      float f14 = f8 + f;
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
      DepthState.onFloatFloatFloatFloatFloatMatrix4fFloatBufferBuilderFloatFloatFloatFloat(
         value, f11, f10, f12, value4, matrix4f, value3, bufferbuilder, f9, f14, value2, f13
      );
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      float f15 = Math.min(1.0F, value3 * 3.0F);
      BufferBuilder bufferbuilder1 = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
      float f16 = 0.008F;
      DepthState.onFloatFloatFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloatFloatFloat(
         f12, f15, value, f10, matrix4f, f9, f11, f13, bufferbuilder1, value4, f16, f14, value2
      );
      BufferRenderer.drawWithGlobalProgram(bufferbuilder1.end());
   }

   private static ItemStack getItemStackByIdentityHashMapSetting(IdentityHashMap identityHashMap, Setting setting2) {
      Item item = (Item)identityHashMap.get(setting2);
      return item != null ? new ItemStack(item) : ItemStack.EMPTY;
   }

   private static boolean isEntity(Entity entity2) {
      return !entity2.isSpectator() && entity2.canHit();
   }

   private static EntityHitResult getEntityHitResultByVec3dListVec3dBoxArray(Vec3d vec3d2, List<Entity> list, Vec3d vec3d3, Box[] boxArray) {
      if (boxArray == null) {
         return null;
      } else {
         Entity entity = null;
         double d0 = Double.MAX_VALUE;
         Vec3d vec3d = null;

         for (int i = 0; i < boxArray.length; i++) {
            Optional optional = boxArray[i].raycast(vec3d3, vec3d2);
            if (optional.isPresent()) {
               double d1 = vec3d3.squaredDistanceTo((Vec3d)optional.get());
               if (d1 < d0) {
                  d0 = d1;
                  entity = (Entity)list.get(i);
                  vec3d = (Vec3d)optional.get();
               }
            }
         }

         return entity != null ? new EntityHitResult(entity, vec3d) : null;
      }
   }

   private float[] getFloatArrayByItemStack(ItemStack itemStack) {
      return itemStack.isOf(Items.CROSSBOW) && this.isItemStack(itemStack) ? floatArray2 : floatArray;
   }

   private void update11() {
      long i = System.nanoTime();
      if (this.time != 0L && i - this.time <= NANOS_PER_SECOND) {
         float f = (float)(i - this.time) / (float)NANOS_PER_SECOND;
         this.time = i;
         boolean flag = false;

         for (TrajectoryPath trajectorypath : this.list3) {
            if (trajectorypath.vec3d != null) {
               flag = true;
               break;
            }
         }

         float f1 = 20.0F;
         if (flag) {
            this.value236 = Math.min(1.0F, this.value236 + f * f1);
         } else {
            this.value236 = Math.max(0.0F, this.value236 - f * f1);
         }
      } else {
         this.time = i;
      }
   }

   private static void onFloatListListList(float value2, List<TrajectoryPath> list2, List list3, List list4) {
      while (list4.size() < list2.size()) {
         list4.add(new TrajectoryPath());
      }

      while (list4.size() > list2.size()) {
         list4.removeLast();
      }

      int i = Math.min(list3.size(), list2.size());

      for (int j = 0; j < i; j++) {
         TrajectoryPath trajectorypath = (TrajectoryPath)list3.get(j);
         TrajectoryPath trajectorypath1 = (TrajectoryPath)list2.get(j);
         TrajectoryPath trajectorypath2 = (TrajectoryPath)list4.get(j);
         trajectorypath2.list.clear();
         trajectorypath2.value = trajectorypath1.value;
         trajectorypath2.flag = trajectorypath1.flag;
         trajectorypath2.flag2 = trajectorypath1.flag2;
         trajectorypath2.vec3d2 = trajectorypath1.vec3d2;
         if (trajectorypath.vec3d != null && trajectorypath1.vec3d != null) {
            trajectorypath2.vec3d = trajectorypath.vec3d.lerp(trajectorypath1.vec3d, value2);
         } else {
            trajectorypath2.vec3d = trajectorypath1.vec3d;
         }

         int k = trajectorypath.list.size();
         int l = trajectorypath1.list.size();
         int i1 = Math.min(k, l);

         for (int j1 = 0; j1 < i1; j1++) {
            trajectorypath2.list.add(trajectorypath.list.get(j1).lerp(trajectorypath1.list.get(j1), value2));
         }

         List<Vec3d> listx = l > k ? trajectorypath1.list : trajectorypath.list;

         for (int k1 = i1; k1 < listx.size(); k1++) {
            trajectorypath2.list.add((Vec3d)listx.get(k1));
         }
      }

      for (int l1 = i; l1 < list2.size(); l1++) {
         TrajectoryPath trajectorypath3 = (TrajectoryPath)list2.get(l1);
         TrajectoryPath trajectorypath4 = (TrajectoryPath)list4.get(l1);
         trajectorypath4.list.clear();
         trajectorypath4.list.addAll(trajectorypath3.list);
         trajectorypath4.vec3d = trajectorypath3.vec3d;
         trajectorypath4.vec3d2 = trajectorypath3.vec3d2;
         trajectorypath4.value = trajectorypath3.value;
         trajectorypath4.flag = trajectorypath3.flag;
         trajectorypath4.flag2 = trajectorypath3.flag2;
      }
   }

   private static Vec3d getVec3dByDirection(Direction direction) {
      return direction == null ? new Vec3d(0.0, 1.0, 0.0) : new Vec3d(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
   }

   private static void onEntityHitResultBlockHitResultVec3dTrajectoryPath(EntityHitResult entityHitResult, BlockHitResult blockHitResult, Vec3d vec3d3, TrajectoryPath trajectoryPath) {
      Vec3d vec3d = blockHitResult.getType() == Type.BLOCK ? blockHitResult.getPos() : null;
      Vec3d vec3d1 = entityHitResult != null ? entityHitResult.getPos() : null;
      if (vec3d != null && vec3d1 != null) {
         if (vec3d3.squaredDistanceTo(vec3d) < vec3d3.squaredDistanceTo(vec3d1)) {
            trajectoryPath.vec3d = vec3d;
            trajectoryPath.vec3d2 = getVec3dByDirection(blockHitResult.getSide());
         } else {
            trajectoryPath.vec3d = vec3d1;
            trajectoryPath.vec3d2 = new Vec3d(0.0, 1.0, 0.0);
            trajectoryPath.flag = true;
         }
      } else if (vec3d != null) {
         trajectoryPath.vec3d = vec3d;
         trajectoryPath.vec3d2 = getVec3dByDirection(blockHitResult.getSide());
      } else {
         trajectoryPath.vec3d = vec3d1;
         trajectoryPath.vec3d2 = new Vec3d(0.0, 1.0, 0.0);
         trajectoryPath.flag = true;
      }
   }

   private void onPointDPlayerEntityTrajectoryPathFloat(PointD pointD, PlayerEntity playerEntity, TrajectoryPath trajectoryPath, float value4) {
      float f = playerEntity.getYaw() + value4;
      float f1 = playerEntity.getPitch();
      float f2 = f * (float) (Math.PI / 180.0);
      float f3 = f1 * (float) (Math.PI / 180.0);
      double d0 = -MathHelper.sin(f2) * MathHelper.cos(f3);
      double d1 = -MathHelper.sin(f3);
      double d2 = MathHelper.cos(f2) * MathHelper.cos(f3);
      double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      d0 /= d3;
      d1 /= d3;
      d2 /= d3;
      double d4 = playerEntity.getEyeHeight(playerEntity.getPose());
      Vec3d vec3d = new Vec3d(playerEntity.getX(), playerEntity.getY() + d4 - 0.1, playerEntity.getZ());
      Vec3d vec3d1 = new Vec3d(vec3d.x + d0 * 3.0, vec3d.y + d1 * 3.0, vec3d.z + d2 * 3.0);
      double d5 = 9.0;
      Vec3d vec3d2 = new Vec3d(d0 * pointD.value, d1 * pointD.value, d2 * pointD.value);
      Vec3d vec3d3 = playerEntity.getMovement();
      vec3d2 = vec3d2.add(vec3d3.x, playerEntity.isOnGround() ? 0.0 : vec3d3.y, vec3d3.z);
      trajectoryPath.list.add(vec3d1);
      Box box = new Box(vec3d.subtract(80.0, 80.0, 80.0), vec3d.add(80.0, 80.0, 80.0));
      List<Entity> listx = this.world().getOtherEntities(playerEntity, box, Trajectories::isEntity);
      int i = listx.size();
      Box[] abox = i > 0 ? new Box[i] : null;

      for (int j = 0; j < i; j++) {
         abox[j] = ((Entity)listx.get(j)).getBoundingBox().expand(0.3);
      }

      Vec3d vec3d6 = vec3d;

      for (int k = 0; k < 120; k++) {
         Vec3d vec3d4 = new Vec3d(vec3d2.x, vec3d2.y - pointD.value2, vec3d2.z).multiply(pointD.value3);
         Vec3d vec3d5 = vec3d6.add(vec3d4);
         RaycastContext raycastcontext = new RaycastContext(vec3d6, vec3d5, ShapeType.COLLIDER, FluidHandling.NONE, ShapeContext.absent());
         BlockHitResult blockhitresult = this.world().raycast(raycastcontext);
         EntityHitResult entityhitresult = getEntityHitResultByVec3dListVec3dBoxArray(vec3d5, listx, vec3d6, abox);
         if (blockhitresult.getType() == Type.BLOCK || entityhitresult != null) {
            onEntityHitResultBlockHitResultVec3dTrajectoryPath(entityhitresult, blockhitresult, vec3d6, trajectoryPath);
            trajectoryPath.list.add(trajectoryPath.vec3d);
            break;
         }

         if (vec3d5.squaredDistanceTo(vec3d) > d5) {
            trajectoryPath.list.add(vec3d5);
         }

         vec3d2 = vec3d4;
         vec3d6 = vec3d5;
         if (vec3d5.y < -64.0) {
            break;
         }
      }

      if (trajectoryPath.list.size() >= 3) {
         Vec3d vec3d7 = trajectoryPath.list.get(0);
         Vec3d vec3d8 = trajectoryPath.list.get(1);
         double d7 = (vec3d8.x - vec3d7.x) * d0 + (vec3d8.y - vec3d7.y) * d1 + (vec3d8.z - vec3d7.z) * d2;
         if (d7 < 0.0) {
            trajectoryPath.list.removeFirst();
         }
      }

      if (trajectoryPath.value > 0.0F && trajectoryPath.vec3d != null && !trajectoryPath.flag) {
         double d6 = trajectoryPath.value * trajectoryPath.value;

         for (Entity entity : listx) {
            if (entity instanceof LivingEntity livingentity && livingentity.isAffectedBySplashPotions() && entity.squaredDistanceTo(trajectoryPath.vec3d) <= d6) {
               trajectoryPath.flag = true;
               break;
            }
         }
      }
   }

   private void onListPlayerEntity(List<TrajectoryPath> list, PlayerEntity playerEntity) {
      ItemStack itemstack = playerEntity.getMainHandStack();
      ItemStack itemstack1 = playerEntity.getOffHandStack();
      ItemStack itemstack2 = this.getItemStackByItemStackItemStack(itemstack, itemstack1);
      if (itemstack2 != null) {
         PointD pointd = this.getPointDByItemStackPlayerEntity(itemstack2, playerEntity);
         if (pointd != null) {
            float f = !itemstack2.isOf(Items.SPLASH_POTION) && !itemstack2.isOf(Items.LINGERING_POTION) ? 0.0F : 3.3F;
            boolean flag = itemstack2.isOf(Items.BOW) || itemstack2.isOf(Items.CROSSBOW);

            for (float f1 : this.getFloatArrayByItemStack(itemstack2)) {
               TrajectoryPath trajectorypath = new TrajectoryPath();
               trajectorypath.value = f;
               trajectorypath.flag2 = flag;
               this.onPointDPlayerEntityTrajectoryPathFloat(pointd, playerEntity, trajectorypath, f1);
               list.add(trajectorypath);
            }
         }
      }
   }

   private static float getFloatByVec3dVec3d(Vec3d vec3d, Vec3d vec3d2) {
      float f = (float)vec3d.distanceTo(vec3d2);
      return MathHelper.clamp(0.4F + f * 0.008F, 0.4F, 1.2F);
   }

   private void onMatrix4fVec3dList(Matrix4f matrix4f, Vec3d vec3d4, List<TrajectoryPath> list) {
      float[] afloat = DepthState.getFloatArrayByInt(this.kupolZelya.getInt3());
      float[] afloat1 = DepthState.getFloatArrayByInt(this.popadanieZelem.getInt3());
      float[] afloat2 = DepthState.getFloatArrayByInt(this.krugSnaryada.getInt3());
      float[] afloat3 = DepthState.getFloatArrayByInt(this.popadanieSnaryadom.getInt3());
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.depthMask(false);

      try {
         for (TrajectoryPath trajectorypath : list) {
            if (trajectorypath.vec3d != null) {
               boolean flag = trajectorypath.value > 0.0F;
               float[] afloat4 = flag ? (trajectorypath.flag ? afloat1 : afloat) : (trajectorypath.flag ? afloat3 : afloat2);
               float f = afloat4[3] * this.value236;
               if (!(f <= 0.0F)) {
                  if (!flag && trajectorypath.flag2) {
                     float f9 = afloat4[0];
                     float f8 = afloat4[1];
                     float f4 = afloat4[2];
                     float f3 = f8;
                     float f2 = f9;
                     Vec3d vec3d2 = trajectorypath.vec3d2;
                     Vec3d vec3d1 = trajectorypath.vec3d;
                     onMatrix4fVec3dFloatVec3dVec3dFloatFloatFloat(matrix4f, vec3d1, f3, vec3d4, vec3d2, f2, f, f4);
                  } else {
                     float f1 = flag ? trajectorypath.value : getFloatByVec3dVec3d(trajectorypath.vec3d, vec3d4);
                     Vec3d vec3d = flag ? new Vec3d(0.0, 1.0, 0.0) : trajectorypath.vec3d2;
                     float f10 = afloat4[0];
                     float f11 = afloat4[1];
                     float f7 = afloat4[2];
                     float f6 = f11;
                     float f5 = f10;
                     Vec3d vec3d3 = trajectorypath.vec3d;
                     onFloatMatrix4fVec3dFloatFloatFloatVec3dFloatVec3d(f1, matrix4f, vec3d, f, f7, f6, vec3d4, f5, vec3d3);
                  }
               }
            }
         }
      } finally {
         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
      }
   }

   private static void onMatrix4fBufferBuilderFloatFloatVec3dFloatFloatList(
      Matrix4f matrix4f, BufferBuilder bufferBuilder, float value, float value2, Vec3d vec3d2, float value3, float value4, List list
   ) {
      int i = list.size();
      if (i >= 2) {
         Vec3d vec3d = ((Vec3d)list.getFirst()).subtract(vec3d2);
         float f = (float)vec3d.x;
         float f1 = (float)vec3d.y;
         float f2 = (float)vec3d.z;

         for (int j = 1; j < i; j++) {
            Vec3d vec3d1 = ((Vec3d)list.get(j)).subtract(vec3d2);
            float f3 = (float)vec3d1.x;
            float f4 = (float)vec3d1.y;
            float f5 = (float)vec3d1.z;
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(bufferBuilder, value2, f4, value3, value, f, value4, f5, f1, f2, matrix4f, f3);
            f = f3;
            f1 = f4;
            f2 = f5;
         }
      }
   }

   private void onVec3dListMatrix4f(Vec3d vec3d, List<TrajectoryPath> list2, Matrix4f matrix4f) {
      float[] afloat = DepthState.getFloatArrayByInt(this.colorLines.getInt3());
      BufferBuilder bufferbuilder = DepthState.getBufferBuilderByFloat(1.0F);

      for (TrajectoryPath trajectorypath : list2) {
         float f4 = afloat[0];
         float f5 = afloat[1];
         float f6 = afloat[2];
         float f3 = afloat[3];
         float f2 = f6;
         float f1 = f5;
         float f = f4;
         List listx = trajectorypath.list;
         onMatrix4fBufferBuilderFloatFloatVec3dFloatFloatList(matrix4f, bufferbuilder, f1, f2, vec3d, f3, f, listx);
      }

      DepthState.onBufferBuilder(bufferbuilder);
   }

   @Override
   public void onEnable() {
      this.time = System.nanoTime();
      this.value236 = 0.0F;
      this.list2.clear();
      this.list3.clear();
      this.list4.clear();
      this.value235 = Integer.MIN_VALUE;
   }
}
