package org.demo.electriclunch.exception;

import org.demo.electriclunch.constants.Constants;

public class OutOfBoundsException extends Exception {

    public OutOfBoundsException() {
        super("Drone is out of bounds: " + Constants.MAX_DISTANCE);
    }
}
