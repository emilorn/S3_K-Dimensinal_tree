
/*************************************************************************
 *************************************************************************/

import edu.princeton.cs.algs4.*;

import java.awt.*;

public class KdTree {

    private static class Node {
        Node left;
        Node right;
        Node parent;
        Point2D value;
        boolean vertical;

        private Node (Point2D value, Node parent, boolean vertical){
            this.value = value;
            this.left = null;
            this.right = null;
            this.parent = parent;
            this.vertical = vertical;
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
            insert_into_position(point);
        } else {
            root = new Node(point, null, true);
        }
        size++;
    }

    private void insert_into_position(Point2D point) {
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
            prev_node.left = new Node(point, prev_node, vertical);
        } else {
            prev_node.right = new Node(point, prev_node, vertical);
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

        StdDraw.setCanvasSize(800,800);
        StdDraw.setXscale(-0.05, 1.05);
        StdDraw.setYscale(-0.05, 1.05);
        StdDraw.rectangle(0.5,0.5,0.5,0.5);


        Font font = new Font("Comic Sans MS", Font.ITALIC, 10);
        StdDraw.setFont(font);
//        StdDraw.setPenColor(StdDraw.RED);
//        StdDraw.line(root.value.x(), 0, root.value.x(), 1);
        draw_recursive(root);
    }

    private void draw_recursive(Node current_node){
        double top = 1.0;
        double bottom = 0.0;
        double left = 0.0;
        double right = 1.0;

        if(current_node != null){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.textLeft(current_node.value.x()+0.01, current_node.value.y()+0.02, current_node.value.toString());
            StdDraw.circle(current_node.value.x(), current_node.value.y(), .007);

            if(current_node.parent != null){
                if(current_node.vertical){
                    StdDraw.setPenColor(StdDraw.RED);
                    if(current_node == current_node.parent.left){
                        top = current_node.parent.value.y();
                        if(current_node.value.x() == 0.69){
                            StdOut.println();
                        }
                        Node next = current_node.parent.parent.parent;
                        double shortest_distance = 1;
                        double nearest_y = 1;
                        while (next != null){
                            if (!next.vertical && shortest_distance > Math.abs(next.value.y() - current_node.value.y())
                            && next.value.y() < current_node.value.y()){
                                shortest_distance = Math.abs(next.value.y() - current_node.value.y());
                                nearest_y = next.value.y();
                            }
                            next = next.parent;
                        }
                        if(current_node.value.y() > shortest_distance){
                            bottom = nearest_y;
                        }

                    }

                    else if (current_node == current_node.parent.right){

                        if(current_node.value.x() == 0.69){
                            StdOut.println();
                        }
                        bottom = current_node.parent.value.y();


                        Node next = current_node.parent.parent.parent;
                        double shortest_distance = 0;
                        double nearest_y = 1;
                        while (next != null){
                            if (!next.vertical && shortest_distance < Math.abs(next.value.y() - current_node.value.y())
                                    && next.value.y() > current_node.value.y()){
                                shortest_distance = Math.abs(next.value.y() - current_node.value.y());
                                nearest_y = next.value.y();
                            }
                            if(1 - current_node.value.y() > shortest_distance){
                                top = nearest_y;
                            }
                            next = next.parent;
                        }
                    }
                    StdDraw.line(current_node.value.x(), bottom, current_node.value.x(), top);

                }

                else {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    if (current_node == current_node.parent.right) {

                        if(current_node.value.x() == 0.5){
                            StdOut.println();
                        }
                        left = current_node.parent.value.x();
                        Node next = current_node.parent.parent;
                        double shortest_distance = 1;
                        double nearest_x = 1;
                        while (next != null) {
                            if (next.vertical && shortest_distance > Math.abs(next.value.x() - current_node.value.x())
                                    && next.value.x() >= current_node.value.x()) {
                                shortest_distance = Math.abs(next.value.x() - current_node.value.x());
                                nearest_x = next.value.x();
                            }
                            next = next.parent;
                        }
                        if(1 - current_node.value.x() > shortest_distance){
                            right = nearest_x;
                        }

                    }
                    else if (current_node == current_node.parent.left) {
                        if(current_node.value.x() == 0.5){
                            StdOut.println();
                        }
                        right  = current_node.parent.value.x();
                        Node next = current_node.parent.parent;
                        double shortest_distance = 1;
                        double nearest_x = 1;
                        while (next != null){
                            if (next.vertical && shortest_distance > Math.abs(next.value.x() - current_node.value.x())
                            && next.value.x() <= current_node.value.x()){
                                shortest_distance = Math.abs(next.value.x() - current_node.value.x());
                                nearest_x = next.value.x();
                            }
                            next = next.parent;
                        }
                        if(current_node.value.x() > shortest_distance){
                            left = nearest_x;
                        }
                    }
                    StdDraw.line(left, current_node.value.y(), right, current_node.value.y());

                        }
                    }
            else{
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(root.value.x(), 0, root.value.x(), 1);
            }
            draw_recursive(current_node.left);
            draw_recursive(current_node.right);
                }



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


        StdOut.println("Draw test begins here");
        our_kd_tree.draw();
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
