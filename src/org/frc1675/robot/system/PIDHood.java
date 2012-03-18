/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.driver.XBoxController;

/**
 *
 * @author team1675
 */
public class PIDHood implements Hood {

    private static final double P = 0.25;
    private static final double I = 0.0;
    private static final double D = 0.0;


    private SendablePIDController pid;
    private PIDHoodSource source;
    private XBoxController controller;
    private PIDHoodOutput output;


    public PIDHood(PIDHoodSource source, PIDHoodOutput output){
        this.source = source;
        this.output = output;
        source.resetEncoder();
        source.startEncoder();
        pid = new SendablePIDController(P, I, D, source, output);
        pid.setInputRange(-60, 0);
        pid.setOutputRange(-.66, .4);
        pid.setTolerance(0.0);
        pid.enable();
//        SmartDashboard.putData("hoodPIDSender", pid);
    }

    public void doHood() {
        
    }

    public boolean setAngle(int angle) {
        
//        SmartDashboard.putBoolean("hoodLimit", source.getLimitSwitch());
//        SmartDashboard.putInt("HoodEncoder", source.getEncoderCount());
        pid.setSetpoint(-angle);
//        SmartDashboard.putDouble("Hood PID Get", pid.get());
        return pid.onTarget();

     }

}
