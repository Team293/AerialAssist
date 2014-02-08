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

    private static final Relay pinRelease1 = new Relay(Ports.cageRelease1);
    private static final Relay pinRelease2 = new Relay(Ports.cageRelease2);

    public static void release() {
        //turns on solenoid and pulls out the pin
        pinRelease1.set(Relay.Value.kForward);
        pinRelease2.set(Relay.Value.kForward);
    }

}
