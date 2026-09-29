package pulse.cosmetic;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import net.minecraft.util.Identifier;
import ru.pulse.cosmetic.loader.CosmeticLoader;
import ru.pulse.cosmetic.model.CosmeticModel;

public final class LocalCosmetics {
    private static final int MAX_SCAN = 256;
    private static final List<Entry> ENTRIES = loadEntries();
    private static final LinkedHashMap<String, Integer> SELECTED = new LinkedHashMap<>();
    private static final Map<Integer, CosmeticModel> MODELS = new HashMap<>();
    private LocalCosmetics() {}
    public static int size() { return ENTRIES.size(); }
    public static String name(int i) { return entry(i).name; }
    public static String type(int i) { return entry(i).type; }
    public static int resourceIndex(int i) { return entry(i).index; }
    public static List<Integer> selectedIndices() { return List.copyOf(SELECTED.values()); }
    public static boolean isSelected(int i) { return SELECTED.containsValue(i); }
    public static void clearSelection() { SELECTED.clear(); }
    public static void toggle(int i) {
        if (i < 0 || i >= ENTRIES.size()) return;
        String t = type(i);
        Integer old = SELECTED.get(t);
        if (old != null && old == i) SELECTED.remove(t);
        else { SELECTED.put(t, i); if (!"cape".equals(t)) model(i); }
    }
    public static CosmeticModel modelFor(int i) { return model(i); }
    private static CosmeticModel model(int i) {
        int k = entry(i).index;
        return MODELS.computeIfAbsent(k, idx -> {
            try (InputStream in = LocalCosmetics.class.getResourceAsStream("/assets/pulse/cosmetics/models/cosmetic_" + idx + ".json")) {
                if (in == null) return null;
                String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                Identifier tex = Identifier.of("pulse", "textures/cosmetics/cosmetic_" + idx + ".png");
                return CosmeticLoader.getInstance().loadFromJson(json, tex, idx);
            } catch (Exception e) { return null; }
        });
    }
    private static Entry entry(int i) {
        if (i < 0 || i >= ENTRIES.size()) throw new IndexOutOfBoundsException();
        return ENTRIES.get(i);
    }
    private static List<Entry> loadEntries() {
        List<Entry> list = new ArrayList<>();
        for (int i = 0; i < MAX_SCAN; i++) {
            try (InputStream in = LocalCosmetics.class.getResourceAsStream("/assets/pulse/cosmetics/models/cosmetic_" + i + ".json")) {
                if (in == null) continue;
                JsonObject o = JsonParser.parseString(new String(in.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
                String raw = o.has("name") ? o.get("name").getAsString() : "Cosmetic " + i;
                String rawType = o.has("type") ? o.get("type").getAsString() : "";
                int pos = o.has("pos") ? o.get("pos").getAsInt() : -1;
                list.add(new Entry(i, display(raw, i), classify(i, raw, rawType, pos)));
            } catch (Exception ignored) {}
        }
        list.sort(Comparator.comparingInt(e -> e.index));
        return List.copyOf(list);
    }
    private static String display(String n, int i) {
        if (n == null || n.isBlank()) n = "Cosmetic " + i;
        if (n.startsWith("pulse_")) n = n.substring(6);
        return n.replace('_', ' ').trim();
    }
    private static String classify(int i, String name, String raw, int pos) {
        if (raw != null && !raw.isBlank()) return raw.trim().toLowerCase();
        String l = name == null ? "" : name.toLowerCase();
        if (l.contains("cape")) return "cape";
        if (l.contains("wing")) return "wings";
        if (l.contains("pet") || l.contains("bee")) return "pet";
        if (l.contains("hat") || pos == 2) return "hat";
        if (i <= 13) return "cape";
        if (i <= 26) return "wings";
        if (i <= 37) return "bodywear";
        if (i <= 49) return "pet";
        return "bodywear";
    }
    private static final class Entry {
        final int index; final String name; final String type;
        Entry(int index, String name, String type) { this.index = index; this.name = name; this.type = type; }
    }
}
