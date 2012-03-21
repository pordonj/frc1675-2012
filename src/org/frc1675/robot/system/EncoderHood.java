/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.driver.XBoxController;

/**
 *
 * @author Lil' Sis
 */
public class EncoderHood implements Hood{
    private static final double UP_SPEED_LOW = -0.6;
    private static final double UP_SPEED_HIGH = -0.5;
    private static final double DOWN_SPEED_LOW = 0.5;
    private static final double DOWN_SPEED_HIGH = 0.4;

    private SpeedController hoodMotor;
    private XBoxController controller;
    private Encoder encoder;
    private DigitalInput limitSwitch;
    
    
    public EncoderHood(SpeedController hoodMotor, XBoxController controller, Encoder encoder, DigitalInput limitSwitch){
        this.encoder = encoder;
        this.limitSwitch = limitSwitch;
        this.hoodMotor = hoodMotor;
        this.controller = controller;
        encoder.setDistancePerPulse(1.0);
        encoder.reset();
        encoder.start();
    }
    
    public void doHood() {
        if (limitSwitch.get()){
        encoder.reset();
        } 
//        SmartDashboard.putBoolean("hoodLimit", limitSwitch.get());
        SmartDashboard.putInt("HoodEncoder", encoder.get());
        double input = controller.getRightYAxis();
        double output = 0.0;
        if(input < -.15 || input > .15){
            output = input;
        }
        hoodMotorSet(output);
    }

    public boolean setAngle(int angle) {
//        SmartDashboard.putBoolean("hoodLimit", limitSwitch.get());
//        SmartDashboard.putInt("HoodEncoder", encoder.get());
        int encoderValue = encoder.get();
        if (limitSwitch.get()){
        encoder.reset();
        }             
        
        //move hood down
        if(encoderValue > angle && angle < 30){
            hoodMotorSet(DOWN_SPEED_LOW);
            return false;
        } else if(encoderValue > angle){
            hoodMotorSet(DOWN_SPEED_HIGH);
            return false;
        }
        //move hood up
        else if(encoderValue < angle && angle < 30){
            hoodMotorSet(UP_SPEED_LOW); //foward
            return false;
        } else if (encoderValue < angle){
            hoodMotorSet(UP_SPEED_HIGH); //foward
            return false;
        } else {
            hoodMotorSet(0);
            return true;
        }
         
     }
    
    private void hoodMotorSet (double output){
//        SmartDashboard.putDouble("Hood output sent", output);
        if (limitSwitch.get()&& output < 0.0){
            hoodMotor.set(output);

        } else if(limitSwitch.get()) {
            hoodMotor.set(0);

        } else {
            hoodMotor.set(output);
        }

    }
    
}
    
    

