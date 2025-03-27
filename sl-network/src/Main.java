public class Main {

    public static void main(String[] args) {
        Config config = Config.fromArgs(args);
        int epochs = 1000;

        SLNetwork slNetwork = new SLNetwork(config.languages, config.alpha);

        for (int i = 0; i < epochs; i++) {
            slNetwork.train(config.train);
        }

        var test = config.test;
        int correctCount = 0;
        for (Value value: test) {
            var prediction = slNetwork.predict(value.vector);
            if(prediction.equals(value.label)) {
                correctCount++;
            }else{
                System.out.println(value.label + ": " + prediction);
            }
        }

        System.out.println("Number of epochs: " + epochs);
        System.out.println("Number of tests: " + test.size());
        System.out.println("Number of correct results: " + correctCount);
        System.out.printf("Proportion: %.2f%%\n", correctCount / (double) test.size() * 100);
    }
}