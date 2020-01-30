/* *****************************************************************************
 *  Name: Megha Jakhotia
 *  Date: June 2, 2019
 *  Description: Percolation Statistics Code
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final double[] value;
    private double m, std;

    public PercolationStats(int n, int trials) {
        int i, row, col;
        double d;
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Value of N or T is less than or equal to 0");
        value = new double[trials];
        for (i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            }
            d = p.numberOfOpenSites();
            value[i] = d / (n * n);
        }
    }

    public double mean() {
        m = StdStats.mean(value);
        return m;
    }

    public double stddev() {
        std = StdStats.stddev(value);
        return std;
    }

    public double confidenceLo() {
        return (m - ((CONFIDENCE * std) / Math.sqrt(value.length)));
    }

    public double confidenceHi() {
        return (m + ((CONFIDENCE * std) / Math.sqrt(value.length)));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats perc = new PercolationStats(n, t);
        StdOut.printf("mean                    = %f\n", perc.mean());
        StdOut.printf("stddev                  = %f\n", perc.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]", perc.confidenceLo(),
                      perc.confidenceHi());
    }
}
