import java.util.*;
import java.util.stream.Collectors;

public class KMean {
    List<Value> values;
    List<Cluster> sets;
    int dimension;

    int k;


    public KMean(List<Value> values, int k) {
        this.dimension = values.getFirst().vector.length;
        this.values = values;
        this.k = k;
        sets = new ArrayList<Cluster>(k);
        for (int i = 0; i < k; i++) {
            sets.add(new Cluster(dimension));
        }
        init_centroids();
    }

    private void init_centroids() {
        List<Integer> random_indecies = new Random().ints(0, values.size()).distinct().limit(k).boxed().collect(Collectors.toList());
        for (int i = 0; i < k; i++) {
            var val = values.get(random_indecies.get(i));
//            System.out.println("Centroid: " + val);
            sets.get(i).setCentroid(val.copy());
        }
    }

    public void calculateSets(){
        for (Set<Value> set : sets) {
            set.clear();
        }
        for (var value: values) {
            int index = 0;
            double minDistance = value.diffSquared(sets.get(index).getCentroid());

            for (int i = 1; i < k; i++) {
                double temp = value.diffSquared(sets.get(i).getCentroid());
                if (temp < minDistance){
                    index = i;
                    minDistance = temp;
                }
            }

            sets.get(index).add(value);
        }
    }

    public boolean performIteration(){
        var tempCentroids = sets.stream().map(Cluster::getCentroid).toList();
        calculateSets();
        sets.forEach(Cluster::calculateCentroid);
        for (int i = 0; i < k; i++) {
            if(!tempCentroids.get(i).equals(sets.get(i).getCentroid())){
                return true;
            }
        }
        return false;
    }

    public double totalSum(){
        return sets.stream().map(Cluster::sumOfDistances).mapToDouble(Double::doubleValue).sum();
    }

    public void printInfo(boolean print_members){
        for (int i = 0; i < k; i++) {
            System.out.printf("Set #%d\n", i+1);
            sets.get(i).printHomogeneity();
            if(print_members){
                for (var value : sets.get(i)) {
                    System.out.println("\t" + value);
                }
            }
        }
    }
}
