/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class ShooterRack {

    private static final Shooter shooter1 = new Shooter(Ports.shooter1, Ports.shooter1EncA, Ports.shooter1EncB);
    private static final Shooter shooter2 = new Shooter(Ports.shooter2, Ports.shooter2EncA, Ports.shooter2EncB);
    private static final Talon shooter3 = new Talon(Ports.shooter3);

    public static void setHighRPM() {
        if (!shooter1.pid.isEnable()) {
            shooter1.pid.enable();
        }
        if (!shooter2.pid.isEnable()) {
            shooter2.pid.enable();
        }
        shooter1.pid.setSetpoint(2400);
        shooter2.pid.setSetpoint(1600);
        shooter3.set(0.8);
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
        shooter3.set(0.5);
    }

    public static void stop() {
        shooter1.pid.disable();
        shooter1.motor.set(0.0);
        shooter2.pid.disable();
        shooter2.motor.set(0.0);
        shooter3.set(0.0);
    }
}
