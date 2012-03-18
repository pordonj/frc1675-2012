/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;
/**
 *
 * @author team1675
 */
public class ShooterSystem {

    private Shooter shooter;
    private Turret turret;
    private Hood hood;
    private VisionSystem camera;
    
    



    public ShooterSystem(Shooter shooter, Turret turret, Hood hood)
    {
        this.shooter = shooter;
        this.turret = turret;
        this.hood = hood;
    }
    
    /*
     * Place holder
     */
    public boolean doTeleop(){
        turret.doTurret();
        hood.doHood();
        return shooter.doShooter();
    }




}
