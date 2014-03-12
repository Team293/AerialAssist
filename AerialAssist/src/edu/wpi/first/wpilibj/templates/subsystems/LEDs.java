/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class LEDs {

    private static final DigitalOutput chasers = new DigitalOutput(Ports.chasers);
    private static final Relay directionSignal = new Relay(Ports.directionSignal);

    private static void signalForward() {
        directionSignal.set(Relay.Value.kForward);
    }

    private static void signalReverse() {
        directionSignal.set(Relay.Value.kReverse);
    }

    private static void XXsuperOMGAwesomeLEDSwowFIREEEEEEE() {
        chasers.set(true);
    }

    private static void killTheFun() {
        chasers.set(false);
    }

}
