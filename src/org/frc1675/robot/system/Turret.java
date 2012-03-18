/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

/**
 *
 * @author Josh
 */
public interface Turret {
    
    public void doTurret();

    public void moveClockwise();

    public void moveCounterclockwise();

    public void stop();

    public boolean isAcceptableClockwiseRange();
    
    public boolean isAcceptableCounterclockwiseRange();

}
