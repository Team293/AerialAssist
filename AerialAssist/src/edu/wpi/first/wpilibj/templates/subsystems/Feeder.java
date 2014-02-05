/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class Feeder {

    private static final Talon feeder = new Talon(Ports.feeder),
            shield = new Talon(Ports.sheild);
    private static final DigitalInput shieldUpperLimit = new DigitalInput(Ports.shieldUpperLimit),
            shieldLowerLimit = new DigitalInput(Ports.shieldLowerLimit);

    public static void feed() {
        raiseShield();
        feeder.set(1);
    }

    public static void pass() {
        lowerShield();
        feeder.set(-1);
    }

    public static void raiseShield() {
        if (!shieldUpperLimit.get()) {
            shield.set(1);
        } else {
            shield.set(0);
        }
    }

    public static void lowerShield() {
        if (!shieldLowerLimit.get()) {
            shield.set(-1);
        } else {
            shield.set(0);
        }
    }

}
