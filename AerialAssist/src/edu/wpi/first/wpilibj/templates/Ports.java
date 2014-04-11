/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 * @author Peter
 */
public class Ports {

    public static final int 
            //joysticks
            leftJoystick = 1,
            rightJoystick = 2,
            gamepad = 3,
            //buttons
            //___right joystick
            fire = 1,
            toggleDriveDirection = 3,
            //___gamepad
            toggleFeeder = 1,
            eject = 4,
            recieve = 3,
            pass = 2,
            setToHighRPM = 5,
            setToLowRPM = 6,
            toggleShooter = 8,
            //axes
            gamepadLeftXAxis = 1,
            gamepadLeftYAxis = 2,
            gamepadRightXAxis = 3,
            gamepadRightYAxis = 4,
            //motors
            leftDrive = 1,
            rightDrive = 2,
            feeder = 1,
            roller = 2,
            trigger = 3,
            shooterLow = 3,
            shooterMiddle = 4,
            shooterHigh = 5,
            cageRelease = 6,
            cameraServo = 10,
            //encoders
            shooterLowEncA = 2,
            shooterLowEncB = 3,
            shooterMiddleEncA = 4,
            shooterMiddleEncB = 5,
            shooterHighEncA = 6,
            shooterHighEncB = 7,
            //gyros
            gyro = 1,
            //Ultrasonics
            rightUltrasonic = 2,
            leftUltrasonic = 3,
            //Digital Outputs
            ultrasonicSignal = 8,
            //LEDs
            chasers =5,
            directionSignal = 6,
            //optical limtis
            ballLimit = 1,
            ballLimit2 = 9,
            triggerLimit = 10;
}
