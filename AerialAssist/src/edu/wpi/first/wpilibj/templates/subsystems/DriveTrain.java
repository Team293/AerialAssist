/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Robotics Laptop B
 */
public class DriveTrain {

    private static final Talon leftMotor = new Talon(Ports.leftDrive);
    private static final Talon rightMotor = new Talon(Ports.rightDrive);
    private static final Gyro gyro = new Gyro(Ports.gyro);
    private static final AnalogChannel rightUltrasonic = new AnalogChannel(Ports.rightUltrasonic);
    private static final AnalogChannel leftUltrasonic = new AnalogChannel(Ports.leftUltrasonic);
    private static final DigitalOutput ultrasonicSignal = new DigitalOutput(Ports.ultrasonicSignal);

    private static final RobotDrive drive = new RobotDrive(leftMotor,
            rightMotor);

    public static void tankDrive(double leftMotor, double rightMotor) {
        drive.tankDrive(-leftMotor, -rightMotor);
    }

    static double kP = 0;
    static Timer timer = new Timer();

    /**
     *
     * @param time
     * @param speed
     */
    public static void driveStraight(double time, double speed) {
        if (timer.get() < time) {
            //read the gyro
            double angle = gyro.getAngle();
            //calculate motor output
            kP = SmartDashboard.getNumber("kP", 0.1);
            double rightMotorOutput = speed + kP * angle;
            double leftMotorOutput = speed - kP * angle;
            if (rightMotorOutput > 1) {
                rightMotorOutput = 1;
            }
            if (leftMotorOutput > 1) {
                leftMotorOutput = 1;
            }
            if (rightMotorOutput < -1) {
                rightMotorOutput = - 1;
            }
            if (leftMotorOutput < -1) {
                leftMotorOutput = -1;
            }
            //set motor output
            drive.tankDrive(-leftMotorOutput, -rightMotorOutput);
        }
    }

    public static void autoOrient() {

    }
}
