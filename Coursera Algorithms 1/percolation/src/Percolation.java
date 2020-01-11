import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private final int top;
    private final int bottom;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF weightedQuickUnionUFPerc;
    private int countOpen;
    private boolean[] site;

    public Percolation(int size) {
        if (size < 1) throw new IllegalArgumentException();
        this.size = size;
        weightedQuickUnionUF = new WeightedQuickUnionUF(this.size * this.size + 2);
        weightedQuickUnionUFPerc = new WeightedQuickUnionUF(this.size * this.size + 2);
        site = new boolean[this.size * this.size];
        top = this.size * this.size;
        bottom = this.size * this.size + 1;
    }

    public static void main(String[] args) {
        // comment
    }

    public int numberOfOpenSites() {
        return countOpen;
    }

    public void open(int i, int j) {
        checkBounds(i, j);
        if (isOpen(i, j)) {
            return;
        }
        int currentSite = convert2dTo1dCoordinates(i, j);
        this.site[currentSite] = true;
        countOpen++;

        if (i == 1 && !weightedQuickUnionUF.connected(currentSite, top)) {
            weightedQuickUnionUF.union(currentSite, top);
            weightedQuickUnionUFPerc.union(currentSite, top);
        }
        if (i == size) {
            weightedQuickUnionUFPerc.union(currentSite, bottom);
        }
        if (i < size) {
            if (isOpen(i + 1, j)) {
                weightedQuickUnionUF.union(currentSite, convert2dTo1dCoordinates(i + 1, j));
                weightedQuickUnionUFPerc.union(currentSite, convert2dTo1dCoordinates(i + 1, j));
            }
        }
        if (i > 1) {
            if (isOpen(i - 1, j)) {
                weightedQuickUnionUF.union(currentSite, convert2dTo1dCoordinates(i - 1, j));
                weightedQuickUnionUFPerc.union(currentSite, convert2dTo1dCoordinates(i - 1, j));
            }
        }
        if (j > 1) {
            if (isOpen(i, j - 1)) {
                weightedQuickUnionUF.union(currentSite, convert2dTo1dCoordinates(i, j - 1));
                weightedQuickUnionUFPerc.union(currentSite, convert2dTo1dCoordinates(i, j - 1));
            }
        }
        if (j < size) {
            if (isOpen(i, j + 1)) {
                weightedQuickUnionUF.union(currentSite, convert2dTo1dCoordinates(i, j + 1));
                weightedQuickUnionUFPerc.union(currentSite, convert2dTo1dCoordinates(i, j + 1));
            }
        }
    }

    private void checkBounds(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new IllegalArgumentException();
        }
    }


    public boolean isOpen(int i, int j) {
        checkBounds(i, j);
        return site[convert2dTo1dCoordinates(i, j)];
    }

    public boolean isFull(int i, int j) {
        checkBounds(i, j);
        if (!isOpen(i, j))
            return false;
        int currentSite = convert2dTo1dCoordinates(i, j);
        if (weightedQuickUnionUF.connected(top, currentSite))
            return true;
        return false;
    }

    private int convert2dTo1dCoordinates(int i, int j) {
        int pos = size * (i - 1) + j - 1;
        return pos;
    }


    public boolean percolates() {
        return weightedQuickUnionUFPerc.connected(top, bottom);
    }
}