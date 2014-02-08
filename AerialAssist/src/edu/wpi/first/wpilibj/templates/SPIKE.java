/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.templates.subsystems.Cage;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.templates.subsystems.Shooter;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class SPIKE extends IterativeRobot {

    Timer t = new Timer();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        Cage.release();
        ShooterRack.startEncoders();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        DriveTrain.autoAlign();
        DriveTrain.resetGyro();
        Shooter.shoot();
        //true means there is a second ball
        if (ballSetting)
        {
            DriveTrain.timer.reset();
            DriveTrain.driveStraight(2.25 , -0.70);//Made up Time
            pickUpBallUsingFeeder();
            DriveTrain.driveStraight(2.25 , 0.70);//Made up Time
            AutoAlign.autoAlign();
            shoot();
        }
        DriveTrain.timer.reset();
        DriveTrain.driveStraight(2.25 , 0.70);//Made up Time  
        
        //shooting loop
        
        }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        OperatorInterface.controlDriveTrain();
        OperatorInterface.controlFeeder();
        OperatorInterface.controlShooter();
        OperatorInterface.controlAuto();
        OperatorInterface.controlCatcher();
        OperatorInterface.controlCamera();

    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {

    }

}
