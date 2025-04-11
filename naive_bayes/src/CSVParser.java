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
                Value value = Value.fromString(line);
                if(value != null) {
                    if(dimension != null) {
                        if(value.attributes.length!=dimension) {
                            throw new Exception("Dimension does not match previously defined on line " + lineNumber + ": %s".formatted(line));
                        }
                    }else {
                        dimension = value.attributes.length;
                    }

                    values.add(value);
                }
                lineNumber++;
            }
            return values;

        }
    }
}
