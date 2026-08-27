package client.module.visual;

import client.concurrent.ResourceManagerHooks;
import client.enums.TargetEffect;
import client.module.Category;
import client.module.Module;
import client.render.ParticleBuffer;
import client.render.WorldRenderContext;
import client.setting.ColorSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.MathUtil;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TargetESP extends Module {
   private static final ShaderProgramKey shaderProgramKey = new ShaderProgramKey(
      Identifier.ofVanilla("core/targetesp_glow"), VertexFormats.POSITION_TEXTURE_COLOR, Defines.EMPTY
   );
   private ListSetting effect;
   private ColorSetting color;
   private SliderSetting speed;
   private SliderSetting dlitelnost;
   private SliderSetting zhirnost;
   private SliderSetting radius;
   private SliderSetting plotnost;
   private SliderSetting hvost;
   private SliderSetting strengthSvecheniya;
   private LivingEntity livingEntity;
   private LivingEntity livingEntity2;
   private float value235;
   private float value236;
   private float value237;
   private float value238;
   private float value239;
   private float value240;
   private float value241;
   private float value242;
   private long time;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private boolean flag4;
   private long time2;
   private ShaderProgram shaderProgram;
   private int value243;
   private final ParticleBuffer particleBuffer;
   private final Vector3f vector3f;
   private final Vector3f vector3f2;

   public TargetESP() {
      super("TargetESP", Category.VISUAL);
      ListSetting listsetting = new ListSetting("", "", TargetEffect.list, List.of(TargetEffect.RING.getText()), false);
      listsetting.setName("Эффект");
      listsetting.setDescription("Форма метки цели");
      this.effect = listsetting;
      ColorSetting colorsetting = new ColorSetting("", "", -1096636);
      colorsetting.setName("Цвет");
      colorsetting.setDescription("Цвет метки");
      this.color = colorsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.7, 0.1, 2.0, 0.1);
      slidersetting.setName("Скорость");
      slidersetting.setDescription("Скорость анимации");
      this.speed = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 2.3, 0.3, 10.0, 0.1, " c", 1);
      slidersetting1.setName("Длительность");
      slidersetting1.setDescription("Сколько метка держится после удара до затухания");
      this.dlitelnost = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 0.17, 0.04, 0.4, 0.01);
      slidersetting2.setName("Жирность");
      slidersetting2.setDescription("Размер glow-точек");
      this.zhirnost = slidersetting2;
      SliderSetting slidersetting3 = new SliderSetting("", "", 0.7, 0.4, 1.6, 0.05);
      slidersetting3.setName("Радиус");
      slidersetting3.setDescription("Радиус метки относительно ширины цели");
      this.radius = slidersetting3;
      SliderSetting slidersetting4 = new SliderSetting("", "", 220.0, 30.0, 220.0, 2.0);
      slidersetting4.setName("Плотность");
      slidersetting4.setDescription("Количество точек");
      this.plotnost = slidersetting4;
      SliderSetting slidersetting5 = new SliderSetting("", "", 6.0, 0.0, 12.0, 1.0);
      slidersetting5.setName("Хвост");
      slidersetting5.setDescription("Длина затухающего шлейфа");
      this.hvost = slidersetting5;
      SliderSetting slidersetting6 = new SliderSetting("", "", 1.0, 0.2, 3.0, 0.05, "", 2);
      slidersetting6.setName("Сила свечения");
      slidersetting6.setDescription("Насколько ярко горит метка");
      this.strengthSvecheniya = slidersetting6;
      this.value243 = -1;
      this.particleBuffer = new ParticleBuffer();
      this.vector3f = new Vector3f();
      this.vector3f2 = new Vector3f();
      this.addSettings(
         new Setting[]{this.effect, this.color, this.speed, this.dlitelnost, this.zhirnost, this.radius, this.plotnost, this.hvost, this.strengthSvecheniya}
      );
   }

   @Override
   public void onTick() {
      if (!this.notInGame()) {
         this.time2 = System.currentTimeMillis();
         this.value237 = this.value235;
         this.value238 = this.value236;
         this.value241 = this.value239;
         this.value242 = this.value240;
         boolean flagx = this.livingEntity != null && (this.livingEntity.isRemoved() || !this.livingEntity.isAlive());
         if (flagx) {
            this.flag = false;
         }

         PlayerEntity playerentity = this.player();
         this.flag3 = playerentity != null && this.livingEntity != null && playerentity.canSee(this.livingEntity);
         this.flag4 = playerentity != null && this.livingEntity2 != null && playerentity.canSee(this.livingEntity2);
         long i = System.currentTimeMillis() - this.time;
         boolean flag1 = this.flag && !flagx && i < (long)(this.dlitelnost.getValue() * 1000.0);
         this.flag2 = flag1;
         if (flag1) {
            this.value235 = getFloatByFloatFloatFloat(this.value235, 1.0F, 0.15F);
         } else if (this.livingEntity != null) {
            this.value235 = getFloatByFloatFloatFloat(this.value235, 0.0F, 0.25F);
            if (this.value235 < 0.03F) {
               this.value235 = 0.0F;
               this.flag = false;
               this.livingEntity = null;
            }
         }

         if (this.livingEntity2 != null) {
            this.value236 = getFloatByFloatFloatFloat(this.value236, 0.0F, 0.3F);
            if (this.value236 < 0.03F) {
               this.value236 = 0.0F;
               this.livingEntity2 = null;
            }
         }

         float f = this.speed.getValueAsFloat();
         if (this.value235 > 0.03F) {
            this.value239 += f;
         }

         if (this.value236 > 0.03F) {
            this.value240 += f;
         }
      }
   }

   private static void update11() {
      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
   }

   @Override
   public void onDisable() {
      this.livingEntity = null;
      this.livingEntity2 = null;
      this.shaderProgram = null;
      this.update12();
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!this.notInGame()) {
         if (this.livingEntity != null || this.livingEntity2 != null) {
            float f = Math.min((float)(System.currentTimeMillis() - this.time2) / 50.0F, 1.0F);
            float f1 = getFloatByFloatFloatFloat(this.value237, this.value235, f);
            float f2 = getFloatByFloatFloatFloat(this.value238, this.value236, f);
            boolean flagx = this.livingEntity != null && this.flag3 && f1 > 0.03F;
            boolean flag1 = this.livingEntity2 != null && this.flag4 && f2 > 0.03F;
            if (flagx || flag1) {
               ShaderProgram shaderprogram = this.getShaderProgram();
               if (shaderprogram != null) {
                  int k = this.color.getInt();
                  float f4 = 1.2F;
                  int j = k;
                  int i = MathUtil.getIntByFloatInt(f4, j);
                  TargetEffect targeteffect = TargetEffect.getTargetEffectByString(this.effect.getString2());
                  Camera camera = worldRenderContext.getCamera();
                  this.vector3f.set(1.0F, 0.0F, 0.0F).rotate(camera.getRotation());
                  this.vector3f2.set(0.0F, 1.0F, 0.0F).rotate(camera.getRotation());
                  Matrix4f matrix4f = worldRenderContext.getMatrixStack().peek().getPositionMatrix();
                  boolean flag2x = MathUtil.isInt(i);
                  this.onBooleanShaderProgram(flag2x, shaderprogram);

                  try {
                     BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
                     if (flag1) {
                        float f3 = getFloatByFloatFloatFloat(this.value242, this.value240, f);
                        boolean flag3x = false;
                        LivingEntity livingentity = this.livingEntity2;
                        this.onBufferBuilderTargetEffectFloatFloatCameraIntFloatLivingEntityBooleanMatrix4f(
                           bufferbuilder, targeteffect, f3, f2, camera, i, f, livingentity, flag3x, matrix4f
                        );
                     }

                     if (flagx) {
                        float f5 = getFloatByFloatFloatFloat(this.value241, this.value239, f);
                        boolean flag4x = this.flag2;
                        LivingEntity livingentity1 = this.livingEntity;
                        this.onBufferBuilderTargetEffectFloatFloatCameraIntFloatLivingEntityBooleanMatrix4f(
                           bufferbuilder, targeteffect, f5, f1, camera, i, f, livingentity1, flag4x, matrix4f
                        );
                     }

                     BuiltBuffer builtbuffer = bufferbuilder.endNullable();
                     if (builtbuffer != null) {
                        BufferRenderer.drawWithGlobalProgram(builtbuffer);
                     }
                  } finally {
                     update11();
                  }
               }
            }
         }
      }
   }

   private static float getFloatByFloatFloatFloat(float value, float value2, float value3) {
      float f = value2 - value;
      return Math.abs(f) < 0.001F ? value2 : value + f * value3;
   }

   private ShaderProgram getShaderProgram() {
      if (ResourceManagerHooks.isFlag4()) {
         return null;
      } else {
         int i = ResourceManagerHooks.getInt();
         if (i != this.value243) {
            this.shaderProgram = null;
            this.value243 = i;
         }

         if (this.shaderProgram != null) {
            return this.shaderProgram;
         } else {
            try {
               this.shaderProgram = this.client().getShaderLoader().getOrCreateProgram(shaderProgramKey);
            } catch (Throwable throwable) {
               return null;
            }

            return this.shaderProgram;
         }
      }
   }

   private void onBufferBuilderTargetEffectFloatFloatCameraIntFloatLivingEntityBooleanMatrix4f(
      BufferBuilder bufferBuilder, TargetEffect targetEffect, float value, float value2, Camera camera, int count, float value3, LivingEntity livingEntity, boolean flag, Matrix4f matrix4f
   ) {
      if (!(value2 <= 0.0F) && livingEntity != null) {
         float f = value2 * value2 * (3.0F - 2.0F * value2);
         double d0 = camera.getPos().x;
         double d1 = camera.getPos().y;
         double d2 = camera.getPos().z;
         double d3 = livingEntity.lastRenderX + (livingEntity.getX() - livingEntity.lastRenderX) * value3;
         double d4 = livingEntity.lastRenderY + (livingEntity.getY() - livingEntity.lastRenderY) * value3;
         double d5 = livingEntity.lastRenderZ + (livingEntity.getZ() - livingEntity.lastRenderZ) * value3;
         ParticleBuffer particlebuffer = this.particleBuffer;
         Vector3f vector3f2x = this.vector3f;
         Vector3f vector3f3 = this.vector3f2;
         float f9 = (float)(d3 - d0);
         float f10 = (float)(d4 - d1);
         float f11 = (float)(d5 - d2);
         float f12 = livingEntity.getWidth() * this.radius.getValueAsFloat();
         float f13 = livingEntity.getHeight();
         float f14 = this.zhirnost.getValueAsFloat();
         float f15 = value * 0.04F;
         int k = this.plotnost.getInt2();
         int l = this.hvost.getInt2();
         float f8 = this.strengthSvecheniya.getValueAsFloat();
         int j = l;
         int i = k;
         float f7 = f15;
         float f6 = f14;
         float f5 = f13;
         float f4 = f12;
         float f3 = f11;
         float f2 = f10;
         float f1 = f9;
         Vector3f vector3f1 = vector3f3;
         Vector3f vector3fx = vector3f2x;
         particlebuffer.onIntBufferBuilderFloatFloatMatrix4fFloatVector3fVector3fIntFloatIntFloatFloatFloatFloatBooleanFloat(
            count, bufferBuilder, f1, f2, matrix4f, f5, vector3f1, vector3fx, i, f6, j, f8, f3, f, f4, flag, f7
         );
         targetEffect.onParticleBuffer(this.particleBuffer);
      }
   }

   private void update12() {
      this.value235 = 0.0F;
      this.value236 = 0.0F;
      this.value237 = 0.0F;
      this.value238 = 0.0F;
      this.value239 = 0.0F;
      this.value240 = 0.0F;
      this.value241 = 0.0F;
      this.value242 = 0.0F;
      this.time = 0L;
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.flag4 = false;
   }

   @Override
   public void onPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      if (this.isEnabled() && entity2 instanceof LivingEntity livingentity && livingentity.isAlive()) {
         if (this.livingEntity != livingentity) {
            this.livingEntity2 = this.livingEntity;
            this.value236 = this.value235;
            this.value240 = this.value239;
            this.livingEntity = livingentity;
            this.value235 = 0.0F;
            this.value239 = 0.0F;
         }

         this.time = System.currentTimeMillis();
         this.flag = true;
      }
   }

   private void onBooleanShaderProgram(boolean flag, ShaderProgram shaderProgram) {
      RenderSystem.enableBlend();
      if (!flag) {
         RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
      } else {
         RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
      }

      RenderSystem.depthMask(false);
      RenderSystem.enableDepthTest();
      RenderSystem.disableCull();
      RenderSystem.setShader(shaderProgram);
   }

   @Override
   public void onEnable() {
      this.time2 = System.currentTimeMillis();
      this.update12();
   }
}
