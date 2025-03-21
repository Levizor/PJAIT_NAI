import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SLNetwork {
    List<Perceptron> perceptrons;

    public SLNetwork(Set<String> languages, double alpha) {
        perceptrons = new ArrayList<>();
        languages.forEach(l -> perceptrons.add(new Perceptron(26, alpha, l)));
    }

    public void train(List<Value> values){
        perceptrons.forEach(perceptron -> perceptron.train(values));
    }

    public String predict(double[] letterFrequencies){
        var nets = perceptrons.stream().map(p -> p.net(letterFrequencies)).toList();
        var maxIndex = indexOfMax(nets);
        return perceptrons.get(maxIndex).label;

    }


    public <T extends Comparable> int indexOfMax(List<T> list){
        if (list.isEmpty()) return -1;
        if(list.size()==1) return 0;
        int max = 0;
        for(int i=1; i<list.size(); i++){
            if(list.get(i).compareTo(list.get(max)) > 0) max = i;
        }

        return max;
    }
}
