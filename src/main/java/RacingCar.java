import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

class RacingCar implements Runnable {
    private Car car;

    public RacingCar() {
        this.car = new Car();
        CarRaceProgram.cars.add(car);
    }

    @Override
    public void run() {

        generateCarSpeed();

        try {
            CarRaceProgram.cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        recordCarResults();

        CarRaceProgram.finalLatch.countDown();
    }

    private void generateCarSpeed() {
        Random r = new Random();
        double carSpeed = Math.round((CarRaceProgram.SPEED_MIN + (CarRaceProgram.SPEED_MAX - CarRaceProgram.SPEED_MIN) * r.nextDouble())* 100.0) / 100.0;
        car.setSpeed(carSpeed);
    }


    private void recordCarResults() {
        int position = CarRaceProgram.cars.indexOf(car) + 1;
        car.setRecords(position);
    }


}
