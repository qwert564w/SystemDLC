package recovered.fabric.nativebridge;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

public final class NativeLibrary {
   private NativeLibrary() {
   }

   public static boolean enabled() {
      return Boolean.parseBoolean(System.getProperty("systemdlc.native", "true"));
   }

   public static Path extract() throws IOException {
      String resource;
      String fileName;
      String osName = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
      if (osName.contains("windows")) {
         resource = "/native/windows-x86_64/systemdlc.dll";
         fileName = "systemdlc.dll";
      } else if (osName.contains("linux")) {
         resource = "/native/linux-x86_64/systemdlc.so";
         fileName = "systemdlc.so";
      } else if (osName.contains("mac") || osName.contains("darwin")) {
         resource = "/native/macos-x86_64/systemdlc.dylib";
         fileName = "systemdlc.dylib";
      } else {
         throw new IOException("Unsupported OS: " + osName);
      }
      Path path;
      try (InputStream input = NativeLibrary.class.getResourceAsStream(resource)) {
         if (input == null) {
            throw new IOException("Native library resource is missing: " + resource);
         }

         Path directory = Files.createTempDirectory("systemdlc-native-");
         Path library = directory.resolve(fileName);
         Files.copy(input, library, StandardCopyOption.REPLACE_EXISTING);
         directory.toFile().deleteOnExit();
         library.toFile().deleteOnExit();
         path = library;
      }

      return path;
   }
}
