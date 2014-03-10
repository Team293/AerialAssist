/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.Autonomous.Auto;
import edu.wpi.first.wpilibj.templates.Autonomous.ColdOneBall;
import edu.wpi.first.wpilibj.templates.Autonomous.ColdTwoBall;
import edu.wpi.first.wpilibj.templates.Autonomous.CrossLine;
import edu.wpi.first.wpilibj.templates.Autonomous.HotOneBall;
import edu.wpi.first.wpilibj.templates.Autonomous.HotTwoBall;
import edu.wpi.first.wpilibj.templates.Autonomous.UltrasonicColdOneBall;
import edu.wpi.first.wpilibj.templates.Autonomous.UltrasonicColdTwoBall;
import edu.wpi.first.wpilibj.templates.Autonomous.UltrasonicHotTwoBall;
import edu.wpi.first.wpilibj.templates.subsystems.Cage;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.templates.subsystems.Feeder;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Spike extends IterativeRobot {

    DriverStationLCD LCD = DriverStationLCD.getInstance();
    SendableChooser chooser = new SendableChooser();
    String[] autonomiNames;
    Auto[] autonomi;
    Auto selectedAuto;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        autonomiNames = new String[]{"cross line",
            "one ball",
            "two ball",
            "one ball hot",
            "two ball hot",
            "US one ball",
            "US two ball",
            "US two ball hot"};
        autonomi = new Auto[]{new CrossLine(),
            new ColdOneBall(),
            new ColdTwoBall(),
            new HotOneBall(),
            new HotTwoBall(),
            new UltrasonicColdOneBall(),
            new UltrasonicColdTwoBall(),
            new UltrasonicHotTwoBall()};
        for (int i = 0; i < autonomiNames.length; ++i) {
            chooser.addObject(autonomiNames[i], autonomi[i]);
        }
        SmartDashboard.putData("Which Autonomouse?", chooser);

        ShooterRack.init();
        Feeder.triggerEnabled();
        ShooterRack.setToShootingRPM();
    }

    public void teleopInit() {
        Cage.release();
    }

    public void autonomousInit() {
        selectedAuto = (Auto) chooser.getSelected();
        selectedAuto.init();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        selectedAuto.run();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        OperatorInterface.controlDriveTrain();
        OperatorInterface.controlShooter();
        OperatorInterface.controlFeeder();
        OperatorInterface.controlCamera();
        DriveTrain.rangeUltrasonics();
        LCD.println(DriverStationLCD.Line.kUser1, 1, "" + DriveTrain.getLeftDistance());
        LCD.println(DriverStationLCD.Line.kUser2, 1, "" + DriveTrain.getRightDistance());
        LCD.updateLCD();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

    public void teleopDisabled() {
        DriveTrain.stop();
        Feeder.stop();
        ShooterRack.stop();
        Cage.reset();
    }

    public void autonomousDisabled() {
        DriveTrain.stop();
        Feeder.stop();
        ShooterRack.stop();
        Cage.reset();
    }

}
