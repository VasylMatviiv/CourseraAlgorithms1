import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double COF = 1.96;
    private final double[] x;
    private final int experiments;
    private double stddev;
    private double mean;

    public PercolationStats(int n, int trials) {
        experiments = trials;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Given n <= 0 || trials <= 0");
        }

        x = new double[experiments];
        for (int t = 0; t < experiments; t++) {
            int numOfOpens;
            Percolation pc = new Percolation(n);
            while (!pc.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!pc.isOpen(i, j)) {
                    pc.open(i, j);
                }
            }
            numOfOpens = pc.numberOfOpenSites();
            x[t] = (double) numOfOpens / (n * n);
        }
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("95% confidence interval = " + confidence);
    }

    public double mean() {
        if(mean==0){
            mean = StdStats.mean(x);
        }
        return mean;
    }

    public double stddev() {
        if(stddev==0){
            stddev = StdStats.stddev(x);
        }
        return stddev;
    }

    public double confidenceLo() {
        return mean() - ((COF * stddev) / Math.sqrt(experiments));
    }

    public double confidenceHi() {
        return mean() + ((COF * stddev()) / Math.sqrt(experiments));
    }
}
