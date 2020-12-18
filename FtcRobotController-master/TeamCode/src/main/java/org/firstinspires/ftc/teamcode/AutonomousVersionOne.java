package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name="AutonomousVersionDrive")
public class AutonomousVersionOne extends LinearOpMode {

    MecanumHardware robot = new MecanumHardware();

    //intake rollers for 250 ms
    //then intake rollers for 400 ms
    //then intake rollers for 400 ms

    /*
       Move wobble goal to target zone
       shoot for top goal in tower

       3 seconds for winding up shooter motor
       .25 second for rolling the input
       .4 seconds for rolling the input for the next 2
     */

    @Override
    public void runOpMode()
    {
        robot.init(hardwareMap);

        waitForStart();
        while(opModeIsActive())
        {
            //if this doesn't work are we going to have to multithread?
            drive(.5);
            robot.output.setPower(1);
            sleep(1300);

            drive(0);
            robot.output.setPower(1);
            sleep(2500);

            robot.input.setPower(1);
            sleep(250);

            robot.input.setPower(0);
            robot.output.setPower(1);
            sleep(2500);

            robot.input.setPower(1);
            sleep(350);

            robot.input.setPower(0);
            robot.output.setPower(1);
            sleep(2500);

            robot.input.setPower(1);
            sleep(350);

            sleep(200000);
        }
    }

    public void drive(double power) {
        robot.leftFront.setPower(-power);  //negative should stay because of the direction of the robot
        robot.rightFront.setPower(-power);
        robot.leftBack.setPower(-power);
        robot.rightBack.setPower(-power);
    }


}
