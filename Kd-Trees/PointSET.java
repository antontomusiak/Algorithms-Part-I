import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> ps;

    public PointSET() {
        this.ps = new TreeSet<>();
    }

    public boolean isEmpty() {
        return ps.isEmpty();
    }

    public int size() {
        return ps.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        ps.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return ps.contains(p);
    }

    public void draw() {
        for (Point2D p: ps) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> res = new ArrayList<>();
        for (Point2D p: ps) {
            if (rect.contains(p)) {
                res.add(p);
            }
        }
        return res;
    }

    public Point2D nearest(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        Point2D res = null;
        for (Point2D p: ps) {
            if (res == null || p.distanceSquaredTo(point) < res.distanceSquaredTo(point)) {
                res = p;
            }
        }
        return res;
    }
}
