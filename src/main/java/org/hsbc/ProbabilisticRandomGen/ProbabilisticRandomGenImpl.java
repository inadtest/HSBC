package org.hsbc.ProbabilisticRandomGen;

import java.util.Random;

public class ProbabilisticRandomGenImpl implements ProbabilisticRandomGen {
    private final int[] numberList;
    private final float[] probabilityList;
    private final Random random;

    public ProbabilisticRandomGenImpl(int[] numbers, float[] probabilities, Random random) {
        this.probabilityList = probabilities;
        this.numberList = numbers;
        this.random = random;
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
