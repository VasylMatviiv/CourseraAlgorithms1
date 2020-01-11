public class LineSegment {
    private Point startPoint;
    private Point endPoint;

    public LineSegment(Point p, Point q) {
        startPoint = p;
        endPoint = q;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void draw() {
        startPoint.drawTo(endPoint);
    }

    @Override
    public String toString() {
        return "LineSegment{" +
                "point1=" + startPoint +
                ", point2=" + endPoint +
                '}';
    }
}
