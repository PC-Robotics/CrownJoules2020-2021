package org.firstinspires.ftc.teamcode.InverseStackerPrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.InverseStackerPrograms.StackerHardware;

@TeleOp(name="StackHehexd", group="TankBot")
public class StackerDrive extends LinearOpMode {

    StackerHardware robot = new StackerHardware();   // Use a Pushbot's hardware

    @Override
    public void runOpMode() {
        double          clawPosition  = 0.5;                  // Servo mid position
        boolean intakeValIn = true;
        boolean intakeValOut = true;
        boolean toggleIn = true;
        boolean toggleOut = true;
        ElapsedTime timer  = new ElapsedTime();
        double placerSpeed = .7;
        boolean placerVal = true;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //double slowModeVal = gamepad1.left_trigger;
            double liftVal = 0.75;

            //Driving Module
            if (Math.abs(gamepad1.left_stick_y) > .2 || Math.abs(gamepad1.left_stick_x) > .2 || Math.abs(gamepad1.right_stick_y) > .2 || Math.abs(gamepad1.left_stick_x) > .2) {
                double motorCoeff = 1.2;
                double magnitude = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
                double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = gamepad1.right_stick_x;
                double fld = (magnitude * Math.cos(robotAngle) + rightX) * motorCoeff; //cos +
                double frd = (magnitude * Math.sin(robotAngle) - rightX) * motorCoeff; //sin -
                double bld = (magnitude * Math.cos(robotAngle) + rightX) * motorCoeff; //sin
                double brd = (magnitude * Math.sin(robotAngle) - rightX) * motorCoeff; //cos
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

                telemetry.addData("leftFront",  "%.2f", fld);
                telemetry.addData("rightFront", "%.2f", frd);
                telemetry.addData("leftBack",  "%.2f", bld);
                telemetry.addData("rightBack", "%.2f", brd);

            } else
            {
                robot.leftFront.setPower(0);
                robot.rightFront.setPower(0);
                robot.leftBack.setPower(0);
                robot.rightBack.setPower(0);
            }

            //module for Intake
            if (toggleIn && gamepad2.x) {  // Only execute once per Button push
                toggleIn = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
                intakeValOut = true;
                if (intakeValIn) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
                    intakeValIn = false;
                    robot.leftIntake.setPower(.5);
                    robot.rightIntake.setPower(.5);
                } else {
                    intakeValIn = true;
                    robot.leftIntake.setPower(0);
                    robot.rightIntake.setPower(0);
                }
            } else if (gamepad2.x == false) {
                toggleIn = true; // Button has been released, so this allows a re-press to activate the code above.
            }

            if (toggleOut && gamepad2.y) {  // Only execute once per Button push
                toggleOut = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
                intakeValIn = true;
                if (intakeValOut) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
                    intakeValOut = false;
                    robot.leftIntake.setPower(-.5);
                    robot.rightIntake.setPower(-.5);
                } else {
                    intakeValOut = true;
                    robot.leftIntake.setPower(0);
                    robot.rightIntake.setPower(0);
                }
            } else if (gamepad2.y == false) {
                toggleOut = true; // Button has been released, so this allows a re-press to activate the code above.
            }


            if (gamepad2.a && placerVal) {
                timer.reset();
                placerSpeed = 0.5;
                placerVal = false;
            }
            else if (gamepad2.b && placerVal) {

                timer.reset();
                placerSpeed = -0.5;
                placerVal = false;


            }
            if (!(gamepad2.a || gamepad2.b)) {
                placerVal = true;
            }


            if(timer.milliseconds() < 750) {
                robot.blockPlacer.setPower(placerSpeed);
            }
            else {
                robot.blockPlacer.setPower(0.0);
            }




            //blockPlacingModule
            boolean blockPlacerBoolean = true;
            if(gamepad2.a && blockPlacerBoolean)
            {
                blockPlacerBoolean = false;
                timer.reset();
                robot.blockPlacer.setPower(.7);
            }
            else if(gamepad2.a && !blockPlacerBoolean)
            {
                blockPlacerBoolean = true;
                timer.reset();
                robot.blockPlacer.setPower(0.0);
            }

            //lifting module....
            if (Math.abs(gamepad2.left_stick_y) > 0.2) {
                if(gamepad2.left_stick_y < 0){
                    robot.lift.setPower(.09); //doubler check numbers
                }
                else {
                    robot.lift.setPower(-.09);//double check numbers
                }
            }
            else {
                robot.lift.setPower(0.0);
            }

            //module for sideBlocks... note that the winch is gonna take some trial and error with!
            boolean sideDropper = true;
            double sidePosition = .7;
            if(gamepad2.left_bumper && sideDropper)
            {
                sideDropper = false;
                robot.sideDropper.setPosition(sidePosition);
            }
            else if(gamepad2.left_bumper && !sideDropper)
            {
                sideDropper = true;
                robot.sideDropper.setPosition(0);
            }

            //extender module
            double extenderPower = .7;
            if(gamepad2.b) {
                robot.extender.setPower(extenderPower);
            }
            else if(gamepad2.right_bumper)
            {
                robot.extender.setPower(-extenderPower);
            }
            else
                robot.extender.setPower(0.0);

            //capstone lift Module
            double capstoneLiftMotorPower = .09;
            if(Math.abs(gamepad2.right_stick_y) > .2)
            {
                if(gamepad2.right_stick_y > 0)
                    robot.capstoneLift.setPower(capstoneLiftMotorPower);
                else
                    robot.capstoneLift.setPower(-capstoneLiftMotorPower);
            }
            else
                robot.capstoneLift.setPower(0);
            //capstone pivot lift module
            double capstonePivotPower = .7;
            if(gamepad2.right_trigger > .1)
                robot.capstonePivot.setPower(capstonePivotPower);
            else if (gamepad2.left_trigger > .1)
                robot.capstonePivot.setPower(-capstonePivotPower);
            else
                robot.capstonePivot.setPower(0.0);

            /*
                Recap:
                Gamepad1:
                Driving with mecanum and rotation

                Gamepad2:
                leftStick controls regular lift

                x - block intake with motored wheels
                y - block output with motored wheels (idk if we need this but helpful)
                a - block placement module

                b - extends the extender for side dropper
                right bumper - retracts the extender for side dropper
                left bumper - drops it down...

                right stick controls capstone lift
                right trigger rotates capstone pivot right
                left trigger rotates capstone pivot left
                (all buttons are set on toggle for now)
             */


            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}

