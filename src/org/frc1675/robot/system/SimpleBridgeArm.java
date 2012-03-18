/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.driver.XBoxController;

/**
 *
 * @author Josh
 */
public class SimpleBridgeArm implements BridgeArm {

    Relay armRelay;
    XBoxController controller;
    DigitalInput bridgeIn;
    DigitalInput bridgeOut;
    
    public SimpleBridgeArm(Relay armRelay, XBoxController controller, DigitalInput bridgeIn, DigitalInput bridgeOut){
        this.armRelay = armRelay;
        this.controller = controller;
        this.bridgeIn = bridgeIn;
        this.bridgeOut = bridgeOut;
    }
    
    public void doBridgeArm() {
//        SmartDashboard.putBoolean("in hit", bridgeIn.get());
//        SmartDashboard.putBoolean("out hit", bridgeOut.get());

        if(controller.getDPadLeft() && bridgeOut.get()){
            armRelay.set(Relay.Value.kReverse);
        } else if(controller.getDPadRight() && bridgeIn.get()){
            armRelay.set(Relay.Value.kForward);
        } else {
            armRelay.set(Relay.Value.kOff);
        }
    }
    
    
    
}
