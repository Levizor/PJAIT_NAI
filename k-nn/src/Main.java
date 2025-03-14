import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
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
        ArrayList<Value> test = null;

        try {
            train = CSVParser.parse(cliParser.getTrainSet());
            if (train.isEmpty()) {
                System.err.println("Train set is empty");
                System.exit(1);
            }
            if(cliParser.isInputVector()) {
                test = addTestsInteractively(train.getFirst().vector.length);
            } else {
                test = CSVParser.parse(cliParser.getTestSet());
            }
            if(train.getFirst().vector.length!=test.getFirst().vector.length) {
                throw new Exception("Dimensions of train and test set do not match");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        int correctCount = 0;

        for (Value value: test) {
            var kclosest = getKclosest(k, train, value);
            var maxCount = findMaxCount(kclosest);
            if (value.label.equals(maxCount)) {
                correctCount++;
            }
        }

        System.out.println("Number of tests: " + test.size());
        System.out.println("Number of correct results: " + correctCount);
        System.out.println("Proportion: %.2f%%".formatted(correctCount / (double) test.size() * 100));
    }

    public static ArrayList<Value> getKclosest(int k, ArrayList<Value> train, Value test ) {

        for (Value train_value: train) {
            train_value.setDistance(calc_distance(train_value, test));
        }


        train.sort(Comparator.comparingDouble(v -> v.distance));

        var result = new ArrayList<Value>();

        for(int i=0; i<Math.min(train.size(), k); i++) {
            result.add(train.get(i));
        }

        return result;
    }

    public static String findMaxCount(ArrayList<Value> values) {
        HashMap<String, Integer> counts = new HashMap<>();
        for (Value value: values) {
            counts.put(value.label, counts.getOrDefault(value.label, 0) + 1);
        }

        int maxCount = 0;
        String maxLabel = null;

        for (String label: counts.keySet()) {
            if (counts.get(label) > maxCount) {
                maxCount = counts.get(label);
                maxLabel = label;
            }
        }

        return maxLabel;
    }

    public static double calc_distance(Value a, Value b) {
        double sum = 0;
        for (int i=0; i<a.vector.length; i++) {
            sum += Math.pow(a.vector[i] - b.vector[i], 2 );
        }
        return Math.sqrt(sum);
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
            System.out.println("Added test value: %s \n".formatted(value));
        }

        return tests;
    }

}
