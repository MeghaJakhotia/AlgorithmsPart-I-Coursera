import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;

public class PointSET {
    private final SET<Point2D> setOfPoints;

    public PointSET() {
        setOfPoints = new SET<>();
    }

    public boolean isEmpty() {
        return setOfPoints.isEmpty();
    }

    public int size() {
        return setOfPoints.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("No Argument Passed!");
        setOfPoints.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("No Argument Passed!");
        return setOfPoints.contains(p);
    }

    public void draw() {
        Iterator<Point2D> drawingPoints = setOfPoints.iterator();
        while (drawingPoints.hasNext()) {
            drawingPoints.next().draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("No Argument Passed!");
        Stack<Point2D> rangeOfPoints = new Stack<>();
        Iterator<Point2D> iter = setOfPoints.iterator();
        while (iter.hasNext()) {
            Point2D currentItem = iter.next();
            if (rect.contains(currentItem)) {
                rangeOfPoints.push(currentItem);
            }
        }
        return rangeOfPoints;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("No Argument Passed!");
        if (setOfPoints.isEmpty()) return null;
        Iterator<Point2D> iter = setOfPoints.iterator();
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D toReturnPoint = new Point2D(0, 0);
        while (iter.hasNext()) {
            Point2D currentItem = iter.next();
            double distance = currentItem.distanceSquaredTo(p);
            if (distance < minDistance) {
                minDistance = distance;
                toReturnPoint = currentItem;
            }
        }
        return toReturnPoint;
    }
}
