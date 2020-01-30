import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;
    private Stack<Point2D> allPoints;


    private class Node {
        private final Point2D point;
        private Node left, right;
        private final int level;
        private RectHV rect;

        public Node(Point2D p, int level) {
            this.point = p;
            this.level = level;
            this.right = null;
            this.left = null;
            this.rect = null;
        }

        public int compareTo(Node input) {
            if (this.level % 2 == 0) {
                return Double.compare(input.point.x(), this.point.x());
            } else {
                return Double.compare(input.point.y(), this.point.y());
            }
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("No Argument Passed!");
        if (root == null || !contains(p)) {
            root = insert2(root, null, p, 0, false);
            // initrectangle(root, null, null, false);
            size++;
        }
    }

    private Node insert2(Node x, Node previousNode, Point2D p, int count, boolean direction) {
        if (x == null) {
            Node newNode = new Node(p, count);
            initrectangle(newNode, previousNode, direction);
            return newNode;
        }
        if (x.point.equals(p)) return x;
        int cmp = x.compareTo(new Node(p, count));
        if (cmp < 0) {
            x.left = insert2(x.left, x, p, count + 1, false);
            return x;
        } else {
            x.right = insert2(x.right, x, p, count + 1, true);
            return x;
        }

    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("No Argument Passed!");
        return containsPoint(root, p, 0);
    }

    private boolean containsPoint(Node x, Point2D p, int count) {
        if (x == null) return false;
        if (x.point.equals(p)) return true;
        int cmp = x.compareTo(new Node(p, count));
        if (cmp < 0) return containsPoint(x.left, p, count + 1);
        else return containsPoint(x.right, p, count + 1);
    }

    public void draw() {
        if (root != null) {
            RectHV unitSquare = new RectHV(0, 0, 1, 1);
            unitSquare.draw();
        } else
            return;
        StdDraw.clear();
        drawFull(root);
    }

    private void drawFull(Node x) {
        if (x.rect == null) return;
        if (x.left != null) drawFull(x.left);
        if (x.right != null) drawFull(x.right);
        drawPoint(x.point);
        if (x.level % 2 == 0)
            drawVertical(x.rect, x.point);
        else
            drawHorizontal(x.rect, x.point);
    }

    private void drawPoint(Point2D p) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        p.draw();
    }

    private void drawVertical(RectHV current, Point2D p) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(p.x(), current.ymin(), p.x(), current.ymax());
    }

    private void drawHorizontal(RectHV current, Point2D p) {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(current.xmin(), p.y(), current.xmax(), p.y());
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("No Argument Passed!");
        allPoints = new Stack<>();
        thePoints(root, rect);
        return allPoints;
    }

    private void thePoints(Node x, RectHV rect) {
        if (x == null) return;
        if (rect.contains(x.point)) allPoints.push(x.point);
        if (x.rect == null) return;
        if (x.left != null && rect.intersects(x.left.rect))
            thePoints(x.left, rect);
        if (x.right != null && rect.intersects(x.right.rect))
            thePoints(x.right, rect);
    }

    // direction = false is left
    // direction = true is right

    private void initrectangle(Node x, Node previousNode, boolean direction) {
        if (x == root)
            x.rect = new RectHV(0, 0, 1, 1);
        else if (x.rect == null) {
            x.rect = rectangeOfPoint(previousNode, x, direction);
        }

    }

    private RectHV rectangeOfPoint(Node previousNode, Node currentNode, boolean direction) {

        if (currentNode == root || previousNode == null)
            return new RectHV(0, 0, 1, 1);
        else if (currentNode.level % 2 != 0 && !direction) {
            return new RectHV(previousNode.rect.xmin(), previousNode.rect.ymin(),
                    previousNode.point.x(), previousNode.rect.ymax());

        } else if (currentNode.level % 2 == 0 && !direction) {
            return new RectHV(previousNode.rect.xmin(), previousNode.rect.ymin(),
                    previousNode.rect.xmax(), previousNode.point.y());

        } else if (currentNode.level % 2 != 0 && direction) {
            return new RectHV(previousNode.point.x(), previousNode.rect.ymin(),
                    previousNode.rect.xmax(), previousNode.rect.ymax());

        } else if (currentNode.level % 2 == 0 && direction) {
            return new RectHV(previousNode.rect.xmin(), previousNode.point.y(),
                    previousNode.rect.xmax(), previousNode.rect.ymax());

        }
        return null;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("No Argument Passed!");
        if (root == null) return null;
        return thePoint(root, p, root).point;
    }

    private Node thePoint(Node current, Point2D p, Node nearest) {

        if (current == null)
            return nearest;

        if (current.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p))
            nearest = current;

        Node left = thePoint(current.left, p, nearest);
        Node right = thePoint(current.right, p, nearest);

        if (left.point.distanceSquaredTo(p) < right.point.distanceSquaredTo(p)) {
            if (left.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p))
                nearest = left;
        } else {
            if (right.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p))
                nearest = right;
        }
        return nearest;

    }

}
