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
        random = new Random();
        probabilityList = new float[numAndProbabilities.size()];
        numberList = new int[numAndProbabilities.size()];
        sum = numberList.length;

        // Calculate the sum of all probabilities
        float probabilitySum = 0;
        for (int i = 0; i < numAndProbabilities.size(); i++) {
            probabilitySum += numAndProbabilities.get(i).getProbabilityOfSample();
        }
        // Normalize probabilities so that they sum to 1
        for (int i = 0; i < numAndProbabilities.size(); i++) {
            probabilityList[i] = numAndProbabilities.get(i).getProbabilityOfSample() / probabilitySum;
            numberList[i] = numAndProbabilities.get(i).getNumber();
        }

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
