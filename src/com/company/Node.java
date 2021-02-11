package com.company;

public class Node {

    private double x;
    private double y;
    public static int nNodes = 0;
    private int id;
    private double t0;

    public Node(double x, double y, double t0) {
        this.x = x;
        this.y = y;
        nNodes++;
        this.id = Node.nNodes;
        this.t0 = t0;
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

    public double getT0() {
        return t0;
    }
}


