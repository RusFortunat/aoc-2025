package org.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class WeirdCalculator {

    /**
     * The input is such, that the first three lines are the numbers,
     * and the last one contains operators
     * @param lines
     * @return
     */
    public static long doComputation(List<String> lines) {

        List<Integer> nums1 = Arrays.stream(lines.get(0).trim().split("\\s+"))
            .map(Integer::parseInt).toList();
        List<Integer> nums2 = Arrays.stream(lines.get(1).trim().split("\\s+"))
            .map(Integer::parseInt).toList();
        List<Integer> nums3 = Arrays.stream(lines.get(2).trim().split("\\s+"))
            .map(Integer::parseInt).toList();
        List<Integer> nums4 = Arrays.stream(lines.get(3).trim().split("\\s+"))
            .map(Integer::parseInt).toList();
        List<Character> operators = Arrays.stream(lines.getLast().trim().split("\\s+"))
            .map(s -> s.charAt(0)).toList();

        long result = 0;
        for (int i = 0; i < nums1.size(); i++) {
            long tempResult = 0;
            if (operators.get(i).compareTo('+') == 0) {
                tempResult += nums1.get(i) + nums2.get(i) + nums3.get(i) + nums4.get(i);
            }
            else if (operators.get(i).compareTo('*') == 0) {
                tempResult += (long) nums1.get(i) * nums2.get(i) * nums3.get(i) * nums4.get(i);
            }
            result += tempResult;
        }

        return result;
    }

    /**
     * Now the spaces matter, and the number is constructed by reading
     */
    public long doComputationPart2(List<String> lines) throws Exception {

        List<Problem> problems = new ArrayList<>();

        int longestLineSize = lines.stream().mapToInt(String::length).max().getAsInt();
        List<Long> nums = new ArrayList<>();
        for (int character = 0; character < longestLineSize; character++){

            String d1 = character < lines.get(0).length() ? lines.get(0).charAt(character) + "" : "";
            String d2 = character < lines.get(1).length() ? lines.get(1).charAt(character) + "" : "";
            String d3 = character < lines.get(2).length() ? lines.get(2).charAt(character) + "" : "";
            String d4 = character < lines.get(3).length() ? lines.get(3).charAt(character) + "" : "";
            String number = (d1 + d2 + d3 + d4).trim();

            if (!number.isEmpty()){
                nums.add(Long.parseLong(number));
            }
            else {
                // new problem begins
                Problem problem = new Problem(nums);
                problems.add(problem);
                nums =  new ArrayList<>();
            }
        }
        Problem problem = new Problem(nums);
        problems.add(problem);

        List<Character> operators = Arrays.stream(lines.getLast().trim().split("\\s+"))
            .map(s -> s.charAt(0)).toList();

        if(operators.size() != problems.size()){
            throw new Exception("Something went wrong during the parsing!");
        }

        long result = 0;
        for (int i = 0; i < problems.size(); i++) {

            long tempResult = 0;
            if (operators.get(i).compareTo('+') == 0) {
                tempResult = problems.get(i).getNumbers().stream().mapToLong(Long::valueOf).sum();
            }
            else if (operators.get(i).compareTo('*') == 0) {
                tempResult = problems.get(i).getNumbers().stream().reduce(1L, (a,b) -> a*b);
            }
            result += tempResult;
        }

        return result;
    }

    class Problem {
        private List<Long> numbers;
        public Problem(List<Long> nums){
            this.numbers = nums;
        }

        public List<Long> getNumbers(){
            return this.numbers;
        }
    }
}
