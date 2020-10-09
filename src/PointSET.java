
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

    public static void count_nearest(String[] args, int n){
        In input = new In(args[0]);
        int N = n;
        Point2D[] point_arr = new Point2D[N];
        PointSET new_point_set = new PointSET();

        for(int i = 0; !input.isEmpty(); i++){
            double x = input.readDouble();
            double y = input.readDouble();
            point_arr[i] = new Point2D(x,y);
        }
        for (Point2D point2D : point_arr) {
            new_point_set.insert(point2D);
        }

        int counter = 0;
        Stopwatch OneSecond = new Stopwatch();
        while (OneSecond.elapsedTime() < 1){
            new_point_set.nearest(new Point2D(StdRandom.uniform(), StdRandom.uniform()));
            counter++;
        }
        StdOut.println(counter);
    }

    //********************** Main here ****************************************

    public static void main(String[] args) {
        int n = 100000;
        int t = 100;
        for (int i = 0; i < t; i++){
            PointSET.count_nearest(args, n);
        }
    }
}
