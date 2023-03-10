package org.hsbc.ProbabilisticRandomGen;

import java.util.List;
import java.util.Random;

public class ProbabilisticRandomGenImpl implements ProbabilisticRandomGen {
    private final int[] numberList;
    private final float[] probabilityList;
    private final Random random;
    private final int sum;

    public ProbabilisticRandomGenImpl(int[] numbers, float[] probabilities, Random random) {
        this.probabilityList = probabilities;
        this.numberList = numbers;
        this.random = random;
        this.sum = 0;
    }

    public ProbabilisticRandomGenImpl(List<NumAndProbability> numAndProbabilities) {
        if(numAndProbabilities == null || numAndProbabilities.size() == 0)
            throw new IllegalArgumentException("Input list is not valid");
        random = new Random();
        probabilityList = new float[numAndProbabilities.size()];
        numberList = new int[numAndProbabilities.size()];
        sum = 0;
       // populate(numAndProbabilities);
        float totalProbability = 0f;
        for (int i = 0; i < numAndProbabilities.size(); i++) {
            numberList[i] = numAndProbabilities.get(i).getNumber();
            probabilityList[i] = numAndProbabilities.get(i).getProbabilityOfSample();
            totalProbability += probabilityList[i];
        }
        if(totalProbability <= 0f)
            throw new IllegalArgumentException ("Total probability cannot be less than 0");
    }

    @Override
    public int nextFromSample() {
        float sum = 0;
        float rand = random.nextFloat();
        for(int i = 0; i < probabilityList.length; i++) {
            sum += probabilityList[i];
            if(rand < sum)
                return numberList[i];
        }
        return 0;
    }
}
