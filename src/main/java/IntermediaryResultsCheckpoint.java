import java.util.Comparator;

class IntermediaryResultsCheckpoint implements Runnable {
    public static String[] intermediaryResults;

    @Override
    public void run() {
        CarRaceProgram.cars.sort(Comparator.comparingDouble(Car::getTotalSpeed).reversed());

        intermediaryResults = new String[3];
        for (int i = 0; i < 3; i++) {
            if (i < CarRaceProgram.cars.size()) {
                Car preWinner = CarRaceProgram.cars.get(i);
                intermediaryResults[i] = (preWinner.getName());
            }
        }

        CarRaceProgram.intermediaryLatch.countDown();
    }
}


