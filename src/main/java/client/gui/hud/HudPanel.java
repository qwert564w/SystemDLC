package client.gui.hud;

import client.api.Theme;
import client.data.ScrollAnimator;
import client.gui.widget.KeybindEntry;
import client.gui.widget.KeybindRow;
import client.gui.widget.RenderElement;
import client.gui.widget.UiContext;
import client.module.CategoryType;
import client.render.MatrixUtil;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.util.Interpolation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public abstract class HudPanel extends RenderElement {
   private static final float value271 = 4.5F;
   protected static final float value272 = 20.0F;
   private static final float value273 = 4.0F;
   private static final float value274 = 6.0F;
   private final ScrollAnimator<KeybindRow> scrollAnimator = new ScrollAnimator<>(6.0F);
   private List<KeybindEntry> list2 = Collections.emptyList();
   private final ArrayList<Object> list3 = new ArrayList<>();
   private final Function<Object, KeybindRow> function = var1 -> new KeybindRow(this, this.getKeybindEntryByString((String)var1));
   private final ArrayList<KeybindEntry> list4 = new ArrayList<>();
   private final Set<String> set = new HashSet<>();
   private static final float value275 = 0.08F;
   private final Map<String, Float> map = new HashMap<>();
   private final Interpolation interpolation2 = new Interpolation();
   private long time = -1L;
   private float value276;

   protected float getFloat28() {
      return 0.0F;
   }

   public float getFloatByKeybindEntry(KeybindEntry keybindEntry) {
      return this.getFloat30();
   }

   protected List getList2() {
      return Collections.emptyList();
   }

   protected int getIntByKeybindEntry(KeybindEntry keybindEntry) {
      return Theme.mutedFg();
   }

   protected boolean check24() {
      return false;
   }

   protected float getFloatByKeybindEntry2(KeybindEntry keybindEntry) {
      return 0.0F;
   }

   protected float getFloat29() {
      return 20.0F;
   }

   @Override
   public float getFloat9() {
      this.update4();
      return this.scrollAnimator.check2() ? 16.0F + this.getFloat30() : 16.0F + this.scrollAnimator.getFloat();
   }

   @Override
   public void update2() {
      this.scrollAnimator.setFlag();
   }

   protected float getFloatByKeybindEntry3(KeybindEntry keybindEntry) {
      return 0.0F;
   }

   protected void onFloatMatrix4fFloatFloatKeybindEntryFloatFloat(float value, Matrix4f matrix4f, float value2, float value3, KeybindEntry keybindEntry, float value4, float value5) {
   }

   protected boolean check25() {
      return false;
   }

   private float getFloatByFloatFloatMatrix4fFloatKeybindEntryFloat(float value, float value2, Matrix4f matrix4f, float value3, KeybindEntry keybindEntry, float value4) {
      CategoryType categorytype = keybindEntry.getCategoryType();
      if (categorytype == null) {
         return value3;
      } else if (this.check25()) {
         float f11 = this.getFloat29();
         float f12 = value + (value4 - f11) / 2.0F;
         int l = Theme.elevated();
         float f7 = this.getFloatByFloat(value2);
         int i = l;
         float f6 = 6.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f6, value3, i, matrix4f, f11, f11, f7, f12);
         float f13 = f11 - 8.0F;
         float f3 = f13 / Math.max(categorytype.getWidth(), categorytype.getHeight());
         float f4 = categorytype.getWidth() * f3;
         float f5 = categorytype.getHeight() * f3;
         float f15 = value3 + (f11 - f4) / 2.0F;
         float f14 = f12 + (f11 - f5) / 2.0F;
         int j = Theme.mutedFg();
         float f9 = f14;
         float f8 = f15;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, j, matrix4f, f9, categorytype, f5, f8, f4);
         return value3 + f11 + 6.0F;
      } else {
         float f = 12.0F / Math.max(categorytype.getWidth(), categorytype.getHeight());
         float f1 = categorytype.getWidth() * f;
         float f2 = categorytype.getHeight() * f;
         float f16 = value + (value4 - f2) / 2.0F;
         int k = Theme.mutedFg();
         float f10 = f16;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, k, matrix4f, f10, categorytype, f2, value3, f1);
         return value3 + f1 + 4.0F;
      }
   }

   private void onMatrix4fFloatMatrixStackKeybindRowFloatFloatFloatFloat(
      Matrix4f matrix4f2, float value, MatrixStack matrixStack, KeybindRow keybindRow, float value2, float value3, float value4, float value5
   ) {
      float f = keybindRow.animation.getValue7();
      float f1 = 0.85F + 0.15F * value;
      KeybindEntry keybindentry = keybindRow.keybindEntry;
      float f2 = this.getFloatByKeybindEntry(keybindentry);
      float f3 = f + f2 / 2.0F;
      Matrix4f matrix4f = matrix4f2;
      boolean flag = false;
      if (matrixStack != null && f1 < 0.999F) {
         float f8 = 0.0F;
         float f7 = 1.0F;
         matrix4f = MatrixUtil.getMatrix4fByFloatFloatFloatFloatMatrixStackFloat(value4, f7, f3, f8, matrixStack, f1);
         flag = true;
      }

      float f9 = value2 - this.getFloat28() - 8.0F;
      this.onKeybindEntryFloatFloatFloatMatrix4fFloat(keybindentry, f, value, value3, matrix4f, f9);
      float f4 = this.getFloatByKeybindEntry2(keybindentry);
      float f6 = f + f4 + (f2 - f4 - 12.0F) / 2.0F;
      float f13 = f + f4;
      float f11 = f2 - f4;
      float f10 = f13;
      float f5 = this.getFloatByFloatFloatMatrix4fFloatKeybindEntryFloat(f10, value3, matrix4f, value2, keybindentry, f11);
      onFloatKeybindEntryMatrix4fFloatFloat(f5, keybindentry, matrix4f, f6, value3);
      float f14 = f2 - f4;
      int i = this.getIntByKeybindEntry(keybindentry);
      float f12 = f14;
      this.onFloatMatrix4fKeybindEntryFloatIntFloatFloat(value3, matrix4f, keybindentry, f6, i, f12, value5);
      if (flag) {
         matrixStack.pop();
      }
   }

   @Override
   protected void onFloatFloatFloatMatrix4f2(float value, float value2, float value3, Matrix4f matrix4f) {
      this.update4();
      float f = value2 + 8.0F + this.getFloat28();
      float f1 = value2 + 200.0F - 8.0F;
      float f2 = value3 + 8.0F;
      float f3 = value2 + 100.0F;
      this.scrollAnimator.setFloat(f2);
      MatrixStack matrixstack = this.drawContext != null ? this.drawContext.getMatrices() : null;

      for (KeybindRow keybindrow : (Iterable<KeybindRow>)(this.scrollAnimator.getCollection())) {
         float f4 = keybindrow.animation.getFloat();
         float f5 = value * f4;
         if (!(f5 <= 0.001F) && keybindrow.keybindEntry != null) {
            this.onMatrix4fFloatMatrixStackKeybindRowFloatFloatFloatFloat(matrix4f, f4, matrixstack, keybindrow, f, f5, f3, f1);
         }
      }
   }

   protected final void addList(List list) {
      int i = 0;

      for (int j = list.size(); i < j; i++) {
         this.list4.add((KeybindEntry)list.get(i));
      }

      list.clear();
   }

   private float getFloatByFloatKeybindEntry(float value, KeybindEntry keybindEntry) {
      if (!this.check24()) {
         return value;
      } else {
         long i = UiContext.getTime();
         if (this.time != i) {
            this.time = i;
            this.value276 = this.interpolation2.getFloat2();
         }

         String s = keybindEntry.getText();
         Float f = this.map.get(s);
         float f5;
         if (f == null) {
            f5 = value;
         } else {
            f5 = f;
            float f4 = 0.08F;
            float f3 = this.value276;
            float f2 = f5;
            f5 = Interpolation.getFloatByFloatFloatFloatFloat2(value, f2, f3, f4);
         }

         float f1 = f5;
         this.map.put(s, f1);
         return f1;
      }
   }

   private void onFloatMatrix4fKeybindEntryFloatIntFloatFloat(float value, Matrix4f matrix4f, KeybindEntry keybindEntry, float value2, int count, float value3, float value4) {
      float f3 = TextShader.getFloatByStringFloat(keybindEntry.getText2(), 12.0F);
      float f = this.getFloatByFloatKeybindEntry(f3, keybindEntry);
      float f1 = this.getFloatByKeybindEntry3(keybindEntry);
      float f2 = value4 - f1 - f;
      TextShader.update2();
      this.onFloatMatrix4fFloatFloatKeybindEntryFloatFloat(value2, matrix4f, f2, value3, keybindEntry, value, f);
      TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, keybindEntry.getText2(), f2, value2, 12.0F, count, value);
   }

   private static void onFloatKeybindEntryMatrix4fFloatFloat(float value, KeybindEntry keybindEntry, Matrix4f matrix4f, float value2, float value3) {
      if (keybindEntry.isFlag()) {
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat2(matrix4f, keybindEntry.getText(), value, value2, 12.0F, Theme.foreground(), value3);
      } else {
         String s1 = keybindEntry.getText();
         int i = Theme.foreground();
         float f = 12.0F;
         String s = s1;
         TextShader.onIntFloatFloatMatrix4fFloatFloatString(i, value3, value2, matrix4f, value, f, s);
      }
   }

   @SafeVarargs
   protected final void addMapArray(Map... map2) {
      if (map2.length != 0) {
         this.set.clear();

         for (KeybindRow keybindrow : (Iterable<KeybindRow>)(this.scrollAnimator.getCollection())) {
            if (keybindrow.keybindEntry != null) {
               this.set.add(keybindrow.keybindEntry.getText());
            }
         }

         for (Map mapx : map2) {
            mapx.keySet().retainAll(this.set);
         }

         this.map.keySet().retainAll(this.set);
      }
   }

   protected abstract List getList3();

   private KeybindEntry getKeybindEntryByString(String text) {
      for (KeybindEntry keybindentry : this.list2) {
         if (keybindentry.getText().equals(text)) {
            return keybindentry;
         }
      }

      return null;
   }

   private void update4() {
      if (this.scrollAnimator.isLong(UiContext.getTime())) {
         this.list2 = this.getList3();
         if (this.list2.isEmpty() && this.check4()) {
            this.list2 = this.getList2();
         }

         this.list3.clear();

         for (KeybindEntry keybindentry : this.list2) {
            this.list3.add(keybindentry.getText());
         }

         this.scrollAnimator.onListFunction(this.list3, this.function);

         for (KeybindRow keybindrow : (Iterable<KeybindRow>)(this.scrollAnimator.getCollection())) {
            KeybindEntry keybindentry1 = this.getKeybindEntryByString(keybindrow.keybindEntry.getText());
            if (keybindentry1 != null) {
               keybindrow.keybindEntry = keybindentry1;
            }
         }

         this.scrollAnimator.onList(this.list3);
         this.scrollAnimator.update();
      }
   }

   protected final KeybindEntry getKeybindEntryByBooleanStringCategoryTypeString(boolean flag, String text, CategoryType categoryType, String text2) {
      int i = this.list4.size();
      return i > 0
         ? this.list4.remove(i - 1).getKeybindEntryByBooleanCategoryTypeStringString(flag, categoryType, text2, text)
         : new KeybindEntry(text2, text, flag, categoryType);
   }

   protected float getFloat30() {
      return 12.0F;
   }

   protected final KeybindEntry getKeybindEntryByStringString(String text, String text2) {
      Object object = null;
      boolean flag = false;
      return this.getKeybindEntryByBooleanStringCategoryTypeString(flag, text, (CategoryType)object, text2);
   }

   protected void onMatrix4fKeybindEntryFloatFloatFloat(Matrix4f matrix4f, KeybindEntry keybindEntry, float value, float value2, float value3) {
   }

   protected final KeybindEntry getKeybindEntryByStringStringBoolean(String text, String text2, boolean flag) {
      Object object = null;
      return this.getKeybindEntryByBooleanStringCategoryTypeString(flag, text, (CategoryType)object, text2);
   }

   protected void onKeybindEntryFloatFloatFloatMatrix4fFloat(KeybindEntry keybindEntry, float value, float value2, float value3, Matrix4f matrix4f, float value4) {
      this.onMatrix4fKeybindEntryFloatFloatFloat(matrix4f, keybindEntry, value3, value4, value);
   }

   @Override
   protected boolean check14() {
      this.update4();
      return !this.list2.isEmpty();
   }

   @Override
   protected boolean check23() {
      return !this.getList3().isEmpty();
   }
}
