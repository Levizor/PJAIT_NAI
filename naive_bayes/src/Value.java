import java.util.Arrays;

public class Value {
    public String label;
    public String[] attributes;

    public Value(String[] attributes, String label) {
        this.attributes = attributes;
        this.label = label;
    }

    @Override
    public String toString() {
        return label + ": " + Arrays.toString(attributes);
    }

    public static Value fromString(String s) {
        String[] tokens = s.split(",");
        String label = tokens[0];
        String[] attributes = new String[tokens.length-1];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = tokens[i+1];
        }

        return new Value(attributes, label);
    }
}
