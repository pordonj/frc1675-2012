/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.driver.XBoxController;


/**
 *
 * @author jpordon
 */
public class PresetFinderShooterSystem {
    
    private static final double COOLDOWN = 0.25;

        
    private Shooter shooter;
    private Hood hood;
    private Turret turret;
    private XBoxController controller;
    
    private boolean stepAvailable;
    private int currentAngle;
    private Timer timer;
    
    public PresetFinderShooterSystem(Shooter shooter, Hood hood, Turret turret, XBoxController controller){
        this.shooter = shooter;
        this.hood = hood;
        this.turret = turret;
        this.controller = controller;
        
        stepAvailable = true;
        currentAngle = 10;
        timer = new Timer();
    }
    
    public void doTeleop(){
        handleStepping();
        if(controller.getAButton()){
            goHome();
        } else if (controller.getBButton()){
            goToAngle();
        } else {
            manualHood();
        }
        shooter.doShooter();
    }
    
    private void handleStepping() {
        if(stepAvailable){
           
            if(controller.getLeftBumperButton()){
                addStep();
            } else if(controller.getRightBumperButton()){
                subtractStep();
            }
        } else {
            checkCooldown();
        }
        SmartDashboard.putInt("Stepping Angle", currentAngle);
    }

    private void addStep() {
        currentAngle += 2;
        startCooldown();
    }
    
    private void subtractStep() {
        if(currentAngle >= 0){
            currentAngle -= 2;
            startCooldown();
        }
    }

    private void startCooldown() {
        stepAvailable = false;
        
        timer.start();
    }

    private void checkCooldown() {
        if(timer.get() > COOLDOWN){
            stopCooldown();
        }
    }

    private void stopCooldown() {
        stepAvailable = true;
        timer.stop();
    }

    private void goHome() {
        hood.setAngle(0);
    }

    private void goToAngle() {
        hood.setAngle(currentAngle);
    }

    private void manualHood() {
        hood.doHood();
    }
    
    
}
