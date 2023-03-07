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
        numAndProbabilities.add(new ProbabilisticRandomGen.NumAndProbability(1, 0.3f));
        numAndProbabilities.add(new ProbabilisticRandomGen.NumAndProbability(2, 0.2f));
        numAndProbabilities.add(new ProbabilisticRandomGen.NumAndProbability(3, 0.5f));

        ProbabilisticRandomGen prg = ProbabilisticRandomGenFactory.create(numAndProbabilities);
        // Test that generated numbers are within expected range
        for (int i = 0; i < 1000; i++) {
            int randomNumber = prg.nextFromSample();
            assertTrue(randomNumber >= 1 && randomNumber <= 3);
        }
        int num1Count = 0, num2Count = 0, num3Count = 0;
        for (int i = 0; i < 10000; i++) {
            int randomNumber = prg.nextFromSample();
            switch (randomNumber) {
                case 1:
                    num1Count++;
                    break;
                case 2:
                    num2Count++;
                    break;
                case 3:
                    num3Count++;
                    break;
                default:
                    break;
            }
        }
        System.out.println(num1Count);
        assertEquals(3000, num1Count, 150); // allow for 10% variance
        assertEquals(2000, num2Count, 100);
        assertEquals(5000, num3Count, 250);

    }
}
