/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.SpikeButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    private static final SpikeButton toggleFeeder = new SpikeButton(gamepad, Ports.toggleFeeder),
            fire = new SpikeButton(leftJoystick, Ports.fire),
            pass = new SpikeButton(gamepad, Ports.pass),
            //catcher = new SpikeButton(gamepad, Ports.leftTrigger),
            setHighRPM = new SpikeButton(gamepad, Ports.setHighRPM),
            setLowRPM = new SpikeButton(gamepad, Ports.setLowRPM),
            stopShooter = new SpikeButton(gamepad, Ports.stopShooter),
            toggleDriveDirection = new SpikeButton(rightJoystick, Ports.toggleDriveDirection),
            autoAlign = new SpikeButton(gamepad, Ports.autoAlign);

    public static void controlDriveTrain() {
        //prevent from manually driving if autoaiming
        if (!autoAlign.get()) {
            //check which way is forward
            if (toggleDriveDirection.getState()) {
                DriveTrain.tankDrive(leftJoystick.getY(), rightJoystick.getY());
            } else {
                DriveTrain.tankDrive(-rightJoystick.getY(), -leftJoystick.getY());
            }
        }
    }

    public static void controlFeeder() {
        //no manula control on feeder if you're shooting
        if (!ShooterRack.isFiring()) {
            if (pass.get()) {
                Feeder.pass();
            } else if (toggleFeeder.getState()) {
                Feeder.feed();
            } else {
                Feeder.stopFeed();
            }
        }
    }

    public static void manualControlShooter() {
        //read in setpoint from smart dashboard
        double speed1 = SmartDashboard.getNumber("1", 0.0);
        double speed2 = SmartDashboard.getNumber("2", 0.0);
        double speed3 = SmartDashboard.getNumber("3", 0.0);
        ShooterRack.shooterLow.setSetpoint(speed1);
        ShooterRack.shooterMiddle.setSetpoint(speed2);
        ShooterRack.shooterHigh.setSetpoint(speed3);
    }

    public static void controlShooter() {
        if (!stopShooter.getState()) {
            ShooterRack.stop();
        }
        if (setHighRPM.get()) {
            ShooterRack.setHighRPM();
        } else if (setLowRPM.get()) {
            ShooterRack.setLowRPM();
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
        //dividing by 4 decreases the range of the cameras motion
        //0.75 centers that motion around a slightly upward angle
        double pos = gamepad.getRawAxis(Ports.gamepadLeftYAxis) / 4 + 0.75;
        Vision.setServo(pos);
    }
}
