/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.SpikeButton;
import edu.wpi.first.wpilibj.templates.subsystems.Catcher;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;
import edu.wpi.first.wpilibj.templates.subsystems.Vision;

/**
 *
 * @author Peter
 */
public class OperatorInterface {

    private static final Joystick leftJoystick = new Joystick(Ports.leftJoystick),
            rightJoystick = new Joystick(Ports.rightJoystick),
            gamepad = new Joystick(Ports.gamepad);
    private static final SpikeButton toggleFeeder = new SpikeButton(gamepad, Ports.toggleFeeder),
            fire = new SpikeButton(leftJoystick, Ports.fire),
            catcher = new SpikeButton(gamepad, Ports.catcher),
            setHighRPM = new SpikeButton(gamepad, Ports.setHighRPM),
            setLowRPM = new SpikeButton(gamepad, Ports.setLowRPM),
            stopShooter = new SpikeButton(gamepad, Ports.stopShooter),
            autoOrient = new SpikeButton(gamepad, Ports.autoOrient);

    public static void controlDriveTrain() {
        DriveTrain.tankDrive(leftJoystick.getY(), rightJoystick.getY());
    }

    public static void controlFeeder() {
    }

    public static void controlCatcher() {
        Catcher.setCatcher(catcher.getState());
    }

    public static void controlShooter() {
        if (setHighRPM.get()) {
            ShooterRack.setHighRPM();
        } else if (setLowRPM.get()) {
            ShooterRack.setLowRPM();
        } else if (stopShooter.get()) {
            ShooterRack.stop();
        }
    }

    public static void controlAuto() {
        if (autoOrient.get()) {
            DriveTrain.autoOrient();
        }
    }

    public static void controlCamera() {
        Vision.setServo(gamepad.getRawAxis(Ports.gamepadLeftYAxis));
    }
}
