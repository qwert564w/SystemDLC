package client.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class OrderedSet<K> {
   private final LinkedHashSet<K> linkedHashSet = new LinkedHashSet<>();
   private final ArrayList<K> list = new ArrayList<>();

   public void update() {
      this.linkedHashSet.clear();
   }

   public Iterable getLinkedHashSetAsIterable() {
      return this.linkedHashSet;
   }

   public void onSetComparator(Set set, Comparator comparator) {
      this.linkedHashSet.retainAll(set);
      this.list.clear();

      for (Object object : set) {
         if (!this.linkedHashSet.contains(object)) {
            this.list.add((K)object);
         }
      }

      if (comparator != null) {
         this.list.sort(comparator);
      }

      this.linkedHashSet.addAll(this.list);
   }
}
