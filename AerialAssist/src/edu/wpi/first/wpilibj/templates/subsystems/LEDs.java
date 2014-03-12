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

    /**
     * not sure if any of this is right
     */
    private static final Relay chasers = new Relay(Ports.chasers),
            directionSignal = new Relay(Ports.directionSignal);

    private static void signalForward() {
        directionSignal.set(Relay.Value.kForward);
    }

    private static void signalReverse() {
        directionSignal.set(Relay.Value.kReverse);
    }

    private static void chaseRed() {
        chasers.set(Relay.Value.kForward);
    }

    private static void chaseBlue() {
        chasers.set(Relay.Value.kForward);
    }

}
