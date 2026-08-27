package recovered.fabric.offline;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public final class ConfigFiles {
   private static final Path folder = Path.of("config", "systemdlc", "configs").toAbsolutePath();
   private static final Path activeFile = folder.resolve("active");

   private ConfigFiles() {
   }

   public static List<ConfigRecord> all() throws Exception {
      if (!Files.isDirectory(folder)) {
         return List.of();
      } else {
         List list2;
         try (Stream<Path> paths = Files.list(folder)) {
            list2 = paths.filter(path -> path.getFileName().toString().endsWith(".cfg"))
               .map(ConfigFiles::readUnchecked)
               .sorted(Comparator.comparing(ConfigRecord::createdAt))
               .toList();
         }

         return list2;
      }
   }

   public static ConfigRecord find(String name) throws Exception {
      Path path = path(name);
      return Files.isRegularFile(path) ? read(path) : null;
   }

   public static ConfigRecord findByKey(String key) throws Exception {
      return all().stream().filter(config -> config.key().equals(key)).findFirst().orElse(null);
   }

   public static void write(ConfigRecord config) throws Exception {
      Files.createDirectories(folder);
      Files.writeString(path(config.name()), config.serialize(), StandardCharsets.UTF_8);
   }

   public static void rename(String oldName, ConfigRecord config) throws Exception {
      write(config);
      Files.deleteIfExists(path(oldName));
      if (oldName.equals(active())) {
         active(config.name());
      }
   }

   public static void delete(String name) throws Exception {
      Files.deleteIfExists(path(name));
      if (name.equals(active())) {
         Files.deleteIfExists(activeFile);
      }
   }

   public static String active() throws Exception {
      if (!Files.isRegularFile(activeFile)) {
         return null;
      } else {
         String encoded = Files.readString(activeFile, StandardCharsets.UTF_8);
         return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
      }
   }

   public static void active(String name) throws Exception {
      Files.createDirectories(folder);
      String encoded = Base64.getEncoder().encodeToString(name.getBytes(StandardCharsets.UTF_8));
      Files.writeString(activeFile, encoded, StandardCharsets.UTF_8);
   }

   private static ConfigRecord read(Path path) throws Exception {
      return ConfigRecord.deserialize(Files.readString(path, StandardCharsets.UTF_8));
   }

   private static ConfigRecord readUnchecked(Path path) {
      try {
         return read(path);
      } catch (Exception exception) {
         throw new IllegalStateException(exception);
      }
   }

   private static Path path(String name) {
      UUID id = UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));
      return folder.resolve(id + ".cfg");
   }
}
