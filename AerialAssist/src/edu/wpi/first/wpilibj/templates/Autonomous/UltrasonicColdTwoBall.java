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
public class UltrasonicColdTwoBall extends Auto {

    public UltrasonicColdTwoBall() {
        super();
    }

    public void run() {
        //feed 1
        Feeder.triggerEnabled();
        while (!Feeder.ballLimit2.get()) {
            Feeder.feed();
            Feeder.triggerEnabled();
            SmartDashboard.putString("debug..", "feeding");
        }
        markTime();
        Feeder.stop();
        Feeder.triggerEnabled();

        //move forward 1
        while (!DriveTrain.isAtShootingDistance()) {
            SmartDashboard.putString("debug..", "driving forward 1");
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
            ShooterRack.fire();
        }
        Feeder.triggerEnabled();
        ShooterRack.stop();

        //back up && feed
        while (!Feeder.possessing()) {
            SmartDashboard.putString("debug..", "back up till feed");
            if (autoTimer.get() - commandStartTime < searchTime) {
                driveStraight(driveSpeedReverse);
            }
        }
        DriveTrain.stop();
        Feeder.stop();

        //move forward 2
        ShooterRack.setToShootingRPM();
        while (!DriveTrain.isAtShootingDistance()) {
            SmartDashboard.putString("debug..", "move forward 2");
            driveStraight(driveSpeedForward);
            ShooterRack.run();
            if (!Feeder.ballLimit2.get()) {
                Feeder.feed();
            } else {
                Feeder.stop();
            }
        }
        DriveTrain.stop();
        //shoot
        autoFire();

        Feeder.triggerEnabled();
        ShooterRack.stop();
    }

}
