package client.util;

import client.data.EventStatus;
import client.enums.EventState;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.math.BlockPos;

public final class EventChatParser {
   private static final EventSchedule eventSchedule = new EventSchedule(List.of(), List.of(), List.of(), null);
   private static final Pattern pattern = Pattern.compile("§.");
   private static final Pattern pattern2 = Pattern.compile("^\\[Ивенты]\\s*$");
   private static final Pattern pattern3 = Pattern.compile("^\\[(\\d+)]\\s*(.+?)\\s*$");
   private static final Pattern pattern4 = Pattern.compile("^До следующего ивента:\\s*(.+)$", 66);
   private static final Pattern pattern5 = Pattern.compile("^\\|\\|\\s*Статус:\\s*»?\\s*(.+)$", 66);
   private static final Pattern pattern6 = Pattern.compile("^\\|\\|\\s*Координаты:\\s*\\[(-?\\d+)\\s+(-?\\d+)\\s+(-?\\d+)]\\s*$");
   private static final Pattern pattern7 = Pattern.compile("^\\|\\|\\s*(/warp\\s+\\S+.*)$", 66);
   private static final Pattern pattern8 = Pattern.compile("^\\|\\|\\s*(.+)$");
   private static final Pattern pattern9 = Pattern.compile("(\\d+)\\s*мин", 66);
   private static final Pattern pattern10 = Pattern.compile("(\\d+)\\s*сек", 66);
   private static final Pattern pattern11 = Pattern.compile("\\((\\d+)\\s*сек\\)", 66);
   private static final Pattern pattern12 = Pattern.compile("\\d");
   private static final Pattern pattern13 = Pattern.compile("завершен|завершено|завершено|окончен|окончено", 66);
   private static final Pattern pattern14 = Pattern.compile("Еще не активирован|до извержения|до активации|до открытия|до призыва", 66);
   private static final Pattern pattern15 = Pattern.compile("через[:\\s]+\\d", 66);
   private static final Pattern pattern16 = Pattern.compile("Призыв.+\\(\\d+\\s*сек\\)", 66);
   private static final Pattern pattern17 = Pattern.compile("Лутание\\s*\\(\\d+\\s*сек\\)", 66);

   public static boolean isString(String text) {
      for (String s : (Iterable<String>)(getListByList(List.of(text)))) {
         if (s.contains("[Ивенты]")) {
            return true;
         }

         if (pattern8.matcher(s).matches()) {
            return true;
         }

         if (pattern3.matcher(s).matches()) {
            return true;
         }
      }

      return false;
   }

   private static Integer getIntegerByPatternString(Pattern pattern, String text) {
      Matcher matcher = pattern.matcher(text);
      return matcher.find() ? Integer.parseInt(matcher.group(1)) : null;
   }

   private static List getListByList(List<String> list) {
      ArrayList arraylist = new ArrayList();

      for (String s : list) {
         if (s != null) {
            for (String s1 : pattern.matcher(s).replaceAll("").split("\n")) {
               String s2 = s1.trim();
               if (!s2.isEmpty()) {
                  arraylist.add(s2);
               }
            }
         }
      }

      return arraylist;
   }

   public static EventStatus getEventStatusByString(String text) {
      if (text == null) {
         return null;
      } else {
         String s = text.trim();
         if (s.isEmpty()) {
            return null;
         } else if (pattern13.matcher(s).find()) {
            return new EventStatus(s, EventState.DONE, null);
         } else if (pattern14.matcher(s).find()) {
            return new EventStatus(s, EventState.PENDING, getIntegerByString(s));
         } else if (pattern15.matcher(s).find()) {
            return new EventStatus(s, EventState.PENDING, getIntegerByString(s));
         } else if (pattern16.matcher(s).find()) {
            return new EventStatus(s, EventState.PENDING, getIntegerByPatternString(pattern11, s));
         } else if (pattern17.matcher(s).find()) {
            return new EventStatus(s, EventState.ACTIVE, getIntegerByPatternString(pattern11, s));
         } else if (pattern10.matcher(s).find()) {
            return new EventStatus(s, EventState.ACTIVE, getIntegerByPatternString(pattern10, s));
         } else {
            return !pattern12.matcher(s).find() ? new EventStatus(s, EventState.ACTIVE, null) : new EventStatus(s, EventState.UNKNOWN, null);
         }
      }
   }

