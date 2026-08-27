package client.gui.widget;

import client.api.Theme;
import client.api.UiMetrics;
import client.concurrent.ConfigManager;
import client.concurrent.SystemClient;
import client.data.HudConfig;
import client.data.IconMetrics;
import client.data.TextAlignSwitchMap;
import client.enums.TextAlign;
import client.module.CategoryType;
import client.module.client.HudModule;
import client.render.MatrixUtil;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.ActionSetting;
import client.setting.AlignmentSetting;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.Animation;
import client.util.Easings;
import client.util.Interpolation;
import client.util.TextHash;
import client.util.UnsafeAccess;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public abstract class RenderElement implements UiMetrics, Theme {
   public static final float value235 = 200.0F;
   public static final float value236 = 12.0F;
   public static final float value237 = 6.0F;
   protected static final float value238 = 32.0F;
   protected static final float value239 = 20.0F;
   protected static final float value240 = 14.0F;
   protected static final float value241 = 12.0F;
   protected static final float value242 = 6.0F;
   protected static final float value243 = 8.0F;
   protected static final float value244 = 6.0F;
   protected static final float value245 = 8.0F;
   protected static final float value246 = 8.0F;
   protected static final float value247 = 6.0F;
   private static final float value248 = 4.5F;
   private static final float value249 = 2.0F;
   private static final float value250 = 0.09F;
   private static final float value251 = 0.18F;
   private static final float value252 = 0.06F;
   private static final float value253 = 0.06F;
   private static final float value254 = 0.08F;
   private static final float value255 = 0.09F;
   private static final float value256 = 0.001F;
   private static final float value257 = 0.5F;
   private static final float value258 = 2.0F;
   private static final float value259 = 1.35F;
   protected static final UnsafeAccess<HudModule> unsafeAccess = new UnsafeAccess<>(HudModule.class);
   protected float value260;
   protected float value261;
   protected boolean flag = true;
   private float value262;
   private float value263;
   private final Animation animation = getAnimation();
   private final Animation animation2 = new Animation(2.0F).getAnimationByFunction(Easings::getFloatByFloat);
   private final Interpolation interpolation = new Interpolation();
   private boolean flag2;
   private float value264;
   private float value265 = -1.0F;
   private float value266 = -1.0F;
   private float value267 = -1.0F;
   private float value268 = -1.0F;
   private float value269 = -1.0F;
   private float value270 = -1.0F;
   protected DrawContext drawContext;
   private final List<Setting> list = new ArrayList<>();
   private final SliderSetting sizeModulya;
   private final SliderSetting neprozrachnostModulya;
   private BooleanSetting hidePustoyModul;
   private final BooleanSetting showHeder;
   private final BooleanSetting rasshiritNazvanie;
   private final AlignmentSetting polozhenieHedera;
   private final BooleanSetting showIkonku;
   private final BooleanSetting showBackgroundYIkonki;
   private final BooleanSetting staknut;
   private final ActionSetting sbros;
   private final float[] floatArray;
   private final float[] floatArray2;
   private final float[] floatArray3;
   private final float[] floatArray4;

   protected RenderElement() {
      SliderSetting slidersetting = new SliderSetting("", "", 100.0, 50.0, 200.0, 5.0, "%", 0);
      slidersetting.setName("Размер модуля");
      slidersetting.setDescription("Используйте ползунок для указателя размера модуля.");
      this.sizeModulya = slidersetting;
      slidersetting = new SliderSetting("", "", 100.0, 0.0, 100.0, 5.0, "%", 0);
      slidersetting.setName("Непрозрачность модуля");
      slidersetting.setDescription("Используйте ползунок для указателя непрозрачности модуля.");
      this.neprozrachnostModulya = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать хедер");
      booleansetting.setDescription("Отображать хедер (плашку c названием) над контентом.");
      this.showHeder = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Расширить название");
      booleansetting1.setDescription("Растянуть хедер на всю ширину компонента.");
      this.rasshiritNazvanie = booleansetting1;
      AlignmentSetting alignmentsetting = new AlignmentSetting("", "", TextAlign.CENTER);
      alignmentsetting.setName("Положение хедера");
      alignmentsetting.setDescription("Выберите положение хедера: лево, центр или право.");
      this.polozhenieHedera = alignmentsetting;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", true);
      booleansetting2.setName("Показывать иконку");
      booleansetting2.setDescription("Отображать иконку в хедере модуля.");
      this.showIkonku = booleansetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", true);
      booleansetting3.setName("Показывать фон y иконки");
      booleansetting3.setDescription("Отображать подложку под иконкой хедера.");
      this.showBackgroundYIkonki = booleansetting3;
      BooleanSetting booleansetting4 = new BooleanSetting("", "", false);
      booleansetting4.setName("Стакнуть");
      booleansetting4.setDescription("Слить хедер c контент-плашкой в единую форму c плавными переходами.");
      this.staknut = booleansetting4;
      ActionSetting actionsetting = new ActionSetting("", "");
      actionsetting.setName("Сброс");
      actionsetting.setDescription("Сбросить настройки этого модуля до значений по умолчанию.");
      this.sbros = actionsetting;
      this.floatArray = new float[4];
      this.floatArray2 = new float[4];
      this.floatArray3 = new float[4];
      this.floatArray4 = new float[4];
      this.showHeder.setVisibleWhen(this::check20);
      this.rasshiritNazvanie.setVisibleWhen(() -> this.check20() && this.showHeder.isFlag3() && !this.check10());
      this.polozhenieHedera.setVisibleWhen(() -> this.check20() && this.showHeder.isFlag3());
      this.showIkonku.setVisibleWhen(() -> this.check7() && this.showHeder.isFlag3());
      this.showBackgroundYIkonki.setVisibleWhen(() -> this.check7() && this.showHeder.isFlag3() && this.showIkonku.isFlag3());
      this.staknut.setVisibleWhen(() -> this.check20() && this.showHeder.isFlag3());
      this.showHeder.setOnChange(this::update);
      this.showIkonku.setOnChange(this::update);
      this.showBackgroundYIkonki.setOnChange(this::update);
      this.staknut.setOnChange(this::update);
      if (this.check11()) {
         BooleanSetting booleansetting5 = new BooleanSetting("", "", this.check16());
         booleansetting5.setName("Скрывать пустой модуль");
         booleansetting5.setDescription("Включите функцию для того чтобы модуль автоматически пропадал c экрана во время когда в нем отсутствуют данные.");
         this.hidePustoyModul = booleansetting5;
      }

      this.onSettingArray(
         this.sizeModulya,
         this.neprozrachnostModulya,
         this.hidePustoyModul,
         this.showHeder,
         this.rasshiritNazvanie,
         this.polozhenieHedera,
         this.showIkonku,
         this.showBackgroundYIkonki,
         this.staknut
      );
      this.sbros.setRunnable(this::update3);
      this.addSetting(this.sbros);
   }

   public SliderSetting getNeprozrachnostModulya() {
      return this.neprozrachnostModulya;
   }

   public BooleanSetting getRasshiritNazvanie() {
      return this.rasshiritNazvanie;
   }

   private void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = this.value261 + this.getFloat15() + this.getFloat2();
      float f1 = value3 * value;
      if (!(f1 <= 0.5F)) {
         float f2 = (float)Math.pow(value, 1.35F);
         float f3 = this.getFloat3();
         float f4 = this.value260 + 100.0F;
         float f5 = this.getFloat() <= 0.001F ? f4 : this.getFloat6() + this.getFloat4() * 0.5F;
         float f6 = f3 + (200.0F - f3) * f2;
         float f7 = f5 + (f4 - f5) * f2;
         float f8 = value2 * value;
         float f9 = f7 - f6 * 0.5F;
         int k = Theme.background();
         float f18 = this.getFloatByFloat(f8);
         float f17 = 1.0F;
         float f16 = 1.0F;
         float f15 = 0.0F;
         int j = 436207616;
         float f14 = 0.0F;
         byte b0 = 0;
         int i = k;
         float f13 = 12.0F;
         float f12 = 12.0F;
         float f11 = 12.0F;
         float f10 = 12.0F;
         ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
            f6, j, i, f9, f, f17, f10, f1, f16, matrix4f, f18, b0, f12, f14, f11, f13, f15
         );
         this.onMatrix4fFloatFloatFloatFloatFloat(matrix4f, f6, f7, value2, value, value3);
      }
   }

   protected boolean check() {
      return true;
   }

   protected float getFloat() {
      return this.value269 < 0.0F ? (this.check3() ? 1.0F : 0.0F) : Math.max(0.0F, Math.min(1.0F, this.value269));
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public BooleanSetting getShowIkonku() {
      return this.showIkonku;
   }

   private float getFloat2() {
      return 6.0F * this.getFloat() * (1.0F - this.getFloat19());
   }

   public boolean check2() {
      return true;
   }

   public BooleanSetting getShowBackgroundYIkonki() {
      return this.showBackgroundYIkonki;
   }

   private float getFloat3() {
      float f = this.getFloat();
      return f <= 0.001F ? 0.0F : this.getFloat4() * f;
   }

   public float getFloat4() {
      if (this.value265 < 0.0F) {
         this.value265 = this.getFloat20();
      }

      return this.value265;
   }

   protected float getFloat5() {
      float f = TextShader.getFloatByStringFloat(this.getString2(), 14.0F);
      if (this.check7()) {
         float f1 = this.value268 < 0.0F ? (this.check15() ? 1.0F : 0.0F) : Math.max(0.0F, this.value268);
         return 8.0F + 26.0F * f1 + f + 8.0F;
      } else {
         return 8.0F + f + 8.0F;
      }
   }

   public float getFloat6() {
      return this.value260 + (200.0F - this.getFloat4()) * this.getFloat25();
   }

   private void update() {
      ConfigManager configmanager = getConfigManager();
      if (configmanager != null) {
         configmanager.onString(this.getString3());
      }
   }

   public String getString() {
      return this.getString3();
   }

   public final boolean check3() {
      return this.check20() && this.check8();
   }

   public CategoryType getCategoryType() {
      return this.getCategoryType2();
   }

   protected final boolean check4() {
      UiContext uicontext = UiContext.getInstance();
      return uicontext != null && uicontext.check();
   }

   protected final float getFloat7() {
      return this.animation2.getFloat();
   }

   protected final boolean check5() {
      HudModule hudmodule = (HudModule)unsafeAccess.getModule2();
      return hudmodule != null && this.isHudModule(hudmodule);
   }

   public float getFloat8() {
      HudConfig hudconfig = HudConfig.getHudConfig();
      SliderSetting slidersetting = hudconfig.isRenderElement(this) ? hudconfig.getSizeModulya() : this.sizeModulya;
      return Math.max(0.1F, slidersetting.getValueAsFloat() / 100.0F);
   }

   private boolean check6() {
      HudConfig hudconfig = HudConfig.getHudConfig();
      return hudconfig.isRenderElement(this) ? hudconfig.getShowBackgroundYIkonki().isFlag3() : this.showBackgroundYIkonki.isFlag3();
   }

   public float getFloat9() {
      return 0.0F;
   }

   private boolean check7() {
      return this.check20() && this.getCategoryType2() != null;
   }

   protected String getString2() {
      return "";
   }

   public void update2() {
   }

   private boolean check8() {
      HudConfig hudconfig = HudConfig.getHudConfig();
      return hudconfig.isRenderElement(this) ? hudconfig.getShowHeder().isFlag3() : this.showHeder.isFlag3();
   }

   public final List<Setting> getList() {
      return Collections.unmodifiableList(this.list);
   }

   public abstract String getString3();

   private void update3() {
      for (Setting setting : this.list) {
         if (setting != this.sbros) {
            setting.reset();
         }
      }

      this.update();
   }

   public void onFloatFloat(float value, float value2) {
      this.value260 = value;
      this.value261 = value2;
   }

   private static Animation getAnimation() {
      return new Animation(4.5F).getAnimationByFunction(Easings::getFloatByFloat7);
   }

   public final Setting getSettingByString(String text) {
      for (Setting setting : this.list) {
         if (setting.getName().equals(text)) {
            return setting;
         }
      }

      return null;
   }

   protected abstract void onHudModuleBoolean(HudModule hudModule, boolean flag);

   public final void onMap(Map map) {
      if (map != null && !map.isEmpty()) {
         for (Setting setting : this.list) {
            JsonObject jsonobject = (JsonObject)map.get(setting.getNameHash());
            if (jsonobject == null) {
               String s = setting.getName();
               jsonobject = (JsonObject)TextHash.getObjectByStringMap(s, map);
               if (jsonobject != null) {
                  TextHash.setFlag();
               }
            }

            if (jsonobject != null) {
               try {
                  setting.fromJson(jsonobject);
               } catch (Exception exception) {
               }
            }
         }
      }
   }

   public final void onSettingArray(Setting... setting2) {
      for (Setting setting : setting2) {
         this.addSetting(setting);
      }
   }

   public final void addSetting(Setting setting2) {
      if (setting2 != null) {
         for (Setting setting : this.list) {
            if (setting.getName().equals(setting2.getName())) {
               return;
            }
         }

         int i = this.list.indexOf(this.sbros);
         if (setting2 != this.sbros && i >= 0) {
            this.list.add(i, setting2);
         } else {
            this.list.add(setting2);
         }

         if (setting2.getOnChange() == null) {
            setting2.setOnChange(this::update);
         }
      }
   }

   protected void onFloatFloatFloatMatrix4f2(float value, float value2, float value3, Matrix4f matrix4f) {
   }

   protected CategoryType getCategoryType2() {
      return null;
   }

   private static ConfigManager getConfigManager() {
      SystemClient systemclient = SystemClient.getInstance();
      return systemclient != null ? systemclient.getConfigManager() : null;
   }

   public final void onBoolean(boolean flag) {
      HudModule hudmodule = (HudModule)unsafeAccess.getModule2();
      if (hudmodule != null) {
         this.onHudModuleBoolean(hudmodule, flag);
      }
   }

   public final Map getMap() {
      LinkedHashMap linkedhashmap = new LinkedHashMap();

      for (Setting setting : this.list) {
         try {
            JsonObject jsonobject = setting.toJson();
            if (jsonobject != null) {
               linkedhashmap.put(setting.getNameHash(), jsonobject);
            }
         } catch (Exception exception) {
         }
      }

      return linkedhashmap;
   }

   protected abstract boolean isHudModule(HudModule hudModule);

   private boolean check9() {
      HudConfig hudconfig = HudConfig.getHudConfig();
      return hudconfig.isRenderElement(this) ? hudconfig.getShowIkonku().isFlag3() : this.showIkonku.isFlag3();
   }

   public float getFloat10() {
      float f = this.getFloat15();
      return !this.check14() ? f : f + this.getFloat2() + this.getFloat9();
   }

   protected final boolean check10() {
      if (!this.check3()) {
         return false;
      } else {
         HudConfig hudconfig = HudConfig.getHudConfig();
         return hudconfig.isRenderElement(this) ? hudconfig.getStaknut().isFlag3() : this.staknut.isFlag3();
      }
   }

   public float getFloat11() {
      return Math.max(this.getFloat4(), 200.0F);
   }

   protected boolean check11() {
      return true;
   }

   public final boolean check12() {
      return this.check15() && this.check6();
   }

   public final boolean check13() {
      return this.check5();
   }

   public final float getFloat12() {
      return this.getFloat23() * this.getFloat8();
   }

   protected boolean check14() {
      return true;
   }

   public final float getFloat13() {
      return this.getValue261();
   }

   public final boolean check15() {
      return this.check3() && this.check7() && this.check9();
   }

   protected boolean check16() {
      return false;
   }

   public float getFloat14() {
      return 200.0F;
   }

   public final boolean check17() {
      return this.rasshiritNazvanie.isFlag3();
   }

   private float getFloat15() {
      return 32.0F * this.getFloat();
   }

   public float getFloat16() {
      return this.animation.getFloat();
   }

   public final boolean check18() {
      return this.check19();
   }

   protected boolean check19() {
      if (!this.check5()) {
         return false;
      } else {
         return this.check4() ? true : !this.check21() || this.check23();
      }
   }

   public final float getFloat17() {
      return this.getValue260() + (this.getFloat22() - this.getValue260()) * this.getFloat8();
   }

   protected boolean check20() {
      return true;
   }

   public float getFloat18() {
      HudConfig hudconfig = HudConfig.getHudConfig();
      SliderSetting slidersetting = hudconfig.isRenderElement(this) ? hudconfig.getNeprozrachnostModulya() : this.neprozrachnostModulya;
      return Math.clamp(slidersetting.getValueAsFloat() / 100.0F, 0.0F, 1.0F);
   }

   protected float getFloat19() {
      return this.value270 < 0.0F ? (this.check10() ? 1.0F : 0.0F) : Math.max(0.0F, Math.min(1.0F, this.value270));
   }

   public final boolean check21() {
      return this.hidePustoyModul != null && this.hidePustoyModul.isFlag3();
   }

   public BooleanSetting getStaknut() {
      return this.staknut;
   }

   protected float getFloat20() {
      return this.check17() && !this.check10() ? 200.0F : this.getFloat5();
   }

   public boolean check22() {
      return true;
   }

   public BooleanSetting getHidePustoyModul() {
      return this.hidePustoyModul;
   }

   public AlignmentSetting getPolozhenieHedera() {
      return this.polozhenieHedera;
   }

   public RenderElement getRenderElement() {
      return this;
   }

   public boolean isFloatFloat(float value, float value2) {
      float f = this.getFloat17();
      float f1 = this.getFloat13();
      return value >= f && value <= f + this.getFloat21() && value2 >= f1 && value2 <= f1 + this.getFloat12();
   }

   public final void onFloatMatrix4fDrawContext(float value, Matrix4f matrix4f, DrawContext drawContext) {
      boolean flagx = this.flag && this.check19();
      this.setBoolean(flagx);
      if (!flagx && this.animation.check()) {
         float f6 = this.check3() ? 32.0F : 0.0F;
         if (this.value263 <= 0.0F) {
            this.value263 = f6;
         }

         if (this.value262 <= 0.0F) {
            this.value262 = 200.0F;
         }

         this.interpolation.setTime();
      } else {
         boolean flag1 = this.check14();
         if (!this.check()) {
            onAnimationBoolean(this.animation, flagx);
            onAnimationBoolean(this.animation2, flag1);
         } else {
            this.animation.setBoolean(flagx);
            this.animation2.setBoolean(flag1);
         }

         float f = this.interpolation.getFloat2();
         this.animation.setFloat2(f);
         this.animation2.setFloat2(f);
         this.setFloat(f);
         float f1 = this.animation.getFloat();
         float f2 = this.animation2.getFloat();
         if (this.animation2.isFlag()) {
            float f3 = this.getFloat9();
            float f4 = f3 < this.value264 ? 0.18F : 0.09F;
            float f5 = this.value264;
            this.value264 = Interpolation.getFloatByFloatFloatFloatFloat2(f3, f5, f, f4);
         }

         this.value262 = this.getFloat4();
         float f7 = this.getFloat15();
         float f8 = this.getFloat2();
         this.value263 = f2 > 0.001F && this.value264 > 0.5F ? f7 + f8 + this.value264 * f2 : f7;
         if (!(f1 <= 0.001F) || flagx) {
            this.onFloatFloatMatrix4fDrawContextFloat(f1, value, matrix4f, drawContext, f2);
         }
      }
   }

   public SliderSetting getSizeModulya() {
      return this.sizeModulya;
   }

   public float[] getFloatArray() {
      return null;
   }

   protected void onFloatDrawContextMatrix4fFloat(float value, DrawContext drawContext, Matrix4f matrix4f, float value2) {
      this.onMatrix4fFloatFloat(matrix4f, value2, value);
   }

   private void onFloatFloatMatrix4fFloat(float value, float value2, Matrix4f matrix4f, float value3) {
      if (this.check12()) {
         int j = Theme.elevated();
         float f3 = this.getFloatByFloat(value2);
         int i = j;
         float f2 = 6.0F;
         float f1 = 20.0F;
         float f = 20.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f2, value3, i, matrix4f, f1, f, f3, value);
      }

      CategoryType categorytype = this.getCategoryType2();
      this.onFloatFloatMatrix4fCategoryTypeFloat(value3, value, matrix4f, categorytype, value2);
   }

   private void onFloatFloatMatrix4fCategoryTypeFloat(float value, float value2, Matrix4f matrix4f, CategoryType categoryType, float value3) {
      if (categoryType != null) {
         float f = IconMetrics.getFloatByCategoryType2(categoryType);
         float f1 = IconMetrics.getFloatByCategoryType(categoryType);
         float f4 = value + (20.0F - f) / 2.0F;
         float f5 = value2 + (20.0F - f1) / 2.0F;
         int i = Theme.mutedFg();
         float f3 = f5;
         float f2 = f4;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, i, matrix4f, f3, categoryType, f1, f2, f);
      }
   }

   protected void onMatrix4fFloatFloat(Matrix4f matrix4f, float value, float value2) {
      float f = this.getFloat();
      float f1 = this.getFloat19();
      float f2 = this.value264;
      boolean flagx = f2 > 0.5F && value > 0.001F;
      if (f1 > 0.001F && f > 0.001F && flagx) {
         float f3 = getFloatByFloatFloat(value, f2);
         this.onFloatFloatFloatFloatMatrix4fFloatFloat(value2, f3, f2, f1, matrix4f, value, f);
         float f11 = this.getFloat26();
         float f5 = value2 * f;
         float f4 = f11;
         this.onFloatMatrix4fFloat(f4, matrix4f, f5);
         this.onFloatFloatFloatFloatMatrix4f(value, f3, value2, f2, matrix4f);
      } else {
         if (f > 0.001F) {
            float f12 = this.getFloat6();
            float f13 = this.getFloat4();
            float f8 = value2 * f;
            float f7 = f13;
            float f6 = f12;
            this.onFloatFloatFloatMatrix4f3(f8, f7, f6, matrix4f);
            f12 = this.getFloat26();
            float f10 = value2 * f;
            float f9 = f12;
            this.onFloatMatrix4fFloat(f9, matrix4f, f10);
            TextShader.update2();
         }

         if (flagx) {
            this.onFloatFloatFloatMatrix4f(value, value2, f2, matrix4f);
         }
      }
   }

   private static float getFloatByFloatFloat(float value, float value2) {
      float f = Math.clamp(28.0F / Math.max(value2, 1.0F), 0.3F, 0.6F);
      float f1 = Math.max(0.0F, (value - f) / (1.0F - f));
      return f1 * f1;
   }

   protected boolean check23() {
      return true;
   }

   protected final float getFloatByFloat(float value) {
      return value * this.getFloat18();
   }

   public final float getFloat21() {
      return this.getFloat24() * this.getFloat8();
   }

   private void setBoolean(boolean flag) {
      if (!this.flag2) {
         onAnimationBoolean(this.animation, flag);
         this.flag2 = true;
      }
   }

   private static void onAnimationBoolean(Animation animation, boolean flag) {
      if (flag) {
         animation.update2();
      } else {
         animation.update();
      }
   }

   private void setFloat(float value) {
      float f18 = this.value269;
      float f19 = this.check3() ? 1.0F : 0.0F;
      float f3 = 0.09F;
      float f2 = f19;
      float f1 = f18;
      this.value269 = Interpolation.getFloatByFloatFloatFloatFloat(f2, value, f1, f3);
      f18 = this.value270;
      f19 = this.check10() ? 1.0F : 0.0F;
      float f6 = 0.09F;
      float f5 = f19;
      float f4 = f18;
      this.value270 = Interpolation.getFloatByFloatFloatFloatFloat(f5, value, f4, f6);
      f18 = this.value268;
      f19 = this.check15() ? 1.0F : 0.0F;
      float f9 = 0.08F;
      float f8 = f19;
      float f7 = f18;
      this.value268 = Interpolation.getFloatByFloatFloatFloatFloat(f8, value, f7, f9);
      f18 = this.value265;
      f19 = this.getFloat20();
      float f12 = 0.06F;
      float f11 = f19;
      float f10 = f18;
      this.value265 = Interpolation.getFloatByFloatFloatFloatFloat(f11, value, f10, f12);
      f18 = this.value266;
      f19 = this.getFloat27();
      float f15 = 0.06F;
      float f14 = f19;
      float f13 = f18;
      this.value266 = Interpolation.getFloatByFloatFloatFloatFloat(f14, value, f13, f15);
      float f = this.polozhenieHedera.getTextAlign() == TextAlign.RIGHT ? 1.0F : 0.0F;
      float f17 = 0.06F;
      float f16 = this.value267;
      this.value267 = Interpolation.getFloatByFloatFloatFloatFloat(f, value, f16, f17);
   }

   private void onFloatFloatMatrix4fDrawContextFloat(float value, float value2, Matrix4f matrix4f2, DrawContext drawContext2, float value3) {
      float f = value2 * value;
      float f1 = 0.96F + 0.04F * value;
      float f2 = f1 * this.getFloat8();
      float f3 = (1.0F - value) * -8.0F;
      float f4 = this.value260;
      float f5 = this.value261;
      float f6 = f1 < 0.999F ? this.getFloat14() * 0.5F * this.getFloat8() * (1.0F - f1) : 0.0F;
      MatrixStack matrixstack = drawContext2.getMatrices();
      Matrix4f matrix4f = MatrixUtil.getMatrix4fByFloatFloatFloatFloatMatrixStackFloatFloat(f5, f6, f2, f3, matrixstack, f4, f2);
      this.drawContext = drawContext2;
      ScissorStack.onFloatFloatFloatFloatFloat(f3, f6, f5, f2, f4);

      try {
         this.onFloatDrawContextMatrix4fFloat(f, drawContext2, matrix4f, value3);
      } finally {
         ScissorStack.update2();
         this.drawContext = null;
         matrixstack.pop();
      }
   }

   private void onFloatFloatFloatFloatMatrix4fFloatFloat(float value, float value2, float value3, float value4, Matrix4f matrix4f, float value5, float value6) {
      float f = this.getFloat4();
      float f1 = this.getFloat6();
      float f2 = this.value261 + this.getFloat15() + this.getFloat2();
      float f3 = value3 * value5;
      float f4 = 200.0F + (f - 200.0F) * (1.0F - value2);
      float f5 = f1 + f * 0.5F + (this.value260 + 100.0F - (f1 + f * 0.5F)) * value2;
      float f6 = value * Math.max(value6, value5);
      if (value2 <= 0.0F) {
         float f40 = this.value261;
         float f41 = f2 - this.value261 + f3;
         int j1 = Theme.background();
         float f34 = this.getFloatByFloat(f6);
         float f33 = 1.0F;
         float f32 = 1.0F;
         float f31 = 0.0F;
         int j = 436207616;
         float f30 = 0.0F;
         byte b0 = 0;
         int i = j1;
         float f29 = 12.0F;
         float f28 = 12.0F;
         float f27 = 12.0F;
         float f26 = 12.0F;
         float f25 = f41;
         float f24 = f40;
         ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
            f, j, i, f1, f24, f33, f26, f25, f32, matrix4f, f34, b0, f28, f30, f27, f29, f31
         );
      } else {
         float f7 = Math.min(f1, f5 - f4 * 0.5F);
         float f8 = this.value261;
         float f9 = Math.max(f1 + f, f5 + f4 * 0.5F) - f7;
         float f10 = f2 + f3 - f8;
         float f11 = 2.0F * value4;
         float f12 = f11 * 0.5F;
         this.floatArray[0] = f1 + f * 0.5F - f7;
         this.floatArray[1] = this.value261 + 16.0F + f12 - f8;
         this.floatArray[2] = f5 - f7;
         this.floatArray[3] = f2 + f3 * 0.5F - f12 - f8;
         this.floatArray2[0] = f * 0.5F;
         this.floatArray2[1] = 16.0F + f12;
         this.floatArray2[2] = f4 * 0.5F;
         this.floatArray2[3] = f3 * 0.5F + f12;
         float f13 = Math.max(0.0F, f1 - (f5 - f4 * 0.5F));
         float f14 = Math.max(0.0F, f5 + f4 * 0.5F - (f1 + f));
         float f15 = Math.max(0.0F, f5 - f4 * 0.5F - f1);
         float f16 = Math.max(0.0F, f1 + f - (f5 + f4 * 0.5F));
         float f17 = Math.min(this.floatArray2[2], this.floatArray2[3]);
         float f18 = Math.min(12.0F, f13);
         float f19 = Math.min(12.0F, f14);
         this.floatArray4[0] = Math.min(12.0F + (f18 - 12.0F) * value4, f17);
         this.floatArray4[1] = Math.min(12.0F + (f19 - 12.0F) * value4, f17);
         this.floatArray4[2] = Math.min(12.0F, f17);
         this.floatArray4[3] = Math.min(12.0F, f17);
         float f20 = Math.min(12.0F, f16);
         float f21 = Math.min(12.0F, f15);
         this.floatArray3[0] = 12.0F;
         this.floatArray3[1] = 12.0F;
         this.floatArray3[2] = 12.0F + (f20 - 12.0F) * value4;
         this.floatArray3[3] = 12.0F + (f21 - 12.0F) * value4;
         float f22 = Math.min(18.0F, f13) * value4;
         float f23 = Math.min(18.0F, f14) * value4;
         int i1 = Theme.background();
         float f42 = this.getFloatByFloat(f6);
         float[] afloat3 = this.floatArray4;
         float[] afloat2 = this.floatArray3;
         float[] afloat1 = this.floatArray2;
         float[] afloat = this.floatArray;
         float f39 = f42;
         float f38 = 1.0F;
         float f37 = 1.0F;
         float f36 = 0.0F;
         int l = 436207616;
         float f35 = 0.0F;
         byte b1 = 0;
         int k = i1;
         ShapeShader.onFloatArrayFloatFloatFloatIntFloatArrayFloatFloatFloatFloatFloatIntFloatMatrix4fFloatIntFloatFloatArrayFloatArray(
            afloat3, f9, f39, f35, b1, afloat1, f8, f23, f36, f10, f38, l, f37, matrix4f, f7, k, f22, afloat, afloat2
         );
      }
   }

   private void onFloatMatrix4fFloat(float value, Matrix4f matrix4f, float value2) {
      float f = this.value261 + 6.0F;
      float f1 = this.value261 + 9.0F;
      String s = this.getString2();
      float f2 = Math.max(0.0F, this.value268);
      float f3;
      if (this.check7()) {
         float f4 = this.getFloat5();
         float f5 = value + 8.0F;
         float f6 = value + f4 - 8.0F - 20.0F;
         float f7 = value + 8.0F;
         float f8 = f5 + 20.0F + 6.0F;
         float f9 = value + 8.0F;
         float f10 = Math.max(0.0F, this.value267);
         float f11 = f5 + (f6 - f5) * f10;
         float f12 = f8 + (f9 - f8) * f10;
         f3 = f7 + (f12 - f7) * f2;
         if (f2 > 0.001F) {
            float f13 = value2 * f2;
            this.onFloatFloatMatrix4fFloat(f, f13, matrix4f, f11);
         }
      } else {
         f3 = value + 8.0F;
      }

      int i = Theme.foreground();
      float f14 = 14.0F;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f1, f3, i, f14, value2, s, matrix4f);
   }

   private void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f) {
      float f11 = this.value261;
      int k = Theme.background();
      float f10 = this.getFloatByFloat(value);
      float f9 = 1.0F;
      float f8 = 1.0F;
      float f7 = 0.0F;
      int j = 436207616;
      float f6 = 0.0F;
      byte b0 = 0;
      int i = k;
      float f5 = 12.0F;
      float f4 = 12.0F;
      float f3 = 12.0F;
      float f2 = 12.0F;
      float f1 = 32.0F;
      float f = f11;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value2, j, i, value3, f, f9, f2, f1, f8, matrix4f, f10, b0, f4, f6, f3, f5, f7
      );
   }

   private void onMatrix4fFloatFloatFloatFloatFloat(Matrix4f matrix4f2, float value, float value2, float value3, float value4, float value5) {
      float f = value5 * value4;
      float f1 = value4 * value4;
      if (!(f <= 0.5F) && !(f1 <= 0.001F)) {
         float f2 = this.value261 + this.getFloat15() + this.getFloat2();
         float f3 = 0.85F + 0.15F * value4;
         MatrixStack matrixstack = this.drawContext != null ? this.drawContext.getMatrices() : null;
         Matrix4f matrix4f = matrix4f2;
         boolean flagx = false;
         if (matrixstack != null && f3 < 0.999F) {
            float f9 = this.value260 + 100.0F;
            float f5 = 0.0F;
            float f4 = f9;
            matrix4f = MatrixUtil.getMatrix4fByFloatFloatFloatFloatMatrixStack(f3, f4, f5, f2, matrixstack);
            flagx = true;
         }

         float f6 = value2 - value * 0.5F;
         ScissorStack.onFloatFloatFloatFloat(value, f, f2, f6);

         try {
            float f8 = value3 * f1;
            float f7 = this.value260;
            this.onFloatFloatFloatMatrix4f2(f8, f7, f2, matrix4f);
         } finally {
            ScissorStack.update();
            if (flagx) {
               matrixstack.pop();
            }
         }
      }
   }

   private void onFloatFloatFloatFloatMatrix4f(float value, float value2, float value3, float value4, Matrix4f matrix4f) {
      float f = this.getFloat4();
      float f1 = this.getFloat6();
      float f2 = 200.0F + (f - 200.0F) * (1.0F - value2);
      float f3 = f1 + f * 0.5F + (this.value260 + 100.0F - (f1 + f * 0.5F)) * value2;
      this.onMatrix4fFloatFloatFloatFloatFloat(matrix4f, f2, f3, value3, value, value4);
   }

   public float getFloat22() {
      return this.value260 + (this.getFloat14() - this.getFloat24()) / 2.0F;
   }

   public float getFloat23() {
      return this.value263 <= 0.0F ? this.getFloat10() : this.value263;
   }

   public float getFloat24() {
      return this.value262 <= 0.0F ? this.getFloat11() : Math.max(this.value262, 200.0F);
   }

   public float getValue260() {
      return this.value260;
   }

   private float getFloat25() {
      return this.value266 < 0.0F ? this.getFloat27() : this.value266;
   }

   public float getValue261() {
      return this.value261;
   }

   protected float getFloat26() {
      return this.value260 + (200.0F - this.getFloat5()) * this.getFloat25();
   }

   private float getFloat27() {
      return switch (TextAlignSwitchMap.intArray[this.polozhenieHedera.getTextAlign().ordinal()]) {
         case 1 -> 0.0F;
         case 2 -> 1.0F;
         default -> 0.5F;
      };
   }
}
