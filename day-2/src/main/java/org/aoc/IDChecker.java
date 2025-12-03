package org.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IDChecker {

    /**
     * We receive input in the form 123-321, and need to iterate over all
     * numbers to find "invalidIDs". ID is invalid, if the number can be split
     * into two repeating numbers, e.g., 11, 123123, etc.
     *
     * @param numberRange
     * @return countInvalidIDs
     */
    public static long countInvalidIDs(String numberRange) {
        System.out.println("Number range: " + numberRange);
        long start = Long.parseLong(numberRange.split("-")[0]);
        long end = Long.parseLong(numberRange.split("-")[1]);
        System.out.println("start: " + start);
        System.out.println("end: " + end);

        long sumOfInvalidIDs = 0;

        for (long i = start; i <= end; i++) {
            String id = Long.toString(i);
            int strLength = id.length();

            // first check (for the first star)
//            if (strLength % 2 == 0) {
//                if(id.substring(0, strLength/2).equals(
//                    id.substring(strLength/2))){
//                    sumOfInvalidIDs += Long.parseLong(id);
//                }
//            }

            // second check -- second part
            int nextHighestDivisor = strLength / 2;
            while (nextHighestDivisor > 0){
                if (strLength % nextHighestDivisor == 0) {
                    List<String> parts = new ArrayList<>();
                    int position = nextHighestDivisor;
                    while (position <=  strLength) {
                        parts.add(id.substring(position - nextHighestDivisor, position));
                        position += nextHighestDivisor;
                    }
                    boolean isValid = new HashSet<>(parts).size() <= 1; // are all equal?
                    if (isValid){
                        sumOfInvalidIDs += Long.parseLong(id);
                        break;
                    }
                }
                nextHighestDivisor--;
            }
        }

        return sumOfInvalidIDs;
    }

}
