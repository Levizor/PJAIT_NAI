public class Main {
    public static void main(String[] args) {
        Config config = Config.fromArgs(args);

        KMean kMean = new KMean(config.train, config.k);
        int iteration = 1;

        while (kMean.performIteration()){
            System.out.printf("Iteration %d: %.2f\n", iteration, kMean.totalSum());
            iteration++;
        }
        System.out.println("\n\nInfo:\n\n");

        kMean.printInfo(false);
    }
}