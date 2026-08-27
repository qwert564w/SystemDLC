package client.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateUtil {
   private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
   private static final DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT);
   private static final DateTimeFormatter dateTimeFormatter3 = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ROOT);
   private static final DateTimeFormatter dateTimeFormatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
   private static final DateTimeFormatter dateTimeFormatter5 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", Locale.ROOT);

   public static String getStringByString(String text) {
      LocalDateTime localdatetime = getLocalDateTimeByString(text);
      return localdatetime != null ? localdatetime.format(dateTimeFormatter2) : getStringByString3(text);
   }

   public static String getString() {
      return LocalDateTime.now().format(dateTimeFormatter2);
   }

   public static String getStringByString2(String text) {
      LocalDate localdate = getLocalDateByString(text);
      return localdate == null ? null : localdate.format(dateTimeFormatter3);
   }

   public static String getStringByString3(String text) {
      LocalDate localdate = getLocalDateByString(text);
      return localdate == null ? null : localdate.format(dateTimeFormatter);
   }

   private static LocalDateTime getLocalDateTimeByString(String text) {
      if (text == null) {
         return null;
      } else {
         String s = text.trim();
         if (s.isEmpty()) {
            return null;
         } else {
            try {
               return LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException datetimeparseexception4) {
               try {
                  return OffsetDateTime.parse(s).toLocalDateTime();
               } catch (DateTimeParseException datetimeparseexception3) {
                  try {
                     return Instant.parse(s).atZone(ZoneId.systemDefault()).toLocalDateTime();
                  } catch (DateTimeParseException datetimeparseexception2) {
                     try {
                        return LocalDateTime.parse(s, dateTimeFormatter4);
                     } catch (DateTimeParseException datetimeparseexception1) {
                        try {
                           return LocalDateTime.parse(s, dateTimeFormatter5);
                        } catch (DateTimeParseException datetimeparseexception) {
                           return null;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static LocalDate getLocalDateByString(String text) {
      if (text == null) {
         return null;
      } else {
         String s = text.trim();
         if (s.isEmpty()) {
            return null;
         } else {
            try {
               return LocalDate.parse(s, dateTimeFormatter);
            } catch (DateTimeParseException datetimeparseexception2) {
               try {
                  return LocalDate.parse(s, dateTimeFormatter3);
               } catch (DateTimeParseException datetimeparseexception1) {
                  LocalDateTime localdatetime = getLocalDateTimeByString(s);
                  if (localdatetime != null) {
                     return localdatetime.toLocalDate();
                  } else {
                     if (s.length() >= 10) {
                        String s1 = s.substring(0, 10);

                        try {
                           return LocalDate.parse(s1, dateTimeFormatter);
                        } catch (DateTimeParseException datetimeparseexception) {
                        }
                     }

                     return null;
                  }
               }
            }
         }
      }
   }

   public static String getString2() {
      return LocalDate.now().format(dateTimeFormatter);
   }
}
