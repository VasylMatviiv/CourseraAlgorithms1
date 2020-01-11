import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }                           // draws this point

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }                // draws the line segment from this point to that point

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    // string representation

    public int compareTo(Point that) {

        if (this.y != that.y) {
            return this.y > that.y ? 1 : -1;
        }
        if (this.x != that.x) {
            return this.x > that.x ? 1 : -1;
        }
        return 0;
    }  // compare two points by y-coordinates, breaking ties by x-coordinates

    public double slopeTo(Point that) {
        if (that.x - this.x == 0 && that.y - this.y == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (that.x - this.x == 0) {
            return Double.POSITIVE_INFINITY;
        } else if (that.y - this.y == 0) {
            return 0.0;
        }

        return (double) (that.y - this.y) / (that.x - this.x);
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {

        Point point = Point.this;

        @Override
        public int compare(Point t, Point t1) {
            if (point.slopeTo(t) < point.slopeTo(t1)) {
                return -1;
            } else if (point.slopeTo(t) > point.slopeTo(t1)) {
                return 1;
            }

            return 0;
        }
    }
}