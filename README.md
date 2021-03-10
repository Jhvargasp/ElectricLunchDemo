# Drones for deliveries
Demo test associated with coding test for S4N

The goal is to use 'drones' to deliver lunches in some specific space area, using a file to read instructions to
move the drones.  

Each file has its own instruction for each lunch that must to be delivered.

There are some restrictions, those are
1. There is a limit of drones available (those can be modified into Constants.QUANTITY_DRONES)
2. There is a limit of lunches that the drone can carry up (this value  can be modified into Constants.MAX_DELIVERIES_PER_FLY)
3. There are just only 3 valid movements - instructions in the file A (move forward), I (turn to the left), D (turn to the right)
4. Internally the app is using threads for each drone 
4. Files with instructions must to be located in the folder /files located in the project folder

Technologies used:
-----------------------
1. JDK 8
2. Maven
3. Junit
4. Log4j

Steps to run and test the application:
-----------------------

1. Download the code using: git clone
2. Run the command: `mvn clean compile`
3. Run the command: `mvn clean test`
4. Run command: `mvn exec:java -Dexec.mainClass="org.demo.electriclunch.Main"`