package client.module.render;

import client.data.AxisSwitchMap;
import client.enums.TrackedItem;
import client.module.Category;
import client.module.Module;
import client.render.DepthState;
import client.render.WorldRenderContext;
import client.setting.ActionSetting;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.ColorToggleSetting;
import client.setting.CompactGroupSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.util.SphereItems;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;

public class ItemRadius extends Module {
   private static final Pattern pattern = Pattern.compile("бульдозер\\s*(iii|ii|i|\\d+)?", 66);
   private static final List<String> list = List.of(
      TrackedItem.ENDER_EYE.text,
      TrackedItem.SUGAR.text,
      TrackedItem.FIRE_CHARGE.text,
      TrackedItem.BOZHA_AURA.text,
      TrackedItem.NETHERITE_SCRAP.text,
      TrackedItem.DRIED_KELP.text,
      "Бульдозер"
   );
   private MultilistSetting items;
   private ColorSetting dezka;
   private ColorSetting yavka;
   private ColorSetting ognennyyZaryad;
   private ColorSetting bozhyaAura;
   private ColorSetting trapka;
   private ColorSetting plast;
   private ColorSetting buldozer;
   private ColorToggleSetting podsvetkaPopadaniya;
   private BooleanSetting zapolnenie;
   private BooleanSetting skinDrakona;
   private ActionSetting opredelitSkin;
   private int value235;
   private int value236;
   private float value237;
   private boolean flag;
   private int value238;

   public ItemRadius() {
      super("ItemRadius", Category.RENDER);
      MultilistSetting multilistsetting = new MultilistSetting("", "", list, list);
      multilistsetting.setName("Предметы");
      multilistsetting.setDescription("Для каких предметов рисовать радиус");
      this.items = multilistsetting;
      ColorSetting colorsetting = new ColorSetting("", "", -16755456, true);
      colorsetting.setName("Дезка");
      colorsetting.setDescription("Цвет радиуса дезки");
      this.dezka = colorsetting;
      ColorSetting colorsetting1 = new ColorSetting("", "", -6710887, true);
      colorsetting1.setName("Явка");
      colorsetting1.setDescription("Цвет радиуса явки");
      this.yavka = colorsetting1;
      ColorSetting colorsetting2 = new ColorSetting("", "", -11206656, true);
      colorsetting2.setName("Огненный заряд");
      colorsetting2.setDescription("Цвет радиуса заряда");
      this.ognennyyZaryad = colorsetting2;
      ColorSetting colorsetting3 = new ColorSetting("", "", -16737895, true);
      colorsetting3.setName("Божья аура");
      colorsetting3.setDescription("Цвет радиуса ауры");
      this.bozhyaAura = colorsetting3;
      ColorSetting colorsetting4 = new ColorSetting("", "", -7650029, true);
      colorsetting4.setName("Трапка");
      colorsetting4.setDescription("Цвет куба трапки");
      this.trapka = colorsetting4;
      ColorSetting colorsetting5 = new ColorSetting("", "", -13421773, true);
      colorsetting5.setName("Пласт");
      colorsetting5.setDescription("Цвет пласта");
      this.plast = colorsetting5;
      ColorSetting colorsetting6 = new ColorSetting("", "", -1, true);
      colorsetting6.setName("Бульдозер");
      colorsetting6.setDescription("Цвет превью бульдозера");
      this.buldozer = colorsetting6;
      ColorToggleSetting colortogglesetting = new ColorToggleSetting("", "", true, -16711800, true);
      colortogglesetting.setName("Подсветка попадания");
      colortogglesetting.setDescription("Показывать подсветку при попадании игроков в зону");
      this.podsvetkaPopadaniya = colortogglesetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Заполнение");
      booleansetting.setDescription("Заливка фигур");
      this.zapolnenie = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Скин дракона");
      booleansetting1.setDescription("Использовать форму трапки/пласта для драконьего скина");
      this.skinDrakona = booleansetting1;
      ActionSetting actionsetting = new ActionSetting("", "");
      actionsetting.setName("Определить скин");
      actionsetting.setDescription("Найти трапку в инвентаре и выставить скин по lore");
      this.opredelitSkin = actionsetting;
      this.value235 = 0;
      this.value236 = 0;
      this.value237 = 0.0F;
      this.flag = false;
      this.value238 = -1;
      IdentityHashMap identityhashmap = new IdentityHashMap();
      identityhashmap.put(this.dezka, Items.ENDER_EYE);
      identityhashmap.put(this.yavka, Items.SUGAR);
      identityhashmap.put(this.ognennyyZaryad, Items.FIRE_CHARGE);
      identityhashmap.put(this.bozhyaAura, Items.PHANTOM_MEMBRANE);
      identityhashmap.put(this.trapka, Items.NETHERITE_SCRAP);
      identityhashmap.put(this.plast, Items.DRIED_KELP);
      identityhashmap.put(this.buldozer, Items.NETHERITE_PICKAXE);
      this.dezka.setVisibleWhen(this::getBoolean7);
      this.yavka.setVisibleWhen(this::getBoolean4);
      this.ognennyyZaryad.setVisibleWhen(this::getBoolean);
      this.bozhyaAura.setVisibleWhen(this::getBoolean5);
      this.trapka.setVisibleWhen(this::getBoolean3);
      this.plast.setVisibleWhen(this::getBoolean2);
      this.buldozer.setVisibleWhen(this::getBoolean6);
      CompactGroupSetting compactgroupsetting1 = new CompactGroupSetting(
         "", "", this.dezka, this.yavka, this.ognennyyZaryad, this.bozhyaAura, this.trapka, this.plast, this.buldozer
      );
      compactgroupsetting1.setName("Цвета");
      compactgroupsetting1.setDescription("Цвет радиуса для каждого предмета.");
      CompactGroupSetting compactgroupsetting = compactgroupsetting1;
      compactgroupsetting.getCompactGroupSettingByFunction2(p0 -> ItemRadius.getItemStackByIdentityHashMapSetting(identityhashmap, p0));
      this.addSettings(new Setting[]{this.items, compactgroupsetting, this.podsvetkaPopadaniya, this.zapolnenie, this.skinDrakona, this.opredelitSkin});
      this.opredelitSkin.setRunnable(this::update11);
   }

