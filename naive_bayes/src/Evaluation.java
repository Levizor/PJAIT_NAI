import java.util.Collection;
import java.util.List;

public abstract class Evaluation {
    public static double precision(int TP, int FP) {
        return TP/(double)(TP+FP);
    }

    public static double recall(int TP, int FN) {
        return precision(TP, FN);
    }

    public static double f_measure(int TP, int FN, int FP) {
        var precision = precision(TP, FP);
        var recall = recall(TP, FN);

        return 2*precision*recall / (double) (precision+recall);
    }

    public static double accuracy(int TP, int FN, int FP, int TN) {
        return TP+TN/(double)(TP+FN+FP+TN);
    }

}
