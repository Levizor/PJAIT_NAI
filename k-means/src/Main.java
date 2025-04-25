import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Config config = Config.fromArgs(args);

        System.out.println(config.k);
        System.out.println(config.train);

    }
}