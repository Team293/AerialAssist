/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.templates.subsystems.Feeder;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;

/**
 *
 * @author Peter
 */
public class ColdOneBall extends Auto {

    public ColdOneBall() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        //feed
        while (!Feeder.possessing()) {
            Feeder.feed();
            SmartDashboard.putString("debugging", "feeding");
        }
        markTime();
        Feeder.stop();

        //move forward
        while (autoTimer.get() - commandStartTime < stopTime1) {
            SmartDashboard.putString("debugging", "driving forward");
            driveStraight(driveSpeedForward);
            ShooterRack.run();
            if (!Feeder.possessing()) {
                Feeder.feed();
            } else {
                Feeder.stop();
            }
        }

        //shoot
        ShooterRack.autonomousFire();

        //setup for teleop
        Feeder.triggerEnabled();
        ShooterRack.stop();
        DriveTrain.stop();
    }
}
