import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParser {
    public List<String> languages;
    public ArrayList<Value> values;

    public ArrayList<Value> parse(File file) throws Exception{
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            languages = new ArrayList<>();
            ArrayList<Value> values = new ArrayList<>(50);
            String line;
            while((line = br.readLine()) != null) {
                Value value = Value.parseValue(line);
                values.add(value);
            }
            this.values = values;
            return values;
        }
    }

}
