
/****************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    
 *  Dependencies:
 *  Author:
 *  Date:
 *
 *  Data structure for maintaining a set of 2-D points, 
 *    including rectangle and nearest-neighbor queries
 *
 *************************************************************************/

import edu.princeton.cs.algs4.*;

import java.util.Arrays;
import java.util.Iterator;

public class PointSET {
    // construct an empty set of points
    SET<Point2D> point_set;
    public PointSET() {
        point_set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return point_set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return point_set.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        point_set.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {

        return point_set.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        Iterator<Point2D> my_point_array = point_set.iterator();
        while (my_point_array.hasNext()){
            my_point_array.next().draw();
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> points_in_rectangle = new SET<>();
        Iterator<Point2D> my_point_array = point_set.iterator();

        while (my_point_array.hasNext()){
            Point2D current_point = my_point_array.next();
            if (rect.contains(current_point)){
                points_in_rectangle.add(current_point);
            }
        }
        return points_in_rectangle;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        Iterator<Point2D> my_point_array = point_set.iterator();

        Point2D current_point = my_point_array.next();
        double min_distance = p.distanceSquaredTo(current_point);
        Point2D closest_point = current_point;

        while (my_point_array.hasNext()){
            current_point = my_point_array.next();
            if (p.distanceSquaredTo(current_point) < min_distance){
                min_distance = p.distanceSquaredTo(current_point);
                closest_point = current_point;
            }
        }
        return closest_point;
    }


    //********************** Main here ****************************************

    public static void main(String[] args) {
        In input = new In(args[0]);
        PointSET our_point_set = new PointSET();
        int number_of_lines = input.readInt();
        for(int i = 0; i < number_of_lines; i++){
            double x = input.readDouble();
            double y = input.readDouble();
            Point2D point = new Point2D(x,y);
            our_point_set.insert(point);
        }

        RectHV my_rectangle = new RectHV(0.3, 0.8, 1.0, 1.0);
        Iterable<Point2D> my_square_points = our_point_set.range(my_rectangle);

        Point2D test_point = new Point2D(0.85, 0.4);
        Point2D fancy_point = our_point_set.nearest(test_point);
        StdOut.println(fancy_point);

        our_point_set.draw();
    }

//    public static void main(String[] args) {
//        In in = new In();
//        Out out = new Out();
//        int nrOfRecangles = in.readInt();
//        int nrOfPointsCont = in.readInt();
//        int nrOfPointsNear = in.readInt();
//        RectHV[] rectangles = new RectHV[nrOfRecangles];
//        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
//        Point2D[] pointsNear = new Point2D[nrOfPointsNear];
//        for (int i = 0; i < nrOfRecangles; i++) {
//            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
//                    in.readDouble(), in.readDouble());
//        }
//        for (int i = 0; i < nrOfPointsCont; i++) {
//            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
//        }
//        for (int i = 0; i < nrOfPointsNear; i++) {
//            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
//        }
//        PointSET set = new PointSET();
//        for (int i = 0; !in.isEmpty(); i++) {
//            double x = in.readDouble(), y = in.readDouble();
//            set.insert(new Point2D(x, y));
//        }
//        for (int i = 0; i < nrOfRecangles; i++) {
//            // Query on rectangle i, sort the result, and print
//            Iterable<Point2D> ptset = set.range(rectangles[i]);
//            int ptcount = 0;
//            for (Point2D p : ptset)
//                ptcount++;
//            Point2D[] ptarr = new Point2D[ptcount];
//            int j = 0;
//            for (Point2D p : ptset) {
//                ptarr[j] = p;
//                j++;
//            }
//            Arrays.sort(ptarr);
//            out.println("Inside rectangle " + (i + 1) + ":");
//            for (j = 0; j < ptcount; j++)
//                out.println(ptarr[j]);
//        }
//        out.println("Contain test:");
//        for (int i = 0; i < nrOfPointsCont; i++) {
//            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
//        }
//
//        out.println("Nearest test:");
//        for (int i = 0; i < nrOfPointsNear; i++) {
//            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
//        }
//
//        out.println();
//    }

}
