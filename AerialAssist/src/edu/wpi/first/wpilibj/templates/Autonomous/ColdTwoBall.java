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
        Feeder.triggerEnabled();
        while (!Feeder.ballLimit2.get()) {
            Feeder.feed();
            Feeder.triggerEnabled();
            SmartDashboard.putString("debug..", "feeding");
        }
        commandStartTime = autoTimer.get();
        Feeder.stop();
        Feeder.triggerEnabled();

        //move forward 1
        while (autoTimer.get() - commandStartTime < stopTime1) {
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
        commandStartTime = autoTimer.get();
        Feeder.triggerEnabled();

        //align to straight
        while (autoTimer.get() - commandStartTime < alignTime) {
            SmartDashboard.putString("debug..", "aligning");
            align();
            ShooterRack.run();
        }
        commandStartTime = autoTimer.get();

        //shoot
        while (Feeder.possessing()) {
            SmartDashboard.putString("debug..", "shooting");
            ShooterRack.run();
            Feeder.triggerDisabled();
            Feeder.feed();
        }
        commandStartTime = autoTimer.get();
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
        commandStartTime = autoTimer.get();

        //move forward 2
        ShooterRack.setToShootingRPM();
        while (autoTimer.get() - commandStartTime < stopTime2) {
            SmartDashboard.putString("debug..", "move forward 2");
            driveStraight(driveSpeedForward);
            ShooterRack.run();
            if (!Feeder.ballLimit2.get()) {
                Feeder.feed();
            } else {
                Feeder.stop();
            }
        }
        commandStartTime = autoTimer.get();
        DriveTrain.stop();
        //shoot
        while (Feeder.possessing()) {
            ShooterRack.run();
            SmartDashboard.putString("debug..", "shoot 2");
            Feeder.triggerDisabled();
            Feeder.feed();
        }
        Feeder.triggerEnabled();
        ShooterRack.stop();
    }

}
