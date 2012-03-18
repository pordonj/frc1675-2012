/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.component;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.robot.system.Elevator;
import org.frc1675.robot.system.Intake;
import org.frc1675.robot.system.UltrasonicWrapper;

/**
 *
 * @author team1675
 */
public class Storage {

    public static class StorageMode{
        public static final int REGULAR = 0;
        public static final int FEEDING = 1;
    }

    private Intake intake;
    private Elevator elevator;
    private static final int MAX_CAPACITY=3;
    private int ballCount;    
    private Timer optimizationTimer;
    private UltrasonicWrapper ultraSonicIntake;
    private UltrasonicWrapper ultraSonicBottomElevator;
    private UltrasonicWrapper ultraSonicTopElevator;
    private DigitalInput ballIn;
    private Timer intakeTimer;
    private int storageMode;

    public Storage (Intake intake, Elevator elevator, int ballCount, UltrasonicWrapper ultraSonicIntake, UltrasonicWrapper ultraSonicBottomElevator, UltrasonicWrapper ultraSonicTopElevator){
        this.intake = intake;
        this.elevator = elevator;
        this.ballCount = ballCount;
        optimizationTimer = new Timer();
        this.ultraSonicIntake = ultraSonicIntake;
        this.ultraSonicBottomElevator = ultraSonicBottomElevator;
        this.ultraSonicTopElevator = ultraSonicTopElevator;
        storageMode = StorageMode.REGULAR;
    }

    public Storage (Intake intake, Elevator elevator, int ballCount, UltrasonicWrapper ultraSonicIntake, UltrasonicWrapper ultraSonicBottomElevator, DigitalInput ballIn){
        this.intake = intake;
        this.elevator = elevator;
        this.ballCount = ballCount;
        optimizationTimer = new Timer();
        this.ultraSonicIntake = ultraSonicIntake;
        this.ultraSonicBottomElevator = ultraSonicBottomElevator;
        this.ballIn = ballIn;
        storageMode = StorageMode.REGULAR;
    }

    public void setStorageMode(int storageMode)
    {
        this.storageMode = storageMode;
    }

    public void manage(boolean shooting){
        evaluateSensors();
        updateBallCount();
        doOptimization(shooting);
        outputSensors();
    }

    public void doManual(boolean shooting){
        //evaluateSensors();
        intake.doIntake();
        elevator.doElevator(shooting);
        //outputSensors();
    }

    public void doManualFeeding()
    {
        intake.doAutomaticIntakeReverse();
        elevator.elevatorDown();
    }

    public int maxCapacity(){
        return MAX_CAPACITY;
    }

    public int getCurrentCount()
    {
        return ballCount;
    }

    public boolean isAtMaxCapacity()
    {
        return ballCount >= MAX_CAPACITY;
    }

    public boolean isEmpty()
    {
        return ballCount == 0;
    }

    public void prepareForExpellation(){
        evaluateSensors();
        updateBallCount();
    }

    public void chuckInventory(){
        evaluateSensors();
        updateBallCount();
    }

    private void doOptimization(boolean shooting){
        if(storageMode == StorageMode.REGULAR && !shooting){
            //if a ball reaches the top sensor, stop the elevator
//            if(ultraSonicTopElevator.isRisingEdge() && elevator.isGoingUp()){
//                elevator.elevatorStop();
//            }
            //a new ball just came in, so put it in the elevator
            if( elevator.isElevatorControlling()){
                elevator.doElevator(false);
            }
            else if(!ballIn.get() && intake.isIntakeIn())
            {
                elevator.elevatorUp();
                optimizationTimer.start();
            }
            else if(optimizationTimer.get() > 0.33){
                optimizationTimer.stop();
                optimizationTimer.reset();
                elevator.elevatorStop();
//                SmartDashboard.putString("Stopped by: ", "Timer");
            }
             else if( optimizationTimer.get() == 0.0){
                            elevator.elevatorStop();
             }
            //if the elevator is going up and a ball gets into the bottom slot, stop moving the elevator
//            else if(ultraSonicBottomElevator.isRisingEdge() && elevator.isGoingUp())
//            {
//                optimizationTimer.stop();
//                optimizationTimer.reset();
//                elevator.elevatorStop();
//                SmartDashboard.putString("Stopped by: ", "Ultrasonic");
//            }
            updateIntakeSystem();
        }else if(storageMode == StorageMode.REGULAR && shooting){
            elevator.elevatorUp();
        }else if(storageMode == StorageMode.FEEDING){
            doManualFeeding();
        }
        
    }

    private void evaluateSensors(){
        ultraSonicIntake.evaluate();
        ultraSonicBottomElevator.evaluate();
        //ultraSonicTopElevator.evaluate();
    }

    private void updateBallCount(){
        if(storageMode == StorageMode.REGULAR){
            if (ultraSonicIntake.isFallingEdge() && intake.isIntakeIn()){
                ballCount++;
            }
//            if (ultraSonicTopElevator.isFallingEdge() && elevator.isGoingUp()){
//                ballCount--;
//            }
        }else if(storageMode == StorageMode.FEEDING){
            if (ultraSonicIntake.isFallingEdge() && intake.isIntakeOut()){
                ballCount--;
            }
        }       
        
    }

    private void updateIntakeSystem(){
        if(isAtMaxCapacity()){
            intake.doAutomaticIntakeReverse();
        }else{
            intake.doAutomaticIntakeForward();
        }
    }

    private void outputSensors(){
//        SmartDashboard.putBoolean("Intake Detected: ", ultraSonicIntake.isDetecting());
////        SmartDashboard.putDouble("Intake distance: ", ultraSonicIntake.getRange());
//        SmartDashboard.putInt("Intake count: ", ultraSonicIntake.getCurrentCount());
//        SmartDashboard.putBoolean("Bottom Detected: ", ultraSonicBottomElevator.isDetecting());
//        SmartDashboard.putDouble("Bottom distance: ", ultraSonicBottomElevator.getRange());
//        SmartDashboard.putInt("Bottom count: ", ultraSonicBottomElevator.getCurrentCount());
////        SmartDashboard.putBoolean("Top Detected: ", ultraSonicTopElevator.isDetecting());
////        SmartDashboard.putDouble("Top distance: ", ultraSonicTopElevator.getRange());
//        SmartDashboard.putInt("Ball count: ", ballCount);
    }
}
