package com.company;

import java.util.ArrayList;
import java.util.List;

public class GaussQuadratureIntegration {


    public double calculateTwoPoints(){

        double point = 1.0 / Math.sqrt(3.0);

        List<Node> integrationPoints = new ArrayList<>();

        integrationPoints.add(new Node(-point, -point));
        integrationPoints.add(new Node(point, -point));
        integrationPoints.add(new Node(point, point));
        integrationPoints.add(new Node(-point, point));

        double result = 0.0;

        for(Node node : integrationPoints){
            double x = node.getX();
            double y = node.getY();

            result += functionToCompute(x, y);
        }

        return result;
    }

    public double calculateThreePoints(){


        double point = Math.sqrt(3.0/5.0);

        double weight1 = 5.0/9.0;
        double weight2 = 8.0/9.0;

        List<Node> integrationPoints = new ArrayList<>();

        integrationPoints.add(new Node(-point, -point));
        integrationPoints.add(new Node(0, -point));
        integrationPoints.add(new Node(point, -point));

        integrationPoints.add(new Node(-point, 0));
        integrationPoints.add(new Node(0, 0));
        integrationPoints.add(new Node(point, 0));

        integrationPoints.add(new Node(-point, point));
        integrationPoints.add(new Node(0, point));
        integrationPoints.add(new Node(point, point));

        double result = 0.0;

        for(Node node : integrationPoints){
            double x = node.getX();
            double y = node.getY();

            if((node.getX() == 0 || node.getY() == 0) && node.getX() != node.getY()) {
                result += functionToCompute(x, y) * weight1 * weight2;
            }
            else if (node.getX() == 0 && (node.getX() == node.getY())){
                result += functionToCompute(x, y) * weight2 * weight2;
            }
            else{
                result += functionToCompute(x, y) * weight1 * weight1;
            }
        }

        return result;
    }


    public double functionToCompute(double x, double y){
        //return -2 * (x * x) * y + 2 * x * y + 4;
        return (-5 * (x * x) * y) + (2 * x * (y * y)) + 10;
    }
}
