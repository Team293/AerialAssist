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
import edu.wpi.first.wpilibj.templates.Autonomous.*;
import edu.wpi.first.wpilibj.templates.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Spike extends IterativeRobot {

    DriverStationLCD LCD = DriverStationLCD.getInstance();
    SendableChooser autonomousChooser = new SendableChooser();
    SendableChooser colorChooser = new SendableChooser();
    String[] autonomiNames;
    String[] colorNames;
    Auto[] autonomi;
    Auto selectedAuto;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        autonomiNames = new String[]{"cross line",
            "cold one ball",
            "cold two ball",
            "hot one ball",
            "hot two ball",
            "US cold one ball",
            "US cold two ball",
            "US hot one ball",
            "US hot two ball"};

        colorNames = new String[]{"BLUE",
            "RED"};

        autonomi = new Auto[]{new CrossLine(),
            new ColdOneBall(),
            new ColdTwoBall(),
            new HotOneBall(),
            new HotTwoBall(),
            new UltrasonicColdOneBall(),
            new UltrasonicColdTwoBall(),
            new UltrasonicHotOneBall(),
            new UltrasonicHotTwoBall()};
        for (int i = 0; i < autonomiNames.length; ++i) {
            autonomousChooser.addObject(autonomiNames[i], autonomi[i]);
        }
        SmartDashboard.putData("Which Autonomouse?", autonomousChooser);

        for (int i = 0; i < colorNames.length; ++i) {
            colorChooser.addObject(colorNames[i], colorNames[i]);
        }
        SmartDashboard.putData("Which Allaince Color?", colorChooser);

        ShooterRack.init();
        Feeder.triggerEnabled();
        ShooterRack.setToShootingRPM();
    }

    public void teleopInit() {
        Cage.release();
    }

    public void autonomousInit() {
        if (((String) autonomousChooser.getSelected()).equals("RED")) {
            LEDs.RED = true;
        } else {
            LEDs.RED = false;
        }
        selectedAuto = (Auto) autonomousChooser.getSelected();
        SmartDashboard.putString("selected auto: ", autonomousChooser.getSelected().toString());
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
        Cage.reset();
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
    }

    public void autonomousDisabled() {
        Auto.autoTimer.reset();
        DriveTrain.stop();
        Feeder.stop();
        ShooterRack.stop();
    }

}
