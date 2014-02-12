/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    Timer t = new Timer();
    DigitalInput ballSetting = new DigitalInput(Ports.ballSetting);

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        //Test.addComponents();
        //Cage.release();
        SmartDashboard.putNumber("1", 0.0);
        SmartDashboard.putNumber("2", 0.0);
        SmartDashboard.putNumber("3", 0.0);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

        boolean secondaryBall = ballSetting.get();
        double shootTime = 5; //Amount of time needed to shoot and move forward
        double maxBackDistance = 6;
        ShooterRack.setHighRPM();
        ShooterRack.setToFiring();

        //if running two ball autonomous
        if (secondaryBall) {
            while (!DriveTrain.autoDistance(ShooterRack.shooterDistance, 0.7)) {
            }
            //shoot first ball
            ShooterRack.autonomousFiring();
            //pick up second ball
            while (!Feeder.ballLimit.get() && DriveTrain.getDistance() < maxBackDistance) {
                DriveTrain.driveStraight(-0.7);
                Feeder.feed();
            }
            ShooterRack.setToFiring();
        }
        while (!DriveTrain.autoDistance(ShooterRack.shooterDistance, 0.7)) {
        }
        while (Feeder.ballLimit.get() && DriverStation.getInstance().getMatchTime() < shootTime) {
            if (Vision.getBlobCount() == 2) {
                ShooterRack.autonomousFiring();
            }
        }
        ShooterRack.setToFiring();
        if (Feeder.ballLimit.get() && DriverStation.getInstance().getMatchTime()<8.0) {
            ShooterRack.autonomousFiring();
        }

        while (!DriveTrain.autoDistance(5, 0.7))//Close enough to the wall to count as being within our zone
        {
        }
        DriveTrain.stop();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //LEDs.indicateSituation();
        OperatorInterface.controlDriveTrain();
        OperatorInterface.controlFeeder();
        //OperatorInterface.controlShooter();
        OperatorInterface.manualControlShooter();
        //OperatorInterface.controlAutoAlign();
        //OperatorInterface.controlCatcher();
        //OperatorInterface.controlCamera();

    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

}
