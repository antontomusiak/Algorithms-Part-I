import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment[] lines;


    public FastCollinearPoints(Point[] points) {
        if (points == null || points[0] == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        if (points.length < 4) {
            lines = new LineSegment[0];
            return;
        }
        ArrayList<SegmentInfo> internal = new ArrayList<>();
        Point[] points2 = points.clone();
        for (int i = 0; i < points.length; i++) {
            Comparator<Point> cmp = points[i].slopeOrder();
            Arrays.sort(points2, cmp);
            Point prev = points2[1];
            SegmentInfo line = new SegmentInfo();
            line.addPoint(prev);
            for (int j = 2; j < points2.length; j++) {
                int cmpResult = cmp.compare(prev, points2[j]);
                if (cmpResult == 0) {
                    line.addPoint(points2[j]);
                }
                if (cmpResult != 0 || j == (points2.length - 1)) {
                    if (line.size() >= 3) {
                        line.addPoint(points[i]);
                        internal.add(line);
                    }
                    line = new SegmentInfo();
                    line.addPoint(points2[j]);
                }
                prev = points2[j];
            }
        }
        Collections.sort(internal);
        ArrayList<LineSegment> result = new ArrayList<>();
        SegmentInfo prev = null;
        for (int i = 0; i < internal.size(); i++) {
            if (prev == null || prev.compareTo(internal.get(i)) != 0) {
                result.add(internal.get(i).toLineSegment());
            }
            prev = internal.get(i);
        }
        lines = new LineSegment[result.size()];
        lines = result.toArray(lines);
    }


    public int numberOfSegments() {
        return lines.length;
    }

    public LineSegment[] segments() {
        return lines.clone();
    }

    private class SegmentInfo implements Comparable<SegmentInfo> {
        private Point start;
        private Point end;
        private int counter;

        public LineSegment toLineSegment() {
            return new LineSegment(start, end);
        }

        public int compareTo(SegmentInfo other) {
            int cmp1 = this.start.compareTo(other.start);
            return cmp1 != 0 ? cmp1 : this.end.compareTo(other.end);
        }

        public void addPoint(Point p) {
            if (start == null || start.compareTo(p) > 0) {
                start = p;
            }
            if (end == null || end.compareTo(p) < 0) {
                end = p;
            }
            counter++;
        }

        public int size() {
            return counter;
        }
    }
}
