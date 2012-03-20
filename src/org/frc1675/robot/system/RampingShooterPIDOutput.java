/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author team1675
 */
public class RampingShooterPIDOutput implements PIDOutput{

    private static final double RAMP_TIME = 0.05;
    private SpeedController motor;
    private double lastValue;
    private Timer rampTimer;

    public RampingShooterPIDOutput(SpeedController motor){
        this.motor = motor;
        rampTimer = new Timer();
        lastValue = 0.0;
        rampTimer.start();
    }

    public void pidWrite(double output) {
        if(Math.abs(output - lastValue) > 0.000001){
            rampTimer.reset();
            lastValue = output;
        }
        double scalar = Math.min(rampTimer.get(), RAMP_TIME);
        motor.pidWrite(lastValue * (scalar / RAMP_TIME));
    }
}
