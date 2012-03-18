/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.driver.TankController;
import org.frc1675.driver.XBoxController;
import org.frc1675.ups2012.SmartDashboardOutputProfiler;

/**
 *
 * @author jpordon
 */
public class EncoderTankDrive {
    private static double LEFT_HIGH = 20.0;
    private static double RIGHT_HIGH = 135.0;
    private static double LEFT_LOW = 135.0;
    private static double RIGHT_LOW = 20.0;
    
    private TankController driveController;
    private SpeedController leftSide;
    private SpeedController rightSide;
    private Servo leftShifter;
    private Servo rightShifter;
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    
    private SendablePIDController leftPID;
    private SendablePIDController rightPID;
    
    private boolean previousShiftingState;
    private boolean shiftersCentered;
    
    public EncoderTankDrive(TankController driveController, SpeedController leftSide, SpeedController rightSide, Servo leftShifter, Servo rightShifter, Encoder leftEncoder, Encoder rightEncoder){
        
        
        this.driveController = driveController;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.leftShifter = leftShifter;
        this.rightShifter = rightShifter;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        
        previousShiftingState = false;
        shiftersCentered = true;
        leftShifter.setAngle(LEFT_HIGH);
        rightShifter.setAngle(RIGHT_HIGH);
        
        leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        
        leftPID = new SendablePIDController(0.5, 0.0, 0.0, leftEncoder, leftSide);
        rightPID = new SendablePIDController(0.5, 0.0, 0.0, rightEncoder, rightSide);
        
    }
    
    public void driveTeleop(){
        handleShifting();
        double speedLeft = driveController.getRawLeftSpeed();
        double speedRight = driveController.getRawRightSpeed();
        leftSide.set(speedLeft);
        rightSide.set(speedRight);
        dashboardDisplay();
    }

    private void dashboardDisplay() {
        String shiftText;
        if (shiftersCentered == false){
            shiftText = "Low Gear";
        }
        else{ 
            shiftText = "High Gear";
        }
        SmartDashboardOutputProfiler.getInstance().putString("Driver", "GearState", shiftText);
    }

    private void handleShifting() {
        boolean leftBumperPushed = driveController.getShiftButton();
        if(leftBumperPushed){
            if(previousShiftingState == false){
                //rising edge -- shift
                System.out.println("shift!");
                shift();
               
            }
        }
        
        previousShiftingState = leftBumperPushed;
    }

    private void shift() {
        if(shiftersCentered){
            shiftersCentered = false;
            leftShifter.setAngle(LEFT_LOW);
            rightShifter.setAngle(RIGHT_LOW);
        } else {
            shiftersCentered = true;
            leftShifter.setAngle(LEFT_HIGH);
            rightShifter.setAngle(RIGHT_HIGH);
        }
    }
    
    public void resetPIDs(){
        leftPID.reset();
        rightPID.reset();
    }
    
    public void driveForwardInches(double inches){
        leftPID.setSetpoint(inches);
        leftPID.setSetpoint(inches);
    }
    
}
