package client.module.visual;

import client.concurrent.ResourceManagerHooks;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gl.ShaderLoader;
import net.minecraft.client.gl.PostEffectPipeline.Uniform;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.util.Identifier;

public class Bloom extends Module {
   private static final Identifier identifier = Identifier.ofVanilla("bloom");
   private static PostEffectProcessor postEffectProcessor = null;
   private static Object value235 = null;
   private static Object value236 = null;
   private static int value237 = -1;
   private static ObjectAllocator objectAllocator = null;
   private static boolean flag = false;
   private static boolean flag2 = true;
   private static float value238 = 0.6F;
   private static float value239 = 1.0F;
   private static float value240 = 1.5F;
   private static List<Float> list;
   private static List<Float> list2;
   private static List<Float> list3;
   private static float value241 = Float.NaN;
   private static float value242 = Float.NaN;
   private static float value243 = Float.NaN;
   private SliderSetting porog;
   private SliderSetting intensivnost;
   private SliderSetting strengthSvecheniya;
   private BooleanSetting onlyMir;

   public Bloom() {
      super("Bloom", Category.VISUAL);
      SliderSetting slidersetting = new SliderSetting("", "", 0.9, 0.0, 1.0, 0.01, "", 2);
      slidersetting.setName("Порог");
      slidersetting.setDescription("Минимальная яркость пикселя для попадания в bloom");
      this.porog = slidersetting;
      slidersetting = new SliderSetting("", "", 4.0, 0.0, 10.0, 0.05, "", 2);
      slidersetting.setName("Интенсивность");
      slidersetting.setDescription("Насколько яркие свечения");
      this.intensivnost = slidersetting;
      slidersetting = new SliderSetting("", "", 3.0, 0.0, 8.0, 0.05, "x", 2);
      slidersetting.setName("Сила свечения");
      slidersetting.setDescription("Множитель яркости bloom при смешивании со сценой");
      this.strengthSvecheniya = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Только мир");
      booleansetting.setDescription("Применять только к миру. Руки и HUD останутся обычными");
      this.onlyMir = booleansetting;
      this.addSettings(new Setting[]{this.porog, this.intensivnost, this.strengthSvecheniya, this.onlyMir});
   }

   @Override
   public void onTick() {
      if (this.isEnabled()) {
         flag = true;
         this.update11();
      }
   }

   @Override
   public void onDisable() {
      flag = false;
   }

   private static Field getFieldByClassClass(Class value, Class value2) {
      for (Field field : value.getDeclaredFields()) {
         if (!Modifier.isStatic(field.getModifiers()) && field.getType() == List.class && isFieldClass(field, value2)) {
            field.setAccessible(true);
            return field;
         }
      }

      return null;
   }

   private static boolean isFieldClass(Field field, Class value) {
      if (field.getGenericType() instanceof ParameterizedType parameterizedtype) {
         Type[] atype = parameterizedtype.getActualTypeArguments();
         if (atype.length != 1) {
            return false;
         } else {
            Type type = atype[0];
            Class oclass = type instanceof Class oclass2
               ? oclass2
               : (type instanceof ParameterizedType parameterizedtype1 && parameterizedtype1.getRawType() instanceof Class oclass1 ? oclass1 : null);
            return oclass == value;
         }
      } else {
         return false;
      }
   }

   private static List getListByClassObjectClass(Class value, Object value2, Class value3) throws IllegalAccessException {
      for (Field field : value3.getDeclaredFields()) {
         if (!Modifier.isStatic(field.getModifiers()) && field.getType() == List.class && isFieldClass(field, value)) {
            field.setAccessible(true);
            return (List)field.get(value2);
         }
      }

      return null;
   }

