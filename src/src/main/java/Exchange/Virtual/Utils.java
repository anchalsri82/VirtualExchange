package Exchange.Virtual;
import java.util.Map;

public class Utils {

    public  static <K, V> void ConvertToTreeMap(Map<K, V> fromHashMap, Map<K, V> toTreeMap)
    {
        for (Map.Entry<K, V> entry : fromHashMap.entrySet()) {
            toTreeMap.put(entry.getKey(), entry.getValue());
        }
    }
}
