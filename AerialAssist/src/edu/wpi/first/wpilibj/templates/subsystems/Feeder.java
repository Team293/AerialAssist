/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class Feeder {

    public static final Relay feeder = new Relay(Ports.feeder);
    public static final Relay feeder2 = new Relay(Ports.feeder2);
    static final Relay trigger = new Relay(Ports.trigger);
    public static final DigitalInput ballLimit = new DigitalInput(Ports.ballLimit);

    public static void pass() {
        feeder.set(Relay.Value.kReverse);
        feeder2.set(Relay.Value.kReverse);
    }

    public static void feed() {
        feeder.set(Relay.Value.kForward);
        feeder2.set(Relay.Value.kForward);
    }

    public static void stop() {
        feeder.set(Relay.Value.kOff);
        feeder2.set(Relay.Value.kOff);
    }

    public static void triggerDisabled() {
        trigger.set(Relay.Value.kOff);
    }

    public static void triggerEnabled() {
        trigger.set(Relay.Value.kForward);
    }

    public static boolean possessing() {
        return !ballLimit.get();
    }
}
