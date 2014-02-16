/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Noam
 */
public class LEDs {

    public static final DigitalOutput led1 = new DigitalOutput(Ports.led1);
    public static final DigitalOutput led2 = new DigitalOutput(Ports.led2);
    public static final DigitalOutput led3 = new DigitalOutput(Ports.led3);
    public static final DigitalOutput led4 = new DigitalOutput(Ports.led4);
    public static final DigitalInput ballLimit = new DigitalInput(Ports.ballLimit);

    public static void indicateSituation() {
        if (ballLimit.get()) {
            led1.set(true);
            led2.set(true);
            led3.set(true);
            led4.set(true);
            if (ShooterRack.isShooting()) {
                led1.set(Math.ceil(DriverStation.getInstance().getMatchTime() % 2) == 0);
                led2.set(Math.ceil(DriverStation.getInstance().getMatchTime() % 2) == 0);
            }
            if (DriveTrain.isAligned()) {
                led1.set(Math.ceil(DriverStation.getInstance().getMatchTime() % 2) == 0);
                led2.set(Math.ceil(DriverStation.getInstance().getMatchTime() % 2) == 0);
            }
        } else {
            led1.set(false);
            led2.set(false);
            led3.set(false);
            led4.set(false);
        }
    }
}
