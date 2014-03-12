/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.subsystems.Feeder;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;

/**
 *
 * @author Peter
 */
public class HotTwoBall extends Auto {

    public HotTwoBall() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        //check blob count
        blobCount = SmartDashboard.getNumber("blobCount", 0);

        //enable trigger
        Feeder.triggerEnabled();

        //feed
        while (!Feeder.possessing()) {
            Feeder.feed();
            SmartDashboard.putString("debug..", "feeding");
        }
        markTime();

        //drive foreward for stopTime1 at driveSpeed1
        while (autoTimer.get() - commandStartTime < stopTime1) {
            driveStraight(driveSpeedForward);
        }
        markTime();

        //turn and then shoot first ball
        while (Feeder.possessing()) {
            ShooterRack.run();
            //if left goal is hot, turn left
            if (blobCount == 2 && autoTimer.get() - commandStartTime < turnTime) {
                turn(turnLeft);
            } //if left goal is NOT hot, turn right
            else if (blobCount == 1 && autoTimer.get() - commandStartTime < turnTime) {
                turn(turnRight);
            } else {
                SmartDashboard.putString("debug..", "shooting");
                ShooterRack.fire();
            }
            markTime();
        }
        ShooterRack.stop();

        //turn back to straight
        while (autoTimer.get() - commandStartTime < turnTime) {
            if (blobCount == 1) {
                turn(turnLeft);
            } else if (blobCount == 2) {
                turn(turnRight);
            }
        }
        markTime();

        //feed second ball
        while (!Feeder.possessing()) {
            driveStraight(driveSpeedReverse);
            Feeder.feed();
        }
        markTime();

        //drive forward
        while (autoTimer.get() - commandStartTime < stopTime2) {
            ShooterRack.run();
            driveStraight(driveSpeedReverse);
        }

        //turn and shoot
        while (!Feeder.possessing()) {
            //if left goal was not hot the first time, turn left this time
            if (blobCount == 1 && autoTimer.get() - commandStartTime < turnTime) {
                turn(turnLeft);
            } //if left goal was hot the first time, turn right this time
            else if (blobCount == 2 && autoTimer.get() - commandStartTime < turnTime) {
                turn(turnRight);
            } else {
                ShooterRack.run();
                ShooterRack.fire();
            }
        }

    }
}
