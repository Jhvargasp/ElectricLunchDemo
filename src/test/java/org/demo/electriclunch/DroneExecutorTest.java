package org.demo.electriclunch;

import org.demo.electriclunch.controller.DroneExecutor;
import org.demo.electriclunch.exception.MovementNotAllowedException;
import org.demo.electriclunch.exception.OutOfBoundsException;
import org.demo.electriclunch.model.Drone;
import org.demo.electriclunch.model.Position;
import org.demo.electriclunch.model.enumeration.Direction;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DroneExecutorTest {

    @Test
    public void successfulDeliveryTest() {
        try {
            Drone drone = new Drone(1);
            DroneExecutor droneExecutor = new DroneExecutor(drone);
            droneExecutor.act("AAAAIAAD");
            Position expectedPosition = new Position(-2, 4, Direction.NORTH);
            assertEquals(expectedPosition, droneExecutor.getDrone().getPosition());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = OutOfBoundsException.class)
    public void outOfBoundsTest() throws OutOfBoundsException, MovementNotAllowedException {
        Drone drone = new Drone(1);
        DroneExecutor droneExecutor = new DroneExecutor(drone);
        droneExecutor.act("AAAAAAAAAAA");
    }

    @Test(expected = MovementNotAllowedException.class)
    public void MovementNotAllowedTest() throws OutOfBoundsException, MovementNotAllowedException {
        Drone drone = new Drone(1);
        DroneExecutor droneExecutor = new DroneExecutor(drone);
        droneExecutor.act("AAADDIIL");
    }

    @Test(expected = MovementNotAllowedException.class)
    public void MovementNotAllowedCauseEmptyTest() throws OutOfBoundsException, MovementNotAllowedException {
        Drone drone = new Drone(1);
        DroneExecutor droneExecutor = new DroneExecutor(drone);
        droneExecutor.act("");
    }

    @Test
    public void processReadValidFileTest() {
        Drone drone = new Drone(1);
        DroneExecutor droneExecutor = new DroneExecutor(drone);

        List<Position> visitedPositions = droneExecutor.processInstructions(new File("src/test/resources/input.txt").getAbsolutePath());
        assertEquals(new Position(-2,4, Direction.WEST), visitedPositions.get(0));
        assertEquals(new Position(-1,3, Direction.SOUTH), visitedPositions.get(1));
        assertEquals(new Position(0,0, Direction.WEST), visitedPositions.get(2));
    }

    @Test
    public void processGenerateReportTest() throws FileNotFoundException {
        //Given
        Drone drone = new Drone(1);
        drone.setVisited(buildPositions());
        String outputFile = "outputTmp.txt";
        DroneExecutor droneExecutor = new DroneExecutor(drone);
        //When
        droneExecutor.buildDeliveriesReport(outputFile);
        //Then
        Scanner reader = new Scanner(new FileInputStream(outputFile));
        reader.nextLine();
        List<Position> pointsVisited = buildPositions();
        for (Position position : pointsVisited) {
            assertEquals(position.toString(), reader.nextLine());
        }
        //delete tmp file after complete test
        new File(outputFile).delete();
    }

    private List<Position> buildPositions() {
        List<Position> positionList = new ArrayList<>();
        positionList.add(new Position(-2,4, Direction.WEST));
        positionList.add(new Position(-1,3, Direction.SOUTH));
        positionList.add(new Position(0,0, Direction.WEST));
        return positionList;
    }
}