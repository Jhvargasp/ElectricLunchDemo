package org.demo.electriclunch.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.demo.electriclunch.constants.Constants;
import org.demo.electriclunch.exception.ExceedLoadException;
import org.demo.electriclunch.exception.MovementNotAllowedException;
import org.demo.electriclunch.exception.OutOfBoundsException;
import org.demo.electriclunch.model.Drone;
import org.demo.electriclunch.model.Position;
import org.demo.electriclunch.model.enumeration.PossibleMovements;
import org.demo.electriclunch.util.DronesUtil;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class DroneExecutor extends Thread {

    private final static Logger LOGGER = LogManager.getLogger(DroneExecutor.class);

    private Drone drone;

    private String inputFile;
    private String outputFile;

    public DroneExecutor(Drone drone) {
        this.drone = drone;
    }

    public DroneExecutor(Drone drone, String inputFile, String outputFile) {
        this.drone = drone;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }


    public Drone getDrone() {
        return this.drone;
    }

    public void act(String movement) throws OutOfBoundsException, MovementNotAllowedException {
        for (String character : movement.split("")) {
            PossibleMovements possibleMovements = getMovement(character);
            switch (possibleMovements) {
                case UP:
                    drone.goForward();
                    break;
                case LEFT:
                    drone.turnLeft();
                    break;
                case RIGHT:
                    drone.turnRight();
                    break;
            }
            if (!validatePosition(drone.getPosition())) {
                throw new OutOfBoundsException();
            }
        }
        drone.addPositionVisited();
        //LOGGER.info("Dron " + drone.getDroneId() + ": " + drone.getPosition());
    }

    private PossibleMovements getMovement(String singleLetter) throws MovementNotAllowedException {
        Optional<PossibleMovements> possibleMovement = Arrays.stream(PossibleMovements.values())
                .filter(x -> x.getLiteralMovement().equals(singleLetter)).findFirst();
        if (possibleMovement.isPresent()) {
            return possibleMovement.get();
        } else {
            throw new MovementNotAllowedException();
        }
    }

    private boolean validatePosition(Position position) {
        return position.getX() <= Constants.MAX_DISTANCE
                && position.getX() >= -Constants.MAX_DISTANCE
                && position.getY() <= Constants.MAX_DISTANCE
                && position.getY() >= -Constants.MAX_DISTANCE;
    }

    public List<Position> processInstructions(String instructionsFile) {
        String instructionLine;
        try {
            Scanner reader = new Scanner(new FileInputStream(instructionsFile));
            int numDeliveries = 0;

            while (reader.hasNext() ) {
                instructionLine = reader.nextLine();
                if( numDeliveries < Constants.MAX_DELIVERIES_PER_FLY) {
                    //LOGGER.info("Drone: "+drone.getDroneId()+" fileName: "+instructionsFile+", Processing: " + instructionLine);
                    act(instructionLine);
                    numDeliveries++;
                }
                else{
                    LOGGER.warn("Drone: "+drone.getDroneId()+" fileName: "+instructionsFile+
                            ", could not process: " + instructionLine);
                    throw new ExceedLoadException();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Dron " + drone.getDroneId() + ":" + e.getMessage());
        }
        return drone.getVisited();
    }

    public void buildDeliveriesReport(String reportFile) {
        PrintWriter writer = null;
        try {
            LOGGER.info("Drone deliveries complete: "+drone.getDroneId()+" generating file: "+reportFile);
            writer = new PrintWriter(reportFile);
            writer.println(Constants.REPORT_HEADER);
            for (Position position : drone.getVisited()) {
                writer.println(position.toString());
            }

        } catch (Exception ex) {
            LOGGER.error(ex);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    @Override
    public void run() {
        try {
            DronesUtil.getInstance().increaseThreadsRunning();
            LOGGER.info("Drone service started. "+drone.getDroneId());
            processInstructions(inputFile);
            buildDeliveriesReport(outputFile);
        } catch (Exception ex) {
            LOGGER.error(ex);
        } finally {
            LOGGER.info("Drone service complete. "+drone.getDroneId());
            DronesUtil.getInstance().decreaseThreadsRunning();
        }
    }
}
