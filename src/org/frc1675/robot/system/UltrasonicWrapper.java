/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author team1675
 */
public class UltrasonicWrapper {

    private Ultrasonic ultraSonicSensor;
    private int minDetectionCount;
    private double threshold;
    
    private int currentCount;
    private boolean previouslyDetecting;
    private boolean currentlyDetecting;
    private static final double MAX_RANGE = 20.0;
    private Timer debugTimer;
    public UltrasonicWrapper(Ultrasonic sensor, double threshold, int minDetectionCount)
    {
        this.ultraSonicSensor = sensor;
        this.threshold = threshold;
        this.minDetectionCount = minDetectionCount;
        
        currentCount = 0;
        
        currentlyDetecting = previouslyDetecting = false;
        debugTimer = new Timer();
    }
    
    public void evaluate()
    {
    	previouslyDetecting = currentlyDetecting;
    	
    	//the split edge detection separates the logic from rising and falling edge
    	//use this if we want separate minDetectionCounts for rising and falling
        //splitEdgeDetection();
        
    	//the combined edge detection abstracts the logic from rising and falling edge detection
    	//use this if we don't need separate minDetectionCounts for rising and falling
        combinedEdgeDetection();
        
    }	

	private void combinedEdgeDetection() {
		//if we are detecting an edge (rising or falling), increase the count
            if(isDetectingEdge())
            {
                if(currentCount == 0)
                {
                    debugTimer.start();
                }
                currentCount++;
//                SmartDashboard.putInt("current count: ", currentCount);
            }
            else{//otherwise, leave the count at 0
                    currentCount = 0;
            }
            //once the count reaches the minimum required for detection
            if(currentCount >= minDetectionCount)
            {
                //invert the current state of detection
                currentlyDetecting = !currentlyDetecting;
//                SmartDashboard.putDouble("timer count: ", debugTimer.get());
                debugTimer.stop();
                debugTimer.reset();
            }
	}

	private boolean isDetectingEdge() {
            boolean edgeDetected = false;
    //if currentlyDetecting is false, we are detecting for a rising edge
            if(!currentlyDetecting)
            {
                edgeDetected = getRange() < threshold;
            }else{//if currentlyDetecting is true, we are detecting a falling edge
                edgeDetected = getRange() > threshold;
            }
            return edgeDetected;
        }
    
	private void splitEdgeDetection() {
		//if the currentlyDetecting is false, then we are looking for a rising edge
		if(!currentlyDetecting)
        {
            detectRisingEdge();
        }else{//if currentlyDetecting is true, then we are looking for a falling edge
            detectFallingEdge();
        }
	}
	
    private void detectFallingEdge(){
    	if(getRange() > threshold)
		{
		    currentCount++;
		}
		else{
		    currentCount = 0;
		}
		
		if(currentCount >= minDetectionCount)
		{		    
		    currentlyDetecting = !currentlyDetecting;
		}
    }

	private void detectRisingEdge() {
		if(getRange() < threshold)
		{
		    currentCount++;
		}
		else{
		    currentCount = 0;
		}
		
		if(currentCount >= minDetectionCount)
		{
		    currentlyDetecting = !currentlyDetecting;
		}
	}
	
	/**
	 * Use this method to determine whether something is currently detected
	 * @return true if something is currently detected within the threshold
	 */
	public boolean isDetecting()
	{
		return currentlyDetecting;
	}
    
    public double getRange()
    {
        double range = MAX_RANGE;
        if(ultraSonicSensor.isRangeValid())
        {
            range = ultraSonicSensor.pidGet();
        }
        
        return range;
    }
    
    public void modifyThreshold(double amount)
    {
    	threshold += amount;
    }
    
    public void setThreshold(double threshold)
    {
    	this.threshold = threshold;
    }
    
    public double getThreshold()
    {
    	return threshold;
    }
    
    public void modifyMinDetectionCount(int amount)
    {
    	minDetectionCount += amount;
    }
    
    public void setMinDetectionCount(int minDetectionCount)
    {
    	this.minDetectionCount = minDetectionCount;
    }
    
    public int getMinDetectionCount()
    {
    	return minDetectionCount;
    }
    
    /**
     * This method will return true for 1 iteration that the edge is rising
     * @return Whether something has just been detected
     */
    public boolean isRisingEdge()
    {
        return currentlyDetecting && !previouslyDetecting;
    }
    
    /**
     * This method will return true for 1 iteration that the edge is falling
     * @return Whether something has just been undetected
     */
    public boolean isFallingEdge()
    {
        return !currentlyDetecting && previouslyDetecting;
    }

    public int getCurrentCount()
    {
        return currentCount;
    }
    
}
