package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Ethan Drive")
public class EthanDrive extends LinearOpMode {

    public DcMotor leftFront   = null;
    public DcMotor  rightFront  = null;
    public DcMotor leftBack   = null;
    public DcMotor  rightBack  = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    public ElapsedTime period  = new ElapsedTime();

    /* Constructor */

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        //cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());

        // Define and Initialize Motors
        leftFront = hwMap.get(DcMotor.class, "left_front");
        rightFront = hwMap.get(DcMotor.class, "right_front");
        leftBack = hwMap.get(DcMotor.class, "left_back");
        rightBack = hwMap.get(DcMotor.class, "right_back");

        leftFront.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightFront.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        leftBack.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightBack.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors

        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0); //
        leftBack.setPower(0);
        rightBack.setPower(0);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void runOpMode() {
        boolean directionToggle = true;
        boolean motorToggle = true;
        boolean inputDirectionToggle = true;
        boolean shooterPowerToggle = true;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hi Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Driving
            //if (gamepad1.left_stick_y > .2f || gamepad1.left_stick_x > .2f || gamepad2.right_stick_x > .2f || gamepad2.left_stick_y > .2f) {
            double motorCoeff = 1.2;
            double magnitude = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            double fld = (magnitude * Math.cos(robotAngle) + rightX) * motorCoeff; //cos +
            double frd = (magnitude * Math.sin(robotAngle) - rightX) * motorCoeff; //sin -
            double bld = (magnitude * Math.sin(robotAngle) + rightX) * motorCoeff; //sin
            double brd = (magnitude * Math.cos(robotAngle) - rightX) * motorCoeff; //cos

            if (gamepad1.right_bumper) {
                fld = fld * 0.4;
                frd = frd * 0.4;
                bld = bld * 0.4;
                brd = brd * 0.4;
            }

            leftFront.setPower(fld);
            rightFront.setPower(frd);
            leftBack.setPower(bld);
            rightBack.setPower(brd);

            telemetry.addData("leftFront", "%.2f", fld);
            telemetry.addData("rightFront", "%.2f", frd);
            telemetry.addData("leftBack", "%.2f", bld);
            telemetry.addData("rightB", "%.2f", brd);


            //Driving Direction Toggle
            if(gamepad1.a && directionToggle){
                leftFront.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
                rightFront.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
                leftBack.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
                rightBack.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
                directionToggle = false;
            }
            else if(gamepad1.a && !directionToggle){
                leftFront.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
                rightFront.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
                leftBack.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
                rightBack.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
                directionToggle = true;
            }

            //see if we need deadzone

            telemetry.update();
        }
    }
}