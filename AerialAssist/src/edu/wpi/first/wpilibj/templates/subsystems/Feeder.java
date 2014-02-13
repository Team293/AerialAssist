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

    static final Relay feeder = new Relay(Ports.feeder);
    static final Relay trigger = new Relay(Ports.trigger);
    public static final DigitalInput ballLimit = new DigitalInput(Ports.ballLimit);

    public static void feed() {
        triggerDisabled();
        //check if reversed to prevent breaking stuff
        //you dont want to go full reverse to full forward
        if (feeder.get() == Relay.Value.kReverse) {
            feeder.set(Relay.Value.kOff);
        } else {
            if (!ballLimit.get()) {
                feeder.set(Relay.Value.kForward);
            } else {
                stopFeed();
            }
        }
    }

    public static void pass() {
        //check if forward to prevent breaking stuff
        //you dont want to go full forward to full reverse
        if (feeder.get() == Relay.Value.kForward) {
            feeder.set(Relay.Value.kOff);
        } else {
            if (ballLimit.get()) {
                feeder.set(Relay.Value.kReverse);
            } else {
                stopFeed();
            }
        }
    }

    public static void stopFeed() {
        feeder.set(Relay.Value.kOff);
    }

    public static void triggerDisabled() {
        if (!ShooterRack.isFiring()) {
            trigger.set(Relay.Value.kOff);
        }
    }

    public static void triggerEnabled() {
        trigger.set(Relay.Value.kForward);
    }

    public static boolean possessing() {
        return ballLimit.get();
    }
}
