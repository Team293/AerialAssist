/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Peter
 */
public class Shooter {

    public Talon motor;
    Encoder enc;
    PIDController pid;
    double kP = 0, kI = 0, kD = 0, kF, setpoint = 0;

    public static final double shootDistance = 14;

    /**
     *
     * @param motorPort
     * @param encAPort
     * @param encBPort
     */
    public Shooter(int motorPort, int encAPort, int encBPort) {
        motor = new Talon(motorPort);
        enc = new Encoder(encAPort, encBPort, true, CounterBase.EncodingType.k4X);
        enc.start();
        enc.setDistancePerPulse(1 / 256);
        pid = new PIDController(kP, kI, kD, kF, enc, motor);
        pid.setPercentTolerance(5);

    }

    public void refresh() {
        pid.setPID(kP, kI, kD, kF);
        pid.setSetpoint(setpoint);
    }

    public void stop() {
        pid.disable();
        motor.set(0.0);
    }
}
