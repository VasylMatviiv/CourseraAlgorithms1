import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {
        isValidPoints(points);
        Point[] newPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(newPoints);

        lineSegments = new ArrayList<LineSegment>();
        for (int p = 0; p < newPoints.length - 3; p++) {
            for (int q = p + 1; q < newPoints.length - 2; q++) {
                for (int r = q + 1; r < newPoints.length - 1; r++) {
                    for (int s = r + 1; s < newPoints.length; s++) {
                        if (Double.compare(newPoints[p].slopeTo(newPoints[q]), newPoints[p].slopeTo(newPoints[r])) == 0 &&
                                Double.compare(newPoints[p].slopeTo(newPoints[r]), newPoints[p].slopeTo(newPoints[s])) == 0) {
                            lineSegments.add(new LineSegment(newPoints[p], newPoints[s]));
                        }
                    }
                }
            }
        }
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
