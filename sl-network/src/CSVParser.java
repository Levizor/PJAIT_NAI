import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class CSVParser {
    public Set<String> languages;

    public ArrayList<Value> parse(File file) throws Exception{
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            languages = new HashSet<>();
            ArrayList<Value> values = new ArrayList<>(50);
            String line;
            while((line = br.readLine()) != null) {
                Value value = Value.parseValue(line);
                if(value!=null) {
                    values.add(value);
                    languages.add(value.label);
                }
            }
            return values;
        }
    }

}
