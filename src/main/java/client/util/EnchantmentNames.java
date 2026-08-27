package client.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKey;

public final class EnchantmentNames {
   private static final String[] stringArray = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
   private static final Map<String, EnchantmentNames> map = new LinkedHashMap<>();
   private final RegistryKey<Enchantment> registryKey;
   private final int value;
   private final String text;
   private final String text2;
   private static final List<EnchantmentNames> list = new ArrayList<>();
   private static final List<EnchantmentNames> list2 = new ArrayList<>();

   private EnchantmentNames(RegistryKey registryKey2, int count, String text3) {
      this.registryKey = registryKey2;
      this.value = count;
      this.text = count > 0 ? text3 + " " + stringArray[Math.min(count, stringArray.length - 1)] : text3;
      this.text2 = registryKey2.getValue() + "@" + count;
   }

   static {
      String s = "Защита";
      byte b0 = 5;
      RegistryKey registrykey = Enchantments.PROTECTION;
      List listx = list;
      onIntRegistryKeyStringList(b0, registrykey, s, listx);
      String s1 = "Прочность";
      byte b1 = 5;
      RegistryKey registrykey1 = Enchantments.UNBREAKING;
      List list1 = list;
      onIntRegistryKeyStringList(b1, registrykey1, s1, list1);
      String s2 = "Починка";
      byte b2 = 0;
      RegistryKey registrykey2 = Enchantments.MENDING;
      List list2x = list;
      onIntRegistryKeyStringList(b2, registrykey2, s2, list2x);
      String s3 = "Шипы";
      byte b3 = 0;
      RegistryKey registrykey3 = Enchantments.THORNS;
      List list3 = list;
      onIntRegistryKeyStringList(b3, registrykey3, s3, list3);
      String s4 = "Невесомость";
      byte b4 = 4;
      RegistryKey registrykey4 = Enchantments.FEATHER_FALLING;
      List list4 = list;
      onIntRegistryKeyStringList(b4, registrykey4, s4, list4);
      String s5 = "Подводная ходьба";
      byte b5 = 3;
      RegistryKey registrykey5 = Enchantments.DEPTH_STRIDER;
      List list5 = list;
      onIntRegistryKeyStringList(b5, registrykey5, s5, list5);
      String s6 = "Острота";
      byte b6 = 7;
      RegistryKey registrykey6 = Enchantments.SHARPNESS;
      List list6 = list2;
      onIntRegistryKeyStringList(b6, registrykey6, s6, list6);
      String s7 = "Прочность";
      byte b7 = 5;
      RegistryKey registrykey7 = Enchantments.UNBREAKING;
      List list7 = list2;
      onIntRegistryKeyStringList(b7, registrykey7, s7, list7);
      String s8 = "Заговор огня";
      byte b8 = 2;
      RegistryKey registrykey8 = Enchantments.FIRE_ASPECT;
      List list8 = list2;
      onIntRegistryKeyStringList(b8, registrykey8, s8, list8);
      String s9 = "Отдача";
      byte b9 = 2;
      RegistryKey registrykey9 = Enchantments.KNOCKBACK;
      List list9 = list2;
      onIntRegistryKeyStringList(b9, registrykey9, s9, list9);
      String s10 = "Добыча";
      byte b10 = 3;
      RegistryKey registrykey10 = Enchantments.LOOTING;
      List list10 = list2;
      onIntRegistryKeyStringList(b10, registrykey10, s10, list10);
      String s11 = "Починка";
      byte b11 = 0;
      RegistryKey registrykey11 = Enchantments.MENDING;
      List list11 = list2;
      onIntRegistryKeyStringList(b11, registrykey11, s11, list11);
   }

   public String getText2() {
      return this.text2;
   }

   public static List<EnchantmentNames> getList() {
      return getListByList(list2);
   }

   public int getValue() {
      return this.value;
   }

   public String getText() {
      return this.text;
   }

   private static void onIntRegistryKeyStringList(int count, RegistryKey registryKey, String text2, List<EnchantmentNames> list) {
      EnchantmentNames enchantmentnames = new EnchantmentNames(registryKey, count, text2);
      map.put(enchantmentnames.text, enchantmentnames);
      list.add(enchantmentnames);
   }

   public static EnchantmentNames getEnchantmentNamesByString(String text) {
      return map.get(text);
   }

   private static List getListByList(List<EnchantmentNames> list) {
      ArrayList arraylist = new ArrayList(list.size());

      for (EnchantmentNames enchantmentnames : list) {
         arraylist.add(enchantmentnames.text);
      }

      return arraylist;
   }

   public static List<EnchantmentNames> getList2() {
      return getListByList(list);
   }

   public RegistryKey<Enchantment> getRegistryKey() {
      return this.registryKey;
   }
}
