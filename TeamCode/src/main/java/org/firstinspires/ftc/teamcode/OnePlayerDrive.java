package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="OnePlayerDrive")
public class OnePlayerDrive extends LinearOpMode {

    MecanumHardware robot = new MecanumHardware();   // Use a Pushbot's hardware

    @Override
    public void runOpMode() {
        boolean directionToggle = true;
        boolean motorToggle = true;
        boolean inputDirectionToggle = true;
        boolean shooterPowerToggle = true;
        double motorCoefficient = .90;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Crown Joules... Yeeaaaaahhhhhhh!!!");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double motorCoeff = 1.2;
            double magnitude = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = -gamepad1.right_stick_x;
            double fld = (magnitude * Math.cos(robotAngle) + rightX) * motorCoeff; //cos +
            double frd = (magnitude * Math.sin(robotAngle) - rightX) * motorCoeff; //sin -
            double bld = (magnitude * Math.sin(robotAngle) + rightX) * motorCoeff; //sin
            double brd = (magnitude * Math.cos(robotAngle) - rightX) * motorCoeff; //cos



            robot.leftFront.setPower(fld);
            robot.rightFront.setPower(frd);
            robot.leftBack.setPower(bld);
            robot.rightBack.setPower(brd);

            telemetry.addData("leftFront", "%.2f", fld);
            telemetry.addData("rightFront", "%.2f", frd);
            telemetry.addData("leftBack", "%.2f", bld);
            telemetry.addData("rightBack", "%.2f", brd);



            //Motor Coefficients
            if (gamepad1.a)
                motorCoefficient = 0.90;
            else if (gamepad1.b)
                motorCoefficient = 1.0;

            if (gamepad1.right_bumper)
                robot.input.setPower(-1);


            robot.input.setPower(gamepad1.right_trigger);
            robot.output.setPower(gamepad1.left_trigger * motorCoefficient);
            robot.output2.setPower(gamepad1.left_trigger * motorCoefficient);

            if(gamepad1.left_bumper)
                robot.input.setPower(.90);
            if(!gamepad1.dpad_up || !gamepad1.dpad_down)
                robot.wobble.setPower(0);
            if(gamepad1.dpad_up)
                robot.wobble.setPower(.5);
            if(gamepad1.dpad_down)
                robot.wobble.setPower(-.5);

            telemetry.addData("bool up", gamepad1.dpad_up);
            telemetry.addData("bool down", gamepad1.dpad_down);

            telemetry.addData("Driving motor direction toggle: ", directionToggle);
            telemetry.addData("Shooting Motor Direction (shouldn't need to use): ", motorToggle);
            telemetry.addData("Shooter Motor Power: ", motorCoefficient);

            telemetry.addData("Direction", gamepad1.dpad_up);
            telemetry.update();
        }
    }
}