/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;

import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import java.util.Vector;

/**
 *
 * @author team1675
 */
public class CartesianPlane {

    private Vector listOfParticleAnalysisReports;
    public CartesianPlane(){
        listOfParticleAnalysisReports = new Vector();
    }
    
    public CartesianPlane (ParticleAnalysisReport particleMassOne, ParticleAnalysisReport particleMassTwo, ParticleAnalysisReport particleMassThree, ParticleAnalysisReport particleMassFour ){
        listOfParticleAnalysisReports = new Vector();
        listOfParticleAnalysisReports.addElement(particleMassOne);
        listOfParticleAnalysisReports.addElement(particleMassTwo);
        listOfParticleAnalysisReports.addElement(particleMassThree);
        listOfParticleAnalysisReports.addElement(particleMassFour);

    }

    public CartesianPlane(ParticleAnalysisReport[] particleArray){
        listOfParticleAnalysisReports = new Vector();
        for(int i=0; i < particleArray.length ; i++){
            listOfParticleAnalysisReports.addElement(particleArray[i]);
        }
    }

    public void setParticleList(ParticleAnalysisReport[] particleArray){
        listOfParticleAnalysisReports = new Vector();
        for(int i=0; i < particleArray.length ; i++){
            listOfParticleAnalysisReports.addElement(particleArray[i]);
        }
    }


    public void addToList (ParticleAnalysisReport ParticleAnalysisReport){
        listOfParticleAnalysisReports.addElement(ParticleAnalysisReport);
    }

    public void clearList (){
        listOfParticleAnalysisReports.removeAllElements();
    }

    public ParticleAnalysisReport getTopMostParticle() {
        ParticleAnalysisReport maxParticleAnalysisReport = null;
        for(int i=0; i < listOfParticleAnalysisReports.size(); i++){
            ParticleAnalysisReport currentParticleAnalysisReport = (ParticleAnalysisReport)listOfParticleAnalysisReports.elementAt(i);
            if (i==0){
                maxParticleAnalysisReport = currentParticleAnalysisReport;
            }else{
                if (currentParticleAnalysisReport.center_mass_y_normalized > maxParticleAnalysisReport.center_mass_y_normalized){
                    maxParticleAnalysisReport = currentParticleAnalysisReport;
                }
            }
        }
        return maxParticleAnalysisReport;
    }

    public ParticleAnalysisReport getBottomMostParticle() {
        ParticleAnalysisReport minParticleAnalysisReport = null;
        for(int i=0; i < listOfParticleAnalysisReports.size(); i++){
            ParticleAnalysisReport currentParticleAnalysisReport = (ParticleAnalysisReport)listOfParticleAnalysisReports.elementAt(i);
            if (i==0){
                minParticleAnalysisReport = currentParticleAnalysisReport;
            }else{
                if (currentParticleAnalysisReport.center_mass_y_normalized < minParticleAnalysisReport.center_mass_y_normalized ){
                    minParticleAnalysisReport = currentParticleAnalysisReport;
                }
            }
        }
        return minParticleAnalysisReport;
    }

    public ParticleAnalysisReport getRightMostParticle() {
        ParticleAnalysisReport maxParticleAnalysisReport = null;
        for(int i=0; i < listOfParticleAnalysisReports.size(); i++){
            ParticleAnalysisReport currentParticleAnalysisReport = (ParticleAnalysisReport)listOfParticleAnalysisReports.elementAt(i);
            if (i==0){
                maxParticleAnalysisReport = currentParticleAnalysisReport;
            }else{
                if (currentParticleAnalysisReport.center_mass_x_normalized > maxParticleAnalysisReport.center_mass_x_normalized ){
                    maxParticleAnalysisReport = currentParticleAnalysisReport;
                }
            }
        }
        return maxParticleAnalysisReport;

    }

    public ParticleAnalysisReport getLeftMostParticle() {
        ParticleAnalysisReport minParticleAnalysisReport = null;
        for(int i=0; i < listOfParticleAnalysisReports.size(); i++){
            ParticleAnalysisReport currentParticleAnalysisReport = (ParticleAnalysisReport)listOfParticleAnalysisReports.elementAt(i);
            if (i==0){
                minParticleAnalysisReport = currentParticleAnalysisReport;
            }else{
                if (currentParticleAnalysisReport.center_mass_x_normalized < minParticleAnalysisReport.center_mass_x_normalized ){
                    minParticleAnalysisReport = currentParticleAnalysisReport;
                }
            }
        }
        return minParticleAnalysisReport;

    }

    public Vector OrderedList(){
        Vector internalListOfParticles = createCopy();
        Vector orderedListOfParticles = new Vector();
        while(internalListOfParticles.size() > 0){
            orderedListOfParticles.addElement(removeTopMostParticle(internalListOfParticles));
        }
        return orderedListOfParticles;
    }

    private Vector createCopy(){
        Vector copyList = new Vector();
        for(int i=0; i < listOfParticleAnalysisReports.size(); i++){
            copyList.addElement(listOfParticleAnalysisReports.elementAt(i));
        }
        return copyList;
    }

    private ParticleAnalysisReport removeTopMostParticle(Vector internalListOfParticles){       
        ParticleAnalysisReport maxParticleAnalysisReport = null;
        int indexToRemove=-1;
        for(int i=0; i < internalListOfParticles.size(); i++){
            ParticleAnalysisReport currentParticleAnalysisReport = (ParticleAnalysisReport)internalListOfParticles.elementAt(i);
            if (i==0){
                maxParticleAnalysisReport = currentParticleAnalysisReport;
                indexToRemove = i;
            }else{
                if (currentParticleAnalysisReport.center_mass_y_normalized > maxParticleAnalysisReport.center_mass_y_normalized){
                    maxParticleAnalysisReport = currentParticleAnalysisReport;
                    indexToRemove = i;
                }
            }
        }
        internalListOfParticles.removeElementAt(indexToRemove);       
        return maxParticleAnalysisReport;
    }
}
