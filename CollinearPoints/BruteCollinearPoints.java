import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lines;
    private int n;

    public BruteCollinearPoints(Point[] points) {
        if (points == null || points[0] == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length-1; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i+1; j < points.length; j++) {
                if (points[j] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        Point[] newPoints = points.clone();
        Arrays.sort(newPoints);
        this.lines = new LineSegment[1];
        this.n = 0;
        for (int i = 0; i < newPoints.length-3; i++) {
            for (int j = i+1; j < newPoints.length-2; j++) {
                for (int k = j+1; k < newPoints.length-1; k++) {
                    if (newPoints[i].slopeTo(newPoints[j]) == newPoints[j].slopeTo(newPoints[k])) {
                        for (int m = k+1; m < newPoints.length; m++) {
                            if ((newPoints[i].slopeTo(newPoints[j]) == newPoints[j].slopeTo(newPoints[k])) && (newPoints[j].slopeTo(newPoints[k]) == newPoints[k].slopeTo(newPoints[m]))) {
                                addLine(new LineSegment(newPoints[i], newPoints[m]));
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return n;
    }

    public LineSegment[] segments() {
        resize(n);
        return lines.clone();
    }

    private void addLine(LineSegment line) {
        if (n == lines.length) resize(2 * lines.length);
        lines[n++] = line;
    }

    private void resize(int cap) {
        LineSegment[] copy = new LineSegment[cap];
        for (int i = 0; i < n; i++) {
            copy[i] = lines[i];
        }
        lines = copy;
    }
}
