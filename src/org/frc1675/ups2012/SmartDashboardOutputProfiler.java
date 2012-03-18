/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.frc1675.ups2012;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboardData;

/**
 *
 * @author Josh
 */
public class SmartDashboardOutputProfiler {

//    public static void getInstance(String encoder, String shooterEncoder, double rate) {
//    }
    
    private String currentProfile;
    private static SmartDashboardOutputProfiler profiler = null;
    
    public static SmartDashboardOutputProfiler getInstance(){
        if(profiler == null){
            profiler = new SmartDashboardOutputProfiler();
        }
        return profiler;
    }
    
    private SmartDashboardOutputProfiler(){
        //making constructor private
    }
    
    public void setProfile(String profile){
        this.currentProfile = profile;
    }
    
    public void putBoolean(String profile, String key, boolean value){
        if(currentProfile.equals(profile)){
            SmartDashboard.putBoolean(key, value);
        }
    }
    
    public void putData(String profile, String key, SmartDashboardData value){
        if(currentProfile.equals(profile)){
            SmartDashboard.putData(key, value);
        }
    }
    
    public void putDouble(String profile, String key, double value){
        if(currentProfile.equals(profile)){
            SmartDashboard.putDouble(key, value);
        }
    }
    
    public void putInt(String profile, String key, int value){
        if(currentProfile.equals(profile)){
            SmartDashboard.putInt(key, value);
        }
    }
    
    public void putString(String profile, String key, String value){
        if(currentProfile.equals(profile)){
            SmartDashboard.putString(key, value);
        }
    }
}
