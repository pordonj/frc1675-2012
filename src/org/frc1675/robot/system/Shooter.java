/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

/**
 *
 * @author Josh
 */
public interface Shooter {
    
    /**
     * Class-specific implementation of normal shooter behavior.
     * @return whether the robot should be shooting balls
     */
    public boolean doShooter();


    public boolean setSpeed (double speed);

    }
