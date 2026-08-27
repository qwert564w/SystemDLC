package client.gui.widget;

import client.api.Theme;
import client.concurrent.ModuleRegistry;
import client.concurrent.SystemClient;
import client.concurrent.Translations;
import client.data.CharMap;
import client.data.Tween;
import client.gui.screen.ClickGuiScreen;
import client.module.Category;
import client.module.CategoryType;
import client.module.Feature;
import client.module.Module;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.Setting;
import client.util.EasingPresets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class SearchBar extends ButtonWidget {
   private static final float value241 = 48.0F;
   private static final int value242 = 3;
   private static final int value243 = 10;
   private final TextInputState textInputState = new TextInputState(14.0F);
   private int value244 = -1;
   private final Tween tween5 = EasingPresets.getTweenByFloatFloat(0.0F, 0.28F);
   private float value245;
   private final List<SearchResultRow> list = new ArrayList<>();
   private final List<SearchResultRow> list2 = new ArrayList<>();
   private final List<SearchResultRow> list3 = new ArrayList<>();
   private final Deque<String> deque = new ArrayDeque<>();
   private Consumer<Category> consumer;
   private BiConsumer<Category, Module> biConsumer;

   public SearchBar() {
      this.value237 = 448.0F;
      this.value238 = 304.0F;
      this.textInputState.setConsumer(var1 -> this.update14());
      this.update15();
      this.update11();
   }

   @Override
   protected void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f2) {
      this.update13();
      float f = this.value236 + EasingPresets.getFloatByFloat(value2);
      Matrix4f matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, value2, this.value235 + this.value237 / 2.0F, f + this.value238 / 2.0F);
      float f6 = this.value235;
      float f7 = this.value237;
      float f8 = this.value238;
      int k = Theme.background();
      int l = Theme.border();
      float f5 = 1.0F;
      int j = l;
      int i = k;
      float f4 = 12.0F;
      float f3 = f8;
      float f2 = f7;
      float f1 = f6;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f5, f2, value2, matrix4f, f, i, f4, f1, j, f3);
      this.onFloatMatrix4fFloat(f, matrix4f, value2);
      this.onFloatFloatMatrix4fFloatFloat(value, f, matrix4f, value2, value3);
   }

   private void update11() {
      for (Category category : Category.values()) {
         if (!category.check()) {
            this.list2.add(this.getSearchResultRowByCategory(category));
         }
      }
   }

   private void update12() {
      List<SearchResultRow> listx = this.getList();
      if (!listx.isEmpty()) {
         int i = Math.max(this.value244, 0);
         if (i < listx.size()) {
            Runnable runnable = ((SearchResultRow)listx.get(i)).getRunnable();
            if (runnable != null) {
               runnable.run();
            }
         }
      }
   }

   private void setList(List<SearchResultRow> list) {
      if (this.value244 >= 0 && this.value244 < list.size()) {
         SearchResultRow searchresultrow = (SearchResultRow)list.get(this.value244);
         float f = this.value236 + 40.0F + 1.0F;
         float f1 = this.value236 + this.value238 - f;
         float f2 = searchresultrow.getValue236() - f + this.value245;
         float f3 = f2 + 32.0F;
         if (f2 < this.value245) {
            this.value245 = Math.max(0.0F, f2 - 4.0F);
         } else if (f3 > this.value245 + f1) {
            this.value245 = f3 - f1 + 4.0F;
         }

         this.tween5.setFloat2(this.value245);
      }
   }

   private List getList() {
      if (!this.textInputState.check2()) {
         return this.list3;
      } else {
         ArrayList arraylist = new ArrayList(this.list.size() + this.list2.size());
         arraylist.addAll(this.list);
         arraylist.addAll(this.list2);
         return arraylist;
      }
   }

   @Override
   protected boolean isIntIntInt3(int count, int count2, int count3) {
      if (count3 == 257 || count3 == 335) {
         this.update12();
         return true;
      } else if (count3 == 264) {
         this.setInt(1);
         return true;
      } else if (count3 == 265) {
         this.setInt(-1);
         return true;
      } else {
         this.textInputState.isIntInt(count, count3);
         return true;
      }
   }

   private void update13() {
      float f = ClickGuiScreen.getValue235();
      float f1 = Feature.mc.getWindow().getScaledWidth() / f;
      float f2 = Feature.mc.getWindow().getScaledHeight() / f;
      this.value235 = Math.round((f1 - this.value237) / 2.0F);
      this.value236 = Math.round((f2 - this.value238) / 2.0F);
   }

   private void onList(List<SearchResultRow> list) {
      for (int i = 0; i < list.size(); i++) {
         ((SearchResultRow)list.get(i)).setFlag5(i == this.value244);
      }
   }

   @Override
   public void update5() {
      this.textInputState.update3();
      this.list3.clear();
      this.value244 = -1;
      this.value245 = 0.0F;
      this.tween5.setFloat(0.0F);
      this.update16();
      super.update5();
   }

   private void update14() {
      this.value245 = 0.0F;
      this.tween5.setFloat2(0.0F);

      for (SearchResultRow searchresultrow : this.list3) {
         searchresultrow.setFlag5(false);
      }

      this.list3.clear();
      this.value244 = -1;

      for (SearchResultRow searchresultrow1 : this.list) {
         searchresultrow1.setFlag5(false);
      }

      for (SearchResultRow searchresultrow2 : this.list2) {
         searchresultrow2.setFlag5(false);
      }

      if (!this.textInputState.check2()) {
         ModuleRegistry moduleregistry = SystemClient.getInstance() != null ? SystemClient.getInstance().getModuleRegistry() : null;
         if (moduleregistry != null) {
            String s2 = CharMap.getStringByString(this.textInputState.getString2()).toLowerCase();

            for (Module module : moduleregistry.getList32()) {
               Category category = module.getCategory();
               if (category != null) {
                  if (this.isStringModule(s2, module)) {
                     List list1 = this.list3;
                     String s = this.getStringByInt(this.list3.size());
                     list1.add(this.getSearchResultRowByCategoryStringModule(category, s, module));
                  }

                  for (Setting setting : (Iterable<Setting>)(module.getVisibleSettings())) {
                     if (this.isStringSetting(s2, setting)) {
                        List<SearchResultRow> listx = this.list3;
                        String s1 = this.getStringByInt(this.list3.size());
                        listx.add(this.getSearchResultRowBySettingStringModuleCategory(setting, s1, module, category));
                     }
                  }
               }
            }
         }
      }
   }

   private void update15() {
      ModuleRegistry moduleregistry = SystemClient.getInstance() != null ? SystemClient.getInstance().getModuleRegistry() : null;
      if (moduleregistry != null) {
         List<Module> listx = moduleregistry.getList32();
         int i = 0;

         for (Module module : listx) {
            if (i >= 3) {
               break;
            }

            Category category = module.getCategory();
            if (category != null) {
               List list1 = this.list;
               String s = this.getStringByInt(i);
               list1.add(this.getSearchResultRowByCategoryStringModule(category, s, module));
               i++;
            }
         }
      }
   }

   private boolean isStringModule(String text, Module module2) {
      return this.isStringString2(module2.getName(), text);
   }

   private boolean isStringSetting(String text, Setting setting2) {
      return this.isStringString2(setting2.getDisplayName(), text) ? true : this.isStringString2(setting2.getName(), text);
   }

   private SearchResultRow getSearchResultRowBySettingStringModuleCategory(Setting setting2, String text, Module module2, Category category) {
      return new SearchResultRow(category.getCategoryType(), module2.getName(), setting2.getDisplayName(), true, text, () -> this.onCategoryModule3(category, module2));
   }

   private void onCategoryModule3(Category category, Module module2) {
      this.onModule(module2);
      if (this.biConsumer != null) {
         this.biConsumer.accept(category, module2);
      } else if (this.consumer != null) {
         this.consumer.accept(category);
      }

      this.update4();
   }

   private void onCategory2(Category category) {
      if (this.consumer != null) {
         this.consumer.accept(category);
      }

      this.update4();
   }

   private void onModule(Module module2) {
      if (module2 != null) {
         String s = module2.getName();
         if (s != null && !s.isBlank()) {
            this.deque.removeIf(var1x -> var1x.equalsIgnoreCase(s));
            this.deque.addFirst(s);

            while (this.deque.size() > 10) {
               this.deque.removeLast();
            }
         }
      }
   }

   public void addList(List<String> list) {
      this.deque.clear();
      if (list != null) {
         for (String s : list) {
            if (s != null && !s.isBlank()) {
               if (this.deque.size() >= 10) {
                  break;
               }

               this.deque.add(s);
            }
         }

         this.update16();
      }
   }

   public List<String> getList2() {
      return new ArrayList<>(this.deque);
   }

   public void setBiConsumer(BiConsumer<Category, Module> biConsumer2) {
      this.biConsumer = biConsumer2;
   }

   public void setConsumer(Consumer<Category> consumer2) {
      this.consumer = consumer2;
   }

   private boolean isStringString2(String text, String text2) {
      return text == null ? false : CharMap.getStringByString(text).toLowerCase().contains(text2);
   }

   private float getFloatByFloatStringFloatListFloatMatrix4fFloat(float value, String text, float value2, List list, float value3, Matrix4f matrix4f, float value4) {
      float f = value3 + 10.0F;
      TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, text, this.value235 + 12.0F, f, 12.0F, Theme.mutedFg(), value2);
      float f1 = f + 12.0F + 6.0F;
      float f2 = this.value235 + 4.0F;

      for (int i = 0; i < list.size(); i++) {
         SearchResultRow searchresultrow = (SearchResultRow)list.get(i);
         searchresultrow.setValue237(440.0F);
         searchresultrow.onFloatFloat2(f1, f2);
         searchresultrow.onFloatFloatFloatMatrix4f(value2, value, value4, matrix4f);
         f1 += 32.0F;
         if (i < list.size() - 1) {
            f1 += 4.0F;
         }
      }

      return f1;
   }

   private void setInt(int count) {
      List<SearchResultRow> listx = this.getList();
      if (listx.isEmpty()) {
         this.value244 = -1;
      } else {
         if (this.value244 < 0) {
            this.value244 = count > 0 ? 0 : listx.size() - 1;
         } else {
            this.value244 = Math.clamp((long)(this.value244 + count), 0, listx.size() - 1);
         }

         this.onList(listx);
         this.setList(listx);
      }
   }

   private float getFloatByInt2(int count) {
      return count <= 0 ? 28.0F : 28.0F + count * 32.0F + Math.max(0, count - 1) * 4.0F;
   }

   private float getFloat6() {
      return !this.textInputState.check2()
         ? this.getFloatByInt2(this.list3.size()) + 10.0F
         : this.getFloatByInt2(this.list.size()) + 10.0F + 1.0F + this.getFloatByInt2(this.list2.size()) + 10.0F;
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      this.textInputState.isChar(symbol);
      return true;
   }

   @Override
   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      if (count != 0) {
         return true;
      } else {
         float f = this.value236 + 40.0F + 1.0F;
         if (value2 >= f && value2 <= this.value236 + this.value238) {
            List<SearchResultRow> listx = !this.textInputState.check2() ? this.list3 : null;
            if (listx != null) {
               for (SearchResultRow searchresultrow : listx) {
                  if (searchresultrow.isIntDoubleDouble(count, value, value2)) {
                     return true;
                  }
               }
            } else {
               for (SearchResultRow searchresultrow1 : this.list) {
                  if (searchresultrow1.isIntDoubleDouble(count, value, value2)) {
                     return true;
                  }
               }

               for (SearchResultRow searchresultrow2 : this.list2) {
                  if (searchresultrow2.isIntDoubleDouble(count, value, value2)) {
                     return true;
                  }
               }
            }
         }

         return true;
      }
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      float f = this.value236 + 40.0F + 1.0F;
      if (!(value2 < this.value235) && !(value2 > this.value235 + this.value237) && !(value3 < f) && !(value3 > this.value236 + this.value238)) {
         float f1 = this.value236 + this.value238 - f;
         float f2 = Math.max(0.0F, this.getFloat6() - f1);
         this.value245 = Math.clamp(this.value245 - (float)(value * 48.0), 0.0F, f2);
         this.tween5.setFloat2(this.value245);
         return true;
      } else {
         return true;
      }
   }

   private SearchResultRow getSearchResultRowByCategoryStringModule(Category category, String text, Module module2) {
      return new SearchResultRow(category.getCategoryType(), category.getString(), module2.getName(), false, text, () -> this.onCategoryModule3(category, module2));
   }

   private SearchResultRow getSearchResultRowByCategory(Category category) {
      return new SearchResultRow(category.getCategoryType(), category.getString(), null, () -> this.onCategory2(category));
   }

   private String getStringByInt(int count) {
      return count < 9 ? "Ctrl " + (count + 1) : null;
   }

   public boolean isInt(int count) {
      List listx = this.textInputState.check2() ? this.list : this.list3;
      if (count >= 0 && count < listx.size()) {
         Runnable runnable = ((SearchResultRow)listx.get(count)).getRunnable();
         if (runnable == null) {
            return false;
         } else {
            runnable.run();
            return true;
         }
      } else {
         return false;
      }
   }

   private void update16() {
      this.list.clear();
      ModuleRegistry moduleregistry = SystemClient.getInstance() != null ? SystemClient.getInstance().getModuleRegistry() : null;
      if (moduleregistry == null) {
         this.update15();
      } else {
         int i = 0;

         for (String s : this.deque) {
            if (i >= 3) {
               break;
            }

            Module module = moduleregistry.getModuleByString(s);
            if (module != null) {
               Category category = module.getCategory();
               if (category != null) {
                  List listx = this.list;
                  String s1 = this.getStringByInt(i);
                  listx.add(this.getSearchResultRowByCategoryStringModule(category, s1, module));
                  i++;
               }
            }
         }

         if (this.list.isEmpty()) {
            this.update15();
         }
      }
   }

   private void onFloatMatrix4fFloat(float value, Matrix4f matrix4f, float value2) {
      float f = this.value235 + 14.0F;
      float f1 = value + 14.0F;
      CategoryType categorytype1 = CategoryType.SEARCH;
      int i = Theme.mutedFg();
      float f7 = 12.0F;
      float f6 = 12.0F;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, i, matrix4f, f1, categorytype, f7, f, f6);
      float f2 = f + 12.0F + 10.0F;
      float f3 = value + 13.0F;
      float f4 = this.value235 + this.value237 - 14.0F - f2;
      float f8 = 40.0F;
      ScissorStack.onFloatFloatFloatFloat(f4, f8, value, f2);
      if (this.textInputState.check2()) {
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(
            matrix4f, Translations.getInstance().getStringByString2("Введите команду или поиск..."), f2, f3, 14.0F, Theme.mutedFg(), value2
         );
         int j = Theme.foreground();
         float f10 = 14.0F;
         float f9 = 1.0F;
         ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value2, f10, f2, f3, f9, matrix4f, j);
      } else {
         TextInputState textinputstate = this.textInputState;
         int i1 = Theme.foreground();
         boolean flag = true;
         int k = i1;
         textinputstate.onFloatIntMatrix4fFloatBooleanFloatFloat(f3, k, matrix4f, value2, flag, f2, f4);
      }

      ScissorStack.update();
      float f5 = value + 40.0F;
      float f14 = this.value235;
      float f15 = this.value237;
      int l = Theme.border();
      float f13 = 1.0F;
      float f12 = f15;
      float f11 = f14;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value2, f13, f11, f5, f12, matrix4f, l);
   }

   private void onFloatFloatMatrix4fFloatFloat(float value, float value2, Matrix4f matrix4f, float value3, float value4) {
      float f = value2 + 40.0F + 1.0F;
      float f1 = value2 + this.value238;
      float f2 = f1 - f;
      float f3 = this.getFloat6();
      float f4 = Math.max(0.0F, f3 - f2);
      if (this.value245 > f4) {
         this.value245 = f4;
      }

      if (this.value245 < 0.0F) {
         this.value245 = 0.0F;
      }

      this.tween5.setFloat2(this.value245);
      float f5 = this.tween5.getFloat();
      float f6 = value2 + this.value238 - 2.5F;
      float f12 = f6 - f;
      float f11 = this.value237;
      float f10 = this.value235;
      ScissorStack.onFloatFloatFloatFloat(f11, f12, f, f10);
      float f7 = f - f5;
      boolean flag = !this.textInputState.check2();
      if (flag) {
         String s4 = Translations.getInstance().getStringByString2("Результаты");
         List listx = this.list3;
         String s = s4;
         this.getFloatByFloatStringFloatListFloatMatrix4fFloat(value, s, value3, listx, f7, matrix4f, value4);
      } else {
         String s3 = Translations.getInstance().getStringByString2("Предложения");
         List list1 = this.list;
         String s1 = s3;
         float f8 = this.getFloatByFloatStringFloatListFloatMatrix4fFloat(value, s1, value3, list1, f7, matrix4f, value4);
         float f9 = f8 + 10.0F;
         float f17 = this.value235;
         float f18 = this.value237;
         int i = Theme.border();
         float f15 = 1.0F;
         float f14 = f18;
         float f13 = f17;
         ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value3, f15, f13, f9, f14, matrix4f, i);
         s3 = Translations.getInstance().getStringByString2("Категории");
         float f16 = f9 + 1.0F;
         List list2x = this.list2;
         String s2 = s3;
         this.getFloatByFloatStringFloatListFloatMatrix4fFloat(value, s2, value3, list2x, f16, matrix4f, value4);
      }

      ScissorStack.update();
   }
}
