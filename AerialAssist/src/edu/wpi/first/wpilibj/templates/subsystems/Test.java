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
        LiveWindow.addActuator("Drive Train", "left drive", DriveTrain.leftMotor);
        LiveWindow.addActuator("Drive Train", "right drive", DriveTrain.rightMotor);
        LiveWindow.addSensor("Drive Train", "gyro", DriveTrain.gyro);
        LiveWindow.addSensor("Drive Train", "left US", DriveTrain.leftUltrasonic);
        LiveWindow.addSensor("Drive Train", "right US", DriveTrain.rightUltrasonic);
        LiveWindow.addActuator("Drive Train", "US signal", DriveTrain.ultrasonicSignal);

        LiveWindow.addActuator("Cage", "pin release", Cage.pinRelease);

        LiveWindow.addActuator("Feeder", "feeder motor", Feeder.feeder);
        LiveWindow.addActuator("Feeder", "trigger", Feeder.trigger);
        LiveWindow.addSensor("Feeder", "ball limit", Feeder.ballLimit);

        LiveWindow.addActuator("LEDs", "led1", LEDs.led1);
        LiveWindow.addActuator("LEDs", "led2", LEDs.led2);
        LiveWindow.addActuator("LEDs", "led3", LEDs.led3);
        LiveWindow.addActuator("LEDs", "led4", LEDs.led4);

        LiveWindow.addSensor("ShooterRack", "enc1", ShooterRack.shooter1.enc);
        LiveWindow.addSensor("ShooterRack", "enc2", ShooterRack.shooter2.enc);
        LiveWindow.addSensor("ShooterRack", "enc3", ShooterRack.shooter3.enc);
        LiveWindow.addActuator("ShooterRack", "motor1", ShooterRack.shooter1.motor);
        LiveWindow.addActuator("ShooterRack", "motor2", ShooterRack.shooter2.motor);
        LiveWindow.addActuator("ShooterRack", "motor3", ShooterRack.shooter3.motor);
        LiveWindow.addActuator("ShooterRack", "pid1", ShooterRack.shooter1.pid);
        LiveWindow.addActuator("ShooterRack", "pid2", ShooterRack.shooter2.pid);
        LiveWindow.addActuator("ShooterRack", "pid3", ShooterRack.shooter3.pid);
    }
}
