/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.robot.component.SourceCounter;
import org.frc1675.robot.component.TwoMotorGearbox;

/**
 *
 * @author jpordon
 */
public class PIDShooter implements Shooter {

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
    
    public PIDShooter(SpeedController shooterMotor, Counter counter){
        this.counterSource = new SourceCounter(counter);
        this.counterSource.start();
        errorResetTimer = new Timer();
        errorResetTimer.start();
        pid = new SendablePIDController(P, I, D, counterSource, shooterMotor);
        pid.setOutputRange(PID_MIN_OUT, PID_MAX_OUT);
        pid.setInputRange(PID_MIN_IN, PID_MAX_IN);
        pid.setTolerance(PID_TOLERANCE_PCT);

        pid.enable();
        SmartDashboard.putData("Shooter send pid", pid);
    }
    
    public boolean doShooter() {
        return false;
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
    
}
