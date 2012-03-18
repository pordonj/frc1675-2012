package org.frc1675.robot.system;


import org.frc1675.driver.XBoxController;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lil' Sis
 */
public class Elevator{
    private XBoxController liftController;
    private Relay leftLift;
    private Relay rightLift;
    private Timer upTimerDelay;
    private Timer downTimerDelay;
    
    private static final double TIMER_DELAY = 0.25;



    private int currentState;

    private static final int RESTING = 0;
    private static final int GOING_UP = 1;
    private static final int GOING_DOWN = 2;

    
    public Elevator(Relay leftLift, Relay rightLift, XBoxController liftController){
       this.leftLift = leftLift;
       this.rightLift = rightLift;
       this.liftController = liftController;
       this.leftLift.setDirection(Relay.Direction.kBoth);
       this.rightLift.setDirection(Relay.Direction.kBoth);
       this.upTimerDelay = new Timer();
       this.downTimerDelay = new Timer();
       this.currentState = RESTING;
    }
    
    public void doElevator(boolean shooting){
        doManualElevator(shooting);
    }
    
    private void doAutomaticElevator(boolean shooting){
        if(shooting){
            autoElevatorShooting();
        } else {
            autoElevatorNotShooting();
        }
    }

    private void doManualElevator(boolean shooting){
        if(shooting){
            manualElevatorShooting();
        } else {
            manualElevatorNotShooting();
        }
    }

    public boolean isElevatorControlling()
    {
        if(liftController.getRightYAxis() > 0.5)
        {
            return true;
        }
        else if(liftController.getRightYAxis() < -0.5)
        {
            return true;
        }
        return false;
    }

    private void manualElevatorNotShooting() {
        if(liftController.getRightYAxis() > 0.5)
        {
            elevatorUp();
        }
        else if(liftController.getRightYAxis() < -0.5)
        {
            elevatorDown();
        }
        else{
            elevatorStop();
        }
//        if(liftController.getYButton() && upTimerDelay.get() < TIMER_DELAY ){
//            
//            elevatorUp();
//            if(upTimerDelay.get() == 0){
//                upTimerDelay.start();
////                downTimerDelay.stop();
////                downTimerDelay.reset();
//            }
//            
//        }else if(liftController.getAButton()  && downTimerDelay.get() < TIMER_DELAY ){
//            
//            elevatorDown();
//            if(downTimerDelay.get() == 0){
//                downTimerDelay.start();
////                upTimerDelay.stop();
////                upTimerDelay.reset();
//            }
//            
//        }else if((liftController.getYButton() || liftController.getAButton()) &&
//                 (upTimerDelay.get() > TIMER_DELAY ) || downTimerDelay.get() > TIMER_DELAY ){
//            
//            elevatorStop();
//            downTimerDelay.stop();
//            downTimerDelay.reset();
//            upTimerDelay.stop();
//            upTimerDelay.reset();
//        }
    }
    
    public void elevatorUp(){
        leftLift.set(Relay.Value.kReverse);
        rightLift.set(Relay.Value.kReverse);        
        currentState = GOING_UP;
    }
    
    public void elevatorDown(){
        leftLift.set(Relay.Value.kForward);
        rightLift.set(Relay.Value.kForward);        
        currentState = GOING_DOWN;
    }
    
    public void elevatorStop(){
        leftLift.set(Relay.Value.kOff);
        rightLift.set(Relay.Value.kOff);        
        currentState = RESTING;
    }

    public boolean isMoving()
    {
        //return true or false based on whetehr the lifts are active/set
        return currentState != RESTING;
    }

    public boolean isGoingUp()
    {
        return currentState == GOING_UP;
    }

    public boolean isGoingDown()
    {
        return currentState == GOING_DOWN;
    }

    private void manualElevatorShooting() {
        elevatorUp();
    }

    private void autoElevatorNotShooting() {
    }

    private void autoElevatorShooting() {
    }
}

