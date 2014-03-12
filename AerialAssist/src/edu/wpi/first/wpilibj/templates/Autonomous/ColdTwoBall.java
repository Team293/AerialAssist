/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autonomous;

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
