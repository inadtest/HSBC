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

        populate(numAndProbabilities);

        /*// Calculate the sum of all probabilities
        float probabilitySum = 0;
        for (int i = 0; i < numAndProbabilities.size(); i++) {
            probabilitySum += numAndProbabilities.get(i).getProbabilityOfSample();
        }
//System.out.println(probabilitySum);
        // Normalize probabilities so that they sum to 1
        for (int i = 0; i < numAndProbabilities.size(); i++) {
            numberList[i] = numAndProbabilities.get(i).getNumber();
            probabilityList[i] = numAndProbabilities.get(i).getProbabilityOfSample() / probabilitySum;
            System.out.println(probabilityList[i]);

        }*/
    }

    private void populate(List<NumAndProbability> numAndProbabilities) {
        float probabilitySum = (float) numAndProbabilities.stream().mapToDouble(NumAndProbability::getProbabilityOfSample).sum();
        for (int i = 0; i < numAndProbabilities.size(); i++) {
            numberList[i] = numAndProbabilities.get(i).getNumber();
            // dividing this by sum, so sum of all probabilities is 1
            probabilityList[i] = numAndProbabilities.get(i).getProbabilityOfSample() / probabilitySum;
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
