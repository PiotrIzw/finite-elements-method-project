package com.company;

public class Node {

    private double x;
    private double y;
    private static int nNodes;
    private int id;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
        nNodes++;
        this.id = Node.nNodes;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getnNodes() {
        return nNodes;
    }

    public void setnNodes(int nNodes) {
        this.nNodes = nNodes;
    }

    public int getId() {
        return id;
    }
}


