/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.OperatorInterface;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class LEDs {

    public static boolean RED = false, flashing = false;
    //public static int interval = 0;

    /**
     * not sure if any of this is right
     */
    private static final Relay chasers = new Relay(Ports.chasers),
            directionSignal = new Relay(Ports.directionSignal);
//
//    public static void flashSignalLights() {
//        flashing=true;
//        if ((LEDs.interval / 20) % 2 == 0) {
//            if (OperatorInterface.toggleDriveDirection.getState()) {
//                LEDs.signalForward();
//            } else {
//                LEDs.signalReverse();
//            }
//
//        } else {
//            LEDs.signalOff();
//        }
//    }

    public static void signalForward() {
        directionSignal.set(Relay.Value.kForward);
    }

    public static void signalReverse() {
        directionSignal.set(Relay.Value.kReverse);
    }

    public static void signalOff() {
        directionSignal.set(Relay.Value.kOff);
    }

    //gold both ground for off?
    public static void chasersOff() {
        chasers.set(Relay.Value.kOff);
    }

    //hold white ground and red high for blue chasers
    public static void chaseRed() {
        chasers.set(Relay.Value.kForward);
        SmartDashboard.putString("chasers", "chase red");
    }

    //hold both high for blue
    public static void chaseBlue() {
        chasers.set(Relay.Value.kReverse);
        SmartDashboard.putString("casers", "chase blue");
    }

}
