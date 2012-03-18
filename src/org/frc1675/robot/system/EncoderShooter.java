/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author team1675
 */
public class EncoderShooter implements Shooter {
    private Counter shooterCounter;
    private SpeedController shooterMotor;
    private static final int speedTolerance = 50;
    private Timer reportTimer;
    private long ticksPastSecond;
    private int rpm;
    private double accelerationIncrement;
    
    public EncoderShooter(Counter shooterCounter, double accelerationIncrement, SpeedController shooterMotor){
        this.shooterCounter = shooterCounter;
        this.reportTimer = new Timer();
        ticksPastSecond = 0;
        reportTimer.start();
        this.shooterCounter.start();
        rpm = 0;
        this.accelerationIncrement = accelerationIncrement;
        this.shooterMotor = shooterMotor;
    }

    public boolean doShooter() {
        return true;
    }

    public boolean setSpeed(double speed){
        calculateRPM();
        double shooterSpeed = shooterMotor.get();
        if (rpm < speed - speedTolerance){
            shooterMotor.set(shooterSpeed+accelerationIncrement);
            return false;
        }
        if (rpm > speed + speedTolerance){
            shooterMotor.set(shooterSpeed-accelerationIncrement);
            return false;
        }            
        return true;
    }

    private void calculateRPM() {
        if (reportTimer.get() > 1.0) {
            ticksPastSecond = shooterCounter.get();
            shooterCounter.reset();
            reportTimer.reset();
        }
        rpm = (int) (ticksPastSecond / 6L);

    }       
}



