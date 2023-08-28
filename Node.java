public class Node {
     private int g;
     private int h;
     private int f;
     private Point loc;
     private Node prior;

     public Node(int g, int h, int f, Point loc, Node prior){
         this.g = g;
         this.h = h;
         this.f = f;
         this.loc = loc;
         this.prior = prior;
     }

     public int getG(){
         return this.g;
     }

    public int getF(){
        return this.f;
    }

    public Point getLoc(){
        return this.loc;
    }

    public Node getPrior(){
        return this.prior;
    }

    public boolean equals(Object o){
         if (o == null)
             return false;
         if (o.getClass() != this.getClass())
             return false;

         Node n = (Node) o;
         return loc == n.loc;
    }
}
