package com.company.integration;

import com.company.Node;

import java.util.ArrayList;
import java.util.List;

public class GaussQuadratureIntegration {

    private class IntegrationNode{
        double x,y;

        public IntegrationNode(double x, double y){
            this.x = x;
            this.y = y;
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
    }


    public double calculateTwoPoints(){

        double point = 1.0 / Math.sqrt(3.0);

        List<IntegrationNode> integrationPoints = new ArrayList<>();

        integrationPoints.add(new IntegrationNode(-point, -point));
        integrationPoints.add(new IntegrationNode(point, -point));
        integrationPoints.add(new IntegrationNode(point, point));
        integrationPoints.add(new IntegrationNode(-point, point));

        double result = 0.0;

        for(IntegrationNode node : integrationPoints){
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

        List<IntegrationNode> integrationPoints = new ArrayList<>();

        integrationPoints.add(new IntegrationNode(-point, -point));
        integrationPoints.add(new IntegrationNode(0, -point));
        integrationPoints.add(new IntegrationNode(point, -point));

        integrationPoints.add(new IntegrationNode(-point, 0));
        integrationPoints.add(new IntegrationNode(0, 0));
        integrationPoints.add(new IntegrationNode(point, 0));

        integrationPoints.add(new IntegrationNode(-point, point));
        integrationPoints.add(new IntegrationNode(0, point));
        integrationPoints.add(new IntegrationNode(point, point));

        double result = 0.0;

        for(IntegrationNode node : integrationPoints){
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
