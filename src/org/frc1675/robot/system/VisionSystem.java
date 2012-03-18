/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
//import java.util.Vector;
/**
 *
 * @author team1675
 */
public class VisionSystem {

    AxisCamera visionCamera;
    CartesianPlane cartesianPlane;
    ColorImage baseImage;
    BinaryImage filteredImage;
    CriteriaCollection cc;  // the criteria for doing the particle filter operation
    Turret turret;
    ParticleAnalysisReport currentTarget;
    private double turretTolerance;




    private static final int RED_LOW = 25;
    private static final int RED_HIGH = 255;

    private static final int GREEN_LOW = 0;
    private static final int GREEN_HIGH = 45;

    private static final int BLUE_LOW = 0;
    private static final int BLUE_HIGH = 47;

    private static final int HUE_LOW = 25;
    private static final int HUE_HIGH = 255;

    private static final int SAT_LOW = 0;
    private static final int SAT_HIGH = 45;

    private static final int LUM_LOW = 0;
    private static final int LUM_HIGH = 47;



    public VisionSystem(AxisCamera visionCamera, CartesianPlane cartesianPlane, double turretTolerance){
    
        this.turretTolerance = turretTolerance;
        this.visionCamera = visionCamera;
        this.cartesianPlane = cartesianPlane;
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);        
    }

    public void doVisionProcessing(){




        if(visionCamera.freshImage())
        {            
            setFreshImage();
            setFilteredImage();
        }
        try {
            ParticleAnalysisReport[] analysisReports = filteredImage.getOrderedParticleAnalysisReports(4);
            cartesianPlane.setParticleList(analysisReports);




            //convert to cartesian plane
            //interpret values
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }
    }

    public void setFilteredImage()
    {
        try {
            BinaryImage thresholdImage = baseImage.thresholdRGB(RED_LOW, RED_HIGH, GREEN_LOW, GREEN_HIGH, BLUE_LOW, BLUE_HIGH);
            BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);  // remove small artifacts
            BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
            filteredImage = convexHullImage.particleFilter(cc);           // find filled in rectangles

            thresholdImage.free();
            convexHullImage.free();
            bigObjectsImage.free();


        } catch (NIVisionException ex) {
            ex.printStackTrace();

        }
    }

    private void setFreshImage()
    {
        if(baseImage != null)
        {
            try {
                baseImage.free();
                filteredImage.free();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
        }
        try {
            baseImage = visionCamera.getImage();
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }
    }

    private boolean aimTurret(){
        currentTarget = cartesianPlane.getTopMostParticle();


        if (currentTarget.center_mass_x_normalized < turretTolerance && currentTarget.center_mass_x_normalized > -turretTolerance){
            turret.stop();
            return true;
        }

        if (turret.isAcceptableClockwiseRange())
            if (currentTarget.center_mass_x_normalized > turretTolerance){
                turret.moveClockwise();
                return false;
            }
        
        if (turret.isAcceptableCounterclockwiseRange())
            if (currentTarget.center_mass_x_normalized < -turretTolerance){
                turret.moveCounterclockwise();
                return false;
            }

        return false;
     }

}


