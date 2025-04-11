import java.security.KeyPair;
import java.util.HashMap;
import java.util.List;



// is edible?
public class BayesClassifier {
    private List<Value> values;
    private HashMap<Pair<Integer, String>, Integer> trueCounts = new HashMap<>();
    private HashMap<Pair<Integer, String>, Integer> falseCounts = new HashMap<>();

    public BayesClassifier(List<Value> values) {
        this.values = values;
        countAttributes();
    }

    public void countAttributes() {
        for (Value value : values) {
            var counts = switch (value.label){
                case "e" -> trueCounts;
                case "p" -> falseCounts;
                default -> null;
            };
            assert counts != null;

            for(int i=0; i<value.attributes.length; i++){
                var pair = new Pair<>(i, value.attributes[i]);
                counts.put(pair, counts.getOrDefault(pair, 0) + 1);
            }
        }
        System.out.println(trueCounts);
    }

}
