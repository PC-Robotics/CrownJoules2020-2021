package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MecanumHardwareTwo {
    //Control Hub Assignments
    //C: Control hub
    //E: Expansion hub

    /* Public OpMode members. */


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    public ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public MecanumHardwareTwo(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

    }

}