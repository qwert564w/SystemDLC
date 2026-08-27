package client.module.visual;

import client.api.Theme;
import client.concurrent.ResourceManagerHooks;
import client.module.Category;
import client.module.Module;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.AttackEffectEntry;
import client.util.MathUtil;
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
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class AttackEffects extends Module {
   private static final List<String> list = List.of("Плазма", "Искры", "Волна", "Слэш");
   private static final String[] stringArray = new String[]{"plasma_pinch", "attack_sparks", "attack_shock", "attack_slash"};
   private static final ShaderProgramKey[] shaderProgramKeyArray = getShaderProgramKeyArray();
   private ListSetting effect;
   private SliderSetting radius;
   private SliderSetting temperatura;
   private SliderSetting yarkost;
   private SliderSetting nasyschennost;
   private SliderSetting dlitelnost;
   private BooleanSetting colorTemy;
   private ColorSetting color;
   private final List<AttackEffectEntry> list2;
   private long time;
   private int value235;
   private int value236;
   private final Vector3f vector3f;
   private final Vector3f vector3f2;
   private ShaderProgram shaderProgram;
   private int value237;
   private int value238;
   private GlUniform glUniform;
   private GlUniform glUniform2;
   private GlUniform glUniform3;
   private GlUniform glUniform4;
   private GlUniform glUniform5;
   private GlUniform glUniform6;

   public AttackEffects() {
      super("AttackEffects", Category.VISUAL);
      ListSetting listsetting = new ListSetting("", "", list, List.of(list.getFirst()), false);
      listsetting.setName("Эффект");
      listsetting.setDescription("Что вспыхивает в точке удара");
      this.effect = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 1.4, 0.4, 5.0, 0.05);
      slidersetting.setName("Радиус");
      slidersetting.setDescription("Размер эффекта в блоках");
      this.radius = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 1.0, 0.5, 2.0, 0.05);
      slidersetting1.setName("Температура");
      slidersetting1.setDescription("Плотность эффекта: ядро, толщина лучей и волн");
      this.temperatura = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 0.8, 0.3, 3.0, 0.02);
      slidersetting2.setName("Яркость");
      slidersetting2.setDescription("Интенсивность свечения");
      this.yarkost = slidersetting2;
      SliderSetting slidersetting3 = new SliderSetting("", "", 1.2, 0.0, 2.0, 0.05, "", 2);
      slidersetting3.setName("Насыщенность");
      slidersetting3.setDescription("1 — цвет как есть, выше — чище и сочнее");
      this.nasyschennost = slidersetting3;
      SliderSetting slidersetting4 = new SliderSetting("", "", 480.0, 220.0, 900.0, 10.0, StringParts.join(new String[]{" ", "м", "c"}), 0);
      slidersetting4.setName("Длительность");
      slidersetting4.setDescription("Время жизни эффекта");
      this.dlitelnost = slidersetting4;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Цвет темы");
      booleansetting.setDescription("Красить вспышку в акцентный цвет темы клиента");
      this.colorTemy = booleansetting;
      ColorSetting colorsetting = new ColorSetting("", "", -10029228);
      colorsetting.setName("Цвет");
      colorsetting.setDescription("Свой цвет вспышки");
      this.color = colorsetting;
      this.list2 = new ArrayList<>();
      this.value235 = Integer.MIN_VALUE;
      this.vector3f = new Vector3f();
      this.vector3f2 = new Vector3f();
      this.value237 = -1;
      this.value238 = -1;
      this.color.setVisibleWhen(this::getBoolean);
      this.addSettings(new Setting[]{this.effect, this.radius, this.temperatura, this.yarkost, this.nasyschennost, this.dlitelnost, this.colorTemy, this.color});
   }

   @Override
   public void onDisable() {
      this.list2.clear();
      this.shaderProgram = null;
      this.value237 = -1;
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!this.notInGame()) {
         long i = System.currentTimeMillis();
         this.list2.removeIf(p0 -> AttackEffects.isLongAttackEffectEntry(i, p0));
         if (!this.list2.isEmpty()) {
            if (!ResourceManagerHooks.isFlag4()) {
               Camera camera = worldRenderContext.getCamera();
               Vec3d vec3d = camera.getPos();
               this.vector3f.set(1.0F, 0.0F, 0.0F).rotate(camera.getRotation());
               this.vector3f2.set(0.0F, 1.0F, 0.0F).rotate(camera.getRotation());
               Matrix4f matrix4f = worldRenderContext.getMatrixStack().peek().getPositionMatrix();
               float f = (float)(i % 100000L) / 1000.0F;
               RenderSystem.enableBlend();
               RenderSystem.depthMask(false);
               RenderSystem.disableCull();

               try {
                  for (AttackEffectEntry attackeffectentry : this.list2) {
                     ShaderProgram shaderprogram = this.getShaderProgramByInt(attackeffectentry.getEffect());
                     if (shaderprogram != null) {
                        RenderSystem.setShader(shaderprogram);
                        if (MathUtil.isInt(attackeffectentry.getColor())) {
                           RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
                        } else {
                           RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_COLOR);
                        }

                        float f1 = Math.min(1.0F, (float)(i - attackeffectentry.getStartMs()) / (float)attackeffectentry.getDurationMs());
                        if (this.glUniform != null) {
                           this.glUniform.set(f);
                        }

                        if (this.glUniform2 != null) {
                           this.glUniform2.set(f1);
                        }

                        if (this.glUniform3 != null) {
                           this.glUniform3.set(attackeffectentry.getSeed());
                        }

                        if (this.glUniform4 != null) {
                           this.glUniform4.set(attackeffectentry.getTemperature());
                        }

                        if (this.glUniform5 != null) {
                           this.glUniform5.set(attackeffectentry.getBrightness());
                        }

                        if (this.glUniform6 != null) {
                           this.glUniform6
                              .set(
                                 (attackeffectentry.getColor() >> 16 & 0xFF) * 0.003921569F,
                                 (attackeffectentry.getColor() >> 8 & 0xFF) * 0.003921569F,
                                 (attackeffectentry.getColor() & 0xFF) * 0.003921569F
                              );
                        }

                        this.onMatrix4fAttackEffectEntryVec3d(matrix4f, attackeffectentry, vec3d);
                     }
                  }
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

   private void onMatrix4fAttackEffectEntryVec3d(Matrix4f matrix4f, AttackEffectEntry attackEffectEntry, Vec3d vec3d) {
      float f = (float)(attackEffectEntry.getPos().x - vec3d.x);
      float f1 = (float)(attackEffectEntry.getPos().y - vec3d.y);
      float f2 = (float)(attackEffectEntry.getPos().z - vec3d.z);
      float f3 = attackEffectEntry.getRadius() * 1.4F;
      float f4 = this.vector3f.x * f3;
      float f5 = this.vector3f.y * f3;
      float f6 = this.vector3f.z * f3;
      float f7 = this.vector3f2.x * f3;
      float f8 = this.vector3f2.y * f3;
      float f9 = this.vector3f2.z * f3;
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
      bufferbuilder.vertex(matrix4f, f - f4 - f7, f1 - f5 - f8, f2 - f6 - f9).texture(0.0F, 0.0F);
      bufferbuilder.vertex(matrix4f, f + f4 - f7, f1 + f5 - f8, f2 + f6 - f9).texture(1.0F, 0.0F);
      bufferbuilder.vertex(matrix4f, f + f4 + f7, f1 + f5 + f8, f2 + f6 + f9).texture(1.0F, 1.0F);
      bufferbuilder.vertex(matrix4f, f - f4 + f7, f1 - f5 + f8, f2 - f6 + f9).texture(0.0F, 1.0F);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
   }

   private ShaderProgram getShaderProgramByInt(int count) {
      int i = ResourceManagerHooks.getInt();
      if (i != this.value238) {
         this.shaderProgram = null;
         this.value237 = -1;
         this.value238 = i;
      }

      if (this.shaderProgram != null && this.value237 == count) {
         return this.shaderProgram;
      } else {
         ShaderProgram shaderprogram;
         try {
            shaderprogram = this.client().getShaderLoader().getOrCreateProgram(shaderProgramKeyArray[count]);
         } catch (Throwable throwable) {
            return null;
         }

         if (shaderprogram == null) {
            return null;
         } else {
            this.shaderProgram = shaderprogram;
            this.value237 = count;
            this.glUniform = shaderprogram.getUniform("uTime");
            this.glUniform2 = shaderprogram.getUniform("uProgress");
            this.glUniform3 = shaderprogram.getUniform("uSeed");
            this.glUniform4 = shaderprogram.getUniform("uTemperature");
            this.glUniform5 = shaderprogram.getUniform("uBrightness");
            this.glUniform6 = shaderprogram.getUniform("uTint");
            return shaderprogram;
         }
      }
   }

   private static boolean isLongAttackEffectEntry(long time, AttackEffectEntry attackEffectEntry) {
      return attackEffectEntry.isLong(time);
   }

   private Boolean getBoolean() {
      return !this.colorTemy.isFlag3();
   }

   private static ShaderProgramKey[] getShaderProgramKeyArray() {
      ShaderProgramKey[] ashaderprogramkey = new ShaderProgramKey[stringArray.length];

      for (int i = 0; i < ashaderprogramkey.length; i++) {
         ashaderprogramkey[i] = new ShaderProgramKey(Identifier.ofVanilla("core/" + stringArray[i]), VertexFormats.POSITION_TEXTURE, Defines.EMPTY);
      }

      return ashaderprogramkey;
   }

   @Override
   public void onPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      if (!this.notInGame() && entity2 != null && entity2 != this.player()) {
         long i = System.currentTimeMillis();
         if (entity2.getId() != this.value235 || i - this.time >= 30L) {
            this.value235 = entity2.getId();
            this.time = i;
            Vec3d vec3d = this.player().getRotationVec(1.0F).normalize();
            this.value236 = this.value236 + 1 & 1023;
            float f = this.value236 * 7.31F % 41.0F;
            this.list2
               .add(
                  new AttackEffectEntry(
                     this.getVec3dByVec3dEntity(vec3d, entity2),
                     i,
                     (long)this.dlitelnost.getValue(),
                     this.radius.getValueAsFloat(),
                     this.temperatura.getValueAsFloat(),
                     this.yarkost.getValueAsFloat(),
                     f,
                     this.getInt(),
                     Math.max(0, list.indexOf(this.effect.getString2()))
                  )
               );

            while (this.list2.size() > 12) {
               this.list2.remove(0);
            }
         }
      }
   }

   private Vec3d getVec3dByVec3dEntity(Vec3d vec3d2, Entity entity2) {
      if (this.client().crosshairTarget instanceof EntityHitResult entityhitresult && entityhitresult.getEntity() == entity2) {
         return entityhitresult.getPos();
      } else {
         Box box = entity2.getBoundingBox();
         Vec3d vec3d = this.player().getEyePos();
         return box.raycast(vec3d, vec3d.add(vec3d2.multiply(9.0))).orElse(box.getCenter());
      }
   }

   private int getInt() {
      int i;
      if (this.colorTemy.isFlag3()) {
         try {
            i = Theme.primary();
         } catch (Throwable throwable) {
            i = 6747988;
         }
      } else {
         i = this.color.getInt3();
      }

      float f = this.nasyschennost.getValueAsFloat();
      return MathUtil.getIntByFloatInt(f, i) & 16777215;
   }

   @Override
   public void onEnable() {
      this.list2.clear();
      this.value235 = Integer.MIN_VALUE;
      this.time = 0L;
   }
}
