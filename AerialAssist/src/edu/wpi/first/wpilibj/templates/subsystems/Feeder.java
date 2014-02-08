/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class Feeder {

    private static final Relay feeder = new Relay(Ports.feeder);
    private static final Relay trigger = new Relay(Ports.trigger);
    private static final DigitalInput ballLimit = new DigitalInput(Ports.ballLimit);

    public static void feed() {
        raiseShield();
        feeder.set(Relay.Value.kForward);
    }

    public static void pass() {
        feeder.set(Relay.Value.kReverse);
    }

    public static void stopFeed() {
        feeder.set(Relay.Value.kOff);
    }

    public static void raiseShield() {
        trigger.set(Relay.Value.kForward);
    }

    public static void lowerShield() {
        trigger.set(Relay.Value.kOff);
    }

    public static boolean possessing() {
        return ballLimit.get();
    }
}
