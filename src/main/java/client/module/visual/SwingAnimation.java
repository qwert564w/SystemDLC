package client.module.visual;

import client.enums.SwingMode;
import client.module.Category;
import client.module.Module;
import client.module.combat.TriggerBot;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.UnsafeAccess;
import java.util.Arrays;
import java.util.Collections;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class SwingAnimation extends Module {
   private static final UnsafeAccess<TriggerBot> unsafeAccess = new UnsafeAccess<>(TriggerBot.class);
   public ListSetting mode;
   public SliderSetting strengthVzmaha;
   public BooleanSetting onlyVGlavnoyRuke;
   public SliderSetting smooth;
   public BooleanSetting onlyCTriggerbot;
   private final float[] floatArray;
   private final long[] timeArray = new long[2];

   public SwingAnimation() {
      super("SwingAnimation", Category.VISUAL);
      ListSetting listsetting = new ListSetting(
         "", "", Arrays.stream(SwingMode.values()).map(SwingMode::getText).toList(), Collections.singletonList(SwingMode.SMOOTH.getText()), false
      );
      listsetting.setName("Режим");
      listsetting.setDescription("Mode selection");
      this.mode = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 1.0, 0.0, 15.0, 1.0, "", 1);
      slidersetting.setName("Сила взмаха");
      slidersetting.setDescription("Сила анимации удара руки");
      this.strengthVzmaha = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Только в главной руке");
      booleansetting.setDescription("Применять анимацию только к главной руке");
      this.onlyVGlavnoyRuke = booleansetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 2.0, 1.0, 3.0, 0.1F);
      slidersetting1.setName("Плавность");
      slidersetting1.setDescription("Скорость плавности анимации");
      this.smooth = slidersetting1;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Только c ТриггерБот");
      booleansetting1.setDescription("Работает только когда ТриггерБот включён");
      this.onlyCTriggerbot = booleansetting1;
      this.floatArray = new float[2];
      this.addSettings(new Setting[]{this.mode, this.strengthVzmaha, this.onlyVGlavnoyRuke, this.smooth, this.onlyCTriggerbot});
   }

   @Override
   public void onDisable() {
      Arrays.fill(this.floatArray, 0.0F);
      Arrays.fill(this.timeArray, 0L);
   }

   public boolean check3() {
      if (!this.isEnabled()) {
         return false;
      } else {
         return this.onlyCTriggerbot.isFlag3() ? unsafeAccess.getModule2() != null : true;
      }
   }

   public float getFloatByFloat(float value) {
      return value;
   }

   private void onFloatArmFloatMatrixStack(float value, Arm arm, float value2, MatrixStack matrixStack) {
      int i = arm == Arm.RIGHT ? 1 : -1;
      float f = MathHelper.sin(value * value * (float) Math.PI);
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * (45.0F + f * -20.0F * value2)));
      float f1 = MathHelper.sin(MathHelper.sqrt(value) * (float) Math.PI);
      matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * f1 * -20.0F * value2));
      matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(f1 * -80.0F * value2));
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * -45.0F));
   }

   private SwingMode getSwingMode() {
      return SwingMode.getSwingModeByString(this.mode.getString2());
   }

   private float getDelta(int count) {
      long i = System.nanoTime();
      long j = this.timeArray[count];
      this.timeArray[count] = i;
      if (j == 0L) {
         return 0.0F;
      } else {
         float f = (float)(i - j) / 1.0E9F;
         return f <= 0.0F ? 0.0F : Math.min(f, 0.25F);
      }
   }

   public void onFloatArmMatrixStack(float value, Arm arm, MatrixStack matrixStack) {
      int i = arm == Arm.RIGHT ? 1 : -1;
      int j = arm == Arm.RIGHT ? 0 : 1;
      float f = this.floatArray[j];
      float f8 = this.getDelta(j);
      if (value >= f) {
         f = value;
      } else if (f > 0.0F) {
         f += f8 / Math.max(0.05F, 0.3F * (this.smooth.getValueAsFloat() / 2.0F));
         if (f >= 1.0F) {
            f = 0.0F;
         }
      }

      this.floatArray[j] = f;
      value = f;
      float f2 = this.strengthVzmaha.getValueAsFloat() / 20.0F;
      SwingMode swingmode = this.getSwingMode();
      switch (swingmode) {
         case SMOOTH:
            this.onFloatArmFloatMatrixStack(value, arm, f2, matrixStack);
            break;
         case PUNCH:
            float f7 = (float)Math.sin(value * (Math.PI / 2) * 2.0);
            matrixStack.translate(0.0F, 0.02F, -(f7 * 0.002 * f2));
            matrixStack.scale(1.0F, 1.0F, f7 * f2 * 0.5F + 1.0F);
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F * f7 * f2));
            break;
         case SLIDE:
            float f6 = (float)Math.sin(value * (Math.PI / 2) * 2.0);
            matrixStack.translate(i * 0.19F, 0.17F, -0.28F);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * 90));
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * -60));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F - f2 * 50.0F * f6));
            break;
         case SWAG:
            float f5 = MathHelper.sin(value * (float) Math.PI);
            matrixStack.translate(i * 0.0F, 0.2F, 0.0F);
            matrixStack.translate(0.0F, 0.0F, -1.5F * f5 / 5.0F * f2);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(80.0F));
            matrixStack.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(45.0F));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-10.0F));
            matrixStack.translate(0.0F, 0.0F, -0.4F * f5 * f2);
            matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(f5 * -100.0F * f2));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(f5 * -180.0F * f2));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-70.0F));
            break;
         case SELF_BACK:
            float f3 = (float)Math.sin(value * (Math.PI / 2) * 2.0);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90 * i));
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-70 * i));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees((-100.0F - 60.0F * f3) * f2));
      }
   }

   @Override
   public void onEnable() {
      Arrays.fill(this.floatArray, 0.0F);
      Arrays.fill(this.timeArray, 0L);
   }
}
