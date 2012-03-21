/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.driver.XBoxController;
import org.frc1675.robot.component.SourceCounter;

/**
 * Used for help in finding presets.
 * @author Josh
 */
public class PIDSteppingShooter implements Shooter {
    private static final double PID_MIN_OUT = 0.0;
    private static final double PID_MAX_OUT = 1.0;
    private static final double PID_MIN_IN = 0.0;
    private static final double PID_MAX_IN = 5000.0;
    
    private static final double PID_TOLERANCE_PCT = 2.0; //1.0 = 1%
    
    private static final double P = 0.0020;
    private static final double I = 0.00015;
    private static final double D = 0.0001;
    
    private SendablePIDController pid;
    private SourceCounter counterSource;
    private Timer errorResetTimer;
    private XBoxController controller;
    private boolean stepAvailable;
    private int currentStep;
    private Timer timer;
    private static final double COOLDOWN = 0.25;
    
    public PIDSteppingShooter(SpeedController shooterMotor, Counter counter, XBoxController controller){
        this.counterSource = new SourceCounter(counter);
        this.counterSource.start();
        errorResetTimer = new Timer();
        errorResetTimer.start();
        timer = new Timer();
        timer.start();
        pid = new SendablePIDController(P, I, D, counterSource, shooterMotor);
        pid.setOutputRange(PID_MIN_OUT, PID_MAX_OUT);
        pid.setInputRange(PID_MIN_IN, PID_MAX_IN);
        pid.setTolerance(PID_TOLERANCE_PCT);
        this.controller = controller;
        pid.enable();
        SmartDashboard.putData("Shooter send pid", pid);
    }
    
    public boolean doShooter() {
        SmartDashboard.putDouble("Counter PID Get", fix2(counterSource.pidGet()));
        SmartDashboard.putData("Shooter send pid", pid);
        double speed = handleStepping();
        pid.setSetpoint(speed);
        SmartDashboard.putInt("Step", currentStep);
        boolean onTarget = pid.onTarget();
        SmartDashboard.putBoolean("On Target Speed", onTarget);
        return onTarget;
    }

    public boolean setSpeed(double speed) {
        if(errorResetTimer.get() > 5.0){
            errorResetTimer.reset();
            pid.reset();
            pid.enable();
        }
        SmartDashboard.putDouble("Counter PID Get", fix2(counterSource.pidGet()));
        SmartDashboard.putData("Shooter send pid", pid);
        pid.setSetpoint(speed);
        SmartDashboard.putDouble("Shooter PID", fix2(pid.get()));
        boolean onTarget = pid.onTarget();
        SmartDashboard.putBoolean("On Target Speed", onTarget);
        SmartDashboard.putDouble("Shooter Error", pid.getError());
        return onTarget;
    }

    private double fix2(double pidGet) {
        int tempInt = (int) (pidGet * 100);
        return (double)tempInt / 100.0;
    }

    private double handleStepping() {
        if(stepAvailable){
             System.out.println("Step Available");
            if(controller.getLeftBumperButton()){
                addStep();
            } else if(controller.getRightBumperButton()){
                subtractStep();
            }
        } else {
            checkCooldown();
        }
        
        double rpmFromStepping = 100.0 * (double)currentStep;
        
        if(rpmFromStepping != 0){
            rpmFromStepping += 2400.0;
        }
        
       return (rpmFromStepping);
    }
    
    private void addStep() {
        if(currentStep < 25){
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
}
