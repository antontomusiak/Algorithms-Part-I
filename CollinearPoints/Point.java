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
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point that) {
        if ((this.y < that.y) || (this.y == that.y && this.x < that.x)) {
            return -1;
        }
        if (this.y == that.y && this.x == that.x) {
            return 0;
        }
        return 1;
    }

    public double slopeTo(Point that) {
        if (that.x == this.x) {
            if (that.y == this.y) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y) return +0.0;
        return ((double) (that.y - this.y))/(that.x - this.x);
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {

        public int compare(Point p1, Point p2) {
//            int t = signum(p2.x, x) * signum(p1.x, x);
//            return (t * (p1.y - y) * (p2.x - x)) - (t * (p2.y - y) * (p1.x - x));
            if (slopeTo(p1) < slopeTo(p2)) {
                return -1;
            }
            if (slopeTo(p1) > slopeTo(p2)) {
                return 1;
            }
            return 0;
        }

//        private int signum(int a, int b) {
//            return (a-b) >= 0 ? 1 : -1;
//        }
    }

        public static void main(String[] args) {

            //'(10000, 0) -> (13000, 0) -> (20000, 0) -> (30000, 0)'

            Point p1 = new Point(10000, 0);
            Point p2 = new Point(13000, 0);
            Point p3 = new Point(4, 8);
            Point p4 = new Point(20000, 0);
            Point p5 = new Point(30000, 0);
            //Point p6 = new Point(40000, 0);
            Point p7 = new Point(12, 48);
            Point p8 = new Point(15, 37);
            Point p9 = new Point(10000, 13);
            Point p10 = new Point(10000, 20);
            Point p11 = new Point(10000, 30);
            Point[] pp = new Point[]{p1, p2, p3, p4, p5, p7, p8, p9, p10, p11};
            FastCollinearPoints fcp = new FastCollinearPoints(pp);
            LineSegment[] lines = fcp.segments();
            for (LineSegment l: lines) {
                System.out.println(l.toString());
            }

        }
}