   private Boolean getBoolean() {
      return this.items.isString2(TrackedItem.FIRE_CHARGE.text);
   }

   private static boolean isPlayerEntityPlayerEntity(PlayerEntity playerEntity, PlayerEntity playerEntity2) {
      return playerEntity2 == playerEntity || !playerEntity2.isAlive() || playerEntity2.isSpectator() || playerEntity2.isInvisible() || playerEntity2.isInvisibleTo(playerEntity);
   }

   private Boolean getBoolean2() {
      return this.items.isString2(TrackedItem.DRIED_KELP.text);
   }

   private void update11() {
      PlayerEntity playerentity = this.player();
      if (playerentity != null) {
         ItemStack itemstack = getItemStackByPlayerEntity(playerentity);
         if (itemstack != null) {
            boolean flagx = isItemStack(itemstack);
            this.skinDrakona.setBoolean(flagx);
         }
      }
   }

   private static int getIntByItemStack(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.isIn(ItemTags.PICKAXES)) {
         int i = 0;
         LoreComponent lorecomponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
         if (lorecomponent != null) {
            for (Text text : lorecomponent.lines()) {
               i = Math.max(i, getIntByString(text.getString()));
            }
         }

         Text text1 = (Text)itemStack.get(DataComponentTypes.CUSTOM_NAME);
         if (text1 != null) {
            i = Math.max(i, getIntByString(text1.getString()));
         }

         return i;
      } else {
         return 0;
      }
   }

   private Boolean getBoolean3() {
      return this.items.isString2(TrackedItem.NETHERITE_SCRAP.text);
   }

   @Override
   public void onDisable() {
      this.update12();
   }

   private Boolean getBoolean4() {
      return this.items.isString2(TrackedItem.SUGAR.text);
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!this.notInGame()) {
         PlayerEntity playerentity = this.player();
         if (playerentity != null) {
            float f = worldRenderContext.getRenderTickCounter().getTickDelta(false);
            MatrixStack matrixstack = worldRenderContext.getMatrixStack();
            Vec3d vec3d = worldRenderContext.getCamera().getPos();
            int i = this.getIntByPlayerEntity(playerentity);
            if (i == -1) {
               this.value238 = -1;
            } else {
               switch (i) {
                  case 0:
                     int j = this.dezka.getInt3();
                     byte b0 = 0;
                     double d0 = 10.0;
                     this.onIntIntDoubleFloatPlayerEntityVec3dMatrixStack(j, b0, d0, f, playerentity, vec3d, matrixstack);
                     break;
                  case 1:
                     int k = this.yavka.getInt3();
                     byte b1 = 1;
                     double d1 = 10.0;
                     this.onIntIntDoubleFloatPlayerEntityVec3dMatrixStack(k, b1, d1, f, playerentity, vec3d, matrixstack);
                     break;
                  case 2:
                     int l = this.ognennyyZaryad.getInt3();
                     byte b2 = 2;
                     double d2 = 10.0;
                     this.onIntIntDoubleFloatPlayerEntityVec3dMatrixStack(l, b2, d2, f, playerentity, vec3d, matrixstack);
                     break;
                  case 3:
                     int i1 = this.bozhyaAura.getInt3();
                     byte b3 = 3;
                     double d3 = 2.0;
                     this.onIntIntDoubleFloatPlayerEntityVec3dMatrixStack(i1, b3, d3, f, playerentity, vec3d, matrixstack);
                     break;
                  case 4:
                     this.render(matrixstack, vec3d, f, playerentity);
                     break;
                  case 5:
                     this.onVec3dMatrixStackFloatPlayerEntity(vec3d, matrixstack, f, playerentity);
                     break;
                  case 6:
                     this.onVec3dFloatPlayerEntityMatrixStack(vec3d, f, playerentity, matrixstack);
               }
            }
         }
      }
   }

   private Boolean getBoolean5() {
      return this.items.isString2(TrackedItem.BOZHA_AURA.text);
   }

   private boolean isPlayerEntityPlayerEntity2(PlayerEntity playerEntity, PlayerEntity playerEntity2) {
      if (this.world() == null) {
         return false;
      } else {
         Vec3d vec3d = playerEntity2.getEyePos();
         Box box = playerEntity.getBoundingBox();
         Vec3d[] avec3d = new Vec3d[]{
            playerEntity.getEyePos(), box.getCenter(), new Vec3d(box.minX, box.getCenter().y, box.minZ), new Vec3d(box.maxX, box.getCenter().y, box.maxZ)
         };

         for (Vec3d vec3d1 : avec3d) {
            BlockHitResult blockhitresult = this.world().raycast(new RaycastContext(vec3d, vec3d1, ShapeType.COLLIDER, FluidHandling.NONE, playerEntity2));
            if (blockhitresult.getType() == Type.MISS) {
               return true;
            }
         }

         return false;
      }
   }

   private static Vec3d getVec3dByPlayerEntityFloat(PlayerEntity playerEntity, float value) {
      return new Vec3d(
         MathHelper.lerp(value, playerEntity.prevX, playerEntity.getX()), MathHelper.lerp(value, playerEntity.prevY, playerEntity.getY()), MathHelper.lerp(value, playerEntity.prevZ, playerEntity.getZ())
      );
   }

   private boolean isPlayerEntityList(PlayerEntity playerEntity, List<BlockPos> list) {
      if (this.clientWorld() == null) {
         return false;
      } else {
         for (PlayerEntity playerentity : this.clientWorld().getPlayers()) {
            if (!isPlayerEntityPlayerEntity(playerEntity, playerentity)) {
               BlockPos blockpos = playerentity.getBlockPos();

               for (BlockPos blockpos1 : list) {
                  if (blockpos1.equals(blockpos)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   private static int getIntByFloatIntInt(float value, int count, int count2) {
      int i = count2 >> 24 & 0xFF;
      int j = count2 >> 16 & 0xFF;
      int k = count2 >> 8 & 0xFF;
      int l = count2 & 0xFF;
      int i1 = count >> 24 & 0xFF;
      int j1 = count >> 16 & 0xFF;
      int k1 = count >> 8 & 0xFF;
      int l1 = count & 0xFF;
      return (int)(i + (i1 - i) * value) << 24 | (int)(j + (j1 - j) * value) << 16 | (int)(k + (k1 - k) * value) << 8 | (int)(l + (l1 - l) * value);
   }

   private static int getIntByIntInt(int count, int count2) {
      return count << 24 | count2 & 16777215;
   }

   private boolean isDoubleVec3dPlayerEntity(double value, Vec3d vec3d, PlayerEntity playerEntity) {
      if (this.clientWorld() == null) {
         return false;
      } else {
         double d0 = value * value;

         for (PlayerEntity playerentity : this.clientWorld().getPlayers()) {
            if (!isPlayerEntityPlayerEntity(playerEntity, playerentity)
               && this.isPlayerEntityPlayerEntity2(playerentity, playerEntity)
               && playerentity.getPos().squaredDistanceTo(vec3d) <= d0) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isPlayerEntityBox(PlayerEntity playerEntity, Box box) {
      if (this.clientWorld() == null) {
         return false;
      } else {
         for (PlayerEntity playerentity : this.clientWorld().getPlayers()) {
            if (!isPlayerEntityPlayerEntity(playerEntity, playerentity)
               && this.isPlayerEntityPlayerEntity2(playerentity, playerEntity)
               && playerentity.getBoundingBox().intersects(box)) {
               return true;
            }
         }

         return false;
      }
   }

   private void onString2(String text2) {
      PlayerEntity playerentity = this.player();
      if (playerentity != null) {
         playerentity.sendMessage(Text.literal(text2), false);
      }
   }

   private static ItemStack getItemStackByIdentityHashMapSetting(IdentityHashMap identityHashMap, Setting setting2) {
      Item item = (Item)identityHashMap.get(setting2);
      return item != null ? new ItemStack(item) : ItemStack.EMPTY;
   }

   private Boolean getBoolean6() {
      return this.items.isString2("Бульдозер");
   }

   private void onIntListIntMatrixStackVec3d(int count, List<BlockPos> list, int count2, MatrixStack matrixStack, Vec3d vec3d) {
      VoxelShape voxelshape = VoxelShapes.empty();

      for (BlockPos blockpos : list) {
         voxelshape = VoxelShapes.union(voxelshape, VoxelShapes.cuboid(new Box(blockpos)));
      }

      for (Box box : voxelshape.simplify().getBoundingBoxes()) {
         this.onIntMatrixStackIntBoxVec3d(count2, matrixStack, count, box, vec3d);
      }
   }

   private static float getFloatByFloat(float value) {
      float f = MathHelper.wrapDegrees(value);
      return f < 0.0F ? f + 360.0F : f;
   }

   private static Integer getIntegerByFloat(float value) {
      for (int i : new int[]{45, 135, 225, 315}) {
         if (Math.abs(value - i) < 22.0F) {
            return i;
         }
      }

      return null;
   }

   private static ItemStack getItemStackByPlayerEntity(PlayerEntity playerEntity) {
      for (int i = 0; i < playerEntity.getInventory().size(); i++) {
         ItemStack itemstack = playerEntity.getInventory().getStack(i);
         TrackedItem trackeditem = TrackedItem.NETHERITE_SCRAP;
         if (isTrackedItemItemStack(trackeditem, itemstack)) {
            return itemstack;
         }
      }

      return null;
   }

   private static boolean isItemStack(ItemStack itemStack) {
      Text text = (Text)itemStack.get(DataComponentTypes.CUSTOM_NAME);
      if (text != null && text.getString().toLowerCase(Locale.ROOT).contains("дракон")) {
         return true;
      } else {
         LoreComponent lorecomponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
         if (lorecomponent != null) {
            for (Text text1 : lorecomponent.lines()) {
               if (text1.getString().toLowerCase(Locale.ROOT).contains("дракон")) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private void onIntIntDoubleFloatPlayerEntityVec3dMatrixStack(int count, int count2, double value, float value2, PlayerEntity playerEntity, Vec3d vec3d2, MatrixStack matrixStack) {
      Vec3d vec3d = getVec3dByPlayerEntityFloat(playerEntity, value2);
      Vec3d vec3d1 = new Vec3d(vec3d.x, vec3d.y + playerEntity.getHeight() - 1.4, vec3d.z);
      boolean flagx = this.isDoubleVec3dPlayerEntity(value, vec3d1, playerEntity);
      this.onIntFloatBooleanInt(count2, value2, flagx, count);
      int i = this.value236;
      this.onVec3dVec3dMatrixStackIntDouble(vec3d2, vec3d1, matrixStack, i, value);
      if (this.zapolnenie.isFlag3()) {
         int j = this.value235;
         this.onIntDoubleVec3dVec3dMatrixStack(j, value, vec3d2, vec3d1, matrixStack);
      }
   }

   private void onVec3dVec3dMatrixStackIntDouble(Vec3d vec3d, Vec3d vec3d2, MatrixStack matrixStack, int count, double value) {
      float[] afloat = DepthState.getFloatArrayByInt(count);
      float f = (float)(vec3d2.x - vec3d.x);
      float f1 = (float)(vec3d2.y - vec3d.y);
      float f2 = (float)(vec3d2.z - vec3d.z);
      float f3 = (float)value;
      BufferBuilder bufferbuilder = DepthState.getBufferBuilderByFloat(0.5F);
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      float f4 = f;
      float f5 = f2 - f3;

      for (float f6 = 5.0F; f6 <= 360.0F; f6 += 5.0F) {
         float f7 = (float)Math.toRadians(f6);
         float f8 = f + MathHelper.sin(f7) * f3;
         float f9 = f2 - MathHelper.cos(f7) * f3;
         float f14 = afloat[0];
         float f15 = afloat[1];
         float f16 = afloat[2];
         float f13 = afloat[3];
         float f12 = f16;
         float f11 = f15;
         float f10 = f14;
         DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(
            bufferbuilder, f12, f1, f13, f11, f4, f10, f9, f1, f5, matrix4f, f8
         );
         f4 = f8;
         f5 = f9;
      }

      DepthState.onBufferBuilder(bufferbuilder);
   }

   private void onIntDoubleVec3dVec3dMatrixStack(int count, double value, Vec3d vec3d, Vec3d vec3d2, MatrixStack matrixStack) {
      float[] afloat = DepthState.getFloatArrayByInt(count);
      float f = (float)(vec3d2.x - vec3d.x);
      float f1 = (float)(vec3d2.y - vec3d.y);
      float f2 = (float)(vec3d2.z - vec3d.z);
      float f3 = (float)value;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.disableDepthTest();
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      bufferbuilder.vertex(matrix4f, f, f1, f2).color(afloat[0], afloat[1], afloat[2], afloat[3]);

      for (float f4 = 0.0F; f4 <= 360.0F; f4 += 5.0F) {
         float f5 = (float)Math.toRadians(f4);
         float f6 = f + MathHelper.sin(f5) * f3;
         float f7 = f2 - MathHelper.cos(f5) * f3;
         bufferbuilder.vertex(matrix4f, f6, f1, f7).color(afloat[0], afloat[1], afloat[2], afloat[3]);
      }

      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      RenderSystem.enableDepthTest();
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   private void render(MatrixStack matrixStack, Vec3d vec3d, float value, PlayerEntity playerEntity) {
      Box box = this.getBoxByPlayerEntity(playerEntity);
      int l = this.trapka.getInt3();
      boolean flagx = this.isPlayerEntityBox(playerEntity, box);
      int i = l;
      byte b0 = 4;
      this.onIntFloatBooleanInt(b0, value, flagx, i);
      int k = this.value235;
      int j = this.value236;
      this.onIntMatrixStackIntBoxVec3d(j, matrixStack, k, box, vec3d);
   }

   private Box getBoxByPlayerEntity(PlayerEntity playerEntity) {
      BlockPos blockpos = playerEntity.getBlockPos();
      boolean flagx = this.skinDrakona.isFlag3();
      return flagx
         ? new Box(blockpos.getX() - 2.0, blockpos.getY(), blockpos.getZ() - 2.0, blockpos.getX() + 3.0, blockpos.getY() + 5.0, blockpos.getZ() + 3.0)
         : new Box(blockpos.getX() - 1.0, blockpos.getY(), blockpos.getZ() - 1.0, blockpos.getX() + 2.0, blockpos.getY() + 3.0, blockpos.getZ() + 2.0);
   }

   private void onVec3dMatrixStackFloatPlayerEntity(Vec3d vec3d, MatrixStack matrixStack, float value, PlayerEntity playerEntity) {
      boolean flagx = this.skinDrakona.isFlag3();
      BlockPos blockpos = playerEntity.getBlockPos();
      float f = playerEntity.getPitch();
      float f1 = getFloatByFloat(playerEntity.getYaw());
      if (Math.abs(f) <= 45.0F) {
         Integer integer = getIntegerByFloat(f1);
         if (integer != null) {
            int i = integer;
            List listx = getListByBlockPosBooleanInt(blockpos, flagx, i);
            int i1 = this.plast.getInt3();
            boolean flag1 = this.isPlayerEntityList(playerEntity, listx);
            int j = i1;
            byte b0 = 5;
            this.onIntFloatBooleanInt(b0, value, flag1, j);
            int l = this.value235;
            int k = this.value236;
            this.onIntListIntMatrixStackVec3d(l, listx, k, matrixStack, vec3d);
            return;
         }

         Box box = getBoxByBooleanFloatBlockPos(flagx, f1, blockpos);
         this.render(matrixStack, box, vec3d, value, playerEntity);
      } else if (f < -45.0F) {
         Box box1 = getBoxByBlockPosBoolean(blockpos, flagx);
         this.render(matrixStack, box1, vec3d, value, playerEntity);
      } else {
         Box box2 = getBoxByBooleanBlockPos(flagx, blockpos);
         this.render(matrixStack, box2, vec3d, value, playerEntity);
      }
   }

   private void update12() {
      this.value238 = -1;
      this.value237 = 0.0F;
      this.flag = false;
   }

   public boolean check3() {
      return this.skinDrakona.isFlag3();
   }

   private int getIntByPlayerEntity(PlayerEntity playerEntity) {
      ItemStack itemstack = playerEntity.getMainHandStack();
      int i = getIntByItemStack(itemstack);
      if (i > 0 && this.items.isString2("Бульдозер")) {
         return 6;
      } else {
         int j = this.getIntByItemStack2(itemstack);
         return j != -1 ? j : this.getIntByItemStack2(playerEntity.getOffHandStack());
      }
   }

   private int getIntByItemStack2(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         TrackedItem trackeditem = TrackedItem.ENDER_EYE;
         if (isTrackedItemItemStack(trackeditem, itemStack) && this.items.isString2(TrackedItem.ENDER_EYE.text)) {
            return 0;
         } else {
            TrackedItem trackeditem1 = TrackedItem.SUGAR;
            if (isTrackedItemItemStack(trackeditem1, itemStack) && this.items.isString2(TrackedItem.SUGAR.text)) {
               return 1;
            } else {
               TrackedItem trackeditem2 = TrackedItem.FIRE_CHARGE;
               if (isTrackedItemItemStack(trackeditem2, itemStack) && this.items.isString2(TrackedItem.FIRE_CHARGE.text)) {
                  return 2;
               } else {
                  TrackedItem trackeditem3 = TrackedItem.BOZHA_AURA;
                  if (isTrackedItemItemStack(trackeditem3, itemStack) && this.items.isString2(TrackedItem.BOZHA_AURA.text)) {
                     return 3;
                  } else {
                     TrackedItem trackeditem4 = TrackedItem.NETHERITE_SCRAP;
                     if (isTrackedItemItemStack(trackeditem4, itemStack) && this.items.isString2(TrackedItem.NETHERITE_SCRAP.text)) {
                        return 4;
                     } else {
                        TrackedItem trackeditem5 = TrackedItem.DRIED_KELP;
                        return isTrackedItemItemStack(trackeditem5, itemStack) && this.items.isString2(TrackedItem.DRIED_KELP.text) ? 5 : -1;
                     }
                  }
               }
            }
         }
      } else {
         return -1;
      }
   }

   private static boolean isTrackedItemItemStack(TrackedItem trackedItem, ItemStack itemStack) {
      if (itemStack.getItem() != trackedItem.item) {
         return false;
      } else if (trackedItem.text5 == null) {
         return true;
      } else {
         String s = trackedItem.text5;
         return SphereItems.isStringItemStack(s, itemStack);
      }
   }

   private void onVec3dFloatPlayerEntityMatrixStack(Vec3d vec3d, float value, PlayerEntity playerEntity, MatrixStack matrixStack) {
      MinecraftClient minecraftclient = this.client();
      if (minecraftclient != null && minecraftclient.crosshairTarget instanceof BlockHitResult blockhitresult) {
         if (blockhitresult.getType() == Type.BLOCK) {
            int l1 = getIntByItemStack(playerEntity.getMainHandStack());
            if (l1 > 0) {
               int i = l1 >= 2 ? 2 : 1;
               Axis axis = blockhitresult.getSide().getAxis();
               Direction direction = blockhitresult.getSide().getOpposite();
               BlockPos blockpos = blockhitresult.getBlockPos();
               int i2 = this.buldozer.getInt3();
               boolean flagx = false;
               int i1 = i2;
               byte b0 = 6;
               this.onIntFloatBooleanInt(b0, value, flagx, i1);

               for (int j = 0; j < i; j++) {
                  BlockPos blockpos1 = blockpos.offset(direction, j);

                  for (int k = -1; k <= 1; k++) {
                     for (int l = -1; l <= 1; l++) {
                        BlockPos blockpos2 = getBlockPosByIntBlockPosAxisInt(k, blockpos1, axis, l);
                        Box box = new Box(blockpos2);
                        int j2 = this.value236;
                        int k1 = this.zapolnenie.isFlag3() ? this.value235 : 0;
                        int j1 = j2;
                        this.onIntMatrixStackIntBoxVec3d(j1, matrixStack, k1, box, vec3d);
                     }
                  }
               }
            }
         }
      }
   }

   private static BlockPos getBlockPosByIntBlockPosAxisInt(int count, BlockPos blockPos, Axis axis, int count2) {
      return switch (AxisSwitchMap.intArray[axis.ordinal()]) {
         case 1 -> blockPos.add(count, 0, count2);
         case 2 -> blockPos.add(0, count, count2);
         case 3 -> blockPos.add(count, count2, 0);
         default -> throw new MatchException(null, null);
      };
   }

   private static int getIntByString(String text2) {
      java.util.regex.Matcher matcher = pattern.matcher(text2);
      if (!matcher.find()) {
         return 0;
      }

      String s = matcher.group(1);
      if (s == null || s.isEmpty()) {
         return 1;
      }

      switch (s.toLowerCase(java.util.Locale.ROOT)) {
         case "i":
            return 1;
         case "ii":
            return 2;
         case "iii":
            return 3;
         default:
            try {
               return Integer.parseInt(s);
            } catch (NumberFormatException numberformatexception) {
               return 0;
            }
      }
   }

   private void onIntMatrixStackIntBoxVec3d(int count, MatrixStack matrixStack, int count2, Box box, Vec3d vec3d2) {
      Vec3d vec3d = new Vec3d(box.minX - vec3d2.x, box.minY - vec3d2.y, box.minZ - vec3d2.z);
      Vec3d vec3d1 = new Vec3d(box.maxX - vec3d2.x, box.maxY - vec3d2.y, box.maxZ - vec3d2.z);
      float[] afloat = DepthState.getFloatArrayByInt(count);
      float f = 0.01F;
      DepthState.update2();
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      float f15 = (float)vec3d.x;
      float f16 = (float)vec3d.y;
      float f17 = (float)vec3d.z;
      float f18 = (float)vec3d1.x;
      float f19 = (float)vec3d1.y;
      float f20 = (float)vec3d1.z;
      float f21 = afloat[0];
      float f22 = afloat[1];
      float f23 = afloat[2];
      float f10 = afloat[3];
      float f9 = f23;
      float f8 = f22;
      float f7 = f21;
      float f6 = f20;
      float f5 = f19;
      float f4 = f18;
      float f3 = f17;
      float f2 = f16;
      float f1 = f15;
      DepthState.onFloatFloatFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloatFloatFloat(
         f4, f10, f8, f2, matrix4f, f1, f3, f5, bufferbuilder, f9, f, f6, f7
      );
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      DepthState.update();
      if (count2 != 0 && this.zapolnenie.isFlag3()) {
         float[] afloat1 = DepthState.getFloatArrayByInt(count2);
         f16 = afloat1[0];
         f17 = afloat1[1];
         f18 = afloat1[2];
         f19 = afloat1[3];
         boolean flagx = false;
         float f14 = f19;
         float f13 = f18;
         float f12 = f17;
         float f11 = f16;
         DepthState.render(matrixStack, f13, f11, vec3d, flagx, f12, vec3d1, f14);
      }
   }

   private void onIntFloatBooleanInt(int count, float value, boolean flag2, int count2) {
      if (this.value238 != count) {
         this.value238 = count;
         this.flag = false;
         this.value237 = 0.0F;
         byte b0 = 85;
         this.value235 = getIntByIntInt(b0, count2);
         short short1 = 255;
         this.value236 = getIntByIntInt(short1, count2);
      }

      boolean flagx = this.podsvetkaPopadaniya.check() && flag2;
      int i = this.podsvetkaPopadaniya.check() ? this.podsvetkaPopadaniya.getInt() : count2;
      if (flagx != this.flag) {
         this.value237 = 0.0F;
         this.flag = flagx;
      }

      int l1 = flagx ? i : count2;
      byte b1 = 85;
      int l = l1;
      int j = getIntByIntInt(b1, l);
      l1 = flagx ? i : count2;
      short short2 = 255;
      int i1 = l1;
      int k = getIntByIntInt(short2, i1);
      this.value237 = Math.min(this.value237 + value / 0.5F, 1.0F);
      float f = this.value237;
      int j1 = this.value235;
      this.value235 = getIntByFloatIntInt(f, j, j1);
      float f1 = this.value237;
      int k1 = this.value236;
      this.value236 = getIntByFloatIntInt(f1, k, k1);
   }

   private void render(MatrixStack matrixStack, Box box2, Vec3d vec3d, float value, PlayerEntity playerEntity) {
      Box box = box2.contract(0.01, 0.01, 0.01);
      int l = this.plast.getInt3();
      boolean flagx = this.isPlayerEntityBox(playerEntity, box2);
      int i = l;
      byte b0 = 5;
      this.onIntFloatBooleanInt(b0, value, flagx, i);
      int k = this.value235;
      int j = this.value236;
      this.onIntMatrixStackIntBoxVec3d(j, matrixStack, k, box, vec3d);
   }

   private static Box getBoxByBooleanFloatBlockPos(boolean flag, float value, BlockPos blockPos) {
      double d0 = flag ? -3.0 : -2.0;
      double d1 = flag ? 4.0 : 3.0;
      double d2 = flag ? 6.0 : 4.0;
      if (value >= 315.0F || value < 45.0F) {
         return new Box(blockPos.getX() + d0, blockPos.getY() - 1, blockPos.getZ() + 2.0, blockPos.getX() + d1, blockPos.getY() + d2, blockPos.getZ() + 4.0);
      } else if (value < 135.0F) {
         return new Box(blockPos.getX() - 3.0, blockPos.getY() - 1, blockPos.getZ() + d0, blockPos.getX() - 1.0, blockPos.getY() + d2, blockPos.getZ() + d1);
      } else {
         return value < 225.0F
            ? new Box(blockPos.getX() + d0, blockPos.getY() - 1, blockPos.getZ() - 3.0, blockPos.getX() + d1, blockPos.getY() + d2, blockPos.getZ() - 1.0)
            : new Box(blockPos.getX() + 2.0, blockPos.getY() - 1, blockPos.getZ() + d0, blockPos.getX() + 4.0, blockPos.getY() + d2, blockPos.getZ() + d1);
      }
   }

   private static Box getBoxByBlockPosBoolean(BlockPos blockPos, boolean flag) {
      double d0 = flag ? -3.0 : -2.0;
      double d1 = flag ? 4.0 : 3.0;
      double d2 = 3.0;
      double d3 = flag ? 7.0 : 5.0;
      return new Box(blockPos.getX() + d0, blockPos.getY() + d2, blockPos.getZ() + d0, blockPos.getX() + d1, blockPos.getY() + d3, blockPos.getZ() + d1);
   }

   private static List getListByBlockPosBooleanInt(BlockPos blockPos, boolean flag, int count) {
      byte b0;
      byte b1;
      boolean flagx;
      switch (count) {
         case 45:
            b0 = -3;
            b1 = 2;
            flagx = false;
            break;
         case 135:
            b0 = -3;
            b1 = -2;
            flagx = true;
            break;
         case 225:
            b0 = 2;
            b1 = -2;
            flagx = false;
            break;
         default:
            b0 = 2;
            b1 = 2;
            flagx = true;
      }

      BlockPos blockpos = blockPos.add(b0, -1, b1);
      int i = flag ? -3 : -2;
      int j = flag ? 3 : 2;
      int k = flag ? 7 : 5;
      ArrayList arraylist = new ArrayList((j - i + 1) * k * 2);

      for (int l = i; l <= j; l++) {
         int i1 = flagx ? -l : l;

         for (int j1 = 0; j1 < k; j1++) {
            arraylist.add(blockpos.add(l, j1, i1));
            arraylist.add(blockpos.add(l + 1, j1, i1));
         }
      }

      return arraylist;
   }

   private static Box getBoxByBooleanBlockPos(boolean flag, BlockPos blockPos) {
      double d0 = flag ? -3.0 : -2.0;
      double d1 = flag ? 4.0 : 3.0;
      double d2 = -3.0;
      return new Box(blockPos.getX() + d0, blockPos.getY() + d2, blockPos.getZ() + d0, blockPos.getX() + d1, blockPos.getY() + 1, blockPos.getZ() + d1);
   }

   @Override
   public void onEnable() {
      this.update12();
   }

   private Boolean getBoolean7() {
      return this.items.isString2(TrackedItem.ENDER_EYE.text);
   }
}
