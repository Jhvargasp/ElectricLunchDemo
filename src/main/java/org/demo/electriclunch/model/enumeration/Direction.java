package org.demo.electriclunch.model.enumeration;

public enum Direction {
    NORTH("Norte"), SOUTH("Sur"), EAST("Oriente"), WEST("Occidente");

    private String literal;

    Direction(String literalDirection) {
        this.literal = literalDirection;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public String toString() {
        return literal;
    }
}
