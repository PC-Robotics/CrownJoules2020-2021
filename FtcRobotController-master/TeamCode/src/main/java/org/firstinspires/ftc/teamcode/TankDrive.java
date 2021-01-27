package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="TwoPlayerDrive")
public class TankDrive extends LinearOpMode {

    MecanumHardware robot = new MecanumHardware();   // Use a Pushbot's hardware

    @Override
    public void runOpMode() {
        boolean directionToggle = true;
        boolean motorToggle = true;
        boolean inputDirectionToggle = true;
        boolean shooterPowerToggle = true;
        double motorCoefficient = 1.0;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Good Luck Nate and Ethan!");    //
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
                double rightX = -gamepad1.right_stick_x;
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


            //Driving Direction Toggle
            if(gamepad1.a && directionToggle){
                robot.leftFront.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
                robot.rightFront.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
                robot.leftBack.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
                robot.rightBack.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
                directionToggle = false;
            }
            else if(gamepad1.a && !directionToggle){
                robot.leftFront.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
                robot.rightFront.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
                robot.leftBack.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
                robot.rightBack.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
                directionToggle = true;
            }

            //Motor Coefficients
            if (gamepad2.a)
                motorCoefficient = 0.85;
            else if (gamepad2.b)
                motorCoefficient = 1.0;

            if (gamepad2.left_bumper)
                robot.input.setPower(-1);


            robot.input.setPower(gamepad2.right_trigger);
            robot.output.setPower(gamepad2.left_trigger * motorCoefficient);
            robot.output2.setPower(gamepad2.left_trigger * motorCoefficient);
            //reverse motor direction
            //Three things in autonomous
            //1 Grabbing the wobble goal -> blue target zone (15 points)
            //2 Navigation -> 5 points (just moving)
            //3 Power Shot -> 15 points per thing.... 12 points per top...
            //Autonomous: give it 3 seconds to spin up and then advance the first r

            //check if we have to reverse the direction for this...

            if(gamepad2.right_bumper)
                robot.input.setPower(.85);
            if(!gamepad2.dpad_up || !gamepad2.dpad_down)
                robot.wobble.setPower(0);
            if(gamepad2.dpad_up)
                robot.wobble.setPower(.6);
            if(gamepad2.dpad_down)
                robot.wobble.setPower(-1);

            telemetry.addData("bool up", gamepad2.dpad_up);
            telemetry.addData("bool down", gamepad2.dpad_down);


            //Input Direction Toggle

            //Shooter Power Toggle
            /*
            if (gamepad1.y && shooterPowerToggle){
                robot.output.setPower(1);
                shooterPowerToggle = false;
            }
            else if (gamepad1.y && shooterPowerToggle){
                robot.output.setPower(0);
                shooterPowerToggle = true;
            }
             */

            telemetry.addData("Driving motor direction toggle: ", directionToggle);
            telemetry.addData("Shooting Motor Direction (shouldn't need to use): ", motorToggle);
            telemetry.addData("Shooter Motor Power: ", motorCoefficient);
            telemetry.update();
        }
    }
}