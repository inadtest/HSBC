package org.hsbc.ProbabilisticRandomGen;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProbabilisticRandomGenTest {
    @Test
    public void testProbabilisticRandomGen() {
        List<ProbabilisticRandomGen.NumAndProbability> numAndProbabilities = new ArrayList<>();
        numAndProbabilities.add(new ProbabilisticRandomGen.NumAndProbability(1, 0.2f));
        numAndProbabilities.add(new ProbabilisticRandomGen.NumAndProbability(2, 0.3f));
        numAndProbabilities.add(new ProbabilisticRandomGen.NumAndProbability(3, 0.5f));

        ProbabilisticRandomGen probabilisticRandomGen = ProbabilisticRandomGenFactory.create(numAndProbabilities);
        // Test that generated numbers are within expected range
        for (int i = 0; i < 1000; i++) {
            int randomNumber = probabilisticRandomGen.nextFromSample();
            assertTrue(randomNumber >= 1 && randomNumber <= 3);
        }
        int num1Count = 0, num2Count = 0, num3Count = 0;
        for (int i = 0; i < 10000; i++) {
            int randomNumber = probabilisticRandomGen.nextFromSample();
            if (randomNumber == 1) {
                num1Count++;
            } else if (randomNumber == 2) {
                num2Count++;
            } else if (randomNumber == 3) {
                num3Count++;
            }
        }

        assertEquals(2000, num1Count, 200);
        assertEquals(3000, num2Count, 200);
        assertEquals(5000, num3Count, 200);

    }
}
