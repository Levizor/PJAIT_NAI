import java.util.Arrays;
import java.util.List;

// Is_versicolor
public class Perceptron {
    double[] weights;
    double bias;
    int dimension;
    double alpha = 0.01;

    public Perceptron(int dimension, double alpha) {
        this.dimension = dimension;
        this.alpha = alpha;
        this.weights = new double[dimension];
        for(int i = 0; i < dimension; i++) {
            weights[i] = Math.random();
        }
        this.bias = Math.random();
    }

    double net(double[] x){
        double product = scalarProduct(x, weights);
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
            apply_delta_rule(value.label, decision, value.vector);
        }
        );
    }

    void apply_delta_rule(int d, int y, double[] x){
        weights = addVector(weights, scaleVector(x,alpha*(d-y)));
        bias = bias - alpha*(d-y);
    }

    public boolean is_versicolor(Value x){
        return predict(x.vector)==1;
    }

    public boolean is_verginica(Value x){
        return !is_versicolor(x);
    }

    private static double scalarProduct(double[] x, double[] y) {
        double result = 0.0;
        for (int i = 0; i < x.length; i++) {
            result += x[i] * y[i];
        }
        return result;
    }

    private static double[] addVector(double[] x, double[] y){
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            result[i] = x[i] + y[i];
        }
        return result;
    }

    private static double[] scaleVector(double[] x, double scalar){
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            result[i] = x[i] * scalar;
        }
        return result;
    }
}
