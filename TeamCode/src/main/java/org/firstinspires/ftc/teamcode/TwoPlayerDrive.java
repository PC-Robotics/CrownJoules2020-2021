package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Func;


@TeleOp(name="TwoPlayerDrive")
public class TwoPlayerDrive extends LinearOpMode {

    MecanumHardware robot = new MecanumHardware();   // Use a Pushbot's hardware

    double batteryCurrent = 14.4;
    final double batteryRelative = 12.56;
    double batteryCoefficient =  batteryRelative / batteryCurrent;

    @Override
    public void runOpMode() {
        boolean directionToggle = true;
        boolean motorToggle = true;
        boolean inputDirectionToggle = true;
        boolean shooterPowerToggle = true;
        double motorCoefficient = .80;

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
            if (gamepad2.a)
                motorCoefficient = 0.80;
            else if (gamepad2.b)
                motorCoefficient = 1.0;

            if (gamepad2.right_bumper)
                robot.input.setPower(-1);


            robot.input.setPower(gamepad2.right_trigger * batteryCoefficient);
            robot.output.setPower(gamepad2.left_trigger * motorCoefficient * batteryCoefficient);
            robot.output2.setPower(gamepad2.left_trigger * motorCoefficient * batteryCoefficient);

            if(gamepad2.left_bumper)
                robot.input.setPower(.90 * batteryCoefficient);
            if(!gamepad2.dpad_up || !gamepad2.dpad_down)
                robot.wobble.setPower(0 * batteryCoefficient);
            if(gamepad2.dpad_up)
                robot.wobble.setPower(.5 * batteryCoefficient);
            if(gamepad2.dpad_down)
                robot.wobble.setPower(-.5 * batteryCoefficient);

            telemetry.addData("MotorCoefficient: ", motorCoefficient);
            telemetry.addData("voltage", "%.1f volts", getBatteryVoltage()); //does it have to be a new Function?

            telemetry.update();
        }
    }

    public double getBatteryVoltage()
    {
        double result = Double.POSITIVE_INFINITY;

        for(VoltageSensor sensor : hardwareMap.voltageSensor)
            if(sensor.getVoltage() > 0)
                result = Math.min(result, sensor.getVoltage());

        return result;
    }
}