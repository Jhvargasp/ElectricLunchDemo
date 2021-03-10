package org.demo.electriclunch.exception;

public class MovementNotAllowedException extends Exception {

    public MovementNotAllowedException() {
        super("Movement not allowed");
    }
}
