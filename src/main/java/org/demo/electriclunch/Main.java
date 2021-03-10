package org.demo.electriclunch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.demo.electriclunch.constants.Constants;
import org.demo.electriclunch.controller.DroneExecutor;
import org.demo.electriclunch.model.Drone;
import org.demo.electriclunch.util.DronesUtil;

public class Main {

    private final static Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String args[]) {
        try {
            DronesUtil instance = DronesUtil.getInstance();
            String[] files = instance.getFilesToBeProcessed();

            //create and start threads for each drone based on input files
            for (int i = 0; i < files.length; i++) {
                Drone drone = new Drone(i + 1);

                String fileIn = instance.getFileIn(files[i]);
                String fileOut = instance.getFileOutput(files[i]);

                DroneExecutor droneExecutor = new DroneExecutor(drone, fileIn, fileOut);
                droneExecutor.start();

                boolean waitToRelease = true;
                while (waitToRelease) {
                    if (instance.getThreadsRunning() >= Constants.QUANTITY_DRONES) {
                        LOGGER.warn("Waiting for a drone to return... no enough drones..");
                        Thread.sleep(100);
                    } else {
                        waitToRelease = false;
                    }
                }

            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
