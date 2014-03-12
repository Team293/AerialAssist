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

    private static final int driveTime = 2;

    public CrossLine() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        while (autoTimer.get() < driveTime) {
            driveStraight(0.6);
        }
    }

}
