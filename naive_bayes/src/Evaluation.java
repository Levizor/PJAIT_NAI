import java.util.Collection;
import java.util.List;

public class Evaluation {
    public int TP = 0;
    public int TN = 0;
    public int FP = 0;
    public int FN = 0;

    public double precision() {
        return TP/(double)(TP+FP);
    }

    public double recall() {
        return precision();
    }

    public double f_measure() {
        var precision = precision();
        var recall = recall();

        return 2*precision*recall / (double) (precision+recall);
    }

    public double accuracy() {
        return (TP+TN)/(double)(TP+FN+FP+TN);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TP: ").append(TP).append("\n");
        sb.append("FP: ").append(FP).append("\n");
        sb.append("FN: ").append(FN).append("\n");
        sb.append("TN: ").append(TN).append("\n");
        sb.append("Accuracy: ").append(String.format("%.2f%%", accuracy() * 100)).append("\n");
        sb.append("Precision: ").append(String.format("%.2f%%", precision() * 100)).append("\n");
        sb.append("Recall: ").append(String.format("%.2f%%", recall() * 100)).append("\n");
        sb.append("FMeasure: ").append(String.format("%.2f%%", f_measure() * 100)).append("\n");

        return sb.toString();
    }

}
