package client.gui.widget;

import client.api.Theme;
import client.enums.TextAlign;
import client.module.CategoryType;
import client.render.SvgShader;
import client.setting.AlignmentSetting;
import org.joml.Matrix4f;

public class AlignmentRow extends SegmentedRow {
   private static final float value260 = 87.67F;
   private static final TextAlign[] textAlignArray = new TextAlign[]{TextAlign.LEFT, TextAlign.CENTER, TextAlign.RIGHT};
   private static final CategoryType[] categoryTypeArray = new CategoryType[]{CategoryType.ALIGN_LEFT, CategoryType.ALIGN_CENTER, CategoryType.ALIGN_RIGHT};
   private final AlignmentSetting alignmentSetting;

   public AlignmentRow(AlignmentSetting alignmentSetting2) {
      super(alignmentSetting2);
      this.alignmentSetting = alignmentSetting2;
      this.tween4.setFloat(getIntByTextAlign(alignmentSetting2.getTextAlign()));
   }

   @Override
   protected float getFloat8() {
      return getIntByTextAlign(this.alignmentSetting.getTextAlign());
   }

   @Override
   protected float getFloat3() {
      return 12.0F;
   }

   @Override
   protected float getFloat6() {
      return 8.0F;
   }

   @Override
   protected void onMatrix4fFloatFloat(Matrix4f matrix4f, float value, float value2) {
      TextAlign textalign = this.alignmentSetting.getTextAlign();
      float f = value + 11.5F;

      for (int i = 0; i < textAlignArray.length; i++) {
         float f1 = this.getFloatByInt2(i) - 7.0F;
         int j = textAlignArray[i] == textalign ? Theme.foreground() : Theme.mutedFg();
         CategoryType categorytype1 = categoryTypeArray[i];
         float f3 = 9.0F;
         float f2 = 14.0F;
         CategoryType categorytype = categorytype1;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, j, matrix4f, f, categorytype, f3, f1, f2);
      }
   }

   @Override
   protected void onInt(int count) {
      this.alignmentSetting.setTextAlign(textAlignArray[count]);
   }

   @Override
   protected float getFloat10() {
      return 87.67F;
   }

   private static int getIntByTextAlign(TextAlign textAlign) {
      for (int i = 0; i < textAlignArray.length; i++) {
         if (textAlignArray[i] == textAlign) {
            return i;
         }
      }

      return 1;
   }

   @Override
   protected int getInt() {
      return textAlignArray.length;
   }

   @Override
   protected CategoryType getCategoryType() {
      return CategoryType.SETTING_ALIGNMENT;
   }

   @Override
   protected String getString() {
      return this.alignmentSetting.getTextAlign().text;
   }
}
