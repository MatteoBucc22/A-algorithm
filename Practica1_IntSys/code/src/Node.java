package practice1;

public class Node {
    int x, y;
    double f, g, h;
    Node parent;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.parent = null;
    }



    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }


    public boolean equals(Node a){
        if (x == a.x && y==a.y) return true;
        else return false;
    }
        
}