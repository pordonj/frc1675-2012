/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.driver.XBoxController;
import org.frc1675.ups2012.SmartDashboardOutputProfiler;

/**
 *
 * @author Josh
 */
public class SteppingShooter implements Shooter{

    private SpeedController shooterMotor;
    private XBoxController controller;
    
    private static final double COOLDOWN = 0.25;
    
    private int currentStep;
    private Timer timer;
    private boolean stepAvailable;
    
    public SteppingShooter(SpeedController shooterMotor, XBoxController controller){
        this.shooterMotor = shooterMotor;
        this.controller = controller;
        currentStep = 0;
        timer = new Timer();
        stepAvailable = true;
    }
    
    public boolean doShooter(){
        handleStepping();
        SmartDashboardOutputProfiler.getInstance().putDouble("Driver", "Shooter Speed: ", shooterMotor.get());
        return controller.getXButton();
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
        
        shooterMotor.set(0.05 * (double)currentStep);
//        SmartDashboard.putInt("Step", currentStep);
    }

    private void addStep() {
        if(currentStep < 20){
            currentStep++;
            startCooldown();
        }
    }
    
    private void subtractStep() {
        if(currentStep > 0){
            currentStep--;
            startCooldown();
        }
    }

    private void startCooldown() {
        stepAvailable = false;
        timer.reset();
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

    public boolean setSpeed(double Speed) {
    return true;
    }
    
}
