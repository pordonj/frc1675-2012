/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

/**
 *
 * @author team1675
 */
class Point {

    private double particleMassX;
    private double particleMassY;

    public Point (double particleMassX, double particleMassY){
        this.particleMassX = particleMassX;
        this.particleMassY = particleMassY;
    }
    public double getX(){
    return particleMassX;
    }
    public double getY(){
    return particleMassY;
    }
}
