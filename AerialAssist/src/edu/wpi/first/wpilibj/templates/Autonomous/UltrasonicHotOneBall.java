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
import edu.wpi.first.wpilibj.templates.subsystems.Vision;

/**
 *
 * @author Peter
 */
public class UltrasonicHotOneBall extends Auto {

    public UltrasonicHotOneBall() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        Vision.setServo(0.65);
        Feeder.triggerEnabled();

        //feed
        while (!Feeder.possessing()) {
            SmartDashboard.putString("debugging", "looking for ball");
            Feeder.feed();
        }

        //drive forward
        while (!DriveTrain.isAtShootingDistance()) {
            SmartDashboard.putString("debuggin", "driving forward");
            Feeder.triggerEnabled();
            driveStraight(driveSpeedForward);
            ShooterRack.run();
            if (!Feeder.possessing()) {
                Feeder.feed();
            } else {
                Feeder.stop();
            }
        }
        markTime();

        //wait for hot goal, or emergancy shoot at 7 seconds
        while (SmartDashboard.getNumber("blobCount") != 2 && autoTimer.get() < 7) {
            ShooterRack.run();
            if (autoTimer.get() - commandStartTime < alignTime) {
                SmartDashboard.putString("debugging", "aligngin");
                align();
            } else {
                SmartDashboard.putString("debugging", "waiting");
                DriveTrain.stop();
            }
        }

        //FIRE!!!!
        ShooterRack.autonomousFire();
    }
}
