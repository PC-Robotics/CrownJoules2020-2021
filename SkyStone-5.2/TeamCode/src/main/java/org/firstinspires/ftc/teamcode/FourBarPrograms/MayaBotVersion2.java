package org.firstinspires.ftc.teamcode.FourBarPrograms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="MayaBotV1.5", group="TankBot")
public class MayaBotVersion2 extends LinearOpMode {

    MecanumHardware robot = new MecanumHardware();   // Use a Pushbot's hardware

    public double left;
    public double right;
    public double drive;
    public double turn;
    public double max;
    public double clawPosition = 0.5;                  // Servo mid position
    public final double CLAW_SPEED = 0.01;                 // sets rate to move servo
    public boolean grabberVal = true;
    public double motorTester = 0.0;

    //double slowModeVal = gamepad1.left_trigger;
    public double liftVal = 0.75;


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            drive();
            lift();
            intake();
            telemetry.update();
        }
    }

    public void drive() {
        if (gamepad1.left_stick_y > .2f || gamepad1.left_stick_x > .2f || gamepad2.right_stick_x > .2f || gamepad2.left_stick_y > .2f) {
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

            robot.leftFront.setPower(fld);
            robot.rightFront.setPower(frd);
            robot.leftBack.setPower(bld);
            robot.rightBack.setPower(brd);

            telemetry.addData("leftFront", "%.2f", fld);
            telemetry.addData("rightFront", "%.2f", frd);
            telemetry.addData("leftBack", "%.2f", bld);
            telemetry.addData("rightBack", "%.2f", brd);
            telemetry.addData("grabber", "%.2f", clawPosition);
        } else {
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
            robot.rightBack.setPower(0);
        }
    }

    /* WORK WITH THIS  if (gamepad1.x)
           clawPosition += CLAW_SPEED;
       else if (gamepad1.y)
           clawPosition -= CLAW_SPEED;

           clawPosition = Range.clip(clawPosition, robot.MIN_SERVO, robot.MAX_SERVO);
       robot.grabber.setPosition(clawPosition);
           */

    public void intake() {

        if (gamepad2.x && grabberVal) {
            clawPosition += 0.04;
            grabberVal = false;
        } else if (gamepad2.y && grabberVal) {
            clawPosition -= 0.04;
            grabberVal = false;
        }
        if (!gamepad2.x && !gamepad2.y) {
            grabberVal = true;
        }

        clawPosition = Range.clip(clawPosition, 0, 1);
        robot.grabber.setPosition(clawPosition);
        //sleep(500);
    }

    public void lift() {
        robot.lift.setPower(gamepad2.left_stick_y);
    }

    public void intake2() {
        while (gamepad2.a) {
            //robot.intakeServo1.setPower();
            //robot.intakeServo2.setPower();
        }
    }


        // Pace this loop so jaw action is reasonable speed.
        //sleep(50);
}



