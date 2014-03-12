/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.templates.subsystems.Feeder;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;

/**
 *
 * @author Peter
 */
public class ColdTwoBall extends Auto {

    public ColdTwoBall() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        autoFeed();
        moveForward1();
        autoFire();
        backFeed();
        moveForward2();
        autoFire();
        setTeleop();
    }

}
