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

    private static final Shooter shooter1 = new Shooter(Ports.shooter1, Ports.shooter1EncA, Ports.shooter1EncB);
    private static final Shooter shooter2 = new Shooter(Ports.shooter2, Ports.shooter2EncA, Ports.shooter2EncB);
    private static final Shooter shooter3 = new Shooter(Ports.shooter3, Ports.shooter3EncA, Ports.shooter3EncB);
    private static boolean firing = false;

    public static void initShooter() {
        shooter1.enc.start();
        shooter1.enc.reset();
        shooter2.enc.start();
        shooter2.enc.reset();
        shooter1.pid.setPercentTolerance(5);
    }

    public static void controlFiring() {
        if (firing) {
            if (atRPM()) {
                Feeder.lowerShield();
                Feeder.feed();
            }
            if (!Feeder.possessing()) {
                firing=false;
                Feeder.raiseShield();
                Feeder.stopFeed();
            }
        }
    }

    public static void setHighRPM() {
        if (!shooter1.pid.isEnable()) {
            shooter1.pid.enable();
        }
        if (!shooter2.pid.isEnable()) {
            shooter2.pid.enable();
        }
        shooter1.pid.setSetpoint(2400);
        shooter2.pid.setSetpoint(1600);
        shooter2.pid.setSetpoint(800);
    }

    public static void setLowRPM() {
        if (!shooter1.pid.isEnable()) {
            shooter1.pid.enable();
        }
        if (!shooter2.pid.isEnable()) {
            shooter2.pid.enable();
        }
        shooter1.pid.setSetpoint(1200);
        shooter2.pid.setSetpoint(800);
        shooter2.pid.setSetpoint(400);
    }

    public static void setReverseRPM() {
        if (!shooter1.pid.isEnable()) {
            shooter1.pid.enable();
        }
        if (!shooter2.pid.isEnable()) {
            shooter2.pid.enable();
        }
        shooter1.pid.setSetpoint(-400);
        shooter2.pid.setSetpoint(-60);
        shooter3.pid.setSetpoint(0);
    }

    public static boolean atRPM() {
        return shooter1.pid.onTarget() && shooter2.pid.onTarget() && shooter3.pid.onTarget();
    }

    public static void stop() {
        shooter1.stop();
        shooter2.stop();
        shooter3.stop();
    }

    public static void setToFiring() {
        firing = true;
    }
    
    public static boolean isFiring() {
        return firing;
    }
}
