/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author team1675
 */
public class PIDHoodSource implements PIDSource{

    private Encoder encoder;
    private DigitalInput limitSwitch;

    public PIDHoodSource(Encoder encoder, DigitalInput limitSwitch){
        this.encoder = encoder;
        this.limitSwitch = limitSwitch;
        encoder.setDistancePerPulse(1.0);
        encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
    }

    public void startEncoder(){
        encoder.start();
    }

    public void resetEncoder(){
        encoder.reset();
    }

    public void stopEncoder(){
        encoder.stop();
    }

    public boolean getLimitSwitch(){
        return limitSwitch.get();
    }

    public int getEncoderCount(){
        return encoder.get();
    }

    public double pidGet() {
        if(limitSwitch.get()){
            return 0.0;
        } else {
            return encoder.pidGet();
        }
    }

}
