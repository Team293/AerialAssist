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
public class Catcher {
 
    private static final Servo servo = new Servo(Ports.catcherServo);
    
    public static void setCatcher(boolean state){
        if (state){
            servo.setPosition(0);
        } else {
            servo.setPosition(1);
        }
    }
    
}
