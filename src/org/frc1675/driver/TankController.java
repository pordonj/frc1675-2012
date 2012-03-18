/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.driver;

/**
 *
 * @author jpordon
 */
public interface TankController {
    
    public double getRawLeftSpeed();
    public double getRawRightSpeed();
    public boolean getShiftButton();
}
