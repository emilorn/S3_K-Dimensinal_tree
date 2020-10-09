
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
            size++;
        }
    }

    private void insert_into_position(Point2D point) {
        Node current_node = root;
        Node prev_node = null;
        boolean vertical = true;
        boolean left = true;

        while (current_node != null){
            if (current_node.value.x() == point.x() && current_node.value.y() == point.y()){
                return;
            }
            prev_node = current_node;
            if (vertical) {
                left = point.x() < current_node.value.x();
                vertical = false;
            } else {
                left = point.y() < current_node.value.y();
                vertical = true;
            }
            if (left) {
                current_node = current_node.left;
            } else {
                current_node = current_node.right;
            }
        }
        size++;
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
        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setXscale(-0.05, 1.05);
        StdDraw.setYscale(-0.05, 1.05);
        StdDraw.rectangle(0.5,0.5,0.5,0.5);
        RectHV root_rect = new RectHV(0, 0, 1, 1);
        draw_recur(root, root_rect);
    }
    private void draw_recur(Node current_node, RectHV parent_rect){
        if(current_node != null){
            StdDraw.setPenColor(StdDraw.BLACK);
            //StdDraw.textLeft(current_node.value.x()+0.01, current_node.value.y()+0.02, current_node.value.toString());
            StdDraw.setPenRadius(0.01);
            StdDraw.point(current_node.value.x(), current_node.value.y());
//            StdDraw.circle(current_node.value.x(), current_node.value.y(), .007);
            StdDraw.setPenRadius();

            if(current_node.vertical){
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(current_node.value.x(), parent_rect.ymin(), current_node.value.x(), parent_rect.ymax());
            }
            else{
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(parent_rect.xmin(), current_node.value.y(), parent_rect.xmax(), current_node.value.y());
            }

            draw_recur(current_node.left, get_point_rect(current_node.left, parent_rect));
            draw_recur(current_node.right, get_point_rect(current_node.right, parent_rect));
        }
    }

    public void draw2() {

        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setXscale(-0.05, 1.05);
        StdDraw.setYscale(-0.05, 1.05);
        StdDraw.rectangle(0.5,0.5,0.5,0.5);


        Font font = new Font("Comic Sans MS", Font.ITALIC, 10);
        StdDraw.setFont(font);
//        StdDraw.setPenColor(StdDraw.RED);
//        StdDraw.line(root.value.x(), 0, root.value.x(), 1);
        draw_recur2(root);
    }

    private void draw_recur2(Node current_node){
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
                    StdDraw.line(current_node.value.x(), top, current_node.value.x(), bottom);
                }
                else {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    if (current_node == current_node.parent.right) {
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
            draw_recur2(current_node.left);
            draw_recur2(current_node.right);
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        RectHV root_rect = new RectHV(0, 0, 1, 1);
        SET<Point2D> points_in_rectangle = new SET<>();
        return range(points_in_rectangle, rect, root, root_rect);
    }

    private SET<Point2D> range(SET<Point2D> points_in_rectangle, RectHV rect, Node current_node, RectHV parent_rect){

        boolean in_left, in_right, both;

        if (current_node != null){
            RectHV new_rect = get_point_rect(current_node, parent_rect);

            if (rect.contains(current_node.value)){
                points_in_rectangle.add(current_node.value);
            }
            both = rect.intersects(new_rect);
            if (both) {
                in_left = in_right = true;
            }
            else {
                if (current_node.vertical) {
                    in_left = rect.xmax() < current_node.value.x();
                    in_right = rect.xmin() > current_node.value.x();
                } else {
                    in_left = rect.ymax() < current_node.value.y();
                    in_right = rect.ymin() > current_node.value.y();
                }
            }

            if (in_left){
                points_in_rectangle = range(points_in_rectangle, rect, current_node.left, parent_rect);
            }
            if (in_right){
                points_in_rectangle = range(points_in_rectangle, rect, current_node.right, parent_rect);
            }
        }
        return points_in_rectangle;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D point){
        RectHV root_rect = new RectHV(0, 0, 1, 1);
        Node nearest_node = nearest(root, root, point, root_rect);
        return nearest_node.value;
    }

    private Node nearest(Node current_node, Node nearest_node, Point2D destination, RectHV parent_rect) {
        if (current_node != null) {

            RectHV new_rect = get_point_rect(current_node, parent_rect);

            if (current_node.value.distanceSquaredTo(destination) < nearest_node.value.distanceSquaredTo(destination)){
                nearest_node = current_node;
            }
            if (new_rect.distanceSquaredTo(destination) <= nearest_node.value.distanceSquaredTo(destination)) {
                Node left = nearest(current_node.left, nearest_node, destination, new_rect);
                Node right = nearest(current_node.right, nearest_node, destination, new_rect);
                if (left.value.distanceSquaredTo(destination) <= right.value.distanceSquaredTo(destination)){
                    nearest_node = left;
                } else {
                    nearest_node = right;
                }
            } else {
                return nearest_node;
            }
        }
        return nearest_node;
    }

    private RectHV get_point_rect(Node node, RectHV parent_rect){
        double rect_x_min;
        double rect_x_max;
        double rect_y_min;
        double rect_y_max;
        if (node == root){
            return parent_rect;
        }
        if(node == null){
            return parent_rect;
        }
        if(node.vertical){
            if(node == node.parent.left){
                rect_x_min = parent_rect.xmin();
                rect_x_max = parent_rect.xmax();
                rect_y_min = parent_rect.ymin();
                rect_y_max = node.parent.value.y();
            }
            else{
                rect_x_min = parent_rect.xmin();
                rect_x_max = parent_rect.xmax();
                rect_y_min = node.parent.value.y();
                rect_y_max = parent_rect.ymax();
            }
        }else{
            if(node == node.parent.left){
                rect_x_min = parent_rect.xmin();
                rect_x_max = node.parent.value.x();
                rect_y_min = parent_rect.ymin();
                rect_y_max = parent_rect.ymax();
            }
            else{
                rect_x_min = node.parent.value.x();
                rect_x_max = parent_rect.xmax();
                rect_y_min = parent_rect.ymin();
                rect_y_max = parent_rect.ymax();
            }
        }
        return new RectHV(rect_x_min, rect_y_min, rect_x_max, rect_y_max);
    }

    public static void count_nearest(String[] args, int n){
        In input = new In(args[0]);
        int N = n;
        Point2D[] point_arr = new Point2D[N];
        KdTree new_tree = new KdTree();

        for(int i = 0; !input.isEmpty(); i++){
            double x = input.readDouble();
            double y = input.readDouble();
            point_arr[i] = new Point2D(x,y);
        }
        for (Point2D point2D : point_arr) {
            new_tree.insert(point2D);
        }

        int counter = 0;
        Stopwatch OneSecond = new Stopwatch();
        while (OneSecond.elapsedTime() < 1){
            new_tree.nearest(new Point2D(StdRandom.uniform(), StdRandom.uniform()));
            counter++;
        }
        StdOut.println(counter);
    }

    public static void build_tree(int n, int t){
        int N = n;
        int T = t;
        double[] timeArr = new double[T];
        KdTree our_kd_tree;
        for (int i = 0; i < timeArr.length; i++){
            our_kd_tree = new KdTree();
            Point2D[] point_arr = new Point2D[N];
            for (int j = 0; j < N; j++){
                double x = StdRandom.uniform();
                double y = StdRandom.uniform();
                point_arr[j] = new Point2D(x, y);
            }
            Stopwatch watch = new Stopwatch();
            for (int j = 0; j < point_arr.length; j++){
                our_kd_tree.insert(point_arr[j]);
            }
            timeArr[i] = watch.elapsedTime();
        }
        double timeMean = StdStats.mean(timeArr);
        StdOut.println("Building tree of "+N+" Size took "+timeMean+" seconds");
    }



    /*******************************************************************************
     * Test client
     ******************************************************************************/

    public static void main(String[] args) {
        int n = 1000000;
        int t = 100;
        for (int i = 0; i < t; i++){
            KdTree.count_nearest(args, n);
        }
        KdTree.build_tree(n, t);
    }
}
