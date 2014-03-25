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
public class ColdOneBall extends Auto {

    public ColdOneBall() {
        super();
    }

    public void init() {
        super.init();
    }

    public void run() {
        if (!Auto.hasRunAuto) {
            autoFeed();
            moveForward1();
            autoFire();
            setTeleop();
        }
    }
}
