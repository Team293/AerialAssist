/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class ShooterRack {

    public static final PShooter shooterLow = new PShooter(Ports.shooterLow, Ports.shooterLowEncA, Ports.shooterLowEncB, 0, 0, true);
    public static final PShooter shooterMiddle = new PShooter(Ports.shooterMiddle, Ports.shooterMiddleEncA, Ports.shooterMiddleEncB, 0, 0, false);
    public static final PShooter shooterHigh = new PShooter(Ports.shooterHigh, Ports.shooterHighEncA, Ports.shooterHighEncB, 0, 0, false);
    static boolean firing = false;
    public static final double shooterDistance = 10; //Random optimum distance from the wall

    public static void controlFiring() {
        if (firing) {
            if (atRPM()) {
                Feeder.triggerDisabled();
                Feeder.feed();
            }
            if (!Feeder.possessing()) {
                firing = false;
                Feeder.triggerEnabled();
                Feeder.stopFeed();
            }
        }
    }

    public static void setHighRPM() {
        shooterLow.setSetpoint(800);
        shooterMiddle.setSetpoint(600);
        shooterHigh.setSetpoint(400);
    }

    public static void setLowRPM() {
        shooterLow.setSetpoint(400);
        shooterMiddle.setSetpoint(600);
        shooterHigh.setSetpoint(200);
    }

    public static boolean atRPM() {
        return shooterLow.onTarget() && shooterMiddle.onTarget() && shooterHigh.onTarget();
    }

    public static void stop() {
        shooterLow.stop();
        shooterMiddle.stop();
        shooterHigh.stop();
    }

    public static void setToFiring() {
        firing = true;
    }

    public static boolean isFiring() {
        return firing;
    }

    public static void autonomousFiring() {
        while (firing) {
            controlFiring();
        }
    }
}
