package com.company;

public class Node {

    private double x;
    private double y;
    private int nNodes;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
        nNodes++;
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
}
