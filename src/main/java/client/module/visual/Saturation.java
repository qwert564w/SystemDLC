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

public class Saturation extends Module {
   private static final Identifier identifier = Identifier.ofVanilla("saturation");
   private static float value235 = 1.0F;
   private static List<Float> list = null;
   private static boolean flag = false;
   private static PostEffectProcessor postEffectProcessor = null;
   private static Object value236 = null;
   private static Object value237 = null;
   private static int value238 = -1;
   private static ObjectAllocator objectAllocator = null;
   private static boolean flag2 = false;
   private SliderSetting nasyschennost;
   private BooleanSetting onlyMir;

   public Saturation() {
      super("Saturation", Category.VISUAL);
      SliderSetting slidersetting = new SliderSetting("", "", 1.0, 0.0, 2.0, 0.05, "", 2);
      slidersetting.setName("Насыщенность");
      slidersetting.setDescription("0 — серый, 1 — обычный, выше — насыщеннее");
      this.nasyschennost = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Только мир");
      booleansetting.setDescription("Применять только к миру. Руки и HUD останутся обычными");
      this.onlyMir = booleansetting;
      this.addSettings(new Setting[]{this.nasyschennost, this.onlyMir});
   }

   @Override
   public void onTick() {
      float f = this.nasyschennost.getValueAsFloat();
      value235 = f;
      flag = Math.abs(f - 1.0F) > 0.001F;
      flag2 = this.onlyMir.isFlag3();
      onFloat(f);
   }

   @Override
   public void onDisable() {
      value235 = 1.0F;
      flag = false;
      onFloat(1.0F);
   }

   private static List getListByClassClassObject(Class value, Class value2, Object value3) throws IllegalAccessException {
      for (Field field : value2.getDeclaredFields()) {
         if (!Modifier.isStatic(field.getModifiers()) && field.getType() == List.class && isFieldClass(field, value)) {
            field.setAccessible(true);
            return (List)field.get(value3);
         }
      }

      return null;
   }

   private static Field getFieldByClassClass(Class value, Class value2) {
      for (Field field : value2.getDeclaredFields()) {
         if (!Modifier.isStatic(field.getModifiers()) && field.getType() == List.class && isFieldClass(field, value)) {
            field.setAccessible(true);
            return field;
         }
      }

      return null;
   }

// PasterEnd, CordLaucnher, and other community UEABNU AND XYECOCN AND PIDORASI CLUBAITE NORM A NE KAK PIDORASU

   private static void setObject(Object value) throws Exception {
      Class<Uniform> oclass1 = Uniform.class;
      Class<PostEffectPass> oclass = PostEffectPass.class;
      Field field = getFieldByClassClass(oclass1, oclass);
      if (field != null) {
         List listx = (List)field.get(value);
         if (listx != null && !listx.isEmpty()) {
            int i = -1;
            Uniform uniform = null;

            for (int j = 0; j < listx.size(); j++) {
               Uniform uniform1 = (Uniform)listx.get(j);
               if ("Saturation".equals(uniform1.name())) {
                  i = j;
                  uniform = uniform1;
                  break;
               }
            }

            if (i >= 0 && uniform != null) {
               List list1 = uniform.values();
               if (list1 != null) {
                  ArrayList arraylist1 = new ArrayList(list1);
                  list = arraylist1;
                  Constructor constructor = Uniform.class.getDeclaredConstructors()[0];
                  constructor.setAccessible(true);
                  ArrayList arraylist = new ArrayList(listx);
                  arraylist.set(i, (Uniform)constructor.newInstance(uniform.name(), arraylist1));
                  field.set(value, arraylist);
               }
            }
         }
      }
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

   public static void setFramebuffer(Framebuffer framebuffer) {
      if (flag) {
         if (!ResourceManagerHooks.isFlag4()) {
            ObjectAllocator objectallocator = objectAllocator;
            if (framebuffer != null && objectallocator != null) {
               ShaderLoader shaderloader = mc.getShaderLoader();
               if (shaderloader != null) {
                  int i = ResourceManagerHooks.getInt();
                  if (i != value238) {
                     postEffectProcessor = null;
                     value236 = null;
                     value237 = null;
                     list = null;
                     value238 = i;
                  }

                  PostEffectProcessor posteffectprocessor = postEffectProcessor;
                  if (posteffectprocessor == null || shaderloader != value236) {
                     if (shaderloader == value237) {
                        return;
                     }

                     try {
                        posteffectprocessor = mc.getShaderLoader().loadPostEffect(identifier, DefaultFramebufferSet.MAIN_ONLY);
                     } catch (Throwable throwable1) {
                        value237 = shaderloader;
                        return;
                     }

                     if (posteffectprocessor == null) {
                        value237 = shaderloader;
                        return;
                     }

                     onPostEffectProcessor(posteffectprocessor);
                     onFloat(value235);
                     postEffectProcessor = posteffectprocessor;
                     value236 = shaderloader;
                     value237 = null;
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

   public static boolean isFlag2() {
      return flag2;
   }

   private static void onFloat(float value) {
      List listx = list;
      if (listx != null && !listx.isEmpty()) {
         try {
            listx.set(0, value);
         } catch (Throwable throwable) {
         }
      }
   }

   public static void setObjectAllocator(ObjectAllocator objectAllocator2) {
      objectAllocator = objectAllocator2;
   }

   public static void onPostEffectProcessor(PostEffectProcessor postEffectProcessor) {
      try {
         Class<PostEffectPass> oclass1 = PostEffectPass.class;
         Class<PostEffectProcessor> oclass = PostEffectProcessor.class;
         List listx = getListByClassClassObject(oclass1, oclass, postEffectProcessor);
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
   }
}
