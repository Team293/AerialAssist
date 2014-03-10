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
        //fee
        Feeder.triggerEnabled();
        while (!Feeder.ballLimit2.get()) {
            Feeder.feed();
            Feeder.triggerEnabled();
            SmartDashboard.putString("debug..", "feeding");
        }
        markTime();
        Feeder.stop();
        Feeder.triggerEnabled();

        //move forward
        while (autoTimer.get() - commandStartTime < stopTime1) {
            SmartDashboard.putString("debug..", "driving forward");
            Feeder.triggerEnabled();
            driveStraight(driveSpeedForward);
            ShooterRack.run();
            if (!Feeder.ballLimit2.get()) {
                Feeder.feed();
            } else {
                Feeder.stop();
            }
        }
        Feeder.triggerEnabled();

        //shoot
        while (Feeder.possessing()) {
            SmartDashboard.putString("debug..", "shooting");
            ShooterRack.run();
            Feeder.triggerDisabled();
            Feeder.feed();
        }

        Feeder.triggerEnabled();
        ShooterRack.stop();
        DriveTrain.stop();
    }
}
