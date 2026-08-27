package client.api;

import client.module.CategoryType;
import client.render.SvgShader;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix4f;

public record CategoryIcon(CategoryType icon) implements Icon {
   public CategoryType getIcon() {
      return this.icon;
   }

   @Override
   public void draw(float value, float value2, float value3, Matrix4f matrix4f, float value4, DrawContext drawContext) {
      if (this.icon != null) {
         float f = Math.min(value4 / this.icon.getWidth(), value4 / this.icon.getHeight());
         float f1 = this.icon.getWidth() * f;
         float f2 = this.icon.getHeight() * f;
         float f3 = value2 + (value4 - f1) / 2.0F;
         float f4 = value + (value4 - f2) / 2.0F;
         CategoryType categorytype1 = this.icon;
         int i = Theme.foreground();
         CategoryType categorytype = categorytype1;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, i, matrix4f, f4, categorytype, f2, f3, f1);
      }
   }
}
