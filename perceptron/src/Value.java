import java.util.Arrays;

public class Value {
    int label;
    public double[] vector;

    public Value(double[] vector, int label) {
        this.vector = vector;
        this.label = label;
    }

    @Override
    public String toString() {
        return label + ": " + Arrays.toString(vector);
    }

}
