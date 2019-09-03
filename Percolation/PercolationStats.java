import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private static final double COEFFICIENT = 1.96;
    private final double[] results;
    private final double meanValue;
    private final double stddevValue;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            }

            results[i] = ((double) p.numberOfOpenSites()) / (n * n);
        }
        meanValue = StdStats.mean(results);
        stddevValue = StdStats.stddev(results);

    }

    public double mean() {
        return meanValue;
    }

    public double stddev() {
        return stddevValue;
    }

    public double confidenceLo() {
        return meanValue - (COEFFICIENT * stddevValue / Math.sqrt(results.length));
    }

    public double confidenceHi() {
        return meanValue + (COEFFICIENT * stddevValue / Math.sqrt(results.length));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.printf("mean = %f\n", ps.mean());
        System.out.printf("stddev = %f\n", ps.stddev());
        System.out.printf("95 confidence interval = [%f, %f]\n", ps.confidenceLo(), ps.confidenceHi());
    }
}
