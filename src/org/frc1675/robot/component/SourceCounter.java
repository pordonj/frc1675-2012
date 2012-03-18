/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.robot.component;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author jpordon
 */
public class SourceCounter implements PIDSource {
    
    private Timer reportTimer;
        private Counter counter;
        
        private static final double REPORT_TIME = 0.05;
        
        public SourceCounter(Counter counter){
            this.counter = counter;
            reportTimer = new Timer();
        }
        
        public void start(){
            reportTimer.start();
            counter.start();
        }
        
        public Counter getCounter(){
            return counter;
        }
        
        private double lastRPMReport;
        private double lastLastRPMReport;
        
        public double pidGet() {
            if(reportTimer.get() > REPORT_TIME){
                int reportedTicks = counter.get();
                counter.reset();
                reportTimer.reset();
                lastLastRPMReport = lastRPMReport;
                lastRPMReport = ((double)reportedTicks / (6.0 * REPORT_TIME));
                if(lastRPMReport > 5000.0){
                    lastRPMReport = lastLastRPMReport;
                }
            }
            SmartDashboard.putInt("Shooter RPM", (int)lastRPMReport);
            return lastRPMReport;
        }
    
}
