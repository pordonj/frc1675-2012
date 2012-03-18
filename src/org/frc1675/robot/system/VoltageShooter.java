/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author team1675
 */
public class VoltageShooter implements Shooter {

    private static final double MAX_VOLTAGE = 12.0;
    private SpeedController shooterMotor;

    public VoltageShooter(SpeedController shooterMotor){
        this.shooterMotor = shooterMotor;
    }

    public boolean doShooter() {
        return false;
    }

    public boolean setSpeed(double speed) {
        double currentVoltage = DriverStation.getInstance().getBatteryVoltage();
        double voltageScalar = MAX_VOLTAGE / currentVoltage;
        shooterMotor.set(speed * voltageScalar);
        return false; // we dont know if we are up to mspeed;
    }

}
