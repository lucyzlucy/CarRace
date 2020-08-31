import java.util.*;
import java.util.concurrent.*;

public class CarRaceProgram {

    public static final int NUM_CARS = 10;
    public static final int NUM_SECONDS = 20;
    public static final double SPEED_MIN = 5;
    public static final double SPEED_MAX = 350;

    public static CyclicBarrier cyclicBarrier;
    public static CountDownLatch intermediaryLatch;
    public static CountDownLatch finalLatch;

    public static List<Car> cars
            = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        CarRaceProgram race = new CarRaceProgram();
        race.runSimulation();
        race.printWinners();
    }

    public void runSimulation() {
        List<Runnable> racingCars
                = new ArrayList<>();
        cyclicBarrier = new CyclicBarrier(NUM_CARS, new IntermediaryResultsCheckpoint());
        finalLatch = new CountDownLatch(NUM_SECONDS * NUM_CARS);
        ExecutorService executor = Executors.newScheduledThreadPool(NUM_CARS);


        for (int i = 0; i < NUM_CARS; i++) {
            Runnable car = new RacingCar();
            racingCars.add(car);
        }

        for (int secondsCounter = 1; secondsCounter <= NUM_SECONDS; secondsCounter++) {
            for (Runnable car : racingCars) {
                executor.execute(car);
            }
            intermediaryLatch = new CountDownLatch(1);
            try {
                Thread.sleep(1000);
                intermediaryLatch.await();
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Second " + secondsCounter + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

                if (secondsCounter != NUM_SECONDS) {
                    printIntermediaryResults();
                }

            } catch (InterruptedException  e) {
                e.printStackTrace();
            }
        }

        try {
            finalLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    private void printWinners() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!FINISH!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Here are the results: ");
        for (int i = 0; i < 3; i++) {

            if (i < cars.size()) {
                System.out.println("---------------------------------------Position " + (i + 1) + "---------------------------------------");
                Car winner = cars.get(i);
                System.out.println(winner.getName());

                int counter = 1;
                for (ResultsPerSecondAggregator record : winner.getRecords()) {
                    System.out.print("Second " + counter + "; ");
                    record.printResults();
                    counter++;
                }
            }
        }
    }

    private void printIntermediaryResults() {

        System.out.println("Top 3 cars:");

       for (String result: IntermediaryResultsCheckpoint.intermediaryResults) {
                System.out.println(result);
            }
        }



}

