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

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {

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
        Arrays.sort(points);

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        double calc = points[i].slopeTo(points[j]);
                        if ((calc == points[i].slopeTo(points[k])) && (calc == points[i]
                                .slopeTo(points[m]))) {
                            storeSegments.add(new LineSegment(points[i], points[m]));
                        }
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
