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
public class SimpleHood implements Hood{

    private SpeedController hoodMotor;
    private XBoxController controller;
    
    public SimpleHood(SpeedController hoodMotor, XBoxController controller){
        this.hoodMotor = hoodMotor;
        this.controller = controller;
    }
    
    public void doHood() {
        double input = controller.getRightYAxis();
        double output = 0.0;
        if(input < -.15 || input > .15){
            output = 0.05 * input;
        }
        hoodMotor.set(output);
    }

    public boolean setAngle(int angle) {
        return true;
    }
    
}
