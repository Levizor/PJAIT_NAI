import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// is edible?
public class BayesClassifier {
    private List<Value> values;
    private int numTotal = 0;
    private HashMap<Pair<Integer, String>, Integer> edibleCounts = new HashMap<>();
    private int numEdible = 0;
    private HashMap<Pair<Integer, String>, Integer> poisonousCounts = new HashMap<>();
    private int numPoisonus = 0;
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
                    numEdible++; yield edibleCounts;
                }
                case "p" -> {
                    numPoisonus++; yield poisonousCounts;
                }
                default -> null;
            };
            assert counts != null;

            for(int i=0; i<value.attributes.length; i++){
                var pair = new Pair<>(i, value.attributes[i]);
                counts.put(pair, counts.getOrDefault(pair, 0) + 1);
            }
        }

        var keys =  new HashSet<>(edibleCounts.keySet());
        keys.addAll(poisonousCounts.keySet());
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
        return (double) numEdible / numTotal;
    }

    private double falseProbability(){
        return (double) numPoisonus / numTotal;
    }

    private double probability(boolean is_edible, Pair<Integer, String> attribute){
        var counts = is_edible ? edibleCounts : poisonousCounts;
        var num = is_edible ? numEdible : numPoisonus;

        double pairCount = counts.getOrDefault(attribute, 0);

        if (pairCount == 0) {
            pairCount = 1;
            num += numberOfFeatures.get(attribute.first);
        }

        return pairCount / num;
    }

    private double probability(boolean is_edible, String[] attributes){
        double probability = is_edible ? trueProbability() : falseProbability();
        for (int i = 0; i < attributes.length; i++) {
            var pair = new Pair<>(i, attributes[i]);
            probability *= probability(is_edible, pair);
        }

        return probability;
    }

    public boolean is_edible(Value value) {
        var edible = probability(true, value.attributes);
        var poisonous = probability(false, value.attributes);

        return edible >= poisonous;
    }


    public Evaluation classify(List<Value> values) {
        Evaluation eval = new Evaluation();

        for (Value value : values) {
            boolean is_edible = is_edible(value);
            if(is_edible){
               if (value.is_edible()) eval.TP++;
               else eval.FP++;
            } else {
                if (value.is_poisonous()) eval.TN++;
                else eval.FN++;
            }
        }

        return eval;
    }

}
