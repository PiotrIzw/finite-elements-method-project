package com.company;

import java.util.List;

public class Element {

    private List<Node> nodes;
    public static int elements = 0;

    public Element(List<Node> nodes) {
        this.nodes = nodes;
        elements++;

    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public static int getElements() {
        return elements;
    }

}