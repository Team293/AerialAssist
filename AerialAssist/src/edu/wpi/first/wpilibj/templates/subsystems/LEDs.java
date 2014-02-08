/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class LEDs {

    DigitalOutput led1 = new DigitalOutput(Ports.led1);
    DigitalOutput led2 = new DigitalOutput(Ports.led2);
    DigitalOutput led3 = new DigitalOutput(Ports.led3);
    DigitalOutput led4 = new DigitalOutput(Ports.led4);

    public void indicatedSituation(boolean ballLimit) {
        if (ballLimit) {
            led1.set(true);
            led2.set(true);
            led3.set(true);
            led4.set(true);
            if (ShooterRack.isFiring()) {
                led1.set(Math.ceil(DriverStation.getInstance().getMatchTime() % 2) == 0);
                led2.set(Math.ceil(DriverStation.getInstance().getMatchTime() % 2) == 0);
            }
            if (DriveTrain.autoAligned()) {
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
