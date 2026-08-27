package client.module.movement;

import client.module.Category;
import client.module.CategoryModule;
import client.render.DepthState;
import client.render.WorldRenderContext;
import client.setting.ColorSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.util.StringParts;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class FakeLag extends CategoryModule {
   private ListSetting modeOtobrazheniya;
   private ColorSetting colorBoksa;
   private ColorSetting colorObvodki;

   public FakeLag() {
      super("FakeLag", Category.MOVEMENT);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(StringParts.join(new String[]{"М", "о", "д", "е", "л", "ь"}), StringParts.join(new String[]{"Б", "о", "к", "с"})),
         List.of(StringParts.join(new String[]{"М", "о", "д", "е", "л", "ь"})),
         false
      );
      listsetting.setName("Режим отображения");
      listsetting.setDescription("Модель или бокс");
      this.modeOtobrazheniya = listsetting;
      ColorSetting colorsetting = new ColorSetting("", "", 675592960, true);
      colorsetting.setName("Цвет бокса");
      colorsetting.setDescription("Цвет заливки бокса");
      this.colorBoksa = colorsetting;
      ColorSetting colorsetting1 = new ColorSetting("", "", -16777216, true);
      colorsetting1.setName("Цвет обводки");
      colorsetting1.setDescription("Цвет обводки бокса");
      this.colorObvodki = colorsetting1;
      this.colorBoksa.setVisibleWhen(this::getBoolean);
      this.colorObvodki.setVisibleWhen(this::getBoolean2);
      this.addSettings(new Setting[]{this.delay, this.pokazatServernuyuPoziciyu, this.modeOtobrazheniya, this.colorBoksa, this.colorObvodki});
   }

   private Boolean getBoolean() {
      return this.modeOtobrazheniya.isString("Бокс");
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!this.notInGame()
         && this.pokazatServernuyuPoziciyu.isFlag3()
         && this.fakePlayerCopy.check()
         && !this.client().options.getPerspective().isFirstPerson()) {
         if (this.modeOtobrazheniya.isString("Модель")) {
            this.fakePlayerCopy.render(worldRenderContext);
         } else {
            this.render8(worldRenderContext);
         }
      }
   }

   private void render8(WorldRenderContext worldRenderContext) {
      Vec3d vec3d = this.fakePlayerCopy.getVec3d();
      OtherClientPlayerEntity otherclientplayerentity = this.fakePlayerCopy.getOtherClientPlayerEntity();
      if (vec3d != null && otherclientplayerentity != null) {
         MatrixStack matrixstack = worldRenderContext.getMatrixStack();
         Vec3d vec3d1 = worldRenderContext.getCamera().getPos();
         float f = otherclientplayerentity.getWidth();
         float f1 = otherclientplayerentity.getHeight();
         float f2 = f / 2.0F;
         float f3 = (float)(vec3d.x - vec3d1.x);
         float f4 = (float)(vec3d.y - vec3d1.y);
         float f5 = (float)(vec3d.z - vec3d1.z);
         DepthState.update2();
         matrixstack.push();
         matrixstack.translate(f3, f4, f5);
         Matrix4f matrix4f = matrixstack.peek().getPositionMatrix();
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
         int i = this.colorBoksa.getInt3();
         float f21 = -f2;
         float f22 = -f2;
         float f23 = (i >> 16 & 0xFF) / 255.0F;
         float f24 = (i >> 8 & 0xFF) / 255.0F;
         float f25 = (i & 0xFF) / 255.0F;
         float f12 = (i >> 24 & 0xFF) / 255.0F;
         float f11 = f25;
         float f10 = f24;
         float f9 = f23;
         float f8 = f22;
         float f7 = 0.0F;
         float f6 = f21;
         DepthState.onFloatFloatFloatFloatFloatMatrix4fFloatBufferBuilderFloatFloatFloatFloat(
            f10, f8, f7, f2, f11, matrix4f, f12, bufferbuilder, f6, f2, f9, f1
         );
         int j = this.colorObvodki.getInt3();
         f21 = -f2;
         f22 = -f2;
         f24 = (j >> 16 & 0xFF) / 255.0F;
         f25 = (j >> 8 & 0xFF) / 255.0F;
         float f26 = (j & 0xFF) / 255.0F;
         float f20 = (j >> 24 & 0xFF) / 255.0F;
         float f19 = f26;
         float f18 = f25;
         float f17 = f24;
         float f16 = 0.005F;
         float f15 = f22;
         float f14 = 0.0F;
         float f13 = f21;
         DepthState.onFloatFloatFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloatFloatFloat(
            f2, f20, f18, f14, matrix4f, f13, f15, f1, bufferbuilder, f19, f16, f2, f17
         );
         BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
         matrixstack.pop();
         DepthState.update();
      }
   }

   private Boolean getBoolean2() {
      return this.modeOtobrazheniya.isString("Бокс");
   }

   @Override
   protected boolean check3() {
      return true;
   }
}
