import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {
    private TreeNode root;
    private int size;

    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        if (root == null) {
            root = new TreeNode(point, 0, 0.0, 0.0, 1.0, 1.0);
            size++;
            return;
        }
        TreeNode parentNode = null;
        var currentNode = root;
        while (currentNode != null) {
            parentNode = currentNode;
            if (isVertical(currentNode)) {
                if (point.x() > currentNode.key.x()) {
                    currentNode = currentNode.rightChild;
                } else if (point.x() < currentNode.key.x()) {
                    currentNode = currentNode.leftChild;
                } else if (point.x() == currentNode.key.x() && point.y() > currentNode.key.y()) {
                    currentNode = currentNode.rightChild;
                } else if (point.x() == currentNode.key.x() && point.y() < currentNode.key.y()) {
                    currentNode = currentNode.leftChild;
                } else {
                    return;
                }
            } else {
                if (point.y() > currentNode.key.y()) {
                    currentNode = currentNode.rightChild;
                } else if (point.y() < currentNode.key.y()) {
                    currentNode = currentNode.leftChild;
                } else if (point.y() == currentNode.key.y() && point.x() > currentNode.key.x()) {
                    currentNode = currentNode.rightChild;
                } else if (point.y() == currentNode.key.y() && point.x() < currentNode.key.x()) {
                    currentNode = currentNode.leftChild;
                } else {
                    return;
                }
            }
        }
        if (isVertical(parentNode)) {
            if (point.x() > parentNode.key.x()) {
                TreeNode newNode = new TreeNode(point, parentNode.level+1,
                        parentNode.key.x(), parentNode.rect.ymin(),
                        parentNode.rect.xmax(), parentNode.rect.ymax());
                parentNode.rightChild = newNode;
                size++;
            } else if (point.x() < parentNode.key.x()){
                TreeNode newNode = new TreeNode(point, parentNode.level+1,
                        parentNode.rect.xmin(), parentNode.rect.ymin(),
                        parentNode.key.x(), parentNode.rect.ymax());
                parentNode.leftChild = newNode;
                size++;
            } else if (point.x() == parentNode.key.x() && point.y() > parentNode.key.y()) {
                TreeNode newNode = new TreeNode(point, parentNode.level+1,
                        parentNode.rect.xmin(), parentNode.rect.ymin(),
                        parentNode.rect.xmax(), parentNode.rect.ymax());
                parentNode.rightChild = newNode;
                size++;
            } else {
                TreeNode newNode = new TreeNode(point, parentNode.level+1,
                        parentNode.rect.xmin(), parentNode.rect.ymin(),
                        parentNode.rect.xmax(), parentNode.rect.ymax());
                parentNode.leftChild = newNode;
                size++;
            }
        } else {
            if (point.y() > parentNode.key.y()) {
                TreeNode newNode = new TreeNode(point, parentNode.level+1,
                        parentNode.rect.xmin(), parentNode.key.y(),
                        parentNode.rect.xmax(), parentNode.rect.ymax());
                parentNode.rightChild = newNode;
                size++;
            } else if (point.y() < parentNode.key.y()) {
                TreeNode newNode = new TreeNode(point, parentNode.level+1,
                        parentNode.rect.xmin(), parentNode.rect.ymin(),
                        parentNode.rect.xmax(), parentNode.key.y());
                parentNode.leftChild = newNode;
                size++;
            } else if (point.y() == parentNode.key.y() && point.x() > parentNode.key.x()) {
                TreeNode newNode = new TreeNode(point, parentNode.level+1,
                        parentNode.rect.xmin(), parentNode.rect.ymin(),
                        parentNode.rect.xmax(), parentNode.rect.ymax());
                parentNode.rightChild = newNode;
                size++;
            } else {
                TreeNode newNode = new TreeNode(point, parentNode.level+1,
                        parentNode.rect.xmin(), parentNode.rect.ymin(),
                        parentNode.rect.xmax(), parentNode.rect.ymax());
                parentNode.leftChild = newNode;
                size++;
            }
        }
    }

    public boolean contains(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        var currentNode = root;
        if (currentNode == null) return false;
        while (!currentNode.key.equals(point)) {
            if (isVertical(currentNode)) {
                if (point.x() > currentNode.key.x()) {
                    currentNode = currentNode.rightChild;
                } else if (point.x() < currentNode.key.x()){
                    currentNode = currentNode.leftChild;
                } else if (point.x() == currentNode.key.x() && point.y() > currentNode.key.y()) {
                    currentNode = currentNode.rightChild;
                } else {
                    currentNode = currentNode.leftChild;
                }
            } else {
                if (point.y() > currentNode.key.y()) {
                    currentNode = currentNode.rightChild;
                } else if (point.y() < currentNode.key.y()){
                    currentNode = currentNode.leftChild;
                } else if (point.y() == currentNode.key.y() && point.x() > currentNode.key.x()) {
                    currentNode = currentNode.rightChild;
                } else {
                    currentNode = currentNode.leftChild;
                }
            }
            if (currentNode == null) return false;
        }
        return true;
    }

    public void draw() {
        for (Point2D p: toList()) {
            p.draw();
        }
    }

    private ArrayList<Point2D> toList() {
        ArrayList<Point2D> res = new ArrayList<>();
        toList(res, root);
        return res;
    }

    private void toList(ArrayList<Point2D> ps, TreeNode node) {
        if (node != null) {
            toList(ps, node.leftChild);
            ps.add(node.key);
            toList(ps, node.rightChild);
        }
    }

    private boolean isVertical(TreeNode node) {
        return node.level % 2 == 0;
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> res = new ArrayList<>();
        range(rect, res, root);
        return res;
    }

    private void range(RectHV rect, ArrayList<Point2D> r, TreeNode node) {
        if (node == null) return;
        if (rect.contains(node.key)) {
            r.add(node.key);
        }
        if (isVertical(node)) {
            if (rect.xmin() > node.key.x()) {
                range(rect, r, node.rightChild);
            } else if (rect.xmax() < node.key.x()) {
                range(rect, r, node.leftChild);
            } else {
                range(rect, r, node.leftChild);
                range(rect, r, node.rightChild);
            }
        } else {
            if (rect.ymin() > node.key.y()) {
                range(rect, r, node.rightChild);
            } else if (rect.ymax() < node.key.y()) {
                range(rect, r, node.leftChild);
            } else {
                range(rect, r, node.leftChild);
                range(rect, r, node.rightChild);
            }
        }
    }

    public Point2D nearest(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        var res = nearest(root, point, root.key);
        return res;
    }

    private Point2D nearest(TreeNode node, Point2D point, Point2D min) {
        if (node == null) return min;
        if (node.key.distanceSquaredTo(point) < min.distanceSquaredTo(point)) {
            min = node.key;
        }
        if (isVertical(node)) {
            if (isInLeftChild(node, point)) {
                min = nearest(node.leftChild, point, min);
                double rectDist = node.rightChild != null
                        ? node.rightChild.rect.distanceSquaredTo(point)
                        : Double.POSITIVE_INFINITY;
                boolean isGreater = min.distanceSquaredTo(point) > rectDist;
                if (isGreater) {
                    min = nearest(node.rightChild, point, min);
                }
            } else {
                min = nearest(node.rightChild, point, min);
                double rectDist = node.leftChild != null
                        ? node.leftChild.rect.distanceSquaredTo(point)
                        : Double.POSITIVE_INFINITY;
                boolean isGreater = min.distanceSquaredTo(point) > rectDist;
                if (isGreater) {
                    min = nearest(node.leftChild, point, min);
                }
            }
        } else {
            if (isInLeftChild(node, point)) {
                min = nearest(node.leftChild, point, min);
                double rectDist = node.rightChild != null
                        ? node.rightChild.rect.distanceSquaredTo(point)
                        : Double.POSITIVE_INFINITY;
                boolean isGreater = min.distanceSquaredTo(point) > rectDist;
                if (isGreater) {
                    min = nearest(node.rightChild, point, min);
                }
            } else {
                min = nearest(node.rightChild, point, min);
                double rectDist = node.leftChild != null
                        ? node.leftChild.rect.distanceSquaredTo(point)
                        : Double.POSITIVE_INFINITY;
                boolean isGreater = min.distanceSquaredTo(point) > rectDist;
                if (isGreater) {
                    min = nearest(node.leftChild, point, min);
                }
            }
        }
        return min;
    }

    private boolean isInLeftChild(TreeNode node, Point2D point) {
        if (isVertical(node)) {
            if (point.x() == node.key.x()) {
                return point.y() < node.key.y();
            }
            return point.x() < node.key.x();
        } else {
            if (point.y() == node.key.y()) {
                return point.x() < node.key.x();
            }
            return point.y() < node.key.y();
        }
    }

    private static class TreeNode {
        private final Point2D key;
        private TreeNode leftChild;
        private TreeNode rightChild;
        private final int level;
        private final RectHV rect;

        public TreeNode(Point2D key, int level, double xmin, double ymin, double xmax, double ymax) {
            if (key == null) throw new IllegalArgumentException();
            this.key = key;
            this.level = level;
            this.rect = new RectHV(xmin, ymin, xmax, ymax);
            this.leftChild = null;
            this.rightChild = null;
        }
    }
}
