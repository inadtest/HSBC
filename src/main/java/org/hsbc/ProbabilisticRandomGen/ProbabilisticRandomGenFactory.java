package org.hsbc.ProbabilisticRandomGen;

import java.util.List;

public class ProbabilisticRandomGenFactory {

    public static ProbabilisticRandomGen create(List<ProbabilisticRandomGen.NumAndProbability> numAndProbabilities) {
        return new ProbabilisticRandomGenImpl(numAndProbabilities);
    }
}
