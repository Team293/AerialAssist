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
public class Cage {

    private static final Relay pinRelease = new Relay(Ports.cageRelay);

    public static void release() {
        //turns on solenoid and pulls out the pin
        pinRelease.set(Relay.Value.kForward);
    }

}
