import java.io.File;
import java.io.FileNotFoundException;

public class CliParser {
    double alpha;
    File train_set;
    File test_set;
    boolean inputVector = false;

    public CliParser() {}
    public void parse(String[] args) throws NumberFormatException, FileNotFoundException {
        if (args.length < 2) {
            System.out.println("Usage: perceptron <learning rate> <train_set> <<test_set>>");
            System.exit(0);
        }

        alpha = Double.parseDouble(args[0]);

        train_set = new File(args[1]);
        if(!train_set.exists()) {
            throw new FileNotFoundException(args[1]);
        }

        if (args.length > 2) {
            test_set = new File(args[2]);
            if(!test_set.exists()) {
                throw new FileNotFoundException(args[2]);
            }
        } else {
            inputVector = true;
        }
    }

    boolean isInputVector(){
        return inputVector;
    }

    double getAlpha(){
        return alpha;
    }

    File getTrainSet(){
        return train_set;
    }

    File getTestSet(){
        return test_set;
    }
}
