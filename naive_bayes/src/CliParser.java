import java.io.File;
import java.io.FileNotFoundException;

public class CliParser {
    File train_set;
    File test_set;
    boolean inputVector = false;

    public CliParser() {}
    public void parse(String[] args) throws NumberFormatException, FileNotFoundException {
        if (args.length < 1) {
            System.out.println("Usage: perceptron <learning rate> <train_set> <<test_set>>");
            System.exit(0);
        }


        train_set = new File(args[0]);
        if(!train_set.exists()) {
            throw new FileNotFoundException(args[0]);
        }

        if (args.length > 1) {
            test_set = new File(args[1]);
            if(!test_set.exists()) {
                throw new FileNotFoundException(args[1]);
            }
        } else {
            inputVector = true;
        }
    }

    boolean isInputVector(){
        return inputVector;
    }

    File getTrainSet(){
        return train_set;
    }

    File getTestSet(){
        return test_set;
    }
}
