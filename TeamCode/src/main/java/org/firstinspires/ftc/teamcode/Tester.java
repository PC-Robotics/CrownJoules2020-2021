package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Tester")
public class Tester extends LinearOpMode {

    MecanumHardware robot = new MecanumHardware();   // Use a Pushbot's hardware

    @Override
    public void runOpMode() {

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

            telemetry.addData("Intake: ", gamepad2.right_trigger);
            telemetry.addData("Shooter Motor Power: ", gamepad2.left_trigger);
            telemetry.addData("A: ", gamepad2.a);
            telemetry.addData("B: ", gamepad2.b);
            telemetry.addData("X: ", gamepad2.x);
            telemetry.addData("Y", gamepad2.y);
            telemetry.addData("Left stick: ", gamepad2.left_stick_x);
            telemetry.addData("right stick: ", gamepad2.right_stick_y);
            telemetry.addData("dpad up: ", gamepad2.dpad_up);
            telemetry.addData("dpad down", gamepad2.dpad_down);
            telemetry.addLine(gamepad2.toString());

            telemetry.update();
        }
    }
}