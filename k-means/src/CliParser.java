import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Arrays;

public class CliParser {
    int k;
    File train_set;
    boolean inputVector = false;

    public CliParser() {}
    public void parse(String[] args) throws NumberFormatException, FileNotFoundException {
        if (args.length < 2) {
            System.out.println("Usage: k-means <k> <train_set>");
            System.exit(0);
        }

        k = Integer.parseInt(args[0]);

        train_set = new File(args[1]);
        if(!train_set.exists()) {
            throw new FileNotFoundException(args[1]);
        }

    }

    boolean isInputVector(){
        return inputVector;
    }

    int getK(){
        return k;
    }

    File getTrainSet(){
        return train_set;
    }

}
