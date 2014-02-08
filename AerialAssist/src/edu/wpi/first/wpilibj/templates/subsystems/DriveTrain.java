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
    private static double kP = 0;

    private static final RobotDrive drive = new RobotDrive(leftMotor,
            rightMotor);

    public static void tankDrive(double leftMotor, double rightMotor) {
        drive.tankDrive(-leftMotor, -rightMotor);
    }
    
    public static void arcadeDrive(double speed, double rotate) {
        drive.arcadeDrive(-speed, rotate);
    }
    
    public static void stop() {
        drive.tankDrive(0, 0);
    }
    
    public static void resetGyro() {
        gyro.reset();
    }
    
    public static double getAngle() {
        double angle = gyro.getAngle();
        return angle;
    }

    /**
     *
     * @param speed
     */
    public static void driveStraight(double speed) {
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
    
    public static boolean turn(double targetAngle) {
        boolean state = false;
        
        double current = gyro.getAngle();
        
        double difference = targetAngle - current;
        
        double speed = 0;
        
        if (Math.abs(difference) > 5) {
            speed = difference / 180;
        } else {
            speed = 0;
        }
        
        arcadeDrive(0, speed);
        if (targetAngle == current) {
            state = true;
        }
        return state;
    }

    public static double convertToDistance(double rawVoltage) {
        return (rawVoltage + 0.0056) / 0.1141;
    }
    
    
    public static boolean autoAlign(){
        boolean alignedState = false;
        
        double leftDistance = convertToDistance(leftUltrasonic.getVoltage());
        double rightDistance = convertToDistance(rightUltrasonic.getVoltage());
        
        double difference = leftDistance - rightDistance;
        
        double rotateSpeed = difference/20;
        
        if (rotateSpeed > 1) {
            rotateSpeed = 1;
        } else if (rotateSpeed < -1) {
            rotateSpeed = -1;
        }
        
        if (Math.abs(rotateSpeed)>.05){
            DriveTrain.arcadeDrive(0, rotateSpeed);
        } else {
            stop();
            alignedState = true;
        }
        return alignedState;
    }
    
    public static double getDistance() {
        double distance = convertToDistance((leftUltrasonic.getAverageVoltage() + rightUltrasonic.getAverageVoltage())/2);
        return distance;
    }
    
    public static void autoDistance(double targetDistance) {
        autoAlign();
        
        double actualDistance = convertToDistance((leftUltrasonic.getAverageVoltage() + rightUltrasonic.getAverageVoltage())/2);
        double difference = targetDistance - actualDistance;
        
        double speed = difference/4;
        
        if (speed > 1) {
            speed = 1;
        } else if (speed < -1) {
            speed = -1;
        }
        if (Math.abs(speed) > 0.125) {
            driveStraight(speed);
        } else {
            stop();
        }

    }
}
