package com.orient.metamodel.dijkstra;

import java.util.HashMap;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-15 9:34
 */
public class DistanceTest {

    public static void main(String[] args) {
        HashMap<Integer, HashMap<Integer, Integer>> stepLength = new HashMap<>();
        HashMap<Integer, Integer> step1 = new HashMap<>();
        stepLength.put(1, step1);
        step1.put(6, 1);
        step1.put(3, 1);
        step1.put(2, 1);

        HashMap<Integer, Integer> step2 = new HashMap<>();
        stepLength.put(2, step2);
        step2.put(1, 1);
        step2.put(3, 1);
        step2.put(4, 1);

        HashMap<Integer, Integer> step3 = new HashMap<>();
        stepLength.put(3, step3);
        step3.put(1, 1);
        step3.put(2, 1);
        step3.put(4, 1);
        step3.put(6, 1);

        HashMap<Integer, Integer> step4 = new HashMap<>();
        stepLength.put(4, step4);
        step4.put(2, 1);
        step4.put(5, 1);
        step4.put(3, 1);

        HashMap<Integer, Integer> step5 = new HashMap<>();
        stepLength.put(5, step5);
        step5.put(6, 1);
        step5.put(4, 1);

        HashMap<Integer, Integer> step6 = new HashMap<>();
        stepLength.put(6, step6);
        step6.put(1, 1);
        step6.put(5, 1);
        step6.put(3, 1);

        Distance distance = new DistanceDijkstraImpl();
        MinStep step = distance.getMinStep(1, 5, stepLength);
        System.err.println("hello");
    }
}
