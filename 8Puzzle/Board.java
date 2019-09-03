import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class Board {
    private final int[][] data2;
    private final int manhattanPriority;
    private final int hammingPriority;
    private final int twin1;
    private final int twin2;
    private final int twin3;
    private final int twin4;


    public Board(int[][] data) {
        this.data2 = data;
        this.manhattanPriority = calculateManhattan();
        this.hammingPriority = calculateHamming();
        int[] twin = makeTwin();
        this.twin1 = twin[0];
        this.twin2 = twin[1];
        this.twin3 = twin[2];
        this.twin4 = twin[3];
    }

    public String toString() {
        String res = "";
        res += dimension() + "\n";
        for (int i = 0; i < data2.length; i++) {
            for (int j = 0; j < data2.length; j++) {
                res += data2[i][j] + " ";
            }
            res += "\n";
        }
        return res;
    }

    public int dimension() {
        return data2.length;
    }

    public int manhattan() {
        return manhattanPriority;
    }

    public int hamming() {
        return hammingPriority;
    }

    private int calculateHamming() {
        int res = 0;
        for (int i = 0; i < data2.length; i++) {
            for (int j = 0; j < data2.length; j++) {
                if (i == data2.length - 1 && j == data2.length - 1) {
                    break;
                }
                if (data2[i][j] != i * data2.length + j + 1) {
                    res++;
                }
            }
        }
        return res;
    }

    private int calculateManhattan() {
        int res = 0;
        for (int i = 0; i < data2.length; i++) {
            for (int j = 0; j < data2.length; j++) {
                if (data2[i][j] == 0) continue;
                if (data2[i][j] != i * data2.length + j + 1) {
                    int c = Math.abs(i - (data2[i][j] - 1) / data2.length);
                    int r = Math.abs(j - (data2[i][j] - 1) % data2.length);
                    res = res + c + r;
                }
            }
        }
        return res;
    }

    public boolean isGoal() {
        return manhattanPriority == 0;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        for (int i = 0; i < data2.length; i++) {
            for (int j = 0; j < data2.length; j++) {
                if (that.data2[i][j] != this.data2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        int blankI = 0;
        int blankJ = 0;
        for (int i = 0; i < data2.length; i++) {
            for (int j = 0; j < data2.length; j++) {
                if (data2[i][j] == 0) {
                    blankI = i;
                    blankJ = j;

                }
            }
        }
        ArrayList<Board> res = new ArrayList<>();
        if (blankI > 0) {
            int[][] tmpData = new int[data2.length][data2.length];
            fillData(tmpData);
            swap(tmpData, blankI - 1, blankJ, blankI, blankJ);
            res.add(new Board(tmpData));
        }
        if (blankI < (data2.length - 1)) {
            int[][] tmpData = new int[data2.length][data2.length];
            fillData(tmpData);
            swap(tmpData, blankI+1, blankJ, blankI, blankJ);
            res.add(new Board(tmpData));
        }
        if (blankJ > 0) {
            int[][] tmpData = new int[data2.length][data2.length];
            fillData(tmpData);
            swap(tmpData, blankI, blankJ - 1, blankI, blankJ);
            res.add(new Board(tmpData));
        }
        if (blankJ < (data2.length - 1)) {
            int[][] tmpData = new int[data2.length][data2.length];
            fillData(tmpData);
            swap(tmpData, blankI, blankJ + 1, blankI, blankJ);
            res.add(new Board(tmpData));
        }
        return res;
    }

    private void fillData(int[][] arr) {
        for (int i = 0; i < data2.length; i++) {
            arr[i] = this.data2[i].clone();
        }
    }

    public Board twin() {
        int[][] tmp = new int[data2.length][data2.length];
        fillData(tmp);
        swap(tmp, twin1, twin2, twin3, twin4);
        return new Board(tmp);
    }

    private int[] makeTwin() {
        int i = StdRandom.uniform(data2.length);
        int j = StdRandom.uniform(data2.length);
        while (data2[i][j] == 0) {
            i = StdRandom.uniform(data2.length);
            j = StdRandom.uniform(data2.length);
        }
        int k = StdRandom.uniform(data2.length);
        int m = StdRandom.uniform(data2.length);
        while (data2[k][m] == 0 || data2[k][m] == data2[i][j]) {
            k = StdRandom.uniform(data2.length);
            m = StdRandom.uniform(data2.length);
        }
        return new int[]{i, j, k, m};
    }

    private void swap(int[][] data, int i, int j, int k, int m) {
        int tmp = data[i][j];
        data[i][j] = data[k][m];
        data[k][m] = tmp;
    }
}

