import java.util.Arrays;

public class Value {
    String label;
    double distance;
    public double[] vector;

    public Value(double[] vector, String label) {
        this.vector = vector;
        this.label = label;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return label + ": " + Arrays.toString(vector);
    }

}
