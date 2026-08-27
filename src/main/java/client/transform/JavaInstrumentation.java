package client.transform;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.ProtectionDomain;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public final class JavaInstrumentation {
   private static Instrumentation instrumentation;
   private static boolean failed;
   private static long lastAttempt;
   private static int attempts;

   private JavaInstrumentation() {
   }

   public static synchronized boolean isAvailable() {
      if (instrumentation != null) {
         return true;
      }
      if (attempts >= 3) {
         return false;
      }
      if (failed && System.currentTimeMillis() - lastAttempt < 5000L) {
         return false;
      }
      attempts++;
      try {
         lastAttempt = System.currentTimeMillis();
         attemptAttach();
      } catch (Throwable throwable) {
         failed = true;
         System.err.println("[SystemDLC] JavaInstrumentation attach failed (" + attempts + "/3): " + throwable.getMessage());
         if (attempts >= 3) {
            System.err.println("[SystemDLC] JavaInstrumentation disabled; mixin hooks remain active");
         }
      }
      return instrumentation != null;
   }

   public static boolean redefineClass(Class<?> targetClass, byte[] newBytecode) {
      if (instrumentation == null || !instrumentation.isRedefineClassesSupported()) {
         return false;
      }
      try {
         ClassFileTransformer transformer = new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
               return newBytecode;
            }
         };
         instrumentation.addTransformer(transformer, true);
         instrumentation.retransformClasses(targetClass);
         instrumentation.removeTransformer(transformer);
         return true;
      } catch (Throwable throwable) {
         return false;
      }
   }

   private static void attemptAttach() throws Throwable {
      String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
      Path agentJar = createAgentJar();
      try {
         Class<?> vmClass;
         try {
            vmClass = Class.forName("com.sun.tools.attach.VirtualMachine");
         } catch (ClassNotFoundException | NoClassDefFoundError e) {
            throw new IllegalStateException("Attach API not available. Add --add-opens jdk.attach/com.sun.tools.attach=ALL-UNNAMED to JVM args", e);
         }
         Object vm = vmClass.getMethod("attach", String.class).invoke(null, pid);
         vmClass.getMethod("loadAgent", String.class, String.class)
            .invoke(vm, agentJar.toAbsolutePath().toString(), "");
         vmClass.getMethod("detach").invoke(vm);
         vmClass.getMethod("close").invoke(vm);
      } finally {
         Files.deleteIfExists(agentJar);
      }
   }

   private static Path createAgentJar() throws Throwable {
      Path tempPath = Files.createTempFile("systemdlc-agent-", ".jar");
      Manifest manifest = new Manifest();
      manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
      manifest.getMainAttributes().putValue("Premain-Class", "client.transform.JavaInstrumentation$Agent");
      manifest.getMainAttributes().putValue("Can-Retransform-Classes", "true");
      try (JarOutputStream jos = new JarOutputStream(Files.newOutputStream(tempPath), manifest)) {
         jos.putNextEntry(new JarEntry("client/transform/JavaInstrumentation$Agent.class"));
         jos.write(getAgentBytecode());
         jos.closeEntry();
      }
      return tempPath;
   }

   private static byte[] getAgentBytecode() {
      String[] paths = {
         "/client/transform/JavaInstrumentation$Agent.class",
         "/client/transform/JavaInstrumentation$Agent.class",
         "client/transform/JavaInstrumentation$Agent.class"
      };
      for (String path : paths) {
         try (var is = JavaInstrumentation.class.getResourceAsStream(path)) {
            if (is != null) {
               byte[] bytes = is.readAllBytes();
               if (bytes.length > 4) {
                  return bytes;
               }
            }
         } catch (Throwable ignored) {
         }
      }
      System.err.println("[SystemDLC] Agent bytecode not found on classpath");
      return new byte[0];
   }

   public static class Agent {
      public static void premain(String args, Instrumentation inst) {
         instrumentation = inst;
      }

      public static void agentmain(String args, Instrumentation inst) {
         instrumentation = inst;
      }
   }
}
