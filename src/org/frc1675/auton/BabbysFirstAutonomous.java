/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.auton;

import edu.wpi.first.wpilibj.Timer;
import org.frc1675.robot.component.Storage;
import org.frc1675.robot.system.RotatingPresetShooterSystem;

/**
 *
 * @author Josh
 */
public class BabbysFirstAutonomous implements AutonomousScript{

    private static final double SECONDS_UNTIL_START = 2.0;
    private static final double BALL_WAIT = 5.0;
    private static final double BALL_WAIT_2 = 3.0;

    private RotatingPresetShooterSystem shooterSystem;
    private Storage storage;
    private Timer autonTimer;
    private boolean timerStarted = false;

    public BabbysFirstAutonomous(RotatingPresetShooterSystem shooterSystem, Storage storage){
        this.shooterSystem = shooterSystem;
        this.storage = storage;
        autonTimer = new Timer();
        timerStarted = false;
    }

    public void doAutonomous() {
        System.out.println("Doing Babby's First Autonomous!");
        if(!timerStarted){
            autonTimer.start();
            timerStarted = true;
        }

        shooterSystem.explicitPresetChange(RotatingPresetShooterSystem.KEY);
        shooterSystem.doTeleop();
        boolean shooting = false;
        if(autonTimer.get() < BALL_WAIT){
            shooting = false;
        } else if(autonTimer.get() < BALL_WAIT + 1.0){
            shooting = true;
        } else if(autonTimer.get() < BALL_WAIT + BALL_WAIT_2){
            shooting = false;
        } else if(autonTimer.get() < 14.0){
            shooting = true;
        } else if(autonTimer.get() < 16.0){
            shooting = false;
        }

        storage.doManual(shooting);
    }
    
}
