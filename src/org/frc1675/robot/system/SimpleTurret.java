/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.SpeedController;
import org.frc1675.driver.XBoxController;

/**
 *
 * @author Josh
 */
public class SimpleTurret implements Turret{

    private SpeedController turretMotor;
    private XBoxController controller;
    
    public SimpleTurret(SpeedController turretMotor, XBoxController controller){
        this.turretMotor = turretMotor;
        this.controller = controller;
    }
    
    public void doTurret() {
        double input = controller.getLeftXAxis();
        if(input < -.15 || input > .15){
            turretMotor.set(input);
        } else {
            turretMotor.set(0.0);
        }
    }

    public void moveClockwise() {
    }

    public void moveCounterclockwise() {
    }

    public void stop() {
    }

    public boolean isAcceptableClockwiseRange() {
        return true;
    }

    public boolean isAcceptableCounterclockwiseRange() {
        return true;
    }
    
}
