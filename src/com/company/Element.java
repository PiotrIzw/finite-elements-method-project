package com.company;

import java.util.List;

public class Element {

    private List<Node> nodes;
    public static int elements = 0;
    private double[][] HLocal;
    private double[][] CLocal;

    public Element(List<Node> nodes) {
        this.nodes = nodes;
        elements++;
        calculateProperties();
    }

    private void calculateProperties() {
        double[] x = new double[nodes.size()];
        double[] y = new double[nodes.size()];

        for (int j = 0; j < nodes.size(); j++) {
            x[j] = nodes.get(j).getX();
            y[j] = nodes.get(j).getY();
        }

        Elem4 elem4 = new Elem4(x, y, GlobalData.getIntegrationSchema());
        HLocal = elem4.calculateHMatrix();
        CLocal = elem4.calculateCMatrix();

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

    public double[][] getHLocal() {
        return HLocal;
    }

    public double[][] getCLocal() {
        return CLocal;
    }
}