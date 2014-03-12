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
public class ColdTwoBall extends Auto {

    public ColdTwoBall() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        //feed 1
        while (!Feeder.possessing()) {
            Feeder.feed();
            SmartDashboard.putString("debugging", "feeding");
        }
        markTime();
        Feeder.stop();

        //move forward 1
        while (autoTimer.get() - commandStartTime < stopTime1) {
            SmartDashboard.putString("debugging", "driving forward 1");
            driveStraight(driveSpeedForward);
            ShooterRack.run();
            if (!Feeder.possessing()) {
                Feeder.feed();
            } else {
                Feeder.stop();
            }
        }
        markTime();

        //shoot
        while (Feeder.possessing()) {
            SmartDashboard.putString("debugging", "shooting");
            ShooterRack.run();
            ShooterRack.fire();
        }
        markTime();
        Feeder.triggerEnabled();
        ShooterRack.stop();

        //back up && feed
        while (!Feeder.possessing()) {
            SmartDashboard.putString("debugging", "back up till feed");
            if (autoTimer.get() - commandStartTime < searchTime) {
                driveStraight(driveSpeedReverse);
            }
        }
        Feeder.stop();
        DriveTrain.stop();
        markTime();

        //move forward 2
        while (autoTimer.get() - commandStartTime < stopTime2) {
            SmartDashboard.putString("debugging", "move forward 2");
            driveStraight(driveSpeedForward);
            ShooterRack.run();
        }
        markTime();
        DriveTrain.stop();

        //shoot
        ShooterRack.autonomousFire();

        //setup
        Feeder.triggerEnabled();
        ShooterRack.stop();
    }

}
