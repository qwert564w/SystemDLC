package client.render;

import client.module.Feature;
import client.module.client.ThemeModule;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;

public class ShapeShader {
   public static final int value = 0;
   private static final ShaderProgramKey shaderProgramKey = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "rect", VertexFormats.POSITION_TEXTURE, Defines.EMPTY
   );
   private static final ShaderProgramKey shaderProgramKey2 = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "liquidglass_rect", VertexFormats.POSITION_TEXTURE, Defines.EMPTY
   );
   private static final ShaderProgramKey shaderProgramKey3 = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "gradient", VertexFormats.POSITION_TEXTURE, Defines.EMPTY
   );
   private static final ShaderProgramKey shaderProgramKey4 = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "colorbox", VertexFormats.POSITION_TEXTURE, Defines.EMPTY
   );
   private static final ShaderProgramKey shaderProgramKey5 = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "huestrip", VertexFormats.POSITION_TEXTURE, Defines.EMPTY
   );
   private static final ShaderProgramKey shaderProgramKey6 = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "ring", VertexFormats.POSITION_TEXTURE, Defines.EMPTY
   );
   private static int value2 = 0;
   private static long time = 0L;
   private static float value3 = 0.0F;
   private static ShaderProgram shaderProgram;
   private static long time2 = -1L;

   public static void update() {
      if (value2 != 0) {
         if (--value2 == 0) {
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   public static int getIntByIntIntIntInt(int count, int count2, int count3, int count4) {
      return getIntByInt(count4) << 24 | getIntByInt(count2) << 16 | getIntByInt(count) << 8 | getIntByInt(count3);
   }

   public static int getIntByIntIntFloatInt(int count, int count2, float value, int count3) {
      int i = Math.round(getFloatByFloat(value) * 255.0F);
      return getIntByIntIntIntInt(count2, count, count3, i);
   }

   public static int getIntByIntFloat(int count, float value) {
      int i = Math.round((count >>> 24 & 0xFF) * getFloatByFloat(value));
      return count & 16777215 | i << 24;
   }

   private static int getIntByInt(int count) {
      return Math.clamp((long)count, 0, 255);
   }

   public static void onFloatIntFloatFloatFloatFloatFloatIntMatrix4f(
      float value, int count, float value2, float value3, float value4, float value5, float value6, int count2, Matrix4f matrix4f
   ) {
      onFloatIntFloatFloatFloatIntMatrix4fFloatIntIntFloat(value3, count2, value2, value, value5, count, matrix4f, value6, count, count2, value4);
   }

   public static void onIntMatrix4fFloatIntFloatFloatFloat(int count, Matrix4f matrix4f, float value, int count2, float value2, float value3, float value4) {
      onFloatIntIntFloatFloatMatrix4fIntIntFloat(value2, count2, count, value4, value3, matrix4f, count2, count, value);
   }

   public static void onIntFloatFloatIntFloatFloatMatrix4fFloatFloat(
      int count, float value, float value2, int count2, float value3, float value4, Matrix4f matrix4f, float value5, float value6
   ) {
      onFloatIntFloatFloatFloatIntMatrix4fFloatIntIntFloat(value3, count2, value6, value2, value5, count2, matrix4f, value4, count, count, value);
   }

   public static void onFloatIntIntFloatFloatMatrix4fIntIntFloat(
      float value, int count, int count2, float value2, float value3, Matrix4f matrix4f, int count3, int count4, float value4
   ) {
      float f1 = 1.0F;
      float f = 0.0F;
      onFloatIntFloatFloatFloatIntMatrix4fFloatIntIntFloat(value2, count2, f1, f, value4, count3, matrix4f, value3, count, count4, value);
   }

   public static void onIntFloatMatrix4fFloatFloatFloatFloatFloat(
      int count, float value, Matrix4f matrix4f, float value3, float value4, float value5, float value6, float value7
   ) {
      if (!(value7 <= 0.0F) && !(value4 <= 0.0F) && !(value3 <= 0.0F) && !(value6 <= 0.0F)) {
         if (!(getFloatByIntFloat(count, value6) <= 0.0F)) {
            boolean flag = value2 > 0;
            if (!flag) {
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableCull();
            }

            try {
               ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey6);
               if (shaderprogram != null) {
                  String s = "Size";
                  ShaderUniforms.onStringFloatFloatShaderProgram(s, value7, value7, shaderprogram);
                  float f = Math.min(value4, value7 * 0.5F);
                  String s1 = "Thickness";
                  ShaderUniforms.onFloatShaderProgramString(f, shaderprogram, s1);
                  float f1 = Math.clamp(value3, 0.0F, 1.0F);
                  String s2 = "Progress";
                  ShaderUniforms.onFloatShaderProgramString(f1, shaderprogram, s2);
                  float f6 = ShaderUniforms.getFloatByInt4(count);
                  float f7 = ShaderUniforms.getFloatByInt(count);
                  float f5 = ShaderUniforms.getFloatByInt3(count);
                  float f4 = ShaderUniforms.getFloatByInt2(count);
                  float f3 = f7;
                  float f2 = f6;
                  String s3 = "RingColor";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f5, f3, shaderprogram, s3, f4, f2);
                  String s4 = "GlobalAlpha";
                  ShaderUniforms.onFloatShaderProgramString(value6, shaderprogram, s4);
                  BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                  bufferbuilder.vertex(matrix4f, value5, value, 0.0F).texture(0.0F, 0.0F);
                  bufferbuilder.vertex(matrix4f, value5, value + value7, 0.0F).texture(0.0F, 1.0F);
                  bufferbuilder.vertex(matrix4f, value5 + value7, value + value7, 0.0F).texture(1.0F, 1.0F);
                  bufferbuilder.vertex(matrix4f, value5 + value7, value, 0.0F).texture(1.0F, 0.0F);
                  BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
                  return;
               }
            } finally {
               if (!flag) {
                  RenderSystem.enableCull();
                  RenderSystem.disableBlend();
               }
            }
         }
      }
   }

   private static float getFloatByIntFloat(int count, float value) {
      return ShaderUniforms.getFloatByInt3(count) * value;
   }

   public static void onFloatFloatMatrix4fFloatFloatFloatFloat(float value, float value3, Matrix4f matrix4f, float value4, float value5, float value6, float value7) {
      if (!(value6 <= 0.0F) && !(value <= 0.0F) && !(value7 <= 0.0F)) {
         boolean flag = value2 > 0;
         if (!flag) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
         }

         try {
            ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey5);
            if (shaderprogram != null) {
               String s = "Size";
               ShaderUniforms.onStringFloatFloatShaderProgram(s, value, value6, shaderprogram);
               String s1 = "Radius";
               ShaderUniforms.onFloatShaderProgramString(value4, shaderprogram, s1);
               String s2 = "GlobalAlpha";
               ShaderUniforms.onFloatShaderProgramString(value7, shaderprogram, s2);
               BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
               bufferbuilder.vertex(matrix4f, value5, value3, 0.0F).texture(0.0F, 0.0F);
               bufferbuilder.vertex(matrix4f, value5, value3 + value, 0.0F).texture(0.0F, 1.0F);
               bufferbuilder.vertex(matrix4f, value5 + value6, value3 + value, 0.0F).texture(1.0F, 1.0F);
               bufferbuilder.vertex(matrix4f, value5 + value6, value3, 0.0F).texture(1.0F, 0.0F);
               BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
               return;
            }
         } finally {
            if (!flag) {
               RenderSystem.enableCull();
               RenderSystem.disableBlend();
            }
         }
      }
   }

   public static void onFloatMatrix4fFloatFloatFloatFloatFloatInt(
      float value, Matrix4f matrix4f, float value3, float value4, float value5, float value6, float value7, int count
   ) {
      if (!(value6 <= 0.0F) && !(value3 <= 0.0F) && !(value <= 0.0F)) {
         boolean flag = value2 > 0;
         if (!flag) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
         }

         try {
            ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey4);
            if (shaderprogram != null) {
               String s = "Size";
               ShaderUniforms.onStringFloatFloatShaderProgram(s, value3, value6, shaderprogram);
               String s1 = "Radius";
               ShaderUniforms.onFloatShaderProgramString(value4, shaderprogram, s1);
               float f3 = ShaderUniforms.getFloatByInt4(count);
               float f2 = ShaderUniforms.getFloatByInt2(count);
               float f1 = ShaderUniforms.getFloatByInt(count);
               float f = f3;
               String s2 = "HueColor";
               ShaderUniforms.onShaderProgramFloatStringFloatFloat(shaderprogram, f1, s2, f2, f);
               String s3 = "GlobalAlpha";
               ShaderUniforms.onFloatShaderProgramString(value, shaderprogram, s3);
               BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
               bufferbuilder.vertex(matrix4f, value5, value7, 0.0F).texture(0.0F, 0.0F);
               bufferbuilder.vertex(matrix4f, value5, value7 + value3, 0.0F).texture(0.0F, 1.0F);
               bufferbuilder.vertex(matrix4f, value5 + value6, value7 + value3, 0.0F).texture(1.0F, 1.0F);
               bufferbuilder.vertex(matrix4f, value5 + value6, value7, 0.0F).texture(1.0F, 0.0F);
               BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
               return;
            }
         } finally {
            if (!flag) {
               RenderSystem.enableCull();
               RenderSystem.disableBlend();
            }
         }
      }
   }

   private static float getFloatByFloat(float value) {
      return Math.clamp(value, 0.0F, 1.0F);
   }

   public static void onFloatFloatFloatIntFloatArrayFloatFloatArrayFloatMatrix4fFloatFloatFloatFloatFloatFloatIntFloatFloatIntFloat(
      float value,
      float value2,
      float value3,
      int count,
      float[] valueArray,
      float value4,
      float[] valueArray2,
      float value5,
      Matrix4f matrix4f,
      float value6,
      float value7,
      float value8,
      float value9,
      float value10,
      float value11,
      int count2,
      float value12,
      float value13,
      int count3,
      float value14
   ) {
      float f1 = 0.0F;
      float f = 0.0F;
      Object object3 = null;
      Object object2 = null;
      Object object1 = null;
      Object object = null;
      onIntFloatArrayFloatFloatArrayFloatFloatFloatFloatFloatFloatArrayFloatArrayFloatArrayFloatFloatFloatIntIntFloatFloatFloatFloatFloatArrayMatrix4fFloatFloatFloat(
         count3,
         valueArray2,
         value14,
         (float[])object2,
         f1,
         value10,
         value2,
         value4,
         value5,
         (float[])object1,
         (float[])object3,
         valueArray,
         value13,
         f,
         value8,
         count,
         count2,
         value7,
         value11,
         value,
         value12,
         (float[])object,
         matrix4f,
         value6,
         value3,
         value9
      );
   }

   public static void onFloatArrayFloatFloatFloatIntFloatArrayFloatFloatFloatFloatFloatIntFloatMatrix4fFloatIntFloatFloatArrayFloatArray(
      float[] valueArray,
      float value,
      float value2,
      float value3,
      int count,
      float[] valueArray2,
      float value4,
      float value5,
      float value6,
      float value7,
      float value8,
      int count2,
      float value9,
      Matrix4f matrix4f,
      float value10,
      int count3,
      float value11,
      float[] valueArray3,
      float[] valueArray4
   ) {
      float f4 = 0.0F;
      Object object1 = null;
      Object object = null;
      float f3 = 0.0F;
      float f2 = 0.0F;
      float f1 = 0.0F;
      float f = 0.0F;
      onIntFloatArrayFloatFloatArrayFloatFloatFloatFloatFloatFloatArrayFloatArrayFloatArrayFloatFloatFloatIntIntFloatFloatFloatFloatFloatArrayMatrix4fFloatFloatFloat(
         count,
         (float[])object,
         f4,
         valueArray4,
         value5,
         f1,
         value10,
         f,
         f3,
         valueArray2,
         valueArray,
         (float[])object1,
         value7,
         value11,
         value6,
         count2,
         count3,
         value9,
         value,
         value3,
         value2,
         valueArray3,
         matrix4f,
         f2,
         value4,
         value8
      );
   }

   private static void onIntFloatArrayFloatFloatArrayFloatFloatFloatFloatFloatFloatArrayFloatArrayFloatArrayFloatFloatFloatIntIntFloatFloatFloatFloatFloatArrayMatrix4fFloatFloatFloat(
      int count,
      float[] valueArray,
      float value,
      float[] valueArray2,
      float value3,
      float value4,
      float value5,
      float value6,
      float value7,
      float[] valueArray3,
      float[] valueArray4,
      float[] valueArray5,
      float value8,
      float value9,
      float value10,
      int count2,
      int count3,
      float value11,
      float value12,
      float value13,
      float value14,
      float[] valueArray6,
      Matrix4f matrix4f,
      float value15,
      float value16,
      float value17
   ) {
      if (!(value12 <= 0.0F) && !(value8 <= 0.0F) && !(value14 <= 0.0F)) {
         float f = getFloatByIntFloat(count3, value14);
         float f1 = value13 > 0.0F ? getFloatByIntFloat(count, value14) : 0.0F;
         float f2 = !(value17 > 0.0F) && value10 == 0.0F && value11 == 0.0F ? 0.0F : getFloatByIntFloat(count2, value14);
         if (!(f <= 0.0F) || !(f1 <= 0.0F) || !(f2 <= 0.0F)) {
            ThemeModule thememodule = ThemeModule.getThemeModule();
            boolean flag = thememodule != null && thememodule.check4() && f > 0.0F;
            int i = 0;
            if (flag) {
               i = MipmapCapture.getInt();
               if (i == 0) {
                  flag = false;
               } else if (thememodule.getFloat() > 0.0F) {
                  MipmapCapture.update();
               }
            }

            boolean flag1 = value2 > 0;
            if (!flag1) {
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableCull();
            }

            try {
               if (flag) {
                  RenderSystem.setShaderTexture(0, i);
               }

               ShaderProgram shaderprogram = RenderSystem.setShader(flag ? shaderProgramKey2 : shaderProgramKey);
               if (shaderprogram != null) {
                  float f3 = flag ? 1.0F : Math.max(1.0F, Math.abs(value10) + Math.abs(value11) + value17 + 1.0F);
                  float f4 = value12 + f3 * 2.0F;
                  float f5 = value8 + f3 * 2.0F;
                  String s = "Size";
                  ShaderUniforms.onStringFloatFloatShaderProgram(s, value8, value12, shaderprogram);
                  String s1 = "QuadSize";
                  ShaderUniforms.onStringFloatFloatShaderProgram(s1, f5, f4, shaderprogram);
                  String s2 = "ContentOffset";
                  ShaderUniforms.onStringFloatFloatShaderProgram(s2, f3, f3, shaderprogram);
                  String s3 = "Radius";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(value7, value4, shaderprogram, s3, value15, value6);
                  float f51 = ShaderUniforms.getFloatByInt4(count3);
                  float f52 = ShaderUniforms.getFloatByInt(count3);
                  float f25 = ShaderUniforms.getFloatByInt3(count3);
                  float f24 = ShaderUniforms.getFloatByInt2(count3);
                  float f23 = f52;
                  float f22 = f51;
                  String s4 = "FillColor";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f25, f23, shaderprogram, s4, f24, f22);
                  f51 = ShaderUniforms.getFloatByInt4(count);
                  f52 = ShaderUniforms.getFloatByInt(count);
                  float f29 = ShaderUniforms.getFloatByInt3(count);
                  float f28 = ShaderUniforms.getFloatByInt2(count);
                  float f27 = f52;
                  float f26 = f51;
                  String s5 = "BorderColor";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f29, f27, shaderprogram, s5, f28, f26);
                  String s6 = "BorderWidth";
                  ShaderUniforms.onFloatShaderProgramString(value13, shaderprogram, s6);
                  String s7 = "GlobalAlpha";
                  ShaderUniforms.onFloatShaderProgramString(value14, shaderprogram, s7);
                  if (!flag) {
                     int j = 0;
                     float f6 = 0.0F;
                     float f7 = 0.0F;
                     float f8 = 0.0F;
                     float f9 = 0.0F;
                     float f10 = 0.0F;
                     float f11 = 0.0F;
                     float f12 = 0.0F;
                     float f13 = 0.0F;
                     float f14 = 0.0F;
                     float f15 = 0.0F;
                     float f16 = 0.0F;
                     float f17 = 0.0F;
                     float f18 = 0.0F;
                     float f19 = 0.0F;
                     float f20 = 0.0F;
                     float f21 = 0.0F;
                     if (valueArray != null && valueArray5 != null) {
                        j = Math.min(Math.min(valueArray.length, valueArray5.length), 8);
                        if (j > 0) {
                           f6 = valueArray[0];
                           f14 = valueArray5[0];
                        }

                        if (j > 1) {
                           f7 = valueArray[1];
                           f15 = valueArray5[1];
                        }

                        if (j > 2) {
                           f8 = valueArray[2];
                           f16 = valueArray5[2];
                        }

                        if (j > 3) {
                           f9 = valueArray[3];
                           f17 = valueArray5[3];
                        }

                        if (j > 4) {
                           f10 = valueArray[4];
                           f18 = valueArray5[4];
                        }

                        if (j > 5) {
                           f11 = valueArray[5];
                           f19 = valueArray5[5];
                        }

                        if (j > 6) {
                           f12 = valueArray[6];
                           f20 = valueArray5[6];
                        }

                        if (j > 7) {
                           f13 = valueArray[7];
                           f21 = valueArray5[7];
                        }
                     }

                     String s8 = "SegmentCount";
                     ShaderUniforms.onShaderProgramIntString(shaderprogram, j, s8);
                     String s9 = "SegCentersA";
                     ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f9, f7, shaderprogram, s9, f8, f6);
                     String s10 = "SegCentersB";
                     ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f13, f11, shaderprogram, s10, f12, f10);
                     String s11 = "SegHalfWidthsA";
                     ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f17, f15, shaderprogram, s11, f16, f14);
                     String s12 = "SegHalfWidthsB";
                     ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f21, f19, shaderprogram, s12, f20, f18);
                     String s13 = "SegmentRadius";
                     ShaderUniforms.onFloatShaderProgramString(value, shaderprogram, s13);
                  }

                  int k = 0;
                  float f35 = 0.0F;
                  float f36 = 0.0F;
                  float f37 = 0.0F;
                  float f38 = 0.0F;
                  float f39 = 0.0F;
                  float f40 = 0.0F;
                  float f41 = 0.0F;
                  float f42 = 0.0F;
                  float f43 = 0.0F;
                  float f44 = 0.0F;
                  float f45 = 0.0F;
                  float f46 = 0.0F;
                  float f47 = 0.0F;
                  float f48 = 0.0F;
                  float f49 = 0.0F;
                  float f50 = 0.0F;
                  if (valueArray6 != null && valueArray3 != null && valueArray2 != null) {
                     k = Math.min(valueArray6.length / 2, valueArray3.length / 2);
                     if (valueArray4 == null) {
                        k = Math.min(k, 1);
                     }

                     k = Math.min(k, 2);
                     if (k > 0) {
                        f35 = valueArray6[0];
                        f36 = valueArray6[1];
                        f39 = valueArray3[0];
                        f40 = valueArray3[1];
                        f43 = valueArray2[0];
                        f44 = valueArray2[1];
                        f45 = valueArray2[2];
                        f46 = valueArray2[3];
                     }

                     if (k > 1) {
                        f37 = valueArray6[2];
                        f38 = valueArray6[3];
                        f41 = valueArray3[2];
                        f42 = valueArray3[3];
                        f47 = valueArray4[0];
                        f48 = valueArray4[1];
                        f49 = valueArray4[2];
                        f50 = valueArray4[3];
                     }
                  }

                  String s14 = "BoxCount";
                  ShaderUniforms.onShaderProgramIntString(shaderprogram, k, s14);
                  String s15 = "BoxCenterA";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f38, f36, shaderprogram, s15, f37, f35);
                  String s16 = "BoxHalfSizeA";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f42, f40, shaderprogram, s16, f41, f39);
                  String s17 = "BoxRadii0";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f46, f44, shaderprogram, s17, f45, f43);
                  String s18 = "BoxRadii1";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f50, f48, shaderprogram, s18, f49, f47);
                  String s19 = "FilletRadius";
                  ShaderUniforms.onStringFloatFloatShaderProgram(s19, value3, value9, shaderprogram);
                  if (flag) {
                     onThemeModuleShaderProgram(thememodule, shaderprogram);
                  } else {
                     f51 = ShaderUniforms.getFloatByInt4(count2);
                     f52 = ShaderUniforms.getFloatByInt(count2);
                     float f33 = ShaderUniforms.getFloatByInt3(count2);
                     float f32 = ShaderUniforms.getFloatByInt2(count2);
                     float f31 = f52;
                     float f30 = f51;
                     String s20 = "ShadowColor";
                     ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f33, f31, shaderprogram, s20, f32, f30);
                     String s21 = "ShadowOffset";
                     ShaderUniforms.onStringFloatFloatShaderProgram(s21, value11, value10, shaderprogram);
                     String s22 = "ShadowBlur";
                     ShaderUniforms.onFloatShaderProgramString(value17, shaderprogram, s22);
                  }

                  float f34 = value5 - f3;
                  f35 = value16 - f3;
                  BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                  bufferbuilder.vertex(matrix4f, f34, f35, 0.0F).texture(0.0F, 0.0F);
                  bufferbuilder.vertex(matrix4f, f34, f35 + f5, 0.0F).texture(0.0F, 1.0F);
                  bufferbuilder.vertex(matrix4f, f34 + f4, f35 + f5, 0.0F).texture(1.0F, 1.0F);
                  bufferbuilder.vertex(matrix4f, f34 + f4, f35, 0.0F).texture(1.0F, 0.0F);
                  BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
                  return;
               }
            } finally {
               if (flag) {
                  RenderSystem.setShaderTexture(0, 0);
               }

               if (!flag1) {
                  RenderSystem.enableCull();
                  RenderSystem.disableBlend();
               }
            }
         }
      }
   }

   public static void onFloatFloatIntMatrix4fFloatFloatFloatFloat(
      float value, float value2, int count, Matrix4f matrix4f, float value3, float value4, float value5, float value6
   ) {
      float f3 = 0.0F;
      float f2 = 0.0F;
      float f1 = 0.0F;
      byte b1 = 0;
      float f = 0.0F;
      byte b0 = 0;
      onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value4, b1, count, value2, value6, f3, value, value3, f2, matrix4f, value5, b0, value, f, value, value, f1
      );
   }

   public static void onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
      float value,
      int count,
      int count2,
      float value2,
      float value3,
      float value4,
      float value5,
      float value6,
      float value7,
      Matrix4f matrix4f,
      float value8,
      int count3,
      float value9,
      float value10,
      float value11,
      float value12,
      float value13
   ) {
      float f2 = 0.0F;
      float f1 = 0.0F;
      Object object5 = null;
      Object object4 = null;
      Object object3 = null;
      Object object2 = null;
      float f = 0.0F;
      Object object1 = null;
      Object object = null;
      onIntFloatArrayFloatFloatArrayFloatFloatFloatFloatFloatFloatArrayFloatArrayFloatArrayFloatFloatFloatIntIntFloatFloatFloatFloatFloatArrayMatrix4fFloatFloatFloat(
         count3,
         (float[])object,
         f,
         (float[])object4,
         f2,
         value11,
         value2,
         value5,
         value12,
         (float[])object3,
         (float[])object5,
         (float[])object1,
         value6,
         f1,
         value13,
         count,
         count2,
         value7,
         value,
         value10,
         value8,
         (float[])object2,
         matrix4f,
         value9,
         value3,
         value4
      );
   }

   private static void onThemeModuleShaderProgram(ThemeModule themeModule, ShaderProgram shaderProgram2) {
      long i = MipmapCapture.getTime();
      if (shaderProgram2 != shaderProgram || i != time2) {
         shaderProgram = shaderProgram2;
         time2 = i;
         int j = Feature.mc.getFramebuffer().textureWidth;
         int k = Feature.mc.getFramebuffer().textureHeight;
         float f24 = j;
         float f1 = k;
         float f = f24;
         String s = "Resolution";
         ShaderUniforms.onStringFloatFloatShaderProgram(s, f1, f, shaderProgram2);
         int l = themeModule.getInt2();
         f24 = ShaderUniforms.getFloatByInt4(l);
         float f25 = ShaderUniforms.getFloatByInt(l);
         float f26 = ShaderUniforms.getFloatByInt2(l);
         float f5 = 1.0F;
         float f4 = f26;
         float f3 = f25;
         float f2 = f24;
         String s1 = "GlassTint";
         ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f5, f3, shaderProgram2, s1, f4, f2);
         float f6 = themeModule.getFloat7();
         String s2 = "GlassTintStrength";
         ShaderUniforms.onFloatShaderProgramString(f6, shaderProgram2, s2);
         float f7 = themeModule.getFloat4();
         String s3 = "GlassRefraction";
         ShaderUniforms.onFloatShaderProgramString(f7, shaderProgram2, s3);
         float f8 = themeModule.getFloat9();
         String s4 = "GlassChromatic";
         ShaderUniforms.onFloatShaderProgramString(f8, shaderProgram2, s4);
         float f9 = themeModule.getFloat();
         String s5 = "GlassBlur";
         ShaderUniforms.onFloatShaderProgramString(f9, shaderProgram2, s5);
         float f10 = themeModule.getFloat3();
         String s6 = "GlassIntensity";
         ShaderUniforms.onFloatShaderProgramString(f10, shaderProgram2, s6);
         float f11 = themeModule.getFloat13();
         String s7 = "GlassFrosted";
         ShaderUniforms.onFloatShaderProgramString(f11, shaderProgram2, s7);
         float f12 = themeModule.getFloat6();
         String s8 = "GlassPosterize";
         ShaderUniforms.onFloatShaderProgramString(f12, shaderProgram2, s8);
         float f13 = themeModule.getFloat2();
         String s9 = "GlassPixelate";
         ShaderUniforms.onFloatShaderProgramString(f13, shaderProgram2, s9);
         float f14 = themeModule.getFloat14();
         String s10 = "GlassWave";
         ShaderUniforms.onFloatShaderProgramString(f14, shaderProgram2, s10);
         float f15 = themeModule.getFloat12();
         String s11 = "GlassWaveSpeed";
         ShaderUniforms.onFloatShaderProgramString(f15, shaderProgram2, s11);
         float f16 = themeModule.getFloat8();
         String s12 = "GlassWaveFreq";
         ShaderUniforms.onFloatShaderProgramString(f16, shaderProgram2, s12);
         float f17 = themeModule.getFloat11();
         String s13 = "GlassInnerGlow";
         ShaderUniforms.onFloatShaderProgramString(f17, shaderProgram2, s13);
         float f18 = themeModule.getFloat5();
         String s14 = "GlassInnerGlowSize";
         ShaderUniforms.onFloatShaderProgramString(f18, shaderProgram2, s14);
         int i1 = themeModule.getInt();
         f24 = ShaderUniforms.getFloatByInt4(i1);
         f25 = ShaderUniforms.getFloatByInt(i1);
         float f22 = ShaderUniforms.getFloatByInt3(i1);
         float f21 = ShaderUniforms.getFloatByInt2(i1);
         float f20 = f25;
         float f19 = f24;
         String s15 = "GlassInnerGlowColor";
         ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f22, f20, shaderProgram2, s15, f21, f19);
         float f23 = getFloat();
         String s16 = "Time";
         ShaderUniforms.onFloatShaderProgramString(f23, shaderProgram2, s16);
      }
   }

   private static float getFloat() {
      long i = System.nanoTime();
      if (i - time > 1000000L) {
         time = i;
         value3 = (float)(i / 1000000L % 1000000L) / 1000.0F;
      }

      return value3;
   }

   public static void onFloatFloatIntIntMatrix4fFloatFloat(float value, float value2, int count, int count2, Matrix4f matrix4f, float value3, float value4) {
      onFloatIntIntFloatFloatMatrix4fIntIntFloat(value, count2, count, value3, value2, matrix4f, count, count2, value4);
   }

   public static void update2() {
      if (value2++ == 0) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();
      }
   }

   public static void onFloatIntFloatFloatFloatIntMatrix4fFloatIntIntFloat(
      float value, int count, float value3, float value4, float value5, int count2, Matrix4f matrix4f, float value6, int count3, int count4, float value7
   ) {
      if (!(value5 <= 0.0F) && !(value7 <= 0.0F) && !(value3 <= 0.0F)) {
         if (!(getFloatByIntFloat(count, value3) <= 0.0F)
            || !(getFloatByIntFloat(count4, value3) <= 0.0F)
            || !(getFloatByIntFloat(count3, value3) <= 0.0F)
            || !(getFloatByIntFloat(count2, value3) <= 0.0F)) {
            boolean flag = value2 > 0;
            if (!flag) {
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableCull();
            }

            try {
               ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey3);
               if (shaderprogram != null) {
                  String s = "Size";
                  ShaderUniforms.onStringFloatFloatShaderProgram(s, value7, value5, shaderprogram);
                  String s1 = "Radius";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(value4, value4, shaderprogram, s1, value4, value4);
                  float f16 = ShaderUniforms.getFloatByInt4(count);
                  float f17 = ShaderUniforms.getFloatByInt(count);
                  float f3 = ShaderUniforms.getFloatByInt3(count);
                  float f2 = ShaderUniforms.getFloatByInt2(count);
                  float f1 = f17;
                  float f = f16;
                  String s2 = "ColorTL";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f3, f1, shaderprogram, s2, f2, f);
                  f16 = ShaderUniforms.getFloatByInt4(count4);
                  f17 = ShaderUniforms.getFloatByInt(count4);
                  float f7 = ShaderUniforms.getFloatByInt3(count4);
                  float f6 = ShaderUniforms.getFloatByInt2(count4);
                  float f5 = f17;
                  float f4 = f16;
                  String s3 = "ColorTR";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f7, f5, shaderprogram, s3, f6, f4);
                  f16 = ShaderUniforms.getFloatByInt4(count3);
                  f17 = ShaderUniforms.getFloatByInt(count3);
                  float f11 = ShaderUniforms.getFloatByInt3(count3);
                  float f10 = ShaderUniforms.getFloatByInt2(count3);
                  float f9 = f17;
                  float f8 = f16;
                  String s4 = "ColorBR";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f11, f9, shaderprogram, s4, f10, f8);
                  f16 = ShaderUniforms.getFloatByInt4(count2);
                  f17 = ShaderUniforms.getFloatByInt(count2);
                  float f15 = ShaderUniforms.getFloatByInt3(count2);
                  float f14 = ShaderUniforms.getFloatByInt2(count2);
                  float f13 = f17;
                  float f12 = f16;
                  String s5 = "ColorBL";
                  ShaderUniforms.onFloatFloatShaderProgramStringFloatFloat(f15, f13, shaderprogram, s5, f14, f12);
                  String s6 = "GlobalAlpha";
                  ShaderUniforms.onFloatShaderProgramString(value3, shaderprogram, s6);
                  BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                  bufferbuilder.vertex(matrix4f, value6, value, 0.0F).texture(0.0F, 0.0F);
                  bufferbuilder.vertex(matrix4f, value6, value + value7, 0.0F).texture(0.0F, 1.0F);
                  bufferbuilder.vertex(matrix4f, value6 + value5, value + value7, 0.0F).texture(1.0F, 1.0F);
                  bufferbuilder.vertex(matrix4f, value6 + value5, value, 0.0F).texture(1.0F, 0.0F);
                  BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
                  return;
               }
            } finally {
               if (!flag) {
                  RenderSystem.enableCull();
                  RenderSystem.disableBlend();
               }
            }
         }
      }
   }

   public static void onFloatFloatFloatFloatFloatMatrix4fInt(float value, float value2, float value3, float value4, float value5, Matrix4f matrix4f, int count) {
      float f = 0.0F;
      onFloatFloatIntMatrix4fFloatFloatFloatFloat(f, value3, count, matrix4f, value2, value5, value, value4);
   }

   public static void onFloatFloatMatrix4fFloatFloatIntFloatFloat(
      float value, float value2, Matrix4f matrix4f, float value3, float value4, int count, float value5, float value6
   ) {
      float f5 = 0.0F;
      float f4 = 0.0F;
      float f3 = 0.0F;
      byte b1 = 0;
      float f2 = 0.0F;
      byte b0 = 0;
      float f1 = 0.0F;
      float f = 0.0F;
      onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value3, b1, count, value2, value5, f5, value4, value, f4, matrix4f, value6, b0, f1, f2, f, value4, f3
      );
   }

   public static void onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(
      float value, float value2, float value3, Matrix4f matrix4f, float value4, int count, float value5, float value6, int count2, float value7
   ) {
      float f2 = 0.0F;
      float f1 = 0.0F;
      float f = 0.0F;
      byte b0 = 0;
      onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value2, b0, count, value6, value4, f2, value5, value7, f1, matrix4f, value3, count2, value5, value, value5, value5, f
      );
   }
}
