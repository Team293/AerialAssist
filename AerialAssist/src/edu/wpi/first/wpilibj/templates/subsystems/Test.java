/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 * @author Peter
 */
public class Test {

    public static void addComponents() {
        LiveWindow.addSensor("Drive Train", "gyro", DriveTrain.gyro);
        LiveWindow.addSensor("Drive Train", "left US", DriveTrain.leftUltrasonic);
        LiveWindow.addSensor("Drive Train", "right US", DriveTrain.rightUltrasonic);
        LiveWindow.addActuator("Drive Train", "US signal", DriveTrain.ultrasonicSignal);

        LiveWindow.addActuator("Cage", "pin release", Cage.release);

        LiveWindow.addActuator("Feeder", "feeder motor", Feeder.feeder);
        LiveWindow.addActuator("Feeder", "trigger", Feeder.trigger);

        LiveWindow.addActuator("LEDs", "led1", LEDs.led1);
        LiveWindow.addActuator("LEDs", "led2", LEDs.led2);
        LiveWindow.addActuator("LEDs", "led3", LEDs.led3);
        LiveWindow.addActuator("LEDs", "led4", LEDs.led4);

//        LiveWindow.addSensor("ShooterRack", "enc1", ShooterRack.shooterLow.encoder);
//        LiveWindow.addSensor("ShooterRack", "enc2", ShooterRack.shooterMiddle.encoder);
//        LiveWindow.addSensor("ShooterRack", "enc3", ShooterRack.shooterHigh.encoder);
//        LiveWindow.addSensor("ShooterRack", "enc1", ShooterRack.shooterLow.talon);
//        LiveWindow.addSensor("ShooterRack", "enc2", ShooterRack.shooterMiddle.talon);
//        LiveWindow.addSensor("ShooterRack", "enc3", ShooterRack.shooterHigh.talon);
    }
}
