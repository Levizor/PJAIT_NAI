import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CSVParser {

    public static ArrayList<Value> parse(File file) throws Exception{
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            ArrayList<Value> values = new ArrayList<>(50);
            String line;
            Integer dimension = null;
            int lineNumber = 1;
            while((line = br.readLine()) != null) {
                Value value = parseValue(line);
                if (value == null) {
                    throw new Exception("Couldn't parse line " + lineNumber + ": " + line);
                }
                if(dimension != null) {
                    if(value.vector.length!=dimension) {
                        throw new Exception("Dimension does not match previously defined on line " + lineNumber + ": %s".formatted(line));
                    }
                }else {
                    dimension = value.vector.length;
                }
                values.add(value);
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

            Value value = new Value(vectorVal, label);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

}
