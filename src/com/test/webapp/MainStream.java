package com.test.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {

    private static int minValue(int[] values) {
        IntStream stream = Arrays.stream(values);
        return stream.distinct().sorted().reduce(0, (x, y) -> x * 10 + y);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> collect = integers.stream().collect(Collectors.partitioningBy(o -> o % 2 == 0));
        return collect.get(collect.get(false).size() % 2 != 0);
    }

    public static void main(String[] args) {
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4)));
        System.out.println(minValue(new int[]{1, 2, 3, 3, 4}));
    }
}
