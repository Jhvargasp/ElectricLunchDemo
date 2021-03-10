package org.demo.electriclunch.model;


import org.demo.electriclunch.model.enumeration.Direction;

import java.util.ArrayList;
import java.util.List;

import static org.demo.electriclunch.model.enumeration.Direction.*;

public class Drone {


    private Position position;

    private int droneId;

    private List<Position> visited;

    public Drone(int droneId) {
        //the initial position is 0,0,N
        this.droneId = droneId;
        this.position  = new Position(0, 0, Direction.NORTH);
        this.visited = new ArrayList<>();
    }

    public Position getPosition() {
        return position;
    }

    public int getDroneId() {
        return droneId;
    }

    public List<Position> getVisited() {
        return visited;
    }

    public void setVisited(List<Position> visited) {
        this.visited = visited;
    }

    public void addPositionVisited() {
        this.visited.add(new Position(
                position.getX(),
                position.getY(),
                position.getDirection()));
    }

    public void goForward() {
        switch (position.getDirection()) {
            case NORTH:
                position.setY(position.getY() + 1);
                break;
            case EAST:
                position.setX(position.getX() + 1);
                break;
            case WEST:
                position.setX(position.getX() - 1);
                break;
            case SOUTH:
                position.setY(position.getY() - 1);
                break;
        }
    }

    public void turnRight() {
        switch (position.getDirection()) {
            case NORTH:
                position.setDirection(EAST);
                break;
            case EAST:
                position.setDirection(SOUTH);
                break;
            case WEST:
                position.setDirection(NORTH);
                break;
            case SOUTH:
                position.setDirection(WEST);
                break;
        }
    }

    public void turnLeft() {
        switch (position.getDirection()) {
            case NORTH:
                position.setDirection(WEST);
                break;
            case EAST:
                position.setDirection(NORTH);
                break;
            case WEST:
                position.setDirection(SOUTH);
                break;
            case SOUTH:
                position.setDirection(EAST);
                break;
        }
    }
}
