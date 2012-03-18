/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import org.frc1675.driver.XBoxController;
import org.frc1675.ups2012.SmartDashboardOutputProfiler;

/**
 *
 * @author Josh
 */
public class EncoderDebugShooter extends SteppingShooter{

    //just counting revolutions - DPP = 1/360
    private static final double DPP = 0.002778;
    
    private Encoder encoder;
    
    public EncoderDebugShooter(SpeedController shooterMotor, XBoxController controller, Encoder encoder){
        super(shooterMotor, controller);
        
        
        encoder.setDistancePerPulse(DPP);
        encoder.start();
        this.encoder = encoder;
    }
    
    public boolean doShooter(){
        boolean retVal = super.doShooter();
        SmartDashboardOutputProfiler.getInstance().putDouble("encoder", "shooterEncoder", encoder.getRate());
        return retVal;
    }
    
}
