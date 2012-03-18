/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author Tony, Mike, Trevor
 */
public class EncoderTurret implements Turret {
    private SpeedController turretMotor;
    private Encoder turretEncoder;
    private int acceptableClockwiseTurretRange;
    private int acceptableCounterclockwiseTurretRange;
    private double turretMotorClockwisePower;
    private double turretMotorCounterclockwisePower;
    
    
    public EncoderTurret(SpeedController turretMotor, Encoder turretEncoder, double turretMotorClockwisePower, double turretMotorCounterclockwisePower, int acceptableClockwiseTurretRange, int acceptableCounterclockwiseTurretRange ){
        this.turretMotor = turretMotor;
        this.turretEncoder = turretEncoder;
        this.acceptableClockwiseTurretRange = Math.abs(acceptableClockwiseTurretRange);
        this.acceptableCounterclockwiseTurretRange = -Math.abs(acceptableCounterclockwiseTurretRange);
        this.turretMotorClockwisePower = turretMotorClockwisePower;
        this.turretMotorCounterclockwisePower = turretMotorCounterclockwisePower;
    }

    public void doTurret() {
    }

    public void moveClockwise() {
        turretMotor.set (turretMotorClockwisePower);
    }

    public void moveCounterclockwise(){
        turretMotor.set(turretMotorCounterclockwisePower);
    }

    public void stop(){
        turretMotor.set(0.0);
    }

    public boolean isAcceptableClockwiseRange(){
        return turretEncoder.get() < acceptableClockwiseTurretRange;
    }

    public boolean isAcceptableCounterclockwiseRange(){
        return turretEncoder.get() > acceptableCounterclockwiseTurretRange;
    }
}
