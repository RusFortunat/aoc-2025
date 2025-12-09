package org.aoc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
public class NetworkProcesser {

    private List<Circuit> mergedCircuits;

    private static int MAX_CONNECTIONS = 1000; // 10 for test-input and 1000 for puzzle-input

    public NetworkProcesser() {
        mergedCircuits =  new ArrayList<>();
    }

    // idea is simple -- make all-to-all connections, sort them, leaving only MAX_CONNECTIONS
    // number, and then make circuits out of remaining connections
    public long makeCircuits(List<Vertex> vertices) {

        List<Circuit> circuits = new ArrayList<>();
        // i exclude double counting here
        for (int v1 = 0; v1 < vertices.size() - 1; v1++) {
            for (int v2 = v1 + 1; v2 < vertices.size(); v2++) {
                Circuit circuit = new Circuit(Set.of(vertices.get(v1), vertices.get(v2)),
                    distance(vertices.get(v1), vertices.get(v2)));
                circuits.add(circuit);
            }
        }

        // sort and leave only MAX_CONNECTIONS with min distance
        Comparator<Circuit> compareDistance = Comparator.comparingDouble(Circuit::getDistance);
        circuits = circuits.stream().sorted(compareDistance).limit(MAX_CONNECTIONS).toList();

        int[] excludedCircuits = new int[circuits.size()];

        for(int circ = 0; circ < circuits.size(); circ++) {
            if (excludedCircuits[circ] != 1) {

                Circuit circuit = circuits.get(circ);
                Set<Vertex> joinedVertices = new HashSet<>(circuit.getVertices());
                excludedCircuits[circ] = 1;

                boolean newCircuitFound = true;
                while (newCircuitFound) {
                    boolean circuitsMerged = false;
                    for (int circ2 = 1; circ2 < circuits.size(); circ2++) {

                        if (excludedCircuits[circ2] != 1) {
                            Circuit circuit2 = circuits.get(circ2);
                            boolean overlap = joinedVertices.stream().anyMatch(circuit2.getVertices()::contains);

                            if (overlap) {
                                joinedVertices.addAll(circuit2.getVertices());
                                circuitsMerged = true;
                                excludedCircuits[circ2] = 1;
                                break;
                            }
                        }
                    }
                    newCircuitFound = circuitsMerged;
                }

                Circuit mergedCircuit = new Circuit(joinedVertices, 0.0);
                mergedCircuits.add(mergedCircuit);
            }
        }

        mergedCircuits = mergedCircuits.stream()
            .sorted((a,b) ->b.getVertices().size() - a.getVertices().size()).toList();

        // find three largest
        List<Circuit> threeLargest = mergedCircuits.stream().limit(3).toList();
        System.out.println("Number of vertices in three largest circuits: ["
            + mergedCircuits.getFirst().getVertices().size() + ", "
            + mergedCircuits.get(1).getVertices().size() + ", "
            + mergedCircuits.get(2).getVertices().size() + "]");

        long product = 1;
        for (Circuit circuit : threeLargest) {
            product *= circuit.getVertices().size(); // for N vertices we need N-1 connections
        }

        return product;
    }

    // we need to connect all non-connected merged circuits into a single circuit
    public long connectThemAll(){

        // similar idea
        Circuit oneBigCircuit = new Circuit(Set.of(), 0.0);
        List<Circuit> copyOfMergedCircuits = new ArrayList<>(mergedCircuits);
        int[] excludedCircuits = new int[mergedCircuits.size()];

        for(int circ = 0; circ < mergedCircuits.size(); circ++) {

            if (excludedCircuits[circ] != 1) {
                Circuit circuit = mergedCircuits.get(circ);
                excludedCircuits[circ] = 1;

                double smallestDistance = 1000000;
                int closestCircuitIndex = -1;
                for (int circ2 = circ + 1; circ2 < mergedCircuits.size(); circ2++) {
                    if (excludedCircuits[circ2] != 1) {
                        Circuit circuit2 = mergedCircuits.get(circ2);

                        double closestDistance = findClosestLink(circuit, circuit2);
                        if(closestDistance < smallestDistance) {
                            smallestDistance = closestDistance;
                            closestCircuitIndex = circ2;
                        }
                    }
                }
                if (closestCircuitIndex != -1){
                    Set<Vertex> joinedVertices = new HashSet<>(oneBigCircuit.getVertices());
                    joinedVertices.addAll(circuit.getVertices());
                    Circuit closestCircuit = mergedCircuits.get(closestCircuitIndex);
                    joinedVertices.addAll(closestCircuit.getVertices());
                    oneBigCircuit = new Circuit(joinedVertices, 0.0);
                }

            }
        }

        return 0L;
    }

    private double findClosestLink(Circuit circuit, Circuit circuit2) {



    }


    private double distance(Vertex v1, Vertex v2) {
        long deltaX = Math.abs(v1.getX() - v2.getX());
        long deltaY = Math.abs(v1.getY() - v2.getY());
        long deltaZ = Math.abs(v1.getZ() - v2.getZ());
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    @Data
    @AllArgsConstructor
    private class Circuit {
        private Set<Vertex> vertices;
        private Double distance;
    }
}
