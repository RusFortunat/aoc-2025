package org.aoc;

import java.util.*;

public class BeamSplitter {

    private List<List<Integer>> beams;
    private List<String> puzzleInput;

    public BeamSplitter(int inputSize, List<String> puzzleInput) {
        this.beams = new ArrayList<>();
        for (int i = 0; i < inputSize - 1; i++) {
            List<Integer> beamLine = new ArrayList<>();
            beams.add(beamLine);
        }

        this.puzzleInput = puzzleInput;
    }

    /**
     * The splitting logic is such, that when the beam "|" encounters the splitter "^"
     * on the line below it, the beam is split into two
     * @return
     */
    public int countBeamSplits() {

        int startPosition = puzzleInput.getFirst().indexOf('S');

        beams.getFirst().add(startPosition);

        int beamSplits = 0;
        for (int row = 2; row < puzzleInput.size(); row++) {
            Set<Integer> newBeamsList = new HashSet<>();
            String line = puzzleInput.get(row);
            for (Integer beam : beams.get(row - 2)) {
                // we assume that beam splitters are not placed right next to each other line this: ^^
                if (line.charAt(beam) == '^') {
                    beamSplits++;
                    int leftBeam = beam > 0 ? beam - 1 : -1;
                    if (leftBeam != -1) {
                       newBeamsList.add(leftBeam);
                    }
                    int rightBeam = beam < line.length() - 1 ? beam + 1 : -1;
                    if (rightBeam != -1) {
                        newBeamsList.add(rightBeam);
                    }
                } else {
                    newBeamsList.add(beam);
                }
            }
            beams.get(row - 1).addAll(newBeamsList); // replace the list with next beams row indices
        }

        return beamSplits;
    }

    /**
     * Let's go with a recursive approach first -> Well, that was a mistake. Test input worked, but the puzzle got stuck
     * Le'ts try using memorization now
     * @return
     */
    public long countTimelines() {

        Map<Integer, Long> coefficients = new HashMap<>();
        for (int i = 0; i < puzzleInput.getFirst().length(); i++){
            coefficients.put(i, 0L);
        }
        coefficients.put(beams.getFirst().getFirst(), 1L);

        // count number of times a path crosses the position
        for (int row = 0; row < beams.size(); row++) {
            for (Integer beamPosition : coefficients.keySet()) {
                if(row + 2 < puzzleInput.size()) {
                    if (puzzleInput.get(row + 2).charAt(beamPosition) == '^') {
                        long incomingCoeff = coefficients.get(beamPosition);
                        if (beamPosition - 1 >= 0) {
                            long currentCoeffLeft = coefficients.get(beamPosition - 1);
                            coefficients.put(beamPosition - 1, currentCoeffLeft + incomingCoeff);
                        }
                        if(beamPosition + 1 < puzzleInput.getFirst().length()) {
                            long currentCoeffRight = coefficients.get(beamPosition + 1);
                            coefficients.put(beamPosition + 1, currentCoeffRight + incomingCoeff);
                        }
                        coefficients.put(beamPosition, 0L);
                    }
                }
            }
        }

        // sum all coefficients to get the timeline paths count
        long timelines = coefficients.values().stream().mapToLong(Long::longValue).sum();
        return timelines;
    }

    // recursive approach that breaks on the puzzle input
    private int countPaths(int row, int beamPosition) {

        int countPossiblePaths = 0;

        // row counter is -1 behind the puzzle input row index, because i skipped the first line in puzzle input
        if (row + 1 < beams.size()) {
            // splitter
            if(puzzleInput.get(row + 2).charAt(beamPosition) == '^') {
                countPossiblePaths++;

                countPossiblePaths += countPaths(row + 1, beamPosition - 1);
                countPossiblePaths += countPaths(row + 1, beamPosition + 1);

            } else {
                countPossiblePaths += countPaths(row + 1, beamPosition);
            }
        }

        return countPossiblePaths;
    }
}
