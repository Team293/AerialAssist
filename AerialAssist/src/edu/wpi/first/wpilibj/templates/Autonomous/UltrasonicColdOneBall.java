/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autonomous;

import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.templates.subsystems.Feeder;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;

/**
 *
 * @author Peter
 */
public class UltrasonicColdOneBall extends Auto {

    public UltrasonicColdOneBall() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        ShooterRack.setToShootingRPM();
        DriveTrain.rangeUltrasonics();
        Feeder.triggerEnabled();
        //feed in ball
        while (!Feeder.possessing()) {
            Feeder.feed();
        }
        Feeder.stop();

        //move forward until at shooting Distance
        while (!DriveTrain.isAtShootingDistance()) {
            DriveTrain.rangeUltrasonics();
            driveStraight(driveSpeedForward);
            //make sure you have a ball
            if (!Feeder.possessing()) {
                Feeder.feed();
            } else {
                Feeder.stop();
            }
            //spin up shooter wheels
            ShooterRack.run();
        }
        //FIRE!!!
        ShooterRack.autonomousFire();
        
        ShooterRack.stop();
        DriveTrain.stop();
    }
}
