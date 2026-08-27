package client.module.visual;

import client.concurrent.ResourceManagerHooks;
import client.module.Category;
import client.module.Module;
import client.setting.ColorSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.StringParts;
import com.mojang.blaze3d.systems.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.MinecraftClient;
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
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class SkyShader extends Module {
   private final ListSetting mode;
   private final ColorSetting color;
   private final ListSetting palitraNeona;
   private final ColorSetting neonColor1;
   private final ColorSetting neonColor2;
   private final ColorSetting neonColor3;
   private final SliderSetting speed;
   private final SliderSetting size;
   private final SliderSetting intensivnost;
   private final SliderSetting opacity;
   private static final ShaderProgramKey shaderProgramKey = getShaderProgramKeyByString("core/sky_nebula");
   private static final ShaderProgramKey shaderProgramKey2 = getShaderProgramKeyByString("core/sky_aurora");
   private static final ShaderProgramKey shaderProgramKey3 = getShaderProgramKeyByString("core/sky_stars");
   private static final ShaderProgramKey shaderProgramKey4 = getShaderProgramKeyByString("core/sky_plasma");
   private static final ShaderProgramKey shaderProgramKey5 = getShaderProgramKeyByString("core/sky_neon");
   private ShaderProgram shaderProgram;
   private int value235;
   private GlUniform glUniform;
   private GlUniform glUniform2;
   private GlUniform glUniform3;
   private GlUniform glUniform4;
   private GlUniform glUniform5;
   private GlUniform glUniform6;
   private GlUniform glUniform7;
   private GlUniform glUniform8;
   private GlUniform glUniform9;
   private GlUniform glUniform10;
   private GlUniform glUniform11;
   private GlUniform glUniform12;
   private GlUniform glUniform13;
   private GlUniform glUniform14;
   private final Matrix4f matrix4f;
   private final Matrix4f matrix4f2;
   private final Matrix4f matrix4f3;
   private long time;
   private static SkyShader INSTANCE;

   public SkyShader() {
      super("SkyShader", Category.VISUAL);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"Т", "у", "м", "а", "н", "н", "о", "с", "т", "ь"}),
            StringParts.join(new String[]{"С", "и", "я", "н", "и", "е"}),
            StringParts.join(new String[]{"З", "в", "ё", "з", "д", "ы"}),
            StringParts.join(new String[]{"П", "л", "а", "з", "м", "а"}),
            StringParts.join(new String[]{"Н", "е", "о", "н"})
         ),
         List.of(StringParts.join(new String[]{"Т", "у", "м", "а", "н", "н", "о", "с", "т", "ь"})),
         false
      );
      listsetting.setName("Режим");
      listsetting.setDescription("Тип эффекта неба");
      this.mode = listsetting;
      ColorSetting colorsetting = new ColorSetting("", "", -13395457, false);
      colorsetting.setName("Цвет");
      colorsetting.setDescription("Цвет эффекта");
      this.color = colorsetting;
      listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            "RGB",
            StringParts.join(new String[]{"Т", "ё", "м", "н", "ы", "е"}),
            StringParts.join(new String[]{"С", "в", "е", "т", "л", "ы", "е"}),
            StringParts.join(new String[]{"С", "в", "о", "и"})
         ),
         List.of("RGB"),
         false
      );
      listsetting.setName("Палитра неона");
      listsetting.setDescription("Цветовая схема для режима Неон");
      this.palitraNeona = listsetting;
      ColorSetting colorsetting1 = new ColorSetting("", "", -65536, false);
      colorsetting1.setName("Неон цвет 1");
      colorsetting1.setDescription("Первый цвет пятен");
      this.neonColor1 = colorsetting1;
      ColorSetting colorsetting2 = new ColorSetting("", "", -16711936, false);
      colorsetting2.setName("Неон цвет 2");
      colorsetting2.setDescription("Второй цвет пятен");
      this.neonColor2 = colorsetting2;
      ColorSetting colorsetting3 = new ColorSetting("", "", -16776961, false);
      colorsetting3.setName("Неон цвет 3");
      colorsetting3.setDescription("Третий цвет пятен");
      this.neonColor3 = colorsetting3;
      SliderSetting slidersetting = new SliderSetting("", "", 1.0, 0.1, 5.0, 0.1);
      slidersetting.setName("Скорость");
      slidersetting.setDescription("Скорость анимации");
      this.speed = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 5.0, 1.0, 20.0, 0.5);
      slidersetting1.setName("Размер");
      slidersetting1.setDescription("Размер узора");
      this.size = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 0.01, 0.001, 0.05, 0.001);
      slidersetting2.setName("Интенсивность");
      slidersetting2.setDescription("Яркость бликов");
      this.intensivnost = slidersetting2;
      SliderSetting slidersetting3 = new SliderSetting("", "", 1.0, 0.3, 1.0, 0.05);
      slidersetting3.setName("Прозрачность");
      slidersetting3.setDescription("Прозрачность эффекта");
      this.opacity = slidersetting3;
      this.value235 = -1;
      this.matrix4f = new Matrix4f();
      this.matrix4f2 = new Matrix4f();
      this.matrix4f3 = new Matrix4f();
      this.time = -1L;
      this.color.setVisibleWhen(this::getBoolean);
      this.palitraNeona.setVisibleWhen(this::getBoolean4);
      this.neonColor1.setVisibleWhen(this::getBoolean3);
      this.neonColor2.setVisibleWhen(this::getBoolean2);
      this.neonColor3.setVisibleWhen(this::getBoolean5);
      this.addSettings(
         new Setting[]{
            this.mode, this.color, this.palitraNeona, this.neonColor1, this.neonColor2, this.neonColor3, this.speed, this.size, this.intensivnost, this.opacity
         }
      );
      INSTANCE = this;
   }

   private Boolean getBoolean() {
      return !this.mode.isString("Неон");
   }

   private Boolean getBoolean2() {
      return this.mode.isString("Неон") && this.palitraNeona.isString("Свои");
   }

   private Boolean getBoolean3() {
      return this.mode.isString("Неон") && this.palitraNeona.isString("Свои");
   }

   @Override
   public void onDisable() {
      this.time = -1L;
      this.shaderProgram = null;
   }

   private Boolean getBoolean4() {
      return this.mode.isString("Неон");
   }

   private Boolean getBoolean5() {
      return this.mode.isString("Неон") && this.palitraNeona.isString("Свои");
   }

   private static ShaderProgramKey getShaderProgramKeyByString(String text) {
      return new ShaderProgramKey(Identifier.ofVanilla(text), VertexFormats.POSITION, Defines.EMPTY);
   }

   public static SkyShader getInstance() {
      return INSTANCE;
   }

   public void update11() {
      if (this.isEnabled()) {
         if (!ResourceManagerHooks.isFlag4()) {
            MinecraftClient minecraftclient = MinecraftClient.getInstance();
            if (minecraftclient.player != null && minecraftclient.world != null) {
               int i = ResourceManagerHooks.getInt();
               if (i != this.value235) {
                  this.shaderProgram = null;
                  this.value235 = i;
               }

               if (this.time < 0L) {
                  this.time = System.currentTimeMillis();
               }

               float f = (float)(System.currentTimeMillis() - this.time) / 1000.0F;
               ShaderProgramKey shaderprogramkey;
               if (this.mode.isString("Сияние")) {
                  shaderprogramkey = shaderProgramKey2;
               } else if (this.mode.isString("Звёзды")) {
                  shaderprogramkey = shaderProgramKey3;
               } else if (this.mode.isString("Плазма")) {
                  shaderprogramkey = shaderProgramKey4;
               } else if (this.mode.isString("Неон")) {
                  shaderprogramkey = shaderProgramKey5;
               } else {
                  shaderprogramkey = shaderProgramKey;
               }

               ShaderProgram shaderprogram;
               try {
                  shaderprogram = minecraftclient.getShaderLoader().getOrCreateProgram(shaderprogramkey);
               } catch (Throwable throwable) {
                  return;
               }

               if (shaderprogram != null) {
                  if (shaderprogram != this.shaderProgram) {
                     this.shaderProgram = shaderprogram;
                     this.glUniform = shaderprogram.getUniform("uTime");
                     this.glUniform2 = shaderprogram.getUniform("uResolution");
                     this.glUniform3 = shaderprogram.getUniform("uColor");
                     this.glUniform4 = shaderprogram.getUniform("uAlpha");
                     this.glUniform5 = shaderprogram.getUniform("uSpeed");
                     this.glUniform6 = shaderprogram.getUniform("uScale");
                     this.glUniform7 = shaderprogram.getUniform("uIntensity");
                     this.glUniform9 = shaderprogram.getUniform("uCamRight");
                     this.glUniform10 = shaderprogram.getUniform("uCamUp");
                     this.glUniform11 = shaderprogram.getUniform("uCamForward");
                     this.glUniform8 = shaderprogram.getUniform("uFov");
                     this.glUniform12 = shaderprogram.getUniform("uColor1");
                     this.glUniform13 = shaderprogram.getUniform("uColor2");
                     this.glUniform14 = shaderprogram.getUniform("uColor3");
                  }

                  float f1 = minecraftclient.getWindow().getFramebufferWidth();
                  float f2 = minecraftclient.getWindow().getFramebufferHeight();
                  float f3 = this.color.getValue() * 0.003921569F;
                  float f4 = this.color.getValue2() * 0.003921569F;
                  float f5 = this.color.getValue3() * 0.003921569F;
                  Camera camera = minecraftclient.gameRenderer.getCamera();
                  float f6 = (float)Math.toRadians(camera.getYaw());
                  float f7 = (float)Math.toRadians(camera.getPitch());
                  float f8 = ((Integer)minecraftclient.options.getFov().getValue()).floatValue();
                  float f9 = (float)Math.cos(-f6);
                  float f10 = (float)Math.sin(-f6);
                  float f11 = (float)Math.cos(-f7);
                  float f12 = (float)Math.sin(-f7);
                  float f13 = 0.0F;
                  float f14 = -f10;
                  float f15 = f10 * f12;
                  float f16 = f9 * f12;
                  float f17 = -f10 * f11;
                  float f18 = -f9 * f11;
                  this.matrix4f.set(RenderSystem.getProjectionMatrix());
                  RenderSystem.setProjectionMatrix(this.matrix4f2, ProjectionType.ORTHOGRAPHIC);
                  RenderSystem.enableBlend();
                  RenderSystem.defaultBlendFunc();
                  RenderSystem.disableDepthTest();
                  RenderSystem.depthMask(false);
                  RenderSystem.disableCull();
                  RenderSystem.setShader(shaderprogram);
                  if (this.glUniform != null) {
                     this.glUniform.set(f);
                  }

                  if (this.glUniform2 != null) {
                     this.glUniform2.set(f1, f2);
                  }

                  if (this.glUniform3 != null) {
                     this.glUniform3.set(f3, f4, f5);
                  }

                  if (this.glUniform4 != null) {
                     this.glUniform4.set((float)this.opacity.getValue());
                  }

                  if (this.glUniform5 != null) {
                     this.glUniform5.set((float)this.speed.getValue());
                  }

                  if (this.glUniform6 != null) {
                     this.glUniform6.set((float)this.size.getValue());
                  }

                  if (this.glUniform7 != null) {
                     this.glUniform7.set((float)this.intensivnost.getValue());
                  }

                  if (this.glUniform9 != null) {
                     this.glUniform9.set(f9, f13, f14);
                  }

                  if (this.glUniform10 != null) {
                     this.glUniform10.set(f15, f11, f16);
                  }

                  if (this.glUniform11 != null) {
                     this.glUniform11.set(f17, f12, f18);
                  }

                  if (this.glUniform8 != null) {
                     this.glUniform8.set(f8);
                  }

                  if (this.mode.isString("Неон") && this.glUniform12 != null && this.glUniform13 != null && this.glUniform14 != null) {
                     float[] afloat = new float[3];
                     float[] afloat1 = new float[3];
                     float[] afloat2 = new float[3];
                     this.onFloatArrayFloatArrayFloatArray(afloat1, afloat, afloat2);
                     this.glUniform12.set(afloat[0], afloat[1], afloat[2]);
                     this.glUniform13.set(afloat1[0], afloat1[1], afloat1[2]);
                     this.glUniform14.set(afloat2[0], afloat2[1], afloat2[2]);
                  }

                  BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION);
                  bufferbuilder.vertex(this.matrix4f3, -1.0F, -1.0F, 1.0F);
                  bufferbuilder.vertex(this.matrix4f3, 1.0F, -1.0F, 1.0F);
                  bufferbuilder.vertex(this.matrix4f3, 1.0F, 1.0F, 1.0F);
                  bufferbuilder.vertex(this.matrix4f3, -1.0F, 1.0F, 1.0F);
                  BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
                  RenderSystem.depthMask(true);
                  RenderSystem.enableCull();
                  RenderSystem.enableDepthTest();
                  RenderSystem.disableBlend();
                  RenderSystem.setProjectionMatrix(this.matrix4f, ProjectionType.PERSPECTIVE);
               }
            }
         }
      }
   }

   private void onFloatArrayFloatArrayFloatArray(float[] valueArray, float[] valueArray2, float[] valueArray3) {
      if (this.palitraNeona.isString("Тёмные")) {
         float f2 = 0.1F;
         float f1 = 0.1F;
         float f = 0.1F;
         onFloatFloatArrayFloatFloat(f, valueArray2, f1, f2);
         float f5 = 0.25F;
         float f4 = 0.25F;
         float f3 = 0.25F;
         onFloatFloatArrayFloatFloat(f3, valueArray, f4, f5);
         float f8 = 0.45F;
         float f7 = 0.45F;
         float f6 = 0.45F;
         onFloatFloatArrayFloatFloat(f6, valueArray3, f7, f8);
      } else if (this.palitraNeona.isString("Светлые")) {
         float f11 = 0.8F;
         float f10 = 0.8F;
         float f9 = 0.8F;
         onFloatFloatArrayFloatFloat(f9, valueArray2, f10, f11);
         float f14 = 0.95F;
         float f13 = 0.95F;
         float f12 = 0.95F;
         onFloatFloatArrayFloatFloat(f12, valueArray, f13, f14);
         float f17 = 1.0F;
         float f16 = 1.0F;
         float f15 = 1.0F;
         onFloatFloatArrayFloatFloat(f15, valueArray3, f16, f17);
      } else if (this.palitraNeona.isString("Свои")) {
         ColorSetting colorsetting = this.neonColor1;
         onFloatArrayColorSetting(valueArray2, colorsetting);
         ColorSetting colorsetting1 = this.neonColor2;
         onFloatArrayColorSetting(valueArray, colorsetting1);
         ColorSetting colorsetting2 = this.neonColor3;
         onFloatArrayColorSetting(valueArray3, colorsetting2);
      } else {
         float f20 = 0.0F;
         float f19 = 0.0F;
         float f18 = 1.0F;
         onFloatFloatArrayFloatFloat(f18, valueArray2, f19, f20);
         float f23 = 0.0F;
         float f22 = 1.0F;
         float f21 = 0.0F;
         onFloatFloatArrayFloatFloat(f21, valueArray, f22, f23);
         float f26 = 1.0F;
         float f25 = 0.0F;
         float f24 = 0.0F;
         onFloatFloatArrayFloatFloat(f24, valueArray3, f25, f26);
      }
   }

   private static void onFloatFloatArrayFloatFloat(float value, float[] valueArray, float value2, float value3) {
      valueArray[0] = value;
      valueArray[1] = value2;
      valueArray[2] = value3;
   }

   private static void onFloatArrayColorSetting(float[] valueArray, ColorSetting colorSetting) {
      valueArray[0] = colorSetting.getValue() * 0.003921569F;
      valueArray[1] = colorSetting.getValue2() * 0.003921569F;
      valueArray[2] = colorSetting.getValue3() * 0.003921569F;
   }

   @Override
   public void onEnable() {
      this.time = -1L;
   }
}
