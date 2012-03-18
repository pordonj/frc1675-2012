/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.driver;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author jpordon
 */
public class JoystickTankController implements TankController {

    private static final double DEADZONE = 0.15;
    private Joystick leftStick;
    private Joystick rightStick;
    
    public JoystickTankController(Joystick leftStick, Joystick rightStick){
        this.leftStick = leftStick;
        this.rightStick = rightStick;
    }
    
    public double getRawLeftSpeed() {
        double speed = -leftStick.getY();
        if(speed < -DEADZONE || speed > DEADZONE){
            //don't change speed
        } else {
            speed = 0;
        }
        return speed;
    }

    public double getRawRightSpeed() {
        double speed = -rightStick.getY();
        if(speed < -DEADZONE || speed > DEADZONE){
            //don't change speed
        } else {
            speed = 0;
        }
        return speed;
    }

    public boolean getShiftButton() {
        return rightStick.getRawButton(2);
    }
    
}
