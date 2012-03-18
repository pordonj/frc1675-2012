/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.driver;

/**
 *
 * @author jpordon
 */
public class XBoxTankController implements TankController {

    private XBoxController controller;
    
    public XBoxTankController(XBoxController controller){
        this.controller = controller;
    }
    
    public double getRawLeftSpeed() {
        return controller.getLeftYAxis();
    }

    public double getRawRightSpeed() {
        return controller.getRightYAxis();
    }

    public boolean getShiftButton() {
        return controller.getLeftBumperButton();
    }
    
}
