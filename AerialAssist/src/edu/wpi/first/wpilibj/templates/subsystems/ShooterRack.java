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

    private static final Shooter shooterLow = new Shooter(Ports.shooterLow, Ports.shooterLowEncA, Ports.shooterLowEncB);
    private static final Shooter shooterMiddle = new Shooter(Ports.shooterMiddle, Ports.shooterMiddleEncA, Ports.shooterMiddleEncB);
    private static final Shooter shooterHigh = new Shooter(Ports.shooterHigh, Ports.shooterHighEncA, Ports.shooterHighEncB);
    private static boolean shooting = false;
    public static final double shooterDistance = 10; //Random optimum distance from the wall

    public static void startShooting() {
        shooting = true;
    }

    public static void finishedShooting() {
        shooting = false;
        LEDs.chasersOff();
    }

    public static void init() {
        shooterLow.init();
        shooterMiddle.init();
        shooterHigh.init();
    }

    public static void fire() {
        if (LEDs.RED) {
            LEDs.chaseRed();
        } else {
            LEDs.chaseBlue();
        }
        Feeder.feed();
        Feeder.triggerDisabled();
    }

    public static void setToShootingRPM() {
        shooterLow.setSetpoint(-900);
        shooterMiddle.setSetpoint(2300);
        shooterHigh.setSetpoint(2000);
    }

    public static void setToRecieveRPM() {
        shooterLow.setSetpoint(0);
        shooterMiddle.setSetpoint(-250);
        shooterHigh.setSetpoint(-300);
    }

    public static void setToLowGoalRPM() {
        shooterLow.setSetpoint(-600);
        shooterMiddle.setSetpoint(900);
        shooterHigh.setSetpoint(700);
    }

    public static void run() {
        shooterLow.run();
        shooterMiddle.run();
        shooterHigh.run();
    }

    public static void stop() {
        shooterLow.stop();
        shooterMiddle.stop();
        shooterHigh.stop();
    }

    public static boolean isShooting() {
        return shooting;
    }

    public static boolean atRPM() {
        return shooterLow.atRPM() && shooterMiddle.atRPM() && shooterHigh.atRPM();
    }
}
