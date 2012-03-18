/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.frc1675.robot.system;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import java.util.Vector;
import org.frc1675.driver.XBoxController;

/**
 *
 * @author team1675
 */
public class ChooseTarget {
    XBoxController xBoxController;
    CartesianPlane cartesianPlane;
    private ParticleAnalysisReport currentTarget;

    private Timer dPadRightTimer;
    private Timer dPadLeftTimer;
    private double buttonDelay;
//    private Vector particles;
    private int maxIndex;
    private int currentIndex;

    ChooseTarget(CartesianPlane cartesianPlane){
//        this.xBoxController = xBoxController;
        currentTarget = cartesianPlane.getTopMostParticle();
        this.cartesianPlane = cartesianPlane;
//        this.particles = cartesianPlane.OrderedList();

/*       this.buttonDelay = 0.5;
        dPadRightTimer = new Timer();
        dPadLeftTimer = new Timer();

        this.particles = particles;
        maxIndex = particles.size();
        currentIndex = 0;
*/
 }
       


    /*public void cycleThroughTargets(){
        if(xBoxController.getRightBumperButton() == true){
            if(xBoxController.getDPadRight() == true && dPadRightTimer.get() == 0){
                dPadRightTimer.start();
                incrementIndex();
                currentTarget = (ParticleAnalysisReport)particles.elementAt(currentIndex);
            }
            if(xBoxController.getDPadLeft() == true && dPadLeftTimer.get() == 0){
                dPadLeftTimer.start();
                decrementIndex();
                currentTarget = (ParticleAnalysisReport)particles.elementAt(currentIndex);
            }
        }
        if(dPadRightTimer.get() >= buttonDelay){
            dPadRightTimer.stop();
            dPadRightTimer.reset();
        }
        if(dPadLeftTimer.get() >= buttonDelay){
            dPadLeftTimer.stop();
            dPadLeftTimer.reset();
        }
    }


    private void incrementIndex(){
        if(currentIndex == maxIndex){
            currentIndex = 0;
        }else{
            currentIndex++;
        }
    }

    private void decrementIndex(){
        if(currentIndex == 0){
            currentIndex = maxIndex;
        }else{
            currentIndex--;
        }
    }

    public void setParticle(Vector particles){
        this. particles = particles;
        maxIndex = particles.size();
        currentIndex = 0;
    }

    public ParticleAnalysisReport getCurrentTarget(){
        return currentTarget;
    }


    public
*/

     }




