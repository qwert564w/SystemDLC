package client.render;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import org.joml.Matrix4f;

public class ShaderUniforms {
   public static float getFloatByInt(int count) {
      return (count >> 8 & 0xFF) / 255.0F;
   }

   public static float getFloatByInt2(int count) {
      return (count & 0xFF) / 255.0F;
   }

   public static float getFloatByInt3(int count) {
      return (count >>> 24 & 0xFF) / 255.0F;
   }

   public static float getFloatByInt4(int count) {
      return (count >> 16 & 0xFF) / 255.0F;
   }

   public static void onStringFloatFloatShaderProgram(String text, float value, float value2, ShaderProgram shaderProgram) {
      GlUniform gluniform = shaderProgram.getUniform(text);
      if (gluniform != null) {
         gluniform.set(value2, value);
      }
   }

   public static void onFloatShaderProgramString(float value, ShaderProgram shaderProgram, String text) {
      GlUniform gluniform = shaderProgram.getUniform(text);
      if (gluniform != null) {
         gluniform.set(value);
      }
   }

   public static void onShaderProgramIntString(ShaderProgram shaderProgram, int count, String text) {
      GlUniform gluniform = shaderProgram.getUniform(text);
      if (gluniform != null) {
         gluniform.set(count);
      }
   }

   public static void onStringMatrix4fShaderProgram(String text, Matrix4f matrix4f, ShaderProgram shaderProgram) {
      GlUniform gluniform = shaderProgram.getUniform(text);
      if (gluniform != null) {
         gluniform.set(matrix4f);
      }
   }

   public static void onShaderProgramFloatStringFloatFloat(ShaderProgram shaderProgram, float value, String text, float value2, float value3) {
      GlUniform gluniform = shaderProgram.getUniform(text);
      if (gluniform != null) {
         gluniform.set(value3, value, value2);
      }
   }

   public static void onFloatFloatShaderProgramStringFloatFloat(float value, float value2, ShaderProgram shaderProgram, String text, float value3, float value4) {
      GlUniform gluniform = shaderProgram.getUniform(text);
      if (gluniform != null) {
         gluniform.set(value4, value2, value3, value);
      }
   }
}
