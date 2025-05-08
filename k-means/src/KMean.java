import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KMean {
    List<Value> values;
    List<Set<Value>> sets;
    List<Value> centroids;
    int dimension;

    int k;


    public KMean(List<Value> values, int k) {
        this.dimension = values.getFirst().vector.length;
        this.values = values;
        this.k = k;
        sets = new ArrayList<Set<Value>>(k);
        for (var set : sets) {
            set = new HashSet<>();
        }
        centroids = new ArrayList<>(k);
        init_centroids();
    }

    private void init_centroids() {
        for (int i = 0; i < k; i++) {
            var val = values.get((int) (Math.random() * values.size()));
            centroids.set(i, new Value(val.vector));
        }
    }

    public void calculateSets(){
        for (Set<Value> set : sets) {
            set.clear();
        }
        for (var value: values) {
            int index = 0;
            double sum = value.diffSquared(centroids.get(index));

            for (int i = 1; i < k; i++) {
                double temp = value.diffSquared(centroids.get(i));
                if (temp < sum){
                    index = i;
                    sum = temp;
                }
            }

            sets.get(index).add(value);
        }
    }

    public boolean performIteration(){
        var tempCentroids = new ArrayList<>(centroids);
        calculateSets();
        calculateCentroids();
        for (int i = 0; i < k; i++) {
            if(!tempCentroids.get(i).equals(centroids.get(i))){
                return true;
            }
        }
        return false;
    }

    public double totalSum(){
        double sum = 0;
        for (int i = 0; i < k; i++) {
            for(var value: sets.get(i)){
                sum+=value.diffSquared(centroids.get(i));
            }
        }

        return sum;
    }

    public void calculateCentroids(){
        for (int i = 0; i < k; i++) {
            centroids.set(i, getCentroid(sets.get(i)));
        }
    }

    public Value getCentroid(Set<Value> set){
        double[] sums = new double[dimension];
        for (var value : set) {
            for (int i = 0; i < dimension; i++){
                sums[i]+=value.vector[i];
            }
        }

        for (int i = 0; i < dimension; i++){
            sums[i]/=set.size();
        }

        return new Value(sums);
    }

}
