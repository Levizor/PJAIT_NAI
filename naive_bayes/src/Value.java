import java.util.Arrays;

public class Value {
    char label;
    public char[] attributes;

    public Value(char[] attributes, char label) {
        this.attributes = attributes;
        this.label = label;
    }

    @Override
    public String toString() {
        return label + ": " + Arrays.toString(attributes);
    }

    public static Value fromString(String s) {
        char label = s.charAt(0);
        char[] attributes = new char[s.length()-1];
        for (int i = 1; i < s.length(); i++) {
            attributes[i-1] = s.charAt(i);
        }

        return new Value(attributes, label);
    }
}
