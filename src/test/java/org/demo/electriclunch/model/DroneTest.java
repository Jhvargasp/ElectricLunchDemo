package org.demo.electriclunch.model;

import org.demo.electriclunch.model.enumeration.Direction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DroneTest {

    @Test
    public void moveFwdTest() {
        Position expectedPosition = new Position(0, 1, Direction.NORTH);
        Drone drone = new Drone(1);
        drone.goForward();
        assertEquals(drone.getPosition(), expectedPosition);
    }

    @Test
    public void turnRightTest() {
        Position expectedPosition = new Position(0, 0, Direction.EAST);
        Drone drone = new Drone(1);
        drone.turnRight();
        assertEquals(drone.getPosition(), expectedPosition);
    }

    @Test
    public void turnLeftTest() {
        Position expectedPosition = new Position(0, 0, Direction.WEST);
        Drone drone = new Drone(1);
        drone.turnLeft();
        assertEquals(drone.getPosition(), expectedPosition);
    }

    @Test
    public void turnInCircleTest() {
        Position expectedPosition = new Position(0, 0, Direction.NORTH);
        Drone drone = new Drone(1);
        drone.turnRight();
        drone.turnRight();
        drone.turnRight();
        drone.turnRight();
        assertEquals(drone.getPosition(), expectedPosition);
    }

    @Test
    public void moveOutAndReturnTest() {
        Position expectedPosition = new Position(0, 0, Direction.NORTH);
        Drone drone = new Drone(1);
        drone.goForward();
        drone.goForward();
        //2N
        drone.turnRight();
        drone.turnRight();

        drone.goForward();
        drone.goForward();
        //0,S
        drone.turnRight();
        drone.turnRight();
        //0,N
        assertEquals(drone.getPosition(), expectedPosition);
    }
}