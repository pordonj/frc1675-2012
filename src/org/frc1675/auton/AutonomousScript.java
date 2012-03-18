/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.auton;

/**
 * Class to let us separate the concerns of autonomous. Implementing classes should
 * take all robot systems used in constructor and treat doAutonomous as autonomousPeriodic
 * @author Josh
 */
public interface AutonomousScript {
    
    /**
     * Loops just like autonomousPeriodic in IterativeRobot.
     */
    public void doAutonomous();
}
