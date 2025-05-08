import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Config {
    public ArrayList<Value> train;
    public int k;
    public int dimension;

    public Config(ArrayList<Value> train, int k, int dimension) {
        this.train = train;
        this.dimension = dimension;
        this.k = k;
    }

    public static Config fromArgs(String[] args) {
        CliParser cliParser = new CliParser();
        try {
            cliParser.parse(args);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Couldn't parse the number:" + e.getMessage());
        }

        int k = cliParser.getK();

        ArrayList<Value> train = null;
        int dimension = 0;

        try {
            train = CSVParser.parse(cliParser.getTrainSet());
            if (train.isEmpty()) {
                System.err.println("Train set is empty");
                System.exit(1);
            }
            dimension = train.getFirst().vector.length;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return new Config(train, k, dimension);
    }

    public static ArrayList<Value> addTestsInteractively(int dimension) {
        System.out.println("Adding tests interactively...");
        System.out.println("Enter q to finish. Enter vectors and their labels in this format:");
        System.out.println("v,v,v,v,label. Example: 5.1,3.5,1.4,0.2,Iris-setosa");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        ArrayList<Value> tests = new ArrayList<>();
        while(true) {
            System.out.print("-> ");
            String input = scanner.nextLine();
            if (input.isBlank()) {
                continue;
            }
            if(input.trim().equalsIgnoreCase("q")) {
                break;
            }

            Value value = CSVParser.parseValue(input);
            if(value == null) {
                System.out.println("Couldn't parse value: " + input);
                continue;
            }
            if (value.vector.length != dimension) {
                System.out.println("Error: vector length does not match dimension of the train set.\n");
                continue;
            }
            tests.add(value);
            System.out.printf("Added test value: %s \n\n", value);
        }

        return tests;
    }
}
