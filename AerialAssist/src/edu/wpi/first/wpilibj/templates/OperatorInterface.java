/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.buttons.SpikeButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.templates.subsystems.Feeder;
import edu.wpi.first.wpilibj.templates.subsystems.LEDs;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;
import edu.wpi.first.wpilibj.templates.subsystems.Vision;

/**
 *
 * @author Noam
 */
public class OperatorInterface {

    public static final Joystick leftJoystick = new Joystick(Ports.leftJoystick),
            rightJoystick = new Joystick(Ports.rightJoystick),
            gamepad = new Joystick(Ports.gamepad);
    private static final SpikeButton pass = new SpikeButton(gamepad, Ports.pass),
            toggleFeeder = new SpikeButton(gamepad, Ports.toggleFeeder),
            eject = new SpikeButton(gamepad, Ports.eject),
            fire = new SpikeButton(rightJoystick, Ports.fire),
            recieve = new SpikeButton(gamepad, Ports.recieve),
            setToHighRPM = new SpikeButton(gamepad, Ports.setToHighRPM),
            toggleShooters = new SpikeButton(gamepad, Ports.toggleShooter),
            setToLowRPM = new SpikeButton(gamepad, Ports.setToLowRPM);
    public static final SpikeButton toggleDriveDirection = new SpikeButton(rightJoystick, Ports.toggleDriveDirection);

    public static void controlDriveTrain() {
        double leftY = leftJoystick.getY();
        double rightY = rightJoystick.getY();
        if (toggleDriveDirection.getState()) {
            //if (!LEDs.flashing) {
            LEDs.signalForward();
            //}
            DriveTrain.tankDrive(leftY, rightY);
        } else {
            //if (!LEDs.flashing) {
            LEDs.signalReverse();
            //}
            DriveTrain.tankDrive(-rightY, -leftY);
        }
    }

    public static void controlShooter() {
        if (setToHighRPM.get()) {
            ShooterRack.setToShootingRPM();
        } else if (setToLowRPM.get()) {
            ShooterRack.setToLowGoalRPM();
        }

        if (toggleShooters.getState()) {
            ShooterRack.run();
        } else { 
            LEDs.chasersOff();
            ShooterRack.stop();
        }
    }

    public static void controlFeeder() {
        SmartDashboard.putBoolean("feeding", toggleFeeder.getState());
        SmartDashboard.putBoolean("recieving", recieve.getState());
        if (fire.getClick()) {
            ShooterRack.startShooting();
        }

        if (!ShooterRack.isShooting()) {
            if (pass.get()) { //pass
                Feeder.pass();
            } else if (eject.get()) {
                ShooterRack.setToEjectRPM();
                toggleShooters.setState(true);
                ShooterRack.run();
                Feeder.triggerDisabled();
                Feeder.pass();
                SmartDashboard.putString("EJECTING", "EJECTING");
            } else if (recieve.getState()) {
                toggleShooters.setState(true);
                ShooterRack.setToRecieveRPM();
                ShooterRack.run();
                Feeder.triggerDisabled();
                Feeder.feed();
                //Feeder.lastOverFedState && 
                if (Feeder.possessing() && (!Feeder.overFed())) {
                    recieve.setState(false);
                    Feeder.stop();
                    toggleFeeder.setState(true);
                    ShooterRack.setToShootingRPM();
                }
            } else if (toggleFeeder.getState()) { //feed
                if (!Feeder.possessing()) {
                    Feeder.feed();
                } else if (Feeder.overFed()) {
                    Feeder.roller.set(Relay.Value.kForward);
                } else {
                    Feeder.stop();
                }
            } else {
                //toggle off
                Feeder.stop();
            }
        } else {
            //firing
            ShooterRack.fire();
            if (!Feeder.possessing()) {
                //stop firing
                ShooterRack.finishedShooting();
                toggleFeeder.setState(false);
                Feeder.triggerEnabled();
            }
        }
    }

    public static void controlCamera() {
        //dividing by 4 decreases the range of the cameras motion
        //0.75 centers that motion around a slightly upward angle
        double pos = gamepad.getRawAxis(Ports.gamepadLeftYAxis) / 5 + 0.5;
        Vision.setServo(pos);
    }
}
