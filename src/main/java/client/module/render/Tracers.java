package client.module.render;

import client.data.TracerEntry;
import client.module.Category;
import client.module.Module;
import client.module.player.Protect;
import client.render.ArrowTextureLoader;
import client.render.IconAtlas;
import client.render.TextShader;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.ItemPickupMath;
import client.util.StringParts;
import client.util.UnsafeAccess;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class Tracers extends Module {
   private ListSetting stilStrelki;
   private ColorSetting colorStrelokEnemies;
   private ColorSetting colorStrelokFriends;
   private MultilistSetting targets;
   private SliderSetting maxRange;
   private SliderSetting sizeStrelki;
   private SliderSetting radiusStrelok;
   private ListSetting showImena;
   private BooleanSetting showDistanciyu;
   private SliderSetting sizeTeksta;
   private static final UnsafeAccess<Protect> unsafeAccess = new UnsafeAccess<>(Protect.class);
   private final Map<UUID, TracerEntry> map;
   private final Set<UUID> set;
   private long time;
   private static final float value235 = 1.0F / (float)NANOS_PER_SECOND;
   private float value236;
   private float value237;
   private double value238;
   private double value239;
   private float value240;

   public Tracers() {
      super("Tracers", Category.RENDER);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"O", "б", "ы", "ч", "н", "ы", "е"}),
            StringParts.join(new String[]{"З", "a", "п", "о", "л", "н", "е", "н", "н", "ы", "е"})
         ),
         List.of(StringParts.join(new String[]{"O", "б", "ы", "ч", "н", "ы", "е"})),
         false
      );
      listsetting.setName("Стиль стрелки");
      listsetting.setDescription("Выбор между обычной и заполненной стрелкой");
      this.stilStrelki = listsetting;
      ColorSetting colorsetting = new ColorSetting("", "", -1, true);
      colorsetting.setName("Цвет стрелок — враги");
      colorsetting.setDescription("Цвет стрелок к врагам");
      this.colorStrelokEnemies = colorsetting;
      ColorSetting colorsetting1 = new ColorSetting("", "", -16738561, true);
      colorsetting1.setName("Цвет стрелок — друзья");
      colorsetting1.setDescription("Цвет стрелок к друзьям");
      this.colorStrelokFriends = colorsetting1;
      MultilistSetting multilistsetting = new MultilistSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"Д", "p", "у", "з", "ь", "я"}),
            StringParts.join(new String[]{"В", "р", "a", "г", "и"}),
            StringParts.join(new String[]{"В", "р", "a", "г", "и", " ", "т", "o", "л", "ь", "к", "о", " ", "в", " ", "б", "р", "o", "н", "е"}),
            StringParts.join(new String[]{"В", "р", "a", "г", "и", " ", "т", "o", "л", "ь", "к", "о", " ", "в", " ", "н", "e", "з", "е", "р", "и", "т", "е"})
         ),
         Arrays.asList(StringParts.join(new String[]{"Д", "p", "у", "з", "ь", "я"}), StringParts.join(new String[]{"В", "р", "a", "г", "и"}))
      );
      multilistsetting.setName("Цели");
      multilistsetting.setDescription("Кого отображать");
      this.targets = multilistsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 64.0, 8.0, 256.0, 8.0);
      slidersetting.setName("Макс. дистанция");
      slidersetting.setDescription("Максимальная дистанция отображения");
      this.maxRange = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 10.0, 5.0, 30.0, 1.0);
      slidersetting1.setName("Размер стрелки");
      slidersetting1.setDescription("Размер стрелок");
      this.sizeStrelki = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 40.0, 20.0, 100.0, 5.0);
      slidersetting2.setName("Радиус стрелок");
      slidersetting2.setDescription("Радиус круга стрелок");
      this.radiusStrelok = slidersetting2;
      listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"В", "ы", "к", "л", "ю", "ч", "e", "н", "о"}),
            StringParts.join(new String[]{"В", "c", "е"}),
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "д", "p", "у", "з", "ь", "я"}),
            StringParts.join(new String[]{"T", "о", "л", "ь", "к", "о", " ", "в", "р", "a", "г", "и"})
         ),
         List.of(StringParts.join(new String[]{"В", "ы", "к", "л", "ю", "ч", "e", "н", "о"})),
         false
      );
      listsetting.setName("Показывать имена");
      listsetting.setDescription("Чьи ники отображать");
      this.showImena = listsetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать дистанцию");
      booleansetting.setDescription("Отображать расстояние до игроков");
      this.showDistanciyu = booleansetting;
      SliderSetting slidersetting3 = new SliderSetting("", "", 6.4, 4.0, 12.0, 0.2);
      slidersetting3.setName("Размер текста");
      slidersetting3.setDescription("Размер шрифта");
      this.sizeTeksta = slidersetting3;
      this.map = new HashMap<>();
      this.set = new HashSet<>();
      this.value240 = 0.0F;
      this.sizeTeksta.setVisibleWhen(this::getBoolean);
      this.addSettings(
         new Setting[]{
            this.targets,
            this.maxRange,
            this.stilStrelki,
            this.colorStrelokEnemies,
            this.colorStrelokFriends,
            this.sizeStrelki,
            this.radiusStrelok,
            this.showDistanciyu,
            this.showImena,
            this.sizeTeksta
         }
      );
      this.time = System.nanoTime();
   }

   private boolean isPlayerEntity(PlayerEntity playerEntity) {
      ItemStack itemstack = playerEntity.getEquippedStack(EquipmentSlot.HEAD);
      ItemStack itemstack1 = playerEntity.getEquippedStack(EquipmentSlot.CHEST);
      ItemStack itemstack2 = playerEntity.getEquippedStack(EquipmentSlot.LEGS);
      ItemStack itemstack3 = playerEntity.getEquippedStack(EquipmentSlot.FEET);
      if (!itemstack.isOf(Items.NETHERITE_HELMET)) {
         return false;
      } else if (!itemstack2.isOf(Items.NETHERITE_LEGGINGS)) {
         return false;
      } else {
         return !itemstack3.isOf(Items.NETHERITE_BOOTS) ? false : itemstack1.isOf(Items.NETHERITE_CHESTPLATE) || itemstack1.isOf(Items.ELYTRA);
      }
   }

   private void render(DrawContext drawContext) {
      if (!this.map.isEmpty()) {
         int i = this.client().getWindow().getScaledWidth() / 2;
         int j = this.client().getWindow().getScaledHeight() / 2;
         float f = this.value236;
         float f1 = this.sizeStrelki.getValueAsFloat();
         float f2 = f1 * 0.5F;
         boolean flag = "Зaполненные".equals(this.stilStrelki.getString2());
         int k = this.colorStrelokFriends.getInt3();
         int l = this.colorStrelokEnemies.getInt3();
         MatrixStack matrixstack = drawContext.getMatrices();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
         RenderSystem.setShaderTexture(0, flag ? ArrowTextureLoader.identifier2 : ArrowTextureLoader.identifier);
         BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

         for (TracerEntry tracerentry : this.map.values()) {
            float f3 = tracerentry.value2;
            int i1 = tracerentry.flag ? k : l;
            int j1 = i1 & 16777215 | (int)((i1 >> 24 & 0xFF) * f3) << 24;
            float f4 = tracerentry.value;
            double d0 = Math.toRadians(f4);
            float f5 = (float)(i + Math.cos(d0) * f);
            float f6 = (float)(j + Math.sin(d0) * f);
            matrixstack.push();
            matrixstack.translate(f5, f6, 0.0F);
            matrixstack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f4 + 90.0F));
            Matrix4f matrix4f = matrixstack.peek().getPositionMatrix();
            if (flag) {
               float f8;
               float f7 = f8 = -f2;
               onFloatFloatIntBufferBuilderFloatFloatMatrix4f(f8, f1, j1, bufferbuilder, f7, f1, matrix4f);
            } else {
               float f10;
               float f9 = f10 = -f2;
               onBufferBuilderMatrix4fFloatFloatIntFloatFloat(bufferbuilder, matrix4f, f1, f10, j1, f9, f1);
            }

            matrixstack.pop();
         }

         BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
         RenderSystem.disableBlend();
         this.onIntFloatIntFloatMatrixStack(j, f2, i, f, matrixstack);
      }
   }

   @Override
   public void onDisable() {
      this.map.clear();
      this.set.clear();
      this.value240 = 0.0F;
   }

   private boolean isBooleanBooleanPlayerEntityBooleanBoolean(boolean flag, boolean flag2, PlayerEntity playerEntity, boolean flag3, boolean flag4) {
      if (flag4) {
         return this.isString("Друзья");
      } else if (!flag3 && !flag && !flag2) {
         return false;
      } else {
         return flag2 && !this.isPlayerEntity(playerEntity) ? false : !flag || flag2 || this.isPlayerEntity2(playerEntity);
      }
   }

   private boolean isPlayerEntity2(PlayerEntity playerEntity) {
      for (ItemStack itemstack : playerEntity.getArmorItems()) {
         if (!itemstack.isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private boolean isString(String text) {
      return this.targets.isString(text);
   }

   private static TracerEntry getTracerEntryByUUID(UUID uUID) {
      return new TracerEntry();
   }

   private Boolean getBoolean() {
      return !"Выключeно".equals(this.showImena.getString2());
   }

   private static void onBufferBuilderMatrix4fFloatFloatIntFloatFloat(
      BufferBuilder bufferBuilder, Matrix4f matrix4f, float value, float value2, int count, float value3, float value4
   ) {
      int i = count >> 24 & 0xFF;
      int j = count >> 16 & 0xFF;
      int k = count >> 8 & 0xFF;
      int l = count & 0xFF;
      float f = value3 + value4;
      float f1 = value2 + value;
      bufferBuilder.vertex(matrix4f, value3, value2, 0.0F).texture(0.0F, 0.0F).color(j, k, l, i);
      bufferBuilder.vertex(matrix4f, value3, f1, 0.0F).texture(0.0F, 1.0F).color(j, k, l, i);
      bufferBuilder.vertex(matrix4f, f, f1, 0.0F).texture(1.0F, 1.0F).color(j, k, l, i);
      bufferBuilder.vertex(matrix4f, f, value2, 0.0F).texture(1.0F, 0.0F).color(j, k, l, i);
   }

   public void render2(DrawContext drawContext) {
      if (this.isEnabled() && !this.notInGame()) {
         try {
            this.update11();
            this.render(drawContext);
         } catch (Exception exception) {
         }
      }
   }

   private void update11() {
      long i = System.nanoTime();
      float f = (float)(i - this.time) * value235;
      this.time = i;
      if (f > 0.1F) {
         f = 0.1F;
      }

      PlayerEntity playerentity = this.player();
      Vec3d vec3d = playerentity.getPos();
      double d0 = this.maxRange.getValue();
      float f1 = playerentity.getYaw();
      boolean flag = this.isString("Друзья");
      boolean flag1 = !"Выключeно".equals(this.showImena.getString2());
      double d1 = vec3d.x;
      double d2 = vec3d.z;
      Protect protect = (Protect)unsafeAccess.getModule2();
      if (this.value238 == 0.0 && this.value239 == 0.0) {
         this.value238 = d1;
         this.value239 = d2;
      }

      double d3 = d1 - this.value238;
      double d4 = d2 - this.value239;
      float f2 = (float)Math.sqrt(d3 * d3 + d4 * d4) / Math.max(f, 0.001F);
      this.value238 = d1;
      this.value239 = d2;
      this.value240 = MathHelper.lerp(f * 5.0F, this.value240, f2);
      float f3 = this.radiusStrelok.getValueAsFloat();
      float f4 = f3 - 15.0F;
      float f5 = f3 + 5.0F;
      float f6 = MathHelper.clamp(this.value240 / 8.0F, 0.0F, 1.0F);
      this.value237 = MathHelper.lerp(f6, f4, f5);
      this.value236 = MathHelper.lerp(f * 6.0F, this.value236, this.value237);
      this.set.clear();
      boolean flag2 = this.isString("Враги");
      boolean flag3 = this.isString("Враги только в броне");
      boolean flag4 = this.isString("Враги только в незерите");
      int j = playerentity.age;

      for (PlayerEntity playerentity1 : this.world().getPlayers()) {
         boolean flag6 = false;
         if (ItemPickupMath.isDoublePlayerEntityVec3dModuleBooleanBooleanPlayerEntity(d0, playerentity, vec3d, this, flag, flag6, playerentity1)) {
            boolean flag5 = this.isFriend(playerentity1);
            if (this.isBooleanBooleanPlayerEntityBooleanBoolean(flag3, flag4, playerentity1, flag2, flag5)) {
               UUID uuid = playerentity1.getUuid();
               this.set.add(uuid);
               TracerEntry tracerentry = this.map.computeIfAbsent(uuid, Tracers::getTracerEntryByUUID);
               Vec3d vec3d1 = playerentity1.getPos();
               double d5 = vec3d1.x - d1;
               double d6 = vec3d1.y - vec3d.y;
               double d7 = vec3d1.z - d2;
               double d8 = d5 * d5 + d7 * d7;
               double d9 = Math.sqrt(d8 + d6 * d6);
               if (!(d9 < 0.1)) {
                  float f7 = d8 < 0.01 ? tracerentry.value : (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(d7, d5)) - f1 + 180.0);
                  float f8 = f7 - tracerentry.value;
                  if (f8 > 180.0F) {
                     f8 -= 360.0F;
                  } else if (f8 < -180.0F) {
                     f8 += 360.0F;
                  }

                  tracerentry.value = MathHelper.wrapDegrees(tracerentry.value + f8 * f * 12.0F);
                  if (flag1 && (tracerentry.value3 != j || tracerentry.text.isEmpty())) {
                     tracerentry.value3 = j;
                     String s = playerentity1.getName().getString();
                     tracerentry.text = protect != null ? protect.getStringByString2(s) : s;
                  }

                  tracerentry.value4 = d9;
                  tracerentry.value5 = d6;
                  tracerentry.flag = flag5;
                  tracerentry.value2 = tracerentry.value2 + (1.0F - tracerentry.value2) * f * 10.0F;
                  if (tracerentry.value2 > 1.0F) {
                     tracerentry.value2 = 1.0F;
                  }
               }
            }
         }
      }

      Iterator iterator = this.map.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry entry = (Entry)iterator.next();
         if (!this.set.contains(entry.getKey())) {
            TracerEntry tracerentry1 = (TracerEntry)entry.getValue();
            tracerentry1.value2 = tracerentry1.value2 - tracerentry1.value2 * f * 10.0F;
            if (tracerentry1.value2 < 0.01F) {
               iterator.remove();
            }
         }
      }
   }

   private void onIntFloatIntFloatMatrixStack(int count, float value, int count2, float value2, MatrixStack matrixStack) {
      String s = this.showImena.getString2();
      boolean flag = this.showDistanciyu.isFlag3();
      boolean flag1 = !"Выключeно".equals(s);
      if (flag1 || flag) {
         float f = this.sizeTeksta.getValueAsFloat();
         float f1 = f - 2.0F;
         Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
         TextShader.update3();

         try {
            this.onFloatIntFloatIntBooleanFloatFloatMatrix4fBooleanString(f, count2, f1, count, flag, value, value2, matrix4f, flag1, s);
         } finally {
            TextShader.update();
         }
      }
   }

   private void onFloatIntFloatIntBooleanFloatFloatMatrix4fBooleanString(
      float value3, int count, float value6, int count2, boolean flag2, float value7, float value8, Matrix4f matrix4f, boolean flag3, String text2
   ) {
      for (TracerEntry tracerentry : this.map.values()) {
         float f = tracerentry.value2;
         double d0 = Math.toRadians(tracerentry.value);
         float f1 = (float)(count + Math.cos(d0) * value8);
         float f2 = (float)(count2 + Math.sin(d0) * value8);
         float f3 = f2 + value7;
         boolean flag = flag3
            && !tracerentry.text.isEmpty()
            && ("Вcе".equals(text2) || "Tолько дpузья".equals(text2) && tracerentry.flag || "Tолько врaги".equals(text2) && !tracerentry.flag);
         if (flag) {
            String s1 = tracerentry.text;
            IconAtlas iconatlas = icon;
            float f4 = TextShader.getFloatByFloatIconAtlasString(value3, iconatlas, s1);
            float f9 = f1 - f4 * 0.5F;
            byte b0 = -1;
            float f6 = f9;
            String s2 = tracerentry.text;
            IconAtlas iconatlas1 = icon;
            TextShader.onIconAtlasFloatStringIntMatrix4fFloatFloatFloat(iconatlas1, f6, s2, b0, matrix4f, value3, f3, f);
            f3 += value3 + 2.0F;
         }

         if (flag2) {
            String s3 = "";
            if (tracerentry.value5 > 3.0) {
               s3 = " ↑";
            } else if (tracerentry.value5 < -3.0) {
               s3 = " ↓";
            }

            String s = (int)tracerentry.value4 + "m" + s3;
            IconAtlas iconatlas2 = icon;
            float f5 = TextShader.getFloatByFloatIconAtlasString(value6, iconatlas2, s);
            float f8 = f1 - f5 * 0.5F;
            byte b1 = -1;
            float f7 = f8;
            IconAtlas iconatlas3 = icon;
            TextShader.onIconAtlasFloatStringIntMatrix4fFloatFloatFloat(iconatlas3, f7, s, b1, matrix4f, value6, f3, f);
         }
      }
   }

   private static void onFloatFloatIntBufferBuilderFloatFloatMatrix4f(
      float value, float value2, int count, BufferBuilder bufferBuilder, float value3, float value4, Matrix4f matrix4f
   ) {
      int i = count >> 24 & 0xFF;
      int j = count >> 16 & 0xFF;
      int k = count >> 8 & 0xFF;
      int l = count & 0xFF;
      float f = value3 + value2;
      float f1 = value + value4;
      int i1 = j * 102 >> 8;
      int j1 = k * 102 >> 8;
      int k1 = l * 102 >> 8;
      int l1 = j * 179 >> 8;
      int i2 = k * 179 >> 8;
      int j2 = l * 179 >> 8;
      bufferBuilder.vertex(matrix4f, value3, value, -1.0F).texture(0.0F, 0.0F).color(i1, j1, k1, i);
      bufferBuilder.vertex(matrix4f, value3, f1, -1.0F).texture(0.0F, 1.0F).color(i1, j1, k1, i);
      bufferBuilder.vertex(matrix4f, f, f1, -1.0F).texture(1.0F, 1.0F).color(i1, j1, k1, i);
      bufferBuilder.vertex(matrix4f, f, value, -1.0F).texture(1.0F, 0.0F).color(i1, j1, k1, i);
      bufferBuilder.vertex(matrix4f, value3, value, -0.5F).texture(0.0F, 0.0F).color(l1, i2, j2, i);
      bufferBuilder.vertex(matrix4f, value3, f1, -0.5F).texture(0.0F, 1.0F).color(l1, i2, j2, i);
      bufferBuilder.vertex(matrix4f, f, f1, -0.5F).texture(1.0F, 1.0F).color(l1, i2, j2, i);
      bufferBuilder.vertex(matrix4f, f, value, -0.5F).texture(1.0F, 0.0F).color(l1, i2, j2, i);
      bufferBuilder.vertex(matrix4f, value3, value, 0.0F).texture(0.0F, 0.0F).color(j, k, l, i);
      bufferBuilder.vertex(matrix4f, value3, f1, 0.0F).texture(0.0F, 1.0F).color(j, k, l, i);
      bufferBuilder.vertex(matrix4f, f, f1, 0.0F).texture(1.0F, 1.0F).color(j, k, l, i);
      bufferBuilder.vertex(matrix4f, f, value, 0.0F).texture(1.0F, 0.0F).color(j, k, l, i);
   }

   @Override
   public void onEnable() {
      this.map.clear();
      this.time = System.nanoTime();
      float f = this.radiusStrelok.getValueAsFloat();
      this.value236 = f - 15.0F;
      this.value237 = this.value236;
      this.value240 = 0.0F;
      if (this.player() != null) {
         this.value238 = this.player().getX();
         this.value239 = this.player().getZ();
      }
   }
}
