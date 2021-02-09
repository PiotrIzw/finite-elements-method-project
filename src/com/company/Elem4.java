package com.company;

public class Elem4 {

    private final double point = 1.0 / Math.sqrt(3.0);
    private final double[] ksi = new double[]{-point, point, point, -point};
    private final double[] eta = new double[]{-point, -point, point, point};

    private final double[][] ksiDerivatives = new double[4][4];
    private final double[][] etaDerivatives = new double[4][4];

    private final double[][] jacobiMatrix = new double[4][4];

    private final double[] x = new double[]{0.0, 4.0, 4.0, 0.0};
    private final double[] y = new double[]{0.0, 0.0, 6.0, 6.0};

    private final double[] detJ = new double[4];

    private final double[][] dNAfterDX = new double[4][4];
    private final double[][] dNAfterDY = new double[4][4];

    public final double[][] hLocal = new double[4][4];

    public Elem4(){
        calculateKsiAndEtaDerivatives();
        calculateJacobiMatrix();
        calculateDetJ();
        calculateInverseJacobiMatrix();
        calculateHMatrix();
    }

    public void calculateKsiAndEtaDerivatives(){
        for(int i = 0; i < ksiDerivatives.length; i++){
            ksiDerivatives[i][0] = - (1.0 / 4.0) * (1.0 - eta[i]);
            ksiDerivatives[i][1] = (1.0 / 4.0) * (1.0 - eta[i]);
            ksiDerivatives[i][2] = (1.0 / 4.0) * (1.0 + eta[i]);
            ksiDerivatives[i][3] = - (1.0 / 4.0) * (1.0 + eta[i]);

            etaDerivatives[i][0] = - (1.0 / 4.0) * (1.0 - ksi[i]);
            etaDerivatives[i][1] = - (1.0 / 4.0) * (1.0 + ksi[i]);
            etaDerivatives[i][2] = (1.0 / 4.0) * (1.0 + ksi[i]);
            etaDerivatives[i][3] = (1.0 / 4.0) * (1.0 - ksi[i]);

            //System.out.println("eta \t" + eta[i]);
            System.out.println(ksiDerivatives[i][0] + "\t" + ksiDerivatives[i][1] + "\t" + ksiDerivatives[i][2] + "\t" + ksiDerivatives[i][3]);
            //System.out.println(etaDerivatives[i][0] + "\t" + etaDerivatives[i][1] + "\t" + etaDerivatives[i][2] + "\t" + etaDerivatives[i][3]);
        }
    }

    public void calculateJacobiMatrix(){
        for (int i = 0; i < jacobiMatrix.length; i++){
            for (int j = 0; j < jacobiMatrix.length; j++) {
                jacobiMatrix[i][0] += ksiDerivatives[i][j] * x[j]; // derivative x/ksi
                jacobiMatrix[i][1] += etaDerivatives[i][j] * y[j]; // derivative y/eta
                jacobiMatrix[i][2] += etaDerivatives[i][j] * x[j]; // derivative x/eta
                jacobiMatrix[i][3] += ksiDerivatives[i][j] * y[j]; // derivative y/ksi
            }
        }

        System.out.println("Jacobi matrix - integration point nr.1");
        System.out.println("[ " + jacobiMatrix[0][0] + "\t" + jacobiMatrix[0][3] + " ]");
        System.out.println("[ " + jacobiMatrix[0][2] + "\t" + jacobiMatrix[0][1] + " ]");
    }

    public void calculateDetJ() {
        for (int i = 0; i < detJ.length; i++) {
                detJ[i] = (jacobiMatrix[i][0] * jacobiMatrix[i][1]) - ((jacobiMatrix[i][2] * jacobiMatrix[i][3]));
        }

        System.out.println("Matrix detJ determinants - integration points 1 - 4");
        System.out.println(detJ[0]);
        System.out.println(detJ[1]);
        System.out.println(detJ[2]);
        System.out.println(detJ[3]);

    }

    public void calculateInverseJacobiMatrix(){

        for(int i = 0; i < 4; i++){

            for(int j=0; j < 4; j++){

                dNAfterDX[i][j] = (1 / detJ[j]) * ((jacobiMatrix[j][1] * ksiDerivatives[i][j])
                        + (-jacobiMatrix[j][3] * etaDerivatives[i][j])); // ksi, eta [i][0]

                dNAfterDY[i][j] = (1 / detJ[j]) * ((-jacobiMatrix[j][2] * ksiDerivatives[i][j])
                        + (jacobiMatrix[j][0] * etaDerivatives[i][j]));
            }


        }
        System.out.println("Derivatives after x - integration point 1");
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++) {
                System.out.print(dNAfterDX[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Derivatives after y - integration point 1");
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(dNAfterDY[i][j] + "\t");
            }
            System.out.println();
        }
    }


    public void calculateHMatrix() {
        double[][] tempMatrixDx = new double[4][4];
        double[][] tempMatrixDy = new double[4][4];

        double[][] hMatrix = new double[4][4];

        for(int k = 0; k < 4; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    tempMatrixDx[i][j] = dNAfterDX[k][i] * dNAfterDX[k][j];
                    tempMatrixDy[i][j] = dNAfterDY[k][i] * dNAfterDY[k][j];
                    hMatrix[i][j] = 30 * (tempMatrixDx[i][j] + tempMatrixDy[i][j]) * detJ[i];
                }
            }

            for (int y = 0; y < 4; y++) {
                for(int z = 0; z < 4; z++) {
                    hLocal[z][y] += hMatrix[z][y];
                }
            }
        }
        System.out.println();
        System.out.println("H matrix local");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(hLocal[i][j] + " ");
                }
                System.out.println();
            }
    }
}
