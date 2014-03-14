/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class LEDs {

    public static boolean RED = false;

    /**
     * not sure if any of this is right
     */
    private static final Relay chasers = new Relay(Ports.chasers),
            directionSignal = new Relay(Ports.directionSignal);

    public static void signalForward() {
        directionSignal.set(Relay.Value.kForward);
    }

    public static void signalReverse() {
        directionSignal.set(Relay.Value.kReverse);
    }

    //gold both ground for off?
    public static void chasersOff() {
        chasers.set(Relay.Value.kOff);
    }

    //hold white ground and red high for blue chasers
    public static void chaseRed() {
        chasers.set(Relay.Value.kForward);
    }

    //hold both high for blue
    public static void chaseBlue() {
        chasers.set(Relay.Value.kOn);
    }

}
