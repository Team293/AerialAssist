/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autonomous;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.Ports;
import edu.wpi.first.wpilibj.templates.subsystems.Cage;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.templates.subsystems.Feeder;
import edu.wpi.first.wpilibj.templates.subsystems.LEDs;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;

/**
 *
 * @author Peter
 */
public class Auto {

    static final Gyro gyro = new Gyro(Ports.gyro);
    public static boolean hasRunAuto = false;
    static final double kStraight = 0.081, kAlign = 0.089;
    double alignTime = 0.5,
            stopTime1 = 2.0,
            stopTime2 = 1.9,
            searchTime = 2.90,
            quickBack1 = 0.85,
            driveSpeedForward = 0.79,
            driveSpeedForward2 = 0.83,
            driveSpeedReverse = -0.67,
            leftStopSpeed = -0.3,
            rightStopSpeed = -0.3,
            turnLeft = 20,
            blobCount = 0,
            turnTime = 0.75,
            turnRight = (-turnLeft);
    double commandStartTime = 0;
    public static Timer autoTimer = new Timer();

    public Auto() {
    }

    public void init() {
        autoTimer.start();
        Cage.release();
        gyro.reset();
        ShooterRack.finishedShooting();
        Feeder.triggerEnabled();
    }

    public void run() {
    }

    public void align() {
        double angle = gyro.getAngle();
        //calculate motor output
        SmartDashboard.putNumber("gyro", angle);
        double turnOutput = kAlign * angle;
        if (turnOutput > 1) {
            turnOutput = 1;
        }
        if (turnOutput < -1) {
            turnOutput = -1;
        }
        DriveTrain.tankDrive(-turnOutput, turnOutput);
    }

    public void turn(double turnAngle) {
        double angle = gyro.getAngle();
        //calculate motor output
        angle = angle - turnAngle;
        SmartDashboard.putNumber("gyro", angle);
        double turnOutput = kAlign * angle;
        if (turnOutput > 1) {
            turnOutput = 1;
        }
        if (turnOutput < -1) {
            turnOutput = -1;
        }
        DriveTrain.tankDrive(-turnOutput, turnOutput);
    }

    public void driveStraight(double speed) {
        //read the gyro
        double angle = gyro.getAngle();
        //calculate motor output
        SmartDashboard.putNumber("gyro", angle);
        double rightMotorOutput = speed - kStraight * angle;
        double leftMotorOutput = speed + kStraight * angle;
        if (rightMotorOutput > 1) {
            rightMotorOutput = 1;
        }
        if (leftMotorOutput > 1) {
            leftMotorOutput = 1;
        }
        if (rightMotorOutput < -1) {
            rightMotorOutput = -1;
        }
        if (leftMotorOutput < -1) {
            leftMotorOutput = -1;
        }
        //set motor output
        DriveTrain.tankDrive(-leftMotorOutput, -rightMotorOutput);
    }

    public void moveForward1() {
        markTime();
        while (autoTimer.get() - commandStartTime < stopTime1) {
            SmartDashboard.putString("debugging", "driving forward 1");
            driveStraight(driveSpeedForward);
            ShooterRack.run();
            if (!Feeder.possessing()) {
                Feeder.feed();
            } else if (Feeder.overFed()) {
                Feeder.roller.set(Relay.Value.kForward);
            } else {
                Feeder.stop();
            }
        }
    }

    public void autoFeed() {
        markTime();
        Feeder.triggerEnabled();
        while (!Feeder.possessing()) {
            Feeder.feed();
            SmartDashboard.putString("debugging", "feeding");
        }
        Feeder.stop();
    }

    public void autoFire() {
        markTime();
        Feeder.triggerDisabled();
        while (Feeder.possessing()) {
            SmartDashboard.putString("debugging", "shooting");
            ShooterRack.run();
            ShooterRack.fire();
        }
        Feeder.triggerEnabled();
        ShooterRack.stop();
        LEDs.chasersOff();
    }

    public void backFeed() {
        markTime();
        while (!Feeder.possessing()) {
            SmartDashboard.putString("debugging", "back up till feed");
            if (autoTimer.get() - commandStartTime < searchTime) {
                driveStraight(driveSpeedReverse);
            }
        }
        DriveTrain.stop();
        Feeder.stop();
    }

    public void moveForward2() {
        markTime();
        ShooterRack.setToShootingRPM();
        while (autoTimer.get() - commandStartTime < stopTime2) {
            SmartDashboard.putString("debugging", "move forward 2");
            driveStraight(driveSpeedForward2);
            ShooterRack.run();
            if (!Feeder.possessing()) {
                Feeder.feed();
            } else if (Feeder.overFed()) {
                Feeder.roller.set(Relay.Value.kForward);
            } else {
                Feeder.stop();
            }
        }
    }

    public void stop() {
        markTime();
        SmartDashboard.putString("debugging", "stopping");
        while (autoTimer.get() - commandStartTime < 0.5) {
            DriveTrain.tankDrive(leftStopSpeed, rightStopSpeed);
        }
    }

    public void setTeleop() {
        Feeder.triggerEnabled();
        ShooterRack.stop();
        DriveTrain.stop();
        Auto.hasRunAuto = true;
    }

    public void markTime() {
        commandStartTime = autoTimer.get();
    }
}
