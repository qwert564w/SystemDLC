package ru.pulse.cosmetic.geckolib;

import ru.pulse.Pulse;
import ru.pulse.cosmetic.geo.GeoModel;
import ru.pulse.cosmetic.geo.GeoModelParser;
import ru.pulse.cosmetic.model.CosmeticModel;

public class GeckolibModelParser {
   public GeoModel parseModel(CosmeticModel var1) {
      try {
         String var2 = var1.getRawModelJson();
         if (var2 == null) {
            Pulse.getLOGGER().error("No raw model JSON available for cosmetic: " + var1.getName());
            return null;
         } else {
            GeoModel var3 = GeoModelParser.parse(var2);
            if (var3 == null) {
               Pulse.getLOGGER().error("Failed to parse GeoModel for: " + var1.getName());
               return null;
            } else {
               Pulse.getLOGGER().info("Successfully parsed model for: " + var1.getName() + " (bones: " + var3.topLevelBones.size() + ")");
               return var3;
            }
         }
      } catch (Exception var4) {
         Pulse.getLOGGER().error("Error parsing model for: " + var1.getName(), var4);
         return null;
      }
   }
}
