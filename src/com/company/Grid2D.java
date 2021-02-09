package com.company;

import java.util.ArrayList;
import java.util.List;

public class Grid2D {
    private List<Element> allElements = new ArrayList<>();
    private List<Node> allNodes = new ArrayList<>();
    private GlobalData globalData;
    private double H[][];

    public Grid2D(GlobalData globalData) {
        this.globalData = globalData;

        H = new double[globalData.getnN()][globalData.getnN()];


        calculateNodes();
        saveElementsWithNodes();
    }

    private void saveElementsWithNodes(){
        for (int i = 0; i < globalData.getnW() - 1; i++) {
            for (int j = 0; j < globalData.getnH() - 1; j++) {
                List<Node> nodes = new ArrayList<>();

                int index = (i * globalData.getnH()) + j;
                int index1 = (i * globalData.getnH()) + j + 1;
                int index2 = ((i + 1) * globalData.getnH()) + j + 1;
                int index3 = ((i + 1) * globalData.getnH()) + j;

                nodes.add(allNodes.get(index));
                nodes.add(allNodes.get(index1));
                nodes.add(allNodes.get(index2));
                nodes.add(allNodes.get(index3));

                allElements.add(new Element(nodes));
                //System.out.println(index + " " + index1 + " " + index2 + " " + index3);
            }


        }

    }

    private void calculateNodes() {
        int x = 0, y = 0; // origin of the coordinate system
        for (int i = 0; i < globalData.getnW(); i++) {
            for (int j = 0; j < globalData.getnH(); j++) {
                double nodeX = x + (i * (globalData.getW() / (globalData.getnW() - 1)));
                double nodeY = y + (j * (globalData.getH() / (globalData.getnH() - 1)));
                allNodes.add(new Node(nodeX, nodeY));
                //System.out.println("x = " + nodeX + "; y =" + nodeY);
            }
        }
    }

    public void getLastGridElement(){
        int lastElementIndex = globalData.getnE() - 1;
        Element lastElement = allElements.get(lastElementIndex);

        System.out.println("Element nr. " + (lastElementIndex + 1));

        for(Node node : lastElement.getNodes()){

            System.out.println("(" + node.getX() + ", " + node.getY() + ")");
        }



    }

    public void calculateHGlobal() {
        for (int i = 0; i < allElements.size(); i++) {
            for (int j = 0; j < allElements.get(i).getNodes().size(); j++) {
                for (int k = 0; k < allElements.get(i).getNodes().size(); k++) {
                    H[allElements.get(i).getNodes().get(j).getId() - 1][allElements.get(i).getNodes().get(k).getId() - 1] += allElements.get(i).HLocal[j][k];
                }
            }
        }
        printMatrix(H);
    }
}