/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

/**
 * Represents expected behavior for the shooter's hood.
 * @author team1675
 */
public interface Hood {

    public void doHood();
    public boolean setAngle(int angle);
}
