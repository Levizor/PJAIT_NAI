import java.util.Arrays;

public class Value {
    String label;
    double distance;
    public double[] vector;

    public Value(double[] vector, String label) {
        this.vector = vector;
        this.label = label;
    }

    public Value(double[] vector) {
        this.vector = vector;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return label + ": " + Arrays.toString(vector);
    }

    static double sumOfDistanceDiffSquared(Value v1, Value v2) {
        var vec1 = v1.vector;
        var vec2 = v2.vector;

        double sum = 0;

        for (int i = 0; i < vec1.length; i++) {
            sum += (Math.pow(vec1[i] - vec2[i], 2));
        }

        return sum;
    }

    double diffSquared(Value v2) {
        return sumOfDistanceDiffSquared(this, v2);
    }

    Value copy(){
        return new Value(vector.clone());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Value)) {
            return false;
        }

        Value other = (Value) obj;

        return Arrays.equals(vector, other.vector);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vector);
    }

    public String getLabel() {
        return label;
    }

}
