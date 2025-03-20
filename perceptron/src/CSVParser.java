import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVParser {

    public static ArrayList<Value> parse(File file) throws Exception{
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            ArrayList<Value> values = new ArrayList<>(50);
            String line;
            Integer dimension = null;
            int lineNumber = 1;
            while((line = br.readLine()) != null) {
                Value value = parseValue(line);
                if(value != null) {
                    if(dimension != null) {
                        if(value.vector.length!=dimension) {
                            throw new Exception("Dimension does not match previously defined on line " + lineNumber + ": %s".formatted(line));
                        }
                    }else {
                        dimension = value.vector.length;
                    }

                    values.add(value);
                }
                lineNumber++;
            }
            return values;

        }
    }
    public static Value parseValue(String line) {
        try {
            int lastComma = line.lastIndexOf(",");
            String vector = line.substring(0, lastComma);
            String label = line.substring(lastComma + 1);
            double[] vectorVal = Arrays.stream(vector.split(",")).map(String::trim).mapToDouble(Double::parseDouble).toArray();

            int labelValue = label.equalsIgnoreCase("Iris-versicolor") ? 1 : 0;

            return new Value(vectorVal, labelValue);
        } catch (Exception e) {
            return null;
        }
    }

}
