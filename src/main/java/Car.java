import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Car {
    private double currentSpeed;
    private double totalSpeed;
    private String name;
    private List<ResultsPerSecondAggregator> records = new ArrayList<>();

    public Car() {
        this.name = generateName();
    }

    public double getTotalSpeed() {
        return totalSpeed;
    }

    public void setSpeed(double carSpeed) {
        this.currentSpeed = carSpeed;
        totalSpeed += currentSpeed;
    }

    public String getName() {
        return name;
    }

    private String generateName() {
        Random r = new Random();
        return "Car" + r.nextInt(1234);
    }

    public List<ResultsPerSecondAggregator> getRecords() {
        return records;
    }

    public void setRecords(int position) {
        ResultsPerSecondAggregator results = new ResultsPerSecondAggregator(position, this.currentSpeed);
        this.records.add(results);
    }
}
