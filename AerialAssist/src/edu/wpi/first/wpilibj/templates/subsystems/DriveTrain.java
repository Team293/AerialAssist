/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Robotics Laptop B
 */
public class DriveTrain {

    public static final Talon leftMotor = new Talon(Ports.leftDrive);
    public static final Talon rightMotor = new Talon(Ports.rightDrive);
    public static RobotDrive drive = new RobotDrive(leftMotor,
            rightMotor);
    static final AnalogChannel rightUltrasonic = new AnalogChannel(Ports.rightUltrasonic);
    static final AnalogChannel leftUltrasonic = new AnalogChannel(Ports.leftUltrasonic);
    static final DigitalOutput ultrasonicSignal = new DigitalOutput(Ports.ultrasonicSignal);
    private static double rightDistance, leftDistance, average;
    private static int ping = 0;
    private static final int sampleSize = 10;
    private static final double[] val = new double[sampleSize];

    public static void tankDrive(double leftMotor, double rightMotor) {
        drive.tankDrive(-leftMotor, -rightMotor);
    }

    public static void stop() {
        drive.tankDrive(0.0, 0.0);
    }

    public static void rangeUltrasonics() {
        //ping ultrasonic sensors
        ping++;
        if (ping % 5 == 0) {
            ultrasonicSignal.pulse(0.0001);
        }
        leftDistance = convertToDistance(leftUltrasonic.getAverageVoltage());
        rightDistance = convertToDistance(rightUltrasonic.getAverageVoltage());
        SmartDashboard.putNumber("leftD", leftDistance);
        SmartDashboard.putNumber("rightD", rightDistance);

        //remove oldest two values
        for (int i = 0; i < val.length - 2; i++) {
            val[i] = val[i + 2];
        }
        //add in the new values
        val[sampleSize - 2] = leftDistance;
        val[sampleSize - 1] = rightDistance;

        //average all 10 values
        int sum = 0;
        for (int i = 0; i < val.length; i++) {
            sum += val[i];
        }
        average = sum / val.length;

        //print stuff
        SmartDashboard.putNumber("leftD", leftDistance);
        SmartDashboard.putNumber("rightD", rightDistance);
        SmartDashboard.putNumber("average", average);
        isAtShootingDistance();
    }

    public static double getLeftDistance() {
        return leftDistance;
    }

    public static double getRightDistance() {
        return rightDistance;
    }

    /*
     * Changing the implementation of 
     */
    public static boolean isAtShootingDistance() {
        //returns true if average Ultrasonic distance is between 6 && 8
        boolean atDistance = Math.abs(average - 7) < 1;
        SmartDashboard.putBoolean("FIRE", atDistance);
        return atDistance;
    }

    public static double convertToDistance(double rawVoltage) {
        return (rawVoltage + 0.0056) / 0.1141;
    }

    private static void sort(double[] a) {
        while (!inOrder(a)) {
            for (int i = 0; i < a.length; i++) {
                //if in wrong order, flip
                if (a[i] > a[i + 1]) {
                    double temp = a[i + 1];
                    a[i + 1] = a[i];
                    a[i] = temp;
                }
            }
        }
    }

    private static boolean inOrder(double[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }

}
