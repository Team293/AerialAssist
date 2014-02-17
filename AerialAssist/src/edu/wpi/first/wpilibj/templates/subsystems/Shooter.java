/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.SpikeEncoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Peter
 */
public class Shooter {

    SpikeEncoder encoder;
    Talon talon;
    static int ID;
    static double correction = 0.02, tolerance = 30;
    double setpoint, output, rpm, error;
    int id;

    Shooter(int talonPort, int encAPort, int encBPort) {
        talon = new Talon(talonPort);
        encoder = new SpikeEncoder(encAPort, encBPort,SpikeEncoder.BLACK);
        setpoint = 0;
        output = 0;
        rpm = 0;
        error = 0;
        this.id = ++ID;
    }

    public void init() {
        encoder.start();
    }

    public void stop() {
        talon.set(0);
        output = 0;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    public void run() {
        rpm = encoder.getRPM();
        error = rpm - setpoint;
        //positive error is too fast
        if (error > tolerance) {
            output -= correction;
        } else if (error < tolerance) {
            output += correction;
        }
        SmartDashboard.putNumber("output" + id, output);
        SmartDashboard.putNumber("rpm" + id, rpm);
        SmartDashboard.putNumber("setpoint" + id, setpoint);
        SmartDashboard.putNumber("error" + id, error);
        talon.set(output);
    }

    public boolean atRPM() {
        if (Math.abs(error) < tolerance) {
            return true;
        }
        return false;
    }
}
