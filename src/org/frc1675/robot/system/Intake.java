/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import org.frc1675.driver.XBoxController;

/**
 * Intake
 * @author team1675
 */
public class Intake {

    private XBoxController intakeController;
    private boolean currentState;
    private static final boolean INTAKE_IN = true;
    private static final boolean INTAKE_OUT = false;
    private SpeedController intakeMotor;
    private static final double BACKWARD_SPEED = -1.0;
    private static final double FORWARD_SPEED = 1.0;
    private static final double STOP_SPEED = 0.0;
    private Timer intakeTimer;
    private Timer switchDirectionDelayTimer;

    private static final double BUTTON_DELAY = 0.5;
    private static final double SWITCH_DELAY = 0.5;

    private Timer automaticIntakeTimerForward;
    private Timer automaticIntakeTimerReverse;


    public Intake(SpeedController intakeMotor, XBoxController intakeController){
        this.intakeMotor = intakeMotor;
        this.currentState = true;
        this.intakeController = intakeController;
        intakeTimer = new Timer();
        switchDirectionDelayTimer = new Timer();
        automaticIntakeTimerForward = new Timer();
        automaticIntakeTimerReverse = new Timer();
    }

    public void doIntake(){
        doManualIntake2();
    }

    // nEEVER  user in rea life because you might die unless that is you rgoar.
//    public void doManualIntake(){
//
//        if(switchDirectionDelayTimer.get() == 0.0)
//        {
//            if(intakeController.getBButton() && (intakeTimer.get() == 0.0  || intakeTimer.get() >= 0.5)){
//                currentState = !currentState;
//                switchDirectionDelayTimer.start();
//                if(intakeTimer.get() == 0.0){
//                    intakeTimer.start();
//                }else if(intakeTimer.get() >= 0.5){
//                    intakeTimer.stop();
//                    intakeTimer.reset();
//                }
//            }
//        }
//
//        if(switchDirectionDelayTimer.get() < 1.0 && switchDirectionDelayTimer.get() != 0.0)
//        {
//            intakeStop();
//        }
//        else{
//            switchDirectionDelayTimer.stop();
//            switchDirectionDelayTimer.reset();
//            if(currentState){
//                intakeForward();
//            }else if(!currentState){
//                intakeReverse();
//            }
//        }
//    }


    
    public void doManualIntake2(){
        
        if(intakeController.getXButton() && intakeTimer.get()== 0 ){
            currentState = !currentState;            
            switchDirectionDelayTimer.stop();
            switchDirectionDelayTimer.reset();
            switchDirectionDelayTimer.start();  
            intakeTimer.start();
        }       
        
        if(intakeTimer.get() >= BUTTON_DELAY){
            intakeTimer.stop();                
            intakeTimer.reset();            
        }
        
        if(switchDirectionDelayTimer.get()< SWITCH_DELAY && intakeTimer.get() != 0.0){
            intakeStop();
        }else{               
            if(currentState == INTAKE_IN){
                intakeForward();                
            }else if(currentState == INTAKE_OUT){
                intakeReverse();                
            }
        }       
    }
    
    public void intakeStop(){
        intakeMotor.set(STOP_SPEED);
    }

    public void intakeForward(){
        this.currentState = INTAKE_IN;
        intakeMotor.set(FORWARD_SPEED);
    }

    public void intakeReverse(){
        this.currentState = INTAKE_OUT;
        intakeMotor.set(BACKWARD_SPEED);
    }




    public void doAutomaticIntakeForward(){
        if(automaticIntakeTimerForward.get() == 0){
            automaticIntakeTimerForward.start();
        }
        if(automaticIntakeTimerForward.get() < SWITCH_DELAY){
            intakeStop();
        }else{
            automaticIntakeTimerReverse.stop();
            automaticIntakeTimerReverse.reset();
            intakeForward();
        }
    }

    public void doAutomaticIntakeReverse(){
        if(automaticIntakeTimerReverse.get() == 0){
            automaticIntakeTimerReverse.start();
        }
        if(automaticIntakeTimerReverse.get() < SWITCH_DELAY){
            intakeStop();
        }else{
            automaticIntakeTimerForward.stop();
            automaticIntakeTimerForward.reset();
            intakeReverse();
        }
    }
    
    public boolean isIntakeIn(){
        return currentState == INTAKE_IN;
    }    
    
    public boolean isIntakeOut(){
        return currentState == INTAKE_OUT;
    }
}
