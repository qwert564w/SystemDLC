package client.util;

public final class TimeFormat {
   private TimeFormat() {
   }

   public static String getStringByDouble(double value) {
      return value < 10.0 ? String.format("%.1fc", value) : String.format("%dc", (int)value);
   }

   public static String getStringByLong(long time) {
      long i = Math.max(0L, time) / 1000L;
      return String.format("%d:%02d", i / 60L, i % 60L);
   }
}
