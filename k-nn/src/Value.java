import java.util.Arrays;

public class Value {
    public double distance;
    public String label;
    public double[] vector;

    public Value(double[] vector, String label) {
        this.vector = vector;
        this.label = label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }

    @Override
    public String toString() {
        return label + ": " + Arrays.toString(vector);
    }

}
