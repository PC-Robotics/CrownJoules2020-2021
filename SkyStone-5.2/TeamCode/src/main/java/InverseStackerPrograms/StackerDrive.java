package InverseStackerPrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.FourBarPrograms.MecanumHardware;

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
        boolean intakeValIn2 = true;
        boolean intakeValOut2 = true;
        boolean toggleIn2 = true;
        boolean toggleOut2 = true;


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


            if (toggleIn && gamepad2.x) {  // Only execute once per Button push
                toggleIn = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
                intakeValOut = true;
                if (intakeValIn) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
                    intakeValIn= false;
                    robot.leftIntake.setPower(1);
                    robot.rightIntake.setPower(1);
                } else {
                    intakeValIn= true;
                    robot.leftIntake.setPower(0);
                    robot.rightIntake.setPower(0);
                }
            } else if(gamepad2.x == false) {
                toggleIn = true; // Button has been released, so this allows a re-press to activate the code above.
            }

            if (toggleOut && gamepad2.y) {  // Only execute once per Button push
                toggleOut = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
                intakeValIn = true;
                if (intakeValOut) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
                    intakeValOut= false;
                    robot.leftIntake.setPower(-1);
                    robot.rightIntake.setPower(-1);
                } else {
                    intakeValOut= true;
                    robot.leftIntake.setPower(0);
                    robot.rightIntake.setPower(0);
                }
            } else if(gamepad2.y == false) {
                toggleOut = true; // Button has been released, so this allows a re-press to activate the code above.
            }




            if (toggleIn2 && gamepad2.a) {  // Only execute once per Button push
                toggleIn2 = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
                intakeValOut2 = true;
                if (intakeValIn2) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
                    intakeValIn2= false;
                    robot.blockPlacer.setPower(0.5);
                } else {
                    intakeValIn2= true;
                    robot.blockPlacer.setPower(0.0);
                }
            } else if(gamepad2.a == false) {
                toggleIn2 = true; // Button has been released, so this allows a re-press to activate the code above.
            }

            if (toggleOut2 && gamepad2.b) {  // Only execute once per Button push
                toggleOut2 = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
                intakeValIn2 = true;
                if (intakeValOut2) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
                    intakeValOut2 = false;
                    robot.blockPlacer.setPower(-0.5);
                } else {
                    intakeValOut2 = true;
                    robot.blockPlacer.setPower(0.0);
                }
            } else if(gamepad2.b == false) {
                toggleOut2 = true; // Button has been released, so this allows a re-press to activate the code above.
            }



            if (gamepad1.x) {

                robot.lift.setPower(0.5);


            }
            else if (gamepad1.y) {

                robot.lift.setPower(-0.5);


            }
            else {

                robot.lift.setPower(0.0);
            }


            // Send telemetry message to signify robot running;
            telemetry.addData("leftFront",  "%.2f", fld);
            telemetry.addData("rightFront", "%.2f", frd);
            telemetry.addData("leftBack",  "%.2f", bld);
            telemetry.addData("rightBack", "%.2f", brd);
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
