/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.SpikeEncoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 *
 * @author Robotics Laptop B
 */
public class PShooter implements LiveWindowSendable {

    static int ID = 0;
    public Talon motor;
    public SpikeEncoder encoder;
    int id;
    double kP, setpoint, rpm;
    boolean reversed, enabled;

    /**
     *
     * @param motorPort
     * @param encoderA
     * @param encoderB
     * @param kP
     * @param tolerance
     * @param setpoint
     */
    PShooter(int motorPort, int encoderA, int encoderB, double kP, double setpoint, boolean reversed) {
        motor = new Talon(motorPort);
        encoder = new SpikeEncoder(encoderA, encoderB);
        this.kP = 0.0015;
        this.setpoint = setpoint;
        this.id = ID;
        this.reversed = reversed;
        this.enabled = false;
        ID++;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    public void enable() {
        enabled = true;
        encoder.start();
        encoder.reset();
        SmartDashboard.putNumber("kP " + id, kP);
        SmartDashboard.putNumber("setpoint " + id, setpoint);
    }

    public void run() {
        double output = 0, error = 0;
        if (enabled) {
            kP = SmartDashboard.getNumber("kP " + id, kP);
            setpoint = SmartDashboard.getNumber("setpoint " + id, setpoint);
            rpm = encoder.getRPM();
            error = setpoint - rpm;
            output = kP * error;
            motor.set(output);
        }
        SmartDashboard.putNumber("_kP " + id, kP);
        SmartDashboard.putNumber("_error" + id, error);
        SmartDashboard.putNumber("_output " + id, output);
        SmartDashboard.putNumber("_rpm " + id, rpm);

    }

    public void disable() {
        enabled = false;
    }

    public void setConstant(double kP) {
        this.kP = kP;
    }

    public boolean onTarget() {
        return Math.abs(setpoint - rpm) < 40;
    }

    public void stop() {
        motor.set(0);
    }

    //runs live window
    private ITableListener listener = new ITableListener() {
        public void valueChanged(ITable table, String key, Object value, boolean isNew) {

            if (key.equals("setpoint")) {
                if (setpoint != ((Double) value).doubleValue()) {
                    setSetpoint(((Double) value).doubleValue());
                }
            } else if (key.equals("enabled")) {
                if (enabled != ((Boolean) value).booleanValue()) {
                    if (((Boolean) value).booleanValue()) {
                        enable();
                    } else {
                        disable();
                    }
                }
            } else if (key.equals("kP")) {
                if (kP != ((Double) value).doubleValue()) {
                    setConstant(((Double) value).doubleValue());
                }
            }
        }
    };

    private ITable table;

    public void initTable(ITable table) {

        if (this.table != null) {
            this.table.removeTableListener(listener);
        }
        this.table = table;
        if (table != null) {
            table.putNumber("setpoint", setpoint);
            table.putBoolean("enabled", enabled);
            table.putNumber("kP", kP);
            table.addTableListener(listener, false);
        }
    }

    public void updateTable() {

    }

    public void startLiveWindowMode() {
        disable();
    }

    public void stopLiveWindowMode() {

    }

    public ITable getTable() {
        return table;
    }

    public String getSmartDashboardType() {
        return "PShooter";
    }
}
