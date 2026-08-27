package client.api;

import net.minecraft.client.util.math.MatrixStack;

@FunctionalInterface
public interface MatrixDrawCall {
   public void apply(MatrixStack matrixStack, int count, int count2, int count3, float value);
}
