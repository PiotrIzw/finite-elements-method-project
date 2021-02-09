package com.company;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class GlobalData {
    private double H; // height
    private double W; // width
    private int nH; // number of nodes in height
    private int nW; // number of nodes in width
    private int nE; // number of all elements
    private int nN; // number of all nodes
    private static int npc;
    private static double K;
    public GlobalData(String filePath) {

        File file;
        Scanner scanner;

        try {
            file = new File(filePath);
            scanner = new Scanner(file);

            if (scanner.hasNextLine()) {
                H = scanner.nextDouble();
                scanner.nextLine();

                W = scanner.nextDouble();
                scanner.nextLine();

                nH = scanner.nextInt();
                scanner.nextLine();

                nW = scanner.nextInt();
                scanner.nextLine();

                npc = scanner.nextInt();
                scanner.nextLine();

                K = scanner.nextDouble();

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
            nE = (nH - 1) * (nW - 1);
            nN = nH * nW;
        }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double getW() {
        return W;
    }

    public void setW(double w) {
        W = w;
    }

    public int getnH() {
        return nH;
    }

    public void setnH(int nH) {
        this.nH = nH;
    }

    public int getnW() {
        return nW;
    }

    public void setnW(int nW) {
        this.nW = nW;
    }

    public int getnE() {
        return nE;
    }

    public void setnE(int nE) {
        this.nE = nE;
    }

    public int getnN() {
        return nN;
    }

    public void setnN(int nN) {
        this.nN = nN;
    }

    public static int getNpc() {
        return npc;
    }

    public static double getK() {
        return K;
    }
}


