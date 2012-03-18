/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.driver.XBoxController;

/**
 *
 * @author Josh
 */
public class RotatingPresetShooterSystem {

    public static final int STOP = 0;
    public static final int KEY = 1;
    public static final int FENDER_HIGH = 2;
    public static final int FENDER_MID = 3;
    public static final int BRIDGE = 4;
    public static final int MAX = 5;

    private static final double STOP_RPM = 0.0;
    private static final double STOP_PCT = 0.0;

    private static final int KEY_ANGLE = 24;
    private static final double KEY_RPM = 2800.0;
    private static final double KEY_PCT = 0.46;
    
    private static final int FENDER_HIGH_ANGLE = 8;
    private static final double FENDER_HIGH_RPM = 2300.0;
    private static final double FENDER_HIGH_PCT = 0.36;
    
    private static final int FENDER_MID_ANGLE = 40;
    private static final double FENDER_MID_RPM = 1800.0;
    private static final double FENDER_MID_PCT = 0.26;
    
    private static final int BRIDGE_ANGLE = 32;
    private static final double BRIDGE_RPM = 3600.0;
    private static final double BRIDGE_PCT = 0.60;
    
    private static final int MAX_ANGLE = 35;
    private static final double MAX_RPM = 5000.0;
    private static final double MAX_PCT = 1.00;
    
    private static final double NUDGE_RPM = 100.0;
    private static final double NUDGE_PCT = 0.02;

    private boolean pctMode;

    private boolean angleReady;
    private boolean speedReady;
    
    private int currentNudgeLevel;
    
    private Shooter shooter;
    private Hood hood;
    private Turret turret;
    private XBoxController controller;
    
    private int potentialPreset;
    private int currentPreset;
    
    private int currentAngle;
    private double currentSpeed;
    
    private Timer buttonTimer;
    
    public RotatingPresetShooterSystem(Shooter shooter, Hood hood, Turret turret, XBoxController controller){
        this.shooter = shooter;
        this.hood = hood;
        this.turret = turret;
        this.controller = controller;
        angleReady = false;
        speedReady = false;
        currentNudgeLevel = 0;
        buttonTimer = new Timer();
        currentPreset = STOP;
        potentialPreset = STOP;
        currentAngle = 0;
        currentSpeed = 0.0;
        pctMode = (shooter instanceof VoltageShooter);
        buttonTimer.start();
    }
    
    /**
     * Lets the operator rotate between presets, engage a preset, and shoot.
     * @return whether robot is shooting.
     */
    public boolean doTeleop(){
        //if buttons are cooled down
        if(buttonTimer.get() > 0.25){
            handleNudges();
            handlePresetRotation();
            handleEngage();
        }
        SmartDashboard.putString("Current Preset", getPresetString(currentPreset));
        SmartDashboard.putString("Next Preset", getPresetString(potentialPreset));
//        SmartDashboard.putInt("Current Nudge", currentNudgeLevel);
        angleReady = hood.setAngle(currentAngle);
        speedReady = false;
        if(currentPreset == STOP){
            shooter.setSpeed(0.0);
        } else {
            if(!pctMode){
                speedReady = shooter.setSpeed(currentSpeed + (NUDGE_RPM * currentNudgeLevel));
            } else {
                speedReady = false;
                shooter.setSpeed(currentSpeed + (NUDGE_PCT * currentNudgeLevel));
            }
        }
//        SmartDashboard.putBoolean("Hood Ready", angleReady);
//        SmartDashboard.putBoolean("Shooter Ready", speedReady);
        handleTurret();
        return handleShooting();
        
    }

    public boolean getAngleReady(){
        return angleReady;
    }

    public boolean getSpeedReady(){
        return speedReady;
    }

    public boolean isReadyToShoot(){
        return angleReady && speedReady;
    }

    private void handleNudges() {
        if(controller.getLeftBumperButton()){
            currentNudgeLevel++;
            buttonTimer.reset();
        } else if (controller.getRightBumperButton()){
            currentNudgeLevel--;
            buttonTimer.reset();
        }
    }

    private void handlePresetRotation() {
        if(controller.getYButton()){
            presetForwardRotation();
            buttonTimer.reset();
        } else if (controller.getAButton()){
            presetBackwardRotation();
            buttonTimer.reset();
        }
    }

    private void handleEngage() {
        if(controller.getBButton()){
            currentPreset = potentialPreset;
            setupAngleAndSpeed(currentPreset);
            buttonTimer.reset();
        }
    }

    private void handleTurret() {
        turret.doTurret();
    }

    private boolean handleShooting() {
        return controller.getXButton();
    }

    private String getPresetString(int preset) {
        switch(preset){
            case STOP:
                return "Stop";
            case FENDER_HIGH:
                return "H. Fender";
            case FENDER_MID:
                return "M. Fender";
            case KEY:
                return "Key";
            case BRIDGE:
                return "Bridge";
            case MAX:
                return "Max";
            default:
                return "Broken";
        }
    }

    private void presetForwardRotation() {
        potentialPreset++;
        if(potentialPreset == 6){
            potentialPreset = 0;
        }
    }

    private void presetBackwardRotation() {
        potentialPreset--;
        if(potentialPreset == -1){
            potentialPreset = 5;
        }
    }

    private void setupAngleAndSpeed(int preset) {
        setupAngle(preset);
        setupSpeed(preset);
        
    }

    /**
     * Explicitly changes the current preset to the argued preset. Used for autonomous.
     * @param preset
     */
    public void explicitPresetChange(int preset){
        currentPreset = preset;
        setupAngleAndSpeed(currentPreset);
    }

    private void setupAngle(int preset) {
        switch(preset){
            case STOP:
                currentSpeed = STOP_RPM;
                break;
            case FENDER_HIGH:
                currentAngle = FENDER_HIGH_ANGLE;
                break;
            case FENDER_MID:
                currentAngle = FENDER_MID_ANGLE;
                break;
            case KEY:
                currentAngle = KEY_ANGLE;
                break;
            case BRIDGE:
                currentAngle = BRIDGE_ANGLE;
                break;
            case MAX:
                currentAngle = MAX_ANGLE;
                break;
            default:
                break;
        }
    }

    private void setupSpeed(int preset) {
        if(!pctMode){
            switch(preset){
            case STOP:
                currentSpeed = STOP_RPM;
                break;
            case FENDER_HIGH:
                currentSpeed = FENDER_HIGH_RPM;
                break;
            case FENDER_MID:
                currentSpeed = FENDER_MID_RPM;
                break;
            case KEY:
                currentSpeed = KEY_RPM;
                break;
            case BRIDGE:
                currentSpeed = BRIDGE_RPM;
                break;
            case MAX:
                currentSpeed = MAX_RPM;
                break;
            default:
                break;
            }
        } else {
            switch(preset){
            case STOP:
                currentSpeed = STOP_PCT;
                break;
            case FENDER_HIGH:
                currentSpeed = FENDER_HIGH_PCT;
                break;
            case FENDER_MID:
                currentSpeed = FENDER_MID_PCT;
                break;
            case KEY:
                currentSpeed = KEY_PCT;
                break;
            case BRIDGE:
                currentSpeed = BRIDGE_PCT;
                break;
            case MAX:
                currentSpeed = MAX_PCT;
                break;
            default:
                break;
            }
        }
    }
    
    
}
