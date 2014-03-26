/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;
import edu.wpi.first.wpilibj.templates.subsystems.Vision;

/**
 *
 * @author Peter
 */
public class HotOneBall extends Auto {

    public HotOneBall() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        ShooterRack.setToShootingRPM();
        if (!Auto.hasRunAuto) {
            //while loop to wait for 2 vision targets or maybe increase in luminosity
            Vision.setServo(0.51);
            autoFeed();
            while (SmartDashboard.getNumber("blobCount", 0) != 2 && Auto.autoTimer.get() < 6) {
                ShooterRack.run();
                SmartDashboard.putString("debugging", "waiting");
            }
            SmartDashboard.putString("debugging", "ready!");
            moveForward1();
            autoFire();
            setTeleop();
        } else {
            SmartDashboard.putString("debugging", "done auto");
        }
    }
}
