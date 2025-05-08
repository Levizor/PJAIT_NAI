import java.util.*;

public class Cluster extends HashSet<Value> {
    int dimension = 0;
    Value centroid = null;

    public Cluster(int dimension) {
        this.dimension = dimension;
    }

    public Value calculateCentroid(){
        double[] sums = new double[dimension];
        for (var value : this) {
            for (int i = 0; i < dimension; i++){
                sums[i]+=value.vector[i];
            }
        }

        for (int i = 0; i < dimension; i++){
            sums[i]/=size();
        }

        centroid = new Value(sums);

        return centroid;
    }

    public Value getCentroid() {
        return centroid;
    }

    public void setCentroid(Value centroid) {
        this.centroid = centroid;
    }

    public double sumOfDistances(){
        double sum = 0;
        for (var value : this) {
            sum += value.diffSquared(getCentroid());
        }

        return sum;
    }

    public Map<String, Double> homogeneity(){
        var labelCounts = new HashMap<String, Double>();
        for (var value : this) {
            labelCounts.put(value.label, labelCounts.getOrDefault(value.label, 0d) + 1);
        }

        for (var label : labelCounts.keySet()) {
            labelCounts.put(label, labelCounts.get(label) * 100 / size());
        }

       return labelCounts;
    }

    public void printHomogeneity(){
        var homogeneity = homogeneity();

        for(var label : homogeneity.keySet()){
            System.out.printf("%s: %.2f%% \n", label, homogeneity.get(label));
        }
    }
}
