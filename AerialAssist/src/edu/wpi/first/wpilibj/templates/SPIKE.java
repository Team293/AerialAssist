/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.subsystems.Cage;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.templates.subsystems.Feeder;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;
import edu.wpi.first.wpilibj.templates.subsystems.Vision;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class SPIKE extends IterativeRobot {

    boolean hasFired = false;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
//        Test.addComponents();
        ShooterRack.init();
        Feeder.triggerEnabled();
    }

    public void teleopInit() {
        Cage.release();
        ShooterRack.setToShootingRPM();
    }

    public void autonomousInit() {
        DriveTrain.resetGyro();
        Feeder.feed();
        hasFired = false;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        //make sure you have a ball
        ShooterRack.run();
        if (!Feeder.possessing()&&!hasFired) {
            DriveTrain.driveStraight(-0.4);
            Feeder.triggerEnabled();
        } else {
            double blobCount = Vision.getBlobCount();
            SmartDashboard.putNumber("blobCountFromCode", blobCount);
            SmartDashboard.putBoolean("blobCountFromCode", blobCount == 2);
            //shooting loop 
            if (blobCount == 2 && !ShooterRack.atRPM() && !hasFired) {
                hasFired = true;
                ShooterRack.startShooting();
                DriveTrain.startTimer();
            }
            //driving loop
            if (DriveTrain.getTime() < 2 && !ShooterRack.isShooting()) {
                DriveTrain.driveStraight(0.6);
            } else {
                DriveTrain.stop();
            }
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //LEDs.indicateSituation();
        OperatorInterface.controlDriveTrain();
        OperatorInterface.manualControlShooter();
        OperatorInterface.controlAutoAlign();
        OperatorInterface.controlCamera();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

}
