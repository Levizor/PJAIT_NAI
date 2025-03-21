import java.util.Arrays;

public class Value {
    String label;
    public double[] vector = new double[26];

    public Value(double[] vector, String label) {
        this.vector = vector;
        this.label = label;
    }

    @Override
    public String toString() {
        return label + ": " + Arrays.toString(vector);
    }

    public static Value parseValue(String line) {
        try {
            int firstComa = line.indexOf(",");
            String text = line.substring(0, firstComa);
            String label = line.substring(firstComa + 1);
            double[] vectorVal = getLetterFrequencies(text);

            return new Value(vectorVal, label);
        } catch (Exception e) {
            return null;
        }
    }

    public static double[] getLetterFrequencies(String text) {
        double[] letterFrequencies = new double[26];
        int size = text.length();

        for (int i = 0; i < size; i++) {
            letterFrequencies[text.charAt(i)-'a']++;
        }

        for (int i = 0; i < 26; i++) {
            letterFrequencies[i] /= size;
        }

        return letterFrequencies;
    }
}
