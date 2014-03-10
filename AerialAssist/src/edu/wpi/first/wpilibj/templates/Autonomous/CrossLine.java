/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autonomous;

/**
 *
 * @author Peter
 */
public class CrossLine extends Auto {

    public CrossLine() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        while (autoTimer.get() < 4) {
            driveStraight(0.5);
        }
    }

}
