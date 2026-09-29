package ru.pulse.cosmetic.render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class RenderStack {
   private MatrixStack stack;

   public void update(MatrixStack var1) {
      this.stack = var1;
   }

   public void push() {
      this.stack.push();
   }

   public void pop() {
      this.stack.pop();
   }

   public MatrixStack get() {
      return this.stack;
   }

   public void translate(float var1, float var2, float var3) {
      this.stack.translate(var1, var2, var3);
   }

   public void scale(float var1, float var2, float var3) {
      this.stack.scale(var1, var2, var3);
   }

   public void rotateX(float var1) {
      this.stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var1));
   }

   public void rotateY(float var1) {
      this.stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var1));
   }

   public void rotateZ(float var1) {
      this.stack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(var1));
   }

   public void rotate(float var1, float var2, float var3) {
      if (var3 != 0.0F) {
         this.rotateZ(var3);
      }

      if (var2 != 0.0F) {
         this.rotateY(var2);
      }

      if (var1 != 0.0F) {
         this.rotateX(var1);
      }
   }

   public void rotateDegrees(float var1, float var2, float var3) {
      this.rotate(var1, var2, var3);
   }

   public void rotateXDegrees(float var1) {
      this.rotateX(var1);
   }

   public void rotateYDegrees(float var1) {
      this.rotateY(var1);
   }

   public void rotateZDegrees(float var1) {
      this.rotateZ(var1);
   }
}
