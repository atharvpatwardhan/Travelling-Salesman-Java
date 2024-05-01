public class Tour {

    private class Node {
        private Point p;
        private Node  next;

        public Node(Point P , Node next){
            this.next = next;
            this.p = P;
        }

        public Point getP() {
            return p;
        }

        public void setP(Point p) {
            this.p = p;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node(Point p){
            this.p = p;
            this.next = null;
        }
    }
    Node first;

    public        Tour(){
// creates an empty tour
    }
    public        Tour(Point a, Point b, Point c, Point d){// creates the 4-point tour a->b->c->d->a (for debugging)
        Node n1 = new Node(a);
        Node n2 = new Node(b,n1);
        Node n3 = new Node(c,n2);
        Node n4 = new Node(d,n3);
        n1.setNext(n4);

        this.first = n1;




    }
    public    int size(){ // returns the number of points in this tour
        Node start = first;
        Node next = start.next;
        int count = 0;
        while (start.next != first){
            count += 1;
            start = start.next;
        }

        return count + 1;
    }
    public double length()       { // returns the length of this tour
        Node start = first;
        double totalDistance = 0.0;
        while(start.next != first){
            Point p1 = start.getP();
            totalDistance += p1.distanceTo(start.next.getP());
            start = start.next;
        }
        totalDistance += start.getP().distanceTo(start.next.getP());
        return totalDistance;
    }
    public String toString()    {   // returns a string representation of this tour
        Node start = first;
        Node current = first.next;
        StringBuilder sb = new StringBuilder();
        sb.append(first.getP());
        while(current != first){
            sb.append(current.getP());
            current = current.next;
        }
        return sb.toString();
    }
    public   void draw()    {   // draws this tour to standard drawing
        Node start = first;
        Node current = start.next;
        start.p.drawTo(current.p);
        while(current != first){
            current.p.drawTo(current.next.p);
            current = current.next;
        }
    }
    public   void insertNearest(Point p)   {    // inserts p using the nearest neighbor heuristic

        if(first == null){
            Node newNode = new Node(p);
            first = newNode;
            return;
        }
        if(first.next == null){
            first.next = new Node(p);
            first.next.next = first;
            return;
        }
        Node start = first;
        double minDist = p.distanceTo(start.p);
        Node minNode = first;
        Node current = start.next;

        while(current!=first){
            double distance = p.distanceTo(current.p);
            if(distance<minDist){
                minDist = distance;
                minNode = current;
            }
            current = current.next;
        }

        Node newNode = new Node(p, minNode.next);
        minNode.next = newNode;

    }
    public   void insertSmallest(Point p)   {   // inserts p using the smallest increase heuristic
        if(first == null){
            Node newFirstNode = new Node(p);
            first = newFirstNode;
            return;
        }
        if(first.next == null){
            first.next = new Node(p);
            first.next.next = first;
            return;
        }

        Node newNode = new Node(p);

        Node current = first.next;

        Node insert = first;
        first.next = newNode;
        newNode.next = current;
        double minLength = this.length();
        double length;
        first.next = current;

        Node next;

        while(current!=first){
            next = current.next;
            current.next = newNode;
            newNode.next = next;
            length = this.length();
            if(length<minLength){
                insert = current;
                minLength = length;
            }
            current.next = next;
            current = current.next;
        }
        next = insert.next;
        insert.next = newNode;
        newNode.next = next;

    }


    public static void main(String[] args){
        Point a = new Point(100.0, 100.0);
        Point b = new Point(500.0, 100.0);
        Point c = new Point(500.0, 500.0);
        Point d = new Point(100.0, 500.0);

        // create the tour a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, b, c, d);

        int size = squareTour.size();
        StdOut.println("Number of points = " + size);

        double length = squareTour.length();
        StdOut.println("Tour length = " + length);

        StdOut.println(squareTour);

        StdDraw.setXscale(0, 600);
        StdDraw.setYscale(0, 600);


        Point newPoint = new Point(150,450);

        squareTour.insertNearest(newPoint);

        squareTour.insertSmallest(newPoint);
        StdOut.println(squareTour);
        squareTour.draw();


    }
}