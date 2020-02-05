package org.firstinspires.ftc.teamcode.FourBarPrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="AutoBlue", group="TankBot")
public class BlueAuto extends LinearOpMode {
    public DcMotor leftFront   = null;
    public DcMotor  rightFront  = null;
    public DcMotor leftBack   = null;
    public DcMotor  rightBack  = null;
    HardwareMap hwMap           =  null;
    public Servo grabber = null;
    public DcMotor lift = null;
    public static final double MID_SERVO       =  0.51;
    double          clawPosition  = 0.51;

    // MecanumHardware robot = new MecanumHardware();
    public void init(HardwareMap ahwMap) {

        //SWITCHED REVERSE, SO POSSIBLY EVERYTHING WRONG BUT STRAFE
        hwMap = ahwMap;
        leftFront = hwMap.get(DcMotor.class, "left_front");
        rightFront = hwMap.get(DcMotor.class, "right_front");
        leftBack = hwMap.get(DcMotor.class, "left_back");
        rightBack = hwMap.get(DcMotor.class, "right_back");
        grabber = hwMap.get(Servo.class, "grabber");
        lift = hwMap.get(DcMotor.class, "lift");
        leftFront.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightFront.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        leftBack.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightBack.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

       /* leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER); */


        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        lift.setPower(0);

        grabber.setPosition(MID_SERVO);
    }

    @Override
    public void runOpMode() {

        init(hardwareMap);

        waitForStart();

        /*EASY MODE */
        sleepRunning(1807);

        grabbingBlock();

        takeBreak();

        liftingUp();

        sleepRunningBack(1150); //reg 750

        leftTurn();

        sleepRunning(2679);

        letGoBlock();

        takeBreak();

        sleepRunningBack(1259);

        leftFront.setPower(0.0);
        rightFront.setPower(0.0);
        leftBack.setPower(0.0);
        rightBack.setPower(0.0);


        /*sleepRunning(1807);

        grabbingBlock();

        takeBreak();

        liftingUp();

        sleepRunningBack(0750);

        leftTurn();

        sleepRunning(4420);

        rightTurn();

        sleepRunning(0750);

        letGoBlock();

        takeBreak();

        sleepRunningBack(0750);

        rightTurn();

        sleepRunning(2518);

        leftFront.setPower(0.0);
        rightFront.setPower(0.0);
        leftBack.setPower(0.0);
        rightBack.setPower(0.0); */


    }

    public void sleepRunning(int time) {


        leftFront.setPower(0.3);
        rightFront.setPower(0.3);
        leftBack.setPower(0.3);
        rightBack.setPower(0.3);
        sleep(time);

        leftFront.setPower(0.0);
        rightFront.setPower(0.0);
        leftBack.setPower(0.0);
        rightBack.setPower(0.0);



    }

    public void sleepRunningBack(int time) {


        leftFront.setPower(-0.3);
        rightFront.setPower(-0.3);
        leftBack.setPower(-0.3);
        rightBack.setPower(-0.3);
        sleep(time);

        leftFront.setPower(0.0);
        rightFront.setPower(0.0);
        leftBack.setPower(0.0);
        rightBack.setPower(0.0);



    }


    public void rightTurn() {


        leftFront.setPower(0.6);
        rightFront.setPower(-0.6);
        leftBack.setPower(0.6);
        rightBack.setPower(-0.6);
        sleep(1850);

        leftFront.setPower(0.0);
        rightFront.setPower(0.0);
        leftBack.setPower(0.0);
        rightBack.setPower(0.0);



    }


    public void leftTurn() {


        leftFront.setPower(-0.6);
        rightFront.setPower(0.6);
        leftBack.setPower(-0.6);
        rightBack.setPower(0.6);
        sleep(1850);

        leftFront.setPower(0.0);
        rightFront.setPower(0.0);
        leftBack.setPower(0.0);
        rightBack.setPower(0.0);



    }

    public void takeBreak() {

        leftFront.setPower(0.0);
        rightFront.setPower(0.0);
        leftBack.setPower(0.0);
        rightBack.setPower(0.0);
        sleep(1500);
    }

    public void grabbingBlock() {

        clawPosition = 0.59;
        clawPosition = Range.clip(clawPosition, 0, 1);
        grabber.setPosition(clawPosition);

    }

    public void letGoBlock() {

        clawPosition = 0.51;
        clawPosition = Range.clip(clawPosition, 0, 1);
        grabber.setPosition(clawPosition);

    }

    public void liftingUp() {

        lift.setPower(-0.5);
        sleep(1500);

    }

    public void liftingDown() {

        lift.setPower(-0.5);
        sleep(1500);

    }
}
