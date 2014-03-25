/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.subsystems.Feeder;
import edu.wpi.first.wpilibj.templates.subsystems.ShooterRack;

/**
 *
 * @author Peter
 */
public class HotTwoBall extends Auto {

    public HotTwoBall() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        //check blob count at beginning
        blobCount = SmartDashboard.getNumber("blobCount", 0);
        autoFeed();
        moveForward1();
        markTime();
        
        //turn and then shoot first ball
        while (Feeder.possessing()) {
            ShooterRack.run();
            //if left goal is hot, turn left
            if (blobCount == 2 && autoTimer.get() - commandStartTime < turnTime) {
                turn(turnLeft);
            } //if left goal is NOT hot, turn right
            else if (blobCount == 1 && autoTimer.get() - commandStartTime < turnTime) {
                turn(turnRight);
            } else {
                SmartDashboard.putString("debug..", "shooting");
                ShooterRack.fire();
            }
        }

        ShooterRack.stop();
        markTime();
        //turn back to straight
        while (autoTimer.get() - commandStartTime < turnTime) {
            if (blobCount == 1) {
                turn(turnLeft);
            } else if (blobCount == 2) {
                turn(turnRight);
            }
        }
        backFeed();
        moveForward2();
        markTime();
        //turn and shoot
        while (!Feeder.possessing()) {
            //if left goal was not hot the first time, turn left this time
            if (blobCount == 1 && autoTimer.get() - commandStartTime < turnTime) {
                turn(turnLeft);
            } //if left goal was hot the first time, turn right this time
            else if (blobCount == 2 && autoTimer.get() - commandStartTime < turnTime) {
                turn(turnRight);
            } else {
                ShooterRack.run();
                ShooterRack.fire();
            }
        }
        setTeleop();

    }
}
