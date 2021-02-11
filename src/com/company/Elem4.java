package com.company;

public class Elem4 {

    private double point, point2, point3;
    private double[] ksi;
    private double[] eta;
    private double wages[];

    private double[][] ksiDerivatives;
    private double[][] etaDerivatives;

    private double[][] jacobiMatrix;

    private double[] x;
    private double[] y;

    private double[] detJ;

    private final double[][] dNAfterDX;
    private final double[][] dNAfterDY;

    private final double[][] hLocal;

    private double[][] N;


    public Elem4(double[] x, double[] y, int integrationSchema){
        this.x = x;
        this.y = y;

        if(integrationSchema == 2) {
            wages = new double[]{1, 1};
            point = 1.0 / Math.sqrt(3.0);
            ksi = new double[]{-point, point, point, -point};
            eta = new double[]{-point, -point, point, point};
        }
        else if(integrationSchema == 3) {
            wages = new double[]{5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0};
            point = -Math.sqrt(3.0 / 5.0);
            point2 = 0.0;
            point3 = Math.sqrt(3.0 / 5.0);
            ksi = new double[]{point, point2, point3, point, point2, point3, point, point2, point3};
            eta = new double[]{point, point, point, point2, point2, point2, point3, point3, point3};
        }
        else if(integrationSchema == 4) {
            wages = new double[]{((18.0 - Math.sqrt(30)) / 36.0), (18.0 + Math.sqrt(30.0)) / 36.0, (18.0 + Math.sqrt(30.0)) / 36.0, (18.0 - Math.sqrt(30.0)) / 36.0};
            point = Math.sqrt((3.0 / 7.0) - (2.0 / 7.0) * Math.sqrt(6.0 / 5.0));
            point2 = Math.sqrt((3.0 / 7.0) + (2.0 / 7.0) * Math.sqrt(6.0 / 5.0));
            eta = new double[]{
                    -point2, -point2, -point2, -point2,
                    -point, -point, -point, -point,
                    point, point, point, point,
                    point2, point2, point2, point2
            };
            ksi = new double[]{
                    -point2, -point, point, point2,
                    -point2, -point, point, point2,
                    -point2, -point, point, point2,
                    -point2, -point, point, point2
            };

        }
        else{
            System.err.println("Wrong integrationSchema size.");
            System.exit(0);
        }
        detJ = new double[ksi.length];
        ksiDerivatives = new double[ksi.length][x.length];
        etaDerivatives = new double[ksi.length][x.length];
        jacobiMatrix = new double[ksi.length][x.length];
        dNAfterDX = new double[ksi.length][x.length];
        dNAfterDY = new double[ksi.length][x.length];
        hLocal = new double[ksi.length][x.length];

        N = new double[ksi.length][x.length];

        calculateKsiAndEtaDerivatives();
        calculateJacobiMatrix();
        calculateDetJ();
        calculateShapeFunctions();
        calculateInverseJacobiMatrix();
    }

    public void calculateKsiAndEtaDerivatives(){
        System.out.println("ksi derivatives");

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

    private void calculateShapeFunctions() {
        // Obliczanie funkcji kształtu
        for (int i = 0; i < ksi.length; i++) {
            N[i][0] = 0.25 * (1.0 - ksi[i]) * (1 - eta[i]);
            N[i][1] = 0.25 * (1.0 + ksi[i]) * (1 - eta[i]);
            N[i][2] = 0.25 * (1.0 + ksi[i]) * (1 + eta[i]);
            N[i][3] = 0.25 * (1.0 - ksi[i]) * (1 + eta[i]);
        }
    }

    public void calculateJacobiMatrix(){
        for (int i = 0; i < jacobiMatrix.length; i++){
            for (int j = 0; j < jacobiMatrix[i].length; j++) {
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
        System.out.println("Matrix detJ determinants - integration points 1 - " + (detJ.length));
        for (int i = 0; i < detJ.length; i++) {
                //detJ[i] = (-1) * ((jacobiMatrix[i][0] * jacobiMatrix[i][1]) - ((jacobiMatrix[i][2] * jacobiMatrix[i][3])));
                detJ[i] = (jacobiMatrix[i][0] * jacobiMatrix[i][1]) - ((jacobiMatrix[i][2] * jacobiMatrix[i][3]));
            System.out.println(detJ[i]);

        }

    }

    public void calculateInverseJacobiMatrix(){

        for(int i = 0; i < dNAfterDX.length; i++){

            for(int j=0; j < dNAfterDX[i].length; j++){

                dNAfterDX[i][j] = (1 / detJ[j]) * ((jacobiMatrix[j][1] * ksiDerivatives[i][j])
                        + (-jacobiMatrix[j][3] * etaDerivatives[i][j])); // ksi, eta [i][0]

                dNAfterDY[i][j] = (1 / detJ[j]) * ((-jacobiMatrix[j][2] * ksiDerivatives[i][j])
                        + (jacobiMatrix[j][0] * etaDerivatives[i][j]));
            }


        }
        System.out.println("Derivatives after x - integration point 1");
        for (int i = 0; i < dNAfterDX.length; i++){
            for (int j = 0; j < dNAfterDX[i].length; j++) {
                System.out.print(dNAfterDX[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Derivatives after y - integration point 1");
        for(int i = 0; i < dNAfterDY.length; i++) {
            for (int j = 0; j < dNAfterDY[i].length; j++) {
                System.out.print(dNAfterDY[i][j] + "\t");
            }
            System.out.println();
        }
    }


    public double[][] calculateHMatrix() {
        double[][] tempMatrixDx = new double[4][4];
        double[][] tempMatrixDy = new double[4][4];

        double[][] hMatrix = new double[4][4];
        double[] matrixWages = calculateWages();

        for(int k = 0; k < ksi.length; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    tempMatrixDx[i][j] = dNAfterDX[k][i] * dNAfterDX[k][j];
                    tempMatrixDy[i][j] = dNAfterDY[k][i] * dNAfterDY[k][j];
                    hMatrix[i][j] = GlobalData.getK() * (tempMatrixDx[i][j] + tempMatrixDy[i][j]) * detJ[i];
                }
            }

            for (int y = 0; y < 4; y++) {
                for(int z = 0; z < 4; z++) {
                    hLocal[z][y] += hMatrix[z][y] * matrixWages[k];
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
            return hLocal;
    }


    public double[][] calculateCMatrix() {
        double[][] tempMatrixN = new double[4][4];

        double[][] cMatrix = new double[4][4];
        double[] matrixWages = calculateWages();

        double[][] cMatrixLocal = new double[4][4];

        for(int k = 0; k < ksi.length; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    tempMatrixN[i][j] = N[k][i] * N[k][j];
                    cMatrix[i][j] = GlobalData.getRo() * GlobalData.getCp() * tempMatrixN[i][j] * detJ[i];
                }
            }

            for (int y = 0; y < 4; y++) {
                for(int z = 0; z < 4; z++) {
                    cMatrixLocal[z][y] += cMatrix[z][y] * matrixWages[k];
                }
            }
        }
        System.out.println();
        System.out.println("C matrix local");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(cMatrixLocal[i][j] + " ");
            }
            System.out.println();
        }
        return cMatrixLocal;
    }

    private double[] calculateWages() {
        double[] tempWages = new double[ksi.length];
        int index = 0;
        for (int i = 0; i < GlobalData.getIntegrationSchema(); i++) {
            for (int j = 0; j < GlobalData.getIntegrationSchema(); j++) {
                tempWages[index] = wages[i] * wages[j];
                index++;
            }
        }
        return tempWages;
    }
}
