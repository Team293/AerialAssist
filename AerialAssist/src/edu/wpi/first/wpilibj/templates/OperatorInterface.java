/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.SpikeButton;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.templates.subsystems.Feeder;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;
import edu.wpi.first.wpilibj.templates.subsystems.Vision;

/**
 *
 * @author Noam
 */
public class OperatorInterface {

    private static final Joystick leftJoystick = new Joystick(Ports.leftJoystick),
            rightJoystick = new Joystick(Ports.rightJoystick),
            gamepad = new Joystick(Ports.gamepad);
    private static final SpikeButton toggleFeeder = new SpikeButton(gamepad, Ports.AButton),
            fire = new SpikeButton(leftJoystick, Ports.triggerButton),
            catcher = new SpikeButton(gamepad, Ports.leftTrigger),
            setHighRPM = new SpikeButton(gamepad, Ports.XButton),
            setLowRPM = new SpikeButton(gamepad, Ports.YButton),
            stopShooter = new SpikeButton(gamepad, Ports.BButton),
            autoAlign = new SpikeButton(gamepad, Ports.rightTrigger);

    public static void controlDriveTrain() {
        DriveTrain.tankDrive(leftJoystick.getY(), rightJoystick.getY());
    }

    public static void controlFeeder() {
        if (!ShooterRack.isFiring()) {
            if (toggleFeeder.getState()) {
                Feeder.feed();
            } else {
                Feeder.pass();
            }
        }
    }

    public static void controlCatcher() {
    }

    public static void controlShooter() {
        if (setHighRPM.get()) {
            ShooterRack.setHighRPM();
        } else if (setLowRPM.get()) {
            ShooterRack.setLowRPM();
        } else if (stopShooter.get()) {
            ShooterRack.stop();
        } else if (fire.getClick()) {
            ShooterRack.setToFiring();
        }
        ShooterRack.controlFiring();
    }

    public static void controlAutoAlign() {
        if (autoAlign.get()) {
            DriveTrain.autoAlign();
        }
    }

    public static void controlCamera() {
        Vision.setServo(gamepad.getRawAxis(Ports.gamepadLeftYAxis));
    }
}
