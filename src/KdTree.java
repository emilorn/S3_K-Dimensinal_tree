
/*************************************************************************
 *************************************************************************/

import edu.princeton.cs.algs4.*;

public class KdTree {

    private static class Node {
        Node left;
        Node right;
        Point2D value;

        private Node (Point2D value){
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size = 0;

    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D point) {
        if (root != null){
            find_position(point);
        } else {
            root = new Node(point);
        }
        size++;
    }

    private void find_position(Point2D point) {
        Node next_node = root;
        Node prev_node = null;
        boolean vertical = true;
        boolean left = true;

        while (next_node != null){
            prev_node = next_node;
            if (vertical) {
                left = point.x() < next_node.value.x();
                vertical = false;
            } else {
                left = point.y() < next_node.value.y();
                vertical = true;
            }
            if (left) {
                next_node = next_node.left;
            } else {
                next_node = next_node.right;
            }
        }
        if (left){
            prev_node.left = new Node(point);
        } else {
            prev_node.right = new Node(point);
        }
    }

    // does the set contain the point p?
    public boolean contains(Point2D point) {
        Node next_node = root;
        boolean vertical = true;
        boolean left;

        while (next_node != null){
            if (next_node.value.x() == point.x() && next_node.value.y() == point.y()) {
                return true;
            }
            if (vertical) {
                left = point.x() < next_node.value.x();
                vertical = false;
            } else {
                left = point.y() < next_node.value.y();
                vertical = true;
            }
            if (left) {
                next_node = next_node.left;
            } else {
                next_node = next_node.right;
            }
        }
        return false;
    }

    // draw all of the points to standard draw
    public void draw() {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> points_in_rectangle = new SET<>();
        return range_recursive(points_in_rectangle, rect, root, true);
    }

    private SET<Point2D> range_recursive (SET<Point2D> points_in_rectangle, RectHV rect, Node next_node, boolean vertical){

        boolean in_left, in_right, both;

        if (next_node == null){
            return points_in_rectangle;
        }

        if (rect.contains(next_node.value)){
            points_in_rectangle.add(next_node.value);
        }
            both = rect.distanceSquaredTo(next_node.value) == 0;
            if (both) { in_left = in_right = true; }
            else {
                if (vertical) {
                    in_left = rect.xmax() < next_node.value.x();
                    in_right = rect.xmin() > next_node.value.x();
                } else {
                    in_left = rect.ymax() < next_node.value.x();
                    in_right = rect.ymin() > next_node.value.x();
                }
            }
            vertical = !vertical;

        if (in_left){
            points_in_rectangle = range_recursive(points_in_rectangle, rect, next_node.left, vertical);
        }
        if (in_right){
            points_in_rectangle = range_recursive(points_in_rectangle, rect, next_node.right, vertical);
        }
        return points_in_rectangle;
    }


    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D point) {
        return nearest_recursive(point, root, true, root.value);
    }

    private Point2D nearest_recursive(Point2D p, Node next_node, boolean vertical, Point2D nearest_point) {
        boolean in_left;

        if (p.distanceSquaredTo(next_node.value) > p.distanceSquaredTo(nearest_point)){
            return nearest_point;
        } else {
            nearest_point = next_node.value;
        }

        if (vertical) {
            in_left = p.x() < next_node.value.x();
        } else {
            in_left = p.y() < next_node.value.y();
        }

        vertical = !vertical; // for every depth of the recursion, orientation of the line is flipped
        if (in_left){
            if (next_node.left == null){
                return next_node.value;
            }
            nearest_point = nearest_recursive(p, next_node.left, vertical, nearest_point);
        } else {
            if (next_node.right == null){
                return next_node.value;
            }
            nearest_point = nearest_recursive(p, next_node.right, vertical, nearest_point);
        }
        return nearest_point;
    }

    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        In input = new In(args[0]);
        KdTree our_kd_tree = new KdTree();
        int number_of_lines = input.readInt();
        for(int i = 0; i < number_of_lines; i++){
            double x = input.readDouble();
            double y = input.readDouble();
            Point2D point = new Point2D(x,y);
            our_kd_tree.insert(point);
        }

        Point2D point_to_find = new Point2D(0.2,	0.8);
        StdOut.println(our_kd_tree.contains(point_to_find));
        StdOut.println(our_kd_tree.size());

        RectHV my_rectangle = new RectHV(0.2, 0.2, 0.4, 0.6);
        Iterable<Point2D> my_square_points = our_kd_tree.range(my_rectangle);

        Point2D test_point = new Point2D(0.1, 0.1);
        Point2D fancy_point = our_kd_tree.nearest(test_point);
        StdOut.println(fancy_point);


//        our_kd_tree.draw();
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
