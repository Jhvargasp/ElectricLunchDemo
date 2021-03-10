package org.demo.electriclunch.exception;

import org.demo.electriclunch.constants.Constants;

public class ExceedLoadException extends Exception {
    public ExceedLoadException() {
        super("The drone can´t handle the total lunches per fly: "
                + Constants.MAX_DELIVERIES_PER_FLY);
    }
}
