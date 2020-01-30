/* *****************************************************************************
 *  Name: Megha Jakhotia
 *  Date: June 2, 2019
 *  Description: Percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] array;
    private final WeightedQuickUnionUF wf;
    private final int num;
    private int opened = 0;
    private final int start;
    private final int end;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Value of N is less than or equal to 0");
        array = new boolean[n][n];
        int row, col;
        for (row = 0; row < n; row++) {
            for (col = 0; col < n; col++) {
                array[row][col] = false;
            }
        }
        num = n;
        start = num * num;
        end = num * num + 1;
        wf = new WeightedQuickUnionUF(n * n + 2);
    }

    private int oneD(int row, int col) {
        return row * num + col;
    }

    private void validate(int row, int col) {
        if (row >= num || row < 0 || col >= num || col < 0)
            throw new IllegalArgumentException("Enter a valid argument");
    }

    public void open(int row, int col) {
        int value;
        row = row - 1;
        col = col - 1;
        validate(row, col);
        if (!array[row][col]) {
            value = oneD(row, col);
            array[row][col] = true;
            if (row > 0 && array[row - 1][col]) wf.union(value, value - num);
            if (col > 0 && array[row][col - 1]) wf.union(value, value - 1);
            if (row < num - 1 && array[row + 1][col]) wf.union(value, value + num);
            if (col < num - 1 && array[row][col + 1]) wf.union(value, value + 1);
            if (value < num) wf.union(value, start);
            if (value >= num * num - num) wf.union(value, end);
            opened++;
        }
    }

    public boolean isOpen(int row, int col) {
        row = row - 1;
        col = col - 1;
        validate(row, col);
        return array[row][col];
    }

    public boolean isFull(int row, int col) {
        row = row - 1;
        col = col - 1;
        validate(row, col);
        return (wf.connected(oneD(row, col), start) && array[row][col]);
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        return wf.connected(start, end);
    }
}
