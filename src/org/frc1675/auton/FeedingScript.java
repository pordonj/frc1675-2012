/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.auton;

import edu.wpi.first.wpilibj.Timer;
import org.frc1675.robot.component.Storage;
import org.frc1675.robot.component.Storage.StorageMode;

/**
 * Feeding script will feed for X seconds then switch back to regular mode
 * @author team1675
 */
public class FeedingScript implements AutonomousScript {

    Storage storage;
    Timer feedTimer;
    double feedingTime;
    public FeedingScript(Storage storage, double feedingTime)
    {
        this.storage = storage;
        feedTimer = new Timer();
        this.feedingTime = feedingTime;
    }

    public void doAutonomous() {
        if(feedTimer.get() == 0.0){
            feedTimer.start();
            storage.setStorageMode(StorageMode.FEEDING);
        }

        if(feedTimer.get() > feedingTime){
            feedTimer.stop();
            storage.setStorageMode(StorageMode.REGULAR);            
        }
        storage.manage(false);
        
    }

}
