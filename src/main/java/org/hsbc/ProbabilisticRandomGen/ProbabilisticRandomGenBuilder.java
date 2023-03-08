//package org.hsbc.ProbabilisticRandomGen;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class ProbabilisticRandomGenBuilder {
//    private List<ProbabilisticRandomGen.NumAndProbability> numAndProbabilities;
//    private Random random;
//
//    public ProbabilisticRandomGenBuilder(List<ProbabilisticRandomGen.NumAndProbability> numAndProbabilities) {
//        this.numAndProbabilities = numAndProbabilities;
//        this.random = new Random();
//    }
///*
//    public ProbabilisticRandomGenBuilder addListEntry(ProbabilisticRandomGen.NumAndProbability numAndProbability) {
//        this.numAndProbabilities.add(numAndProbability);
//        return this;
//    }*/
//
//    public ProbabilisticRandomGenBuilder setRandom(Random random1) {
//        this.random = random;
//        return this;
//    }
//
//    public ProbabilisticRandomGen build() {
//        float[] probabilities = new float[numAndProbabilities.size()];
//        int[] numbers = new int[numAndProbabilities.size()];
//        for (int i = 0; i < numAndProbabilities.size(); i++) {
//            probabilities[i] = numAndProbabilities.get(i).getProbabilityOfSample();
//            numbers[i] = numAndProbabilities.get(i).getNumber();
//        }
//        return new ProbabilisticRandomGenImpl(numbers, probabilities, random);
//    }
//}
