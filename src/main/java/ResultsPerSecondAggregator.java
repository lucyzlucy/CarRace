public class ResultsPerSecondAggregator {
    private int position;
    private double speed;

    public ResultsPerSecondAggregator(int position, double speed) {
        this.position = position;
        this.speed = speed;
    }

    public void printResults() {
        System.out.println("position: " + position + "; speed: " + speed);
    }

}
