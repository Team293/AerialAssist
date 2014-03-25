/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStation;
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
            fire = new SpikeButton(rightJoystick, Ports.fire),
            recieve = new SpikeButton(gamepad, Ports.recieve),
            toggleDriveDirection = new SpikeButton(rightJoystick, Ports.toggleDriveDirection),
            setToHighRPM = new SpikeButton(gamepad, Ports.setToHighRPM),
            toggleShooters = new SpikeButton(gamepad, Ports.toggleShooter),
            setToLowRPM = new SpikeButton(gamepad, Ports.setToLowRPM);

    public static void controlDriveTrain() {
        double leftY = leftJoystick.getY();
        double rightY = rightJoystick.getY();
        if (toggleDriveDirection.getState()) {
            LEDs.signalForward();
            DriveTrain.tankDrive(leftY, rightY);
        } else {
            LEDs.signalReverse();
            DriveTrain.tankDrive(-rightY, -leftY);
        }
    }

    public static void controlShooter() {
        SmartDashboard.putBoolean("shooters", toggleShooters.getState());
        if (setToHighRPM.get()) {
            ShooterRack.setToShootingRPM();
        } else if (setToLowRPM.get()) {
            ShooterRack.setToLowGoalRPM();
        }

        if (toggleShooters.getState()) {
            ShooterRack.run();
        } else {
            ShooterRack.stop();
        }
    }

    public static void controlFeeder() {
        SmartDashboard.putNumber("Voltage", DriverStation.getInstance().getBatteryVoltage());
        SmartDashboard.putBoolean("feeding", toggleFeeder.getState());
        SmartDashboard.putBoolean("recieving", recieve.getState());
        SmartDashboard.putBoolean("posseessing", Feeder.possessing());
        SmartDashboard.putBoolean("lastPossesingState", Feeder.lastPossessState);

        if (fire.getClick()) {
            ShooterRack.startShooting();
        }

        if (!ShooterRack.isShooting()) {
            if (pass.get()) { //pass
                Feeder.pass();
            } else if (recieve.getState()) { //recieve now passes straight through
                ShooterRack.run();
                toggleShooters.setState(true);
                ShooterRack.setToRecieveRPM();
                Feeder.triggerDisabled();
                Feeder.pass();
            } else if (toggleFeeder.getState()) { //feed
                if (!Feeder.possessing()) {
                    Feeder.feed();
                } else {
                    //remove the line below if power issue is fixed
                    toggleFeeder.setState(false);
                    Feeder.stop();
                }
            } else if (Feeder.overFed()) {
                Feeder.roller.set(Relay.Value.kForward);
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
