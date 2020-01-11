import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        lineSegments = new ArrayList<LineSegment>();
        isValidPoints(points);
        Point[] copyPoints = Arrays.copyOf(points, points.length);

        for (int i = 0; i < copyPoints.length - 3; i++) {
            Arrays.sort(copyPoints);
            Arrays.sort(copyPoints, copyPoints[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < copyPoints.length; last++) {
                while (last < copyPoints.length &&
                        Double.compare(copyPoints[p].slopeTo(copyPoints[first]), copyPoints[p].slopeTo(copyPoints[last])) == 0) {
                    last++;
                }
                if (last - first >= 3 && copyPoints[p].compareTo(copyPoints[first]) < 0) {
                    lineSegments.add(new LineSegment(copyPoints[p], copyPoints[last - 1]));
                }
                first = last;
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    private void isValidPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point point : points)
            if (point == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
    }
}
