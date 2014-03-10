/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class Cage {

    private static final Servo release = new Servo(Ports.cageRelease);
    private static final double released = 0.0, active = 0.8;

    public static void release() {
        release.set(released);
    }

    public static void reset() {
        release.set(active);
    }

}
