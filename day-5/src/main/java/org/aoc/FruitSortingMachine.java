package org.aoc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FruitSortingMachine {

    public Results findFreshFruits(List<Stick> sticks, List<Long> ids) throws Exception {

        // let's sort the array first
        Comparator<Stick> longComparator = Comparator.comparingLong(Stick::getPosition);
        List<Stick> sortedSticks = sticks.stream().sorted(longComparator).toList();
        System.out.println("Sorted sticks: " + sortedSticks);

        List<Stick> mergedSticks = mergeSticks(sortedSticks);
        System.out.println("Merged sticks: " + mergedSticks);

        int countFreshFruits = 0;
        for (Long id : ids) {
            boolean found = mergedSticks.stream().anyMatch(stick ->
                stick.getPosition() <= id && stick.getPosition() + stick.getLength() >= id);
            if (found) {
                countFreshFruits++;
            }
        }

        // part two, i'm lazy af
        Long totalIDsRange = mergedSticks.stream().mapToLong(Stick::getLength).sum() + mergedSticks.size();

        return new Results(countFreshFruits, totalIDsRange);
    }

    // The approach is the following, i get the first stick from sorted list,
    // and merge all overlapping ones, then repeat
    private List<Stick> mergeSticks(List<Stick> sortedSticks) throws Exception {

        List<Stick> mergedSticks =  new ArrayList<>();

        long startPosition = sortedSticks.getFirst().getPosition();
        long endPosition = startPosition + sortedSticks.getFirst().getLength();

        for (int stick = 1; stick < sortedSticks.size(); stick++) {

            long stickPosition = sortedSticks.get(stick).getPosition();
            if (stickPosition < startPosition) {
                throw new Exception("Stick position inside the loop cannot be smaller than the start position!");
            }

            // stick lays outside the current start-end range: put new merged stick and start a new one
            if (stickPosition > endPosition) {
                long length = endPosition - startPosition;
                Stick mergedStick = new Stick(startPosition, length);
                mergedSticks.add(mergedStick);

                startPosition = stickPosition;
                endPosition = stickPosition + sortedSticks.get(stick).getLength();
                continue;
            }

            // stick is within the current start-end range
            long endStickPosition = stickPosition + sortedSticks.get(stick).getLength();
            if (endStickPosition > endPosition) {
                endPosition =  endStickPosition;
            }
        }
        Stick mergedStick = new Stick(startPosition, endPosition -  startPosition);
        mergedSticks.add(mergedStick);

        return mergedSticks;
    }

    @Getter
    @RequiredArgsConstructor
    public class Results {
        private final int freshFruits;
        private final Long totalIDsRange;
    }
}
