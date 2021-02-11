package com.company;

import java.util.ArrayList;
import java.util.List;

public class Grid2D {
    private List<Element> allElements = new ArrayList<>();
    private List<Node> allNodes = new ArrayList<>();
    private GlobalData globalData;
    private double H[][];
    private double C[][];

    public Grid2D(GlobalData globalData) {
        this.globalData = globalData;

        H = new double[globalData.getnN()][globalData.getnN()];
        C = new double[globalData.getnN()][globalData.getnN()];

        calculateNodes();
        saveElementsWithNodes();
    }

    private void saveElementsWithNodes() {

        for (int i = 0; i < globalData.getnW() - 1; i++) {
            for (int j = 0; j < globalData.getnH() - 1; j++) {
                ArrayList<Node> nodes = new ArrayList<>();
                nodes.add(allNodes.get((i * globalData.getnW()) + j));
                nodes.add(allNodes.get(((i + 1) * globalData.getnW()) + j));
                nodes.add(allNodes.get(((i + 1) * globalData.getnW()) + j + 1));
                nodes.add(allNodes.get((i * globalData.getnW()) + j + 1));
                allElements.add(new Element(nodes));
            }

        }
    }
    private void calculateNodes(){
        int x = 0, y = 0; // origin of the coordinate system
        for (int i = 0; i < globalData.getnW(); i++) {
            for (int j = 0; j < globalData.getnH(); j++) {
               // System.out.println(globalData.getnW() + " " + globalData.getnH());
                double nodeX = x + (i * (globalData.getW() / (globalData.getnW() - 1)));
                double nodeY = y + (j * (globalData.getH() / (globalData.getnH() - 1)));
                allNodes.add(new Node(nodeX, nodeY, GlobalData.getT0()));

               // System.out.println("x = " + nodeX + "; y =" + nodeY);
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
                    H[allElements.get(i).getNodes().get(j).getId() - 1][allElements.get(i).getNodes().get(k).getId() - 1] += allElements.get(i).getHLocal()[j][k];
                }
            }
        }

        printMatrix(H);
    }

    public void calculateCGlobal(){

        for (int i = 0; i < allElements.size(); i++) {
            for (int j = 0; j < allElements.get(i).getNodes().size(); j++) {
                for (int k = 0; k < allElements.get(i).getNodes().size(); k++) {
                    C[allElements.get(i).getNodes().get(j).getId() - 1][allElements.get(i).getNodes().get(k).getId() - 1] += allElements.get(i).getCLocal()[j][k];
                }
            }
        }
        printMatrix(C);
    }

    public void printMatrix(double[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                //if(matrix[i][j] == 0) System.out.format("0 ");
                System.out.format("%11f%4s", matrix[i][j], "");
            }
            System.out.println();
        }
    }
}