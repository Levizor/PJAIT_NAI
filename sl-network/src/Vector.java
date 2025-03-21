public class Vector {

    public static double scalarProduct(double[] x, double[] y) {
        double result = 0.0;
        for (int i = 0; i < x.length; i++) {
            result += x[i] * y[i];
        }
        return result;
    }

    public static double[] addVector(double[] x, double[] y){
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            result[i] = x[i] + y[i];
        }
        return result;
    }

    public static double[] scaleVector(double[] x, double scalar){
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            result[i] = x[i] * scalar;
        }
        return result;
    }
}
