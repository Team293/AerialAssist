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
    
    Talon motor;
    Encoder enc;
    PIDController pid;
    double kP = 0, kI = 0, kD = 0,setpoint=0;
    
    /**
     * 
     * @param motorPort
     * @param encAPort
     * @param encBPort 
     */
    public Shooter(int motorPort, int encAPort, int encBPort) {
        motor = new Talon(motorPort);
        enc = new Encoder(encAPort, encBPort, true, CounterBase.EncodingType.k4X);
        pid = new PIDController(kP, kI, kD, enc, motor);
        
    }
    
    public void refresh() {
        pid.setPID(kP, kI, kD);
        pid.setSetpoint(setpoint);
    }
}
