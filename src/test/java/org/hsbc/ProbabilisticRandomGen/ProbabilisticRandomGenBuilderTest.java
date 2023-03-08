//package org.hsbc.ProbabilisticRandomGen;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ProbabilisticRandomGenBuilderTest {
//    @Test
//    public void testSetRandom() {
//        Random random1 = new Random(1479);
//        List<ProbabilisticRandomGen.NumAndProbability> list = new ArrayList<>();
//        list.add(new ProbabilisticRandomGen.NumAndProbability(1, 0.1f));
//        ProbabilisticRandomGen prg1 = new ProbabilisticRandomGenBuilder(list)
//                //.addListEntry(new ProbabilisticRandomGen.NumAndProbability(1, 0.5f))
//                .setRandom(random1)
//                .build();
//
//
//    }
//
//    @Test
//    public void testNextFromSample() {
//        List<ProbabilisticRandomGen.NumAndProbability> list = new ArrayList<>();
//        list.add(new ProbabilisticRandomGen.NumAndProbability(1, 0.2f));
//        list.add(new ProbabilisticRandomGen.NumAndProbability(2, 0.3f));
//        list.add(new ProbabilisticRandomGen.NumAndProbability(3, 0.5f));
//        ProbabilisticRandomGen prg = new ProbabilisticRandomGenBuilder(list).build();
//
//        int count1 = 0;
//        int count2 = 0;
//        int count3 = 0;
//
//        for (int i = 0; i < 10000; i++) {
//            int r = prg.nextFromSample();
//            if (r == 1) count1++;
//            if (r == 2) count2++;
//            if (r == 3) count3++;
//        }
//
//        // Check that the number of occurrences of each number is roughly equal to its probability
//        assertTrue(Math.abs(count1 - 2000) < 100);
//        assertTrue(Math.abs(count2 - 3000) < 100);
//        assertTrue(Math.abs(count3 - 5000) < 100);
//    }
//
//}
