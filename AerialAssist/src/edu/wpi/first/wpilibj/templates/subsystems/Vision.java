/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.templates.Ports;

/**
 *
 * @author Peter
 */
public class Vision {

    private static final NetworkTable cameraTable = NetworkTable.getTable("SmartDashboard");
    private static final Servo servo = new Servo(Ports.cameraServo);

    public static double getBlobCount() {
        return cameraTable.getNumber("BLOB_COUNT", -1);
    }

    public static void setServo(double position) {
        servo.set(position);
    }

}
