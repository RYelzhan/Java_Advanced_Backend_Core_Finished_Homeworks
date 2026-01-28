package com.epam.contest.lessonThree.futures;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {

    public static void main(String[] args) {
        System.out.println(fibonacci(0));
        System.out.println(fibonacci(1));
        System.out.println(fibonacci(2));
        System.out.println(fibonacci(100));
    }

    // Last item in list should not be greater than limit
    public static List<Integer> fibonacci(int limit) {

        List<Integer> fibNum = new ArrayList<>();
        int prev = 0;
        int next = 1;
        int temp;

        while(prev < limit){
            fibNum.add(prev);
            temp = next;
            next = prev + next;
            prev = temp;
        }
        return fibNum;
    }

}
