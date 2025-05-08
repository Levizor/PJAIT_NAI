import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// is edible?
public class BayesClassifier {
    private List<Value> values;
    private int numTotal = 0;
    private HashMap<Pair<Integer, String>, Integer> positiveCounts = new HashMap<>();
    private int numPositive = 0;
    private HashMap<Pair<Integer, String>, Integer> negativeCounts = new HashMap<>();
    private int numNegative = 0;
    private HashMap<Integer, Integer> numberOfFeatures = new HashMap<>();

    public BayesClassifier(List<Value> values) {
        this.values = values;
        this.numTotal = values.size();
        countAttributes();
    }

    public void countAttributes() {
        for (Value value : values) {
            var counts = switch (value.label){
                case "e" -> {
                    numPositive++; yield positiveCounts;
                }
                case "p" -> {
                    numNegative++; yield negativeCounts;
                }
                default -> null;
            };
            assert counts != null;

            for(int i=0; i<value.attributes.length; i++){
                var pair = new Pair<>(i, value.attributes[i]);
                counts.put(pair, counts.getOrDefault(pair, 0) + 1);
            }
        }

        var keys =  new HashSet<>(positiveCounts.keySet());
        keys.addAll(negativeCounts.keySet());
        var mapSet = new HashMap<Integer, Set<String>>();

        keys.stream().forEach(pair -> {
            var set =  mapSet.getOrDefault(pair.first, new HashSet<String>());
            set.add(pair.second);
            mapSet.put(pair.first, set);
        });

        for (var key : mapSet.keySet()) {
            numberOfFeatures.put(key, mapSet.get(key).size());
        }
    }

    private double trueProbability(){
        return (double) numPositive / numTotal;
    }

    private double falseProbability(){
        return (double) numNegative / numTotal;
    }

    private double probability(boolean is_positive, Pair<Integer, String> attribute){
        var counts = is_positive ? positiveCounts : negativeCounts;
        var num = is_positive ? numPositive : numNegative;

        double pairCount = counts.getOrDefault(attribute, 0);

        if (pairCount == 0) {
            pairCount = 1;
            num += numberOfFeatures.get(attribute.first);
        }

        return pairCount / num;
    }

    private double probability(boolean is_positive, String[] attributes){
        double probability = is_positive ? trueProbability() : falseProbability();
        for (int i = 0; i < attributes.length; i++) {
            var pair = new Pair<>(i, attributes[i]);
            probability *= probability(is_positive, pair);
        }

        return probability;
    }

    public boolean is_positive(Value value) {
        var positiveProbability = probability(true, value.attributes);
        var negativeProbability = probability(false, value.attributes);

        return positiveProbability >= negativeProbability;
    }


    public Evaluation classify(List<Value> values) {
        Evaluation eval = new Evaluation();

        for (Value value : values) {
            boolean is_positive = is_positive(value);
            if(is_positive){
               if (value.is_positive()) eval.TP++;
               else eval.FP++;
            } else {
                if (value.is_negative()) eval.TN++;
                else eval.FN++;
            }
        }

        return eval;
    }

}
