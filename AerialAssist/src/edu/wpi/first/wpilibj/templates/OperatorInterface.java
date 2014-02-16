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
    private static final SpikeButton pass = new SpikeButton(gamepad, Ports.pass),
            toggleFeeder = new SpikeButton(gamepad, Ports.toggleFeeder),
            fire = new SpikeButton(rightJoystick, Ports.fire),
            autoAlign = new SpikeButton(leftJoystick, 1),
            toggleDriveDirection = new SpikeButton(rightJoystick, Ports.toggleDriveDirection);

    public static void controlDriveTrain() {
        //check which way is forward
        if (toggleDriveDirection.getState()) {
            DriveTrain.tankDrive(leftJoystick.getY(), rightJoystick.getY());
        } else {
            DriveTrain.tankDrive(-rightJoystick.getY(), -leftJoystick.getY());
        }
    }

    public static void manualControlShooter() {
        //read in setpoint from smart dashboard
        double speed1 = SmartDashboard.getNumber("1", 0.0);
        double speed2 = SmartDashboard.getNumber("2", 0.0);
        double speed3 = SmartDashboard.getNumber("3", 0.0);
        ShooterRack.shooterLow.setSetpoint(-speed1);
        ShooterRack.shooterMiddle.setSetpoint(speed2);
        ShooterRack.shooterHigh.setSetpoint(speed3);

        ShooterRack.shooterLow.run();
        ShooterRack.shooterMiddle.run();
        ShooterRack.shooterHigh.run();
        
        if (fire.getClick()) {
            ShooterRack.shooting = true;
        }
        
        Relay.Value feederValue;
        if (!ShooterRack.shooting) {
            if (pass.get()) {
                feederValue = Relay.Value.kReverse;
            } else if (toggleFeeder.getState()) {
                //ball limit is wired in reverse!~~~
                if (Feeder.ballLimit.get()) {
                    feederValue = Relay.Value.kForward;
                } else {                    
                    feederValue = Relay.Value.kOff;
                }
            } else {
                feederValue = Relay.Value.kOff;
            }
        } else {
            Feeder.triggerDisabled();
            feederValue = Relay.Value.kForward;
            if (Feeder.ballLimit.get()) {
                ShooterRack.shooting = false;
                Feeder.triggerEnabled();
            }
        }
        Feeder.feeder.set(feederValue);
        Feeder.feeder2.set(feederValue);
    }

    public static void controlAutoAlign() {
        DriveTrain.rangeUltrasonics();
        DriveTrain.isAligned();
    }

    public static void controlCamera() {
        //dividing by 4 decreases the range of the cameras motion
        //0.75 centers that motion around a slightly upward angle
        double pos = gamepad.getRawAxis(Ports.gamepadLeftYAxis) / 4 + 0.75;
        Vision.setServo(pos);
    }
}