   private static void setObject(Object value) throws Exception {
      Field field = getFieldByClassClass(PostEffectPass.class, Uniform.class);
      if (field != null) {
         List listx = (List)field.get(value);
         if (listx != null && !listx.isEmpty()) {
            Constructor constructor = Uniform.class.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            ArrayList arraylist = new ArrayList(listx);

            for (int i = 0; i < arraylist.size(); i++) {
               Uniform uniform = (Uniform)arraylist.get(i);
               String s = uniform.name();
               Object object;
               if ("Threshold".equals(s)) {
                  object = list;
               } else if ("Intensity".equals(s)) {
                  object = list2;
               } else {
                  if (!"BloomBoost".equals(s)) {
                     continue;
                  }

                  object = list3;
               }

               if (object == null) {
                  List list1 = uniform.values();
                  if (list1 == null || list1.isEmpty()) {
                     continue;
                  }

                  object = new ArrayList(list1);
                  if ("Threshold".equals(s)) {
                     list = (List<Float>)object;
                  } else if ("Intensity".equals(s)) {
                     list2 = (List<Float>)object;
                  } else {
                     list3 = (List<Float>)object;
                  }
               }

               arraylist.set(i, (Uniform)constructor.newInstance(s, object));
            }

            field.set(value, arraylist);
         }
      }
   }

   public static void setFramebuffer(Framebuffer framebuffer) {
      if (flag) {
         if (!(value239 <= 0.001F) && !(value240 <= 0.001F)) {
            if (!ResourceManagerHooks.isFlag4()) {
               ObjectAllocator objectallocator = objectAllocator;
               if (framebuffer != null && objectallocator != null) {
                  ShaderLoader shaderloader = mc.getShaderLoader();
                  if (shaderloader != null) {
                     int i = ResourceManagerHooks.getInt();
                     if (i != value237) {
                        postEffectProcessor = null;
                        value235 = null;
                        value236 = null;
                        list3 = null;
                        list2 = null;
                        list = null;
                        value243 = Float.NaN;
                        value242 = Float.NaN;
                        value241 = Float.NaN;
                        value237 = i;
                     }

                     PostEffectProcessor posteffectprocessor = postEffectProcessor;
                     if (posteffectprocessor == null || shaderloader != value235) {
                        if (shaderloader == value236) {
                           return;
                        }

                        try {
                           posteffectprocessor = mc.getShaderLoader().loadPostEffect(identifier, DefaultFramebufferSet.MAIN_ONLY);
                        } catch (Throwable throwable1) {
                           value236 = shaderloader;
                           return;
                        }

                        if (posteffectprocessor == null) {
                           value236 = shaderloader;
                           return;
                        }

                        list3 = null;
                        list2 = null;
                        list = null;
                        value243 = Float.NaN;
                        value242 = Float.NaN;
                        value241 = Float.NaN;
                        onPostEffectProcessor(posteffectprocessor);
                        postEffectProcessor = posteffectprocessor;
                        value235 = shaderloader;
                        value236 = null;
                     }

                     if (list != null && value238 != value241) {
                        list.set(0, value238);
                        value241 = value238;
                     }

                     if (list2 != null && value239 != value242) {
                        list2.set(0, value239);
                        value242 = value239;
                     }

                     if (list3 != null && value240 != value243) {
                        list3.set(0, value240);
                        value243 = value240;
                     }

                     try {
                        posteffectprocessor.render(framebuffer, objectallocator);
                     } catch (Throwable throwable) {
                     }
                  }
               }
            }
         }
      }
   }

   private void update11() {
      flag2 = this.onlyMir.isFlag3();
      value238 = this.porog.getValueAsFloat();
      value239 = this.intensivnost.getValueAsFloat();
      value240 = this.strengthSvecheniya.getValueAsFloat();
   }

   public static boolean isFlag2() {
      return flag2;
   }

   public static void setObjectAllocator(ObjectAllocator objectAllocator2) {
      objectAllocator = objectAllocator2;
   }

   private static void onPostEffectProcessor(PostEffectProcessor postEffectProcessor) {
      try {
         Class<PostEffectPass> oclass1 = PostEffectPass.class;
         Class<PostEffectProcessor> oclass = PostEffectProcessor.class;
         List listx = getListByClassObjectClass(oclass1, postEffectProcessor, oclass);
         if (listx == null) {
            return;
         }

         for (Object object : listx) {
            if (object != null) {
               setObject(object);
            }
         }
      } catch (Throwable throwable) {
      }
   }

   @Override
   public void onEnable() {
      flag = true;
      this.update11();
   }
}
