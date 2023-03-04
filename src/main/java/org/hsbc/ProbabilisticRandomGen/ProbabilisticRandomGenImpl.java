package org.hsbc.ProbabilisticRandomGen;

import io.vavr.Tuple2;
import org.hsbc.ProbabilisticRandomGen.ProbabilisticRandomGen;

import java.util.List;
import java.util.Random;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class ProbabilisticRandomGenImpl implements ProbabilisticRandomGen {
    List<NumAndProbability> numAndProbabilities;
    private final Random random;

    public ProbabilisticRandomGenImpl(List<NumAndProbability> numAndProbabilities) {
        this.numAndProbabilities = List.copyOf(numAndProbabilities);
        this.random = new Random();
    }

    public ProbabilisticRandomGenImpl of(Tuple2<Integer, Float>... numAndProbabilities) {
        return new ProbabilisticRandomGenImpl(ofNullable(numAndProbabilities)
                .map(List::of)
                .orElse(emptyList())
                .stream()
                .map(tuple -> new NumAndProbability(tuple._1(), tuple._2()))
                .collect(toList()));
    }

    @Override
    public int nextFromSample() {
        for(NumAndProbability n : numAndProbabilities) {
            float randomFloat = random.nextFloat();
            randomFloat = n.getProbabilityOfSample();
        }
        return 0;
    }
}
