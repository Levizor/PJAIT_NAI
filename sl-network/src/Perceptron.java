import java.util.List;

public class Perceptron {
    String label;
    double[] weights;
    double bias;
    int dimension;
    double alpha = 0.01;

    public Perceptron(int dimension, double alpha, String label) {
        this.dimension = dimension;
        this.alpha = alpha;
        this.weights = new double[dimension];
        for(int i = 0; i < dimension; i++) {
            weights[i] = Math.random();
        }
        this.bias = Math.random();
        this.label = label;
    }

    public double net(double[] x){
        double product = Vector.scalarProduct(x, weights);
        double result = product - bias;
        return result;
    }

    public int predict(double[] x){
        double net = net(x);

        if(net>=0){
            return 1;
        }else{
            return 0;
        }
    }

    public void train(List<Value> values){
        values.forEach(value -> {
            int decision = predict(value.vector);
            apply_delta_rule(value.label.equals(label) ? 1 : 0, decision, value.vector);
        }
        );
    }

    void apply_delta_rule(int d, int y, double[] x){
        weights = Vector.addVector(weights, Vector.scaleVector(x,alpha*(d-y)));
        bias = bias - alpha*(d-y);
    }
}
