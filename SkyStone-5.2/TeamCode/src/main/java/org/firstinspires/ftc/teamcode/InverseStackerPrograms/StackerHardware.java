package org.firstinspires.ftc.teamcode.InverseStackerPrograms;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class StackerHardware {

    /* Public OpMode members. */
    //TENTATIVE HARDWARE MEMEBERS
    public DcMotor leftFront   = null;
    public DcMotor  rightFront  = null;
    public DcMotor leftBack   = null;
    public DcMotor  rightBack  = null;
    public DcMotor  lift  = null; //MAY NEED 2
    public DcMotor leftIntake = null;
    public DcMotor rightIntake = null;
    public CRServo blockPlacer = null; //MAY NEED 2
    public  Servo grabber = null;
    //public CRServo blockPlacerRight = null;
    public  Servo towerGrabLeft = null;
    public  Servo towerGrabRight = null;
    public DcMotor capstoneLift = null;
    public Servo sideDropper = null;
    public CRServo extender = null;
    public CRServo capstonePivot = null;

            //note: Please double check everything is safe hardware mapping wise!!!


    WebcamName webcamName = null; //MIGHT NOT USE
    public static final double MID_SERVO       =  0.5;
    public static final double MIN_SERVO       =  0.0;
    public static final double MAX_SERVO       =  1.0;
    BNO055IMU imu = null;
    int cameraMonitorViewId;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    public ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public StackerHardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());

        // Define and Initialize Motors
        leftFront  = hwMap.get(DcMotor.class, "left_front");
        rightFront = hwMap.get(DcMotor.class, "right_front");
        leftBack  = hwMap.get(DcMotor.class, "left_back");
        rightBack = hwMap.get(DcMotor.class, "right_back");
        leftIntake = hwMap.get(DcMotor.class, "left_intake");
        rightIntake = hwMap.get(DcMotor.class, "right_intake");
        blockPlacer = hwMap.get(CRServo.class, "block_placer");
        //capstoneLift = hwMap.get(DcMotor.class, "capstone_lift");
        //sideDropper = hwMap.get(Servo.class, "side_dropper");
        //extender = hwMap.get(CRServo.class, "extender");
        //capstonePivot = hwMap.get(CRServo.class, "capstonePivot");
        towerGrabLeft = hwMap.get(Servo.class, "grab_left");
        towerGrabRight = hwMap.get(Servo.class, "grab_right");
        lift = hwMap.get(DcMotor.class, "lift");
        //webcamName = hwMap.get(WebcamName.class, "Webcam 1");
        imu = hwMap.get(BNO055IMU.class, "imu");

        //imu = hwMap.get(BNO055IMU.class, "imu");
        leftFront.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightFront.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        leftBack.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightBack.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        lift.setDirection(DcMotor.Direction.FORWARD);
        rightIntake.setDirection(DcMotor.Direction.FORWARD);
        leftIntake.setDirection(DcMotor.Direction.REVERSE);
        //capstoneLift.setDirection(DcMotor.Direction.FORWARD);

        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //capstoneLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        lift.setPower(0);

        towerGrabLeft.setPosition(MID_SERVO);
        towerGrabRight.setPosition(MID_SERVO);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);// a
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);// b
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //c
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // abc were set without encoders
        // why is this one running with encoders and the others aren't... ^^^
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //capstoneLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
      /*  leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER); */
        //lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }

}

