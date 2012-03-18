/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author team1675
 */
public class PIDHoodOutput implements PIDOutput {

    private SpeedController hoodMotor;
    private DigitalInput limitSwitch;

    public PIDHoodOutput(SpeedController hoodMotor, DigitalInput limitSwitch){
        this.hoodMotor = hoodMotor;
        this.limitSwitch = limitSwitch;
    }

    public void pidWrite(double output) {
        if(limitSwitch.get() && output > 0.0){
            hoodMotor.pidWrite(0.0);
        } else {
            hoodMotor.pidWrite(output);
        }
    }

}
