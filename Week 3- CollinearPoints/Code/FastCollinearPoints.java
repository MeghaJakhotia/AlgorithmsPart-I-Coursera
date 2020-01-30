/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException("No entries");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new java.lang.IllegalArgumentException("The entry is null");
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new java.lang.IllegalArgumentException("The entry is null");
                if (points[i].equals(points[j]))
                    throw new java.lang.IllegalArgumentException("Two entries are equal");
            }
        }
        if (points.length < 4) return;
        ArrayList<LineSegment> storeSegments = new ArrayList<LineSegment>();
        Point[] cpy = Arrays.copyOf(points, points.length);
        for (Point point : points) {
            Arrays.sort(cpy, point.slopeOrder());
            double tmpSlope = point.slopeTo(cpy[0]);
            int count = 1, i;
            for (i = 1; i < cpy.length; i++) {
                if (point.slopeTo(cpy[i]) == tmpSlope) {
                    count++;
                    continue;
                }
                else {
                    if (count >= 3) {
                        Arrays.sort(cpy, i - count, i);
                        if (point.compareTo(cpy[i - count]) < 0) {
                            storeSegments.add(new LineSegment(point, cpy[i - 1]));
                        }
                    }
                    tmpSlope = point.slopeTo(cpy[i]);
                    count = 1;
                }
            }
            if (count >= 3) {
                Arrays.sort(cpy, i - count, i);
                if (point.compareTo(cpy[i - count]) < 0)
                    storeSegments.add(new LineSegment(point, cpy[i - 1]));
            }
        }
        lineSegments = storeSegments.toArray(new LineSegment[storeSegments.size()]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
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

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
