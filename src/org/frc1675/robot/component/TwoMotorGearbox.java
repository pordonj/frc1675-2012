/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.component;

import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author lundtk
 */
public class TwoMotorGearbox implements SpeedController {

    SpeedController motorA;
    SpeedController motorB;
    boolean reversed;

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }
    
    public TwoMotorGearbox(SpeedController motorA, SpeedController motorB, boolean reversed){
        this.motorA = motorA;
        this.motorB = motorB;
        this.reversed = reversed;
    }
    
    public double get() {
        return motorA.get();
    }

    public void set(double speed, byte syncGroup) {
        if(reversed){
            speed = -speed;
        }
        motorA.set(speed, syncGroup);
        motorB.set(speed, syncGroup);
    }

    public void set(double speed) {
        if(reversed){
            speed = -speed;
        }
        motorA.set(speed);
        motorB.set(speed);
    }

    public void disable() {
        motorA.disable();
        motorB.disable();
    }

    public void pidWrite(double output) {
        motorA.pidWrite(output);
        motorB.pidWrite(output);
    }
    
}
