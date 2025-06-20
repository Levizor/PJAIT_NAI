//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Config config = Config.fromArgs(args);

        BayesClassifier classifier = new BayesClassifier(config.train);

        var evaluation = classifier.classify(config.test);

        System.out.println(evaluation);
    }
}