   public static Integer getIntegerByString(String text) {
      if (text == null) {
         return null;
      } else {
         Matcher matcher = pattern9.matcher(text);
         Matcher matcher1 = pattern10.matcher(text);
         boolean flag = matcher.find();
         boolean flag1 = matcher1.find();
         return !flag && !flag1 ? null : (flag ? Integer.parseInt(matcher.group(1)) * 60 : 0) + (flag1 ? Integer.parseInt(matcher1.group(1)) : 0);
      }
   }

   public static EventSchedule getEventScheduleByList(List list2) {
      List list = getListByList(list2);
      int i = -1;

      for (int j = list.size() - 1; j >= 0; j--) {
         if (((String)list.get(j)).contains("[Ивенты]")) {
            i = j;
            break;
         }
      }

      if (i < 0) {
         return eventSchedule;
      } else {
         ArrayList arraylist3 = new ArrayList();
         ArrayList arraylist = new ArrayList();
         ArrayList arraylist1 = new ArrayList();
         Integer integer = null;
         int k = i;

         while (k < list.size()) {
            String s = (String)list.get(k);
            if (pattern2.matcher(s).matches()) {
               k++;
            } else {
               Matcher matcher = pattern3.matcher(s);
               if (!matcher.matches()) {
                  k++;
               } else {
                  int l = Integer.parseInt(matcher.group(1));
                  String s1 = matcher.group(2);
                  k++;
                  Matcher matcher1 = pattern4.matcher(s1);
                  if (matcher1.matches()) {
                     Integer integer1 = getIntegerByString(matcher1.group(1));
                     if (integer1 != null) {
                        integer = integer1;
                     }
                  } else {
                     String s2 = s1.replaceAll(":\\s*$", "").trim();
                     EventStatus eventstatus = null;
                     BlockPos blockpos = null;
                     String s3 = null;
                     ArrayList arraylist2 = new ArrayList();

                     while (true) {
                        if (k < list.size()) {
                           String s4 = (String)list.get(k);
                           label69:
                           if (!pattern2.matcher(s4).matches() && !pattern3.matcher(s4).matches()) {
                              Matcher matcher2;
                              if ((matcher2 = pattern5.matcher(s4)).matches()) {
                                 eventstatus = getEventStatusByString(matcher2.group(1));
                              } else if ((matcher2 = pattern6.matcher(s4)).matches()) {
                                 blockpos = new BlockPos(
                                    Integer.parseInt(matcher2.group(1)), Integer.parseInt(matcher2.group(2)), Integer.parseInt(matcher2.group(3))
                                 );
                              } else if ((matcher2 = pattern7.matcher(s4)).matches()) {
                                 s3 = matcher2.group(1).trim();
                              } else {
                                 if (!(matcher2 = pattern8.matcher(s4)).matches()) {
                                    break label69;
                                 }

                                 arraylist2.add(matcher2.group(1).trim());
                              }

                              k++;
                              continue;
                           }
                        }

                        EventInfo eventinfo = new EventInfo(l, s2, eventstatus, blockpos, s3, arraylist2);
                        EventState eventstate = eventstatus == null ? null : eventstatus.getState();
                        if (eventstate == EventState.ACTIVE) {
                           arraylist3.add(eventinfo);
                        } else if (eventstate == EventState.DONE) {
                           arraylist1.add(eventinfo);
                        } else {
                           arraylist.add(eventinfo);
                        }
                        break;
                     }
                  }
               }
            }
         }

         return new EventSchedule(arraylist3, arraylist, arraylist1, integer);
      }
   }

   public static boolean isString2(String text) {
      return text == null ? false : pattern.matcher(text).replaceAll("").contains("[Ивенты]");
   }
}
