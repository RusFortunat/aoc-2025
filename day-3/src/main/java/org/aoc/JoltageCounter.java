package org.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoltageCounter {

    /**
     * We have to find highest joltage by looking for the two digits, the sequence of which
     * produces a highest number
     * @param line
     * @return
     */
    public static long findHighestJoltage(String line) {

        List<Integer> highestDigits = new ArrayList<>();
        System.out.println("input: " + line);
        // first look for the highest digit in a length-1 sequence
        int highestDigit = 0;
        char[] digits = line.toCharArray();
        for (int i = 0; i < digits.length - 11; i++) {
            int digit = Integer.parseInt("" + digits[i]);
            if (digit > highestDigit) {
                highestDigit = digit;
            }
        }
        System.out.println("Highest digit is " + highestDigit);

        int firstInstanceOfHighestDigitPos = 0;
        for (int i = 0; i < digits.length - 11; i++) {
            int digit = Integer.parseInt("" + digits[i]);
            if (digit == highestDigit) {
                firstInstanceOfHighestDigitPos = i;
                break;
            }
        }
        highestDigits.add(highestDigit);

        int position = firstInstanceOfHighestDigitPos;
        int tempPosition = position + 1;
        for (int d = 0; d < 11; d++){
            int currentHighestDigit = 0;
            for (int pos = position + 1; pos < digits.length - 10 + d; pos++){
                int digit = Integer.parseInt("" + digits[pos]);
//                if(digit == highestDigit){
//                    currentHighestDigit = digit;
//                    tempPosition = pos;
//                    break;
//                }
                if (digit > currentHighestDigit) {
                    currentHighestDigit = digit;
                    tempPosition = pos;
                }
            }
            highestDigits.add(currentHighestDigit);
            position = tempPosition;
        }

        StringBuilder sequence = new StringBuilder();
        for(Integer digit : highestDigits){
            sequence.append(digit);
        }

        System.out.println("Joltage is " + sequence);

        return Long.parseLong(sequence.toString());
    }
}
