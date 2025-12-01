package org.aoc;

import static java.lang.Math.abs;

public class Dial {

    private int position;
    private final int length;

    public Dial(int position, int length) {
        this.position = position;
        this.length = length;
    }

    public int setPosition(int delta) {

        int crossedZeroCount = abs(delta / length);

        // delta can be as positive, as negative, and exceed the length
        int reducedDelta = delta % length;
        if (this.position + reducedDelta >= length) {
            if(this.position + reducedDelta > length) {
                crossedZeroCount++;
            }
            this.position = this.position + reducedDelta - length;
        }
        else if( this.position + reducedDelta < 0) {
            if (this.position != 0) {
                crossedZeroCount++;
            }
            this.position = this.position + length + reducedDelta;
        }
        else {
            this.position = this.position + reducedDelta;
        }

        if(this.position >= length) {
            throw new IllegalArgumentException("Invalid position: " +  this.position);
        }

        return crossedZeroCount;
    }

    public int getPosition() {
        return this.position;
    }
}
