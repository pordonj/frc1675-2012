/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import org.frc1675.driver.XBoxController;
import org.frc1675.robot.component.TwoMotorGearbox;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.driver.TankController;
/**
 *
 * @author kempensp/
 */
public class SimpleTankDrive implements TankDrive{
    private static double LEFT_LOW = 20.0;
    private static double RIGHT_LOW = 135.0;
    private static double LEFT_HIGH = 135.0;
    private static double RIGHT_HIGH = 0.0;

    private static final double RAMP_TIME = 0.5;
    
    private TankController driveController;
    private SpeedController leftSide;
    private SpeedController rightSide;
    private Servo leftShifter;
    private Servo rightShifter;
    private Timer leftTimer;
    private Timer rightTimer;
    
    private boolean previousShiftingState;
    private boolean shiftersCentered;
    
    public SimpleTankDrive(TankController driveController, SpeedController leftSide, SpeedController rightSide, Servo leftShifter, Servo rightShifter){
        
        
        this.driveController = driveController;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.leftShifter = leftShifter;
        this.rightShifter = rightShifter;
        
        previousShiftingState = false;
        shiftersCentered = true;
        leftShifter.setAngle(LEFT_HIGH);
        rightShifter.setAngle(RIGHT_HIGH);
        leftTimer = new Timer();
        rightTimer = new Timer();
        leftTimer.start();
        rightTimer.start();
        
    }
    
    public void driveTeleop(){
        handleShifting();
        double speedLeft = driveController.getRawLeftSpeed();
        double speedRight = driveController.getRawRightSpeed();
        if(speedLeft == 0.0){
            leftTimer.reset();
        } else if (leftTimer.get() < RAMP_TIME){
            speedLeft = speedLeft * (leftTimer.get() / RAMP_TIME);
        }
        if(speedRight == 0.0){
            rightTimer.reset();
        } else if (rightTimer.get() < RAMP_TIME){
            speedRight = speedRight * (rightTimer.get() / RAMP_TIME);
        }
        speedLeft = -speedLeft;
        speedRight = -speedRight;
        leftSide.set(speedLeft);
        rightSide.set(speedRight);
    }

    private void handleShifting() {
        boolean leftBumperPushed = driveController.getShiftButton();
//        SmartDashboard.putBoolean("left bumper: ", leftBumperPushed);
//        SmartDashboard.putBoolean("prev shift state: ", previousShiftingState);
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
    
}
