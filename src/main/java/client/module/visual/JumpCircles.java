package client.module.visual;

import client.concurrent.ResourceManagerHooks;
import client.module.Category;
import client.module.Module;
import client.render.SnapshotFramebuffer;
import client.render.WorldRenderContext;
import client.setting.ColorSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.JumpCircle;
import client.util.JumpWave;
import client.util.StringParts;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class JumpCircles extends Module {
   private static final ShaderProgramKey shaderProgramKey = new ShaderProgramKey(
      Identifier.ofVanilla("core/jump_circle"), VertexFormats.POSITION_TEXTURE, Defines.EMPTY
   );
   private static final ShaderProgramKey shaderProgramKey2 = new ShaderProgramKey(
      Identifier.ofVanilla("core/jump_wave"), VertexFormats.POSITION_TEXTURE, Defines.EMPTY
   );
   private static final List<String> list = List.of("Обычный", "Волна");
   private ListSetting mode;
   private SliderSetting dlitelnost;
   private SliderSetting radius;
   private SliderSetting strengthVolny;
   private SliderSetting radiusVolny;
   private SliderSetting timeVolny;
   private SliderSetting iskazhenie;
   private ColorSetting color;
   private final List<JumpCircle> list2;
   private final List<JumpWave> list3;
   private final SnapshotFramebuffer snapshotFramebuffer;
   private int value235;
   private ShaderProgram shaderProgram;
   private GlUniform glUniform;
   private GlUniform glUniform2;
   private GlUniform glUniform3;
   private ShaderProgram shaderProgram2;
   private GlUniform glUniform4;
   private GlUniform glUniform5;
   private GlUniform glUniform6;
   private GlUniform glUniform7;

   public JumpCircles() {
      super("JumpCircles", Category.VISUAL);
      ListSetting listsetting = new ListSetting("", "", list, List.of(StringParts.join(new String[]{"O", "б", "ы", "ч", "н", "ы", "й"})), false);
      listsetting.setName("Режим");
      listsetting.setDescription("Что показывать при прыжке");
      this.mode = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 800.0, 200.0, 3000.0, 10.0, StringParts.join(new String[]{" ", "м", "c"}), 0);
      slidersetting.setName("Длительность");
      slidersetting.setDescription("Время жизни кольца");
      this.dlitelnost = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 1.5, 0.0, 5.0, 0.1, "", 1);
      slidersetting1.setName("Радиус");
      slidersetting1.setDescription("Размер кольца в блоках");
      this.radius = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 0.6, 0.0, 2.0, 0.05, "", 2);
      slidersetting2.setName("Сила волны");
      slidersetting2.setDescription("Насколько сильно гнет картинку");
      this.strengthVolny = slidersetting2;
      SliderSetting slidersetting3 = new SliderSetting("", "", 3.0, 0.5, 10.0, 0.1, "", 1);
      slidersetting3.setName("Радиус волны");
      slidersetting3.setDescription("До какого радиуса доходит волна");
      this.radiusVolny = slidersetting3;
      SliderSetting slidersetting4 = new SliderSetting("", "", 600.0, 150.0, 2500.0, 10.0, StringParts.join(new String[]{" ", "м", "c"}), 0);
      slidersetting4.setName("Время волны");
      slidersetting4.setDescription("Сколько живет волна");
      this.timeVolny = slidersetting4;
      SliderSetting slidersetting5 = new SliderSetting("", "", 1.0, 0.0, 3.0, 0.05, "x", 2);
      slidersetting5.setName("Искажение");
      slidersetting5.setDescription("Множитель силы искажения");
      this.iskazhenie = slidersetting5;
      ColorSetting colorsetting = new ColorSetting("", "", -8952321);
      colorsetting.setName("Цвет");
      colorsetting.setDescription("Цвет колец и волны");
      this.color = colorsetting;
      this.list2 = new ArrayList<>();
      this.list3 = new ArrayList<>();
      this.snapshotFramebuffer = new SnapshotFramebuffer();
      this.value235 = -1;
      this.dlitelnost.setVisibleWhen(this::check3);
      this.radius.setVisibleWhen(this::check3);
      this.strengthVolny.setVisibleWhen(this::getBoolean3);
      this.radiusVolny.setVisibleWhen(this::getBoolean2);
      this.timeVolny.setVisibleWhen(this::getBoolean);
      this.iskazhenie.setVisibleWhen(this::getBoolean4);
      this.addSettings(
         new Setting[]{this.mode, this.dlitelnost, this.radius, this.strengthVolny, this.radiusVolny, this.timeVolny, this.iskazhenie, this.color}
      );
   }

   private static float getFloatByFloat(float value) {
      float f = MathHelper.clamp(value, 0.0F, 1.0F);
      return f * f * (3.0F - 2.0F * f);
   }

   private Boolean getBoolean() {
      return !this.check3();
   }

   private void update11() {
      int i = ResourceManagerHooks.getInt();
      if (i != this.value235 || this.shaderProgram == null || this.shaderProgram2 == null) {
         this.value235 = i;
         this.shaderProgram = this.getShaderProgramByShaderProgramKey(shaderProgramKey);
         if (this.shaderProgram != null) {
            this.glUniform = this.shaderProgram.getUniform("uTime");
            this.glUniform2 = this.shaderProgram.getUniform("uAlpha");
            this.glUniform3 = this.shaderProgram.getUniform("uTint");
         }

         this.shaderProgram2 = this.getShaderProgramByShaderProgramKey(shaderProgramKey2);
         if (this.shaderProgram2 != null) {
            this.glUniform4 = this.shaderProgram2.getUniform("uProgress");
            this.glUniform5 = this.shaderProgram2.getUniform("uStrength");
            this.glUniform6 = this.shaderProgram2.getUniform("uFade");
            this.glUniform7 = this.shaderProgram2.getUniform("uCrestColor");
         }
      }
   }

   private static float getFloatByFloat2(float value) {
      float f = 1.0F - MathHelper.clamp(value, 0.0F, 1.0F);
      return 1.0F - f * f * f;
   }

   private Boolean getBoolean2() {
      return !this.check3();
   }

   @Override
   public void onDisable() {
      this.update12();
      this.snapshotFramebuffer.update();
      this.shaderProgram = null;
      this.shaderProgram2 = null;
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!this.notInGame()) {
         long i = System.currentTimeMillis();
         if (!this.list2.isEmpty() || !this.list3.isEmpty()) {
            this.list2.removeIf(p0 -> JumpCircles.isLongJumpCircle(i, p0));
            this.list3.removeIf(p0 -> JumpCircles.isLongJumpWave(i, p0));
            if (!this.list2.isEmpty() || !this.list3.isEmpty()) {
               if (!ResourceManagerHooks.isFlag4()) {
                  this.update11();
                  Matrix4f matrix4f = worldRenderContext.getMatrixStack().peek().getPositionMatrix();
                  Vec3d vec3d = worldRenderContext.getCamera().getPos();
                  int j = this.color.getInt3();
                  float f = (float)(i % 1000000L) / 1000.0F;
                  RenderSystem.enableBlend();
                  RenderSystem.depthMask(false);
                  RenderSystem.disableCull();

                  try {
                     this.onVec3dFloatLongMatrix4fInt(vec3d, f, i, matrix4f, j);
                     this.onIntLongVec3dMatrix4f(j, i, vec3d, matrix4f);
                  } finally {
                     RenderSystem.depthMask(true);
                     RenderSystem.enableCull();
                     RenderSystem.defaultBlendFunc();
                     RenderSystem.disableBlend();
                  }
               }
            }
         }
      }
   }

   private Boolean getBoolean3() {
      return !this.check3();
   }

   private static float getFloatByFloat3(float value) {
      float f = MathHelper.clamp(value, 0.0F, 1.0F);
      return MathHelper.clamp(getFloatByFloat(f / 0.18F) * getFloatByFloat((1.0F - f) / 0.82F), 0.0F, 1.0F);
   }

   private static boolean isLongJumpWave(long time, JumpWave jumpWave) {
      return jumpWave.isLong(time);
   }

   private static boolean isLongJumpCircle(long time, JumpCircle jumpCircle) {
      return jumpCircle.isLong(time);
   }

   private Boolean getBoolean4() {
      return !this.check3();
   }

   private void update12() {
      this.list2.clear();
      this.list3.clear();
   }

   private void onVec3dFloatLongMatrix4fInt(Vec3d vec3d2, float value, long time, Matrix4f matrix4f, int count) {
      if (this.shaderProgram != null && !this.list2.isEmpty()) {
         float f = this.radius.getValueAsFloat();
         if (!(f <= 0.0F)) {
            RenderSystem.setShader(this.shaderProgram);
            RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE, SrcFactor.ZERO, DstFactor.ONE);
            if (this.glUniform != null) {
               this.glUniform.set(value);
            }

            if (this.glUniform3 != null) {
               this.glUniform3.set((count >> 16 & 0xFF) * 0.003921569F, (count >> 8 & 0xFF) * 0.003921569F, (count & 0xFF) * 0.003921569F);
            }

            for (JumpCircle jumpcircle : this.list2) {
               float f1 = jumpcircle.getFloatByLong(time);
               if (this.glUniform2 != null) {
                  this.glUniform2.set(1.0F - f1);
               }

               Vec3d vec3d1 = jumpcircle.getPos();
               float f4 = f * getFloatByFloat2(f1);
               float f3 = 0.04F;
               float f2 = f4;
               Vec3d vec3d = vec3d1;
               this.onFloatVec3dMatrix4fVec3dFloat(f2, vec3d2, matrix4f, vec3d, f3);
            }
         }
      }
   }

   private void onIntLongVec3dMatrix4f(int count, long time, Vec3d vec3d2, Matrix4f matrix4f) {
      if (this.shaderProgram2 != null && !this.list3.isEmpty()) {
         RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
         RenderSystem.setShader(this.shaderProgram2);
         if (this.glUniform7 != null) {
            this.glUniform7.set((count >> 16 & 0xFF) * 0.003921569F, (count >> 8 & 0xFF) * 0.003921569F, (count & 0xFF) * 0.003921569F);
         }

         for (JumpWave jumpwave : this.list3) {
            float f = jumpwave.getFloatByLong(time);
            float f1 = getFloatByFloat3(f);
            if (!(jumpwave.getStrength() <= 0.001F) && !(f1 <= 0.003F)) {
               SimpleFramebuffer simpleframebuffer = this.snapshotFramebuffer.getSimpleFramebuffer();
               if (simpleframebuffer == null) {
                  return;
               }

               this.shaderProgram2.addSamplerTexture("SceneSampler", simpleframebuffer.getColorAttachment());
               if (this.glUniform4 != null) {
                  this.glUniform4.set(getFloatByFloat2(f));
               }

               if (this.glUniform5 != null) {
                  this.glUniform5.set(jumpwave.getStrength());
               }

               if (this.glUniform6 != null) {
                  this.glUniform6.set(f1);
               }

               Vec3d vec3d1 = jumpwave.getPos();
               float f4 = jumpwave.getMaxRadius();
               float f3 = 0.03F;
               float f2 = f4;
               Vec3d vec3d = vec3d1;
               this.onFloatVec3dMatrix4fVec3dFloat(f2, vec3d2, matrix4f, vec3d, f3);
            }
         }
      }
   }

   private ShaderProgram getShaderProgramByShaderProgramKey(ShaderProgramKey shaderProgramKey) {
      try {
         return this.client().getShaderLoader().getOrCreateProgram(shaderProgramKey);
      } catch (Throwable throwable) {
         return null;
      }
   }

   private boolean check3() {
      return this.mode.isString("Обычный");
   }

   private void onFloatVec3dMatrix4fVec3dFloat(float value, Vec3d vec3d, Matrix4f matrix4f, Vec3d vec3d2, float value2) {
      float f = (float)(vec3d2.x - vec3d.x);
      float f1 = (float)(vec3d2.y + value2 - vec3d.y);
      float f2 = (float)(vec3d2.z - vec3d.z);
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
      bufferbuilder.vertex(matrix4f, f + value, f1, f2 + value).texture(1.0F, 1.0F);
      bufferbuilder.vertex(matrix4f, f + value, f1, f2 - value).texture(1.0F, 0.0F);
      bufferbuilder.vertex(matrix4f, f - value, f1, f2 - value).texture(0.0F, 0.0F);
      bufferbuilder.vertex(matrix4f, f - value, f1, f2 + value).texture(0.0F, 1.0F);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
   }

   @Override
   public void update4() {
      this.update12();
   }

      private boolean flag;

   @Override
   public void update7() {
      if (!this.notInGame()) {
         boolean onGround = this.player().isOnGround();
         if (onGround == this.flag) {
            return;
         }
         this.flag = onGround;
         if (onGround || !(this.player().getVelocity().y > 0.0)) {
            return;
         }
         Vec3d vec3d = this.player().getPos();
         long i = System.currentTimeMillis();
         if (this.check3()) {
            this.list2.add(new JumpCircle(vec3d, i, this.dlitelnost.getValueAsFloat()));
         } else {
            this.list3
               .add(
                  new JumpWave(
                     vec3d,
                     i,
                     this.timeVolny.getValueAsFloat(),
                     this.strengthVolny.getValueAsFloat() * this.iskazhenie.getValueAsFloat(),
                     this.radiusVolny.getValueAsFloat()
                  )
               );
         }
      }
   }

   @Override
   public void onEnable() {
      this.update12();
   }
}
