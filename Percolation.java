import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] grid;
    private final int n;
    private int openSites;
    private final WeightedQuickUnionUF topComponents;
    private final WeightedQuickUnionUF components;
    private final int topVirtualSiteIndex;
    private final int bottomVirtualSiteIndex;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        openSites = 0;
        this.n = n;
        grid = new boolean[n][n];
        topComponents = new WeightedQuickUnionUF(n * n + 1);
        components = new WeightedQuickUnionUF(n * n + 2);
        topVirtualSiteIndex = n * n;
        bottomVirtualSiteIndex = n * n + 1;
    }

    public void open(int row, int col) {
        checkIndices(row, col);
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            openSites++;
        }
        int index1 = n * (row - 1) + col - 1;
        if (row - 2 >= 0 && grid[row - 2][col - 1]) {
            int index2 = n * (row - 2) + col - 1;
            topComponents.union(index1, index2);
            components.union(index1, index2);
        } else if (row - 2 < 0) {
            topComponents.union(topVirtualSiteIndex, index1);
            components.union(topVirtualSiteIndex, index1);
        }

        if (row < n && grid[row][col - 1]) {
            int index2 = n * (row) + col - 1;
            topComponents.union(index1, index2);
            components.union(index1, index2);
        } else if (row >= n) {
            components.union(bottomVirtualSiteIndex, index1);
        }
        if (col - 2 >= 0 && grid[row - 1][col - 2]) {
            int index2 = n * (row - 1) + col - 2;
            topComponents.union(index1, index2);
            components.union(index1, index2);
        }
        if (col < n && grid[row - 1][col]) {
            int index2 = n * (row - 1) + col;
            topComponents.union(index1, index2);
            components.union(index1, index2);
        }

    }

    public boolean isOpen(int row, int col) {
        checkIndices(row, col);
        return grid[row - 1][col - 1];
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean isFull(int row, int col) {
        checkIndices(row, col);
        return topComponents.connected(topVirtualSiteIndex, getComponentIndex(row, col));
    }

    public boolean percolates() {
        return components.connected(topVirtualSiteIndex, bottomVirtualSiteIndex);
    }

    private int getComponentIndex(int row, int col) {
        return n * (row - 1) + col - 1;
    }

    private void checkIndices(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IllegalArgumentException();
        }
        if (col <= 0 || col > n) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 3);
        p.open(3, 3);
        p.open(2, 3);
        p.open(3, 1);
        //p.open(5, 2);
        System.out.printf("isFull = %b\n", p.isFull(3, 1));
        System.out.printf("isOpen = %b\n", p.isOpen(3, 1));
        System.out.printf("percolates = %b\n", p.percolates());
    }
}