package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MecanumHardware {
    //Control Hub Assignments
    //C: Control hub
    //E: Expansion hub

    /* Public OpMode members. */
    public DcMotor leftFront; //C0
    public DcMotor  rightFront; //C1
    public DcMotor leftBack; //C2
    public DcMotor  rightBack; //C3
    public DcMotor input; //E0
    public DcMotor output; //E1
    public DcMotor output2;
    public DcMotor wobble;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    public ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public MecanumHardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        //cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());

        // Define and Initialize Motors
        leftFront  = hwMap.get(DcMotor.class, "left_front");
        rightFront = hwMap.get(DcMotor.class, "right_front");
        leftBack  = hwMap.get(DcMotor.class, "left_back");
        rightBack = hwMap.get(DcMotor.class, "right_back");
        input = hwMap.get(DcMotor.class, "input");
        output = hwMap.get(DcMotor.class, "output");
        output2 = hwMap.get(DcMotor.class, "output2");
        //wobble = hwMap.get(DcMotor.class, "wobble");

        //webcamName = hwMap.get(WebcamName.class, "Webcam 1");
        leftFront.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightFront.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        leftBack.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightBack.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        input.setDirection(DcMotor.Direction.REVERSE);
        output.setDirection(DcMotor.Direction.REVERSE);
        output2.setDirection(DcMotor.Direction.REVERSE);
        //wobble.setDirection(DcMotor.Direction.REVERSE);


        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        input.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        output.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        output2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //wobble.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set all motors to zero power
        input.setPower(0);
        output.setPower(0);
        output2.setPower(0);
        leftFront.setPower(0);
        rightFront.setPower(0); //
        leftBack.setPower(0);
        rightBack.setPower(0);
        //wobble.setPower(0);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        input.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        output.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //wobble.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
      /*  leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER); */
        //lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }

}