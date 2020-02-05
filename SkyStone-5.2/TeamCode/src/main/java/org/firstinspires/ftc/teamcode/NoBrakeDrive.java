package org.firstinspires.ftc.teamcode.FourBarPrograms;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp(name="ZeroBrake hehexd", group="TankBot")
public class NoBrakeDrive extends LinearOpMode {

    public DcMotor leftFront   = null;
    public DcMotor  rightFront  = null;
    public DcMotor leftBack   = null;
    public DcMotor  rightBack  = null;
    public Servo grabber = null;
    public DcMotor lift = null;
    WebcamName webcamName = null;
    public static final double MID_SERVO       =  0.51;
    public static final double MIN_SERVO       =  0.0;
    public static final double MAX_SERVO       =  1.0;
    BNO055IMU imu = null;
    int cameraMonitorViewId;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    public ElapsedTime period  = new ElapsedTime();

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
        grabber = hwMap.get(Servo.class, "grabber");
        lift = hwMap.get(DcMotor.class, "lift");
        webcamName = hwMap.get(WebcamName.class, "Webcam 1");
        imu = hwMap.get(BNO055IMU.class, "imu");

        //imu = hwMap.get(BNO055IMU.class, "imu");
        leftFront.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightFront.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        leftBack.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightBack.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors


        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        lift.setPower(0);

        grabber.setPosition(MID_SERVO);


        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
      /*  leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER); */
        //lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }  // Use a Pushbot's hardware

    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;
        double max;
        double          clawPosition  = 0.51;                  // Servo mid position
        final double    CLAW_SPEED  = 0.01;                 // sets rate to move servo
        boolean grabberVal = true;
        double motorTester = 0.0;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //double slowModeVal = gamepad1.left_trigger;
            double liftVal = 0.75;

            //The left joystick is used to translate the robot, while the right joystick controls the rotation of the robot.
                /*double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
                double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = gamepad1.right_stick_x; //Mess with 0.5 value
                double v1 = r * Math.cos(robotAngle) + rightX;
                double v2 = r * -Math.sin(robotAngle) - rightX;
                double v3 = r * Math.sin(robotAngle) + rightX;
                double v4 = r * -Math.cos(robotAngle) - rightX;
                //left_stick_x (top) is neg, was positive before
                if (gamepad1.right_bumper) {

                    v1 = v1 * 0.2;
                    v2 = v2 * 0.2;
                    v3 = v3 * 0.2;
                    v4 = v4 * 0.2;

                } */
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


                /*LOSER VERSION
                double r = Math.hypot(gamepad1.right_stick_x, -gamepad1.left_stick_y);
                double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4;
                double rightX = gamepad1.left_stick_x; //Mess with 0.5 value
                double v1 = r * Math.cos(robotAngle) + rightX;
                double v2 = r * Math.sin(robotAngle) - rightX;
                double v3 = r * Math.sin(robotAngle) + rightX;
                double v4 = r * Math.cos(robotAngle) - rightX;
                 */




            /* FATHERBOARDS CODE
            float motorPower = 1.2f;
            if((Math.abs(gamepad1.left_stick_x) > 0.2 || (Math.abs(gamepad1.left_stick_y) > 0.2 )) || (Math.abs(gamepad1.right_stick_x) > 0.2 || (Math.abs(gamepad1.right_stick_y) > 0.2)))
            {
                double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
                double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = -gamepad1.right_stick_x;
                //left motor
                final double v1 = r * Math.cos(robotAngle) + rightX * motorPower;
                //right motor
                final double v2 = r * Math.sin(robotAngle) - rightX * motorPower;
                //left motor2
                final double v3 = r * Math.sin(robotAngle) + rightX * motorPower;
                //right motor2
                final double v4 = r * Math.cos(robotAngle) - rightX * motorPower;

                robot.leftFront.setPower(v1);
                robot.rightFront.setPower(v2);
                robot.leftBack.setPower(v3);
                robot.rightBack.setPower(v4);
            }
            else
            {
                robot.leftFront.setPower(0);
                robot.leftBack.setPower(0);
                robot.rightFront.setPower(0);
                robot.rightBack.setPower(0);
            } */


         /* WORK WITH THIS  if (gamepad1.x)
                clawPosition += CLAW_SPEED;
            else if (gamepad1.y)
                clawPosition -= CLAW_SPEED;

                clawPosition = Range.clip(clawPosition, robot.MIN_SERVO, robot.MAX_SERVO);
            robot.grabber.setPosition(clawPosition);
                */


            if (gamepad2.x && grabberVal) {

                clawPosition += 0.045;
                grabberVal = false;



            }
            else if (gamepad2.y && grabberVal) {

                clawPosition -= 0.045;
                grabberVal = false;
            }

            if (!gamepad2.x && !gamepad2.y) {

                grabberVal = true;

            }

            clawPosition = Range.clip(clawPosition, 0, 1);
            grabber.setPosition(clawPosition);

            lift.setPower(gamepad2.left_stick_y);

          /*  if (gamepad2.b) {

                robot.lift.setPower(-liftVal);
                motorTester = 1.0;
            }
            else if (gamepad2.a) {

                robot.lift.setPower(liftVal);
                motorTester = 0.0;

            }
            else {
                robot.lift.setPower(0.0);
            } */


            // Send telemetry message to signify robot running;
            telemetry.addData("leftFront",  "%.2f", fld);
            telemetry.addData("rightFront", "%.2f", frd);
            telemetry.addData("leftBack",  "%.2f", bld);
            telemetry.addData("rightBack", "%.2f", brd);
            telemetry.addData("grabber", "%.2f", clawPosition);
            //telemetry.addData("slowmode", "%.2f", slowModeVal);
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }

}
