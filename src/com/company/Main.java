package com.company;

import com.company.integration.GaussQuadratureIntegration;

public class Main {

    public static void main(String[] args) {
        String fileDirectory = "src/com/company/resources/data.txt";


        /*
        Testing valid grid filling
         */
        GlobalData globalData = new GlobalData(fileDirectory);
        Grid2D grid2D = new Grid2D(globalData);
        grid2D.getLastGridElement();


        /*
        Testing valid gauss quadrature integration for:
        -two points integration
        -three points integration
         */
        System.out.println("Two points integration: " + new GaussQuadratureIntegration().calculateTwoPoints());
        System.out.println("Three points integration: " + new GaussQuadratureIntegration().calculateThreePoints());


        new Elem4();
    }

}
