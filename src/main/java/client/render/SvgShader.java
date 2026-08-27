package client.render;

import client.data.FloatParser;
import client.data.SvgPath;
import client.module.CategoryType;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.geom.PathIterator;
import java.awt.geom.Path2D.Float;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

public class SvgShader {
   private static final int value = 256;
   private static final float value2 = 0.12F;
   private static final float value3 = 1.0F;
   private static final ShaderProgramKey shaderProgramKey = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "svg", VertexFormats.POSITION_TEXTURE, Defines.EMPTY
   );
   private static final Map<CategoryType, SvgPath> map = new EnumMap<>(CategoryType.class);
   private static final FloatBuffer floatBuffer = MemoryUtil.memAllocFloat(1024);
   private static int value4 = -1;
   private static int value5 = -1;
   private static SvgPath svgPath = null;
   private static int value6 = -1;
   private static int value7;
   private static int value8;
   private static int value9;
   private static float[] floatArray = new float[1280];
   private static SvgPath svgPath2;
   private static CategoryType categoryType;
   private static int value10;
   private static float value11;
   private static float value12;

   private SvgShader() {
   }

   public static void update() {
      if (value7 > 0 && --value7 == 0) {
         update2();
      }
   }

   private static SvgPath getSvgPathByCategoryType(CategoryType categoryType) {
      float[] afloat = new float[1024];
      int i = 0;

      for (String s : categoryType.getPaths()) {
         Float f;
         try {
            f = FloatParser.getFloatByString(s);
         } catch (RuntimeException runtimeexception) {
            continue;
         }

         i = getIntByFloatArrayIntFloat(afloat, i, f);
         if (i >= 256) {
            break;
         }
      }

      if (i < 256) {
         float[] afloat1 = new float[i * 4];
         System.arraycopy(afloat, 0, afloat1, 0, i * 4);
         afloat = afloat1;
      }

      return new SvgPath(afloat, i);
   }

   private static void update2() {
      if (value8 != 0) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();

         try {
            ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey);
            if (shaderprogram != null) {
               CategoryType categorytype = categoryType;
               float f3 = categorytype.getHeight();
               float f2 = categorytype.getWidth();
               float f1 = 0.0F;
               float f = 0.0F;
               String s = "ViewBox";
               ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f3, f1, shaderprogram, s, f2, f);
               float f4 = value12;
               String s1 = "PaddingSvg";
               ShaderUniforms.onFloatShaderProgramString(f4, shaderprogram, s1);
               float f5 = categorytype.getStrokeWidth();
               String s2 = "StrokeWidth";
               ShaderUniforms.onFloatShaderProgramString(f5, shaderprogram, s2);
               float f13 = ShaderUniforms.getFloatByInt4(value10);
               float f14 = ShaderUniforms.getFloatByInt(value10);
               float f9 = ShaderUniforms.getFloatByInt3(value10);
               float f8 = ShaderUniforms.getFloatByInt2(value10);
               float f7 = f14;
               float f6 = f13;
               String s3 = "Color";
               ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f9, f7, shaderprogram, s3, f8, f6);
               float f10 = value11;
               String s4 = "GlobalAlpha";
               ShaderUniforms.onFloatShaderProgramString(f10, shaderprogram, s4);
               float f11 = categorytype.isFilled() ? 1.0F : 0.0F;
               String s5 = "Filled";
               ShaderUniforms.onFloatShaderProgramString(f11, shaderprogram, s5);
               float f12 = svgPath2.count();
               String s6 = "SegmentCount";
               ShaderUniforms.onFloatShaderProgramString(f12, shaderprogram, s6);
               SvgPath svgpath = svgPath2;
               onSvgPathShaderProgram(svgpath, shaderprogram);
               BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

               for (int b0 = 0; b0 < value9; b0 += 5) {
                  bufferbuilder.vertex(floatArray[b0], floatArray[b0 + 1], floatArray[b0 + 2]).texture(floatArray[b0 + 3], floatArray[b0 + 4]);
               }

               BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
               return;
            }
         } finally {
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            value8 = 0;
            value9 = 0;
         }
      }
   }

   public static void update3() {
      synchronized (map) {
         map.clear();
      }

      svgPath = null;
      value6 = -1;
      value8 = 0;
      value9 = 0;
      svgPath2 = null;
   }

   private static SvgPath getSvgPathByCategoryType2(CategoryType categoryType) {
      synchronized (map) {
         SvgPath svgpath = map.get(categoryType);
         if (svgpath != null) {
            return svgpath;
         } else {
            SvgPath svgpath1 = getSvgPathByCategoryType(categoryType);
            map.put(categoryType, svgpath1);
            return svgpath1;
         }
      }
   }

   private static int getIntByFloatArrayIntFloat(float[] valueArray, int count, Float value) {
      PathIterator pathiterator = value.getPathIterator(null, 0.12F);
      float[] afloat = new float[6];
      float f = 0.0F;
      float f1 = 0.0F;
      float f2 = 0.0F;

      for (float f3 = 0.0F; !pathiterator.isDone() && count < 256; pathiterator.next()) {
         int i = pathiterator.currentSegment(afloat);
         switch (i) {
            case 0:
               f = afloat[0];
               f1 = afloat[1];
               f2 = f;
               f3 = f1;
               break;
            case 1:
               float f6 = afloat[0];
               float f5 = afloat[1];
               float f4 = f6;
               count = getIntByIntFloatFloatArrayFloatFloatFloat(count, f5, valueArray, f4, f1, f);
               f = afloat[0];
               f1 = afloat[1];
            case 2:
            case 3:
            default:
               break;
            case 4:
               count = getIntByIntFloatFloatArrayFloatFloatFloat(count, f3, valueArray, f2, f1, f);
               f = f2;
               f1 = f3;
         }
      }

      return count;
   }

   private static void onFloatMatrix4fFloatCategoryTypeFloatFloatIntFloatSvgPath(
      float value, Matrix4f matrix4f, float value2, CategoryType categoryType, float value3, float value4, int count2, float value5, SvgPath svgPath
   ) {
      update2();
      float f = categoryType.getWidth();
      float f1 = categoryType.getHeight();
      float f2 = Math.min(value3 / f, value5 / f1);
      float f3 = f * f2;
      float f4 = f1 * f2;
      float f5 = value4 + (value3 - f3) * 0.5F;
      float f6 = value + (value5 - f4) * 0.5F;
      float f7 = f3 / f;
      float f8 = categoryType.getStrokeWidth() * 0.5F + 1.0F / Math.max(f7, 1.0E-4F);
      float f9 = f8 * f7;
      float f10 = f5 - f9;
      float f11 = f6 - f9;
      float f12 = f3 + f9 * 2.0F;
      float f13 = f4 + f9 * 2.0F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();

      try {
         ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey);
         if (shaderprogram != null) {
            float f15 = 0.0F;
            float f14 = 0.0F;
            String s = "ViewBox";
            ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f1, f15, shaderprogram, s, f, f14);
            String s1 = "PaddingSvg";
            ShaderUniforms.onFloatShaderProgramString(f8, shaderprogram, s1);
            float f16 = categoryType.getStrokeWidth();
            String s2 = "StrokeWidth";
            ShaderUniforms.onFloatShaderProgramString(f16, shaderprogram, s2);
            float f23 = ShaderUniforms.getFloatByInt4(count2);
            float f24 = ShaderUniforms.getFloatByInt(count2);
            float f20 = ShaderUniforms.getFloatByInt3(count2);
            float f19 = ShaderUniforms.getFloatByInt2(count2);
            float f18 = f24;
            float f17 = f23;
            String s3 = "Color";
            ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f20, f18, shaderprogram, s3, f19, f17);
            String s4 = "GlobalAlpha";
            ShaderUniforms.onFloatShaderProgramString(value2, shaderprogram, s4);
            float f21 = categoryType.isFilled() ? 1.0F : 0.0F;
            String s5 = "Filled";
            ShaderUniforms.onFloatShaderProgramString(f21, shaderprogram, s5);
            float f22 = svgPath.count();
            String s6 = "SegmentCount";
            ShaderUniforms.onFloatShaderProgramString(f22, shaderprogram, s6);
            onSvgPathShaderProgram(svgPath, shaderprogram);
            BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            bufferbuilder.vertex(matrix4f, f10, f11, 0.0F).texture(0.0F, 0.0F);
            bufferbuilder.vertex(matrix4f, f10, f11 + f13, 0.0F).texture(0.0F, 1.0F);
            bufferbuilder.vertex(matrix4f, f10 + f12, f11 + f13, 0.0F).texture(1.0F, 1.0F);
            bufferbuilder.vertex(matrix4f, f10 + f12, f11, 0.0F).texture(1.0F, 0.0F);
            BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
            return;
         }
      } finally {
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
      }
   }

   private static void onFloatArrayIntShaderProgram(float[] valueArray, int count, ShaderProgram shaderProgram) {
      int i = shaderProgram.getGlRef();
      if (i != value4) {
         value5 = GL20.glGetUniformLocation(i, "Segments");
         value4 = i;
      }

      if (value5 >= 0) {
         floatBuffer.clear();
         floatBuffer.put(valueArray, 0, count * 4);
         floatBuffer.flip();
         GlStateManager._glUseProgram(i);
         GL20.glUniform4fv(value5, floatBuffer);
         svgPath = null;
         value6 = -1;
      }
   }

   public static void onFloatFloatMatrix4fFloatFloatArrayIntFloatFloatFloatFloatFloatInt(
      float value, float value2, Matrix4f matrix4f, float value3, float[] valueArray, int count, float value4, float value5, float value6, float value7, float value8, int count2
   ) {
      if (count > 0 && !(value6 <= 0.0F) && !(value <= 0.0F)) {
         update2();
         float f = Math.min(value4 / value6, value2 / value);
         float f1 = value6 * f;
         float f2 = value * f;
         float f3 = value3 + (value4 - f1) * 0.5F;
         float f4 = value8 + (value2 - f2) * 0.5F;
         float f5 = f1 / value6;
         float f6 = value5 * 0.5F + 1.0F / Math.max(f5, 1.0E-4F);
         float f7 = f6 * f5;
         float f8 = f3 - f7;
         float f9 = f4 - f7;
         float f10 = f1 + f7 * 2.0F;
         float f11 = f2 + f7 * 2.0F;
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();

         try {
            ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey);
            if (shaderprogram != null) {
               float f13 = 0.0F;
               float f12 = 0.0F;
               String s = "ViewBox";
               ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(value, f13, shaderprogram, s, value6, f12);
               String s1 = "PaddingSvg";
               ShaderUniforms.onFloatShaderProgramString(f6, shaderprogram, s1);
               String s2 = "StrokeWidth";
               ShaderUniforms.onFloatShaderProgramString(value5, shaderprogram, s2);
               float f20 = ShaderUniforms.getFloatByInt4(count2);
               float f21 = ShaderUniforms.getFloatByInt(count2);
               float f17 = ShaderUniforms.getFloatByInt3(count2);
               float f16 = ShaderUniforms.getFloatByInt2(count2);
               float f15 = f21;
               float f14 = f20;
               String s3 = "Color";
               ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f17, f15, shaderprogram, s3, f16, f14);
               String s4 = "GlobalAlpha";
               ShaderUniforms.onFloatShaderProgramString(value7, shaderprogram, s4);
               float f18 = 0.0F;
               String s5 = "Filled";
               ShaderUniforms.onFloatShaderProgramString(f18, shaderprogram, s5);
               float f19 = count;
               String s6 = "SegmentCount";
               ShaderUniforms.onFloatShaderProgramString(f19, shaderprogram, s6);
               onFloatArrayIntShaderProgram(valueArray, count, shaderprogram);
               BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
               bufferbuilder.vertex(matrix4f, f8, f9, 0.0F).texture(0.0F, 0.0F);
               bufferbuilder.vertex(matrix4f, f8, f9 + f11, 0.0F).texture(0.0F, 1.0F);
               bufferbuilder.vertex(matrix4f, f8 + f10, f9 + f11, 0.0F).texture(1.0F, 1.0F);
               bufferbuilder.vertex(matrix4f, f8 + f10, f9, 0.0F).texture(1.0F, 0.0F);
               BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
               return;
            }
         } finally {
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private static int getIntByIntFloatFloatArrayFloatFloatFloat(int count, float value, float[] valueArray, float value2, float value3, float value4) {
      float f = value2 - value4;
      float f1 = value - value3;
      if (f * f + f1 * f1 < 1.0E-8F) {
         return count;
      } else {
         int i = count * 4;
         valueArray[i] = value4;
         valueArray[i + 1] = value3;
         valueArray[i + 2] = value2;
         valueArray[i + 3] = value;
         return count + 1;
      }
   }

   public static void onIntFloatFloatFloatFloatFloatMatrix4f(int count, float value, float value2, float value3, float value4, float value5, Matrix4f matrix4f) {
      float f = Math.min(value3, value2) * 0.2F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f, value, count, matrix4f, value2, value3, value4, value5);
   }

   public static void onFloatFloatMatrix4fFloatFloatInt(float value, float value2, Matrix4f matrix4f, float value3, float value4, int count) {
      float f = value4 * 0.2F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f, value3, count, matrix4f, value4, value4, value, value2);
   }

   public static void onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(
      float value, int count2, Matrix4f matrix4f, float value2, CategoryType categoryType, float value3, float value4, float value5
   ) {
      SvgPath svgpath = getSvgPathByCategoryType2(categoryType);
      if (svgpath.count() == 0) {
         update2();
         float f = Math.min(value5, value3) * 0.2F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f, value4, count2, matrix4f, value3, value5, value, value2);
      } else if (value7 > 0) {
         onIntFloatCategoryTypeMatrix4fFloatFloatFloatFloatSvgPath(count2, value2, categoryType, matrix4f, value4, value3, value5, value, svgpath);
      } else {
         onFloatMatrix4fFloatCategoryTypeFloatFloatIntFloatSvgPath(value2, matrix4f, value, categoryType, value5, value4, count2, value3, svgpath);
      }
   }

   public static void onFloatCategoryTypeIntMatrix4fFloatFloatFloat(float value, CategoryType categoryType, int count, Matrix4f matrix4f, float value2, float value3, float value4) {
      onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, count, matrix4f, value, categoryType, value3, value4, value3);
   }

   private static void onSvgPathShaderProgram(SvgPath svgPath2, ShaderProgram shaderProgram) {
      int i = shaderProgram.getGlRef();
      if (i != value4) {
         value5 = GL20.glGetUniformLocation(i, "Segments");
         value4 = i;
      }

      if (value5 >= 0) {
         if (svgPath2 != svgPath || i != value6) {
            floatBuffer.clear();
            floatBuffer.put(svgPath2.segments(), 0, svgPath2.count() * 4);
            floatBuffer.flip();
            GlStateManager._glUseProgram(i);
            GL20.glUniform4fv(value5, floatBuffer);
            svgPath = svgPath2;
            value6 = i;
         }
      }
   }

   public static void update4() {
      value7++;
   }

   private static void onFloatFloatMatrix4fFloatFloat(float value, float value2, Matrix4f matrix4f, float value3, float value4) {
      int i = value9;
      floatArray[i] = matrix4f.m00() * value + matrix4f.m10() * value4 + matrix4f.m30();
      floatArray[i + 1] = matrix4f.m01() * value + matrix4f.m11() * value4 + matrix4f.m31();
      floatArray[i + 2] = matrix4f.m02() * value + matrix4f.m12() * value4 + matrix4f.m32();
      floatArray[i + 3] = value2;
      floatArray[i + 4] = value3;
      value9 += 5;
   }

   private static void onIntFloatCategoryTypeMatrix4fFloatFloatFloatFloatSvgPath(
      int count, float value, CategoryType categoryType2, Matrix4f matrix4f, float value2, float value3, float value4, float value5, SvgPath svgPath
   ) {
      float f = categoryType2.getWidth();
      float f1 = categoryType2.getHeight();
      float f2 = Math.min(value4 / f, value3 / f1);
      float f3 = f * f2;
      float f4 = f1 * f2;
      float f5 = value2 + (value4 - f3) * 0.5F;
      float f6 = value + (value3 - f4) * 0.5F;
      float f7 = f3 / f;
      float f8 = categoryType2.getStrokeWidth() * 0.5F + 1.0F / Math.max(f7, 1.0E-4F);
      float f9 = f8 * f7;
      if (value8 > 0 && (categoryType != categoryType2 || value10 != count || value11 != value5 || value12 != f8)) {
         update2();
      }

      categoryType = categoryType2;
      svgPath2 = svgPath;
      value10 = count;
      value11 = value5;
      value12 = f8;
      if (value9 + 20 > floatArray.length) {
         floatArray = Arrays.copyOf(floatArray, floatArray.length * 2);
      }

      float f10 = f5 - f9;
      float f11 = f6 - f9;
      float f12 = f3 + f9 * 2.0F;
      float f13 = f4 + f9 * 2.0F;
      float f15 = 0.0F;
      float f14 = 0.0F;
      onFloatFloatMatrix4fFloatFloat(f10, f14, matrix4f, f15, f11);
      float f27 = f11 + f13;
      float f18 = 1.0F;
      float f17 = 0.0F;
      float f16 = f27;
      onFloatFloatMatrix4fFloatFloat(f10, f17, matrix4f, f18, f16);
      float f26 = f10 + f12;
      f27 = f11 + f13;
      float f22 = 1.0F;
      float f21 = 1.0F;
      float f20 = f27;
      float f19 = f26;
      onFloatFloatMatrix4fFloatFloat(f19, f21, matrix4f, f22, f20);
      f26 = f10 + f12;
      float f25 = 0.0F;
      float f24 = 1.0F;
      float f23 = f26;
      onFloatFloatMatrix4fFloatFloat(f23, f24, matrix4f, f25, f11);
      value8++;
   }
}
