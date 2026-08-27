package client.module.visual;

import client.concurrent.ResourceManagerHooks;
import client.module.Category;
import client.module.Module;
import client.render.ShaderCache;
import client.render.ShaderUniforms;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.DisplayInfo;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gl.ShaderLoader;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MotionBlur extends Module {
   private static final Identifier identifier = Identifier.ofVanilla("motion_blur");
   private static final ShaderProgramKey shaderProgramKey = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "motion_blur", VertexFormats.POSITION, Defines.EMPTY
   );
   private static final Matrix4f matrix4f = new Matrix4f();
   private static final Matrix4f matrix4f2 = new Matrix4f();
   private static final Vector3f vector3f = new Vector3f();
   private static final Matrix4f matrix4f3 = new Matrix4f();
   private static final Matrix4f matrix4f4 = new Matrix4f();
   private static final Vector3f vector3f2 = new Vector3f();
   private static final Matrix4f matrix4f5 = new Matrix4f();
   private static boolean flag = false;
   private static float value235 = 1.0F;
   private static int value236 = 32;
   private static boolean flag2 = true;
   private static boolean flag3 = false;
   private static boolean flag4 = false;
   private static boolean flag5 = true;
   private static boolean flag6 = false;
   private static boolean flag7 = false;
   private static long time = 16666666L;
   private static long time2 = 0L;
   private static float value237 = 1.0F;
   private static ObjectAllocator objectAllocator = null;
   private static PostEffectProcessor postEffectProcessor = null;
   private static boolean flag8 = false;
   private static int value238 = -1;
   private SliderSetting strength;
   private SliderSetting semply;
   private BooleanSetting poGlubine;
   private BooleanSetting poCentru;
   private BooleanSetting otTretegoLica;
   private BooleanSetting kompensaciyaGc;

   public MotionBlur() {
      super("MotionBlur", Category.VISUAL);
      SliderSetting slidersetting = new SliderSetting("", "", 1.0, 0.0, 3.0, 0.05, "x", 2);
      slidersetting.setName("Сила");
      slidersetting.setDescription("Длина смаза за один кадр");
      this.strength = slidersetting;
      slidersetting = new SliderSetting("", "", 32.0, 4.0, 128.0, 1.0, "", 0);
      slidersetting.setName("Сэмплы");
      slidersetting.setDescription("Предел выборок на пиксель: выше — плавнее, но дороже");
      this.semply = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("По глубине");
      booleansetting.setDescription("Смазывать по глубине сцены, a не только от поворота камеры");
      this.poGlubine = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("По центру");
      booleansetting1.setDescription("След в обе стороны от пикселя, a не только назад");
      this.poCentru = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", false);
      booleansetting2.setName("От третьего лица");
      booleansetting2.setDescription("Смазывать картинку и в виде от третьего лица");
      this.otTretegoLica = booleansetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", true);
      booleansetting3.setName("Компенсация Гц");
      booleansetting3.setDescription("Усиливать след, когда FPS выше частоты монитора");
      this.kompensaciyaGc = booleansetting3;
      this.addSettings(new Setting[]{this.strength, this.semply, this.poGlubine, this.poCentru, this.otTretegoLica, this.kompensaciyaGc});
   }

   @Override
   public void onTick() {
      if (this.isEnabled()) {
         flag = true;
         this.update12();
      }
   }

   private static void update11() {
      long i = System.nanoTime();
      long j = i - time2;
      time2 = i;
      if (j > 0L && j < time) {
         value237 = Math.min((float)time / (float)j, 8.0F);
      } else {
         value237 = 1.0F;
      }
   }

   @Override
   public void onDisable() {
      flag = false;
      flag6 = false;
      flag7 = false;
   }

   public static boolean check3() {
      return flag && flag7 && value235 > 0.0F && (flag4 || mc.options.getPerspective().isFirstPerson());
   }

   private static float getFloat() {
      return flag5 ? value235 * value237 : value235;
   }

   private static PostEffectProcessor getPostEffectProcessorByShaderLoader(ShaderLoader shaderLoader) {
      int i = ResourceManagerHooks.getInt();
      if (i != value238) {
         value238 = i;
         postEffectProcessor = null;
         flag8 = false;
      }

      if (postEffectProcessor == null && !flag8) {
         try {
            postEffectProcessor = shaderLoader.loadPostEffect(identifier, DefaultFramebufferSet.MAIN_ONLY);
         } catch (Throwable throwable) {
         }

         flag8 = postEffectProcessor == null;
      }

      return postEffectProcessor;
   }

   private void update12() {
      value235 = this.strength.getValueAsFloat();
      value236 = this.semply.getInt2();
      flag2 = this.poGlubine.isFlag3();
      flag3 = this.poCentru.isFlag3();
      flag4 = this.otTretegoLica.isFlag3();
      flag5 = this.kompensaciyaGc.isFlag3();
   }

   public static void onMatrix4fObjectAllocatorMatrix4fCamera(Matrix4f matrix4f5, ObjectAllocator objectAllocator2, Matrix4f matrix4f6, Camera camera) {
      if (flag && camera != null && !mc.gameRenderer.isRenderingPanorama()) {
         objectAllocator = objectAllocator2;
         update11();
         if (flag6) {
            matrix4f3.set(matrix4f);
            matrix4f4.set(matrix4f2);
            vector3f2.set(vector3f);
            flag7 = true;
         }

         Vec3d vec3d = camera.getPos();
         matrix4f.set(matrix4f5);
         matrix4f2.set(matrix4f6);
         vector3f.set((float)(vec3d.x % 30000.0), (float)(vec3d.y % 30000.0), (float)(vec3d.z % 30000.0));
         flag6 = true;
         if (flag7 && vector3f.distanceSquared(vector3f2) > 16.0F) {
            flag7 = false;
         }
      } else {
         flag6 = false;
         flag7 = false;
         time2 = 0L;
      }
   }

   public static void onFramebuffer(Framebuffer framebuffer) {
      if (check3()) {
         if (!ResourceManagerHooks.isFlag4()) {
            ObjectAllocator objectallocator = objectAllocator;
            if (framebuffer != null && objectallocator != null) {
               ShaderLoader shaderloader = mc.getShaderLoader();
               if (shaderloader != null) {
                  PostEffectProcessor posteffectprocessor = getPostEffectProcessorByShaderLoader(shaderloader);
                  if (posteffectprocessor != null) {
                     ShaderProgram shaderprogram = shaderloader.getOrCreateProgram(shaderProgramKey);
                     if (shaderprogram != null) {
                        Matrix4f matrix4fx = matrix4f5.set(matrix4f).invert();
                        String s = "MvInverse";
                        ShaderUniforms.onStringMatrix4fShaderProgram(s, matrix4fx, shaderprogram);
                        Matrix4f matrix4f1 = matrix4f5.set(matrix4f2).invert();
                        String s1 = "ProjInverse";
                        ShaderUniforms.onStringMatrix4fShaderProgram(s1, matrix4f1, shaderprogram);
                        Matrix4f matrix4f2x = matrix4f3;
                        String s2 = "PrevModelView";
                        ShaderUniforms.onStringMatrix4fShaderProgram(s2, matrix4f2x, shaderprogram);
                        Matrix4f matrix4f3x = matrix4f4;
                        String s3 = "PrevProjection";
                        ShaderUniforms.onStringMatrix4fShaderProgram(s3, matrix4f3x, shaderprogram);
                        float f2 = vector3f.z;
                        float f1 = vector3f.y;
                        float f = vector3f.x;
                        String s4 = "CameraPos";
                        ShaderUniforms.onShaderProgramFloatStringFloatFloat(shaderprogram, f1, s4, f2, f);
                        float f5 = vector3f2.z;
                        float f4 = vector3f2.y;
                        float f3 = vector3f2.x;
                        String s5 = "PrevCameraPos";
                        ShaderUniforms.onShaderProgramFloatStringFloatFloat(shaderprogram, f4, s5, f5, f3);
                        float f6 = getFloat();
                        String s6 = "Strength";
                        ShaderUniforms.onFloatShaderProgramString(f6, shaderprogram, s6);
                        int i = value236;
                        String s7 = "Samples";
                        ShaderUniforms.onShaderProgramIntString(shaderprogram, i, s7);
                        int j = flag3 ? 1 : 0;
                        String s8 = "Centered";
                        ShaderUniforms.onShaderProgramIntString(shaderprogram, j, s8);
                        int k = flag2 ? 1 : 0;
                        String s9 = "UseDepth";
                        ShaderUniforms.onShaderProgramIntString(shaderprogram, k, s9);

                        try {
                           posteffectprocessor.render(framebuffer, objectallocator);
                        } catch (Throwable throwable) {
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
      flag = true;
      flag6 = false;
      flag7 = false;
      time = 1000000000L / DisplayInfo.getInt();
      time2 = 0L;
      value237 = 1.0F;
      this.update12();
   }
}
