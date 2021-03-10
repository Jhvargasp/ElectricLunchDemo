package org.demo.electriclunch.model.enumeration;


public enum PossibleMovements {
    UP("A"), LEFT("I"), RIGHT("D");

    private String literalMovement;

    PossibleMovements(String literalMovement) {
        this.literalMovement = literalMovement;
    }

    public String getLiteralMovement() {
        return literalMovement;
    }

    @Override
    public String toString() {
        return "PossibleMovements{" +
                "letter='" + literalMovement + '\'' +
                '}';
    }
}
