package org.demo.electriclunch.util;

import org.demo.electriclunch.constants.Constants;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class DronesUtil {

    private static DronesUtil instance;

    private DronesUtil() {
    }

    private AtomicInteger threadsRunning = new AtomicInteger(0);

    public int increaseThreadsRunning (){
        return threadsRunning.addAndGet(1);
    }

    public int decreaseThreadsRunning (){
        return threadsRunning.decrementAndGet();
    }

    public int getThreadsRunning () {
        return threadsRunning.get();
    }


    public static synchronized DronesUtil getInstance() {
        if (instance == null) {
            instance = new DronesUtil();
        }
        return instance;
    }

    public String[] getFilesToBeProcessed() {
        File dir= new File(Constants.FOLDER_FILES);
        return dir.list((dir1, name) -> name.startsWith(Constants.INPUT_FILE_START_WITH));
    }

    public String getFileOutput(String fileIn) {
        return Constants.FOLDER_FILES + File.separator + fileIn.replaceAll("in","out");
    }

    public String getFileIn(String file) {
        return Constants.FOLDER_FILES + File.separator + file;
    }
}
