
/*************************************************************************
 *************************************************************************/

import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class KdTree {
    // construct an empty set of points
    private class Node {
        Node left;
        Node right;
        Point2D value;
        private Node (Point2D value){
            this.value = value;
            this.left = null;
            this.right = null;
        }

    }

    Node root;

    public KdTree() {

    }


    // is the set empty?
    public boolean isEmpty() {
        return false;
    }

    // number of points in the set
    public int size() {
        return 0;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (root != null){
            Node new_node = new Node(p);
            find_position(new_node);
        } else {
            root = new Node(p);
        }
    }

    private void find_position(Node new_node) {
        Node next_node = root;
        Node prev_node = null;
        boolean vertical = true;
        boolean left = true;
        
        while (next_node != null){
            prev_node = next_node;
            if (vertical) {

                if (new_node.value.x() <= next_node.value.x()){
                    next_node = next_node.left;
                    left = true;
                } else {
                    next_node = next_node.right;
                    left = false;
                }
                vertical = false;
            } else {
                if (new_node.value.y() <= next_node.value.y()){
                    next_node = next_node.left;
                    left = true;
                } else {
                    next_node = next_node.right;
                    left = false;
                }
                vertical = true;
            }
        }
        if (left){
            prev_node.left = new_node;
        } else {
            prev_node.right = new_node;
        }

    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return false;
    }

    // draw all of the points to standard draw
    public void draw() {

    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        return p;
    }

    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        In input = new In(args[0]);
        KdTree our_kdtree = new KdTree();
        int number_of_lines = input.readInt();
        for(int i = 0; i < number_of_lines; i++){
            double x = input.readDouble();
            double y = input.readDouble();
            Point2D point = new Point2D(x,y);
            our_kdtree.insert(point);
        }
        StdOut.println();

        RectHV my_rectangle = new RectHV(0.3, 0.8, 1.0, 1.0);
        Iterable<Point2D> my_square_points = our_kdtree.range(my_rectangle);

        Point2D test_point = new Point2D(2.2, 3.0);
        Point2D fancy_point = our_kdtree.nearest(test_point);
        StdOut.println();

        our_kdtree.draw();
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
//
//
//        KdTree set = new KdTree();
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
