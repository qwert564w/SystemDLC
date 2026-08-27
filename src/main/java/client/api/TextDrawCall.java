package client.api;

import org.joml.Matrix4f;

@FunctionalInterface
public interface TextDrawCall {
   public void draw(Matrix4f matrix4f, String text, float value, float value2, float value3, int count, float value4);
}
